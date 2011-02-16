/*	
 * 	File    : JcsPhotoGalleryAdmin.java
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

package net.dancioi.jcsphotogallery.admin;


import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;

import net.dancioi.webdav.client.*;
import net.dancioi.jcsphotogallery.client.*;


/**
 * 		JcsPhotoGallery Admin application.
 * 
 * The Main class of this project :p
 * 
 * For details about this project see the following web pages:
 * http://www.dancioi.net/projects/jcsphotogallery/
 * http://code.google.com/p/jcsphotogallery/
 * 
 * For a demo of this project see the following web page:
 * http://www.dancioi.net/projects/jcsphotogallery/demo/
 * 
 * @version 1.0 
 * @author Daniel Cioi <dan@dancioi.net>
 */

public class JcsPhotoGalleryAdmin extends Jcsphotogallery{

	private boolean login;
	String url= "http://www.serverintest.com/webdav/gallery/";
	public WebdavClient webdavLib;

	
	public void onModuleLoad() {
		initialize();
		checkUser();
	}
	
	private void checkUser(){
		new LoginPanel(this);
	}
	

	public void initializeWebDavLibrary(String validUsername, String validPassword){
		webdavLib = new WebdavClient(url, validUsername, validPassword);
	}
	
	
	
	public void setLogin(boolean loginResult){
		login = loginResult;
		if(login)loginTrue();
	}
		
	public void loginTrue(){
		//Window.alert("Succesfully login");
	}
	
	
	
	@Override
	public void initializeAlbums(){
		albums = new AlbumsAdmin();
	}

	
	/**
	 * Method to get the selected album parameters.
	 * @param nr
	 */
	@Override
	public void getAlbumNr(int nr){
		if(nr == albums.getAllAlbumsNr()-1){
			showAddAlbumDialog();
		}
		else{
			String albumPath = "gallery/"+albums.getAlbumFolderName(nr)+"/album.xml";
			String imagesPath = "gallery/"+albums.getAlbumFolderName(nr)+"/";
			readXml.getXML(albumPath, imagesPath);
		}
	}
	
	
	private void showAddAlbumDialog(){
		//Window.alert("show add album");
		new AddAlbumDialog().show();
	
	}
	
}
