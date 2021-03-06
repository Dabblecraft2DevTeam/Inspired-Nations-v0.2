package com.github.InspiredOne.InspiredNations.ManageTown;

import java.math.BigDecimal;
import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.TownMethods;
import com.github.InspiredOne.InspiredNations.HUD.Menu;
import com.github.InspiredOne.InspiredNations.Tools.menuType;
import com.github.InspiredOne.InspiredNations.Tools.version;

public class TownProtectionLevel extends Menu {
	
	// Constructor
	public TownProtectionLevel(InspiredNations instance, Player playertemp, int errortemp) {
		super(instance, playertemp, errortemp);
		town = PDI.getTownMayored();
		TM = new TownMethods(plugin, town);
	}
	
	@Override
	public String getPromptText(ConversationContext arg0) {
		String space = tools.space();
		String main = tools.header("Manage Town. Type an option number.");
		String options = "";
		String end = tools.footer(true);
		String errmsg = ChatColor.RED + tools.errors.get(error);
		
		// Make inputs vector
		inputs.add("Set Level <level>");
		
		// Make Options Text
		options = options.concat(menuType.LABEL + "Current Protection Level: " + menuType.VALUE + town.getProtectionLevel() + "\n");
		options = options.concat(menuType.LABEL + "Current Protection Funding: " + menuType.VALUE + (TM.getTaxAmount(true, version.NEW)).toString()) +
				menuType.UNIT + " " + town.getPluralMoney() + "\n";
		options = options.concat(menuType.LABEL + "Cost For Next Level: " + menuType.VALUE + TM.getTaxAmount(town.getProtectionLevel() + 1, town.getMilitaryLevel(), true, version.NEW)) +
				menuType.UNIT + " " + town.getPluralMoney() + "\n";
		options = tools.addDivider(options);
		options = options.concat(menuType.VALUE + "Level 0: " + menuType.VALUEDESCRI + "(No protection) Anybody can build in your town. Town Land can be" +
				" claimed by other towns.\n");
		options = options.concat(menuType.VALUE + "Level 1: " + menuType.VALUEDESCRI + "(Claim Protection) Town land cannot be claimed by other towns.\n");
		options = options.concat(menuType.VALUE + "Level 2: " + menuType.VALUEDESCRI + "(Immigration Control) Players need permission to join.\n");
		options = options.concat(menuType.VALUE + "Level 3: " + menuType.VALUEDESCRI + "(Block and Interactable Protection) Only town residents can build and interact in your town\n");
		options = options.concat(menuType.VALUE + "Level 4: " + menuType.VALUEDESCRI + "(Player Protection) Players are protected from attacks while within the town boundary.\n");
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
			return new TownProtectionLevel(plugin, player, 0);
		}
		
		try {
			answer = Integer.decode(args[0])-1;
		}
		catch (Exception ex) {
			return new TownProtectionLevel(plugin, player,1);
		}
		
		if (answer > inputs.size()-1) {
			return new TownProtectionLevel(plugin, player, 2);
		}
		
		if (inputs.get(answer).equals("Set Level <level>")) {
			if (args.length != 2) {
				return new TownProtectionLevel(plugin, player, 3);
			}
			else {
				try {
					int level = Integer.parseInt(args[1]);
					BigDecimal oldtax = TM.getTaxAmount(true, version.OLD);
					BigDecimal newtax = TM.getTaxAmount(level, town.getMilitaryLevel(), true, version.OLD);
					BigDecimal fraction = new BigDecimal(plugin.taxTimer.getFractionLeft());
					BigDecimal difference;
					
					oldtax = oldtax.multiply(fraction);
					newtax = newtax.multiply(fraction);
					
					difference = oldtax.subtract(newtax);
					
					if(difference.negate().compareTo(town.getMoney()) > 0) {
						return new TownProtectionLevel(plugin, player, 25);
					}
					else {
						town.changeProtectionLevel(level);
						return new TownProtectionLevel(plugin, player, 0);
					}
				}
				
				catch (Exception ex) {
					return new TownProtectionLevel(plugin, player, 17);
				}
			}
		}
		return null;
	}



}
