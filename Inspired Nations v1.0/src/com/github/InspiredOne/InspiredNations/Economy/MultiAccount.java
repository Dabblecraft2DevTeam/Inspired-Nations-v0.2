package com.github.InspiredOne.InspiredNations.Economy;

import java.math.BigDecimal;

import com.github.InspiredOne.InspiredNations.InspiredNations;

public class MultiAccount extends Account {

	private Account Bank;
	public MultiAccount(InspiredNations instance) {
		super(instance);
		Bank = new Account(plugin);
	}
	
	public void withdrawRaw(BigDecimal money) {
		/**Transfers money from bank to account*/
		Bank.transferRawMoney(money, this);
	}
	
	public void depositRaw(BigDecimal money) {
		/**Transfers money from account to bank*/
		this.transferRawMoney(money, Bank);
	}
	
	public void withdrawMoney(BigDecimal money) {
		Bank.transferMoney(money, this);
	}
	
	public void depositMoney(BigDecimal money) {
		this.transferMoney(money, this);
	}
	
	public BigDecimal getTotalRawMoney() {
		/**Returns the total amount of money in both accounts*/
		return Bank.getRawMoney().add(this.getRawMoney());
	}
	
	@Override
	public BigDecimal getMoney() {
		return tools.cut(Bank.getMoney().add(this.getMoney()));
	}
	
	@Override
	public void addMoney(BigDecimal money) {
		this.addMoney(money);
	}

	
	public void setBankMoney(BigDecimal money) {
		Bank.setMoney(money);
	}
	
	public void setOnHandMoney(BigDecimal money) {
		this.setMoney(money);
	}
	
	
	@Override
	public void setMoneyMultiplyer(BigDecimal multiplyer) {
		this.setMoneyMultiplyer(multiplyer);
		Bank.setMoneyMultiplyer(multiplyer);
	}
	
	public void setBankAccount(Account bank) {
		Bank = bank;
	}
	
	public Account getBankAccount() {
		return Bank;
	}
	
}
