/*	
 * 	File    : PopUpImgShow.java
 * 
 * 	Copyright (C) 2010 Daniel Cioi <dan@dancioi.net>
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

package net.dancioi.jcsphotogallery.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;

/**
 * This class shows the selected image 
 * (from the center panel) on a PopUp panel.
 *  
 * @author Daniel Cioi <dan@dancioi.net>
 * @version Revision: $Revision$  Last modified: $Date$  Last modified by: $Author$
 */

public class PopUpImgShow extends PopupGeneric{

//TODO set the autoplay on popUp (by user).

	private AbsolutePanel ap;
	private int popUpSizeX;
	private int popUpSizeY;
	private AbsolutePanel imgPanel;
	private int imgPanelSizeX;
	private int imgPanelSizeY;
	private AbsolutePanel bottomPanel;

	private PictureBean[] pictures;
	
	private int imgStart;				// image id from to start.
	private String imgPath; 			// images path
	private ImagePopUp img; 			// image which will be shown.
	
	private Image next;
	private Image previous;
	private Image close;
	
	
	private Label lImgName;				// image name
	private Label lImgComment;			// image comment

	private int currentImg;
	
	/* add a loading message		*/
	private String loading;
	private Label loadingLabel;
	private int ind = 1;
	private Timer t;
	
	/* cache the next and previous image		*/
	private Image imgCacheN;
	private Image imgCacheP;
	
	
	//Button play;				// button AUTO PLAY
	private Image play;
	private boolean playFlag = true;
	private boolean autoPlayOn = false;
	private Timer tPlay;
	private Label playMode;
	
	

	public PopUpImgShow(int imgStart, String imgPath, PictureBean[] pictures){
		super();
		this.imgStart = imgStart;
		this.imgPath = imgPath;
		this.pictures = pictures;
		currentImg = imgStart;
		initialize();
	}

