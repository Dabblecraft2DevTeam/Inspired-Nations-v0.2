package com.github.InspiredOne.InspiredNations.Regions;

import java.awt.Rectangle;
import java.util.Vector;

import org.bukkit.Location;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.Tools;

public class InspiredRegion {
	private Tools tools;
	private String country;
	private int town;
	private InspiredNations plugin;
	private polygonPrism polyspace = null;
	private Cuboid cubespace = null;
	private String name = "";
	private Vector<String> builders = new Vector<String>();
	private int protectionLevel = 1;

	
	public InspiredRegion(InspiredNations instance, Cuboid space,String countrytemp, int towntemp, String nametemp) {
		plugin = instance;
		cubespace = space;
		country = countrytemp;
		town = towntemp;
		name = nametemp;
		tools = new Tools(plugin);
	}
	
	public InspiredRegion(InspiredNations instance, polygonPrism space, String countrytemp, int towntemp, String nametemp) {
		plugin = instance;
		polyspace = space;
		country = countrytemp;
		town = towntemp;
		name = nametemp;
		tools = new Tools(plugin);
	}
	
	public void setAddress(String countrytemp, int towntemp) {
		countrytemp = tools.findCountry(countrytemp).get(0);
		country = countrytemp;
		town = towntemp;
	}
	
	public void setPolySpace(polygonPrism space) {
		polyspace = space;
		cubespace = null;
	}
	
	public void setCubeSpace(Cuboid space) {
		cubespace = space;
		polyspace = null;
	}
	

	
	public void setName(String nametemp) {
		name = nametemp;
	}
	
	public void setBuilders(Vector<String> builderstemp) {
		builders = builderstemp;
	}
	
	public void addBuilder(String builder) {
		builder = tools.findPerson(builder).get(0);
		builders.add(builder);
	}
	
	public void removeBuilder(String builder) {
		builder = tools.findPerson(builder).get(0);
		builders.remove(builder);
	}
	
	public Vector<String> getBuilders() {
		return builders;
	}
	
	public boolean isBuilder(String builder) {
		builder = tools.findPerson(builder).get(0);
		return builders.contains(builder);
	}
	
	public Cuboid getCubeSpace() {
		return cubespace;
	}
	
	public polygonPrism getPolySpace() {
		return polyspace;
	}
	
	public InspiredNations getPlugin() {
		return plugin;
	}
	
	public String getCountry() {
		return country;
	}
	
	public int getTown() {
		return town;
	}
	
	public String getName() {
		return name;
	}
	
	public void setProtectionLevel(int level) {
		protectionLevel = level;
	}
	
	public int getProtectionLevel() {
		return protectionLevel;
	}
	
	public Boolean isPolySpace() {
		try {	
			if (cubespace.equals(null)) {
				return true;
			}
			else return false;
		}
		catch (Exception ex) {
			return true;
		}
	}
	
	public Boolean isCubeSpace() {
		try {	
			if (polyspace.equals(null)) {
				return true;
			}
			else return false;
		}
		catch (Exception ex) {
			return true;
		}
	}
	
	public boolean isInTown() {
		Town townIn = plugin.countrydata.get(country.toLowerCase()).getTowns().get(town);
		if (isPolySpace()) {
			Rectangle rect = polyspace.getPolygon().getBounds();
			for (int i = (int) rect.getMinX(); i < (int) rect.getMaxX(); i++) {
				for (int j = (int) rect.getMinY(); j < (int)rect.getMaxY(); j++) {
					for (int l = polyspace.getYMin(); l < polyspace.getYMax(); l++) {
						Location test = new Location(plugin.getServer().getWorld(polyspace.getWorld()), i, l, j);
						if ((!townIn.isIn(test)) && polyspace.isIn(test)) {
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
						if ((!townIn.isIn(test))) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}
	
	public Boolean isIn(Location tile) {
		if (isPolySpace()) {
			return polyspace.isIn(tile);
		}
		else if (isCubeSpace()) {
			return cubespace.isIn(tile);
		}
		else return false;
	}
	
	public int Area() {
		if (isPolySpace()) {
			return polyspace.Area();
		}
		else if (isCubeSpace()) {
			return cubespace.Area();
		}
		else return 0;
	}
	
	public int Volume() {
		if (isPolySpace()) {
			return polyspace.Volume();
		}
		else if (isCubeSpace()) {
			return cubespace.Volume();
		}
		else return 0;
	}
	
	public int Perimeter() {
		if (isPolySpace()) {
			return polyspace.Perimeter();
		}
		else if (isCubeSpace()) {
			return cubespace.Perimeter();
		}
		else return 0;
	}
	

}
