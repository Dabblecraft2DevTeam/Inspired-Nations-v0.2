package com.github.InspiredOne.InspiredNations.ManageTown;

import java.util.Vector;

import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.entity.Player;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.PlayerModes;
import com.github.InspiredOne.InspiredNations.Tools;
import com.github.InspiredOne.InspiredNations.Tools.optionType;
import com.github.InspiredOne.InspiredNations.Tools.region;
import com.github.InspiredOne.InspiredNations.HUD.HudConversationMain;
import com.github.InspiredOne.InspiredNations.HUD.SelectBusiness3;
import com.github.InspiredOne.InspiredNations.HUD.SelectHouse2;
import com.github.InspiredOne.InspiredNations.Regions.Cuboid;
import com.github.InspiredOne.InspiredNations.Regions.Town;
import com.github.InspiredOne.InspiredNations.Regions.polygonPrism;

public class InvalidSelection extends StringPrompt{

	InspiredNations plugin;
	Tools tools;
	Player player;
	PlayerData PDI;
	PlayerModes PM;
	Town town;
	region Region;
	
	Vector<String> inputs = new Vector<String>();
	int error;
	int selectionError;
	
	// Constructor
	public InvalidSelection(InspiredNations instance, Player playertemp, int errortemp, Object object, region type) {
		plugin = instance;
		player = playertemp;
		tools = new Tools(plugin);
		PDI = plugin.playerdata.get(player.getName());
		PM = plugin.playermodes.get(player.getName());
		error = errortemp;
		town = PDI.getTownMayored();
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
		inputs.add("Reclaim");
		inputs.add("Cancel");
		
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
			case SERVICEBUSINESS:
				PM.serviceBusiness(true);
			case PARK:
				PM.park(true);
				return new SelectPark2(plugin ,player, 0);
			case BANK:
				PM.localBank(true);
				return new SelectBank2(plugin, player, 0);
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
			}
		}
		else if(inputs.get(answer).equals("Cancel")) {
			PM.selectCuboid(false);
			PM.selectPolygon(false);
			PM.setBlocksBack();
			PM.goodBusiness(false);
			PM.serviceBusiness(false);
			PM.house(false);
			PM.town(false);
			PM.localBank(false);
			PM.localPrison(false);
			PM.park(false);
			PM.country(false);
			PM.federalPark(false);
			PM.reSelectHouse = false;
			PM.reSelectGoodBusiness = false;
			PM.reSelectServiceBusiness = false;
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
			}
		}
		return new InvalidSelection(plugin, player, 2, selectionError, Region);
	}


}
