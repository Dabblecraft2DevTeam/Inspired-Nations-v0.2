package com.github.InspiredOne.InspiredNations.ManageTown;

import java.math.BigDecimal;
import java.util.Vector;

import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.TownMethods;
import com.github.InspiredOne.InspiredNations.HUD.Menu;

public class ManageTownFinances extends Menu {
	//TODO Change chat colors to menuType
	// Constructor
	public ManageTownFinances(InspiredNations instance, Player playertemp, int errortemp) {
		super(instance, playertemp, errortemp);
		town = PDI.getTownMayored();
		TM = new TownMethods(plugin, town);
	}
	
	public ManageTownFinances(InspiredNations instance, Player playertemp, int errortemp, Vector<String> namestemp) {
		super(instance, playertemp, errortemp, namestemp);
		town = PDI.getTownMayored();
		TM = new TownMethods(plugin, town);
	}
	
	@Override
	public String getPromptText(ConversationContext arg0) {
		String space = tools.space();
		String main = tools.header("Manage Town. Type an option number.");
		String options = "";
		String end = tools.footer(false);
		String errmsg = ChatColor.RED + tools.errors.get(error) + names;
		
		// Make Inputs Vector
		if(town.getLoan().compareTo(BigDecimal.ZERO) == 0){
			inputs.add("Pay <player> <amount>");
			inputs.add("Pay Country <country> <amount>");
			inputs.add("Pay Town <town's country> / <country> <amount>");
		}
		else {
			inputs.add("*Pay <player> <amount> " + ChatColor.GRAY + "Must pay back loans");
			inputs.add("*Pay Country <country> <amount> "+ ChatColor.GRAY + "Must pay back loans");
			inputs.add("*Pay Town <town's country> <country> <amount> " + ChatColor.GRAY + "Must pay back loans");
		}
		if(!PDI.getIsInLocalBank() /*&& PDI.getCountryIn().getName().equals(PDI.getCountryResides().getName()*/) {
			inputs.add("Take Out Loan <amount> " + ChatColor.GRAY + "Max: " + town.getMaxLoan() + " "+ town.getPluralMoney());
			if(town.getLoan().compareTo(BigDecimal.ZERO) != 0) {
				inputs.add("Repay Loan <amount>");
			}
			else {
				inputs.add("*Repay Loan <amount> " + ChatColor.GRAY + "No loans to pay back");
			}
		}
		else {
			inputs.add("*Take Out Loan <amount> " + ChatColor.GRAY + "Must be within a bank. Max: " + town.getMaxLoan() + " "+ town.getPluralMoney());
			inputs.add("*Repay Loan <amount> " + ChatColor.GRAY + "Must be within a bank");
		}

		inputs.add("Change Tax Rates");
		
		// make options text
		options = options.concat(ChatColor.GOLD + "" + ChatColor.BOLD + "The Town Has:\n" + ChatColor.RESET);
		options = options.concat(ChatColor.GOLD + "" + town.getMoney() + ChatColor.YELLOW + " " + town.getPluralMoney() + " in total\n");
		options = options.concat(ChatColor.AQUA + "" + ChatColor.BOLD + "Taxes:\n" + ChatColor.RESET);
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
			return new ManageTown(plugin, player, 0);
		}
		String[] args = arg.split(" ");
		if (args[0].equalsIgnoreCase("say"))  {
			if(args.length > 1) {
				PMeth.SendChat(tools.formatSpace(tools.subArray(args, 1, args.length - 1)));
			}
			return new ManageTownFinances(plugin, player, 0);
		}
		
		try {
			answer = Integer.decode(args[0])-1;
		}
		catch (Exception ex) {
			return new ManageTownFinances(plugin, player,1);
		}
		
		if (answer > inputs.size()-1) {
			return new ManageTownFinances(plugin, player, 2);
		}
		
		// Take Out Loan <amount>
				else if(inputs.get(answer).equals("Take Out Loan <amount> " + ChatColor.GRAY + "Max: " + town.getMaxLoan() + " "+ town.getPluralMoney())) {
					if (args.length != 2) {
						return new ManageTownFinances(plugin, player, 3);
					}
					else {
						try {
							BigDecimal loan = (new BigDecimal(args[1])).abs();
							if(loan.compareTo(town.getMaxLoan().subtract(town.getLoan())) > 0) {
								return new ManageTownFinances(plugin, player, 10);
							}
							town.addLoan(loan);
							town.addMoney(loan);
							return new ManageTownFinances(plugin, player, 0);
						}
						catch(Exception ex) {
							return new ManageTownFinances(plugin, player, 11);
						}
					}
				}
				
