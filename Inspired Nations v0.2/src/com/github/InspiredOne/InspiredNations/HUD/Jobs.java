package com.github.InspiredOne.InspiredNations.HUD;

import java.util.Vector;

import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.Regions.Business;
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
		for(Business business:PDI.getBusinesses()) {
			businessesowned.add(business.getName() + " / " + country.getTowns().get(business.getTown()).getName());
		}
		for(Town town:PDI.getCountryResides().getTowns()) {
			towns.add(town.getName());
		}
	}
	
	public Jobs(InspiredNations instance, Player playertemp, int errortemp, Vector<String> namestemp) {
		super(instance, playertemp, errortemp, namestemp);
		country = PDI.getCountryResides();
		for(Business business:PDI.getBusinesses()) {
			businessesowned.add(business.getName() + " / " + country.getTowns().get(business.getTown()).getName());
		}
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
		String errmsg = ChatColor.RED + tools.errors.get(error) + names;
		
		// Make Inputs Vector
		inputs.add("Request Owner <job's town> / <job's name>");
		inputs.add("Request Job <job's town> / <job's name>");
		inputs.add("Remove Owner Request <job's town> / <job's name>");
		inputs.add("Remove Job Request <job's town> / <job's name>");
		inputs.add("Quit Owner <job's town> / <job's name>");
		inputs.add("Quit Job <job's town> / <job's name>");
		inputs.add("Job Offers (" + menuType.OPTIONDESCRIP + 
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
		// Job Offers
		if (inputs.get(answer).equals("Job Offers " + "(" + ChatColor.GRAY + 
				(PMeth.getJobOffers().size() + PMeth.getOwnerOffers().size()) + ChatColor.GREEN + ")")) {
			return new JobOffers(plugin, player, 0);
		}
		
		// Remove Owner Request <job's town> / <job's name>
		if(inputs.get(answer).equals("Remove Owner Request <job's town> / <job's name>")) {
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
						if(business.getOwnerRequest().contains(player.getName())) {
							business.removeOwnerRequest(player.getName());
							return new Jobs(plugin, player, 0);	
						}
						else {
							return new Jobs(plugin, player, 63);
						}
						
					}
				}
			}
		}
		// Remove Job Request <job's town> / <job's name>
		if(inputs.get(answer).equals("Remove Owner Request <job's town> / <job's name>")) {
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
						if(business.getEmployRequest().contains(player.getName())) {
							business.removeEmployRequest(player.getName());
							return new Jobs(plugin, player, 0);
						}
						else {
							return new Jobs(plugin, player, 64);
						}
						
						
					}
				}
			}
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
					Vector<Business> businesslist = tools.findBusiness(townobj, business.get(0));
					if(( businesslist.get(0)).getOwnerRequest().contains(player.getName())) {
						return new Jobs(plugin, player, 54);
					}
					else if(( businesslist.get(0)).getOwnerOffers().contains(player.getName())) {
						if(businesslist.get(0) instanceof GoodBusiness) {
							PDI.addGoodBusinessOwned((GoodBusiness) businesslist.get(0));
						}
						else {
							PDI.addServiceBusinessOwned((ServiceBusiness) businesslist.get(0));
						}
						return new Jobs(plugin, player, 0);
					}
					else if((businesslist.get(0)).getOwners().contains(player.getName())) {
						return new Jobs(plugin, player, 56);
					}
					else {
						(businesslist.get(0)).addOwnerRequest(player.getName());
						return new Jobs(plugin, player, 0);
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
					Vector<Business> businesslist = tools.findBusiness(townobj, business.get(0));
					if(( businesslist.get(0)).getEmployRequest().contains(player.getName())) {
						return new Jobs(plugin, player, 55);
					}
					else if(( businesslist.get(0)).getEmployOffers().contains(player.getName())) {
						if(businesslist.get(0) instanceof GoodBusiness) {
							((GoodBusiness) businesslist.get(0)).addEmployee(player.getName());
						}
						else {
							((ServiceBusiness) businesslist.get(0)).addEmployee(player.getName());
						}
						return new Jobs(plugin, player, 0);
					}
					else if((businesslist.get(0)).getEmployees().contains(player.getName())) {
						return new Jobs(plugin, player, 57);
					}
					else {
						(businesslist.get(0)).addEmployRequest(player.getName());
						return new Jobs(plugin, player, 0);
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
					for(Business i:towntemp.getBusinesses()) {
						businesses.add(i.getName());
					}
					Vector<String> businessposs = tools.find(address[1].trim(), businesses);
					if(businessposs.size() == 0) {
						return new Jobs(plugin,player, 60);
					}
					else if(businessposs.size() > 1) {
						return new Jobs(plugin, player, 4, businessposs);
					}
					else {
						Business business = tools.findBusiness(towntemp, businessposs.get(0)).get(0);
							//TODO figure out how to handle if it's the last owner to leave the business
						business.removeOwner(player);
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
					for(Business businesstemp:towntemp.getBusinesses()) {
						if(businesstemp.getEmployees().contains(player.getName())) {
							businesses.add(businesstemp.getName());
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
						Business business = tools.findBusiness(towntemp, businessposs.get(0)).get(0);
						business.removeEmployee(player.getName());
						return new Jobs(plugin, player, 0);
					}
				}
			}
		}
		return new Jobs(plugin, player, 2);
	}
}
