package com.github.InspiredOne.InspiredNations.HUD;


import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.HUD.ManageHouse.ManageHouse1;
import com.github.InspiredOne.InspiredNations.ManageTown.InvalidSelection;
import com.github.InspiredOne.InspiredNations.ManageTown.TownGovernmentRegions;
import com.github.InspiredOne.InspiredNations.Regions.Cuboid;
import com.github.InspiredOne.InspiredNations.Regions.polygonPrism;
import com.github.InspiredOne.InspiredNations.Tools.region;

public class SelectHouse2 extends Menu {

	// Constructor
	public SelectHouse2(InspiredNations instance, Player playertemp, int errortemp) {
		super(instance, playertemp, errortemp);
		town = PDI.getTownResides();
	}
	
	@Override
	public String getPromptText(ConversationContext arg0) {
		return tools.writeRegionSelection2("house", error, PM);
	}
	
	@Override
	public Prompt acceptInput(ConversationContext arg0, String arg) {
		if (arg.startsWith("/")) {
			arg = arg.substring(1);
		}
		String[] args = arg.split(" ");
		if (args[0].equalsIgnoreCase("say"))  {
			if(args.length > 1) {
				PMeth.SendChat(tools.formatSpace(tools.subArray(args, 1, args.length - 1)));
			}
			return new SelectHouse2(plugin, player, 0);
		}
		
		if (arg.equalsIgnoreCase("back")) {
			PM.selectCuboid(false);
			PM.selectPolygon(false);
			PM.setBlocksBack();
			return new SelectHouse1(plugin, player, 0);
		}
		else if(arg.equalsIgnoreCase("cancel")) {
			PM.setBlocksBack();
			PM.selectCuboid(false);
			PM.selectPolygon(false);
			PM.house(false);
			PM.setPolygon(new polygonPrism(player.getWorld().getName()));
			PM.setCuboid(new Cuboid(player.getWorld().getName()));
			return new TownGovernmentRegions(plugin, player, 0);
		}
		else if(arg.equalsIgnoreCase("finish")) {
			if(tools.selectionValid(player, region.HOUSE)) {
				PM.selectCuboid(false);
				PM.selectPolygon(false);
				PM.setBlocksBack();
				PM.house(false);
				PM.setPolygon(new polygonPrism(player.getWorld().getName()));
				PM.setCuboid(new Cuboid(player.getWorld().getName()));
				return new ManageHouse1(plugin, player, 0);
				
			}
			else {
				PM.selectCuboid(false);
				PM.selectPolygon(false);
				PM.house(false);
				PM.setBlocksBack();
				return new InvalidSelection(plugin, player, 0, arg0.getSessionData("error"), region.HOUSE);
			}
		}
		return new SelectHouse2(plugin, player, 2);
	}
}
