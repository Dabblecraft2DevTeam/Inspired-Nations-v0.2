package com.github.InspiredOne.InspiredNations;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;


import com.github.InspiredOne.InspiredNations.Regions.Business;
import com.github.InspiredOne.InspiredNations.Regions.ChunkData;
import com.github.InspiredOne.InspiredNations.Regions.Country;
import com.github.InspiredOne.InspiredNations.Regions.GoodBusiness;
import com.github.InspiredOne.InspiredNations.Regions.House;
import com.github.InspiredOne.InspiredNations.Regions.InspiredRegion;
import com.github.InspiredOne.InspiredNations.Regions.LocalBank;
import com.github.InspiredOne.InspiredNations.Regions.LocalPrison;
import com.github.InspiredOne.InspiredNations.Regions.Park;
import com.github.InspiredOne.InspiredNations.Regions.ServiceBusiness;
import com.github.InspiredOne.InspiredNations.Regions.Town;

public class Tools {

	public Vector<String> errors = new Vector<String>();
	// Grabbing instance of plugin
	InspiredNations plugin;
	public Tools(InspiredNations instance) {
		plugin = instance;
		errors.add("");
		errors.add("\nThat is not an option. Response must be a number.");//1
		errors.add("\nThat is not an option.");//2
		errors.add("\nIncorrect number of arguments.");//3
		errors.add("\nAdd more letters. It could be: ");//4
		errors.add("\nThat player does not exist on this server.");//5
		errors.add("\nSomething went wrong, be sure you entered your payment as a number.");//6
		errors.add("\nSomething went wrong, be sure you entered your deposit as a number.");//7
		errors.add("\nYou don't have that much money on hand to deposit.");//8
		errors.add("\nYou don't have that much money in the bank to withdraw.");//9
		errors.add("\nYou are not allowed to take out that much in loans.");//10
		errors.add("\nSomething went wrong, be sure you entered your loan amount as a number.");//11
		errors.add("\nYou don't have that much in loans to pay back.");//12
		errors.add("\nYou Don't have that much money on hand.");//13
		errors.add("\nThat country name is already taken.");//14
		errors.add("\nYou need to put both the singular form and plural form of your money separated by a space.");//15
		errors.add("\nThat money name already exists");//16
		errors.add("\nSomething went wrong, be sure you entered your answer as a number.");//17
		errors.add("\nYou can't put the value of your diamond that high.");//18
		errors.add("\nYou can't put the value of your diamond that low.");//19
		errors.add("\nThat country does not exist on this server.");//20
		errors.add("\nThat town does not exist on this server.");//21
		errors.add("\nThe country does not have that much money.");//22
		errors.add("\nYou need to put a '/' in between the town's country and the town");//23
		errors.add("\nIllegal Character '/'. You cannot create a country name with '/'.)");//24
		errors.add("\nInsufficient Funds.");//25
		errors.add("\nThat town name is already taken.");//26
		errors.add("\nThe selection you made was a complex shape. This means that the sides of the shape crossed each other.");//27
		errors.add("\nThe selection you made was too small with a volume of 0. Please select something larger.");//28
		errors.add("\nThe selection you made went outside of the town.");//29
		errors.add("\nThe selection you made contained part of somebody's house");//30
		errors.add("\nThe selection you made contained part of somebody's goods business");//31
		errors.add("\nThe selection you made contained part of somebody's service business");//32
		errors.add("\nThe selection you made contained part of a local park.");//33
		errors.add("\nThe selection you made contained part of the bank.");//34
		errors.add("\nThe selection you made contained part of the prison.");//35
		errors.add("\nThat business name is already taken.");//36
		errors.add("\nThat town does not exist in this country.");//37
		errors.add("\nThe selection you made went outside of the country.");//38
		errors.add("\nThe selection you made contained part of a federal park.");//39
		errors.add("\nThat player already has been offered ownership.");//40
		errors.add("\nThat player has not been offered ownership.");//41
		errors.add("\nThat player is already a builder.");//42
		errors.add("\nThat player is not a builder.");//43
		errors.add("\nThat player is already a worker.");//44
		errors.add("\nThat player has not been offered a job.");//45
		errors.add("\nThat player is not a resident of your country.");//46
		errors.add("\nThat player does not work in this business.");//47
		errors.add("\nThat player has not requested ownership.");//48
		errors.add("\nThat player has not requested a job.");//49
		errors.add("\nIllegal Character '/'. You cannot create a business name with '/'.)");//50
		errors.add("\nIllegal Character '/'. You cannot create a town name with '/'.)");//51
		errors.add("\nThat town does not exist in this country.");//52
		errors.add("\nThat business does not exist in that town.");//53
		errors.add("\nYou have already requested ownership of that business.");//54
		errors.add("\nYou have already requestion a job at that business.");//55
		errors.add("\nYou are already an owner of that business.");//56
		errors.add("\nYou already have a job there.");//57
		errors.add("\nYou need to put a '/' in between the job's town and the job");//58
		errors.add("\nYou are not an owner in that town.");//59
		errors.add("\nYou are not an owner of that business.");//60
		errors.add("\nYou are not employed in that town.");//61
		errors.add("\nYou are not an employee of that business.");//62
		errors.add("\nYou have not requested ownership of that business.");//63
		errors.add("\nYou have not requested a job from that business.");//64
		errors.add("\nYou have not been offered ownership of that business.");//65
		errors.add("\nYou have not been offered a job at that business.");//66
		errors.add("\nThat house name is already taken.");//67
		errors.add("\nThat park name is already taken.");//68
		errors.add("\nIllegal Character '/'. You cannot create a house name with '/'.)");//69
		errors.add("\nIllegal Character '/'. You cannot create a park name with '/'.)");//70
		errors.add("\nThat cell names is already in use.");//71
		errors.add("\nPrison cells must be within the prison.");//72
		errors.add("\nThat cell does not exist");//73
		errors.add("\nYou cannot place your sign on sand, gravel, or other signs.");//74
		errors.add("\nYou must put your sign inside your business.");//75
		errors.add("\nThe block that you put your sign on must be inside your business.");//76
		errors.add("\nYou must first place a valid sign.");//77
	}
	public enum version {
		OLD, NEW
	}
	
	public enum menuType {
		HEADER(ChatColor.GOLD + "" + ChatColor.BOLD),
		SUBHEADER(ChatColor.YELLOW + "" + ChatColor.ITALIC + "" + ChatColor.BOLD),
		LABEL(ChatColor.RED + ""),
		VALUE(ChatColor.GOLD + ""),
		VALUEDESCRI(ChatColor.YELLOW + ""),
		DIVIDER(ChatColor.DARK_AQUA + ""),
		
		OPTION(ChatColor.GREEN + ""),
		OPTIONNUMBER(ChatColor.GOLD + ""),
		OPTIONDESCRIP(ChatColor.GRAY + ""),
		UNAVAILABLE(ChatColor.DARK_GRAY + ""),
		UNAVAILREASON(ChatColor.GRAY + ""),
		INSTRUCTION(ChatColor.YELLOW + ""),
		ALERT(ChatColor.RED + ""),
		UNIT(ChatColor.YELLOW + ""),
		ENDINSTRU(ChatColor.AQUA + "");
		
		private String color;
		
        private menuType(String color) {
                this.color = color;
        }
        @Override
        public String toString() {
        	return color;
        }
	}

	public enum optionType {
		OPTION, UNAVAILABLE, INSTRUCTION, ALERT
	}
	public enum mapSize{
		SMALL,MEDIUM,LARGE
	}
	public enum region {
		PRISON,BANK,HOUSE,GOODBUSINESS,SERVICEBUSINESS,PARK,FEDERALPARK,TOWN,COUNTRY, REGOOD, RESERVICE, REHOUSE, REPARK, REFEDERALPARK
	}
	
