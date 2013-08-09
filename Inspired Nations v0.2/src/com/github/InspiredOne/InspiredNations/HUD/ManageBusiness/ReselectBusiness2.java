package com.github.InspiredOne.InspiredNations.HUD.ManageBusiness;


import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.HUD.InvalidSelection;
import com.github.InspiredOne.InspiredNations.HUD.Menu;
import com.github.InspiredOne.InspiredNations.Regions.Cuboid;
import com.github.InspiredOne.InspiredNations.Regions.polygonPrism;
import com.github.InspiredOne.InspiredNations.Tools.region;

public class ReselectBusiness2 extends Menu {
	Cuboid cube = null;
	polygonPrism poly = null;
	// Constructor
	public ReselectBusiness2(InspiredNations instance, Player playertemp, int errortemp) {
		super(instance, playertemp, errortemp);
		town = PDI.getTownResides();
		if(this.busi.isCubeSpace()) {
			cube = this.busi.getCubeSpace();
		}
		else {
			poly = this.busi.getPolySpace();
		}
	}
	
	@Override
	public String getPromptText(ConversationContext arg0) {
		
		if (PM.isReSelectServiceBusiness()) {
			return tools.writeRegionSelection2("service business", error, PM);
		}
		else if (PM.isReSelectGoodBusiness()) {
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
			return new ManageBusiness2(plugin, player, 0);
		}
		String[] args = arg.split(" ");
		if (args[0].equalsIgnoreCase("say"))  {
			if(args.length > 1) {
				PMeth.SendChat(tools.formatSpace(tools.subArray(args, 1, args.length - 1)));
			}
			return new ReselectBusiness2(plugin, player, 0);
		}
		else if(arg.equalsIgnoreCase("cancel")) {
			PM.setBlocksBack();
			PM.selectCuboid(false);
			PM.selectPolygon(false);
			PM.setReSelectGoodBusiness(false);
			PM.setReSelectServiceBusiness(false);
			PM.setPolygon(new polygonPrism(player.getWorld().getName()));
			PM.setCuboid(new Cuboid(player.getWorld().getName()));
			return new ManageBusiness2(plugin, player, 0);
		}
		else if(arg.equalsIgnoreCase("finish")) {
			if (PM.isReSelectServiceBusiness()) {
				busi.setPolySpace(null);
				busi.setCubeSpace(null);
				if(tools.selectionValid(player, region.RESERVICE)) {
					if(PM.isSelectingCuboid()) {
						busi.setCubeSpace(PM.getCuboid());
					}
					else {
						busi.setPolySpace(PM.getPolygon());
					}
					PM.selectCuboid(false);
					PM.selectPolygon(false);
					PM.serviceBusiness(false);
					PM.setBlocksBack();
					PM.setPolygon(new polygonPrism(player.getWorld().getName()));
					PM.setCuboid(new Cuboid(player.getWorld().getName()));
					PM.setReSelectGoodBusiness(false);
					PM.setReSelectServiceBusiness(false);
					return new ManageBusiness2(plugin, player, 0);
				}
				else {
					busi.setPolySpace(poly);
					busi.setCubeSpace(cube);
					PM.setReSelectGoodBusiness(false);
					PM.setReSelectServiceBusiness(false);
					PM.setBlocksBack();
					return new InvalidSelection(plugin, player, 0, arg0.getSessionData("error"), region.RESERVICE);
				}
			}
			else if (PM.isReSelectGoodBusiness()) {
				busi.setPolySpace(null);
				busi.setCubeSpace(null);
				if(tools.selectionValid(player, region.REGOOD)) {
					if(PM.isSelectingCuboid()) {
						busi.setCubeSpace(PM.getCuboid());
					}
					else {
						busi.setPolySpace(PM.getPolygon());
					}
					PM.selectCuboid(false);
					PM.selectPolygon(false);
					PM.setBlocksBack();
					PM.setPolygon(new polygonPrism(player.getWorld().getName()));
					PM.setCuboid(new Cuboid(player.getWorld().getName()));
					PM.setReSelectGoodBusiness(false);
					PM.setReSelectServiceBusiness(false);
					return new ManageBusiness2(plugin, player, 0);
				}
				else {
					busi.setPolySpace(poly);
					busi.setCubeSpace(cube);
					PM.setReSelectGoodBusiness(false);
					PM.setReSelectServiceBusiness(false);
					PM.setBlocksBack();
					return new InvalidSelection(plugin, player, 0, arg0.getSessionData("error"), region.REGOOD);
				}
			}
		}
		return new ReselectBusiness2(plugin, player, 2);
	}



}
