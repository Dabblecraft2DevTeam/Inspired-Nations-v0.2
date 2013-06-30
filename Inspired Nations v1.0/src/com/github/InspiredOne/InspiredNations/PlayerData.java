package com.github.InspiredOne.InspiredNations;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Vector;

import org.bukkit.conversations.Conversation;

import com.github.InspiredOne.InspiredNations.Regions.Country;
import com.github.InspiredOne.InspiredNations.Regions.GoodBusiness;
import com.github.InspiredOne.InspiredNations.Regions.House;
import com.github.InspiredOne.InspiredNations.Regions.LocalBank;
import com.github.InspiredOne.InspiredNations.Regions.LocalPrison;
import com.github.InspiredOne.InspiredNations.Regions.Park;
import com.github.InspiredOne.InspiredNations.Regions.ServiceBusiness;
import com.github.InspiredOne.InspiredNations.Regions.Town;

public class PlayerData {

	// Initializing Variables
	// Locations Variables
	private InspiredNations plugin;
	private Tools tools;
	private House houseIn = null;
	private Town townIn = null;
	private boolean inCapital = false;
	private Country countryIn = null;
	private LocalPrison localPrisonIn = null;
	private LocalBank localBankIn = null;
	private GoodBusiness goodBusinessIn = null;
	private ServiceBusiness serviceBusinessIn = null;
	private Park localParkIn = null;
	private Park federalParkIn = null;

	// Portfolio Variables
	public String playername;
	private Town townMayor = null;
	private Country countryRuler = null;
	private Vector<House> houseOwned = new Vector<House>();
	private Vector<GoodBusiness> goodBusinessOwned = new Vector<GoodBusiness>();
	private Vector<ServiceBusiness> serviceBusinessOwned = new Vector<ServiceBusiness>();
	private LocalPrison localPrisonJailed = null;
	private Town townResides = null;
	private Country countryResides = null;
	
	// Economy Variables
	private BigDecimal money = new BigDecimal(500);
	private BigDecimal moneyInBankHigh = new BigDecimal(300);
	private BigDecimal moneyInBankLow = new BigDecimal(300);
	private BigDecimal moneyMultiplyer = new BigDecimal(1);
	private String pluralMoneyName = "coins";
	private String singularMoneyName = "coin";
	private double houseTax = 0;
	private double goodBusinessTax = 0;
	private double serviceBusinessTax = 0;
	private BigDecimal loanAmount = new BigDecimal(0);
	private BigDecimal maxLoan = new BigDecimal(5000);
	private MathContext mcup = new MathContext(100, RoundingMode.UP);
	private MathContext mcdown = new MathContext(100, RoundingMode.DOWN);
	
	private Conversation convo = null;
	
	// Grabbing instance of plugin
	public PlayerData(InspiredNations instance, String playername) {
		plugin = instance;
		tools = new Tools(plugin);
		this.playername = playername;
	}

	// Economy Setters
	public void setMoney(double onHandtemp) {
		BigDecimal onHand = new BigDecimal(onHandtemp);
		money = onHand.divide(moneyMultiplyer, mcup);
	}
	
	public void setMoney(BigDecimal onHand) {
		money = onHand.divide(moneyMultiplyer, mcup);
	}
	
	public void setRawMoney(double onHand) {
		money = new BigDecimal(onHand);
	}
	
	public void setRawMoney(BigDecimal onHand) {
		money = onHand;
	}
	
	public void removeMoney(double taketemp) {
		BigDecimal take = new BigDecimal(taketemp);
		money = money.subtract(take.divide(moneyMultiplyer, mcdown));
	}
	
	public void removeMoney(BigDecimal take) {
		money = money.subtract(take.divide(moneyMultiplyer, mcdown));
	}
	
	public void addMoney(double givetemp) {
		BigDecimal give = new BigDecimal(givetemp);
		money = money.add(give.divide(moneyMultiplyer, mcup));
	}
	
