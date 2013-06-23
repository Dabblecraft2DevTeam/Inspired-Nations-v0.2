package com.github.InspiredOne.InspiredNations;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.conversations.Conversation;
import org.bukkit.entity.Player;

import com.github.InspiredOne.InspiredNations.Tools.mapSize;


public class InspiredNationsCommandExecutor implements CommandExecutor{
	
	InspiredNations plugin;
	Tools tools;
	public InspiredNationsCommandExecutor(InspiredNations instance) {
		plugin = instance;
		tools = new Tools(plugin);
	}

	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String CommandLable, String[] args) {
		if(!(sender instanceof Player)) {
			plugin.logger.info("HUD cannot be called from console.");
			return false;
		}
		
		PlayerData PDI = plugin.playerdata.get(sender.getName());
		if (CommandLable.equalsIgnoreCase("hud")) {
			// Handles Commands
			ConversationBuilder convo = new ConversationBuilder(plugin);
			Conversation conversation = convo.HudConvo(plugin.getServer().getPlayer(sender.getName()));
			PDI.setConversation(conversation);
			conversation.begin();
		}
		else if(CommandLable.equalsIgnoreCase("map")) {
			ConversationBuilder convo = new ConversationBuilder(plugin);
			Conversation conversation = convo.MapConvo(plugin.getServer().getPlayer(sender.getName()));
			PDI.setConversation(conversation);
			conversation.begin();
			plugin.playermodes.get(sender.getName()).setMap(true);
		}
		else return false;
		return false;
	}
}
