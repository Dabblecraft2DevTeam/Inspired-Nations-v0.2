package com.github.InspiredOne.InspiredNations.HUD.ManageCountry;


import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.HUD.Menu;
import com.github.InspiredOne.InspiredNations.Regions.Park;

public class ManageFederalPark1 extends Menu {

	// Constructor
	public ManageFederalPark1(InspiredNations instance, Player playertemp, int errortemp) {
		super(instance, playertemp, errortemp);
		country = PDI.getCountryRuled();
		for(Park park:country.getParks()) {
			inputs.add(park.getName());
		}
	}
	
	@Override
	public String getPromptText(ConversationContext arg0) {
		String space = tools.space();
		String main = tools.header("Manage Park. Type an option number.");
		String options = "";
		String end = tools.footer(false);
		String errmsg = ChatColor.RED + tools.errors.get(error);
		
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
			return new CountryGovernmentRegions(plugin, player, 0);
		}
		String[] args = arg.split(" ");
		
		try {
			answer = Integer.decode(args[0])-1;
		}
		catch (Exception ex) {
			return new ManageFederalPark1(plugin, player,1);
		}
		
		if (answer > inputs.size()-1) {
			return new ManageFederalPark1(plugin, player, 2);
		}
		
		Park selection = null;
		for(Park park: country.getParks()) {
			if(park.getName().equals(inputs.get(answer))) {
				selection = park;
				break;
			}
		}
		arg0.setSessionData("federalpark", selection);
		
		return new ManageFederalPark2(plugin, player, 0);
	}



}
