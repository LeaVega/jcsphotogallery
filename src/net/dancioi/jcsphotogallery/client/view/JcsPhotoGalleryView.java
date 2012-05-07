/*	
 * 	File    : JcsPhotoGalleryView.java
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

package net.dancioi.jcsphotogallery.client.view;

import net.dancioi.jcsphotogallery.client.shared.PictureBean;
import net.dancioi.jcsphotogallery.client.shared.Thumbnails;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * JcsPhotoGallery's view.
 * 
 * @author Daniel Cioi <dan@dancioi.net>
 * @version $Revision$ Last modified: $Date$, by: $Author$
 */
public class JcsPhotoGalleryView extends View {

	// TODO define background color in albums.xml
	// TODO add option to remove the selected categories.; not the best yet
	private String galleryVersion = "1.0.6";

	private Label headerLabel;
	private TopPanel topPanel;
	private String homeLink = "";

	private CenterPanel centerPanel;
	private BottomPanel bottomPanel;
	private VersionPanel versionPanel;

	public JcsPhotoGalleryView() {
		initialize();
	}

	/**
	 * Initialize
	 */
	public void initialize() {
		addHeader();
		addTopPanel();
		addBottomPanel();
		addAppVersion();
		addCenterPanel();
		addHandlers();
	}

	/*
	 * Adds the header where a text with the owner gallery will be shown.
	 */
	private void addHeader() {
		headerLabel = new Label("jcsPhotoGallery");
		headerLabel.setStyleName("h1");
		RootPanel.get("header").add(headerLabel);
	}

	/*
	 * Adds the center panel.
	 */
	private void addCenterPanel() {
		centerPanel = new CenterPanel(this);
		RootPanel.get("images").add(centerPanel);
	}

	/*
	 * Adds the top panel.
	 */
	private void addTopPanel() {
		topPanel = new TopPanel(this);
		RootPanel.get("topPanel").add(topPanel);
	}

	/*
	 * Adds the bottom panel.
	 */
	private void addBottomPanel() {
		bottomPanel = new BottomPanel(this);
		RootPanel.get("bottomPanel").add(bottomPanel);
	}

	private void addAppVersion() {
		versionPanel = new VersionPanel(galleryVersion);
		RootPanel.get("versionPanel").add(versionPanel);
	}

	private void addHandlers() {
		new ViewHandlers(bottomPanel, versionPanel);
	}

	/**
	 * Sets the owner gallery name and the owner's link to the home web page.
	 * 
	 * @param name
	 *            owner's name
	 * @param homePage
	 *            link to the home web page
	 */
	@Override
	public void setGalleryName(String name, String homePage) {
		setHeader(name);
		topPanel.setHomePage(name, homePage);
	}

	/*
	 * Sets the header owner's gallery.
	 * 
	 * @param name owners' name
	 */
	private void setHeader(String name) {
		headerLabel.setText(name + "'s gallery");
	}

	/**
	 * Notify the presenter about the click event on a cell.
	 * 
	 * @param id
	 */
	public void clickEventOnCell(int id) {
		getPresenter().clickedCellEvent(id);
	}

	/**
	 * Shows the popUp with the selected image.
	 * 
	 * @param id
	 */
	@Override
	public void showPopUpImg(int id, String imgPath, PictureBean[] pictures) {
		new PopUpImgShow(id - 1, imgPath, pictures).show();
	}

	@Override
	public void setLeftButtonVisible(boolean visible) {
		bottomPanel.setLeftButtonVisible(visible);
	}

	@Override
	public void setRightButtonVisible(boolean visible) {
		bottomPanel.setRightButtonVisible(visible);
	}

	@Override
	public void setUpButtonVisible(boolean visible) {
		bottomPanel.setUpButtonVisible(visible);
	}

	@Override
	public void setPageNr(String pageNr) {
		bottomPanel.setPageNr(pageNr);
	}

	/**
	 * Shows the image on grid (3x3 matrix)
	 */
	@Override
	public void showImagesOnGrid(String imagesPath, Thumbnails[] thumbnails) {
		centerPanel.showImages(imagesPath, thumbnails);
	}

	@Override
	public void setAlbumLabel(String albumName) {
		bottomPanel.setAlbumLabel(albumName);
	}

	public void showAlbumsByCategory(int selected) {
		getPresenter().getAlbumsByCategory(selected);
	}

	@Override
	public void setAlbumsTags(String[] tags) {
		topPanel.addTagsToListBox(tags);
	}
}
