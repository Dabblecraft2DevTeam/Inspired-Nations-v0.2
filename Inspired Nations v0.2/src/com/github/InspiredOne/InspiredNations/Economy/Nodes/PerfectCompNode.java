package com.github.InspiredOne.InspiredNations.Economy.Nodes;

import java.math.BigDecimal;

import com.github.InspiredOne.InspiredNations.InspiredNations;
import com.github.InspiredOne.InspiredNations.Economy.NPC;

public class PerfectCompNode extends Node {

	double[] ratio;
	
	public PerfectCompNode(NPC instance, double[] ratio, Node[] elems) {
		super(instance, elems);
		this.ratio = ratio;
	}

	@Override
	public double getCoef() {
		double coeftemp = 0;
		for(int i = 0; i < elems.length;i++) {
			double holder = elems[i].getCoef()*ratio[i];
			if(holder >= thresh) {
				coeftemp += holder;
			}
			else {
				return 0;
			}
		}
		
		return coeftemp;
	}

	@Override
	public void buy(BigDecimal amount) {
		
		double divisor = 0;
		for(int i = 0; i < elems.length; i++){
			double holder = elems[i].getCoef()*ratio[i];
			divisor += holder;
		}
		if(divisor <= thresh) {
			return;
		}
		
		for(int i = 0; i< elems.length; i++) {
			elems[i].buy( amount.multiply(new BigDecimal(ratio[i])).divide(new BigDecimal(divisor)));
		}

	}

}
