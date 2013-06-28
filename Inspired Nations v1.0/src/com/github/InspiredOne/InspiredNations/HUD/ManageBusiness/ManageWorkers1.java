package com.github.InspiredOne.InspiredNations.HUD.ManageBusiness;

import java.util.Vector;

import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.HUD.HudConversationMain;
import com.github.InspiredOne.InspiredNations.HUD.Menu;

public class ManageWorkers1 extends Menu {

	// Constructor
	public ManageWorkers1(InspiredNations instance, Player playertemp, int errortemp, String business) {
		super(instance, playertemp, errortemp, business);
		town = PDI.getTownResides();
	}
	
	// Constructor
	public ManageWorkers1(InspiredNations instance, Player playertemp, int errortemp, String business, Vector<String> namestemp) {
		super(instance, playertemp, errortemp, business, namestemp);
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
		inputs.add("Job Requests (" + ChatColor.GRAY + (good.getEmployRequest().size() + good.getOwnerRequest().size()) + ChatColor.GREEN + ")");
		if (isGoodBusiness) {
			options = options.concat(ChatColor.YELLOW + "Owners: " + ChatColor.GOLD + tools.format(good.getOwners()) + "\n" );
			options = options.concat(ChatColor.YELLOW + "Workers: " + ChatColor.GOLD + tools.format(good.getEmployees()) + "\n" );
			options = options.concat(ChatColor.YELLOW + "Owner Offers: " + ChatColor.GOLD + tools.format(good.getOwnerOffers()) + "\n" );
			options = options.concat(ChatColor.YELLOW + "Job Offers: " + ChatColor.GOLD + tools.format(good.getEmployOffers()) + "\n" );
			options = tools.addDivider(options);
		}
		else {
			options = options.concat(ChatColor.YELLOW + "Owners: " + ChatColor.GOLD + tools.format(service.getOwners()) + "\n");
			options = options.concat(ChatColor.YELLOW + "Workers: " + ChatColor.GOLD + tools.format(service.getEmployees()) + "\n" );
			options = options.concat(ChatColor.YELLOW + "Owner Offers: " + ChatColor.GOLD + tools.format(service.getOwnerOffers()) + "\n" );
			options = options.concat(ChatColor.YELLOW + "Job Offers: " + ChatColor.GOLD + tools.format(service.getEmployOffers()) + "\n" );
			options = tools.addDivider(options);
		}
		
		
		
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
			return new ManageWorkers1(plugin, player, 0, businessname);
		}
		
		if (arg.equalsIgnoreCase("back")) {
			return new ManageBusiness2(plugin, player, 0, businessname);
		}
		try {
			answer = Integer.decode(args[0])-1;
		}
		catch (Exception ex) {
			return new ManageWorkers1(plugin, player,1, businessname);
		}
		
		if (answer > inputs.size()-1) {
			return new ManageWorkers1(plugin, player, 2, businessname);
		}
		
		// Offer Ownership <player>
		if (inputs.get(answer).equals("Offer Ownership <player>")) {
			if (args.length !=2) {
				return new ManageWorkers1(plugin, player, 3, businessname);
			}
			else {
				Vector<String> names = tools.findPersonExcept(args[1], player.getName(), PDI.getCountryResides().getResidents());
				if(names.size() == 0) {
					return new ManageWorkers1(plugin, player, 46, businessname);
				}
				else if(names.size() > 1) {
					return new ManageWorkers1(plugin ,player, 4, businessname, names);
				}
				else {
					
					if (isGoodBusiness) {
						if (good.getOwnerOffers().contains(names.get(0))) {
							return new ManageWorkers1(plugin, player, 40, businessname);
						}
						else if(good.getOwnerRequest().contains(names.get(0))) {
							good.addOwner(names.get(0));
							return new ManageWorkers1(plugin, player, 0, businessname);
						}
						else {
							good.addOwnerOffer(names.get(0));
						}
					}
					else {
						if (service.getOwnerOffers().contains(names.get(0))) {
							return new ManageWorkers1(plugin, player, 40, businessname);
						}
						else if(service.getOwnerRequest().contains(names.get(0))) {
							service.addOwner(names.get(0));
							return new ManageWorkers1(plugin, player, 0, businessname);
						}
						else {
							service.addOwnerOffer(names.get(0));
						}
					}
					return new ManageWorkers1(plugin, player, 0, businessname);
				}
				
			}
			
		}
		//Remove Ownership Offer <player>
		if (inputs.get(answer).equals("Remove Owner Offer <player>")) {
			if (args.length !=2) {
				return new ManageWorkers1(plugin, player, 3, businessname);
			}
			else {
				Vector<String> names;
				if (this.isGoodBusiness) {
					names = tools.find(args[1], good.getOwnerOffers());
				}
				else {
					names = tools.find(args[1], service.getOwnerOffers());
				}
				
				if(names.size() == 0) {
					return new ManageWorkers1(plugin, player, 41, businessname);
				}
				else if(names.size() > 1) {
					return new ManageWorkers1(plugin ,player, 4, businessname, names);
				}
				else {
					if (isGoodBusiness) {
						good.removeOwnerOffer(names.get(0));
					}
					else {
						service.removeOwnerOffer(names.get(0));
					}
					return new ManageWorkers1(plugin, player, 0, businessname);
				}
				
			}
		}
		// Offer Job <player>
		if (inputs.get(answer).equals("Offer Job <player>")) {
			if (args.length !=2) {
				return new ManageWorkers1(plugin, player, 3, businessname);
			}
			else {
				Vector<String> names = tools.findPersonExcept(args[1], player.getName(), PDI.getCountryResides().getResidents());
				if(names.size() == 0) {
					return new ManageWorkers1(plugin, player, 46, businessname);
				}
				else if(names.size() > 1) {
					return new ManageWorkers1(plugin ,player, 4, businessname, names);
				}
				else {
					if (isGoodBusiness) {
						if (good.getEmployOffers().contains(names.get(0))) {
							return new ManageWorkers1(plugin, player, 44, businessname);
						}
						else if(good.getEmployRequest().contains(names.get(0))) {
							good.addEmployee(names.get(0));
							return new ManageWorkers1(plugin, player, 0, businessname);
						}
						else {
							good.addEmployOffer(names.get(0));
						}
					}
					else {
						if (service.getEmployOffers().contains(names.get(0))) {
							return new ManageWorkers1(plugin, player, 44, businessname);
						}
						else if(service.getEmployRequest().contains(names.get(0))) {
							service.addEmployee(names.get(0));
							return new ManageWorkers1(plugin, player, 0, businessname);
						}
						else {
							service.addEmployOffer(names.get(0));
						}
					}
					return new ManageWorkers1(plugin, player, 0, businessname);
				}
				
			}
			
		}
		
