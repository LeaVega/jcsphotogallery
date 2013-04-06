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

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import net.dancioi.jcsphotogallery.client.shared.Constants;

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
		this.setSize(460, 240);
		this.setResizable(false);
		this.setLayout(new FlowLayout());
		JPanel panel = new JPanel();
		panel.add(getAboutArea());
		this.getContentPane().add(panel);
	}

	private Component getAboutArea() {
		JScrollPane aboutScrollPanel = new JScrollPane(getAboutText());
		aboutScrollPanel.setPreferredSize(new Dimension(420, 180));
		aboutScrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		aboutScrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		return aboutScrollPanel;
	}

	private JTextArea getAboutText() {
		JTextArea aboutText = new JTextArea();
		aboutText.setText("\n JcsPhotoGallery desktop application, \n a pictures manager for JcsPhototGallery client application \n"
				+ "The application is  released as free software\n under the GNU General Public License (GPL). \n\n "
				+ "For more info please see:\n http://www.dancioi.net/projects/jcsphotogallery/ \n\n Current version is " + Constants.APP_VERSION);
		aboutText.setEditable(false);
		return aboutText;
	}
}
