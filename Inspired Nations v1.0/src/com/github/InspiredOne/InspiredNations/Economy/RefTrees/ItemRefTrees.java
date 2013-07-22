package com.github.InspiredOne.InspiredNations.Economy.RefTrees;

import org.bukkit.inventory.ItemStack;

import com.github.InspiredOne.InspiredNations.Economy.NPC;
import com.github.InspiredOne.InspiredNations.Economy.Nodes.ItemNode;
import com.github.InspiredOne.InspiredNations.Economy.Nodes.Node;
import com.github.InspiredOne.InspiredNations.Economy.Nodes.PerfectCompNode;
import com.github.InspiredOne.InspiredNations.Economy.Nodes.PerfectSubNode;

public class ItemRefTrees {

	NPC npc;
	
	public Node smelt;
	public Node goldIngot;
	public Node ironIngot;
	public Node diamond;
	public Node stick;
	public Node plank;
	
	public ItemRefTrees(NPC npc) {
		this.npc = npc;
		
		smelt = new PerfectSubNode(npc, new double[] {1./8., 1./8., 1./72., 1, 1, 2, 2./3.}, new Node[] {
					new ItemNode(npc, new ItemStack(263, 1, (short) 0)), // Coal
					new ItemNode(npc, new ItemStack(263, 1, (short) 1)), // Charcoal
					new ItemNode(npc, new ItemStack(173, 1, (short) 0)), // Coal Block
					new ItemNode(npc, new ItemStack(270, 1, (short) 0), new Node[] { // Wooden Pickaxe
						new PerfectCompNode(npc, new double[] {2,3}, new Node[] {
								this.stick,
								this.plank
						})
					}),
					new ItemNode(npc, new ItemStack(269, 1, (short) 0), new Node[] { // Wooden Shovel
						new PerfectCompNode(npc, new double[] {2,1}, new Node[] {
								this.stick,
								this.plank
						})
					}),
					new ItemNode(npc, new ItemStack(271, 1, (short) 0), new Node[] { // Wooden Axe
						new PerfectCompNode(npc, new double[] {2,3}, new Node[] {
								this.stick,
								this.plank
						})
					}),
					new ItemNode(npc, new ItemStack(290, 1, (short) 0), new Node[] { // Wooden Hoe
						new PerfectCompNode(npc, new double[] {2,2}, new Node[] {
								this.stick,
								this.plank
						})
					}),
					this.stick,
					this.plank
				});
		
		stick = new PerfectSubNode(npc, new double[] {1, 1./2.}, new Node[] {
				new ItemNode(npc, new ItemStack(280, 1, (short) 0), new Node[] {
					new PerfectCompNode(npc, new double[] {2}, new Node[] {
							this.plank
					})
				}),
				new ItemNode(npc, new ItemStack(333, 1, (short) 0))
		});
		
		plank = new PerfectSubNode(npc, new double[] {1,1,1,1,1./3.,1,1,1,1}, new Node[] {
				new ItemNode(npc, new ItemStack(5,1,(short) 0)), // Oak Plank
				new ItemNode(npc, new ItemStack(5,1,(short) 1)), // Spruce Plank
				new ItemNode(npc, new ItemStack(5,1,(short) 2)), // Birch Plank
				new ItemNode(npc, new ItemStack(5,1,(short) 3)), // Jungle Plank
				new ItemNode(npc, new ItemStack(333, 1, (short) 0)), // Boat
				new PerfectCompNode(npc, new double[] {1./4.}, new Node[] {
						new ItemNode(npc, new ItemStack(17,1,(short) 0)) // Oak Log
				}),
				new PerfectCompNode(npc, new double[] {1./4.}, new Node[] {
						new ItemNode(npc, new ItemStack(17,1,(short) 1)) // Spruce Log
				}),
				new PerfectCompNode(npc, new double[] {1./4.}, new Node[] {
						new ItemNode(npc, new ItemStack(17,1,(short) 2)) // Birch Log
				}),
				new PerfectCompNode(npc, new double[] {1./4.}, new Node[] {
						new ItemNode(npc, new ItemStack(17,1,(short) 3)) // Jungle Log
				})
				
		});
		
		goldIngot = new PerfectSubNode(npc, new double[] {1,1,1,1}, new Node[] {
						new ItemNode(npc, new ItemStack(266, 1, (short) 0)), // Gold Ingot
						new PerfectCompNode(npc, new double[] {9}, new Node[] {
							new ItemNode(npc, new ItemStack(371, 1, (short) 0)) // Gold Nugget	
						}),
						new PerfectCompNode(npc, new double[] {1, 1}, new Node[] {
							new ItemNode(npc, new ItemStack(14, 1, (short) 0)), // Gold Ore
							smelt
						}),
						new PerfectCompNode(npc, new double[] {1./9.}, new Node[] {
							new ItemNode(npc, new ItemStack(41, 1, (short) 0)) // Gold Block
						})
					});
		
		ironIngot = new PerfectSubNode(npc, new double[] {1,1}, new Node[] {
						new PerfectCompNode(npc, new double[] {1}, new Node[] {
							new ItemNode(npc, new ItemStack(265, 1, (short) 0)) // Iron Ingot
						}),
						new PerfectCompNode(npc, new double[] {1, 1./8.}, new Node[] {
							new ItemNode(npc, new ItemStack(15, 1, (short) 0)), // Iron Ore
							smelt
						}),
						new PerfectCompNode(npc, new double[] {1/9}, new Node[] {
							new ItemNode(npc, new ItemStack(42, 1, (short) 0)) // Iron Block
						})
					});
		
		diamond = new PerfectSubNode(npc, new double[] {}, new Node[] {
						new PerfectCompNode(npc, new double[] {1}, new Node[] {
							new ItemNode(npc, new ItemStack(264, 1, (short) 0)) // Diamond
						}),
						new PerfectCompNode(npc, new double[] {1}, new Node[] {
							new ItemNode(npc, new ItemStack(56, 1, (short) 0)) // Diamond Ore
						}),
						new PerfectCompNode(npc, new double[] {1./9.}, new Node[] {
							new ItemNode(npc, new ItemStack(57, 1, (short) 0)) // Diamond Block
						})
					});
		
		
	}
	
}
