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


import org.bukkit.Location;
import org.bukkit.block.Chest;
import org.bukkit.inventory.DoubleChestInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;


public class ChestShop {

	private ItemStack itemtype;
	private double price;
	Location[] chests;
	private int quantity;
	
	// 1 and 2 for chests, 3 for sign, 4 for block sign is on.
	public ChestShop( ItemStack itemtypetemp, double cost,int quant, Location[] spots) {
		itemtype = itemtypetemp;
		price = cost;
		chests = spots.clone();
		quantity = quant;
	}
	
	public void setMaterial(ItemStack material) {
		itemtype = material;
	}
	
	public ItemStack getMaterial() {
		return itemtype;
	}
	
	public String getMaterialName() {
		
		return null;
		
	}
	
	public void setPrice(double pricetemp) {
		price = pricetemp;
	}
	
	public double getPrice() {
		return price;
	}
	
	public Location[] getSpot() {
		return chests;
	}
	
	public boolean getDoubleChest() {
		
		Inventory inventory = ((Chest) chests[0].getBlock()).getInventory();
		
		if(inventory instanceof DoubleChestInventory){
			return true;
		}
		else {
			return false;
		}
	}
	
	public void setQuantity(int quant) {
		quantity = quant;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public boolean isIn(Location spot) {
		if (this.getDoubleChest()) {
			if (spot.getBlock().getLocation().equals(chests[0])) {
				return true;
			}
			if (spot.getBlock().getLocation().equals(chests[1])) {

				return true;
			}
			if (spot.getBlock().getLocation().equals(chests[2])) {
				return true;
			}
			if (spot.getBlock().getLocation().equals(chests[3])) {
				return true;
			}
			return false;
		}
		else {
			if (spot.getBlock().getLocation().equals(chests[0])) {
				return true;
			}
			if (spot.getBlock().getLocation().equals(chests[2])) {
				return true;
			}
			if (spot.getBlock().getLocation().equals(chests[3])) {
				return true;
			}
			return false;
		}
	}
	
}
