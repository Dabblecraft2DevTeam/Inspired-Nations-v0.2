package com.github.InspiredOne.InspiredNations.Regions;

import java.math.BigDecimal;
import java.util.Vector;

import org.bukkit.entity.Player;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.PlayerMethods;
import com.github.InspiredOne.InspiredNations.Tools.version;


public class House extends InspiredRegion{
	
	private Vector<String> coownerrequest = new Vector<String>();
	private Vector<String> coowneroffer = new Vector<String>();
	private Vector<String> owners = new Vector<String>();
	
	public House(InspiredNations instance, Cuboid space, Player owner, String countrytemp, int towntemp, String nametemp) {
		super(instance, space, countrytemp, towntemp, nametemp);
		owners.add(owner.getName());

	}
	
	public House(InspiredNations instance, Cuboid space, Vector<String> owner, String countrytemp, int towntemp, String nametemp) {
		super(instance, space, countrytemp, towntemp, nametemp);
		owners = owner;
	}
	
	public House(InspiredNations instance, polygonPrism space, Player owner, String countrytemp, int towntemp, String nametemp) {
		super(instance, space, countrytemp, towntemp, nametemp);
		owners.add(owner.getName());
	}
	
	public House(InspiredNations instance, polygonPrism space, Vector<String> owner, String countrytemp, int towntemp, String nametemp) {
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
	
	public void removeOwner(Player owner) {
		owners.remove(owner.getName());
	}
	
	public void setOwnerOffer(Player owner) {
		coowneroffer.add(owner.getName());
	}
	
	public void setOwnerOffers(Vector<String> owner) {
		coowneroffer = owner;
	}
	
	public void addOwnerOffer(Player owner) {
		coowneroffer.add(owner.getName());
	}
	
	public void removeOwnerOffer(Player owner) {
		coowneroffer.remove(owner.getName());
	}
	
	public void setOwneRequest(Player owner) {
		coownerrequest.add(owner.getName());
	}
	
	public void setOwnerRequests(Vector<String> owner) {
		coownerrequest = owner;
	}
	
	public void addOwnerRequest(Player owner) {
		coownerrequest.add(owner.getName());
	}
	
	public void removeOwnerRequest(Player owner) {
		coownerrequest.remove(owner.getName());
	}
	
	public boolean isOwner(Player player) {
		if (owners.contains(player.getName())) return true;
		else return false;
	}
	
	public Vector<String> getOwners() {
		return owners;
	}
	public Vector<String> getOwnerOffers() {
		return coowneroffer;
	}
	public Vector<String> getOwnerRequest() {
		return coownerrequest;
	}

	@Override
	public void changeProtectionLevel(int level) {
		try {
			
			BigDecimal oldtax;
			BigDecimal newtax;
			PlayerMethods PM = new PlayerMethods(plugin, this.getOwners().get(0));

			oldtax = PM.houseTax(this, true, false, version.OLD);
			newtax = PM.houseTax(this, level, true, false, version.OLD);
			
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
