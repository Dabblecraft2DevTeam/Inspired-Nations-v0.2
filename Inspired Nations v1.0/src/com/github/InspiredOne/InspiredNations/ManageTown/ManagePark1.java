package com.github.InspiredOne.InspiredNations.ManageTown;

import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.HUD.Menu;
import com.github.InspiredOne.InspiredNations.Regions.Park;

public class ManagePark1 extends Menu implements Prompt {

	// Constructor
	public ManagePark1(InspiredNations instance, Player playertemp, int errortemp) {
		super(instance, playertemp, errortemp);
		town = PDI.getTownMayored();
		for(Park park:town.getParks()) {
			inputs.add(park.getName());
		}
	}
	
	@Override
	public boolean blocksForInput(ConversationContext arg0) {
		if (inputs.size() == 1) {
			PDI.getConversation().acceptInput("1");
			return true;
		}
		else {
			return true;
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
			return new TownGovernmentRegions(plugin, player, 0);
		}
		String[] args = arg.split(" ");
		if (args[0].equalsIgnoreCase("say"))  {
			if(args.length > 1) {
				PMeth.SendChat(tools.formatSpace(tools.subArray(args, 1, args.length - 1)));
			}
			return new ManagePark1(plugin, player, 0);
		}
		try {
			answer = Integer.decode(args[0])-1;
		}
		catch (Exception ex) {
			return new ManagePark1(plugin, player,1);
		}
		
		if (answer > inputs.size()-1) {
			return new ManagePark1(plugin, player, 2);
		}
		
		Park selection = null;
		for(Park park: town.getParks()) {
			if(park.getName().equals(inputs.get(answer))) {
				selection = park;
				break;
			}
		}
		arg0.setSessionData("localpark", selection);
		
		return new ManagePark2(plugin, player, 0);
	}
}
