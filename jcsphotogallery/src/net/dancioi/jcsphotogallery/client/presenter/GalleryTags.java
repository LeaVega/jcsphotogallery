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

import net.dancioi.jcsphotogallery.client.shared.AlbumBean;
import net.dancioi.jcsphotogallery.client.shared.GalleryAlbums;

/**
 * Create the albums' categories and return the selected category (tag).
 * 
 * @author Daniel Cioi <dan@dancioi.net>
 * @version $Revision: 42 $ Last modified: $Date: 2012-04-20 05:50:05 +0300 (Fri, 20 Apr 2012) $, by: $Author: dan.cioi@gmail.com $
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

		AlbumsTag tag = new AlbumsTag("All albums", albums);
		result.add(tag);
		tag = new AlbumsTag("Recent albums", getAlbumsInReverseOrder(albums));
		result.add(tag);

		for (AlbumBean album : albums) {
			String[] categories = album.getCategory();
			for (String category : categories) {
				tag = new AlbumsTag(category, album);
				int indexOfTags = result.indexOf(tag);
				if (indexOfTags == -1) {// add the tag to list
					result.add(tag);
				} else {// add the album to the existing tag
					result.get(indexOfTags).addAlbumToCategory(album);
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

	public AlbumBean[] getAlbumsByTagId(int tagId) {
		if (tagId == 0) {// if show all albums is selected, then previous categories are removed, and don't add it to selectedTags
			selectedTags.clear();
		} else {
			AlbumsTag albumsTag = tags.get(tagId);
			if (selectedTags.contains(albumsTag)) {
				selectedTags.remove(albumsTag);
			} else {
				selectedTags.add(albumsTag);
			}
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
	 * Gets just the albums that correspond to the selected tags
	 * 
	 * @return
	 */
	public AlbumBean[] getAlbumsBySelectedTags() {
		return findAlbumsIntersectionByTags();
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
			result.append(shownTag.getCategory() + "; ");
		}
		return result.toString();
	}
}
