/*	
 * 	File    : AlbumsDataAccess.java
 * 
 * 	Copyright (C) 2011-2011 Daniel Cioi <dan@dancioi.net>
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

import java.util.List;

/**
 * Generic class for Read XML files.
 * 
 * @author Daniel Cioi <dan@dancioi.net>
 * @version Revision: $Revision$  Last modified: $Date$  Last modified by: $Author$
 */
public abstract class ReadXMLGeneric {

	public abstract void getXML(String file, String imgPath);
	
	public abstract void readAlbums(String xmlText);
	
	public abstract void readAlbum(String xmlText);
	
	public List getGalleryAlbums(){
		return null;
	}
	
	public PictureBean[] getGalleryAlbumPictures(){
		return null;
	}
	
}
