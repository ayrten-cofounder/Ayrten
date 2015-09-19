package com.ayrten.scrots.shop;

import com.ayrten.scrots.manager.Assets;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class ShopRow extends Table 
{
	// Used to set highlight color when row is selected.
	protected Image highlight;
	protected float column_width;

	public ShopRow(float colummn_width) {
		highlight = new Image(Assets.rounded_rectangle_blue);
		highlight.setVisible(false);
		this.column_width = colummn_width;
		addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
			
			}
		});
		align(Align.left);
	}
	
	public float getColWidth() { return column_width; }
	
//	public void highlightRow(boolean highlight) {
//		this.highlight.setVisible(highlight);
//	}
	
	public void setItem(ShopItem item) {
		add(item.getIcon()).width(column_width);
		add(item.getPriceLabel()).width(column_width);
		if(item instanceof UnlockItem) {
			if(((UnlockItem) item).isUnlocked()) {
				// Add the amount label
				// Add the Total cost label
			} else
				// Add the unlock label
				add(((UnlockItem) item).createUnlockLabel()).width(column_width * 2);
		} else {
			
		}
	}
}
