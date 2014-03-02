/*	
 * 	File    : PopUpImgShowMobile.java
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

import net.dancioi.jcsphotogallery.client.view.ImagePopUp;
import net.dancioi.jcsphotogallery.client.view.PopUpImgShow;
import net.dancioi.jcsphotogallery.shared.PictureBean;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Touch;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;

/**
 * This class shows the selected image (from the center panel) on a PopUp panel.
 * 
 * @author Daniel Cioi <dan@dancioi.net>
 * @version $Revision: 102 $ Last modified: $Date: 2013-09-08 22:50:02 +0200
 *          (Sun, 08 Sep 2013) $, by: $Author: dan.cioi $
 */
public class PopUpImgShowMobile extends PopUpImgShow {

	private int popUpSizeX;
	private int popUpSizeY;
	private AbsolutePanel imgPanel;
	private int imgPanelSizeX;
	private int imgPanelSizeY;

	private PictureBean[] pictures;

	private int imgStart; // image id from to start.
	private String imgPath; // images path
	private ImagePopUp img; // image which will be shown.

	private Image closeIcon;

	private Label lImgName; // image name
	private Label lImgComment; // image comment

	private int currentImg;

	/* add a loading message */
	private String loading;
	private Label loadingLabel;
	private int ind = 1;
	private Timer loadingTimer;

	private int startPositionX = -1;
	private int startPositionY = -1;

	public PopUpImgShowMobile(int imgStart, String imgPath,
			PictureBean[] pictures) {
		super();
		this.imgStart = imgStart;
		this.imgPath = imgPath;
		this.pictures = pictures;
		currentImg = imgStart;
		initialize();
	}

	private void initialize() {
		setGlassStyleName("gwt-PopupPanelGlass");
		setGlassEnabled(true);

		sinkEvents(Event.TOUCHEVENTS | Event.ONCLICK);

		computeMaximSquareSize();
		setPosition();

		AbsolutePanel ap = new AbsolutePanel();
		ap.setPixelSize(popUpSizeX, popUpSizeY);
		ap.setStyleName("popUpPanel");

		imgPanel = new AbsolutePanel();
		imgPanelSizeX = popUpSizeX;
		imgPanelSizeY = popUpSizeY;
		imgPanel.setPixelSize(imgPanelSizeX, imgPanelSizeY);

		ap.add(imgPanel, 1, 1);

		AbsolutePanel bottomPanel = getBottomControllPanel();
		ap.add(bottomPanel, 1, popUpSizeY - 40);

		setWidget(ap);

		addImage(imgPath + pictures[imgStart].getFileName());

	}

	@Override
	public void onBrowserEvent(Event event) {
		GWT.log("Event " + event.getType());
		switch (DOM.eventGetType(event)) {
		case Event.ONTOUCHMOVE: {
			onTouchMove(event);
			break;
		}
		case Event.ONTOUCHEND: {
			break;
		}
		case Event.ONTOUCHSTART: {
			onTouchStart(event);
			break;
		}
		case Event.ONCLICK: {
			// TODO check with click event on closeIcon
		}
		}
		super.onBrowserEvent(event);
	}

	private void onTouchMove(Event event) {
		Touch touch = event.getTouches().get(0);
		int movedX = startPositionX - touch.getClientX();
		int movedY = startPositionY - touch.getClientY();
		if (movedX > 15) {
			nextImg();
		}
		if (movedX < -15) {
			previousImg();
		}

		if (movedY > 15) {
			closeImg();
		}
	}

	private void onTouchStart(Event event) {
		Touch touch = event.getTouches().get(0);
		this.startPositionX = touch.getClientX();
		this.startPositionY = touch.getClientY();
	}

