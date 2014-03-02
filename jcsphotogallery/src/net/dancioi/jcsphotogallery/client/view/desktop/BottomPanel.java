/*	
 * 	File    : BottomPanel.java
 * 
 * 	Copyright (C) 2010-2011 Daniel Cioi <dan@dancioi.net>
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

package net.dancioi.jcsphotogallery.client.view.desktop;

import net.dancioi.jcsphotogallery.client.view.PageController;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;

/**
 * Class for Bottom Panel.
 * 
 * The application contains 3 panels (top, center, bottom).
 * 
 * @author Daniel Cioi <dan@dancioi.net>
 * @version $Revision$ Last modified: $Date$, by: $Author$
 */
public class BottomPanel extends AbsolutePanel {

	private PageController galleryControll;
	private Label pageNr; // page number label.
	private Label leftCornerLabel; // album/tags label.

	private Image buttonIconLeft;
	private Image buttonIconRight;
	private Image buttonIconUp;

	public BottomPanel(PageController galleryControll) {
		this.galleryControll = galleryControll;
		initialize();
	}

	private void initialize() {
		this.setPixelSize(790, 40);
		this.setStyleName("bottomPanel", true);

		buttonIconLeft = new Image("template/ext/previous.gif"); // PREVIOUS button
		buttonIconLeft.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				leftButtonClicked();
			}
		});
		add(buttonIconLeft, 550, 3);

		buttonIconRight = new Image("template/ext/next.gif"); // NEXT button
		buttonIconRight.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				rightButtonClicked();
			}
		});
		add(buttonIconRight, 630, 3);

		buttonIconUp = new Image("template/ext/backToAlbums.gif"); // BACK to albums
		buttonIconUp.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				upButtonClicked();
			}
		});
		add(buttonIconUp, 720, 3);

		pageNr = new Label();
		add(pageNr, 370, 10);

		addAlbumLabel();

		allOff();
	}

	/*
	 * Adds the album label.
	 */
	private void addAlbumLabel() {
		leftCornerLabel = new Label("");
		add(leftCornerLabel, 20, 10);
	}

	/*
	 * PREVIOUS button action.
	 */
	private void leftButtonClicked() {
		galleryControll.previousPageEvent();
	}

	/*
	 * NEXT button action.
	 */
	private void rightButtonClicked() {
		galleryControll.nextPageEvent();
	}

	/*
	 * BACK to Albums button action.
	 */
	private void upButtonClicked() {
		galleryControll.upPagesEvent();
	}

	/**
	 * Sets enable/disable the PREVIOUS button.
	 * 
	 * @param v
	 *            boolean
	 */
	public void setLeftButtonVisible(boolean v) {
		buttonIconLeft.setVisible(v);
	}

	/**
	 * Sets enable/disable the NEXT button.
	 * 
	 * @param v
	 *            boolean
	 */
	public void setRightButtonVisible(boolean v) {
		buttonIconRight.setVisible(v);
	}

	/**
	 * Sets enable/disable the BACK to Albums button.
	 * 
	 * @param v
	 *            boolean
	 */
	public void setUpButtonVisible(boolean v) {
		// GWT.log("up visible " + v);
		buttonIconUp.setVisible(v);
	}

	/**
	 * Sets the page number.
	 * 
	 * @param pn
	 *            page number string
	 */
	public void setPageNr(String pn) {
		pageNr.setText(pn);
	}

	/**
	 * Sets the album name (shown on the bottom left corner)
	 * 
	 * @param album
	 *            album name
	 */
	public void setLeftCornerLabel(String label) {
		String styleName = leftCornerLabel.getStyleName();
		if (!styleName.equals("gwt-Label")) {
			leftCornerLabel.setStyleName("gwt-Label");
		}
		leftCornerLabel.setText(label);
	}

	public void setLeftCornerLabelAlertMode() {
		leftCornerLabel.setStyleName("gwt-Label-alert");
	}

	/**
	 * Disables all buttons on the bottom panel.
	 */
	public void allOff() {
		setLeftButtonVisible(false);
		setRightButtonVisible(false);
		setUpButtonVisible(false);
		setPageNr("");
		setLeftCornerLabel("");
	}

}
