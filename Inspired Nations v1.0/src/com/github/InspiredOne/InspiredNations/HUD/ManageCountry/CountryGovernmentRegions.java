package com.github.InspiredOne.InspiredNations.HUD.ManageCountry;

import java.util.Vector;

import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.Tools.menuType;
import com.github.InspiredOne.InspiredNations.HUD.Menu;

public class CountryGovernmentRegions extends Menu {
	Vector<String>  nameslist = new Vector<String>();
	
	// Constructor
	public CountryGovernmentRegions(InspiredNations instance, Player playertemp, int errortemp) {
		super(instance, playertemp, errortemp);
		country = PDI.getCountryRuled();
	}
	
	// Constructor
	public CountryGovernmentRegions(InspiredNations instance, Player playertemp, int errortemp, Vector<String> namestemp) {
		super(instance, playertemp, errortemp, namestemp);
		country = PDI.getCountryRuled();
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
		inputs.add("Select Park " + menuType.OPTIONDESCRIP + "Protects a given area from citizens");
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
				Vector<String> towns = tools.findTown(country.getName(), tools.formatSpace(tools.subArray(args, 1, args.length - 1)));
				if (towns.size() == 1) {
					country.getCapital().setIsCapital(false);
					tools.findTown(country, towns.get(0)).get(0).setIsCapital(true);
				}
				else if (towns.size() == 0) {
					return new CountryGovernmentRegions(plugin, player, 37);
				}
				else {
					return new CountryGovernmentRegions(plugin, player, 4, towns);
				}
			}
		}
		
		else if (inputs.get(answer).equals("Select Park " + menuType.OPTIONDESCRIP + "Protects a given area from citizens")) {
			PM.federalPark(true);
			return new SelectFederalPark1(plugin, player, 0);
		}
		
		return new CountryGovernmentRegions(plugin ,player, 2);
	}



}
