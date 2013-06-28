package com.github.InspiredOne.InspiredNations.ManageTown;

import java.math.BigDecimal;
import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.TownMethods;
import com.github.InspiredOne.InspiredNations.HUD.Menu;
import com.github.InspiredOne.InspiredNations.Tools.mapSize;
import com.github.InspiredOne.InspiredNations.Tools.optionType;
import com.github.InspiredOne.InspiredNations.TownMethods.taxType;

public class ClaimTownLand extends Menu {

	// Constructor
	public ClaimTownLand(InspiredNations instance, Player playertemp, int errortemp) {
		super(instance, playertemp, errortemp);
		town = PDI.getTownMayored();
		TM = new TownMethods(plugin, town);
		if (town.getMoney().compareTo(TM.getCostPerChunk(taxType.OLD).multiply(new BigDecimal(plugin.taxTimer.getFractionLeft()))) < 0) {
			error = 25;
		}
	}
	
	@Override
	public String getPromptText(ConversationContext arg0) {
		String space = tools.space();
		String main = tools.header("Claim Town Land. Type what you would like to do.");
		String options = "";
		String end = tools.footer(true);
		String errmsg = ChatColor.RED + tools.errors.get(error);
		
		if(!PM.townSelect()) {
			options = tools.addLine(options, "Type 'begin' to begin claiming land", optionType.INSTRUCTION);
		}
		else{
			options = tools.addLine(options, "Type 'stop' to stop claiming land", optionType.INSTRUCTION);
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
			return new ClaimTownLand(plugin, player, 0);
		}
		if (arg.equalsIgnoreCase("back")) {
			PM.town(false);
			PM.preTown(false);
			return new ManageTown(plugin, player, 0);
		}
		else if(arg.equalsIgnoreCase("begin")) {
			PM.town(true);
			return new ClaimTownLand(plugin, player, 0);
		}
		else if(arg.equalsIgnoreCase("stop")) {
			PM.town(false);
			return new ClaimTownLand(plugin, player, 0);
		}
		
		return new ClaimTownLand(plugin, player, 2) ;
	}


}
