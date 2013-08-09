package com.github.InspiredOne.InspiredNations.HUD.ManageCountry;

import java.util.HashMap;
import java.util.Vector;

import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.HUD.HudConversationMain;
import com.github.InspiredOne.InspiredNations.HUD.Menu;
import com.github.InspiredOne.InspiredNations.Regions.Cell;
import com.github.InspiredOne.InspiredNations.Regions.Town;
import com.github.InspiredOne.InspiredNations.Tools.menuType;

public class ManageCountryPeople extends Menu {

	// Constructor
	public ManageCountryPeople(InspiredNations instance, Player playertemp, int errortemp) {
		super(instance, playertemp, errortemp);
		country = PDI.getCountryRuled();
	}
	
	// Constructor
	public ManageCountryPeople(InspiredNations instance, Player playertemp, int errortemp, Vector<String> names) {
		super(instance, playertemp, errortemp, names);
		country = PDI.getCountryRuled();
	}
	
	@Override
	public String getPromptText(ConversationContext arg0) {
		String space = tools.space();
		String main = tools.header("Manage People. Type an option number.");
		String options = "";
		String end = tools.footer(false);
		String errmsg = ChatColor.RED + tools.errors.get(error) + names;
		
		// Make Inputs Vector
		inputs.add("Make Coruler <player>");
		inputs.add("Remove Coruler <player>");
		inputs.add("Jail <player>");

		// Make options text
		options = options.concat(menuType.LABEL + "Corulers: " + menuType.VALUE + tools.format(country.getCoRulers()) + "\n");
		options = tools.addDivider(options);
		options = options.concat(tools.options(inputs));
		return space + main + options + end + errmsg;
	}
	
	@Override
	public Prompt acceptInput(ConversationContext arg0, String arg) {
		int answer = 0;
		if (arg.startsWith("/")) {
			arg = arg.substring(1);
		}
		if (arg.equalsIgnoreCase("back")) {
			return new ManageCountry(plugin, player, 0);
		}
		String[] args = arg.split(" ");
		if (args[0].equalsIgnoreCase("say"))  {
			if(args.length > 1) {
				PMeth.SendChat(tools.formatSpace(tools.subArray(args, 1, args.length - 1)));
			}
			return new ManageCountryPeople(plugin, player, 0);
		}
		
		try {
			answer = Integer.decode(args[0])-1;
		}
		catch (Exception ex) {
			return new ManageCountryPeople(plugin, player,1);
		}
		
		if (answer > inputs.size()-1) {
			return new ManageCountryPeople(plugin, player, 2);
		}
		
		if(inputs.get(answer).equals("Make Coruler <player>")) {
			if(args.length != 2) {
				return new ManageCountryPeople(plugin, player, 3);
			}
			else {
				Vector<String> people = tools.find(args[1], country.getResidents());
				if(people.size() == 0) {
					return new ManageCountryPeople(plugin, player, 46);
				}
				else if(people.size() > 1) {
					return new ManageCountryPeople(plugin, player, 4, people);
				}
				else {
					country.addCoRuler(people.get(0));
					return new ManageCountryPeople(plugin, player, 0);
				}
			}
		}
		
		if(inputs.get(answer).equals("Remove Coruler <player>")) {
			if(args.length != 2) {
				return new ManageCountryPeople(plugin, player, 3);
			}
			else {
				Vector<String> people = tools.find(args[1], country.getCoRulers());
				if(people.size() == 0) {
					return new ManageCountryPeople(plugin, player, 78);
				}
				else if (people.size() > 1) {
					return new ManageCountryPeople(plugin, player, 4, people);
				}
				else {
					
					country.removeCoRuler(people.get(0));
					if(!country.getCoRulers().contains(player.getName())) {
						return new HudConversationMain(plugin, player, 0);
					}
					else {
						return new ManageCountryPeople(plugin, player, 0);
					}
				}
			}
		}
		
		if(inputs.get(answer).equals("Jail <player>")) {
			if(args.length != 2) {
				return new ManageCountryPeople(plugin, player, 3);
			}
			else {
				Vector<String> people = tools.find(args[1], country.getResidents());
				if(people.size() == 0) {
					return new ManageCountryPeople(plugin, player, 46);
				}
				else if(people.size() > 1) {
					return new ManageCountryPeople(plugin, player, 4, people);
				}
				else {
					boolean jailed = false;
					if(country.hasCapital()) {
						if(country.getCapital().hasPrison()) {
							HashMap<String, Cell> cells = country.getCapital().getPrison().getCells();
							for(Cell cell:cells.values()) {
								if(!cell.isOccupied()) {
									jailed = true;
									//TODO teleport prisoner to jail, make sure you account for horses
									return new ManageCountryPeople(plugin, player, 0);
								}
							}
						}
						else {
							for(Town town:country.getTowns()) {
								if(town.hasPrison()) {
									HashMap<String, Cell> cells = town.getPrison().getCells();
									for(Cell cell:cells.values()) {
										if(!cell.isOccupied()) {
											jailed = true;
											//TODO teleport prisoner to jail, make sure you account for horses
											return new ManageCountryPeople(plugin, player, 0);
										}
									}
								}
							}
						}
					}
					else {
						for(Town town:country.getTowns()) {
							if(town.hasPrison()) {
								HashMap<String, Cell> cells = town.getPrison().getCells();
								for(Cell cell:cells.values()) {
									if(!cell.isOccupied()) {
										jailed = true;
										//TODO teleport prisoner to jail, make sure you account for horses
										return new ManageCountryPeople(plugin, player, 0);
									}
								}
							}
						}
					}
					if(!jailed) {
						return new ManageCountryPeople(plugin, player, 79);
					}
				}
			}
		}
		
		return new ManageCountryPeople(plugin, player, 2);
	}



}
