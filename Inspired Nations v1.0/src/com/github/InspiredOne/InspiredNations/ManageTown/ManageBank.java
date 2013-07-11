package com.github.InspiredOne.InspiredNations.ManageTown;

import java.util.Vector;

import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.HUD.Menu;

public class ManageBank extends Menu {

	// Constructor
	public ManageBank(InspiredNations instance, Player playertemp, int errortemp) {
		super(instance, playertemp, errortemp);
		town = PDI.getTownMayored();
	}
	// Constructor
	public ManageBank(InspiredNations instance, Player playertemp, int errortemp, Vector<String> names) {
		super(instance, playertemp, errortemp, names);
		town = PDI.getTownMayored();
	}
	@Override
	public String getPromptText(ConversationContext arg0) {
		String space = tools.space();
		String main = tools.header("Manage Bank. Type an option number.");
		String options = "";
		String end = tools.footer(false);
		String errmsg = ChatColor.RED + tools.errors.get(error);
		
		// Make Inputs Vector
		inputs.add("Add Builder <player> " + ChatColor.GRAY + "Adds person that can interact");
		if(town.getBank().getBuilders().size() > 0) {
			inputs.add("Remove Builder <player>");
		}
		inputs.add("Reclaim Bank");
		
		// Make Options text
		
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
			return new TownGovernmentRegions(plugin, player, 0);
		}
		String[] args = arg.split(" ");
		if (args[0].equalsIgnoreCase("say"))  {
			if(args.length > 1) {
				PMeth.SendChat(tools.formatSpace(tools.subArray(args, 1, args.length - 1)));
			}
			return new ManageBank(plugin, player, 0);
		}
		
		try {
			answer = Integer.decode(args[0])-1;
		}
		catch (Exception ex) {
			return new ManageBank(plugin, player,1);
		}
		
		if (answer > inputs.size()-1) {
			return new ManageBank(plugin, player, 2);
		}
		
		// Reclaim
		if (inputs.get(answer).equals("Reclaim Bank")) {
			PM.localBank(true);
			return new SelectBank1(plugin, player, 0);
		}
		
		// Add Builder <player>
		if (inputs.get(answer).equals("Add Builder <player> " + ChatColor.GRAY + "Adds person that can interact")) {
			if (args.length !=2) {
				return new ManageBank(plugin, player, 3);
			}
			else {
				Vector<String> names = tools.findPersonExcept(args[1], player.getName());
				if(names.size() == 0) {
					return new ManageBank(plugin, player, 5);
				}
				else if(names.size() > 1) {
					return new ManageBank(plugin ,player,  4, names);
				}
				else {
					if (town.getBank().getBuilders().contains(names.get(0))) {
						return new ManageBank(plugin, player, 42);
					}
					else {
						town.getBank().addBuilder(names.get(0));
					}
					return new ManageBank(plugin, player,  0);
				}
			}
		}
		
		//Remove Builder <player>
		if (inputs.get(answer).equals("Remove Builder <player>")) {
			if (args.length !=2) {
				return new ManageBank(plugin, player,  3);
			}
			else {
				Vector<String> names;
				names = tools.find(args[1], town.getBank().getBuilders());
				
				if(names.size() == 0) {
					return new ManageBank(plugin, player, 43);
				}
				else if(names.size() > 1) {
					return new ManageBank(plugin ,player, 4, names);
				}
				else {
					town.getBank().removeBuilder(names.get(0));
					return new ManageBank(plugin, player, 0);
				}
			}
		}
		return new ManageBank(plugin, player, 2);
	}
}
