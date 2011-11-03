/*	
 * 	File    : ReadXML.java
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

import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;

/**
 * Reads the XML files from the web server.
 *  
 * @author Daniel Cioi <dan@dancioi.net>
 * @version Revision: $Revision$  Last modified: $Date$  Last modified by: $Author$
 */

public class ReadXML extends ReadXMLGeneric{

	private ReadXMLCallback readCallback;
	private String currentImagesPath;
	
	public static int FLAG_ALBUMS = 1;
	public static int FLAG_ALBUMPHOTOS = 2;
	
	public ReadXML(ReadXMLCallback readCallback){
		this.readCallback = readCallback;
	}
	
	/**
	 * Gets the albums' items' list.
	 * Parse the XML string.
	 * @param xmlText String.
	 */
	public void readAlbums(String albumsFile){
			readXmlFile(albumsFile, FLAG_ALBUMS);
	}

	/**
	 * Gets the album's images list.
	 */
	public void readAlbum(String imagesPath){
		currentImagesPath = imagesPath;
		readXmlFile(imagesPath+"/album.xml", FLAG_ALBUMPHOTOS);			
	}

	@Override
	public void albumsCallback(Element element) {
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
		
		readCallback.albumsCallback(galleryAlbums);
	}

	@Override
	public void albumPhotosCallback(Element element) {
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
		albumPhotos.setImagesPath(currentImagesPath);
		
		readCallback.albumPhotosCallback(albumPhotos);
	}

}
