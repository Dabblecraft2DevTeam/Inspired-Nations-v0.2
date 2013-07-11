package com.github.InspiredOne.InspiredNations.HUD.ManageCountry;

import java.util.Vector;

import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.HUD.Menu;
import com.github.InspiredOne.InspiredNations.Regions.Park;
import com.github.InspiredOne.InspiredNations.Tools.menuType;

public class ManageFederalPark2 extends Menu {

	// Constructor
	public ManageFederalPark2(InspiredNations instance, Player playertemp, int errortemp) {
		super(instance, playertemp, errortemp);
		country = PDI.getCountryRuled();
		park = (Park) PDI.getConversation().getContext().getSessionData("federalpark");
	}
	// Constructor
	public ManageFederalPark2(InspiredNations instance, Player playertemp, int errortemp, Vector<String> names) {
		super(instance, playertemp, errortemp, names);
		country = PDI.getCountryRuled();
		park = (Park) PDI.getConversation().getContext().getSessionData("federalpark");
	}
	
	
	@Override
	public String getPromptText(ConversationContext arg0) {
		String space = tools.space();
		String main = tools.header("Manage Park. Type an option number.");
		String options = "";
		String end = tools.footer(false);
		String errmsg = ChatColor.RED + tools.errors.get(error);
		
		// Make inputs Vector
		inputs.add("Add Builder <player> " + ChatColor.GRAY + "Adds person that can interact");
		if(park.getBuilders().size() > 0) {
			inputs.add("Remove Builder <player>");
		}
		inputs.add("Protection Level (" + menuType.OPTIONDESCRIP + park.getProtectionLevel() + menuType.OPTION + ")");
		inputs.add("Reclaim Park");
		inputs.add("Rename <name>");
		
		// Make options text
		options = options.concat(tools.options(inputs));
		
		return space + main + options + end + errmsg;
	}

	
	@Override
	public Prompt acceptInput(ConversationContext arg0, String arg) {
		int answer = 0;
		if (arg.startsWith("/")) {
			arg = arg.substring(1);
		}
		if (arg.equalsIgnoreCase("back")) {
			if(country.getParks().size() < 2) {
				return new CountryGovernmentRegions(plugin, player, 0);
			}
			else {
				return new ManageFederalPark1(plugin, player, 0);
			}
		}
		String[] args = arg.split(" ");
		
		try {
			answer = Integer.decode(args[0])-1;
		}
		catch (Exception ex) {
			return new ManageFederalPark2(plugin, player,1);
		}
		
		if (answer > inputs.size()-1) {
			return new ManageFederalPark2(plugin, player, 2);
		}
		
		// Reclaim Park
		if(inputs.get(answer).equals("Reclaim Park")) {
			PM.setReSelectFederalPark(true);
			PM.federalPark(true);
			return new SelectFederalPark1(plugin, player, 0);
		}
		// Add Builder <player>
				if (inputs.get(answer).equals("Add Builder <player> " + ChatColor.GRAY + "Adds person that can interact")) {
					if (args.length !=2) {
						return new ManageFederalPark2(plugin, player, 3);
					}
					else {
						Vector<String> names = tools.findPersonExcept(args[1], player.getName());
						if(names.size() == 0) {
							return new ManageFederalPark2(plugin, player, 5);
						}
						else if(names.size() > 1) {
							return new ManageFederalPark2(plugin ,player,  4, names);
						}
						else {
							if (park.getBuilders().contains(names.get(0))) {
								return new ManageFederalPark2(plugin, player, 42);
							}
							else {
								park.addBuilder(names.get(0));
							}
							return new ManageFederalPark2(plugin, player,  0);
						}
					}
				}
				
				//Remove Builder <player>
				if (inputs.get(answer).equals("Remove Builder <player>")) {
					if (args.length !=2) {
						return new ManageFederalPark2(plugin, player,  3);
					}
					else {
						Vector<String> names;
						names = tools.find(args[1], park.getBuilders());
						
						if(names.size() == 0) {
							return new ManageFederalPark2(plugin, player, 43);
						}
						else if(names.size() > 1) {
							return new ManageFederalPark2(plugin ,player, 4, names);
						}
						else {
							busi.removeBuilder(names.get(0));
							return new ManageFederalPark2(plugin, player, 0);
						}
						
					}
				}
				// Rename <name>
				if (inputs.get(answer).equals("Rename <name>")) {
					if(args.length < 2) {
						return new ManageFederalPark2(plugin, player, 3);
					}
					else {
						String FederalParkName = tools.formatSpace(tools.subArray(args, 1, args.length - 1));
						if(FederalParkName.contains("/")) {
							return new ManageFederalPark2(plugin, player, 70);
						}
						boolean works = true;
						for(Park test:country.getParks()) {
							if(test.getName().equalsIgnoreCase(FederalParkName) && !test.equals(park)) {
								works = false;
							}
						}
						if (works) {
							park.setName(FederalParkName);
							return new ManageFederalPark2(plugin, player, 0);
						}
						else {
							return new ManageFederalPark2(plugin, player, 68);
						}
					}
				}
				
				// Protection Level
				if(inputs.get(answer).equals("Protection Level (" + menuType.OPTIONDESCRIP + park.getProtectionLevel() + menuType.OPTION + ")")) {
					return new FederalParkProtectionLevels(plugin, player, 0);
				}
		return new ManageFederalPark2(plugin, player, 0);
	}


}
