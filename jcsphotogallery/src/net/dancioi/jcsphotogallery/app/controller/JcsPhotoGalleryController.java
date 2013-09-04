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
import java.awt.event.KeyListener;
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
import javax.swing.SwingWorker;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import net.dancioi.jcsphotogallery.app.model.JcsPhotoGalleryModelInterface;
import net.dancioi.jcsphotogallery.app.view.AboutFrame;
import net.dancioi.jcsphotogallery.app.view.JcsPhotoGalleryViewInterface;
import net.dancioi.jcsphotogallery.app.view.Preferences;
import net.dancioi.jcsphotogallery.client.shared.AlbumBean;
import net.dancioi.jcsphotogallery.client.shared.PictureBean;

/**
 * JcsPhotoGallery's Controller.
 * 
 * @author Daniel Cioi <dan@dancioi.net>
 * @version $Revision$ Last modified: $Date: 2013-04-07 02:54:26 +0300
 *          (Sun, 07 Apr 2013) $, by: $Author$
 */
public class JcsPhotoGalleryController implements JcsPhotoGalleryControllerInterface, RightClickPopUpInterface {
	// TODO on Mac OS - icons/imgNotFound.png File not found (icon's path issue
	// just on Mac OS)
	// TODO on linux, issue with OpenJDK when import images
	// TODO add undo function for remove actions
	// TODO after delete an album, move the selection on the above album
	private JcsPhotoGalleryModelInterface model;
	private JcsPhotoGalleryViewInterface view;
	private RightClickPopUp rightClickPopUp;
	private File currentFolder = new java.io.File(".");

	public JcsPhotoGalleryController(JcsPhotoGalleryModelInterface model, JcsPhotoGalleryViewInterface view) {
		this.model = model;
		this.view = view;

		initialize();
	}

