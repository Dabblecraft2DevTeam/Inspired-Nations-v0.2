package com.github.InspiredOne.InspiredNations.HUD.ManageCountry;

import java.math.BigDecimal;

import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;

import com.github.InspiredOne.InspiredNations.CountryMethods;
import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.HUD.HudConversationMain;
import com.github.InspiredOne.InspiredNations.HUD.Menu;
import com.github.InspiredOne.InspiredNations.Tools.menuType;

public class CountryMilitaryLevel extends Menu {
	
	// Constructor
	public CountryMilitaryLevel(InspiredNations instance, Player playertemp, int errortemp) {
		super(instance, playertemp, errortemp);
		country = PDI.getCountryRuled();
		CM = new CountryMethods(plugin, country);
	}
	
	@Override
	public String getPromptText(ConversationContext arg0) {
		String space = tools.space();
		String main = tools.header("Manage Country. Type an option number.");
		String options = "";
		String end = tools.footer(false);
		String errmsg = ChatColor.RED + tools.errors.get(error);
		
		// Make Inputs Vector
		inputs.add("Set Level <level>");
		
		// Make Options Text
		options = options.concat(menuType.LABEL + "Current Military Level: " + menuType.VALUE + country.getMilitaryLevel() + "\n");
		options = options.concat(menuType.LABEL + "Current Military Cost: " + menuType.VALUE + CM.getMilitaryFunding() + menuType.UNIT + " " +
				country.getPluralMoney() + "\n");
		options = options.concat(menuType.LABEL + "Cost For Next Level: " + menuType.VALUE + CM.getMilitaryFunding(country.getMilitaryLevel() + 1) +
				menuType.UNIT + " " + country.getPluralMoney() + "\n");
		options = tools.addDivider(options);
		options = options.concat(menuType.VALUEDESCRI + "Military level is your country's ability to attack other countries and defend your " +
				"own. If you have a higher military level than another country, you can bypass as many protection levels as you have military levels above" +
				" them.\n");
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
			return new ManageCountry(plugin, player, 0);
		}
		String[] args = arg.split(" ");
		if (args[0].equalsIgnoreCase("say"))  {
			if(args.length > 1) {
				PMeth.SendChat(tools.formatSpace(tools.subArray(args, 1, args.length - 1)));
			}
			return new CountryMilitaryLevel(plugin, player, 0);
		}
		
		try {
			answer = Integer.decode(args[0])-1;
		}
		catch (Exception ex) {
			return new CountryMilitaryLevel(plugin, player,1);
		}
		
		if (answer > inputs.size()-1) {
			return new CountryMilitaryLevel(plugin, player, 2);
		}
		
		if(inputs.get(answer).equals("Set Level <level>")) {
			if(args.length != 2) {
				return new CountryMilitaryLevel(plugin, player,3);
			}
			else {
				try {
					int newlevel = Integer.decode(args[1]);

					BigDecimal oldtax = CM.getMilitaryFunding();
					BigDecimal newtax = CM.getMilitaryFunding(newlevel);
					BigDecimal fraction = new BigDecimal(plugin.taxTimer.getFractionLeft());
					BigDecimal difference;
					
					oldtax = oldtax.multiply(BigDecimal.ONE.subtract(fraction));
					newtax = newtax.multiply(fraction);
					
					difference = oldtax.subtract(newtax);
					
					if(difference.negate().compareTo(country.getMoney()) > 0) {
						return new CountryMilitaryLevel(plugin, player, 25);
					}
					else {
						country.setMilitaryLevel(newlevel);
						return new CountryMilitaryLevel(plugin, player, 0);
					}
				}
				catch (Exception ex) {
					return new CountryMilitaryLevel(plugin, player, 17);
				}
			}
		}
		
		return new CountryMilitaryLevel(plugin, player, 2);
	}



}
