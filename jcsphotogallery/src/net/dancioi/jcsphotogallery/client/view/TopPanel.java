/*	
 * 	File    : TopPanel.java
 * 
 * 	Copyright (C) 2010 Daniel Cioi <dan@dancioi.net>
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

package net.dancioi.jcsphotogallery.client.view;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ListBox;

/**
 * This class create the TOP panel. The application contains 3 panels (top, center, bottom).
 * 
 * @author Daniel Cioi <dan@dancioi.net>
 * @version $Revision$ Last modified: $Date$, by: $Author$
 */
public class TopPanel extends AbsolutePanel {
	// TODO the "last albums" should show last 9 albums; remove tag if the same is selected.
	private JcsPhotoGalleryView view;
	private ListBox galleryTags;

	public TopPanel(JcsPhotoGalleryView view) {
		this.view = view;
		initialize();
	}

	private void initialize() {
		setSize("800px", "25px");

		galleryTags = new ListBox(false);
		galleryTags.setWidth("200px");
		galleryTags.addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				int selected = galleryTags.getSelectedIndex();
				selectedList(selected);
			}
		});

		add(galleryTags, 597, 1);
	}

	/**
	 * This method get the selected item from ListBox and pass it.
	 * 
	 * @param selected
	 *            Sorting ListBox id selected.
	 */
	private void selectedList(int selected) {
		view.showAlbumsByCategory(selected);
	}

	/**
	 * This method add the gallery owner home link web page. the owner is read from albums.xml file.
	 * 
	 * @param name
	 *            the gallery owner's name
	 * @param nameHomePage
	 *            the gallery owner's link to home page.
	 */
	public void setHomePage(String name, String nameHomePage) {
		String homeLink = "<div> <a href=\"" + nameHomePage + "\">" + name + "'s Web Page</a> </div>";
		HTML homePage = new HTML(homeLink);
		add(homePage, 3, 1);
	}

	/**
	 * Method to add the sorted items from SortAlbums class.
	 * 
	 * @param tags
	 *            a list with sorted items.
	 */
	public void addTagsToListBox(String[] tags) {
		for (String tag : tags) {
			galleryTags.addItem(tag);
		}
	}

}
