package com.github.InspiredOne.InspiredNations.HUD;

import java.util.Vector;

import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.Regions.GoodBusiness;
import com.github.InspiredOne.InspiredNations.Regions.InspiredRegion;
import com.github.InspiredOne.InspiredNations.Regions.ServiceBusiness;
import com.github.InspiredOne.InspiredNations.Regions.Town;
import com.github.InspiredOne.InspiredNations.Tools.menuType;

public class Jobs extends Menu {

	Vector<String> businesses = new Vector<String>();
	Vector<String> businessesowned = new Vector<String>();
	Vector<String> towns = new Vector<String>();
	// Constructor
	public Jobs(InspiredNations instance, Player playertemp, int errortemp) {
		super(instance, playertemp, errortemp);
		country = PDI.getCountryResides();
		for(GoodBusiness business:PDI.getGoodBusinessOwned()) {
			towns.add(PDI.getCountryResides().getTowns().get(business.getTown()).getName());
			businessesowned.add(business.getName() + " / " + country.getTowns().get(business.getTown()).getName());
		}
		for(ServiceBusiness business:PDI.getServiceBusinessOwned()) {
			towns.add(PDI.getCountryResides().getTowns().get(business.getTown()).getName());
			businessesowned.add(business.getName() + " / " + country.getTowns().get(business.getTown()).getName());
		}
	}
	
	public Jobs(InspiredNations instance, Player playertemp, int errortemp, Vector<String> namestemp) {
		super(instance, playertemp, errortemp, namestemp);
		country = PDI.getCountryResides();
		for(GoodBusiness business:PDI.getGoodBusinessOwned()) {
			towns.add(PDI.getCountryResides().getTowns().get(business.getTown()).getName());
			businessesowned.add(business.getName() + " / " + country.getTowns().get(business.getTown()).getName());
		}
		for(ServiceBusiness business:PDI.getServiceBusinessOwned()) {
			towns.add(PDI.getCountryResides().getTowns().get(business.getTown()).getName());
			businessesowned.add(business.getName() + " / " + country.getTowns().get(business.getTown()).getName());
		}
	}
	
	@Override
	public String getPromptText(ConversationContext arg0) {
		String space = tools.space();
		String main = tools.header("Jobs. Type an option number.");
		String options = "";
		String end = tools.footer(false);
		String errmsg = ChatColor.RED + tools.errors.get(error) + names;
		
		// Make Inputs Vector
		inputs.add("Request Owner <job's town> / <job's name>");
		inputs.add("Request Job <job's town> / <job's name>");
		inputs.add("Quit Owner <job's town> / <job's name>");
		inputs.add("Quit Job <job's town> / <job's name>");
		inputs.add("Job Offers " + "(" + menuType.OPTIONDESCRIP + 
				(PMeth.getJobOffers().size() + PMeth.getOwnerOffers().size()) + menuType.OPTION + ")");
		
		options = options.concat(menuType.LABEL + "Owner: " + menuType.VALUE + tools.format(businessesowned) + "\n");
		options = options.concat(menuType.LABEL + "Jobs: " + menuType.VALUE + tools.format(PMeth.getEmployed()) + "\n");
		options = options.concat(menuType.LABEL + "Owner Requests: " + menuType.VALUE + tools.format(PMeth.getOwnerRequests()) + "\n");
		options = options.concat(menuType.LABEL + "Job Requests: " + menuType.VALUE + tools.format(PMeth.getJobRequests()) + "\n");
		
		options = tools.addDivider(options);
		options = options.concat(tools.options(inputs));
		
		return space + main + options + end + errmsg;
	}
	
