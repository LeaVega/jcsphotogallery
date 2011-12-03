/*	
 * 	File    : SortAlbums.java
 * 
 * 	Copyright (C) 2010 Daniel Cioi <dan@dancioi.net>
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

package net.dancioi.jcsphotogallery.client;

import java.util.ArrayList;

/**
 * The class to sort the albums by categories.
 *  
 * @author Daniel Cioi <dan@dancioi.net>
 * @version $Revision$  Last modified: $Date$, by: $Author$
 */

public class SortAlbums {

	private Jcsphotogallery pg;

	private String[] albumCat1;		// first category selection
	private String[] albumCat2;		// second category selection

	private ArrayList<SortItem> item1 = new ArrayList<SortItem>();
	private ArrayList<SortItem> item2 = new ArrayList<SortItem>();

	private boolean[] albumVisible;

	public SortAlbums(Jcsphotogallery pg){
		this.pg = pg;	
	}

	/**
	 * Method to sort by two categories.
	 * @param albumCat1 first category elements
	 * @param albumCat2 second category elements
	 */
	public void sortAlbums(String[] albumCat1, String[] albumCat2){	
		this.albumCat1 = albumCat1;
		this.albumCat2 = albumCat2;
		albumVisible = new boolean[albumCat1.length];
		sort1(albumCat1);
		sort2(albumCat2);
		pg.addSortedCategories(getResult());
	}

	public void sortAllAlbums(String[][] categories){
		sortAlbums(categories[0], categories[1]);
	}
	
	/**
	 * Method to flag which albums will be visible.
	 * @param selected
	 * @return
	 */
	public boolean setVisibleAlbums(int selected){
		int b=0;
		int c=0;
		boolean doSort = true;
		for(int i=0;i<albumCat1.length;i++){
			if(selected==1)albumVisible[i]=true;
			else if(selected<3+item1.size() && selected>=3){
				if(item1.get(selected-3).getAlbumIndex(b)==i){
					albumVisible[i]=true;
					if(item1.get(selected-3).albumIndex.size()-1>b)b++;
				}
				else albumVisible[i]=false;
			}
			else if(selected>=3+item1.size()+1){
				if(item2.get(selected-3-item1.size()-1).getAlbumIndex(c)==i){
					albumVisible[i]=true;
					if(item2.get(selected-3-item1.size()-1).albumIndex.size()-1>c)c++;
				}
				else albumVisible[i]=false;
			}
			else{
				doSort=false;
				i = albumCat1.length;
			}
		}
//		pg.albums.showSorted(albumVisible);

		return doSort;
	}

	/**
	 * Method to get the categories list.
	 * @return a string with the results.
	 */
	private String[] getResult(){
		int ii=0;
		String[] result = new String[item1.size()+item2.size()+3];
		result[ii]= "All Albums";
		ii++;
		result[ii]= "----------------------------";
		ii++;
		for(int i=0;i<item1.size();i++){
			result[ii]= item1.get(i).getAlbumItem();
			ii++;
		}
		result[ii]= "----------------------------";
		ii++;
		for(int i=0;i<item2.size();i++){
			result[ii]= item2.get(i).getAlbumItem();
			ii++;
		}

		return result;
	}

	/**
	 * Method to sort first category.
	 * @param albumCat first category.
	 */
	private void sort1(String[] albumCat){	
		int a=0;
		for(int i=0;i<albumCat.length;i++){
			boolean newEl = true;
			for(int j=0; j<item1.size();j++){
				if(albumCat[i].equals(item1.get(j).getAlbumItem())){
					item1.get(j).addAlbumIndex(i);
					newEl = false;
				}
			}
			if(newEl){
				item1.add(new SortItem(albumCat[i]));
				item1.get(a).addAlbumIndex(i);
				a++;
			}
		}
	}

	/**
	 * Method to sort second category.
	 * @param albumCat second category.
	 */
	private void sort2(String[] albumCat){	
		int a=0;
		for(int i=0;i<albumCat.length;i++){
			boolean newEl = true;
			for(int j=0; j<item2.size();j++){
				if(albumCat[i].equals(item2.get(j).getAlbumItem())){
					item2.get(j).addAlbumIndex(i);
					newEl = false;
				}
			}
			if(newEl){
				item2.add(new SortItem(albumCat[i]));
				item2.get(a).addAlbumIndex(i);
				a++;
			}
		}
	}


	/**
	 * This class keeps the sort categories' results.
	 *
	 */
	class SortItem{
		String albumItem;
		ArrayList<Integer> albumIndex = new ArrayList<Integer>(); 
		public SortItem(String albumItem){
			this.albumItem = albumItem;
		}
		public void addAlbumIndex(int i){
			albumIndex.add(i);
		}

		public int getAlbumIndex(int j){
			return albumIndex.get(j);
		}

		public String getAlbumItem(){
			return albumItem;
		}
	}


}
