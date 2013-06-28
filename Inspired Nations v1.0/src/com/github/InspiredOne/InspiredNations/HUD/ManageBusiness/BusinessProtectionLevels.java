package com.github.InspiredOne.InspiredNations.HUD.ManageBusiness;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.Tools.region;
import com.github.InspiredOne.InspiredNations.HUD.Menu;

public class BusinessProtectionLevels extends Menu {


	
	// Constructor
	public BusinessProtectionLevels(InspiredNations instance, Player playertemp, int errortemp, String business) {
		super(instance, playertemp, errortemp, business);
		town = PDI.getTownResides();
	}
	
	@Override
	public String getPromptText(ConversationContext arg0) {

		//make Inputs Vector
		inputs.add("Set <level>");
		
		if (isGoodBusiness) {
			return tools.protLevels("Business", error,good.getProtectionLevel(),tools.cut(PMeth.goodBusinessTax(good)), region.GOODBUSINESS, inputs);
		}
		else {
			return tools.protLevels("Business", error, service.getProtectionLevel(), tools.cut(PMeth.serviceBusinessTax(service)), region.SERVICEBUSINESS, inputs);
		}
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
			return new BusinessProtectionLevels(plugin, player, 0, businessname);
		}
		if (arg.equalsIgnoreCase("back")) {
			return new ManageBusiness2(plugin, player, 0, businessname);
		}
		try {
			answer = Integer.decode(args[0])-1;
		}
		catch (Exception ex) {
			return new BusinessProtectionLevels(plugin, player,1, businessname);
		}
		
		if (answer > inputs.size()-1) {
			return new BusinessProtectionLevels(plugin, player,2, businessname);
		}
		
		if (inputs.get(answer).equals("Set <level>")) {
			if (args.length != 2) {
				return new BusinessProtectionLevels(plugin, player, 3, businessname);
			}
			else {
				try {
					int level = Integer.decode(args[1]);

					if (isGoodBusiness) {		
						good.setProtectionLevel(level);
					}
					else {
						service.setProtectionLevel(level);
					}
				}
				catch(Exception ex) {
					return new BusinessProtectionLevels(plugin ,player, 17, businessname);
				}
			}
		}
		
		return new BusinessProtectionLevels(plugin ,player, 2, businessname);
	}
}