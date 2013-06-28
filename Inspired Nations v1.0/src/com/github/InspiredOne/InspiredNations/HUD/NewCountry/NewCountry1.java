package com.github.InspiredOne.InspiredNations.HUD.NewCountry;

import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.HUD.HudConversationMain;
import com.github.InspiredOne.InspiredNations.HUD.Menu;
import com.github.InspiredOne.InspiredNations.Regions.Country;
import com.github.InspiredOne.InspiredNations.Tools.optionType;

public class NewCountry1 extends Menu {
	
	//TODO need to make this so that it considers if you own a country
	boolean permission = true;
	
	// Constructor
	public NewCountry1(InspiredNations instance, Player playertemp, int errortemp) {
		super(instance, playertemp, errortemp);
	}

	@Override
	public String getPromptText(ConversationContext arg0) {
		String space = tools.space();
		String main = tools.header("New Country. Read the instructions.");
		String options = "";
		String end = tools.footer(false);
		String errmsg = ChatColor.RED + tools.errors.get(error);
		
		options = tools.addLine(options, "Type the name of your new country.", optionType.INSTRUCTION);
		
		return space + main + options + end + errmsg;
	}
	
	@Override
	public Prompt acceptInput(ConversationContext arg0, String arg) {
		if (arg.startsWith("/")) {
			arg = arg.substring(1);
		}
		if (arg.equalsIgnoreCase("back")) {
			return new HudConversationMain(plugin, player, 0);
		}
		
		String[] args = arg.split(" ");
		if (args[0].equalsIgnoreCase("say"))  {
			if(args.length > 1) {
				PMeth.SendChat(tools.formatSpace(tools.subArray(args, 1, args.length - 1)));
			}
			return new NewCountry1(plugin, player, 0);
		}
		
		if (arg.contains("/")) {
			return new NewCountry1(plugin, player, 24);
		}
		
		if (!tools.countryUnique(arg)) {
			return new NewCountry1(plugin, player, 14);
		}
		else {
			PMeth.leaveCountry();
			new Country(plugin, arg.trim(), player.getName());
			return new NewCountry2(plugin, player, 0);
		}
	}
}
