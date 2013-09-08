/*	
 * 	File    : View.java
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

import net.dancioi.jcsphotogallery.client.presenter.Presenter;
import net.dancioi.jcsphotogallery.shared.PictureBean;
import net.dancioi.jcsphotogallery.shared.Thumbnails;

/**
 * JcsPhotoGallery's view.
 * 
 * @author Daniel Cioi <dan@dancioi.net>
 * @version $Revision$ Last modified: $Date$, by: $Author$
 */
public abstract class View implements PageController {

	private Presenter presenter;

	public void bindPresenter(Presenter presenter) {
		this.presenter = presenter;

	}

	protected Presenter getPresenter() {
		return presenter;
	}

	public abstract void setGalleryName(String name, String homePage);

	@Override
	public void previousPageEvent() {
		presenter.previousPageEvent();
	}

	@Override
	public void nextPageEvent() {
		presenter.nextPageEvent();
	}

	@Override
	public void upPagesEvent() {
		presenter.upPagesEvent();
	}

	public abstract void showImagesOnGrid(String imagesPath, Thumbnails[] thumbnails);

	public abstract void setAlbumLabel(String albumName);

	public abstract void setTagsLabel(String selectedTags);

	public abstract void showPopUpImg(int id, String imgPath, PictureBean[] pictures);

	public abstract void setLeftButtonVisible(boolean visible);

	public abstract void setRightButtonVisible(boolean visible);

	public abstract void setUpButtonVisible(boolean visible);

	public abstract void setPageNr(String pageNr);

	public abstract void setAlbumsTags(String[] tags);

	public abstract void noGalleryToShow();

	public abstract void showAlertMessage(String msg);

}
