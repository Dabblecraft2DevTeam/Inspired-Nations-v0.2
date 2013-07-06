package com.github.InspiredOne.InspiredNations.ManageTown;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.HUD.InvalidSelection;
import com.github.InspiredOne.InspiredNations.HUD.Menu;
import com.github.InspiredOne.InspiredNations.Regions.Cuboid;
import com.github.InspiredOne.InspiredNations.Regions.polygonPrism;
import com.github.InspiredOne.InspiredNations.Tools.region;

public class SelectBank2 extends Menu {
	Cuboid cube;
	polygonPrism poly;
	// Constructor
	public SelectBank2(InspiredNations instance, Player playertemp, int errortemp) {
		super(instance, playertemp, errortemp);
		town = PDI.getTownMayored();
		if (town.hasBank()) {
			if (town.getBank().isCubeSpace()) {
				cube = town.getBank().getCubeSpace();
			} else {
				poly = town.getBank().getPolySpace();
			}
		}
	}
	
	@Override
	public String getPromptText(ConversationContext arg0) {
		return tools.writeRegionSelection2("bank", error, PM);
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
			return new SelectBank1(plugin, player, 0);
		}
		
		String[] args = arg.split(" ");
		if (args[0].equalsIgnoreCase("say"))  {
			if(args.length > 1) {
				PMeth.SendChat(tools.formatSpace(tools.subArray(args, 1, args.length - 1)));
			}
			return new SelectBank2(plugin, player, 0);
		}
		else if(arg.equalsIgnoreCase("cancel")) {
			PM.setBlocksBack();
			PM.selectCuboid(false);
			PM.selectPolygon(false);
			PM.localBank(false);
			PM.setPolygon(new polygonPrism(player.getWorld().getName()));
			PM.setCuboid(new Cuboid(player.getWorld().getName()));
			return new TownGovernmentRegions(plugin, player, 0);
		}
		else if(arg.equalsIgnoreCase("finish")) {
			if(town.hasBank()){
				town.getBank().setCubeSpace(null);
				town.getBank().setPolySpace(null);
			}
			if(tools.selectionValid(player, region.BANK)) {
				if(PM.isSelectingCuboid()) {
					town.getBank().setCubeSpace(PM.getCuboid());
				}
				else {
					town.getBank().setPolySpace(PM.getPolygon());
				}
				PM.selectCuboid(false);
				PM.selectPolygon(false);
				PM.setBlocksBack();
				PM.localBank(false);
				PM.setPolygon(new polygonPrism(player.getWorld().getName()));
				PM.setCuboid(new Cuboid(player.getWorld().getName()));
				return new ManageBank(plugin, player, 0);
				
			}
			else {
				if(town.hasBank()){
					town.getBank().setCubeSpace(cube);
					town.getBank().setPolySpace(poly);
				}
				PM.localBank(false);
				PM.setBlocksBack();
				return new InvalidSelection(plugin, player, 0, arg0.getSessionData("error"), region.BANK);
			}
		}
		return new SelectBank2(plugin, player, 2);
	}





}
