package com.github.InspiredOne.InspiredNations.ManageTown;

import java.math.BigDecimal;

import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.HUD.Menu;
import com.github.InspiredOne.InspiredNations.HUD.ManageCountry.CountryMilitaryLevel;
import com.github.InspiredOne.InspiredNations.HUD.ManageCountry.ManageCountry;
import com.github.InspiredOne.InspiredNations.Tools.menuType;
import com.github.InspiredOne.InspiredNations.Tools.version;
import com.github.InspiredOne.InspiredNations.TownMethods;

public class TownMilitaryLevel extends Menu {

	public TownMilitaryLevel(InspiredNations instance, Player playertemp, int errortemp) {
		super(instance, playertemp, errortemp);
		town = PDI.getTownMayored();
		TM = new TownMethods(plugin, town);
	}
	
	@Override
	public String getPromptText(ConversationContext arg0) {
		String space = tools.space();
		String main = tools.header("Town Military Level. Type an option number.");
		String options = "";
		String end = tools.footer(false);
		String errmsg = ChatColor.RED + tools.errors.get(error);
		
		// Make Inputs Vector
		inputs.add("Set Level <level>");
		
		// Make Options Text
		options = options.concat(menuType.LABEL + "Current Military Level: " + menuType.VALUE + town.getMilitaryLevel() + "\n");
		options = options.concat(menuType.LABEL + "Current Military Cost: " + menuType.VALUE + TM.getMilitaryFunding(true, version.NEW) + menuType.UNIT + " " +
				town.getPluralMoney() + "\n");
		options = options.concat(menuType.LABEL + "Cost For Next Level: " + menuType.VALUE + TM.getMilitaryFunding(town.getMilitaryLevel() + 1, true, version.NEW) +
				menuType.UNIT + " " + town.getPluralMoney() + "\n");
		options = tools.addDivider(options);
		options = options.concat(menuType.VALUEDESCRI + "Military level is your town's ability to attack other towns and defend your " +
				"own. If you have a higher military level than another town, you can bypass as many protection levels as you have military levels above" +
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
			return new ManageTown(plugin, player, 0);
		}
		String[] args = arg.split(" ");
		if (args[0].equalsIgnoreCase("say"))  {
			if(args.length > 1) {
				PMeth.SendChat(tools.formatSpace(tools.subArray(args, 1, args.length - 1)));
			}
			return new TownMilitaryLevel(plugin, player, 0);
		}
		
		try {
			answer = Integer.decode(args[0])-1;
		}
		catch (Exception ex) {
			return new TownMilitaryLevel(plugin, player,1);
		}
		
		if (answer > inputs.size()-1) {
			return new TownMilitaryLevel(plugin, player, 2);
		}
		
		if(inputs.get(answer).equals("Set Level <level>")) {
			if(args.length != 2) {
				return new TownMilitaryLevel(plugin, player,3);
			}
			else {
				try {
					int newlevel = Integer.decode(args[1]);

					BigDecimal oldtax = TM.getMilitaryFunding(true, version.OLD);
					BigDecimal newtax = TM.getMilitaryFunding(newlevel, true, version.OLD);
					BigDecimal fraction = new BigDecimal(plugin.taxTimer.getFractionLeft());
					BigDecimal difference;
					
					oldtax = oldtax.multiply(fraction);
					newtax = newtax.multiply(fraction);
					
					difference = oldtax.subtract(newtax);
					
					if(difference.compareTo(BigDecimal.ZERO) > 0) {
						town.changeMilitaryLevel(newlevel);
						return new TownMilitaryLevel(plugin, player, 0);
					}
					else {
						if(newtax.compareTo(town.getMoney()) > 0) {
							return new TownMilitaryLevel(plugin, player, 25);
						}
						else {
							town.changeMilitaryLevel(newlevel);
							return new TownMilitaryLevel(plugin, player, 0);
						}
					}
				}
				catch (Exception ex) {
					return new TownMilitaryLevel(plugin, player, 17);
				}
			}
		}
		
		return new TownMilitaryLevel(plugin, player, 2);
	}



}
