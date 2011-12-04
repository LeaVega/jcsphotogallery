package net.dancioi.jcsphotogallery.app.view;

import javax.swing.JMenuBar;

import net.dancioi.jcsphotogallery.client.model.Albums;

public interface JcsPhotoGalleryViewInterface {
	
	void addMenuBar(JMenuBar menuBar);
	
	void populateTree(Albums albums);
}
