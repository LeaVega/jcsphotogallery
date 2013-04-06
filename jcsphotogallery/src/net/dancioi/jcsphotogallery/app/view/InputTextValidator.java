/*	
 * 	File    : InputTextValidator.java
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

/**
 * Input text validator to prevent interfering with xml files.
 * 
 * @author Daniel Cioi <dan@dancioi.net>
 * @version $Revision$ Last modified: $Date$, by: $Author$
 */
public class InputTextValidator {

	private static CharSequence[] invalidCharacters = new CharSequence[] { "<", ">", "\"", "&", "'" }; // xml predefined entities.

	/**
	 * Validate text to do not contain xml entities.
	 * 
	 * @param text
	 * @return true if valid, otherwise false
	 */
	public static boolean validateText(String text) {
		for (CharSequence invalidCharacter : invalidCharacters) {
			boolean contains = text.contains(invalidCharacter);
			if (contains)
				return false;
		}
		return true;
	}
}
