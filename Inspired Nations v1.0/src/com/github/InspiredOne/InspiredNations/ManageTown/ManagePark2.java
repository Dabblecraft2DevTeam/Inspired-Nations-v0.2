package com.github.InspiredOne.InspiredNations.ManageTown;

import java.util.Vector;

import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.HUD.Menu;
import com.github.InspiredOne.InspiredNations.Regions.Park;
import com.github.InspiredOne.InspiredNations.Tools.menuType;

public class ManagePark2 extends Menu {

	// Constructor
	public ManagePark2(InspiredNations instance, Player playertemp, int errortemp) {
		super(instance, playertemp, errortemp);
		town = PDI.getTownMayored();
		park = (Park) PDI.getConversation().getContext().getSessionData("localpark");
	}
	// Constructor
	public ManagePark2(InspiredNations instance, Player playertemp, int errortemp, Vector<String> names) {
		super(instance, playertemp, errortemp, names);
		town = PDI.getTownMayored();
		park = (Park) PDI.getConversation().getContext().getSessionData("localpark");
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
			if(town.getParks().size() < 2) {
				return new TownGovernmentRegions(plugin, player, 0);
			}
			else {
				return new ManagePark1(plugin, player, 0);
			}
		}
		String[] args = arg.split(" ");
		
		try {
			answer = Integer.decode(args[0])-1;
		}
		catch (Exception ex) {
			return new ManagePark2(plugin, player,1);
		}
		
		if (answer > inputs.size()-1) {
			return new ManagePark2(plugin, player, 2);
		}
		
		if(inputs.get(answer).equals("Reclaim Park")) {
			PM.park(true);
			PM.setReSelectLocalPark(true);
			return new SelectPark1(plugin, player, 0);
		}
		
		// Add Builder <player>
		if (inputs.get(answer).equals("Add Builder <player> " + ChatColor.GRAY + "Adds person that can interact")) {
			if (args.length !=2) {
				return new ManagePark2(plugin, player, 3);
			}
			else {
				Vector<String> names = tools.findPersonExcept(args[1], player.getName());
				if(names.size() == 0) {
					return new ManagePark2(plugin, player, 5);
				}
				else if(names.size() > 1) {
					return new ManagePark2(plugin ,player,  4, names);
				}
				else {
					if (park.getBuilders().contains(names.get(0))) {
						return new ManagePark2(plugin, player, 42);
					}
					else {
						park.addBuilder(names.get(0));
					}
					return new ManagePark2(plugin, player,  0);
				}
			}
		}
		
		//Remove Builder <player>
		if (inputs.get(answer).equals("Remove Builder <player>")) {
			if (args.length !=2) {
				return new ManagePark2(plugin, player,  3);
			}
			else {
				Vector<String> names;
				names = tools.find(args[1], park.getBuilders());
				
				if(names.size() == 0) {
					return new ManagePark2(plugin, player, 43);
				}
				else if(names.size() > 1) {
					return new ManagePark2(plugin ,player, 4, names);
				}
				else {
					busi.removeBuilder(names.get(0));
					return new ManagePark2(plugin, player, 0);
				}
				
			}
		}
		// Rename <name>
		if (inputs.get(answer).equals("Rename <name>")) {
			if(args.length < 2) {
				return new ManagePark2(plugin, player, 3);
			}
			else {
				String ParkName = tools.formatSpace(tools.subArray(args, 1, args.length - 1));
				if(ParkName.contains("/")) {
					return new ManagePark2(plugin, player, 70);
				}
				boolean works = true;
				for(Park test:PDI.getTownResides().getParks()) {
					if(test.getName().equalsIgnoreCase(ParkName) && !test.equals(park)) {
						works = false;
					}
				}
				if (works) {
					park.setName(ParkName);
					return new ManagePark2(plugin, player, 0);
				}
				else {
					return new ManagePark2(plugin, player, 68);
				}
			}
		}
		
		if(inputs.get(answer).equals("Protection Level (" + menuType.OPTIONDESCRIP + park.getProtectionLevel() + menuType.OPTION + ")")) {
			return new LocalParkProtectionLevels(plugin, player, 0);
		}
		return new ManagePark2(plugin, player, 2);
	}



}
