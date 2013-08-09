package com.github.InspiredOne.InspiredNations.Messages;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Message {

	private String Message;
	private Priority Priority;
	private Date timestamp;
	
	public Message(String Message, Priority Priority) {
		this.Message = Message;
		this.Priority = Priority;
		timestamp = new Date();
	}

	public String getMessage() {
		return Message;
	}

	public void setMessage(String message) {
		Message = message;
	}

	public Priority getPriority() {
		return Priority;
	}

	public void setPriority(Priority priority) {
		Priority = priority;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
	public boolean isOlderThan(Message msg) {
		return msg.getTimestamp().after(timestamp);
	}
	
	public String getDateSent() {
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy HH:mm");
		return dateFormat.format(timestamp);
	}
	
	
	
}
