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

import com.github.InspiredOne.InspiredNations.Tools.version;
import com.github.InspiredOne.InspiredNations.Regions.Country;
import com.github.InspiredOne.InspiredNations.Regions.Cuboid;
import com.github.InspiredOne.InspiredNations.Regions.Park;
import com.github.InspiredOne.InspiredNations.Regions.Town;
import com.github.InspiredOne.InspiredNations.Regions.polygonPrism;



public class TownMethods {
	
	InspiredNations plugin;
	Town town;
	Tools tools;
	public TownMethods(InspiredNations instance, Town towntemp) {
		plugin = instance;
		town = towntemp;
		tools = new Tools(plugin);
	}

	public BigDecimal getTaxAmount(boolean adjusted, version ver) {
		int levelProt = town.getProtectionLevel();
		int levelMil = town.getMilitaryLevel();
		return getTaxAmount(levelProt, levelMil, adjusted, ver);
	}
	public BigDecimal getTaxAmount(int levelProt, int levelMil, boolean adjusted, version ver) {
		BigDecimal amount = BigDecimal.ZERO;
		switch(ver) {
		case OLD:
			amount = new BigDecimal(town.getArea()*levelProt*town.getNationTaxOld()/100);	
		case NEW:
			amount = new BigDecimal(town.getArea()*levelProt*town.getNationTaxOld()/100);
		}
		amount = amount.add(getLocalParkTax(levelProt, false, ver)); // false so we don't adjust twice with moneymultiplyer.
		amount = amount.add(this.getMilitaryFunding(levelMil, false, ver)); // false so we don't adjust twice with moneymultiplyer.
		if(adjusted) {
			return tools.cut(amount.multiply(town.getMoneyMultiplyer()));
		}
		else {
			return amount;
		}
	}
	
	public BigDecimal getLocalParkTax(Object obj, int level, int townlevel, boolean adjusted, version ver) {
		BigDecimal amount = BigDecimal.ZERO;
		switch(ver) {
		case OLD:
			if(obj instanceof Cuboid) {
			amount = (new BigDecimal(((Cuboid) obj).Volume() * level * town.getNationTax()/10000 *
					plugin.getConfig().getDouble("park_tax_multiplyer") + ((Cuboid) obj).Volume() * townlevel * town.getNationTaxOld()/10000 *
					plugin.getConfig().getDouble("park_tax_multiplyer"))); 
			}
			else {
				amount = (new BigDecimal(((Cuboid) obj).Volume() * level * town.getNationTax()/10000 *
						plugin.getConfig().getDouble("park_tax_multiplyer") + ((polygonPrism) obj).Volume() * townlevel * town.getNationTaxOld()/10000 *
						plugin.getConfig().getDouble("park_tax_multiplyer"))); 
			}
			break;
		case NEW:
			if(obj instanceof Cuboid) {
			amount = (new BigDecimal(((Cuboid) obj).Volume() * level * town.getNationTax()/10000 *
					plugin.getConfig().getDouble("park_tax_multiplyer") + ((Cuboid) obj).Volume() * townlevel * town.getNationTax()/10000 *
					plugin.getConfig().getDouble("park_tax_multiplyer")));
			}
			else {
				amount = (new BigDecimal(((Cuboid) obj).Volume() * level * town.getNationTax()/10000 *
						plugin.getConfig().getDouble("park_tax_multiplyer") + ((polygonPrism) obj).Volume() * townlevel * town.getNationTax()/10000 *
						plugin.getConfig().getDouble("park_tax_multiplyer"))); 
			}
			break;
		}
		if(adjusted) {
			return tools.cut(amount.multiply(town.getMoneyMultiplyer()));
		}
		else {
			return amount;
		}
	}
	
	public BigDecimal getLocalParkTax(Park park, boolean adjusted, version ver) {
		Country country = plugin.countrydata.get(park.getCountry());
		Town town = country.getTowns().get(park.getTown());
		return getLocalParkTax(park.getRegion(),park.getProtectionLevel(), town.getProtectionLevel(), adjusted, ver);
	}
	
	public BigDecimal getLocalParkTax(Park park, int townlevel, boolean adjusted, version ver) {
		return getLocalParkTax(park.getRegion(),park.getProtectionLevel(), townlevel, adjusted, ver);
	}
	
	public BigDecimal getLocalParkTax(int townlevel, boolean adjusted, version ver) {
		BigDecimal amount = BigDecimal.ZERO;
		for(Park park:town.getParks()) {
			amount = amount.add(getLocalParkTax(park, townlevel, adjusted, ver));
		}
		return amount;
	}
	
	public BigDecimal getMilitaryFunding(int level, boolean adjusted, version ver) {
		BigDecimal base = BigDecimal.ZERO;
		Country country = plugin.countrydata.get(town.getCountry());
		switch(ver) {
			case OLD:
				base = new BigDecimal(country.getOldMilitaryBase());
			case NEW:
				base = new BigDecimal(plugin.getConfig().getDouble("military_base_cost"));
		}
		if(adjusted) {
			return tools.cut(base.multiply(BigDecimal.valueOf(3)).pow(level).multiply(town.getMoneyMultiplyer()).multiply(new BigDecimal(level)));
		}
		else {
			return base.multiply(BigDecimal.valueOf(3)).pow(level).multiply(town.getMoneyMultiplyer()).multiply(new BigDecimal(level));
		}
	}
	
	public BigDecimal getMilitaryFunding(boolean adjusted, version ver) {
		return getMilitaryFunding(town.getMilitaryLevel(), adjusted, ver);
	}
//////////////////////////////////////////////////////////////////////////
	

	
	public BigDecimal getCostPerChunk(version tax) {
		BigDecimal output = null;
		switch(tax) {
			case OLD:
				output = tools.cut(town.getMoneyMultiplyer().multiply(new BigDecimal(town.getNationTaxOld()/100)).multiply(new BigDecimal(town.getProtectionLevel())));
			
			case NEW:
				output = tools.cut(town.getMoneyMultiplyer().multiply(new BigDecimal(town.getNationTax()/100)).multiply(new BigDecimal(town.getProtectionLevel())));
		}
		return output;
	}
}
