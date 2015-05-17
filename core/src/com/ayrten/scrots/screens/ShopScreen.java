package com.ayrten.scrots.screens;

import java.util.ArrayList;

import com.ayrten.scrots.manager.Assets;
import com.ayrten.scrots.manager.ButtonInterface;
import com.ayrten.scrots.shop.ShopDot;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

public class ShopScreen extends ScrotsScreen {
	private int points = 0;

	private ScrollPane scroll_view;

	private Label points_label;
	private Label total_price_label;
	private Label buy_label;
	private Label clear_label;
	private Label premium_label;

	private ArrayList<ShopDot> dots;
	private int total_price = 0;

	public ShopScreen(Screen bscreen) {
		super(bscreen, true);

		points = Assets.points_manager.getTotalPoints();

		setupStage();
		setUpShopScreen();
		setUpShopTable();
		updatePoints();

		showTableScreen();
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
		points_label = new Label("Points: " + String.valueOf(points),
				Assets.style_font_32_white);

		// Premium Label
		premium_label = new Label("Buy Points!", Assets.style_font_64_orange);
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

		addToNavBar(points_label);
		addToNavBar(premium_label);

		// Total Price Label
		total_price_label = new Label(String.valueOf(total_price),
				Assets.style_font_32_white);
		total_price_label.setAlignment(Align.center);

		int left = (int) ((int) Assets.width * 0.02);
		int right = left;
		int top = (int) ((int) Assets.height * 0.02);
		int bottom = top;

		// Clear Label
		NinePatchDrawable rounded_rectangle_red;
		rounded_rectangle_red = new NinePatchDrawable(new NinePatch(
				new Texture(
						Gdx.files.internal("data/rounded_rectangle_red.png")),
				left, right, top, bottom));

		LabelStyle clear_style = new LabelStyle();
		clear_style.font = Assets.font_32;
		clear_style.fontColor = Color.WHITE;
		clear_style.background = rounded_rectangle_red;

		clear_label = new Label("Clear", clear_style);
		clear_label.setAlignment(Align.center);
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

		NinePatchDrawable rounded_rectangle_green = new NinePatchDrawable(
				new NinePatch(new Texture(Gdx.files
						.internal("data/rounded_rectangle_green.png")), left,
						right, top, bottom));
		;

		LabelStyle buy_style = new LabelStyle();
		buy_style.font = Assets.font_32;
		buy_style.fontColor = Color.WHITE;
		buy_style.background = rounded_rectangle_green;

		// Buy Label
		buy_label = new Label("Buy", buy_style);
		buy_label.setAlignment(Align.center);
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

		Label p = new Label("Item", Assets.style_font_32_white);
		Label des = new Label("Description", Assets.style_font_32_white);
		Label c = new Label("Cost", Assets.style_font_32_white);
		Label a = new Label("Amount", Assets.style_font_32_white);
		Label t = new Label("Total", Assets.style_font_32_white);

		p.setAlignment(Align.center);
		des.setAlignment(Align.center);
		c.setAlignment(Align.center);
		a.setAlignment(Align.center);
		t.setAlignment(Align.center);

		Table column = new Table();
		column.setWidth(Assets.width);
		column.setSkin(Assets.skin);
		column.setBackground(Assets.rounded_rectangle_border);
		column.add(p).width(Assets.width / 6);
		column.add(des).width(Assets.width / 6);
		column.add(c).width(Assets.width / 6);
		column.add(a).width(Assets.width / 6);
		column.add(t).width(Assets.width / 6);

		table.add().width(Assets.width / 6);
		table.add().width(Assets.width / 6);
		table.add().width(Assets.width / 6);
		table.add().width(Assets.width / 6);
		table.add().width(Assets.width / 6);

		table.row();
		table.add(column).colspan(5).height(100).fill().top();

		Table tempt = new Table();
		tempt.setWidth(Assets.width);
		tempt.setSkin(Assets.skin);

		tempt.row();
		tempt.add().width(Assets.width / 6);
		tempt.add().width(Assets.width / 6);
		tempt.add().width(Assets.width / 6);
		tempt.add().width(Assets.width / 6);
		tempt.add().width(Assets.width / 6);

		float cell_wh = (float) (Assets.height * 0.14);

		for (ShopDot d : dots) {
			tempt.row().padBottom(Assets.PAD);

			if (d.unlocked) {
				tempt.add(d.dotImage).height(cell_wh).width(cell_wh);
				tempt.add(d.descriptionImage).height(cell_wh).width(cell_wh);
				tempt.add(d.priceLabel).height(cell_wh).width(cell_wh).center();
				tempt.add(d.amountTable).height(cell_wh).width(cell_wh).center();
				tempt.add(d.totalCostLabel).height(cell_wh).width(cell_wh).center();
			} else {
				tempt.add(d.dotImage).height(cell_wh).width(cell_wh).center();
				tempt.add(d.descriptionImage).height(cell_wh).width(cell_wh).center();
				tempt.add(d.unlockPriceLabel).height(cell_wh).width(cell_wh).center();
				tempt.add(d.unlockBuyLabel).colspan(2).center();
			}
		}

		tempt.left();
		scroll_view = new ScrollPane(tempt);
		scroll_view.setScrollingDisabled(true, false);
		scroll_view.getStyle().background = Assets.rounded_rectangle_border;

		table.row().padBottom(Assets.PAD);
		table.add(scroll_view).colspan(5).fill().expandY();

		table.row();
		table.add();
		table.add();
		table.add(clear_label).fill()
				.padLeft(clear_label.getStyle().font.getSpaceWidth())
				.padRight(clear_label.getStyle().font.getSpaceWidth());
		table.add(buy_label).fill()
				.padLeft(buy_label.getStyle().font.getSpaceWidth())
				.padRight(buy_label.getStyle().font.getSpaceWidth());
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
}
