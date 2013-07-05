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

import com.github.InspiredOne.InspiredNations.Tools.version;
import com.github.InspiredOne.InspiredNations.Regions.Business;
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
	Tools tools;
	
	public PlayerMethods(InspiredNations instance, Player playertemp) {
		plugin = instance;
		player = playertemp;
		PDI = plugin.playerdata.get(player.getName());
		playername = player.getName();
		tools = new Tools(plugin);
	}
	
	public PlayerMethods(InspiredNations instance, String playernametemp) {
		plugin = instance;
		playername = playernametemp;
		PDI = plugin.playerdata.get(playername);
		tools = new Tools(plugin);
	}
	
	public BigDecimal taxAmount(boolean adjusted, boolean total, version ver) {
		return (houseTax(adjusted, total, ver).add(goodBusinessTax(adjusted, total, ver)).add(serviceBusinessTax(adjusted, total, ver)));
	}
	public BigDecimal taxAmount(String townname, boolean adjusted, boolean total, version ver){
		Country country = PDI.getCountryResides();
		Town town = tools.findTown(country, townname).get(0);
		BigDecimal amount = (houseTax(town, adjusted, total, ver).add(goodBusinessTax(town, adjusted, total, ver)).add(serviceBusinessTax(town, adjusted, total, ver)));
		if(adjusted) {
			return tools.cut(amount);
		}
		else {
			return amount;
		}
	}
	
	public void SendChat(String msg) {
		for(Player player_target: plugin.getServer().getOnlinePlayers()) {
			player_target.sendRawMessage(player.getDisplayName() + ": " + msg);
		}
	}

	public BigDecimal houseTax(Object obj, Town town, int level, boolean adjusted, version ver) {
		BigDecimal amount = BigDecimal.ZERO;
		plugin.logger.info(ver + "");
		switch(ver){
		case OLD:
			if (obj instanceof Cuboid) {
				obj = (Cuboid) obj;
				amount = new BigDecimal(((Cuboid) obj).Volume()*PDI.getOldHouseTax() * level/100.0);
				
			}
			else if (obj instanceof polygonPrism) {
				obj = (polygonPrism) obj;
				amount = new BigDecimal((((polygonPrism) obj).Volume()*PDI.getOldHouseTax()*level/100.0));
			}
			break;
		case NEW:
			plugin.logger.info("here");
			plugin.logger.info((obj instanceof Cuboid) + "");
			if (obj instanceof Cuboid) {
				obj = (Cuboid) obj;
				plugin.logger.info(((Cuboid) obj).Volume() + "");
				plugin.logger.info(town.getHouseTax() + "");
				plugin.logger.info(level + "");
				amount = new BigDecimal(((Cuboid) obj).Volume()*town.getHouseTax() * level/100.0);
			}
			else if (obj instanceof polygonPrism) {
				obj = (polygonPrism) obj;
				amount = new BigDecimal((((polygonPrism) obj).Volume()*town.getHouseTax()*level/100.0));
			}
			break;
		default:
			plugin.logger.info("broken");
			break;
		}
		if(adjusted) {
			return tools.cut(amount.multiply(PDI.getMoneyMultiplyer()));
		}
		else return amount;
	}
	
	public BigDecimal houseTax(House house, int level, boolean adjusted, boolean total, version ver) {
		Country country = plugin.countrydata.get(house.getCountry());
		Town town = country.getTowns().get(house.getTown());
		BigDecimal amount = BigDecimal.ZERO;
		if (total) {
			amount = this.houseTax(house.getRegion(), town, level, adjusted, ver);
		}
		else {
			amount = this.houseTax(house.getRegion(), town, level, adjusted, ver).divide(new BigDecimal(house.getOwners().size()));
		}
		if(adjusted) {
			return tools.cut(amount);
		}
		else {
			return amount;
		}
	}
	
	public BigDecimal houseTax(House house, boolean adjusted, boolean total, version ver) {
		BigDecimal amount = this.houseTax(house, house.getProtectionLevel(), adjusted, total, ver);
		if(adjusted) {
			return tools.cut(amount);
		}
		else {
			return amount;
		}
	}
	
	public BigDecimal houseTax(Town town, boolean adjusted, boolean total, version ver) {
		BigDecimal amount = BigDecimal.ZERO;
		for(House house:town.getHouses()) {
			amount = amount.add(houseTax(house, adjusted, total, ver));
		}
		if(adjusted) {
			return tools.cut(amount);
		}
		else {
			return amount;
		}
	}
	public BigDecimal houseTax(boolean adjusted, boolean total, version ver) {
		BigDecimal amount = BigDecimal.ZERO;
		for(House house:PDI.getHouseOwned()) {
			amount = amount.add(houseTax(house, adjusted, total, ver));
		}
		if(adjusted) {
			return tools.cut(amount);
		}
		else {
			return amount;
		}
	}

