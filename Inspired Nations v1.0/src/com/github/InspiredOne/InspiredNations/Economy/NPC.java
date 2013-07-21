package com.github.InspiredOne.InspiredNations.Economy;

import java.math.BigDecimal;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.Economy.RefTrees.NodeRef;

public class NPC {

	public BigDecimal[] buyVector;
	public BigDecimal[] costVector;
	public InspiredNations plugin;
	public NodeRef node;
	public ItemIndexes index;
	
	
	
	
	public NPC(InspiredNations instance) {
		plugin = instance;
		node = new NodeRef(this);
		index = plugin.index;
		buyVector = new BigDecimal[index.index.size()];
	}
	
	
}
