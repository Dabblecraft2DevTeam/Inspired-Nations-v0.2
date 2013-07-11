package com.github.InspiredOne.InspiredNations.ManageTown;

import java.util.Vector;

import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.HUD.Menu;
import com.github.InspiredOne.InspiredNations.Regions.Cell;
import com.github.InspiredOne.InspiredNations.Tools.optionType;

public class AddCell extends Menu {

	// Constructor
	public AddCell(InspiredNations instance, Player playertemp, int errortemp) {
		super(instance, playertemp, errortemp);
		town = PDI.getTownMayored();
	}
	

	@Override
	public String getPromptText(ConversationContext arg0) {
		String space = tools.space();
		String main = tools.header("Manage Prison. Type an option number.");
		String options = "";
		String end = tools.footer(false);
		String errmsg = ChatColor.RED + tools.errors.get(error);
		
		// Make Options text
		
		options = tools.addLine(options, "Stand in the spot you would like prisoners to spawn and enter the name of the cell.", optionType.INSTRUCTION);
		
		return space + main + options + end + errmsg;
	}
	
	@Override
	public Prompt acceptInput(ConversationContext arg0, String arg) {
		if (arg.startsWith("/")) {
			arg = arg.substring(1);
		}
		if (arg.equalsIgnoreCase("back")) {
			return new ManagePrison(plugin, player, 0);
		}
		
		String[] args = arg.split(" ");
		if (args[0].equalsIgnoreCase("say"))  {
			if(args.length > 1) {
				PMeth.SendChat(tools.formatSpace(tools.subArray(args, 1, args.length - 1)));
			}
			return new AddCell(plugin, player, 0);
		}
		
		Vector<String> cellnames = new Vector<String>();
		
		cellnames.addAll(town.getPrison().getCells().keySet());
		
		if(cellnames.contains(arg)) {
			return new AddCell(plugin, player, 71);
		}
		else {
			if(town.getPrison().isIn(player.getLocation())){
				town.getPrison().addCell(arg, new Cell(player.getLocation(), arg));
				return new ManagePrison(plugin, player, 0);
			}
			else {
				return new AddCell(plugin, player, 72);
			}
		}
	}
}
