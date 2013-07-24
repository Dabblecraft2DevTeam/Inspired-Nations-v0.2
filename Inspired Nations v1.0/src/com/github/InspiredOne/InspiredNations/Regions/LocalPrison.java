/*******************************************************************************
 * Copyright (c) 2013 InspiredOne.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *     InspiredOne - initial API and implementation
 ******************************************************************************/
package com.github.InspiredOne.InspiredNations.Regions;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.Regions.Cuboid;
import com.github.InspiredOne.InspiredNations.Regions.polygonPrism;;

public class LocalPrison extends InspiredRegion {
	HashMap<String, Cell> cells = new HashMap<String, Cell>();
	
	public LocalPrison(InspiredNations instance, Cuboid space, String countrytemp, int towntemp) {
		super(instance, space, countrytemp, towntemp, "");
	}
	
	public LocalPrison(InspiredNations instance, polygonPrism space, String countrytemp, int towntemp) {
		super(instance, space, countrytemp, towntemp, "");
	}
	
	
	public HashMap<String, Cell> getCells() {
		return cells;
	}
	
	public void addCell(String name, Cell spot) {
		cells.put(name, spot);
	}
	
	public void removeCell(String name) {
		cells.remove(name);
	}
		
	public Vector<String> getOccupants() {
		Vector<String> occupants = new Vector<String>();
		for (Iterator<String> i = cells.keySet().iterator(); i.hasNext();) {
			occupants.addAll(cells.get(i.next()).getOccupant());
		}
		return occupants;
	}

	@Override
	public void changeProtectionLevel(int level) {
		// TODO Auto-generated method stub
		
	}
}
