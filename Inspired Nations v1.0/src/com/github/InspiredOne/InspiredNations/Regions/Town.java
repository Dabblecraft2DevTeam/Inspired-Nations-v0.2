package com.github.InspiredOne.InspiredNations.Regions;

import java.awt.Point;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.Vector;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.PlayerMethods;
import com.github.InspiredOne.InspiredNations.Tools;
import com.github.InspiredOne.InspiredNations.TownMethods;
import com.github.InspiredOne.InspiredNations.Tools.version;

public class Town {
	
	private InspiredNations plugin;
	private Tools tools;
	private String country;
	private String name = "";
	private String mayor = "";
	private Vector<String> coMayors = new Vector<String>();
	private Vector<House> houses = new Vector<House>();
	private Vector<GoodBusiness> goodBusinesses = new Vector<GoodBusiness>();
	private Vector<ServiceBusiness> serviceBusinesses = new Vector<ServiceBusiness>();
	private Vector<Park> parks = new Vector<Park>();
	private Vector<String> residents = new Vector<String>();
	private Vector<String> request = new Vector<String>();
	private Vector<String> offer = new Vector<String>();
	private LocalPrison prison = null;
	private LocalBank bank = null;
	private Chunks area = new Chunks();
	private double nationTax = 1;
	private double nationTaxOld = 1;
	private double houseTax = 1;
	private double goodBusinessTax = 1;
	private double serviceBusinessTax = 1;
	private String pluralMoneyName = "";
	private String singularMoneyName = "";
	private BigDecimal money = new BigDecimal(0);
	private BigDecimal moneyMultiplyer = new BigDecimal(1);
	private BigDecimal loan = new BigDecimal(0);
	private BigDecimal maxLoan;
	private BigDecimal refund = BigDecimal.ZERO;
	private boolean isCapital = false;
	private int protectionLevel = 0;
	private int militaryLevel = 0;
	private MathContext mcup = new MathContext(100, RoundingMode.UP);
	private MathContext mcdown = new MathContext(100, RoundingMode.DOWN);
	TownMethods TM;
	
	public Town(InspiredNations instance, Chunks areatemp, String nametemp, String mayortemp, String countrytemp) {
		plugin = instance;
		name = nametemp;
		maxLoan =  new BigDecimal(plugin.getConfig().getDouble("town_start_loan"));
		country = countrytemp;
		area = areatemp;
		tools = new Tools(plugin);
		this.setMayor(mayortemp);
		this.addResident(mayor);
		Country countryobj = plugin.countrydata.get(countrytemp);
		this.setNationTax(countryobj.getTaxRate());
		this.setPluralMoney(countryobj.getPluralMoney());
		this.setSingularMoney(countryobj.getSingularMoney());
		this.setMoneyMultiplyer(countryobj.getMoneyMultiplyer());
		if(!countryobj.hasCapital()) {
			this.setIsCapital(true);
		}
		countryobj.addTown(this);
		TM = new TownMethods(plugin, this);

	}
	
	public Town(InspiredNations instance, String nametemp, String mayortemp, String countrytemp) {
		plugin = instance;
		name = nametemp;
		mayor = mayortemp;
		maxLoan =  new BigDecimal(plugin.getConfig().getDouble("town_start_loan"));
		country = countrytemp;
		tools = new Tools(plugin);
		this.setMayor(mayortemp);
		this.addResident(mayor);
		Country countryobj = plugin.countrydata.get(countrytemp);
		this.setNationTax(countryobj.getTaxRate());
		this.setPluralMoney(countryobj.getPluralMoney());
		this.setSingularMoney(countryobj.getSingularMoney());
		this.setMoneyMultiplyer(countryobj.getMoneyMultiplyer());
		if(countryobj.hasCapital()) {
			this.setIsCapital(true);
		}
		countryobj.addTown(this);
		TM = new TownMethods(plugin, this);

	}
	
	public void setCountry(String countryname) {
		countryname = tools.findCountry(countryname).get(0);
		country = countryname;
	}
	
	public void setName(String nametemp) {
		name = nametemp;
	}
	
	public void setMayor(String mayortemp) {
		try {
			mayortemp = tools.findPerson(mayortemp).get(0);
		} catch (Exception e1) {

		}
		try {
			PlayerData PDITarget = plugin.playerdata.get(mayortemp);
			PDITarget.setTownMayored(this);
		} catch (Exception e) {

		}
		mayor = mayortemp;
	}
	
	public void setCoMayors(Vector<String> coMayorsTemp) {
		coMayors = coMayorsTemp;
	}
	
