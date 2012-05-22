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
import java.awt.GridLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.tree.DefaultMutableTreeNode;

import net.dancioi.jcsphotogallery.app.controller.JcsPhotoGalleryControllerInterface;
import net.dancioi.jcsphotogallery.client.shared.PictureBean;

/**
 * Class that shows the selected picture and allow to edit name and description .
 * 
 * @author Daniel Cioi <dan@dancioi.net>
 * @version $Revision$ Last modified: $Date$, by: $Author$
 */

public class PicturePanelBottom extends JPanel implements FocusListener {

	private static final long serialVersionUID = 1L;
	private JButton previous;
	private JButton next;
	private PictureBean pictureBean;
	private UpdateTree tree;

	private JTextField nameTextField;
	private JTextField descriptionTextField;

	private PictureBean editedPicture;
	private DefaultMutableTreeNode treeNode;

	public PicturePanelBottom(UpdateTree tree) {
		this.tree = tree;
		initialize();
	}

	private void initialize() {
		this.setLayout(new GridLayout(2, 1));
		add(imageEditPanel());
		add(controllPanel());
	}

	private JPanel controllPanel() {
		JPanel controll = new JPanel();
		previous = new JButton("Previous");
		previous.setPreferredSize(new Dimension(100, 32));

		next = new JButton("Next");
		next.setPreferredSize(new Dimension(100, 32));

		controll.add(previous, BorderLayout.LINE_START);
		controll.add(next, BorderLayout.LINE_END);

		return controll;
	}

	private JPanel imageEditPanel() {
		JPanel edit = new JPanel();
		JLabel nameLabel = new JLabel("Name:");
		nameTextField = new JTextField();
		nameTextField.setPreferredSize(new Dimension(150, 32));
		nameTextField.addFocusListener(this);
		edit.add(nameLabel);
		edit.add(nameTextField);

		JLabel descriptionLabel = new JLabel("Description:");
		descriptionTextField = new JTextField();
		descriptionTextField.setPreferredSize(new Dimension(350, 32));
		descriptionTextField.addFocusListener(this);
		edit.add(descriptionLabel);
		edit.add(descriptionTextField);

		return edit;
	}

	@Override
	public void focusGained(FocusEvent e) {
		editedPicture = pictureBean;
	}

	@Override
	public void focusLost(FocusEvent e) {
		System.out.println("***  picture panel ");
		if (e.getSource() instanceof JTextField) {
			if (e.isTemporary())
				return;
			if (editedPicture != null) {
				updateEditedPicture();
			}
		}
	}

	private String validateText(String text) {
		// TODO check the text to exclude html tags etc.
		return text;
	}

	public void attachActions(JcsPhotoGalleryControllerInterface controller) {
		previous.addActionListener(controller.addPreviousPictureActionListener());
		next.addActionListener(controller.addNextPictureActionListener());
	}

	public void setCurrentPictureBean(PictureBean pictureBean, DefaultMutableTreeNode treeNode) {
		if (editedPicture != null) {
			updateEditedPicture();
		}
		this.pictureBean = pictureBean;
		this.treeNode = treeNode;
		nameTextField.setText(pictureBean.getName());
		descriptionTextField.setText(pictureBean.getDescription());

	}

	private void updateEditedPicture() {
		editedPicture.getParent().setEdited(true);
		editedPicture.setName(nameTextField.getText());
		editedPicture.setDescription(descriptionTextField.getText());
		treeNode.setUserObject(editedPicture);
		tree.updateNode(treeNode);
		editedPicture = null;
	}

}