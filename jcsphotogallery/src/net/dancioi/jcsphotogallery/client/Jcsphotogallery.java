/*	
 * 	File    : Jcsphotogallery.java
 * 
 * 	Copyright (C) 2010-2014 Daniel Cioi <dan@dancioi.net>
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

import net.dancioi.jcsphotogallery.client.model.JcsPhotoGalleryModel;
import net.dancioi.jcsphotogallery.client.model.Model;
import net.dancioi.jcsphotogallery.client.presenter.JcsPhotoGalleryPresenter;
import net.dancioi.jcsphotogallery.client.view.View;

import com.google.gwt.core.client.EntryPoint;

/**
 * JcsPhotoGallery
 * 
 * The Main class of this project :p
 * 
 * For details about this project see the following web pages:
 * http://www.dancioi.net/projects/jcsphotogallery/
 * http://code.google.com/p/jcsphotogallery/
 * 
 * For a demo of this project see the following web page:
 * http://www.dancioi.net/projects/jcsphotogallery/demo/
 * 
 * @author Daniel Cioi <dan@dancioi.net>
 * @version $Revision$ Last modified: $Date: 2012-06-23 13:45:50 +0200
 *          (Sat, 23 Jun 2012) $, by: $Author$
 */
public abstract class Jcsphotogallery implements EntryPoint {

	public void onModuleLoad() {
		// MVP pattern
		Model model = new JcsPhotoGalleryModel();
		View view = getView();
		new JcsPhotoGalleryPresenter(model, view);
	}

	protected abstract View getView();

}
