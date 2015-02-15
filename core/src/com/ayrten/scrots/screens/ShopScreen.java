package com.ayrten.scrots.screens;

import com.ayrten.scrots.manager.Assets;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class ShopScreen extends ScrotsScreen {
	private static final int MAGNET_PRICE = 150;
	private static final int INVINCIBLE_PRICE = 250;
	private static final int RAINBOW_PRICE = 350;

	private int points = 0;

	private Label points_label;

	private Table table;
	
	private Image background_window;

	private Image invincible_dot;
	private Image rainbow_dot;
	private Image magnet_dot;

	private Label invincible_dot_price;
	private Label rainbow_dot_price;
	private Label magnet_dot_price;

	private Label invincible_dot_buy;
	private Label rainbow_dot_buy;
	private Label magnet_dot_buy;

	public ShopScreen(Screen bscreen) {
		super(bscreen, true);

		table = new Table();
		table.setFillParent(true);
		table.setSkin(Assets.skin);

		points = Assets.points_manager.getTotalPoints();

		setupStage();
		setUpShopTable();
		updatePoints();
	}
	
	private void setBackgroundWindow()
	{
		background_window = new Image(Assets.transparent_background);
		background_window.setBounds(0, 0, Assets.width, Assets.height);
		
		Label notEnough = new Label("Not enough points. Do you want to purchase more?",
				Assets.style_font_32_white);
		notEnough.setPosition((Assets.width / 2) - (notEnough.getWidth() / 2), Assets.height / 2);
		
		stage.addActor(background_window);
		stage.addActor(notEnough);
	}

	private void updatePoints() {
		points = Assets.points_manager.getTotalPoints();
		points_label.setText("Points: " + String.valueOf(points));
	}

	private void setUpShopTable() {
		points_label = new Label(
				"Points: " + String.valueOf(points),
				Assets.prefs.getString("bg_color").equals("Black") ? Assets.style_font_32_white
						: Assets.style_font_32_black);

		invincible_dot = new Image(Assets.invincible_dot);
		rainbow_dot = new Image(Assets.rainbow_dot);
		magnet_dot = new Image(Assets.magnet_dot);

		invincible_dot_price = new Label(
				String.valueOf(INVINCIBLE_PRICE),
				Assets.prefs.getString("bg_color").equals("Black") ? Assets.style_font_32_white
						: Assets.style_font_32_black);

		rainbow_dot_price = new Label(
				String.valueOf(RAINBOW_PRICE),
				Assets.prefs.getString("bg_color").equals("Black") ? Assets.style_font_32_white
						: Assets.style_font_32_black);

		magnet_dot_price = new Label(
				String.valueOf(MAGNET_PRICE),
				Assets.prefs.getString("bg_color").equals("Black") ? Assets.style_font_32_white
						: Assets.style_font_32_black);

		invincible_dot_buy = new Label(
				"Buy",
				Assets.prefs.getString("bg_color").equals("Black") ? Assets.style_font_32_white
						: Assets.style_font_32_black);
		magnet_dot_buy = new Label("Buy", Assets.prefs.getString("bg_color")
				.equals("Black") ? Assets.style_font_32_white
				: Assets.style_font_32_black);
		rainbow_dot_buy = new Label("Buy", Assets.prefs.getString("bg_color")
				.equals("Black") ? Assets.style_font_32_white
				: Assets.style_font_32_black);

		invincible_dot_buy.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				buyInvincibleDot(1);
			}
		});
		
		magnet_dot_buy.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				buyMagnetDot(1);
			}
		});
		
		rainbow_dot_buy.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				buyRainbowDot(1);
			}
		});

		table.add(points_label);
		table.row();
		table.row();
		table.add(invincible_dot);
		table.add(invincible_dot_price);
		table.add(invincible_dot_buy);
		table.row();
		table.row();
		table.add(rainbow_dot);
		table.add(rainbow_dot_price);
		table.add(rainbow_dot_buy);
		table.row();
		table.row();
		table.add(magnet_dot);
		table.add(magnet_dot_price);
		table.add(magnet_dot_buy);

		stage.addActor(table);
	}

	private void buyInvincibleDot(int num) {
		int cost = INVINCIBLE_PRICE * num;

		if (cost <= points) {
			Assets.points_manager.addPoints(-(cost));
			Assets.power_dot_manager
					.setInvincibleDotAmount(Assets.power_dot_manager
							.getInvincibleDots() + num);
			updatePoints();
		} else {
			notEnoughPoints();
		}
	}

	private void buyRainbowDot(int num) {
		int cost = RAINBOW_PRICE * num;

		if (cost <= points) {
			Assets.points_manager.addPoints(-(cost));
			Assets.power_dot_manager
					.setRainbowDotAmount(Assets.power_dot_manager
							.getRainbowDots() + num);
			updatePoints();
		} else {
			notEnoughPoints();
		}
	}

	private void buyMagnetDot(int num) {
		int cost = MAGNET_PRICE * num;

		if (cost <= points) {
			Assets.points_manager.addPoints(-(cost));
			Assets.power_dot_manager
					.setMagnetDotAmount(Assets.power_dot_manager
							.getMagnetDots() + num);
			updatePoints();
		} else {
			notEnoughPoints();
		}
	}

	private void notEnoughPoints() {
		setBackgroundWindow();
	}

	@Override
	public void show() {
		super.show();

		updatePoints();
	}
}