		//Remove Job Offer <player>
		if (inputs.get(answer).equals("Remove Job Offer <player>")) {
			if (args.length !=2) {
				return new ManageWorkers1(plugin, player, 3, businessname);
			}
			else {
				Vector<String> names;
				if (this.isGoodBusiness) {
					names = tools.find(args[1], good.getEmployOffers());
				}
				else {
					names = tools.find(args[1], service.getEmployOffers());
				}
				
				if(names.size() == 0) {
					return new ManageWorkers1(plugin, player, 45, businessname);
				}
				else if(names.size() > 1) {
					return new ManageWorkers1(plugin ,player, 4, businessname, names);
				}
				else {
					if (isGoodBusiness) {
						good.removeEmployOffer(names.get(0));
					}
					else {
						service.removeEmployOffer(names.get(0));
					}
					return new ManageWorkers1(plugin, player, 0, businessname);
				}
				
			}
		}
		
		// Fire Worker <player>
		if (inputs.get(answer).equals("Fire Worker <player>")) {
			if (args.length !=2) {
				return new ManageWorkers1(plugin, player, 3, businessname);
			}
			else {
				Vector<String> names;
				if (this.isGoodBusiness) {
					names = tools.find(args[1], good.getEmployees());
				}
				else {
					names = tools.find(args[1], service.getEmployees());
				}
				
				if(names.size() == 0) {
					return new ManageWorkers1(plugin, player, 47, businessname);
				}
				else if(names.size() > 1) {
					return new ManageWorkers1(plugin ,player, 4, businessname, names);
				}
				else {
					if (isGoodBusiness) {
						good.removeEmployee(names.get(0));
					}
					else {
						service.removeEmployee(names.get(0));
					}
					return new ManageWorkers1(plugin, player, 0, businessname);
				}
			}
		}
		
		// Fire Owner <player>
		if (inputs.get(answer).equals("Fire Owner <player>")) {
			if (args.length !=2) {
				return new ManageWorkers1(plugin, player, 3, businessname);
			}
			else {
				Vector<String> names;
				if (this.isGoodBusiness) {
					names = tools.find(args[1], good.getOwners());
				}
				else {
					names = tools.find(args[1], service.getOwners());
				}
				
				if(names.size() == 0) {
					return new ManageWorkers1(plugin, player, 47, businessname);
				}
				else if(names.size() > 1) {
					return new ManageWorkers1(plugin ,player, 4, businessname, names);
				}
				else {
					if (isGoodBusiness) {
						good.removeOwner(names.get(0));
					}
					else {
						service.removeOwner(names.get(0));
					}
					return new ManageWorkers1(plugin, player, 0, businessname);
				}
			}
		}
		
		if(inputs.get(answer).equals("Job Requests (" + ChatColor.GRAY + (good.getEmployRequest().size() + good.getOwnerRequest().size()) + ChatColor.GREEN + ")")) {
			return new ManageWorkers2(plugin, player, 0, businessname);
		}
		
		return new ManageWorkers1(plugin, player, 2, businessname);
	}



}
