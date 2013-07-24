package com.github.InspiredOne.InspiredNations.Economy;

import java.math.BigDecimal;

import org.bukkit.scheduler.BukkitRunnable;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.PlayerMethods;
import com.github.InspiredOne.InspiredNations.Regions.Country;
import com.github.InspiredOne.InspiredNations.Regions.House;
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
