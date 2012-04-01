/*	
 * 	File    : PanelCenter.java
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

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

/**
 * Shows the picture.
 * 
 * @author Daniel Cioi <dan@dancioi.net>
 * @version $Revision: 34 $ Last modified: $Date: 2011-12-04 23:04:24 +0200
 *          (Sun, 04 Dec 2011) $, by: $Author: dan.cioi@gmail.com $
 */

public class ImageViewer extends JPanel {

	private static final long serialVersionUID = 1L;

	private BufferedImage centerImage;

	private int width = 600;
	private int height = 600;

	public ImageViewer() {
		initialize();
	}

	private void initialize() {
		setPreferredSize(new Dimension(width, height));
	}

	public void loadImage(BufferedImage image) {
		centerImage = image;
		repaint();
	}

	public void paint(Graphics g) {
		if (centerImage != null) {
			g.setColor(getBackground());
			g.fillRect(0, 0, getWidth(), getHeight());
			g.drawImage(centerImage, (getWidth() - centerImage.getWidth()) / 2, (getHeight() - centerImage.getHeight()) / 2, this);
		}
	}

	public void updateSize(int width, int height) {
		this.width = width;
		this.height = height;
		setPreferredSize(new Dimension(width, height));
		repaint();
	}
}
