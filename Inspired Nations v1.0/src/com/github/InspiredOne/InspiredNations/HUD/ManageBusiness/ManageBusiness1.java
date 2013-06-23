package com.github.InspiredOne.InspiredNations.HUD.ManageBusiness;

import java.util.Vector;

import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.PlayerModes;
import com.github.InspiredOne.InspiredNations.Tools;
import com.github.InspiredOne.InspiredNations.HUD.HudConversationMain;
import com.github.InspiredOne.InspiredNations.Regions.GoodBusiness;
import com.github.InspiredOne.InspiredNations.Regions.ServiceBusiness;
import com.github.InspiredOne.InspiredNations.Regions.Town;

public class ManageBusiness1 implements Prompt {

	InspiredNations plugin;
	Tools tools;
	Player player;
	PlayerData PDI;
	PlayerModes PM;
	Town town;
	
	Vector<String> inputs = new Vector<String>();
	int error;
	
	// Constructor
	public ManageBusiness1(InspiredNations instance, Player playertemp, int errortemp) {
		plugin = instance;
		player = playertemp;
		tools = new Tools(plugin);
		PDI = plugin.playerdata.get(player.getName());
		PM = plugin.playermodes.get(player.getName());
		error = errortemp;
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
			plugin.logger.info("I'm there");
			return false;
		}
	}
	
	@Override
	public String getPromptText(ConversationContext arg0) {
		String space = tools.space();
		String main = tools.header("Manage Business. Select A Business.");
		String options = "";
		String end = tools.footer(false);
		String errmsg = ChatColor.RED + tools.errors.get(error);
		plugin.logger.info(inputs.size() + "");
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
