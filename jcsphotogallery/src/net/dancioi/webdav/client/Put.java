/*	
 * 	File    : Put.java
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
 * 		Put a file on WebDAV server.
 *  
 * @author Daniel Cioi <dan@dancioi.net>
 * @version Revision: $Revision$  Last modified: $Date$  Last modified by: $Author$
 */

public class Put{

	private boolean succesfull;
	private CommandException commandException;
	private String dataContent, url, username, password;

	/**
	 * Constructor. 
	 * @param url
	 * @param username
	 * @param password
	 */
	public Put(String url, String username, String password, String dataContent){
		this.url = url;
		this.username = username;
		this.password = password;
		this.dataContent = dataContent;
		execute();
	}


	/**
	 * Method that return if the file was successful added.
	 * @return
	 * @throws CommandException
	 */
	public boolean isSuccesfull() throws CommandException{
		if(succesfull)
			return succesfull;
		else throw commandException;
	}

	private void execute(){
		RequestBuilderWebdav builder = new RequestBuilderWebdav("PUT", URL.encode(url));

		builder.setHeader("Content-Type", "application/x-www-form-urlencoded");
		builder.setUser(username);
		builder.setPassword(password);

		tryRequest(builder);
	}	


	protected void tryRequest(RequestBuilderWebdav builder){
		try {
			builder.sendRequest(dataContent, new RequestCallback() {
				public void onError(Request request, Throwable exception) {
					//setConnected(false); //"Could not connect to server."
				}

				public void onResponseReceived(Request request, Response response) {
					if (201 == response.getStatusCode()) {
						
					}
					else{
						commandException = new CommandException(response.getStatusCode(),response.getStatusText());
					}
				}
			});
		} catch (RequestException e) {
			// do something with this.
			// failed to send the request
		}
	}

	


}
