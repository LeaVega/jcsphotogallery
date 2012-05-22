/*	
 * 	File    : PicturePanel.java
 * 
 * 	Copyright (C) 2012 Daniel Cioi <dan@dancioi.net>
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
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.tree.DefaultMutableTreeNode;

import net.dancioi.jcsphotogallery.app.controller.JcsPhotoGalleryControllerInterface;
import net.dancioi.jcsphotogallery.client.shared.PictureBean;

/**
 * Panel to edit the picture.
 * 
 * @author Daniel Cioi <dan@dancioi.net>
 * @version $Revision$ Last modified: $Date$, by: $Author$
 */
public class PicturePanel extends JPanel implements UpdateTree {

	private static final long serialVersionUID = 1L;
	private UpdateTree tree;
	private PicturePanelCenter panelCenter;
	private PicturePanelBottom panelBottom;

	public PicturePanel(UpdateTree tree) {
		this.tree = tree;
		initialize();
	}

	private void initialize() {
		this.setLayout(new BorderLayout());
		this.add(addCenterPanel(), BorderLayout.CENTER);
		panelBottom = new PicturePanelBottom(this);
		add(panelBottom, BorderLayout.PAGE_END);

	}

	/**
	 * Center panel where the picture is shown.
	 * 
	 * @return
	 */
	private JPanel addCenterPanel() {
		panelCenter = new PicturePanelCenter();
		return panelCenter;
	}

	public void fillUpParameters(PictureBean pictureBean, BufferedImage picture, DefaultMutableTreeNode treeNode) {
		panelCenter.showPicture(picture);
		panelBottom.setCurrentPictureBean(pictureBean, treeNode);
	}

	public void attachActions(JcsPhotoGalleryControllerInterface controller) {
		panelBottom.attachActions(controller);
	}

	@Override
	public void updateNode(DefaultMutableTreeNode treeNode) {
		tree.updateNode(treeNode);
	}

	public int getImageViewerMinSize() {
		return panelCenter.getMinVisibleDimension();
	}

}
