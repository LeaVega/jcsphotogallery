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

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

	public JcsPhotoGalleryModel() {
		initialize();
	}

	private void initialize() {
		getPreviousConfigs();
		picturesImport = new PicturesImporter();
	}

	/**
	 * Method to get the previous configuration. If it's first time when the application run, then create a default configs object.
	 */
	private void getPreviousConfigs() {
		try {
			FileInputStream fis = new FileInputStream(new File("configs.cfg"));
			ObjectInputStream ois = new ObjectInputStream(fis);
			configs = (Configs) ois.readObject();
		} catch (FileNotFoundException e) {
			System.out.println("The configs.cfg file is missing. It happens just first time when you run the application");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		if (configs == null)
			configs = new Configs();
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
		appGalleryPath = galleryPath;
		galleryAlbums = new GalleryAlbums();
		galleryAlbums.setGalleryName("Change this");
		galleryAlbums.setGalleryHomePage("http://www.changeThis.com");
		galleryAlbums.setEdited(true);

		configs.setGalleryPath(galleryPath);

		return new DefaultMutableTreeNode[] { addPicturesToNewAlbum(null) };
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

}
