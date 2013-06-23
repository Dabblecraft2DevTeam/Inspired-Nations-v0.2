package com.github.InspiredOne.InspiredNations.Regions;

import java.awt.Point;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Iterator;
import java.util.Vector;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.github.InspiredOne.InspiredNations.CountryMethods;
import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.PlayerMethods;
import com.github.InspiredOne.InspiredNations.Tools;
import com.github.InspiredOne.InspiredNations.TownMethods;
import com.github.InspiredOne.InspiredNations.TownMethods.taxType;

public class Country {
	
	private InspiredNations plugin;
	private Tools tools;
	private String name;
	private String ruler;
	private Vector<String> coRulers = new Vector<String>();
	private Vector<Town> towns = new Vector<Town>();
	private Vector<String> residents = new Vector<String>();
	private Vector<Park> parks = new Vector<Park>();
	private Vector<String> request = new Vector<String>();
	private Vector<String> offer = new Vector<String>();
	private int population = 0;
	private Chunks area = new Chunks();
	private String pluralMoneyName = "";
	private String singularMoneyName = "";
	private double taxRate = 1.0;
	private BigDecimal moneyMultiplyer = new BigDecimal(Math.PI);
	private BigDecimal money;
	private BigDecimal loan;
	private BigDecimal maxLoan;
	private int protectionLevel = 1;
	private MathContext mcup = new MathContext(100, RoundingMode.UP);
	private MathContext mcdown = new MathContext(100, RoundingMode.DOWN);
	
	CountryMethods CM;
	
	public Country(InspiredNations instance, Chunks areatemp, String nametemp, String rulertemp) {
		plugin = instance;
		tools = new Tools(plugin);
		try {
			rulertemp = tools.findPerson(rulertemp).get(0);
		} catch (Exception e) {

		}
		this.setRuler(rulertemp);
		this.addResident(rulertemp);
		area = areatemp;
		name = nametemp;
		money = new BigDecimal(plugin.getConfig().getString("country_start_loan"));
		loan = new BigDecimal(plugin.getConfig().getString("country_start_loan"));
		maxLoan =  new BigDecimal(plugin.getConfig().getDouble("country_start_loan"));
		plugin.countrydata.put(this.getName(), this);
		CM = new CountryMethods(plugin, this);
	}
	
	public Country(InspiredNations instance, String nametemp, String rulertemp) {
		plugin = instance;
		tools = new Tools(plugin);
		try {
			rulertemp = tools.findPerson(rulertemp).get(0);
		} catch (Exception e) {

		}
		name = nametemp;
		this.setRuler(rulertemp);
		this.addResident(rulertemp);
		money = new BigDecimal(plugin.getConfig().getString("country_start_loan"));
		loan = new BigDecimal(plugin.getConfig().getString("country_start_loan"));
		new BigDecimal(plugin.getConfig().getDouble("country_start_loan"));
		plugin.countrydata.put(this.getName(), this);
		CM = new CountryMethods(plugin, this);
	}
	
	public void setName(String nametemp) {
		name = nametemp;
		for(Town town:this.getTowns()) {
			town.setCountry(nametemp);
		}
	}
	
