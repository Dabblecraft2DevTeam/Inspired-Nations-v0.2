package com.github.InspiredOne.InspiredNations.ManageTown;

import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.HUD.Menu;

public class ManagePrison extends Menu {
	
	// Constructor
	public ManagePrison(InspiredNations instance, Player playertemp, int errortemp) {
		super(instance, playertemp, errortemp);
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
		inputs.add("Reclaim Prison");
		
		// Make Output text
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
		
		try {
			answer = Integer.decode(args[0])-1;
		}
		catch (Exception ex) {
			return new ManagePrison(plugin, player,1);
		}
		
		if (answer > inputs.size()-1) {
			return new ManagePrison(plugin, player, 2);
		}
		
		if(inputs.get(answer).equals("Reclaim Prison")) {
			PM.localPrison(true);
			return new SelectPrison1(plugin, player, 0);
		}
		
		return new ManagePrison(plugin, player, 2);
	}




}
