package net.dancioi.jcsphotogallery.app.model;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.tree.DefaultMutableTreeNode;

public interface JcsPhotoGalleryModelInterface {

	void setGalleryPath(File galleryPath);

	File getGalleryPath();

	DefaultMutableTreeNode[] getTreeNodes();

	BufferedImage getPicture(String picturePath, int maxSize);
}
