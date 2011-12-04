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
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import net.dancioi.jcsphotogallery.app.model.JcsPhotoGalleryModelInterface;
import net.dancioi.jcsphotogallery.client.model.Albums;

/**
 * JcsPhotoGallery's View
 *  
 * @author Daniel Cioi <dan@dancioi.net>
 * @version $Revision$  Last modified: $Date$, by: $Author$
 */

public class JcsPhotoGalleryView extends JFrame implements JcsPhotoGalleryViewInterface{

	private static final long serialVersionUID = 1L;
	private JcsPhotoGalleryModelInterface model;
	private PanelLeft  panelLeft;
	private PanelTop  panelTop;
	private PanelBottom  panelBottom;
	private PanelCenter  panelCenter;
	

	/**
	 * Default constructor.
	 */
	public JcsPhotoGalleryView(JcsPhotoGalleryModelInterface model){
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
		this.setContentPane(getRootPanel());
		this.setVisible(true);
				
	}
	
	/**
	 * Method to get the JFrame's ContentPane.
	 * @return JPanel
	 */
	private JPanel getRootPanel(){
		JPanel rootPanel = new JPanel();
		rootPanel.setLayout(new BorderLayout());
		rootPanel.add(addLeftPanel(), BorderLayout.WEST);
		
		JPanel rightPanel = new JPanel();
		rightPanel.add(addTopPanel(), BorderLayout.NORTH);
		rightPanel.add(addBottomPanel(), BorderLayout.SOUTH);
		rightPanel.add(addCenterPanel(), BorderLayout.CENTER);
		
		rootPanel.add(rightPanel);

		return rootPanel;
	}


	/**
	 * Center panel where the picture is shown.
	 * @return
	 */
	private JPanel addCenterPanel() {
		panelCenter = new PanelCenter();
		return panelCenter;
	}
	
	/**
	 * Left panel with the gallery tree structure.
	 * @return JPanel
	 */
	private JPanel addLeftPanel() {
		panelLeft = new PanelLeft();
		return panelLeft;
	}

	/**
	 * Top panel.
	 * @return JPanel
	 */
	private JPanel addTopPanel() {
		panelTop = new PanelTop();
		return panelTop;
	}

	/**
	 * Bottom Panel.
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
	public void populateTree(Albums albums) {
		panelLeft.addGalleryAlbums(albums);
	}

}