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
package com.github.InspiredOne.InspiredNations;

import java.util.HashMap;
import java.util.Iterator;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.github.InspiredOne.InspiredNations.Regions.ChestShop;
import com.github.InspiredOne.InspiredNations.Regions.Cuboid;
import com.github.InspiredOne.InspiredNations.Regions.Point3D;
import com.github.InspiredOne.InspiredNations.Regions.polygonPrism;


public class PlayerModes {

	private InspiredNations plugin;
	private String playername;
	
	private boolean map = false;
	private boolean selectHouse = false;
	private boolean selectGoodBusiness = false;
	private boolean selectServiceBusiness = false;
	private boolean selectLocalBank = false;
	private boolean selectLocalPrison = false;
	private boolean selectPark = false;
	private boolean selectFederalPark = false;
	private boolean preSelectFederalPark = false;
	private boolean selectCapital = false;
	private boolean preSelectTown = false;
	private boolean selectTown = false;
	private boolean preDeselectTown = false;
	private boolean deselectTown = false;
	private boolean preSelectCountry = false;
	private boolean selectCountry = false;
	private boolean preDeselectCountry = false;
	private boolean deselectCountry = false;
	private boolean reSelectHouse = false;
	private boolean reSelectGoodBusiness = false;
	private boolean reSelectServiceBusiness = false;
	private polygonPrism selectiontemp;
	private Cuboid selectiontemp2;
	private HashMap<Point3D, Byte> blocks;
	private HashMap<Point3D, Integer> blocktypes;
	private boolean polygon;
	private boolean cuboid;
	public Location point1;
	public Location point2;
	private boolean aloudSelect = true;
	
	
	// A bunch of shop making variables...
	public String itemname;
	public Integer quantity;
	private ItemStack itemtype;
	public double cost;
	public Location[] tempchests = new Location[4];
	public ChestShop tempshop;
	public Inventory items;
	public boolean placesign = false;
	private boolean placeitem = false;
	public boolean legalChest = false;
	public boolean legalItem = false;
	public String businessName;
	public boolean outside = false;
	public boolean onfallblock = false;
	public boolean againstoutside = false;
	public boolean legalsign = false;
	public boolean doublechest;
	public boolean alreadyChest = false;
	
	
	// Grabbing instance of plugin
	public PlayerModes(InspiredNations instance, String playernametemp) {
		plugin = instance;
		blocks = new HashMap<Point3D, Byte>();
		blocktypes = new HashMap<Point3D, Integer>();
		selectiontemp = new polygonPrism(plugin.getServer().getWorlds().get(0).getName());
		selectiontemp2 = new Cuboid(plugin.getServer().getWorlds().get(0).getName());
		playername = playernametemp;
	}
	
	// Setters
	public void house(boolean select) {
		selectHouse = select;
	}
	
	public void goodBusiness(boolean select) {
		selectGoodBusiness = select;
	}
	
	public void serviceBusiness(boolean select) {
		selectServiceBusiness = select;
	}
	
	public void localBank(boolean select) {
		selectLocalBank = select;
	}
	
	public void localPrison(boolean select) {
		selectLocalPrison = select;
	}
	
	public void park(boolean select) {
		selectPark = select;
	}
	
	public void federalPark(boolean select){
		selectFederalPark = select;
	}
	
	public void preFederalPark(boolean select) {
		preSelectFederalPark = select;
	}
	
	public void capital(boolean select) {
		selectCapital = select;
	}
	
	public void preTown(boolean select) {
		preSelectTown = select;
	}
	
	public void town(boolean select) {
		selectTown = select;
	}
	
	public void predetown(boolean select) {
		preDeselectTown = select;
	}
	
	public void detown(boolean select) {
		deselectTown = select;
	}
	
	public void preCountry(boolean select) {
		preSelectCountry = select;
	}
	
	public void country(boolean select) {
		selectCountry = select;
	}
	
	public void predecountry(boolean select) {
		preDeselectCountry = select;
	}
	
	public void decountry(boolean select) {
		deselectCountry = select;
	}
	
	public void setPolygon(polygonPrism poly) {
		selectiontemp = poly;
	}
	
	public void setBlocks(HashMap<Point3D, Byte> blocktemp) {
		blocks = blocktemp;
	}
	
	public void setItemType(ItemStack stuff) {
		itemtype = stuff;
	}
	
	public void addBlock(Point3D spot, Block blocktemp) {
		Location test = new Location(plugin.getServer().getWorld(spot.getWorld()), spot.x, spot.y, spot.z);
		if (test.getBlock().getTypeId() != 7 && test.getBlock().getTypeId() != 54 && test.getBlock().getTypeId() != 52) {
			blocks.put(spot,  blocktemp.getData());
			blocktypes.put(spot, blocktemp.getTypeId());
		}
	}
	
