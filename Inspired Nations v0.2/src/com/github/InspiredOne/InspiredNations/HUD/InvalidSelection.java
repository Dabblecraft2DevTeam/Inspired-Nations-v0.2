package com.github.InspiredOne.InspiredNations.HUD;

import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.Tools.optionType;
import com.github.InspiredOne.InspiredNations.Tools.region;
import com.github.InspiredOne.InspiredNations.HUD.ManageBusiness.ManageBusiness2;
import com.github.InspiredOne.InspiredNations.HUD.ManageBusiness.ReselectBusiness2;
import com.github.InspiredOne.InspiredNations.HUD.ManageCountry.ManageFederalPark2;
import com.github.InspiredOne.InspiredNations.HUD.ManageCountry.SelectFederalPark2;
import com.github.InspiredOne.InspiredNations.HUD.ManageHouse.ManageHouse2;
import com.github.InspiredOne.InspiredNations.HUD.ManageHouse.ReselectHouse2;
import com.github.InspiredOne.InspiredNations.ManageTown.ManagePark2;
import com.github.InspiredOne.InspiredNations.ManageTown.SelectBank2;
import com.github.InspiredOne.InspiredNations.ManageTown.SelectPark2;
import com.github.InspiredOne.InspiredNations.ManageTown.SelectPrison2;
import com.github.InspiredOne.InspiredNations.ManageTown.TownGovernmentRegions;
import com.github.InspiredOne.InspiredNations.Regions.Cuboid;
import com.github.InspiredOne.InspiredNations.Regions.polygonPrism;

public class InvalidSelection extends Menu {

	region Region;
	int selectionError;
	
	// Constructor
	public InvalidSelection(InspiredNations instance, Player playertemp, int errortemp, Object object, region type) {
		super(instance, playertemp, errortemp);
		town = PDI.getTownResides();
		Region = type;
		selectionError = (Integer) object;
	}
	
	@Override
	public String getPromptText(ConversationContext arg0) {
		String space = tools.space();
		String main = tools.header("Invalid Selection. Type an option number.");
		String options = "";
		String end = tools.footer(false);
		String errmsg = ChatColor.RED + tools.errors.get(error);
		
		// Make inputs vector
		inputs.setSize(2);
		inputs.set(0,"Reclaim");
		inputs.set(1,"Cancel");
		
		// Make options text
		options = tools.addLine(options, tools.errors.get(selectionError).substring(1), optionType.ALERT);
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
			return new InvalidSelection(plugin, player, 0, selectionError, Region);
		}
		
		if (arg.equalsIgnoreCase("back")) {
			PM.setBlocksBack();
			PM.setPolygon(new polygonPrism(player.getWorld().getName()));
			PM.setCuboid(new Cuboid(player.getWorld().getName()));
			switch(Region) {
			case PRISON: 
				PM.localPrison(true);
				return new SelectPrison2(plugin, player, 0);
			case HOUSE:
				PM.house(true);
				return new SelectHouse2(plugin, player, 0);
			case GOODBUSINESS:
				PM.goodBusiness(true);
				return new SelectBusiness3(plugin, player, 0);
			case SERVICEBUSINESS:
				PM.serviceBusiness(true);
				return new SelectBusiness3(plugin, player, 0);
			case PARK:
				PM.park(true);
				return new SelectPark2(plugin ,player, 0);
			case BANK:
				PM.localBank(true);
				return new SelectBank2(plugin, player, 0);
			case FEDERALPARK:
				PM.federalPark(true);
				return new SelectFederalPark2(plugin, player, 0);
			case REGOOD:
				PM.setReSelectGoodBusiness(true);
				return new ReselectBusiness2(plugin, player, 0);
			case RESERVICE:
				PM.setReSelectServiceBusiness(true);
				return new ReselectBusiness2(plugin, player, 0);
			case REHOUSE:
				PM.setReSelectHouse(true);
				return new ReselectHouse2(plugin, player, 0);
			case REPARK:
				PM.setReSelectLocalPark(true);
				PM.park(true);
				return new SelectPark2(plugin, player, 0);
			case REFEDERALPARK:
				PM.setReSelectFederalPark(true);
				PM.federalPark(true);
				return new SelectFederalPark2(plugin, player, 0);
			default:
				break;
			}
		}
		try {
			answer = Integer.decode(args[0])-1;
		}
		catch (Exception ex) {
			return new InvalidSelection(plugin, player,1, selectionError, Region);
		}
		
