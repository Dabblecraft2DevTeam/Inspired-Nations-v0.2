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

import java.math.BigDecimal;
import java.util.Vector;


import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.github.InspiredOne.InspiredNations.Regions.Country;
import com.github.InspiredOne.InspiredNations.Regions.Cuboid;
import com.github.InspiredOne.InspiredNations.Regions.GoodBusiness;
import com.github.InspiredOne.InspiredNations.Regions.House;
import com.github.InspiredOne.InspiredNations.Regions.Park;
import com.github.InspiredOne.InspiredNations.Regions.ServiceBusiness;
import com.github.InspiredOne.InspiredNations.Regions.Town;
import com.github.InspiredOne.InspiredNations.Regions.polygonPrism;

public class PlayerMethods {

	InspiredNations plugin;
	Player player;
	PlayerData PDI;
	String playername;
	
	public PlayerMethods(InspiredNations instance, Player playertemp) {
		plugin = instance;
		player = playertemp;
		PDI = plugin.playerdata.get(player.getName());
		playername = player.getName();
	}
	
	public PlayerMethods(InspiredNations instance, String playernametemp) {
		plugin = instance;
		playername = playernametemp;
		PDI = plugin.playerdata.get(playername);
	}
	
	public BigDecimal taxAmount() {
		return (houseTax().add(goodBusinessTax()).add(serviceBusinessTax()));
	}
	public BigDecimal taxAmount(String town){
		return (houseTax(town).add(goodBusinessTax(town)).add(serviceBusinessTax(town)));
	}
	
	public BigDecimal houseTax() {
		Vector<House> Houses = PDI.getHouseOwned();
		Country country = PDI.getCountryResides();
		BigDecimal amount = BigDecimal.ZERO;
		for (int i=0; i<Houses.size(); i++) {
			House house = Houses.get(i);
			amount = amount.add(new BigDecimal(country.getTowns().get(house.getTown()).getHouseTax()).multiply(new BigDecimal(house.Volume()*house.getProtectionLevel()).divide(new BigDecimal((house.getOwners().size()*100.0)))));
		}
		return amount;
	}
	
	// Returns the total houseTax to be paid to the given town at the begining of each tax cycle. Answer given in player's units
	public BigDecimal houseTax(Object obj, Town town, int level) {
		BigDecimal amount = BigDecimal.ZERO;
		
		if (obj instanceof Cuboid) {
			obj = (Cuboid) obj;
			amount = new BigDecimal(((Cuboid) obj).Volume()*town.getHouseTax() * level/100.0);
			
		}
		else if (obj instanceof polygonPrism) {
			obj = (polygonPrism) obj;
			amount = new BigDecimal((((polygonPrism) obj).Volume()*town.getHouseTax()*level/100.0));
		}
		return amount.multiply(PDI.getMoneyMultiplyer());
	}
	public BigDecimal houseTax(House house) {
		Country country = PDI.getCountryResides();
		BigDecimal amount = BigDecimal.ZERO;
		if (house.isCubeSpace()) {
			amount = houseTax(house.getCubeSpace(), country.getTowns().get(house.getTown()), house.getProtectionLevel()).divide(new BigDecimal(house.getOwners().size()));
		}
		else {
			amount = houseTax(house.getPolySpace(), country.getTowns().get(house.getTown()), house.getProtectionLevel()).divide(new BigDecimal(house.getOwners().size()));
		}
		return amount;
	}
	public BigDecimal houseTax(House house, int level) {
		Country country = PDI.getCountryResides();
		BigDecimal amount = BigDecimal.ZERO;
		if (house.isCubeSpace()) {
			amount = houseTax(house.getCubeSpace(), country.getTowns().get(house.getTown()), level).divide(new BigDecimal(house.getOwners().size()));
		}
		else {
			amount = houseTax(house.getPolySpace(), country.getTowns().get(house.getTown()), level).divide(new BigDecimal(house.getOwners().size()));
		}
		return amount;
	}
	public BigDecimal houseTax(String town) {
		Vector<House> Houses = PDI.getHouseOwned();
		Country country = PDI.getCountryResides();
		BigDecimal amount = BigDecimal.ZERO;
		for (int i=0; i<Houses.size(); i++) {
			House house = Houses.get(i);
			if (PDI.getCountryResides().getTowns().get(house.getTown()).getName().equalsIgnoreCase(town)){
				if (house.isCubeSpace()) {
					amount = amount.add(houseTax(house.getCubeSpace(), country.getTowns().get(house.getTown()), house.getProtectionLevel()).divide(new BigDecimal(house.getOwners().size())));
				}
				else {
					amount = amount.add(houseTax(house.getPolySpace(), country.getTowns().get(house.getTown()), house.getProtectionLevel()).divide(new BigDecimal(house.getOwners().size())));
				}
			}
		}
		return amount;
	}
	
