/*	
 * 	File    : GalleryIO.java
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

package net.dancioi.jcsphotogallery.app.model;

import java.awt.image.BufferedImage;
import java.awt.image.renderable.ParameterBlock;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.media.jai.InterpolationNearest;
import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;
import javax.media.jai.RenderedOp;

/**
 * Gallery Input/Output.
 * 
 * @author Daniel Cioi <dan@dancioi.net>
 * @version $Revision$ Last modified: $Date: 2012-06-23 11:17:37 +0300
 *          (Sat, 23 Jun 2012) $, by: $Author$
 */
public class GalleryIO {

	/**
	 * Loads picture by filepath.
	 */
	protected PlanarImage loadPicture(String picturePath) {
		PlanarImage picture = null;
		try {
			picture = JAI.create("fileload", picturePath);
		} catch (IllegalArgumentException e) {
			URL resource = this.getClass().getResource("/icons/imgNotFound.png");
			picture = JAI.create("fileload", resource);
		}
		return picture;
	}

	/**
	 * Rotate operation about center reference point.
	 * 
	 * @param bufferedImage
	 * @param rotDegree
	 * @return
	 */
	protected BufferedImage rotatePicture(BufferedImage bufferedImage,
			int rotDegree) {
		float angle = (float) (rotDegree * (Math.PI / 180.0F));
		ParameterBlock pb = new ParameterBlock();
		pb.addSource(bufferedImage);
		pb.add(bufferedImage.getWidth() / 2f);
		pb.add(bufferedImage.getHeight() / 2f);
		pb.add(angle);
		pb.add(new InterpolationNearest());
		RenderedOp rotatedImage = JAI.create("Rotate", pb, null);
		return rotatedImage.getAsBufferedImage();
	}

	/**
	 * Writes the picture as jpeg file to gallery.
	 */
	protected boolean writePicture(BufferedImage picture, String picturePath) {
		return writeImageWithJAI(picture, picturePath);
	}

	/**
	 * Writes the thumbnail.
	 */
	protected boolean writeThumbnail(BufferedImage picture, String picturePath) {
		return writeImageWithJAI(picture, picturePath);
	}

	private boolean writeImageWithJAI(BufferedImage picture, String picturePath) {
		try {
			BufferedOutputStream output = new BufferedOutputStream(
					new FileOutputStream(picturePath));
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

	@SuppressWarnings("unused")
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

}
