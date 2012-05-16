/*	
 * 	File    : PicturesImporter.java
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

import javax.imageio.ImageIO;
import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;
import javax.media.jai.RenderedOp;
import javax.media.jai.operator.SubsampleAverageDescriptor;

import net.dancioi.jcsphotogallery.client.shared.PictureBean;

/**
 * This class imports resizes and writes a picture .
 * 
 * @author Daniel Cioi <dan@dancioi.net>
 * @version $Revision$ Last modified: $Date$, by: $Author$
 */

public class PicturesImporter {
	private int pictureWidth = 1200;
	private int pictureHeight = 900;

	/**
	 * Gets picture by filepath.
	 * 
	 * @param picturePath
	 * @param maxSize
	 * @return BufferedImage
	 */
	public BufferedImage getPicture(String picturePath, int maxSize) {
		PlanarImage picture = loadPicture(picturePath);
		double scale = picture.getWidth() > picture.getHeight() ? (double) maxSize / picture.getHeight() : (double) maxSize / picture.getWidth();
		return resizePicture(picture, scale);
	}

	/*
	 * Loads picture by filepath.
	 */
	private PlanarImage loadPicture(String picturePath) {
		PlanarImage picture = null;
		try {
			picture = JAI.create("fileload", picturePath);
		} catch (IllegalArgumentException e) {
			picture = JAI.create("fileload", "help/imgNotFound.jpg");
		}
		return picture;
	}

	/*
	 * Resizes the picture with scale factor.
	 */
	public BufferedImage resizePicture(PlanarImage picture, double scale) {
		RenderingHints qualityHints = new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		RenderedOp resizedImage = SubsampleAverageDescriptor.create(picture, scale, scale, qualityHints);
		return resizedImage.getAsBufferedImage();
	}

	/**
	 * Adds picture to gallery.
	 * 
	 * @param sourcePath
	 * @param destinationFolder
	 */
	public PictureBean addPicture(File sourcePicture, File destinationFolder) {
		long fileName = System.currentTimeMillis() - 1336300000000l;
		PlanarImage picture = loadPicture(sourcePicture.getAbsolutePath());
		int width = picture.getWidth();
		int height = picture.getHeight();
		double scale = 1;
		scale = width > height ? (double) pictureWidth / width : (double) pictureHeight / height;
		writePicture(resizePicture(picture, scale), destinationFolder.getAbsolutePath() + File.separator + fileName + ".jpg");
		scale = width > height ? (double) 200 / width : (double) 150 / height;
		writeThumbnail(resizePicture(picture, scale), destinationFolder.getAbsolutePath() + File.separator + fileName + "T" + ".jpg");

		PictureBean pictureBean = new PictureBean(sourcePicture.getName(), fileName + ".jpg", "", fileName + "T.jpg");
		return pictureBean;
	}

	/*
	 * Writes the picture as jpeg file to gallery.
	 */
	private boolean writePicture(BufferedImage picture, String picturePath) {
		return writeImageWithJAI(picture, picturePath);
	}

	/*
	 * Writes the thumbnail.
	 */
	private boolean writeThumbnail(BufferedImage picture, String picturePath) {
		return writeImageWithJAI(picture, picturePath);
	}

	private boolean writeImageWithJAI(BufferedImage picture, String picturePath) {
		try {
			BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(picturePath));
			JAI.create("encode", picture, output, "JPEG", null);
			output.flush();
			output.close();
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}

	private boolean writeFileWithImageIO(BufferedImage img, String fileName) {
		try {
			File outputfile = new File(fileName);
			ImageIO.write(img, "jpg", outputfile);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;

	}

	public void copyToGallery() {
	}

}