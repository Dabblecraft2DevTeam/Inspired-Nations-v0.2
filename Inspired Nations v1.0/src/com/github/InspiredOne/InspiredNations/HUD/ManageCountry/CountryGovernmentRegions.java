package com.github.InspiredOne.InspiredNations.HUD.ManageCountry;

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
import com.github.InspiredOne.InspiredNations.Regions.Country;
import com.github.InspiredOne.InspiredNations.Regions.Town;

public class CountryGovernmentRegions extends StringPrompt {
	InspiredNations plugin;
	Tools tools;
	Player player;
	PlayerData PDI;
	PlayerModes PM;
	PlayerMethods PMeth;
	String playername;
	int error;
	Country country;
	String names = "";
	
	Vector<String> inputs = new Vector<String>();
	Vector<String>  nameslist = new Vector<String>();
	
	// Constructor
	public CountryGovernmentRegions(InspiredNations instance, Player playertemp, int errortemp) {
		plugin = instance;
		tools = new Tools(plugin);
		player = playertemp;
		PDI = plugin.playerdata.get(player.getName());
		country = PDI.getCountryRuled();
		playername = player.getName();
		PM = plugin.playermodes.get(playername);
		PMeth = new PlayerMethods(plugin ,player);
		error = errortemp;
	}
	
	// Constructor
	public CountryGovernmentRegions(InspiredNations instance, Player playertemp, int errortemp, Vector<Town> namestemp) {
		plugin = instance;
		tools = new Tools(plugin);
		player = playertemp;
		PDI = plugin.playerdata.get(player.getName());
		country = PDI.getCountryRuled();
		playername = player.getName();
		PM = plugin.playermodes.get(playername);
		PMeth = new PlayerMethods(plugin ,player);
		error = errortemp;
		for(Town town: namestemp) {
			nameslist.add(town.getName());
		}
		names = tools.format(nameslist);
		
	}
	
	@Override
	public String getPromptText(ConversationContext arg0) {
		String space = tools.space();
		String main = tools.header("Government Regions. Type an option number.");
		String options = "";
		String end = tools.footer(false);
		String errmsg = ChatColor.RED + tools.errors.get(error) + names;
		
		// Make Inputs Vector
		inputs.add("Make Capital <town>");
		inputs.add("Select Park " + ChatColor.GRAY + "Protects a given area from citizens");
		if(country.getParks().size() != 0) {
			inputs.add("Manage Park");
		}
		
		// Make Output Text
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
			return new ManageCountry(plugin, player, 0);
		}
		String[] args = arg.split(" ");
		if (args[0].equalsIgnoreCase("say"))  {
			if(args.length > 1) {
				PMeth.SendChat(tools.formatSpace(tools.subArray(args, 1, args.length - 1)));
			}
			return new CountryGovernmentRegions(plugin, player, 0);
		}
		
		try {
			answer = Integer.decode(args[0])-1;
		}
		catch (Exception ex) {
			return new CountryGovernmentRegions(plugin, player,1);
		}
		
		if (inputs.get(answer).equals("Make Capital <town>")) {
			if(args.length < 2) {
				return new CountryGovernmentRegions(plugin, player, 3);
			}
			else {
				Vector<Town> towns = tools.findTown(country, tools.formatSpace(tools.subArray(args, 1, args.length - 1)));
				if (towns.size() == 1) {
					country.getCapital().setIsCapital(false);
					towns.get(0).setIsCapital(true);
				}
				else if (towns.size() == 0) {
					return new CountryGovernmentRegions(plugin, player, 37);
				}
				else {
					return new CountryGovernmentRegions(plugin, player, 4, towns);
				}
			}
		}
		
		else if (inputs.get(answer).equals("Select Park " + ChatColor.GRAY + "Protects a given area from citizens")) {
			PM.federalPark(true);
			return new SelectFederalPark1(plugin, player, 0);
		}
		
		return new CountryGovernmentRegions(plugin ,player, 2);
	}



}
