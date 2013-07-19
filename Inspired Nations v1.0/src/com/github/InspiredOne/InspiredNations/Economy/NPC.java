package com.github.InspiredOne.InspiredNations.Economy;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.Economy.RefTrees.NodeRef;

public class NPC {

	double[] buyVector = new double[500];
	
	
	public InspiredNations plugin;
	public NodeRef node;
	public ItemIndexes index;
	
	public NPC(InspiredNations instance) {
		plugin = instance;
		node = new NodeRef(this);
		index = plugin.index;
	}
	
	
}
