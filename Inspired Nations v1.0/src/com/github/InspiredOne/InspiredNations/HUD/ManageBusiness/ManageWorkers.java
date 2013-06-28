package com.github.InspiredOne.InspiredNations.HUD.ManageBusiness;

import java.util.Vector;

import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.HUD.Menu;

public class ManageWorkers extends Menu {

	// Constructor
	public ManageWorkers(InspiredNations instance, Player playertemp, int errortemp, String business) {
		super(instance, playertemp, errortemp, business);
		town = PDI.getTownResides();
	}
	
	// Constructor
	public ManageWorkers(InspiredNations instance, Player playertemp, int errortemp, String business, Vector<String> namestemp) {
		super(instance, playertemp, errortemp, business, namestemp);
		town = PDI.getTownResides();
	}
	
	@Override
	public String getPromptText(ConversationContext arg0) {
		String space = tools.space();
		String main = tools.header("Manage Workers. Type an option number.");
		String options = "";
		String end = tools.footer(false);
		String errmsg = ChatColor.RED + tools.errors.get(error) + names;
		
		
		
		return space + main + options + end + errmsg;
	}
	
	@Override
	public Prompt acceptInput(ConversationContext arg0, String arg1) {
		// TODO Auto-generated method stub
		return null;
	}



}
