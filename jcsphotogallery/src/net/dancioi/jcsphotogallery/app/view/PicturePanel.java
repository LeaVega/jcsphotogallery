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

import net.dancioi.jcsphotogallery.app.controller.JcsPhotoGalleryControllerInterface;
import net.dancioi.jcsphotogallery.client.shared.PictureBean;

/**
 * Panel to edit the picture.
 * 
 * @author Daniel Cioi <dan@dancioi.net>
 * @version $Revision: 39 $ Last modified: $Date: 2012-03-20 22:39:16 +0200
 *          (Tue, 20 Mar 2012) $, by: $Author: dan.cioi $
 */
public class PicturePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private PicturePanelCenter panelCenter;
	private PicturePanelBottom panelBottom;

	public PicturePanel() {

		initialize();
	}

	private void initialize() {
		this.setLayout(new BorderLayout());
		this.add(addCenterPanel(), BorderLayout.CENTER);
		panelBottom = new PicturePanelBottom();
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

	public void fillUpParameters(PictureBean pictureBean, BufferedImage picture) {
		panelCenter.showPicture(picture);
		panelBottom.setCurrentPictureBean(pictureBean);
	}

	public void attachActions(JcsPhotoGalleryControllerInterface controller) {
		panelBottom.attachActions(controller);
	}

}
