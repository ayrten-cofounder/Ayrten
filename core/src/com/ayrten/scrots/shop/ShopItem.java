package com.ayrten.scrots.shop;

import com.ayrten.scrots.screens.ShopScreen;

public class ShopItem {
	protected ShopScreen shop;
	protected String description;
	protected int price;
	
	public ShopItem(ShopScreen shop) {
		this.shop = shop;
	}
}
