package com.ayrten.scrots.shop;

public interface IAP
{
	static final String ITEM_1 = "tier1"; // $1
	static final String ITEM_2 = "tier2"; // $5
	static final String ITEM_3 = "tier3"; // $10
	static final String ITEM_4 = "tier4"; // $15
	static final String ITEM_5 = "tier5"; // $20
	static final String REMOVE_ADS = "no_ads";
	
	public boolean isConnected();
	public boolean retrievedItems();
	public String getPrice(String item);
	public String getDescription(String item);
	
	public void purchase(String item, IAPInterface callback);
	public void consume(String item, IAPInterface callback);
	public void queryPurchaseItems();
}
