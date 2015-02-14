package com.ayrten.scrots.screens;

import com.ayrten.scrots.manager.Assets;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class ShopScreen extends ScrotsScreen {
	private static final int MAGNET_PRICE = 150;
	private static final int INVINCIBLE_PRICE = 250;
	private static final int RAINBOW_PRICE = 350;
	
	private int points = 0;
	private int earned_points = 0;
	private int bought_points = 0;
	
	private Label points_label;
	
	private Table table;
	
	private Image invincible_dot;
	private Image rainbow_dot;
	private Image magnet_dot;
	
	private Label invincible_dot_price;
	private Label rainbow_dot_price;
	private Label magnet_dot_price;

	public ShopScreen(Screen bscreen) {
		super(bscreen, true);
		
		table = new Table();
		table.setFillParent(true);
		table.setSkin(Assets.skin);
		
		points = Assets.points_manager.getTotalPoints();
		
		setupStage();
		setUpShopTable();
	}

	private void updatePoints()
	{
		points = Assets.points_manager.getTotalPoints();
		points_label.setText("Points: " + String.valueOf(points));
	}
	
	private void setUpShopTable()
	{
		points_label = new Label("Points: " + String.valueOf(points), Assets.prefs.getString("bg_color").equals(
				"Black") ? Assets.style_font_32_white
				: Assets.style_font_32_black);
		
		invincible_dot = new Image(Assets.invincible_dot);
		rainbow_dot = new Image(Assets.rainbow_dot);
		magnet_dot = new Image(Assets.magnet_dot);
		
		
		invincible_dot_price = new Label(String.valueOf(INVINCIBLE_PRICE), Assets.prefs.getString("bg_color").equals(
				"Black") ? Assets.style_font_32_white
				: Assets.style_font_32_black);
		
		rainbow_dot_price = new Label(String.valueOf(RAINBOW_PRICE), Assets.prefs.getString("bg_color").equals(
				"Black") ? Assets.style_font_32_white
				: Assets.style_font_32_black);
		
		magnet_dot_price = new Label(String.valueOf(MAGNET_PRICE), Assets.prefs.getString("bg_color").equals(
				"Black") ? Assets.style_font_32_white
				: Assets.style_font_32_black);
		
		table.add(points_label);
		table.row();
		table.row();
		table.add(invincible_dot);
		table.add(invincible_dot_price);
		table.row();
		table.row();
		table.add(rainbow_dot);
		table.add(rainbow_dot_price);
		table.row();
		table.row();
		table.add(magnet_dot);
		table.add(magnet_dot_price);
		
		stage.addActor(table);
	}
}
