package com.github.InspiredOne.InspiredNations.Economy.Nodes;

import java.math.BigDecimal;

import com.github.InspiredOne.InspiredNations.InspiredNations;

public class PerfectCompNode extends Node {

	double[] ratio;
	
	public PerfectCompNode(InspiredNations instance, int id, int[] elems, double[] ratio) {
		super(instance, id, elems);
		this.ratio = ratio;
	}

	@Override
	public double getCoef() {
		double coeftemp = 0;
		for(int i = 0; i < elems.length;i++) {
			double holder = ref.get(elems[i]).getCoef()*ratio[i];
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
			double holder = ref.get(elems[i]).getCoef()*ratio[i];
			divisor += holder;
		}
		if(divisor <= thresh) {
			return;
		}
		
		for(int i = 0; i< elems.length; i++) {
			ref.get(elems[i]).buy( amount.multiply(new BigDecimal(ratio[i])).divide(new BigDecimal(divisor)));
		}

	}

}