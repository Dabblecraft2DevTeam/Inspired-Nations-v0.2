package com.github.InspiredOne.InspiredNations.Economy.Nodes;

import java.math.BigDecimal;
import java.util.Vector;

import com.github.InspiredOne.InspiredNations.InspiredNations;

public class PerfectSubNode extends Node {

	double[] coefs;
	int choice = -1;
	
	public PerfectSubNode(InspiredNations instance, int id, int[] elems, double[] coefs) {
		super(instance, id, elems);
		this.coefs = coefs;
	}

	@Override
	public double getCoef() {
		
		double coefTemp = 0;
		Vector<Integer> ids = new Vector<Integer>();
		
		for(int i = 0; i < elems.length; i++) {
			double holder = ref.get(elems[i]).getCoef()*coefs[i];
			if(holder > coefTemp) {
				ids.clear();
				coefTemp = holder;
			}
			else if(holder == coefTemp) {
				ids.add(elems[i]);
			}
		}
		
		if(ids.size() > 1) {
			int rand = ids.size();
			while(rand == ids.size()) {
				rand = (int) Math.floor(Math.random()*ids.size());
			}
			choice = ids.get(rand);
			return ref.get(ids.get(rand)).getCoef();
		}

		else{
			choice = ids.get(0);
			return ref.get(ids.get(0)).getCoef();
		}
	}

	@Override
	public void buy(BigDecimal amount) {
		if(choice == -1) {
			this.getCoef();
		}
		ref.get(choice).buy(amount);
	}
}
