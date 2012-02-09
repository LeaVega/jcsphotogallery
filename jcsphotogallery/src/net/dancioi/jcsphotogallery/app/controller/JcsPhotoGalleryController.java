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
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.filechooser.FileFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import net.dancioi.jcsphotogallery.app.model.JcsPhotoGalleryModelInterface;
import net.dancioi.jcsphotogallery.app.view.JcsPhotoGalleryViewInterface;
import net.dancioi.jcsphotogallery.client.model.PictureBean;

/**
 * JcsPhotoGallery's Controller.
 * 
 * @author Daniel Cioi <dan@dancioi.net>
 * @version $Revision$ Last modified: $Date: 2011-12-04 23:04:24 +0200
 *          (Sun, 04 Dec 2011) $, by: $Author$
 */
public class JcsPhotoGalleryController implements
		JcsPhotoGalleryControllerInterface {

	private JcsPhotoGalleryModelInterface model;
	private JcsPhotoGalleryViewInterface view;

	public JcsPhotoGalleryController(JcsPhotoGalleryModelInterface model,
			JcsPhotoGalleryViewInterface view) {
		this.model = model;
		this.view = view;

		initialize();
	}

	private void initialize() {
		view.addMenuBar(getMenu());
		addListenersToTree();
	}

	private JMenuBar getMenu() {
		JMenuBar menuBar = new JMenuBar();
		JMenu menuFile = new JMenu("File");
		menuFile.add(getMenuOpenGallery());
		menuFile.add(getMenuSaveGallery());
		menuFile.addSeparator();
		menuFile.add(getMenuExit());

		menuFile.setMnemonic(KeyEvent.VK_F);

		JMenu menuTools = new JMenu("Tools");
		menuTools.setMnemonic(KeyEvent.VK_T);

		JMenu menuHelp = new JMenu("Help");
		menuHelp.setMnemonic(KeyEvent.VK_H);
		menuHelp.add(getMenuAbout());

		menuBar.add(menuFile);
		menuBar.add(menuTools);
		menuBar.add(menuHelp);
		return menuBar;
	}

	private JMenuItem getMenuOpenGallery() {
		JMenuItem menuOpenGallery = new JMenuItem("Open Gallery");
		menuOpenGallery.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser folderChooser = new JFileChooser();
				folderChooser.setCurrentDirectory(new java.io.File("."));
				folderChooser.setDialogTitle("Choose the gallery.xml file.");
				folderChooser.setAcceptAllFileFilterUsed(false);
				folderChooser.setFileFilter(new GalleryFilter());
				if (folderChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					model.setGalleryPath(folderChooser.getSelectedFile());
					view.populateTree(model.getTreeNodes());
				}
			}
		});
		return menuOpenGallery;
	}

	private JMenuItem getMenuSaveGallery() {
		JMenuItem menuSaveGallery = new JMenuItem("Save Gallery");
		menuSaveGallery.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO here

			}
		});
		return menuSaveGallery;
	}

	private JMenuItem getMenuExit() {
		JMenuItem menuExit = new JMenuItem("Exit");
		menuExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO here

			}
		});
		return menuExit;
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
			if (e.isPopupTrigger()) {
				Point loc = e.getPoint();
				// popup.show(tree, loc.x, loc.y);

				TreePath treePath = view.getTree().getPathForLocation(loc.x, loc.y);
				System.out.printf("path = %s%n", treePath);

			}

		}

		public void mouseClicked(MouseEvent e) {
			Point loc = e.getPoint();

			TreePath path = view.getTree().getPathForLocation(loc.x, loc.y);
			System.out.printf("path = %s%n", path);
			DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) path
					.getLastPathComponent();
			PictureBean pictureBean = (PictureBean) treeNode.getUserObject();
			System.out.println(path.getLastPathComponent());
			System.out.println(model.getGalleryPath().getParent()
					+ File.separator + pictureBean.getParent().getFolderName()
					+ File.separator + pictureBean.getFileName());
			view.showPicture(model.getGalleryPath().getParent()
					+ File.separator + pictureBean.getParent().getFolderName()
					+ File.separator + pictureBean.getFileName());
		};

		public void mouseReleased(MouseEvent e) {
		}
	};

	private void showPicture() {
		// view.showPicture();
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
