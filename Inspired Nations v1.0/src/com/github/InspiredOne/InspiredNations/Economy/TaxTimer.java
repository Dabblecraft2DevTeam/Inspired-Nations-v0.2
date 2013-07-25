package com.github.InspiredOne.InspiredNations.Economy;

import java.math.BigDecimal;

import org.bukkit.scheduler.BukkitRunnable;

import com.github.InspiredOne.InspiredNations.CountryMethods;
import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.PlayerMethods;
import com.github.InspiredOne.InspiredNations.TownMethods;
import com.github.InspiredOne.InspiredNations.Regions.Country;
import com.github.InspiredOne.InspiredNations.Regions.GoodBusiness;
import com.github.InspiredOne.InspiredNations.Regions.House;
import com.github.InspiredOne.InspiredNations.Regions.Park;
import com.github.InspiredOne.InspiredNations.Regions.ServiceBusiness;
import com.github.InspiredOne.InspiredNations.Regions.Town;
import com.github.InspiredOne.InspiredNations.Tools.version;

public class TaxTimer {

	int cycleLength;
	int countdown;
	InspiredNations plugin;
	public TaxTimer(InspiredNations instance){
		plugin = instance;
		cycleLength = plugin.getConfig().getInt("tax_cycle_length");
		countdown = cycleLength;
	}
	
	

	public void startTimer() {
		
		
		new BukkitRunnable() {

			@Override
			public void run() {
				countdown--;
				if(countdown == 0) {
					countdown = cycleLength;
				}
			}
			
		}.runTaskTimer(plugin, 0, 20);		
	}
	
	public double getFractionLeft() {
		return ((double) countdown)/((double)cycleLength);
	}
	