	@Override
	public Prompt acceptInput(ConversationContext arg0, String arg) {
		// Cleared for use down below
		
		int answer = 0;
		if (arg.startsWith("/")) {
			arg = arg.substring(1);
		}
		String[] args = arg.split(" ");
		if (args[0].equalsIgnoreCase("say"))  {
			if(args.length > 1) {
				PMeth.SendChat(tools.formatSpace(tools.subArray(args, 1, args.length - 1)));
			}
			return new Jobs(plugin, player, 0);
		}
		if (arg.equalsIgnoreCase("back")) {
			return new HudConversationMain(plugin, player, 0);
		}
		try {
			answer = Integer.decode(args[0])-1;
		}
		catch (Exception ex) {
			return new Jobs(plugin, player,1);
		}
		
		if (answer > inputs.size()-1) {
			return new Jobs(plugin, player, 2);
		}
		
		if (inputs.get(answer).equals("Job Offers " + "(" + ChatColor.GRAY + 
				(PMeth.getJobOffers().size() + PMeth.getOwnerOffers().size()) + ChatColor.GREEN + ")")) {
			return new JobOffers(plugin, player, 0);
		}
		
		// Request Owner <job's town> / <job's name>
		if(inputs.get(answer).equals("Request Owner <job's town> / <job's name>")) {
			if (args.length < 3) {
				return new Jobs(plugin, player, 3);
			}
			String[] address = tools.formatSpace(tools.subArray(args, 1, args.length - 1)).split("/");
			if(address.length != 2) {
				return new Jobs(plugin, player, 58);
			}
			Vector<String> town = tools.findTown(country.getName(), address[0].trim(), true);
			plugin.logger.info("1");
			if (town.size() == 0) {
				 return new Jobs(plugin, player, 52);
			}
			else if(town.size() > 1) {
				return new Jobs(plugin, player, 4, town);
			}
			else {
				plugin.logger.info("2");
				plugin.logger.info(town.get(0));
				plugin.logger.info(tools.findTown(country, address[0].trim()) + "");
				Town townobj = tools.findTown(country, address[0].trim()).get(0);
				Vector<String> allBusiness = new Vector<String>();
				for(InspiredRegion business: townobj.getGoodBusinesses()) {
					allBusiness.add(business.getName());
				}
				plugin.logger.info("3");
				for(InspiredRegion business: townobj.getServiceBusinesses()) {
					allBusiness.add(business.getName());
				}
				plugin.logger.info("4");
				Vector<String> business = tools.find(address[1].trim(), allBusiness);
				plugin.logger.info("5");
				if(business.size() == 0) {
					return new Jobs(plugin, player, 53);
				}
				else if(business.size() > 1) {
					return new Jobs(plugin, player, 4, business);
				}
				else {
					plugin.logger.info("6");
					Vector<InspiredRegion> businesslist = tools.findBusiness(townobj, business.get(0));
					if(businesslist.get(0) instanceof GoodBusiness) {
						plugin.logger.info("7");
						if(((GoodBusiness) businesslist.get(0)).getOwnerRequest().contains(player.getName())) {
							return new Jobs(plugin, player, 54);
						}
						else if(((GoodBusiness) businesslist.get(0)).getOwnerOffers().contains(player.getName())) {
							PDI.addGoodBusinessOwned((GoodBusiness) businesslist.get(0));
							return new Jobs(plugin, player, 0);
						}
						else if(((GoodBusiness) businesslist.get(0)).getOwners().contains(player.getName())) {
							return new Jobs(plugin, player, 56);
						}
						else {
							((GoodBusiness) businesslist.get(0)).addOwnerRequest(player.getName());
							return new Jobs(plugin, player, 0);
						}
					}
					if(businesslist.get(0) instanceof ServiceBusiness) {
						if(((ServiceBusiness) businesslist.get(0)).getOwnerRequest().contains(player.getName())) {
							return new Jobs(plugin, player, 54);
						}
						else if(((ServiceBusiness) businesslist.get(0)).getOwnerOffers().contains(player.getName())) {
							PDI.addServiceBusinessOwned((ServiceBusiness) businesslist.get(0));
							return new Jobs(plugin, player, 0);
						}
						else if(((ServiceBusiness) businesslist.get(0)).getOwners().contains(player.getName())) {
							return new Jobs(plugin, player, 56);
						}
						else {
							((ServiceBusiness) businesslist.get(0)).addOwnerRequest(player.getName());
							return new Jobs(plugin, player, 0);
						}
					}
				}
			}
		}
		// Request Job <job's town> / <job's name>
		if(inputs.get(answer).equals("Request Job <job's town> / <job's name>")) {
			if (args.length < 3) {
				return new Jobs(plugin, player, 3);
			}
			String[] address = tools.formatSpace(tools.subArray(args, 1, args.length - 1)).split("/");
			if(address.length != 2) {
				return new Jobs(plugin, player, 58);
			}
			Vector<String> town = tools.findTown(PDI.getCountryResides().getName(), address[0].trim(), true);
			if (town.size() == 0) {
				 return new Jobs(plugin, player, 52);
			}
			else if(town.size() > 1) {
				return new Jobs(plugin, player, 4, town);
			}
			else {
				Town townobj = tools.findTown(country, address[0].trim()).get(0);
				Vector<String> allBusiness = new Vector<String>();
				for(InspiredRegion business: townobj.getGoodBusinesses()) {
					allBusiness.add(business.getName());
				}
				for(InspiredRegion business: townobj.getServiceBusinesses()) {
					allBusiness.add(business.getName());
				}
				Vector<String> business = tools.find(address[1].trim(), allBusiness);
				if(business.size() == 0) {
					return new Jobs(plugin, player, 53);
				}
				else if(business.size() > 1) {
					return new Jobs(plugin, player, 4, business);
				}
				else {
					Vector<InspiredRegion> businesslist = tools.findBusiness(townobj, business.get(0));
					if(businesslist.get(0) instanceof GoodBusiness) {
						if(((GoodBusiness) businesslist.get(0)).getEmployRequest().contains(player.getName())) {
							return new Jobs(plugin, player, 55);
						}
						else if(((GoodBusiness) businesslist.get(0)).getEmployOffers().contains(player.getName())) {
							((GoodBusiness) businesslist.get(0)).addEmployee(player.getName());
							return new Jobs(plugin, player, 0);
						}
						else if(((GoodBusiness) businesslist.get(0)).getEmployees().contains(player.getName())) {
							return new Jobs(plugin, player, 57);
						}
						else {
							((GoodBusiness) businesslist.get(0)).addEmployRequest(player.getName());
							return new Jobs(plugin, player, 0);
						}
					}
					if(businesslist.get(0) instanceof ServiceBusiness) {
						if(((ServiceBusiness) businesslist.get(0)).getEmployRequest().contains(player.getName())) {
							return new Jobs(plugin, player, 55);
						}
						else if(((ServiceBusiness) businesslist.get(0)).getEmployOffers().contains(player.getName())) {
							((ServiceBusiness) businesslist.get(0)).addEmployee(player.getName());
							return new Jobs(plugin, player, 0);
						}
						else if(((ServiceBusiness) businesslist.get(0)).getEmployOffers().contains(player.getName())) {
							return new Jobs(plugin, player, 57);
						}
						else {
							((ServiceBusiness) businesslist.get(0)).addEmployRequest(player.getName());
							return new Jobs(plugin, player, 0);
						}
					}
				}
			}	
		}
		
		if (inputs.get(answer).equals("Quit Owner <job's town> / <job's name>")) {
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
					for(GoodBusiness businesstemp:towntemp.getGoodBusinesses()) {
						if(businesstemp.getOwners().contains(player.getName())) {
							businesses.add(businesstemp.getName());
						}
					}
					for(ServiceBusiness business:towntemp.getServiceBusinesses()) {
						if(business.getOwners().contains(player.getName())) {
							businesses.add(business.getName());
						}
					}
					Vector<String> businessposs = tools.find(address[1].trim(), businesses);
					if(businessposs.size() == 0) {
						return new Jobs(plugin,player, 60);
					}
					else if(businessposs.size() > 1) {
						return new Jobs(plugin, player, 4, businessposs);
					}
					else {
						InspiredRegion business = null;
						for(GoodBusiness businesstemp:towntemp.getGoodBusinesses()) {
							if(businesstemp.getName().equals(businessposs.get(0))) {
								business = businesstemp;
								break;
							}
						}
						for(ServiceBusiness businesstemp:towntemp.getServiceBusinesses()) {
							if(businesstemp.getName().equals(businessposs.get(0))) {
								business = businesstemp;
								break;
							}
						}
						if(business instanceof GoodBusiness) {
							//TODO figure out how to handle if it's the last owner to leave the business
							((GoodBusiness) business).removeOwner(player);
						}
						else {
							((ServiceBusiness) business).removeOwner(player);
						}
						return new Jobs(plugin, player, 0);
					}
				}
			}
		}
		
		// Quit Job
		if (inputs.get(answer).equals("Quit Job <job's town> / <job's name>")) {
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
					return new Jobs(plugin, player, 61);
				}
				else if(townposs.size() > 1) {
					return new Jobs(plugin, player, 4, townposs);
				}
				else {
					Town towntemp = tools.findTown(PDI.getCountryResides(), address[0].trim()).get(0);
					for(GoodBusiness businesstemp:towntemp.getGoodBusinesses()) {
						if(businesstemp.getEmployees().contains(player.getName())) {
							businesses.add(businesstemp.getName());
						}
					}
					for(ServiceBusiness business:towntemp.getServiceBusinesses()) {
						if(business.getEmployees().contains(player.getName())) {
							businesses.add(business.getName());
						}
					}
					Vector<String> businessposs = tools.find(address[1].trim(), businesses);
					if(businessposs.size() == 0) {
						return new Jobs(plugin,player, 62);
					}
					else if(businessposs.size() > 1) {
						return new Jobs(plugin, player, 4, businessposs);
					}
					else {
						InspiredRegion business = null;
						for(GoodBusiness businesstemp:towntemp.getGoodBusinesses()) {
							if(businesstemp.getName().equals(businessposs.get(0))) {
								business = businesstemp;
								break;
							}
						}
						for(ServiceBusiness businesstemp:towntemp.getServiceBusinesses()) {
							if(businesstemp.getName().equals(businessposs.get(0))) {
								business = businesstemp;
								break;
							}
						}
						if(business instanceof GoodBusiness) {
							((GoodBusiness) business).removeEmployee(player.getName());
						}
						else {
							((ServiceBusiness) business).removeEmployee(player.getName());
						}
						return new Jobs(plugin, player, 0);
						
					}
				}
			}
		}
		return new Jobs(plugin, player, 2);
	}
}
