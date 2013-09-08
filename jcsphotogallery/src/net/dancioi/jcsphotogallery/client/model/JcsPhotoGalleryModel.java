/*	
 * 	File    : JcsPhotoGalleryModel.java
 * 
 * 	Copyright (C) 2012 Daniel Cioi <dan@dancioi.net>
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

import net.dancioi.jcsphotogallery.shared.AlbumBean;
import net.dancioi.jcsphotogallery.shared.GalleryAlbums;

/**
 * JcsPhotoGallery's model.
 * 
 * @author Daniel Cioi <dan@dancioi.net>
 * @version $Revision$ Last modified: $Date$, by: $Author$
 */
public class JcsPhotoGalleryModel extends Model implements ReadXMLCallback {
	private ReadXML readXml;

	public JcsPhotoGalleryModel() {
		readXml = new ReadXML(this);
	}

	@Override
	public void albumsCallback(GalleryAlbums galleryAlbums) {
		getPresenter().responseGalleryAlbums(galleryAlbums);
	}

	@Override
	public void albumPhotosCallback(AlbumBean album) {
		getPresenter().responseSelectedAlbum(album);
	}

	@Override
	public void readAlbum(String imagesPath) {
		readXml.readAlbum(imagesPath);
	}

	@Override
	public void readGalleryAlbums(String albumsFilePath) {
		readXml.readAlbums(albumsFilePath);
	}
}
