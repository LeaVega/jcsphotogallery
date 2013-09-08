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
import net.dancioi.jcsphotogallery.client.view.View;
import net.dancioi.jcsphotogallery.shared.AlbumBean;
import net.dancioi.jcsphotogallery.shared.GalleryAlbums;
import net.dancioi.jcsphotogallery.shared.JcsPhotoGalleryConstants;
import net.dancioi.jcsphotogallery.shared.PictureBean;
import net.dancioi.jcsphotogallery.shared.Thumbnails;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;

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
	private String currentSelectedTags = "0";

	private GalleryTags galleryTags;

	private int currentAlbumId;

	private int galleryAlbumsCurrentPage = 1;

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
		addHistory();
	}

	private void populateView() {
		model.readGalleryAlbums(GALLERY_PATH + "albums.xml");
	}

	/*
	 * Adds history support (for Browser's Back button).
	 */
	private void addHistory() {
		String token = History.getToken();
		if (token.length() == 0) {
			History.newItem("t;0");
		}
		History.addValueChangeHandler(new ValueChangeHandler<String>() {

			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				String token = History.getToken();
				if (token.startsWith("a")) {
					String[] split = token.split(JcsPhotoGalleryConstants.ALBUM_SEPARATOR);
					int parseInt = Integer.parseInt(split[1]);
					getAlbumNr(parseInt);
				} else if (token.startsWith("t")) {
					String[] split = token.split(JcsPhotoGalleryConstants.ALBUM_SEPARATOR);
					if (split.length > 2) {
						showAlbumsByTag(split[2], Integer.parseInt(split[1]));
					}
				}
			}

		});
		// History.fireCurrentHistoryState();
	}

	@Override
	public void responseGalleryAlbums(GalleryAlbums galleryAlbums) {
		this.galleryAlbums = galleryAlbums;
		view.setGalleryName(galleryAlbums.getGalleryName(), galleryAlbums.getGalleryHomePage());
		galleryTags = new GalleryTags(galleryAlbums);
		view.setAlbumsTags(galleryTags.getTags());
		setCurrentThumbnails(GALLERY_PATH, galleryAlbums.getAllAlbums(), -1);
	}

	@Override
	public void responseSelectedAlbum(AlbumBean album) {
		view.setAlbumLabel(galleryAlbums.getAlbumName(currentAlbumId));
		setCurrentThumbnails(GALLERY_PATH + galleryAlbums.getAlbumFolderName(currentAlbumId) + "/", album.getPictures(), -1);
	}

	private void setCurrentThumbnails(String parentFolderPath, Thumbnails[] thumbnails, int lastPage) {
		currentThumbnails = thumbnails;
		currentThumbnailsPath = parentFolderPath;
		currentThumbnailsPagesNr = (int) Math.ceil((double) thumbnails.length / 9);
		currentThumbnailsPage = lastPage > 0 ? lastPage : 1;

		displayThumbnails();
	}

	private void displayThumbnails() {
		view.showImagesOnGrid(currentThumbnailsPath, onePage(currentThumbnails));
	}

	private Thumbnails[] onePage(Thumbnails[] thumbnails) {
		if (thumbnails.length == 0) {
			view.setTagsLabel(galleryTags.getSelectedTags());
			return new Thumbnails[0];
		}

		if (thumbnails[0] instanceof PictureBean) {
			view.setUpButtonVisible(true);
		} else {
			view.setUpButtonVisible(false);
			galleryAlbumsCurrentPage = currentThumbnailsPage; // keep the current albums' page number
			view.setAlbumLabel(galleryTags.getSelectedTags());
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
			int length = thumbnails.length > 9 ? thumbnails.length - (currentThumbnailsPage - 1) * 9 : thumbnails.length;// (currentThumbnailsPage - 1) because is not yet switched to the next page.
			length = length > 9 ? 9 : length;
			Thumbnails[] result = new Thumbnails[length];
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
		currentThumbnailsPage = galleryAlbumsCurrentPage;
		History.newItem("t;" + galleryAlbumsCurrentPage + JcsPhotoGalleryConstants.ALBUM_SEPARATOR + currentSelectedTags);
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
			int id = getID(cellID) - 1;
			getAlbumNr(currentThumbnails[id].getIndex());
			History.newItem("a;" + currentThumbnails[id].getIndex());
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

	@Override
	public void showAlbumsByTag(String selectedTags) {
		currentSelectedTags = selectedTags;
		History.newItem("t;" + currentThumbnailsPage + JcsPhotoGalleryConstants.ALBUM_SEPARATOR + selectedTags); // add tag history.
	}

	private void showAlbumsByTag(String selectedTags, int lastPage) {
		AlbumBean[] albumsByTags = galleryTags.getAlbumsByTagId(selectedTags);
		setCurrentThumbnails(GALLERY_PATH, albumsByTags, lastPage);
		if (albumsByTags.length == 0) {
			view.noGalleryToShow();
		}
	}

}
