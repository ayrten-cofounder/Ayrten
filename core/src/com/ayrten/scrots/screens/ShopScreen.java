package com.ayrten.scrots.screens;

import java.util.ArrayList;

import com.ayrten.scrots.manager.Assets;
import com.ayrten.scrots.manager.ButtonInterface;
import com.ayrten.scrots.shop.ShopDot;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

public class ShopScreen extends ScrotsScreen {
	private static final int SPACE = 50;

	private int points = 0;

	private Table table;
	private Table bottom_bar_table;

	private Label points_label;
	private Label total_price_label;
	private Label buy_label;
	private Label clear_label;
	private Label premium_label;

	private ArrayList<ShopDot> dots;
	private int total_price = 0;

	public ShopScreen(Screen bscreen) {
		super(bscreen, true);

		table = new Table();
		table.setFillParent(true);
		table.setSkin(Assets.skin);

		points = Assets.points_manager.getTotalPoints();

		setupStage();
		setUpShopTable();
		setUpShopScreen();
		updatePoints();

		stage.addActor(points_label);
		stage.addActor(premium_label);
		// stage.addActor(bottom_bar_table);
		stage.addActor(table);
	}

	private void notEnoughtPointsWindow() {
		MessageScreen message = new MessageScreen(stage);

		message.makeWindow(
				"You do not have enough points. Do you want to purchase more?",
				"Yes", "No", new ButtonInterface() {

					@Override
					public void buttonPressed() {
						goToPremiumShop();
					}
				}, new ButtonInterface() {

					@Override
					public void buttonPressed() {
					}
				});
	}

	private void confirmBuyWindow() {
		MessageScreen message = new MessageScreen(stage);

		message.makeWindow("Are you sure?", "Yes", "No", new ButtonInterface() {

			@Override
			public void buttonPressed() {
				buy();
			}
		}, new ButtonInterface() {

			@Override
			public void buttonPressed() {
			}
		});
	}

	private void areYouAMinorWindow() {
		MessageScreen message = new MessageScreen(stage);

		message.makeWindow("Are you a minor?", "Yes", "No",
				new ButtonInterface() {

					@Override
					public void buttonPressed() {
						havePermissionWindow();
					}
				}, new ButtonInterface() {

					@Override
					public void buttonPressed() {
						Assets.game.setScreen(getPremiumShopScreen());
					}
				});
	}

	private void havePermissionWindow() {
		MessageScreen message = new MessageScreen(stage);

		message.makeWindow(
				"Do you have permission from your parent/guardian to purchase points?",
				"Yes", "No", new ButtonInterface() {

					@Override
					public void buttonPressed() {
						Assets.game.setScreen(getPremiumShopScreen());
					}
				}, new ButtonInterface() {

					@Override
					public void buttonPressed() {
					}
				});
	}

	public int getPoints() {
		return points;
	}

	public void updatePoints() {
		points = Assets.points_manager.getTotalPoints();
		points_label.setText("Points: " + String.valueOf(points));
	}

