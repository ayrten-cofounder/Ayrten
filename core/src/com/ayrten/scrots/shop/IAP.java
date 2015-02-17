package com.ayrten.scrots.shop;

public interface IAP
{
	static final String ITEM_1 = "points";
	
	public boolean isConnected();
	public String getPrice(String item);
	
	public void purchase(String item, IAPInterface callback);
	public void consume(String item, IAPInterface callback);
	public void queryPurchaseItems();
}
