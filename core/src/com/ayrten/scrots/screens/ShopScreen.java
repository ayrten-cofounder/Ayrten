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
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

public class ShopScreen extends ScrotsScreen {
	private static final int PAD = 20;

	private int points = 0;

	private ScrollPane scroll_view;
	// private Table table;

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
		// stage.addActor(points_label);
		// stage.addActor(premium_label);
		// stage.addActor(table);
		// stage.addActor(bottom_bar_table);
		// stage.addActor(scroll_view);
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

		// table = new Table();
		// table.setSize(Assets.width - (PAD * 2),
		// Assets.height - back.getHeight() - (PAD * 2));
		// table.setPosition(PAD * 2 / 2, PAD * 2 / 2);
		// table.setSkin(Assets.skin);
		// table.setBackground(Assets.rounded_rectangle_red);

		// scroll_view = new ScrollPane(table);
		// scroll_view.setSize(Assets.width, Assets.height - back.getHeight()
		// - bottom_bar_table.getHeight());
		// // scroll_view.setPosition(0, Assets.height - back.getHeight());
		// scroll_view.setPosition(0, 0 + bottom_bar_table.getHeight() + PAD);

		// Total Points Label
		points_label = new Label("Points: " + String.valueOf(points),
				Assets.style_font_32_white);
		// points_label.setCenterPosition(Assets.width / 2, Assets.height
		// - points_label.getHeight());

		// Premium Label
		premium_label = new Label("Buy Points!", Assets.style_font_64_orange);

		// premium_label.setPosition(Assets.width - premium_label.getWidth(),
		// Assets.height - premium_label.getHeight());

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

		// Buy Label
		buy_label = new Label("Buy", Assets.style_font_32_white);
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

		// Clear Label
		clear_label = new Label("Clear", Assets.style_font_32_white);
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

		table.row().padTop(PAD);
//		table.add(p).width(Assets.width / 6);
//		table.add(des).width(Assets.width / 6);
//		table.add(c).width(Assets.width / 6);
//		table.add(a).width(Assets.width / 6);
//		table.add(t).width(Assets.width / 6);
		
		table.add().width(Assets.width / 6);
		table.add().width(Assets.width / 6);
		table.add().width(Assets.width / 6);
		table.add().width(Assets.width / 6);
		table.add().width(Assets.width / 6);
		
		table.row();
		table.add(column).colspan(5).height(100).fill();

		Table tempt = new Table();
		tempt.setWidth(Assets.width);
		tempt.setSkin(Assets.skin);
		// tempt.setBackground(Assets.gray_box);

		// tempt.debug();
		// table.debug();

		tempt.row();
		tempt.add().width(Assets.width / 6);
		tempt.add().width(Assets.width / 6);
		tempt.add().width(Assets.width / 6);
		tempt.add().width(Assets.width / 6);
		tempt.add().width(Assets.width / 6);

		for (ShopDot d : dots) {
			tempt.row().padBottom(PAD);

			// Table row = new Table();
			// row.setWidth(tempt.getWidth());
			// row.setSkin(Assets.skin);
			// row.setBackground(Assets.rounded_rectangle_blue);

			if (d.unlocked) {
				tempt.add(d.dotImage).height(d.dotImage.getHeight());
				tempt.add(d.descriptionImage);
				tempt.add(d.priceLabel);
				tempt.add(d.amountTable);
				tempt.add(d.totalCostLabel);
			} else {
				tempt.add(d.dotImage);
				tempt.add(d.descriptionImage);
				tempt.add(d.unlockPriceLabel);
				tempt.add(d.unlockBuyLabel).colspan(2);
			}
		}

		scroll_view = new ScrollPane(tempt);
		scroll_view.setScrollingDisabled(true, false);
		// scroll_view.getStyle().background = Assets.gray_box;
		scroll_view.getStyle().background = Assets.rounded_rectangle_border;

		table.row().padBottom(PAD);
		table.add(scroll_view).colspan(5).fill();

		table.row().padBottom(PAD);
		table.add();
		table.add();
		table.add(clear_label);
		table.add(buy_label);
		table.add(total_price_label);

		// table.row().pad(20);
		// table.add();
		// table.add();
		// table.add(clear_label);
		// table.add(buy_label);
		// table.add(total_price_label);
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

		Table.drawDebug(stage);
	}

}
