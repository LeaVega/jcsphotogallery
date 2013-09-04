/*	
 * 	File    : JcsPhotoGalleryView.java
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

package net.dancioi.jcsphotogallery.app.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import net.dancioi.jcsphotogallery.app.controller.JcsPhotoGalleryControllerInterface;
import net.dancioi.jcsphotogallery.app.model.JcsPhotoGalleryModelInterface;
import net.dancioi.jcsphotogallery.client.shared.AlbumBean;
import net.dancioi.jcsphotogallery.client.shared.GalleryAlbums;
import net.dancioi.jcsphotogallery.client.shared.PictureBean;

/**
 * JcsPhotoGallery's View
 * 
 * @author Daniel Cioi <dan@dancioi.net>
 * @version $Revision$ Last modified: $Date$, by: $Author$
 */

public class JcsPhotoGalleryView extends JFrame implements JcsPhotoGalleryViewInterface {

	private static final long serialVersionUID = 1L;
	private JcsPhotoGalleryModelInterface model;
	private AppPanelLeft panelLeft;
	private AppPanelRight panelRight;
	private int minPictureViewSize = 600;

	/**
	 * Default constructor.
	 */
	public JcsPhotoGalleryView(JcsPhotoGalleryModelInterface model) {
		super("JcsPhotoGallery");
		this.model = model;
		initialize();
	}

	/**
	 * Initialize.
	 */
	private void initialize() {
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.setContentPane(getMainPanel());
		this.setMinimumSize(new Dimension(800, 600));
		this.setPreferredSize(new Dimension(1050, 750));

		this.getRootPane().addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				frameResizeEvent(e);
			}

		});

		this.pack();
	}

	private void frameResizeEvent(ComponentEvent e) {
		minPictureViewSize = panelRight.getImageViewerMinSize();
		// System.out.println(e.getComponent().getWidth() + "  " + e.getComponent().getHeight());
	}

	/**
	 * Method to get the JFrame's ContentPane.
	 * 
	 * @return JPanel
	 */
	private JSplitPane getMainPanel() {
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, getLeftPanel(), getRightPanel());
		splitPane.setOneTouchExpandable(false);
		splitPane.setDividerLocation(250);
		return splitPane;
	}

	/**
	 * Right panel with the gallery tree structure.
	 * 
	 * @return JPanel
	 */
	private JPanel getRightPanel() {
		panelRight = new AppPanelRight(this);
		return panelRight;
	}

	/**
	 * Left panel with the gallery tree structure.
	 * 
	 * @return JPanel
	 */
	@Override
	public JPanel getLeftPanel() {
		if (panelLeft == null) {
			panelLeft = new AppPanelLeft(this);
		}
		return panelLeft;
	}

	@Override
	public void addMenuBar(JMenuBar menuBar) {
		this.setJMenuBar(menuBar);
	}

	@Override
	public void populateTree(DefaultMutableTreeNode[] treeNodes) {
		panelLeft.addGalleryAlbums(treeNodes);
		// switch to gallery edit panel after the gallery was imported.
		showGallery(model.getGalleryAlbums());
	}

	@Override
	public JTree getTree() {
		return panelLeft.getTree();
	}

	@Override
	public void addNewAlbumToGallery(DefaultMutableTreeNode newAlbum) {
		panelLeft.addAlbumToGallery(newAlbum);
	}

	@Override
	public void addPicturesToAnExistingAlbum(DefaultMutableTreeNode addPicturesToExistingAlbum) {
		panelLeft.addPicturesToAnExistingAlbum();

	}

	@Override
	public void attachActions(JcsPhotoGalleryControllerInterface controller) {
		panelRight.getPicturePanel().attachActions(controller);
	}

	@Override
	public void showPicture(PictureBean pictureBean, BufferedImage picture, DefaultMutableTreeNode treeNode) {
		panelRight.editPicture(pictureBean, picture, treeNode);
		getTree().setSelectionPath(new TreePath(treeNode.getPath()));
	}

	@Override
	public void showAlbum(AlbumBean album, BufferedImage picture, DefaultMutableTreeNode treeNode) {
		panelRight.editAlbum(album, picture, treeNode);

	}

	@Override
	public void showHelp() {
		panelRight.showHelp();
	}

	@Override
	public void showGallery(GalleryAlbums galleryAlbums) {
		if (null != model.getAppGalleryPath())
			panelRight.editGallery(galleryAlbums, model.getAppGalleryPath().getAbsolutePath());
	}

	@Override
	public void addCloseWindowListener(WindowAdapter windowAdapter) {
		addWindowListener(windowAdapter);
	}

	public void updateNode(final DefaultMutableTreeNode treeNode) {
		((DefaultTreeModel) getTree().getModel()).nodeChanged(treeNode);

	}

	public JcsPhotoGalleryModelInterface getModel() {
		return model;
	}

	@Override
	public JProgressBar getProgressBar() {
		return panelRight.getProgressBar();
	}

	public void selectPicture(DefaultMutableTreeNode destNode) {
		model.selectNode(destNode);
	}

	@Override
	public int getMinPictureViewSize() {
		return minPictureViewSize;
	}

	@Override
	public boolean isDeleteConfirmed() {
		int confirmation = JOptionPane.showConfirmDialog(null, "Should also be deleted the jpg files for removed pictures?", "Delete question", JOptionPane.YES_NO_OPTION);
		return confirmation == 0 ? true : false;
	}

	@Override
	public void showMessageToUser(String messageToUser) {
		JOptionPane.showMessageDialog(this, messageToUser, "An Exception has occurred!", JOptionPane.INFORMATION_MESSAGE);
	}

}