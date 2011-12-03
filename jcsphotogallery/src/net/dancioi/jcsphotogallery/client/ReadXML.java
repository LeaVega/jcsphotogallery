/*	
 * 	File    : ReadXML.java
 * 
 * 	Copyright (C) 2010-2011 Daniel Cioi <dan@dancioi.net>
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

import com.google.gwt.xml.client.Element;

/**
 * Reads the XML files from the web server.
 *  
 * @author Daniel Cioi <dan@dancioi.net>
 * @version Revision: $Revision$  Last modified: $Date$  Last modified by: $Author$
 */

public class ReadXML extends ReadXMLGeneric{

	private ReadXMLCallback readCallback;
	private String currentImagesPath;
	
	public static int FLAG_ALBUMS = 1;
	public static int FLAG_ALBUMPHOTOS = 2;
	
	public ReadXML(ReadXMLCallback readCallback){
		this.readCallback = readCallback;
	}
	
	/**
	 * Gets the albums' items' list.
	 * Parse the XML string.
	 * @param xmlText String.
	 */
	public void readAlbums(String albumsFile){
			readXmlFile(albumsFile, FLAG_ALBUMS);
	}

	/**
	 * Gets the album's images list.
	 */
	public void readAlbum(String imagesPath){
		currentImagesPath = imagesPath;
		readXmlFile(imagesPath+"/album.xml", FLAG_ALBUMPHOTOS);			
	}

	@Override
	public void albumsCallback(Element element) {
		readCallback.albumsCallback(getAlbums(element));
	}

	@Override
	public void albumPhotosCallback(Element element) {
		AlbumPhotos albumPhotos = getAlbumPhotos(element);
		albumPhotos.setImagesPath(currentImagesPath);
		readCallback.albumPhotosCallback(albumPhotos);
	}

}
