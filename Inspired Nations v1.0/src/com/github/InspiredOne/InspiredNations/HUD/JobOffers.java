package com.github.InspiredOne.InspiredNations.HUD;

import java.util.Vector;

import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.Regions.Business;
import com.github.InspiredOne.InspiredNations.Regions.GoodBusiness;
import com.github.InspiredOne.InspiredNations.Regions.ServiceBusiness;
import com.github.InspiredOne.InspiredNations.Regions.Town;
import com.github.InspiredOne.InspiredNations.Tools.menuType;

public class JobOffers extends Menu {
	Vector<String> businesses = new Vector<String>();
	Vector<String> towns = new Vector<String>();
	// Constructor
	public JobOffers(InspiredNations instance, Player playertemp, int errortemp) {
		super(instance, playertemp, errortemp);
		for(Town town:PDI.getCountryResides().getTowns()) {
			towns.add(town.getName());
		}
	}
	
	@Override
	public String getPromptText(ConversationContext arg0) {
		String space = tools.space();
		String main = tools.header("Jobs. Type an option number.");
		String options = "";
		String end = tools.footer(false);
		String errmsg =  ChatColor.RED + tools.errors.get(error) + names;
		
		inputs.add("Accept Owner <job's town> / <job's name>");
		inputs.add("Accept Job <job's town> / <job's name>");
		inputs.add("Reject Owner <job's town> / <job's name>");
		inputs.add("Reject Job <job's town> / <job's name>");
		
		options = options.concat(menuType.LABEL + "Owner Offers: " + menuType.VALUE + tools.format(PMeth.getOwnerOffers()) + "\n");
		options = options.concat(menuType.LABEL+ "Job Offers: " + menuType.VALUE + tools.format(PMeth.getJobOffers()) + "\n");
		
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
			return new JobOffers(plugin, player, 0);
		}
		if (arg.equalsIgnoreCase("back")) {
			return new Jobs(plugin, player, 0);
		}
		try {
			answer = Integer.decode(args[0])-1;
		}
		catch (Exception ex) {
			return new JobOffers(plugin, player,1);
		}
		
		if (answer > inputs.size()-1) {
			return new JobOffers(plugin, player, 2);
		}
		
