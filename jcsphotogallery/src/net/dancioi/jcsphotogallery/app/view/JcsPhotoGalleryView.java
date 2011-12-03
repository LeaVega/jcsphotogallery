/*	
 * 	File    : JcsPhotoGalleryView.java
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

package net.dancioi.jcsphotogallery.app.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import net.dancioi.jcsphotogallery.app.model.Configs;
import net.dancioi.jcsphotogallery.app.model.JcsPhotoGalleryModel;

/**
 * JcsPhotoGallery's View
 *  
 * @author Daniel Cioi <dan@dancioi.net>
 * @version Revision: $Revision$  Last modified: $Date$  Last modified by: $Author$
 */

public class JcsPhotoGalleryView extends JFrame{

	private static final long serialVersionUID = 1L;
	private JcsPhotoGalleryModel model;
	private PanelLeft  panelLeft;
	private PanelTop  panelTop;
	private PanelBottom  panelBottom;
	private PanelCenter  panelCenter;
	private Configs  configs;

	/**
	 * Default constructor.
	 */
	public JcsPhotoGalleryView(JcsPhotoGalleryModel model){
		super("JcsPhotoGallery");
		this.model = model;
		initialize();
	}
	
	/**
	 * Initialize.
	 */
	private void initialize() {
		this.setSize(1200, 700);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setContentPane(getRootPanel());
		this.setJMenuBar(getMenu());
		this.setVisible(true);
		getPreviousConfigs();		
	}
	
	/**
	 * Method to get the JFrame's ContentPane.
	 * @return JPanel
	 */
	private JPanel getRootPanel(){
		JPanel rootPanel = new JPanel();
		rootPanel.setLayout(new BorderLayout());
		rootPanel.add(addLeftPanel(), BorderLayout.WEST);
		
		JPanel rightPanel = new JPanel();
		rightPanel.add(addTopPanel(), BorderLayout.NORTH);
		rightPanel.add(addBottomPanel(), BorderLayout.SOUTH);
		rightPanel.add(addCenterPanel(), BorderLayout.CENTER);
		
		rootPanel.add(rightPanel);

		return rootPanel;
	}
	
	private JMenuBar getMenu(){
		JMenuBar menuBar = new JMenuBar();
		JMenu menuFile = new JMenu("File");
		menuFile.add(getMenuOpenGallery());
		menuFile.add(getMenuSaveGallery());
		menuFile.addSeparator();
		menuFile.add(getMenuExit());
		
		menuFile.setMnemonic(KeyEvent.VK_F);
		
		JMenu menuTools = new JMenu("Tools");
		menuTools.setMnemonic(KeyEvent.VK_T);
		
		JMenu menuHelp = new JMenu("Help");
		menuHelp.setMnemonic(KeyEvent.VK_H);
		menuHelp.add(getMenuAbout());
		
		menuBar.add(menuFile);
		menuBar.add(menuTools);
		menuBar.add(menuHelp);
		return menuBar;
	}
	
	private JMenuItem getMenuOpenGallery(){
		JMenuItem menuOpenGallery = new JMenuItem("Open Gallery");
		menuOpenGallery.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO here
				
			}
		});
		return menuOpenGallery;
	}
	
	private JMenuItem getMenuSaveGallery(){
		JMenuItem menuSaveGallery = new JMenuItem("Save Gallery");
		menuSaveGallery.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO here
				
			}
		});
		return menuSaveGallery;
	}
	
	private JMenuItem getMenuExit(){
		JMenuItem menuExit = new JMenuItem("Exit");
		menuExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO here
				
			}
		});
		return menuExit;
	}
	
	private JMenuItem getMenuAbout(){
		JMenuItem menuAbout = new JMenuItem("About...");
		menuAbout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO here
				
			}
		});
		return menuAbout;
	}
	
	
	
	/**
	 * Method to get the previous configuration.
	 * If it's first time when the application run, then create a default configs object.
	 */
	private void getPreviousConfigs(){
		try {
			FileInputStream fis = new FileInputStream(new File("configs.cfg"));
			ObjectInputStream ois = new ObjectInputStream(fis);
			configs = (Configs)ois.readObject();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		if(configs==null) configs = new Configs(); 
	}

	/**
	 * Method to save on configs.cfg file the current settings
	 */
	private void setCurrentSettings(){
		try {
			FileOutputStream fos = new FileOutputStream(new File("configs.cfg"));
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(configs);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	/**
	 * Method to save the application's settings.
	 */
	public void saveSettings(){
		setCurrentSettings();
	}
	
	
	/**
	 * Method to get the current configs (settings).
	 * @return
	 */
	public Configs getConfigs() {
		return configs;
	}

	/**
	 * Method to modify application's configs (settings).
	 * @param configs
	 */
	public void setConfigs(Configs configs) {
		this.configs = configs;
	}

	/**
	 * Center panel where the picture is shown.
	 * @return
	 */
	private JPanel addCenterPanel() {
		panelCenter = new PanelCenter();
		return panelCenter;
	}
	
	/**
	 * Left panel with the gallery tree structure.
	 * @return JPanel
	 */
	private JPanel addLeftPanel() {
		panelLeft = new PanelLeft();
		return panelLeft;
	}

	/**
	 * Top panel.
	 * @return JPanel
	 */
	private JPanel addTopPanel() {
		panelTop = new PanelTop();
		return panelTop;
	}

	/**
	 * Bottom Panel.
	 * @return JPanel
	 */
	private JPanel addBottomPanel() {
		panelBottom = new PanelBottom();
		return panelBottom;
	}

	public List getUpdatedGallery() {
		return null;
	}

}