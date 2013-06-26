package com.github.InspiredOne.InspiredNations.ManageTown;

import java.math.BigDecimal;
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
import com.github.InspiredOne.InspiredNations.TownMethods;
import com.github.InspiredOne.InspiredNations.HUD.HudConversationMain;
import com.github.InspiredOne.InspiredNations.Regions.Town;

public class ChangeTownTaxRates extends StringPrompt {

	
	InspiredNations plugin;
	Tools tools;
	Player player;
	PlayerData PDI;
	PlayerModes PM;
	PlayerMethods PMeth;
	Town town;
	TownMethods TM;
	
	Vector<String> inputs = new Vector<String>();
	int error;
	
	// Constructor
	public ChangeTownTaxRates(InspiredNations instance, Player playertemp, int errortemp) {
		plugin = instance;
		player = playertemp;
		tools = new Tools(plugin);
		PDI = plugin.playerdata.get(player.getName());
		PM = plugin.playermodes.get(player.getName());
		PMeth = new PlayerMethods(plugin ,player);
		error = errortemp;
		town = PDI.getTownMayored();
		TM = new TownMethods(plugin, town);
	}
	
	@Override
	public String getPromptText(ConversationContext arg0) {
		String space = tools.space();
		String main = tools.header("Change Town Tax Rates. Type an option number.");
		String options = "";
		String end = tools.footer(false);
		String errmsg = ChatColor.RED + tools.errors.get(error);
		
		// make inputs vector
		inputs.add("Set House Tax <tax>");
		inputs.add("Set Good Business Tax <tax>");
		inputs.add("Set Service Business Tax <tax>");
		
		// make options text
		options = options.concat(ChatColor.GOLD + "" + ChatColor.BOLD + "The Town Has:\n" + ChatColor.RESET);
		options = options.concat(ChatColor.GOLD + "" + town.getMoney() + ChatColor.YELLOW + " " + town.getPluralMoney() + " in total\n");
		options = options.concat(ChatColor.DARK_RED + "" + ChatColor.BOLD + "Taxes:\n" + ChatColor.RESET);
		options = options.concat(ChatColor.RED + "House Tax Rate: " + ChatColor.GOLD + town.getHouseTax() + ChatColor.YELLOW + "%\n");
		options = options.concat(ChatColor.RED + "Good Business Tax Rate: " + ChatColor.GOLD + town.getGoodBusinessTax() + ChatColor.YELLOW + "%\n");
		options = options.concat(ChatColor.RED + "Service Business Tax Rate: " + ChatColor.GOLD + town.getServiceBusinessTax() + ChatColor.YELLOW + "%\n");
		options = tools.addDivider(options);
		options = options.concat(ChatColor.RED + "Expenditures: " + ChatColor.GOLD + TM.getTaxAmount() + ChatColor.YELLOW + " " + town.getPluralMoney() + "\n");
		options = options.concat(ChatColor.RED + "Revenue: " + ChatColor.GOLD + town.getRevenue() + " " + ChatColor.YELLOW + town.getPluralMoney() + "\n");
		options = options.concat(ChatColor.RED + "Difference: " + ChatColor.GOLD + town.getRevenue().subtract(TM.getTaxAmount()).toString() + ChatColor.YELLOW + " "
		+ town.getPluralMoney() + "\n");
		if(town.getLoan().compareTo(BigDecimal.ZERO) != 0) {
			options = options.concat(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Loans Due:\n" + ChatColor.RESET);
			options = options.concat(ChatColor.GOLD + town.getLoan().toString() + " / " + town.getMaxLoan() 
					+ " " + ChatColor.YELLOW + town.getPluralMoney() + "\n");
		}
		
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
			return new ManageTownFinances(plugin, player, 0);
		}
		String[] args = arg.split(" ");
		if (args[0].equalsIgnoreCase("say"))  {
			if(args.length > 1) {
				PMeth.SendChat(tools.formatSpace(tools.subArray(args, 1, args.length - 1)));
			}
			return new ChangeTownTaxRates(plugin, player, 0);
		}
		
		try {
			answer = Integer.decode(args[0])-1;
		}
		catch (Exception ex) {
			return new ChangeTownTaxRates(plugin, player,1);
		}
		
		if (answer > inputs.size()-1) {
			return new ChangeTownTaxRates(plugin, player, 2);
		}
		
		if (inputs.get(answer).equals("Set House Tax <tax>")) {
			if (args.length != 2) {
				return new ChangeTownTaxRates(plugin, player, 3);
			}
			else {
				try{
					double newrate = Math.abs(Double.parseDouble(args[1]));
					town.setHouseTax(newrate);
					return new ChangeTownTaxRates(plugin, player, 0);
					
				}
				catch(Exception ex){
					return new ChangeTownTaxRates(plugin, player, 17);
				}
			}
		}
		else if(inputs.get(answer).equals("Set Good Business Tax <tax>")) {
			if (args.length != 2) {
				return new ChangeTownTaxRates(plugin, player, 3);
			}
			else {
				try{
					double newrate = Math.abs(Double.parseDouble(args[1]));
					town.setGoodBusinessTax(newrate);
					return new ChangeTownTaxRates(plugin, player, 0);
					
				}
				catch(Exception ex){
					return new ChangeTownTaxRates(plugin, player, 17);
				}
			}
		}
		else if(inputs.get(answer).equals("Set Service Business Tax <tax>")) {
			if (args.length != 2) {
				return new ChangeTownTaxRates(plugin, player, 3);
			}
			else {
				try{
					double newrate = Math.abs(Double.parseDouble(args[1]));
					town.setServiceBusinessTax(newrate);
					return new ChangeTownTaxRates(plugin, player, 0);
					
				}
				catch(Exception ex){
					return new ChangeTownTaxRates(plugin, player, 17);
				}
			}
		}
		return new ChangeTownTaxRates(plugin, player, 2);
	}

	

}
