/*******************************************************************************
 * Copyright (c) 2013 InspiredOne.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *     InspiredOne - initial API and implementation
 ******************************************************************************/
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
import com.github.InspiredOne.InspiredNations.HUD.ManageCountry.UnclaimCountryLand;
import com.github.InspiredOne.InspiredNations.Regions.ChunkData;
import com.github.InspiredOne.InspiredNations.Regions.Chunks;
import com.github.InspiredOne.InspiredNations.Regions.Country;
import com.github.InspiredOne.InspiredNations.Tools.version;

public class UnclaimCountryLandPlayerListener{
	
	InspiredNations plugin;
	Player player;
	String playername;
	PlayerData PDI;
	PlayerModes PM;
	CountryMethods countryMethods;
	
	public UnclaimCountryLandPlayerListener(InspiredNations instance, PlayerMoveEvent event) {
		plugin = instance;
		player = event.getPlayer();
		playername = player.getName();
		PDI = plugin.playerdata.get(playername);
		PM = plugin.playermodes.get(playername);
		countryMethods = new CountryMethods(plugin, PDI.getCountryRuled());
	}

	public void onPlayerMove() {
		if (!PDI.getIsCountryRuler()) return;
		Country country = PDI.getCountryRuled();
		Chunks area = country.getChunks();
		Location spot = player.getLocation();
		boolean aloud = false;
		ChunkData tile = new ChunkData(new Point(spot.getChunk().getX(), spot.getChunk().getZ()), spot.getWorld().getName());
	
		// Deselect Country
		if (!player.isConversing()) {
			PM.predecountry(false);
			PM.decountry(false);
			return;
		}
		if (!PM.preDeselectCountry()) return;
		//player.sendRawMessage(generateMap(country, player));
		generateMap();
		if (!PM.countryDeselect()) return;
		if ((!area.isIn(spot)));
				//|| !chunkAdjacent(area, spot, tile)) && area.Chunks.size() != 0) return;
		else aloud = true;
		if (aloud) {
			country.removeChunk(tile);
			country.transferMoneyFromNPC(countryMethods.getCostPerChunk(country.getProtectionLevel(), true, version.NEW).multiply(new BigDecimal(plugin.taxTimer.getFractionLeft())));
			for(Player playertarget:plugin.getServer().getOnlinePlayers()) {
				PlayerMethods PM = new PlayerMethods(plugin, playertarget);
				PM.resetLocationBooleans();
			}
			//player.sendRawMessage(generateMap(country, player));
			generateMap();
		}
	}

	// Build the map for the PlayerListener
	public void generateMap() {
		UnclaimCountryLand convo = new UnclaimCountryLand(plugin, player, 0);
		ConversationContext arg = PDI.getConversation().getContext();
		String current = convo.getPromptText(arg);
		if (!current.equals(plugin.InspiredNationsPL.previous)) {
			PDI.getConversation().outputNextPrompt();
			plugin.InspiredNationsPL.previous = current;
		}
	}
	
	// A method to determine if a chunk is aloud to be removed
	public boolean chunkAdjacent(Chunks area, Location spot, ChunkData tile) {
		boolean aloud = true;
		int in = 0;
		if (area.Area() < 3) return aloud;
		
		ChunkData tile1 = new ChunkData(new Point(tile.point.x, tile.point.y + 1), tile.world);
		ChunkData tile1L = new ChunkData(new Point(tile.point.x - 1, tile.point.y + 1), tile.world);
		ChunkData tile1R = new ChunkData(new Point(tile.point.x + 1, tile.point.y + 1), tile.world);
		ChunkData tile2 = new ChunkData(new Point(tile.point.x + 1, tile.point.y), tile.world);
		ChunkData tile2T = new ChunkData(new Point(tile.point.x + 1, tile.point.y + 1), tile.world);
		ChunkData tile2B = new ChunkData(new Point(tile.point.x + 1, tile.point.y - 1), tile.world);
		ChunkData tile3 = new ChunkData(new Point(tile.point.x, tile.point.y - 1), tile.world);
		ChunkData tile3L = new ChunkData(new Point(tile.point.x - 1, tile.point.y - 1), tile.world);
		ChunkData tile3R = new ChunkData(new Point(tile.point.x + 1, tile.point.y - 1), tile.world);
		ChunkData tile4 = new ChunkData(new Point(tile.point.x - 1, tile.point.y), tile.world);
		ChunkData tile4T = new ChunkData(new Point(tile.point.x - 1, tile.point.y + 1), tile.world);
		ChunkData tile4B = new ChunkData(new Point(tile.point.x - 1, tile.point.y - 1), tile.world);
		if (area.isIn(spot)) {
			if (area.isIn(tile1)) {
				if((!area.isIn(tile1R) || !area.isIn(tile2)) &&
						(!area.isIn(tile1L) || !area.isIn(tile4))) aloud = false;
				in++;
			}
			if (area.isIn(tile2)) {
				if ((!area.isIn(tile2B) || !area.isIn(tile3)) && (!area.isIn(tile2T)
						|| !area.isIn(tile1))) aloud = false;
				in++;
			}
			if (area.isIn(tile3)) {
				if ((!area.isIn(tile3L) || !area.isIn(tile4)) && (!area.isIn(tile3R)
						|| !area.isIn(tile2))) aloud = false;
				in++;
			}
			if (area.isIn(tile4)) {
				if ((!area.isIn(tile4B) || !area.isIn(tile3)) && (!area.isIn(tile4T)
						|| !area.isIn(tile1))) aloud = false;
				in++;
			}
		}
		if (in == 1) aloud = true;
		return aloud;
	}
}
