package com.github.InspiredOne.InspiredNations.HUD.ManageBusiness;

import java.util.Vector;

import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.entity.Player;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.PlayerMethods;
import com.github.InspiredOne.InspiredNations.PlayerModes;
import com.github.InspiredOne.InspiredNations.Tools;
import com.github.InspiredOne.InspiredNations.HUD.HudConversationMain;
import com.github.InspiredOne.InspiredNations.Regions.GoodBusiness;
import com.github.InspiredOne.InspiredNations.Regions.ServiceBusiness;
import com.github.InspiredOne.InspiredNations.Regions.Town;

public class ManageBusiness2 extends StringPrompt {

	
	InspiredNations plugin;
	Tools tools;
	Player player;
	PlayerData PDI;
	PlayerModes PM;
	PlayerMethods PMeth;
	Town town;
	String businessname;
	ServiceBusiness service;
	GoodBusiness good;
	boolean isGoodBusiness = true;
	String names = "";
	
	Vector<String> inputs = new Vector<String>();
	int error;
	
	// Constructor
	public ManageBusiness2(InspiredNations instance, Player playertemp, int errortemp, String business) {
		plugin = instance;
		player = playertemp;
		tools = new Tools(plugin);
		PDI = plugin.playerdata.get(player.getName());
		PM = plugin.playermodes.get(player.getName());
		PMeth = new PlayerMethods(plugin, player);
		error = errortemp;
		town = PDI.getTownResides();
		businessname = business;
		for(GoodBusiness i: PDI.getGoodBusinessOwned()){
			if (i.getName().equals(business)) {
				good = i;
			}
		}
		for(ServiceBusiness i: PDI.getServiceBusinessOwned()) {
			if (i.getName().equals(business)) {
				service = i;
				isGoodBusiness = false;
			}
		}
	}
	
	// Constructor
	public ManageBusiness2(InspiredNations instance, Player playertemp, int errortemp, String business, Vector<String> namestemp) {
		plugin = instance;
		player = playertemp;
		tools = new Tools(plugin);
		PDI = plugin.playerdata.get(player.getName());
		PM = plugin.playermodes.get(player.getName());
		error = errortemp;
		town = PDI.getTownResides();
		businessname = business;
		for(GoodBusiness i: PDI.getGoodBusinessOwned()){
			if (i.getName().equals(business)) {
				good = i;
			}
		}
		for(ServiceBusiness i: PDI.getServiceBusinessOwned()) {
			if (i.getName().equals(business)) {
				service = i;
				isGoodBusiness = false;
			}
		}
		
		names = tools.format(namestemp);
	}
	

	@Override
	public String getPromptText(ConversationContext arg0) {
		String space = tools.space();
		String main = tools.header("Manage Business. Type an option number.");
		String options = "";
		String end = tools.footer(false);
		String errmsg = ChatColor.RED + tools.errors.get(error) + names;
		
		// make inputs vector
		
		inputs.add("Add Builder <player> " + ChatColor.GRAY + "Adds person that can interact");
		if (good.getBuilders().size() !=0) {
			inputs.add("Remove Builder <player>");
		}
		inputs.add("Manage Budget");
		inputs.add("Manage Employment");
		inputs.add("Protection Levels");
		inputs.add("Reclaim Land");
		inputs.add("Rename <name>");
		
		// Make options text
		options = options.concat(ChatColor.BOLD + "" + ChatColor.GOLD + good.getName() + ChatColor.RESET + "\n");
		if (good.getBuilders().size() != 0) {
			options = options.concat(ChatColor.YELLOW + "Builders:\n");
			options = options.concat(ChatColor.GOLD + tools.format(good.getBuilders()) + "\n");
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
			return new ManageBusiness2(plugin, player, 0, businessname);
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
			return new ManageBusiness2(plugin, player,1, businessname);
		}
		
		if (answer > inputs.size()-1) {
			return new ManageBusiness2(plugin, player, 2, businessname);
		}
		
		// Add Builder <player>
		if (inputs.get(answer).equals("Add Builder <player> " + ChatColor.GRAY + "Adds person that can interact")) {
			if (args.length !=2) {
				return new ManageBusiness2(plugin, player, 3, businessname);
			}
			else {
				Vector<String> names = tools.findPersonExcept(args[1], player.getName());
				if(names.size() == 0) {
					return new ManageBusiness2(plugin, player, 5, businessname);
				}
				else if(names.size() > 1) {
					return new ManageBusiness2(plugin ,player, 4, businessname, names);
				}
				else {
					if (isGoodBusiness) {
						good.addBuilder(names.get(0));
					}
					else {
						service.addBuilder(names.get(0));
					}
					return new ManageBusiness2(plugin, player, 0, businessname);
				}
				
			}
			
		}
		
		if (inputs.get(answer).equals("Rename <name>")) {
			if(args.length < 2) {
				return new ManageBusiness2(plugin, player, 3, businessname);
			}
			else {
				String BusinessName = tools.formatSpace(tools.subArray(args, 1, args.length - 1));
				boolean works = true;
				if (isGoodBusiness) {
					for(GoodBusiness testG: PDI.getTownResides().getGoodBusinesses()) {
						if (testG.getName().equalsIgnoreCase(BusinessName) && !testG.equals(good)) {
							works = false;
						}
					}
					for(ServiceBusiness testS: PDI.getTownResides().getServiceBusinesses()) {
						if (testS.getName().equalsIgnoreCase(BusinessName)) {
							works = false;
						}
					}
				}
				else {
					for(GoodBusiness testG: PDI.getTownResides().getGoodBusinesses()) {
						if (testG.getName().equalsIgnoreCase(BusinessName)) {
							works = false;
						}
					}
					for(ServiceBusiness testS: PDI.getTownResides().getServiceBusinesses()) {
						if (testS.getName().equalsIgnoreCase(BusinessName) && !testS.equals(service)) {
							works = false;
						}
					}
				}
				if (works) {
					try   {
						good.setName(BusinessName);
					}
					catch (Exception ex) {
						service.setName(BusinessName);
					}
					return new ManageBusiness2(plugin, player, 0,BusinessName);
				}
				else {
					return new ManageBusiness2(plugin, player, 36, businessname);
				}
			}
		}
		
		if (inputs.get(answer).equals("Protection Levels")) {
			return new BusinessProtectionLevels(plugin, player, 0, businessname);
		}
		
		return new ManageBusiness2(plugin , player, 2, businessname);
	}
}