	private void initialize() {
		model.bindView(view);
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
		File previousGalleryPath = model.getConfigs().getGalleryPath();
		if (previousGalleryPath != null) {
			openGallery(previousGalleryPath);
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
		menuHelp.add(getHelpContents());
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
		view.populateTree(model.getNewGalleryTreeNodes(galleryPath));
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
					if (folderChooser.getSelectedFile().getAbsolutePath().toLowerCase().endsWith("albums.xml"))
						openGallery(folderChooser.getSelectedFile().getParentFile());
				}
			}
		});
		return menuOpenGallery;
	}

	private void openGallery(File galleryPath) {
		DefaultMutableTreeNode[] galleryTree = model.getGalleryTreeNodes(galleryPath);
		if (galleryTree != null) {
			view.populateTree(galleryTree);
		}
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
			int exitQuestion = JOptionPane.showConfirmDialog(null, "The changes you have made are not saved\n" + "Press YES to saved it!!! \nor NO to exit without saving them", "Exit question", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
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
				new Preferences(model.getConfigs());
			}
		});
		return menuPreferences;
	}

	private JMenuItem getHelpContents() {
		JMenuItem menuAbout = new JMenuItem("Help Contents");
		menuAbout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				view.showHelp();
			}
		});
		return menuAbout;
	}

	private JMenuItem getMenuAbout() {
		JMenuItem menuAbout = new JMenuItem("About...");
		menuAbout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new AboutFrame().setVisible(true);
			}
		});
		return menuAbout;
	}

	private void addListenersToTree() {
		view.getTree().addMouseListener(mouseListener);
		view.getLeftPanel().addMouseListener(mouseListener);
		view.getTree().addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent tse) {
				TreePath treePath = tse.getPath();
				// System.out.printf("path = %s%n", treePath);
				if (null != treePath) {
					DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) treePath.getLastPathComponent();
					model.selectNode(treeNode);
				}
			}
		});
		view.getTree().addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_DELETE) {
					TreePath treePath = view.getTree().getSelectionPath();
					if (null != treePath) {
						DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) treePath.getLastPathComponent();
						deleteImage(treeNode);
					}
				}

			}
		});
	}

	private MouseListener mouseListener = new MouseAdapter() {

		public void mousePressed(MouseEvent e) {
			popUpShow(e); // for mac and linux
		}

		public void mouseClicked(MouseEvent e) {
			// some times the click event is missed, mousePressed is used
			// instead.
		};

		public void mouseReleased(MouseEvent e) {
			popUpShow(e); // for windows
		}
	};

	private boolean popUpShow(MouseEvent e) {
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
			} else {
				TreePath pathForRow = view.getTree().getPathForRow(0);
				DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) pathForRow.getPathComponent(0);
				rightClickPopUp.enableMenus(RightClickPopUp.ROOT, treeNode);
			}
			rightClickPopUp.show(e.getComponent(), loc.x, loc.y);
			return true;
		}
		return false;
	}

	@Override
	public void addNewAlbum() {
		JFileChooser folderChooser = new JFileChooser();
		folderChooser.setCurrentDirectory(currentFolder);
		folderChooser.setDialogTitle("Select the jpg files. Multi-selection is allowed.");
		folderChooser.setAcceptAllFileFilterUsed(false);
		folderChooser.setFileFilter(new PictureFilter());
		folderChooser.setMultiSelectionEnabled(true);
		if (folderChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			File[] selectedFiles = folderChooser.getSelectedFiles();
			currentFolder = selectedFiles[0].getParentFile();
			new TaskImportPicturesToNewAlbum(folderChooser.getSelectedFiles()).execute();
		}
	}

	class TaskImportPicturesToNewAlbum extends SwingWorker<Void, Void> {
		private File[] files;

		public TaskImportPicturesToNewAlbum(File[] files) {
			this.files = files;
		}

		@Override
		protected Void doInBackground() {
			DefaultMutableTreeNode picturesToNewAlbum = model.getPicturesTreeNodeAddedToNewAlbum(files, view.getProgressBar());
			view.addNewAlbumToGallery(picturesToNewAlbum);
			return null;
		}

		@Override
		protected void done() {

		}
	}

	@Override
	public void addNewImage(DefaultMutableTreeNode treeNode) {
		if (treeNode.getUserObject() instanceof AlbumBean) {
			JFileChooser folderChooser = new JFileChooser();
			folderChooser.setCurrentDirectory(currentFolder);
			folderChooser.setDialogTitle("Select the jpg files. Multi-selection is allowed.");
			folderChooser.setAcceptAllFileFilterUsed(false);
			folderChooser.setFileFilter(new PictureFilter());
			folderChooser.setMultiSelectionEnabled(true);
			if (folderChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				currentFolder = folderChooser.getSelectedFile().getParentFile();
				new TaskImportPicturesToExistingAlbum(folderChooser.getSelectedFiles(), treeNode).execute();
			}
		}
	}

	class TaskImportPicturesToExistingAlbum extends SwingWorker<Void, Void> {
		private File[] files;
		private DefaultMutableTreeNode treeNode;

		public TaskImportPicturesToExistingAlbum(File[] files, DefaultMutableTreeNode treeNode) {
			this.files = files;
			this.treeNode = treeNode;
		}

		@Override
		protected Void doInBackground() {
			DefaultMutableTreeNode picturesToExistingAlbum = model.getPicturesTreeNodesAddedToExistingAlbum(files, treeNode, view.getProgressBar());
			view.addPicturesToAnExistingAlbum(picturesToExistingAlbum);
			return null;
		}

		@Override
		protected void done() {

		}
	}

	@Override
	public void setAlbumImage(DefaultMutableTreeNode treeNode) {
		if (treeNode.getUserObject() instanceof PictureBean) {
			PictureBean pictureBean = (PictureBean) treeNode.getUserObject();
			AlbumBean album = pictureBean.getParent();
			album.setImgThumbnail(pictureBean.getImgThumbnail());
			album.getParent().setEdited(true);
		}

	}

	@Override
	public void deleteImage(DefaultMutableTreeNode treeNode) {
		model.deleteImage(treeNode);
	}

	@Override
	public void deleteAlbum(DefaultMutableTreeNode treeNode) {
		model.deleteAlbum(treeNode);
	}

	@Override
	public ActionListener addNextPictureActionListener() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.selectNextNode();
			}
		};
	}

	@Override
	public ActionListener addPreviousPictureActionListener() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.selectPreviousNode();
			}
		};
	}

	@Override
	public ActionListener addRotatePictureClockwiseActionListener() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.rotatePictureClockwise();
			}
		};
	}

	@Override
	public ActionListener addRotatePictureCounterClockwiseActionListener() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.rotatePictureCounterClockwise();
			}
		};
	}

	@Override
	public boolean isGalleryCreated() {
		return model.getAppGalleryPath() == null ? false : true;
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

}