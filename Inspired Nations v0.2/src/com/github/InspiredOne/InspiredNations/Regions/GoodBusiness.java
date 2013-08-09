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
import com.github.InspiredOne.InspiredNations.Regions.ChestShop;
import com.github.InspiredOne.InspiredNations.Regions.Cuboid;
import com.github.InspiredOne.InspiredNations.Regions.polygonPrism;

public class GoodBusiness extends Business{
	
	private Tools tools;
	private Vector<String> owners = new Vector<String>();
	private Vector<ChestShop> shops = new Vector<ChestShop>();
	private Vector<String> employmentrequest = new Vector<String>();
	private Vector<String> employmentoffers = new Vector<String>();
	private Vector<String> ownerrequest = new Vector<String>();
	private Vector<String> owneroffers = new Vector<String>();
	private Vector<String> employees = new Vector<String>();
	
	public GoodBusiness(InspiredNations instance, Cuboid space, Player owner, String countrytemp, int towntemp, String nametemp) {
		super(instance, space, owner, countrytemp, towntemp, nametemp);
		
	}
	
	public GoodBusiness(InspiredNations instance, Cuboid space, Vector<String> owner, String countrytemp, int towntemp, String nametemp) {
		super(instance, space, owner, countrytemp, towntemp, nametemp);
	}
	
	public GoodBusiness(InspiredNations instance, polygonPrism space, Player owner, String countrytemp, int towntemp, String nametemp) {
		super(instance, space, owner, countrytemp, towntemp, nametemp);
	}
	
	public GoodBusiness(InspiredNations instance, polygonPrism space, Vector<String> owner, String countrytemp, int towntemp, String nametemp) {
		super(instance, space, owner, countrytemp, towntemp, nametemp);
	}
			
	public void addChestShop(ChestShop shop) {
		shops.add(shop);
	}
	public void removeChestShop(ChestShop shop) {
		shops.remove(shop);
	}
	
	public Vector<ChestShop> getChestShop() {
		return shops;
	}
	
	public void removeOutsideShops() {
		for (ChestShop shop: shops) {
			if (shop.getDoubleChest()) {
				if (!(isIn(shop.getSpot()[0]) && isIn(shop.getSpot()[1]) && isIn(shop.getSpot()[2]) && isIn(shop.getSpot()[3]))) {
					removeChestShop(shop);
				}
			}
			else {
				if (!(isIn(shop.getSpot()[0]) && isIn(shop.getSpot()[2])&& isIn(shop.getSpot()[3]))) {
					removeChestShop(shop);
				}
			}
		}
	}
}
