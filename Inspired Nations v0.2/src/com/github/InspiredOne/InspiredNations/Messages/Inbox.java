package com.github.InspiredOne.InspiredNations.Messages;

import java.util.Vector;

import org.bukkit.entity.Player;

public class Inbox {

	private Player owner;
	
	private Vector<Notification> Notif = new Vector<Notification>();
	private Vector<PlayerMsg> Pmsg = new Vector<PlayerMsg>();
	private Vector<Message> read = new Vector<Message>();
	
	public Inbox(Player owner) {
		this.owner = owner;
	}

	public Player getOwner() {
		return owner;
	}

	public void setOwner(Player owner) {
		this.owner = owner;
	}

	public Vector<Notification> getNotif() {
		return Notif;
	}

	public void setNotif(Vector<Notification> notif) {
		Notif = notif;
	}

	public Vector<PlayerMsg> getPmsg() {
		return Pmsg;
	}

	public void setPmsg(Vector<PlayerMsg> pmsg) {
		Pmsg = pmsg;
	}
	
	public void addPlayerMsg(String msg, Player sender, Priority priority) {
		PlayerMsg Pmsgtemp = new PlayerMsg(msg, priority, sender);
		addPlayerMsg(Pmsgtemp);
	}
	
	public void addPlayerMsg(PlayerMsg pmsgtemp) {
		Pmsg.add(pmsgtemp);
	}
	
	public void removePlayerMsg(PlayerMsg pmsgtemp) {
		Pmsg.remove(pmsgtemp);
	}

	public Vector<Message> getRead() {
		return read;
	}

	public void setRead(Vector<Message> read) {
		this.read = read;
	}
}
