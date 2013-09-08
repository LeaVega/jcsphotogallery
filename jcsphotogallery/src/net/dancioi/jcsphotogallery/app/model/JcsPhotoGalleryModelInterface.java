/*	
 * 	File    : JcsPhotoGalleryModelInterface.java
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

package net.dancioi.jcsphotogallery.app.model;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.JProgressBar;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import net.dancioi.jcsphotogallery.app.view.JcsPhotoGalleryViewInterface;
import net.dancioi.jcsphotogallery.shared.AlbumBean;
import net.dancioi.jcsphotogallery.shared.GalleryAlbums;
import net.dancioi.jcsphotogallery.shared.PictureBean;

/**
 * JcsPhotoGallery's Model interface.
 * 
 * @author Daniel Cioi <dan@dancioi.net>
 * @version $Revision$ Last modified: $Date$, by: $Author$
 */
public interface JcsPhotoGalleryModelInterface {

	DefaultMutableTreeNode[] getGalleryTreeNodes(File galleryPath);

	DefaultMutableTreeNode[] getGalleryTreeNodesFromNewGallery(File galleryPath) throws GalleryException;

	BufferedImage getPicture(PictureBean picture, int maxSize);

	DefaultMutableTreeNode getPicturesTreeNodeAddedToNewAlbum(File[] selectedFiles, JProgressBar progressBar);

	DefaultMutableTreeNode getPicturesTreeNodesAddedToExistingAlbum(File[] selectedFiles, DefaultMutableTreeNode treeNode, JProgressBar progressBar);

	void saveGalleryChanges(JTree jTree);

	GalleryAlbums getGalleryAlbums();

	File getAppGalleryPath();

	boolean isGallerySaved(JTree jTree);

	Configs getConfigs();

	void deleteAlbum(AlbumBean albumToDelete);

	void deletePicture(PictureBean picture);

	void copyPicture(PictureBean picture, AlbumBean albumSource, AlbumBean albumDestination);

	void deleteImage(DefaultMutableTreeNode treeNode);

	void deleteAlbum(DefaultMutableTreeNode treeNode);

	void selectNextNode();

	void selectPreviousNode();

	void selectNode(DefaultMutableTreeNode treeNode);

	void bindView(JcsPhotoGalleryViewInterface view);

	void rotatePictureClockwise();

	void rotatePictureCounterClockwise();
}
