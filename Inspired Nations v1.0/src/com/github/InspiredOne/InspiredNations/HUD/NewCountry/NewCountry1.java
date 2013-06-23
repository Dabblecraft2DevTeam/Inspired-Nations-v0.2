package com.github.InspiredOne.InspiredNations.HUD.NewCountry;

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
import com.github.InspiredOne.InspiredNations.HUD.HudConversationMain;
import com.github.InspiredOne.InspiredNations.Regions.Country;
import com.github.InspiredOne.InspiredNations.Tools.optionType;

public class NewCountry1 extends StringPrompt {
	
	InspiredNations plugin;
	Tools tools;
	Player player;
	PlayerData PDI;
	PlayerModes PM;
	PlayerMethods PMeth;
	String playername;
	boolean permission = true;
	int error;
	
	// Constructor
	public NewCountry1(InspiredNations instance, Player playertemp, int errortemp) {
		plugin = instance;
		tools = new Tools(plugin);
		player = playertemp;
		PDI = plugin.playerdata.get(player.getName());
		PMeth = new PlayerMethods(plugin, player);
		playername = player.getName();
		PM = plugin.playermodes.get(playername);
		error = errortemp;
	}

	@Override
	public String getPromptText(ConversationContext arg0) {
		String space = tools.space();
		String main = tools.header("New Country. Read the instructions.");
		String options = "";
		String end = tools.footer(false);
		String errmsg = ChatColor.RED + tools.errors.get(error);
		
		options = tools.addLine(options, "Type the name of your new country.", optionType.INSTRUCTION);
		
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
			return new NewCountry1(plugin, player, 24);
		}
		
		if (!tools.countryUnique(arg)) {
			return new NewCountry1(plugin, player, 14);
		}
		else {
			PMeth.leaveCountry();
			new Country(plugin, arg.trim(), playername);
			return new NewCountry2(plugin, player, 0);
		}
	}
}
