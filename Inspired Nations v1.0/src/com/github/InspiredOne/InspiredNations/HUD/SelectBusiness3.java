package com.github.InspiredOne.InspiredNations.HUD;

import java.util.Vector;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.entity.Player;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.PlayerModes;
import com.github.InspiredOne.InspiredNations.Tools;
import com.github.InspiredOne.InspiredNations.HUD.ManageBusiness.ManageBusiness1;
import com.github.InspiredOne.InspiredNations.ManageTown.InvalidSelection;
import com.github.InspiredOne.InspiredNations.ManageTown.TownGovernmentRegions;
import com.github.InspiredOne.InspiredNations.Regions.Cuboid;
import com.github.InspiredOne.InspiredNations.Regions.Town;
import com.github.InspiredOne.InspiredNations.Regions.polygonPrism;
import com.github.InspiredOne.InspiredNations.Tools.region;

public class SelectBusiness3 extends StringPrompt {


	InspiredNations plugin;
	Tools tools;
	Player player;
	PlayerData PDI;
	PlayerModes PM;
	Town town;
	
	Vector<String> inputs = new Vector<String>();
	int error;
	
	// Constructor
	public SelectBusiness3(InspiredNations instance, Player playertemp, int errortemp) {
		plugin = instance;
		player = playertemp;
		tools = new Tools(plugin);
		PDI = plugin.playerdata.get(player.getName());
		PM = plugin.playermodes.get(player.getName());
		error = errortemp;
		town = PDI.getTownResides();
	}
	
	@Override
	public String getPromptText(ConversationContext arg0) {
		
		if (PM.serviceBusinessSelect()) {
			return tools.writeRegionSelection2("service business", error, PM);
		}
		else if (PM.goodBusinessSelect()) {
			return tools.writeRegionSelection2("good business", error, PM);
		}
		return null;
	}
	
	@Override
	public Prompt acceptInput(ConversationContext arg0, String arg) {
		if (arg.startsWith("/")) {
			arg = arg.substring(1);
		}
		if (arg.equalsIgnoreCase("back")) {
			PM.selectCuboid(false);
			PM.selectPolygon(false);
			PM.setBlocksBack();
			return new SelectBusiness2(plugin, player, 0);
		}
		else if(arg.equalsIgnoreCase("cancel")) {
			PM.setBlocksBack();
			PM.selectCuboid(false);
			PM.selectPolygon(false);
			PM.serviceBusiness(false);
			PM.goodBusiness(false);
			PM.setPolygon(new polygonPrism(player.getWorld().getName()));
			PM.setCuboid(new Cuboid(player.getWorld().getName()));
			return new HudConversationMain(plugin, player, 0);
		}
		else if(arg.equalsIgnoreCase("finish")) {
			if (PM.serviceBusinessSelect()) {
				if(tools.selectionValid(player, region.SERVICEBUSINESS)) {
					PM.selectCuboid(false);
					PM.selectPolygon(false);
					PM.setBlocksBack();
					PM.setPolygon(new polygonPrism(player.getWorld().getName()));
					PM.setCuboid(new Cuboid(player.getWorld().getName()));
					return new ManageBusiness1(plugin, player, 0);
					
				}
				else {
					PM.house(false);
					PM.setBlocksBack();
					return new InvalidSelection(plugin, player, 0, arg0.getSessionData("error"), region.SERVICEBUSINESS);
				}
			}
			else if (PM.goodBusinessSelect()) {
				if(tools.selectionValid(player, region.GOODBUSINESS)) {
					PM.selectCuboid(false);
					PM.selectPolygon(false);
					PM.setBlocksBack();
					PM.setPolygon(new polygonPrism(player.getWorld().getName()));
					PM.setCuboid(new Cuboid(player.getWorld().getName()));
					return new ManageBusiness1(plugin, player, 0);
					
				}
				else {
					PM.house(false);
					PM.setBlocksBack();
					return new InvalidSelection(plugin, player, 0, arg0.getSessionData("error"), region.GOODBUSINESS);
				}
			}
		}
		return new SelectHouse2(plugin, player, 2);
	}



}
