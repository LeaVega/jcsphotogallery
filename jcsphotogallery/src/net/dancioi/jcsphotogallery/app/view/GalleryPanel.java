/*	
 * 	File    : GalleryPanel.java
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
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.dancioi.jcsphotogallery.client.shared.GalleryAlbums;

/**
 * Panel to edit the gallery.
 * 
 * @author Daniel Cioi <dan@dancioi.net>
 * @version $Revision: 39 $ Last modified: $Date: 2012-03-20 22:39:16 +0200
 *          (Tue, 20 Mar 2012) $, by: $Author: dan.cioi $
 */
public class GalleryPanel extends JPanel implements FocusListener {

	private static final long serialVersionUID = 1L;
	private JTextField galleryName;
	private JTextField galleryHomePage;
	private JTextField galleryPath;
	private GalleryAlbums galleryAlbums;

	public GalleryPanel() {
		initialize();
	}

	private void initialize() {
		this.setLayout(new BorderLayout());
		this.add(getEditPanel());
	}

	private JPanel getEditPanel() {
		JPanel editPanel = new JPanel();
		editPanel.setLayout(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		editPanel.add(new JLabel("Gallery name: "), c);
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 5;
		galleryName = new JTextField();
		galleryName.setPreferredSize(new Dimension(400, 32));
		galleryName.addFocusListener(this);
		editPanel.add(galleryName, c);

		c.gridx = 0;
		c.gridy = 1;
		editPanel.add(new JLabel("Home page: "), c);
		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 5;
		galleryHomePage = new JTextField();
		galleryHomePage.setPreferredSize(new Dimension(400, 32));
		galleryHomePage.addFocusListener(this);
		editPanel.add(galleryHomePage, c);

		c.gridx = 0;
		c.gridy = 2;
		editPanel.add(new JLabel("Gallery path: "), c);
		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth = 5;
		galleryPath = new JTextField();
		galleryPath.setPreferredSize(new Dimension(400, 32));
		galleryPath.setEditable(false);
		editPanel.add(galleryPath, c);

		return editPanel;
	}

	public void fillUpParameters(GalleryAlbums galleryAlbums, String appGalleryPath) {
		this.galleryAlbums = galleryAlbums;
		galleryName.setText(galleryAlbums.getGalleryName());
		galleryHomePage.setText(galleryAlbums.getGalleryHomePage());
		galleryPath.setText(appGalleryPath);
	}

	@Override
	public void focusGained(FocusEvent e) {
		// nothing here
	}

	@Override
	public void focusLost(FocusEvent e) {
		if (e.getSource() instanceof JTextField) {
			if (e.isTemporary())
				return;
			galleryAlbums.setEdited(true);
			galleryAlbums.setGalleryName(galleryName.getText());
			galleryAlbums.setGalleryHomePage(galleryHomePage.getText());
		}

	}

}
