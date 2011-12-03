package net.dancioi.jcsphotogallery.app;

import net.dancioi.jcsphotogallery.app.controller.JcsPhotoGalleryController;
import net.dancioi.jcsphotogallery.app.model.JcsPhotoGalleryModel;
import net.dancioi.jcsphotogallery.app.view.JcsPhotoGalleryView;

public class JcsPhotoGallery {
	
	private String osName;
	
	public JcsPhotoGallery(String osName){
		this.osName = osName;
		initialize();
	}
	
	private void initialize(){
		JcsPhotoGalleryModel model = new JcsPhotoGalleryModel();
		JcsPhotoGalleryView view = new JcsPhotoGalleryView(model);
		JcsPhotoGalleryController controller = new JcsPhotoGalleryController(model, view);
		view.setVisible(true);
	}
	

}