	// Returns the total GoodBusinessTax to be paid to the given town at the begining of each tax cycle. Answer given in player's units
	public BigDecimal goodBusinessTax(Object obj, Town town, int level) {
		BigDecimal amount = BigDecimal.ZERO;
		
		if (obj instanceof Cuboid) {
			obj = (Cuboid) obj;
			amount = new BigDecimal(((Cuboid) obj).Volume()*town.getGoodBusinessTax() * level/100.0);
			
		}
		else if (obj instanceof polygonPrism) {
			obj = (polygonPrism) obj;
			amount = new BigDecimal((((polygonPrism) obj).Volume()*town.getGoodBusinessTax()*level/100.0));
		}
		return amount.multiply(PDI.getMoneyMultiplyer());
	}
	
	public BigDecimal goodBusinessTax() {
		Vector<GoodBusiness> businesses = PDI.getGoodBusinessOwned();
		Country country = PDI.getCountryResides();
		BigDecimal amount = BigDecimal.ZERO;
		for (int i=0; i<businesses.size(); i++) {
			GoodBusiness business = businesses.get(i);
			if (business.isCubeSpace()) {
				amount = amount.add(goodBusinessTax(business.getCubeSpace(), country.getTowns().get(business.getTown()), business.getProtectionLevel()).divide(new BigDecimal(business.getOwners().size())));
			}
			else {
				amount = amount.add(goodBusinessTax(business.getPolySpace(), country.getTowns().get(business.getTown()), business.getProtectionLevel()).divide(new BigDecimal(business.getOwners().size())));
			}
		}
		return amount;
	}
	public BigDecimal goodBusinessTax(GoodBusiness business) {
		Country country = PDI.getCountryResides();
		BigDecimal amount = BigDecimal.ZERO;
		
		if (business.isCubeSpace()) {
			amount = amount.add(goodBusinessTax(business.getCubeSpace(), country.getTowns().get(business.getTown()), business.getProtectionLevel()).divide(new BigDecimal(business.getOwners().size())));
		}
		else {
			amount = amount.add(goodBusinessTax(business.getPolySpace(), country.getTowns().get(business.getTown()), business.getProtectionLevel()).divide(new BigDecimal(business.getOwners().size())));
		}
		return amount;
	}
	public BigDecimal goodBusinessTax(GoodBusiness business, int level) {
		Country country = PDI.getCountryResides();
		BigDecimal amount = BigDecimal.ZERO;
		if (business.isCubeSpace()) {
			amount = amount.add(goodBusinessTax(business.getCubeSpace(), country.getTowns().get(business.getTown()), level).divide(new BigDecimal(business.getOwners().size())));
		}
		else {
			amount = amount.add(goodBusinessTax(business.getPolySpace(), country.getTowns().get(business.getTown()), level).divide(new BigDecimal(business.getOwners().size())));
		}
		return amount;
	}
	public BigDecimal goodBusinessTax(String town) {
		Vector<GoodBusiness> businesses = PDI.getGoodBusinessOwned();
		Country country = PDI.getCountryResides();
		BigDecimal amount = BigDecimal.ZERO;
		for (int i=0; i<businesses.size(); i++) {
			GoodBusiness business = businesses.get(i);
			if (PDI.getCountryResides().getTowns().get(business.getTown()).getName().equalsIgnoreCase(town)){
				if (business.isCubeSpace()) {
					amount = amount.add(goodBusinessTax(business.getCubeSpace(), country.getTowns().get(business.getTown()), business.getProtectionLevel()).divide(new BigDecimal(business.getOwners().size())));
				}
				else {
					amount = amount.add(goodBusinessTax(business.getPolySpace(), country.getTowns().get(business.getTown()), business.getProtectionLevel()).divide(new BigDecimal(business.getOwners().size())));
				}
			}
		}
		return amount;
	}
	
