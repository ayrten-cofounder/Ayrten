package com.ayrten.scrots.shop;

import java.util.ArrayList;

import com.ayrten.scrots.manager.Assets;
import com.ayrten.scrots.screens.ShopScreen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;

public class UnlockItem extends ShopItem 
{
	protected boolean unlocked;
	protected short unlockPrice;
	protected ShopRow row;

	public UnlockItem(ShopScreen shop) {
		super(shop);
	}
	
	public UnlockItem(ShopScreen shop, Texture texture, String description, short price, boolean unlocked, short unlockPrice) {
		super(shop, texture, description, price);
		this.unlocked = unlocked;
		this.unlockPrice = unlockPrice;
		priceLabel = new Label(Short.toString(getPrice()),
				Assets.style_font_32_white);
		priceLabel.setAlignment(Align.center);
	}
	
	// Called inside ShopRow. UnlockLabel is not a global variable to reduce memory consumption.
	// When item is unlocked, label is replaced by amountTable and label is be consumed by 
	// garbage collection.
	public Label createUnlockLabel() {
		Label label = new Label("Unlock", Assets.style_font_32_white);
		label.setBounds(label.getX(), label.getY(), label.getWidth(), label.getHeight());
		label.setAlignment(Align.center);
		label.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				unlockItem(event);
			}
		});
		
		return label;
	}
	
	public void setShopRow(ShopRow row) { this.row = row; }
	protected void unlockItem(InputEvent event) {
//		int cost = getPrice();
//		if(Assets.points_manager.getTotalPoints() < cost)
//			shop.notEnoughPoints();
//		else {
			unlocked = true;
			event.getTarget().remove();
			row.clear();
			row.setupRow();
//		}
	}
	public boolean isUnlocked() { return unlocked; }
	
	@Override
	public short getPrice() {
		if(unlocked)
			return super.getPrice();
		return unlockPrice;
	}
	
	@Override
	protected void createPriceLabel() {}
}