	// A method that builds new lines on a string with the standard formatting
	public String addLine(String text, String addition, optionType type) {
		optionType Type = type;
		
		switch(Type) {
			case OPTION:
				text = text.concat(menuType.OPTION + addition + "\n");
				break;
			case INSTRUCTION:
				text = text.concat(menuType.INSTRUCTION + addition + "\n");
				break;
			case ALERT:
				text = text.concat(menuType.ALERT + addition + "\n");
				break;
			case UNAVAILABLE:
				text = text.concat(menuType.UNAVAILABLE + addition + "\n");
				break;
		}
		return text;
	}
	
	// A method to add the divider
	public String addDivider(String text) {
		return text.concat(menuType.DIVIDER + repeat("-", 53) + "\n");
	}
	
	// A method that builds the hud header
	public String header(String msg) {
		return addDivider(menuType.HEADER + msg + "\n" + ChatColor.RESET);
	}
	
	// A method that builds the pre-hud space
	public String space() {
		return repeat("\n", plugin.getConfig().getInt("hud_pre_message_space"));
	}
	
	// A method to build the hud footer.
	public String footer(boolean isMainHud) {
		if (isMainHud) {
			return addDivider("") + menuType.ENDINSTRU + "Type 'exit' to leave or 'say' to chat.";
		}
		else {
			return addDivider("") + menuType.ENDINSTRU + "Type 'exit' to leave, 'say' to chat, or 'back' to go back.";
		}
	}
	
	// A method to build the hud error message
	public String errormsg(int error) {
		return menuType.ALERT + this.errors.get(error);
	}
	
	// A method to build Options
	public String options(Vector<String> inputs) {
		String output = menuType.OPTION + "";
		int n = 1;
		for (String option:inputs) {
			if (option.startsWith("*")) {
				output = addLine(output, menuType.UNAVAILABLE + "[" + menuType.UNAVAILREASON + n + menuType.UNAVAILABLE +"] " + option.substring(1), optionType.UNAVAILABLE);
			}
			else {
				output = addLine(output, "["+ menuType.OPTIONNUMBER + n + ChatColor.RESET + menuType.OPTION +"] " + option, optionType.OPTION);
			}
			n += 1;
		}
		return output;
	}
	
	// A Method to build the name of an item from it's ItemStack
	public String getItemName(ItemStack stack) {
		String[] namesplit = stack.getType().name().split("_");
		String itemname = "";
		for (int i = 0; i < namesplit.length; i++) {
			itemname = itemname.concat(namesplit[i] + " ");
		}
		
		if(stack.getType().equals(Material.INK_SACK) && stack.getDurability() != 0) {
			itemname = "DYE";
		}
		
		return itemname;
	}
	
	// A Method that builds the Protection Level Text
	public String protLevels(InspiredRegion building, Player player, String regionName, int error, int Level, region Region, Vector<String> inputs) {
		String space = this.space();
		String main = this.header(regionName + " Protection Levels. Type an option number.");
		String options = "";
		String end = this.footer(false);
		String errmsg = this.errormsg(error);
		PlayerMethods PMeth = new PlayerMethods(plugin, player);
		PlayerData PDI = plugin.playerdata.get(player.getName());
		
		Country country = plugin.countrydata.get(building.getCountry());
		CountryMethods CM = new CountryMethods(plugin, country);
		Town town = PDI.getTownResides();
		TownMethods TM = new TownMethods(plugin, town);
		options = options.concat(menuType.LABEL + "Current Protection Level: " + menuType.VALUE + Level + menuType.UNIT + "\n");
		

		
		switch(Region) {
		case GOODBUSINESS:
			options = options.concat(menuType.LABEL + "Current Protection Cost: " + menuType.VALUE + PMeth.goodBusinessTax((GoodBusiness) building, true, false, version.NEW) +
					menuType.UNIT + " " + town.getPluralMoney() + "\n");
			options = options.concat(menuType.LABEL + "Cost For Next Level: " + menuType.VALUE + PMeth.goodBusinessTax((GoodBusiness) building, Level + 1, true, false, version.NEW) +
					menuType.UNIT + " " + town.getPluralMoney() + "\n");
			options = this.addDivider(options);
			options = options.concat(menuType.VALUE + "Level 0: " + menuType.VALUEDESCRI + "(No protection) Anybody that can build and interact in your town can build and interact in your business.\n");
			options = options.concat(menuType.VALUE + "Level 1: " + menuType.VALUEDESCRI + "(Block protection) Only country rulers, town rulers, employees, and business owners can build in the business.\n");
			options = options.concat(menuType.VALUE + "Level 2: " + menuType.VALUEDESCRI + "(Interact protection) Only country rulers, town rulers, employees, and business owners can interact in the business.\n");
			options = options.concat(menuType.VALUE + "Level 3: " + menuType.VALUEDESCRI + "(Player protection) Employees and owners gain protection from attacks within the business.\n");
			break;
		case SERVICEBUSINESS:
			options = options.concat(menuType.LABEL + "Current Protection Cost: " + menuType.VALUE + PMeth.serviceBusinessTax((ServiceBusiness) building, true, false, version.NEW) +
					menuType.UNIT + " " + town.getPluralMoney() + "\n");
			options = options.concat(menuType.LABEL + "Cost For Next Level: " + menuType.VALUE + PMeth.serviceBusinessTax((ServiceBusiness) building, Level + 1, true, false, version.NEW) +
					menuType.UNIT + " " + town.getPluralMoney() + "\n");
			options = this.addDivider(options);
			options = options.concat(menuType.VALUE + "Level 0: " + menuType.VALUEDESCRI + "(No protection) Anybody that can build and interact in your town can build and interact in your business.\n");
			options = options.concat(menuType.VALUE + "Level 1: " + menuType.VALUEDESCRI + "(Block protection) Only country rulers, town rulers, employees, and business owners can build in the business.\n");
			options = options.concat(menuType.VALUE + "Level 2: " + menuType.VALUEDESCRI + "(Interact protection) Only country rulers, town rulers, employees, and business owners can interact in the business.\n");
			options = options.concat(menuType.VALUE + "Level 3: " + menuType.VALUEDESCRI + "(Player protection) Employees and owners gain protection from attacks within the business.\n");
			break;
		case BANK:
			break;
		case FEDERALPARK:
			options = options.concat(menuType.LABEL + "Current Protection Cost: " + menuType.VALUE + CM.getFederalParkTax((Park) building, country.getProtectionLevel(), true, version.NEW)  +
					menuType.UNIT + " " + town.getPluralMoney() + "\n");
			options = options.concat(menuType.LABEL + "Cost For Next Level: " + menuType.VALUE + CM.getFederalParkTax((Park) building, Level + 1, country.getProtectionLevel(), true, version.NEW) +
					menuType.UNIT + " " + town.getPluralMoney() + "\n");
			options = this.addDivider(options);
			options = options.concat(menuType.VALUE + "Level 0: " + menuType.VALUEDESCRI + "(No protection) Anybody that can build in your country can build in your park.\n");
			options = options.concat(menuType.VALUE + "Level 1: " + menuType.VALUEDESCRI + "(Complete protection) Only country rulers and designated builders can build in the park.\n");
			break;
		case HOUSE:
			options = options.concat(menuType.LABEL + "Current Protection Cost: " + menuType.VALUE + PMeth.houseTax((House) building, true, false, version.NEW)  +
					menuType.UNIT + " " + town.getPluralMoney() + "\n");
			options = options.concat(menuType.LABEL + "Cost For Next Level: " + menuType.VALUE + PMeth.houseTax((House) building, Level + 1, true, false, version.NEW) +
					menuType.UNIT + " " + town.getPluralMoney() + "\n");
			options = this.addDivider(options);
			options = options.concat(menuType.VALUE + "Level 0: " + menuType.VALUEDESCRI + "(No protection) Anybody that can build and interact in your town can build and interact in your house.\n");
			options = options.concat(menuType.VALUE + "Level 1: " + menuType.VALUEDESCRI + "(Block protection) Only country rulers, town rulers, designated builders, and house owners can build in the house.\n");
			options = options.concat(menuType.VALUE + "Level 2: " + menuType.VALUEDESCRI + "(Interact protection) Only country rulers, town rulers, designated builders, and house owners can interact in the house.\n");
			options = options.concat(menuType.VALUE + "Level 3: " + menuType.VALUEDESCRI + "(Player protection) You gain damage protection from non-house residents.\n");
			break;
		case PARK:
			plugin.logger.info("her1)");
			options = options.concat(menuType.LABEL + "Current Protection Cost: " + menuType.VALUE + TM.getLocalParkTax((Park) building, town.getProtectionLevel(), true, version.NEW)  +
					menuType.UNIT + " " + town.getPluralMoney() + "\n");
			plugin.logger.info("her2)");
			options = options.concat(menuType.LABEL + "Cost For Next Level: " + menuType.VALUE + TM.getLocalParkTax((Park) building, Level + 1, town.getProtectionLevel(), true, version.NEW) +
					menuType.UNIT + " " + town.getPluralMoney() + "\n");
			plugin.logger.info("her3)");
			options = this.addDivider(options);
			options = options.concat(menuType.VALUE + "Level 0: " + menuType.VALUEDESCRI + "(No protection) Anybody that can build in your town can build in your park.\n");
			options = options.concat(menuType.VALUE + "Level 1: " + menuType.VALUEDESCRI + "(Complete protection) Only country rulers, town rulers and designated builders can build in the park.\n");
			break;
		case PRISON:
			break;
		case COUNTRY:
			break;
		case TOWN:
			break;
		default:
			break;

		}
		options = this.addDivider(options);
		options = options.concat(this.options(inputs));
		return space + main + options + end + errmsg;

	}
	
