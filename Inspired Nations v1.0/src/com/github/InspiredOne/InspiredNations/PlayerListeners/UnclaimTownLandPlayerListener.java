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

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.PlayerMethods;
import com.github.InspiredOne.InspiredNations.PlayerModes;
import com.github.InspiredOne.InspiredNations.TownMethods;
import com.github.InspiredOne.InspiredNations.ManageTown.UnclaimTownLand;
import com.github.InspiredOne.InspiredNations.Regions.ChunkData;
import com.github.InspiredOne.InspiredNations.Regions.Chunks;
import com.github.InspiredOne.InspiredNations.Regions.Town;

public class UnclaimTownLandPlayerListener {
	
	InspiredNations plugin;
	Player player;
	String playername;
	PlayerData PDI;
	PlayerModes PM;
	TownMethods TMI;
	Town town;
	
	public UnclaimTownLandPlayerListener(InspiredNations instance, PlayerMoveEvent event) {
		plugin = instance;
		player = event.getPlayer();
		playername = player.getName();
		PDI = plugin.playerdata.get(playername);
		PM = plugin.playermodes.get(playername);
		TMI = new TownMethods(plugin, PDI.getTownMayored());
		town = PDI.getTownMayored();
	}
	
	public void onPlayerMove() {
		if (!PDI.getIsTownMayor()) return;
		Chunks area = town.getChunks();
		Location spot = player.getLocation();
		boolean aloud = false;
		ChunkData tile = new ChunkData(new Point(spot.getChunk().getX(), spot.getChunk().getZ()), spot.getWorld().getName());
	
		// Deselect Town
		if (!player.isConversing()) {
			PM.predetown(false);
			PM.detown(false);
			return;
		}
		if (!PM.preDeselectTown()) return;
		generateMap(player);
		if (!PM.townDeselect()) return;
		if ((!area.isIn(spot))) return; 
				//|| !chunkAdjacent(area, spot, tile)) && area.Chunks.size() != 0) return;
		else aloud = true;
		if (aloud) {
			town.removeChunk(tile);
			for(Player playertarget:plugin.getServer().getOnlinePlayers()) {
				PlayerMethods PM = new PlayerMethods(plugin, playertarget);
				PM.resetLocationBooleans();
			}
			generateMap(player);
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
	
	// Build the map for the PlayerListener
	public void generateMap(Player player) {
		UnclaimTownLand convo = new UnclaimTownLand(plugin, player, 0);
		ConversationContext arg = PDI.getConversation().getContext();
		String current = convo.getPromptText(arg);
		if (!current.equals(plugin.InspiredNationsPL.previous)) {
			PDI.getConversation().outputNextPrompt();
			plugin.InspiredNationsPL.previous = current;
		}
		return;

	}
	
	// A method to simply repeat a string
	public String repeat(String entry, int multiple) {
		String temp = "";
		for (int i = 0; i < multiple; i++) {
			temp = temp.concat(entry);
		}
		return temp;
	}
	
	// A method to cut off decimals greater than the hundredth place;
	public double cut(double x) {
		int y;
		y = (int) (x*100);
		return y/100.0;
	}
	
	// A method to cut off decimals greater than the hundredth place;
	public BigDecimal cut(BigDecimal x) {
		return x.divide(new BigDecimal(1), 2, BigDecimal.ROUND_DOWN);
	}
}
