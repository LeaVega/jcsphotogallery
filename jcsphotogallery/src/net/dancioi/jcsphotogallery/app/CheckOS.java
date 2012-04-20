/*	
 * 	File    : CheckOS.java
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

package net.dancioi.jcsphotogallery.app;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Class to check the OS.
 * 
 * @author Daniel Cioi <dan.cioi@vrforcad.org>
 * @version $Revision$ Last modified: $Date: 2012-03-20 22:39:16 +0200
 *          (Tue, 20 Mar 2012) $, by: $Author$
 */
public class CheckOS {
	
	/*
	* this gets rid of exception for not using native acceleration (JAI)
	*/
	static
	{
	System.setProperty("com.sun.media.jai.disableMediaLib", "true");
	}

	/**
	 * Checks the OS.
	 * @return
	 */
	public String getOS() {
		// get the OS name;
		String osName = System.getProperties().getProperty("os.name");
		if (osName.contains("Mac")) {

			// put the menuBar on the Mac's menuBar;
			System.setProperty("apple.laf.useScreenMenuBar", "true");
			System.setProperty("com.apple.mrj.application.growbox.intrudes", "false");
			System.setProperty("com.apple.mrj.application.apple.menu.about.name", "JcsPhotoGallery");

			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (UnsupportedLookAndFeelException e) {
				e.printStackTrace();
			}

		}
		return osName;
	}
}
