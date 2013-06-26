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
import com.github.InspiredOne.InspiredNations.Tools.optionType;
import com.github.InspiredOne.InspiredNations.HUD.HudConversationMain;
import com.github.InspiredOne.InspiredNations.Regions.Country;

public class CountryProtectionLevel extends StringPrompt{

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
	
	Vector<String> inputs = new Vector<String>();
	
	// Constructor
	public CountryProtectionLevel(InspiredNations instance, Player playertemp, int errortemp) {
		plugin = instance;
		tools = new Tools(plugin);
		player = playertemp;
		PDI = plugin.playerdata.get(player.getName());
		country = PDI.getCountryRuled();
		playername = player.getName();
		PM = plugin.playermodes.get(playername);
		PMeth = new PlayerMethods(plugin, player);
		error = errortemp;
		CM = new CountryMethods(plugin, country);
	}
	

	@Override
	public String getPromptText(ConversationContext arg0) {
		String space = tools.space();
		String main = tools.header("Country Protection Level. Type an option number.");
		String options = "";
		String end = tools.footer(false);
		String errmsg = ChatColor.RED + tools.errors.get(error);
		
		// Make inputs vector
		inputs.add("Set Level <level>");
		
		// Make options text
		options = tools.addLine(options, "Current Protection Level: " + ChatColor.GOLD + country.getProtectionLevel(), optionType.INSTRUCTION);
		options = tools.addLine(options, "Current Military Funding: " + ChatColor.GOLD + CM.getTaxAmount() + " " +
				ChatColor.YELLOW + country.getPluralMoney(), optionType.INSTRUCTION);
		options = tools.addDivider(options);
		options = options.concat(ChatColor.GOLD + "Level 0: " + ChatColor.YELLOW + "(No protection) Any entity can claim or build on your country's land.\n" );
		options = options.concat(ChatColor.GOLD + "Level 1: " + ChatColor.YELLOW + "(Claim Protection) Country land is protected from being claimed.\n");
		options = options.concat(ChatColor.GOLD + "Level 2: " + ChatColor.YELLOW + "(Immigration Control) Players need permission to join.\n");
		options = options.concat(ChatColor.GOLD + "Level 3: " + ChatColor.YELLOW + "(Block and Interactable Protection) Only country residents can build and interact in country.\n");
		options = options.concat(ChatColor.GOLD + "Level 4: " + ChatColor.YELLOW + "(Player Protection) Players are protected from attacks while within country boundary.\n");
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
			return new CountryProtectionLevel(plugin, player, 0);
		}
		
		try {
			answer = Integer.decode(args[0])-1;
		}
		catch (Exception ex) {
			return new CountryProtectionLevel(plugin, player,1);
		}
		
		if (answer > inputs.size()-1) {
			return new CountryProtectionLevel(plugin, player, 2);
		}
		
		if(inputs.get(answer).equals("Set Level <level>")) {
			if (args.length != 2) {
				return new CountryProtectionLevel(plugin, player, 3);
			}
			else {
				try {
					int level = Integer.parseInt(args[1]);
					BigDecimal oldtax = CM.getTaxAmount();
					BigDecimal newtax = CM.getTaxAmount(level);
					BigDecimal fraction = new BigDecimal(plugin.taxTimer.getFractionLeft());
					BigDecimal difference;
					
					oldtax = oldtax.multiply(BigDecimal.ONE.subtract(fraction));
					newtax = newtax.multiply(fraction);
					
					difference = oldtax.subtract(newtax);
					
					if(difference.negate().compareTo(country.getMoney()) > 0) {
						return new CountryProtectionLevel(plugin, player, 25);
					}
					else {
						country.setProtectionLevel(level);
						return new CountryProtectionLevel(plugin, player, 0);
					}
				}
				catch (Exception ex) {
					return new CountryProtectionLevel(plugin, player, 17);
				}
			}
		}
		
		return new CountryProtectionLevel(plugin, player, 2);
	}



}
