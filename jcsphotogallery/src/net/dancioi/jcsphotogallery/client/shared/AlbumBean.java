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
	private String[] tags;
	private PictureBean[] pictures;
	private String albumPath;
	private boolean edited;
	private int index;
	private GalleryAlbums parent;

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
	 * @param tags
	 */
	public AlbumBean(String imgThumbnail, String folderName, String name, String[] tags, int index) {
		super();
		this.imgThumbnail = imgThumbnail;
		this.folderName = folderName;
		this.name = name;
		this.tags = tags;
		this.index = index;
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

	public String[] getTags() {
		return tags;
	}

	public void setTags(String[] tags) {
		this.tags = tags;
	}

	public String getTagsInOneLine() {
		StringBuilder lineTags = new StringBuilder();
		for (String tag : tags) {
			lineTags.append(tag);
			lineTags.append(Constants.ALBUM_SEPARATOR);
		}
		return lineTags.toString();
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

	@Override
	public int getIndex() {
		return index;
	}

	public GalleryAlbums getParent() {
		return parent;
	}

	public void setParent(GalleryAlbums parent) {
		this.parent = parent;
	}

}
