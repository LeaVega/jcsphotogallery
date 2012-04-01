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

package net.dancioi.jcsphotogallery.app.model;

import net.dancioi.jcsphotogallery.client.model.AlbumBean;
import net.dancioi.jcsphotogallery.client.model.Albums;
import net.dancioi.jcsphotogallery.client.model.PictureBean;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Obtains Albums or Album's photos from an XML element. Duplicated like in
 * client because of the different import statements.
 * 
 * @author Daniel Cioi <dan@dancioi.net>
 * @version $Revision: 33 $ Last modified: $Date: 2011-12-03 13:18:31 +0200
 *          (Sat, 03 Dec 2011) $, by: $Author: dan.cioi@gmail.com $
 */
public abstract class ElementXML {

	/**
	 * Gets the albums' list.
	 * 
	 * @param element
	 * @return Albums
	 */
	protected Albums getAlbums(Element element) {
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
			photoAlbums[i] = new AlbumBean(elAlbum.getAttribute("img"), elAlbum.getAttribute("folderName"), elAlbum.getAttribute("name"), tags);
		}

		Albums galleryAlbums = new Albums();
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

			pictures[i] = new PictureBean(elAlbum.getAttribute("name"), elAlbum.getAttribute("img"), elAlbum.getAttribute("comment"), elAlbum.getAttribute("imgt"));
		}

		AlbumBean albumPhotos = new AlbumBean();
		albumPhotos.setPictures(pictures);

		return albumPhotos;
	}

	protected void getAlbumPicturesElements(Document doc, PictureBean[] pictures) {
		Element albumElement = doc.createElement("album");
		Element root = doc.getDocumentElement();

		for (PictureBean picture : pictures) {

			Element photoElement = doc.createElement("i");
			// use short name to reduce the xml file's size

			photoElement.setAttribute("imgt", picture.getImgThumbnail());
			photoElement.setAttribute("img", picture.getFileName());
			photoElement.setAttribute("name", picture.getName());
			photoElement.setAttribute("comment", picture.getDescription());

			albumElement.appendChild(photoElement);

			root.appendChild(photoElement);

		}

	}

	protected void getAlbumsElements(Document doc, AlbumBean[] albums) {
		Element galleryNameElement = doc.createElement("galleryName");
		galleryNameElement.appendChild(doc.createTextNode("gallery name"));

		Element homePageElement = doc.createElement("homePage");
		homePageElement.appendChild(doc.createTextNode("http://www.dancioi.com/projects/jcsphotogallery"));

		Element root = doc.getDocumentElement();

		root.appendChild(galleryNameElement);
		root.appendChild(homePageElement);

		Element albumsElement = doc.createElement("albums");

		for (AlbumBean album : albums) {

			Element albumElement = doc.createElement("album");

			albumElement.setAttribute("img", album.getImgThumbnail());
			albumElement.setAttribute("folderName", album.getFolderName());
			albumElement.setAttribute("name", album.getName());
			albumElement.setAttribute("category", "");// album.getCategory().toString());

			albumsElement.appendChild(albumElement);

		}
		root.appendChild(albumsElement);

	}

}
