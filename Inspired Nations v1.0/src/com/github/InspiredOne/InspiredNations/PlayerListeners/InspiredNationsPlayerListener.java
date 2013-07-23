package com.github.InspiredOne.InspiredNations.PlayerListeners;


import org.bukkit.Location;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;


import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.PlayerData;
import com.github.InspiredOne.InspiredNations.PlayerMethods;
import com.github.InspiredOne.InspiredNations.PlayerModes;
import com.github.InspiredOne.InspiredNations.Economy.ItemIndexes;
import com.github.InspiredOne.InspiredNations.HUD.SelectBusiness3;
import com.github.InspiredOne.InspiredNations.HUD.SelectHouse2;
import com.github.InspiredOne.InspiredNations.HUD.ShowMap;
import com.github.InspiredOne.InspiredNations.HUD.ManageBusiness.ReselectBusiness2;
import com.github.InspiredOne.InspiredNations.HUD.ManageCountry.SelectFederalPark2;
import com.github.InspiredOne.InspiredNations.HUD.ManageHouse.ReselectHouse2;
import com.github.InspiredOne.InspiredNations.ManageTown.SelectBank2;
import com.github.InspiredOne.InspiredNations.ManageTown.SelectPark2;
import com.github.InspiredOne.InspiredNations.ManageTown.SelectPrison2;
import com.github.InspiredOne.InspiredNations.Regions.Country;
import com.github.InspiredOne.InspiredNations.Regions.LocalBank;
import com.github.InspiredOne.InspiredNations.Regions.LocalPrison;
import com.github.InspiredOne.InspiredNations.Regions.Park;
import com.github.InspiredOne.InspiredNations.Regions.Town;

public class InspiredNationsPlayerListener implements Listener {

