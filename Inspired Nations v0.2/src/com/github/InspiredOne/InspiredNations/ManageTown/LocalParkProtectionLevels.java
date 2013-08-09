package com.github.InspiredOne.InspiredNations.ManageTown;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.HUD.Menu;
import com.github.InspiredOne.InspiredNations.Regions.Park;
import com.github.InspiredOne.InspiredNations.Tools.region;

public class LocalParkProtectionLevels extends Menu {

	// Constructor
	public LocalParkProtectionLevels(InspiredNations instance, Player playertemp, int errortemp) {
		super(instance, playertemp, errortemp);
		town = PDI.getTownMayored();
		park = (Park) PDI.getConversation().getContext().getSessionData("localpark");
	}
	
	@Override
	public String getPromptText(ConversationContext arg0) {
		// Make Inputs Vector
		inputs.add("Set Level <level>");
		
		return tools.protLevels(park, player,"Park", error, park.getProtectionLevel(), region.PARK, inputs);
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
			return new LocalParkProtectionLevels(plugin, player, 0);
		}
		if (arg.equalsIgnoreCase("back")) {
			return new ManagePark2(plugin, player, 0);
		}
		try {
			answer = Integer.decode(args[0])-1;
		}
		catch (Exception ex) {
			return new LocalParkProtectionLevels(plugin, player, 1);
		}
		
		if (answer > inputs.size()-1) {
			return new LocalParkProtectionLevels(plugin, player, 2);
		}
		
		if (inputs.get(answer).equals("Set Level <level>")) {
			if (args.length != 2) {
				return new LocalParkProtectionLevels(plugin, player, 3);
			}
			else {
				try {
					int level = Integer.decode(args[1]);

					park.setProtectionLevel(level);
					return new LocalParkProtectionLevels(plugin, player, 0);
				}
				catch(Exception ex) {
					return new LocalParkProtectionLevels(plugin ,player,17);
				}
			}
		}
		
		return new LocalParkProtectionLevels(plugin, player, 2);
	}



}
