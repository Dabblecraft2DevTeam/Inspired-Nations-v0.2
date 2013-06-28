package com.github.InspiredOne.InspiredNations.HUD.ManageBusiness;

import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.HUD.HudConversationMain;
import com.github.InspiredOne.InspiredNations.HUD.Menu;
import com.github.InspiredOne.InspiredNations.Regions.GoodBusiness;
import com.github.InspiredOne.InspiredNations.Regions.ServiceBusiness;

public class ManageBusiness1 extends Menu implements Prompt {

	// Constructor
	public ManageBusiness1(InspiredNations instance, Player playertemp, int errortemp) {
		super(instance, playertemp, errortemp);
		town = PDI.getTownResides();
		for(GoodBusiness business: PDI.getGoodBusinessOwned()) {
			inputs.add(business.getName());
		}
		for(ServiceBusiness business: PDI.getServiceBusinessOwned()) {
			inputs.add(business.getName());
		}
	}
	
	@Override
	public boolean blocksForInput(ConversationContext arg0) {
		if (inputs.size() == 1) {
			PDI.getConversation().acceptInput("1");
			return true;
		}
		else {
			return true;
		}
	}
	
	@Override
	public String getPromptText(ConversationContext arg0) {
		String space = tools.space();
		String main = tools.header("Manage Business. Select A Business.");
		String options = "";
		String end = tools.footer(false);
		String errmsg = ChatColor.RED + tools.errors.get(error);
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
			return new ManageBusiness1(plugin, player, 0);
		}
		if (arg.equalsIgnoreCase("back")) {
			return new HudConversationMain(plugin, player, 0);
		}
		try {
			answer = Integer.decode(args[0])-1;
		}
		catch (Exception ex) {
			return new ManageBusiness1(plugin, player,1);
		}
		
		if (answer > inputs.size()-1) {
			return new ManageBusiness1(plugin, player, 2);
		}
		return new ManageBusiness2(plugin, player, 0, inputs.get(answer));
		//return new ManageBusiness1(plugin, player, 2);
	}





}
