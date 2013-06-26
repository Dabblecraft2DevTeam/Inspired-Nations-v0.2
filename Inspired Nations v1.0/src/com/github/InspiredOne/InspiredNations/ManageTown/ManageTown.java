package com.github.InspiredOne.InspiredNations.ManageTown;

import java.util.Vector;

import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.entity.Player;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.PlayerMethods;
import com.github.InspiredOne.InspiredNations.PlayerModes;
import com.github.InspiredOne.InspiredNations.Tools;
import com.github.InspiredOne.InspiredNations.HUD.HudConversationMain;
import com.github.InspiredOne.InspiredNations.Regions.Town;

public class ManageTown extends StringPrompt {

	InspiredNations plugin;
	Tools tools;
	Player player;
	PlayerData PDI;
	PlayerModes PM;
	PlayerMethods PMeth;
	Town town;
	
	Vector<String> inputs = new Vector<String>();
	int error;
	
	// Constructor
	public ManageTown(InspiredNations instance, Player playertemp, int errortemp) {
		plugin = instance;
		player = playertemp;
		tools = new Tools(plugin);
		PDI = plugin.playerdata.get(player.getName());
		PM = plugin.playermodes.get(player.getName());
		PMeth = new PlayerMethods(plugin, player);
		error = errortemp;
		town = PDI.getTownMayored();
	}
	
	@Override
	public String getPromptText(ConversationContext arg0) {
		String space = tools.space();
		String main = tools.header("Manage Town. Type an option number.");
		String options = "";
		String end = tools.footer(false);
		String errmsg = ChatColor.RED + tools.errors.get(error);
		
		// make inputs vector
		if(town.getCoMayors().size() < plugin.getConfig().getInt("min_comayors")) {
			inputs.add("*Claim Land " + ChatColor.GRAY + "Must have " + plugin.getConfig().getInt("min_corulers") + " Co-Mayors");
		}
		else if(town.population() < plugin.getConfig().getInt("min_town_population")) {
			inputs.add("*Claim Land " + ChatColor.GRAY + "Must have " + plugin.getConfig().getInt("min_town_population") + " Population");
		}
		else {
			inputs.add("Claim Land");
		}
		if(town.getArea() != 0) {
			inputs.add("Unclaim Land");
			inputs.add("Government Regions");
		}
		
		inputs.add("Manage Finances");
		inputs.add("Protection Level");
		inputs.add("Manage People");
		inputs.add("Rename <name>");
		
		// make options text
		options = options.concat(ChatColor.YELLOW + "" + ChatColor.BOLD + town.getName() + "\n" + ChatColor.RESET);
		options = options.concat(ChatColor.YELLOW + "Population: " + ChatColor.GOLD + town.population() + "\n");
		options = options.concat(ChatColor.YELLOW + "Size: " + ChatColor.GOLD + town.getChunks().Chunks.size() + ChatColor.YELLOW + " Chunks\n");
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
		if (arg.equalsIgnoreCase("back")) {
			return new HudConversationMain(plugin, player, 0);
		}
		String[] args = arg.split(" ");
		if (args[0].equalsIgnoreCase("say"))  {
			if(args.length > 1) {
				PMeth.SendChat(tools.formatSpace(tools.subArray(args, 1, args.length - 1)));
			}
			return new ManageTown(plugin, player, 0);
		}
		
		try {
			answer = Integer.decode(args[0])-1;
		}
		catch (Exception ex) {
			return new ManageTown(plugin, player,1);
		}
		
		if (answer > inputs.size()-1) {
			return new ManageTown(plugin, player, 2);
		}
		
		if (inputs.get(answer).equals("Manage Finances")) {
			return new ManageTownFinances(plugin, player, 0);
			
		}
		else if(inputs.get(answer).equals("Claim Land")) {
			PM.preTown(true);
			return new ClaimTownLand(plugin, player, 0);
		}
		else if(inputs.get(answer).equals("Unclaim Land")){
			PM.predetown(true);
			return new UnclaimTownLand(plugin, player, 0);
		}
		else if(inputs.get(answer).equals("Manage People")) {
			
		}
		else if(inputs.get(answer).equals("Government Regions")) {
			return new TownGovernmentRegions(plugin, player, 0);
		}
		else if(inputs.get(answer).equals("Protection Level")) {
			return new TownProtectionLevel(plugin, player, 0);
		}
		else if(inputs.get(answer).equals("Rename <name>")) {
			if(args.length < 2) {
				return new ManageTown(plugin, player, 3);
			}
			else {
				if(tools.townUnique(tools.formatSpace(tools.subArray(args, 1, args.length - 1)), town.getCountry())) {
					town.setName(tools.formatSpace(tools.subArray(args, 1, args.length - 1)));
					return new ManageTown(plugin, player, 0);
				}
				else return new ManageTown(plugin, player, 26);
			}
		}
		return new ManageTown(plugin, player, 2);
	}

}
