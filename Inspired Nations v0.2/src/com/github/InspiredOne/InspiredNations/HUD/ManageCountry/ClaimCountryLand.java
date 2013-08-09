package com.github.InspiredOne.InspiredNations.HUD.ManageCountry;

import java.math.BigDecimal;

import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;

import com.github.InspiredOne.InspiredNations.CountryMethods;
import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.Tools.optionType;
import com.github.InspiredOne.InspiredNations.Tools.version;
import com.github.InspiredOne.InspiredNations.HUD.Menu;
import com.github.InspiredOne.InspiredNations.Tools.mapSize;

public class ClaimCountryLand extends Menu {

	// Constructor
	public ClaimCountryLand(InspiredNations instance, Player playertemp, int errortemp) {
		super(instance, playertemp, errortemp);
		country = PDI.getCountryRuled();
		CM = new CountryMethods(plugin, country);
		if (country.getMoney().compareTo(CM.getCostPerChunk(country.getProtectionLevel(), true, version.NEW).multiply(new BigDecimal(plugin.taxTimer.getFractionLeft()))) < 0) {
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
		options = tools.addLine(options, "Cost per tax cycle: " + CM.getTaxAmount(true, version.NEW) + " " + country.getPluralMoney(), optionType.INSTRUCTION);
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
