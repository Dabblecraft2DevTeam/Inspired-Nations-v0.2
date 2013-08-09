package com.github.InspiredOne.InspiredNations.Messages;

public enum Priority {
	HIGH(3),
	MEDIUM(2),
	LOW(1);
	
	private int Level;
	
	private Priority(int level) {
		setLevel(level);
	}

	public int getLevel() {
		return Level;
	}

	public void setLevel(int level) {
		Level = level;
	}
}
