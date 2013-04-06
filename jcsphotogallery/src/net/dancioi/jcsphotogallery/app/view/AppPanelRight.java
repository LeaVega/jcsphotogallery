/*	
 * 	File    : AppPanelRight.java
 * 
 * 	Copyright (C) 2012 Daniel Cioi <dan@dancioi.net>
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
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import net.dancioi.jcsphotogallery.client.shared.AlbumBean;
import net.dancioi.jcsphotogallery.client.shared.Constants;
import net.dancioi.jcsphotogallery.client.shared.GalleryAlbums;
import net.dancioi.jcsphotogallery.client.shared.PictureBean;

/**
 * The Application's right side.
 * 
 * @author Daniel Cioi <dan@dancioi.net>
 * @version $Revision$ Last modified: $Date$, by: $Author$
 */
public class AppPanelRight extends JPanel implements UpdateTree {

	private static final long serialVersionUID = 1L;
	private JcsPhotoGalleryView view;
	private JPanel panels;
	private HelpPanel helpPanel;
	private GalleryPanel galleryPanel;
	private AlbumPanel albumPanel;
	private PicturePanel picturePanel;
	private CardLayout switchPanel;
	private EditPanel frontPanel;
	private JProgressBar progressBar;

	public AppPanelRight(JcsPhotoGalleryView jcsPhotoGalleryView) {
		this.view = jcsPhotoGalleryView;
		initialize();
	}

	private void initialize() {
		this.setLayout(new BorderLayout());
		panels = new JPanel(new CardLayout());
		helpPanel = new HelpPanel();
		galleryPanel = new GalleryPanel(this);
		albumPanel = new AlbumPanel(this);
		picturePanel = new PicturePanel(this);

		panels.add(helpPanel, EditPanel.HELP.toString());
		panels.add(galleryPanel, EditPanel.GALLERY.toString());
		panels.add(albumPanel, EditPanel.ALBUM.toString());
		panels.add(picturePanel, EditPanel.PICTURE.toString());
		switchPanel = (CardLayout) (panels.getLayout());

		this.add(panels, BorderLayout.CENTER);
		this.add(getBottomPanel(), BorderLayout.PAGE_END);

		showPanel(EditPanel.HELP);
	}

	public void editGallery(GalleryAlbums galleryAlbums, String appGalleryPath) {
		int nrPictures = 0;
		DefaultTreeModel treeModel = (DefaultTreeModel) view.getTree().getModel();
		int childCount = treeModel.getChildCount(treeModel.getRoot());
		for (int i = 0; i < childCount; i++) {
			DefaultMutableTreeNode child = (DefaultMutableTreeNode) treeModel.getChild(treeModel.getRoot(), i);
			nrPictures += child.getChildCount();
		}
		infoMessage("Info: " + "The gallery contains " + childCount + " albums and " + nrPictures + " pictures");
		showPanel(EditPanel.GALLERY);
		galleryPanel.fillUpParameters(galleryAlbums, appGalleryPath);
	}

	public void editAlbum(AlbumBean album, BufferedImage albumThumbnail, DefaultMutableTreeNode treeNode) {
		infoMessage("Info: " + "Use character " + Constants.ALBUM_SEPARATOR + " to separate tags");
		showPanel(EditPanel.ALBUM);
		albumPanel.setCurrentAlbum(album, albumThumbnail, treeNode);
	}

	public void editPicture(PictureBean pictureBean, BufferedImage picture, DefaultMutableTreeNode treeNode) {
		infoMessage("Info: " + "Edit picture's name and description. It will be automatically updated.");
		showPanel(EditPanel.PICTURE);
		picturePanel.fillUpParameters(pictureBean, picture, treeNode);
	}

	public void showHelp() {
		infoMessage("Help...");
		showPanel(EditPanel.HELP);
	}

	private void showPanel(EditPanel editPanel) {
		if (frontPanel != editPanel) {
			frontPanel = editPanel;
			switchPanel.show(panels, editPanel.toString());
		}
	}

	public PicturePanel getPicturePanel() {
		return picturePanel;
	}

	private JPanel getBottomPanel() {
		JPanel bPanel = new JPanel();
		bPanel.setLayout(new BorderLayout());
		bPanel.add(new InfoPanel(), BorderLayout.WEST);
		bPanel.add(getProgressBarPanel(), BorderLayout.EAST);
		return bPanel;
	}

	private JPanel getProgressBarPanel() {
		JPanel progressBarPanel = new JPanel();
		progressBar = new JProgressBar(0, 100);
		progressBar.setValue(0);
		progressBar.setStringPainted(true);
		progressBarPanel.setLayout(new BorderLayout());
		progressBarPanel.setPreferredSize(new Dimension(150, 32));
		progressBarPanel.add(progressBar, BorderLayout.SOUTH);
		return progressBarPanel;
	}

	public JProgressBar getProgressBar() {
		return progressBar;
	}

	public void infoMessage(String msg) {
		InfoPanel.setInfoMessage(msg, InfoPanel.BLUE);
	}

	enum EditPanel {
		HELP, GALLERY, ALBUM, PICTURE
	}

	@Override
	public void updateNode(DefaultMutableTreeNode treeNode) {
		view.updateNode(treeNode);
	}

	public int getImageViewerMinSize() {
		return picturePanel.getImageViewerMinSize();
	}

}
