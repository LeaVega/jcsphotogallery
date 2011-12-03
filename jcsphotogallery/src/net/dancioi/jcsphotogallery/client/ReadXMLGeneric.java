/*	
 * 	File    : AlbumsDataAccess.java
 * 
 * 	Copyright (C) 2011-2011 Daniel Cioi <dan@dancioi.net>
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
import com.google.gwt.xml.client.XMLParser;
import com.google.gwt.xml.client.impl.DOMParseException;

/**
 * Generic class for Read XML files.
 * 
 * @author Daniel Cioi <dan@dancioi.net>
 * @version Revision: $Revision$  Last modified: $Date$  Last modified by: $Author$
 */
public abstract class ReadXMLGeneric extends ElementXML{

	/**
	 * Gets the XML file from http server.
	 */
	public void readXmlFile(final String file, final int flag){
		RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.GET, file);
		try {
			requestBuilder.sendRequest(null, new RequestCallback() {
				public void onError(Request request, Throwable exception) {
					showException("Error sending request");
				}
				public void onResponseReceived(Request request, Response response) {
					if (200 == response.getStatusCode()) {
						parseXMLString(response.getText(), flag);	// careful here, this is an asynchronous callback.
					} 
					else if(404 == response.getStatusCode()){
						showException("File "+file+" not found on server. Wrong name or missing.");
					}
					else{
						showException("Other exception on GET the "+file +" file");
					}
				}
			});
		} catch (RequestException ex) {
			new ReadException("Error sending request");
		}
	}
	
	/*
	 * Parse the xml file.
	 * @param file
	 * @return element
	 */
	private void parseXMLString(String xmlText, int flag){
		Document document = null;
		try{
			document = XMLParser.parse(xmlText);

			Element element = document.getDocumentElement();
			XMLParser.removeWhitespace(element);
			
			if(flag == ReadXML.FLAG_ALBUMS) albumsCallback(element);
			else if (flag == ReadXML.FLAG_ALBUMPHOTOS) albumPhotosCallback(element);
		}
		catch(DOMParseException de){
			showException("File parse exception. Use a XML editor to avoid syntax errors in xml file.");
		}
	}

	private void showException(String msg){
		new ReadException(msg);
	}
	

	public abstract void albumsCallback(Element element);
	
	public abstract void albumPhotosCallback(Element element);
	
}