	// A method that takes an ItemStack, and returns it's name as a nice little string :)
	public String itemName(ItemStack item) {
		String[] namesplit = item.getType().name().split("_");
		String name = "";
		for (String word:namesplit) {
			name = name.concat(word + " ");
		}
		return name.substring(0, name.length() - 1).toLowerCase();
	}
	
	// A generalized find function where you give it the vector you want it to search in.
	public Vector<String> find(String name, Vector<String> test) {
		Vector<String> list = new Vector<String>();
		for (String nametest:test) {
			if (nametest.toLowerCase().contains(name.toLowerCase())) {
				list.add(nametest);
			}
			if (nametest.equalsIgnoreCase(name)) {
				list.clear();
				list.add(nametest);
				return list;
			}
		}
		return list;
	}
	
	// A method to find any person given an incomplete string;
	public Vector<String> findPerson(String name) {
		Set<String> players = plugin.playerdata.keySet();
		OfflinePlayer[] playernames = plugin.getServer().getOfflinePlayers();
		Vector<String> list = new Vector<String>();
		for (Iterator<String> i = players.iterator(); i.hasNext();) {

			String nametest = i.next();
			for (OfflinePlayer person: playernames) {
				if (nametest.equalsIgnoreCase(person.getName())) {
					nametest = person.getName();
				}
			}
			if (nametest.toLowerCase().contains(name.toLowerCase())) {
				list.add(nametest);
				if (nametest.equalsIgnoreCase(name)) {
					list.clear();
					list.add(nametest);
					return list;
				}
			}
		}
		return list;
	}
	
	// A method to find any person given an incomplete string;
	public Vector<String> findPersonExcept(String name, String except) {
		Set<String> players = plugin.playerdata.keySet();
		OfflinePlayer[] playernames = plugin.getServer().getOfflinePlayers();
		Vector<String> list = new Vector<String>();
		for (Iterator<String> i = players.iterator(); i.hasNext();) {

			String nametest = i.next();
			for (OfflinePlayer person: playernames) {
				if (nametest.equalsIgnoreCase(person.getName())) {
					nametest = person.getName();
				}
			}
			if (nametest.toLowerCase().contains(name.toLowerCase()) && !nametest.equalsIgnoreCase(except)) {
				list.add(nametest);
				if (nametest.equalsIgnoreCase(name)) {
					list.clear();
					list.add(nametest);
					return list;
				}
			}
		}
		return list;
	}
	// A method to find any person given an incomplete string and a vector;
	public Vector<String> findPersonExcept(String name, String except, Vector<String> names) {
		
		OfflinePlayer[] playernames = plugin.getServer().getOfflinePlayers();
		Vector<String> list = new Vector<String>();
		for (String nametest: names) {

			for (OfflinePlayer person: playernames) {
				if (nametest.equalsIgnoreCase(person.getName())) {
					nametest = person.getName();
				}
			}
			if (nametest.toLowerCase().contains(name.toLowerCase()) && !nametest.equalsIgnoreCase(except)) {
				list.add(nametest);
				if (nametest.equalsIgnoreCase(name)) {
					list.clear();
					list.add(nametest);
					return list;
				}
			}
		}
		return list;
	}
	
	// A method to find any country given an incomplete string;
	public Vector<String> findCountry(String name) {
		Set<String> countries = plugin.countrydata.keySet();
		Vector<String> list = new Vector<String>();
		for (Iterator<String> i = countries.iterator(); i.hasNext();) {

			String nametest = i.next();
			if (nametest.toLowerCase().contains(name.toLowerCase())) {
				list.add(nametest);
				if (nametest.equalsIgnoreCase(name)) {
					list.clear();
					list.add(nametest);
					return list;
				}
			}
		}
		return list;
	}
	
	// A method to find any town given two incomplete strings
	public Vector<String> findTown(String country, String town, boolean fulladdress) {
		Vector<String> countries = findCountry(country);
		Vector<String> towns = new Vector<String>();
		Vector<String> results = new Vector<String>();
		Vector<String> temp = new Vector<String>();
		int n;
		for(String countryname:countries) {
			n = 0;
			Country countrytest = plugin.countrydata.get(countryname);
			for(Town townconvert: countrytest.getTowns()) {
				towns.add(townconvert.getName());
			}
			temp.addAll(find(town,towns));
			if (fulladdress) {
				for(String entry:temp) {
					temp.set(n, entry + "/" + countryname);
					n +=1;
				}
			}
			results.addAll(temp);
			temp.clear();
		}
		
		return results;
	}
	
	// A method to find a town given it's country and an incomplete string
	public Vector<Town> findTown(Country country, String town){
		Vector<Town> results = new Vector<Town>();
		Vector<String> towns = new Vector<String>();
		Vector<String> temp = new Vector<String>();
		for(Town townconvert: country.getTowns()) {
			towns.add(townconvert.getName());
		}
		temp = find(town, towns);
		for(Town townconvert: country.getTowns()){
			for(String townname: temp){
				if(townconvert.getName().equals(townname)) {
					results.add(townconvert);
				}
				else if(townconvert.getName().equalsIgnoreCase(town)) {
					results.clear();
					results.add(townconvert);
					return results;
				}
			}
		}
		return results;
	}
	
	public Vector<Business> findBusiness(Town town, String businessname) {
		Vector<Business> businesses = new Vector<Business>();
		
		for(Business business:town.getBusinesses()) {
			if(business.getName().toLowerCase().contains(businessname.toLowerCase())) {
				businesses.add(business);
			}
			if(business.getName().equalsIgnoreCase(businessname)) {
				businesses.clear();
				businesses.add(business);
				return businesses;
			}
		}
		return businesses;
	}
	
	// A method to find any money name given an incomplete string;
	public Vector<String> findMoney(String name){
		Vector<String> singular = new Vector<String>();
		Vector<String> plural = new Vector<String>();
		for (Iterator<String> i = plugin.countrydata.keySet().iterator(); i.hasNext();) {
			Country temp = plugin.countrydata.get(i.next());
			singular.add(temp.getSingularMoney());
			plural.add(temp.getPluralMoney());
		}
		singular.addAll(plural);
		return find(name, singular);
	}
	
