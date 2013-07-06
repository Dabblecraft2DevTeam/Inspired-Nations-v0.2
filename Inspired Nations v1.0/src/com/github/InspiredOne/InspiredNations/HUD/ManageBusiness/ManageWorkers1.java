package com.github.InspiredOne.InspiredNations.HUD.ManageBusiness;

import java.util.Vector;

import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.Tools.menuType;
import com.github.InspiredOne.InspiredNations.HUD.Menu;

public class ManageWorkers1 extends Menu {

	// Constructor
	public ManageWorkers1(InspiredNations instance, Player playertemp, int errortemp) {
		super(instance, playertemp, errortemp);
		town = PDI.getTownResides();
	}
	
	// Constructor
	public ManageWorkers1(InspiredNations instance, Player playertemp, int errortemp, Vector<String> namestemp) {
		super(instance, playertemp, errortemp, namestemp);
		town = PDI.getTownResides();
	}
	
	@Override
	public String getPromptText(ConversationContext arg0) {
		String space = tools.space();
		String main = tools.header("Manage Workers. Type an option number.");
		String options = "";
		String end = tools.footer(false);
		String errmsg = ChatColor.RED + tools.errors.get(error) + names;
		
		// Make Inputs Vector
		inputs.add("Fire Owner <player>");
		inputs.add("Fire Worker <player>");
		inputs.add("Offer Ownership <player>");
		inputs.add("Offer Job <player>");
		inputs.add("Remove Owner Offer <player>");
		inputs.add("Remove Job Offer <player>");
		inputs.add("Job Requests (" + menuType.OPTIONDESCRIP + (busi.getEmployRequest().size() + busi.getOwnerRequest().size()) + menuType.OPTION + ")");	
		
		options = options.concat(menuType.LABEL + "Owners: " + menuType.VALUE + tools.format(busi.getOwners()) + "\n" );
		options = options.concat(menuType.LABEL + "Workers: " + menuType.VALUE + tools.format(busi.getEmployees()) + "\n" );
		options = options.concat(menuType.LABEL + "Owner Offers: " + menuType.VALUE + tools.format(busi.getOwnerOffers()) + "\n" );
		options = options.concat(menuType.LABEL + "Job Offers: " + menuType.VALUE + tools.format(busi.getEmployOffers()) + "\n" );
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
		
		String[] args = arg.split(" ");
		if (args[0].equalsIgnoreCase("say"))  {
			if(args.length > 1) {
				PMeth.SendChat(tools.formatSpace(tools.subArray(args, 1, args.length - 1)));
			}
			return new ManageWorkers1(plugin, player, 0);
		}
		
		if (arg.equalsIgnoreCase("back")) {
			return new ManageBusiness2(plugin, player, 0);
		}
		try {
			answer = Integer.decode(args[0])-1;
		}
		catch (Exception ex) {
			return new ManageWorkers1(plugin, player,1);
		}
		
		if (answer > inputs.size()-1) {
			return new ManageWorkers1(plugin, player, 2);
		}
		
		// Offer Ownership <player>
		if (inputs.get(answer).equals("Offer Ownership <player>")) {
			if (args.length !=2) {
				return new ManageWorkers1(plugin, player, 3);
			}
			else {
				Vector<String> names = tools.findPersonExcept(args[1], player.getName(), PDI.getCountryResides().getResidents());
				if(names.size() == 0) {
					return new ManageWorkers1(plugin, player, 46);
				}
				else if(names.size() > 1) {
					return new ManageWorkers1(plugin ,player, 4, names);
				}
				else {
					
						if (busi.getOwnerOffers().contains(names.get(0))) {
							return new ManageWorkers1(plugin, player, 40);
						}
						else if(busi.getOwnerRequest().contains(names.get(0))) {
							busi.addOwner(names.get(0));
							return new ManageWorkers1(plugin, player, 0);
						}
						else {
							busi.addOwnerOffer(names.get(0));
						}

					return new ManageWorkers1(plugin, player, 0);
				}
				
			}
			
		}
		//Remove Ownership Offer <player>
		if (inputs.get(answer).equals("Remove Owner Offer <player>")) {
			if (args.length !=2) {
				return new ManageWorkers1(plugin, player, 3);
			}
			else {
				Vector<String> names;
				names = tools.find(args[1], busi.getOwnerOffers());

				
				if(names.size() == 0) {
					return new ManageWorkers1(plugin, player, 41);
				}
				else if(names.size() > 1) {
					return new ManageWorkers1(plugin ,player, 4, names);
				}
				else {
					busi.removeOwnerOffer(names.get(0));

					return new ManageWorkers1(plugin, player, 0);
				}
				
			}
		}
		// Offer Job <player>
		if (inputs.get(answer).equals("Offer Job <player>")) {
			if (args.length !=2) {
				return new ManageWorkers1(plugin, player, 3);
			}
			else {
				Vector<String> names = tools.findPersonExcept(args[1], player.getName(), PDI.getCountryResides().getResidents());
				if(names.size() == 0) {
					return new ManageWorkers1(plugin, player, 46);
				}
				else if(names.size() > 1) {
					return new ManageWorkers1(plugin ,player, 4, names);
				}
				else {
					if (busi.getEmployOffers().contains(names.get(0))) {
						return new ManageWorkers1(plugin, player, 44);
					}
					else if(busi.getEmployRequest().contains(names.get(0))) {
						busi.addEmployee(names.get(0));
						return new ManageWorkers1(plugin, player, 0);
					}
					else {
						busi.addEmployOffer(names.get(0));
					}
					return new ManageWorkers1(plugin, player, 0);
				}
				
			}
			
		}
		
		//Remove Job Offer <player>
		if (inputs.get(answer).equals("Remove Job Offer <player>")) {
			if (args.length !=2) {
				return new ManageWorkers1(plugin, player, 3);
			}
			else {
				Vector<String> names;
				names = tools.find(args[1], busi.getEmployOffers());

				
				if(names.size() == 0) {
					return new ManageWorkers1(plugin, player, 45);
				}
				else if(names.size() > 1) {
					return new ManageWorkers1(plugin ,player, 4, names);
				}
				else {
					busi.removeEmployOffer(names.get(0));

					return new ManageWorkers1(plugin, player, 0);
				}
				
			}
		}
		
		// Fire Worker <player>
		if (inputs.get(answer).equals("Fire Worker <player>")) {
			if (args.length !=2) {
				return new ManageWorkers1(plugin, player, 3);
			}
			else {
				Vector<String> names;
				names = tools.find(args[1], busi.getEmployees());
				
				if(names.size() == 0) {
					return new ManageWorkers1(plugin, player, 47);
				}
				else if(names.size() > 1) {
					return new ManageWorkers1(plugin ,player, 4, names);
				}
				else {
					busi.removeEmployee(names.get(0));

					return new ManageWorkers1(plugin, player, 0);
				}
			}
		}

		// Fire Owner <player>
		if (inputs.get(answer).equals("Fire Owner <player>")) {
			if (args.length !=2) {
				return new ManageWorkers1(plugin, player, 3);
			}
			else {
				Vector<String> names;
				names = tools.find(args[1], busi.getOwners());

				
				if(names.size() == 0) {
					return new ManageWorkers1(plugin, player, 47);
				}
				else if(names.size() > 1) {
					return new ManageWorkers1(plugin ,player, 4, names);
				}
				else {
					busi.removeOwner(names.get(0));

					return new ManageWorkers1(plugin, player, 0);
				}
			}
		}
		if(inputs.get(answer).equals("Job Requests (" + menuType.OPTIONDESCRIP + (busi.getEmployRequest().size() + busi.getOwnerRequest().size()) + menuType.OPTION + ")")) {
			return new ManageWorkers2(plugin, player, 0);
		}

		return new ManageWorkers1(plugin, player, 2);
	}



}
