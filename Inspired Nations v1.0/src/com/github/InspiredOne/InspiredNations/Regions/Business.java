package com.github.InspiredOne.InspiredNations.Regions;

import java.util.Vector;

import org.bukkit.entity.Player;

import com.github.InspiredOne.InspiredNations.InspiredNations;

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

	}

	public Business(InspiredNations instance, Cuboid space, Vector<String> owner, String countrytemp, int towntemp, String nametemp) {
		super(instance, space, countrytemp, towntemp, nametemp);
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
}
