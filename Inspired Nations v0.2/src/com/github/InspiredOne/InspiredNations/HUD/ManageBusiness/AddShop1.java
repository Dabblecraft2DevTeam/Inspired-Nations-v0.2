package com.github.InspiredOne.InspiredNations.HUD.ManageBusiness;

import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.HUD.HudConversationMain;
import com.github.InspiredOne.InspiredNations.HUD.Menu;
import com.github.InspiredOne.InspiredNations.Regions.Business;
import com.github.InspiredOne.InspiredNations.Tools.menuType;
import com.github.InspiredOne.InspiredNations.Tools.optionType;

public class AddShop1 extends Menu {

	// Constructor
	public AddShop1(InspiredNations instance, Player playertemp, int errortemp) {
		super(instance, playertemp, errortemp);
		town = PDI.getTownResides();

	}
	
	@Override
	public String getPromptText(ConversationContext arg0) {
		String space = tools.space();
		String main = tools.header("Add Shop. Type the amount you want to sell.");
		String options = "";
		String end = tools.footer(false);
		String errmsg = ChatColor.RED + tools.errors.get(error) + names;
		
		// Make options text
		
		options = tools.addLine(options, "Put the item you want to sell in your hand. Click the chest you want to sell from, then enter the number of items you" +
				" would like to sell per sale.", optionType.INSTRUCTION);
		options = tools.addDivider(options);
		if (!PM.legalChest) {
			options = options.concat(menuType.LABEL + "Legal Chest:" + menuType.VALUE +" No\n");
		}
		else {
			options = options.concat(menuType.LABEL + "Legal Chest:" + menuType.VALUE +" Yes\n");
		}
		if (!PM.legalItem) {
			options = options.concat(menuType.LABEL + "Item Type: " + menuType.VALUE + "Pending...\n" );
		}
		else {
			
			String[] namesplit = PM.getItemType().getType().name().split("_");
			String itemname = "";
			for (int i = 0; i < namesplit.length; i++) {
				itemname = itemname.concat(namesplit[i] + " ");
			}
			
			options = options.concat(menuType.LABEL + "Item Type: " + menuType.VALUE + itemname + " ");
		}
		
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
			return new AddShop1(plugin, player, 0);
		}
		if (arg.equalsIgnoreCase("back")) {
			return new ManageBusiness2(plugin, player, 0);
		}
		try {
			answer = Integer.decode(args[0]);
			PM.setPlaceItem(false);
			PM.quantity = answer;
			PM.itemname = tools.getItemName(PM.getItemType());
			return new AddShop2(plugin, player, 0);
		}
		catch (Exception ex) {
			return new AddShop1(plugin, player,1);
		}
		
	}
}
