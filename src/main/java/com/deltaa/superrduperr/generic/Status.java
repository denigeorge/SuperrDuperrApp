package com.deltaa.superrduperr.generic;

/**
 * @author denigeorge
 * status for the ToDo item.
 */
public enum Status {
	ACTIVE(1),
	COMPLETED(2),
	DELETED(0);
	
	private int val;
	
	Status(int val){
		this.val = val;
	}

	public int getVal() {
		return val;
	}

}
