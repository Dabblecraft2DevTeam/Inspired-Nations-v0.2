package com.github.InspiredOne.InspiredNations.HUD.ManageCountry;


import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;

import com.github.InspiredOne.InspiredNations.CountryMethods;
import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.HUD.Menu;
import com.github.InspiredOne.InspiredNations.Tools.mapSize;
import com.github.InspiredOne.InspiredNations.Tools.optionType;
import com.github.InspiredOne.InspiredNations.Tools.version;

public class UnclaimCountryLand extends Menu {

	
	// Constructor
	public UnclaimCountryLand(InspiredNations instance, Player playertemp, int errortemp) {
		super(instance, playertemp, errortemp);
		country = PDI.getCountryRuled();
		CM = new CountryMethods(plugin, country);
	}
	
	@Override
	public String getPromptText(ConversationContext arg0) {
		String space = tools.space();
		String main = tools.header("Unclaim Country Land. Type what you would like to do.");
		String options = "";
		String end = tools.footer(false);
		String errmsg = ChatColor.RED + tools.errors.get(error);
		
		// Make input vector
		
		// Make options text
		if(!PM.countryDeselect()) {
			options = tools.addLine(options, "Type 'begin' to begin unclaiming land", optionType.INSTRUCTION);
		}
		else{
			options = tools.addLine(options, "Type 'stop' to stop unclaiming land", optionType.INSTRUCTION);
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
		if (args[0].equalsIgnoreCase("say"))  {
			if(args.length > 1) {
				PMeth.SendChat(tools.formatSpace(tools.subArray(args, 1, args.length - 1)));
			}
			return new UnclaimCountryLand(plugin, player, 0);
		}
		
		if (arg.equalsIgnoreCase("back")) {
			PM.predecountry(false);
			PM.decountry(false);
			return new ManageCountry(plugin, player, 0);
		}
		else if(arg.equalsIgnoreCase("begin")) {
			PM.decountry(true);
			return new UnclaimCountryLand(plugin, player, 0);
		}
		else if(arg.equals("stop")) {
			PM.decountry(false);
			return new UnclaimCountryLand(plugin, player, 0);
		}
		return new UnclaimCountryLand(plugin, player, 2);
	}
}