/////////////////////////////////////////////////////////////////
	public BigDecimal goodBusinessTax(Object obj, Town town, int level, boolean adjusted, version ver) {
		BigDecimal amount = BigDecimal.ZERO;
		switch(ver){
		case OLD:
			if (obj instanceof Cuboid) {
				obj = (Cuboid) obj;
				amount = new BigDecimal(((Cuboid) obj).Volume()*PDI.getOldGoodBusinessTax() * level/100.0);
				
			}
			else if (obj instanceof polygonPrism) {
				obj = (polygonPrism) obj;
				amount = new BigDecimal((((polygonPrism) obj).Volume()*PDI.getOldGoodBusinessTax()*level/100.0));
			}
			break;
		case NEW:
			if (obj instanceof Cuboid) {
				obj = (Cuboid) obj;
				amount = new BigDecimal(((Cuboid) obj).Volume()*town.getGoodBusinessTax() * level/100.0);
				
			}
			else if (obj instanceof polygonPrism) {
				obj = (polygonPrism) obj;
				amount = new BigDecimal((((polygonPrism) obj).Volume()*town.getGoodBusinessTax()*level/100.0));
			}
			break;
		}
		if(adjusted) {
			return tools.cut(amount.multiply(PDI.getMoneyMultiplyer()));
		}
		else return amount;
	}
	
	public BigDecimal goodBusinessTax(Business busi, int level, boolean adjusted, boolean total, version ver) {
		Country country = plugin.countrydata.get(busi.getCountry());
		Town town = country.getTowns().get(busi.getTown());
		BigDecimal amount = BigDecimal.ZERO;
		if(total) {
			amount = this.goodBusinessTax(busi.getRegion(), town, level, adjusted, ver);
		}
		else {
			amount = this.goodBusinessTax(busi.getRegion(), town, level, adjusted, ver).divide(new BigDecimal(busi.getOwners().size()));
		}
		if(adjusted) {
			return tools.cut(amount);
		}
		else {
			return amount;
		}
	}
	
	public BigDecimal goodBusinessTax(Business busi, boolean adjusted, boolean total, version ver) {
		BigDecimal amount = this.goodBusinessTax(busi, busi.getProtectionLevel(), adjusted, total, ver);
		if(adjusted) {
			return tools.cut(amount);
		}
		else {
			return amount;
		}
	}
	
	public BigDecimal goodBusinessTax(Town town, boolean adjusted, boolean total, version ver) {
		BigDecimal amount = BigDecimal.ZERO;
		for(Business busi:town.getGoodBusinesses()) {
			if(busi.getOwners().contains(player.getName())) {
				amount = amount.add(goodBusinessTax(busi, adjusted, total, ver));
			}
		}
		if(adjusted) {
			return tools.cut(amount);
		}
		else {
			return amount;
		}
	}
	public BigDecimal goodBusinessTax(boolean adjusted, boolean total, version ver) {
		BigDecimal amount = BigDecimal.ZERO;
		for(Business busi:PDI.getGoodBusinessOwned()) {
			amount = amount.add(goodBusinessTax(busi, adjusted, total, ver));
		}
		if(adjusted) {
			return tools.cut(amount);
		}
		else {
			return amount;
		}
	}
