package com.ayrten.scrots.shop;

import com.ayrten.scrots.manager.Assets;
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
	protected TextField amountTextField;
	protected Table amountTable;

	public ShopItem(ShopScreen shop) {
	}

	public ShopItem(Texture texture, String description, short price) {
		icon = new Image(texture);
		this.description = description;
		this.price = price;
		createPriceLabel();
		createAmountTable();

	}
	
	protected void createPriceLabel() {
		priceLabel = new Label(Short.toString(getPrice()),
				Assets.style_font_32_white);
		priceLabel.setAlignment(Align.center);
	}
	
	protected void createAmountTable() {
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
//						amountToBuy = Integer.valueOf(textField.getText());
//						setTotalCost();
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
			}
		});
		
		Image minus = new Image(Assets.down_button);
		minus.setBounds(minus.getX(), minus.getY(), minus.getWidth(), minus.getHeight());
		minus.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
			
			}
		});
		
		Table arrow_table = new Table(Assets.skin);
		arrow_table.add(add);
		arrow_table.row();
		arrow_table.add(minus);
		
		amountTable = new Table(Assets.skin);
		amountTable.add(amountTextField);
		amountTable.add(arrow_table);
	}
	
	public short getPrice() {
		return price;
	};

	public String getDescription() { return description; }
	public Image getIcon() { return icon; }
	public Label getPriceLabel() { return priceLabel; }
}
