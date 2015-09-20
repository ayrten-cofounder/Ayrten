package com.ayrten.scrots.shop;


import com.ayrten.scrots.common.Assets;
import com.ayrten.scrots.screens.ShopScreen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class UnlockItem extends ShopItem 
{
	protected Class<?> clazz;
	protected short unlockPrice;
	protected ShopRow row;

	public UnlockItem(ShopScreen shop) {
		super(shop);
	}
	
	public UnlockItem(ShopScreen shop, Texture texture, String description, short price, Class<?> clazz, short unlockPrice) {
		super(shop, texture, description, price);
		this.clazz = clazz;
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
	
	protected void unlockItem(InputEvent event) {
		int cost = getPrice();
		if(Assets.points_manager.getTotalPoints() < cost)
			shop.notEnoughPoints();
		else {
			Assets.points_manager.subtractPoints(cost);
			Assets.power_dot_manager.unlockDot(clazz);
			event.getTarget().remove();
			row.clear();
			row.setupRow();
			shop.updatePoints();
		}
	}
	
	public void setShopRow(ShopRow row) { this.row = row; }
	public boolean isUnlocked() { return (Assets.power_dot_manager.isDotUnlocked(clazz)); }
	
	@Override
	public short getPrice() {
		if(Assets.power_dot_manager.isDotUnlocked(clazz))
			return super.getPrice();
		return unlockPrice;
	}
	
	@Override
	protected void createPriceLabel() {}
}
