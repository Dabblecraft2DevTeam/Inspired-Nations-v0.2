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

import java.math.BigDecimal;


import org.bukkit.Location;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.PlayerModes;
import com.github.InspiredOne.InspiredNations.Tools;
import com.github.InspiredOne.InspiredNations.HUD.ManageBusiness.AddShop1;
import com.github.InspiredOne.InspiredNations.HUD.ManageBusiness.AddShop3;
import com.github.InspiredOne.InspiredNations.Regions.GoodBusiness;
import com.github.InspiredOne.InspiredNations.Tools.menuType;

public class ChestShopPlayerListener {
	InspiredNations plugin;
	HumanEntity player;
	String playername;
	PlayerData PDI;
	PlayerModes PM;
	PlayerItemHeldEvent held;
	InventoryOpenEvent open;
	InventoryCloseEvent close;
	PlayerInteractEvent interact;
	BlockPlaceEvent place;
	SignChangeEvent sign;
	ItemStack item;
	Tools tools;
	boolean first = true;
	boolean legal;
	
	public ChestShopPlayerListener(InspiredNations instance, InventoryOpenEvent event) {
		plugin = instance;
		player = event.getPlayer();
		playername = player.getName();
		PDI = plugin.playerdata.get(playername);
		PM = plugin.playermodes.get(playername);
		legal = PM.legalItem;
		open = event;
		tools = new Tools(plugin);
	}
	public ChestShopPlayerListener(InspiredNations instance, PlayerItemHeldEvent event) {
		plugin = instance;
		player = event.getPlayer();
		playername = player.getName();
		PDI = plugin.playerdata.get(playername);
		PM = plugin.playermodes.get(playername);
		legal = PM.legalItem;
		held = event;
		tools = new Tools(plugin);
	}
	public ChestShopPlayerListener(InspiredNations instance, SignChangeEvent event) {
		plugin = instance;
		player = event.getPlayer();
		playername = player.getName();
		PDI = plugin.playerdata.get(playername);
		PM = plugin.playermodes.get(playername);
		legal = PM.legalItem;
		sign = event;
		tools = new Tools(plugin);
	}
	
	public ChestShopPlayerListener(InspiredNations instance, InventoryCloseEvent event) {
		plugin = instance;
		player = event.getPlayer();
		playername = player.getName();
		PDI = plugin.playerdata.get(playername);
		PM = plugin.playermodes.get(playername);
		legal = PM.legalItem;
		close = event;
		tools = new Tools(plugin);
	}
	
	public ChestShopPlayerListener(InspiredNations instance, PlayerInteractEvent event) {
		plugin = instance;
		player = event.getPlayer();
		playername = player.getName();
		PDI = plugin.playerdata.get(playername);
		PM = plugin.playermodes.get(playername);
		legal = PM.legalItem;
		interact = event;
		tools = new Tools(plugin);
	}
	
	public ChestShopPlayerListener(InspiredNations instance, BlockPlaceEvent event) {
		plugin = instance;
		player = event.getPlayer();
		playername = player.getName();
		PDI = plugin.playerdata.get(playername);
		PM = plugin.playermodes.get(playername);
		legal = PM.legalItem;
		place = event;
		tools = new Tools(plugin);
	}
	
	public void onOpenInventory() {
		if (!PM.getPlaceItem()) return;
		if (!open.getInventory().getType().equals(InventoryType.CHEST)) return;
		//PM.setItemType(player.getItemInHand());
		//plugin.getServer().getPlayer(player.getName()).sendRawMessage(generateMessage(0));
			
	}
	
	public void onItemHeld() {
		if (!PM.getPlaceItem()) return;
		try {
			PM.setItemType(player.getInventory().getItem(held.getNewSlot()));
			legal = true;
			PM.legalItem = true;
			generateMessage(0);
		}
		catch (Exception ex) {
			legal = false;
			PM.legalItem = false;
			generateMessage(0);
		}
	}
	