				// Repay Loan <amount>
				else if(inputs.get(answer).equals("Repay Loan <amount>")) {
					if (args.length != 2) {
						return new ManageTownFinances(plugin, player, 3);
					}
					else {
						try {
							BigDecimal loan = (new BigDecimal(args[1])).abs();
							if(loan.compareTo(town.getLoan()) > 0) {
								return new ManageTownFinances(plugin, player, 12);
							}
							town.removeLoan(loan);
							town.removeMoney(loan);
							return new ManageTownFinances(plugin, player, 0);
						}
						catch(Exception ex) {
							return new ManageTownFinances(plugin, player, 11);
						}
					}
				}
				
				// Pay <player> <amount>
				else if(inputs.get(answer).equals("Pay <player> <amount>")){
					if(args.length != 3) {
						return new ManageTownFinances(plugin, player, 3);
					}
					else {
						Vector<String> names = tools.findPerson(args[1]);
						if(names.size() == 1) {
							try {
								BigDecimal payment = (new BigDecimal(args[2])).abs();
								if(payment.compareTo(town.getMoney()) > 0) {
									return new ManageTownFinances(plugin, player, 22);
								}
								else {
									town.transferMoney(payment, names.get(0));
									return new ManageTownFinances(plugin, player, 0);
								}
							}
							catch (Exception ex) {
								return new ManageTownFinances(plugin, player, 6);
							}
						}
						else if(names.size() > 1) {
							return new ManageTownFinances(plugin, player, 4, names);
						}
						else return new ManageTownFinances(plugin, player, 5);
					}
				}
				
				// Pay Country <country> <amount>
				else if(inputs.get(answer).equals("Pay Country <country> <amount>")) {
					if(args.length < 3) {
						return new ManageTownFinances(plugin, player, 3);
					}
					else {
						Vector<String> countries = tools.findCountry(tools.formatSpace(tools.subArray(args, 1, args.length - 2)));

						if (countries.size() == 1) {
							try {
								BigDecimal payment = (new BigDecimal(args[args.length - 1])).abs();
								if(payment.compareTo(town.getMoney()) > 0) {
									return new ManageTownFinances(plugin, player, 22);
								}
								else {
									town.transferMoneyToCountry(payment, countries.get(0));
									return new ManageTownFinances(plugin, player, 0);
								}
							}
							catch (Exception ex) {
								return new ManageTownFinances(plugin, player, 6);
							}
						}
						else if(countries.size() > 1) {
							return new ManageTownFinances(plugin, player, 4, countries);
						}
						else return new ManageTownFinances(plugin, player, 20);
					}
				}
				
				// Pay Town <town's country> <town> <amount>
				else if (inputs.get(answer).equals("Pay Town <town's country> / <town> <amount>")){
					if(args.length < 3) {
						return new ManageTownFinances(plugin, player, 3);
					}
					else {
						String temp = tools.formatSpace(tools.subArray(args, 1, args.length-2));
						String[] inaddress = temp.split("/");
						if (inaddress.length!=2) {
							return new ManageTownFinances(plugin, player, 23);
						}
						Vector<String> towns = tools.findTown(inaddress[0], inaddress[1]);
						if (towns.size() == 1) {
							try {
								BigDecimal payment = (new BigDecimal(args[args.length - 1])).abs();
								if(payment.compareTo(town.getMoney()) > 0) {
									return new ManageTownFinances(plugin, player, 22);
								}
								else {
									String[] address = towns.get(0).split("/");
									town.transferMoneyToTown(payment, address[0], address[1]);
									return new ManageTownFinances(plugin, player, 0);
								}
							}
							catch (Exception ex) {
								return new ManageTownFinances(plugin, player, 6);
							}
						}
						else if(towns.size() > 1) {
							return new ManageTownFinances(plugin, player, 4, towns);
						}
						else return new ManageTownFinances(plugin, player, 21);
					}
				}
				else if (inputs.get(answer).equals("Change Tax Rates")) {
					return new ChangeTownTaxRates(plugin, player, 0);
				}
		
		return new ManageTownFinances(plugin, player, 2);
	}

	
}
