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
import com.github.InspiredOne.InspiredNations.Tools.mapSize;

public class ShowMap extends StringPrompt {
	InspiredNations plugin;
	Tools tools;
	Player player;
	PlayerData PDI;
	PlayerModes PM;
	PlayerMethods PMeth;
	
	Vector<String> inputs = new Vector<String>();
	int error;
	
	// Constructor
	public ShowMap(InspiredNations instance, Player playertemp, int errortemp) {
		plugin = instance;
		player = playertemp;
		tools = new Tools(plugin);
		PDI = plugin.playerdata.get(player.getName());
		PM = plugin.playermodes.get(player.getName());
		PMeth = new PlayerMethods(plugin ,player);
		error = errortemp;
	}
	
	@Override
	public String getPromptText(ConversationContext arg0) {
		String space = tools.space();
		String main = tools.header("Map. The Surrounding Area Is Shown.");
		String options = "";
		String end = tools.footer(false);
		String errmsg = ChatColor.RED + tools.errors.get(error);
		
		options = tools.drawTownMap(player, mapSize.LARGE);
		
		return space + main + options + end + errmsg;
	}
	
	@Override
	public Prompt acceptInput(ConversationContext arg0, String arg) {
		if (arg.startsWith("/")) {
			arg = arg.substring(1);
		}
		String[] args = arg.split(" ");
		if (args[0].equalsIgnoreCase("say"))  {
			if(args.length > 1) {
				PMeth.SendChat(tools.formatSpace(tools.subArray(args, 1, args.length - 1)));
			}
			return new ShowMap(plugin, player, 0);
		}
		
		if (arg.equalsIgnoreCase("back")) {
			PM.setMap(false);
			return new HudConversationMain(plugin, player, 0);
		}
		return new ShowMap(plugin, player, 2);
	}




}
