package com.couponsystem.couponsystem.beans;

public enum IncomeType {

CUSTOMER_PURCHASE("description..."),COMPANY_NEW_COUPON("description..."),COMPANY_UPDATE_COUPON("description...");
	
	private String descString;

	
	private IncomeType(String descString) {
		this.descString = descString;
	}


	public String getDescString() {
		return descString;
	}
	


	
}
