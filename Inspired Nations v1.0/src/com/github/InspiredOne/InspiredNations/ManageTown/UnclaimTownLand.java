package com.github.InspiredOne.InspiredNations.ManageTown;

import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.HUD.Menu;
import com.github.InspiredOne.InspiredNations.Tools.mapSize;
import com.github.InspiredOne.InspiredNations.Tools.optionType;

public class UnclaimTownLand extends Menu {
	
	// Constructor
	public UnclaimTownLand(InspiredNations instance, Player playertemp, int errortemp) {
		super(instance, playertemp, errortemp);
		town = PDI.getTownMayored();
	}
	
	@Override
	public String getPromptText(ConversationContext arg0) {
		String space = tools.space();
		String main = tools.header("Unclaim Town Land. Type what you would like to do.");
		String options = "";
		String end = tools.footer(true);
		String errmsg = ChatColor.RED + tools.errors.get(error);
		
		// Make inputs vector
		
		// Make options text
		if(!PM.townDeselect()) {
			options = tools.addLine(options, "Type 'begin' to begin unclaiming land", optionType.INSTRUCTION);
		}
		else{
			options = tools.addLine(options, "Type 'stop' to stop unclaiming land", optionType.INSTRUCTION);
		}
			
		options = tools.addDivider(options);
		options = options.concat(tools.drawTownMap(player, mapSize.LARGE));
		
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
			return new UnclaimTownLand(plugin, player, 0);
		}
		if (arg.equalsIgnoreCase("back")) {
			PM.predetown(false);
			PM.detown(false);
			return new ManageTown(plugin, player, 0);
		}
		else if(arg.equalsIgnoreCase("begin")) {
			PM.detown(true);
			return new UnclaimTownLand(plugin, player, 0);
		}
		else if(arg.equalsIgnoreCase("stop")) {
			PM.detown(false);
			return new UnclaimTownLand(plugin, player, 0);
		}
		return new UnclaimTownLand(plugin, player, 2);
	}



}
