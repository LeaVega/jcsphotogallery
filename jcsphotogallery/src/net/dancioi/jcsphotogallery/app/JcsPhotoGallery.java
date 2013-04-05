/*	
 * 	File    : Jcsphotogallery.java
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

package net.dancioi.jcsphotogallery.app;

import net.dancioi.jcsphotogallery.app.controller.JcsPhotoGalleryController;
import net.dancioi.jcsphotogallery.app.model.JcsPhotoGalleryModel;
import net.dancioi.jcsphotogallery.app.model.JcsPhotoGalleryModelInterface;
import net.dancioi.jcsphotogallery.app.view.JcsPhotoGalleryView;
import net.dancioi.jcsphotogallery.app.view.JcsPhotoGalleryViewInterface;

/**
 * JcsPhotoGallery
 * 
 * The Main class of this project :p
 * 
 * For details about this project see the following web pages: http://www.dancioi.net/projects/jcsphotogallery/ http://code.google.com/p/jcsphotogallery/
 * 
 * For a demo of this project see the following web page: http://www.dancioi.net/projects/jcsphotogallery/demo/
 * 
 * @author Daniel Cioi <dan@dancioi.net>
 * @version $Revision$ Last modified: $Date$, by: $Author$
 */

// TODO allow right click to add album anywhere in app's frame
// TODO keep the last path when add new album or new photos
// FIXME if delete the picture that is also thumbnail, set the next one.
// TODO show already added tags in new album tags (to do not duplicate one by misspelling)
// TODO add version in ...About

public class JcsPhotoGallery {

	private String osName;

	public JcsPhotoGallery(String osName) {
		this.osName = osName;
		initialize();
	}

	private void initialize() {
		// MVC pattern
		JcsPhotoGalleryModelInterface model = new JcsPhotoGalleryModel();
		JcsPhotoGalleryViewInterface view = new JcsPhotoGalleryView(model);
		new JcsPhotoGalleryController(model, view);
	}

	public String getOsName() {
		return osName;
	}

}
