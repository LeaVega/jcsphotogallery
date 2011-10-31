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

package net.dancioi.jcsphotogallery.client;

import java.util.ArrayList;

/**
 * The class to keep the albums data.
 *  
 * @author Daniel Cioi <dan@dancioi.net>
 * @version Revision: $Revision$  Last modified: $Date$  Last modified by: $Author$
 */
public class Albums {
	
	private AlbumBean[] albums;
	private int nrAlbumsVisible;	
	
	private ArrayList<String> categoryString = new ArrayList<String>();
	private ArrayList<AlbumsCategory> categories = new ArrayList<AlbumsCategory>();
	
	public Albums(){
		
	}
	

	public AlbumBean[] getAlbums() {
		return albums;
	}


	public void setAlbums(AlbumBean[] albums) {
		this.albums = albums;
	}


	/**
	 * Show sorted albums.
	 * @param visible flag with which album will be shown.  
	 */
	public void showSorted(boolean[] visible){
		int count = 0;
		for(int i=0;i<visible.length;i++)
			if(visible[i]) count++;

		nrAlbumsVisible = count;

		for(int i=0;i<visible.length;i++){
			if(visible[i]){				
//				albums[i].setVisible(true);
			}
		}
	}

	/**
	 * Show all albums.
	 */
	public void showAll(){
		setVisibleAllAlbums();
	}

	protected void setVisibleAllAlbums(){
	//	for(int i=0;i<albums.length;i++)
	//		albums[i].setVisible(true);
	}
	

	public int getNrAlbums(){
		return nrAlbumsVisible;
	}
	
	/**
	 * Method to get the number of all albums in gallery.
	 * @return number of all albums.
	 */
	public int getAllAlbumsNr(){
		return albums.length;
	}
	
	
	public AlbumBean[] getVisibleAlbums(){
		return albums;
	}
	
	public String getAlbumFolderName(int a){
		return albums[a].getFolderName();
	}
	
	public String getAlbumName(int a){
		return albums[a].getName();
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
