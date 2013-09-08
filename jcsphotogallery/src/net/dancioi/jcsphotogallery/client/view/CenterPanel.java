/*	
 * 	File    : CenterPanel.java
 * 
 * 	Copyright (C) 2010-2011 Daniel Cioi <dan@dancioi.net>
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

package net.dancioi.jcsphotogallery.client.view;

import net.dancioi.jcsphotogallery.shared.Thumbnails;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ErrorEvent;
import com.google.gwt.event.dom.client.ErrorHandler;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Image;

/**
 * The 3x3 grid where the image thumbnails are shown. CENTER panel.
 * 
 * The application contains 3 panels (top, center, bottom).
 * 
 * @author Daniel Cioi <dan@dancioi.net>
 * @version $Revision$ Last modified: $Date$, by: $Author$
 */
public class CenterPanel extends Grid {

	private JcsPhotoGalleryView pgw;
	private Image[] cell = new Image[9]; // 3x3 matrix.
	private boolean[] cellOn = new boolean[9]; // flag to know which cells have images attached

	public CenterPanel(JcsPhotoGalleryView pgw) {
		super(3, 3);
		this.pgw = pgw;
		initialize();
	}

	/**
	 * Initialize.
	 */
	private void initialize() {
		setSize("800px", "630px");
		setBorderWidth(0);
		setCellSpacing(5);
		setTheCells();
	}

	/**
	 * Table 3x3 where the pictures are shown. Format the cells.
	 */
	private void setTheCells() {
		for (int h = 0; h < 3; h++) {
			for (int w = 0; w < 3; w++) {

				getCellFormatter().setHorizontalAlignment(h, w, HasHorizontalAlignment.ALIGN_CENTER);
				getCellFormatter().setStyleName(h, w, "gridCell-format");
				getCellFormatter().setHeight(h, w, "210px");
				getCellFormatter().setWidth(h, w, "240px");
			}
		}
	}

	/**
	 * Adds the image to the table.
	 */
	public void showImages(String imagesPath, Thumbnails[] thumbnails) {
		int cellID = 0;
		int imgId = 0;
		for (int h = 0; h < 3; h++) {
			for (int w = 0; w < 3; w++) {
				if (cellID < thumbnails.length) {
					cell[cellID] = new Image(imagesPath + thumbnails[imgId].getImgThumbnail());
					cell[cellID].addErrorHandler(new ErrorHandler() {
						@Override
						public void onError(ErrorEvent event) {
							// if image is missing then show the next one
							((Image) (event.getSource())).setUrl("template/ext/albumThumbnailNotFound.png");
						}
					});
					cell[cellID].setTitle(thumbnails[imgId].getName());

					cellOn[cellID] = true;
					imgId++;
				} else { // for less than 9 thumbnails, fill it with empty image.
					cell[cellID] = new Image("template/ext/cellBackground.gif");
					cellOn[cellID] = false;
				}
				setWidget(h, w, cell[cellID]);
				cellID++;
			}
		}
		addClickEvent();
	}

	/**
	 * Adds click event to each cell of the table.
	 */
	private void addClickEvent() {
		cell[0].addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (cellOn[0])
					clickEventOnCell(1);
			}
		});
		if (cellOn[0])
			cell[0].addStyleName("handOver");

		cell[1].addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (cellOn[1])
					clickEventOnCell(2);
			}
		});
		if (cellOn[1])
			cell[1].addStyleName("handOver");

		cell[2].addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (cellOn[2])
					clickEventOnCell(3);
			}
		});
		if (cellOn[2])
			cell[2].addStyleName("handOver");

		cell[3].addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (cellOn[3])
					clickEventOnCell(4);
			}
		});
		if (cellOn[3])
			cell[3].addStyleName("handOver");

		cell[4].addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (cellOn[4])
					clickEventOnCell(5);
			}
		});
		if (cellOn[4])
			cell[4].addStyleName("handOver");

		cell[5].addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (cellOn[5])
					clickEventOnCell(6);
			}
		});
		if (cellOn[5])
			cell[5].addStyleName("handOver");

		cell[6].addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (cellOn[6])
					clickEventOnCell(7);
			}
		});
		if (cellOn[6])
			cell[6].addStyleName("handOver");

		cell[7].addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (cellOn[7])
					clickEventOnCell(8);
			}
		});
		if (cellOn[7])
			cell[7].addStyleName("handOver");

		cell[8].addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (cellOn[8])
					clickEventOnCell(9);
			}
		});
		if (cellOn[8])
			cell[8].addStyleName("handOver");
	}

	/*
	 * Shows the PopUP panel that shows image one by one.
	 * 
	 * @param id the image id selected
	 */
	private void clickEventOnCell(int id) {
		pgw.clickEventOnCell(id);
	}

}