	// A method that determines duplicates of money names
	public boolean moneyUnique(String name) {
		boolean unique = true;
		for (Iterator<String> i = plugin.countrydata.keySet().iterator(); i.hasNext();) {
			Country temp = plugin.countrydata.get(i.next());
			if (temp.getSingularMoney().equalsIgnoreCase(name)) unique = false;
			if (temp.getPluralMoney().equalsIgnoreCase(name)) unique = false;
		}
		return unique;
	}
	
	public boolean countryUnique(String name) {
		boolean unique = true;
		for (Iterator<String> i = plugin.countrydata.keySet().iterator(); i.hasNext();) {
			Country temp = plugin.countrydata.get(i.next());
			if (temp.getName().equalsIgnoreCase(name)) unique = false;
		}
		return unique;
	}
	
	public boolean townUnique(String test, String country) {
		boolean unique = true;
		for (Town town:plugin.countrydata.get(country).getTowns()) {
			if (town.getName().equalsIgnoreCase(test)) {
				unique = false;
			}
		}
		return unique;
	}
	
	// A method to take a Vector<String> and return all the elements as a formated String with commas.
	public String format(Vector<String> words) {
		String result = "";
		if (words.size()==0) {
			return "";
		}
		for (String i : words) {
			result = result.concat(i + ", ");
		}
		return result.substring(0, result.length() - 2);
	}
	
	// A method to take a Vector<String> and return all the elements as a formated String with spaces.
	public String formatSpace(Vector<String> words) {
		String result = "";
		if (words.size()==0) {
			return "";
		}
		for (String i : words) {
			result = result.concat(i + " ");
		}
		return result.substring(0, result.length() - 1);
	}
	
	// A method to take a Vector<String> and return all the elements as a formated String with spaces.
	public String formatSpace(String[] words) {
		String result = "";
		if (words.length==0) {
			return "";
		}
		for (String i : words) {
			result = result.concat(i + " ");
		}
		return result.substring(0, result.length() - 1);
	}
	
	// A method that rights the region type cuboid/polygonprism selection menu
	public String writeRegionSelection1(String regionName, Vector<String> inputs, int error) {
		String space = this.space();
		String main = this.header("Claim " + regionName + ". Pick your selection type.");
		String options = "";
		String end = this.footer(false);
		String errmsg = this.errormsg(error);
		options = options.concat(menuType.INSTRUCTION + "What kind of selection would you like to make?\n\n" + "A " + menuType.VALUE + "cuboid" + menuType.INSTRUCTION + " is a perfect rectangular prism requiring you" +
				" to select two opposite corners of the 'box'.\n" + "A " + menuType.VALUE + "polygon" + menuType.INSTRUCTION + " is a shape with as many corners as you want. The highest and lowest corners" +
				" dictate where the top and bottom of your selection is.\n");
		options = this.addDivider(options);
		options = options.concat(this.options(inputs));
		return space + main + options + end + errmsg;
	}
	
	// A method that determines if Federal Park Selection is valid
	public boolean selectionFederalValid(Player player) {
		PlayerData PDI = plugin.playerdata.get(player.getName());
		PlayerModes PM = plugin.playermodes.get(player.getName());
		ConversationContext context = PDI.getConversation().getContext();
		Country country = PDI.getCountryRuled();
		if (PM.isSelectingPolygon()) {
			Rectangle rect = PM.getPolygon().getPolygon().getBounds();
			if (!isSimple(PM.getPolygon().getPolygon())) {
				context.setSessionData("error", 27);
				return false;
			}

			if (PM.getPolygon().Area() == 0){
				context.setSessionData("error", 28);
				return false;
			}
				
			for (int i = (int) rect.getMinX(); i < (int) rect.getMaxX(); i++) {
				
				// Makes the Progress Bar
				int done = (int) Math.ceil(((i - rect.getMinX() + 1)/(rect.getMaxX() - rect.getMinX() + 2)) * 30);
				player.sendRawMessage(menuType.INSTRUCTION + this.space() + "Determining if selection is valid.\n");
				player.sendRawMessage(ChatColor.GRAY + " [" + ChatColor.GREEN + repeat("#",done) +
						ChatColor.GRAY + repeat("#", (int) (30 - done)) + "]");
				
				for (int j = (int) rect.getMinY(); j < (int)rect.getMaxY(); j++) {
					for (int l = PM.getPolygon().getYMin(); l <= PM.getPolygon().getYMax(); l++) {
						Location test = null;
						test = new Location(plugin.getServer().getWorld(PM.getPolygon().getWorld()), i, l, j);
						
						if ((!country.isIn(test)) && PM.getPolygon().isIn(test)) {
							context.setSessionData("error", 38);
							return false;
						}
						for (Park park : country.getParks()) {
							if ((PM.getPolygon().isIn(test) && park.isIn(test))) {
								context.setSessionData("error", 39);
								return false;
							}
						}
					}
				}
			}
			PM.setBlocksBack();
			PM.federalPark(false);
			
			String ParkName = "";
			int test = country.getParks().size();
			while(ParkName.isEmpty()) {
				if(country.getParks().contains("Park " + test)) {
					test +=1;
				}
				else {
					ParkName = "Park " + test;
				}
			}
			
			country.addPark(new Park(plugin, PM.getPolygon(), country.getName(), -1, true, ParkName));
		}
		else {
			if (PM.getCuboid().Volume() == 0) {
				context.setSessionData("error", 28);
				return false;
			}
			for (int i = PM.getCuboid().getXmin(); i <= PM.getCuboid().getXmax(); i++) {
				
				// Makes the Progress Bar
				int done = (int) Math.ceil(((i - PM.getCuboid().getXmin() + 1) * 30)/(PM.getCuboid().getXmax() - PM.getCuboid().getXmin() + 1));
				player.sendRawMessage(menuType.INSTRUCTION + this.space() + "Determining if selection is valid.\n");
				player.sendRawMessage(ChatColor.GRAY + " [" + ChatColor.GREEN + repeat("#", done) +
						ChatColor.GRAY + repeat("#", (int) (30 - done)) + "]");
				
				for (int j = PM.getCuboid().getYmin(); j <= PM.getCuboid().getYmax(); j++) {
					for (int l = PM.getCuboid().getZmin(); l <= PM.getCuboid().getZmax(); l++) {
						Location test = null;
						test = new Location(plugin.getServer().getWorld(PM.getCuboid().getWorld()), i, j, l);

						if ((!country.isIn(test))) {
							context.setSessionData("error", 38);
							return false;
						}
						for (Park park : country.getParks()) {
							if ((PM.getCuboid().isIn(test) && park.isIn(test))) {
								context.setSessionData("error", 39);
								return false;
							}
						}
					}
				}
			}
			PM.setBlocksBack();
			PM.federalPark(false);
			
			String ParkName = "";
			int test = country.getParks().size();
			while(ParkName.isEmpty()) {
				if(country.getParks().contains("Park " + test)) {
					test +=1;
				}
				else {
					ParkName = "Park " + test;
				}
			}

			country.addPark(new Park(plugin, PM.getCuboid(), country.getName(), -1, true, ParkName));
		}
		return true;
	}
	
