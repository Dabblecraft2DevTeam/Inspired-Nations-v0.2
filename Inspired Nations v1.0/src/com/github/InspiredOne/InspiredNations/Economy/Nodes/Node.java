package com.github.InspiredOne.InspiredNations.Economy.Nodes;

import java.math.BigDecimal;

import com.github.InspiredOne.InspiredNations.InspiredNations;

public abstract class Node {

	InspiredNations plugin;
	int ID;
	public Node(InspiredNations instance, int id) {
		plugin = instance;
		ID = id;
	}
	
	public abstract double getCoef();
	
	public abstract void buy(BigDecimal amount);
	
}
