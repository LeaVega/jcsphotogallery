/*	
 * 	File    : PageController.java
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

package net.dancioi.jcsphotogallery.client.view;

/**
 * Page navigator on bottom side.
 * 
 * @author Daniel Cioi <dan@dancioi.net>
 * @version $Revision: 41 $ Last modified: $Date: 2012-03-20 22:39:16 +0200 (Tue, 20 Mar 2012) $, by: $Author: dan.cioi $
 */
public interface PageController {

	void previousPageEvent();

	void nextPageEvent();

	void upPagesEvent();

}
