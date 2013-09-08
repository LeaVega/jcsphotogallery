/*	
 * 	File    : GalleryTags.java
 * 
 * 	Copyright (C) 2012 Daniel Cioi <dan@dancioi.net>
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
import net.dancioi.jcsphotogallery.shared.GalleryAlbums;

/**
 * Create the albums' categories and return the selected tag.
 * 
 * @author Daniel Cioi <dan@dancioi.net>
 * @version $Revision$ Last modified: $Date$, by: $Author$
 */
public class GalleryTags {

	private ArrayList<AlbumsTag> tags;
	private GalleryAlbums galleryAlbums;
	private ArrayList<AlbumsTag> selectedTags;

	public GalleryTags(GalleryAlbums galleryAlbums) {
		this.galleryAlbums = galleryAlbums;
		initialize();
	}

	private void initialize() {
		selectedTags = new ArrayList<AlbumsTag>();
		tags = getTags(galleryAlbums.getAllAlbums());
	}

	/*
	 * Sorts the albums by tags.
	 */
	private ArrayList<AlbumsTag> getTags(AlbumBean[] albums) {
		ArrayList<AlbumsTag> result = new ArrayList<AlbumsTag>();

		AlbumsTag albumsTag = new AlbumsTag("All albums", albums);
		result.add(albumsTag);
		albumsTag = new AlbumsTag("Recent albums", getAlbumsInReverseOrder(albums));
		result.add(albumsTag);

		for (AlbumBean album : albums) {
			String[] tags = album.getTags();
			for (String tag : tags) {
				albumsTag = new AlbumsTag(tag, album);
				int indexOfTags = result.indexOf(albumsTag);
				if (indexOfTags == -1) {// add the tag to list
					result.add(albumsTag);
				} else {// add the album to the existing tag
					result.get(indexOfTags).addAlbumToTags(album);
				}
			}
		}

		return result;
	}

	private AlbumBean[] getAlbumsInReverseOrder(AlbumBean[] albums) {
		AlbumBean[] reverseAlbums = new AlbumBean[albums.length];
		for (int i = 0; i < albums.length; i++) {
			reverseAlbums[i] = albums[albums.length - i - 1];
		}
		return reverseAlbums;
	}

	/**
	 * Gets Array of tags that will be attached to ListBox
	 * 
	 * @return
	 */
	public String[] getTags() {
		String[] result = new String[tags.size()];
		int i = 0;
		for (AlbumsTag tag : tags) {
			result[i++] = tag.toString();
		}
		return result;
	}

	public AlbumBean[] getAlbumsByTagId(String tagsSeparated) {
		String[] tagsIds = tagsSeparated.split(",");
		selectedTags.clear();
		for (String tagId : tagsIds) {
			selectedTags.add(tags.get(Integer.parseInt(tagId)));
		}
		return findAlbumsIntersectionByTags();
	}

	/*
	 * Finds common albums for two ore more tags selected
	 */
	private AlbumBean[] findAlbumsIntersectionByTags() {
		if (selectedTags.isEmpty()) {
			return tags.get(0).getAlbums();
		}
		if (selectedTags.size() == 1) {
			return selectedTags.get(0).getAlbums();
		} else {
			AlbumBean[] result = selectedTags.get(0).getAlbums();
			for (int i = 1; i < selectedTags.size(); i++) {
				ArrayList<AlbumBean> temp = new ArrayList<AlbumBean>();
				for (AlbumBean albumL : selectedTags.get(i).getAlbums()) {
					for (AlbumBean albumR : result) {
						if (albumL.equals(albumR)) {
							temp.add(albumL);
						}
					}
				}
				AlbumBean[] intesected = new AlbumBean[temp.size()];
				temp.toArray(intesected);
				result = intesected;
			}
			return result;
		}
	}

	/**
	 * Gets a string with the selected tags to be shown in bottom-left corner.
	 * 
	 * @return
	 */
	public String getSelectedTags() {
		StringBuffer result = new StringBuffer();
		result.append("Tags: ");
		for (AlbumsTag shownTag : selectedTags) {
			result.append(shownTag.getTag() + "; ");
		}
		return result.toString();
	}
}
