package com.github.InspiredOne.InspiredNations.HUD.ManageBusiness;

import java.util.Vector;

import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.HUD.HudConversationMain;
import com.github.InspiredOne.InspiredNations.HUD.Menu;
import com.github.InspiredOne.InspiredNations.Regions.Business;
import com.github.InspiredOne.InspiredNations.Regions.Cuboid;
import com.github.InspiredOne.InspiredNations.Regions.polygonPrism;
import com.github.InspiredOne.InspiredNations.Tools.menuType;

public class ManageBusiness2 extends Menu {
	
	// Constructor
	public ManageBusiness2(InspiredNations instance, Player playertemp, int errortemp) {

		super(instance, playertemp, errortemp);
		town = PDI.getTownResides();

	}
	
	// Constructor
	public ManageBusiness2(InspiredNations instance, Player playertemp, int errortemp, Vector<String> namestemp) {
		super(instance, playertemp, errortemp, namestemp);
		town = PDI.getTownResides();
	}
	

	@Override
	public String getPromptText(ConversationContext arg0) {
		arg0.setSessionData("businame", busi.getName());
		String space = tools.space();
		String main = tools.header("Manage Business. Type an option number.");
		String options = "";
		String end = tools.footer(false);
		String errmsg = ChatColor.RED + tools.errors.get(error) + names;
		
		// make inputs vector
		inputs.add("Add Builder <player> " + ChatColor.GRAY + "Adds person that can interact");
			if (busi.getBuilders().size() !=0) {
				inputs.add("Remove Builder <player>");
			}
		inputs.add("Manage Budget");
		inputs.add("Manage Workers ("+ menuType.OPTIONDESCRIP + (busi.getEmployRequest().size() + busi.getOwnerRequest().size()) + menuType.OPTION + ")");
		inputs.add("Protection Levels");
		inputs.add("Reclaim Land");
		inputs.add("Rename <name>");
		
		// Make options text
		options = options.concat(ChatColor.BOLD + "" + ChatColor.GOLD + busi.getName() + ChatColor.RESET + "\n");
		if (busi.getBuilders().size() != 0) {
			options = options.concat(ChatColor.YELLOW + "Builders:\n");
			options = options.concat(ChatColor.GOLD + tools.format(busi.getBuilders()) + "\n");
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
			return new ManageBusiness2(plugin, player, 0);
		}
		
		if (arg.equalsIgnoreCase("back")) {
			if (PDI.getGoodBusinessOwned().size() + PDI.getServiceBusinessOwned().size() != 1) {
				return new ManageBusiness1(plugin, player, 0);
			}
			else {
				return new HudConversationMain(plugin, player, 0);
			}
		}
		try {
			answer = Integer.decode(args[0])-1;
		}
		catch (Exception ex) {
			return new ManageBusiness2(plugin, player,1);
		}
		
		if (answer > inputs.size()-1) {
			return new ManageBusiness2(plugin, player, 2);
		}
		
		// Reclaim Land
		if(inputs.get(answer).equals("Reclaim Land")) {
			if(this.isGoodBusiness) {
				PM.setReSelectGoodBusiness(true);
				PM.setReSelectServiceBusiness(false);
			}
			else {
				plugin.logger.info("reselectservice true");
				PM.setReSelectGoodBusiness(false);
				PM.setReSelectServiceBusiness(true);
			}
			PM.setPolygon(new polygonPrism(player.getWorld().getName()));
			PM.setCuboid(new Cuboid(player.getWorld().getName()));
			return new ReselectBusiness1(plugin, player, 0);
		}
		
		// Add Builder <player>
		if (inputs.get(answer).equals("Add Builder <player> " + ChatColor.GRAY + "Adds person that can interact")) {
			if (args.length !=2) {
				return new ManageBusiness2(plugin, player, 3);
			}
			else {
				Vector<String> names = tools.findPersonExcept(args[1], player.getName());
				if(names.size() == 0) {
					return new ManageBusiness2(plugin, player, 5);
				}
				else if(names.size() > 1) {
					return new ManageBusiness2(plugin ,player, 4, names);
				}
				else {
					if (busi.getBuilders().contains(names.get(0))) {
						return new ManageBusiness2(plugin, player, 42);
					}
					else {
						busi.addBuilder(names.get(0));
					}
					return new ManageBusiness2(plugin, player, 0);
				}
			}
		}
		
		//Remove Builder <player>
		if (inputs.get(answer).equals("Remove Builder <player>")) {
			if (args.length !=2) {
				return new ManageBusiness2(plugin, player, 3);
			}
			else {
				Vector<String> names;
				names = tools.find(args[1], busi.getBuilders());
				
				if(names.size() == 0) {
					return new ManageBusiness2(plugin, player, 43);
				}
				else if(names.size() > 1) {
					return new ManageBusiness2(plugin ,player, 4, names);
				}
				else {
					busi.removeBuilder(names.get(0));
					return new ManageBusiness2(plugin, player, 0);
				}
				
			}
		}
		
		// Manage Workers
		if(inputs.get(answer).equals("Manage Workers (" + menuType.OPTIONDESCRIP + (busi.getEmployRequest().size() + busi.getOwnerRequest().size()) + menuType.OPTION + ")")) {
			return new ManageWorkers1(plugin, player, 0);
		}
		
		// Rename <name>
		if (inputs.get(answer).equals("Rename <name>")) {
			if(args.length < 2) {
				return new ManageBusiness2(plugin, player, 3);
			}
			else {
				String BusinessName = tools.formatSpace(tools.subArray(args, 1, args.length - 1));
				if(BusinessName.contains("/")) {
					return new ManageBusiness2(plugin, player, 50);
				}
				boolean works = true;
				for(Business test:PDI.getTownResides().getBusinesses()) {
					if(test.getName().equalsIgnoreCase(BusinessName) && !test.equals(busi)) {
						works = false;
					}
				}
				if (works) {
					busi.setName(BusinessName);
					return new ManageBusiness2(plugin, player, 0);
				}
				else {
					return new ManageBusiness2(plugin, player, 36);
				}
			}
		}
		
		if (inputs.get(answer).equals("Protection Levels")) {
			return new BusinessProtectionLevels(plugin, player, 0, businessname);
		}
		
		return new ManageBusiness2(plugin , player, 2);
	}
}
