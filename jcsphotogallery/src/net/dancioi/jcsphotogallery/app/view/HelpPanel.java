/*	
 * 	File    : HelpPanel.java
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
import java.awt.Dimension;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * Short help page. It will be show just at first run..
 * 
 * @author Daniel Cioi <dan@dancioi.net>
 * @version $Revision$ Last modified: $Date: 2012-03-20 22:39:16 +0200
 *          (Tue, 20 Mar 2012) $, by: $Author$
 */
public class HelpPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public HelpPanel() {
		initialize();
	}

	private void initialize() {
		this.setLayout(new BorderLayout());
		JEditorPane htmlView = new JEditorPane();
		htmlView.setEditable(false);
		try {
			htmlView.setPage(getHelpHtml());
		} catch (IOException e) {
			e.printStackTrace();
		}

		JScrollPane scrollPane = new JScrollPane(htmlView);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setPreferredSize(new Dimension(600, 600));
		this.add(scrollPane, BorderLayout.CENTER);
	}

	private URL getHelpHtml() throws MalformedURLException {
		return getClass().getClassLoader().getResource("help/JcsPhotoGalleryHelp.html");
	}

}
