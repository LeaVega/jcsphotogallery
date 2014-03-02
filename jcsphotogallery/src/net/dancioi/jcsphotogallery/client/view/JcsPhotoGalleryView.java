/*	
 * 	File    : JcsPhotoGalleryView.java
 * 
 * 	Copyright (C) 2014 Daniel Cioi <dan@dancioi.net>
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

public abstract class JcsPhotoGalleryView extends View {

	protected JcsPhotoGalleryView() {
		initialize();
	}

	/*
	 * Initialize
	 */
	private void initialize() {
		addHeader();
		addTopPanel();
		addBottomPanel();
		addAppVersion();
		addCenterPanel();
		addHandlers();
	}

	protected abstract void addHeader();

	protected abstract void addTopPanel();

	protected abstract void addBottomPanel();

	protected abstract void addAppVersion();

	protected abstract void addCenterPanel();

	protected abstract void addHandlers();

	protected abstract void clickEventOnCell(int id);

}
