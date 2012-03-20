/*	
 * 	File    : PanelLeft.java
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

package net.dancioi.jcsphotogallery.app.view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

import net.dancioi.jcsphotogallery.app.model.GalleryRead;

/**
 * This class shows the gallery structure in a tree. All operations are
 * performed on this tree (add/delete/modify albums, pictures).
 * 
 * @author Daniel Cioi <dan@dancioi.net>
 * @version $Revision$ Last modified: $Date: 2012-02-09 23:48:14 +0200
 *          (Thu, 09 Feb 2012) $, by: $Author$
 */

public class PanelLeft extends JPanel implements TreeSelectionListener {

	private static final long serialVersionUID = 1L;
	private GalleryRead importGallery;
	private JTree tree;
	private DefaultMutableTreeNode root;

	/**
	 * Default constructor.
	 * 
	 * @param jcsPhotoGalleryView
	 */
	public PanelLeft(JcsPhotoGalleryView jcsPhotoGalleryView) {

		initialize();
	}

	/**
	 * Initialize.
	 */
	private void initialize() {
		this.setLayout(new BorderLayout());
		this.setMinimumSize(new Dimension(200, 700));
		add(addTree(), BorderLayout.CENTER);
	}

	/**
	 * Adds the gallery tree structure.
	 * 
	 * @return
	 */
	private JScrollPane addTree() {
		root = new DefaultMutableTreeNode("Gallery root");

		tree = new JTree(root);
		tree.setEditable(false);
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.setShowsRootHandles(true);

		tree.addTreeSelectionListener(this);
		JScrollPane scrollPane = new JScrollPane(tree);
		return scrollPane;
	}

	public void addGalleryAlbums(DefaultMutableTreeNode[] treeNodes) {
		for (DefaultMutableTreeNode node : treeNodes)
			root.add(node);
		((DefaultTreeModel) tree.getModel()).reload();
	}

	private void rightClickEventDetected() {

	}

	@Override
	public void valueChanged(TreeSelectionEvent arg0) {

	}

	public JTree getTree() {
		return tree;
	}

}