		// Accept Owner <job's town> / <job's name>
		if(inputs.get(answer).equals("Accept Owner <job's town> / <job's name>")) {
			if(args.length < 3) {
				return new Jobs(plugin, player, 3); 
			}
			else {
				String[] address = tools.formatSpace(tools.subArray(args, 1, args.length - 1)).split("/");
				if(address.length != 2) {
					return new Jobs(plugin, player, 58);
				}
				Vector<String> townposs = tools.find(address[0].trim(), towns);
				if(townposs.size() == 0) {
					return new Jobs(plugin, player, 59);
				}
				else if(townposs.size() > 1) {
					return new Jobs(plugin, player, 4, townposs);
				}
				else {
					Town towntemp = tools.findTown(PDI.getCountryResides(), address[0].trim()).get(0);
					for(Business i:towntemp.getBusinesses()) {
						businesses.add(i.getName());
					}
					Vector<String> businessposs = tools.find(address[1].trim(), businesses);
					if(businessposs.size() == 0) {
						return new Jobs(plugin,player, 53);
					}
					else if(businessposs.size() > 1) {
						return new Jobs(plugin, player, 4, businessposs);
					}
					else {
						Business business = tools.findBusiness(towntemp, businessposs.get(0)).get(0);
							//TODO figure out how to handle if it's the last owner to leave the business
						if(business.getOwnerOffers().contains(player.getName())) {
							business.removeOwnerOffer(player.getName());
							if(business instanceof GoodBusiness) {
								PDI.addGoodBusinessOwned((GoodBusiness) business);
								return new Jobs(plugin, player, 0);	
							}
							else {
								PDI.addServiceBusinessOwned((ServiceBusiness) business);
								return new Jobs(plugin, player, 0);
							}
							
						}
						else {
							return new Jobs(plugin, player, 65);
						}
						
					}
				}
			}
		}
		// Reject Owner <job's town> / <job's name>
		if(inputs.get(answer).equals("Reject Owner <job's town> / <job's name>")) {
			if(args.length < 3) {
				return new Jobs(plugin, player, 3); 
			}
			else {
				String[] address = tools.formatSpace(tools.subArray(args, 1, args.length - 1)).split("/");
				if(address.length != 2) {
					return new Jobs(plugin, player, 58);
				}
				Vector<String> townposs = tools.find(address[0].trim(), towns);
				if(townposs.size() == 0) {
					return new Jobs(plugin, player, 59);
				}
				else if(townposs.size() > 1) {
					return new Jobs(plugin, player, 4, townposs);
				}
				else {
					Town towntemp = tools.findTown(PDI.getCountryResides(), address[0].trim()).get(0);
					for(Business i:towntemp.getBusinesses()) {
						businesses.add(i.getName());
					}
					Vector<String> businessposs = tools.find(address[1].trim(), businesses);
					if(businessposs.size() == 0) {
						return new Jobs(plugin,player, 53);
					}
					else if(businessposs.size() > 1) {
						return new Jobs(plugin, player, 4, businessposs);
					}
					else {
						Business business = tools.findBusiness(towntemp, businessposs.get(0)).get(0);
							//TODO figure out how to handle if it's the last owner to leave the business
						if(business.getOwnerOffers().contains(player.getName())) {
							business.removeOwnerOffer(player.getName());
						}
						else {
							return new Jobs(plugin, player, 65);
						}
					}
				}
			}
		}
		// Accept Job <job's town> / <job's name>
		if(inputs.get(answer).equals("Accept Job <job's town> / <job's name>")) {
			if(args.length < 3) {
				return new Jobs(plugin, player, 3); 
			}
			else {
				String[] address = tools.formatSpace(tools.subArray(args, 1, args.length - 1)).split("/");
				if(address.length != 2) {
					return new Jobs(plugin, player, 58);
				}
				Vector<String> townposs = tools.find(address[0].trim(), towns);
				if(townposs.size() == 0) {
					return new Jobs(plugin, player, 59);
				}
				else if(townposs.size() > 1) {
					return new Jobs(plugin, player, 4, townposs);
				}
				else {
					Town towntemp = tools.findTown(PDI.getCountryResides(), address[0].trim()).get(0);
					for(Business i:towntemp.getBusinesses()) {
						businesses.add(i.getName());
					}
					Vector<String> businessposs = tools.find(address[1].trim(), businesses);
					if(businessposs.size() == 0) {
						return new Jobs(plugin,player, 53);
					}
					else if(businessposs.size() > 1) {
						return new Jobs(plugin, player, 4, businessposs);
					}
					else {
						Business business = tools.findBusiness(towntemp, businessposs.get(0)).get(0);
							//TODO figure out how to handle if it's the last owner to leave the business
						if(business.getEmployOffers().contains(player.getName())) {
							business.removeEmployOffer(player.getName());
							business.addEmployee(player.getName());
							return new Jobs(plugin, player, 0);	
						}
						else {
							return new Jobs(plugin, player, 66);
						}
					}
				}
			}
		}
		// Reject Job <job's town> / <job's name>
		if(inputs.get(answer).equals("Reject Job <job's town> / <job's name>")) {
			if(args.length < 3) {
				return new Jobs(plugin, player, 3); 
			}
			else {
				String[] address = tools.formatSpace(tools.subArray(args, 1, args.length - 1)).split("/");
				if(address.length != 2) {
					return new Jobs(plugin, player, 58);
				}
				Vector<String> townposs = tools.find(address[0].trim(), towns);
				if(townposs.size() == 0) {
					return new Jobs(plugin, player, 59);
				}
				else if(townposs.size() > 1) {
					return new Jobs(plugin, player, 4, townposs);
				}
				else {
					Town towntemp = tools.findTown(PDI.getCountryResides(), address[0].trim()).get(0);
					for(Business i:towntemp.getBusinesses()) {
						businesses.add(i.getName());
					}
					Vector<String> businessposs = tools.find(address[1].trim(), businesses);
					if(businessposs.size() == 0) {
						return new Jobs(plugin,player, 53);
					}
					else if(businessposs.size() > 1) {
						return new Jobs(plugin, player, 4, businessposs);
					}
					else {
						Business business = tools.findBusiness(towntemp, businessposs.get(0)).get(0);
							//TODO figure out how to handle if it's the last owner to leave the business
						if(business.getEmployOffers().contains(player.getName())) {
							business.removeEmployOffer(player.getName());
							return new Jobs(plugin, player, 0);	
						}
						else {
							return new Jobs(plugin, player, 66);
						}
					}
				}
			}
		}
		return new JobOffers(plugin, player, 2);
	}



}
