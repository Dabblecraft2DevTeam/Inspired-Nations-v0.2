package com.github.InspiredOne.InspiredNations.HUD;

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
import com.github.InspiredOne.InspiredNations.Regions.Town;
import com.github.InspiredOne.InspiredNations.Tools.optionType;

public class NewTown extends StringPrompt {

	InspiredNations plugin;
	Tools tools;
	Player player;
	PlayerData PDI;
	PlayerModes PM;
	PlayerMethods PMeth;
	
	Vector<String> inputs = new Vector<String>();
	int error;
	
	// Constructor
	public NewTown(InspiredNations instance, Player playertemp, int errortemp) {
		plugin = instance;
		player = playertemp;
		tools = new Tools(plugin);
		PDI = plugin.playerdata.get(player.getName());
		PM = plugin.playermodes.get(player.getName());
		error = errortemp;
		PMeth = new PlayerMethods(plugin, player);
	}
	
	@Override
	public String getPromptText(ConversationContext arg0) {
		String space = tools.space();
		String main = tools.header("New Town. Read the instructions.");
		String options = "";
		String end = tools.footer(true);
		String errmsg = ChatColor.RED + tools.errors.get(error);
		
		// make inputs vector
		
		// make options text
		options = tools.addLine(options, "Type the name of your new town.", optionType.INSTRUCTION);
		
		return space + main + options + end + errmsg;
	}
	
	@Override
	public Prompt acceptInput(ConversationContext arg0, String arg) {
		if (arg.startsWith("/")) {
			arg = arg.substring(1);
		}
		if (arg.equalsIgnoreCase("back")) {
			return new HudConversationMain(plugin, player, 0);
		}
		
		if (arg.contains("/")) {
			return new NewTown(plugin, player, 24);
		}
		if (!tools.townUnique(arg, PDI.getCountryResides().getName())) {
			return new NewTown(plugin, player, 26);
		}
		else {
			
			Town town = new Town(plugin, arg, player.getName(), PDI.getCountryResides().getName());
			PMeth.transferTown(town);
			return new HudConversationMain(plugin, player, 0);
		}
	}



}
