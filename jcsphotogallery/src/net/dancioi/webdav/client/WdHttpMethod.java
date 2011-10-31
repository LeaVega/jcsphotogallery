/*	
 * 	File    : WdHttpMethod.java
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

package net.dancioi.webdav.client;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;


/**
 * 		Abstract class. 
 * Connect and execute a command on WebDAV server.
 *  
 * @author Daniel Cioi <dan@dancioi.net>
 * @version Revision: $Revision$  Last modified: $Date$  Last modified by: $Author$
 */

public abstract class WdHttpMethod {

	protected boolean connected = true;		// connected?
	protected int statusCode; 						// status code
	protected String responseText;					// response text
	protected String responseStatus;					// response status

	// see more at http://www.gwtapps.com/doc/html/com.google.gwt.http.client.html
	
	
	
	public WdHttpMethod(String command, String url, String username, String password){
		logOn(command, url, username, password);
	}


	public abstract void getResults();

	/**
	 * Make HTTP request and process the associated response.
	 * @param command GET, PUT, MKCOL, DELETE, PROPFIND
	 * @param url
	 * @param username
	 * @param password
	 */
	protected void logOn(String command, String url, String username, String password){
		RequestBuilderWebdav builder = new RequestBuilderWebdav(command, URL.encode(url));

		builder.setUser(username);
		builder.setPassword(password);

		tryRequest(builder);
	}

	protected void tryRequest(RequestBuilderWebdav builder){

		try {
			builder.sendRequest(null, new RequestCallback() {
				public void onError(Request request, Throwable exception) {
					setConnected(false); //"Could not connect to server."
				}

				public void onResponseReceived(Request request, Response response) {
					setStatusCode(response.getStatusCode());
					setResponseText(response.getText());
					setResponseStatus(response.getStatusText());
					goNext();
				}
			});
		} catch (RequestException e) {
			// do something with this.
			// failed to send the request
		}
	}

	/**
	 * Trigger the getResults method.
	 */
	protected void goNext(){
		getResults();
	}
	

	/**
	 * Set connected.
	 * @param b
	 */
	protected void setConnected(boolean b) {
		connected = b;
	}


	/**
	 * Set the response status text.
	 * @param statusText
	 */
	protected void setResponseStatus(String statusText) {
		responseStatus = statusText;
	}

	/**
	 * Set the response text.
	 * @param text
	 */
	protected void setResponseText(String text) {
		responseText = text;
	}

	/**
	 * Set the response status code.
	 * @param stsCode
	 */
	protected void setStatusCode(int stsCode) {
		statusCode = stsCode;
	}
	

}