	// this method takes care of the player data, and country data
	public void setRuler(String rulername) {
		try {
			rulername = tools.findPerson(rulername).get(0);
		} catch (Exception e1) {

		}
		ruler = rulername;
		try {
			PlayerData PDITarget = plugin.playerdata.get(rulername);
			PDITarget.setCountryRuled(this);
		} catch (Exception e) {

		}
		
	}
	// this method takes care of the player data, and country data
	public void setCoRulers(Vector<String> rulernames) {
		coRulers = rulernames;
		try {
			for(String corulers:rulernames) {
				PlayerData PDITarget = plugin.playerdata.get(corulers);
				PDITarget.setCountryRuled(this);
			}
		} catch (Exception e) {

		}
	}
	// this method takes care of the player data, and country data
	public void addCoRuler(Player rulertemp) {
		if (!this.getCoRulers().contains(rulertemp.getName())) {
			coRulers.add(rulertemp.getName());
		}
		try {
			PlayerData PDITarget = plugin.playerdata.get(rulertemp.getName());
			PDITarget.setCountryRuled(this);
		} catch (Exception e) {

		}
	}
	// this method takes care of the player data, and country data
	public void addCoRuler(String rulertemp) {
		try {
			rulertemp = tools.findPerson(rulertemp).get(0);
		} catch (Exception e1) {
			
		}
		if(!this.getCoRulers().contains(rulertemp)){
			coRulers.add(rulertemp);
		}
		try {
			PlayerData PDITarget = plugin.playerdata.get(rulertemp);
			PDITarget.setCountryRuled(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// this method takes care of the player data, and country data
	public void removeCoRuler(Player rulertemp) {
		coRulers.remove(rulertemp.getName());
		PlayerData PDITarget = plugin.playerdata.get(rulertemp.getName());
		PDITarget.setCountryRuled(null);
	}
	// this method takes care of the player data, and country data
	public void removeCoRuler(String rulertemp) {
		rulertemp = tools.findPerson(rulertemp).get(0);
		coRulers.remove(rulertemp);
		PlayerData PDITarget = plugin.playerdata.get(rulertemp);
		PDITarget.setCountryRuled(null);
	}
	
	public void setTowns(Vector<Town> townlist) {
		towns = townlist;
	}
	
	public void addTown(Town town) {
		if(!towns.contains(town)) {
			towns.add(town);
		}
	}
	
	public void removeTown(Town town) {
		towns.remove(town);
	}
	
	public void setParks(Vector<Park> parklist) {
		parks = parklist;
	}
	
	public void addPark(Park park) {
		parks.add(park);
	}
	
	public void removePark(Park park) {
		parks.remove(park);
	}
	// this method takes care of the player data, and country data
	public void setResidents(Vector<String> people) {
		residents = people;
		setPopulation();
		try {
			for(String person:people) {
				PlayerData PDITarget = plugin.playerdata.get(person);
				PDITarget.setCountryResides(this);
			}
		} catch (Exception e) {

		}
	}
	// this method takes care of the player data, and country data
	public void addResident(String person) {
		try {
			person = tools.findPerson(person).get(0);
		} catch (Exception e1) {

		}
		if (!this.getResidents().contains(person)) {
			residents.add(person);
		}
		try {
			PlayerData PDITarget = plugin.playerdata.get(person);
			PDITarget.setPluralMoney(this.getPluralMoney());
			PDITarget.setSingularMoney(this.getSingularMoney());
			PDITarget.setMoneyMultiplyer(this.getMoneyMultiplyer());
			PDITarget.setCountryResides(this);
		} catch (Exception e) {

		}
		addPopulation();
	}
	// this method takes care of the player data, and country data
	public void removeResident(String person) {
		person = tools.findPerson(person).get(0);
		PlayerMethods PM = new PlayerMethods(plugin,person);
		PM.leaveCountry();
		removePopulation();
		if(this.getPopulation() == 0) {
			plugin.countrydata.remove(this.getName());
		}
	}
	
	public void addRequest(String person) {
		try {
			person = tools.findPerson(person).get(0);
		} catch (Exception e) {

		}
		request.add(person);
	}
	
	public void removeRequest(String person) {
		person = tools.findPerson(person).get(0);
		request.remove(person);
	}
	
	public void setRequest(Vector<String> list) {
		request = list;
	}
	
	public void addOffer(String person) {
		try {
			person = tools.findPerson(person).get(0);
		} catch (Exception e) {

		}
		offer.add(person);
	}
	
	public void removeOffer(String person) {
		person = tools.findPerson(person).get(0);
		offer.remove(person);
	}
	
	public void setOffer(Vector<String> list) {
		offer = list;
	}
	
	public Vector<String> getRequests() {
		return request;
	}
	
	public Vector<String> getOffers() {
		return offer;
	}
	
	public void setPopulation() {
		population = getPopulation();
		if (population > plugin.getConfig().getInt("min_population")) {
			maxLoan = new BigDecimal((population - plugin.getConfig().getInt("min_population")) * plugin.getConfig().getDouble("loan_per_country_resident")).
					add(tools.cut(new BigDecimal(plugin.getConfig().getDouble("country_start_loan"))));
		}
		else {
			maxLoan = new BigDecimal(plugin.getConfig().getDouble("country_start_loan"));
		}
	}
	
	public void setChunks(Chunks newarea) {
		area = newarea;
	}
	
	public void addChunk(ChunkData tile) {
		if(plugin.chunks.containsKey(tile) && !plugin.countrydata.get(plugin.chunks.get(tile)).equals(this)) {
			plugin.countrydata.get(plugin.chunks.get(tile)).removeChunk(tile);
		}
		this.getChunks().addChunk(tile);
		plugin.chunks.put(tile, this.getName());
	}
	
	public void setPluralMoney(String moneyname) {
		pluralMoneyName = moneyname;
		try {
			for(String resident:this.getResidents()) {
				PlayerData PDI = plugin.playerdata.get(resident);
				PDI.setPluralMoney(moneyname);
			}
		} catch (Exception e) {

		}
	}
	
	public void setSingularMoney(String moneyname) {
		singularMoneyName = moneyname;
		try {
			for(String resident:this.getResidents()) {
				PlayerData PDI = plugin.playerdata.get(resident);
				PDI.setSingularMoney(moneyname);
			}
		} catch (Exception e) {

		}
	}
	
	public void setTaxRate(double tax) {
		taxRate = tax;
		try {
			for(Town town:this.getTowns()) {
				town.setNationTax(tax);
			}
		}
		catch(Exception ex) {
			
		}
	}
	
	public void setMoneyMultiplyer(double multiplyertemp) {
		BigDecimal multiplyer = new BigDecimal(multiplyertemp, mcup);
		moneyMultiplyer = multiplyer;
		try {
			for(String resident:this.getResidents()) {
				PlayerData PDI = plugin.playerdata.get(resident);
				PDI.setMoneyMultiplyer(multiplyer);
			}
		} catch (Exception e) {

		}
	}
	
	public void setMoneyMultiplyer(BigDecimal multiplyer) {
		moneyMultiplyer = multiplyer;
		try {
			for(String resident:this.getResidents()) {
				PlayerData PDI = plugin.playerdata.get(resident);
				PDI.setMoneyMultiplyer(multiplyer);
			}
		} catch (Exception e) {

		}
	}
	
	public void setMoney(double amounttemp) {
		BigDecimal amount = new BigDecimal(amounttemp);
		money = amount.divide(moneyMultiplyer, mcup);
	}
	
	public void setMoney(BigDecimal amount) {
		money = amount.divide(moneyMultiplyer, mcup);
	}
	
	public void setRawMoney(BigDecimal amount) {
		money = amount;
	}
	
	public void addMoney(double amounttemp) {
		BigDecimal amount = new BigDecimal(amounttemp);
		money = money.add((amount.divide(moneyMultiplyer, mcup)));
	}
	
	public void addMoney(BigDecimal amount) {
		money = money.add((amount.divide(moneyMultiplyer, mcup)));
	}
	
	public void removeMoney(double amounttemp) {
		BigDecimal amount = new BigDecimal(amounttemp);
		money = money.subtract((amount.divide(moneyMultiplyer, mcdown)));
	}
	
	public void removeMoney(BigDecimal amount) {
		money = money.subtract((amount.divide(moneyMultiplyer, mcdown)));
	}
	
	public void transferMoney(double amounttemp, String targetname) {
		targetname = tools.findPerson(targetname).get(0);
		BigDecimal amount = new BigDecimal(amounttemp);
		money = money.subtract((amount.divide(moneyMultiplyer, mcdown)));
		PlayerData targetPDI = plugin.playerdata.get(targetname);
		targetPDI.addMoney(amount.divide(moneyMultiplyer, mcup).multiply(targetPDI.getMoneyMultiplyer()));
	}
	
	public void transferMoney(BigDecimal amount, String targetname) {
		targetname = tools.findPerson(targetname).get(0);
		money = money.subtract((amount.divide(moneyMultiplyer, mcdown)));
		PlayerData targetPDI = plugin.playerdata.get(targetname);
		targetPDI.addMoney(amount.divide(moneyMultiplyer, mcup).multiply(targetPDI.getMoneyMultiplyer()));
	}
	
	public void transferMoneyToCountry(double amounttemp, String targetname) {
		BigDecimal amount = new BigDecimal(amounttemp);
		targetname = tools.findCountry(targetname).get(0);
		money = money.subtract((amount.divide(moneyMultiplyer, mcdown)));
		Country country = plugin.countrydata.get(targetname);
		country.addMoney(amount.divide(moneyMultiplyer, mcup).multiply(country.getMoneyMultiplyer()));
	}
	
	public void transferMoneyToCountry(BigDecimal amount, String targetname) {
		targetname = tools.findCountry(targetname).get(0);
		money = money.subtract((amount.divide(moneyMultiplyer, mcdown)));
		Country country = plugin.countrydata.get(targetname);
		country.addMoney(amount.divide(moneyMultiplyer, mcup).multiply(country.getMoneyMultiplyer()));
	}
	
	public void transferMoneyToTown(double amounttemp, String townname, String countryname) {
		BigDecimal amount = new BigDecimal(amounttemp);
		Country countrytarget = plugin.countrydata.get(tools.findCountry(countryname).get(0));
		Town towntarget = tools.findTown(countrytarget, townname).get(0);
		money = money.subtract((amount.divide(moneyMultiplyer, mcdown)));
		towntarget.addMoney(amount.divide(moneyMultiplyer, mcup).multiply(towntarget.getMoneyMultiplyer()));
	}
	
	public void transferMoneyToTown(BigDecimal amount, String townname, String countryname) {
		Country countrytarget = plugin.countrydata.get(tools.findCountry(countryname).get(0));
		Town towntarget = tools.findTown(countrytarget, townname).get(0);
		money = money.subtract((amount.divide(moneyMultiplyer, mcdown)));
		towntarget.addMoney(amount.divide(moneyMultiplyer, mcup).multiply(towntarget.getMoneyMultiplyer()));
	}
	
	public void transferMoneyToNPC(double amounttemp) {
		BigDecimal amount = new BigDecimal(amounttemp);
		money = money.subtract((amount.divide(moneyMultiplyer, mcdown)));
	}
	
	public void transferMoneyToNPC(BigDecimal amount) {
		money = money.subtract((amount.divide(moneyMultiplyer, mcdown)));
	}
	
	public void transferMoneyFromNPC(double amounttemp) {
		BigDecimal amount = new BigDecimal(amounttemp);
		money = money.add((amount.divide(moneyMultiplyer, mcup)));
	}
	
	public void transferMoneyFromNPC(BigDecimal amount) {
		money = money.add((amount.divide(moneyMultiplyer, mcup)));
	}
	
	public void setLoan(double amounttemp) {
		BigDecimal amount = new BigDecimal(amounttemp);
		loan = amount.divide(moneyMultiplyer, mcup);
	}
	
	public void setLoan(BigDecimal amount) {
		loan = amount.divide(moneyMultiplyer, mcup);
	}
	
	public void setRawLoan(BigDecimal amount) {
		loan = amount;
	}
	
	public void addLoan(double amounttemp) {
		BigDecimal amount = new BigDecimal(amounttemp);
		loan = loan.add((amount.divide(moneyMultiplyer, mcup)));
	}
	
	public void addLoan(BigDecimal amount) {
		loan = loan.add((amount.divide(moneyMultiplyer, mcup)));
	}
	
	public void removeLoan(double amounttemp) {
		BigDecimal amount = new BigDecimal(amounttemp);
		loan = loan.subtract((amount.divide(moneyMultiplyer, mcdown)));
	}
	
	public void removeLoan(BigDecimal amount) {
		loan = loan.subtract((amount.divide(moneyMultiplyer, mcdown)));
	}
	
	public void setMaxLoan(double amounttemp) {
		BigDecimal amount = new BigDecimal(amounttemp);
		maxLoan = amount.divide(moneyMultiplyer,mcup);
	}
	
	public void setMaxLoan(BigDecimal amount) {
		maxLoan = amount.divide(moneyMultiplyer,mcup);
	}
	
	public void setRawMaxLoan(BigDecimal amount) {
		maxLoan = amount;
	}
	
	public void setProtectionLevel(int level) {
		try {
			BigDecimal oldtax = CM.getTaxAmount();
			BigDecimal newtax = CM.getTaxAmount(level);
			BigDecimal fraction = new BigDecimal(plugin.taxTimer.getFractionLeft());
			BigDecimal difference;
			
			oldtax = oldtax.multiply(BigDecimal.ONE.subtract(fraction));
			newtax = newtax.multiply(fraction);
			
			difference = oldtax.subtract(newtax);
			
			if(difference.compareTo(BigDecimal.ZERO) > 0) {
				this.transferMoneyFromNPC(difference);
			}
			else {
				this.transferMoneyToNPC(difference.negate());
			}
		} catch (Exception e) {

		}

		protectionLevel = level;
	}
	
	// Getters
	public InspiredNations getPlugin() {
		return plugin;
	}
	public String getName() {
		return name;
	}
	
	public String getRuler() {
		return ruler;
	}
	
	public Vector<String> getCoRulers() {
		return coRulers;
	}
	
	public Vector<Town> getTowns() {
		return towns;
	}
	
	public Vector<Park> getParks() {
		return parks;
	}
	
	public Vector<String> getResidents() {
		return residents;
	}
	
	public boolean isResident(String person) {
		person = tools.findPerson(person).get(0);
		if (residents.contains(person)) return true;
		else return false;
	}
	
	public int getPopulation() {
		return residents.size();
	}
	
	public void addPopulation() {
		population = getPopulation();
		if (population > plugin.getConfig().getInt("min_population")) {
			maxLoan = new BigDecimal((population - plugin.getConfig().getInt("min_population")) * plugin.getConfig().getDouble("loan_per_country_resident")).
					add(tools.cut(new BigDecimal(plugin.getConfig().getDouble("country_start_loan"))));
		}
		else {
			maxLoan = tools.cut(new BigDecimal(plugin.getConfig().getDouble("country_start_loan")));
		}
	}
	
	public void removePopulation() {
		population = getPopulation();
		if (population > plugin.getConfig().getInt("min_population")) {
			maxLoan = new BigDecimal((population - plugin.getConfig().getInt("min_population")) * plugin.getConfig().getDouble("loan_per_country_resident")).
					add(tools.cut(new BigDecimal(plugin.getConfig().getDouble("country_start_loan"))));
		}
		else {
			maxLoan = tools.cut(new BigDecimal(plugin.getConfig().getDouble("country_start_loan")));
		}
	}
	
	public Chunks getChunks() {
		return area;
	}
	
	public boolean townClaimed(Chunk chunk) {
		boolean result = false;
		for (Town town: this.getTowns()) {
			if (town.getChunks().Chunks.contains(tools.ChunktoPoint(chunk))) {
				result = true;
				break;
			}
		}
		return result;
	}
	
	public boolean townClaimed(Point chunk, String worldname) {
		boolean result = false;
		for (Town town: this.getTowns()) {
			if (town.getChunks().Chunks.contains(new ChunkData(chunk, worldname))) {
				result = true;
				break;
			}
		}
		return result;
	}
	
	public boolean townClaimed(ChunkData chunk) {
		boolean result = false;
		for(Town town: this.getTowns()) {
			if(town.getChunks().Chunks.contains(chunk)) {
				result = true;
				break;
			}
		}
		return result;
	}
	
	public Town TownOfChunk(Chunk chunk) {
		for (Town town:this.getTowns()) {
			if(town.getChunks().Chunks.contains(new ChunkData(tools.ChunktoPoint(chunk), chunk.getWorld().getName()))) {
				return town;
			}
		}
		return null;
	}
	
	public Town TownOfChunk(ChunkData chunk) {
		for (Town town:this.getTowns()) {
			if(town.getChunks().Chunks.contains(chunk)) {
				return town;
			}
		}
		return null;
	}
	
	public boolean isIn(Location tile) {
		return area.isIn(tile);
	}
	
	public String getPluralMoney() {
		return pluralMoneyName;
	}
	
	public String getSingularMoney() {
		return singularMoneyName;
	}
	
	public double getTaxRate() {
		return taxRate;
	}
	
	public BigDecimal getMoneyMultiplyer() {
		return moneyMultiplyer;
	}
	
	public BigDecimal getMoney() {
		return tools.cut(money.multiply(moneyMultiplyer, mcup));
	}
	
	public BigDecimal getRawMoney() {
		return money;
	}
	
	public int size() {
		if (area == null) return 0;
		else return area.Area()/256;
	}
	
	public BigDecimal getLoanAmount() {
		return tools.cut(loan.multiply(moneyMultiplyer, mcup));
	}
	
	public BigDecimal getRawLoanAmount() {
		return loan;
	}
	
	public BigDecimal getMaxLoan() {
		return tools.cut(maxLoan.multiply(moneyMultiplyer, mcup));
	}
	
	public BigDecimal getRawMaxLoan() {
		return maxLoan;
	}
	
	public BigDecimal getRevenue() {
		BigDecimal taxRevenue = new BigDecimal(0);
		for (int i = 0; i < towns.size(); i++) {
			taxRevenue = taxRevenue.add(towns.get(i).getTaxAmount());
		}
		return taxRevenue;
	}
	
	public boolean hasCapital() {
		for(Iterator<Town> i = towns.iterator(); i.hasNext();) {
			Town town = i.next();
			if (town.isCapital()) return true;
		}
		return false;
	}
	
	public Town getCapital() {
		for(Iterator<Town> i = towns.iterator(); i.hasNext();) {
			Town town = i.next();
			if (town.isCapital()) return town;
		}
		return null;
	}
	
	public int getProtectionLevel() {
		return protectionLevel;
	}
	
	public void removeChunk(Point tile, String worldname) {
		this.getChunks().removeChunk(new ChunkData(tile,worldname));
		plugin.chunks.remove(new ChunkData(tile, worldname));
		CutTowns(tile, worldname);
	}
	
	public void removeChunk(ChunkData tile) {
		this.getChunks().removeChunk(tile);
		plugin.chunks.remove((tile));
		CutTowns(tile.point, tile.world);
	}
	
	public void CutTowns(Point tile, String worldname) {
		// Check towns to see if any of them got cut out
		for (Iterator<Town> i = getTowns().iterator(); i.hasNext();) {
			Town town = i.next();
			if (town.getChunks().isIn(tile, worldname)) {
				TownMethods TM = new TownMethods(plugin, town);
				town.getChunks().removeChunk(tile, worldname);
				transferMoneyToTown(TM.getCostPerChunk(taxType.OLD), town.getName(), getName());
				town.removeCutOutRegions();
			}
		}
	}
}
