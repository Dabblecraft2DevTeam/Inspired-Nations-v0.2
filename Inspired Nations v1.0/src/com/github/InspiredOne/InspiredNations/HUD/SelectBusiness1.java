package com.github.InspiredOne.InspiredNations.HUD;

import java.util.Vector;

import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.entity.Player;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.PlayerModes;
import com.github.InspiredOne.InspiredNations.Tools;
import com.github.InspiredOne.InspiredNations.Regions.Town;

public class SelectBusiness1 extends StringPrompt {

	InspiredNations plugin;
	Tools tools;
	Player player;
	PlayerData PDI;
	PlayerModes PM;
	Town town;
	
	Vector<String> inputs = new Vector<String>();
	int error;
	
	// Constructor
	public SelectBusiness1(InspiredNations instance, Player playertemp, int errortemp) {
		plugin = instance;
		player = playertemp;
		tools = new Tools(plugin);
		PDI = plugin.playerdata.get(player.getName());
		PM = plugin.playermodes.get(player.getName());
		error = errortemp;
		town = PDI.getTownResides();
	}
	
	@Override
	public String getPromptText(ConversationContext arg0) {
		String space = tools.space();
		String main = tools.header("New Business. Select Business Type.");
		String options = "";
		String end = tools.footer(false);
		String errmsg = ChatColor.RED + tools.errors.get(error);
		
		// Make Inputs Vector
		inputs.add("Make Good Business");
		inputs.add("Make Service Business");
		
		// Make options text
		options = options.concat(ChatColor.YELLOW + "What kind of Business would you like to make?\n\n"+ ChatColor.YELLOW + "A " 
		+ ChatColor.GOLD + "Good Business" + ChatColor.YELLOW + " allows you to sell any item in the game.\n" 
				+ "A " + ChatColor.GOLD + "Service Business" + ChatColor.YELLOW + " allows you to provide players with services like building a house or harvesting crops.\n");
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
			return new HudConversationMain(plugin, player, 0);
		}
		
		try {
			answer = Integer.decode(arg)-1;
		}
		catch (Exception ex) {
			return new SelectBusiness1(plugin, player,1);
		}
		
		if (answer > inputs.size()-1) {
			return new SelectBusiness1(plugin, player, 2);
		}
		if (inputs.get(answer).equals("Make Good Business")) {
			PM.serviceBusiness(false);
			PM.goodBusiness(true);
			return new SelectBusiness2(plugin, player, 0);
		}
		else if(inputs.get(answer).equals("Make Service Business")) {
			PM.serviceBusiness(true);
			PM.goodBusiness(false);
			return new SelectBusiness2(plugin, player, 0);
		}
		return new SelectBusiness1(plugin, player, 2);
	}



}
