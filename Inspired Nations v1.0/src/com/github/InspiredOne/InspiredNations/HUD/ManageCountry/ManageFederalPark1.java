package com.github.InspiredOne.InspiredNations.HUD.ManageCountry;


import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.HUD.Menu;

public class ManageFederalPark1 extends Menu {

	// Constructor
	public ManageFederalPark1(InspiredNations instance, Player playertemp, int errortemp) {
		super(instance, playertemp, errortemp);
		country = PDI.getCountryRuled();
	}
	
	@Override
	public Prompt acceptInput(ConversationContext arg0, String arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPromptText(ConversationContext arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
