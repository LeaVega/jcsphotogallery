/*	
 * 	File    : AlbumBean.java
 * 
 * 	Copyright (C) 2011 Daniel Cioi <dan@dancioi.net>
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

/**
 * Album bean.
 * 
 * @author Daniel Cioi <dan@dancioi.net>
 * @version $Revision$ Last modified: $Date$, by: $Author$
 */
public class AlbumBean implements Thumbnails {

	private String imgThumbnail;
	private String folderName;
	private String name;
	private String[] category;
	private PictureBean[] pictures;
	private String albumPath;
	private boolean edited;

	/**
	 * Default constructor.
	 */
	public AlbumBean() {
	}

	/**
	 * Constructor.
	 * 
	 * @param img
	 * @param folderName
	 * @param name
	 * @param thumbnail
	 * @param category
	 */
	public AlbumBean(String imgThumbnail, String folderName, String name, String[] category) {
		super();
		this.imgThumbnail = imgThumbnail;
		this.folderName = folderName;
		this.name = name;
		this.category = category;
	}

	@Override
	public String getImgThumbnail() {
		return imgThumbnail;
	}

	public void setImgThumbnail(String img) {
		this.imgThumbnail = img;
	}

	public String getFolderName() {
		return folderName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String[] getCategory() {
		return category;
	}

	public void setCategory(String[] category) {
		this.category = category;
	}

	public PictureBean[] getPictures() {
		return pictures;
	}

	public void setPictures(PictureBean[] pictures) {
		this.pictures = pictures;
	}

	@Override
	public String toString() {
		return name;
	}

	public String getAlbumPath() {
		return albumPath;
	}

	public void setAlbumPath(String albumPath) {
		this.albumPath = albumPath;
	}

	public boolean isEdited() {
		return edited;
	}

	public void setEdited(boolean edited) {
		this.edited = edited;
	}

}
