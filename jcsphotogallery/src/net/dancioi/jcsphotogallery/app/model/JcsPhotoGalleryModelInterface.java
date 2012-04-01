package net.dancioi.jcsphotogallery.app.model;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import net.dancioi.jcsphotogallery.client.model.PictureBean;

public interface JcsPhotoGalleryModelInterface {

	DefaultMutableTreeNode[] loadGallery(File galleryDefinition);

	BufferedImage getPicture(String picturePath, int maxSize);

	DefaultMutableTreeNode addPicturesToNewAlbum(File[] selectedFiles);

	String getPicturePath(PictureBean pictureBean);

	DefaultMutableTreeNode addPicturesToExistingAlbum(File[] selectedFiles, DefaultMutableTreeNode treeNode);

	void saveGalleryChanges(JTree jTree);
}
