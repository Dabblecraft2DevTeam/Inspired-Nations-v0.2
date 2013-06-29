package com.github.InspiredOne.InspiredNations.HUD;

import java.util.Vector;

import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.Regions.GoodBusiness;
import com.github.InspiredOne.InspiredNations.Regions.ServiceBusiness;
import com.github.InspiredOne.InspiredNations.Tools.menuType;

public class Jobs extends Menu {

	Vector<String> businesses = new Vector<String>();
	// Constructor
	public Jobs(InspiredNations instance, Player playertemp, int errortemp) {
		super(instance, playertemp, errortemp);
		
		for(GoodBusiness business:PDI.getGoodBusinessOwned()) {
			businesses.add(business.getName() + "/" + PDI.getCountryResides().getTowns().get(business.getTown()).getName());
		}
		for(ServiceBusiness business:PDI.getServiceBusinessOwned()) {
			businesses.add(business.getName() + "/" + PDI.getCountryResides().getTowns().get(business.getTown()).getName());
		}
		
	}
	
	@Override
	public String getPromptText(ConversationContext arg0) {
		String space = tools.space();
		String main = tools.header("Jobs. Type an option number.");
		String options = "";
		String end = tools.footer(false);
		String errmsg = ChatColor.RED + tools.errors.get(error) + names;
		
		// Make Inputs Vector
		inputs.add("Request Owner <job's town> <job's name>");
		inputs.add("Request Job <job's town> <job's name>");
		inputs.add("Quit Owner <job's town> <job's name>");
		inputs.add("Quit Job <job's town> / <job's name>");
		inputs.add("Job Offers " + "(" + menuType.OPTIONDESCRIP + 
				(PMeth.getJobOffers().size() + PMeth.getOwnerOffers().size()) + menuType.OPTION + ")");
		
		options = options.concat(menuType.LABEL + "Owner: " + menuType.VALUE + tools.format(businesses) + "\n");
		options = options.concat(menuType.LABEL + "Jobs: " + menuType.VALUE + tools.format(PMeth.getEmployed()) + "\n");
		options = options.concat(menuType.LABEL + "Owner Requests: " + menuType.VALUE + tools.format(PMeth.getOwnerRequests()) + "\n");
		options = options.concat(menuType.LABEL + "Job Requests: " + menuType.VALUE + tools.format(PMeth.getJobRequests()) + "\n");
		
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
			return new Jobs(plugin, player, 0);
		}
		if (arg.equalsIgnoreCase("back")) {
			return new HudConversationMain(plugin, player, 0);
		}
		try {
			answer = Integer.decode(arg)-1;
		}
		catch (Exception ex) {
			return new Jobs(plugin, player,1);
		}
		
		if (answer > inputs.size()-1) {
			return new Jobs(plugin, player, 2);
		}
		
		if (inputs.get(answer).equals("Job Offers " + "(" + ChatColor.GRAY + 
				(PMeth.getJobOffers().size() + PMeth.getOwnerOffers().size()) + ChatColor.GREEN + ")")) {
			return new JobOffers(plugin, player, 0);
		}
		
		return new Jobs(plugin, player, 2);
	}



}
