package com.github.InspiredOne.InspiredNations.HUD.ManageBusiness;

import java.util.Vector;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.entity.Player;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.PlayerMethods;
import com.github.InspiredOne.InspiredNations.PlayerModes;
import com.github.InspiredOne.InspiredNations.Tools;
import com.github.InspiredOne.InspiredNations.Tools.region;
import com.github.InspiredOne.InspiredNations.HUD.HudConversationMain;
import com.github.InspiredOne.InspiredNations.Regions.GoodBusiness;
import com.github.InspiredOne.InspiredNations.Regions.ServiceBusiness;
import com.github.InspiredOne.InspiredNations.Regions.Town;

public class BusinessProtectionLevels extends StringPrompt {

	
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
	
	Vector<String> inputs = new Vector<String>();
	int error;
	
	// Constructor
	public BusinessProtectionLevels(InspiredNations instance, Player playertemp, int errortemp, String business) {
		plugin = instance;
		player = playertemp;
		tools = new Tools(plugin);
		PDI = plugin.playerdata.get(player.getName());
		PM = plugin.playermodes.get(player.getName());
		error = errortemp;
		town = PDI.getTownResides();
		PMeth = new PlayerMethods(plugin, player);
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
	
	@Override
	public String getPromptText(ConversationContext arg0) {

		//make Inputs Vector
		inputs.add("Set <level>");
		
		if (isGoodBusiness) {
			return tools.protLevels("Business", error, good.getProtectionLevel(),PMeth.goodBusinessTax(good), region.GOODBUSINESS, inputs);
		}
		else {
			return tools.protLevels("Business", error, service.getProtectionLevel(), PMeth.serviceBusinessTax(service), region.SERVICEBUSINESS, inputs);
		}
	}
	
	@Override
	public Prompt acceptInput(ConversationContext arg0, String arg) {
		int answer = 0;
		if (arg.startsWith("/")) {
			arg = arg.substring(1);
		}
		String[] args = arg.split(" ");
		if (arg.equalsIgnoreCase("back")) {
			return new HudConversationMain(plugin, player, 0);
		}
		try {
			answer = Integer.decode(args[0])-1;
		}
		catch (Exception ex) {
			return new ManageBusiness1(plugin, player,1);
		}
		
		if (answer > inputs.size()-1) {
			return new ManageBusiness1(plugin, player, 2);
		}
		
		if (inputs.get(answer).equals("Set <level>")) {
			if (args.length != 2) {
				return new BusinessProtectionLevels(plugin, player, 3, businessname);
			}
			else {
				try {
					int level = Integer.decode(args[1]);

					if (isGoodBusiness) {		
						good.setProtectionLevel(level);
					}
					else {
						service.setProtectionLevel(level);
					}
				}
				catch(Exception ex) {
					return new BusinessProtectionLevels(plugin ,player, 17, businessname);
				}
			}
		}
		
		return new BusinessProtectionLevels(plugin ,player, 2, businessname);
	}
}