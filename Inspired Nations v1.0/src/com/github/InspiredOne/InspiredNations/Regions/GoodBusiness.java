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

public class GoodBusiness extends InspiredRegion{
	
	private InspiredNations plugin;
	private Tools tools;
	private Vector<String> owners = new Vector<String>();
	private Vector<ChestShop> shops = new Vector<ChestShop>();
	private Vector<String> employmentrequest = new Vector<String>();
	private Vector<String> employmentoffers = new Vector<String>();
	private Vector<String> ownerrequest = new Vector<String>();
	private Vector<String> owneroffers = new Vector<String>();
	private Vector<String> employees = new Vector<String>();
	
	public GoodBusiness(InspiredNations instance, Cuboid space, Player owner, String countrytemp, int towntemp, String nametemp) {
		super(instance, space, countrytemp, towntemp, nametemp);
		
		plugin = instance;
		owners.add(owner.getName());
		tools = new Tools(plugin);
		countrytemp = tools.findCountry(countrytemp).get(0);
	}
	
	public GoodBusiness(InspiredNations instance, Cuboid space, Vector<String> owner, String countrytemp, int towntemp, String nametemp) {
		super(instance, space, countrytemp, towntemp, nametemp);
		plugin = instance;
		owners = owner;
		tools = new Tools(plugin);
		countrytemp = tools.findCountry(countrytemp).get(0);
	}
	
	public GoodBusiness(InspiredNations instance, polygonPrism space, Player owner, String countrytemp, int towntemp, String nametemp) {
		super(instance, space, countrytemp, towntemp, nametemp);
		plugin = instance;
		owners.add(owner.getName());
		tools = new Tools(plugin);
		countrytemp = tools.findCountry(countrytemp).get(0);
	}
	
	public GoodBusiness(InspiredNations instance, polygonPrism space, Vector<String> owner, String countrytemp, int towntemp, String nametemp) {
		super(instance, space, countrytemp, towntemp, nametemp);
		plugin = instance;
		owners = owner;
		tools = new Tools(plugin);
		countrytemp = tools.findCountry(countrytemp).get(0);
	}
		
	public void setOwner(Player owner) {
		owners.add(owner.getName());
	}
	
	public void setOwners(Vector<String> owner) {
		owners = owner;
	}
	
	public void addOwner(Player owner) {
		owners.add(owner.getName());
	}
	
	public void removeOwner(Player owner) {
		owners.remove(owner.getName());
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
	
	
	public void setEmployOffers(Vector<String> employoffers) {
		employmentoffers = employoffers;
	}
	
	public void addEmployOffer(String employname) {
		employname = tools.findPerson(employname).get(0);
		employmentoffers.add(employname);
	}
	
	public void removeEmployOffer(String employname) {
		employname = tools.findPerson(employname).get(0);
		employmentoffers.remove(employname);
	}
	
	public void setEmployRequests(Vector<String> employrequests) {
		employmentrequest = employrequests;
	}
	
	public void addEmployRequest(String employname) {
		employname = tools.findPerson(employname).get(0);
		employmentrequest.add(employname);
	}
	
	public void removeEmployRequest(String employname) {
		employname = tools.findPerson(employname).get(0);
		employmentrequest.remove(employname);
	}
	
	public void setOwnerOffers(Vector<String> ownoffers) {
		owneroffers = ownoffers;
	}
	
	public void addOwnerOffer(String ownername) {
		ownername = tools.findPerson(ownername).get(0);
		owneroffers.add(ownername);
	}
	
	public void removeOwnerOffer(String ownername) {
		ownername = tools.findPerson(ownername).get(0);
		owneroffers.remove(ownername);
	}
	
	public void setOwnerRequests(Vector<String> ownrequests) {
		ownerrequest = ownrequests;
	}
	
	public void addOwnerRequest(String ownername) {
		ownername = tools.findPerson(ownername).get(0);
		ownerrequest.add(ownername);
	}
	
	public void removeOwnerRequest(String ownername) {
		ownername = tools.findPerson(ownername).get(0);
		ownerrequest.remove(ownername);
	}
	
	public void setEmployees(Vector<String> employeestemp) {
		employees = employeestemp;
	}
	
	public void addEmployee(String employeename) {
		employeename = tools.findPerson(employeename).get(0);
		employees.add(employeename);
	}
	
	public void removeEmployee(String employeename) {
		employeename = tools.findPerson(employeename).get(0);
		employees.remove(employeename);
	}
	
	public Vector<String> getEmployOffers() {
		return employmentoffers;
	}
	
	public Vector<String> getEmployRequest() {
		return employmentrequest;
	}
	
	public Vector<String> getOwnerOffers() {
		return owneroffers;
	}
	
	public Vector<String> getOwnerRequest() {
		return ownerrequest;
	}
	
	public Vector<String> getEmployees() {
		return employees;
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
	
	public boolean isOwner(Player player) {
		if (owners.contains(player.getName())) return true;
		else return false;
	}
		
	public Vector<String> getOwners() {
		return owners;
	}
}
