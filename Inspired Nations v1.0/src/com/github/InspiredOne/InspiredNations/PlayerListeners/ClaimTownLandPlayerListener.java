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


import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.PlayerMethods;
import com.github.InspiredOne.InspiredNations.PlayerModes;
import com.github.InspiredOne.InspiredNations.TownMethods;
import com.github.InspiredOne.InspiredNations.TownMethods.taxType;
import com.github.InspiredOne.InspiredNations.ManageTown.ClaimTownLand;
import com.github.InspiredOne.InspiredNations.Regions.Chunks;
import com.github.InspiredOne.InspiredNations.Regions.Country;
import com.github.InspiredOne.InspiredNations.Regions.Town;

public class ClaimTownLandPlayerListener {
	InspiredNations plugin;
	Player player;
	String playername;
	PlayerData PDI;
	PlayerModes PM;
	PlayerMoveEvent moveEvent;
	PlayerInteractEvent interactEvent;
	ItemStack item;
	TownMethods TM;
	
	public ClaimTownLandPlayerListener(InspiredNations instance,PlayerMoveEvent event) {
		plugin = instance;
		player = event.getPlayer();
		playername = player.getName();
		PDI = plugin.playerdata.get(playername);
		PM = plugin.playermodes.get(playername);
		moveEvent = event;
	}
	
	public void onPlayerMove() {
		if (!PDI.getIsTownMayor()) return;
		if (!PM.preTownSelect()) return;
		Town town = PDI.getTownMayored();
		TM = new TownMethods(plugin, town);
		Country country = PDI.getCountryResides();
		Chunks area = town.getChunks();
		Location spot = player.getLocation();
		boolean aloud = false;
		Point tile = new Point(spot.getChunk().getX(), spot.getChunk().getZ());	
		// Select Town

		generateMap(player);
		if (!PM.townSelect()) return;
		if (area.isIn(spot)) return;
		
		for(Town towntest:country.getTowns()) {
			if(towntest.isIn(spot) && towntest != town) {
				if (towntest.getProtectionLevel() != 0) {
					return;
				}
			}
		}
		if (!country.isIn(spot)) {
			return;
		}
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
			area.setWorld(spot.getWorld().getName());
		}
		spot = player.getLocation();
		for(Town towntest:country.getTowns()) {
			if(towntest.isIn(spot) && towntest != town) {
				if (towntest.getProtectionLevel() == 0) {
					TownMethods TMtest = new TownMethods(plugin, towntest);
					towntest.getChunks().removeChunk(tile);
					country.transferMoneyToTown(TMtest.getCostPerChunk(taxType.OLD), towntest.getName(), country.getName());
					towntest.removeCutOutRegions();
				}
				else aloud = false;
			}
		}
		if(TM.getCostPerChunk(taxType.OLD).multiply(new BigDecimal(plugin.taxTimer.getFractionLeft())).compareTo(town.getMoney()) > 0) {
			aloud = false;
		}
		
		if (aloud) {

			area.addChunk(tile);
			town.setChunks(area);
			town.transferMoneyToCountry(TM.getCostPerChunk(taxType.OLD).multiply(new BigDecimal(plugin.taxTimer.getFractionLeft())), town.getCountry());
			for(Player playertarget:plugin.getServer().getOnlinePlayers()) {
				PlayerMethods PM = new PlayerMethods(plugin, playertarget);
				PM.resetLocationBooleans();
			}
			
			generateMap(player);
		}
	}
	
	// Build the map for the PlayerListener
	public void generateMap(Player player) {
		ClaimTownLand convo = new ClaimTownLand(plugin, player, 0);
		ConversationContext arg = PDI.getConversation().getContext();
		String current = convo.getPromptText(arg);
		if (!current.equals(plugin.InspiredNationsPL.previous)) {
			PDI.getConversation().outputNextPrompt();
			plugin.InspiredNationsPL.previous = current;
		}
		return;
	}
}
