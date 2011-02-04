/*	
 * 	File    : WebdavClientCommand.java
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
 * Interface that should be implemented by the classes that use webdav library.
 *  
 * @version 1.0 
 * @author Daniel Cioi <dan@dancioi.net>
 */

public interface WebdavClientCommand {

	/**
	 * Method executed when the command result is successfully.
	 */
	public void succesfull();
	
	/**
	 * Method executed when the command return an error.
	 * @param result error message
	 */
	public void errorReturn(String result);

}