	// Returns the total ServiceBusinessTax to be paid to the given town at the beginning of each tax cycle. Answer given in player's units
	public BigDecimal serviceBusinessTax(Object obj, Town town, int level) {
		BigDecimal amount = BigDecimal.ZERO;
		
		if (obj instanceof Cuboid) {
			obj = (Cuboid) obj;
			amount = new BigDecimal(((Cuboid) obj).Volume()*town.getServiceBusinessTax() * level/100.0);
			
		}
		else if (obj instanceof polygonPrism) {
			obj = (polygonPrism) obj;
			amount = new BigDecimal((((polygonPrism) obj).Volume()*town.getServiceBusinessTax()*level/100.0));
		}
		return amount.multiply(PDI.getMoneyMultiplyer());
	}
	
	public BigDecimal serviceBusinessTax() {
		Vector<ServiceBusiness> businesses = PDI.getServiceBusinessOwned();
		Country country = PDI.getCountryResides();
		BigDecimal amount = BigDecimal.ZERO;
		for (int i=0; i<businesses.size(); i++) {
			ServiceBusiness business = businesses.get(i);
			if (business.isCubeSpace()) {
				amount = amount.add(goodBusinessTax(business.getCubeSpace(), country.getTowns().get(business.getTown()), business.getProtectionLevel()).divide(new BigDecimal(business.getOwners().size())));
			}
			else {
				amount = amount.add(goodBusinessTax(business.getPolySpace(), country.getTowns().get(business.getTown()), business.getProtectionLevel()).divide(new BigDecimal(business.getOwners().size())));
			}
		}
		return amount;
	}
	public BigDecimal serviceBusinessTax(ServiceBusiness business) {
		Country country = PDI.getCountryResides();
		BigDecimal amount = BigDecimal.ZERO;
		if (business.isCubeSpace()) {
			amount = amount.add(goodBusinessTax(business.getCubeSpace(), country.getTowns().get(business.getTown()), business.getProtectionLevel()).divide(new BigDecimal(business.getOwners().size())));
		}
		else {
			amount = amount.add(goodBusinessTax(business.getPolySpace(), country.getTowns().get(business.getTown()), business.getProtectionLevel()).divide(new BigDecimal(business.getOwners().size())));
		}
		return amount;
	}
	public BigDecimal serviceBusinessTax(ServiceBusiness business, int level) {
		Country country = PDI.getCountryResides();
		BigDecimal amount = BigDecimal.ZERO;
		if (business.isCubeSpace()) {
			amount = amount.add(goodBusinessTax(business.getCubeSpace(), country.getTowns().get(business.getTown()), level).divide(new BigDecimal(business.getOwners().size())));
		}
		else {
			amount = amount.add(goodBusinessTax(business.getPolySpace(), country.getTowns().get(business.getTown()), level).divide(new BigDecimal(business.getOwners().size())));
		}
		return amount;
	}
	public BigDecimal serviceBusinessTax(String town) {
		Vector<ServiceBusiness> businesses = PDI.getServiceBusinessOwned();
		Country country = PDI.getCountryResides();
		BigDecimal amount = BigDecimal.ZERO;
		for (int i=0; i<businesses.size(); i++) {
			ServiceBusiness business = businesses.get(i);
			if (PDI.getCountryResides().getTowns().get(business.getTown()).getName().equalsIgnoreCase(town)){
				if (business.isCubeSpace()) {
					amount = amount.add(goodBusinessTax(business.getCubeSpace(), country.getTowns().get(business.getTown()), business.getProtectionLevel()).divide(new BigDecimal(business.getOwners().size())));
				}
				else {
					amount = amount.add(goodBusinessTax(business.getPolySpace(), country.getTowns().get(business.getTown()), business.getProtectionLevel()).divide(new BigDecimal(business.getOwners().size())));
				}
			}
		}
		return amount;
	}
	
