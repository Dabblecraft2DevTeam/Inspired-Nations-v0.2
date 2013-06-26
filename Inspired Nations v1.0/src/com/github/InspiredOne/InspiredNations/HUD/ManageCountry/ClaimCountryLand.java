package com.github.InspiredOne.InspiredNations.HUD.ManageCountry;

import java.math.BigDecimal;
import java.util.Vector;

import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.entity.Player;

import com.github.InspiredOne.InspiredNations.CountryMethods;
import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.PlayerMethods;
import com.github.InspiredOne.InspiredNations.PlayerModes;
import com.github.InspiredOne.InspiredNations.Tools;
import com.github.InspiredOne.InspiredNations.Regions.Country;
import com.github.InspiredOne.InspiredNations.Tools.mapSize;
import com.github.InspiredOne.InspiredNations.Tools.optionType;

public class ClaimCountryLand extends StringPrompt {

	InspiredNations plugin;
	Tools tools;
	Player player;
	PlayerData PDI;
	PlayerModes PM;
	PlayerMethods PMeth;
	String playername;
	int error;
	Country country;
	CountryMethods CM;
	String names = "";
	
	Vector<String> inputs = new Vector<String>();
	
	// Constructor
	public ClaimCountryLand(InspiredNations instance, Player playertemp, int errortemp) {
		plugin = instance;
		tools = new Tools(plugin);
		player = playertemp;
		PDI = plugin.playerdata.get(player.getName());
		country = PDI.getCountryRuled();
		playername = player.getName();
		PM = plugin.playermodes.get(playername);
		error = errortemp;
		CM = new CountryMethods(plugin, country);
		PMeth = new PlayerMethods(plugin, player);
		if (country.getMoney().compareTo(CM.getCostPerChunk().multiply(new BigDecimal(plugin.taxTimer.getFractionLeft()))) < 0) {
			error = 25;
		}
	}
	
	@Override
	public String getPromptText(ConversationContext arg0) {
		String space = tools.space();
		String main = tools.header("Claim Country Land. Type what you would like to do.");
		String options = "";
		String end = tools.footer(false);
		String errmsg = ChatColor.RED + tools.errors.get(error);
		
		// Make input vector
		
		// Make options text
		if(!PM.countrySelect()) {
			options = tools.addLine(options, "Type 'begin' to begin claiming land", optionType.INSTRUCTION);
		}
		else{
			options = tools.addLine(options, "Type 'stop' to stop claiming land", optionType.INSTRUCTION);
		}
			
		options = tools.addDivider(options);
		options = options.concat(tools.drawCountryMap(player, mapSize.LARGE));
		options = tools.addLine(options, "Cost per tax cycle: " + CM.getTaxAmount() + " " + country.getPluralMoney(), optionType.INSTRUCTION);
		return space + main + options + end + errmsg;
	}
	
	@Override
	public Prompt acceptInput(ConversationContext arg0, String arg) {
		if (arg.startsWith("/")) {
			arg = arg.substring(1);
		}
		String[] args = arg.split(" ");
		if (arg.equalsIgnoreCase("back")) {
			PM.preCountry(false);
			PM.country(false);
			return new ManageCountry(plugin, player, 0);
		}
		else if (args[0].equalsIgnoreCase("say"))  {
			if(args.length > 1) {
				PMeth.SendChat(tools.formatSpace(tools.subArray(args, 1, args.length - 1)));
			}
			return new ClaimCountryLand(plugin, player, 0);
		}
		else if(arg.equalsIgnoreCase("begin")) {
			PM.country(true);
			return new ClaimCountryLand(plugin, player, 0);
		}
		else if(arg.equalsIgnoreCase("stop")) {
			PM.country(false);
			return new ClaimCountryLand(plugin, player, 0);
		}
		return new ClaimCountryLand(plugin, player, 2);
	}



}
