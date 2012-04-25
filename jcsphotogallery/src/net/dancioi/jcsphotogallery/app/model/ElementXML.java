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

import net.dancioi.jcsphotogallery.client.shared.AlbumBean;
import net.dancioi.jcsphotogallery.client.shared.GalleryAlbums;
import net.dancioi.jcsphotogallery.client.shared.PictureBean;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Obtains Albums or Album's photos from an XML element. Duplicated like in client because of the different import statements.
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

		String[] tags = null;

		NodeList albums = element.getElementsByTagName("album");
		int albumsCount = albums.getLength();
		AlbumBean[] photoAlbums = new AlbumBean[albumsCount];

		for (int i = 0; i < albumsCount; i++) {
			Element elAlbum = (Element) albums.item(i);

			String allCategories = elAlbum.getAttribute("category");
			tags = allCategories.split(",");
			photoAlbums[i] = new AlbumBean(elAlbum.getAttribute("img"), elAlbum.getAttribute("folderName"), elAlbum.getAttribute("name"), tags, i);
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
	protected PictureBean[] getAlbumPhotos(Element element) {
		NodeList images = element.getElementsByTagName("i");
		int imgCount = images.getLength();

		PictureBean[] pictures = new PictureBean[imgCount];

		for (int i = 0; i < images.getLength(); i++) {
			Element elAlbum = (Element) images.item(i);

			pictures[i] = new PictureBean(elAlbum.getAttribute("name"), elAlbum.getAttribute("img"), elAlbum.getAttribute("comment"), elAlbum.getAttribute("imgt"));
		}

		return pictures;
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

	protected void getAlbumsElements(Document doc, GalleryAlbums gallery) {
		Element galleryNameElement = doc.createElement("galleryName");
		galleryNameElement.appendChild(doc.createTextNode(gallery.getGalleryName()));

		Element homePageElement = doc.createElement("homePage");
		homePageElement.appendChild(doc.createTextNode(gallery.getGalleryHomePage()));

		Element root = doc.getDocumentElement();

		root.appendChild(galleryNameElement);
		root.appendChild(homePageElement);

		Element albumsElement = doc.createElement("albums");

		AlbumBean[] albums = gallery.getAllAlbums();
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
