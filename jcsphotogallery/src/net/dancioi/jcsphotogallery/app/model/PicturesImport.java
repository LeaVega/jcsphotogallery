/*	
 * 	File    : PicturesImport.java
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

import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.media.jai.JAI;
import javax.media.jai.RenderedOp;

/**
 * This class .
 * 
 * @author Daniel Cioi <dan@dancioi.net>
 * @version $Revision$ Last modified: $Date: 2011-12-03 13:07:01 +0200
 *          (Sat, 03 Dec 2011) $, by: $Author$
 */

public class PicturesImport {

	private int pictureWidth = 1200;
	private int pictureHeight = 900;

	public BufferedImage getPicture(String picturePath, int maxSize) {
		BufferedImage picture = loadPicture(picturePath);
		// double scale = picture.getWidth() > picture.getHeight() ? (double)
		// maxSize / picture.getWidth() : (double) maxSize /
		// picture.getHeight();
		double scale = 0.5; // TODO solve
							// java.lang.ArrayIndexOutOfBoundsException:
							// Coordinate out of bounds!
		return resizePicture(picture, scale);
	}

	private BufferedImage loadPicture(String picturePath) {
		BufferedImage picture = JAI.create("fileload", picturePath).getAsBufferedImage();
		return picture;
	}

	public BufferedImage resizePicture(BufferedImage image, double scale) {
		RenderingHints qualityHints = new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

		RenderedOp resizedImage = JAI.create("SubsampleAverage", image, scale, scale, qualityHints);

		return resizedImage.getAsBufferedImage();
	}

	public void addPicture(String sourcePath, String destinationFolder) {
		long fileName = System.currentTimeMillis() - 10000000;
		BufferedImage picture = loadPicture(sourcePath);
		int width = picture.getWidth();
		int height = picture.getHeight();
		double scale = 1;
		scale = width > height ? width / 200 : height / 150;
		writeThumbnail(resizePicture(picture, scale), destinationFolder + File.separator + fileName + "T" + ".jpg");
		scale = width > height ? width / pictureWidth : height / pictureHeight;
		writePicture(resizePicture(picture, scale), destinationFolder + File.separator + fileName + ".jpg");
	}

	private void writePicture(BufferedImage picture, String picturePath) {
		try {
			BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(picturePath));
			JAI.create("encode", picture, output, "JPEG", null);
			output.flush();
			output.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void writeThumbnail(BufferedImage picture, String picturePath) {
		writePicture(picture, picturePath);
	}

	public void copyToGallery() {
	}

}