	public void onInteractWithChest() {
		if (PM.getPlaceItem()){
			if (interact.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
				if (interact.getClickedBlock().getTypeId()==54) {
					PM.alreadyChest = false;
					Location spot = interact.getClickedBlock().getLocation();
					GoodBusiness business = (GoodBusiness) PDI.getConversation().getContext().getSessionData("business");
	
					if (business.isIn(spot)) {
						// Check in four directions for another chest
						PM.tempchests[0] = spot;
						Location test = new Location(spot.getWorld(), spot.getX(), spot.getY(), spot.getZ());
						boolean doubleChest = false;
						test.setX(test.getX() + 1);
						if (test.getBlock().getTypeId() == 54) {
							doubleChest = true;
							if (business.isIn(test)) {
								PM.tempchests[0] = spot.clone();
								PM.tempchests[1] = test.clone();
								PM.legalChest = true;
							}
							else {
								PM.legalChest = false;
							}
						}
						test.setX(test.getX() - 2);
						if (test.getBlock().getTypeId() == 54) {
							doubleChest = true;
							if (business.isIn(test)) {
								PM.tempchests[0] = spot.clone();
								PM.tempchests[1] = test.clone();
								PM.legalChest = true;
							}
							else {
								PM.legalChest = false;
							}
						}
						test.setX(test.getX() + 1);
						test.setZ(test.getZ() + 1);
						if (test.getBlock().getTypeId() == 54) {
							doubleChest = true;
							if (business.isIn(test)) {
								PM.tempchests[0] = spot.clone();
								PM.tempchests[1] = test.clone();
								PM.legalChest = true;
							}
							else {
								PM.legalChest = false;
							}
						}
						test.setZ(test.getZ() - 2);
						if (test.getBlock().getTypeId() == 54) {
							doubleChest = true;
							if (business.isIn(test)) {
								PM.tempchests[0] = spot.clone();
								PM.tempchests[1] = test.clone();
								PM.legalChest = true;
							}
							else {
								PM.legalChest = false;
							}
						}
						if (!doubleChest) {
							PM.tempchests[0] = spot.clone();
							PM.tempchests[1] = null;
							PM.legalChest = true;
						}

						PM.doublechest = doubleChest;
						generateMessage(0);
					}
					else {
						PM.legalChest = false;
					}
				}
			}
		}
	}
	
	public void onBlockPlace() {
		if (PM.placesign) {
			GoodBusiness business = (GoodBusiness) PDI.getConversation().getContext().getSessionData("business");
			if (place.getBlockPlaced().getTypeId() != 63 && place.getBlockPlaced().getTypeId() != 68) return;
			if (!business.isIn(place.getBlockPlaced().getLocation())) {
				PM.outside = true;
				place.setCancelled(true);
				place.getPlayer().sendRawMessage(generateMessage2(3));
				return;
			}
			if (!business.isIn(place.getBlockAgainst().getLocation())) {
				PM.againstoutside = true;
				place.setCancelled(true);
				place.getPlayer().sendRawMessage(generateMessage2(4));
				return;
			}
			if (place.getBlockAgainst().getTypeId() == 12 || place.getBlockAgainst().getTypeId() == 13 || place.getBlockAgainst().getTypeId() == 63 || place.getBlockAgainst().getTypeId() == 68) {
				PM.onfallblock = true;
				place.setCancelled(true);
				place.getPlayer().sendRawMessage(generateMessage2(2));
				return;
			}
			PM.outside = false;
			PM.againstoutside = false;
			PM.onfallblock = false;
			PM.legalsign = true;
			place.getPlayer().sendRawMessage(generateMessage2(0));
			PM.tempchests[2] = place.getBlock().getLocation();
			PM.tempchests[3] = place.getBlockAgainst().getLocation();
		}
	}
	
	public void onSignChangeEvent() {
		if (PM.placesign) {
			GoodBusiness business = null;
			for (GoodBusiness temp:PDI.getGoodBusinessOwned()) {
				if (temp.getName().equalsIgnoreCase(PM.businessName)) {
					business = temp;
					break;
				}
			}
			if (!business.isIn(sign.getBlock().getLocation())) return;
			sign.setLine(0, PM.itemname);
			sign.setLine(1, "Price: " + tools.cut(new BigDecimal(PM.cost)).toString());
			sign.setLine(2, "Qt: " + PM.quantity);
		}
	}

	

	
	// A method to generate the printout for PlaceShop1
	public void generateMessage(int error) {
		AddShop1 convo = new AddShop1(plugin, plugin.getServer().getPlayer(player.getName()), 0);
		ConversationContext arg = PDI.getConversation().getContext();
		String current = convo.getPromptText(arg);
		PDI.getConversation().outputNextPrompt();
		plugin.InspiredNationsPL.previous = current;
		return;
	}
	
	// A method to generate the printout for PlaceShop4
	public String generateMessage2(int error) {
		
		AddShop3 convo = new AddShop3(plugin, plugin.getServer().getPlayer(player.getName()), 0);
		ConversationContext arg = PDI.getConversation().getContext();
		String current = convo.getPromptText(arg);
		PDI.getConversation().outputNextPrompt();
		plugin.InspiredNationsPL.previous = current;
		
		String errormsg = menuType.ALERT + "";
		
		if (error == 1) {
			errormsg = errormsg.concat("That is not an option. Check your spelling?");
		}
		if (error == 2) {
			errormsg = errormsg.concat("You cannot place your sign on sand, gravel, or other signs.");
		}
		if (error == 3) {
			errormsg = errormsg.concat("You must put your sign inside your business.");
		}
		if (error == 4) {
			errormsg = errormsg.concat("The block that you put your sign on must be inside your business.");
		}
		
		return errormsg;
	}
	
}
