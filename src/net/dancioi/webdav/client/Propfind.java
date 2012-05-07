/*	
 * 	File    : Propfind.java
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

import com.google.gwt.http.client.URL;

/**
 * 		Get properties for a specific path (files and folders).
 *  
 * @author Daniel Cioi <dan@dancioi.net>
 * @version $Revision$  Last modified: $Date$, by: $Author$
 */

public class Propfind extends WdHttpMethod{

	private WebdavClientCommand wdcc;

	/**
	 * Constructor. 
	 * @param url
	 * @param username
	 * @param password
	 */
	public Propfind(WebdavClientCommand wdcc, String url, String username, String password){
		super("PROPFIND", url, username, password);
		this.wdcc = wdcc;
	}


	


	private void parseResult(String results){
		//Window.alert("Result \n"+results);
	}
	
	
	
	
	/**
	 * Override the method to set the "Depth" parameter.
	 */
	protected void logOn(String command, String url, String username, String password){
		RequestBuilderWebdav builder = new RequestBuilderWebdav(command, URL.encode(url));
		builder.setHeader("Depth", "0");
		builder.setUser(username);
		builder.setPassword(password);
		tryRequest(builder);
	}
	
	
	
	
	
	
	/**
	 * Method to get the answer from WebDAV server.
	 */
	@Override
	public void getResults() {
		if(connected){
			if(207 == statusCode){
				wdcc.succesfull();
				parseResult(responseText);
			}
			else{
				wdcc.errorReturn(""+statusCode+", "+responseStatus );
			}
		}
		else{
			wdcc.errorReturn("0, connection error");
		}
	}
	
//  207 Multi-Status	
//	401 Authorization required
//  403 Forbidden
}