//////////////////////////////////////////////////////////////////////////////
	public BigDecimal serviceBusinessTax(Object obj, Town town, int level, boolean adjusted, version ver) {
		BigDecimal amount = BigDecimal.ZERO;
		switch(ver){
		case OLD:
			if (obj instanceof Cuboid) {
				obj = (Cuboid) obj;
				amount = new BigDecimal(((Cuboid) obj).Volume()*PDI.getOldServiceBusinessTax() * level/100.0);
				
			}
			else if (obj instanceof polygonPrism) {
				obj = (polygonPrism) obj;
				amount = new BigDecimal((((polygonPrism) obj).Volume()*PDI.getOldServiceBusinessTax()*level/100.0));
			}
			break;
		case NEW:
			if (obj instanceof Cuboid) {
				obj = (Cuboid) obj;
				amount = new BigDecimal(((Cuboid) obj).Volume()*town.getServiceBusinessTax() * level/100.0);
				
			}
			else if (obj instanceof polygonPrism) {
				obj = (polygonPrism) obj;
				amount = new BigDecimal((((polygonPrism) obj).Volume()*town.getServiceBusinessTax()*level/100.0));
			}
			break;
		}
		if(adjusted) {
			return tools.cut(amount.multiply(PDI.getMoneyMultiplyer()));
		}
		else return amount;
	}
	
	public BigDecimal serviceBusinessTax(Business busi, int level, boolean adjusted, boolean total, version ver) {
		Country country = plugin.countrydata.get(busi.getCountry());
		Town town = country.getTowns().get(busi.getTown());
		BigDecimal amount = BigDecimal.ZERO;
		if(total) {
			amount = this.serviceBusinessTax(busi.getRegion(), town, level, adjusted, ver);
		}
		else {
			amount = this.serviceBusinessTax(busi.getRegion(), town, level, adjusted, ver).divide(new BigDecimal(busi.getOwners().size()));
		}
		if(adjusted) {
			return tools.cut(amount);
		}
		else {
			return amount;
		}
	}
	
	public BigDecimal serviceBusinessTax(Business busi, boolean adjusted, boolean total, version ver) {
		BigDecimal amount = this.goodBusinessTax(busi, busi.getProtectionLevel(), adjusted, total, ver);
		if(adjusted) {
			return tools.cut(amount);
		}
		else {
			return amount;
		}
	}
	
	public BigDecimal serviceBusinessTax(Town town, boolean adjusted, boolean total, version ver) {
		BigDecimal amount = BigDecimal.ZERO;
		for(Business busi:town.getGoodBusinesses()) {
			if(busi.getOwners().contains(player.getName())) {
				amount = amount.add(goodBusinessTax(busi, adjusted, total, ver));
			}
		}
		if(adjusted) {
			return tools.cut(amount);
		}
		else {
			return amount;
		}
	}
	public BigDecimal serviceBusinessTax(boolean adjusted, boolean total, version ver) {
		BigDecimal amount = BigDecimal.ZERO;
		for(Business busi:PDI.getGoodBusinessOwned()) {
			amount = amount.add(goodBusinessTax(busi, adjusted, total, ver));
		}
		if(adjusted) {
			return tools.cut(amount);
		}
		else {
			return amount;
		}
	}
	
