package com.github.InspiredOne.InspiredNations.Economy.RefTrees;

import java.util.HashMap;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.Economy.NPC;
import com.github.InspiredOne.InspiredNations.Economy.Nodes.ItemNode;
import com.github.InspiredOne.InspiredNations.Economy.Nodes.Node;
import com.github.InspiredOne.InspiredNations.Economy.Nodes.PerfectCompNode;

public class NodeRef {
	HashMap<Integer, Node> ref = new HashMap<Integer, Node>();
	InspiredNations plugin;
	NPC npc;
	
	public NodeRef(NPC instance) {
		npc = instance;
		new ItemNode(npc, 1, 2, 200); //Iron Sword
		new PerfectCompNode(npc, 2, new int[] {3,4}, new double[] {2,1}); //
		new ItemNode(npc, 3, 5, 100);
	}
	
	public Node get(int id) {
		return ref.get(id);
	}
	
	public void put(int id, Node node) {
		ref.put(id, node);
	}
}
