/*	
 * 	File    : Delete.java
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


/**
 * 		Delete a folder/file. 
 * All children from the path will be deleted.
 *  
 * @author Daniel Cioi <dan@dancioi.net>
 * @version Revision: $Revision$  Last modified: $Date$  Last modified by: $Author$
 */

public class Delete extends WdHttpMethod{

	private boolean succesfull;
	private CommandException commandException;

	/**
	 * Constructor. 
	 * @param url
	 * @param username
	 * @param password
	 */
	public Delete(String url, String username, String password){
		super("DELETE", url, username, password);
	}


	/**
	 * Method that return if the folder/file was deleted.
	 * @return
	 * @throws CommandException
	 */
	public boolean isSuccesfull() throws CommandException{
		if(succesfull)
			return succesfull;
		else throw commandException;
	}


	/**
	 * Method to get the answer from WebDAV server.
	 */
	@Override
	public void getResults() {
		if(connected){
			if(204 == statusCode){
				succesfull = true;
			}
			else{
				commandException = new CommandException(statusCode,responseStatus);
			}
		}
		else{
			commandException = new CommandException("connection error");
		}
	}

}
