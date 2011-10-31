/*	
 * 	File    : CommandException.java
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
 * 		WebDAV library - Command Exception.
 *  
 *  Thrown when the command is not successful.  
 *  
 * @author Daniel Cioi <dan@dancioi.net>
 * @version Revision: $Revision$  Last modified: $Date$  Last modified by: $Author$
 */
public class CommandException extends Exception{

	private static final long serialVersionUID = 1L;
	private int errorCode;
	private String errorMessage;

	/**
	 * Constructor.
	 */
	public CommandException (int errorCode, String errorMessage){
		super();
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	/**
	 * Constructor.
	 * For the case - could not connect to server.
	 */
	public CommandException (String errorMessage){
		super();
		this.errorCode = 0;
		this.errorMessage = errorMessage;
	}

	/**
	 * Get the error code from the command.
	 */
	public int getErrorCode(){
		return errorCode;
	}

	/**
	 * Get the error message from the command.
	 */
	public String getErrorMessage(){
		return errorMessage;
	}

}
