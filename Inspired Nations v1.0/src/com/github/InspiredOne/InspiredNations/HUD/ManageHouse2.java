package com.github.InspiredOne.InspiredNations.HUD;


import java.util.Vector;

import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.HUD.ManageBusiness.ManageBusiness2;
import com.github.InspiredOne.InspiredNations.Regions.Business;
import com.github.InspiredOne.InspiredNations.Regions.House;

public class ManageHouse2 extends Menu {
	
	
	// Constructor
	public ManageHouse2(InspiredNations instance, Player playertemp, House house, int errortemp) {
		super(instance, playertemp, errortemp);
		this.house = house;
	}
	// Constructor
	public ManageHouse2(InspiredNations instance, Player playertemp, House house, int errortemp, Vector<String> namestemp) {
		super(instance, playertemp, errortemp, namestemp);
		this.house = house;
	}
	
	@Override
	public String getPromptText(ConversationContext arg0) {
		String space = tools.space();
		String main = tools.header("Manage House. Type an option number.");
		String options = "";
		String end = tools.footer(false);
		String errmsg = ChatColor.RED + tools.errors.get(error);
		
		// Make Inputs Vector
		inputs.add("Add Builder <player>");
		if(house.getBuilders().size() !=0) {
			inputs.add("Remove Builder <player>");
		}
		inputs.add("Rename <name>");
		
		
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
			if(inputs.size() < 2) {
				return new HudConversationMain(plugin, player, 0);
			}
			else {
				return new ManageHouse1(plugin, player, 0);
			}
		}
		String[] args = arg.split(" ");
		if (args[0].equalsIgnoreCase("say"))  {
			if(args.length > 1) {
				PMeth.SendChat(tools.formatSpace(tools.subArray(args, 1, args.length - 1)));
			}
			return new ManageHouse2(plugin, player, house, 0);
		}
		
		try {
			answer = Integer.decode(args[0])-1;
		}
		catch (Exception ex) {
			return new ManageHouse2(plugin, player,house, 1);
		}
		
		if (answer > inputs.size()-1) {
			return new ManageHouse2(plugin, player, house, 2);
		}
		
		// Add Builder <player>
		if (inputs.get(answer).equals("Add Builder <player> " + ChatColor.GRAY + "Adds person that can interact")) {
			if (args.length !=2) {
				return new ManageHouse2(plugin, player, house, 3);
			}
			else {
				Vector<String> names = tools.findPersonExcept(args[1], player.getName());
				if(names.size() == 0) {
					return new ManageHouse2(plugin, player, house, 5);
				}
				else if(names.size() > 1) {
					return new ManageHouse2(plugin ,player,house,  4, names);
				}
				else {
					if (busi.getBuilders().contains(names.get(0))) {
						return new ManageHouse2(plugin, player, house, 42);
					}
					else {
						busi.addBuilder(names.get(0));
					}
					return new ManageHouse2(plugin, player, house, 0);
				}
			}
		}
		
		//Remove Builder <player>
		if (inputs.get(answer).equals("Remove Builder <player>")) {
			if (args.length !=2) {
				return new ManageHouse2(plugin, player, house, 3);
			}
			else {
				Vector<String> names;
				names = tools.find(args[1], house.getBuilders());
				
				if(names.size() == 0) {
					return new ManageHouse2(plugin, player, house, 43);
				}
				else if(names.size() > 1) {
					return new ManageHouse2(plugin ,player, house, 4, names);
				}
				else {
					busi.removeBuilder(names.get(0));
					return new ManageHouse2(plugin, player, house, 0);
				}
				
			}
		}
		// Rename <name>
		if (inputs.get(answer).equals("Rename <name>")) {
			if(args.length < 2) {
				return new ManageHouse2(plugin, player, house, 3);
			}
			else {
				String HouseName = tools.formatSpace(tools.subArray(args, 1, args.length - 1));
				if(HouseName.contains("/")) {
					return new ManageHouse2(plugin, player, house, 50);
				}
				boolean works = true;
				for(House test:PDI.getTownResides().getHouses()) {
					if(test.getName().equalsIgnoreCase(HouseName) && !test.equals(house)) {
						works = false;
					}
				}
				if (works) {
					house.setName(HouseName);
					return new ManageHouse2(plugin, player, house, 0);
				}
				else {
					return new ManageHouse2(plugin, player, house, 36);
				}
			}
		}
		return new ManageHouse2(plugin, player, house, 2);
	}

}
