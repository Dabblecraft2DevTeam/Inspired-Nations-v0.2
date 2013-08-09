package com.github.InspiredOne.InspiredNations.Economy.Nodes;

import java.math.BigDecimal;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.Economy.NPC;
import com.github.InspiredOne.InspiredNations.Economy.RefTrees.NodeRef;

public abstract class Node {

	InspiredNations plugin;
	NPC npc;
	NodeRef ref;
	double thresh = 0.0000001;
	Node[] elems;
	
	public Node(NPC instance, Node[] elems) {
		npc = instance;
		plugin = npc.plugin;
		ref = npc.node;
		this.elems = elems;
	}
	
	public abstract double getCoef();
	
	public abstract void buy(BigDecimal amount);
	
}
