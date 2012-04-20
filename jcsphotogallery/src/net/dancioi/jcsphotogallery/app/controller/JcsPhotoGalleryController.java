/*	
 * 	File    : JcsPhotoGalleryController.java
 * 
 * 	Copyright (C) 2011 Daniel Cioi <dan@dancioi.net>
 *                              
 *	www.dancioi.net/projects/Jcsphotogallery
 *
 *	This file is part of Jcsphotogallery.
 *
 *  Jcsphotogallery is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Jcsphotogallery is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Jcsphotogallery.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */

package net.dancioi.jcsphotogallery.app.controller;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import net.dancioi.jcsphotogallery.app.model.JcsPhotoGalleryModelInterface;
import net.dancioi.jcsphotogallery.app.view.JcsPhotoGalleryViewInterface;
import net.dancioi.jcsphotogallery.client.shared.AlbumBean;
import net.dancioi.jcsphotogallery.client.shared.PictureBean;

/**
 * JcsPhotoGallery's Controller.
 * 
 * @author Daniel Cioi <dan@dancioi.net>
 * @version $Revision$ Last modified: $Date: 2011-12-04 23:04:24 +0200
 *          (Sun, 04 Dec 2011) $, by: $Author$
 */
public class JcsPhotoGalleryController implements JcsPhotoGalleryControllerInterface, RightClickPopUpInterface {

	private JcsPhotoGalleryModelInterface model;
	private JcsPhotoGalleryViewInterface view;
	private RightClickPopUp rightClickPopUp;

	public JcsPhotoGalleryController(JcsPhotoGalleryModelInterface model, JcsPhotoGalleryViewInterface view) {
		this.model = model;
		this.view = view;

		initialize();
	}

