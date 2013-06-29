package com.github.InspiredOne.InspiredNations.HUD;

import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.Tools.menuType;

public class JobOffers extends Menu {

	// Constructor
	public JobOffers(InspiredNations instance, Player playertemp, int errortemp) {
		super(instance, playertemp, errortemp);
	}
	
	@Override
	public String getPromptText(ConversationContext arg0) {
		String space = tools.space();
		String main = tools.header("Jobs. Type an option number.");
		String options = "";
		String end = tools.footer(false);
		String errmsg =  ChatColor.RED + tools.errors.get(error) + names;
		
		inputs.add("Accept Owner <job's town> / <job's name>");
		inputs.add("Accept Job <job's town> / <job's name>");
		inputs.add("Reject Owner <job's town> / <job's name>");
		inputs.add("Reject Job <job's town> / <job's name>");
		
		options = options.concat(menuType.LABEL + "Owner Offers: " + menuType.VALUE + tools.format(PMeth.getOwnerOffers()) + "\n");
		options = options.concat(menuType.LABEL+ "Job Offers: " + menuType.VALUE + tools.format(PMeth.getJobOffers()) + "\n");
		
		options = tools.addDivider(options);
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
			return new JobOffers(plugin, player, 0);
		}
		if (arg.equalsIgnoreCase("back")) {
			return new Jobs(plugin, player, 0);
		}
		try {
			answer = Integer.decode(arg)-1;
		}
		catch (Exception ex) {
			return new JobOffers(plugin, player,1);
		}
		
		if (answer > inputs.size()-1) {
			return new JobOffers(plugin, player, 2);
		}
		
		return new JobOffers(plugin, player, 2);
	}



}
