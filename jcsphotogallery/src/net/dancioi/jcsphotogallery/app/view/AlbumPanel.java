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
	private JTextField albumTagsTextField;

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
		c.ipadx = 50;
		editPanel.add(new JLabel("Tags:"), c);
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 5;
		c.ipady = 0;
		c.ipadx = 200;
		albumTagsTextField = new JTextField();
		albumTagsTextField.setToolTipText("add tags separated by ';'");
		albumTagsTextField.addFocusListener(this);
		albumTagsTextField.setPreferredSize(new Dimension(200, 32));
		editPanel.add(albumTagsTextField, c);

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
		c.ipadx = 50;
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
		albumTagsTextField.setText(album.getTags() == null ? "no tag" : album.getTagsInOneLine());
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
		if (!InputTextValidator.validateText(albumNameTextField.getText()))
			InfoPanel.setInfoMessage("Error: " + "Album Name field for album: " + editedAlbum.getName() + " is not a valid text", InfoPanel.RED);
		else {
			InfoPanel.setInfoMessage("", InfoPanel.RED);
			editedAlbum.setName(albumNameTextField.getText());
		}
		if (!InputTextValidator.validateText(albumTagsTextField.getText()))
			InfoPanel.setInfoMessage("Error: " + "Tags field for album: " + editedAlbum.getName() + " is not a valid text", InfoPanel.RED);
		else {
			String[] tags = albumTagsTextField.getText().split(";");
			for (int i = 0; i < tags.length; i++)
				tags[i] = tags[i].trim();// remove whitespace between tags (if there are any).
			editedAlbum.setTags(tags);
		}

		editedAlbum.getParent().setEdited(true);
		treeNode.setUserObject(editedAlbum);
		tree.updateNode(treeNode);
		editedAlbum = null;
	}

}
