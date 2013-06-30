package com.github.InspiredOne.InspiredNations.HUD.ManageCountry;

import java.math.BigDecimal;

import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.Tools.menuType;
import com.github.InspiredOne.InspiredNations.HUD.HudConversationMain;
import com.github.InspiredOne.InspiredNations.HUD.Menu;
import com.github.InspiredOne.InspiredNations.HUD.NewCountry.NewCountry2;
import com.github.InspiredOne.InspiredNations.HUD.NewCountry.NewCountry3;
import com.github.InspiredOne.InspiredNations.Regions.ChunkData;
import com.github.InspiredOne.InspiredNations.Tools.optionType;

public class ManageCountry extends Menu{
	// Constructor
	public ManageCountry(InspiredNations instance, Player playertemp, int errortemp) {
		super(instance, playertemp, errortemp);
		country = PDI.getCountryRuled();
	}

	@Override
	public String getPromptText(ConversationContext arg0) {
		String space = tools.space();
		String main = tools.header("Manage Country. Type an option number.");
		String options = "";
		String end = tools.footer(false);
		String errmsg = ChatColor.RED + tools.errors.get(error);
		
		// Make inputs vector
		if(country.getPluralMoney().equals("") || country.getMoneyMultiplyer().compareTo(new BigDecimal(Math.PI)) == 0) {
			inputs.add("Finish Economy Setup");
		}
		else {
			if(country.getCoRulers().size() < plugin.getConfig().getInt("min_corulers")) {
				inputs.add("*Claim Land " + menuType.OPTIONDESCRIP + "Must have " + plugin.getConfig().getInt("min_corulers") + " Co-Rulers");
			}
			else if(country.getPopulation() < plugin.getConfig().getInt("min_country_population")) {
				inputs.add("*Claim Land " + menuType.OPTIONDESCRIP + "Must have " + plugin.getConfig().getInt("min_country_population") + " Population");
			}
			else {
				inputs.add("Claim Land");
			}
			if(country.size() != 0) {
				inputs.add("Unclaim Land");
				inputs.add("Government Regions");
			}
			
			inputs.add("Manage Economy");
			inputs.add("Protection Level (" + menuType.OPTIONDESCRIP + country.getProtectionLevel() + menuType.OPTION + ")");
			inputs.add("Military Level (" + menuType.OPTIONDESCRIP + country.getMilitaryLevel() + menuType.OPTION + ")");
			inputs.add("Manage People");
			inputs.add("Rename <name>");
		}
		
		// Make options
		options = options.concat(menuType.HEADER + country.getName() +ChatColor.RESET+ "\n" );
		options = options.concat(menuType.LABEL + "Population: " + menuType.VALUE + country.getPopulation() + "\n");
		options = options.concat(menuType.LABEL + "Size: " + menuType.VALUE + country.size() + menuType.UNIT + " Chunks\n");
		if(country.getPluralMoney().equals("") || country.getMoneyMultiplyer().equals(Math.PI)) {
			options = tools.addLine(options, "You need to finish setting up your economy before you can do\nanything with your country", optionType.ALERT);
		} 
		else {
			if(country.getCoRulers().size() < plugin.getConfig().getInt("min_corulers")) {
				options = tools.addLine(options, "You must have " + plugin.getConfig().getInt("min_corulers") + " Co-Rulers to claim land." +
						" Go to 'Manage People'\nto add Co-Rulers.", optionType.ALERT);
			}
			else if(country.getPopulation() < plugin.getConfig().getInt("min_country_population")) {
				options = tools.addLine(options, "You must have a population of at least " + plugin.getConfig().getInt("min_country_population") + " to claim land." +
						" Go to 'Manage People' to request residents.", optionType.ALERT);
			}
		}
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
			return new HudConversationMain(plugin, player, 0);
		}
		String[] args = arg.split(" ");
		if (args[0].equalsIgnoreCase("say"))  {
			if(args.length > 1) {
				PMeth.SendChat(tools.formatSpace(tools.subArray(args, 1, args.length - 1)));
			}
			return new HudConversationMain(plugin, player, 0);
		}
		
		try {
			answer = Integer.decode(args[0])-1;
		}
		catch (Exception ex) {
			return new ManageCountry(plugin, player,1);
		}
		
		if (answer > inputs.size()-1) {
			return new ManageCountry(plugin, player, 2);
		}
		
		if (inputs.get(answer).equals("Finish Economy Setup")) {
			if(country.getPluralMoney().equals("")) {
				return new NewCountry2(plugin, player, 0);
			}
			else {
				return new NewCountry3(plugin, player, 0);
			}
		}
		
		// Country Government Regions
		else if (inputs.get(answer).equals("Government Regions")) {
			return new CountryGovernmentRegions(plugin, player, 0);
		}
		
		// Rename
		else if (inputs.get(answer).equals("Rename <name>")) {
			String name = country.getName();
			country.setName("");
			if (args.length < 2) {
				country.setName(name);
				return new ManageCountry(plugin, player, 3);
			}
			else if(!tools.countryUnique(arg.substring(2))) {
				country.setName(name);
				return new ManageCountry(plugin, player, 14);
			}
			else {
				plugin.countrydata.remove(country.getName());
				plugin.countrydata.put(arg.substring(2), country);
				country.setName(arg.substring(2));
				for(ChunkData point:plugin.chunks.keySet())	{
					if(plugin.chunks.get(point).equals(name)) {
						plugin.chunks.put(point, country.getName());
					}
				}
				
				return new ManageCountry(plugin, player, 0);
			}
		}
		
		// Claim Land
		else if (inputs.get(answer).equals("Claim Land")) {
			PM.preCountry(true);
			return new ClaimCountryLand(plugin, player, 0);
		}
		
		// Unclaim Land
		else if (inputs.get(answer).equals("Unclaim Land")) {
			PM.predecountry(true);
			return new UnclaimCountryLand(plugin, player, 0);
		}
		else if (inputs.get(answer).equals("Manage People")) {
			
		}
		else if (inputs.get(answer).equals("Manage Economy")) {
			return new ManageEconomy(plugin, player, 0);
		}
		else if (inputs.get(answer).equals("Protection Level (" + ChatColor.GRAY + country.getProtectionLevel() + ChatColor.GREEN + ")")) {
			return new CountryProtectionLevel(plugin, player, 0);
		}
		return new ManageCountry(plugin, player, 2);
	}
}
