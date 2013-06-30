package com.github.InspiredOne.InspiredNations.HUD;

import java.math.BigDecimal;
import java.util.Vector;

import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.HUD.ManageCountry.ManageEconomy;
import com.github.InspiredOne.InspiredNations.Tools.menuType;

public class ManageMoney extends Menu {

	// Constructor
	public ManageMoney(InspiredNations instance, Player playertemp, int errortemp) {
		super(instance, playertemp, errortemp);
	}
	
	public ManageMoney(InspiredNations instance, Player playertemp, int errortemp, Vector<String> namestemp) {
		super(instance, playertemp, errortemp, namestemp);

	}
	
	@Override
	public String getPromptText(ConversationContext arg0) {
		String space = tools.space();
		String main = tools.header("Manage Money. Type an option number.");
		String options = "";
		String end = tools.footer(false);
		String errmsg = ChatColor.RED + tools.errors.get(error) + names;
		
		// Make the inputs vector
		inputs.add("Pay <player> <amount>");
		inputs.add("Pay Country <country> <amount>");
		inputs.add("Pay Town <town's country> / <town> <amount>");
		
		if ((!PDI.getIsInLocalBank()) /* && PDI.getCountryIn().getName().equals(PDI.getCountryResides().getName())*/) {
			inputs.add("Deposit <amount>");
			inputs.add("Withdraw <amount>");
			inputs.add("Take Out Loan <amount> " + menuType.OPTIONDESCRIP + "Max: " + PDI.getMaxLoan() + " " + PDI.getPluralMoney());
			if (PDI.getLoanAmount().compareTo(new BigDecimal(0)) != 0) {
				inputs.add("Repay Loan <amount>");
			}
			else {
				inputs.add("*Repay Loan <amount> " + ChatColor.GRAY + "No loans to pay back");
			}
		}
		else {
			inputs.add("*Deposit <amount> " + ChatColor.GRAY + "Must be within a bank" );
			inputs.add("*Withdraw <amount> " + ChatColor.GRAY + "Must be within a bank");
			inputs.add("*Take Out Loan <amount> " + ChatColor.GRAY + "Must be within a bank. Max: " + PDI.getMaxLoan() + " " + PDI.getPluralMoney());
			inputs.add("*Repay Loan <amount> " + ChatColor.GRAY + "Must be within a bank");
		}
		
		// Make the options text
		options = options.concat(menuType.SUBHEADER + "You have:" + ChatColor.RESET + "\n");
		options = options.concat(menuType.VALUE + "" + PDI.getMoney() + menuType.UNIT + " " + PDI.getPluralMoney() + "\n");
		options = options.concat(menuType.VALUE + "" + PDI.getMoneyOnHand() + menuType.UNIT + " " + PDI.getPluralMoney() + " on hand.\n");
		options = options.concat(menuType.VALUE + "" + PDI.getMoneyInBank() + menuType.UNIT + " " + PDI.getPluralMoney() + " in the bank.\n");
		if (PDI.isHouseOwner() || PDI.isGoodBusinessOwner() || PDI.isServiceBusinessOwner()) {
			options = options.concat(menuType.SUBHEADER + "Taxes:" + ChatColor.RESET + "\n");
			options = options.concat(menuType.LABEL +"Total: " + menuType.VALUE + tools.cut(PMeth.taxAmount()) + menuType.UNIT + " " + PDI.getPluralMoney() + " per tax cycle.\n" );
		}
		if (PDI.isHouseOwner()) {
			options = options.concat(menuType.LABEL + "Residential: " + menuType.VALUE + tools.cut(PMeth.houseTax()) + menuType.UNIT + " " + PDI.getPluralMoney() + " per tax cycle.\n");
		}
		if (PDI.isGoodBusinessOwner()) {
			options = options.concat(menuType.LABEL + "Commercial Goods: " + menuType.VALUE + tools.cut(PMeth.goodBusinessTax()) + menuType.UNIT + " " + PDI.getPluralMoney() + " per tax cycle.\n");
		}
		if (PDI.isServiceBusinessOwner()) {
			options = options.concat(menuType.LABEL + "Commercial Services: "  + menuType.VALUE + tools.cut(PMeth.serviceBusinessTax()) + menuType.UNIT + " " + PDI.getPluralMoney() + " per tax cycle.\n");
		}
		if (PDI.getLoanAmount().compareTo(new BigDecimal(0)) != 0) {
			options = options.concat(menuType.SUBHEADER + "Loans due:" + ChatColor.RESET + "\n");
			options = options.concat(menuType.VALUE + "" + PDI.getLoanAmount() + " / " + PDI.getMaxLoan() + " " + menuType.UNIT + PDI.getPluralMoney()+"\n");
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
			return new HudConversationMain(plugin, player, 0);
		}
		String[] args = arg.split(" ");
		if (args[0].equalsIgnoreCase("say"))  {
			if(args.length > 1) {
				PMeth.SendChat(tools.formatSpace(tools.subArray(args, 1, args.length - 1)));
			}
			return new ManageMoney(plugin, player, 0);
		}
		try {
			answer = Integer.decode(args[0])-1;
		}
		catch (Exception ex) {
			return new ManageMoney(plugin, player,1);
		}
		
		if (answer > inputs.size()-1) {
			return new ManageMoney(plugin, player, 2);
		}
		
		// Pay <player> <amount>
		if(inputs.get(answer).equals("Pay <player> <amount>")) {
			if(args.length != 3) {
				return new ManageMoney(plugin, player, 3);
			}
			else {
				Vector<String> names = tools.findPerson(args[1]);
				if(names.size() == 1) {
					try {
						BigDecimal payment = (new BigDecimal(args[2])).abs();
						if(payment.compareTo(PDI.getMoneyOnHand()) > 0) {
							return new ManageMoney(plugin, player, 13);
						}
						else {
							PDI.transferMoney(payment, names.get(0));
							return new ManageMoney(plugin, player, 0);
						}
					}
					catch (Exception ex) {
						return new ManageMoney(plugin, player, 6);
					}
				}
				else if(names.size() > 1) {
					return new ManageMoney(plugin, player, 4, names);
				}
				else return new ManageMoney(plugin, player, 5);
			}
		}
		
		// Pay Country <country> <amount>
		if(inputs.get(answer).equals("Pay Country <country> <amount>")) {
			if(args.length < 3) {
				return new ManageMoney(plugin, player, 3);
			}
			else {
				Vector<String> countries = tools.findCountry(tools.formatSpace(tools.subArray(args, 1, args.length - 2)));
				if (countries.size() == 1) {
					try {
						BigDecimal payment = (new BigDecimal(args[args.length - 1])).abs();
						if(payment.compareTo(PDI.getMoneyOnHand()) > 0) {
							return new ManageMoney(plugin, player, 13);
						}
						else {
							PDI.transferMoneyToCountry(payment, countries.get(0));
							return new ManageMoney(plugin, player, 0);
						}
					}
					catch (Exception ex) {
						return new ManageMoney(plugin, player, 6);
					}
				}
				else if(countries.size() > 1) {
					return new ManageMoney(plugin, player, 4, countries);
				}
				else return new ManageMoney(plugin, player, 20);
			}
		}
		
		// Pay Town <country> <town> <amount>
		if(inputs.get(answer).equals("Pay Town <town's country> / <town> <amount>")) {
			if(args.length < 4) {
				return new ManageMoney(plugin, player, 3);
			}
			else {
				String temp = tools.formatSpace(tools.subArray(args, 1, args.length-2));
				String[] inaddress = temp.split("/");
				if (inaddress.length!=2) {
					return new ManageEconomy(plugin, player, 23);
				}
				Vector<String> towns = tools.findTown(inaddress[0].trim(), inaddress[1].trim(), true);
				if (towns.size() == 1) {
					try {
						BigDecimal payment = (new BigDecimal(args[args.length - 1])).abs();
						if(payment.compareTo(PDI.getMoneyOnHand()) > 0) {
							return new ManageMoney(plugin, player, 13);
						}
						else {
							String[] address = towns.get(0).split("/");
							PDI.transferMoneyToTown(payment, address[0].trim(), address[1].trim());
							return new ManageMoney(plugin, player, 0);
						}
					}
					catch (Exception ex) {
						return new ManageMoney(plugin, player, 6);
					}
				}
				else if(towns.size() > 1) {
					return new ManageMoney(plugin, player, 4, towns);
				}
				else return new ManageMoney(plugin, player, 21);
			}
		}
		
		// Deposit <amount>
		if(inputs.get(answer).equals("Deposit <amount>")) {
			if (args.length != 2) {
				return new ManageMoney(plugin, player, 3);
			}
			else {
				try {
					BigDecimal deposit = (new BigDecimal(args[1])).abs();
					if(deposit.compareTo(PDI.getMoneyOnHand()) > 0) {
						return new ManageMoney(plugin, player, 8);
					}
					PDI.transferMoneyToBank(deposit);
					return new ManageMoney(plugin, player, 0);
				}
				catch(Exception ex) {
					return new ManageMoney(plugin, player, 7);
				}
			}
		}
		
		// Withdraw <amount>
		if(inputs.get(answer).equals("Withdraw <amount>")) {
			if (args.length != 2) {
				return new ManageMoney(plugin, player, 3);
			}
			else {
				try {
					BigDecimal withdrawl = (new BigDecimal(args[1])).abs();
					if(withdrawl.compareTo(PDI.getMoneyInBank()) > 0) {
						return new ManageMoney(plugin, player, 9);
					}
					PDI.transferMoneyToBank(withdrawl.negate());
					return new ManageMoney(plugin, player, 0);
				}
				catch(Exception ex) {
					return new ManageMoney(plugin, player, 7);
				}
			}
		}

		// Take Out Loan <amount>
		if(inputs.get(answer).equals("Take Out Loan <amount> " +ChatColor.GRAY + "Max: " + PDI.getMaxLoan() + " " + PDI.getPluralMoney())) {
			if (args.length != 2) {
				return new ManageMoney(plugin, player, 3);
			}
			else {
				try {
					BigDecimal loan = (new BigDecimal(args[1])).abs();
					if(loan.compareTo(PDI.getMaxLoan().subtract(PDI.getLoanAmount())) > 0) {
						return new ManageMoney(plugin, player, 10);
					}
					PDI.addLoan(loan);
					PDI.addMoney(loan);
					return new ManageMoney(plugin, player, 0);
				}
				catch(Exception ex) {
					return new ManageMoney(plugin, player, 11);
				}
			}
		}
		
		// Repay Loan <amount>
		if(inputs.get(answer).equals("Repay Loan <amount>")) {
			if (args.length != 2) {
				return new ManageMoney(plugin, player, 3);
			}
			else {
				try {
					BigDecimal loan = (new BigDecimal(args[1])).abs();
					if(loan.compareTo(PDI.getLoanAmount()) > 0) {
						return new ManageMoney(plugin, player, 12);
					}
					PDI.removeLoan(loan);
					PDI.removeMoney(loan);
					return new ManageMoney(plugin, player, 0);
				}
				catch(Exception ex) {
					return new ManageMoney(plugin, player, 11);
				}
			}
		}
		
		return new ManageMoney(plugin, player, 2);
	}

}
