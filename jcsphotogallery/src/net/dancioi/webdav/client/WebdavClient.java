/*	
 * 	File    : WebdavClient.java
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


import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;



/**
 * 		WebDAV library
 *  
 *  Library client side to write (modify) the files on web server.  
 *  
 * @author Daniel Cioi <dan@dancioi.net>
 * @version Revision: $Revision$  Last modified: $Date$  Last modified by: $Author$
 */

public class WebdavClient implements EntryPoint{

	private String url;
	private String username;
	private String password;

	/**
	 * Default constructor.
	 */
	public WebdavClient(){
		
	}

	/**
	 * The constructor of WebDAV client library. 
	 * @param url complete web address
	 * @param username
	 * @param password
	 */
	public WebdavClient(String url, String username, String password){
		this.url = url;
		this.username = username;
		this.password = password;

	}


	
	public void propFind(WebdavClientCommand wdcc, String path){
		String urlProp = url;
		if(path.startsWith(".")){
			// do nothing
		}
		else{
			urlProp = urlProp+path+"/";
		}
		new Propfind(wdcc, urlProp, username, password);
	}
	

	/**
	 * Method to get files and folders from a specific path.
	 * @param path
	 * @return String[]
	 * @throws CommandException
	 */
	public String[] getFilesAndFolders(String path) throws CommandException{
		return new Get(path, username, password).getFilesAndFolders();
	}

	/**
	 * Method to return just the files from a specific path.
	 * @param path
	 * @return files
	 * @throws CommandException
	 */
	public String[] getFiles(String path) throws CommandException{
		return new Get(path, username, password).getFiles();
	}

	/**
	 * Method to return just the folders from a specific path.
	 * @param path
	 * @return folders
	 * @throws CommandException
	 */
	public String[] getFolders(String path) throws CommandException{
		return new Get(path, username, password).getFolders();
	}


	
	/**
	 * Method to upload a file on the WebDAV server.
	 * @param path
	 * @param fileName
	 * @throws CommandException
	 */
	public void puFile(String path, String fileName, String content) throws CommandException{
		String urlPut = path+fileName+"/";
		new Put(urlPut, username, password, content).isSuccesfull();
	}

	/**
	 * Method to create a folder at a specific path.
	 * @param path
	 * @param folder
	 * @throws CommandException
	 */

	public void writeFolder(WebdavClientCommand wdcc, String folder){
		String urlMk = url+folder+"/";
		new Mkdir(wdcc, urlMk, username, password);
	}
	
	
	/**
	 * Method to delete a folder and all his children.
	 * @param path
	 * @param folder
	 * @throws CommandException
	 */
	public void DeleteFolder(String path, String folder) throws CommandException{
		String urlDel = path+folder+"/";
		new Delete(urlDel, username, password).isSuccesfull();
	}

	/**
	 * Method to delete just a file.
	 * @param path
	 * @param file
	 * @throws CommandException
	 */
	public void DeleteFile(String path, String file) throws CommandException{
		String urlDel = path+file+"/";
		new Delete(urlDel, username, password).isSuccesfull();
	}










	/** it's required to be in a GWT library */
	@Override
	public void onModuleLoad() {
		// TODO Auto-generated method stub	
	}
}
