/*	
 * 	File    : JcsPhotoGalleryView.java
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
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import net.dancioi.jcsphotogallery.app.model.JcsPhotoGalleryModelInterface;

/**
 * JcsPhotoGallery's View
 * 
 * @author Daniel Cioi <dan@dancioi.net>
 * @version $Revision$ Last modified: $Date: 2011-12-20 22:40:52 +0200
 *          (Tue, 20 Dec 2011) $, by: $Author$
 */

public class JcsPhotoGalleryView extends JFrame implements JcsPhotoGalleryViewInterface {

	private static final long serialVersionUID = 1L;
	private JcsPhotoGalleryModelInterface model;
	private PanelLeft panelLeft;
	private PanelTop panelTop;
	private PanelBottom panelBottom;
	private PanelCenter panelCenter;

	/**
	 * Default constructor.
	 */
	public JcsPhotoGalleryView(JcsPhotoGalleryModelInterface model) {
		super("JcsPhotoGallery");
		this.model = model;
		initialize();
	}

	/**
	 * Initialize.
	 */
	private void initialize() {
		this.setSize(1200, 700);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setContentPane(getMainPanel());
		this.setVisible(true);

		this.getRootPane().addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				frameResizeEvent();
			}

		});
	}

	private void frameResizeEvent() {
		panelCenter.resizeEvent();

	}

	private void getDSim() {
		System.out.println("  size: width=" + this.getWidth() + "  height=" + this.getHeight());
	}

	/**
	 * Method to get the JFrame's ContentPane.
	 * 
	 * @return JPanel
	 */
	private JSplitPane getMainPanel() {
		JPanel rightPanel = new JPanel();
		rightPanel.setMinimumSize(new Dimension(700, 700));
		rightPanel.add(addTopPanel(), BorderLayout.NORTH);
		rightPanel.add(addBottomPanel(), BorderLayout.SOUTH);
		rightPanel.add(addCenterPanel(), BorderLayout.CENTER);

		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, addLeftPanel(), rightPanel);
		splitPane.setOneTouchExpandable(false);
		splitPane.setDividerLocation(250);

		return splitPane;
	}

	/**
	 * Center panel where the picture is shown.
	 * 
	 * @return
	 */
	private JPanel addCenterPanel() {
		panelCenter = new PanelCenter();
		return panelCenter;
	}

	@Override
	public void showPicture(String picturePath) {
		panelCenter.showPicture(model.getPicture(picturePath, panelCenter.getMinVisibleDimension()));
	}

	/**
	 * Left panel with the gallery tree structure.
	 * 
	 * @return JPanel
	 */
	private JPanel addLeftPanel() {
		panelLeft = new PanelLeft(this);
		return panelLeft;
	}

	/**
	 * Top panel.
	 * 
	 * @return JPanel
	 */
	private JPanel addTopPanel() {
		panelTop = new PanelTop();
		return panelTop;
	}

	/**
	 * Bottom Panel.
	 * 
	 * @return JPanel
	 */
	private JPanel addBottomPanel() {
		panelBottom = new PanelBottom();
		return panelBottom;
	}

	public List getUpdatedGallery() {
		return null;
	}

	@Override
	public void addMenuBar(JMenuBar menuBar) {
		this.setJMenuBar(menuBar);
	}

	@Override
	public void populateTree(DefaultMutableTreeNode[] treeNodes) {
		panelLeft.addGalleryAlbums(treeNodes);
	}

	@Override
	public JTree getTree() {
		return panelLeft.getTree();
	}

}