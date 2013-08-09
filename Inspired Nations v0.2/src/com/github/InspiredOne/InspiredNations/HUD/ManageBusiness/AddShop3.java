package com.github.InspiredOne.InspiredNations.HUD.ManageBusiness;

import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.HUD.Menu;
import com.github.InspiredOne.InspiredNations.Regions.ChestShop;
import com.github.InspiredOne.InspiredNations.Regions.GoodBusiness;
import com.github.InspiredOne.InspiredNations.Tools.optionType;

public class AddShop3 extends Menu {

	// Constructor
	public AddShop3(InspiredNations instance, Player playertemp, int errortemp) {
		super(instance, playertemp, errortemp);
		town = PDI.getTownResides();
	}
	
	@Override
	public String getPromptText(ConversationContext arg0) {
		String space = tools.space();
		String main = tools.header("Add Shop. Type the amount you want to sell.");
		String options = "";
		String end = tools.footer(false);
		String errmsg = ChatColor.RED + tools.errors.get(error) + names;
		
		options = tools.addLine(options, "Place a sign where you would like to sell from, then type 'finish'.", optionType.INSTRUCTION);
		
		return space + main + options + end + errmsg;
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
			return new AddShop3(plugin, player, 0);
		}
		if (arg.equalsIgnoreCase("back")) {
			PM.placesign = false;
			PM.legalsign = false;
			PM.onfallblock = false;
			PM.outside = false;
			PM.againstoutside = false;
			return new AddShop2(plugin, player, 0);
		}
		if (!PM.legalsign) {
			if (PM.onfallblock) {
				return new AddShop3(plugin, player, 74);
			}
			if (PM.outside) {
				return new AddShop3(plugin, player, 75);
			}
			if (PM.againstoutside) {
				return new AddShop3(plugin, player, 76);
			}
			return new AddShop3(plugin, player, 77);
		}
		
		if (arg.equalsIgnoreCase("finish")) {
			((GoodBusiness) arg0.getSessionData("business")).addChestShop(new ChestShop(PM.getItemType(), PM.cost, PM.quantity, PM.tempchests.clone()));
			return new ManageBusiness2(plugin, player, 0);
		}
		
		return new AddShop3(plugin, player, 0);
	}




}
