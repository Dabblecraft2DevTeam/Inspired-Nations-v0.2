package com.github.InspiredOne.InspiredNations.HUD.ManageCountry;

import java.math.BigDecimal;
import java.util.Vector;

import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;

import com.github.InspiredOne.InspiredNations.CountryMethods;
import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.Tools.menuType;
import com.github.InspiredOne.InspiredNations.HUD.Menu;

public class ManageEconomy extends Menu {
	// Constructor
	public ManageEconomy(InspiredNations instance, Player playertemp, int errortemp) {
		super(instance, playertemp, errortemp);
		country = PDI.getCountryRuled();
		CM = new CountryMethods(plugin, country);
	}
	
	public ManageEconomy(InspiredNations instance, Player playertemp, int errortemp, Vector<String> namestemp) {
		super(instance, playertemp, errortemp, namestemp);
		country = PDI.getCountryRuled();
		CM = new CountryMethods(plugin, country);
	}

	@Override
	public String getPromptText(ConversationContext arg0) {
		String space = tools.space();
		String main = tools.header("Manage Country. Type an option number.");
		String options = "";
		String end = tools.footer(false);
		String errmsg = ChatColor.RED + tools.errors.get(error) + names;

		// Make Inputs vector
		if(country.getLoanAmount().compareTo(BigDecimal.ZERO) == 0){
			inputs.add("Pay <player> <amount>");
			inputs.add("Pay Country <country> <amount>");
			inputs.add("Pay Town <town's country> <town> <amount>");
		}
		else {
			inputs.add("*Pay <player> <amount> " + menuType.OPTIONDESCRIP + "Must pay back loans");
			inputs.add("*Pay Country <country> <amount> "+ menuType.OPTIONDESCRIP + "Must pay back loans");
			inputs.add("*Pay Town <town's country> / <town> <amount> " + ChatColor.GRAY + "Must pay back loans");
		}
		if(!PDI.getIsInLocalBank() /*&& PDI.getCountryIn().getName().equals(PDI.getCountryResides().getName()*/) {
			inputs.add("Take Out Loan <amount> " + menuType.OPTIONDESCRIP + "Max: " + country.getMaxLoan() + " "+ country.getPluralMoney());
			if(country.getLoanAmount().compareTo(BigDecimal.ZERO) != 0) {
				inputs.add("Repay Loan <amount>");
			}
			else {
				inputs.add("*Repay Loan <amount> " + menuType.OPTIONDESCRIP + "No loans to pay back");
			}
		}
		else {
			inputs.add("*Take Out Loan <amount> " + menuType.OPTIONDESCRIP + "Must be within a bank. Max: " + country.getMaxLoan() + " "+ country.getPluralMoney());
			inputs.add("*Repay Loan <amount> " + menuType.OPTIONDESCRIP + "Must be within a bank");
		}

		inputs.add("Change Tax Rate <rate>");
		inputs.add("Rename Money <singular> <plural>");

		// Make options text
		options = options.concat(menuType.SUBHEADER + "The Country Has:\n" + ChatColor.RESET);
		options = options.concat(menuType.VALUE + "" + country.getMoney() + menuType.UNIT + " " + country.getPluralMoney() + " in total\n");
		options = options.concat(menuType.SUBHEADER + "Taxes:\n" + ChatColor.RESET);
		options = options.concat(menuType.LABEL + "Tax Rate: " + menuType.VALUE + country.getTaxRate() + menuType.UNIT+ "%\n");
		options = options.concat(menuType.LABEL + "Expenditures: " + menuType.VALUE + CM.getTaxAmount() + menuType.UNIT + " " + country.getPluralMoney() + "\n");
		options = options.concat(menuType.LABEL + "Revenue: " + menuType.VALUE + CM.getRevenue() + " " + menuType.UNIT + country.getPluralMoney() + "\n");
		options = options.concat(menuType.LABEL + "Difference: " + menuType.VALUE + CM.getRevenue().subtract(CM.getTaxAmount()).toString() + menuType.UNIT + " "
		+ country.getPluralMoney() + "\n");
		if(country.getLoanAmount().compareTo(BigDecimal.ZERO) != 0) {
			options = options.concat(menuType.SUBHEADER + "Loans Due:\n" + ChatColor.RESET);
			options = options.concat(menuType.VALUE + country.getLoanAmount().toString() + " / " + country.getMaxLoan() 
					+ " " + menuType.UNIT + country.getPluralMoney() + "\n");
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
			return new ManageCountry(plugin, player, 0);
		}
		String[] args = arg.split(" ");
		if (args[0].equalsIgnoreCase("say"))  {
			if(args.length > 1) {
				PMeth.SendChat(tools.formatSpace(tools.subArray(args, 1, args.length - 1)));
			}
			return new ManageEconomy(plugin, player, 0);
		}
		
		try {
			answer = Integer.decode(args[0])-1;
		}
		catch (Exception ex) {
			return new ManageEconomy(plugin, player,1);
		}
		
		if (answer > inputs.size()-1) {
			return new ManageEconomy(plugin, player, 2);
		}
		
		// Change Tax Rate <rate>
		if(inputs.get(answer).equals("Change Tax Rate <rate>")) {
			if (args.length != 2) {
				return new ManageEconomy(plugin, player, 3);
			}
			else {
				try{
					double newrate = Math.abs(Double.parseDouble(args[1]));
					country.setTaxRate(newrate);
					return new ManageEconomy(plugin, player, 0);
					
				}
				catch(Exception ex){
					return new ManageEconomy(plugin, player, 17);
				}
			}
		}
		
		// Take Out Loan <amount>
		else if(inputs.get(answer).equals("Take Out Loan <amount> " + menuType.OPTIONDESCRIP + "Max: " + country.getMaxLoan() + " "+ country.getPluralMoney())) {
			if (args.length != 2) {
				return new ManageEconomy(plugin, player, 3);
			}
			else {
				try {
					BigDecimal loan = (new BigDecimal(args[1])).abs();
					if(loan.compareTo(country.getMaxLoan().subtract(country.getLoanAmount())) > 0) {
						return new ManageEconomy(plugin, player, 10);
					}
					country.addLoan(loan);
					country.addMoney(loan);
					return new ManageEconomy(plugin, player, 0);
				}
				catch(Exception ex) {
					return new ManageEconomy(plugin, player, 11);
				}
			}
		}
		
		// Repay Loan <amount>
		else if(inputs.get(answer).equals("Repay Loan <amount>")) {
			if (args.length != 2) {
				return new ManageEconomy(plugin, player, 3);
			}
			else {
				try {
					BigDecimal loan = (new BigDecimal(args[1])).abs();
					if(loan.compareTo(country.getLoanAmount()) > 0) {
						return new ManageEconomy(plugin, player, 12);
					}
					country.removeLoan(loan);
					country.removeMoney(loan);
					return new ManageEconomy(plugin, player, 0);
				}
				catch(Exception ex) {
					return new ManageEconomy(plugin, player, 11);
				}
			}
		}
		
		// Pay <player> <amount>
		else if(inputs.get(answer).equals("Pay <player> <amount>")){
			if(args.length != 3) {
				return new ManageEconomy(plugin, player, 3);
			}
			else {
				Vector<String> names = tools.findPerson(args[1]);
				if(names.size() == 1) {
					try {
						BigDecimal payment = (new BigDecimal(args[2])).abs();
						if(payment.compareTo(country.getMoney()) > 0) {
							return new ManageEconomy(plugin, player, 22);
						}
						else {
							country.transferMoney(payment, names.get(0));
							return new ManageEconomy(plugin, player, 0);
						}
					}
					catch (Exception ex) {
						return new ManageEconomy(plugin, player, 6);
					}
				}
				else if(names.size() > 1) {
					return new ManageEconomy(plugin, player, 4, names);
				}
				else return new ManageEconomy(plugin, player, 5);
			}
		}
		
		// Pay Country <country> <amount>
		else if(inputs.get(answer).equals("Pay Country <country> <amount>")) {
			if(args.length < 3) {
				return new ManageEconomy(plugin, player, 3);
			}
			else {
				Vector<String> countries = tools.findCountry(tools.formatSpace(tools.subArray(args, 1, args.length - 2)));

				if (countries.size() == 1) {
					try {
						BigDecimal payment = (new BigDecimal(args[args.length - 1])).abs();
						if(payment.compareTo(country.getMoney()) > 0) {
							return new ManageEconomy(plugin, player, 22);
						}
						else {
							country.transferMoneyToCountry(payment, countries.get(0));
							return new ManageEconomy(plugin, player, 0);
						}
					}
					catch (Exception ex) {
						return new ManageEconomy(plugin, player, 6);
					}
				}
				else if(countries.size() > 1) {
					return new ManageEconomy(plugin, player, 4, countries);
				}
				else return new ManageEconomy(plugin, player, 20);
			}
		}
		
		// Pay Town <town's country> <town> <amount>
		else if (inputs.get(answer).equals("Pay Town <town's country> / <town> <amount>")){
			if(args.length < 3) {
				return new ManageEconomy(plugin, player, 3);
			}
			else {
				String temp = tools.formatSpace(tools.subArray(args, 1, args.length-2));
				String[] inaddress = temp.split("/");
				if (inaddress.length!=2) {
					return new ManageEconomy(plugin, player, 23);
				}
				Vector<String> towns = tools.findTown(inaddress[0], inaddress[1], true);
				if (towns.size() == 1) {
					try {
						BigDecimal payment = (new BigDecimal(args[args.length - 1])).abs();
						if(payment.compareTo(country.getMoney()) > 0) {
							return new ManageEconomy(plugin, player, 22);
						}
						else {
							String[] address = towns.get(0).split("/");
							country.transferMoneyToTown(payment, address[0], address[1]);
							return new ManageEconomy(plugin, player, 0);
						}
					}
					catch (Exception ex) {
						return new ManageEconomy(plugin, player, 6);
					}
				}
				else if(towns.size() > 1) {
					return new ManageEconomy(plugin, player, 4, towns);
				}
				else return new ManageEconomy(plugin, player, 21);
			}
		}
		
		// Rename Money <singular> <plural>
		else if (inputs.get(answer).equals("Rename Money <singular> <plural>")) {
			
			String plural = country.getPluralMoney();
			String single = country.getSingularMoney();
			country.setPluralMoney("");
			country.setSingularMoney("");
			
			if(args.length != 3) {
				return new ManageEconomy(plugin, player, 3);
			}
			else if(!tools.moneyUnique(args[1]) || !tools.moneyUnique(args[2])) {
				country.setPluralMoney(plural);
				country.setSingularMoney(single);
				return new ManageEconomy(plugin, player, 16);
			}
			else {
				country.setPluralMoney(args[2]);
				country.setSingularMoney(args[1]);
				return new ManageEconomy(plugin, player, 0);
			}
		}
		return new ManageEconomy(plugin, player, 2);
	}




}
