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
import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;
import javax.swing.JPanel;

import net.dancioi.jcsphotogallery.client.shared.AlbumBean;
import net.dancioi.jcsphotogallery.client.shared.GalleryAlbums;
import net.dancioi.jcsphotogallery.client.shared.PictureBean;

/**
 * The Application's right side.
 * 
 * @author Daniel Cioi <dan@dancioi.net>
 * @version $Revision$ Last modified: $Date$, by: $Author$
 */
public class AppPanelRight extends JPanel {

	private static final long serialVersionUID = 1L;
	private JcsPhotoGalleryView view;
	private JPanel panels;
	private HelpPanel helpPanel;
	private GalleryPanel galleryPanel;
	private AlbumPanel albumPanel;
	private PicturePanel picturePanel;
	private CardLayout switchPanel;
	private JLabel infoLabel;
	private EditPanel frontPanel;

	public AppPanelRight(JcsPhotoGalleryView jcsPhotoGalleryView) {
		this.view = jcsPhotoGalleryView;
		initialize();
	}

	private void initialize() {
		this.setLayout(new BorderLayout());
		panels = new JPanel(new CardLayout());
		helpPanel = new HelpPanel();
		galleryPanel = new GalleryPanel();
		albumPanel = new AlbumPanel();
		picturePanel = new PicturePanel();

		panels.add(helpPanel, EditPanel.HELP.toString());
		panels.add(galleryPanel, EditPanel.GALLERY.toString());
		panels.add(albumPanel, EditPanel.ALBUM.toString());
		panels.add(picturePanel, EditPanel.PICTURE.toString());
		switchPanel = (CardLayout) (panels.getLayout());

		this.add(panels, BorderLayout.CENTER);
		this.add(infoPanel(), BorderLayout.PAGE_END);

		showPanel(EditPanel.HELP);
	}

	public void editGallery(GalleryAlbums galleryAlbums, String appGalleryPath) {
		// TODO solve info.
		// infoMessage("Info: " + "The gallery contains " + galleryAlbums.getTotalAlbumsNumber() + " albums and " + galleryAlbums.getTotalPicturesNumber() + " pictures");
		showPanel(EditPanel.GALLERY);
		galleryPanel.fillUpParameters(galleryAlbums, appGalleryPath);
	}

	public void editAlbum(AlbumBean album, BufferedImage albumThumbnail) {
		infoMessage(""); // bottom message when shows editAlbum panel
		showPanel(EditPanel.ALBUM);
		albumPanel.setCurrentAlbum(album, albumThumbnail);
	}

	public void editPicture(PictureBean pictureBean, BufferedImage picture) {
		infoMessage("Info: " + "Edit picture's name and description. It will be automatically updated.");
		showPanel(EditPanel.PICTURE);
		picturePanel.fillUpParameters(pictureBean, picture);
	}

	private void showPanel(EditPanel editPanel) {
		if (frontPanel != editPanel) {
			frontPanel = editPanel;
			switchPanel.show(panels, editPanel.toString());
		}
	}

	public GalleryPanel getGalleryPanel() {
		return galleryPanel;
	}

	public AlbumPanel getAlbumPanel() {
		return albumPanel;
	}

	public PicturePanel getPicturePanel() {
		return picturePanel;
	}

	private JPanel infoPanel() {
		JPanel info = new JPanel();
		infoLabel = new JLabel("Info: " + "Import a gallery from File > Import or create a new one from File > New.");
		infoLabel.setForeground(Color.BLUE);
		infoLabel.setPreferredSize(new Dimension(650, 32));
		info.add(infoLabel);
		return info;
	}

	public void infoMessage(String msg) {
		infoLabel.setText(msg);
	}

	enum EditPanel {
		HELP, GALLERY, ALBUM, PICTURE
	}

}
