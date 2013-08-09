package com.github.InspiredOne.InspiredNations.Messages;

import org.bukkit.entity.Player;

public class PlayerMsg extends Message {

	private Player Sender;
	
	public PlayerMsg(String Message, Priority Priority, Player Sender) {
		super(Message, Priority);
		this.setSender(Sender);
	}

	public Player getSender() {
		return Sender;
	}

	public void setSender(Player sender) {
		Sender = sender;
	}
	
}
