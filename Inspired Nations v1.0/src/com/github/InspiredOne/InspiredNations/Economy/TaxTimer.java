package com.github.InspiredOne.InspiredNations.Economy;

import org.bukkit.scheduler.BukkitRunnable;

import com.github.InspiredOne.InspiredNations.InspiredNations;

public class TaxTimer {

	int cycleLength;
	int countdown;
	InspiredNations plugin;
	public TaxTimer(InspiredNations instance){
		plugin = instance;
		cycleLength = plugin.getConfig().getInt("tax_cycle_length");
		countdown = cycleLength;
	}
	
	

	public void startTimer() {
		
		
		new BukkitRunnable() {

			@Override
			public void run() {
				countdown--;
				if(countdown == 0) {
					countdown = cycleLength;
				}
			}
			
		}.runTaskTimer(plugin, 0, 20);		
	}
	
	public double getFractionLeft() {
		return ((double) countdown)/((double)cycleLength);
	}
	
	public void executeTaxCycle() {
		// put new tax in old tax variable
		// start at players, then move to towns, then move to country.
	}
}
