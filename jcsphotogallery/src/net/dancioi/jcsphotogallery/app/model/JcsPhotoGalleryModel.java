/*	
 * 	File    : JcsPhotoGalleryModel.java
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

package net.dancioi.jcsphotogallery.app.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import net.dancioi.jcsphotogallery.client.model.Albums;


/**
 * JcsPhotoGallery's Model.
 *  
 * @author Daniel Cioi <dan@dancioi.net>
 * @version $Revision$  Last modified: $Date$, by: $Author$
 */
public class JcsPhotoGalleryModel implements JcsPhotoGalleryModelInterface{
	
	private Configs  configs;
	private File galleryPath;
	private FileXML fileXML;
	
	public JcsPhotoGalleryModel(){
		initialize();
	}

	
	private void initialize(){
		getPreviousConfigs();
		fileXML = new FileXML();
	}
	
	/**
	 * Method to get the previous configuration.
	 * If it's first time when the application run, then create a default configs object.
	 */
	private void getPreviousConfigs(){
		try {
			FileInputStream fis = new FileInputStream(new File("configs.cfg"));
			ObjectInputStream ois = new ObjectInputStream(fis);
			configs = (Configs)ois.readObject();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		if(configs==null) configs = new Configs(); 
	}
	
	
	/**
	 * Method to save on configs.cfg file the current settings
	 */
	private void setCurrentSettings(){
		try {
			FileOutputStream fos = new FileOutputStream(new File("configs.cfg"));
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(configs);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	/**
	 * Method to save the application's settings.
	 */
	public void saveSettings(){
		setCurrentSettings();
	}
	
	
	/**
	 * Method to get the current configs (settings).
	 * @return
	 */
	public Configs getConfigs() {
		return configs;
	}

	/**
	 * Method to modify application's configs (settings).
	 * @param configs
	 */
	public void setConfigs(Configs configs) {
		this.configs = configs;
	}


	@Override
	public void setGalleryPath(File galleryPath) {
		this.galleryPath = galleryPath;
		//getGalleryAlbums();
	}

	@Override
	public Albums getGalleryAlbums(){
		fileXML = new FileXML();
		return fileXML.getAlbums(galleryPath);
	}

}
