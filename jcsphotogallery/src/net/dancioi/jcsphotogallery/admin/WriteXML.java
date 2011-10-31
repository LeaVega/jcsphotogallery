/*	
 * 	File    : WriteXML.java
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

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.XMLParser;


/**
 * 	The class to create the xml that will be saved on web server.
 *  
 * @author Daniel Cioi <dan@dancioi.net>
 * @version Revision: $Revision$  Last modified: $Date$  Last modified by: $Author$
 */

public class WriteXML {

	public WriteXML(){
		
		
	}
	
	
	public String createAlbums(){
		
		Document messageDom = XMLParser.createDocument();
		messageDom.createElement("bla bla 1");
		messageDom.createElement("bla bla 2");
		
		String str = messageDom.toString();
		return str;
	}
	
	
	
	
}
