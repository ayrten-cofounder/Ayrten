package com.ayrten.scrots.shop;

import com.ayrten.scrots.common.Assets;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class ShopRow extends Stack {
	// Used to set highlight color when row is selected.
	protected Image highlight;
	protected float column_width;
	protected float row_height;
	protected ShopItem item;
	protected Table table_row;

	public ShopRow(float colummn_width, float row_height) {
		table_row = new Table(Assets.skin);
		table_row.align(Align.left);
		highlight = new Image(Assets.rounded_rectangle_blue);
		highlight.setVisible(false);
		this.column_width = colummn_width;
		this.row_height = row_height;
		addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);

			}
		});
	}
	
	public ShopItem getItem() { return item; }
	
	public void highlightRow(boolean opt) {
		highlight.setVisible(opt);
	}

	public float getColWidth() {
		return column_width;
	}

	public void setItem(ShopItem item) {
		// Set pointers.
		this.item = item;
		item.setShopRow(this);
		setupRow();
	}
	
	// Need to override because the class function also clear of EventListeners.
	@Override
	public void clear() {
		table_row.clear();
	}
	
	// Called when you first initialize a ShopRow. Also called when you
	// unlock the UnlockItem.
	public void setupRow() {
		add(highlight);
		addIcon();
		table_row.add(item.getPriceLabel()).width(column_width).height(row_height);
		if (item instanceof UnlockItem && !((UnlockItem) item).isUnlocked())
			table_row.add(((UnlockItem) item).createUnlockLabel()).width(column_width * 2);
		else
			item.addAmountAndTotal();
		add(table_row);
	}

	public void addIcon() {
		float padding = 0;
		if (column_width > row_height)
			padding = (column_width - row_height) / 2;
		table_row.add(item.getIcon()).width(column_width - padding * 2)
				.height(row_height).padLeft(padding).padRight(padding).center();
//		Table inner_table = new Table(Assets.skin);
//		inner_table.add(item.getIcon()).width(row_height).height(row_height);
//		table_row.add(inner_table).width(column_width).height(row_height);
	}
}
