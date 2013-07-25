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

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.Regions.Country;
import com.github.InspiredOne.InspiredNations.Regions.Cuboid;
import com.github.InspiredOne.InspiredNations.Regions.Park;
import com.github.InspiredOne.InspiredNations.Regions.polygonPrism;
import com.github.InspiredOne.InspiredNations.Tools.version;


public class CountryMethods {
	InspiredNations plugin;
	Country country;
	Tools tools;
	
	public CountryMethods(InspiredNations instance, Country countrytemp) {
		plugin = instance;
		country = countrytemp;
		tools = new Tools(plugin);
	}
	
	public BigDecimal getFederalParkTax(Object obj, int level, int countrylevel, boolean adjusted, version ver) {
		BigDecimal amount = BigDecimal.ZERO;
		switch(ver) {
		case OLD:
			if(obj instanceof Cuboid) {
			amount = (new BigDecimal(((Cuboid) obj).Volume() * level * country.getOldFedParkBase()/10000 *
					plugin.getConfig().getDouble("park_tax_multiplyer") + ((Cuboid) obj).Volume() * countrylevel * country.getOldFedParkBase()/10000 *
					plugin.getConfig().getDouble("park_tax_multiplyer"))); 
			}
			else {
				amount = (new BigDecimal(((Cuboid) obj).Volume() * level * country.getOldFedParkBase()/10000 *
						plugin.getConfig().getDouble("park_tax_multiplyer") + ((polygonPrism) obj).Volume() * countrylevel * country.getOldFedParkBase()/10000 *
						plugin.getConfig().getDouble("park_tax_multiplyer"))); 
			}
			break;
		case NEW:
			if(obj instanceof Cuboid) {
			amount = (new BigDecimal(((Cuboid) obj).Volume() * level * plugin.getConfig().getDouble("federal_park_base_cost")/10000 *
					plugin.getConfig().getDouble("park_tax_multiplyer") + ((Cuboid) obj).Volume() * countrylevel * plugin.getConfig().getDouble("base_cost_per_chunk")/10000 *
					plugin.getConfig().getDouble("park_tax_multiplyer")));
			}
			else {
				amount = (new BigDecimal(((Cuboid) obj).Volume() * level * plugin.getConfig().getDouble("federal_park_base_cost")/10000 *
						plugin.getConfig().getDouble("park_tax_multiplyer") + ((polygonPrism) obj).Volume() *  countrylevel * plugin.getConfig().getDouble("federal_park_base_cost")/10000 *
						plugin.getConfig().getDouble("park_tax_multiplyer"))); 
			}
			break;
		}
		if(adjusted) {
			return tools.cut(amount.multiply(country.getMoneyMultiplyer()));
		}
		else {
			return amount;
		}
	}
	
	public BigDecimal getFederalParkTax(Park park, int level, int countrylevel, boolean adjusted, version ver) {
		if (adjusted) {
			return tools.cut(getFederalParkTax(park.getRegion(), level, countrylevel, false, ver).multiply(country.getMoneyMultiplyer()));
		}
		else {
			return getFederalParkTax(park.getRegion(), level, countrylevel, false, ver);
		}
	}
	
	public BigDecimal getFederalParkTax(Park park, int countrylevel, boolean adjusted, version ver) {
		if(adjusted) {
			return tools.cut(getFederalParkTax(park, park.getProtectionLevel(), countrylevel, false, ver).multiply(country.getMoneyMultiplyer()));
		}
		else {
			return getFederalParkTax(park, park.getProtectionLevel(), countrylevel, false, ver);
		}
	}
	
	public BigDecimal getFederalParkTax(int countrylevel, boolean adjusted, version ver) {
		BigDecimal amount = BigDecimal.ZERO;
		for(Park park:country.getParks()) {
			amount = amount.add(getFederalParkTax(park, countrylevel, false, ver));
		}
		if (adjusted) {
			return tools.cut(amount.multiply(country.getMoneyMultiplyer()));
		}
		else {
			return amount;
		}
	}
	