	public String previous = "";
	// Grabbing instance of plugin
	ItemIndexes index;
	InspiredNations plugin;
	public InspiredNationsPlayerListener(InspiredNations instance) {
		plugin = instance;
		index = new ItemIndexes(plugin);
	}
	
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
 		String playername = player.getName();
		if (!plugin.playerdata.containsKey(playername)) {
			plugin.playerdata.put(playername, new PlayerData(plugin, playername));
		}
		plugin.playermodes.put(playername, new PlayerModes(plugin, playername));
		PlayerMethods PM = new PlayerMethods(plugin, player);
		PM.resetLocationBooleans();
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		String playername = player.getName();
		PlayerModes PM = plugin.playermodes.get(playername);
		PM.setBlocksBack();
	}
	
	@EventHandler
	public void onPlayerKick(PlayerKickEvent event) {
		Player player = event.getPlayer();
		String playername = player.getName();
		PlayerModes PM = plugin.playermodes.get(playername);
		PM.setBlocksBack();
	}
	
	@EventHandler
	public void onSignChange(SignChangeEvent event) {
		ChestShopPlayerListener CSPL = new ChestShopPlayerListener(plugin, event);
		CSPL.onSignChangeEvent();
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		ChestShopPlayerListener CSPL = new ChestShopPlayerListener(plugin, event);
		CSPL.onBlockPlace();
	}
	
	@EventHandler
	public void onPlayerHeld(PlayerItemHeldEvent event) {
		ChestShopPlayerListener CSPL = new ChestShopPlayerListener(plugin, event);
		CSPL.onItemHeld();
		
		;
		ItemStack actual = event.getPlayer().getInventory().getItem(event.getNewSlot());
		ItemStack test = actual.clone();
		
		test.setAmount(1);
		if(test.equals(new ItemStack(278,1,(short) 0))) {
			System.out.println("Here I am!!)");
			actual.addEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 3);
		}
		
		System.out.println(index.contains(test));
		if(index.contains(test)) {
			System.out.println(index.get(test));
		}
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		String playername = player.getName();
		PlayerModes PM = plugin.playermodes.get(playername);
		
		if(resetPlayerModes(player, PM)) return;
		
		ClaimLocalBankPlayerListener LBPL = new ClaimLocalBankPlayerListener(plugin, event);
		ClaimParkPlayerListener PPL = new ClaimParkPlayerListener(plugin, event);
		ClaimLocalPrisonPlayerListener LPPL = new ClaimLocalPrisonPlayerListener(plugin, event);
		ClaimFederalParkPlayerListener FPPL = new ClaimFederalParkPlayerListener(plugin, event);
		ClaimHousePlayerListener CHPL = new ClaimHousePlayerListener(plugin, event);
		ClaimServiceBusinessPlayerListener CSBPL = new ClaimServiceBusinessPlayerListener(plugin, event);
		ClaimGoodBusinessPlayerListener CGBPL = new ClaimGoodBusinessPlayerListener(plugin, event);
		ChestShopPlayerListener CSPL = new ChestShopPlayerListener(plugin, event);
		LBPL.onPlayerInteract();
		PPL.onPlayerInteract();
		LPPL.onPlayerInteract();
		FPPL.onPlayerInteract();
		CHPL.onPlayerInteract();
		CSBPL.onPlayerInteract();
		CGBPL.onPlayerInteract();
		CSPL.onInteractWithChest();
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		
		Player player = event.getPlayer();
		String playername = player.getName();
		PlayerData PDI = plugin.playerdata.get(playername);
		PlayerModes PM = plugin.playermodes.get(playername);
		Location spot = event.getTo();
		
		// put this under onPlayerInteract
		if(resetPlayerModes(player, PM)) return;
		
		if(PM.houseSelect() || PM.isReSelectGoodBusiness() || PM.isReSelectServiceBusiness() || PM.isReSelectHouse() ||PM.isReSelectLocalPark()
				|| PM.goodBusinessSelect() || PM.serviceBusinessSelect() || PM.federalParkSelect() || PM.isMap() || PM.parkSelect() || PM.localBankSelect()
				|| PM.localPrisonSelect()) {
			generateMessage(PDI);
		}
		
		
		if (event.getTo().getBlockX() != event.getFrom().getBlockX() || event.getTo().getBlockZ() != event.getFrom().getBlockZ()
				|| (Math.round((float)(event.getFrom().getYaw()/45.)) != Math.round((float)(event.getTo().getYaw()/45.)))) { 
			ClaimCountryLandPlayerListener CLPL = new ClaimCountryLandPlayerListener(plugin, event);
			UnclaimCountryLandPlayerListener UCLPL = new UnclaimCountryLandPlayerListener(plugin, event);
			ClaimTownLandPlayerListener TLPL = new ClaimTownLandPlayerListener(plugin, event);
			UnclaimTownLandPlayerListener UCTLPL = new UnclaimTownLandPlayerListener(plugin, event);
			ClaimFederalParkPlayerListener FPPL = new ClaimFederalParkPlayerListener(plugin, event);
			CLPL.onPlayerMove();
			UCLPL.onPlayerMove();
			TLPL.onPlayerMove();
			FPPL.onPlayerMove();
			UCTLPL.onPlayerMove();
		
			// Player Tracker
			
			// In Country
			for(String countryname:plugin.countrydata.keySet()) {
				Country country = plugin.countrydata.get(countryname);
				if(country.isIn(spot)) {
					if(PDI.getIsInCountry()) {
						if(PDI.getCountryIn().equals(country)) {
							break;
						}
						else {
							if (!PM.preCountrySelect() && !PM.countrySelect() && !PM.countryDeselect() && !PM.preDeselectCountry()) {
								player.sendRawMessage("You have exited " + PDI.getCountryIn().getName());
							}
							PDI.setCountryIn(country);
							if(!PM.preCountrySelect() && !PM.countrySelect() && !PM.countryDeselect() && !PM.preDeselectCountry()) {
								player.sendRawMessage("You have entered " + PDI.getCountryIn().getName());
							}
							break;
						}
					}
					else {
						PDI.setCountryIn(country);
						if(!PM.preCountrySelect() && !PM.countrySelect() && !PM.countryDeselect() && !PM.preDeselectCountry())
						player.sendRawMessage("You have entered " + PDI.getCountryIn().getName());
						break;
					}
				}
				else {
					if(PDI.getIsInCountry()) {
						if(PDI.getCountryIn().equals(country)) {
							if (!PM.preCountrySelect() && !PM.countrySelect() && !PM.countryDeselect() && !PM.preDeselectCountry()) {
								player.sendRawMessage("You have exited " + PDI.getCountryIn().getName());
							}
							PDI.setCountryIn(null);
							break;
						}
					}
				}
			}
			
			if(PDI.getIsInCountry()) {
				// In Country Park
				for(Park park : PDI.getCountryIn().getParks()) {
					if(park.isIn(spot)) {
						if(PDI.getIsInFederalPark()) {
							if(PDI.getFederalParkIn().equals(park)) {
								break;
							}
							else {
								player.sendRawMessage("You have exited " + PDI.getFederalParkIn().getName() + " Federal Park.");
								PDI.setFederalParkIn(park);
								player.sendRawMessage("You have entered " + PDI.getFederalParkIn().getName() + " Federal Park.");
								break;
							}
						}
						else {
							PDI.setFederalParkIn(park);
							player.sendRawMessage("You have entered " + PDI.getFederalParkIn().getName() + " Federal Park.");
							break;
						}
					}
					else {
						if(PDI.getIsInFederalPark()) {
							if(PDI.getFederalParkIn().equals(park)) {
								player.sendRawMessage("You have exited " + PDI.getFederalParkIn().getName() + " Federal Park.");
								PDI.setFederalParkIn(null);
								break;
							}
						}
					}
				}
				// In Town
				for(Town town : PDI.getCountryIn().getTowns()) {
					if(town.isIn(spot)) {
						if(PDI.getIsInTown()) {
							if(PDI.getTownIn().equals(town)) {
								break;
							}
							else {
								player.sendRawMessage("You have exited " + PDI.getTownIn().getName());
								PDI.setTownIn(town);
								player.sendRawMessage("You have entered " + PDI.getTownIn().getName());
								break;
							}
						}
						else {
							PDI.setTownIn(town);
							player.sendRawMessage("You have entered " + PDI.getTownIn().getName());
							break;
						}
					}
					else {
						if(PDI.getIsInTown()) {
							if(PDI.getTownIn().equals(town)) {
								player.sendRawMessage("You have exited " + PDI.getTownIn().getName());
								PDI.setTownIn(null);
								break;
							}
						}
					}
				}
				if(PDI.getIsInTown()) {
					// In local Park
					for(Park park:PDI.getTownIn().getParks()) {
						if(park.isIn(spot)) {
							if(PDI.getIsInLocalPark()) {
								if(PDI.getLocalParkIn().equals(park)) {
									break;
								}
								else {
									player.sendRawMessage("You have exited " + PDI.getLocalParkIn().getName());
									PDI.setLocalParkIn(park);
									player.sendRawMessage("You have entered " + PDI.getLocalParkIn().getName());
									break;
								}
							}
							else {
								PDI.setLocalParkIn(park);
								player.sendRawMessage("You have entered " + PDI.getLocalParkIn().getName());
								break;
							}
						}
						else {
							if(PDI.getIsInLocalPark()) {
								if(PDI.getLocalParkIn().equals(park)) {
									player.sendRawMessage("You have exited " + PDI.getLocalParkIn().getName());
									PDI.setLocalParkIn(null);
									break;
								}
							}
						}
					}
					// Bank
					if(PDI.getTownIn().hasBank()) {
						LocalBank bank = PDI.getTownIn().getBank();
						if(bank.isIn(spot)) {
							if(PDI.getIsInLocalBank()) {
								if(PDI.getLocalBankIn().equals(bank)) {
								}
								else {
									player.sendRawMessage("You have exited the local bank of " + PDI.getTownIn().getName());
									PDI.setLocalBankIn(bank);
									player.sendRawMessage("You have entered the local bank of " + PDI.getTownIn().getName());
								}
							}
							else {
								PDI.setLocalBankIn(bank);
								player.sendRawMessage("You have entered the local bank of " + PDI.getTownIn().getName());
							}
						}
						else {
							if(PDI.getIsInLocalBank()) {
								if(PDI.getLocalBankIn().equals(bank)) {
									player.sendRawMessage("You have exited the local bank of " + PDI.getTownIn().getName());
									PDI.setLocalBankIn(null);
								}
							}
						}
					}
					// Prison
					if(PDI.getTownIn().hasPrison()) {
						LocalPrison prison = PDI.getTownIn().getPrison();
						if(prison.isIn(spot)) {
							if(PDI.getIsInLocalPrison()) {
								if(PDI.getLocalPrisonIn().equals(prison)) {
								}
								else { 
									player.sendRawMessage("You have exited the local prison of " + PDI.getTownIn().getName());
									PDI.setLocalPrisonIn(prison);
									player.sendRawMessage("You have entered the local prison of " + PDI.getTownIn().getName());
								}
							}
							else {
								PDI.setLocalPrisonIn(prison);
								player.sendRawMessage("You have entered the local prison of " + PDI.getTownIn().getName());
							}
						}
						else {
							if(PDI.getIsInLocalPrison()) {
								if(PDI.getLocalPrisonIn().equals(prison)) {
									player.sendRawMessage("You have exited the local prison of " + PDI.getTownIn().getName());
									PDI.setLocalPrisonIn(null);
								}
							}
						}
					}
				}
			}
		}
	}
	
	public void generateMessage(PlayerData PDI) {
		PlayerModes PM = plugin.playermodes.get(PDI.playername);
		Player player = plugin.getServer().getPlayer(PDI.playername);
		Prompt convo;
		ConversationContext arg = PDI.getConversation().getContext();

		
		if (PM.isMap()) convo = new ShowMap(plugin, player, 0);
		else if (PM.houseSelect()) convo = new SelectHouse2(plugin, player, 0);
		else if (PM.parkSelect()) convo = new SelectPark2(plugin, player, 0);
		else if (PM.localBankSelect()) convo = new SelectBank2(plugin, player, 0);
		else if (PM.localPrisonSelect()) convo = new SelectPrison2(plugin, player, 0);
		else if (PM.goodBusinessSelect()) convo = new SelectBusiness3(plugin, player, 0);
		else if (PM.serviceBusinessSelect()) convo = new SelectBusiness3(plugin, player, 0);
		else if (PM.federalParkSelect()) convo = new SelectFederalPark2(plugin, player, 0);
		else if (PM.isReSelectGoodBusiness()) convo = new ReselectBusiness2(plugin, player, 0);
		else if (PM.isReSelectServiceBusiness()) convo = new ReselectBusiness2(plugin, player, 0);
		else if (PM.isReSelectHouse()) convo = new ReselectHouse2(plugin, player, 0);
		else convo = null;
		
		String current = convo.getPromptText(arg);
		if (!current.equals(plugin.InspiredNationsPL.previous)) {
			PDI.getConversation().outputNextPrompt();
			plugin.InspiredNationsPL.previous = current;
		}
		return;
	}
	
	// method to reset player mode values
	public boolean resetPlayerModes(Player player, PlayerModes PM) {
		if (!player.isConversing() 
			
				&& (PM.preCountrySelect() 
				
						
						|| PM.countrySelect() 
						|| PM.townSelect() 
						|| PM.preTownSelect() 
						|| PM.preDeselectCountry() 
						|| PM.countryDeselect()
						|| PM.localBankSelect() 
						|| PM.parkSelect() 
						|| PM.localPrisonSelect() 
						|| PM.federalParkSelect() 
						|| PM.houseSelect() 
						|| PM.serviceBusinessSelect() 
						|| PM.goodBusinessSelect() 
						|| PM.getPlaceItem() 
						|| PM.isReSelectGoodBusiness()
						|| PM.isReSelectHouse()
						|| PM.isReSelectServiceBusiness()
						|| PM.isReSelectLocalPark()
						|| PM.placesign
						|| PM.isMap())
						) {
			PM.preCountry(false);
			PM.predecountry(false);
			PM.country(false);
			PM.decountry(false);
			PM.town(false);
			PM.preTown(false);
			PM.localBank(false);
			PM.park(false);
			PM.localPrison(false);
			PM.federalPark(false);
			PM.house(false);
			PM.goodBusiness(false);
			PM.serviceBusiness(false);
			PM.setReSelectGoodBusiness(false);
			PM.setReSelectHouse(false);
			PM.setReSelectServiceBusiness(false);
			PM.setReSelectLocalPark(false);
			PM.setBlocksBack();
			PM.selectCuboid(false);
			PM.selectPolygon(false);
			PM.setPlaceItem(false);
			PM.placesign = false;
			PM.setMap(false);
			return true;
		}
		else return false;
	}
}
