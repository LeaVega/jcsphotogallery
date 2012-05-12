/*	
 * 	File    : RightClickPopUp.java
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

package net.dancioi.jcsphotogallery.app.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Right click popUp menu.
 * 
 * @author Daniel Cioi <dan@dancioi.net>
 * @version $Revision$ Last modified: $Date$, by: $Author$
 */
public class RightClickPopUp extends JPopupMenu implements ActionListener {

	private static final long serialVersionUID = 1L;
	RightClickPopUpInterface controller;
	private JMenuItem mAddAlbum, mAddPicture, mSetAlbumImage, mDeleteAlbum, mDeleteImage;
	public final static int ROOT = 0;
	public final static int IMAGES = 1;
	public final static int ALBUMS = 2;
	private DefaultMutableTreeNode treeNode;

	private enum Action {
		ADD_NEW_ALBUM, ADD_NEW_IMAGE, SET_ALBUM_IMAGE, DELETE_ALBUM, DELETE_IMAGE
	};

	public RightClickPopUp(RightClickPopUpInterface controller) {
		this.controller = controller;
		initialize();
	}

	/*
	 * Initialize.
	 */
	private void initialize() {
		mAddAlbum = new JMenuItem("Add new album");
		mAddAlbum.addActionListener(this);
		mAddAlbum.setActionCommand(Action.ADD_NEW_ALBUM.toString());
		add(mAddAlbum);

		mAddPicture = new JMenuItem("Add new image");
		mAddPicture.addActionListener(this);
		mAddPicture.setActionCommand(Action.ADD_NEW_IMAGE.toString());
		add(mAddPicture);

		addSeparator();

		mSetAlbumImage = new JMenuItem("Set album's image");
		mSetAlbumImage.addActionListener(this);
		mSetAlbumImage.setActionCommand(Action.SET_ALBUM_IMAGE.toString());
		add(mSetAlbumImage);

		addSeparator();

		mDeleteAlbum = new JMenuItem("Delete album");
		mDeleteAlbum.addActionListener(this);
		mDeleteAlbum.setActionCommand(Action.DELETE_ALBUM.toString());
		add(mDeleteAlbum);

		mDeleteImage = new JMenuItem("Delete image");
		mDeleteImage.addActionListener(this);
		mDeleteImage.setActionCommand(Action.DELETE_IMAGE.toString());
		add(mDeleteImage);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(Action.ADD_NEW_ALBUM.toString()))
			controller.addNewAlbum();
		else if (e.getActionCommand().equals(Action.ADD_NEW_IMAGE.toString()))
			controller.addNewImage(treeNode);
		else if (e.getActionCommand().equals(Action.SET_ALBUM_IMAGE.toString()))
			controller.setAlbumImage(treeNode);
		else if (e.getActionCommand().equals(Action.DELETE_ALBUM.toString()))
			controller.deleteAlbum(treeNode);
		else if (e.getActionCommand().equals(Action.DELETE_IMAGE.toString()))
			controller.deleteImage(treeNode);

	}

	public void enableMenus(int flag, DefaultMutableTreeNode treeNode) {
		this.treeNode = treeNode;
		if (flag == IMAGES) {
			mAddPicture.setEnabled(false);
			mDeleteAlbum.setEnabled(false);
			mSetAlbumImage.setEnabled(true);
			mDeleteImage.setEnabled(true);
		} else if (flag == ALBUMS) {
			mAddPicture.setEnabled(true);
			mDeleteAlbum.setEnabled(true);
			mSetAlbumImage.setEnabled(false);
			mDeleteImage.setEnabled(false);
		} else if (flag == ROOT) {
			mAddPicture.setEnabled(false);
			mDeleteAlbum.setEnabled(false);
			mSetAlbumImage.setEnabled(false);
			mDeleteImage.setEnabled(false);
		}

	}

}
