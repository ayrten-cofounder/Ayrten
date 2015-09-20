package com.ayrten.scrots.screens;

import java.util.ArrayList;

import com.ayrten.scrots.common.Assets;
import com.ayrten.scrots.common.ButtonInterface;
import com.ayrten.scrots.shop.ShopDot;
import com.ayrten.scrots.shop.ShopItem;
import com.ayrten.scrots.shop.ShopRow;
import com.ayrten.scrots.shop.UnlockItem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
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

	protected Image prev_selected_icon;
	protected ShopRow prev_selected_row;
	protected Label item_description;

	public ShopScreen(Screen bscreen) {
		super(bscreen, true);

		points = Assets.points_manager.getTotalPoints();

		setupStage();
		setupPremiumShop();

		// Variables
		float type_column_width = Assets.width - Assets.game_width;
		float header_column_size = Assets.game_width / 4;
		float description_panel_height = Assets.font_32.getLineHeight() * 2;
		float middle_row_height = navigation_bar.getY()
				- description_panel_height - Assets.font_32.getLineHeight();

		table.align(Align.bottomLeft);

		// There are 3 rows total. top_portion consists of first and middle
		// rows.
		Table top_portion = new Table(Assets.skin);
		top_portion.setSize(Assets.width, navigation_bar.getY()
				- description_panel_height);
		top_portion.add().width(type_column_width)
				.height(Assets.font_32.getCapHeight());

		LabelStyle header_style = new LabelStyle();
		header_style.font = Assets.font_32;
		header_style.fontColor = Color.YELLOW;

		Label p = new Label("Item", header_style);
		Label c = new Label("Cost", header_style);
		Label a = new Label("Amount", header_style);
		Label t = new Label("Total", header_style);

		p.setAlignment(Align.center);
		c.setAlignment(Align.center);
		a.setAlignment(Align.center);
		t.setAlignment(Align.center);

		Table header = new Table(Assets.skin);
		header.add(p).width(header_column_size);
		header.add(c).width(header_column_size);
		header.add(a).width(header_column_size);
		header.add(t).width(header_column_size);

		top_portion.add(header);
		top_portion.row();

		Table type_column = new Table(Assets.skin);
		type_column
				.setSize(Assets.width - Assets.game_width, middle_row_height);
		type_column.align(Align.topLeft);

		ArrayList<Texture> type_icons = new ArrayList<Texture>();
		type_icons.add(Assets.rainbow_dot);

		for (int i = 0; i < type_icons.size(); i++) {
			Image icon = new Image(type_icons.get(i));
			Image selected_bkg = new Image(Assets.rounded_rectangle_blue);
			icon.setUserObject(selected_bkg);
			icon.setBounds(icon.getX(), icon.getY(), icon.getWidth(),
					icon.getHeight());
			icon.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					super.clicked(event, x, y);
					if (prev_selected_icon != null)
						((Image) prev_selected_icon.getUserObject())
								.setVisible(false);
					prev_selected_icon = (Image) event.getTarget();
					((Image) prev_selected_icon.getUserObject())
							.setVisible(true);
				}
			});
			if (i == 0)
				prev_selected_icon = icon;
			else
				selected_bkg.setVisible(false);
			selected_bkg.setVisible(false);
			type_column.stack(selected_bkg, icon).width(type_column_width)
					.height(type_column_width).center();

			if (i != type_icons.size() - 1)
				type_column.row();
		}

		ScrollPane type_column_scroll = new ScrollPane(type_column);
		type_column_scroll.setSize(type_column_width, middle_row_height);

		// TODO: create the description table first to calculate final
		// type_column height.
		top_portion.add(type_column_scroll).width(type_column_width)
				.height(middle_row_height);

		// Outer array list = type. Inner array list = all items for that list.
		ArrayList<ArrayList<ShopItem>> item_list = new ArrayList<ArrayList<ShopItem>>();

		ArrayList<ShopItem> dot_itemlist = new ArrayList<ShopItem>();
		UnlockItem rainbow_dot = new UnlockItem(this, Assets.rainbow_dot,
				"Remove negative dots for 5 seconds", (short) 350,
				Assets.power_dot_manager.isRainbowDotUnlocked(), (short) 1050);
		UnlockItem invincible_dot = new UnlockItem(
				this,
				Assets.invincible_dot,
						"Negative dots won't affect you for 5 seconds."
						+ "Negative dots won't affect you for 4 seconds."
						+ "Negative dots won't affect you for 3 seconds."
						+ "Negative dots won't affect you for 2 seconds."
						+ "Negative dots won't affect you for 1 seconds.", (short) 250,
				// "Negative dots won't affect you for 5 seconds.", (short) 250,
				Assets.power_dot_manager.isInvincibleDotUnlocked(), (short) 850);
		UnlockItem magnet_dot = new UnlockItem(this, Assets.magnet_dot,
				"Attracts negative dots for 8 seconds.", (short) 150,
				Assets.power_dot_manager.isMagnetDotUnlocked(), (short) 500);
		UnlockItem decel_dot = new UnlockItem(this, Assets.decelerate_dot,
				"Slow down negative dots' movement.", (short) 150,
				Assets.power_dot_manager.isDecelDotUnlocked(), (short) 500);

		dot_itemlist.add(rainbow_dot);
		dot_itemlist.add(invincible_dot);
		dot_itemlist.add(magnet_dot);
		dot_itemlist.add(decel_dot);

		item_list.add(dot_itemlist);

		// Want to fit 5 items.
		float item_row_height = middle_row_height / 5;
		Table list_table = new Table(Assets.skin);
		for (int i = 0; i < dot_itemlist.size(); i++) {
			final ShopRow row = new ShopRow(header_column_size, item_row_height);
			UnlockItem item = (UnlockItem) dot_itemlist.get(i);
			item.setShopRow(row);
			row.setItem(item);
			row.addListener(new ClickListener() {
				@Override
				public void clicked(InputEvent event, float x, float y) {
					super.clicked(event, x, y);
					if (prev_selected_row != null)
						prev_selected_row.highlightRow(false);
					prev_selected_row = row;
					prev_selected_row.highlightRow(true);
					item_description.setText(prev_selected_row.getItem()
							.getDescription());
				}
			});

			list_table.add(row).width(Assets.game_width)
					.height(item_row_height);
			if (i != dot_itemlist.size() - 1)
				list_table.row();
		}
		// list_table.debug();

		Table main_table = new Table(Assets.skin);
		main_table.align(Align.topLeft);
		main_table.stack(list_table);
		ScrollPane main_table_scroll = new ScrollPane(main_table);
		main_table_scroll.setSize(Assets.game_width, navigation_bar.getY());

		top_portion.add(main_table_scroll).width(Assets.game_width)
				.height(middle_row_height);

		Table bottom_portion = new Table(Assets.skin);
		bottom_portion.setSize(Assets.width, description_panel_height);
		bottom_portion.align(Align.topLeft);

		Table description_panel = new Table(Assets.skin);
		description_panel.setSize(Assets.width / 2, navigation_bar.getY());
		description_panel.debug();

		final ScrollPane dp_scroll = new ScrollPane(description_panel);
		dp_scroll.setSize(description_panel.getWidth(),
				description_panel.getHeight());
		dp_scroll.addAction(Actions.repeat(RepeatAction.FOREVER, new Action() {
			@Override
			public boolean act(float delta) {
				dp_scroll.fling(1, 0, -25);
				return false;
			}
		}));
		
		item_description = new Label("", Assets.style_font_32_white);
		item_description.setWrap(true);
		item_description.setSize(description_panel.getWidth(),
				description_panel.getHeight());
		item_description.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				dp_scroll.fling(2, 0, -50);
			}
		});
		description_panel.add(item_description)
				.width(description_panel.getWidth())
				.top().left();


		dp_scroll.setFlickScroll(true);
		bottom_portion.add(dp_scroll).height(description_panel_height);
		
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

		Label clear_label = new Label("Clear", clear_style);
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
		Label buy_label = new Label("Buy", buy_style);
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
		
		bottom_portion.add(clear_label).width(Assets.width / 6)
				.padLeft(clear_label.getStyle().font.getSpaceWidth())
				.padRight(clear_label.getStyle().font.getSpaceWidth());
		bottom_portion.add(buy_label).width(Assets.width / 6)
				.padLeft(buy_label.getStyle().font.getSpaceWidth())
				.padRight(buy_label.getStyle().font.getSpaceWidth());
		bottom_portion.add(total_price_label).width(Assets.width / 6);
		
		table.add(top_portion);
		table.row();
		table.add(bottom_portion).top().left();

		
		// setUpShopScreen();
		// setUpShopTable();
		updatePoints();

		showTableScreen();
	}


	private void setupPremiumShop() {
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
				tempt.add(d.amountTable).height(cell_wh).width(cell_wh)
						.center();
				tempt.add(d.totalCostLabel).height(cell_wh).width(cell_wh)
						.center();
			} else {
				tempt.add(d.dotImage).height(cell_wh).width(cell_wh).center();
				tempt.add(d.descriptionImage).height(cell_wh).width(cell_wh)
						.center();
				tempt.add(d.unlockPriceLabel).height(cell_wh).width(cell_wh)
						.center();
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
	
	public void minusTotalPrice(int num) {
		total_price -= num;
	}
	
	public void updateTotalPriceLabel(int new_price) {
		total_price += new_price;
		total_price_label.setText(String.valueOf(total_price));
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
		// clear();
	}

	@Override
	protected void createBKGTable() {
	}

	@Override
	protected void createTableScreen() {
		table = new Table(Assets.skin);
		table.setSize(Assets.width, navigation_bar.getY());
	}
}
