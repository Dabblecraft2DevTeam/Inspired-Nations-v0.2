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
import org.bukkit.OfflinePlayer;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;


import com.github.InspiredOne.InspiredNations.Regions.ChunkData;
import com.github.InspiredOne.InspiredNations.Regions.Country;
import com.github.InspiredOne.InspiredNations.Regions.GoodBusiness;
import com.github.InspiredOne.InspiredNations.Regions.House;
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
		errors.add("\nInsuficient Funds.");//25
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


	}
	
	// The enum for option types
	public enum optionType {
		OPTION, UNAVAILABLE, INSTRUCTION, ALERT
	}
	public enum mapSize{
		SMALL,MEDIUM,LARGE
	}
	public enum region {
		PRISON,BANK,HOUSE,GOODBUSINESS,SERVICEBUSINESS,PARK,FEDERALPARK,TOWN,COUNTRY
	}
	
	// A method that builds new lines on a string with the standard formatting
	public String addLine(String text, String addition, optionType type) {
		optionType Type = type;
		
		switch(Type) {
			case OPTION:
				text = text.concat(ChatColor.GREEN + addition + "\n");
				break;
			case INSTRUCTION:
				text = text.concat(ChatColor.YELLOW + addition + "\n");
				break;
			case ALERT:
				text = text.concat(ChatColor.RED + addition + "\n");
				break;
			case UNAVAILABLE:
				text = text.concat(ChatColor.DARK_GRAY + addition + "\n");
				break;
		}
		return text;
	}
	
	// A method to add the divider
	public String addDivider(String text) {
		return text.concat(ChatColor.DARK_AQUA + repeat("-", 53) + "\n");
	}
	
	// A method that builds the hud header
	public String header(String msg) {
		return addDivider(ChatColor.GOLD + "" + ChatColor.BOLD + msg + "\n" + ChatColor.RESET);
	}
	
	// A method that builds the pre-hud space
	public String space() {
		return repeat("\n", plugin.getConfig().getInt("hud_pre_message_space"));
	}
	
	// A method to build the hud footer.
	public String footer(boolean isMainHud) {
		if (isMainHud) {
			return addDivider("") + ChatColor.AQUA + "Type 'exit' to leave or 'say' to chat.";
		}
		else {
			return addDivider("") + ChatColor.AQUA + "Type 'exit' to leave, 'say' to chat, or 'back' to go back.";
		}
	}
	
	// A method to build the hud error message
	public String errormsg(int error) {
		return ChatColor.RED + this.errors.get(error);
	}
	
	// A method to build Options
	public String options(Vector<String> inputs) {
		String output = ChatColor.GREEN + "";
		int n = 1;
		for (String option:inputs) {
			if (option.startsWith("*")) {
				output = addLine(output, "[" + ChatColor.GRAY + n + ChatColor.DARK_GRAY +"] " + option.substring(1), optionType.UNAVAILABLE);
			}
			else {
				output = addLine(output, "["+ ChatColor.BOLD + "" + ChatColor.GOLD + n + ChatColor.RESET + ChatColor.GREEN +"] " + option, optionType.OPTION);
			}
			n += 1;
		}
		return output;
	}
	
	// A Method that builds the Protection Level Text
	public String protLevels(String regionName, int error, int current, BigDecimal cost, region Region, Vector<String> inputs) {
		String space = this.space();
		String main = this.header(regionName + " Protection Levels. Type an option number.");
		String options = "";
		String end = this.footer(false);
		String errmsg = this.errormsg(error);
		
		options = options.concat(ChatColor.YELLOW + "Current Protection Level: " + ChatColor.GOLD + current + ChatColor.YELLOW + "\n");
		options = options.concat(ChatColor.YELLOW + "Current Protection Cost: " + ChatColor.GOLD + cost + "\n");
		options = this.addDivider(options);
		
		switch(Region) {
		case GOODBUSINESS:
			options = options.concat(ChatColor.GOLD + "Level 0: " + ChatColor.YELLOW + "(No protection) Anybody that can build and interact in your town can build and interact in your business.\n");
			options = options.concat(ChatColor.GOLD + "Level 1: " + ChatColor.YELLOW + "(Block protection) Only country rulers, town rulers, employees, and business owners can build in the business.\n");
			options = options.concat(ChatColor.GOLD + "Level 2: " + ChatColor.YELLOW + "(Interact protection) Only country rulers, town rulers, employees, and business owners can interact in the business.\n");
			options = options.concat(ChatColor.GOLD + "Level 3: " + ChatColor.YELLOW + "(Player protection) Employees and owners gain protection from attacks within the business.\n");
			break;
		case SERVICEBUSINESS:
			options = options.concat(ChatColor.GOLD + "Level 0: " + ChatColor.YELLOW + "(No protection) Anybody that can build and interact in your town can build and interact in your business.\n");
			options = options.concat(ChatColor.GOLD + "Level 1: " + ChatColor.YELLOW + "(Block protection) Only country rulers, town rulers, employees, and business owners can build in the business.\n");
			options = options.concat(ChatColor.GOLD + "Level 2: " + ChatColor.YELLOW + "(Interact protection) Only country rulers, town rulers, employees, and business owners can interact in the business.\n");
			options = options.concat(ChatColor.GOLD + "Level 3: " + ChatColor.YELLOW + "(Player protection) Employees and owners gain protection from attacks within the business.\n");
			break;
		case BANK:
			break;
		case FEDERALPARK:
			options = options.concat(ChatColor.GOLD + "Level 0: " + ChatColor.YELLOW + "(No protection) Anybody that can build in your country can build in your park.\n");
			options = options.concat(ChatColor.GOLD + "Level 1: " + ChatColor.YELLOW + "(Complete protection) Only country rulers and designated builders can build in the park.\n");
			break;
		case HOUSE:
			options = options.concat(ChatColor.GOLD + "Level 0: " + ChatColor.YELLOW + "(No protection) Anybody that can build and interact in your town can build and interact in your house.\n");
			options = options.concat(ChatColor.GOLD + "Level 1: " + ChatColor.YELLOW + "(Block protection) Only country rulers, town rulers, designated builders, and house owners can build in the house.\n");
			options = options.concat(ChatColor.GOLD + "Level 2: " + ChatColor.YELLOW + "(Interact protection) Only country rulers, town rulers, designated builders, and house owners can interact in the house.\n");
			options = options.concat(ChatColor.GOLD + "Level 3: " + ChatColor.YELLOW + "(Player protection) You gain damage protection from non-house residents.\n");
			break;
		case PARK:
			options = options.concat(ChatColor.GOLD + "Level 0: " + ChatColor.YELLOW + "(No protection) Anybody that can build in your town can build in your park.\n");
			options = options.concat(ChatColor.GOLD + "Level 1: " + ChatColor.YELLOW + "(Complete protection) Only country rulers, town rulers and designated builders can build in the park.\n");
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
	public Vector<String> findTown(String country, String town) {
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
			for(String entry:temp) {
				temp.set(n, entry + "/" + countryname);
				n +=1;
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
			}
		}
		return results;
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
		options = options.concat(ChatColor.YELLOW + "What kind of selection would you like to make?\n\n" + "A " + ChatColor.GOLD + "cuboid" + ChatColor.YELLOW + " is a perfect rectangular prism requiring you" +
				" to select two opposite corners of the 'box'.\n" + "A " + ChatColor.GOLD + "polygon" + ChatColor.YELLOW + " is a shape with as many corners as you want. The highest and lowest corners" +
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
				player.sendRawMessage(ChatColor.YELLOW + this.space() + "Determining if selection is valid.\n");
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
				player.sendRawMessage(ChatColor.YELLOW + this.space() + "Determining if selection is valid.\n");
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
		Town town = PDI.getTownMayored();
		
		// Finish
			player.sendRawMessage(ChatColor.YELLOW + "Please wait while the server determines if this is a valid selection.");
			if (PM.isSelectingPolygon()) {
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
					
					// Makes the Progress Bar
					int done = (int) Math.ceil(((i - rect.getMinX() + 1)/(rect.getMaxX() - rect.getMinX() + 2)) * 30);
					player.sendRawMessage(ChatColor.YELLOW + this.space() + "Determining if selection is valid.\n");
					player.sendRawMessage(ChatColor.GRAY + " [" + ChatColor.GREEN + repeat("#",done) +
							ChatColor.GRAY + repeat("#", (int) (30 - done)) + "]");
					
					// Iterates Through
					for (int j = (int) rect.getMinY() - 1; j <= (int)rect.getMaxY() + 1; j++) {
						for (int l = PM.getPolygon().getYMin() - 1; l <= PM.getPolygon().getYMax() + 1; l++) {
							Location test = new Location(player.getWorld(), i, l, j);
							if ((!town.isIn(test)) && PM.getPolygon().isIn(test)) {
								context.setSessionData("error",29);
								return false;
							}
							for (Iterator<House> k = town.getHouses().iterator(); k.hasNext();) {
								House housetemp = k.next();
								if (housetemp.isIn(test) && PM.getPolygon().isIn(test)){
									context.setSessionData("error",30);
									return false;
								}
							}
							for (Iterator<GoodBusiness> k = town.getGoodBusinesses().iterator(); k.hasNext();) {
								GoodBusiness businesstemp = k.next();
								if (businesstemp.isIn(test) && PM.getPolygon().isIn(test)){
									context.setSessionData("error",31);
									return false;
								}
							}
							for (Iterator<ServiceBusiness> k = town.getServiceBusinesses().iterator(); k.hasNext();) {
								ServiceBusiness businesstemp = k.next();
								if (businesstemp.isIn(test) && PM.getPolygon().isIn(test)){
									context.setSessionData("error",32);
									return false;
								}
							}
							for (Iterator<Park> k = town.getParks().iterator(); k.hasNext();) {
								Park parktemp = k.next();
								if (parktemp.isIn(test) && PM.getPolygon().isIn(test)){
									context.setSessionData("error",33);
									return false;
								}
							}
							if (town.hasBank()) {
								if (town.getBank().isIn(test) && PM.getPolygon().isIn(test) && !PM.localBankSelect()) {
									context.setSessionData("error",34);
									return false;
								}
							}
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
				switch(type) {
					case PRISON:
						town.setPrison(new LocalPrison(plugin, PM.getPolygon(), town.getCountry(), plugin.countrydata.get(town.getCountry()).getTowns().indexOf(town)));
						break;
					case BANK:
						town.setBank(new LocalBank(plugin, PM.getPolygon(), town.getCountry(), plugin.countrydata.get(town.getCountry()).getTowns().indexOf(town)));
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
							for(Town towntest:country.getTowns()) {
								if(towntest.getGoodBusinesses().contains("Good Business " + test2)) {
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
						String BusinessName1 = "";
						int test21 = 0;
						boolean works21 = true;
						Country country1 = PDI.getCountryResides();
						for (Town towntest: country1.getTowns()) {
							test21+=towntest.getServiceBusinesses().size();
						}
						while(BusinessName1.isEmpty()) {
							works21 = true;
							for(Town towntest:country1.getTowns()) {
								if(towntest.getServiceBusinesses().contains("Service Business " + test21)) {
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
						
						ServiceBusiness businesstemp1 = new ServiceBusiness(plugin, PM.getPolygon(), player, country1.getName(), country1.getTowns().indexOf(town), BusinessName1);
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
							for(Town towntest:PDI.getCountryResides().getTowns()) {
								if(towntest.getHouses().contains("House " + test1)) {
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
						
						House housetemp = new House(plugin, PM.getPolygon(), player, PDI.getCountryResides().getName(), PDI.getCountryResides().getTowns().indexOf(town), HouseName);
						PDI.addHouseOwned(housetemp);
						PDI.getTownResides().addHouse(housetemp);
						break;
					case PARK:
						String ParkName = "";
						int test = town.getParks().size();
						while(ParkName.isEmpty()) {
							if(town.getParks().contains("Park " + test)) {
								test +=1;
							}
							else {
								ParkName = "Park " + test;
							}
						}
						town.addPark(new Park(plugin, PM.getPolygon(), town.getCountry(), plugin.countrydata.get(town.getCountry()).getTowns().indexOf(town),false,(ParkName)));
						break;
					case FEDERALPARK:
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
					player.sendRawMessage(ChatColor.YELLOW + this.space() + "Determining if selection is valid.\n");
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
						town.setPrison(new LocalPrison(plugin, PM.getCuboid(), town.getCountry(), plugin.countrydata.get(town.getCountry()).getTowns().indexOf(town)));
						break;
					case BANK:
						town.setBank(new LocalBank(plugin, PM.getCuboid(), town.getCountry(), plugin.countrydata.get(town.getCountry()).getTowns().indexOf(town)));
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
							for(Town towntest:country.getTowns()) {
								if(towntest.getGoodBusinesses().contains("Good Business " + test2)) {
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
							for(Town towntest:country1.getTowns()) {
								if(towntest.getServiceBusinesses().contains("Service Business " + test21)) {
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
						
						ServiceBusiness businesstemp1 = new ServiceBusiness(plugin, PM.getPolygon(), player, country1.getName(), country1.getTowns().indexOf(town), BusinessName1);
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
							for(Town towntest:PDI.getCountryResides().getTowns()) {
								if(towntest.getHouses().contains("House " + test1)) {
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
						
						House housetemp = new House(plugin, PM.getPolygon(), player, PDI.getCountryResides().getName(), PDI.getCountryResides().getTowns().indexOf(town), HouseName);
						PDI.addHouseOwned(housetemp);
						PDI.getTownResides().addHouse(housetemp);
						break;
					case PARK:
						String ParkName = "";
						int test = town.getParks().size();
						while(ParkName.isEmpty()) {
							if(town.getParks().contains("Park " + test)) {
								test +=1;
							}
							else {
								ParkName = "Park " + test;
							}
						}
						town.addPark(new Park(plugin, PM.getCuboid(), town.getCountry(), plugin.countrydata.get(town.getCountry()).getTowns().indexOf(town),false,(ParkName)));
						break;
					case FEDERALPARK:
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
			options = options.concat(ChatColor.YELLOW + "Size: " + ChatColor.GOLD + PM.getCuboid().Volume() + ChatColor.YELLOW + " Blocks\n");
			if(PM.houseSelect()) {
				options = options.concat("Cost Per Tax Cycle At Level 1 Protection: " + ChatColor.GOLD + 
						cut(PMeth.houseTax(PM.getCuboid(), PDI.getTownResides(), 1)) + ChatColor.YELLOW + " " + PDI.getPluralMoney() + "\n");
				options = this.addDivider(options);
			}
			options = options.concat(this.drawTownMap(plugin.getServer().getPlayer(PM.getPlayername()), mapSize.SMALL));
			options = this.addDivider(options);
			options = this.addLine(options, "Use a " + this.itemName(item) + " to select the two opposite corners of your cuboid. Left click for one corner, " +
					"and right click for the other. Type 'Finish' when you are done or 'Cancel' to abandon.",optionType.INSTRUCTION);
		}
		else if (PM.isSelectingPolygon()) {
			options = options.concat(ChatColor.YELLOW + "Size: " + ChatColor.GOLD + PM.getPolygon().Volume() + ChatColor.YELLOW + " Blocks\n");
			if (PM.houseSelect()) {
				options = options.concat("Cost Per Tax Cycle At Level 1 Protection: " + ChatColor.GOLD + 
						cut(PMeth.houseTax(PM.getCuboid(), PDI.getTownResides(), 1)) + ChatColor.YELLOW + " " + PDI.getPluralMoney() + "\n");
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
