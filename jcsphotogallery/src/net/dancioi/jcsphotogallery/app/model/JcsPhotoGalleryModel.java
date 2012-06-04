/*	
 * 	File    : JcsPhotoGalleryModel.java
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

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import net.dancioi.jcsphotogallery.client.shared.AlbumBean;
import net.dancioi.jcsphotogallery.client.shared.GalleryAlbums;
import net.dancioi.jcsphotogallery.client.shared.PictureBean;

/**
 * JcsPhotoGallery's Model.
 * 
 * @author Daniel Cioi <dan@dancioi.net>
 * @version $Revision$ Last modified: $Date$, by: $Author$
 */
public class JcsPhotoGalleryModel implements JcsPhotoGalleryModelInterface {

	private Configs configs;
	private File appGalleryPath;
	private PicturesImporter picturesImport;
	private GalleryAlbums galleryAlbums;
	private GalleryFiles galleryFiles;

	public JcsPhotoGalleryModel() {
		initialize();
	}

	private void initialize() {
		configs = getPreviousConfigs();
		picturesImport = new PicturesImporter(configs);
		galleryFiles = new GalleryFiles(this);
	}

	/**
	 * Method to get the previous configuration. If it's first time when the application run, then create a default configs object.
	 */
	private Configs getPreviousConfigs() {
		Configs previousConfigs = null;
		try {
			FileInputStream fis = new FileInputStream(new File("configs.cfg"));
			ObjectInputStream ois = new ObjectInputStream(fis);
			previousConfigs = (Configs) ois.readObject();
		} catch (FileNotFoundException e) {
			System.out.println("The configs.cfg file is missing. It happens just first time when you run the application");
			previousConfigs = new Configs(new Dimension(1200, 900), false);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return previousConfigs;
	}

	/**
	 * Method to save on configs.cfg file the current settings
	 */
	private void setCurrentSettings() {
		try {
			FileOutputStream fos = new FileOutputStream(new File("configs.cfg"));
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(configs);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Method to save the application's settings.
	 */
	public void saveSettings() {
		setCurrentSettings();
	}

	/**
	 * Method to get the current configs (settings).
	 * 
	 * @return
	 */
	@Override
	public Configs getConfigs() {
		return configs;
	}

	/**
	 * Method to modify application's configs (settings).
	 * 
	 * @param configs
	 */
	public void setConfigs(Configs configs) {
		this.configs = configs;
	}

	@Override
	public DefaultMutableTreeNode[] loadGallery(File galleryPath) {
		appGalleryPath = galleryPath;
		ArrayList<DefaultMutableTreeNode> root = new ArrayList<DefaultMutableTreeNode>();
		galleryAlbums = new GalleryReader().getAlbums(new File(appGalleryPath.getAbsoluteFile() + File.separator + "albums.xml"));
		AlbumBean[] allAlbums = galleryAlbums.getAllAlbums();

		for (AlbumBean album : allAlbums) {
			DefaultMutableTreeNode albumNode = new DefaultMutableTreeNode(album);
			root.add(albumNode);
			for (PictureBean picture : album.getPictures()) {
				picture.setParent(album);
				albumNode.add(new DefaultMutableTreeNode(picture));
			}
		}

		configs.setGalleryPath(galleryPath);

		return (DefaultMutableTreeNode[]) root.toArray(new DefaultMutableTreeNode[root.size()]);
	}

	@Override
	public BufferedImage getPicture(PictureBean picture, int maxSize) {
		return picturesImport.getPicture(getPicturePath(picture), maxSize);
	}

	public DefaultMutableTreeNode[] createNewGallery(File galleryPath) {
		File galleryFolder = new File(galleryPath.getAbsolutePath() + File.separator + "gallery");
		if (galleryFolder.mkdir()) {
			appGalleryPath = galleryFolder;
			galleryAlbums = new GalleryAlbums();
			galleryAlbums.setGalleryName("Change this");
			galleryAlbums.setGalleryHomePage("http://www.changeThis.com");
			galleryAlbums.setEdited(true);

			configs.setGalleryPath(galleryFolder);

			return new DefaultMutableTreeNode[] { addPicturesToNewAlbum(null) };
		} else {
			return null;
		}
	}

	@Override
	public DefaultMutableTreeNode addPicturesToNewAlbum(File[] selectedFiles) {
		AlbumBean newAlbum = new AlbumBean();
		newAlbum.setEdited(true);
		galleryAlbums.setEdited(true);

		DefaultMutableTreeNode albumNode = new DefaultMutableTreeNode(newAlbum);

		String folderName = String.valueOf(System.currentTimeMillis());
		newAlbum.setName(folderName);
		newAlbum.setFolderName(folderName);
		File albumFolder = new File(appGalleryPath.getAbsolutePath() + File.separatorChar + folderName);
		newAlbum.setAlbumPath(albumFolder.getAbsolutePath());

		if (albumFolder.mkdir()) {
			addIndexHtml(albumFolder);
			if (null != selectedFiles) {
				for (File picturePath : selectedFiles) {
					PictureBean picture = importPicture(newAlbum, albumFolder, picturePath);
					if (null != picture) {
						albumNode.add(new DefaultMutableTreeNode(picture));
						newAlbum.setImgThumbnail(picture.getImgThumbnail());
					}
				}
			}
		}
		return albumNode;
	}

	/*
	 * adds an index.html file to avoid listing all files from this folder. It will redirect to gallery application.
	 */
	private void addIndexHtml(File albumFolder) {
		File indexHtml = new File(albumFolder.getAbsolutePath() + File.separator + "index.html");
		String s = "<!doctype html><html><head><meta http-equiv=\"Refresh\" content=\"0; url=../../\" /></head></html>";
		try {
			PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(indexHtml)));
			writer.write(s, 0, s.length());
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private PictureBean importPicture(AlbumBean album, File destinationFolder, File origPicture) {
		PictureBean picture = picturesImport.addPicture(origPicture, destinationFolder);
		picture.setParent(album);
		return picture;
	}

	private String getPicturePath(PictureBean pictureBean) {
		return appGalleryPath + File.separator + pictureBean.getParent().getFolderName() + File.separator + pictureBean.getFileName();
	}

	@Override
	public DefaultMutableTreeNode addPicturesToExistingAlbum(File[] selectedFiles, DefaultMutableTreeNode albumNode) {
		AlbumBean album = (AlbumBean) albumNode.getUserObject();
		album.setEdited(true);
		for (File picturePath : selectedFiles) {
			PictureBean picture = importPicture(album, new File(appGalleryPath + File.separator + album.getFolderName()), picturePath);
			if (null != picture) {
				albumNode.add(new DefaultMutableTreeNode(picture));
			}
		}
		return null;
	}

	@Override
	public void saveGalleryChanges(JTree jTree) {
		new GalleryWriter(getGalleryAlbums(), jTree, appGalleryPath.getAbsolutePath());
		saveSettings();
	}

	@Override
	public GalleryAlbums getGalleryAlbums() {
		return galleryAlbums;
	}

	@Override
	public File getAppGalleryPath() {
		return appGalleryPath;
	}

	@Override
	public boolean isGallerySaved(JTree jTree) {
		boolean result = true;

		DefaultTreeModel treeNode = (DefaultTreeModel) jTree.getModel();
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) treeNode.getRoot();

		if (galleryAlbums != null && galleryAlbums.isEdited())
			return false;

		for (int albumsNr = 0; albumsNr < root.getChildCount(); albumsNr++) {
			DefaultMutableTreeNode albumNode = (DefaultMutableTreeNode) treeNode.getChild(root, albumsNr);
			if (albumNode.getUserObject() instanceof AlbumBean) {
				AlbumBean album = (AlbumBean) albumNode.getUserObject();
				if (album.isEdited())
					return false;
			}
		}
		return result;
	}

	@Override
	public void deleteAlbum(AlbumBean albumToDelete) {
		galleryFiles.deleteAlbum(albumToDelete);
	}

	@Override
	public void deletePicture(PictureBean picture) {
		galleryFiles.deletePicture(picture);
	}

	@Override
	public void copyPicture(PictureBean picture, AlbumBean albumSource, AlbumBean albumDestination) {
		galleryFiles.copyPicture(picture, albumSource, albumDestination);
	}

}
