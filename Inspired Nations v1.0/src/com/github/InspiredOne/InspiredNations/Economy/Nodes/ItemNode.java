package com.github.InspiredOne.InspiredNations.Economy.Nodes;

import java.math.BigDecimal;

import com.github.InspiredOne.InspiredNations.InspiredNations;

public class ItemNode extends Node {

	
	public ItemNode(InspiredNations instance, int id) {
		super(instance, id);
	}
	
	@Override
	public double getCoef() {
		
		return 0;
	}

	@Override
	public void buy(BigDecimal amount) {
		// TODO Auto-generated method stub
	}

}
