/*	
 * 	File    : AlbumPanel.java
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
import java.awt.image.BufferedImage;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.tree.DefaultMutableTreeNode;

import net.dancioi.jcsphotogallery.client.shared.AlbumBean;

/**
 * Panel to edit the photos' album.
 * 
 * @author Daniel Cioi <dan@dancioi.net>
 * @version $Revision$ Last modified: $Date$, by: $Author$
 */

public class AlbumPanel extends JPanel implements FocusListener {

	private static final long serialVersionUID = 1L;
	private UpdateTree tree;
	private ImageViewer imageViewer;

	private AlbumBean albumBean;

	private JTextField albumNameTextField;
	private JTextArea albumCategoriesTextField;

	private AlbumBean editedAlbum;
	private DefaultMutableTreeNode treeNode;

	public AlbumPanel(UpdateTree tree) {
		this.tree = tree;
		initialize();
	}

	private void initialize() {
		this.setLayout(new BorderLayout());
		this.add(getAlbumEditPanel());
	}

	private JPanel getAlbumEditPanel() {
		JPanel editPanel = new JPanel();
		editPanel.setLayout(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.ipadx = 10;
		editPanel.add(new JLabel("Categories:"), c);
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 5;
		c.ipady = 40;
		c.ipadx = 200;
		albumCategoriesTextField = new JTextArea();
		albumCategoriesTextField.setToolTipText("add categories separated by ';'");
		albumCategoriesTextField.setLineWrap(true);
		albumCategoriesTextField.setWrapStyleWord(true);
		albumCategoriesTextField.addFocusListener(this);
		JScrollPane areaScrollPane = new JScrollPane(albumCategoriesTextField);
		areaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		areaScrollPane.setPreferredSize(new Dimension(200, 40));
		editPanel.add(areaScrollPane, c);

		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 5;
		c.ipady = 210;
		c.ipadx = 210;
		imageViewer = new ImageViewer();
		editPanel.add(imageViewer, c);

		c.gridx = 0;
		c.gridy = 2;
		c.ipady = 0;
		c.ipadx = 10;
		editPanel.add(new JLabel("Album Name:"), c);
		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth = 5;
		c.ipadx = 200;
		albumNameTextField = new JTextField();
		albumNameTextField.setPreferredSize(new Dimension(200, 32));
		albumNameTextField.addFocusListener(this);
		editPanel.add(albumNameTextField, c);

		return editPanel;
	}

	private void showAlbumThumbnail(BufferedImage image) {
		imageViewer.loadImage(image);
	}

	public void setCurrentAlbum(AlbumBean album, BufferedImage albumThumbnail, DefaultMutableTreeNode treeNode) {
		if (editedAlbum != null) {
			updateEditedAlbum();
		}
		this.treeNode = treeNode;
		showAlbumThumbnail(albumThumbnail);
		albumBean = album;
		albumNameTextField.setText(album.getName());
		albumCategoriesTextField.setText(album.getCategory() == null ? "no tag" : album.getCategory().toString());
	}

	@Override
	public void focusGained(FocusEvent arg0) {
		editedAlbum = albumBean;
	}

	@Override
	public void focusLost(FocusEvent e) {
		if (e.getSource() instanceof JTextField || e.getSource() instanceof JTextArea) {
			if (e.isTemporary())
				return;
			if (editedAlbum != null) {
				updateEditedAlbum();
			}
		}

	}

	private void updateEditedAlbum() {
		editedAlbum.setEdited(true);
		editedAlbum.setName(albumNameTextField.getText());
		String[] categories = albumCategoriesTextField.getText().split(";");
		editedAlbum.setCategory(categories);
		treeNode.setUserObject(editedAlbum);
		tree.updateNode(treeNode);
		editedAlbum = null;
	}

	private String validateText(String text) {
		// TODO check the text to exclude html tags etc.
		return text;
	}

}
