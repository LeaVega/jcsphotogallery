package net.dancioi.jcsphotogallery.app.view;

import javax.swing.JMenuBar;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

public interface JcsPhotoGalleryViewInterface {

	void addMenuBar(JMenuBar menuBar);

	void populateTree(DefaultMutableTreeNode[] defaultMutableTreeNodes);

	JTree getTree();

	void showPicture(String picturePath);

	void addToTreeNewAlbum(DefaultMutableTreeNode newAlbum);

	void addToTreePicturesToExistingAlbum(DefaultMutableTreeNode addPicturesToExistingAlbum);

}