	public void addCoMayor(Player player) {
		try {
			PlayerData PDITarget = plugin.playerdata.get(player.getName());
			PDITarget.setTownMayored(this);
		} catch (Exception e) {

		}
		
		coMayors.add(player.getName());
	}
	
	public void addCoMayor(String playername) {
		try {
			playername = tools.findPerson(playername).get(0);
		} catch (Exception e1) {

		}
		try {
			PlayerData PDITarget = plugin.playerdata.get(playername);
			PDITarget.setTownMayored(this);
		} catch (Exception e) {

		}
		coMayors.add(playername);
	}
	
	public void removeCoMayor(Player player) {
		try {
			PlayerData PDITarget = plugin.playerdata.get(player.getName());
			PDITarget.setTownMayored(null);
		} catch (Exception e) {

		}
		coMayors.remove(player.getName());
	}
	
	public void removeCoMayor(String playername) {
		playername = tools.findPerson(playername).get(0);
		try {
			PlayerData PDITarget = plugin.playerdata.get(playername);
			PDITarget.setTownMayored(null);
		} catch (Exception e) {

		}
		coMayors.remove(playername);
	}
	
	public void addRequest(String person) {
		person = tools.findPerson(person).get(0);
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
		person = tools.findPerson(person).get(0);
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
	
	public void setChunks(Chunks newarea) {
		area = newarea;
	}
	
	public void setHouses(Vector<House> housestemp) {
		houses = housestemp;
	}
	
	public void addHouse(House house) {
		houses.add(house);
	}
	
	public void removeHouse(House house) {
		houses.remove(house);
	}
	
	public void setGoodBusinesses(Vector<GoodBusiness> business) {
		goodBusinesses = business;
	}
	
	public void addGoodBusiness(GoodBusiness business) {
		goodBusinesses.add(business);
	}
	
	public void removeGoodBusiness(GoodBusiness business) {
		goodBusinesses.remove(business);
	}
	
	public void setServiceBusinesses(Vector<ServiceBusiness> business) {
		serviceBusinesses = business;
	}
	
	public void addServiceBusiness(ServiceBusiness business) {
		serviceBusinesses.add(business);
	}
	
	public void removeServiceBusiness(ServiceBusiness business) {
		serviceBusinesses.remove(business);
	}
	
	public void setParks(Vector<Park> park) {
		parks = park;
	}
	
	public void addPark(Park park) {
		parks.add(park);
	}
	
	public void removePark(Park park) {
		parks.remove(park);
	}
	
	public void setResidents(Vector<String> people) {
		residents = people;
		if (residents.size() > plugin.getConfig().getInt("min_comayors")) {
			maxLoan = new BigDecimal((residents.size() - plugin.getConfig().getInt("min_comayors")) * plugin.getConfig().getDouble("Loan_per_town_resident")).
					add(tools.cut(new BigDecimal(plugin.getConfig().getDouble("town_start_loan"))));
		}
		else {
			maxLoan = tools.cut(new BigDecimal(plugin.getConfig().getDouble("town_start_loan")));
		}
	}
	
	public void addResident(String person) {
		try {
			person = tools.findPerson(person).get(0);
		} catch (Exception e) {

		}
		try {
			PlayerData PDITarget = plugin.playerdata.get(person);
			PDITarget.setTownResides(this);
			PDITarget.setHouseTax(this.houseTax);
			PDITarget.setGoodBusinessTax(this.goodBusinessTax);
			PDITarget.setServiceBusinessTax(serviceBusinessTax);
		} catch (Exception e) {

		}
		if (!residents.contains(person)) {
			residents.add(person);
		}
		if (residents.size() > plugin.getConfig().getInt("min_comayors")) {
			maxLoan = new BigDecimal((residents.size() - plugin.getConfig().getInt("min_comayors")) * plugin.getConfig().getDouble("Loan_per_town_resident")).
					add(tools.cut(new BigDecimal(plugin.getConfig().getDouble("town_start_loan"))));
		}
		else {
			maxLoan = tools.cut(new BigDecimal(plugin.getConfig().getDouble("town_start_loan")));
		}
	}
	
	public void removeResident(String person) {
		person = tools.findPerson(person).get(0);
		try {
			PlayerData PDITarget = plugin.playerdata.get(person);
			PDITarget.setTownResides(null);
		} catch (Exception e) {

		}
		residents.remove(person);
		if (residents.size() > plugin.getConfig().getInt("min_comayors")) {
			maxLoan = new BigDecimal((residents.size() - plugin.getConfig().getInt("min_comayors")) * plugin.getConfig().getDouble("Loan_per_town_resident")).
					add(tools.cut(new BigDecimal(plugin.getConfig().getDouble("town_start_loan"))));
		}
		else {
			maxLoan = tools.cut(new BigDecimal(plugin.getConfig().getDouble("town_start_loan")));
		}
	}
	
	public void setPrison(LocalPrison building) {
		prison = building;
	}
	
	public void setBank(LocalBank building) {
		bank = building;
	}
	
	public void setNationTax(double tax) {
		nationTax = tax;
	}
	
	public void setHouseTax(double tax) {
		houseTax = tax;
	}
	
	public void setGoodBusinessTax(double tax) {
		goodBusinessTax = tax;
	}
	
	public void setServiceBusinessTax(double tax) {
		serviceBusinessTax = tax;
	}
	public void setIsCapital(boolean isTheCapital) {
		isCapital = isTheCapital;
	}
	
	// Economy Setters
	public void setPluralMoney(String name) {
		pluralMoneyName = name;
	}
	
	public void setSingularMoney(String name) {
		singularMoneyName = name;
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
	
	public void setMoneyMultiplyer(double multiplyertemp) {
		BigDecimal multiplyer = new BigDecimal(multiplyertemp);
		moneyMultiplyer = multiplyer;
	}
	
	public void setMoneyMultiplyer(BigDecimal multiplyer) {
		moneyMultiplyer = multiplyer;
	}
	
	public void changeMoneyMultiplyer(BigDecimal multiplyer) {
		BigDecimal money = this.getMoney();
		BigDecimal loan = this.getLoan();
		BigDecimal refund = this.getRefund();
		this.moneyMultiplyer = multiplyer;
		this.setMoney(money);
		this.setLoan(loan);
		this.setRefund(refund);
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
		maxLoan = amount.divide(moneyMultiplyer, mcup);
	}
	
	public void setMaxLoan(BigDecimal amount) {
		maxLoan = amount.divide(moneyMultiplyer, mcup);
	}
	
	public void setRawMaxLoan(BigDecimal amount) {
		maxLoan = amount;
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
		this.removeMoney(amount);
		Country country = plugin.countrydata.get(targetname);
		country.addMoney(amount.divide(moneyMultiplyer, mcup).multiply(country.getMoneyMultiplyer()));
	}
	
	public void transferMoneyToCountry(BigDecimal amount, String targetname) {
		targetname = tools.findCountry(targetname).get(0);
		this.removeMoney(amount);
		Country country = plugin.countrydata.get(targetname);
		country.addMoney(amount.divide(moneyMultiplyer, mcup).multiply(country.getMoneyMultiplyer()));
	}
	
	public void transferMoneyToTown(double amounttemp, String townname, String countryname) {
		BigDecimal amount = new BigDecimal(amounttemp);
		Country countrytarget = plugin.countrydata.get(tools.findCountry(countryname).get(0));
		Town towntarget = tools.findTown(countrytarget, townname).get(0);
		this.removeMoney(amount);
		towntarget.addMoney(amount.divide(moneyMultiplyer, mcup).multiply(towntarget.getMoneyMultiplyer()));
	}
	
	public void transferMoneyToTown(BigDecimal amount, String townname, String countryname) {
		Country countrytarget = plugin.countrydata.get(tools.findCountry(countryname).get(0));
		Town towntarget = tools.findTown(countrytarget, townname).get(0);
		this.removeMoney(amount);
		towntarget.addMoney(amount.divide(moneyMultiplyer, mcup).multiply(towntarget.getMoneyMultiplyer()));
	}
	
	public void setProtectionLevel(int level) {
		this.protectionLevel = level;
	}
	
	public void changeProtectionLevel(int protection) {
	
		try {
			BigDecimal oldtax = TM.getTaxAmount(true, version.OLD);
			BigDecimal newtax = TM.getTaxAmount(protection, this.getMilitaryLevel(), true, version.OLD);
			BigDecimal fraction = new BigDecimal(plugin.taxTimer.getFractionLeft());
			BigDecimal difference;
			
			oldtax = oldtax.multiply(fraction);
			newtax = newtax.multiply(fraction);
			
			
			difference = oldtax.subtract(newtax);
			
			if(difference.compareTo(BigDecimal.ZERO) > 0) {
				plugin.countrydata.get(tools.findCountry(this.getCountry()).get(0)).transferMoneyToTown(difference, name, this.getCountry());
			}
			else {
				this.transferMoneyToCountry(difference.negate(), this.getCountry());
			}
		} catch (Exception e) {
		}
		
		protectionLevel = protection;
	}
	
	// Getters
	public InspiredNations getPlugin() {
		return plugin;
	}
	
	public String getCountry() {
		return country;
	}
	
	public String getName() {
		return name;
	}
	
	public String getMayor() {
		return mayor;
	}
	
	public Vector<String> getCoMayors() {
		return coMayors;
	}
	
	public Vector<House> getHouses() {
		return houses;
	}
	
	public Vector<GoodBusiness> getGoodBusinesses() {
		return goodBusinesses;
	}
	
	public Vector<ServiceBusiness> getServiceBusinesses() {
		return serviceBusinesses;
	}
	
	
	public Vector<Park> getParks() {
		return parks;
	}
	
	public Vector<String> getResidents() {
		return residents;
	}
	
	public boolean isResident(Player person) {
		if (residents.contains(person.getName())) return true;
		else return false;
	}
	
	public boolean isResident(String person) {
		person = tools.findPerson(person).get(0);
		if (residents.contains(person)) return true;
		else return false;
	}
	
	public int population() {
		return residents.size();
	}
	
	public LocalPrison getPrison() {
		return prison;
	}
	
	public LocalBank getBank() {
		return bank;
	}
	
	public Chunks getChunks() {
		return area;
	}
	
	public boolean isIn(Location tile) {
		return area.isIn(tile);
	}
	
	public boolean isIn(Point spot, String world) {
		return area.isIn(spot, world);
	}
	
	public int getArea() {
		return area.Area();
	}
	
	public int getVolume() {
		return area.Volume();
	}
	
	public double getNationTax() {
		return nationTax;
	}
	
	public double getHouseTax() {
		return houseTax;
	}
	
	public double getGoodBusinessTax() {
		return goodBusinessTax;
	}
	
	public double getServiceBusinessTax() {
		return serviceBusinessTax;
	}
	
	public String getPluralMoney() {
		return pluralMoneyName;
	}
	
	public String getSingularMoney() {
		return singularMoneyName;
	}
	
	public BigDecimal getMoney() {
		return tools.cut(money.multiply(moneyMultiplyer));
	}
	
	public BigDecimal getRawMoney() {
		return money;
	}
	
	public BigDecimal getMoneyMultiplyer() {
		return moneyMultiplyer;
	}
	
	public BigDecimal getLoan() {
		return tools.cut(loan.multiply(moneyMultiplyer));
	}
	
	public BigDecimal getRawLoan() {
		return loan;
	}
	
	public BigDecimal getMaxLoan() {
		return tools.cut(maxLoan.multiply(moneyMultiplyer));
	}
	
	public BigDecimal getRawMaxLoan() {
		return maxLoan;
	}
	
	public BigDecimal getRevenue() {
		BigDecimal taxRevenue = BigDecimal.ZERO;
		for (String playername:plugin.countrydata.get(country).getResidents()) {
			PlayerMethods PMI = new PlayerMethods(plugin, playername);
			taxRevenue = taxRevenue.add(PMI.taxAmount(name, false, true, version.NEW));
		}
		return tools.cut(taxRevenue.multiply(this.getMoneyMultiplyer()));
	}
	
	public int getProtectionLevel() {
		return protectionLevel;
	}
	
	public boolean isCapital() {
		return isCapital;
	}
	
	public boolean hasPrison() {
		try {
			if (!prison.equals(null)) return true;
			else return false;
		}
		catch (Exception ex) {
			return false;
		}
	}
	
	public boolean hasBank() {
		try {
			if (!bank.equals(null)) return true;
			else return false;
		}
		catch (Exception ex) {
			return false;
		}
	}
	
	public void removeClaimedRegions() {
		prison = null;
		bank = null;
		area = new Chunks();
		for (House house: houses) {
			for (String owner: house.getOwners()) {
				plugin.playerdata.get(owner).removeHouseOwned(house);
			}
			this.removeHouse(house);
		}
		for (GoodBusiness business: goodBusinesses) {
			for (String owner: business.getOwners()) {
				plugin.playerdata.get(owner).removeGoodBusinessOwned(business);
			}
			this.removeGoodBusiness(business);
		}
		for (ServiceBusiness business: serviceBusinesses) {
			for (String owner: business.getOwners()) {
				plugin.playerdata.get(owner).removeServiceBusinessOwned(business);
			}
			this.removeServiceBusiness(business);
		}
		for (Park park: parks) {
			this.removePark(park);
		}
	}
	
	public void removeCutOutRegions() {
		// Check claimed regions to see if anything got cut out.
		Town town = this;
		if (town.hasBank()) {
			if (!town.getBank().isInTown()) {
				town.setBank(null);
			}
		}
		if (town.hasPrison()) {
			if (!town.getPrison().isInTown()) {
				town.setPrison(null);
			}
		}
		for (House house: town.getHouses()) {
			if (!house.isInTown()) {
				for (String owner: house.getOwners()) {
					plugin.playerdata.get(owner).removeHouseOwned(house);
				}
				town.removeHouse(house);
			}
		}
		for (GoodBusiness business: town.getGoodBusinesses()) {
			if (!business.isInTown()) {
				for (String owner: business.getOwners()) {
					plugin.playerdata.get(owner).removeGoodBusinessOwned(business);
				}
				town.removeGoodBusiness(business);
			}
		}
		for (ServiceBusiness business: town.getServiceBusinesses()) {
			if (!business.isInTown()) {
				for (String owner: business.getOwners()) {
					plugin.playerdata.get(owner).removeServiceBusinessOwned(business);
				}
				town.removeServiceBusiness(business);
			}
		}
		for (Park park: town.getParks()) {
			if (!park.isInTown()) {
				town.removePark(park);
			}
		}
		
		
	}
	public BigDecimal getTaxAmount() {
		BigDecimal amount = new BigDecimal(getArea()*getProtectionLevel()*getNationTax()/100);
		for (int i = 0; i < getParks().size(); i++) {
			amount = amount.add(getMoneyMultiplyer().multiply((new BigDecimal(getParks().get(i).Volume() * getParks().get(i).getProtectionLevel() * getProtectionLevel() * getNationTax()/10000 * plugin.getConfig().getDouble("park_tax_multiplyer")))));
		}
		return tools.cut(amount);
	}
	
	public BigDecimal getTaxAmount(int level) {
		BigDecimal amount = new BigDecimal(getArea()*level*getNationTax()/100);
		for (int i = 0; i < getParks().size(); i++) {
			amount = amount.add(getMoneyMultiplyer().multiply((new BigDecimal(getParks().get(i).Volume() * getParks().get(i).getProtectionLevel() * getNationTax()/10000 * plugin.getConfig().getDouble("park_tax_multiplyer")*level))));
		}
		return tools.cut(amount);
	}

	public double getNationTaxOld() {
		return nationTaxOld;
	}

	public void setNationTaxOld(double nationTaxOld) {
		this.nationTaxOld = nationTaxOld;
	}
	
	public void removeChunk(ChunkData tile) {
		this.getChunks().removeChunk(tile);
		this.removeCutOutRegions();
	}
	
	public Vector<Business> getBusinesses() {
		Vector<Business> result = new Vector<Business>();
		for(Business i:this.getGoodBusinesses()) {
			result.add(i);
		}
		for(Business i:this.getServiceBusinesses()) {
			result.add(i);
		}
		return result;
	}

	public int getMilitaryLevel() {
		return militaryLevel;
	}

	public void setMilitaryLevel(int militaryLevel) {
		this.militaryLevel = militaryLevel;
	}
	
	public void changeMilitaryLevel(int level) {
		try {
			BigDecimal oldtax = TM.getMilitaryFunding(true, version.OLD);
			BigDecimal newtax = TM.getMilitaryFunding(level, true, version.OLD);
			BigDecimal fraction = new BigDecimal(plugin.taxTimer.getFractionLeft());
			BigDecimal difference;
			
			oldtax = oldtax.multiply(fraction);
			newtax = newtax.multiply(fraction);
			
			difference = oldtax.subtract(newtax);
			
			if(difference.compareTo(BigDecimal.ZERO) > 0) {
				plugin.countrydata.get(tools.findCountry(this.getCountry()).get(0)).transferMoneyToTown(difference, name, this.getCountry());
			}
			else {
				this.transferMoneyToCountry(newtax, this.getCountry());
				this.addRefund(newtax.add(difference));
			}
			
		} catch (Exception e) {

		}

		militaryLevel = level;
	}

	public BigDecimal getRawRefund() {
		return refund;
	}

	public void setRawRefund(BigDecimal refund) {
		this.refund = refund;
	}
	
	public BigDecimal getRefund() {
		return refund.multiply(this.moneyMultiplyer);
	}

	public void setRefund(BigDecimal refund) {
		this.refund = refund.divide(moneyMultiplyer);
	}
	
	public void addRefund(BigDecimal refund) {
		this.refund.add(refund.divide(moneyMultiplyer));
	}
}
