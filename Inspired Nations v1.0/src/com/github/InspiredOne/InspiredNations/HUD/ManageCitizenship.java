package com.github.InspiredOne.InspiredNations.HUD;

import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.Tools.menuType;

public class ManageCitizenship extends Menu {

	// Constructor
	public ManageCitizenship(InspiredNations instance, Player playertemp, int errortemp) {
		super(instance, playertemp, errortemp);
	}
	
	@Override
	public String getPromptText(ConversationContext arg0) {
		String space = tools.space();
		String main = tools.header("Manage Citizenship. Type an option number.");
		String options = "";
		String end = tools.footer(false);
		String errmsg = ChatColor.RED + tools.errors.get(error) + names;
		
		// Make Inputs Vector

		if(PDI.getIsCountryResident()) {
			inputs.add("Join Country <country> " + menuType.OPTIONDESCRIP + "You will lose your regions!");
			inputs.add("Join Town <town>");
		}
		else {
			inputs.add("Join Country <country>");
		}
		
		// Make options text
		if (PDI.getIsCountryResident()) {
			options = options.concat(menuType.LABEL + "Country: " + menuType.VALUE + PDI.getCountryResides().getName() + "\n");
		}
		if (PDI.getIsTownResident()) {
			options = options.concat(menuType.LABEL + "Town: " + menuType.VALUE + PDI.getTownResides().getName() + "\n");
		}
		if (PDI.getIsCountryResident()) {
			options = tools.addDivider(options);
		}
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
			return new ManageCitizenship(plugin, player, 0);
		}
		if (arg.equalsIgnoreCase("back")) {
			return new HudConversationMain(plugin, player, 0);
		}
		try {
			answer = Integer.decode(arg)-1;
		}
		catch (Exception ex) {
			return new ManageCitizenship(plugin, player,1);
		}
		
		if (answer > inputs.size()-1) {
			return new ManageCitizenship(plugin, player, 2);
		}
		return new ManageCitizenship(plugin, player, 2);
	}



}
