/*	
 * 	File    : VersionPanel.java
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

package net.dancioi.jcsphotogallery.client.view;

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.HTML;

/**
 * App's Version Panel.
 * 
 * @author Daniel Cioi <dan@dancioi.net>
 * @version $Revision$ Last modified: $Date$, by: $Author$
 */
public class VersionPanel extends AbsolutePanel {

	private String jcsPhotoGalleryLinkString;
	private String galleryVersion;

	public VersionPanel(String galleryVersion) {
		this.galleryVersion = galleryVersion;
		addVersionNr();
		initialize();
	}

	/*
	 * Shows the project name and version number.
	 */
	private void addVersionNr() {
		jcsPhotoGalleryLinkString = "<div> <a href=\"http://www.dancioi.net/projects/jcsphotogallery/\"><font size=\"1\">jcsPhotoGallery " + galleryVersion + "</font></a> </div>";
	}

	private void initialize() {
		this.setPixelSize(790, 30);
		HTML jcsPhotoGalleryLink = new HTML(jcsPhotoGalleryLinkString);
		add(jcsPhotoGalleryLink, 340, 5);
	}
}
