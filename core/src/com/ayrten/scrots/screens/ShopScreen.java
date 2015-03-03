package com.ayrten.scrots.screens;

import java.util.ArrayList;

import com.ayrten.scrots.manager.Assets;
import com.ayrten.scrots.manager.ButtonInterface;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class ShopScreen extends ScrotsScreen {
	private int points = 0;
	private Table table;
	private Label points_label;

	private enum DOT_TYPE {
		MAGNET(150), INVINCIBLE(250), RAINBOW(350);

		private int price = 0;

		private DOT_TYPE(int p) {
			price = p;
		}

		protected int price() {
			return price;
		}
	}

	private enum DOT_UNLOCK {
		MAGNET(500), INVINCIBLE(850), RAINBOW(1050);

		private int price = 0;

		private DOT_UNLOCK(int p) {
			price = p;
		}

		protected int price() {
			return price;
		}
	}

	private class Dot {
		private Image dotImage;
		private boolean unlocked;

		private Label priceLabel;
		private Label buyLabel;
		private DOT_TYPE dotType;

		private Label unlockPriceLabel;
		private Label unlockBuyLabel;
		private DOT_UNLOCK dotUnlock;

		private InputListener buyDotListener;
		private InputListener buyUnlockDotListener;

		/*
		 * 
		 */
		public void setBuyLabelListener(InputListener listener) {
			buyDotListener = listener;
			buyLabel.addListener(buyDotListener);
		}

		public void setUnlockLabelListener(InputListener listener) {
			buyUnlockDotListener = listener;
			unlockBuyLabel.addListener(buyUnlockDotListener);
		}

		protected Dot(DOT_TYPE dotType) {
			this.dotType = dotType;

			buyDotListener = new InputListener() {
			};
			buyUnlockDotListener = new InputListener() {
			};

			switch (dotType) {
			case INVINCIBLE:
				dotImage = new Image(Assets.invincible_dot);
				dotUnlock = DOT_UNLOCK.INVINCIBLE;
				unlocked = Assets.power_dot_manager.isInvincibleDotUnlocked();
				break;

			case MAGNET:
				dotImage = new Image(Assets.magnet_dot);
				dotUnlock = DOT_UNLOCK.MAGNET;
				unlocked = Assets.power_dot_manager.isMagnetDotUnlocked();
				break;

			case RAINBOW:
				dotImage = new Image(Assets.rainbow_dot);
				dotUnlock = DOT_UNLOCK.RAINBOW;
				unlocked = Assets.power_dot_manager.isRainbowDotUnlocked();
				break;
			}

			if (unlocked) {
				setUnlockedLabels();
			} else {
				setLockedLabels();
			}

		}

		protected void buyDot(int quantity) {
			int cost = dotType.price() * quantity;

			if (points < cost) {
				notEnoughPoints();

			} else {

				Assets.points_manager.addPoints(-(cost));

				switch (dotType) {
				case INVINCIBLE:
					Assets.power_dot_manager
							.setInvincibleDotAmount(Assets.power_dot_manager
									.getInvincibleDots() + quantity);
					break;
				case MAGNET:
					Assets.power_dot_manager
							.setMagnetDotAmount(Assets.power_dot_manager
									.getMagnetDots() + quantity);
					break;
				case RAINBOW:
					Assets.power_dot_manager
							.setRainbowDotAmount(Assets.power_dot_manager
									.getRainbowDots() + quantity);
					break;
				}

				updatePoints();

			}
		}

		protected void unlockDot() {
			int cost = dotUnlock.price();

			if (points < cost) {
				notEnoughPoints();

			} else {

				Assets.points_manager.addPoints(-(cost));

				switch (dotUnlock) {
				case INVINCIBLE:
					Assets.power_dot_manager.unlockInvincibleDot();
					break;
				case MAGNET:
					Assets.power_dot_manager.unlockMagnetDot();
					break;
				case RAINBOW:
					Assets.power_dot_manager.unlockRainbowDot();
					break;
				}

				updatePoints();
				setUnlockedLabels();

			}
		}

		protected void setLockedLabels() {
			priceLabel = new Label(String.valueOf(dotType.price()),
					Assets.style_font_32_light_gray);
			buyLabel = new Label("Buy", Assets.style_font_32_light_gray);

			buyLabel.setTouchable(Touchable.disabled);

			unlockPriceLabel = new Label(
					String.valueOf(dotUnlock.price()),
					Assets.prefs.getString("bg_color").equals("Black") ? Assets.style_font_32_white
							: Assets.style_font_32_black);
			unlockBuyLabel = new Label("Unlock", Assets.prefs.getString(
					"bg_color").equals("Black") ? Assets.style_font_32_white
					: Assets.style_font_32_black);

			unlockBuyLabel.addListener(buyUnlockDotListener);
		}

		protected void setUnlockedLabels() {
			priceLabel = new Label(
					String.valueOf(dotType.price()),
					Assets.prefs.getString("bg_color").equals("Black") ? Assets.style_font_32_white
							: Assets.style_font_32_black);
			buyLabel = new Label("Buy", Assets.prefs.getString("bg_color")
					.equals("Black") ? Assets.style_font_32_white
					: Assets.style_font_32_black);

			buyLabel.removeListener(buyDotListener);
			buyLabel.addListener(buyDotListener);

			unlockPriceLabel = new Label(String.valueOf(dotUnlock.price()),
					Assets.style_font_32_light_gray);
			unlockBuyLabel = new Label("Unlock",
					Assets.style_font_32_light_gray);

			unlockBuyLabel.setTouchable(Touchable.disabled);
		}

		protected Image getDotImage() {
			return dotImage;
		}

		protected Label getPriceLabel() {
			return priceLabel;
		}

		protected Label getBuyLabel() {
			return buyLabel;
		}

		protected Label getUnlockPriceLabel() {
			return unlockPriceLabel;
		}

		protected Label getUnlockBuyLabel() {
			return unlockBuyLabel;
		}
	}

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

	private void setBackgroundWindow() {
		MessageScreen message = new MessageScreen(stage);

		message.makeWindow(
				"You do not have enough points. Do you want to purchase more?",
				"Yes", "No", new ButtonInterface() {

					@Override
					public void buttonPressed() {
						// User clicked yes
						// Go to purchase shop
					}
				}, new ButtonInterface() {

					@Override
					public void buttonPressed() {
						// User clicked no
					}
				});
	}

	private void updatePoints() {
		points = Assets.points_manager.getTotalPoints();
		points_label.setText("Points: " + String.valueOf(points));
	}

	private void setUpShopTable() {
		final Dot invincibleDot = new Dot(DOT_TYPE.INVINCIBLE);
		final Dot rainbowDot = new Dot(DOT_TYPE.RAINBOW);
		final Dot magnetDot = new Dot(DOT_TYPE.MAGNET);

		ArrayList<Dot> dots = new ArrayList<Dot>();
		dots.add(magnetDot);
		dots.add(invincibleDot);
		dots.add(rainbowDot);

		// Total Points Label
		points_label = new Label(
				"Points: " + String.valueOf(points),
				Assets.prefs.getString("bg_color").equals("Black") ? Assets.style_font_32_white
						: Assets.style_font_32_black);

		// Event Listeners
		for (final Dot d : dots) {
			d.setBuyLabelListener(new InputListener() {
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					return true;
				}

				public void touchUp(InputEvent event, float x, float y,
						int pointer, int button) {
					d.buyDot(1);
				}
			});

			d.setUnlockLabelListener(new InputListener() {
				public boolean touchDown(InputEvent event, float x, float y,
						int pointer, int button) {
					return true;
				}

				public void touchUp(InputEvent event, float x, float y,
						int pointer, int button) {
					d.unlockDot();
				}
			});
		}

		// Construct Table
		table.add(points_label);

		for (Dot d : dots) {
			table.row().pad(20);
			table.add(d.getDotImage());
			table.add(d.getPriceLabel());
			table.add(d.getBuyLabel());
			table.add(d.getUnlockPriceLabel());
			table.add(d.getUnlockBuyLabel());
		}

		stage.addActor(table);
	}

	private void notEnoughPoints() {
		setBackgroundWindow();
	}

	@Override
	public void show() {
		super.show();
		updatePoints();
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		super.render(delta);
	}

}
