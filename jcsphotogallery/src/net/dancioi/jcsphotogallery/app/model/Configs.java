/*	
 * 	File    : Configs.java
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

package net.dancioi.jcsphotogallery.app.model;

import java.awt.Dimension;
import java.io.File;
import java.io.Serializable;

/**
 * Application's persistent settings.
 * 
 * @author Daniel Cioi <dan@dancioi.net>
 * @version $Revision$ Last modified: $Date$, by: $Author$
 */
public class Configs implements Serializable {

	private static final long serialVersionUID = 3L;
	private transient DeleteConfirmation deleteConfirmation;
	private File galleryPath;
	private Dimension pictureDimension;
	private int removePictures = -1;

	public Configs(Dimension pictureDimension, int removePictures) {
		this.pictureDimension = pictureDimension;
		this.removePictures = removePictures;
	}

	public void setDeleteConfirmation(DeleteConfirmation deleteConfirmation) {
		this.deleteConfirmation = deleteConfirmation;
	}

	public File getGalleryPath() {
		return galleryPath;
	}

	public void setGalleryPath(File galleryPath) {
		this.galleryPath = galleryPath;
	}

	public Dimension getPictureDimension() {
		return pictureDimension;
	}

	public void setPictureDimension(Dimension pictureDimension) {
		this.pictureDimension = pictureDimension;
	}

	public boolean isRemovePictures(boolean preferences) {
		if (removePictures == 1)
			return true;
		else if (removePictures == 0)
			return false;
		else {
			if(preferences)return false;
			boolean confirmDeleteFiles = deleteConfirmation.confirmDeleteFiles();
			removePictures = confirmDeleteFiles ? 1 : 0;
			return confirmDeleteFiles;
		}
	}

	public void setRemovePictures(boolean removePictures) {
		this.removePictures = 1;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}