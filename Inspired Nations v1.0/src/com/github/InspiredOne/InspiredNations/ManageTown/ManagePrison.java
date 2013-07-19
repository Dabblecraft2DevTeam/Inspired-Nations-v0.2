package com.github.InspiredOne.InspiredNations.ManageTown;

import java.util.Vector;

import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.HUD.Menu;
import com.github.InspiredOne.InspiredNations.Tools.menuType;

public class ManagePrison extends Menu {
	
	// Constructor
	public ManagePrison(InspiredNations instance, Player playertemp, int errortemp) {
		super(instance, playertemp, errortemp);
		town = PDI.getTownMayored();
	}
	// Constructor
	public ManagePrison(InspiredNations instance, Player playertemp, int errortemp, Vector<String> names) {
		super(instance, playertemp, errortemp, names);
		town = PDI.getTownMayored();
	}
	
	@Override
	public String getPromptText(ConversationContext arg0) {
		String space = tools.space();
		String main = tools.header("Manage Prison. Type an option number.");
		String options = "";
		String end = tools.footer(false);
		String errmsg = ChatColor.RED + tools.errors.get(error);
		
		//Make Inputs Vector
		inputs.add("Add Builder <player> " + ChatColor.GRAY + "Adds person that can interact");
		if(town.getPrison().getBuilders().size() > 0) {
			inputs.add("Remove Builder <player>");
		}
		inputs.add("Add Cell");
		if(town.getPrison().getCells().size() > 0) {
			inputs.add("Remove Cell <cell name>");
		}
		inputs.add("Reclaim Prison");

		
		// Make Output text
		options = options.concat(menuType.LABEL + "Cells: ");
		options = options.concat(menuType.VALUE + tools.format(new Vector<String>(town.getPrison().getCells().keySet())) + "\n");
		options = tools.addDivider(options);
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
			return new ManagePrison(plugin, player, 0);
		}
		try {
			answer = Integer.decode(args[0])-1;
		}
		catch (Exception ex) {
			return new ManagePrison(plugin, player,1);
		}
		
		if (answer > inputs.size()-1) {
			return new ManagePrison(plugin, player, 2);
		}
		// Reclaim Prison
		if(inputs.get(answer).equals("Reclaim Prison")) {
			PM.localPrison(true);
			return new SelectPrison1(plugin, player, 0);
		}
		
		// Add Builder <player>
		if (inputs.get(answer).equals("Add Builder <player> " + ChatColor.GRAY + "Adds person that can interact")) {
			if (args.length !=2) {
				return new ManagePrison(plugin, player, 3);
			}
			else {
				Vector<String> names = tools.findPersonExcept(args[1], player.getName());
				if(names.size() == 0) {
					return new ManagePrison(plugin, player, 5);
				}
				else if(names.size() > 1) {
					return new ManagePrison(plugin ,player,  4, names);
				}
				else {
					if (town.getPrison().getBuilders().contains(names.get(0))) {
						return new ManagePrison(plugin, player, 42);
					}
					else {
						town.getPrison().addBuilder(names.get(0));
					}
					return new ManagePrison(plugin, player,  0);
				}
			}
		}
		
		//Remove Builder <player>
		if (inputs.get(answer).equals("Remove Builder <player>")) {
			if (args.length !=2) {
				return new ManagePrison(plugin, player,  3);
			}
			else {
				Vector<String> names;
				names = tools.find(args[1], town.getPrison().getBuilders());
				
				if(names.size() == 0) {
					return new ManagePrison(plugin, player, 43);
				}
				else if(names.size() > 1) {
					return new ManagePrison(plugin ,player, 4, names);
				}
				else {
					town.getPrison().removeBuilder(names.get(0));
					return new ManagePrison(plugin, player, 0);
				}
				
			}
		}
		
		// Add Cell
		if(inputs.get(answer).equals("Add Cell")) {
			return new AddCell(plugin, player, 0);
		}
		
		// Remove Cell
		if(inputs.get(answer).equals("Remove Cell <cell name>")) {
			if(args.length < 2) {
				return new ManagePrison(plugin, player, 3);
			}
			Vector<String> cellnames = new Vector<String>();
			cellnames.addAll(town.getPrison().getCells().keySet());
			
			cellnames = tools.find(tools.formatSpace(tools.subArray(args, 1, args.length - 1)), cellnames);
			
			if(cellnames.size() > 1) {
				return new ManagePrison(plugin, player, 4, cellnames);
			}
			else if(cellnames.size() == 0) {
				return new ManagePrison(plugin, player, 73);
			}
			else {
				town.getPrison().removeCell(cellnames.get(0));
				return new ManagePrison(plugin, player, 0);
			}
		}
		
		return new ManagePrison(plugin, player, 2);
	}
}
