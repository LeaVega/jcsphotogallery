/*	
 * 	File    : InfoPanel.java
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

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * The Application's info panel.
 * 
 * @author Daniel Cioi <dan@dancioi.net>
 * @version $Revision$ Last modified: $Date$, by: $Author$
 */
public class InfoPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private static JLabel infoLabel;
	public static Color RED = Color.red;
	public static Color BLACK = Color.black;
	public static Color BLUE = Color.blue;

	private String GALLERY_INFO = "Import a gallery from File > Import or create a new one from File > New.";

	public InfoPanel() {
		initialize();
	}

	private void initialize() {
		infoLabel = new JLabel("Info: " + GALLERY_INFO);
		infoLabel.setForeground(Color.BLUE);
		infoLabel.setPreferredSize(new Dimension(600, 32));
		this.add(infoLabel);
	}

	public static void setInfoMessage(String message, Color messageColor) {
		infoLabel.setForeground(messageColor);
		infoLabel.setText(message);
	}
}
