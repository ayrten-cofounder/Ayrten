package com.ayrten.scrots.shop;

import com.ayrten.scrots.common.Assets;
import com.ayrten.scrots.screens.ShopScreen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldListener;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class ShopItem {
	protected ShopScreen shop;
	protected Image icon;
	protected String description;
	protected short price;
	protected Label priceLabel;
	protected ShopRow row;
	protected int amountToBuy;
	protected int totalCostToBuy;
	protected Label totalCostLabel;
	protected TextField amountTextField;
	protected String item_name;

	public ShopItem(ShopScreen shop, Texture texture, String description, short price, String item_name) {
		icon = new Image(texture);
		this.shop = shop;
		this.description = description;
		this.price = price;
		this.item_name = item_name;
		createPriceLabel();
	}
	
	public void setRow(ShopRow row) { this.row = row; }
	
	protected void createPriceLabel() {
		priceLabel = new Label(Short.toString(getPrice()),
				Assets.style_font_32_white);
		priceLabel.setAlignment(Align.center);
	}
	
	// Called when item is set to ShopRow. This help reduces memory consumption
	// because ShopRow isn't created until the item is unlocked.
	public void addAmountAndTotal() {
		totalCostLabel = new Label("0", Assets.style_font_32_white);
		totalCostLabel.setAlignment(Align.center);
		
		amountTextField = new TextField("0", Assets.skin);
		amountTextField.getStyle().background = Assets.gray_box;
		amountTextField.getStyle().font = Assets.font_32;
		amountTextField.getStyle().fontColor = Color.WHITE;
		amountTextField.setAlignment(Align.center);
		amountTextField.setTextFieldFilter(new TextField.TextFieldFilter.DigitsOnlyFilter());
		amountTextField.setMaxLength(4);
		amountTextField.setTextFieldListener(new TextFieldListener() {
			@Override
			public void keyTyped(TextField textField, char c) {
				if (c == '\n' || c == '\r') {
					if (!textField.getText().equals("")) {
						amountToBuy = Integer.valueOf(textField.getText());
						textField.setText(String.valueOf(amountToBuy));
						calcTotalCost();
						totalCostLabel.setText(String.valueOf(totalCostToBuy));
					}
				}
			}
		});
		
		
		Image add = new Image(Assets.up_button);
		add.setBounds(add.getX(), add.getY(), add.getWidth(), add.getHeight());
		add.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				addAmountToBuy(1);
				amountTextField.setText(String.valueOf(amountToBuy));
				calcTotalCost();
				totalCostLabel.setText(String.valueOf(totalCostToBuy));
			}
		});
		
		Image minus = new Image(Assets.down_button);
		minus.setBounds(minus.getX(), minus.getY(), minus.getWidth(), minus.getHeight());
		minus.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				minusAmountToBuy(1);
				amountTextField.setText(String.valueOf(amountToBuy));
				calcTotalCost();
				totalCostLabel.setText(String.valueOf(totalCostToBuy));
			}
		});
		
		float arrow_width = row.column_width / 2;
		float arrow_height = row.row_height / 2;
		Table arrow_table = new Table(Assets.skin);
		arrow_table.setSize(row.column_width, row.row_height);
		arrow_table.add(add).width(arrow_width).height(arrow_height);
		arrow_table.row();
		arrow_table.add(minus).width(arrow_width).height(arrow_height);
		
		Table amountTable = new Table(Assets.skin);
		amountTable.add(amountTextField).width(row.column_width - arrow_width).height(row.row_height);
		amountTable.add(arrow_table);
		
		row.table_row.add(amountTable).width(row.column_width).height(row.row_height);
		row.table_row.add(totalCostLabel).width(row.column_width).height(row.row_height);
	}
	
	protected void addAmountToBuy(int num) {
		if (amountToBuy + num > 9999)
			amountToBuy = 9999;
		else
			amountToBuy += num;
	}

	protected void minusAmountToBuy(int num) {
		if (amountToBuy - num < 0) {
			return;
		}

		amountToBuy -= num;
	}
	
	// Instead of looking through all items and adding up
	// the total cost, doing it this way reduces the number
	// of calculations.
	protected void calcTotalCost() {
		shop.minusTotalPrice(totalCostToBuy);
		totalCostToBuy = price * amountToBuy;
		shop.updateTotalPriceLabel(totalCostToBuy);
	}
	
	public short getPrice() {
		return price;
	};

	public String getDescription() { return description; }
	public Image getIcon() { return icon; }
	public Label getPriceLabel() {
		priceLabel.setText(String.valueOf(getPrice()));
		return priceLabel; 
	}
	
	public void clear() {
		totalCostToBuy = 0;
		totalCostLabel.setText("0");
		amountTextField.setText("0");
	}
	
	// Called when items are bought. By default, this is for items
	// that have an amount (ie. PowerDot). Override default if you
	// want something else.
	public void executeEffect() {
		Assets.points_manager.subtractPoints(totalCostToBuy);
		Assets.item_manager.setItemCount(item_name, amountToBuy);
	}
}
