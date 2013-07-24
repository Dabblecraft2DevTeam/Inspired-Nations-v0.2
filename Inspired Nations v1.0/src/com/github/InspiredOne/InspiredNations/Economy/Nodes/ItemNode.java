package com.github.InspiredOne.InspiredNations.Economy.Nodes;

import java.math.BigDecimal;

import org.bukkit.inventory.ItemStack;

import com.github.InspiredOne.InspiredNations.Economy.NPC;

public class ItemNode extends Node {

	BigDecimal cost;
	boolean choseThis;
	ItemStack item;
	
	public ItemNode(NPC instance, ItemStack item, Node[] elems) {
		/**Only put one node in the list*/

		super(instance, elems);
		this.cost = npc.costVector[npc.index.get(item)];
		this.item = item;
	}
	
	public ItemNode(NPC instance, ItemStack item) {
		// {
		super(instance, new Node[] {});
		choseThis = true;
		this.cost = npc.costVector[npc.index.get(item)];
		this.item = item;
		// }
	}
	
	@Override
	public double getCoef() {

		if(cost.signum() < 0) {
			return 0;
		}
		if(cost.compareTo(new BigDecimal(thresh)) < 0) {
			cost = new BigDecimal(thresh);
		}
		
		if(choseThis) {
			return BigDecimal.ONE.divide(cost).doubleValue();
		}
			
		if(elems[0].getCoef() > BigDecimal.ONE.divide(cost).doubleValue()) {
			choseThis = false;
			return elems[0].getCoef();
		}
		

		else {
			choseThis = true;
			return BigDecimal.ONE.divide(cost).doubleValue();
		}
	}
	@Override
	public void buy(BigDecimal amount) {
		if(choseThis) {
			npc.buyVector[this.npc.index.get(item)] = npc.buyVector[this.npc.index.get(item)].add(amount);
		}
		else {
			elems[0].buy(amount);
		}
	}
}