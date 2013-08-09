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
package com.github.InspiredOne.InspiredNations;

import java.util.HashMap;
import java.util.logging.Logger;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.InspiredOne.InspiredNations.Economy.ItemIndexes;
import com.github.InspiredOne.InspiredNations.Economy.TaxTimer;
import com.github.InspiredOne.InspiredNations.PlayerListeners.InspiredNationsPlayerListener;
import com.github.InspiredOne.InspiredNations.Regions.ChunkData;
import com.github.InspiredOne.InspiredNations.Regions.Country;


public class InspiredNations extends JavaPlugin {

	public Logger logger = Logger.getLogger("Minecraft"); // Variable to communicate with console
	private StartStop SS = new StartStop(this); // Deals with start-up and shut-down 
	public HashMap<String, PlayerData> playerdata; // Stores all data associated with players
	public HashMap<String, Country> countrydata; // Stores all data associated with countries
	public HashMap<ChunkData, String> chunks; // Stores every chunks that's been claimed along with its country
	public HashMap<String, PlayerModes> playermodes; // Details of what a player is involved with
	public InspiredNationsCommandExecutor InspiredNationsCE = new InspiredNationsCommandExecutor(this);
	public InspiredNationsPlayerListener InspiredNationsPL = new InspiredNationsPlayerListener(this);
	public TaxTimer taxTimer;
	public ItemIndexes index = new ItemIndexes(this);
	
	public void onEnable() {
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(InspiredNationsPL, this);
		SS.Start();
	}
	
	public void onDisable() {
		SS.Stop();
	}
}
