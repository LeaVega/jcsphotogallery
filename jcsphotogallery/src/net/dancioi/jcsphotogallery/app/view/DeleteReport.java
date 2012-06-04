/*	
 * 	File    : DeleteReport.java
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

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Shows the delete result. Just in case when a file could not be delete (returned false).
 * 
 * @author Daniel Cioi <dan@dancioi.net>
 * @version $Revision: $ Last modified: $Date: $, by: $Author: $
 */

public class DeleteReport extends JFrame {

	private static final long serialVersionUID = 1L;
	private StringBuilder result;

	public DeleteReport(StringBuilder result) {
		this.result = result;
		initialize();
	}

	private void initialize() {
		this.setMinimumSize(new Dimension(600, 300));
		this.setTitle("Delete result");
		this.setLayout(new BorderLayout());
		this.setLocationRelativeTo(null);
		JTextArea textArea = new JTextArea(result.toString());
		JScrollPane scrollPane = new JScrollPane(textArea);
		add(scrollPane, BorderLayout.CENTER);

		this.setVisible(true);
	}

}