	public int protectedVolume() {
		return houseVolume() + goodBusinessVolume() + serviceBusinessVolume();
	}
	
	public int houseVolume() {
		Vector<House> Houses = PDI.getHouseOwned();
		int HouseVolume = 0;
		for (int i = 0; i < Houses.size(); i++) {
			HouseVolume = HouseVolume + Houses.get(i).Volume();
		}
		return HouseVolume;
	}
	
	public int goodBusinessVolume() {
		Vector<GoodBusiness> GoodBusinesses = PDI.getGoodBusinessOwned();
		int GoodBusinessVolume = 0;
		for (int i = 0; i < GoodBusinesses.size(); i++) {
			GoodBusinessVolume = GoodBusinessVolume + GoodBusinesses.get(i).Volume();
		}
		return GoodBusinessVolume;
	}
	
	public int serviceBusinessVolume() {
		Vector<ServiceBusiness> ServiceBusinesses = PDI.getServiceBusinessOwned();
		int ServiceBusinessVolume = 0;
		for (int i = 0; i < ServiceBusinesses.size(); i++) {
			ServiceBusinessVolume = ServiceBusinessVolume + ServiceBusinesses.get(i).Volume();
		}
		return ServiceBusinessVolume;
	}
	
	public int protectedArea() {
		return houseArea() + goodBusinessArea() + serviceBusinessArea();
	}
	
	public int houseArea() {
		Vector<House> Houses = PDI.getHouseOwned();
		int HouseArea = 0;
		for (int i = 0; i < Houses.size(); i++) {
			HouseArea = HouseArea + Houses.get(i).Area();
		}
		return HouseArea;
	}
	
	public int goodBusinessArea() {
		Vector<GoodBusiness> GoodBusinesses = PDI.getGoodBusinessOwned();
		int GoodBusinessArea = 0;
		for (int i = 0; i < GoodBusinesses.size(); i++) {
			GoodBusinessArea = GoodBusinessArea + GoodBusinesses.get(i).Area();
		}
		return GoodBusinessArea;
	}
	
	public int serviceBusinessArea() {
		Vector<ServiceBusiness> ServiceBusinesses = PDI.getServiceBusinessOwned();
		int ServiceBusinessArea = 0;
		for (int i = 0; i < ServiceBusinesses.size(); i++) {
			ServiceBusinessArea = ServiceBusinessArea + ServiceBusinesses.get(i).Area();
		}
		return ServiceBusinessArea;
	}
	
	public boolean leaveCountry() {
		if (PDI.getIsCountryRuler() || PDI.getIsTownMayor() || !PDI.getIsCountryResident()) return false;
		Country countryFrom = PDI.getCountryResides();
		PDI.setCountryResides(null);
		if (PDI.getIsTownResident()) {
			leaveTown();
		}
		countryFrom.removeResident(playername);
		if (countryFrom.getCoRulers().contains(playername)) {
			countryFrom.removeCoRuler(playername);
		}
		PDI.setPluralMoney(plugin.getConfig().getString("plural_money_default"));
		PDI.setSingularMoney(plugin.getConfig().getString("singular_money_default"));
		PDI.setMoneyMultiplyer(1);
		return true;
	}
	
	public boolean leaveTown() {
		if (PDI.getIsTownMayor() || !PDI.getIsTownResident()) return false;
		Town townFrom = PDI.getTownResides();
		PDI.setTownResides(null);
		if (PDI.isHouseOwner()) {
			for (int i = 0; i < PDI.getHouseOwned().size(); i++) {
				if (PDI.getHouseOwned().get(i).getOwners().size() == 1) {
					townFrom.removeHouse(PDI.getHouseOwned().get(i));
				}
				else {
					PDI.getHouseOwned().get(i).removeOwner(player);
				}

			}
			PDI.setHouseOwned(new Vector<House>());
		}
		if (PDI.isGoodBusinessOwner()) {
			for (int i = 0; i < PDI.getGoodBusinessOwned().size(); i++) {
				if (PDI.getGoodBusinessOwned().get(i).getOwners().size() == 1) {
					townFrom.removeGoodBusiness(PDI.getGoodBusinessOwned().get(i));
				}
				else {
					PDI.getGoodBusinessOwned().get(i).removeOwner(player);
				}

			}
			PDI.setGoodBusinessOwned(new Vector<GoodBusiness>());
		}
		if (PDI.isServiceBusinessOwner()) {
			for (int i = 0; i < PDI.getServiceBusinessOwned().size(); i++) {
				if (PDI.getServiceBusinessOwned().get(i).getOwners().size() == 1) {
					townFrom.removeServiceBusiness(PDI.getServiceBusinessOwned().get(i));
				}
				else {
					PDI.getServiceBusinessOwned().get(i).removeOwner(player);
				}
			}
			PDI.setServiceBusinessOwned(new Vector<ServiceBusiness>());
		}
		townFrom.removeResident(playername);
		return true;
	}
	
