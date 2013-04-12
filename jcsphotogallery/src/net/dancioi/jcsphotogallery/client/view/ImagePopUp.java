/*	
 * 	File    : ImagePopUp.java
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

import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Image;

/**
 * The image class to generate a event when the image is fully loaded.
 * 
 * @author Daniel Cioi <dan@dancioi.net>
 * @version $Revision$ Last modified: $Date$, by: $Author$
 */
public class ImagePopUp extends Image {

	private PopUpImgShow pi;
	private boolean loaded;

	public ImagePopUp(String link, PopUpImgShow pi) {
		super(link);
		this.pi = pi;
	}

	/**
	 * When the image is fully loaded a event is fired.
	 */
	/* The ONLOAD event is fired few times per second, so a flag is required. */
	public void onBrowserEvent(Event event) {
		if (event.getTypeInt() == Event.ONLOAD && !loaded) {
			loaded = true;
			pi.scaleImg(this);
		} else if (event.getTypeInt() == Event.ONERROR) {
			this.setUrl("template/ext/imgNotFound.png");
		}
	}

}