	public void selectPolygon(boolean select) {
		polygon = select;
	}
	
	public void selectCuboid(boolean select) {
		cuboid = select;
	}
	
	public void setCuboid(Cuboid select) {
		selectiontemp2 = select;
	}
	
	public void setAloudSelect(boolean select) {
		aloudSelect = select;
	}
	
	public void setPlaceItem(boolean place) {
		placeitem = place;
	}
	
	// Getters
	public InspiredNations getPlugin() {
		return plugin;
	}
	
	public ItemStack getItemType() {
		return itemtype;
	}
	
	public boolean houseSelect() {
		return selectHouse;
	}
	
	public boolean goodBusinessSelect() {
		return selectGoodBusiness;
	}
	
	public boolean serviceBusinessSelect() {
		return selectServiceBusiness;
	}
	
	public boolean localBankSelect() {
		return selectLocalBank;
	}
	
	public boolean localPrisonSelect() {
		return selectLocalPrison;
	}
	
	public boolean parkSelect() {
		return selectPark;
	}
	
	public boolean federalParkSelect() {
		return selectFederalPark;
	}
	
	public boolean preFederalParkSelect() {
		return preSelectFederalPark;
	}
	
	public boolean capitalSelect() {
		return selectCapital;
	}
	
	public boolean preTownSelect() {
		return preSelectTown;
	}
	
	public boolean townSelect() {
		return selectTown;
	}
	
	public boolean preDeselectTown() {
		return preDeselectTown;
	}
	
	public boolean townDeselect() {
		return deselectTown;
	}
	
	public boolean preCountrySelect() {
		return preSelectCountry;
	}
	
	public boolean countrySelect() {
		return selectCountry;
	}
	
	public boolean preDeselectCountry() {
		return preDeselectCountry;
	}
	
	public boolean countryDeselect() {
		return deselectCountry;
	}
	
	public polygonPrism getPolygon() {
		return selectiontemp;
	}
	
	public HashMap<Point3D, Byte> getBlocks() {
		return blocks;
	}
	
	public boolean getAloudSelect() {
		return aloudSelect;
	}
	
	public boolean getPlaceItem() {
		return placeitem;
	}
	
	public void setBlocksBack() {
		for (Iterator<Point3D> i = blocks.keySet().iterator(); i.hasNext();) {
			Point3D spot = i.next();
			Location spot2 = new Location(plugin.getServer().getWorld(spot.world) , spot.x, spot.y, spot.z);
			spot2.getBlock().setTypeIdAndData(blocktypes.get(spot), blocks.get(spot), true);
		}
		blocks.clear();
	}
	
	public boolean isSelectingPolygon() {
		return polygon;
	}
	
	public boolean isSelectingCuboid() {
		return cuboid;
	}
	
	public Cuboid getCuboid() {
		return selectiontemp2;
	}
	
	public boolean hasPoint1() {
		try {
			if (point1.equals(null)) {
				return false;
			}
			else return true;
		}
		catch (Exception ex) {
			return false;
		}
	}
	
	public boolean hasPoint2() {
		try {
			if (point2.equals(null)) {
				return false;	
			}
			else return true;
		}
		catch (Exception ex) {
			return false;
		}
	}
	
	public void clear() {
		setBlocksBack();
		selectiontemp = null;
		selectiontemp2 = null;
		point1 = null;
		point2 = null;
		blocks = new HashMap<Point3D, Byte>();
		blocktypes = new HashMap<Point3D, Integer>();
	}

	public String getPlayername() {
		return playername;
	}

	public void setPlayername(String playername) {
		this.playername = playername;
	}

	public boolean isMap() {
		return map;
	}

	public void setMap(boolean map) {
		this.map = map;
	}

	public boolean isReSelectHouse() {
		return reSelectHouse;
	}

	public void setReSelectHouse(boolean reSelectHouse) {
		this.reSelectHouse = reSelectHouse;
	}

	public boolean isReSelectGoodBusiness() {
		return reSelectGoodBusiness;
	}

	public void setReSelectGoodBusiness(boolean reSelectGoodBusiness) {
		this.reSelectGoodBusiness = reSelectGoodBusiness;
	}

	public boolean isReSelectServiceBusiness() {
		return reSelectServiceBusiness;
	}

	public void setReSelectServiceBusiness(boolean reSelectServiceBusiness) {
		this.reSelectServiceBusiness = reSelectServiceBusiness;
	}
}

