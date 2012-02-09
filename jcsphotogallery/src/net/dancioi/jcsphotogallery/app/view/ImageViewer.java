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

import javax.media.jai.JAI;
import javax.swing.JPanel;

/**
 * Shows the picture.
 * 
 * @author Daniel Cioi <dan@dancioi.net>
 * @version $Revision: 34 $ Last modified: $Date: 2011-12-04 23:04:24 +0200
 *          (Sun, 04 Dec 2011) $, by: $Author: dan.cioi@gmail.com $
 */

public class ImageViewer extends JPanel {
	private BufferedImage centerImage;

	public ImageViewer() {
		initialize();
	}

	private void initialize() {
		setPreferredSize(new Dimension(1024, 860));
	}

	public void loadImage(String imagePath) {
		centerImage = JAI.create("fileload", imagePath).getAsBufferedImage();
		repaint();
	}

	public void paint(Graphics g) {
		if (centerImage != null)
			g.drawImage(centerImage, 0, 0, this);
	}
}
