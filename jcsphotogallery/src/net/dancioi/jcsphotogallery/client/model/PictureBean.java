/*	
 * 	File    : PictureBean.java
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

package net.dancioi.jcsphotogallery.client.model;

/**
 * This class .
 * 
 * @author Daniel Cioi <dan@dancioi.net>
 * @version $Revision$ Last modified: $Date: 2011-12-04 23:04:24 +0200
 *          (Sun, 04 Dec 2011) $, by: $Author$
 */

public class PictureBean implements Thumbnails {

	private String name;

	private String fileName;

	private String description;

	private String imgThumbnail;

	private AlbumBean parent;

	public PictureBean(String name, String fileName, String description,
			String imgThumbnail) {
		super();
		this.name = name;
		this.fileName = fileName;
		this.description = description;
		this.imgThumbnail = imgThumbnail;
	}

	@Override
	public String getImgThumbnail() {
		return imgThumbnail;
	}

	public void setImgThumbnail(String imgThumbnail) {
		this.imgThumbnail = imgThumbnail;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return name;
	}

	public void setParent(AlbumBean album) {
		this.parent = album;
	}

	public AlbumBean getParent() {
		return parent;
	}

}