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
package com.github.InspiredOne.InspiredNations.Regions;

import java.awt.Rectangle;
import java.math.BigDecimal;


import org.bukkit.Location;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.Tools;

public class Park extends InspiredRegion{
	private String country;
	private boolean isFederalPark;
	private int town;
	private InspiredNations plugin;
	private Tools tools;
	private polygonPrism polyspace = null;
	private Cuboid cubespace = null;
	
	public Park(InspiredNations instance, Cuboid space, String countrytemp, int towntemp, boolean isFederal, String nametemp) {
		super(instance, space, countrytemp, towntemp, nametemp);
		plugin = instance;
		cubespace = space;
		tools = new Tools(plugin);
		countrytemp = tools.findCountry(countrytemp).get(0);
		country = countrytemp;
		town = towntemp;
		isFederalPark = isFederal;
	}
	
	public Park(InspiredNations instance, polygonPrism space, String countrytemp, int towntemp, boolean isFederal, String nametemp) {
		super(instance, space, countrytemp, towntemp, nametemp);
		plugin = instance;
		polyspace = space;
		tools = new Tools(plugin);
		countrytemp = tools.findCountry(countrytemp).get(0);
		country = countrytemp;
		town = towntemp;
		isFederalPark = isFederal;
	}
	
	public BigDecimal getTaxAmount() {
		if (town == -1) {
			return plugin.countrydata.get(country.toLowerCase()).getMoneyMultiplyer().multiply(new BigDecimal(Volume() * getProtectionLevel() * plugin.getConfig().getDouble("federal_park_base_cost")));
		}
		else {
			return plugin.countrydata.get(country.toLowerCase()).getTowns().get(town).getMoneyMultiplyer().multiply((new BigDecimal(Volume() * getProtectionLevel() * plugin.countrydata.get(country.toLowerCase()).getTaxRate()/10000 * plugin.getConfig().getDouble("park_tax_multiplyer") *
					plugin.countrydata.get(country.toLowerCase()).getTowns().get(town).getProtectionLevel())));
		}
	}
	
	public BigDecimal getTaxAmount(int level) {
		if (town == -1) {
			return plugin.countrydata.get(country.toLowerCase()).getMoneyMultiplyer().multiply(new BigDecimal(Volume() * level * plugin.getConfig().getDouble("federal_park_base_cost")).multiply(plugin.countrydata.get(country.toLowerCase()).getMoneyMultiplyer()));
		}
		else {
			return plugin.countrydata.get(country.toLowerCase()).getTowns().get(town).getMoneyMultiplyer().multiply((new BigDecimal(Volume() * level * plugin.countrydata.get(country.toLowerCase()).getTaxRate()/10000 * plugin.getConfig().getDouble("park_tax_multiplyer") *
					plugin.countrydata.get(country.toLowerCase()).getTowns().get(town).getProtectionLevel())));
		}
	}
	

	

	
	public void setIsFederal(boolean federal) {
		isFederalPark = federal;
	}
	
	public boolean getIsFederal() {
		return isFederalPark;
	}
	
	public boolean isInCountry() {
		Country countryIn = plugin.countrydata.get(country);
		if (isPolySpace()) {
			Rectangle rect = polyspace.getPolygon().getBounds();
			for (int i = (int) rect.getMinX(); i < (int) rect.getMaxX() + 1; i++) {
				for (int j = (int) rect.getMinY(); j < (int)rect.getMaxY() + 1; j++) {
					for (int l = polyspace.getYMin(); l < polyspace.getYMax(); l++) {
						Location test = new Location(plugin.getServer().getWorld(polyspace.getWorld()), i, l, j);
						if ((!countryIn.isIn(test)) && polyspace.isIn(test)) {
							return false;
						}
					}
				}
			}
		}
		if (isCubeSpace()) {
			for (int i = cubespace.getXmin(); i < cubespace.getXmax(); i++) {
				for (int j = cubespace.getYmin(); j < cubespace.getYmax(); j++) {
					for (int l = cubespace.getZmin(); l < cubespace.getZmax(); l++) {
						Location test = new Location(plugin.getServer().getWorld(cubespace.getWorld()), i, j, l);
						if ((!countryIn.isIn(test))) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}
}
