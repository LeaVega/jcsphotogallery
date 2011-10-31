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

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;
import com.google.gwt.xml.client.impl.DOMParseException;

/**
 * Reads the XML files from the web server.
 *  
 * @author Daniel Cioi <dan@dancioi.net>
 * @version Revision: $Revision$  Last modified: $Date$  Last modified by: $Author$
 */

public class ReadXML extends ReadXMLGeneric{

	private AlbumsDataAccess albumsDataAccess;
	private boolean albumsFlag = true;				// flag to read the albums xml file just once.
	private String curentImgPath;
	private String currentXMLFile;

	public ReadXML(AlbumsDataAccess albumsDataAccess){
		this.albumsDataAccess = albumsDataAccess;
		getXML("gallery/albums.xml", "gallery/"); 	// read the xml file where the albums parameters are.
	}

	/**
	 * Gets the XML file from http server.
	 */
	@Override
	public void getXML(String file, String imgPath){
		curentImgPath = imgPath;
		setCurrentXMLFile(file);
		RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.GET,
				file);
		try {
			requestBuilder.sendRequest(null, new RequestCallback() {
				public void onError(Request request, Throwable exception) {
					new ReadException("Error sending request");
				}
				public void onResponseReceived(Request request, Response response) {
					if (200 == response.getStatusCode()) {
						if(albumsFlag)readAlbums(response.getText());
						else readAlbum(response.getText());	
					} 
					else if(404 == response.getStatusCode()){
						new ReadException("File "+getCurrentXMLFile()+" not found on server. Wrong name or missing.");
					}
					else{
						new ReadException("Other exception on GET the "+getCurrentXMLFile() +" file");
					}
				}
			});
		} catch (RequestException ex) {
			new ReadException("Error sending request");
		}
	}

	/**
	 * Sets the current XML file.  
	 * @param currentFile
	 */
	private void setCurrentXMLFile(String currentFile){
		currentXMLFile = currentFile;
	}

	/**
	 * Gets the current XML file.
	 * @return String
	 */
	private String getCurrentXMLFile(){
		return currentXMLFile;
	}

	/**
	 * Gets the albums' items' list.
	 * Parse the XML string.
	 * @param xmlText String.
	 */
	@Override
	public void readAlbums(String xmlText){
		Document document = null;
		try{
			document = XMLParser.parse(xmlText);

			Element element = document.getDocumentElement();
			XMLParser.removeWhitespace(element);

			String galleryName = element.getElementsByTagName("galleryName").item(0).getFirstChild().getNodeValue();
			String nameHomePage = element.getElementsByTagName("homePage").item(0).getFirstChild().getNodeValue();
			albumsDataAccess.setGalleryName(galleryName, nameHomePage);

			albumsFlag = false;

			String[] categories = null;

			NodeList albums = element.getElementsByTagName("album");
			int albumsCount = albums.getLength();
			AlbumBean[] photoAlbums = new AlbumBean[albumsCount];

			for (int i = 0; i < albumsCount; i++) {
				Element elAlbum = (Element) albums.item(i);

				String allCategories = elAlbum.getAttribute("category");
				categories = allCategories.split(",");
				photoAlbums[i] = new AlbumBean(elAlbum.getAttribute("img"), elAlbum.getAttribute("folderName"), 
						elAlbum.getAttribute("name"), categories);
			}

			albumsDataAccess.attachAllAlbums(photoAlbums);
		}
		catch(DOMParseException de){
			new ReadException("File "+getCurrentXMLFile()+" parse exception. Use a XML editor to avoid syntax errors in xml file.");
		}
	}

	/**
	 * Gets the album's images list.
	 */
	@Override
	public void readAlbum(String xmlText){
		Document document = null;
		try{
			document = XMLParser.parse(xmlText);

			albumsDataAccess.readsAlbumPhotos(true);


			Element element = document.getDocumentElement();
			XMLParser.removeWhitespace(element);

			NodeList images = element.getElementsByTagName("i");
			int imgCount = images.getLength();

			PictureBean[] pictures = new PictureBean[imgCount]; 

			for (int i = 0; i < images.getLength(); i++) {
				Element elAlbum = (Element) images.item(i);

				pictures[i] = new PictureBean(elAlbum.getAttribute("name"), elAlbum.getAttribute("img"),
						elAlbum.getAttribute("comment"), elAlbum.getAttribute("imgt"));
			}

			albumsDataAccess.attachAlbumPhotos(curentImgPath, pictures);
		}
		catch(DOMParseException de){
			new ReadException("File "+getCurrentXMLFile()+" parse exception. Use a XML editor to avoid syntax errors in xml file.");
		}
	}

}
