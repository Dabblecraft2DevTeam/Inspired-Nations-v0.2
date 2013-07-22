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
	ItemRefTrees item;
	
	Node Begin;
	Node Smelt;
	
	public NodeRef(NPC instance) {
		npc = instance;
		item = new ItemRefTrees(npc);

		
		Begin = new CobDugNode(npc, new double[] {1,1}, new Node[] {
			// { Armor
			new CobDugNode(npc, new double[] {1.25,2,1.75,1}, new Node[] { // Armor
				// { Helmet
				new PerfectSubNode(npc, new double[] {1,3,4,6,10}, new Node[] { // Helmet
						new ItemNode(npc, new ItemStack(298, 1, (short) 0), new Node[] { // Leather Helmet
							new PerfectCompNode(npc, new double[] {5}, new Node[] {
								new ItemNode(npc, new ItemStack(334, 1, (short) 0)) // Leather
							})
						}),
						new ItemNode(npc, new ItemStack(302, 1, (short) 0), new Node[] { // Chain Helmet
							new PerfectCompNode(npc, new double[] {5}, new Node[] {
								new ItemNode(npc, new ItemStack(51, 1, (short) 0)) // Fire
							})
						}),
						new ItemNode(npc, new ItemStack(314, 1, (short) 0), new Node[] { // Gold Helmet
							new PerfectCompNode(npc, new double[] {5}, new Node[] {
									item.goldIngot
							})
						}),
						new ItemNode(npc, new ItemStack(306, 1, (short) 0), new Node[] { // Iron Helmet
							new PerfectCompNode(npc, new double[] {5}, new Node[] {
									item.ironIngot
							})
						}),
						new ItemNode(npc, new ItemStack(310, 1, (short) 0), new Node[] { // Diamond Helmet
							new PerfectCompNode(npc, new double[] {5}, new Node[] {
								item.diamond
							})
						})
				}),
				// }
				// { ChestPlate
				new PerfectSubNode(npc, new double[] {1,3,4,6,10}, new Node[] { // Chestplate
						new ItemNode(npc, new ItemStack(299, 1, (short) 0), new Node[] { // Leather Chestplate
							new PerfectCompNode(npc, new double[] {8}, new Node[] {
								new ItemNode(npc, new ItemStack(334, 1, (short) 0)) // Leather
							})
						}),
						new ItemNode(npc, new ItemStack(303, 1, (short) 0), new Node[] { // Chain Chestplate
							new PerfectCompNode(npc, new double[] {8}, new Node[] {
								new ItemNode(npc, new ItemStack(51, 1, (short) 0)) // Fire
							})
						}),
						new ItemNode(npc, new ItemStack(315, 1, (short) 0), new Node[] { // Gold Chestplate
							new PerfectCompNode(npc, new double[] {8}, new Node[] {
									item.goldIngot
							})
						}),
						new ItemNode(npc, new ItemStack(307, 1, (short) 0), new Node[] { // Iron Chestplate
							new PerfectCompNode(npc, new double[] {8}, new Node[] {
									item.ironIngot
							})
						}),
						new ItemNode(npc, new ItemStack(311, 1, (short) 0), new Node[] { // Diamond Chestplate
							new PerfectCompNode(npc, new double[] {8}, new Node[] {
								item.diamond
							})
						})
				}),
				// }
				// { Leggings
				new PerfectSubNode(npc, new double[] {1,3,4,6,10}, new Node[] { // Leggings
						new ItemNode(npc, new ItemStack(300, 1, (short) 0), new Node[] { // Leather Leggings
							new PerfectCompNode(npc, new double[] {7}, new Node[] {
								new ItemNode(npc, new ItemStack(334, 1, (short) 0)) // Leather
							})
						}),
						new ItemNode(npc, new ItemStack(304, 1, (short) 0), new Node[] { // Chain Leggings
							new PerfectCompNode(npc, new double[] {7}, new Node[] {
								new ItemNode(npc, new ItemStack(51, 1, (short) 0)) // Fire
							})
						}),
						new ItemNode(npc, new ItemStack(316, 1, (short) 0), new Node[] { // Gold Leggings
							new PerfectCompNode(npc, new double[] {7}, new Node[] {
									item.goldIngot
							})
						}),
						new ItemNode(npc, new ItemStack(308, 1, (short) 0), new Node[] { // Iron Leggings
							new PerfectCompNode(npc, new double[] {7}, new Node[] {
									item.ironIngot
							})
						}),
						new ItemNode(npc, new ItemStack(312, 1, (short) 0), new Node[] { // Diamond Leggings
							new PerfectCompNode(npc, new double[] {7}, new Node[] {
								item.diamond
							})
						})
				}),
				// }
				// { Boots
				new PerfectSubNode(npc, new double[] {1,3,4,6,10}, new Node[] { // Boots
						new ItemNode(npc, new ItemStack(301, 1, (short) 0), new Node[] { // Leather Leggings
							new PerfectCompNode(npc, new double[] {4}, new Node[] {
								new ItemNode(npc, new ItemStack(334, 1, (short) 0)) // Leather
							})
						}),
						new ItemNode(npc, new ItemStack(305, 1, (short) 0), new Node[] { // Chain Leggings
							new PerfectCompNode(npc, new double[] {4}, new Node[] {
								new ItemNode(npc, new ItemStack(51, 1, (short) 0)) // Fire
							})
						}),
						new ItemNode(npc, new ItemStack(317, 1, (short) 0), new Node[] { // Gold Leggings
							new PerfectCompNode(npc, new double[] {4}, new Node[] {
									item.goldIngot
							})
						}),
						new ItemNode(npc, new ItemStack(309, 1, (short) 0), new Node[] { // Iron Leggings
							new PerfectCompNode(npc, new double[] {4}, new Node[] {
									item.ironIngot
							})
						}),
						new ItemNode(npc, new ItemStack(313, 1, (short) 0), new Node[] { // Diamond Leggings
							new PerfectCompNode(npc, new double[] {4}, new Node[] {
								item.diamond
							})
						})
				})
				// }
			}),
			// }
			// { Tools
			new CobDugNode(npc, new double[] {5,4,3.5,2,1}, new Node[] { // Tools
					// { Pickaxe
					new PerfectSubNode(npc, new double[] {1,2,2.5,5,7}, new Node[] { // Pickaxe
							new ItemNode(npc, new ItemStack(270, 1, (short) 0), new Node[] { // Wooden Pickaxe
								new PerfectCompNode(npc, new double[] {2,3}, new Node[] {
										item.stick,
										item.plank
								})
							}),
							new ItemNode(npc, new ItemStack(274, 1, (short) 0), new Node[] { // Stone Pickaxe
								new PerfectCompNode(npc, new double[] {2,3}, new Node[] {
										item.stick,
										new ItemNode(npc, new ItemStack(4, 1, (short) 0)) // Cobblestone
								})
							}),
							new ItemNode(npc, new ItemStack(285, 1, (short) 0), new Node[] { // Gold Pickaxe
								new PerfectCompNode(npc, new double[] {2,3}, new Node[] {
										item.stick,
										item.goldIngot
								})
							}),
							new ItemNode(npc, new ItemStack(257, 1, (short) 0), new Node[] { // Iron Pickaxe
								new PerfectCompNode(npc, new double[] {2,3}, new Node[] {
										item.stick,
										item.ironIngot
								})
							}),
							new ItemNode(npc, new ItemStack(278, 1, (short) 0), new Node[] { // Diamond Pickaxe
								new PerfectCompNode(npc, new double[] {2,3}, new Node[] {
										item.stick,
										item.diamond
								})
							})
					}),
					// }
					// { Shovel
					new PerfectSubNode(npc, new double[] {1,2,4,7}, new Node[] { // Shovel
							new ItemNode(npc, new ItemStack(269, 1, (short) 0), new Node[] { // Wooden Shovel
								new PerfectCompNode(npc, new double[] {2,1}, new Node[] {
										item.stick,
										item.plank
								})
							}),
							new ItemNode(npc, new ItemStack(273, 1, (short) 0), new Node[] { // Stone Shovel
								new PerfectCompNode(npc, new double[] {2,1}, new Node[] {
										item.stick,
										new ItemNode(npc, new ItemStack(4, 1, (short) 0)) // Cobblestone
								})
							}),
							new ItemNode(npc, new ItemStack(284, 1, (short) 0), new Node[] { // Gold Shovel
								new PerfectCompNode(npc, new double[] {2,1}, new Node[] {
										item.stick,
										item.goldIngot
								})
							}),
							new ItemNode(npc, new ItemStack(256, 1, (short) 0), new Node[] { // Iron Shovel
								new PerfectCompNode(npc, new double[] {2,1}, new Node[] {
										item.stick,
										item.ironIngot
								})
							}),
							new ItemNode(npc, new ItemStack(277, 1, (short) 0), new Node[] { // Diamond Shovel
								new PerfectCompNode(npc, new double[] {2,1}, new Node[] {
										item.stick,
										item.diamond
								})
							})
					}),
					// }
					// { Axe
					new PerfectSubNode(npc, new double[] {1,2,4,7}, new Node[] { // Axe
							new ItemNode(npc, new ItemStack(271, 1, (short) 0), new Node[] { // Wooden Axe
								new PerfectCompNode(npc, new double[] {2,3}, new Node[] {
										item.stick,
										item.plank
								})
							}),
							new ItemNode(npc, new ItemStack(275, 1, (short) 0), new Node[] { // Stone Axe
								new PerfectCompNode(npc, new double[] {2,3}, new Node[] {
										item.stick,
										new ItemNode(npc, new ItemStack(4, 1, (short) 0)) // Cobblestone
								})
							}),
							new ItemNode(npc, new ItemStack(286, 1, (short) 0), new Node[] { // Gold Axe
								new PerfectCompNode(npc, new double[] {2,3}, new Node[] {
										item.stick,
										item.goldIngot
								})
							}),
							new ItemNode(npc, new ItemStack(258, 1, (short) 0), new Node[] { // Iron Axe
								new PerfectCompNode(npc, new double[] {2,3}, new Node[] {
										item.stick,
										item.ironIngot
								})
							}),
							new ItemNode(npc, new ItemStack(279, 1, (short) 0), new Node[] { // Diamond Axe
								new PerfectCompNode(npc, new double[] {2,3}, new Node[] {
										item.stick,
										item.diamond
								})
							})
					}),
					// }
					// { Shears
					new ItemNode(npc, new ItemStack(359, 1, (short) 0), new Node[] { // Shears
						new PerfectCompNode(npc, new double[] {2}, new Node[] {
								item.ironIngot
						})
					}),
					// }
					// { Hoe
					new PerfectSubNode(npc, new double[] {1,2,2.3,2.4}, new Node[] { // Hoe
							new ItemNode(npc, new ItemStack(290, 1, (short) 0), new Node[] { // Wooden Hoe
								new PerfectCompNode(npc, new double[] {2,2}, new Node[] {
										item.stick,
										item.plank
								})
							}),
							new ItemNode(npc, new ItemStack(291, 1, (short) 0), new Node[] { // Stone Hoe
								new PerfectCompNode(npc, new double[] {2,2}, new Node[] {
										item.stick,
										new ItemNode(npc, new ItemStack(4, 1, (short) 0)) // Cobblestone
								})
							}),
							new ItemNode(npc, new ItemStack(294, 1, (short) 0), new Node[] { // Gold Hoe
								new PerfectCompNode(npc, new double[] {2,2}, new Node[] {
										item.stick,
										item.goldIngot
								})
							}),
							new ItemNode(npc, new ItemStack(292, 1, (short) 0), new Node[] { // Iron Hoe
								new PerfectCompNode(npc, new double[] {2,2}, new Node[] {
										item.stick,
										item.ironIngot
								})
							}),
							new ItemNode(npc, new ItemStack(293, 1, (short) 0), new Node[] { // Diamond Hoe
								new PerfectCompNode(npc, new double[] {2,2}, new Node[] {
										item.stick,
										item.diamond
								})
							}),
					})
					// }
			})
			// }
		});
	}
	
	public Node get(int id) {
		return ref.get(id);
	}
	
	public void put(int id, Node node) {
		ref.put(id, node);
	}
}
