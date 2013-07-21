package com.github.InspiredOne.InspiredNations.Economy.RefTrees;

import java.util.HashMap;

import org.bukkit.inventory.ItemStack;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.Economy.NPC;
import com.github.InspiredOne.InspiredNations.Economy.Nodes.CobDugNode;
import com.github.InspiredOne.InspiredNations.Economy.Nodes.ItemNode;
import com.github.InspiredOne.InspiredNations.Economy.Nodes.Node;
import com.github.InspiredOne.InspiredNations.Economy.Nodes.PerfectCompNode;
import com.github.InspiredOne.InspiredNations.Economy.Nodes.PerfectSubNode;

public class NodeRef {
	HashMap<Integer, Node> ref = new HashMap<Integer, Node>();
	InspiredNations plugin;
	NPC npc;
	
	Node Begin;
	
	public NodeRef(NPC instance) {
		npc = instance;
		Begin = new CobDugNode(npc, 1, new int[] {2}, new double[] {1});
			new CobDugNode(npc, 2, new int[] {3,4,5,6}, new double[] {1,1,1,1}); // Armor
				new PerfectSubNode(npc, 3, new int[] {7,8,9,10,11}, new double[] {1,3,4,6,10}); // Helmet
				
					new ItemNode(npc, 7, 37, new ItemStack(298, 1, (short) 0)); //Leather Helmet Item
						new PerfectCompNode(npc, 37, new int[] {38}, new double[] {5}); // Leather
							new ItemNode(npc, 38, -1, new ItemStack(334, 1, (short) 0)); // Leather
							
					new ItemNode(npc, 8, 39, new ItemStack(302, 1, (short) 0)); //Chain Helmet Item
						new PerfectCompNode(npc, 39, new int[] {40}, new double[] {5}); // Fire
							new ItemNode(npc, 40, -1, new ItemStack(51, 1, (short) 0)); // Fire
							
					new ItemNode(npc, 9, 41, new ItemStack(314, 1, (short) 0)); // Gold Helmet Item
						new PerfectSubNode(npc, 41, new int[] {32, 33}, new double[] {1,1});
							new PerfectCompNode(npc, 32, new int[] {38}, new double[] {5}); // Gold Ingot
								new ItemNode(npc, 38, -1, new ItemStack(334, 1, (short) 0)); // Gold Ingot
							new PerfectCompNode(npc, 33, new int[] {);
				new PerfectSubNode(npc, 4, new int[] {12,13,14,15,16}, new double[] {1,3,4,6,10}); // Chestplate
				new PerfectSubNode(npc, 5, new int[] {17,18,19,20,21}, new double[] {1,3,4,6,10}); // Leggings
				new PerfectSubNode(npc, 6, new int[] {22,23,24,25,26}, new double[] {1,3,4,6,10}); // Boots
	}
	
	public Node get(int id) {
		return ref.get(id);
	}
	
	public void put(int id, Node node) {
		ref.put(id, node);
	}
}
