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
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.tree.DefaultMutableTreeNode;

import net.dancioi.jcsphotogallery.shared.AlbumBean;
import net.dancioi.jcsphotogallery.shared.JcsPhotoGalleryConstants;

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
	private JTextArea existingTags;
	private SortedSet<String> allCurrentTags;

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

		GridBagConstraints constraints = new GridBagConstraints();

		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.ipadx = 50;
		editPanel.add(new JLabel("Gallery's Tags:"), constraints);

		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.gridwidth = 5;
		constraints.ipady = 0;
		constraints.ipadx = 200;
		editPanel.add(getExistingTagsArea(), constraints);

		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.ipadx = 50;
		editPanel.add(new JLabel("Album's Tags:"), constraints);
		constraints.gridx = 1;
		constraints.gridy = 1;
		constraints.gridwidth = 5;
		constraints.ipady = 0;
		constraints.ipadx = 200;
		albumTagsTextField = new JTextField();
		albumTagsTextField.setToolTipText("add tags separated by ';'");
		albumTagsTextField.addFocusListener(this);
		albumTagsTextField.setPreferredSize(new Dimension(200, 32));
		albumTagsTextField.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				showCorrespondingTagsByTypedKey(e.getKeyChar());
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyPressed(KeyEvent e) {

			}
		});
		editPanel.add(albumTagsTextField, constraints);

		constraints.gridx = 1;
		constraints.gridy = 2;
		constraints.gridwidth = 5;
		constraints.ipady = 210;
		constraints.ipadx = 210;
		imageViewer = new ImageViewer();
		editPanel.add(imageViewer, constraints);

		constraints.gridx = 0;
		constraints.gridy = 3;
		constraints.ipady = 0;
		constraints.ipadx = 50;
		editPanel.add(new JLabel("Album's Name:"), constraints);
		constraints.gridx = 1;
		constraints.gridy = 3;
		constraints.gridwidth = 5;
		constraints.ipadx = 200;
		albumNameTextField = new JTextField();
		albumNameTextField.setPreferredSize(new Dimension(200, 32));
		albumNameTextField.addFocusListener(this);
		editPanel.add(albumNameTextField, constraints);

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

		allCurrentTags = getAllGalleryTags();
		existingTags.setText(allCurrentTags.toString());
	}

	private SortedSet<String> getAllGalleryTags() {
		Enumeration<DefaultMutableTreeNode> children = treeNode.getParent().children();
		SortedSet<String> currentTags = new TreeSet<String>();
		while (children.hasMoreElements()) {
			DefaultMutableTreeNode nextElement = (DefaultMutableTreeNode) children.nextElement();
			AlbumBean ab = (AlbumBean) nextElement.getUserObject();
			String[] abTags = ab.getTags();
			for (String abTag : abTags) {
				currentTags.add(abTag);
			}
		}
		return currentTags;
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
			String[] tags = albumTagsTextField.getText().split(JcsPhotoGalleryConstants.ALBUM_SEPARATOR);
			for (int i = 0; i < tags.length; i++)
				tags[i] = tags[i].trim();// remove whitespace between tags (if there are any).
			editedAlbum.setTags(tags);
		}

		editedAlbum.getParent().setEdited(true);
		treeNode.setUserObject(editedAlbum);
		tree.updateNode(treeNode);
		editedAlbum = null;
	}

	private Component getExistingTagsArea() {
		existingTags = getExistingTagsText();
		JScrollPane existingTagsScrollPanel = new JScrollPane(existingTags);
		existingTagsScrollPanel.setPreferredSize(new Dimension(200, 80));
		existingTagsScrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		existingTagsScrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		return existingTagsScrollPanel;
	}

	private JTextArea getExistingTagsText() {
		JTextArea existingTagsText = new JTextArea();
		existingTagsText.setBackground(this.getBackground());
		existingTagsText.setText("");
		existingTagsText.setEditable(false);
		return existingTagsText;
	}

	/*
	 * Shows the tags that begin with typed character. Do that just if the typed character is the last from albumTagsTextField, or typed in the character sequence with no ';' after.
	 */
	private void showCorrespondingTagsByTypedKey(char typedChar) {
		if (typedChar == KeyEvent.VK_BACK_SPACE || typedChar == KeyEvent.VK_DELETE) {
			typedChar = ' '; // if backspace or delete is used, then set it to empty space
		}
		String albumTagsText = albumTagsTextField.getText() + typedChar;
		int albumTagsTextCaretPosition = albumTagsTextField.getCaretPosition();
		Set<String> possibleTags = allCurrentTags;
		if (!Character.toString(typedChar).equalsIgnoreCase(JcsPhotoGalleryConstants.ALBUM_SEPARATOR)) {
			int lastIndex = albumTagsText.lastIndexOf(JcsPhotoGalleryConstants.ALBUM_SEPARATOR);
			if (lastIndex < albumTagsTextCaretPosition) {// normal case, separator before cursor, otherwise show all tags.
				String incompleteTag = albumTagsText.substring(lastIndex + 1).trim();
				if (!incompleteTag.isEmpty()) {
					char[] charArray = incompleteTag.toCharArray();
					possibleTags = getCandidateTags(charArray, allCurrentTags, "");
				}
			}
		}
		existingTags.setText(possibleTags.toString());
	}

	private Set<String> getCandidateTags(char[] charArray, Set<String> tagsToChooseFrom, String prefix) {
		Set<String> possibleTags = new TreeSet<String>();
		if (charArray.length > 0) {
			boolean itemDetected = false;
			Iterator<String> iterator = tagsToChooseFrom.iterator();
			while (iterator.hasNext()) {
				boolean currentItemDetected = false;
				String tag = (String) iterator.next();
				String typed = "" + charArray[0];
				if (tag.toLowerCase().startsWith((prefix + typed).toLowerCase())) {
					possibleTags.add(tag);
					currentItemDetected = true;
					itemDetected = true;
				}
				if (itemDetected && !currentItemDetected) {
					break;
				}

			}
		}
		if (charArray.length > 1 && possibleTags.size() > 0) {
			char[] nextChars = Arrays.copyOfRange(charArray, 1, charArray.length);
			return getCandidateTags(nextChars, possibleTags, prefix + charArray[0]);
		} else {
			return possibleTags;
		}
	}

}
