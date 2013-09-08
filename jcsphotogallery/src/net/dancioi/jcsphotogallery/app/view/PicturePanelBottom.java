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

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.tree.DefaultMutableTreeNode;

import net.dancioi.jcsphotogallery.app.controller.JcsPhotoGalleryControllerInterface;
import net.dancioi.jcsphotogallery.shared.PictureBean;

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

	private JButton rotateClockwise;
	private JButton rotateCounterClockwise;

	public PicturePanelBottom(UpdateTree tree) {
		this.tree = tree;
		initialize();
	}

	private void initialize() {
		this.setLayout(new GridLayout(2, 1));
		add(getImageEditPanel());
		add(getControllPanel());
	}

	private JPanel getControllPanel() {
		JPanel controll = new JPanel();
		controll.add(getNavigationButtons(), BorderLayout.LINE_START);
		controll.add(getRotationButtons(), BorderLayout.LINE_END);
		return controll;
	}

	private JPanel getNavigationButtons() {
		JPanel navigatePanel = new JPanel();
		previous = new JButton();
		previous.setPreferredSize(new Dimension(76, 34));
		previous.setIcon(new ImageIcon(getClass().getClassLoader().getResource("icons/controlPrevious.png")));

		next = new JButton();
		next.setPreferredSize(new Dimension(76, 34));
		next.setIcon(new ImageIcon(getClass().getClassLoader().getResource("icons/controlNext.png")));

		navigatePanel.add(previous, BorderLayout.LINE_START);
		navigatePanel.add(next, BorderLayout.LINE_END);

		return navigatePanel;
	}

	private JPanel getRotationButtons() {
		JPanel rotatePanel = new JPanel();
		rotateClockwise = new JButton();
		rotateClockwise.setPreferredSize(new Dimension(76, 34));
		rotateClockwise.setIcon(new ImageIcon(getClass().getClassLoader().getResource("icons/controlRotRight.png")));

		rotateCounterClockwise = new JButton();
		rotateCounterClockwise.setPreferredSize(new Dimension(76, 34));
		rotateCounterClockwise.setIcon(new ImageIcon(getClass().getClassLoader().getResource("icons/controlRotLeft.png")));

		rotatePanel.add(rotateCounterClockwise, BorderLayout.LINE_START);
		rotatePanel.add(rotateClockwise, BorderLayout.LINE_END);

		return rotatePanel;
	}

	private JPanel getImageEditPanel() {
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
		if (e.getSource() instanceof JTextField) {
			if (e.isTemporary())
				return;
			if (editedPicture != null) {
				updateEditedPicture();
			}
		}
	}

	public void attachActions(JcsPhotoGalleryControllerInterface controller) {
		previous.addActionListener(controller.addPreviousPictureActionListener());
		next.addActionListener(controller.addNextPictureActionListener());

		rotateClockwise.addActionListener(controller.addRotatePictureClockwiseActionListener());
		rotateCounterClockwise.addActionListener(controller.addRotatePictureCounterClockwiseActionListener());
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
		if (!InputTextValidator.validateText(nameTextField.getText()))
			InfoPanel.setInfoMessage("Error: " + "Name field for picture: " + pictureBean.getName() + " is not a valid text", InfoPanel.RED);
		else {
			InfoPanel.setInfoMessage("", InfoPanel.RED);
			editedPicture.setName(nameTextField.getText());
		}
		if (!InputTextValidator.validateText(descriptionTextField.getText()))
			InfoPanel.setInfoMessage("Error: " + "Description field for picture: " + pictureBean.getName() + " is not a valid text", InfoPanel.RED);
		else
			editedPicture.setDescription(descriptionTextField.getText());

		editedPicture.getParent().setEdited(true);
		treeNode.setUserObject(editedPicture);
		tree.updateNode(treeNode);
		editedPicture = null;

	}

}