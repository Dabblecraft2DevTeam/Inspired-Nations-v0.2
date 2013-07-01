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

import java.util.Vector;


import org.bukkit.entity.Player;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.Tools;

public class ServiceBusiness extends Business{
	
	public ServiceBusiness(InspiredNations instance, Cuboid space, Player owner, String countrytemp, int towntemp, String nametemp) {
		super(instance, space, owner, countrytemp, towntemp, nametemp);
	}
	
	public ServiceBusiness(InspiredNations instance, Cuboid space, Vector<String> owner, String countrytemp, int towntemp, String nametemp) {
		super(instance, space, owner, countrytemp, towntemp, nametemp);
	}
	
	public ServiceBusiness(InspiredNations instance, polygonPrism space, Player owner, String countrytemp, int towntemp, String nametemp) {
		super(instance, space, owner, countrytemp, towntemp, nametemp);
	}
	
	public ServiceBusiness(InspiredNations instance, polygonPrism space, Vector<String> owner, String countrytemp, int towntemp, String nametemp) {
		super(instance, space, owner, countrytemp, towntemp, nametemp);
	}	
}
