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
package net.dancioi.jcsphotogallery.client;

import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;

/**
 * Obtains Albums or Album's photos from an XML element.
 *  
 * @author Daniel Cioi <dan@dancioi.net>
 * @version Revision: $Revision: 29 $  Last modified: $Date: 2011-11-03 21:46:57 +0200 (Thu, 03 Nov 2011) $  Last modified by: $Author: dan.cioi@gmail.com $
 */
public abstract class ElementXML {

	/**
	 * Gets the albums' list.
	 * @param element
	 * @return Albums
	 */
	public Albums getAlbums(Element element) {
		String galleryName = element.getElementsByTagName("galleryName").item(0).getFirstChild().getNodeValue();
		String galleryHomePage = element.getElementsByTagName("homePage").item(0).getFirstChild().getNodeValue();

		String[] tags = null;

		NodeList albums = element.getElementsByTagName("album");
		int albumsCount = albums.getLength();
		AlbumBean[] photoAlbums = new AlbumBean[albumsCount];

		for (int i = 0; i < albumsCount; i++) {
			Element elAlbum = (Element) albums.item(i);

			String allCategories = elAlbum.getAttribute("category");
			tags = allCategories.split(",");
			photoAlbums[i] = new AlbumBean(elAlbum.getAttribute("img"), elAlbum.getAttribute("folderName"), 
					elAlbum.getAttribute("name"), tags);
		}
		
		Albums galleryAlbums = new Albums();
		galleryAlbums.setGalleryName(galleryName);
		galleryAlbums.setGalleryHomePage(galleryHomePage);
		galleryAlbums.setAlbums(photoAlbums);
		
		return galleryAlbums;
	}
	
	/**
	 * Gets the album's photos
	 * @param element
	 * @return AlbumPhotos
	 */
	public AlbumPhotos getAlbumPhotos(Element element) {
		NodeList images = element.getElementsByTagName("i");
		int imgCount = images.getLength();

		PictureBean[] pictures = new PictureBean[imgCount]; 

		for (int i = 0; i < images.getLength(); i++) {
			Element elAlbum = (Element) images.item(i);

			pictures[i] = new PictureBean(elAlbum.getAttribute("name"), elAlbum.getAttribute("img"),
					elAlbum.getAttribute("comment"), elAlbum.getAttribute("imgt"));
		}

		AlbumPhotos albumPhotos = new AlbumPhotos();
		albumPhotos.setPictures(pictures);
		
		return albumPhotos;
	}
	
}
