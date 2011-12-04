/*	
 * 	File    : Albums.java
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

package net.dancioi.jcsphotogallery.client.model;

import java.util.ArrayList;

/**
 * The class to keep the albums data.
 *  
 * @author Daniel Cioi <dan@dancioi.net>
 * @version $Revision$  Last modified: $Date$, by: $Author$
 */
public class Albums {
	
	private String galleryName;
	private String galleryHomePage;
	private AlbumBean[] albums;	
	
	private ArrayList<String> categoryString = new ArrayList<String>();
	private ArrayList<AlbumsCategory> categories = new ArrayList<AlbumsCategory>();
	
	public Albums(){
		
	}

	/**
	 * add all galery's albums.
	 * @param albums
	 */
	public void setAlbums(AlbumBean[] albums) {
		this.albums = albums;
	}
	
	/**
	 * Method to get the number of all albums in gallery.
	 * @return number of all albums.
	 */
	public int getAllAlbumsNr(){
		return albums.length;
	}
	
	/**
	 * Gets all gallery's albums
	 * @return
	 */
	public AlbumBean[] getAllAlbums(){
		return albums;
	}
	
	/**
	 * Get just the albums according with the selected sorting criteria. 
	 * @return
	 */
	public AlbumBean[] getSortedAlbums(){
		return albums;
	}
	
	public String getAlbumFolderName(int a){
		return albums[a].getFolderName();
	}
	
	public String getAlbumName(int a){
		return albums[a].getName();
	}
	
	public String getGalleryName() {
		return galleryName;
	}

	public void setGalleryName(String galleryName) {
		this.galleryName = galleryName;
	}
	
	public String getGalleryHomePage() {
		return galleryHomePage;
	}

	public void setGalleryHomePage(String galleryHomePage) {
		this.galleryHomePage = galleryHomePage;
	}

	/*
	 * gets the album's index for each category string.
	 */
	private void getAllCategories(){
		for(int i=0;i<albums.length;i++){
			String[] albumCategories = albums[i].getCategory();
			for(int albumIndex=0;albumIndex<albumCategories.length;albumIndex++){
				if(categoryString.contains(albumCategories[albumIndex])){
					int index = categoryString.indexOf(albumCategories[albumIndex]); 
					categories.get(index).addAlbumToCategory(albumIndex);
				}
				else{
					categories.add(new AlbumsCategory(albumCategories[albumIndex], categories.size(), albumIndex));
				}
			}
		}
	}
	
	
	/**
	 * Method to get the albums categories.
	 * format: [cat 0...nrMaxCategories][album 0...length]
	 * @return
	 */
	public String[][] getAlbumsCategories(){
		String[][] catResult = null;
		int catLength = 0;
		if(albums.length>0)catLength = albums[0].getCategory().length;
		catResult = new String[catLength][];
		String[] cat = null;
		for(int a=0; a<albums.length; a++){
			cat = albums[a].getCategory();
			for(int c=0;c<cat.length;c++)
				catResult[c][a] = cat[c];
		}
		return catResult;
	}
	
}
