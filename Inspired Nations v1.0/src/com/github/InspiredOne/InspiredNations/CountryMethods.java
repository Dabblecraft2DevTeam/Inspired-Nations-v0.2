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
import com.github.InspiredOne.InspiredNations.Regions.Park;


public class CountryMethods {
	InspiredNations plugin;
	Country country;
	Tools tools;
	
	public CountryMethods(InspiredNations instance, Country countrytemp) {
		plugin = instance;
		country = countrytemp;
		tools = new Tools(plugin);
	}
	
	public BigDecimal getTaxAmount() {
		BigDecimal temp = new BigDecimal(0);
		temp = new BigDecimal(country.size()*(plugin.getConfig().getDouble("base_cost_per_chunk"))).multiply(country.getMoneyMultiplyer());
		for (Park park : country.getParks()) {
			temp = temp.add(country.getMoneyMultiplyer().multiply(new BigDecimal(park.Volume() * park.getProtectionLevel() * plugin.getConfig().getDouble("federal_park_base_cost")).multiply(country.getMoneyMultiplyer())));
		}
		return tools.cut(temp.multiply(new BigDecimal(country.getProtectionLevel())));
	}
	
	public BigDecimal getTaxAmount(int level) {
		BigDecimal temp = new BigDecimal(0);
		temp = new BigDecimal(country.size()*(plugin.getConfig().getDouble("base_cost_per_chunk"))).multiply(country.getMoneyMultiplyer());
		for (Park park : country.getParks()) {
			temp = temp.add(country.getMoneyMultiplyer().multiply(new BigDecimal(park.Volume() * park.getProtectionLevel() * plugin.getConfig().getDouble("federal_park_base_cost")).multiply(country.getMoneyMultiplyer())));
		}
		return tools.cut(temp.multiply(new BigDecimal(level)));
	}
	
	public int getMaxClaimableChunks() {
		return (country.getMaxLoan().subtract(country.getLoanAmount()).add(country.getMoney().add(country.getRevenue())).divide((new BigDecimal(plugin.
				getConfig().getDouble("base_cost_per_chunk") * 2).multiply(country.getMoneyMultiplyer())), 0, BigDecimal.ROUND_DOWN).toBigInteger().intValue());
	}
	
	public BigDecimal getCostPerChunk() {
		return tools.cut(new BigDecimal(plugin.getConfig().getDouble("base_cost_per_chunk")).multiply(country.getMoneyMultiplyer()
				.multiply(new BigDecimal(country.getProtectionLevel()))));
	}
	
	public BigDecimal getRevenue() {
		BigDecimal taxRevenuetemp = new BigDecimal(0);
		for (int i = 0; i < country.getTowns().size(); i++) {
			TownMethods TMI = new TownMethods(plugin, country.getTowns().get(i));
			taxRevenuetemp = taxRevenuetemp.add(TMI.getTaxAmount());
		}
		BigDecimal taxRevenue = taxRevenuetemp;
		return tools.cut(taxRevenue);
	}
}
