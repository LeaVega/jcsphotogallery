/*	
 * 	File    : JcsPhotoGalleryMobileView.java
 * 
 * 	Copyright (C) 2014 Daniel Cioi <dan@dancioi.net>
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

package net.dancioi.jcsphotogallery.client.view.mobile;

import net.dancioi.jcsphotogallery.client.view.CenterPanel;
import net.dancioi.jcsphotogallery.client.view.JcsPhotoGalleryView;
import net.dancioi.jcsphotogallery.shared.JcsPhotoGalleryConstants;
import net.dancioi.jcsphotogallery.shared.PictureBean;
import net.dancioi.jcsphotogallery.shared.Thumbnails;

import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * JcsPhotoGallery's view for mobile devices.
 * 
 * @author Daniel Cioi <dan@dancioi.net>
 * @version $Revision: 102 $ Last modified: $Date: 2013-09-08 22:50:02 +0200
 *          (Sun, 08 Sep 2013) $, by: $Author: dan.cioi $
 */
public class JcsPhotoGalleryMobileView extends JcsPhotoGalleryView {

	private Label headerLabel;
	private TopPanel topPanel;

	private CenterPanel centerPanel;
	private BottomPanel bottomPanel;
	private VersionPanel versionPanel;

	private PopUpImgShowMobile popUpImgShow = null;
	private String currentImgPath;
	private PictureBean[] currentPictures;

	/*
	 * Adds the header where a text with the owner gallery will be shown.
	 */
	@Override
	protected void addHeader() {
		headerLabel = new Label("jcsPhotoGallery");
		headerLabel.setStyleName("h1");
		RootPanel.get("header").add(headerLabel);
	}

	/*
	 * Adds the center panel.
	 */
	@Override
	protected void addCenterPanel() {
		centerPanel = new CenterPanel(this);
		RootPanel.get("images").add(centerPanel);
	}

	/*
	 * Adds the top panel.
	 */
	@Override
	protected void addTopPanel() {
		topPanel = new TopPanel(this);
		RootPanel.get("topPanel").add(topPanel);
	}

	/*
	 * Adds the bottom panel.
	 */
	@Override
	protected void addBottomPanel() {
		bottomPanel = new BottomPanel(this);
		RootPanel.get("bottomPanel").add(bottomPanel);
	}

	@Override
	protected void addAppVersion() {
		versionPanel = new VersionPanel(JcsPhotoGalleryConstants.APP_VERSION);
		RootPanel.get("versionPanel").add(versionPanel);
	}

	@Override
	protected void addHandlers() {
		// new ViewHandlers(bottomPanel, versionPanel);

		Window.addResizeHandler(new ResizeHandler() {

			@Override
			public void onResize(ResizeEvent event) {
				if (popUpImgShow != null && popUpImgShow.isShowing()) {
					screenRotationEvent(popUpImgShow.getCurentImgId());

				}
			}
		});
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
		currentImgPath = imgPath;
		currentPictures = pictures;
		showImgOneByOne(id - 1, imgPath, pictures);
	}

	private void showImgOneByOne(int id, String imgPath, PictureBean[] pictures) {
		if (popUpImgShow != null) {// TODO check what is happen with this one!
									// is destroyed or?
			popUpImgShow.removeFromParent();
		}
		popUpImgShow = new PopUpImgShowMobile(id, imgPath, pictures);
		popUpImgShow.show();
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
		bottomPanel.setLeftCornerLabel(albumName);
	}

	@Override
	public void setTagsLabel(String selectedTags) {
		bottomPanel.setLeftCornerLabel(selectedTags);
	}

	public void showAlbumsByTag(String selectedTags) {
		getPresenter().showAlbumsByTag(selectedTags);
	}

	@Override
	public void setAlbumsTags(String[] tags) {
		topPanel.addTagsToListBox(tags);
	}

	@Override
	public void noGalleryToShow() {
		bottomPanel.setLeftCornerLabelAlertMode();
		showAlertMessage("The selected tags togheter returns no albums!\nPlease remove one of the tags already selected \nby selecting one of it again from \"Sort Albums by tag\".");
	}

	@Override
	public void showAlertMessage(String msg) {
		Window.alert(msg);
	}

	public void screenRotationEvent(int imgId) {
		showImgOneByOne(imgId, currentImgPath, currentPictures);
	}

}
