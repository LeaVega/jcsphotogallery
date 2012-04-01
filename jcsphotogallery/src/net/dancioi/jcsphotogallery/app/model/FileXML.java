/*	
 * 	File    : FileXML.java
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

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import net.dancioi.jcsphotogallery.client.model.AlbumBean;
import net.dancioi.jcsphotogallery.client.model.Albums;
import net.dancioi.jcsphotogallery.client.model.PictureBean;

import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 * Read/Write XML files.
 * 
 * @author Daniel Cioi <dan@dancioi.net>
 * @version $Revision: 32 $ Last modified: $Date: 2011-12-03 13:07:01 +0200
 *          (Sat, 03 Dec 2011) $, by: $Author: dan.cioi@gmail.com $
 */

public class FileXML extends ElementXML {

	public Albums getAlbums(File xmlFile) {
		return super.getAlbums(readFileXML(xmlFile));
	}

	public AlbumBean getAlbumPhotos(File xmlFile) {
		return super.getAlbumPhotos(readFileXML(xmlFile));
	}

	private Element readFileXML(File xmlFile) {
		Element element = null;

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
			Document document = builder.parse(xmlFile);
			element = document.getDocumentElement();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return element;
	}

	public void saveAlbum(String albumFolder, PictureBean[] pictures) {
		Document doc;
		try {
			doc = getDocument();
			getAlbumPicturesElements(doc, pictures);
			writeFileXML(albumFolder + File.separatorChar + "album.xml", doc);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}

	public void saveGallery(String galleryPayh, AlbumBean[] albums) {
		Document doc;
		try {
			doc = getDocument();
			getAlbumsElements(doc, albums);
			writeFileXML(galleryPayh + "albums.xml", doc);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}

	private Document getDocument() throws ParserConfigurationException {
		DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document doc = docBuilder.newDocument();

		Element root = doc.createElement("root");
		doc.appendChild(root);

		Comment comment = doc.createComment("jcsPhotoGallery");
		root.appendChild(comment);

		return doc;
	}

	private void writeFileXML(String filePath, Document doc) {

		try {
			TransformerFactory transFactory = TransformerFactory.newInstance();
			Transformer aTransformer = transFactory.newTransformer();

			Source src = new DOMSource(doc);
			Result dest = new StreamResult(filePath);
			aTransformer.transform(src, dest);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