	private void initialize() {
		rightClickPopUp = new RightClickPopUp(this);
		view.addMenuBar(getMenu());
		addListenersToTree();
		view.attachActions(this);
		view.addCloseWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				questionDialogOnExit();
			}
		});

		view.setVisible(true);

		// load the last imported gallery
		if (model.getConfigs().getGalleryPath() != null) {
			openGallery(model.getConfigs().getGalleryPath());
		}
	}

	private JMenuBar getMenu() {
		JMenuBar menuBar = new JMenuBar();
		JMenu menuFile = new JMenu("File");
		menuFile.add(getMenuNewGallery());
		menuFile.add(getMenuOpenGallery());
		menuFile.add(getMenuSaveGallery());
		menuFile.addSeparator();
		menuFile.add(getMenuExit());

		menuFile.setMnemonic(KeyEvent.VK_F);

		JMenu menuTools = new JMenu("Tools");
		menuTools.setMnemonic(KeyEvent.VK_T);
		menuTools.add(getPreferences());

		JMenu menuHelp = new JMenu("Help");
		menuHelp.setMnemonic(KeyEvent.VK_H);
		menuHelp.add(getMenuAbout());

		menuBar.add(menuFile);
		menuBar.add(menuTools);
		menuBar.add(menuHelp);
		return menuBar;
	}

	private JMenuItem getMenuNewGallery() {
		JMenuItem menuNewGallery = new JMenuItem("New");
		menuNewGallery.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser folderChooser = new JFileChooser();
				folderChooser.setCurrentDirectory(new java.io.File("."));
				folderChooser.setDialogTitle("Choose the folder to create gallery.");
				folderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				if (folderChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

					createNewGallery(folderChooser.getSelectedFile());
				}
			}
		});
		return menuNewGallery;
	}

	private void createNewGallery(File galleryPath) {
		view.populateTree(model.createNewGallery(galleryPath));
	}

	private JMenuItem getMenuOpenGallery() {
		JMenuItem menuOpenGallery = new JMenuItem("Import");
		menuOpenGallery.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser folderChooser = new JFileChooser();
				folderChooser.setCurrentDirectory(new java.io.File("."));
				folderChooser.setDialogTitle("Choose the gallery.xml file.");
				folderChooser.setAcceptAllFileFilterUsed(false);
				folderChooser.setFileFilter(new GalleryFilter());
				if (folderChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

					openGallery(folderChooser.getSelectedFile());
				}
			}
		});
		return menuOpenGallery;
	}

	private void openGallery(File galleryPath) {
		view.populateTree(model.loadGallery(galleryPath));
	}

	private JMenuItem getMenuSaveGallery() {
		JMenuItem menuSaveGallery = new JMenuItem("Save");
		menuSaveGallery.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveGalleryChanges();
			}
		});
		return menuSaveGallery;
	}

	private void saveGalleryChanges() {
		model.saveGalleryChanges(view.getTree());
	}

	private void exitApplication() {
		System.exit(0);
	}

	private JMenuItem getMenuExit() {
		JMenuItem menuExit = new JMenuItem("Exit");
		menuExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				questionDialogOnExit();
			}
		});
		return menuExit;
	}

	private void questionDialogOnExit() {
		if (model.isGallerySaved(view.getTree())) {
			exitApplication();
		} else {
			int exitQuestion = JOptionPane.showConfirmDialog(null, "The changes that you have made are not saved\n" + "Press YES to saved it!!! \nor NO to exit without saving them", "Exit question", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (exitQuestion == JOptionPane.YES_OPTION) {
				saveGalleryChanges();
			}
			exitApplication();
		}
	}

	private JMenuItem getPreferences() {
		JMenuItem menuPreferences = new JMenuItem("Preferences...");
		menuPreferences.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO here

			}
		});
		return menuPreferences;
	}

	private JMenuItem getMenuAbout() {
		JMenuItem menuAbout = new JMenuItem("About...");
		menuAbout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO here

			}
		});
		return menuAbout;
	}

	private void addListenersToTree() {
		view.getTree().addMouseListener(mouseListener);
	}

	private MouseListener mouseListener = new MouseAdapter() {

		public void mousePressed(MouseEvent e) {
			// working on mac, not on windows
		}

		public void mouseClicked(MouseEvent e) {
			Point loc = e.getPoint();

			TreePath treePath = view.getTree().getPathForLocation(loc.x, loc.y);
			System.out.printf("path = %s%n", treePath);
			if (null != treePath) {
				DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) treePath.getLastPathComponent();
				if (treeNode.getUserObject() instanceof PictureBean) {
					PictureBean pictureBean = (PictureBean) treeNode.getUserObject();
					System.out.println(treePath.getLastPathComponent());
					view.showPicture(pictureBean);
				} else if (treeNode.getUserObject() instanceof AlbumBean) {
					AlbumBean albumBean = (AlbumBean) treeNode.getUserObject();
					System.out.println(treePath.getLastPathComponent());
					view.showAlbum(albumBean);
				} else if (treeNode.getUserObject() instanceof String) {
					System.out.println(treePath.getLastPathComponent());
					view.showGallery(model.getGalleryAlbums());
				}
			}
		};

		public void mouseReleased(MouseEvent e) {
			if (e.isPopupTrigger()) {
				Point loc = e.getPoint();

				TreePath treePath = view.getTree().getPathForLocation(loc.x, loc.y);
				if (null != treePath) {
					DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) treePath.getLastPathComponent();
					if (treeNode.getUserObject() instanceof PictureBean) {
						rightClickPopUp.enableMenus(RightClickPopUp.IMAGES, treeNode);
					} else if (treeNode.getUserObject() instanceof AlbumBean) {
						rightClickPopUp.enableMenus(RightClickPopUp.ALBUMS, treeNode);
					} else {
						rightClickPopUp.enableMenus(RightClickPopUp.ROOT, treeNode);
					}
					rightClickPopUp.show(e.getComponent(), loc.x, loc.y);
				}
				System.out.printf("path = %s%n", treePath);

			}
		}
	};

	@Override
	public void addNewAlbum() {
		JFileChooser folderChooser = new JFileChooser();
		folderChooser.setCurrentDirectory(new java.io.File("."));
		folderChooser.setDialogTitle("Select the jpg files. Multi-selection is allowed.");
		folderChooser.setAcceptAllFileFilterUsed(false);
		folderChooser.setFileFilter(new PictureFilter());
		folderChooser.setMultiSelectionEnabled(true);
		if (folderChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			view.addNewAlbumToGallery(model.addPicturesToNewAlbum(folderChooser.getSelectedFiles()));
		}

	}

	@Override
	public void addNewImage(DefaultMutableTreeNode treeNode) {
		if (treeNode.getUserObject() instanceof AlbumBean) {
			JFileChooser folderChooser = new JFileChooser();
			folderChooser.setCurrentDirectory(new java.io.File("."));
			folderChooser.setDialogTitle("Select the jpg files. Multi-selection is allowed.");
			folderChooser.setAcceptAllFileFilterUsed(false);
			folderChooser.setFileFilter(new PictureFilter());
			folderChooser.setMultiSelectionEnabled(true);
			if (folderChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				view.addPicturesToAnExistingAlbum(model.addPicturesToExistingAlbum(folderChooser.getSelectedFiles(), treeNode));
			}
		}

	}

	@Override
	public void setAlbumImage() {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteImage() {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAlbum() {
		// TODO Auto-generated method stub

	}

	@Override
	public ActionListener addNextPictureActionListener() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// nextPictureButton();
			}
		};
	}

	@Override
	public ActionListener addPreviousPictureActionListener() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// previousPictureButton();
			}
		};
	}

}

class GalleryFilter extends FileFilter {

	@Override
	public boolean accept(File arg0) {
		if (arg0.isDirectory()) {
			return true;
		}

		if (arg0.getName().equals("albums.xml"))
			return true;
		else
			return false;
	}

	@Override
	public String getDescription() {
		return "albums.xml filter";
	}

}

class PictureFilter extends FileFilter {

	@Override
	public boolean accept(File arg0) {
		if (arg0.isDirectory()) {
			return true;
		}

		if (arg0.getName().toLowerCase().endsWith(".jpg"))
			return true;
		else
			return false;
	}

	@Override
	public String getDescription() {
		return "jpg files filter";
	}

}