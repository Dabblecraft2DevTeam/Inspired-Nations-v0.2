package com.github.InspiredOne.InspiredNations.HUD.ManageHouse;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.HUD.Menu;
import com.github.InspiredOne.InspiredNations.Regions.House;
import com.github.InspiredOne.InspiredNations.Tools.region;

public class HouseProtectionLevels extends Menu {
	
	// Constructor
	public HouseProtectionLevels(InspiredNations instance, Player playertemp, House house, int errortemp) {
		super(instance, playertemp, errortemp);
		this.house = house;
	}
	
	@Override
	public String getPromptText(ConversationContext arg0) {
		
		// Make Inputs Vector
		inputs.add("Set Level <level>");
		
		return tools.protLevels(house, player,"House", error, house.getProtectionLevel(), region.HOUSE, inputs);

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
			return new HouseProtectionLevels(plugin, player, house, 0);
		}
		if (arg.equalsIgnoreCase("back")) {
			return new ManageHouse2(plugin, player, house, 0);
		}
		try {
			answer = Integer.decode(args[0])-1;
		}
		catch (Exception ex) {
			return new HouseProtectionLevels(plugin, player,house, 1);
		}
		
		if (answer > inputs.size()-1) {
			return new HouseProtectionLevels(plugin, player,house, 2);
		}
		
		if (inputs.get(answer).equals("Set Level <level>")) {
			if (args.length != 2) {
				return new HouseProtectionLevels(plugin, player, house, 3);
			}
			else {
				try {
					int level = Integer.decode(args[1]);

					house.setProtectionLevel(level);
				}
				catch(Exception ex) {
					return new HouseProtectionLevels(plugin ,player, house, 17);
				}
			}
		}
		
		return new HouseProtectionLevels(plugin ,player, house, 2);
	}



}
