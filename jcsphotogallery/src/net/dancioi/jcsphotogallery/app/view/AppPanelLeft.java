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
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import javax.swing.DropMode;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.TransferHandler;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import net.dancioi.jcsphotogallery.client.shared.AlbumBean;
import net.dancioi.jcsphotogallery.client.shared.PictureBean;

/**
 * This class shows the gallery structure in a tree. All operations are performed on this tree (add/delete/modify albums, pictures).
 * 
 * @author Daniel Cioi <dan@dancioi.net>
 * @version $Revision$ Last modified: $Date$, by: $Author$
 */

public class AppPanelLeft extends JPanel implements TreeSelectionListener {
	// TODO run the import pictures in a separate thread to allow use the application while import
	// TODO function to rotate the imported pictures clockwise and counterclockwise
	// TODO show the position in tree when use the next and previous buttons
	// TODO add a list with used tags

	private static final long serialVersionUID = 1L;
	protected static final Object AlbumBean = null;
	private JcsPhotoGalleryView view; // TODO later
	private JTree tree;
	private DefaultMutableTreeNode root;

	/**
	 * Default constructor.
	 * 
	 * @param jcsPhotoGalleryView
	 */
	public AppPanelLeft(JcsPhotoGalleryView jcsPhotoGalleryView) {
		this.view = jcsPhotoGalleryView;
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
		// tree.setEditable(false);
		tree.setDragEnabled(true);
		tree.setDropMode(DropMode.INSERT);
		tree.setTransferHandler(new TransferHandler() {

			private static final long serialVersionUID = 1L;
			DefaultTreeModel treeModel = (DefaultTreeModel) tree.getModel();
			private DataFlavor customDataFlavor = new DataFlavor(TransferableNode.class, "Transferable JTree node");
			private DefaultMutableTreeNode exportedNode = null;
			private boolean importedData = false;

			@Override
			public int getSourceActions(JComponent c) {
				return MOVE;
			}

			@Override
			public boolean canImport(TransferSupport support) {
				JTree.DropLocation dropLocation = (JTree.DropLocation) support.getDropLocation();
				int childIndex = dropLocation.getChildIndex();
				Object lastPathComponent = dropLocation.getPath().getLastPathComponent();
				if (childIndex == -1)
					return false;

				DefaultMutableTreeNode child = (DefaultMutableTreeNode) treeModel.getChild(lastPathComponent, childIndex);
				if (child == null || exportedNode == null) {
					exportedNode = null;
					return false;
				}

				if (child.equals(exportedNode))
					return false;

				if (child.getUserObject() instanceof AlbumBean && exportedNode.getUserObject() instanceof AlbumBean)
					return true;
				else if (child.getUserObject() instanceof PictureBean && exportedNode.getUserObject() instanceof PictureBean)
					return true;
				else
					return false;
			}

			@Override
			public boolean importData(TransferSupport support) {
				JTree.DropLocation dropLocation = (JTree.DropLocation) support.getDropLocation();
				TreePath path = dropLocation.getPath();
				Transferable transferable = support.getTransferable();
				DefaultMutableTreeNode transferData;

				try {
					transferData = (DefaultMutableTreeNode) transferable.getTransferData(customDataFlavor);
				} catch (IOException e) {
					return false;
				} catch (UnsupportedFlavorException e) {
					return false;
				}

				int childIndex = dropLocation.getChildIndex();
				if (childIndex == -1) {
					childIndex = treeModel.getChildCount(path.getLastPathComponent());
				}

				DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(transferData.getUserObject());
				DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) path.getLastPathComponent();

				if (newNode.getUserObject() instanceof AlbumBean) {
					treeModel.insertNodeInto(newNode, parentNode, childIndex);

					for (int pictureNr = 0; pictureNr < transferData.getChildCount(); pictureNr++) {
						DefaultMutableTreeNode pictureNode = (DefaultMutableTreeNode) transferData.getChildAt(pictureNr);
						if (pictureNode.getUserObject() instanceof PictureBean) {
							treeModel.insertNodeInto(new DefaultMutableTreeNode(pictureNode.getUserObject()), newNode, pictureNr);
						}
					}

				} else
					treeModel.insertNodeInto(newNode, parentNode, childIndex);
				// TODO also copy the files

				TreePath newPath = path.pathByAddingChild(newNode);
				tree.makeVisible(newPath);
				tree.scrollRectToVisible(tree.getPathBounds(newPath));

				importedData = true;
				return true;
			}

			@Override
			protected Transferable createTransferable(JComponent c) {
				DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) ((JTree) c).getLastSelectedPathComponent();
				exportedNode = treeNode;
				return new TransferableNode(treeNode, customDataFlavor);
			}

			@Override
			protected void exportDone(JComponent source, Transferable data, int action) {
				if (exportedNode != null && importedData) {
					treeModel.removeNodeFromParent(exportedNode);
					// TODO also remove the files
					importedData = false;
					exportedNode = null;
				}
			}

		});

		tree.setLargeModel(true);
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.setShowsRootHandles(true);

		tree.addTreeSelectionListener(this);
		JScrollPane scrollPane = new JScrollPane(tree);
		return scrollPane;
	}

	class TransferableNode implements Transferable {
		private DefaultMutableTreeNode treeNode;
		private DataFlavor dataFlavor;

		public TransferableNode(DefaultMutableTreeNode treeNode, DataFlavor dataFlavor) {
			this.treeNode = treeNode;
			this.dataFlavor = dataFlavor;
		}

		@Override
		public DataFlavor[] getTransferDataFlavors() {
			return new DataFlavor[] { dataFlavor };
		}

		@Override
		public boolean isDataFlavorSupported(DataFlavor flavor) {
			return flavor == dataFlavor;
		}

		@Override
		public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
			return treeNode;
		}

	}

	/**
	 * Attach gallery albums from import gallery action.
	 * 
	 * @param treeNodes
	 */
	public void addGalleryAlbums(DefaultMutableTreeNode[] treeNodes) {
		root.removeAllChildren();// remove previous gallery.
		for (DefaultMutableTreeNode node : treeNodes)
			root.add(node);
		refreshTree();
	}

	private void refreshTree() {
		((DefaultTreeModel) tree.getModel()).reload();
	}

	/**
	 * adds a new album to gallery.
	 * 
	 * @param node
	 */
	public void addNewAlbum(DefaultMutableTreeNode node) {
		root.add(node);
	}

	@Override
	public void valueChanged(TreeSelectionEvent arg0) {

	}

	public JTree getTree() {
		return tree;
	}

	public void addAlbumToGallery(DefaultMutableTreeNode newAlbum) {
		root.add(newAlbum);
		refreshTree();
	}

	public void addPicturesToAnExistingAlbum() {
		refreshTree();
	}

}
