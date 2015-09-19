package com.ayrten.scrots.shop;

import com.ayrten.scrots.manager.Assets;
import com.ayrten.scrots.screens.ShopScreen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class UnlockItem extends ShopItem 
{
	protected boolean unlocked;
	protected short unlockPrice;
	protected ShopRow row;

	public UnlockItem(ShopScreen shop) {
		super(shop);
	}
	
	public UnlockItem(Texture texture, String description, short price, boolean unlocked, short unlockPrice) {
		super(texture, description, price);
		this.unlocked = unlocked;
		this.unlockPrice = unlockPrice;
		priceLabel = new Label(Short.toString(getPrice()),
				Assets.style_font_32_white);
		priceLabel.setAlignment(Align.center);
	}
	
	public Label createUnlockLabel() {
		Label label = new Label("Unlock", Assets.style_font_32_white);
		label.setBounds(label.getX(), label.getY(), label.getWidth(), label.getHeight());
		label.setAlignment(Align.center);
		label.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				event.getTarget().remove();
				row.clear();
				row.add(icon).width(row.getColWidth()).left();
				row.add(priceLabel).width(row.getColWidth());
				row.add(amountTable).width(row.getColWidth());
			}
		});
		
		return label;
	}
	
	public boolean isUnlocked() { return unlocked; }
	public void setShopRow(ShopRow row) { this.row = row; }
	
	@Override
	public short getPrice() {
		if(unlocked)
			return super.getPrice();
		return unlockPrice;
	}
	
	@Override
	protected void createPriceLabel() {}
}
