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

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.Regions.Cuboid;
import com.github.InspiredOne.InspiredNations.Regions.polygonPrism;

public class LocalBank extends InspiredRegion{

	
	public LocalBank(InspiredNations instance, Cuboid space, String countrytemp, int towntemp) {
		super(instance, space, countrytemp, towntemp, "bank");
	}
	
	public LocalBank(InspiredNations instance, polygonPrism space, String countrytemp, int towntemp) {
		super(instance, space, countrytemp, towntemp, "bank");
	}
	
}
