/*	
 * 	File    : ReadException.java
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

/**
 * Shows a message if the requested xml file
 * can't be retrieved, or the xml format contains syntax errors.
 *  
 * @author Daniel Cioi <dan@dancioi.net>
 * @version Revision: $Revision$  Last modified: $Date$  Last modified by: $Author$
 */

public class ReadException extends Exception{

	private static final long serialVersionUID = 1L;
	private String message;

	/**
	 * Default constructor
	 */
	public ReadException(){
		super();
	}

	/**
	 * @param message
	 */
	public ReadException(String message){
		super(message);
		this.message = message;
		popUpMessage(message);
	}

	/**
	 * Gets the message.
	 * @return
	 */
	public String getErrorMessage(){
		return message;
	}

	/**
	 * Shows the message.
	 * @param msg
	 */
	private void popUpMessage(String msg){
		Window.alert("Error message : "+msg);
	}

}
