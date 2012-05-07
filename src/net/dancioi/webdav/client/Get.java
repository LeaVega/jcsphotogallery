/*	
 * 	File    : Get.java
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
 * 		Get the files & folders on a specific path.		
 *  
 * @author Daniel Cioi <dan@dancioi.net>
 * @version $Revision$  Last modified: $Date$, by: $Author$
 */

public class Get extends WdHttpMethod{
	private boolean successfully;
	private String[] filesAndFolders;
	private String[] files;
	private String[] folders;
	private CommandException commandException;

	/**
	 * Constructor
	 * @param url complete web address
	 * @param username from login dialog
	 * @param password from login dialog
	 */
	public Get(String url, String username, String password){
		super("GET", url, username, password);
	}


	/**
	 * Method to get the files and folders from url provided in constructor.
	 * @return an array with files and folders together.
	 * @throws CommandException
	 */
	public String[] getFilesAndFolders() throws CommandException{
		if(successfully)return filesAndFolders;
		else throw commandException;
	}

	/**
	 * Method to get the files from url provided in constructor.
	 * @return an array with files.
	 * @throws CommandException
	 */
	public String[] getFiles() throws CommandException{
		return files;
	}

	/**
	 * Method to get the folders from url provided in constructor.
	 * @return an array with folders.
	 * @throws CommandException
	 */
	public String[] getFolders() throws CommandException{
		return folders;
	}


	/**
	 * Parse the xml in String objects. 
	 * @param resultsToSelect the XML result
	 */
	private void selectFilesAndFolders(String resultsToSelect){

	}


	/**
	 * Method to get the answer from WebDAV server.
	 */
	@Override
	public void getResults() {
		if(connected){
			if(200 == statusCode){
				selectFilesAndFolders(responseText);
				successfully = true;
			}
			else{
				commandException = new CommandException(statusCode,responseStatus);
			}
		}
		else{
			commandException = new CommandException("connection error");
		}
	}

	/**
	 * If the command was successfully this will return true
	 * @return boolean
	 */
	public boolean isSuccessfully(){
		return successfully;
	}



}
