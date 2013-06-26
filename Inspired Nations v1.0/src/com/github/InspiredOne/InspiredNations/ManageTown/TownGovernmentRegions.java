package com.github.InspiredOne.InspiredNations.ManageTown;

import java.util.Vector;

import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.entity.Player;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.PlayerMethods;
import com.github.InspiredOne.InspiredNations.PlayerModes;
import com.github.InspiredOne.InspiredNations.Tools;
import com.github.InspiredOne.InspiredNations.Regions.Cuboid;
import com.github.InspiredOne.InspiredNations.Regions.Town;
import com.github.InspiredOne.InspiredNations.Regions.polygonPrism;

public class TownGovernmentRegions extends StringPrompt {

	InspiredNations plugin;
	Tools tools;
	Player player;
	PlayerData PDI;
	PlayerModes PM;
	PlayerMethods PMeth;
	Town town;
	
	Vector<String> inputs = new Vector<String>();
	int error;
	
	// Constructor
	public TownGovernmentRegions(InspiredNations instance, Player playertemp, int errortemp) {
		plugin = instance;
		player = playertemp;
		tools = new Tools(plugin);
		PDI = plugin.playerdata.get(player.getName());
		PM = plugin.playermodes.get(player.getName());
		PMeth = new PlayerMethods(plugin, player);
		error = errortemp;
		town = PDI.getTownMayored();
	}
	
	@Override
	public String getPromptText(ConversationContext arg0) {
		String space = tools.space();
		String main = tools.header("Town Government Regions. Type an option number.");
		String options = "";
		String end = tools.footer(false);
		String errmsg = ChatColor.RED + tools.errors.get(error);

		// Make inputs vector
		inputs.add("Select Prison " + ChatColor.GRAY + "Allows imprisonment of town citizens");
		if(town.hasPrison()){
			inputs.add("Manage Prison");
		}
		inputs.add("Select Bank " + ChatColor.GRAY + "Allows loans and savings account");
		if(town.hasBank()) {
			inputs.add("Manage Bank");
		}
		inputs.add("Select Park " + ChatColor.GRAY + "Protects a given area from citizens");
		if(!town.getParks().isEmpty()) {
			inputs.add("Manage Park");
		}
		
		// Make option text
		options = tools.options(inputs);
		
		return space + main + options + end + errmsg;
	}
	
	@Override
	public Prompt acceptInput(ConversationContext arg0, String arg) {
		int answer = 0;
		if (arg.startsWith("/")) {
			arg = arg.substring(1);
		}
		if (arg.equalsIgnoreCase("back")) {
			return new ManageTown(plugin, player, 0);
		}
		String[] args = arg.split(" ");
		if (args[0].equalsIgnoreCase("say"))  {
			if(args.length > 1) {
				PMeth.SendChat(tools.formatSpace(tools.subArray(args, 1, args.length - 1)));
			}
			return new TownGovernmentRegions(plugin, player, 0);
		}
		
		try {
			answer = Integer.decode(args[0])-1;
		}
		catch (Exception ex) {
			return new TownGovernmentRegions(plugin, player,1);
		}
		
		if (answer > inputs.size()-1) {
			return new TownGovernmentRegions(plugin, player, 2);
		}
		
		PM.setPolygon(new polygonPrism(player.getWorld().getName()));
		PM.setCuboid(new Cuboid(player.getWorld().getName()));
		
		if (inputs.get(answer).equals("Select Prison " + ChatColor.GRAY + "Allows imprisonment of town citizens")) {
			PM.localPrison(true);
			return new SelectPrison1(plugin, player, 0);
		}
		else if (inputs.get(answer).equals("Manage Prison")) {
			return new ManagePrison(plugin, player, 0);
		}
		else if(inputs.get(answer).equals("Select Bank " + ChatColor.GRAY + "Allows loans and savings account")) {
			PM.localBank(true);
			return new SelectBank1(plugin, player, 0);
		}
		else if (inputs.get(answer).equals("Manage Bank")) {
			return new ManageBank(plugin, player, 0);
		}
		else if (inputs.get(answer).equals("Select Park " + ChatColor.GRAY + "Protects a given area from citizens")) {
			PM.park(true);
			return new SelectPark1(plugin, player, 0);
		}
		return new TownGovernmentRegions(plugin, player, 2);
	}


}