	private void setUpShopScreen() {
		// Total Points Label
		points_label = new Label(
				"Points: " + String.valueOf(points),
				Assets.prefs.getString("bg_color").equals("Black") ? Assets.style_font_32_white
						: Assets.style_font_32_black);
		points_label.setCenterPosition(Assets.width / 2, Assets.height
				- points_label.getHeight());

		// Premium Label
		premium_label = new Label("Buy Points!", Assets.prefs.getString(
				"bg_color").equals("Black") ? Assets.style_font_64_white
				: Assets.style_font_64_black);

		premium_label.setPosition(Assets.width - premium_label.getWidth(),
				Assets.height - premium_label.getHeight());

		premium_label.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				goToPremiumShop();
			}
		});

		// Total Price Label
		total_price_label = new Label(
				String.valueOf(total_price),
				Assets.prefs.getString("bg_color").equals("Black") ? Assets.style_font_32_white
						: Assets.style_font_32_black);

		// Buy Label
		buy_label = new Label("Buy", Assets.prefs.getString("bg_color").equals(
				"Black") ? Assets.style_font_32_white
				: Assets.style_font_32_black);

		buy_label.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				confirmBuyWindow();
			}
		});

		// Clear Label
		clear_label = new Label("Clear", Assets.prefs.getString("bg_color")
				.equals("Black") ? Assets.style_font_32_white
				: Assets.style_font_32_black);

		clear_label.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				clear();
			}
		});

		// Bottom Bar Table
		bottom_bar_table = new Table();
		bottom_bar_table.setSkin(Assets.skin);

		bottom_bar_table.setWidth(Assets.width);
		bottom_bar_table.setHeight(total_price_label.getHeight());

		bottom_bar_table.setPosition(0, 0 + bottom_bar_table.getHeight());

		bottom_bar_table.add(clear_label).width(clear_label.getWidth() + SPACE);
		bottom_bar_table.add(buy_label).width(buy_label.getWidth() + SPACE);
		bottom_bar_table.add(total_price_label).width(
				total_price_label.getWidth());
	}

	private void setUpShopTable() {
		table.clear();

		if (dots == null) {
			final ShopDot invincibleDot = new ShopDot(
					ShopDot.DOT_TYPE.INVINCIBLE, this);
			final ShopDot rainbowDot = new ShopDot(ShopDot.DOT_TYPE.RAINBOW,
					this);
			final ShopDot magnetDot = new ShopDot(ShopDot.DOT_TYPE.MAGNET, this);

			dots = new ArrayList<ShopDot>();
			dots.add(magnetDot);
			dots.add(invincibleDot);
			dots.add(rainbowDot);
		}

		Label p = new Label("Power Dot", Assets.prefs.getString("bg_color")
				.equals("Black") ? Assets.style_font_32_white
				: Assets.style_font_32_black);
		Label des = new Label("Description", Assets.prefs.getString("bg_color")
				.equals("Black") ? Assets.style_font_32_white
				: Assets.style_font_32_black);
		Label c = new Label("Cost", Assets.prefs.getString("bg_color").equals(
				"Black") ? Assets.style_font_32_white
				: Assets.style_font_32_black);
		Label a = new Label("Amount", Assets.prefs.getString("bg_color")
				.equals("Black") ? Assets.style_font_32_white
				: Assets.style_font_32_black);
		Label t = new Label("Total", Assets.prefs.getString("bg_color").equals(
				"Black") ? Assets.style_font_32_white
				: Assets.style_font_32_black);

		p.setAlignment(Align.center);
		des.setAlignment(Align.center);
		c.setAlignment(Align.center);
		a.setAlignment(Align.center);
		t.setAlignment(Align.center);

		table.add(p).width(Assets.width / 6);
		table.add(des).width(Assets.width / 6);
		table.add(c).width(Assets.width / 6);
		table.add(a).width(Assets.width / 6);
		table.add(t).width(Assets.width / 6);

		for (ShopDot d : dots) {
			table.row().pad(20);

			if (d.unlocked) {
				table.add(d.dotImage);
				table.add(d.descriptionImage);
				table.add(d.priceLabel);
				table.add(d.amountTable);
				table.add(d.totalCostLabel);
			} else {
				table.add(d.dotImage);
				table.add(d.descriptionImage);
				table.add(d.unlockPriceLabel);
				table.add(d.unlockBuyLabel).colspan(2);
			}
		}

		table.row().pad(20);
		table.add();
		table.add();
		table.add(clear_label);
		table.add(buy_label);
		table.add(total_price_label);
	}

	public void updateShopTable() {
		setUpShopTable();
	}

	public void updateTotalPrice() {
		total_price = 0;

		for (ShopDot d : dots) {
			total_price += d.getTotalCost();
		}

		updateTotalPriceLabel();
	}

	private void updateTotalPriceLabel() {
		total_price_label.setText(String.valueOf(total_price));
	}

	public void notEnoughPoints() {
		notEnoughtPointsWindow();
	}

	private void goToPremiumShop() {
		areYouAMinorWindow();
	}

	private void buy() {
		if (total_price > points) {
			notEnoughPoints();
			return;
		}

		for (ShopDot d : dots) {
			d.buyDots();
		}
		clear();
	}

	private void clear() {
		for (ShopDot d : dots) {
			d.clear();
		}

		setUpShopTable();
		updateTotalPrice();
		updateTotalPriceLabel();
	}

	public Stage getStage() {
		return stage;
	}

	private PremiumShopScreen getPremiumShopScreen() {
		return new PremiumShopScreen(this);
	}

	@Override
	public void show() {
		super.show();
		updatePoints();
		clear();
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		super.render(delta);
	}

}
