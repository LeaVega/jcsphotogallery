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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

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
	private JTextField widthSizeTextField;
	private JTextField heightSizeTextField;
	private JCheckBox removeJpgFilesCheckBox;
	private JLabel validationInfo;

	public Preferences(Configs configs) {
		this.configs = configs;
		setTitle("Preferences");
		this.setMinimumSize(new Dimension(450, 220));
		this.setLocationRelativeTo(null);

		intialize();
	}

	private void intialize() {
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		panel.add(getSaveButton());
		panel.add(getCancelButton());

		removeJpgFilesCheckBox = new JCheckBox("Remove the .jpg files for deleted pictures");
		removeJpgFilesCheckBox.setBounds(30, 80, 426, 44);
		panel.add(removeJpgFilesCheckBox);

		validationInfo = new JLabel();
		validationInfo.setBounds(30, 110, 400, 44);
		validationInfo.setForeground(Color.red);
		panel.add(validationInfo);

		panel.add(getPictureSizePanel());

		setPreviousValues();
		setVisible(true);
	}

	private JPanel getPictureSizePanel() {
		JPanel panelPictureSize = new JPanel();
		panelPictureSize.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Picture size (px)", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panelPictureSize.setBounds(30, 11, 383, 68);
		panelPictureSize.setLayout(null);

		panelPictureSize.add(getPictureSizeBorderTitleLabel());
		panelPictureSize.add(getWidthSizeTextField());
		panelPictureSize.add(getHeightTextField());
		panelPictureSize.add(getLabel("Width", 30, 37, 46));
		panelPictureSize.add(getLabel("(px)", 156, 37, 26));
		panelPictureSize.add(getLabel("Height", 212, 37, 46));
		panelPictureSize.add(getLabel("(px)", 347, 37, 26));

		return panelPictureSize;
	}

	private JLabel getPictureSizeBorderTitleLabel() {
		JLabel lblConvertImportedPictures = new JLabel("Convert imported pictures to size:");
		lblConvertImportedPictures.setBounds(50, 11, 220, 23);
		return lblConvertImportedPictures;
	}

	private JTextField getHeightTextField() {
		heightSizeTextField = new JTextField();
		heightSizeTextField.setBounds(268, 37, 69, 20);
		heightSizeTextField.setEditable(false);
		heightSizeTextField.setColumns(10);

		return heightSizeTextField;
	}

	private JTextField getWidthSizeTextField() {
		widthSizeTextField = new JTextField();
		widthSizeTextField.setBounds(77, 37, 69, 20);
		widthSizeTextField.setColumns(10);
		widthSizeTextField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				isIntroducedValueValid(widthSizeTextField.getText());
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				isIntroducedValueValid(widthSizeTextField.getText());
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				isIntroducedValueValid(widthSizeTextField.getText());
			}
		});
		return widthSizeTextField;
	}

	private boolean isIntroducedValueValid(String value) {
		try {
			Integer newWidth = Integer.valueOf(value);
			if (newWidth < 2500 && newWidth > 500) {
				validationInfo.setText("");
				heightSizeTextField.setText("" + getNewDimensions(value).height);
				return true;
			} else {
				validationInfo.setText("The Width value is out of limits: 500 - 2500 px");
			}
		} catch (NumberFormatException e) {
			validationInfo.setText("The Width value is not an integer number");
		}
		return false;
	}

	private Dimension getNewDimensions(String width) {
		int newWidth = Integer.valueOf(width);
		int newHeight = (int) (newWidth * 0.75);
		return new Dimension(newWidth, newHeight);
	}

	private JLabel getLabel(String text, int positionX, int positionY, int width) {
		JLabel label = new JLabel(text);
		label.setBounds(positionX, positionY, width, 20);
		return label;
	}

	private JButton getCancelButton() {
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancel.setBounds(200, 150, 91, 23);
		return btnCancel;
	}

	private JButton getSaveButton() {
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveSettings();
			}
		});
		btnSave.setBounds(320, 150, 91, 23);
		return btnSave;
	}

	private void setPreviousValues() {
		widthSizeTextField.setText("" + (int) configs.getPictureDimension().getWidth());
		heightSizeTextField.setText("" + (int) configs.getPictureDimension().getHeight());
		removeJpgFilesCheckBox.setSelected(configs.isRemovePictures(true));
	}

	private void saveSettings() {
		String newIntroducedText = widthSizeTextField.getText();
		if (isIntroducedValueValid(newIntroducedText)) {
			configs.setPictureDimension(getNewDimensions(newIntroducedText));
			configs.setRemovePictures(removeJpgFilesCheckBox.isSelected());
			dispose();
		}
	}

}
