package com.github.InspiredOne.InspiredNations.HUD.NewCountry;

import java.math.BigDecimal;

import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.entity.Player;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.PlayerModes;
import com.github.InspiredOne.InspiredNations.Tools;
import com.github.InspiredOne.InspiredNations.HUD.HudConversationMain;
import com.github.InspiredOne.InspiredNations.Regions.Country;
import com.github.InspiredOne.InspiredNations.Tools.optionType;

public class NewCountry3 extends StringPrompt {

	InspiredNations plugin;
	Tools tools;
	Player player;
	PlayerData PDI;
	PlayerModes PM;
	String playername;
	int error;
	Country country;
	
	// Constructor
	public NewCountry3(InspiredNations instance, Player playertemp, int errortemp) {
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
		
		options = tools.addLine(options, "How many " + PDI.getPluralMoney() + " is a single diamond is worth?" +
				"\nThis will set the value of your currency.\nChose between: "+ tools.cut(new BigDecimal(plugin.getConfig().getDouble("min_diamond_value"))) +
				" and " + tools.cut(new BigDecimal(plugin.getConfig().getDouble("max_diamond_value"))) + ".", optionType.INSTRUCTION);
		
		return space + main + options + end + errmsg;
	}
	
	@Override
	public Prompt acceptInput(ConversationContext arg0, String arg) {
		if (arg.startsWith("/")) {
			arg = arg.substring(1);
		}
		if (arg.equalsIgnoreCase("back")) {
			country.setPluralMoney("");
			country.setSingularMoney("");
			return new NewCountry2(plugin, player, 0);
		}
		
		try {
			BigDecimal diamond = new BigDecimal(arg);
			if(diamond.compareTo(new BigDecimal(plugin.getConfig().getDouble("max_diamond_value")))>0) {
				return new NewCountry3(plugin, player, 18);
			}
			else if(diamond.compareTo(new BigDecimal(plugin.getConfig().getDouble("min_diamond_value")))<0) {
				return new NewCountry3(plugin, player, 19);
			}
			else {
				country.setMoneyMultiplyer(diamond.divide(new BigDecimal(500), 25, BigDecimal.ROUND_DOWN));
				return new HudConversationMain(plugin, player, 0);
			}
		}
		catch(Exception ex) {
			return new NewCountry3(plugin, player, 17);
		}
	}
}
