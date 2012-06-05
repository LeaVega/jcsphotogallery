/*	
 * 	File    : JcsPhotoGalleryViewInterface.java
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

package net.dancioi.jcsphotogallery.app.view;

import java.awt.event.WindowAdapter;

import javax.swing.JMenuBar;
import javax.swing.JProgressBar;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import net.dancioi.jcsphotogallery.app.controller.JcsPhotoGalleryControllerInterface;
import net.dancioi.jcsphotogallery.client.shared.AlbumBean;
import net.dancioi.jcsphotogallery.client.shared.GalleryAlbums;
import net.dancioi.jcsphotogallery.client.shared.PictureBean;

/**
 * JcsPhotoGallery's View interface.
 * 
 * @author Daniel Cioi <dan@dancioi.net>
 * @version $Revision$ Last modified: $Date$, by: $Author$
 */
public interface JcsPhotoGalleryViewInterface {

	void addMenuBar(JMenuBar menuBar);

	void populateTree(DefaultMutableTreeNode[] defaultMutableTreeNodes);

	JTree getTree();

	void attachActions(JcsPhotoGalleryControllerInterface controller);

	void addNewAlbumToGallery(DefaultMutableTreeNode newAlbum);

	void addPicturesToAnExistingAlbum(DefaultMutableTreeNode addPicturesToExistingAlbum);

	void showPicture(PictureBean picture, DefaultMutableTreeNode treeNode);

	void showAlbum(AlbumBean albumBean, DefaultMutableTreeNode treeNode);

	void showGallery(GalleryAlbums galleryAlbums);

	void setVisible(boolean b);

	void addCloseWindowListener(WindowAdapter windowAdapter);

	JProgressBar getProgressBar();

}