	// A method that determines if a selection is valid
	public boolean selectionValid(Player player, region type) {
		PlayerData PDI = plugin.playerdata.get(player.getName());
		PlayerModes PM = plugin.playermodes.get(player.getName());
		ConversationContext context = PDI.getConversation().getContext();
		Town town = PDI.getTownResides();
		
		
		// Finish
			player.sendRawMessage(menuType.INSTRUCTION + "Please wait while the server determines if this is a valid selection.");
			if (PM.isSelectingPolygon()) {
				plugin.logger.info("2");
				Rectangle rect = PM.getPolygon().getPolygon().getBounds();
				if (!this.isSimple(PM.getPolygon().getPolygon())) {
					context.setSessionData("error", 27);
					return false;
				}
				if (PM.getPolygon().Volume() == 0) {
					context.setSessionData("error", 28);
					return false;
				}
				for (int i = (int) rect.getMinX() - 1; i <= (int) rect.getMaxX() + 1; i++) {
					plugin.logger.info("3");
					// Makes the Progress Bar
					int done = (int) Math.ceil(((i - rect.getMinX() + 1)/(rect.getMaxX() - rect.getMinX() + 2)) * 30);
					player.sendRawMessage(menuType.INSTRUCTION + this.space() + "Determining if selection is valid.\n");
					player.sendRawMessage(ChatColor.GRAY + " [" + ChatColor.GREEN + repeat("#",done) +
							ChatColor.GRAY + repeat("#", (int) (30 - done)) + "]");
					plugin.logger.info("4");
					// Iterates Through
					for (int j = (int) rect.getMinY() - 1; j <= (int)rect.getMaxY() + 1; j++) {
						for (int l = PM.getPolygon().getYMin() - 1; l <= PM.getPolygon().getYMax() + 1; l++) {
							Location test = new Location(player.getWorld(), i, l, j);
							if ((!town.isIn(test)) && PM.getPolygon().isIn(test)) {
								context.setSessionData("error",29);
								return false;
							}
							plugin.logger.info("4a");
							for (Iterator<House> k = town.getHouses().iterator(); k.hasNext();) {
								House housetemp = k.next();
								if (housetemp.isIn(test) && PM.getPolygon().isIn(test)){
									context.setSessionData("error",30);
									return false;
								}
							}
							plugin.logger.info("4b");
							for (Iterator<GoodBusiness> k = town.getGoodBusinesses().iterator(); k.hasNext();) {
								GoodBusiness businesstemp = k.next();
								if (businesstemp.isIn(test) && PM.getPolygon().isIn(test)){
									context.setSessionData("error",31);
									return false;
								}
							}
							plugin.logger.info("4c");
							for (Iterator<ServiceBusiness> k = town.getServiceBusinesses().iterator(); k.hasNext();) {
								ServiceBusiness businesstemp = k.next();
								if (businesstemp.isIn(test) && PM.getPolygon().isIn(test)){
									context.setSessionData("error",32);
									return false;
								}
							}
							plugin.logger.info("4d");
							for (Iterator<Park> k = town.getParks().iterator(); k.hasNext();) {
								Park parktemp = k.next();
								if (parktemp.isIn(test) && PM.getPolygon().isIn(test)){
									context.setSessionData("error",33);
									return false;
								}
							}
							plugin.logger.info("4e");
							if (town.hasBank()) {
								if (town.getBank().isIn(test) && PM.getPolygon().isIn(test) && !PM.localBankSelect()) {
									context.setSessionData("error",34);
									return false;
								}
							}
							plugin.logger.info("4f");
							if (town.hasPrison()) {
								if(town.getPrison().isIn(test) && PM.getPolygon().isIn(test) && !PM.localPrisonSelect()) {
									context.setSessionData("error", 35);
									return false;
								}
							}
						}
					}
				}
				
				// put the region in the town
				plugin.logger.info("5");
				switch(type) {
					case PRISON:
						if(!town.hasPrison()) {
							town.setPrison(new LocalPrison(plugin, PM.getPolygon(), town.getCountry(), plugin.countrydata.get(town.getCountry()).getTowns().indexOf(town)));
						}
						break;
					case BANK:
						if(!town.hasBank()) {
							town.setBank(new LocalBank(plugin, PM.getPolygon(), town.getCountry(), plugin.countrydata.get(town.getCountry()).getTowns().indexOf(town)));
						}
						break;
					case GOODBUSINESS:
						String BusinessName = "";
						int test2 = 0;
						boolean works2 = true;
						Country country = PDI.getCountryResides();
						for (Town towntest: country.getTowns()) {
							test2+=towntest.getGoodBusinesses().size();
						}
						while(BusinessName.isEmpty()) {
							works2 = true;
							for(GoodBusiness businesstest: town.getGoodBusinesses()) {
								if(businesstest.getName().equalsIgnoreCase("Good Business " + test2)) {
									works2 = false;
									break;
								}
							}
							for(ServiceBusiness businesstest: town.getServiceBusinesses()) {
								if(businesstest.getName().equalsIgnoreCase("Good Business " + test2)) {
									works2 = false;
									break;
								}
							}
							if (!works2) {
								test2 +=1;
							}
							else {
								BusinessName = "Good Business " + test2;
							}
						}
						
						GoodBusiness businesstemp = new GoodBusiness(plugin, PM.getPolygon(), player, country.getName(), country.getTowns().indexOf(town), BusinessName);
						PDI.addGoodBusinessOwned(businesstemp);
						PDI.getTownResides().addGoodBusiness(businesstemp);
						break;
						
					case SERVICEBUSINESS:
						plugin.logger.info("6");
						String BusinessName1 = "";
						int test21 = 0;
						boolean works21 = true;
						Country country1 = PDI.getCountryResides();
						plugin.logger.info("7");
						for (Town towntest: country1.getTowns()) {
							test21+=towntest.getServiceBusinesses().size();
						}
						plugin.logger.info(BusinessName1.isEmpty() + "");
						while(BusinessName1.isEmpty()) {
							plugin.logger.info("here");
							works21 = true;
							for(GoodBusiness businesstest: town.getGoodBusinesses()) {
								if(businesstest.getName().equalsIgnoreCase("Service Business " + test21)) {
									works21 = false;
									break;
								}
							}
							for(ServiceBusiness businesstest: town.getServiceBusinesses()) {
								if(businesstest.getName().equalsIgnoreCase("Service Business " + test21)) {
									works21 = false;
									break;
								}
							}
							if (!works21) {
								test21 +=1;
							}
							else {
								plugin.logger.info("here2");
								BusinessName1 = "Service Business " + test21;
								plugin.logger.info(BusinessName1);
							}
						}
						plugin.logger.info("8");
						plugin.logger.info(BusinessName1 + "2");
						ServiceBusiness businesstemp1 = new ServiceBusiness(plugin, PM.getPolygon(), player, country1.getName(), country1.getTowns().indexOf(town), BusinessName1);
						plugin.logger.info(businesstemp1.getName());
						PDI.addServiceBusinessOwned(businesstemp1);
						town.addServiceBusiness(businesstemp1);
						break;
						
					case HOUSE:
						String HouseName = "";
						int test1 = 0;
						boolean works = true;

						while(HouseName.isEmpty()) {
							works = true;
							for(House housetest:town.getHouses()) {
								if(housetest.getName().equalsIgnoreCase("House " + test1)) {
									works21 = false;
									break;
								}
							}

							if (!works) {
								test1 +=1;
							}
							else {
								HouseName = "House " + test1;
							}
						}
						
						House housetemp = new House(plugin, PM.getPolygon(), player, PDI.getCountryResides().getName(), PDI.getCountryResides().getTowns().indexOf(town), HouseName);
						PDI.addHouseOwned(housetemp);
						PDI.getTownResides().addHouse(housetemp);
						break;
					case PARK:
						if(!PM.isReSelectLocalPark()) {
							String ParkName = "";
							int test = town.getParks().size();
							boolean works3 = true;
							while(ParkName.isEmpty()) {
								works3 = true;
								for(Park parktest: town.getParks()) {
									if(parktest.getName().equalsIgnoreCase("Park " + test)) {
										works3 = false;
										break;
									}
								}
								if(!works3) {
									test += 1;
								}
								else {
									ParkName = "Park " + test;
								}
	
							}
							Park park = new Park(plugin, PM.getPolygon(), town.getCountry(), plugin.countrydata.get(town.getCountry()).getTowns().indexOf(town),false,(ParkName));
							town.addPark(park);
							PDI.getConversation().getContext().setSessionData("localpark", park);
						}
						break;
					case FEDERALPARK:
						if(!PM.isReSelectFederalPark()) {
							Country country11 = PDI.getCountryResides();
							String ParkName1 = "";
							int test11 = country11.getParks().size();
							boolean works31 = true;
							while(ParkName1.isEmpty()) {
								works31 = true;
								for(Park parktest: country11.getParks()) {
									if(parktest.getName().equalsIgnoreCase("Park " + test11)) {
										works31 = false;
										break;
									}
								}
								if(!works31) {
									test11 += 1;
								}
								else {
									ParkName1 = "Park " + test11;
								}
								Park park = new Park(plugin, PM.getPolygon(), country11.getName(), -1, true, ParkName1);
								country11.addPark(park);
								PDI.getConversation().getContext().setSessionData("federalpakr", park);
							}
						}
						break;
					case REGOOD:
						break;
					case RESERVICE:
						break;
					case REHOUSE:
						break;
				case COUNTRY:
					break;
				case TOWN:
					break;
				default:
					break;
				}
			}
			if (PM.isSelectingCuboid()) {
				if (PM.getCuboid().Volume() == 0) {
					context.setSessionData("error",28);
					return false;
				}
				for (int i = PM.getCuboid().getXmin(); i <= PM.getCuboid().getXmax(); i++) {
					
					// Makes the Progress Bar
					int done = (int) Math.ceil(((i - PM.getCuboid().getXmin() + 1) * 30)/(PM.getCuboid().getXmax() - PM.getCuboid().getXmin() + 1));
					player.sendRawMessage(menuType.INSTRUCTION + this.space() + "Determining if selection is valid.\n");
					player.sendRawMessage(ChatColor.GRAY + " [" + ChatColor.GREEN + repeat("#", done) +
							ChatColor.GRAY + repeat("#", (int) (30 - done)) + "]");
					// Iterate Through
					for (int j = PM.getCuboid().getYmin(); j <= PM.getCuboid().getYmax(); j++) {
						for (int l = PM.getCuboid().getZmin(); l <= PM.getCuboid().getZmax(); l++) {
							Location test = new Location(player.getWorld(), i, j, l);
							if ((!town.isIn(test))) {
								context.setSessionData("error",29);
								return false;
							}
							for (Iterator<House> k = town.getHouses().iterator(); k.hasNext();) {
								House housetemp = k.next();
								if (housetemp.isIn(test)){
									context.setSessionData("error",30);
									return false;
								}
							}
							for (Iterator<GoodBusiness> k = town.getGoodBusinesses().iterator(); k.hasNext();) {
								GoodBusiness businesstemp = k.next();
								if (businesstemp.isIn(test)){
									context.setSessionData("error",31);
									return false;
								}
							}
							for (Iterator<ServiceBusiness> k = town.getServiceBusinesses().iterator(); k.hasNext();) {
								ServiceBusiness businesstemp = k.next();
								if (businesstemp.isIn(test)){
									context.setSessionData("error",32);
									return false;
								}
							}
							for (Iterator<Park> k = town.getParks().iterator(); k.hasNext();) {
								Park parktemp = k.next();
								if (parktemp.isIn(test)){
									context.setSessionData("error",33);
									return false;
								}
							}
							if (town.hasBank() && !PM.localBankSelect()) {
								if (town.getBank().isIn(test)) {
									context.setSessionData("error",34);
									return false;
								}
							}
							if (town.hasPrison() && !PM.localPrisonSelect()) {
								if(town.getPrison().isIn(test)) {
									context.setSessionData("error", 35);
									return false;
								}
							}
						}
					}
				}
				// put the region in the town
				switch(type) {
					case PRISON:
						if(!town.hasPrison()) {
							town.setPrison(new LocalPrison(plugin, PM.getCuboid(), town.getCountry(), plugin.countrydata.get(town.getCountry()).getTowns().indexOf(town)));
						}
						break;
					case BANK:
						if(!town.hasBank()) {
							town.setBank(new LocalBank(plugin, PM.getCuboid(), town.getCountry(), plugin.countrydata.get(town.getCountry()).getTowns().indexOf(town)));
						}
						break;
					case GOODBUSINESS:
						String BusinessName = "";
						int test2 = 0;
						boolean works2 = true;
						Country country = PDI.getCountryResides();
						for (Town towntest: country.getTowns()) {
							test2+=towntest.getGoodBusinesses().size();
						}
						while(BusinessName.isEmpty()) {
							works2 = true;
							for(GoodBusiness businesstest: town.getGoodBusinesses()) {
								if(businesstest.getName().equalsIgnoreCase("Good Business " + test2)) {
									works2 = false;
									break;
								}
							}
							for(ServiceBusiness businesstest: town.getServiceBusinesses()) {
								if(businesstest.getName().equalsIgnoreCase("Good Business " + test2)) {
									works2 = false;
									break;
								}
							}
							if (!works2) {
								test2 +=1;
							}
							else {
								BusinessName = "Good Business " + test2;
							}
						}
						
						GoodBusiness businesstemp = new GoodBusiness(plugin, PM.getCuboid(), player, country.getName(), country.getTowns().indexOf(town), BusinessName);
						PDI.addGoodBusinessOwned(businesstemp);
						PDI.getTownResides().addGoodBusiness(businesstemp);
						break;
						
					case SERVICEBUSINESS:
						String BusinessName1 = "";
						int test21 = 0;
						boolean works21 = true;
						Country country1 = PDI.getCountryResides();
						for (Town towntest: country1.getTowns()) {
							test21+=towntest.getServiceBusinesses().size();
						}
						while(BusinessName1.isEmpty()) {
							works21 = true;
							for(GoodBusiness businesstest: town.getGoodBusinesses()) {
								if(businesstest.getName().equalsIgnoreCase("Service Business " + test21)) {
									works21 = false;
									break;
								}
							}
							for(ServiceBusiness businesstest: town.getServiceBusinesses()) {
								if(businesstest.getName().equalsIgnoreCase("Service Business " + test21)) {
									works21 = false;
									break;
								}
							}
							if (!works21) {
								test21 +=1;
							}
							else {
								BusinessName1 = "Service Business " + test21;
							}
						}
						
						ServiceBusiness businesstemp1 = new ServiceBusiness(plugin, PM.getCuboid(), player, country1.getName(), country1.getTowns().indexOf(town), BusinessName1);
						PDI.addServiceBusinessOwned(businesstemp1);
						PDI.getTownResides().addServiceBusiness(businesstemp1);
						break;
					case HOUSE:
						String HouseName = "";
						int test1 = 0;
						boolean works = true;
						for (Town towntest: PDI.getCountryResides().getTowns()) {
							test1+=towntest.getHouses().size();
						}
						while(HouseName.isEmpty()) {
							works = true;
							for(House housetest : town.getHouses()) {
								if(housetest.getName().equalsIgnoreCase("House " + test1)) {
									works = false;
									break;
								}
							}
							if (!works) {
								test1 +=1;
							}
							else {
								HouseName = "House " + test1;
							}
						}
						
						House housetemp = new House(plugin, PM.getCuboid(), player, PDI.getCountryResides().getName(), PDI.getCountryResides().getTowns().indexOf(town), HouseName);
						PDI.addHouseOwned(housetemp);
						PDI.getTownResides().addHouse(housetemp);
						break;
					case PARK:
						if(!PM.isReSelectLocalPark()) {
							String ParkName = "";
	
							int test = town.getParks().size();
							while(ParkName.isEmpty()) {
								boolean works3 = true;
								for(Park park: town.getParks()) {
									if(park.getName().equalsIgnoreCase("Park " + test)) {
										works3 = false;
										break;
									}
								}
								if(!works3) {
									test += 1;
								}
								else {
									ParkName = "Park " + test;
								}
							}
							Park park = new Park(plugin, PM.getCuboid(), town.getCountry(), plugin.countrydata.get(town.getCountry()).getTowns().indexOf(town),false,(ParkName));
							town.addPark(park);
							PDI.getConversation().getContext().setSessionData("localpark", park);
						}

						break;
					case FEDERALPARK:
						if(!PM.isReSelectFederalPark()) {
							Country country11 = PDI.getCountryResides();
							String ParkName1 = "";
							int test11 = country11.getParks().size();
							boolean works31 = true;
							while(ParkName1.isEmpty()) {
								works31 = true;
								for(Park parktest: country11.getParks()) {
									if(parktest.getName().equalsIgnoreCase("Park " + test11)) {
										works31 = false;
										break;
									}
								}
								if(!works31) {
									test11 += 1;
								}
								else {
									ParkName1 = "Park " + test11;
								}
							}
							Park park = new Park(plugin, PM.getCuboid(), country11.getName(), -1, true, ParkName1);
							country11.addPark(park);
							PDI.getConversation().getContext().setSessionData("federalpark", park);
						}
						break;
					case REGOOD:
						break;
					case RESERVICE:
						break;
					case REHOUSE:
						break;
				case COUNTRY:
					break;
				case TOWN:
					break;
				default:
					break;
				}
			}
			return true;
	}
	
	
	
