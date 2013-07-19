package com.github.InspiredOne.InspiredNations.Economy.Nodes;

import java.math.BigDecimal;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.Economy.NPC;
import com.github.InspiredOne.InspiredNations.Economy.RefTrees.NodeRef;

public abstract class Node {

	InspiredNations plugin;
	NPC npc;
	int ID;
	NodeRef ref;
	double thresh = 0.0000001;
	int[] elems;
	
	public Node(NPC instance, int id, int[] elems) {
		npc = instance;
		plugin = npc.plugin;
		ID = id;
		ref = npc.node;
		this.elems = elems;
		ref.put(ID, this);
	}
	
	public abstract double getCoef();
	
	public abstract void buy(BigDecimal amount);
	
}
