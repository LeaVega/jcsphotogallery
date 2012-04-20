/*	
 * 	File    : GalleryAlbums.java
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

package net.dancioi.jcsphotogallery.client.shared;

import java.util.ArrayList;

/**
 * The class to keep the albums data.
 * 
 * @author Daniel Cioi <dan@dancioi.net>
 * @version $Revision$ Last modified: $Date$, by: $Author$
 */
public class GalleryAlbums {

	private String galleryName;
	private String galleryHomePage;
	private AlbumBean[] albums;

	private ArrayList<String> categoryString = new ArrayList<String>();
	private ArrayList<AlbumsCategory> categories = new ArrayList<AlbumsCategory>();

	private boolean edited;

	public GalleryAlbums() {

	}

	/**
	 * Adds all galery's albums.
	 * 
	 * @param albums
	 */
	public void setAlbums(AlbumBean[] albums) {
		this.albums = albums;
	}

	/**
	 * Gets the number of all albums in gallery.
	 * 
	 * @return number of all albums.
	 */
	public int getTotalAlbumsNumber() {
		return albums.length;
	}

	/**
	 * Gets the number of pictures.
	 * 
	 * @return
	 */
	public int getTotalPicturesNumber() {
		int result = 0;
		for (AlbumBean album : albums)
			result += album.getPictures().length;
		return result;
	}

	/**
	 * Gets all gallery's albums
	 * 
	 * @return
	 */
	public AlbumBean[] getAllAlbums() {
		return albums;
	}

	/**
	 * Gets just the albums according with the selected sorting criteria.
	 * 
	 * @return
	 */
	public AlbumBean[] getSortedAlbums() {
		return albums;
	}

	public String getAlbumFolderName(int a) {
		return albums[a].getFolderName();
	}

	public String getAlbumName(int a) {
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

	public boolean isEdited() {
		return edited;
	}

	public void setEdited(boolean edited) {
		this.edited = edited;
	}

	/*
	 * gets the album's index for each category string.
	 */
	private void getAllCategories() {
		for (int i = 0; i < albums.length; i++) {
			String[] albumCategories = albums[i].getCategory();
			for (int albumIndex = 0; albumIndex < albumCategories.length; albumIndex++) {
				if (categoryString.contains(albumCategories[albumIndex])) {
					int index = categoryString.indexOf(albumCategories[albumIndex]);
					categories.get(index).addAlbumToCategory(albumIndex);
				} else {
					categories.add(new AlbumsCategory(albumCategories[albumIndex], categories.size(), albumIndex));
				}
			}
		}
	}

	/**
	 * Gets the albums categories. format: [cat 0...nrMaxCategories][album 0...length]
	 * 
	 * @return
	 */
	public String[][] getAlbumsCategories() {
		String[][] catResult = null;
		int catLength = 0;
		if (albums.length > 0)
			catLength = albums[0].getCategory().length;
		catResult = new String[catLength][];
		String[] cat = null;
		for (int a = 0; a < albums.length; a++) {
			cat = albums[a].getCategory();
			for (int c = 0; c < cat.length; c++)
				catResult[c][a] = cat[c];
		}
		return catResult;
	}

}
