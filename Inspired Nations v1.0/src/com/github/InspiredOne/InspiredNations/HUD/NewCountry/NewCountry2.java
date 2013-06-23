package com.github.InspiredOne.InspiredNations.HUD.NewCountry;

import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.entity.Player;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.PlayerModes;
import com.github.InspiredOne.InspiredNations.Tools;
import com.github.InspiredOne.InspiredNations.Regions.Country;
import com.github.InspiredOne.InspiredNations.Tools.optionType;

public class NewCountry2 extends StringPrompt {

	InspiredNations plugin;
	Tools tools;
	Player player;
	PlayerData PDI;
	PlayerModes PM;
	String playername;
	boolean permission = true;
	int error;
	Country country;
	
	// Constructor
	public NewCountry2(InspiredNations instance, Player playertemp, int errortemp) {
		plugin = instance;
		tools = new Tools(plugin);
		player = playertemp;
		PDI = plugin.playerdata.get(player.getName());
		country = PDI.getCountryRuled();
		playername = player.getName();
		PM = plugin.playermodes.get(playername);
		error = errortemp;
	}
	
	@Override
	public String getPromptText(ConversationContext arg0) {
		String space = tools.space();
		String main = tools.header("New Country Economics. Read the instructions.");
		String options = "";
		String end = tools.footer(false);
		String errmsg = ChatColor.RED + tools.errors.get(error);
		
		options = tools.addLine(options, "Type the name of your money.\nPut the singular form first and then the plural form" +
				".\nFor example: 'coin coins'", optionType.INSTRUCTION);
		
		return space + main + options + end + errmsg;
	}
	
	@Override
	public Prompt acceptInput(ConversationContext arg0, String arg) {
		if (arg.startsWith("/")) {
			arg = arg.substring(1);
		}
		if (arg.equalsIgnoreCase("back")) {
			PDI.setCountryRuled(null);
			plugin.countrydata.remove(country.getName());
			return new NewCountry1(plugin, player, 0);
		}
		String[] args = arg.split(" ");
		if(args.length != 2) {
			return new NewCountry2(plugin, player, 15);
		}
		else if(!tools.moneyUnique(args[0]) || !tools.moneyUnique(args[1])) {
			return new NewCountry2(plugin, player, 16);
		}
		else {
			country.setPluralMoney(args[1]);
			country.setSingularMoney(args[0]);
			return new NewCountry3(plugin, player, 0);
		}
	}




}
