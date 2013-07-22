package com.github.InspiredOne.InspiredNations.Economy.Nodes;

import java.math.BigDecimal;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.Economy.NPC;

public class CobDugNode extends Node {

	
	double[] power;
	double[] ratios;
	
	public CobDugNode(NPC instance, double[] power, Node[] elems) {
		super(instance, elems);
		this.power = power;
	}

	@Override
	public double getCoef() {
		ratios = new double[elems.length];
		double coeftemp = 1;
		
		for(int i = 0; i<elems.length; i++) {
			double holder = Math.pow(elems[i].getCoef(), power[i]);
			
			if(holder >= thresh) {
				ratios[i] = 1;
				coeftemp = coeftemp*holder;
			}
			else {
				ratios[i] = -1;
			}
		}
		return coeftemp;
	}

	@Override
	public void buy(BigDecimal amount) {
		double divisor = 0;
		
		if(ratios.equals(null)) {
			this.getCoef();
		}
		
		for(int i = 0; i < power.length; i++) {
			if(ratios[i] > 0) {
				divisor += power[i];
			}
		}	
		for(int i = 0; i < elems.length; i++) {
			if(ratios[i] > 0) {
				elems[i].buy(amount.multiply(new BigDecimal(power[i])).divide(new BigDecimal(divisor)));
			}
		}
	}
}
