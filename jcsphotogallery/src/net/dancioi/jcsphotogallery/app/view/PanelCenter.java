/*	
 * 	File    : PanelCenter.java
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
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

/**
 * This class .
 * 
 * @author Daniel Cioi <dan@dancioi.net>
 * @version $Revision$ Last modified: $Date: 2011-12-04 23:04:24 +0200
 *          (Sun, 04 Dec 2011) $, by: $Author$
 */

public class PanelCenter extends JPanel {

	private ImageViewer imageViewer;

	public PanelCenter() {
		initialize();
	}

	private void initialize() {
		setLayout(new BorderLayout());
		add(new PanelTop(), BorderLayout.PAGE_START);
		imageViewer = new ImageViewer();
		add(imageViewer, BorderLayout.CENTER);
		add(new PanelBottom(), BorderLayout.PAGE_END);

	}

	public void showPicture(BufferedImage image) {
		imageViewer.loadImage(image);
	}

	public void resizeEvent() {
		imageViewer.updateSize(this.getWidth(), this.getHeight());

	}

	public int getMinVisibleDimension() {
		return getWidth() > getHeight() ? getWidth() : getHeight();
	}

}