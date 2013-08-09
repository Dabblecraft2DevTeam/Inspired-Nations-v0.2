package com.github.InspiredOne.InspiredNations.HUD.ManageCountry;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.HUD.InvalidSelection;
import com.github.InspiredOne.InspiredNations.HUD.Menu;
import com.github.InspiredOne.InspiredNations.Regions.Cuboid;
import com.github.InspiredOne.InspiredNations.Regions.Park;
import com.github.InspiredOne.InspiredNations.Regions.polygonPrism;
import com.github.InspiredOne.InspiredNations.Tools.region;

public class SelectFederalPark2 extends Menu {

	Cuboid cube;
	polygonPrism poly;
	// Constructor
	public SelectFederalPark2(InspiredNations instance, Player playertemp, int errortemp) {
		super(instance, playertemp, errortemp);
		country = PDI.getCountryRuled();
		if(PM.isReSelectFederalPark()) {
			park = (Park) PDI.getConversation().getContext().getSessionData("federalpark");
			if(park.isCubeSpace()) {
				cube = park.getCubeSpace();
			}
			else {
				poly = park.getPolySpace();
			}
		}
	}
	
	@Override
	public String getPromptText(ConversationContext arg0) {
		return tools.writeRegionSelection2("park", error, PM);
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
			return new SelectFederalPark1(plugin, player, 0);
		}
		String[] args = arg.split(" ");
		if (args[0].equalsIgnoreCase("say"))  {
			if(args.length > 1) {
				PMeth.SendChat(tools.formatSpace(tools.subArray(args, 1, args.length - 1)));
			}
			return new SelectFederalPark2(plugin, player, 0);
		}
		else if(arg.equalsIgnoreCase("cancel")) {
			PM.setBlocksBack();
			PM.selectCuboid(false);
			PM.selectPolygon(false);
			PM.setReSelectFederalPark(false);
			PM.federalPark(false);
			PM.setPolygon(new polygonPrism(player.getWorld().getName()));
			PM.setCuboid(new Cuboid(player.getWorld().getName()));
			return new CountryGovernmentRegions(plugin, player, 0);
		}
		else if(arg.equalsIgnoreCase("finish")) {
			if(PM.isReSelectFederalPark()) {
				park.setCubeSpace(null);
				park.setPolySpace(null);
			}
			if(tools.selectionValid(player, region.FEDERALPARK)) {
				if(PM.isReSelectFederalPark()) {
					if(PM.isSelectingCuboid()){
						park.setCubeSpace(PM.getCuboid());
					}
					else {
						park.setPolySpace(PM.getPolygon());
					}
				}
				PM.selectCuboid(false);
				PM.selectPolygon(false);
				PM.setBlocksBack();
				PM.federalPark(false);
				PM.setReSelectFederalPark(false);
				PM.setPolygon(new polygonPrism(player.getWorld().getName()));
				PM.setCuboid(new Cuboid(player.getWorld().getName()));
				return new ManageFederalPark1(plugin, player, 0);
				
			}
			else {
				if(PM.isReSelectFederalPark()) {
					if(PM.isSelectingCuboid()) {
						park.setCubeSpace(cube);
						park.setPolySpace(poly);
					}
				}
				PM.federalPark(false);
				PM.setReSelectFederalPark(false);
				PM.setBlocksBack();
				return new InvalidSelection(plugin, player, 0, arg0.getSessionData("error"), region.REFEDERALPARK);
			}
		}
		return new SelectFederalPark2(plugin, player, 2);
	}



}
