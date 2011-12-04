package net.dancioi.jcsphotogallery.app.model;

import java.io.File;

import net.dancioi.jcsphotogallery.client.model.Albums;

public interface JcsPhotoGalleryModelInterface {

	void setGalleryPath(File galleryPath);
	
	public Albums getGalleryAlbums();
}
