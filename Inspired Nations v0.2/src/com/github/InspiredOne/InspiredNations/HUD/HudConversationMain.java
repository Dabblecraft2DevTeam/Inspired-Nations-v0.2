package com.github.InspiredOne.InspiredNations.HUD;


import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.HUD.ManageBusiness.ManageBusiness1;
import com.github.InspiredOne.InspiredNations.HUD.ManageCountry.ManageCountry;
import com.github.InspiredOne.InspiredNations.HUD.ManageHouse.ManageHouse1;
import com.github.InspiredOne.InspiredNations.HUD.NewCountry.NewCountry1;
import com.github.InspiredOne.InspiredNations.ManageTown.ManageTown;
import com.github.InspiredOne.InspiredNations.Tools.menuType;

public class HudConversationMain extends Menu{
	
	// Constructor
	public HudConversationMain(InspiredNations instance, Player playertemp, int errortemp) {
		super(instance, playertemp, errortemp);
		PM.setReSelectHouse(false);
	}

	@Override
	public String getPromptText(ConversationContext arg0) {
		String space = tools.space();
		String main = tools.header("Welcome to the HUD. Type an option number.");
		String options = "";
		String end = tools.footer(true);
		String errmsg = ChatColor.RED + tools.errors.get(error);
		
		// Make the inputs vector
		inputs.add("Notifications");
		inputs.add("Manage Citizenship");
		inputs.add("Manage Money");
		if (PDI.getIsCountryRuler()) {
			inputs.add("Manage Country");
		}
		if (PDI.getIsTownMayor()) {
			inputs.add("Manage Town");
		}
		if (PDI.isGoodBusinessOwner() || PDI.isServiceBusinessOwner()) {
			inputs.add("Manage Business");
		}
		if (PDI.isHouseOwner()) {
			inputs.add("Manage House");
		}
		inputs.add("New Country");
		if (PDI.getIsCountryResident()) {
			inputs.add("New Town");
		}
		if (PDI.getIsTownResident()) {
			inputs.add("New Business");
			inputs.add("New House");
		}
		if (PDI.getIsCountryResident()) {
			inputs.add("Jobs ("+ menuType.OPTIONDESCRIP + 
					(PMeth.getJobOffers().size() + PMeth.getOwnerOffers().size()) + menuType.OPTION + ")");
		}
		inputs.add("Map");
		
		// Make the options text
		options = tools.options(inputs);
		
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
			return new HudConversationMain(plugin, player, 0);
		}
		try {
			answer = Integer.decode(arg)-1;
		}
		catch (Exception ex) {
			return new HudConversationMain(plugin, player,1);
		}
		
		if (answer > inputs.size()-1) {
			return new HudConversationMain(plugin, player, 2);
		}
		
		if (inputs.get(answer).equals("Manage Money")) {
			return new ManageMoney(plugin, player, 0);
		}
		else if(inputs.get(answer).equals("Manage Citizenship")) {
			return new ManageCitizenship(plugin, player, 0);
		}
		else if(inputs.get(answer).equals("Notifications")) {
			
		}
		else if(inputs.get(answer).equals("Manage Country")) {
			return new ManageCountry(plugin, player, 0);
		}
		else if(inputs.get(answer).equals("Manage Town")) {
			return new ManageTown(plugin, player, 0);
		}
		else if(inputs.get(answer).equals("Manage House")) {
			return new ManageHouse1(plugin, player, 0);
		}
		else if(inputs.get(answer).equals("Manage Business")) {
			return new ManageBusiness1(plugin, player, 0);
		}
		else if(inputs.get(answer).equals("New Country")) {
			return new NewCountry1(plugin, player, 0);
		}
		else if(inputs.get(answer).equals("New Town")) {
			return new NewTown(plugin, player, 0);
		}
		else if(inputs.get(answer).equals("New House")) {
			PM.house(true);
			return new SelectHouse1(plugin, player, 0);
		}
		else if(inputs.get(answer).equals("New Business")) {
			return new SelectBusiness1(plugin, player, 0);
		}
		else if(inputs.get(answer).equals("Map")) {
			PM.setMap(true);
			return new ShowMap(plugin, player, 0);
		}
		else if(inputs.get(answer).equals("Jobs ("+ menuType.OPTIONDESCRIP + 
				(PMeth.getJobOffers().size() + PMeth.getOwnerOffers().size()) + menuType.OPTION + ")")) {
			return new Jobs(plugin, player, 0);
		}
		
		return new HudConversationMain(plugin, player, 2);
	}

}
