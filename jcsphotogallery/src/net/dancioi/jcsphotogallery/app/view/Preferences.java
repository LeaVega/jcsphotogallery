/*	
 * 	File    : PreferencesFrame.java
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
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import net.dancioi.jcsphotogallery.app.model.Configs;

/**
 * The Preferences modal dialog.
 * 
 * @author Daniel Cioi <dan@dancioi.net>
 * @version $Revision$ Last modified: $Date$, by: $Author$
 */
public class Preferences extends JFrame {

	private static final long serialVersionUID = 1L;
	private Configs configs;
	private JTextField sizeWidth;
	private JTextField sizeHeight;
	private JCheckBox checkBoxRemoveJpgFiles;
	private JLabel validationInfo;

	public Preferences(Configs configs) {
		this.configs = configs;
		setTitle("Preferences");
		this.setMinimumSize(new Dimension(450, 220));
		this.setLocationRelativeTo(null);

		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveSettings();
			}
		});
		btnSave.setBounds(341, 160, 91, 23);
		panel.add(btnSave);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancel.setBounds(209, 160, 91, 23);
		panel.add(btnCancel);

		checkBoxRemoveJpgFiles = new JCheckBox("Remove also the .jpg files for deleted pictures");
		checkBoxRemoveJpgFiles.setBounds(30, 80, 426, 44);
		panel.add(checkBoxRemoveJpgFiles);

		validationInfo = new JLabel();
		validationInfo.setBounds(30, 115, 400, 44);
		validationInfo.setForeground(Color.red);
		panel.add(validationInfo);

		JPanel panelPictureSize = new JPanel();
		panelPictureSize.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Picture size (px)", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelPictureSize.setBounds(30, 11, 383, 68);
		panel.add(panelPictureSize);
		panelPictureSize.setLayout(null);

		JLabel lblConvertImportedPictures = new JLabel("Convert imported pictures to size:");
		lblConvertImportedPictures.setBounds(50, 11, 220, 23);
		panelPictureSize.add(lblConvertImportedPictures);

		sizeWidth = new JTextField();
		sizeWidth.setBounds(77, 37, 69, 20);
		panelPictureSize.add(sizeWidth);
		sizeWidth.setColumns(10);

		sizeHeight = new JTextField();
		sizeHeight.setBounds(268, 37, 69, 20);
		panelPictureSize.add(sizeHeight);
		sizeHeight.setEditable(false);
		sizeHeight.setColumns(10);

		JLabel lblPictureWidth = new JLabel("Width");
		lblPictureWidth.setBounds(30, 37, 46, 20);
		panelPictureSize.add(lblPictureWidth);

		JLabel lblPx = new JLabel("px");
		lblPx.setBounds(156, 37, 26, 20);
		panelPictureSize.add(lblPx);

		JLabel lblPictureHeight = new JLabel("Height");
		lblPictureHeight.setBounds(212, 37, 46, 20);
		panelPictureSize.add(lblPictureHeight);

		JLabel lblPx_1 = new JLabel("px");
		lblPx_1.setBounds(347, 37, 26, 20);
		panelPictureSize.add(lblPx_1);

		intialize();
	}

	private void intialize() {
		setPreviousValues();

		setVisible(true);
	}

	private void setPreviousValues() {
		sizeWidth.setText("" + (int) configs.getPictureDimension().getWidth());
		sizeHeight.setText("" + (int) configs.getPictureDimension().getHeight());
		checkBoxRemoveJpgFiles.setSelected(configs.isRemovePictures(true));
	}

	private void saveSettings() {
		try {
			Integer newWidth = Integer.valueOf(sizeWidth.getText());
			if (newWidth < 2500 && newWidth > 500) {
				Integer newHeight = (int) (newWidth * 0.75);

				configs.setPictureDimension(new Dimension(newWidth, newHeight));
				configs.setRemovePictures(checkBoxRemoveJpgFiles.isSelected());
				dispose();
			} else {
				validationInfo.setText("The Width value is out of limits: 500 - 2500 px");
			}
		} catch (NumberFormatException e) {
			validationInfo.setText("The Width value is not an integer number");
		}
	}
}
