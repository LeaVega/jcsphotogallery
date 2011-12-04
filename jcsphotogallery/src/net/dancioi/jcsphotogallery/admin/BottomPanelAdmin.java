/*	
 * 	File    : BottomPanelAdmin.java
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

import net.dancioi.jcsphotogallery.client.controller.GalleryAction;
import net.dancioi.jcsphotogallery.client.view.BottomPanel;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.TextBox;


/**
 * Creates the Bottom Panel. 
 * 
 * The application contains 3 panels (top, center, bottom).
 *  
 * @author Daniel Cioi <dan@dancioi.net>
 * @version $Revision$  Last modified: $Date$, by: $Author$
 */
public class BottomPanelAdmin extends BottomPanel{

	private Image delete;
	private Image moveUp;
	private Image albumImg;
	private TextBox albumLabelTextBox;
	
	/**
	 * @param pg
	 */
	public BottomPanelAdmin(String galleryVersion, GalleryAction galleryControll){
		super(galleryVersion, galleryControll); 
		ini();
	}
	

	/**
	 * Adds delete, move up buttons.
	 */
	private void ini(){

		delete = new Image("ext/previous.gif");		// Delete button
		delete.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				deleteSelected();}} );
		add(delete, 10, 40);
		
		
		moveUp = new Image("ext/previous.gif");		// move up button
		moveUp.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				moveUpSelected();}} );
		add(moveUp, 100, 40);
		
		albumImg = new Image("ext/previous.gif");		// move up button
		albumImg.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				moveUpSelected();}} );
		add(albumImg, 190, 40);
		
		
		delete.setVisible(true);
		moveUp.setVisible(true);
		albumImg.setVisible(true);
	}
	
	
	private void deleteSelected(){
		
	}
	
	private void moveUpSelected(){
		
	}
	
	/**
	 * Adds the album label.
	 * TextBox here.
	 */
	@Override
	protected void addAlbumLabel(){
		albumLabelTextBox = new TextBox();
		albumLabelTextBox.setVisibleLength(40);
		add(albumLabelTextBox, 1,5);
		
	}
	
	
	/**
	 * Sets the album name 
	 * (shown on the bottom left corner)
	 * @param album album name
	 */
	@Override
	public void setAlbumLabel(String album){
		albumLabelTextBox.setText(album);
	}
	
}