	// TODO make the bottom panel bigger (text and close button)
	private AbsolutePanel getBottomControllPanel() {
		AbsolutePanel bottomPanel = new AbsolutePanel();
		bottomPanel.setPixelSize(popUpSizeX, 50);
		bottomPanel.setStyleName("bottomPanel");

		closeIcon = new Image("template/ext/close.gif");
		closeIcon.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				closeImg();
			}
		});
		bottomPanel.add(closeIcon, popUpSizeX - 70, 3);

		lImgName = new Label();
		bottomPanel.add(lImgName, 20, 2);

		lImgComment = new Label();
		bottomPanel.add(lImgComment, 20, 22);

		loading = "Loading...";
		loadingLabel = new Label();
		loadingLabel.setText(loading);
		bottomPanel.add(loadingLabel, popUpSizeX / 2, 2);

		return bottomPanel;
	}

	/*
	 * Method to show the next image.
	 */
	private void nextImg() {
		showLoadingProcess(false);
		if (currentImg < (pictures.length - 1)) {
			currentImg++;
			addImage(imgPath + pictures[currentImg].getFileName());
		}
	}

	/*
	 * Method to show the previous image.
	 */
	private void previousImg() {
		showLoadingProcess(false);
		if (currentImg > 0) {
			currentImg--;
			addImage(imgPath + pictures[currentImg].getFileName());
		}
	}

	/*
	 * Method to close the popup.
	 */
	private void closeImg() {
		this.hide();
	}

	/*
	 * Method to add image to the panel. Because the image size method from the
	 * image class can't return the values until the image is fully loaded by
	 * browser, this method add the image on the popup panel's bottom right
	 * corner (making the image invisible and creating the background loading
	 * effect). When the image is complete downloaded, get the image's size and
	 * the it's added to the popup panel.
	 */
	private void addImage(String imagePath) {
		showLoadingProcess(true);
		img = new ImagePopUp(imagePath, this);
		imgPanel.add(img, imgPanelSizeX, imgPanelSizeY);
	}

	/**
	 * scale the image to fit the panel
	 */
	@Override
	public void scaleImg(Image im) {
		showLoadingProcess(false);

		int iox = im.getWidth();
		int ioy = im.getHeight();

		float scaleX = (float) imgPanelSizeX / iox;
		float scaleY = (float) imgPanelSizeY / ioy;
		// TODO change if the phone is rotated
		// catch the event and adjust according
		float scalef = scaleX <= scaleY ? scaleX : scaleY;
		int ix = (int) (iox * scalef);
		int iy = (int) (ioy * scalef);
		img.setPixelSize(ix, iy);

		lImgName.setText(pictures[currentImg].getName());
		lImgComment.setText(pictures[currentImg].getDescription());

		clearImg();
		imgPanel.add(img, (imgPanelSizeX - ix) / 2, (imgPanelSizeY - iy) / 2);

		getNextAndPrevious(currentImg); // cache the next and previous pictures;
	}

	/*
	 * Method to set the maxim popup size. The dimension should be the same
	 * (square).
	 */
	private void computeMaximSquareSize() {
		int maxX = getBrowserWindowWidth();
		int maxY = getBrowserWindowHeight();
		int border = 30;
		popUpSizeX = maxX - border;
		popUpSizeY = maxY - border;
		setSizeX(popUpSizeX);
		setSizeY(popUpSizeY);
	}

	/*
	 * Method to clear the old image before add the new one.
	 */
	private void clearImg() {
		imgPanel.clear();
	}

	/*
	 * This method shows the picture's loading process.
	 */
	private void showLoadingProcess(boolean show) {
		if (show) {
			// show the loading bar
			ind = 1;
			loadingLabel.setText(loading.substring(0, ind));

			loadingTimer = new Timer() {
				@Override
				public void run() {
					if (ind < loading.length()) {
						ind++;
					} else {
						ind = 1;
					}
					loadingLabel.setText(loading.substring(0, ind));
				}
			};

			loadingTimer.scheduleRepeating(500);
		} else {
			// hide the loading bar
			loadingTimer.cancel();
			loadingLabel.setText("");
		}
	}

	/*
	 * Cache the next and previous pictures to reduce the loading time. cache
	 * the next and previous image. Just brings the images in browser's cache.
	 * 
	 * @param i picture id
	 */
	private void getNextAndPrevious(int i) {
		if (i < pictures.length - 1)
			new Image(imgPath + pictures[i + 1].getFileName());
		if (i > 0)
			new Image(imgPath + pictures[i - 1].getFileName());
	}

}
