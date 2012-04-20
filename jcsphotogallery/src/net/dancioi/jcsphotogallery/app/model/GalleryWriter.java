/*	
 * 	File    : GalleryWriter.java
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
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import net.dancioi.jcsphotogallery.client.shared.AlbumBean;
import net.dancioi.jcsphotogallery.client.shared.GalleryAlbums;
import net.dancioi.jcsphotogallery.client.shared.PictureBean;

import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * This class traverse the JTree and write the modified albums xml files..
 * 
 * @author Daniel Cioi <dan@dancioi.net>
 * @version $Revision$ Last modified: $Date: 2012-03-20 22:39:16 +0200
 *          (Tue, 20 Mar 2012) $, by: $Author$
 */

public class GalleryWriter extends ElementXML {

	private GalleryAlbums gallery;
	private JTree jTree;
	private String galleryPath;

	public GalleryWriter(GalleryAlbums gallery, JTree jTree, String galleryPath) {
		this.gallery = gallery;
		this.jTree = jTree;
		this.galleryPath = galleryPath;
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
					saveAlbum(galleryPath + File.separatorChar + album.getFolderName(), pictures.toArray(new PictureBean[pictures.size()]));
					album.setEdited(false);
				}
				albums.add(album);
			}
		}
		if (gallery.isEdited()) {
			gallery.setAlbums(albums.toArray(new AlbumBean[albums.size()]));
			saveGallery(galleryPath + File.separatorChar, gallery);
			gallery.setEdited(false);
		}
	}

	public void saveAlbum(String albumFolder, PictureBean[] pictures) {
		Document doc;
		try {
			doc = getDocument();
			getAlbumPicturesElements(doc, pictures);
			writeFileXML(albumFolder + File.separatorChar + "album.xml", doc);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}

	public void saveGallery(String galleryPayh, GalleryAlbums gallery) {
		Document doc;
		try {
			doc = getDocument();
			getAlbumsElements(doc, gallery);
			writeFileXML(galleryPayh + "albums.xml", doc);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}

	private Document getDocument() throws ParserConfigurationException {
		DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document doc = docBuilder.newDocument();

		Element root = doc.createElement("root");
		doc.appendChild(root);

		Comment comment = doc.createComment("jcsPhotoGallery");
		root.appendChild(comment);

		return doc;
	}

	private void writeFileXML(String filePath, Document doc) {

		try {
			TransformerFactory transFactory = TransformerFactory.newInstance();
			Transformer aTransformer = transFactory.newTransformer();

			Source src = new DOMSource(doc);
			Result dest = new StreamResult(filePath);
			aTransformer.transform(src, dest);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}