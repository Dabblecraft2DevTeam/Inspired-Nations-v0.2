package com.github.InspiredOne.InspiredNations.Regions;

import java.math.BigDecimal;
import java.util.Vector;

import org.bukkit.entity.Player;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.PlayerMethods;
import com.github.InspiredOne.InspiredNations.Tools.version;

public class Business extends InspiredRegion{
	
	
	Vector<String> owners = new Vector<String>();
	Vector<String> employmentrequest = new Vector<String>();
	Vector<String> employmentoffers = new Vector<String>();
	Vector<String> ownerrequest = new Vector<String>();
	Vector<String> owneroffers = new Vector<String>();
	Vector<String> employees = new Vector<String>();
	
	public Business(InspiredNations instance, Cuboid space, Player owner,  String countrytemp, int towntemp, String nametemp) {
		super(instance, space, countrytemp, towntemp, nametemp);
		owners.add(owner.getName());
	}

	public Business(InspiredNations instance, polygonPrism space, Player owner, String countrytemp, int towntemp, String nametemp) {
		super(instance, space, countrytemp, towntemp, nametemp);
		owners.add(owner.getName());
	}
	
	public Business(InspiredNations instance, polygonPrism space, Vector<String> owner,  String countrytemp, int towntemp, String nametemp) {
		super(instance, space, countrytemp, towntemp, nametemp);
		owners = owner;
	}

	public Business(InspiredNations instance, Cuboid space, Vector<String> owner, String countrytemp, int towntemp, String nametemp) {
		super(instance, space, countrytemp, towntemp, nametemp);
		owners = owner;
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
	
	public void addOwner(String owner) {
		owners.add(owner);
	}
	
	public void removeOwner(Player owner) {
		owners.remove(owner.getName());
	}
	
	public void removeOwner(String owner) {
		owners.remove(owner);
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
	public boolean isOwner(Player player) {
		if (owners.contains(player.getName())) return true;
		else return false;
	}
		
	public Vector<String> getOwners() {
		return owners;
	}

	@Override
	public void changeProtectionLevel(int level) {
	try {
		
		BigDecimal oldtax;
		BigDecimal newtax;
		PlayerMethods PM = new PlayerMethods(plugin, this.getOwners().get(0));
		if(this instanceof GoodBusiness) {
			oldtax = PM.goodBusinessTax(this, true, false, version.OLD);
			newtax = PM.goodBusinessTax(this, level, true, false, version.OLD);
		}
		else {
			oldtax = PM.serviceBusinessTax(this, true, false, version.OLD);
			newtax = PM.serviceBusinessTax(this, level, true, false, version.OLD);
		}
		BigDecimal fraction = new BigDecimal(plugin.taxTimer.getFractionLeft());
		BigDecimal difference;
		
		oldtax = oldtax.multiply(BigDecimal.ONE.subtract(fraction));
		newtax = newtax.multiply(fraction);
		
		difference = oldtax.subtract(newtax);
		
		for(String owner:this.owners) {
			PlayerData PDI = plugin.playerdata.get(owner);
			Town town = plugin.countrydata.get(this.getCountry()).getTowns().get(this.town);
			
			if(difference.compareTo(BigDecimal.ZERO) > 0) {
				town.transferMoney(difference, owner);
			}
			else {
				PDI.transferMoneyToTown(difference.negate(), town.getName(), this.country);
			}
		}

	} catch (Exception e) {

	}
		
	}
}
