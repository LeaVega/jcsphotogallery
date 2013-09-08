/*	
 * 	File    : AlbumsTag.java
 * 
 * 	Copyright (C) 2011-2012 Daniel Cioi <dan@dancioi.net>
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

package net.dancioi.jcsphotogallery.client.presenter;

import java.util.ArrayList;

import net.dancioi.jcsphotogallery.shared.AlbumBean;

/**
 * Class to keep albums' index for each tag.
 * 
 * @author Daniel Cioi <dan@dancioi.net>
 * @version $Revision$ Last modified: $Date$, by: $Author$
 */
public class AlbumsTag {

	private ArrayList<AlbumBean> albums = new ArrayList<AlbumBean>();
	private String tag;

	public AlbumsTag(String tag, AlbumBean album) {
		this.tag = tag;
		addAlbumToTags(album);
	}

	public AlbumsTag(String tag, AlbumBean[] albums) {
		this.tag = tag;
		for (AlbumBean album : albums)
			addAlbumToTags(album);
	}

	public void addAlbumToTags(AlbumBean album) {
		albums.add(album);
	}

	public String getTag() {
		return tag;
	}

	@Override
	public String toString() {
		return tag;
	}

	@Override
	public boolean equals(Object obj) {
		String objCat = ((AlbumsTag) obj).getTag();
		return tag.contentEquals(objCat);
	}

	public AlbumBean[] getAlbums() {
		AlbumBean[] result = new AlbumBean[albums.size()];
		albums.toArray(result);
		return result;
	}
}
