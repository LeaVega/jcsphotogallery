/*	
 * 	File    : PopupGeneric.java
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

package net.dancioi.jcsphotogallery.client;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.PopupPanel;

/**
 * This class is a generic for PopUp panel. 
 * Define the center position. 
 *  
 * @author Daniel Cioi <dan@dancioi.net>
 * @version Revision: $Revision$  Last modified: $Date$  Last modified by: $Author$
 */

public class PopupGeneric extends PopupPanel{

	private int popUpSizeW;	// popup size on X
	private int popUpSizeH;	// popup size on Y
	private int browserWindowW;	// browser window size on X
	private int browserWindowH;	// browser window size on Y
	
	/**
	 * 
	 * @param sizeX
	 * @param sizeY
	 */
	public PopupGeneric(int sizeX, int sizeY){
		super(true);
		popUpSizeW = sizeX;
		popUpSizeH = sizeY;
	}
	
	/**
	 * Default constructor
	 */
	public PopupGeneric(){
		super(true);
	}
	
	
	/**
	 * Method to set the popup panel position.
	 */
	protected void setPosition(){		
		getWindowSize();
		setPopupPosition((browserWindowW-popUpSizeW)/2,(browserWindowH-popUpSizeH)/2); 
	}

	/**
	 * Method to get the visible browser window's size.
	 */
	protected void getWindowSize(){
		browserWindowW = Window.getClientWidth();
		browserWindowH = Window.getClientHeight();
	}
	
	
	/**
	 * Method to set the popup width size.
	 * @param x
	 */
	protected void setSizeX(int x){
		popUpSizeW = x;
	}
	
	/**
	 * Method to get the popup width size.
	 * @return popup width size
	 */
	protected int getSizeX(){
		return popUpSizeW;
	}
	
	/**
	 * Method to set the popup height size.
	 * @param y
	 */
	protected void setSizeY(int y){
		popUpSizeH = y;
	}
	
	/**
	 * Method to get the popup height size.
	 * @return popup height size
	 */
	protected int getSizeY(){
		return popUpSizeH;
	}
	
	/**
	 * Method to set the popup height size.
	 * @return
	 */
	protected int getBrowserWindowWidth(){
		return browserWindowW;
	}
	
	protected int getBrowserWindowHeight(){
		return browserWindowH;
	}
	


}