	// A method to determine if a polygon is simple
	public boolean isSimple(Polygon poly) {
		Vector<Line2D> lines = new Vector<Line2D>();
		Line2D line;
		Line2D line2;
		for (int i = 0; i < poly.npoints; i++) {
			if (!((i+1)<poly.npoints)) {
				line = new Line2D.Double(new Point(poly.xpoints[i], poly.ypoints[i]), new Point(poly.xpoints[0], poly.ypoints[0]));
			}
			else {
				line = new Line2D.Double(new Point(poly.xpoints[i], poly.ypoints[i]), new Point(poly.xpoints[i+1], poly.ypoints[i+1]));
			}
			lines.add(line);
		}
		for (int i = 0; i < lines.size(); i++) {
			line = (Line2D) lines.get(i).clone();
			for (int j = 0; j < lines.size(); j++) {
				line2 = (Line2D) lines.get(j).clone();
				if (i != j && i != (j+1) && i != (j-1) && !(i == 0 && j == lines.size() - 1) && !(i == lines.size() - 1 && j == 0) && line.intersectsLine(line2)) {
					return false;
				}
			}
		}
		return true;
	}
	
	// A method that rights the region selection instructions menu
	public String writeRegionSelection2(String regionName, int error, PlayerModes PM){
		String space = this.space();
		String main = this.header("Claim "+regionName + ". Read the instructions.");
		String options = "";
		String end = this.footer(false);
		String errmsg = this.errormsg(error);
		ItemStack item = new ItemStack(plugin.getConfig().getInt("selection_tool"));
		PlayerMethods PMeth = new PlayerMethods(plugin, plugin.getServer().getPlayer(PM.getPlayername()));
		PlayerData PDI = plugin.playerdata.get(PM.getPlayername());
		if(PM.isSelectingCuboid()) {
			options = options.concat(menuType.LABEL + "Size: " + menuType.VALUE + PM.getCuboid().Volume() + menuType.UNIT + " Blocks\n");
			if(PM.houseSelect()) {
				options = options.concat("Cost Per Tax Cycle At Level 1 Protection: " + menuType.VALUE + 
						cut(PMeth.houseTax(PM.getCuboid(), PDI.getTownResides(), 1, true, version.NEW)) + menuType.UNIT + " " + PDI.getPluralMoney() + "\n");
				options = this.addDivider(options);
			}
			options = options.concat(this.drawTownMap(plugin.getServer().getPlayer(PM.getPlayername()), mapSize.SMALL));
			options = this.addDivider(options);
			options = this.addLine(options, "Use a " + this.itemName(item) + " to select the two opposite corners of your cuboid. Left click for one corner, " +
					"and right click for the other. Type 'Finish' when you are done or 'Cancel' to abandon.",optionType.INSTRUCTION);
		}
		else if (PM.isSelectingPolygon()) {
			options = options.concat(menuType.LABEL + "Size: " + menuType.VALUE + PM.getPolygon().Volume() + menuType.UNIT + " Blocks\n");
			if (PM.houseSelect()) {
				options = options.concat("Cost Per Tax Cycle At Level 1 Protection: " + menuType.VALUE + 
						cut(PMeth.houseTax(PM.getCuboid(), PDI.getTownResides(), 1, true, version.NEW)) + menuType.UNIT + " " + PDI.getPluralMoney() + "\n");
				options = this.addDivider(options);
			}

			options = options.concat(this.drawTownMap(plugin.getServer().getPlayer(PM.getPlayername()), mapSize.SMALL));
			options = this.addDivider(options);
			options = this.addLine(options, "Use a " + this.itemName(item) + " to select the corners of your " + regionName + ". The lowest selection will be" +
					" the bottom, and the hightest selection will be the top. Type 'Finish' when you are done or 'Cancel' to abandon.", optionType.INSTRUCTION);

		}
		
		return space + main + options + end + errmsg;
				
	}
	