	public boolean joinTown(Town townTo) {
		if (PDI.getIsTownResident() || !PDI.getIsCountryResident()) return false;
		PDI.setTownResides(townTo);
		PDI.setHouseTax(townTo.getHouseTax());
		PDI.setGoodBusinessTax(townTo.getGoodBusinessTax());
		PDI.setServiceBusinessTax(townTo.getServiceBusinessTax());
		townTo.addResident(playername);
		return true;
	}
	
	public boolean joinCountry(Country countryTo) {
		if (PDI.getIsCountryResident() || PDI.getIsCountryRuler() || PDI.getIsTownMayor()) return false;
		PDI.setCountryResides(countryTo);
		PDI.setPluralMoney(countryTo.getPluralMoney());
		PDI.setSingularMoney(countryTo.getSingularMoney());
		PDI.setMoneyMultiplyer(countryTo.getMoneyMultiplyer());
		countryTo.addResident(playername);
		return true;
	}
	
	public boolean transferTown(Town townTo) {
		leaveTown();
		return joinTown(townTo);
	}
	
	public boolean transferCountry(Country countryTo) {
		leaveCountry();
		return joinCountry(countryTo);
	}
	public void setLocationBooleansFalse() {
		PDI.setCountryIn(null);
		PDI.setTownIn(null);
		PDI.setHouseIn(null);
		PDI.setLocalParkIn(null);
		PDI.setLocalBankIn(null);
		PDI.setLocalParkIn(null);
		PDI.setInCapital(false);
		PDI.setGoodBusinessIn(null);
		PDI.setServiceBusinessIn(null);
		PDI.setLocalPrisonIn(null);
	}
	public void resetLocationBooleans() {
		Location spot = player.getLocation();
		this.setLocationBooleansFalse();
		// Country
		for (String name: plugin.countrydata.keySet()) {
			Country country = plugin.countrydata.get(name);
			if (country.isIn(spot)) {
				PDI.setCountryIn(country);
				// Town
				for(Town town: PDI.getCountryIn().getTowns()) {
					if (town.isIn(spot)) {
						PDI.setTownIn(town);
						// Capital
						if(town.isCapital()) {
							PDI.setInCapital(true);
						}
						// Bank
						if(town.hasBank()) {
							if(town.getBank().isIn(spot)) {
								PDI.setLocalBankIn(town.getBank());
							}
						}
						// Prison
						if(town.hasPrison()) {
							if(town.getPrison().isIn(spot)) {
								PDI.setLocalPrisonIn(town.getPrison());
							}
						}
						// Parks
						for(Park park:town.getParks()) {
							if(park.isIn(spot)) {
								PDI.setLocalParkIn(park);
							}
						}
						// House
						for(House house:town.getHouses()) {
							if(house.isIn(spot)) {
								PDI.setHouseIn(house);
							}
						}
						// Good Business
						for(GoodBusiness business:town.getGoodBusinesses()) {
							if(business.isIn(spot)) {
								PDI.setGoodBusinessIn(business);
							}
						}
						// Service Business
						for(ServiceBusiness business:town.getServiceBusinesses()) {
							if(business.isIn(spot)) {
								PDI.setServiceBusinessIn(business);
							}
						}
						break;
					}
				}
				// Federal Park
				for(Park park:country.getParks()) {
					if(park.isIn(spot)) {
						PDI.setFederalParkIn(park);
						break;
					}
				}
				break;
			}
			
		}
	}
}