	public BigDecimal getCostPerChunk(int countrylevel, boolean adjusted, version ver) {
		BigDecimal amount = BigDecimal.ZERO;
		switch(ver) {
		case OLD:
			amount = new BigDecimal(country.getOldChunkBase()*countrylevel);
			break;
		case NEW:
			amount = new BigDecimal(plugin.getConfig().getDouble("base_cost_per_chunk")*countrylevel);
			break;
		}
		if(adjusted) {
			return tools.cut(amount.multiply(country.getMoneyMultiplyer()));
		}
		else {
			return amount;
		}
	}
	
	public BigDecimal getProtectionTax(int countrylevel, boolean adjused, version ver) {
		BigDecimal amount = getCostPerChunk(countrylevel, false, ver).multiply(new BigDecimal(country.getChunks().Chunks.size()));
		
		if(adjused) {
			return tools.cut(amount.multiply(country.getMoneyMultiplyer()));
		}
		else {
			return amount;
		}
	}
	
	public BigDecimal getTaxAmount(int countrylevel, boolean adjusted, version ver) {
		BigDecimal amount =  getCostPerChunk(countrylevel, false, ver).multiply(new BigDecimal(country.
				getChunks().Chunks.size())).add(this.getFederalParkTax(countrylevel, false, ver)) // false so we don't adjust twice for the inflation.
				.add(this.getMilitaryFunding(false, ver)); // false so we don't adjust twice for the inflation.
		
		if (adjusted) {
			return tools.cut(amount.multiply(country.getMoneyMultiplyer()));
		}
		else {
			return amount;
		}
	}
	
	public BigDecimal getTaxAmount(boolean adjusted, version ver) {
		BigDecimal amount = getTaxAmount(country.getProtectionLevel(), false, ver);
		if (adjusted) {
			return tools.cut(amount.multiply(country.getMoneyMultiplyer()));
		}
		else {
			return amount;
		}
	}
	
	//////////////////////////////////////////////////////////////////
	
//	public int getMaxClaimableChunks() {
//		return (country.getMaxLoan().subtract(country.getLoanAmount()).add(country.getMoney().add(country.getRevenue())).divide((new BigDecimal(plugin.
//				getConfig().getDouble("base_cost_per_chunk") * 2).multiply(country.getMoneyMultiplyer())), 0, BigDecimal.ROUND_DOWN).toBigInteger().intValue());
//	}
//	
	public BigDecimal getRevenue(Boolean adjusted, version ver) {
		BigDecimal taxRevenuetemp = new BigDecimal(0);

		for (int i = 0; i < country.getTowns().size(); i++) {
			TownMethods TMI = new TownMethods(plugin, country.getTowns().get(i));
			taxRevenuetemp = taxRevenuetemp.add(TMI.getTaxAmount(false, ver));
		}
		if (adjusted) {
			return tools.cut(taxRevenuetemp.multiply(country.getMoneyMultiplyer()));
		}
		else {
			return taxRevenuetemp;
		}
	}
	
	public BigDecimal getMilitaryFunding(int level, boolean adjusted, version ver) {
		BigDecimal base = BigDecimal.ZERO;
		switch(ver) {
			case OLD:
				base = new BigDecimal(country.getOldMilitaryBase());
				break;
			case NEW:
				base = new BigDecimal(plugin.getConfig().getDouble("military_base_cost"));
				break;
		}
		if(adjusted) {
			return tools.cut(base.multiply(BigDecimal.valueOf(3)).pow(level).multiply(country.getMoneyMultiplyer()).multiply(new BigDecimal(level)));
		}
		else {
			return base.multiply(BigDecimal.valueOf(3)).pow(level).multiply(new BigDecimal(level));
		}
	}
	
	public BigDecimal getMilitaryFunding(boolean adjusted, version ver) {
		BigDecimal amount = getMilitaryFunding(country.getMilitaryLevel(), false, ver);
		if (adjusted) {
			return tools.cut(amount.multiply(country.getMoneyMultiplyer()));
		}
		else {
			return amount;
		}
	}
}