	// A method that draws the map for any player
	public String drawCountryMap(Player player, mapSize size){
		PlayerData PDI = plugin.playerdata.get(player.getName());
		int above = 0;
		int below = 0;
		HashMap<String, ChatColor> countrycolors = new HashMap<String,ChatColor>();
		ChatColor[] CColors = {ChatColor.YELLOW, ChatColor.GOLD, ChatColor.RED, ChatColor.DARK_RED, ChatColor.DARK_GREEN, ChatColor.WHITE,
				ChatColor.AQUA, ChatColor.DARK_BLUE, ChatColor.DARK_AQUA, ChatColor.DARK_PURPLE, ChatColor.LIGHT_PURPLE};

		Location spot = player.getLocation();
		ChunkData chunk = new ChunkData(new Point(spot.getChunk().getX(), spot.getChunk().getZ()), spot.getWorld().getName());
		String map = "";
		
		switch(size) {
		case SMALL:
			below = 3;
			above = 2;
			break;
		case MEDIUM:
			below = 5;
			above = 4;
			break;
		case LARGE:
			below = 4;
			above = 9;
			break;
		}
		int[] remove = {160*above+240,160*above+77,160*above-80,160*above+83};
		
		int n = 0;// for iterating through color choices
		if(PDI.getIsCountryResident()) {
			countrycolors.put(PDI.getCountryResides().getName(), ChatColor.GREEN);
		}
		
		for (int z = -above; z < below; z++) {
			for (int x = -26; x < 27; x++) {
				ChunkData test = new ChunkData(new Point(chunk.point.x + x, chunk.point.y + z), chunk.world);
				if (z == 0 && x == 0) {
					if (plugin.chunks.containsKey(chunk)) {
						if(!countrycolors.containsKey(plugin.chunks.get(chunk))) {
							countrycolors.put(plugin.chunks.get(chunk), CColors[n]);
							n++;
							if(n == 11) n = 0;
						}
						
						map = map.concat(countrycolors.get(plugin.chunks.get(chunk)) + "@");

					}
					else {
						map = map.concat(ChatColor.GRAY + "@");
					}
				}
				if (z != 0 || x != 0) {
					if (plugin.chunks.containsKey(test)) {
						if(!countrycolors.containsKey(plugin.chunks.get(test))) {
							countrycolors.put(plugin.chunks.get(test), CColors[n]);
							n++;
							if(n == 11){
								n = 0;
							}
						}
						map = map.concat(countrycolors.get(plugin.chunks.get(test)) + "+");

					}
					else {
						map = map.concat(ChatColor.GRAY + "/");
					}
				}
			}
			map = map.concat("\n");
		}
		
		// Direction Icon
		if ((-45 < spot.getYaw() && 45 >= spot.getYaw()) || (315 < spot.getYaw() && 360 >= spot.getYaw())
				|| (-360 < spot.getYaw() && -315 >= spot.getYaw())) {
			map = map.substring(0, remove[0]).concat("`|`").concat(map.substring(remove[0] + 1));
		}
		if ((45 < spot.getYaw() && 135 >= spot.getYaw()) || (-315 < spot.getYaw() && -225 >= spot.getYaw())) {
			map = map.substring(0, remove[1]).concat("-").concat(map.substring(remove[1] + 1));
		} 
		if ((135 < spot.getYaw() && 225 >= spot.getYaw()) || (-225 < spot.getYaw() && -135 >= spot.getYaw())) {
			map = map.substring(0, remove[2]).concat(",|,").concat(map.substring(remove[2] + 1));

		}
		if ((225 < spot.getYaw() && 315 >= spot.getYaw()) || (-135 < spot.getYaw() && -45 >= spot.getYaw())) {
			map = map.substring(0, remove[3]).concat("-").concat(map.substring(remove[3]+1));
		}
		
		for(String key:countrycolors.keySet()) {
			map = map.concat(countrycolors.get(key) + "+ = " + key + ", ");
		}
		map = map.concat(ChatColor.GRAY + "/ = Unclaimed Land, @ = You.\n");
		
		return map;
		
	}
	
