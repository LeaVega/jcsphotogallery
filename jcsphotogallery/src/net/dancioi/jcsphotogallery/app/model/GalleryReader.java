/*	
 * 	File    : GalleryReader.java
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

package net.dancioi.jcsphotogallery.app.model;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import net.dancioi.jcsphotogallery.shared.AlbumBean;
import net.dancioi.jcsphotogallery.shared.GalleryAlbums;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 * Reads the gallery's xml files.
 * 
 * @author Daniel Cioi <dan@dancioi.net>
 * @version $Revision$ Last modified: $Date: 2012-03-20 22:39:16 +0200
 *          (Tue, 20 Mar 2012) $, by: $Author$
 */
public class GalleryReader extends ElementXML {

	public GalleryAlbums getAlbums(File xmlFile) throws GalleryException{
		Element elementFromXMLFile = getElementFromXMLFile(xmlFile);
		GalleryAlbums galleryAlbums = super.getAlbums(elementFromXMLFile);
		for (AlbumBean album : galleryAlbums.getAllAlbums()) {
			album.setPictures(getAlbumPhotos(getElementFromXMLFile(new File(xmlFile.getParentFile().getAbsolutePath() + File.separatorChar + album.getFolderName() + File.separator + "album.xml"))));
		}

		return galleryAlbums;
	}

	private Element getElementFromXMLFile(File xmlFile) throws GalleryException{
		Element element = null;

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
			Document document = builder.parse(xmlFile);
			element = document.getDocumentElement();
		} catch (ParserConfigurationException e) {
			throw new GalleryException(e);
		} catch (SAXException e) {
			throw new GalleryException(e);
		} catch (IOException e) {
			throw new GalleryException(e);
		}

		return element;
	}

}
