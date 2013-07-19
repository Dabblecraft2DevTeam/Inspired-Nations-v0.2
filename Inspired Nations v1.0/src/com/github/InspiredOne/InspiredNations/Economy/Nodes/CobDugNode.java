package com.github.InspiredOne.InspiredNations.Economy.Nodes;

import java.math.BigDecimal;

import com.github.InspiredOne.InspiredNations.InspiredNations;

public class CobDugNode extends Node {

	
	double[] power;
	double[] ratios;
	
	public CobDugNode(InspiredNations instance, int id, int[] elems, double[] power) {
		super(instance, id, elems);
		this.power = power;
	}

	@Override
	public double getCoef() {
		ratios = new double[elems.length];
		double coeftemp = 1;
		
		for(int i = 0; i<elems.length; i++) {
			double holder = Math.pow(ref.get(elems[i]).getCoef(), power[i]);
			
			if(holder >= thresh) {
				coeftemp = coeftemp*holder;
				
			}
		}
		return coeftemp;
	}

	@Override
	public void buy(BigDecimal amount) {
		// TODO Auto-generated method stub

	}

}
