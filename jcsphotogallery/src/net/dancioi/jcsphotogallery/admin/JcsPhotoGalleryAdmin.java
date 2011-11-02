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


import net.dancioi.jcsphotogallery.client.Jcsphotogallery;
import net.dancioi.webdav.client.WebdavClient;

import com.google.gwt.user.client.ui.RootPanel;
// GWT Designer can cause problems with class path (uninstall it if it's the case).

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
 * @author Daniel Cioi <dan@dancioi.net>
 * @version Revision: $Revision$  Last modified: $Date$  Last modified by: $Author$
 */

public class JcsPhotoGalleryAdmin extends Jcsphotogallery{

	private boolean login;
	String url= "http://www.serverintest.com/webdav/gallery/";
	public WebdavClient webdavLib;

	
	public void onModuleLoad() {
		initialize();
		checkUser();
	}
	
	
	/**
	 * Adds the center panel.
	 */
	protected void addCenterPanel(){
		centerPanel = new CenterPanelAdmin(this);
		RootPanel.get("images").add(centerPanel); 
	}
	
	
	/**
	 * Adds the bottom panel.
	 */
	protected void addBottomPanel(){
		bottomPanel = new BottomPanelAdmin(getGalleryVersion(), this);
		RootPanel.get("bottomPanel").add(bottomPanel);
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

	}
	
	
	
	private void showAddAlbumDialog(){

		new AddAlbumDialog().show();
	
	}
	
}