	public void executeTaxCycle() {
		
		for(Country country:plugin.countrydata.values()) {
			for(Town town:country.getTowns()) {
				
				// { Player Taxes
				
				//Houses
				for(House house:town.getHouses()) {
					boolean afford = false;
					BigDecimal amount = BigDecimal.ZERO;
					
					while(!afford) {
						PlayerMethods PM = new PlayerMethods(plugin, house.getOwners().get(0));
						afford = true;
						amount = PM.houseTax(house, true, false, version.NEW);
						for(String playername:house.getOwners()) {
							PlayerData PDITarget = plugin.playerdata.get(playername);
							if(PDITarget.getMoney().compareTo(amount) < 0) {
								afford = false;
								house.setProtectionLevel(house.getProtectionLevel() - 1);
							}
							amount = PM.houseTax(house, true, false, version.NEW);
						}
						for(String playername:house.getOwners()) {
							PlayerData PDITarget = plugin.playerdata.get(playername);
							PDITarget.transferMoneyToTown(amount, town.getName(), country.getName());
						}
					}
				}
				
				//GoodBusinsses
				for(GoodBusiness busi:town.getGoodBusinesses()) {
					boolean afford = false;
					BigDecimal amount = BigDecimal.ZERO;
					
					while(!afford) {
						PlayerMethods PM = new PlayerMethods(plugin, busi.getOwners().get(0));
						afford = true;
						amount = PM.goodBusinessTax(busi, true, false, version.NEW);
						for(String playername:busi.getOwners()) {
							PlayerData PDITarget = plugin.playerdata.get(playername);
							if(PDITarget.getMoney().compareTo(amount) < 0) {
								afford = false;
								busi.setProtectionLevel(busi.getProtectionLevel() - 1);
							}
							amount = PM.goodBusinessTax(busi, true, false, version.NEW);
						}
						for(String playername:busi.getOwners()) {
							PlayerData PDITarget = plugin.playerdata.get(playername);
							PDITarget.transferMoneyToTown(amount, town.getName(), country.getName());
						}
					}
				}
				
				// ServiceBusiness
				for(ServiceBusiness busi:town.getServiceBusinesses()) {
					boolean afford = false;
					BigDecimal amount = BigDecimal.ZERO;
					
					while(!afford) {
						PlayerMethods PM = new PlayerMethods(plugin, busi.getOwners().get(0));
						afford = true;
						amount = PM.serviceBusinessTax(busi, true, false, version.NEW);
						for(String playername:busi.getOwners()) {
							PlayerData PDITarget = plugin.playerdata.get(playername);
							if(PDITarget.getMoney().compareTo(amount) < 0) {
								afford = false;
								busi.setProtectionLevel(busi.getProtectionLevel() - 1);
							}
						}
						for(String playername:busi.getOwners()) {
							PlayerData PDITarget = plugin.playerdata.get(playername);
							PDITarget.transferMoneyToTown(amount, town.getName(), country.getName());
						}
						amount = PM.serviceBusinessTax(busi, true, false, version.NEW);
					}
				}
				
				 //} 

				// { Town Taxes
				
				boolean afford = false;
				TownMethods TM = new TownMethods(plugin, town);
				
				// Prot Level
				BigDecimal amount = TM.getProtectionTax(town.getProtectionLevel(), true, version.NEW);
				
				while (!afford) {
					afford = true;
					if(amount.compareTo(town.getMoney()) < 0) {
						afford = false;
						town.setProtectionLevel(town.getProtectionLevel() - 1);
					}
					amount = TM.getProtectionTax(town.getProtectionLevel(), true, version.NEW);
				}
				
				town.transferMoneyToCountry(amount, town.getCountry());
				
				afford = false;
				amount = TM.getMilitaryFunding(true, version.NEW);
				
				// Mil Level
				while(!afford) {
					afford = true;
					if(amount.compareTo(town.getMoney()) < 0) {
						afford = false;
						town.setMilitaryLevel(town.getMilitaryLevel() - 1);
					}
					amount = TM.getMilitaryFunding(true, version.NEW);
				}
				
				town.transferMoneyToCountry(amount, town.getCountry());
				
				// Parks
				for(Park park:town.getParks()) {
					afford = false;
					amount = TM.getLocalParkTax(park, true, version.NEW);
					
					while(!afford) {
						afford = true;
						if(amount.compareTo(town.getMoney()) < 0) {
							afford = false;
							park.setProtectionLevel(park.getProtectionLevel() - 1);
						}
						amount = TM.getLocalParkTax(park, true, version.NEW);
					}
				}
				
				town.transferMoneyToCountry(amount, town.getCountry());
				
				// }

				// { Country Taxes
				CountryMethods CM = new CountryMethods(plugin, country);
				afford = false;
				amount = CM.getProtectionTax(country.getProtectionLevel(), true, version.NEW);
				
				while(!afford) {
					afford = true;
					if(amount.compareTo(country.getMoney()) < 0) {
						afford = false;
						country.setProtectionLevel(country.getProtectionLevel() - 1);
					}
					amount = CM.getProtectionTax(country.getProtectionLevel(), true, version.NEW);
				}
				country.transferMoneyToNPC(amount);
				
				afford = false;
				amount = CM.getMilitaryFunding(true, version.NEW);
				
				while(!afford) {
					afford = true;
					if(amount.compareTo(country.getMoney()) < 0) {
						afford = false;
						country.setMilitaryLevel(country.getMilitaryLevel() - 1);
					}
					amount = CM.getMilitaryFunding(true, version.NEW);
				}
				country.transferMoneyToNPC(amount);
				
				for(Park park:country.getParks()) {
					afford = false;
					amount = CM.getFederalParkTax(park, country.getProtectionLevel(), true, version.NEW);
					
					while(!afford) {
						afford = true;
						if(amount.compareTo(country.getMoney()) < 0) {
							afford = false;
							park.setProtectionLevel(park.getProtectionLevel() - 1);
						}
						amount = CM.getFederalParkTax(park, country.getProtectionLevel(), true, version.NEW);
					}
					country.transferMoneyToNPC(amount);
				}
				
				
				// }
			}
		}
		
		
/*		for(PlayerData PDI:plugin.playerdata.values()) {
			PlayerMethods PM = new PlayerMethods(plugin,PDI.playername);
			PDI.setOldHouseTax(PDI.getHouseTax());
			PDI.setOldGoodBusinessTax(PDI.getGoodBusinessTax());
			PDI.setOldServiceBusinessTax(PDI.getServiceBusinessTax());
			for(House house:PDI.getHouseOwned()) {
				boolean afford = false;
				BigDecimal amount = BigDecimal.ZERO;
				
				while (!afford) {
					afford = true;
					amount = PM.houseTax(house, true, false, version.NEW);
					for(String owner:house.getOwners()) {
						PlayerData PDItest = plugin.playerdata.get(owner);
						if(PDItest.getMoney().compareTo(amount) < 0) {
							afford = false;
							house.changeProtectionLevel(house.getProtectionLevel() - 1);
						}
					}
				}
				
				Country country = plugin.countrydata.get(house.getCountry());
				Town town = country.getTowns().get(house.getTown());
				
				PDI.transferMoneyToTown(amount, town.getName(), country.getName());
			}
		}
		*/
		// put new tax in old tax variable
		// start at players, then move to towns, then move to country.
	}
}
