/*	
 * 	File    : JcsPhotoGalleryPresenter.java
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

import net.dancioi.jcsphotogallery.client.model.Model;
import net.dancioi.jcsphotogallery.client.shared.AlbumBean;
import net.dancioi.jcsphotogallery.client.shared.GalleryAlbums;
import net.dancioi.jcsphotogallery.client.shared.PictureBean;
import net.dancioi.jcsphotogallery.client.shared.Thumbnails;
import net.dancioi.jcsphotogallery.client.view.View;

/**
 * JcsPhotoGallery's presenter.
 * 
 * @author Daniel Cioi <dan@dancioi.net>
 * @version $Revision$ Last modified: $Date$, by: $Author$
 */
public class JcsPhotoGalleryPresenter extends Presenter {

	private Model model;
	private View view;

	private final String GALLERY_PATH = "gallery/";

	private GalleryAlbums galleryAlbums;
	private Thumbnails[] currentThumbnails;
	private String currentThumbnailsPath;
	private int currentThumbnailsPagesNr;
	private int currentThumbnailsPage;

	private int currentAlbumId;

	private int galleryAlbumsCurrentPage = 1;// TODO keep this to return to the same page.

	public JcsPhotoGalleryPresenter(Model model, View view) {
		this.model = model;
		this.view = view;

		initialize();
	}

	private void initialize() {
		bindToModelAndView();
		populateView();
	}

	private void bindToModelAndView() {
		model.bindPresenter(this);
		view.bindPresenter(this);
	}

	private void populateView() {
		model.readGalleryAlbums(GALLERY_PATH + "albums.xml");
	}

	@Override
	public void responseGalleryAlbums(GalleryAlbums galleryAlbums) {
		this.galleryAlbums = galleryAlbums;
		view.setGalleryName(galleryAlbums.getGalleryName(), galleryAlbums.getGalleryHomePage());
		setCurrentThumbnails(GALLERY_PATH, galleryAlbums.getAllAlbums());
	}

	@Override
	public void responseSelectedAlbum(AlbumBean album) {
		view.setAlbumLabel(galleryAlbums.getAlbumName(currentAlbumId));
		setCurrentThumbnails(GALLERY_PATH + galleryAlbums.getAlbumFolderName(currentAlbumId) + "/", album.getPictures());
	}

	private void setCurrentThumbnails(String parentFolderPath, Thumbnails[] thumbnails) {
		currentThumbnails = thumbnails;
		currentThumbnailsPath = parentFolderPath;
		currentThumbnailsPagesNr = (int) Math.ceil(thumbnails.length / 9);
		currentThumbnailsPage = 1;

		displayThumbnails();
	}

	private void displayThumbnails() {
		view.showImagesOnGrid(currentThumbnailsPath, onePage(currentThumbnails));
	}

	// TODO check for the case when the album is empty
	private Thumbnails[] onePage(Thumbnails[] thumbnails) {
		if (thumbnails[0] instanceof PictureBean) {
			view.setUpButtonVisible(true);
		} else {
			view.setUpButtonVisible(false);
		}

		if (currentThumbnailsPagesNr > 1 && currentThumbnailsPage < currentThumbnailsPagesNr) {
			view.setRightButtonVisible(true);
		} else {
			view.setRightButtonVisible(false);
		}
		if (currentThumbnailsPagesNr > 1 && currentThumbnailsPage > 1) {
			view.setLeftButtonVisible(true);
		} else {
			view.setLeftButtonVisible(false);
		}

		showPageNr(currentThumbnailsPage, currentThumbnailsPagesNr);

		if (thumbnails.length <= 9) {
			return thumbnails;
		} else {
			Thumbnails[] result = new Thumbnails[9];
			int length = thumbnails.length > 9 ? thumbnails.length - (currentThumbnailsPage - 1) * 9 : thumbnails.length;// (currentThumbnailsPage - 1) because is not yet switched to the next page.
			length = length > 9 ? 9 : length;
			System.arraycopy(thumbnails, (currentThumbnailsPage - 1) * 9, result, 0, length);
			return result;
		}
	}

	@Override
	public void previousPageEvent() {
		currentThumbnailsPage--;
		displayThumbnails();
	}

	@Override
	public void nextPageEvent() {
		currentThumbnailsPage++;
		displayThumbnails();
	}

	@Override
	public void upPagesEvent() {
		view.setAlbumLabel("");
		setCurrentThumbnails(GALLERY_PATH, galleryAlbums.getAllAlbums());
	}

	private void showPageNr(int page, int pages) {
		if (pages <= 1)
			view.setPageNr("page " + 1 + "/" + 1);
		else
			view.setPageNr("page " + page + "/" + pages);
	}

	/**
	 * Event from view. Decide what to show.
	 */
	@Override
	public void clickedCellEvent(int cellID) {
		if (currentThumbnails[0] instanceof AlbumBean) {
			getAlbumNr(getID(cellID) - 1);
		} else {
			view.showPopUpImg(getID(cellID), currentThumbnailsPath, (PictureBean[]) currentThumbnails);
		}
	}

	/*
	 * Gets the image id.
	 */
	private int getID(int id) {
		return id + currentThumbnailsPage * 9 - 9;
	}

	// TODO write about the "gallery" folder in the help page
	private void getAlbumNr(int nr) {
		currentAlbumId = nr; // used to keep compatibility with the previous version (1.0.x). On version 1.2.x it will be added as an element in album.xml file.
		model.readAlbum(GALLERY_PATH + galleryAlbums.getAlbumFolderName(nr));
	}

}
