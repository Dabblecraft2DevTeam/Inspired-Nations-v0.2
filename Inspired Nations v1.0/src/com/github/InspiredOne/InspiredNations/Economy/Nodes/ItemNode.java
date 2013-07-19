package com.github.InspiredOne.InspiredNations.Economy.Nodes;

import java.math.BigDecimal;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.Economy.NPC;

public class ItemNode extends Node {

	double cost;
	boolean choseThis;
	
	public ItemNode(NPC instance, int id, int elems, double cost) {
		// set elems to -1 to make this a termination ItemNode
		
		super(instance, id, new int[] {elems});
		this.cost = cost;
	}
	
	@Override
	public double getCoef() {
		
		if(cost < thresh) {
			cost = thresh;
		}
		
		if(elems[0] < 0) {
			choseThis = true;
			return 1./cost;
		}
		
		if(ref.get(elems[0]).getCoef() > 1./cost) {
			choseThis = false;
			return ref.get(elems[0]).getCoef();
		}
		else {
			choseThis = true;
			return 1./cost;
		}
	}

	@Override
	public void buy(BigDecimal amount) {
		if(choseThis) {
			
		}
		else {
			ref.get(elems[0]).buy(amount);
		}
	}
}