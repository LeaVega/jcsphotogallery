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

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.media.jai.PlanarImage;
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
import net.dancioi.jcsphotogallery.client.shared.JcsPhotoGalleryConstants;
import net.dancioi.jcsphotogallery.client.shared.PictureBean;

import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * This class traverse the JTree and write the modified albums xml files..
 * 
 * @author Daniel Cioi <dan@dancioi.net>
 * @version $Revision$ Last modified: $Date$, by: $Author$
 */

public class GalleryWriter extends ElementXML {

	private GalleryAlbums gallery;
	private JTree jTree;
	private String galleryPath;
	private GalleryIO galleryIO;
	private JcsPhotoGalleryModel model;

	public GalleryWriter(JcsPhotoGalleryModel model, JTree jTree) {
		this.model = model;
		this.gallery = model.getGalleryAlbums();
		this.jTree = jTree;
		this.galleryPath = model.getAppGalleryPath().getAbsolutePath();
		galleryIO = new GalleryIO();
		saveChanges();
	}

	private void saveChanges() {
		DefaultTreeModel treeNode = (DefaultTreeModel) jTree.getModel();
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) treeNode.getRoot();

		ArrayList<File> filesToBeDeleted = new ArrayList<File>();
		ArrayList<AlbumBean> albums = new ArrayList<AlbumBean>();

		for (int albumsNr = 0; albumsNr < root.getChildCount(); albumsNr++) {
			DefaultMutableTreeNode albumNode = (DefaultMutableTreeNode) treeNode.getChild(root, albumsNr);
			if (albumNode.getUserObject() instanceof AlbumBean) {
				AlbumBean album = (AlbumBean) albumNode.getUserObject();

				ArrayList<PictureBean> pictures = new ArrayList<PictureBean>();
				for (int pictureNr = 0; pictureNr < albumNode.getChildCount(); pictureNr++) {
					DefaultMutableTreeNode pictureNode = (DefaultMutableTreeNode) albumNode.getChildAt(pictureNr);
					if (pictureNode.getUserObject() instanceof PictureBean) {
						pictures.add((PictureBean) pictureNode.getUserObject());
					}
				}
				if (album.isEdited()) {
					System.out.println("Saved album: " + album.getName());
					saveAlbum(galleryPath + File.separator + album.getFolderName(), pictures.toArray(new PictureBean[pictures.size()]), filesToBeDeleted);
					if (album.getImgThumbnail().isEmpty() && pictures.size() > 0)
						album.setImgThumbnail(pictures.get(0).getImgThumbnail());
					album.setEdited(false);
				}
				albums.add(album);
			}
		}
		if (gallery.isEdited()) {
			System.out.println("Saved gallery albums.xml");
			gallery.setAlbums(albums.toArray(new AlbumBean[albums.size()]));
			saveGallery(galleryPath + File.separator, gallery);
			gallery.setEdited(false);
		}

		File[] files = filesToBeDeleted.toArray(new File[filesToBeDeleted.size()]);
		model.deletePicturesByFilePath(files);
	}

	private void saveAlbum(String albumFolder, PictureBean[] pictures, ArrayList<File> filesToBeDeleted) {
		saveRotatedPictures(pictures, filesToBeDeleted);

		Document doc;
		try {
			doc = getDocument();
			getAlbumPicturesElements(doc, pictures);
			writeFileXML(albumFolder + File.separator + "album.xml", doc);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}

	}

	private void saveRotatedPictures(PictureBean[] pictures, ArrayList<File> filesToBeDeleted) {
		for (PictureBean picture : pictures) {
			if (picture.getRotateDegree() % 360 != 0) {
				String picturePath = galleryPath + File.separator + picture.getParent().getFolderName() + File.separator;
				filesToBeDeleted.add(new File(picturePath + picture.getFileName()));
				filesToBeDeleted.add(new File(picturePath + picture.getImgThumbnail()));

				if (picture.getParent().getImgThumbnail() != null && picture.getParent().getImgThumbnail().equals(picture.getImgThumbnail()))
					picture.getParent().setImgThumbnail("TR" + picture.getFileName());

				PlanarImage loadedPicture = galleryIO.getLoadedPicture(picturePath + picture.getFileName());
				BufferedImage rotatedPicture = galleryIO.getRotatedPicture(loadedPicture.getAsBufferedImage(), picture.getRotateDegree());
				picture.setFileName("R" + picture.getFileName());
				galleryIO.writePicture(rotatedPicture, picturePath + picture.getFileName());

				PlanarImage loadedPictureThumbnail = galleryIO.getLoadedPicture(picturePath + picture.getImgThumbnail());
				BufferedImage rotatedPictureThumbnail = galleryIO.getRotatedPicture(loadedPictureThumbnail.getAsBufferedImage(), picture.getRotateDegree());
				picture.setImgThumbnail("T" + picture.getFileName());
				galleryIO.writePicture(rotatedPictureThumbnail, picturePath + picture.getImgThumbnail());

				picture.setRotateDegree(0); // reset the rotation for the new one.
			}
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

		Element noCache = doc.createElement("meta"); // the browser should not cache the xml files.
		noCache.setAttribute("http-equiv", "Cache-control");
		noCache.setAttribute("content", "no-cache");
		root.appendChild(noCache);

		Element version = doc.createElement("version");
		version.appendChild(doc.createTextNode(JcsPhotoGalleryConstants.APP_VERSION));
		root.appendChild(version);

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