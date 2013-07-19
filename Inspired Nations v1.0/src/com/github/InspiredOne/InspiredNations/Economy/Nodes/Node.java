package com.github.InspiredOne.InspiredNations.Economy.Nodes;

import java.math.BigDecimal;

import com.github.InspiredOne.InspiredNations.InspiredNations;

public abstract class Node {

	InspiredNations plugin;
	int ID;
	NodeRef ref;
	double thresh = 0.0000001;
	int[] elems;
	
	public Node(InspiredNations instance, int id, int[] elems) {
		plugin = instance;
		ID = id;
		ref = plugin.node;
		this.elems = elems;
		ref.put(ID, this);
	}
	
	public abstract double getCoef();
	
	public abstract void buy(BigDecimal amount);
	
}
