package com.github.InspiredOne.InspiredNations.HUD.ManageHouse;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.HUD.Menu;
import com.github.InspiredOne.InspiredNations.ManageTown.InvalidSelection;
import com.github.InspiredOne.InspiredNations.Regions.Cuboid;
import com.github.InspiredOne.InspiredNations.Regions.House;
import com.github.InspiredOne.InspiredNations.Regions.polygonPrism;
import com.github.InspiredOne.InspiredNations.Tools.region;

public class ReselectHouse2 extends Menu {

	polygonPrism poly;
	Cuboid cube;
	
	// Constructor
	public ReselectHouse2(InspiredNations instance, Player playertemp, int errortemp) {
		super(instance, playertemp, errortemp);
		this.house = (House) PDI.getConversation().getContext().getSessionData("house");
		if(house.isCubeSpace()) {
			cube = house.getCubeSpace();
		}
		else {
			poly = house.getPolySpace();
		}
	}
	
	@Override
	public String getPromptText(ConversationContext arg0) {
		return tools.writeRegionSelection2("House", error, PM);
	}

	
	@Override
	public Prompt acceptInput(ConversationContext arg0, String arg) {
		this.house = (House) arg0.getSessionData("house");
		if (arg.startsWith("/")) {
			arg = arg.substring(1);
		}
		String[] args = arg.split(" ");
		if (args[0].equalsIgnoreCase("say"))  {
			if(args.length > 1) {
				PMeth.SendChat(tools.formatSpace(tools.subArray(args, 1, args.length - 1)));
			}
			return new ReselectHouse2(plugin, player, 0);
		}
		
		if (arg.equalsIgnoreCase("back")) {
			PM.selectCuboid(false);
			PM.selectPolygon(false);
			PM.setBlocksBack();
			return new ReselectHouse1(plugin, player, 0);
		}
		else if(arg.equalsIgnoreCase("cancel")) {
			PM.setBlocksBack();
			PM.selectCuboid(false);
			PM.selectPolygon(false);
			PM.setReSelectHouse(false);
			PM.setPolygon(new polygonPrism(player.getWorld().getName()));
			PM.setCuboid(new Cuboid(player.getWorld().getName()));
			return new ManageHouse2(plugin, player, 0);
		}
		else if(arg.equalsIgnoreCase("finish")) {
			house.setCubeSpace(null);
			house.setPolySpace(null);
			if(tools.selectionValid(player, region.REHOUSE)) {
				if(PM.isSelectingCuboid()) {
					house.setCubeSpace(PM.getCuboid());
				}
				else {
					house.setPolySpace(PM.getPolygon());
				}
				PM.selectCuboid(false);
				PM.selectPolygon(false);
				PM.setBlocksBack();
				PM.setReSelectHouse(false);
				PM.setPolygon(new polygonPrism(player.getWorld().getName()));
				PM.setCuboid(new Cuboid(player.getWorld().getName()));
				return new ManageHouse1(plugin, player, 0);
			}
			else {
				house.setCubeSpace(cube);
				house.setPolySpace(poly);
				PM.setReSelectHouse(false);
				PM.setBlocksBack();
				return new InvalidSelection(plugin, player, 0, arg0.getSessionData("error"), region.REHOUSE);
			}
		}
		return new ReselectHouse2(plugin, player, 2);
	}


}
