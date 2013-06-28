package com.github.InspiredOne.InspiredNations.HUD.ManageBusiness;

import java.util.Vector;

import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.HUD.Menu;

public class ManageWorkers2 extends Menu {
	
	// Constructor
	public ManageWorkers2(InspiredNations instance, Player playertemp, int errortemp, String business) {
		super(instance, playertemp, errortemp, business);
		town = PDI.getTownResides();
	}
	
	// Constructor
	public ManageWorkers2(InspiredNations instance, Player playertemp, int errortemp, String business, Vector<String> namestemp) {
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
		
		// Make inputs vector
		inputs.add("Accept Owner Request <player>");
		inputs.add("Accept Job Request <player>");
		inputs.add("Reject Owner Request <player>");
		inputs.add("Reject Job Request <player>");
		
		if (isGoodBusiness) {
			options = options.concat(ChatColor.YELLOW + "Owner Requests: " + ChatColor.GOLD + tools.format(good.getOwnerRequest()) + "\n");
			options = options.concat(ChatColor.YELLOW + "Employment Requests: " + ChatColor.GOLD + tools.format(good.getEmployRequest()) + "\n");
		}
		else {
			options = options.concat(ChatColor.YELLOW + "Owner Requests: " + ChatColor.GOLD + tools.format(service.getOwnerRequest()) + "\n");
			options = options.concat(ChatColor.YELLOW + "Employment Requests: " + ChatColor.GOLD + tools.format(service.getEmployRequest()) + "\n");
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
		
		String[] args = arg.split(" ");
		if (args[0].equalsIgnoreCase("say"))  {
			if(args.length > 1) {
				PMeth.SendChat(tools.formatSpace(tools.subArray(args, 1, args.length - 1)));
			}
			return new ManageWorkers2(plugin, player, 0, businessname);
		}
		
		if (arg.equalsIgnoreCase("back")) {
			return new ManageWorkers1(plugin, player, 0, businessname);
		}
		try {
			answer = Integer.decode(args[0])-1;
		}
		catch (Exception ex) {
			return new ManageWorkers2(plugin, player,1, businessname);
		}
		
		if (answer > inputs.size()-1) {
			return new ManageWorkers2(plugin, player, 2, businessname);
		}
		
		// Accept Owner Request <player>
		if (inputs.get(answer).equals("Accept Owner Request <player>")) {
			if (args.length !=2) {
				return new ManageWorkers2(plugin, player, 3, businessname);
			}
			else {
				Vector<String> names;
				if (this.isGoodBusiness) {
					names = tools.find(args[1], good.getOwnerRequest());
				}
				else {
					names = tools.find(args[1], service.getOwnerRequest());
				}
				
				if(names.size() == 0) {
					return new ManageWorkers2(plugin, player, 48, businessname);
				}
				else if(names.size() > 1) {
					return new ManageWorkers2(plugin ,player, 4, businessname, names);
				}
				else {
					if (isGoodBusiness) {
						good.removeOwnerRequest(names.get(0));
						good.addOwner(names.get(0));
					}
					else {
						service.removeOwnerRequest(names.get(0));
						service.addOwner(names.get(0));
					}
					return new ManageWorkers2(plugin, player, 0, businessname);
				}
			}
		}
		
		// Accept Job Request <player>
		if (inputs.get(answer).equals("Reject Owner Request <player>")) {
			if (args.length !=2) {
				return new ManageWorkers2(plugin, player, 3, businessname);
			}
			else {
				Vector<String> names;
				if (this.isGoodBusiness) {
					names = tools.find(args[1], good.getEmployRequest());
				}
				else {
					names = tools.find(args[1], service.getEmployRequest());
				}
				
				if(names.size() == 0) {
					return new ManageWorkers2(plugin, player, 49, businessname);
				}
				else if(names.size() > 1) {
					return new ManageWorkers2(plugin ,player, 4, businessname, names);
				}
				else {
					if (isGoodBusiness) {
						good.removeEmployRequest(names.get(0));
						good.addEmployee(names.get(0));
					}
					else {
						service.removeEmployRequest(names.get(0));
						service.addEmployee(names.get(0));
					}
					return new ManageWorkers2(plugin, player, 0, businessname);
				}
			}
		}
		
		// Reject Owner Request <player>
		if (inputs.get(answer).equals("Accept Job Request <player>")) {
			if (args.length !=2) {
				return new ManageWorkers2(plugin, player, 3, businessname);
			}
			else {
				Vector<String> names;
				if (this.isGoodBusiness) {
					names = tools.find(args[1], good.getEmployRequest());
				}
				else {
					names = tools.find(args[1], service.getEmployRequest());
				}
				
				if(names.size() == 0) {
					return new ManageWorkers2(plugin, player, 48, businessname);
				}
				else if(names.size() > 1) {
					return new ManageWorkers2(plugin ,player, 4, businessname, names);
				}
				else {
					if (isGoodBusiness) {
						good.removeOwnerRequest(names.get(0));
					}
					else {
						service.removeOwnerRequest(names.get(0));
					}
					return new ManageWorkers2(plugin, player, 0, businessname);
				}
			}
		}
		
		// Reject Job Request <player>
		if (inputs.get(answer).equals("Reject Job Request <player>")) {
			if (args.length !=2) {
				return new ManageWorkers2(plugin, player, 3, businessname);
			}
			else {
				Vector<String> names;
				if (this.isGoodBusiness) {
					names = tools.find(args[1], good.getEmployRequest());
				}
				else {
					names = tools.find(args[1], service.getEmployRequest());
				}
				
				if(names.size() == 0) {
					return new ManageWorkers2(plugin, player, 49, businessname);
				}
				else if(names.size() > 1) {
					return new ManageWorkers2(plugin ,player, 4, businessname, names);
				}
				else {
					if (isGoodBusiness) {
						good.removeEmployRequest(names.get(0));
					}
					else {
						service.removeEmployRequest(names.get(0));
					}
					return new ManageWorkers2(plugin, player, 0, businessname);
				}
			}
		}
		return new ManageWorkers2(plugin, player, 2, businessname);
	}
}
