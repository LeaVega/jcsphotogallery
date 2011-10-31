/*	
 * 	File    : LoginPanel.java
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

import net.dancioi.jcsphotogallery.client.PopupGeneric;
import net.dancioi.webdav.client.WebdavClient;
import net.dancioi.webdav.client.WebdavClientCommand;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;


/**
 * The Login Panel. 
 * Check the Username and Password to login on WebDAV.
 *  
 * @author Daniel Cioi <dan@dancioi.net>
 * @version Revision: $Revision$  Last modified: $Date$  Last modified by: $Author$
 */


public class LoginPanel extends PopupGeneric implements WebdavClientCommand{
	
	AbsolutePanel apLogin;
	private int pw = 300; // popup width
	private int ph = 150; // popup height
	private JcsPhotoGalleryAdmin jpga;
	private Label titleLabel;
	private TextBox usernameText;
	private PasswordTextBox passwordText;
	private int countFailLogins = 0;
	
	
	public LoginPanel(JcsPhotoGalleryAdmin jpga){
		super(300, 150);
		this.jpga = jpga;
		initialize();
		show();
	}
	
	
	
	/**
	 * Initialize
	 */
	private void initialize(){
		setGlassStyleName("gwt-PopupPanelGlass");
		setGlassEnabled(true); 


		apLogin = new AbsolutePanel();
		apLogin.setPixelSize(pw, ph);	
		apLogin.setStyleName("popUpPanel");

	
		titleLabel = new Label("JcsPhotoGallery Admin Login");
		apLogin.add(titleLabel,50,10);
		
		Label usernameLabel = new Label("Username");
		apLogin.add(usernameLabel,20,50);
		
		Label passwordLabel = new Label("Pasword");
		apLogin.add(passwordLabel,20,80);
		
		
		usernameText = new TextBox();
		usernameText.setWidth("170px");
		apLogin.add(usernameText,100,50);
		
		passwordText = new PasswordTextBox();
		passwordText.setWidth("170px");
		apLogin.add(passwordText,100,80);
		
		
		Button loginButton = new Button("Login");
		loginButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				connect();
				;}} );
		
		apLogin.add(loginButton,245,120);
		
		setPosition();
		setWidget(apLogin);
	}
	
	
	private void connect(){
		//new WebdavClient(jpga.url, usernameText.getText(),passwordText.getText()).writeFolder(this, "blabla");
		// Propfind to check the username, password and web address.
		new WebdavClient(jpga.url, usernameText.getText(),passwordText.getText()).propFind(this, ".");
	}
	
	
	private void returnRessult(boolean result){
		if(result){
			jpga.setLogin(true);
			this.setVisible(false);
		}
		else{
			if(countFailLogins<3){
				countFailLogins++;
				titleLabel.addStyleName("wrongUser");
				titleLabel.setText("Username or Password incorect");
			}
			else{
				jpga.setLogin(false);
				this.setVisible(false);
			}
		}
		
	}
	



	@Override
	public void succesfull() {
		returnRessult(true);
		jpga.initializeWebDavLibrary(usernameText.getText(),passwordText.getText());
	}



	@Override
	public void errorReturn(String result) {
		returnRessult(false);
	}

	
	

}
