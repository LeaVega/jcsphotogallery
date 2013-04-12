/*	
 * 	File    : JcsPhotoGalleryModel.java
 * 
 * 	Copyright (C) 2011-2012 Daniel Cioi <dan@dancioi.net>
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
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.JProgressBar;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import net.dancioi.jcsphotogallery.app.view.JcsPhotoGalleryViewInterface;
import net.dancioi.jcsphotogallery.client.shared.AlbumBean;
import net.dancioi.jcsphotogallery.client.shared.GalleryAlbums;
import net.dancioi.jcsphotogallery.client.shared.PictureBean;

/**
 * JcsPhotoGallery's Model.
 * 
 * @author Daniel Cioi <dan@dancioi.net>
 * @version $Revision$ Last modified: $Date$, by: $Author$
 */
public class JcsPhotoGalleryModel implements JcsPhotoGalleryModelInterface, DeleteConfirmation {

	private Configs configs;
	private File appGalleryPath;
	private PicturesImporter picturesImport;
	private GalleryAlbums galleryAlbums;
	private GalleryFiles galleryFiles;
	private JcsPhotoGalleryViewInterface view;
	private DefaultMutableTreeNode currentNode;
	private File configsCfgFile;

	public JcsPhotoGalleryModel() {
		initialize();
	}

	private void initialize() {
		configs = getPreviousConfigs();
		picturesImport = new PicturesImporter(configs);
		galleryFiles = new GalleryFiles(this);
	}

	@Override
	public void bindView(JcsPhotoGalleryViewInterface view) {
		this.view = view;
	}

	/**
	 * Method to get the previous configuration. If it's first time when the application run, then create a default configs object.
	 */
	private Configs getPreviousConfigs() {
		configsCfgFile = new File("configs.cfg");
		Configs previousConfigs = getConfigs(configsCfgFile);
		if (previousConfigs == null) {
			File configsIniFile = getConfigsIni(new File("configs.ini")); // added to win. Because in Program Files can't be added a new file "configs.cfg" after setup, the UserAppData is used instead. A configs.ini file with the path to configs.cfg is added at setup.
			if (configsIniFile != null) {
				configsCfgFile = configsIniFile;
				previousConfigs = getConfigs(configsIniFile);
			}
			if (previousConfigs == null) {
				if (configsCfgFile == null) {
					configsCfgFile = new File("configs.cfg");
				}
				previousConfigs = new Configs(new Dimension(1280, 960), -1);
			}
		}

		previousConfigs.setDeleteConfirmation(this);
		return previousConfigs;
	}

	private Configs getConfigs(File configsFile) {
		Configs previousConfigs = null;
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try {
			fis = new FileInputStream(configsFile);
			ois = new ObjectInputStream(fis);
			previousConfigs = (Configs) ois.readObject();
		} catch (FileNotFoundException e) {
			System.out.println("The configs.cfg file is missing. It happens just first time when you run the application");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fis != null)
					fis.close();
				if (ois != null)
					ois.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return previousConfigs;
	}

