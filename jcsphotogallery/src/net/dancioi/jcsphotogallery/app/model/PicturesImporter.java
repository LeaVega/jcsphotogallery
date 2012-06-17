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
import java.io.File;

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

public class PicturesImporter extends GalleryIO {
	private Configs configs;

	public PicturesImporter(Configs configs) {
		this.configs = configs;
	}

	/**
	 * Gets picture by filepath.
	 * 
	 * @param picturePath
	 * @param maxSize
	 * @return BufferedImage
	 */
	public BufferedImage getPicture(String picturePath, int maxSize, int rotDegree) {
		System.out.println("PICTURE PATH = " + picturePath);
		PlanarImage picture = loadPicture(picturePath);
		double scale = picture.getWidth() > picture.getHeight() && rotDegree % 180 == 0 ? maxSize / (double) picture.getHeight() : maxSize / (double) picture.getWidth();
		BufferedImage resizePicture = resizePicture(picture, scale);
		if (rotDegree == 0)
			return resizePicture;
		else
			return rotatePicture(resizePicture, rotDegree);

	}

	/*
	 * Resizes the picture with scale factor.
	 */
	private BufferedImage resizePicture(PlanarImage picture, double scale) {
		System.out.println("SCALE=" + scale);
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
		scale = width > height ? configs.getPictureDimension().getWidth() / (double) width : configs.getPictureDimension().getHeight() / (double) height;
		writePicture(resizePicture(picture, scale), destinationFolder.getAbsolutePath() + File.separator + fileName + ".jpg");
		scale = width > height ? 200 / (double) width : 150 / (double) height;
		writeThumbnail(resizePicture(picture, scale), destinationFolder.getAbsolutePath() + File.separator + "T" + fileName + ".jpg");

		String[] pictureName = sourcePicture.getName().toLowerCase().split(".jpg");
		PictureBean pictureBean = new PictureBean(pictureName[0], fileName + ".jpg", "", "T" + fileName + ".jpg");
		return pictureBean;
	}

}