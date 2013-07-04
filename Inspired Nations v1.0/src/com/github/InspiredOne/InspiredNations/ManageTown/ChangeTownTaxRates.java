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

public class ChangeTownTaxRates extends Menu {
	
	// Constructor
	public ChangeTownTaxRates(InspiredNations instance, Player playertemp, int errortemp) {
		super(instance, playertemp, errortemp);
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
		options = options.concat(menuType.SUBHEADER +  "The Town Has:\n" + ChatColor.RESET);
		options = options.concat(menuType.VALUE + "" + town.getMoney() + menuType.UNIT + " " + town.getPluralMoney() + " in total\n");
		options = options.concat(menuType.SUBHEADER + "Taxes:\n" + ChatColor.RESET);
		options = options.concat(menuType.LABEL + "House Tax Rate: " + menuType.VALUE+ town.getHouseTax() + menuType.UNIT + town.getPluralMoney() + "%\n");
		options = options.concat(menuType.LABEL + "Good Business Tax Rate: " + menuType.VALUE + town.getGoodBusinessTax() + menuType.UNIT + town.getPluralMoney() + "%\n");
		options = options.concat(menuType.LABEL + "Service Business Tax Rate: " + menuType.VALUE + town.getServiceBusinessTax() + menuType.UNIT + town.getPluralMoney() + "%\n");
		options = tools.addDivider(options);
		options = options.concat(menuType.LABEL + "Expenditures: " + menuType.VALUE + TM.getTaxAmount(true, version.NEW) + menuType.UNIT + " " + town.getPluralMoney() + "\n");
		options = options.concat(menuType.LABEL + "Revenue: " + menuType.VALUE + town.getRevenue() + " " + menuType.UNIT + town.getPluralMoney() + "\n");
		options = options.concat(menuType.LABEL + "Difference: " + menuType.VALUE + town.getRevenue().subtract(TM.getTaxAmount(true,version.NEW)).toString() + menuType.UNIT + " "
		+ town.getPluralMoney() + "\n");
		if(town.getLoan().compareTo(BigDecimal.ZERO) != 0) {
			options = options.concat(menuType.SUBHEADER + "Loans Due:\n" + ChatColor.RESET);
			options = options.concat(menuType.VALUE+ town.getLoan().toString() + " / " + town.getMaxLoan() 
					+ " " + menuType.UNIT + town.getPluralMoney() + "\n");
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
