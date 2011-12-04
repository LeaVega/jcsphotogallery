/*	
 * 	File    : CenterPanelAdmin.java & SelectorBox.java
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

package net.dancioi.jcsphotogallery.admin;

import net.dancioi.jcsphotogallery.client.view.CenterPanel;
import net.dancioi.jcsphotogallery.client.view.Jcsphotogallery;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Image;


/**
 * The 3x3 grid where the image thumbnails are shown.
 * CENTER panel. 
 * 
 * The application contains 3 panels (top, center, bottom).
 *  
 * @author Daniel Cioi <dan@dancioi.net>
 * @version $Revision$  Last modified: $Date$, by: $Author$
 */

public class CenterPanelAdmin extends CenterPanel{

	SelectorBox opp[];

	/**
	 * Constructor.
	 * @param pg 
	 */
	public CenterPanelAdmin(Jcsphotogallery pg) {
		super(pg);
		ini();
	}

	/**
	 * Method that initialize the Operator class
	 */
	private void ini(){
		opp = new SelectorBox[9];
	}


	/**
	 * adds the image to the table.
	 */
	@Override
	protected void showImg(String imagesPath){
/*		cellID = 0;
		for(int h=0;h<3;h++){
			for(int w=0;w<3;w++){
				if(cellID<imgCountLimit){
					cell[cellID] = new Image(imagesPath+thumbnails[imgId].getImgThumbnail());
					cell[cellID].setTitle(thumbnails[imgId].getName());
					if(cellID==imgCount-1){
						opp[cellID] = new SelectorBox(cell[cellID], false);
					}
					else{
						opp[cellID] = new SelectorBox(cell[cellID], true);
					}
					setWidget(h, w, opp[cellID]);

					//setWidget(h, w, cell[cellID]);
					cellOn[cellID] = true;

					cellID++;
					imgId++;
				}
				else{
					cell[cellID] = new Image("ext/cellBackground.gif");
					setWidget(h, w, cell[cellID]);
					cellOn[cellID] = false;
					cellID++;
				}
			}
		}
		addClickEvent();*/
	}
	
	
	
	
	/**
	 * Method that shows the popUp with the selected image.
	 * @param id
	 */
	@Override
	protected void showPopUpImg(int id){
//		new PopUpImgShowAdmin(id-1, imgPath, pictures).show();
	}
	
	
}



/**
 * This class add panel to show the image and the check box. 
 *  
 * @version 1.0 
 * @author Daniel Cioi <dan@dancioi.net>
 */
class SelectorBox extends AbsolutePanel{
	private boolean isChecked;

	/**
	 * Constructor.
	 * @param img
	 * @param checkBox true if the checkBox should be shown.
	 */
	SelectorBox(Image img, boolean checkBox){
		initialize(img, checkBox);
	}

	/**
	 * Initialize.
	 * @param img thumbnails image 
	 * @param checkBox true if the checkBox should be shown.
	 */
	void initialize(Image img, boolean checkBox){
		setPixelSize(204,234);

		CheckBox cb = new CheckBox();
		cb.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				boolean checked = ((CheckBox) event.getSource()).getValue();
				setChecked(checked);
				//   Window.alert("It is " + (checked ? "" : "not ") + "checked");
			}
		});


		Grid g = new Grid(1,1);
		g.getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_CENTER);
		g.getCellFormatter().setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_MIDDLE);
		g.getCellFormatter().setHeight(0, 0, "202px");
		g.getCellFormatter().setWidth(0, 0, "202px");
		g.setWidget(0, 0, img);
		add(g, 1,16);
		if(checkBox){
			add(cb,1,210);
		}

	}

	/**
	 * Method to set checked variable.
	 * @param checked
	 */
	private void setChecked(boolean checked){
		isChecked = checked;
	}

	/**
	 * Method to get checked variable.
	 * @return
	 */
	public boolean getChecked(){
		return isChecked;
	}
	

	
}


