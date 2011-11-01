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
import com.google.gwt.core.client.GWT;
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

public class Jcsphotogallery implements EntryPoint, AlbumsDataAccess, GalleryAction {

	
	private String galleryVersion = "1.1.1";

	private Label headerLabel;
	private TopPanel topPanel;
	private String homeLink = "";
	private SortAlbums sortAlbums;
	private boolean albumsFlag;
	
	protected Albums albums;
	protected CenterPanel centerPanel;
	protected BottomPanel bottomPanel;
	protected ReadXML readXml;

	public void onModuleLoad() {
		initialize();
	}

	/**
	 * Initialize
	 */
	public void initialize(){
		addHeader();
		addCenterPanel();
		addBottomPanel();
		addTopPanel();
		sortAlbums = new SortAlbums(this);
		getAlbums();
	}

	/**
	 * Adds the header where a text 
	 * with the owner gallery will be shown. 
	 */
	private void addHeader(){
		headerLabel = new Label();
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


	/**
	 * Gets the albums parameters.
	 */
	public void getAlbums(){
		readXml = new ReadXML(this);
	}

	/**
	 * Gets the selected album parameters.
	 * @param nr
	 */
	public void getAlbumNr(int nr){
		String albumPath = "gallery/"+albums.getAlbumFolderName(nr)+"/album.xml";
		String imagesPath = "gallery/"+albums.getAlbumFolderName(nr)+"/";
		readXml.getXML(albumPath, imagesPath);
	}

	/**
	 * Returns to the albums list when the albums button is clicked.
	 */
	public void backToAlbums(){
		bottomPanel.allOff();
		showsAlbums(true);
		centerPanel.prepareImg("gallery/", albums.getAllAlbums(), true);

	}

	/**
	 * Sets the owner gallery name 
	 * and the owner's link to the home web page.  
	 * @param name owner's name
	 * @param homePage link to the home web page 
	 */
	@Override
	public void setGalleryName(String name, String homePage){
		setHeader(name);
		topPanel.setHomePage(name, homePage);
		
		initializeAlbums();
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
		if(sortAlbums.setVisibleAlbums(selected))
			backToAlbums();
	}

	
	/**
	 * Initialize the Albums.
	 */
	private void initializeAlbums(){
		albums = new Albums();
	}
	
	/**
	 * Adds the sorted categories after the albums file is read.
	 * @param categories String array with albums categories
	 */
	public void addSortedCategories(String[] categories){
		topPanel.setSortedCat(categories);
	}
	
	/**
	 * Sorts the albums by categories.
	 * @param categories
	 */
	public void sortAlbumsCategories(String[][] categories){
		sortAlbums.sortAllAlbums(categories);
	}
	
	/**
	 * Sets showing albums flag.
	 * @param flag
	 */
	public void showsAlbums(boolean flag){
		albumsFlag = flag;
	}
	
	/**
	 * Gets the showing albums flag.
	 * Flag to know if the next click event (on one of the 9 cells) 
	 * will fire the PopUp panel or will show the album's thumbnails. 
	 * @return
	 */
	public boolean isShowingAlbums(){
		return albumsFlag;
	}
	
	/**
	 * Access the center Panel (that shows the 9 thumbnails).
	 * @return
	 */
	public CenterPanel getCenterPanel(){
		return centerPanel;
	}
	
	/**
	 * Adds all albums to gallery.
	 * @param allAlbums
	 */
	@Override
	public void attachAllAlbums(AlbumBean[] allAlbums){
		albums.setAlbums(allAlbums);
		showsAlbums(true);
		getCenterPanel().prepareImg("gallery/", albums.getAllAlbums(), false);
		
		//sortAlbumsCategories(albums.getAlbumsCategories());	
	}
	
	/**
	 * Adds the pictures from an album.
	 * @param imagesPath
	 * @param thumbnails
	 */
	@Override
	public void attachAlbumPhotos(String imagesPath, PictureBean[] pictures){
		getCenterPanel().prepareImg(imagesPath, pictures);
	}

	@Override
	public void previousPageEvent() {
		centerPanel.previousPage();	
	}

	@Override
	public void nextPageEvent() {
		centerPanel.nextPage();
	}

	@Override
	public void upToAlbumsEvent() {
		backToAlbums();	
	}

	@Override
	public void readsAlbumPhotos(boolean photosFlag) {
		if(photosFlag) showsAlbums(false);		
	}

	public String getGalleryVersion() {
		return galleryVersion;
	}
	
	
	
}
