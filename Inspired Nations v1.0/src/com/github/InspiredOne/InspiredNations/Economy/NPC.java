package com.github.InspiredOne.InspiredNations.Economy;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Arrays;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.Economy.RefTrees.NodeRef;

public class NPC {

	public BigDecimal[] buyVector;
	public BigDecimal[] costVector;
	public InspiredNations plugin;
	public NodeRef node;
	public ItemIndexes index;
	
	public BigDecimal money = new BigDecimal(100);
	public BigDecimal moneyMultiplyer = new BigDecimal(1);
	
	private MathContext mcup = new MathContext(100, RoundingMode.UP);
	@SuppressWarnings("unused")
	private MathContext mcdown = new MathContext(100, RoundingMode.DOWN);
	
	public NPC(InspiredNations instance) {
		plugin = instance;
		index = plugin.index;
		buyVector = new BigDecimal[index.index.size()];
		costVector = new BigDecimal[index.index.size()];
		Arrays.fill(costVector, new BigDecimal(-1));
		Arrays.fill(buyVector, BigDecimal.ZERO);
		node = new NodeRef(this);
	}
	
	public void setRawMoney(BigDecimal money) {
		this.money = money;
	}
	
	public void setMoneyMultiplyer(BigDecimal multiplyer) {
		this.moneyMultiplyer = multiplyer;
	}
	
	public BigDecimal getRawMoney() {
		return this.money;
	}
	
	public BigDecimal getMoney() {
		return this.money.multiply(this.moneyMultiplyer);
	}
	
	public void setMoney(BigDecimal money) {
		this.money = this.money.divide(moneyMultiplyer, mcup);
	}
	
	public BigDecimal getMoneyMultiplyer() {
		return this.moneyMultiplyer;
	}
	
	
	public void changeMoneyMultiplyer(BigDecimal multiplyer) {
		BigDecimal money = this.getMoney();
		this.moneyMultiplyer = multiplyer;
		this.setMoney(money);
	}
}