	private File getConfigsIni(File configsFile) {
		BufferedReader configsIni = null;
		try {
			configsIni = new BufferedReader(new FileReader(configsFile));
			String line = null;
			while ((line = configsIni.readLine()) != null) {
				if (line.startsWith("CONFIGS_PATH")) {
					return new File(line.substring(13));
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("configs.ini could not be found");
		} catch (IOException e) {
			System.out.println("error reading configs.ini");
		} finally {
			try {
				if (configsIni != null)
					configsIni.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * Method to save on configs.cfg file the current settings
	 */
	private void setCurrentSettings() {
		ObjectOutputStream oos = null;
		try {
			FileOutputStream fos = new FileOutputStream(configsCfgFile);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(configs);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (oos != null)
					oos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
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
		if (picture.getFileName().contains("albumThumbnail"))
			return picturesImport.getPicture(picture.getFileName(), maxSize, picture.getRotateDegree());
		return picturesImport.getPicture(getPicturePath(picture), maxSize, picture.getRotateDegree());
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

			return new DefaultMutableTreeNode[] { addPicturesToNewAlbum(null, null) };
		} else {
			return null;
		}
	}

	@Override
	public DefaultMutableTreeNode addPicturesToNewAlbum(File[] selectedFiles, JProgressBar progressBar) {
		AlbumBean newAlbum = new AlbumBean();
		newAlbum.setEdited(true);
		galleryAlbums.setEdited(true);
		newAlbum.setParent(galleryAlbums);
		newAlbum.setImgThumbnail(""); // for new album without any picture
		if (progressBar != null)
			progressBar.setValue(0);

		DefaultMutableTreeNode albumNode = new DefaultMutableTreeNode(newAlbum);

		String folderName = String.valueOf(System.currentTimeMillis());
		newAlbum.setName(folderName);
		newAlbum.setFolderName(folderName);
		File albumFolder = new File(appGalleryPath.getAbsolutePath() + File.separatorChar + folderName);
		newAlbum.setAlbumPath(albumFolder.getAbsolutePath());
		newAlbum.setTags(new String[] { "No Tag" });

		if (albumFolder.mkdir()) {
			addIndexHtml(albumFolder);
			if (null != selectedFiles) {
				float progressBarIncrement = 100 / (float) selectedFiles.length;
				float progressBarValue = 0;
				for (File picturePath : selectedFiles) {
					PictureBean picture = importPicture(newAlbum, albumFolder, picturePath);
					progressBarValue += progressBarIncrement;
					progressBar.setValue(Math.round(progressBarValue)); // TODO for version 1.1.2, show one progress value if more than one task are running concomitantly
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
	public DefaultMutableTreeNode addPicturesToExistingAlbum(File[] selectedFiles, DefaultMutableTreeNode albumNode, JProgressBar progressBar) {
		AlbumBean album = (AlbumBean) albumNode.getUserObject();
		album.setEdited(true);
		float progressBarIncrement = 100 / (float) selectedFiles.length;
		float progressBarValue = 0;
		for (File picturePath : selectedFiles) {
			PictureBean picture = importPicture(album, new File(appGalleryPath + File.separator + album.getFolderName()), picturePath);
			progressBarValue += progressBarIncrement;
			progressBar.setValue(Math.round(progressBarValue));
			if (null != picture) {
				albumNode.add(new DefaultMutableTreeNode(picture));
			}
		}
		return null;
	}

	@Override
	public void saveGalleryChanges(JTree jTree) {
		// select the root node to avoid issue deleting an in use resource.
		DefaultTreeModel treeModel = (DefaultTreeModel) view.getTree().getModel();
		selectNode((DefaultMutableTreeNode) treeModel.getRoot());

		new GalleryWriter(this, jTree);
		saveSettings();
		galleryFiles.executeQueuedDeleteOperations();
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

	public void deletePicturesByFilePath(File[] filePaths) {
		galleryFiles.deleteFiles(filePaths);
	}

	@Override
	public void deleteImage(DefaultMutableTreeNode treeNode) {
		DefaultTreeModel treeModel = (DefaultTreeModel) view.getTree().getModel();
		selectNextNode();
		treeModel.removeNodeFromParent(treeNode);
		PictureBean picture = (PictureBean) treeNode.getUserObject();
		picture.getParent().setEdited(true);
		if (getConfigs().isRemovePictures(false)) {
			deletePicture(picture);
		}
	}

	@Override
	public void deleteAlbum(DefaultMutableTreeNode treeNode) {
		DefaultTreeModel treeModel = (DefaultTreeModel) view.getTree().getModel();
		treeModel.removeNodeFromParent(treeNode);
		AlbumBean albumToDelete = (AlbumBean) treeNode.getUserObject();
		albumToDelete.getParent().setEdited(true);
		if (getConfigs().isRemovePictures(false)) {
			deleteAlbum(albumToDelete);
		}
	}

	private void selectPicture(DefaultMutableTreeNode treeNode) {
		if (treeNode != null && treeNode.getUserObject() instanceof PictureBean) {
			currentNode = treeNode;
			PictureBean pictureBean = (PictureBean) treeNode.getUserObject();
			BufferedImage picture = getPicture(pictureBean, view.getMinPictureViewSize());
			view.showPicture(pictureBean, picture, treeNode);
		}
	}

	private void selectAlbum(DefaultMutableTreeNode treeNode) {
		if (treeNode != null && treeNode.getUserObject() instanceof AlbumBean) {
			AlbumBean album = (AlbumBean) treeNode.getUserObject();
			String thumbnailFileName = album.getImgThumbnail() == null || album.getImgThumbnail().isEmpty() ? "icons/albumThumbnailNotFound.png" : album.getImgThumbnail();
			PictureBean pictureBean = new PictureBean("Album Thumbnail", thumbnailFileName, "the current album's thumbnail", album.getImgThumbnail());
			pictureBean.setParent(album);
			BufferedImage picture = getPicture(pictureBean, 200);
			view.showAlbum(album, picture, treeNode);
		}
	}

	@Override
	public void selectNode(DefaultMutableTreeNode treeNode) {
		if (treeNode.getUserObject() instanceof PictureBean) {
			selectPicture(treeNode);
		} else if (treeNode.getUserObject() instanceof AlbumBean) {
			selectAlbum(treeNode);
		} else if (treeNode.getUserObject() instanceof String) {
			view.showGallery(getGalleryAlbums());
		}
	}

	@Override
	public void selectNextNode() {
		selectPicture(currentNode.getNextNode());
	}

	@Override
	public void selectPreviousNode() {
		selectPicture(currentNode.getPreviousNode());
	}

	@Override
	public void rotatePictureClockwise() {
		if (currentNode.getUserObject() instanceof PictureBean) {
			PictureBean pictureBean = (PictureBean) currentNode.getUserObject();
			pictureBean.setRotateDegree(pictureBean.getRotateDegree() + 90);
			pictureBean.getParent().setEdited(true);
			selectPicture(currentNode);
		}

	}

	@Override
	public void rotatePictureCounterClockwise() {
		if (currentNode.getUserObject() instanceof PictureBean) {
			PictureBean pictureBean = (PictureBean) currentNode.getUserObject();
			pictureBean.setRotateDegree(pictureBean.getRotateDegree() - 90);
			pictureBean.getParent().setEdited(true);
			selectPicture(currentNode);
		}

	}

	@Override
	public boolean confirmDeleteFiles() {
		return view.askForDeleteConfirmation();
	}

}
