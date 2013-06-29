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

import com.github.InspiredOne.InspiredNations.Regions.Park;
import com.github.InspiredOne.InspiredNations.Regions.Town;



public class TownMethods {
	
	InspiredNations plugin;
	Town town;
	Tools tools;
	public TownMethods(InspiredNations instance, Town towntemp) {
		plugin = instance;
		town = towntemp;
		tools = new Tools(plugin);
	}
	
	public enum taxType {
		OLD, CURRENT
	}
	
	public BigDecimal getTaxAmount() {

		return getTaxAmount(town.getProtectionLevel());
	}
	
	public BigDecimal getTaxAmount(int level) {
		BigDecimal amount = new BigDecimal(town.getArea()*level*town.getNationTax()/100).multiply(town.getMoneyMultiplyer());
		for (Park park:town.getParks()) {
			amount = amount.add(getLocalParkTax(park));
		}
		return tools.cut(amount);
	}
	
	public BigDecimal getCostPerChunk(taxType tax) {
		BigDecimal output = null;
		switch(tax) {
			case OLD:
				output = tools.cut(town.getMoneyMultiplyer().multiply(new BigDecimal(town.getNationTaxOld()/100)).multiply(new BigDecimal(town.getProtectionLevel())));
			
			case CURRENT:
				output = tools.cut(town.getMoneyMultiplyer().multiply(new BigDecimal(town.getNationTax()/100)).multiply(new BigDecimal(town.getProtectionLevel())));
		}
		return output;
	}
	
	public BigDecimal getLocalParkTax(Park park, int parklevel, int townlevel) {
		return town.getMoneyMultiplyer().multiply((new BigDecimal(park.Volume() * parklevel *
				townlevel * town.getNationTax()/10000 * plugin.getConfig().getDouble("park_tax_multiplyer"))));
	}
	
	public BigDecimal getLocalParkTax(Park park) {
		return getLocalParkTax(park, park.getProtectionLevel(), town.getProtectionLevel());
	}
}
