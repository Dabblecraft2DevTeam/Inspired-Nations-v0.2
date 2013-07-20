package com.github.InspiredOne.InspiredNations.Economy.Nodes;

import java.math.BigDecimal;

import org.bukkit.inventory.ItemStack;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.Economy.NPC;

public class ItemNode extends Node {

	BigDecimal cost;
	boolean choseThis;
	ItemStack item;
	
	public ItemNode(NPC instance, int id, int elems, ItemStack item) {
		// set elems to -1 to make this a termination ItemNode
		
		super(instance, id, new int[] {elems});
		this.cost = npc.costVector[npc.index.get(item)];
		this.item = item;
	}
	
	@Override
	public double getCoef() {
		
		if(cost.compareTo(new BigDecimal(thresh)) < 0) {
			cost = new BigDecimal(thresh);
		}
		
		if(elems[0] < 0) {
			choseThis = true;
			return BigDecimal.ONE.divide(cost).doubleValue();
		}
		
		if(ref.get(elems[0]).getCoef() > BigDecimal.ONE.divide(cost).doubleValue()) {
			choseThis = false;
			return ref.get(elems[0]).getCoef();
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
			ref.get(elems[0]).buy(amount);
		}
	}
}