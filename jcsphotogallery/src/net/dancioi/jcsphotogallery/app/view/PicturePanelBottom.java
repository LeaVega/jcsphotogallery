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

import net.dancioi.jcsphotogallery.app.controller.JcsPhotoGalleryControllerInterface;
import net.dancioi.jcsphotogallery.client.model.PictureBean;

/**
 * This class .
 * 
 * @author Daniel Cioi <dan@dancioi.net>
 * @version $Revision$ Last modified: $Date: 2011-12-03 13:07:01 +0200
 *          (Sat, 03 Dec 2011) $, by: $Author$
 */

public class PicturePanelBottom extends JPanel implements FocusListener {

	private static final long serialVersionUID = 1L;
	private JButton previous;
	private JButton next;
	private PictureBean pictureBean;

	private JTextField nameTextField;
	private JTextField descriptionTextField;

	public PicturePanelBottom() {
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
		// nothing here
	}

	@Override
	public void focusLost(FocusEvent e) {
		if (e.getSource() instanceof JTextField) {
			if (e.isTemporary())
				return;
			pictureBean.getParent().setEdited(true);
			pictureBean.setName(nameTextField.getText());
			pictureBean.setDescription(descriptionTextField.getText());
		}
	}

	private String validateText(String text) {

		return text;
	}

	public void attachActions(JcsPhotoGalleryControllerInterface controller) {
		previous.addActionListener(controller.addPreviousPictureActionListener());
		next.addActionListener(controller.addNextPictureActionListener());
	}

	public void setCurrentPictureBean(PictureBean pictureBean) {
		this.pictureBean = pictureBean;
		nameTextField.setText(pictureBean.getName());
		descriptionTextField.setText(pictureBean.getDescription());

	}

}