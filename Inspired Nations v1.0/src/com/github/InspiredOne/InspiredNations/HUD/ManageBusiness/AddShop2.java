package com.github.InspiredOne.InspiredNations.HUD.ManageBusiness;

import java.math.BigDecimal;

import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.HUD.Menu;
import com.github.InspiredOne.InspiredNations.Tools.optionType;

public class AddShop2 extends Menu {

	// Constructor
	public AddShop2(InspiredNations instance, Player playertemp, int errortemp) {
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
		
		// Make Options text
		
		options = tools.addLine(options, "Type the amount that you would like to sell this for.", optionType.INSTRUCTION);
		
		return space + main + options + end + errmsg;
	}
	
	@Override
	public Prompt acceptInput(ConversationContext arg0, String arg) {
		BigDecimal answer = BigDecimal.ZERO;
		if (arg.startsWith("/")) {
			arg = arg.substring(1);
		}
		String[] args = arg.split(" ");
		if (args[0].equalsIgnoreCase("say"))  {
			if(args.length > 1) {
				PMeth.SendChat(tools.formatSpace(tools.subArray(args, 1, args.length - 1)));
			}
			return new AddShop2(plugin, player, 0);
		}
		if (arg.equalsIgnoreCase("back")) {
			return new AddShop1(plugin, player, 0);
		}
		try {
			answer = new BigDecimal(args[0]);
			PM.cost = answer;
			PM.placesign = true;
			return new AddShop3(plugin, player, 0);
		}
		catch (Exception ex) {
			return new AddShop2(plugin, player,1);
		}
	}



}
