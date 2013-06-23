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
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.PlayerModes;
import com.github.InspiredOne.InspiredNations.HUD.SelectBusiness3;
import com.github.InspiredOne.InspiredNations.ManageTown.SelectBank2;
import com.github.InspiredOne.InspiredNations.Regions.Cuboid;
import com.github.InspiredOne.InspiredNations.Regions.Point3D;
import com.github.InspiredOne.InspiredNations.Regions.Town;
import com.github.InspiredOne.InspiredNations.Regions.polygonPrism;

public class ClaimGoodBusinessPlayerListener {
	InspiredNations plugin;
	Player player;
	String playername;
	PlayerData PDI;
	PlayerModes PM;
	PlayerMoveEvent eventmove;
	PlayerInteractEvent eventinteract;
	ItemStack item;
	
	public ClaimGoodBusinessPlayerListener(InspiredNations instance, PlayerMoveEvent event) {
		plugin = instance;
		player = event.getPlayer();
		playername = player.getName();
		PDI = plugin.playerdata.get(playername);
		PM = plugin.playermodes.get(playername);
		eventmove = event;
		item = new ItemStack(plugin.getConfig().getInt("selection_tool"));
	}
	
	public ClaimGoodBusinessPlayerListener(InspiredNations instance, PlayerInteractEvent event) {
		plugin = instance;
		player = event.getPlayer();
		playername = player.getName();
		PDI = plugin.playerdata.get(playername);
		PM = plugin.playermodes.get(playername);
		eventinteract = event;
		item = new ItemStack(plugin.getConfig().getInt("selection_tool"));
	}
	
	
	public void onPlayerMove() {
		if (!PM.goodBusinessSelect() || !(PM.isSelectingCuboid() || PM.isSelectingPolygon())) return;
	}
	
	public void onPlayerInteract() {
		if (!PM.goodBusinessSelect() || !(PM.isSelectingCuboid() || PM.isSelectingPolygon())) return;
		
		// Generic selection code
		if (PM.isSelectingCuboid()) {
			if (!eventinteract.hasItem()) return;
			if (eventinteract.getItem().getTypeId() != item.getTypeId()) return;
			if (eventinteract.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
				PM.point1 = eventinteract.getClickedBlock().getLocation();
				if (PM.hasPoint1() && PM.hasPoint2()) {
					PM.setCuboid(new Cuboid(PM.point1, PM.point2));
				}
				generateMessage();
				player.sendRawMessage(ChatColor.RED + "Point 1 selected, if you haven't selected Point 2 do that now. ");
			}
			if (eventinteract.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
				PM.point2 = eventinteract.getClickedBlock().getLocation();
				if (PM.hasPoint1() && PM.hasPoint2()) {
					PM.setCuboid(new Cuboid(PM.point1, PM.point2));
				}
				generateMessage();
				player.sendRawMessage(ChatColor.RED + "Point 2 selected, if you haven't selected Point 1 do that now. ");
			}
			eventinteract.setCancelled(true);
		}
		
		if (PM.isSelectingPolygon()) {
			if (PM.getAloudSelect()) {
				if (!eventinteract.getAction().equals(Action.LEFT_CLICK_BLOCK)) return;
				if (!eventinteract.hasItem()) return;
				if (eventinteract.getItem().getTypeId() != item.getTypeId()) return;
				Town town = PDI.getTownResides();
				polygonPrism prism = PM.getPolygon();
				Location spot = eventinteract.getClickedBlock().getLocation();
				Point3D spot1 = new Point3D(spot.getWorld().getName(), spot.getBlockX(), spot.getBlockY(), spot.getBlockZ());
				Point3D spot2;
				Point3D spot3;
				Point3D spot4;
				Point3D spot5;
				
				if (!town.isIn(spot)) return;
				prism.addVertex(new Point(spot.getBlockX(), spot.getBlockZ()), spot.getBlockY());
				
				// Make the bedrock marker
				PM.addBlock(spot1, spot.getBlock());
				if (spot.getBlock().getTypeId() != 54 && spot.getBlock().getTypeId() != 52) {
					spot.getBlock().setTypeId(7);
				}
				spot.setY(spot.getY() + 1);
				spot2 = new Point3D(spot1.getWorld(), spot1.x, spot1.y + 1, spot1.z);
				if (player.getLocation().getBlockX() != spot.getBlockX() || player.getLocation().getBlockZ() != spot.getBlockZ()) {
					PM.addBlock(spot2, spot.getBlock());
					if (spot.getBlock().getTypeId() != 54 && spot.getBlock().getTypeId() != 52) {
						spot.getBlock().setTypeId(7);
					}
				}
				spot.setY(spot.getY() + 1);
				spot3 = new Point3D(spot2.getWorld(), spot2.x, spot2.y + 1, spot2.z);
				if (player.getLocation().getBlockX() != spot.getBlockX() || player.getLocation().getBlockZ() != spot.getBlockZ()) {
					PM.addBlock(spot3, spot.getBlock());
					if (spot.getBlock().getTypeId() != 54 && spot.getBlock().getTypeId() != 52) {
						spot.getBlock().setTypeId(7);
					}
				}
				spot.setY(spot.getY() + 1);
				spot4 = new Point3D(spot3.getWorld(), spot3.x, spot3.y + 1, spot3.z);
				PM.addBlock(spot4,  spot.getBlock());
				if (spot.getBlock().getTypeId() != 54 && spot.getBlock().getTypeId() != 52) {
					spot.getBlock().setTypeId(7);
				}
				spot.setY(spot.getY() + 1);
				spot5 = new Point3D(spot4.getWorld(), spot4.x, spot4.y + 1, spot4.z);
				PM.addBlock(spot5, spot.getBlock());
				if (spot.getBlock().getTypeId() != 54 && spot.getBlock().getTypeId() != 52) {
					spot.getBlock().setTypeId(7);
				}
				
				// Delays the clicks
				PM.setAloudSelect(false);
				plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
						public void run() {
							PM.setAloudSelect(true);
						}
				}, 10);
				
	
				generateMessage();
			}
			eventinteract.setCancelled(true);
		}
	}
	
	// A method to generate the message
	public void generateMessage() {
		SelectBusiness3 convo = new SelectBusiness3(plugin, player, 0);
		ConversationContext arg = PDI.getConversation().getContext();
		String current = convo.getPromptText(arg);
		PDI.getConversation().outputNextPrompt();
		plugin.InspiredNationsPL.previous = current;
		return;
	}
}
