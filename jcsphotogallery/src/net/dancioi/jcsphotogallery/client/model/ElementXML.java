/*	
 * 	File    : ElementXML.java
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

import net.dancioi.jcsphotogallery.client.shared.AlbumBean;
import net.dancioi.jcsphotogallery.client.shared.GalleryAlbums;
import net.dancioi.jcsphotogallery.client.shared.PictureBean;

import com.google.gwt.user.client.Window;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;

/**
 * Obtains Albums or Album's photos from an XML element.
 * 
 * @author Daniel Cioi <dan@dancioi.net>
 * @version $Revision$ Last modified: $Date$, by: $Author$
 */
public abstract class ElementXML {

	/**
	 * Gets the albums' list.
	 * 
	 * @param element
	 * @return Albums
	 */
	protected GalleryAlbums getAlbums(Element element) {
		String galleryName = element.getElementsByTagName("galleryName").item(0).getFirstChild().getNodeValue();
		String galleryHomePage = element.getElementsByTagName("homePage").item(0).getFirstChild().getNodeValue();

		String xmlVersion = null;
		NodeList elementVersion = element.getElementsByTagName("version");
		if (elementVersion.getLength() != 0) {
			xmlVersion = elementVersion.item(0).getFirstChild().getNodeValue();
		} else {
			xmlVersion = "1.0.x";
		}

		String[] tags = null;

		NodeList albums = element.getElementsByTagName("album");
		int albumsCount = albums.getLength();
		AlbumBean[] photoAlbums = new AlbumBean[albumsCount];

		for (int i = 0; i < albumsCount; i++) {
			Element elAlbum = (Element) albums.item(i);
			String allCategories = null;
			if (xmlVersion.startsWith("1.1.")) {
				allCategories = elAlbum.getAttribute("tags");
			} else {
				Window.alert("Incompatibility between JcsPhotoGallery Web Application and Gallery xml files. \nPlease use JcsPhotoGallery desktop application to correct this.");
			}
			tags = allCategories.split(";");
			photoAlbums[i] = new AlbumBean(elAlbum.getAttribute("folderName") + "/" + elAlbum.getAttribute("img"), elAlbum.getAttribute("folderName"), elAlbum.getAttribute("name"), tags, i);
		}

		GalleryAlbums galleryAlbums = new GalleryAlbums();
		galleryAlbums.setGalleryName(galleryName);
		galleryAlbums.setGalleryHomePage(galleryHomePage);
		galleryAlbums.setAlbums(photoAlbums);

		return galleryAlbums;
	}

	/**
	 * Gets the album's photos
	 * 
	 * @param element
	 * @return AlbumPhotos
	 */
	protected AlbumBean getAlbumPhotos(Element element) {
		NodeList images = element.getElementsByTagName("i");
		int imgCount = images.getLength();

		PictureBean[] pictures = new PictureBean[imgCount];

		for (int i = 0; i < images.getLength(); i++) {
			Element elAlbum = (Element) images.item(i);

			pictures[i] = new PictureBean(elAlbum.getAttribute("name"), elAlbum.getAttribute("img"), elAlbum.getAttribute("comment"), "T" + elAlbum.getAttribute("img"));
		}

		AlbumBean albumPhotos = new AlbumBean();
		albumPhotos.setPictures(pictures);

		return albumPhotos;
	}

}
