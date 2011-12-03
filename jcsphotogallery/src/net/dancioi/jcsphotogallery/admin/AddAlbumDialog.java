/*	
 * 	File    : AddAlbumDialog.java
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

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.TextBox;


/**
 * 	The album dialog to create a new album.
 *  
 * @author Daniel Cioi <dan@dancioi.net>
 * @version $Revision$  Last modified: $Date$, by:  $Author$
 */

public class AddAlbumDialog  extends PopupGeneric{

	FormPanel fp = new FormPanel();
	
	AbsolutePanel apAddAlbum;
	
	FileUpload upload;
	
	public AddAlbumDialog(){
		super(300, 400);
		initialize();
	}
	
	
	/**
	 * Initialize
	 */
	private void initialize(){
		setGlassStyleName("gwt-PopupPanelGlass");
		setGlassEnabled(true); 
		
//		fp.setAction("/gallery");
		
		fp.setEncoding(FormPanel.ENCODING_MULTIPART);
		fp.setMethod(FormPanel.METHOD_POST);
		
		setPosition();

		apAddAlbum = new AbsolutePanel();
		apAddAlbum.setPixelSize(300, 400);	
		apAddAlbum.setStyleName("popUpPanel");

		fp.setWidget(apAddAlbum);
		
		Grid g = new Grid(4, 2);
		
		TextBox name = new TextBox();
		Button imgPath = new Button("Subbmit");
		imgPath.addClickHandler(new ClickHandler() {
		      public void onClick(ClickEvent event) {
		    	  fp.setAction("/webdav/gallery/"+upload.getFilename());
	//	          Window.alert("How high?");
		    	  fp.submit();
		        }
		      });
		final TextBox folder = new TextBox();
		TextBox cat1 = new TextBox();
		TextBox cat2 = new TextBox();
		
		g.setText(0, 0, "Album name");
		g.setWidget(0,1, name);
		
	//	g.setText(1, 0, "Image file");
	//	g.setWidget(1,1,imgPath);
		
		g.setText(1, 0, "Folder name");
		g.setWidget(1,1, folder);
		
		g.setText(2, 0, "Category 1");
		g.setWidget(2,1, cat1);
		
		g.setText(3, 0, "Category 2");
		g.setWidget(3,1, cat2);
		

		apAddAlbum.add(g);
		
		
		
		 // Create a FileUpload widget.
	    upload = new FileUpload();
	    upload.setName("uploadFormElement");
	    apAddAlbum.add(upload);

	    
	    Grid g2 = new Grid(2, 2);
	    g2.setWidget(1,1,imgPath);
	    apAddAlbum.add(g2);
	    
	    
	    // Add an event handler to the form.
	    fp.addSubmitHandler(new FormPanel.SubmitHandler() {
	      public void onSubmit(SubmitEvent event) {
	        // This event is fired just before the form is submitted. 
	        if (folder.getText().length() == 0) {
	          Window.alert("The text box must not be empty");
	          event.cancel();
	        }
	      }
	    });
	    
	    fp.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
	      public void onSubmitComplete(SubmitCompleteEvent event) {
	        // When the form submission is successfully completed, this event is fired. 
	        Window.alert(event.getResults());
	      }
	    });
		
		
		
	   
	    
	    add(fp);
	}
	
	
	
}
