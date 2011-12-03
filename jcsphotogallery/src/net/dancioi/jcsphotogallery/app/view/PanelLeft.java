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

import java.awt.Dimension;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import net.dancioi.jcsphotogallery.app.model.GalleryRead;
import net.dancioi.jcsphotogallery.client.Albums;

/**
 * This class shows the gallery structure in a tree.
 * All operations are performed on this tree (add/delete/modify albums, pictures).
 *  
 * @author Daniel Cioi <dan@dancioi.net>
 * @version $Revision$  Last modified: $Date$, by: $Author$
 */

public class PanelLeft extends JPanel implements TreeSelectionListener{

	private static final long serialVersionUID = 1L;
	private GalleryRead  importGallery;
	private JTree tree;
	private int xSize = 250;

	/**
	 * Default constructor.
	 */
	public PanelLeft(){
		initialize();
	}

	/**
	 * Initialize.
	 */
	private void initialize() {
		this.setSize(xSize,700);
		add(addTree());
	}

	/**
	 * Adds the gallery tree structure.
	 * @return
	 */
	private JScrollPane addTree() {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Gallery root");
		tree = new JTree(root);
		tree.addTreeSelectionListener(this);
		JScrollPane scrollPane =  new JScrollPane(tree);
		scrollPane.setPreferredSize(new Dimension(xSize, this.getHeight()));
		return scrollPane;
	}
	
	
	public void addGalleryAlbums(Albums albums){
		
	}

	private void rightClickEventDetected() {
	}

	@Override
	public void valueChanged(TreeSelectionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}