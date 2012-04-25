/*	
 * 	File    : ViewHandlers.java
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

import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.ScrollEvent;
import com.google.gwt.user.client.Window.ScrollHandler;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * App's handlers.
 * 
 * @author Daniel Cioi <dan@dancioi.net>
 * @version $Revision: 41 $ Last modified: $Date: 2012-03-20 22:39:16 +0200 (Tue, 20 Mar 2012) $, by: $Author: dan.cioi $
 */
public class ViewHandlers {

	private BottomPanel bottomPanel;
	private VersionPanel versionPanel;
	private int currentScrollPosition = 0;
	private String browser;

	public ViewHandlers(BottomPanel bottomPanel, VersionPanel versionPanel) {
		this.bottomPanel = bottomPanel;
		this.versionPanel = versionPanel;
		browser = getUserAgent();
		addResizeBrowserListener();
	}

	private native String getUserAgent() /*-{
		return navigator.userAgent.toLowerCase();
	}-*/;

	private void addResizeBrowserListener() {
		keepBottomPanelVisible(Window.getClientWidth(), Window.getClientHeight(), currentScrollPosition);

		Window.addResizeHandler(new ResizeHandler() {

			@Override
			public void onResize(ResizeEvent event) {
				keepBottomPanelVisible(event.getWidth(), event.getHeight(), currentScrollPosition);
			}

		});

		Window.addWindowScrollHandler(new ScrollHandler() {

			@Override
			public void onWindowScroll(ScrollEvent event) {
				currentScrollPosition = event.getScrollTop();
				keepBottomPanelVisible(Window.getClientWidth(), Window.getClientHeight() + event.getScrollTop(), 0);
			}
		});
	}

	/*
	 * keeps the bottom panel (control) always visible (for screens with low resolution).
	 */
	private void keepBottomPanelVisible(int width, int height, int scrollPosition) {
		int heightLimit = 810; // in mozilla firefox should be 795, otherwise IE and Chrome are ok.
		if (browser.contains("firefox")) {
			heightLimit = 795;
		}
		// GWT.log("height = " + height);
		if (height < heightLimit) {
			RootPanel.get("bottomPanel").setWidgetPosition(bottomPanel, width / 2 - 400 + 5, height - 40 + scrollPosition);
		} else {
			RootPanel.get("bottomPanel").setWidgetPosition(bottomPanel, width / 2 - 400 + 5, heightLimit - 40);
			RootPanel.get("versionPanel").setWidgetPosition(versionPanel, width / 2 - 400 + 5, heightLimit);
		}
	}

}
