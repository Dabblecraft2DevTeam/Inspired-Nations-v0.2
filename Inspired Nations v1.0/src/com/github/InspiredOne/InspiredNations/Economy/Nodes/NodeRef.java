package com.github.InspiredOne.InspiredNations.Economy.Nodes;

import java.util.HashMap;

public class NodeRef {
	HashMap<Integer, Node> ref = new HashMap<Integer, Node>();
	
	public NodeRef() {
	}
	
	public Node get(int id) {
		return ref.get(id);
	}
	
	public void put(int id, Node node) {
		ref.put(id, node);
	}
}
