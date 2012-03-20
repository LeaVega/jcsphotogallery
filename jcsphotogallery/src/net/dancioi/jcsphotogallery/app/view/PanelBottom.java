/*	
 * 	File    : PanelBottom.java
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
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.dancioi.jcsphotogallery.app.model.GalleryWrite;

/**
 * This class .
 * 
 * @author Daniel Cioi <dan@dancioi.net>
 * @version $Revision$ Last modified: $Date: 2011-12-03 13:07:01 +0200
 *          (Sat, 03 Dec 2011) $, by: $Author$
 */

public class PanelBottom extends JPanel {

	private GalleryWrite albumsList;

	public PanelBottom() {
		initialize();
	}

	private void initialize() {
		add(addImageEdit(), BorderLayout.LINE_START);
		add(addControll(), BorderLayout.LINE_END);
	}

	private JPanel addControll() {
		JPanel controll = new JPanel();
		controll.setPreferredSize(new Dimension(250, 80));

		JButton previous = new JButton("Previous");
		previous.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

			}
		});
		previous.setPreferredSize(new Dimension(100, 30));

		JButton next = new JButton("Next");
		next.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

			}
		});
		next.setPreferredSize(new Dimension(100, 30));

		controll.add(previous, BorderLayout.LINE_START);
		controll.add(next, BorderLayout.LINE_END);

		return controll;
	}

	private JPanel addImageEdit() {
		JPanel edit = new JPanel();
		edit.setPreferredSize(new Dimension(400, 80));
		JLabel name = new JLabel("Name");
		JLabel description = new JLabel("Description");

		return edit;
	}

	public void savechanges() {
	}

	public void nextPictureButton() {
	}

	public void previousPictureButton() {
	}

}