/*	
 * 	File    : AboutFrame.java
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

import javax.swing.JFrame;
import javax.swing.JTextArea;

/**
 * The About modal dialog.
 * 
 * @author Daniel Cioi <dan@dancioi.net>
 * @version $Revision$ Last modified: $Date$, by: $Author$
 */
public class AboutFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	public AboutFrame() {
		initialize();
	}

	private void initialize() {
		this.pack();
		this.setTitle("About...");
		this.setLocationRelativeTo(null);
		this.setPreferredSize(new Dimension(330, 020));
		this.setMinimumSize(new Dimension(330, 200));
		this.getContentPane().add(getAboutText());

	}

	private JTextArea getAboutText() {
		JTextArea aboutText = new JTextArea();
		aboutText
				.setText("\nJcsPhotoGallery desktop application, \na pictures manager for JcsPhototGallery client application \nThe application is  released as free software\nunder the GNU General Public License (GPL). \n\n For more info please see:\n http://www.dancioi.net/projects/jcsphotogallery/ ");
		aboutText.setEditable(false);
		return aboutText;
	}
}