	/**
	 * Initialize
	 */
	private void initialize(){
		setGlassStyleName("gwt-PopupPanelGlass");
		setGlassEnabled(true); 
		
		getMaximSquareSize();
		
		setPosition();

		ap = new AbsolutePanel();	
		ap.setPixelSize(popUpSizeX, popUpSizeY);	
		ap.setStyleName("popUpPanel");

		imgPanel = new AbsolutePanel();
		imgPanelSizeX = popUpSizeX-40;
		imgPanelSizeY = popUpSizeY-40;
		imgPanel.setPixelSize(imgPanelSizeX, imgPanelSizeY);
		ap.add(imgPanel, 20,1);
		
		bottomPanel = new AbsolutePanel();
		bottomPanel.setPixelSize(popUpSizeX, 50);
		
		next = new Image("ext/next.gif");
		next.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if(!playFlag){autoPlay(false);play.setUrl("ext/play.gif");playFlag = true;}
				nextImg();}} );
		bottomPanel.add(next, popUpSizeX-110, 5);
		
		previous = new Image("ext/previous.gif");
		previous.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if(!playFlag){autoPlay(false);play.setUrl("ext/play.gif");playFlag = true;}
				previousImg();}} );
		bottomPanel.add(previous, popUpSizeX-160, 5);
		
		close = new Image("ext/close.gif");
		close.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				closeImg();}} );
		bottomPanel.add(close, popUpSizeX-40, 5);	
		
		play = new Image("ext/play.gif");
		play.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if(playFlag && currentImg < pictures.length-1){
					autoPlay(true);
					play.setUrl("ext/pause.gif");
					playFlag = false;
				}
				else{
					stopAutoPlay();
				}
				}} );
		bottomPanel.add(play, popUpSizeX-240, 5);

		lImgName = new Label();
		bottomPanel.add(lImgName, 20,0);

		lImgComment = new Label();
		bottomPanel.add(lImgComment, 20,20);


		loading = "Loading...";
		loadingLabel = new Label();
		loadingLabel.setText(loading);
		bottomPanel.add(loadingLabel, popUpSizeX/2,2);
		
		playMode = new Label();
		bottomPanel.add(playMode, popUpSizeX/2,22);
		
		ap.add(bottomPanel, 1, popUpSizeY-40);

		setWidget(ap);

		addImage(imgPath+pictures[imgStart].getFileName());

		checkStartImg();
	}

	
	private void stopAutoPlay(){
		autoPlay(false);
		play.setUrl("ext/play.gif");
		playFlag = true;
	}
	
	
	/**
	 * Method to show the next image.
	 */
	public void nextImg(){
		showLoadingProcess(false);
		if(currentImg<(pictures.length-1)){
			currentImg++;
			checkButtons(currentImg);
			addImage(imgPath+pictures[currentImg].getFileName());
		}
	}

	/**
	 * Method to show the previous image.
	 */
	public void previousImg(){
		showLoadingProcess(false);
		if(currentImg>0){
			currentImg--;
			checkButtons(currentImg);
			addImage(imgPath+pictures[currentImg].getFileName());
		}
	}

	/**
	 * Method to check if the selected image is first or the last from album;
	 * disable the buttons according.
	 * @param id image id
	 */
	private void checkButtons(int id){
		next.setVisible(true);
		previous.setVisible(true);
		if(id==(pictures.length-1)){next.setVisible(false);
			if(autoPlayOn){stopAutoPlay();playMode.setText("Last Picture");}
		}
		if(id==0) previous.setVisible(false);
	}


	
	
	
	/**
	 * check if the image is first or the last.
	 * disable the button according.
	 */
	private void checkStartImg(){
		checkButtons(currentImg);
	}

	/**
	 * Method to close the popup.
	 */
	public void closeImg(){
		if(autoPlayOn)autoPlay(false);
		this.hide();
	}

	/**
	 * Method to add image to the panel.
	 * Because the image size method from the image class can't return the values
	 * until the image is fully loaded by browser, this method add the image
	 * on the popup panel's bottom right corner (making the image invisible and
	 * creating the background loading effect). When the image is complete downloaded, 
	 * get the image's size and the it's added to the popup panel.
	 */
	private void addImage(String imagePath){
		showLoadingProcess(true);
		img = new ImagePopUp(imagePath, this);
		imgPanel.add(img, imgPanelSizeX, imgPanelSizeY);
	}

	/**
	 * scale the image to fit the panel
	 */
	public void scaleImg(Image im){
		showLoadingProcess(false);
		
		int iox = im.getWidth();
		int ioy = im.getHeight();

		float scaleX = (float)imgPanelSizeX/iox;
		float scaleY = (float)imgPanelSizeY/ioy;
		
		float scalef = scaleX<=scaleY ? scaleX:scaleY;
		int ix = (int)(iox*scalef);
		int iy = (int)(ioy*scalef);
		img.setPixelSize(ix, iy);

		lImgName.setText(pictures[currentImg].getName());
		lImgComment.setText(pictures[currentImg].getDescription());
		
		clearImg();
		imgPanel.add(img, (imgPanelSizeX-ix)/2, (imgPanelSizeY-iy)/2);
		
		getNextAndPrevious(currentImg);	// cache the next and previous pictures;
	}


	/**
	 * Method to get the maxim popup size. The dimension should be the same (square).
	 */
	private void getMaximSquareSize(){
		getWindowSize();
		int maxX = getBrowserWindowWidth();
		int maxY = getBrowserWindowHeight();
		if(maxX<=maxY){
			popUpSizeX = maxY;
			popUpSizeY = maxY;
		}
		else{
			popUpSizeY = maxY;
			if(maxX>maxY*1.33) popUpSizeX = (int) (maxY*1.33);
			else popUpSizeX = maxX;
		}
		
		popUpSizeX-=30;
		popUpSizeY-=30;
		setSizeX(popUpSizeX);
		setSizeY(popUpSizeY);
	}
	
	/**
	 * Method to clear the old image before add the new one.
	 */
	private void clearImg(){
		imgPanel.clear();
	}
	
	/**
	 * This method shows the picture's loading process.
	 */
	private void showLoadingProcess(boolean show){
		if(show){
			// show the loading bar

			ind = 1;
			loadingLabel.setText(loading.substring(0, ind));
			
			t = new Timer() {
			      @Override
			      public void run() {
			    	  if(ind<loading.length()){
							ind++;
						}
						else{
							ind=1;
						}
						loadingLabel.setText(loading.substring(0, ind));
			      }
			    };
			
			    t.scheduleRepeating(500);
		}
		else{
			// hide the loading bar
			t.cancel();
			loadingLabel.setText("");
		}
	}
	
	/**
	 * Cache the next and previous pictures to reduce the loading time. 
	 * @param i picture id
	 */
	private void getNextAndPrevious(int i){
		if(i<pictures.length-1)imgCacheN = new Image(imgPath+pictures[i+1].getFileName());
		if(i>0)imgCacheP = new Image(imgPath+pictures[i-1].getFileName());
	}

	/**
	 * AutoPlay method.
	 * @param flag 
	 */
	private void autoPlay(boolean flag){
		autoPlayOn = flag;
		if(flag){	
			playMode.setText("Auto Play Mode");
			tPlay = new Timer() {
			      @Override
			      public void run() {
			    	  nextImg();
			      }
			    };
			    tPlay.scheduleRepeating(8000);	// wait 8 seconds between pictures. 
		}
		else{
			playMode.setText("");
			tPlay.cancel();
		}
	}
	
}