		if (answer > inputs.size()-1) {
			return new InvalidSelection(plugin, player, 2, selectionError, Region);
		}
		
		if(inputs.get(answer).equals("Reclaim")) {
			PM.setBlocksBack();
			PM.setPolygon(new polygonPrism(player.getWorld().getName()));
			PM.setCuboid(new Cuboid(player.getWorld().getName()));
			switch(Region) {
			case PRISON: 
				PM.localPrison(true);
				return new SelectPrison2(plugin, player, 0);
			case HOUSE:
				PM.house(true);
				return new SelectHouse2(plugin, player, 0);
			case GOODBUSINESS:
				PM.goodBusiness(true);
				return new SelectBusiness3(plugin, player, 0);
			case SERVICEBUSINESS:
				PM.serviceBusiness(true);
				return new SelectBusiness3(plugin, player, 0);
			case PARK:
				PM.park(true);
				return new SelectPark2(plugin, player, 0);
			case BANK:
				PM.localBank(true);
				return new SelectBank2(plugin, player, 0);
			case COUNTRY:
				break;
			case FEDERALPARK:
				PM.federalPark(true);
				return new SelectFederalPark2(plugin, player, 0);
			case REGOOD:
				PM.setReSelectGoodBusiness(true);
				return new ReselectBusiness2(plugin, player, 0);
			case RESERVICE:
				PM.setReSelectServiceBusiness(true);
				return new ReselectBusiness2(plugin, player, 0);
			case REHOUSE:
				PM.setReSelectHouse(true);
				return new ReselectHouse2(plugin, player, 0);
			case REPARK:
				PM.setReSelectLocalPark(true);
				PM.park(true);
				return new SelectPark2(plugin, player, 0);
			case REFEDERALPARK:
				PM.setReSelectFederalPark(true);
				PM.federalPark(true);
				return new SelectFederalPark2(plugin, player, 0);
			case TOWN:
				break;
			default:
				break;
			}
		}
		else if(inputs.get(answer).equals("Cancel")) {
			PM.selectCuboid(false);
			PM.selectPolygon(false);
			PM.setBlocksBack();
			PM.goodBusiness(false);
			PM.serviceBusiness(false);
			PM.setReSelectGoodBusiness(false);
			PM.setReSelectHouse(false);
			PM.setReSelectServiceBusiness(false);
			PM.house(false);
			PM.town(false);
			PM.localBank(false);
			PM.localPrison(false);
			PM.park(false);
			PM.country(false);
			PM.federalPark(false);
			PM.setReSelectHouse(false);
			PM.setReSelectGoodBusiness(false);
			PM.setReSelectServiceBusiness(false);
			PM.setReSelectLocalPark(false);
			PM.setReSelectFederalPark(false);
			PM.setPolygon(new polygonPrism(player.getWorld().getName()));
			PM.setCuboid(new Cuboid(player.getWorld().getName()));
			
			switch(Region) {
			case PRISON:
				return new TownGovernmentRegions(plugin, player, 0);
			case HOUSE:
				return new HudConversationMain(plugin, player, 0);
			case GOODBUSINESS:
				return new HudConversationMain(plugin, player, 0);
			case SERVICEBUSINESS:
				return new HudConversationMain(plugin, player, 0);
			case PARK:
				return new TownGovernmentRegions(plugin, player, 0);
			case BANK:
				return new TownGovernmentRegions(plugin, player, 0);
			case REGOOD:
				return new ManageBusiness2(plugin, player, 0);
			case RESERVICE:
				return new ManageBusiness2(plugin, player, 0);
			case REHOUSE:
				return new ManageHouse2(plugin, player, 0);
			case REPARK:
				return new ManagePark2(plugin, player, 0);
			case COUNTRY:
				break;
			case FEDERALPARK:
				return new ManageFederalPark2(plugin, player, 0);
			case REFEDERALPARK:
				return new ManageFederalPark2(plugin, player, 0);
			case TOWN:
				break;
			default:
				break;
			}
		}
		return new InvalidSelection(plugin, player, 2, selectionError, Region);
	}


}