	public void addMoney(BigDecimal give) {
		money = money.add(give.divide(moneyMultiplyer, mcup));
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
	
	public void setMoneyInBank(double amounttemp) {
		BigDecimal amount = new BigDecimal(amounttemp);
		moneyInBankHigh = amount.divide(moneyMultiplyer, mcup);
		moneyInBankLow = amount.divide(moneyMultiplyer, mcdown);
	}
	
	public void setMoneyInBank(BigDecimal amount) {
		moneyInBankHigh = amount.divide(moneyMultiplyer, mcup);
		moneyInBankLow = amount.divide(moneyMultiplyer, mcdown);
	}
	
	public void setRawMoneyInBankHigh(BigDecimal amount) {
		moneyInBankHigh = amount;
	}
	
	public void setRawMoneyInBankLow(BigDecimal amount)	{
		moneyInBankLow = amount;
	}
	
	public void addMoneyInBank(double amounttemp) {
		BigDecimal amount = new BigDecimal(amounttemp);
		if (amount.compareTo(BigDecimal.ZERO) > 0) {
			moneyInBankHigh = moneyInBankHigh.add((amount.divide(moneyMultiplyer, mcup)));
			moneyInBankLow = moneyInBankLow.add((amount.divide(moneyMultiplyer, mcdown)));
		}
		else {
			moneyInBankHigh = moneyInBankHigh.add((amount.divide(moneyMultiplyer, mcdown)));
			moneyInBankLow = moneyInBankLow.add((amount.divide(moneyMultiplyer, mcup)));
		}
	}
	
	public void addMoneyInBank(BigDecimal amount) {
		if (amount.compareTo(BigDecimal.ZERO) > 0) {
			moneyInBankHigh = moneyInBankHigh.add((amount.divide(moneyMultiplyer, mcup)));
			moneyInBankLow = moneyInBankLow.add((amount.divide(moneyMultiplyer, mcdown)));
		}
		else {
			moneyInBankHigh = moneyInBankHigh.add((amount.divide(moneyMultiplyer, mcdown)));
			moneyInBankLow = moneyInBankLow.add((amount.divide(moneyMultiplyer, mcup)));
		}
	}
	
	public void transferMoneyToBank(double amounttemp) {
		BigDecimal amount = new BigDecimal(amounttemp);
		if (amount.compareTo(BigDecimal.ZERO) > 0) {
			moneyInBankHigh = moneyInBankHigh.add((amount.divide(moneyMultiplyer, mcup)));
			moneyInBankLow = moneyInBankLow.add((amount.divide(moneyMultiplyer, mcdown)));
		}
		else {
			moneyInBankHigh = moneyInBankHigh.add((amount.divide(moneyMultiplyer, mcdown)));
			moneyInBankLow = moneyInBankLow.add((amount.divide(moneyMultiplyer, mcup)));
		}
	}
	
	public void transferMoneyToBank(BigDecimal amount) {
		if (amount.compareTo(BigDecimal.ZERO) > 0) {
			moneyInBankHigh = moneyInBankHigh.add((amount.divide(moneyMultiplyer, mcup)));
			moneyInBankLow = moneyInBankLow.add((amount.divide(moneyMultiplyer, mcdown)));
		}
		else {
			moneyInBankHigh = moneyInBankHigh.add((amount.divide(moneyMultiplyer, mcdown)));
			moneyInBankLow = moneyInBankLow.add((amount.divide(moneyMultiplyer, mcup)));
		}
	}
	
	public void setMoneyMultiplyer(double multiplyer) {
		BigDecimal multiplyertemp = new BigDecimal(multiplyer);
		moneyMultiplyer = multiplyertemp;
	}
	
	public void setMoneyMultiplyer(BigDecimal multiplyer) {
		moneyMultiplyer = multiplyer;
	}
	
	public void setPluralMoney(String plural) {
		pluralMoneyName = plural;
	}
	
	public void setSingularMoney(String single) {
		singularMoneyName = single;
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
	
	public void setLoanAmount(double amount) {
		BigDecimal amounttemp = new BigDecimal(amount);
		loanAmount = amounttemp.divide(moneyMultiplyer, mcup);
	}
	
	public void setLoanAmount(BigDecimal amount) {
		loanAmount = amount.divide(moneyMultiplyer, mcup);
	}
	
	public void setRawLoanAmount(BigDecimal amount){
		loanAmount = amount;
	}
	
	public void addLoan(double amounttemp) {
		BigDecimal amount = new BigDecimal(amounttemp);
		loanAmount = loanAmount.add((amount.divide(moneyMultiplyer, mcup)));
	}
	
	public void addLoan(BigDecimal amount) {
		loanAmount = loanAmount.add((amount.divide(moneyMultiplyer, mcup)));
	}
	
	public void removeLoan(double amounttemp) {
		BigDecimal amount = new BigDecimal(amounttemp);
		loanAmount = loanAmount.subtract((amount.divide(moneyMultiplyer, mcdown)));
	}
	
	public void removeLoan(BigDecimal amount) {
		loanAmount = loanAmount.subtract((amount.divide(moneyMultiplyer, mcdown)));
	}
	
	public void setMaxLoan(double amounttemp) {
		BigDecimal amount = new BigDecimal(amounttemp);
		maxLoan = amount;
	}
	
	public void setMaxLoan(BigDecimal amount) {
		maxLoan = amount.divide(moneyMultiplyer, mcup);
	}
	
	public void setRawMaxLoan(BigDecimal amount) {
		maxLoan = amount;
	}
	
	// Economy Getters (pre-cut)
	public BigDecimal getMoney() {
		return tools.cut(money.multiply(moneyMultiplyer));
	}
	
	public BigDecimal getRawMoney() {
		return money;
	}
	
	public BigDecimal getMoneyInBank() {
		return tools.cut(moneyInBankHigh.multiply(moneyMultiplyer, mcup));
	}
	
	public BigDecimal getRawMoneyInBankHigh() {
		return moneyInBankHigh;
	}
	public BigDecimal getRawMoneyInBankLow() {
		return moneyInBankLow;
	}
	
	
	public BigDecimal getMoneyOnHand() {
		return tools.cut((money.subtract(moneyInBankLow)).multiply(moneyMultiplyer, mcup));
		// Whenever subtracting the money in bank from something, always use  moneyInBankLow
	}
	
	public BigDecimal getMoneyMultiplyer() {
		return moneyMultiplyer;
	}
	
	public String getPluralMoney() {
		return pluralMoneyName;
	}
	
	public String getSingularMoney() {
		return singularMoneyName;
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
	
	public BigDecimal getLoanAmount() {
		return tools.cut(loanAmount.multiply(moneyMultiplyer));
	}
	
	public BigDecimal getRawLoanAmount() {
		return loanAmount;
	}
	
	public BigDecimal getMaxLoan() {
		return tools.cut(maxLoan.multiply(moneyMultiplyer));
	}
	
	public BigDecimal getRawMaxLoan() {
		return maxLoan;
	}
	
	public Conversation getConversation() {
		return convo;
	}
	
	// Location Getters and Setters
	
	// House, put in Null if not in House
	public void setHouseIn(House theHouseIn) {
		houseIn = theHouseIn;
	}
	
	public House getHouseIn() {
		return houseIn;
	}
	
	public boolean getIsInHouse() {
		try {
			if(!houseIn.equals(null)) {
				return true;
			}
			else {
				return false;
			}
		}
		catch (Exception Ex) {
			return false;
		}
	}
	
	// Town, put in Null if not in Town
	public void setTownIn(Town theTownIn) {
		townIn = theTownIn;
	}
	
	public Town getTownIn() {
		return townIn;
	}
	
	public boolean getIsInTown() {
		try {
			if(!townIn.equals(null)) {
				return true;
			}
			else {
				return false;
			}
		}
		catch (Exception Ex) {
			return false;
		}
	}
	
	// Capital
	public void setInCapital(boolean isInCapital) {
		inCapital = isInCapital;
	}
	
	public boolean getInCapital() {
		return inCapital;
	}
	
	// Country, put in Null if not in Country
	public void setCountryIn(Country theCountryIn) {
		countryIn = theCountryIn;
	}
	
	public Country getCountryIn() {
		return countryIn;
	}
	
	public boolean getIsInCountry() {
		try {
			if(!countryIn.equals(null)) {
				return true;
			}
			else {
				return false;
			}
		}
		catch (Exception Ex) {
			return false;
		}
	}
	
	// LocalPrison, put in Null if not in LocalPrison
	public void setLocalPrisonIn(LocalPrison theLocalPrisonIn) {
		localPrisonIn = theLocalPrisonIn;
	}
	
	public LocalPrison getLocalPrisonIn() {
		return localPrisonIn;
	}
	
	public boolean getIsInLocalPrison() {
		try {
			if(!localPrisonIn.equals(null)) {
				return true;
			}
			else {
				return false;
			}
		}
		catch (Exception Ex) {
			return false;
		}
	}
	
	// LocalBank, put in Null if not in LocalBank
	public void setLocalBankIn(LocalBank theLocalBankIn) {
		localBankIn = theLocalBankIn;
	}
	
	public LocalBank getLocalBankIn() {
		return localBankIn;
	}
	
	public boolean getIsInLocalBank() {
		try {
			if(!localBankIn.equals(null)) {
				return true;
			}
			else {
				return false;
			}
		}
		catch (Exception Ex) {
			return false;
		}
	}
	
	// GoodBusiness, put in Null if not in GoodBusiness
	public void setGoodBusinessIn(GoodBusiness theGoodBusinessIn) {
		goodBusinessIn = theGoodBusinessIn;
	}
	
	public GoodBusiness getGoodBusinessIn() {
		return goodBusinessIn;
	}
	
	public boolean getIsInGoodBusiness() {
		try {
			if(!goodBusinessIn.equals(null)) {
				return true;
			}
			else {
				return false;
			}
		}
		catch (Exception Ex) {
			return false;
		}
	}
	
	// ServiceBusiness, put in Null if not in ServiceBusiness
	public void setServiceBusinessIn(ServiceBusiness theServiceBusinessIn) {
		serviceBusinessIn = theServiceBusinessIn;
	}
	
	public ServiceBusiness getServiceBusinessIn() {
		return serviceBusinessIn;
	}
	
	public boolean getIsInServiceBusiness() {
		try {
			if(!serviceBusinessIn.equals(null)) {
				return true;
			}
			else {
				return false;
			}
		}
		catch (Exception Ex) {
			return false;
		}
	}
	
	// LocalPark, put in Null if not in LocalPark
	public void setLocalParkIn(Park theLocalParkIn) {
		localParkIn = theLocalParkIn;
	}
	
	public Park getLocalParkIn() {
		return localParkIn;
	}
	
	public boolean getIsInLocalPark() {
		try {
			if(!localParkIn.equals(null)) {
				return true;
			}
			else {
				return false;
			}
		}
		catch (Exception Ex) {
			return false;
		}
	}
	
	// FederalPark, put in Null if not in FederalPark
	public void setFederalParkIn(Park theFederalParkIn) {
		federalParkIn = theFederalParkIn;
	}
	
	public Park getFederalParkIn() {
		return federalParkIn;
	}
	
	public boolean getIsInFederalPark() {
		try {
			if(!federalParkIn.equals(null)) {
				return true;
			}
			else {
				return false;
			}
		}
		catch (Exception Ex) {
			return false;
		}
	}
	
	// Portfolio Getters and Setters
	
	// TownMayor, put in Null if not TownMayor
	public void setTownMayored(Town theTownMayored) {
		townMayor = theTownMayored;
	}
	
	public Town getTownMayored() {
		return townMayor;
	}
	
	public boolean getIsTownMayor() {
		try {
			if(!townMayor.equals(null)) {
				return true;
			}
			else {
				return false;
			}
		}
		catch (Exception Ex) {
			return false;
		}
	}
	
	// CountryRuler, put in Null if not CountryRuler
	public void setCountryRuled(Country theCountryRuled) {
		countryRuler = theCountryRuled;
	}
	
	public Country getCountryRuled() {
		return countryRuler;
	}
	
	public boolean getIsCountryRuler() {
		try {
			if(!countryRuler.equals(null)) {
				return true;
			}
			else {
				return false;
			}
		}
		catch (Exception ex) {
			return false;
		}
	}
	
	// HouseOwner
	public void setHouseOwned(Vector<House> housesOwned) {
		houseOwned = housesOwned;
	}
	
	public void addHouseOwned(House house) {
		houseOwned.add(house);
	}
	
	public void removeHouseOwned(House house) {
		houseOwned.remove(house);
	}
	
	public Vector<House> getHouseOwned() {
		return houseOwned;
	}
	
	public boolean isHouseOwner() {
		if(houseOwned.isEmpty()) {
			return false;
		}
		else {
			return true;
		}
	}
	
	// GoodBusiness
	public void setGoodBusinessOwned(Vector<GoodBusiness> goodBusinessesOwned) {
		goodBusinessOwned = goodBusinessesOwned;
	}
	
	public void addGoodBusinessOwned(GoodBusiness business) {
		goodBusinessOwned.add(business);
	}
	
	public void removeGoodBusinessOwned(GoodBusiness business) {
		goodBusinessOwned.remove(business);
	}
	
	public Vector<GoodBusiness> getGoodBusinessOwned() {
		return goodBusinessOwned;
	}
	
	public boolean isGoodBusinessOwner() {
		if(goodBusinessOwned.isEmpty()) {
			return false;
		}
		else {
			return true;
		}
	}
	
	// ServiceBusiness
	public void setServiceBusinessOwned(Vector<ServiceBusiness> serviceBusinessesOwned) {
		serviceBusinessOwned = serviceBusinessesOwned;
	}
	
	public void addServiceBusinessOwned(ServiceBusiness business) {
		serviceBusinessOwned.add(business);
	}
	
	public void removeServiceBusinessOwned(ServiceBusiness business) {
		serviceBusinessOwned.remove(business);
	}
	
	public Vector<ServiceBusiness> getServiceBusinessOwned() {
		return serviceBusinessOwned;
	}
	
	public boolean isServiceBusinessOwner() {
		if(serviceBusinessOwned.isEmpty()) {
			return false;
		}
		else {
			return true;
		}
	}
	
	// Jailed, put in Null if not jailed
	public void setLocalPrisonJailed(LocalPrison thelocalprisonjailed) {
		localPrisonJailed = thelocalprisonjailed;
	}
	
	public LocalPrison getLocalPrisonJailed() {
		return localPrisonJailed;
	}
	
	public boolean isLocalPrisonJailed() {
		try {
			if(!localPrisonJailed.equals(null)) {
				return true;
			}
			else {
				return false;
			}
		}
		catch (Exception ex) {
			return false;
		}
	}
	
	// Town Resides, put in Null if not resident of a Town
	public void setTownResides(Town theTownResides) {

		townResides = theTownResides;
	}
	
	public Town getTownResides() {
		return townResides;
	}
	
	public boolean getIsTownResident() {
		try {
			if(!townResides.equals(null)) {
				return true;
			}
			else {
				return false;
			}
		}
		catch (Exception Ex) {
			return false;
		}
	}
	
	// Country Resides, put in Null if not resident of a Country
	public void setCountryResides(Country theCountryResides) {

		countryResides = theCountryResides;
	}
	
	public Country getCountryResides() {
		return countryResides;
	}
	
	public boolean getIsCountryResident() {
		try {
			if(!countryResides.equals(null)) {
				return true;
			}
			else {
				return false;
			}
		}
		catch (Exception ex) {
			return false;
		}
	}

	public void setConversation(Conversation conversation) {
		convo = conversation;
		
	}
}
