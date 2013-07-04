package com.github.InspiredOne.InspiredNations.PlayerListeners;

import java.awt.Point;
import java.math.BigDecimal;

import org.bukkit.Location;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

import com.github.InspiredOne.InspiredNations.CountryMethods;
import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.PlayerMethods;
import com.github.InspiredOne.InspiredNations.PlayerModes;
import com.github.InspiredOne.InspiredNations.Tools;
import com.github.InspiredOne.InspiredNations.HUD.ManageCountry.ClaimCountryLand;
import com.github.InspiredOne.InspiredNations.Regions.ChunkData;
import com.github.InspiredOne.InspiredNations.Regions.Chunks;
import com.github.InspiredOne.InspiredNations.Regions.Country;
import com.github.InspiredOne.InspiredNations.Tools.version;


public class ClaimCountryLandPlayerListener {
	InspiredNations plugin;
	Player player;
	String playername;
	PlayerData PDI;
	PlayerModes PM;
	CountryMethods countryMethods;
	Tools tools;
	
	public ClaimCountryLandPlayerListener(InspiredNations instance, PlayerMoveEvent event) {
		plugin = instance;
		player = event.getPlayer();
		playername = player.getName();
		PDI = plugin.playerdata.get(playername);
		PM = plugin.playermodes.get(playername);
		countryMethods = new CountryMethods(plugin, PDI.getCountryRuled());
		tools = new Tools(plugin);
	}
	
	public void onPlayerMove() {
		if (!PDI.getIsCountryRuler()) return;
		if (!PM.preCountrySelect()) return;
		Country country = PDI.getCountryRuled();
		Chunks area = country.getChunks();
		Location spot = player.getLocation();
		boolean aloud = false;
		ChunkData tile = new ChunkData(new Point(spot.getChunk().getX(), spot.getChunk().getZ()), spot.getWorld().getName());
		
		// Select Country

	//	player.sendRawMessage(generateMap(country, player));
		generateMap();
		if (!PM.countrySelect()) return;
		if (area.isIn(spot)) return;

		spot.setX(spot.getX() + 16);
		if (area.isIn(spot)) aloud = true;
		spot.setX(spot.getX() - 32);
		if (area.isIn(spot)) aloud = true;
		spot.setX(spot.getX() + 16);
		spot.setZ(spot.getZ() + 16);
		if (area.isIn(spot)) aloud = true;
		spot.setZ(spot.getZ() - 32);
		if (area.isIn(spot)) aloud = true;
		spot.setZ(spot.getZ() + 16);
		if (area.Area() == 0) {
			aloud = true;
		}

		if (country.getMoney().compareTo(countryMethods.getCostPerChunk(country.getProtectionLevel(),true, version.NEW).multiply(new BigDecimal(plugin.taxTimer.getFractionLeft()))) < 0) {
			aloud = false;
		}
		if (aloud && !area.isIn(spot) && plugin.chunks.containsKey(tile)){
			if (plugin.countrydata.get(plugin.chunks.get(tile)).getProtectionLevel() == 0) {
				plugin.countrydata.get(plugin.chunks.get(tile)).removeChunk(tile);
			}
			else {
				aloud = false;
			}
		}
		if (aloud) {
			country.addChunk(tile);
			country.transferMoneyToNPC(countryMethods.getCostPerChunk(country.getProtectionLevel(), true, version.NEW).multiply(new BigDecimal(plugin.taxTimer.getFractionLeft())));
			//player.sendRawMessage(generateMap(country, player));
			generateMap();
			for(Player playertarget:plugin.getServer().getOnlinePlayers()) {
				PlayerMethods PM = new PlayerMethods(plugin, playertarget);
				PM.resetLocationBooleans();
			}
		}
	}
	
	public void generateMap() {
		ClaimCountryLand convo = new ClaimCountryLand(plugin, player, 0);
		ConversationContext arg = PDI.getConversation().getContext();
		String current = convo.getPromptText(arg);
		if (!current.equals(plugin.InspiredNationsPL.previous)) {
			PDI.getConversation().outputNextPrompt();
			plugin.InspiredNationsPL.previous = current;
		}
		return;
	}
}