///////////////////////////////////////////////////////////////////////////////
	
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
	
	// Citizenship methods
	public boolean leaveCountry() {
	//	if (PDI.getIsCountryRuler() || PDI.getIsTownMayor() || !PDI.getIsCountryResident()) return false;


		if(PDI.getIsCountryResident()) {
			Country countryFrom = PDI.getCountryResides();
			countryFrom.removeResident(playername);
			if (countryFrom.getCoRulers().contains(playername)) {
				countryFrom.removeCoRuler(playername);
			}
		}
		PDI.setCountryResides(null);
		if (PDI.getIsTownResident()) {
			leaveTown();
		}
		PDI.setPluralMoney(plugin.getConfig().getString("plural_money_default"));
		PDI.setSingularMoney(plugin.getConfig().getString("singular_money_default"));
		PDI.setMoneyMultiplyer(1);
		return true;
	}
	
	//TODO fix up leaveTown so that it takes into account businesses in other towns
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
	
	// Method that returns a vector of businessnames with thier associated town.
	public Vector<String> getJobOffers() {
		Vector<String> output = new Vector<String>();
		for (Town town : PDI.getCountryResides().getTowns()) {
			for(GoodBusiness business:town.getGoodBusinesses()) {
				if(business.getEmployOffers().contains(playername)) {
					output.add(business.getName() + "/" + town.getName());
				}
			}
			for(ServiceBusiness business:town.getServiceBusinesses()) {
				if(business.getEmployOffers().contains(playername)) {
					output.add(business.getName() + "/" + town.getName());
				}
			}
		}
		return output;
	}
	
	// Method that returns a vector of businessnames with thier associated town.
	public Vector<String> getOwnerOffers() {
		Vector<String> output = new Vector<String>();
		for (Town town : PDI.getCountryResides().getTowns()) {
			for(GoodBusiness business:town.getGoodBusinesses()) {
				if(business.getOwnerOffers().contains(playername)) {
					output.add(business.getName()+ "/" + town.getName());
				}
			}
			for(ServiceBusiness business:town.getServiceBusinesses()) {
				if(business.getOwnerOffers().contains(playername)) {
					output.add(business.getName()+ "/" + town.getName());
				}
			}
		}
		return output;
	}
	
	// Method that returns a vector of businessnames with thier associated town.
	public Vector<String> getOwnerRequests() {
		Vector<String> output = new Vector<String>();
		for (Town town : PDI.getCountryResides().getTowns()) {
			for(GoodBusiness business:town.getGoodBusinesses()) {
				if(business.getOwnerRequest().contains(playername)) {
					output.add(business.getName()+ "/" + town.getName());
				}
			}
			for(ServiceBusiness business:town.getServiceBusinesses()) {
				if(business.getOwnerRequest().contains(playername)) {
					output.add(business.getName()+ "/" + town.getName());
				}
			}
		}
		return output;
	}
	
	// Method that returns a vector of businessnames with thier associated town.
	public Vector<String> getJobRequests() {
		Vector<String> output = new Vector<String>();
		for (Town town : PDI.getCountryResides().getTowns()) {
			for(GoodBusiness business:town.getGoodBusinesses()) {
				if(business.getEmployRequest().contains(playername)) {
					output.add(business.getName()+ "/" + town.getName());
				}
			}
			for(ServiceBusiness business:town.getServiceBusinesses()) {
				if(business.getEmployRequest().contains(playername)) {
					output.add(business.getName()+ "/" + town.getName());
				}
			}
		}
		return output;
	}
	
	// Method that returns a vector of businessnames with thier associated town.
	public Vector<String> getEmployed() {
		Vector<String> output = new Vector<String>();
		for (Town town : PDI.getCountryResides().getTowns()) {
			for(GoodBusiness business:town.getGoodBusinesses()) {
				if(business.getEmployees().contains(playername)) {
					output.add(business.getName()+ "/" + town.getName());
				}
			}
			for(ServiceBusiness business:town.getServiceBusinesses()) {
				if(business.getEmployees().contains(playername)) {
					output.add(business.getName()+ "/" + town.getName());
				}
			}
		}
		return output;
	}
	
	public void resetLocationBooleans() {
		Location spot = player.getLocation();
		this.setLocationBooleansFalse();
		// Country
		PDI.setCountryIn(null);
		PDI.setHouseIn(null);
		PDI.setGoodBusinessIn(null);
		PDI.setServiceBusinessIn(null);
		PDI.setLocalBankIn(null);
		PDI.setLocalParkIn(null);
		PDI.setLocalPrisonIn(null);
		PDI.setFederalParkIn(null);
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