	// A method to draw the map with towns included
	public String drawTownMap(Player player, mapSize size) {
	
		PlayerData PDI = plugin.playerdata.get(player.getName());
		int below = 0;
		int above = 0;
		HashMap<String, ChatColor> countrycolors = new HashMap<String,ChatColor>();
		HashMap<String, ChatColor> towncolors = new HashMap<String, ChatColor>();
		Location spot = player.getLocation();
		ChunkData chunk = new ChunkData(new Point(spot.getChunk().getX(), spot.getChunk().getZ()), spot.getWorld().getName());
		String map = "";
		switch(size) {
		case SMALL:
			below = 3;
			above = 2;
			break;
		case MEDIUM:
			below = 5;
			above = 4;
			break;
		case LARGE:
			below = 4;
			above = 9;
			break;
		}
		int[] remove = {160*above+240,160*above+77,160*above-80,160*above+83};
		
		int n = 0;// for iterating through country color choices
		ChatColor[] CColors = {ChatColor.YELLOW, ChatColor.GOLD, ChatColor.RED, ChatColor.DARK_RED, ChatColor.DARK_GREEN, ChatColor.WHITE};
		int m = 0;// for iterating through town color choices
		ChatColor[] TColors = {ChatColor.AQUA, ChatColor.DARK_BLUE, ChatColor.DARK_AQUA, ChatColor.DARK_PURPLE, ChatColor.LIGHT_PURPLE};
		if(PDI.getIsCountryResident()) {
			countrycolors.put(PDI.getCountryResides().getName(), ChatColor.GREEN);
			if(PDI.getIsTownResident()) {
				towncolors.put(PDI.getTownResides().getName(), ChatColor.BLUE);
			}
		}
		if(PDI.getIsInCountry()) {
			for(Town town:PDI.getCountryIn().getTowns()) {
				if (!towncolors.containsKey(town.getName())) {
					towncolors.put(town.getName(), TColors[m]);
					m++;
					if(m == 5){
						m = 0;
					}
				}
			}	
		}
		
		
		for (int z = -above; z < below; z++) {
			for (int x = -26; x < 27; x++) {
				ChunkData test = new ChunkData(new Point(chunk.point.x + x, chunk.point.y + z), spot.getWorld().getName());
				if (z == 0 && x == 0) {
					
					// If the chunk data has a country, but the country isn't in the color map yet, add it
					if (plugin.chunks.containsKey(chunk)) {

						if(!countrycolors.containsKey(plugin.chunks.get(chunk))) {
							countrycolors.put(plugin.chunks.get(chunk), CColors[n]);
							n++;
							if(n == 6) {
								n = 0;
							}

						}
						 
							// Check if the country of this particular chunk is the one that player is in.
							try {
								if (plugin.countrydata.get(plugin.chunks.get(chunk)).equals(PDI.getCountryIn())) {
									if(plugin.countrydata.get(plugin.chunks.get(chunk)).townClaimed(chunk)){
										map = map.concat(towncolors.get(plugin.countrydata.get(plugin.chunks.get(chunk)).TownOfChunk(chunk).getName()) + "@");
									}
									else {
										map = map.concat(countrycolors.get(plugin.chunks.get(chunk)) + "@");
									}
								}
								else {
									map = map.concat(countrycolors.get(plugin.chunks.get(chunk)) + "@");
								}
							}
							catch(Exception ex) {
								map = map.concat(countrycolors.get(plugin.chunks.get(chunk)) + "@");
							}

					}
					else {
						map = map.concat(ChatColor.GRAY + "@");
					}
				}
				
				
				if (z != 0 || x != 0) {
					// If the chunk data has a country, but the country isn't in the color map yet, add it
					if (plugin.chunks.containsKey(test)) {
						if(!countrycolors.containsKey(plugin.chunks.get(test))) {
							countrycolors.put(plugin.chunks.get(test), CColors[n]);
							n += 1;
							if (n == 6) {
								n = 0;
							}
						}
						try {
							if (plugin.countrydata.get(plugin.chunks.get(test)).equals(PDI.getCountryIn())) {
								if(plugin.countrydata.get(plugin.chunks.get(test)).townClaimed(test)){
									map = map.concat(towncolors.get(plugin.countrydata.get(plugin.chunks.get(test)).TownOfChunk(test).getName()) + "+");
								}
								else {
									map = map.concat(countrycolors.get(plugin.chunks.get(test)) + "+");
								}
							}
							else {
								map = map.concat(countrycolors.get(plugin.chunks.get(test)) + "+");
							}
						}
						catch(Exception ex) {
							map = map.concat(countrycolors.get(plugin.chunks.get(test)) + "+");
						}
					}
					else {
						map = map.concat(ChatColor.GRAY + "/");
					}
				}
			}
			map = map.concat("\n");
		}
		
		// Direction Icon
		if ((-45 < spot.getYaw() && 45 >= spot.getYaw()) || (315 < spot.getYaw() && 360 >= spot.getYaw())
				|| (-360 < spot.getYaw() && -315 >= spot.getYaw())) {
			map = map.substring(0, remove[0]).concat("`|`").concat(map.substring(remove[0] + 1));
		}
		if ((45 < spot.getYaw() && 135 >= spot.getYaw()) || (-315 < spot.getYaw() && -225 >= spot.getYaw())) {
			map = map.substring(0, remove[1]).concat("-").concat(map.substring(remove[1] + 1));
		} 
		if ((135 < spot.getYaw() && 225 >= spot.getYaw()) || (-225 < spot.getYaw() && -135 >= spot.getYaw())) {
			map = map.substring(0, remove[2]).concat(",|,").concat(map.substring(remove[2] + 1));

		}
		if ((225 < spot.getYaw() && 315 >= spot.getYaw()) || (-135 < spot.getYaw() && -45 >= spot.getYaw())) {
			map = map.substring(0, remove[3]).concat("-").concat(map.substring(remove[3] + 1));
		}
		
		for(String key:countrycolors.keySet()) {
			map = map.concat(countrycolors.get(key) + "+ = " + key + ", ");
		}
		for(String key:towncolors.keySet()) {
			map = map.concat(towncolors.get(key) + "+ = " + key + " (town), ");
		}
		map = map.concat(ChatColor.GRAY + "/ = Unclaimed Land, @ = You.\n");
		
		return map;
		
	}
	
	// A method to convert chunks to their respective point
	public Point ChunktoPoint(Chunk chunk) {
		return new Point(chunk.getX(), chunk.getZ());
	}
	
	// A method to simply repeat a string
	public String repeat(String entry, int multiple) {
		String temp = "";
		for (int i = 0; i < multiple; i++) {
			temp = temp.concat(entry);
		}
		return temp;
	}
	
	// A method to make a sub array of an array
	public String[] subArray(String[] input, int begin, int end) {
		String[] output = new String[end-begin + 1];
		int n = 0;
		for (int iter=begin; iter <= end; iter += 1) {
			output[n] = input[iter];
			n +=1;
		}
		return output;
	}
	
	// A method to cut off decimals greater than the hundredth place;
	public double cut(double x) {
		int y;
		y = (int) (x*100);
		return y/100.0;
	}
	
	// A method to cut off decimals greater than the hundredth place;
	public BigDecimal cut(BigDecimal x) {
		return x.divide(new BigDecimal(1), 2, BigDecimal.ROUND_HALF_DOWN);
	}
}
