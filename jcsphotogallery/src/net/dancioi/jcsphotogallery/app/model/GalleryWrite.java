/*	
 * 	File    : GalleryExport.java
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

import java.io.File;
import java.util.ArrayList;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import net.dancioi.jcsphotogallery.client.model.AlbumBean;
import net.dancioi.jcsphotogallery.client.model.PictureBean;

/**
 * This class traverse the JTree and write the modified albums xml files..
 * 
 * @author Daniel Cioi <dan@dancioi.net>
 * @version $Revision$ Last modified: $Date: 2012-03-20 22:39:16 +0200
 *          (Tue, 20 Mar 2012) $, by: $Author$
 */

public class GalleryWrite {

	private JTree jTree;
	private FileXML fileXML;
	private String galleryPayh;

	public GalleryWrite(JTree jTree, FileXML fileXML, String galleryPayh) {
		this.jTree = jTree;
		this.fileXML = fileXML;
		this.galleryPayh = galleryPayh;
		saveChanges();
	}

	private void saveChanges() {
		DefaultTreeModel treeNode = (DefaultTreeModel) jTree.getModel();
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) treeNode.getRoot();

		ArrayList<AlbumBean> albums = new ArrayList<AlbumBean>();

		for (int albumsNr = 0; albumsNr < root.getChildCount(); albumsNr++) {
			DefaultMutableTreeNode albumNode = (DefaultMutableTreeNode) treeNode.getChild(root, albumsNr);
			if (albumNode.getUserObject() instanceof AlbumBean) {
				AlbumBean album = (AlbumBean) albumNode.getUserObject();

				System.out.println("album " + albumNode.getUserObject().toString());
				ArrayList<PictureBean> pictures = new ArrayList<PictureBean>();
				for (int pictureNr = 0; pictureNr < albumNode.getChildCount(); pictureNr++) {
					DefaultMutableTreeNode pictureNode = (DefaultMutableTreeNode) albumNode.getChildAt(pictureNr);
					if (pictureNode.getUserObject() instanceof PictureBean) {
						pictures.add((PictureBean) pictureNode.getUserObject());
						System.out.println("album " + albumNode.getUserObject().toString() + " picture " + pictureNode.getUserObject().toString());
					}
				}
				if (album.isEdited()) {
					fileXML.saveAlbum(galleryPayh + File.separatorChar + album.getFolderName(), pictures.toArray(new PictureBean[pictures.size()]));
				}
				albums.add(album);
			}
		}
		fileXML.saveGallery(galleryPayh + File.separatorChar, albums.toArray(new AlbumBean[albums.size()]));

	}
}