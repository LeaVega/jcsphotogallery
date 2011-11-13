/*	
 * 	File    : Jcsphotogallery.java
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

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;


/**
 * 		JcsPhotoGallery
 * 
 * The Main class of this project :p
 * 
 * For details about this project see the following web pages:
 * http://www.dancioi.net/projects/jcsphotogallery/
 * http://code.google.com/p/jcsphotogallery/
 * 
 * For a demo of this project see the following web page:
 * http://www.dancioi.net/projects/jcsphotogallery/demo/
 *  
 * @author Daniel Cioi <dan@dancioi.net>
 * @version Revision: $Revision$  Last modified: $Date$  Last modified by: $Author$
 */

public class Jcsphotogallery implements EntryPoint, GalleryAction {

	//TODO define background color in albums.xml
	private String galleryVersion = "1.1.1";

	private Label headerLabel;
	private TopPanel topPanel;
	private String homeLink = "";

	protected CenterPanel centerPanel;
	protected BottomPanel bottomPanel;

	public void onModuleLoad() {
		initialize();
	}
	
	/**
	 * Initialize
	 */
	public void initialize(){
		addHeader();
		addTopPanel();
		addBottomPanel();
		addCenterPanel();
		addGalleryAlbums();
	}

	/**
	 * Adds the header where a text 
	 * with the owner gallery will be shown. 
	 */
	private void addHeader(){
		headerLabel = new Label("jcsPhotoGallery");
		headerLabel.setStyleName("h1");
		RootPanel.get("header").add(headerLabel);
	}

	/**
	 * Adds the center panel.
	 */
	protected void addCenterPanel(){
		centerPanel = new CenterPanel(this);
		RootPanel.get("images").add(centerPanel); 
	}

	/**
	 * Adds the top panel.
	 */
	private void addTopPanel(){
		topPanel = new TopPanel(this,homeLink);
		RootPanel.get("topPanel").add(topPanel);
	}


	/**
	 * Adds the bottom panel.
	 */
	protected void addBottomPanel(){
		bottomPanel = new BottomPanel(galleryVersion, this);
		RootPanel.get("bottomPanel").add(bottomPanel);
	}
	
	private void addGalleryAlbums(){
		centerPanel.populateGallery();
	}

	/**
	 * Sets the owner gallery name 
	 * and the owner's link to the home web page.  
	 * @param name owner's name
	 * @param homePage link to the home web page 
	 */
	public void setGalleryName(String name, String homePage){
		setHeader(name);
		topPanel.setHomePage(name, homePage);
	}

	/**
	 * Sets the header owner's gallery.
	 * @param name owners' name
	 */
	public void setHeader(String name){
		headerLabel.setText(name+"'s gallery");
	}

	/**
	 * Shows the the albums sorted by one of the categories. 
	 * @param selected selected id from sorting ListBox on the top panel.
	 */
	public void showSelectedAlbums(int selected){
//		if(sortAlbums.setVisibleAlbums(selected))
//			backToAlbums();
	}

	
	/**
	 * Adds the sorted categories after the albums file is read.
	 * @param categories String array with albums categories
	 */
	public void addSortedCategories(String[] categories){
		topPanel.setSortedCat(categories);
	}
	
	
	/**
	 * Access the center Panel (that shows the 9 thumbnails).
	 * @return
	 */
	public CenterPanel getCenterPanel(){
		return centerPanel;
	}


	@Override
	public void previousPageEvent() {
		centerPanel.previousPageEvent();
	}

	@Override
	public void nextPageEvent() {
		centerPanel.nextPageEvent();
	}

	@Override
	public void upToAlbumsEvent() {
		bottomPanel.allOff();
		centerPanel.upToAlbumsEvent();
	}


	public String getGalleryVersion() {
		return galleryVersion;
	}
	
	
	
}
