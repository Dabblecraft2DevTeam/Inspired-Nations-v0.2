package com.github.InspiredOne.InspiredNations.HUD;

import java.util.Vector;

import org.bukkit.conversations.StringPrompt;
import org.bukkit.entity.Player;

import com.github.InspiredOne.InspiredNations.CountryMethods;
import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.PlayerMethods;
import com.github.InspiredOne.InspiredNations.PlayerModes;
import com.github.InspiredOne.InspiredNations.Tools;
import com.github.InspiredOne.InspiredNations.TownMethods;
import com.github.InspiredOne.InspiredNations.Regions.Business;
import com.github.InspiredOne.InspiredNations.Regions.Country;
import com.github.InspiredOne.InspiredNations.Regions.GoodBusiness;
import com.github.InspiredOne.InspiredNations.Regions.House;
import com.github.InspiredOne.InspiredNations.Regions.ServiceBusiness;
import com.github.InspiredOne.InspiredNations.Regions.Town;

public abstract class Menu extends StringPrompt {

	public InspiredNations plugin;
	public Tools tools;
	public Player player;
	public PlayerData PDI;
	public PlayerModes PM;
	public PlayerMethods PMeth;
	
	public Vector<String> inputs = new Vector<String>();
	public int error;
	public String names = "";
	
	public Country country;
	public CountryMethods CM;
	public Town town;
	public TownMethods TM;
	public String businessname;
	public Business busi;
	public boolean isGoodBusiness = true;
	public House house;
	
	// Constructor
	public Menu(InspiredNations instance, Player playertemp, int errortemp) {
		plugin = instance;
		player = playertemp;
		tools = new Tools(plugin);
		PDI = plugin.playerdata.get(player.getName());
		PM = plugin.playermodes.get(player.getName());
		PMeth = new PlayerMethods(plugin, player);
		error = errortemp;
		names = "";
		try {
			this.busi = (Business) PDI.getConversation().getContext().getSessionData("business");
			businessname = busi.getName();
			if((Object) busi instanceof ServiceBusiness) {
				this.isGoodBusiness = false;
			}
			else {
				this.isGoodBusiness = true;
			}
		}
		catch (Exception ex) {
			
		}
	}
	
	// Constructor
	public Menu(InspiredNations instance, Player playertemp, int errortemp, Vector<String> namestemp) {
		plugin = instance;
		player = playertemp;
		tools = new Tools(plugin);
		PDI = plugin.playerdata.get(player.getName());
		PM = plugin.playermodes.get(player.getName());
		PMeth = new PlayerMethods(plugin, player);
		error = errortemp;
		names = tools.format(namestemp);
		try {
			this.busi = (Business) PDI.getConversation().getContext().getSessionData("business");
			businessname = busi.getName();
			if((Object) busi instanceof ServiceBusiness) {
				this.isGoodBusiness = false;
			}
			else {
				this.isGoodBusiness = true;
			}
		}
		catch (Exception ex) {
			
		}
	}
	
	// Constructor
	public Menu(InspiredNations instance, Player playertemp, int errortemp, String business, Vector<String> namestemp) {
		plugin = instance;
		player = playertemp;
		tools = new Tools(plugin);
		PDI = plugin.playerdata.get(player.getName());
		PM = plugin.playermodes.get(player.getName());
		PMeth = new PlayerMethods(plugin, player);
		error = errortemp;
		names = tools.format(namestemp);
		businessname = business;
		for(Business i: PDI.getBusinesses()){
			if (i.getName().equals(business)) {
				busi = i;
				if(busi instanceof GoodBusiness) {
					this.isGoodBusiness = true;
				}
				else {
					this.isGoodBusiness = false;
				}
			}
		}
	}
	
	// Constructor
	public Menu(InspiredNations instance, Player playertemp, int errortemp, String business) {
		plugin = instance;
		player = playertemp;
		tools = new Tools(plugin);
		PDI = plugin.playerdata.get(player.getName());
		PM = plugin.playermodes.get(player.getName());
		PMeth = new PlayerMethods(plugin, player);
		error = errortemp;
		businessname = business;
		for(Business i: PDI.getBusinesses()){
			if (i.getName().equals(business)) {
				busi = i;
				if(busi instanceof GoodBusiness) {
					this.isGoodBusiness = true;
				}
				else {
					this.isGoodBusiness = false;
				}
			}
		}
	}
}
