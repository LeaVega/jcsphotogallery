package net.dancioi.jcsphotogallery.app.model;

import java.awt.image.BufferedImage;
import java.awt.image.renderable.ParameterBlock;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.media.jai.InterpolationNearest;
import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;
import javax.media.jai.RenderedOp;

/**
 * Gallery Input/Output.
 * 
 * @author Daniel Cioi <dan@dancioi.net>
 * @version $Revision$ Last modified: $Date$, by: $Author$
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
			picture = JAI.create("fileload", "help/imgNotFound.jpg");
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
	protected BufferedImage rotatePicture(BufferedImage bufferedImage, int rotDegree) {
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
