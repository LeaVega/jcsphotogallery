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

package net.dancioi.jcsphotogallery.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;

/**
 * This class create the Bottom Panel. 
 * 
 * The application contains 3 panels (top, center, bottom).
 *  
 * @author Daniel Cioi <dan@dancioi.net>
 * @version Revision: $Revision$  Last modified: $Date$  Last modified by: $Author$
 */
public class BottomPanel extends AbsolutePanel{

	private GalleryAction galleryControll;
	private String jcsPhotoGalleryLinkString;
	private String galleryVersion;
	private Label pageNr;		// page number label.
	private Label albumLabel;	// album label.

	private Image buttonIconLeft;
	private Image buttonIconRight;
	private Image buttonIconUp;
	
	String adminLink = "<div> <a href=\"JcsphotogalleryAdmin.html\"><font size=\"1\">Admin</font></a> </div>";
	
	
	public BottomPanel(String galleryVersion, GalleryAction galleryControll){
		this.galleryVersion = galleryVersion;
		this.galleryControll = galleryControll;
		addVersionNr();
		initialize();
	}

	/*
	 * Shows the project name and version number.
	 */
	private void addVersionNr(){
		jcsPhotoGalleryLinkString = 
			"<div> <a href=\"http://www.dancioi.net/projects/jcsphotogallery/\"><font size=\"1\">jcsPhotoGallery "
			+galleryVersion+"</font></a> </div>";
	}

	/*
	 * Initialize.
	 */
	private void initialize(){
		setSize("800px", "70px");
		buttonIconLeft = new Image("ext/previous.gif");			// PREVIOUS button
		buttonIconLeft.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				leftButtonClicked();}} );
		add(buttonIconLeft, 610, 5);
		
		buttonIconRight = new Image("ext/next.gif");			// NEXT button
		buttonIconRight.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				rightButtonClicked();}} );
		add(buttonIconRight, 660, 5);
		
		buttonIconUp = new Image("ext/albums.gif");				// BACK to albums
		buttonIconUp.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				upButtonClicked();}} );
		add(buttonIconUp, 767, 5);

		pageNr = new Label();
		add(pageNr, 370, 10);

		addAlbumLabel();

		HTML jcsPhotoGalleryLink = new HTML(jcsPhotoGalleryLinkString);
		add(jcsPhotoGalleryLink, 350, 40);

		//HTML adminHtmlLink = new HTML(adminLink);
		//add(adminHtmlLink, 375, 60);
		
		allOff();
	}
	
	/**
	 * Adds the album label.
	 */
	protected void addAlbumLabel(){
		albumLabel = new Label("");
		albumLabel.setStyleName("bottomAlbumLabel");
		add(albumLabel, 1,10);
	}
	
	
	/*
	 * PREVIOUS button action.
	 */
	private void leftButtonClicked(){
		galleryControll.previousPageEvent();
	}

	/*
	 * NEXT button action.
	 */
	private void rightButtonClicked(){
		galleryControll.nextPageEvent();
	}

	/*
	 * BACK to Albums button action.
	 */
	private void upButtonClicked(){
		galleryControll.upToAlbumsEvent();
	}

	/**
	 * Sets enable/disable the PREVIOUS button.
	 * @param v boolean
	 */
	public void setLeftButtonVisible(boolean v){
		buttonIconLeft.setVisible(v);
	}

	/**
	 * Sets enable/disable the NEXT button.
	 * @param v boolean
	 */
	public void setRightButtonVisible(boolean v){
		buttonIconRight.setVisible(v);
	}

	/**
	 * Sets enable/disable the BACK to Albums button.
	 * @param v boolean
	 */
	public void setUpButtonVisible(boolean v){
		GWT.log("up visible "+v);
		buttonIconUp.setVisible(v);
	}

	/**
	 * Sets the page number.
	 * @param pn page number string
	 */
	public void setPageNr(String pn){
		pageNr.setText(pn);
	}

	/**
	 * Sets the album name 
	 * (shown on the bottom left corner)
	 * @param album album name
	 */
	public void setAlbumLabel(String album){
		albumLabel.setText(album);
	}

	/**
	 * Disables all buttons on the bottom panel.
	 */
	public void allOff(){
		setLeftButtonVisible(false);
		setRightButtonVisible(false);
		setUpButtonVisible(false);
		setPageNr("");
		setAlbumLabel("");
	}

}
