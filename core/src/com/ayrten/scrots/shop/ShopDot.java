package com.ayrten.scrots.shop;

import com.ayrten.scrots.manager.Assets;
import com.ayrten.scrots.manager.ButtonInterface;
import com.ayrten.scrots.screens.MessageScreen;
import com.ayrten.scrots.screens.ShopScreen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

public class ShopDot {
	private ShopScreen shop;

	private DOT_TYPE dotType;
	private DOT_UNLOCK dotUnlock;
	private String description;

	public boolean unlocked;

	public Table amountTable;
	public Image dotImage;
	public Image descriptionImage;
	public TextField amountTextField;

	// Unlocked
	public Label priceLabel;
	public Label totalCostLabel;

	// Locked
	public Label unlockPriceLabel;
	public Label unlockBuyLabel;

	private int totalCostToBuy = 0;
	private int amountToBuy = 0;

	public enum DOT_TYPE {
		MAGNET(150), INVINCIBLE(250), RAINBOW(350);

		private int price = 0;

		private DOT_TYPE(int p) {
			price = p;
		}

		protected int price() {
			return price;
		}
	}

	public enum DOT_UNLOCK {
		MAGNET(500), INVINCIBLE(850), RAINBOW(1050);

		private int price = 0;

		private DOT_UNLOCK(int p) {
			price = p;
		}

		protected int price() {
			return price;
		}
	}

	public ShopDot(DOT_TYPE dotType, ShopScreen shop) {
		this.dotType = dotType;
		this.shop = shop;
		this.amountTable = new Table();
		amountTable.setSkin(Assets.skin);

		switch (dotType) {
		case INVINCIBLE:
			dotImage = new Image(Assets.invincible_dot);
			dotUnlock = DOT_UNLOCK.INVINCIBLE;
			unlocked = Assets.power_dot_manager.isInvincibleDotUnlocked();
			description = Assets.invincible_dot_description;
			break;

		case MAGNET:
			dotImage = new Image(Assets.magnet_dot);
			dotUnlock = DOT_UNLOCK.MAGNET;
			unlocked = Assets.power_dot_manager.isMagnetDotUnlocked();
			description = Assets.magnet_dot_description;
			break;

		case RAINBOW:
			dotImage = new Image(Assets.rainbow_dot);
			dotUnlock = DOT_UNLOCK.RAINBOW;
			unlocked = Assets.power_dot_manager.isRainbowDotUnlocked();
			description = Assets.rainbow_dot_description;
			break;
		}

		initilize();
	}

	public void buyDots() {
		if (shop.getPoints() < totalCostToBuy) {
			shop.notEnoughPoints();

		} else {

			Assets.points_manager.addPoints(-(totalCostToBuy));

			switch (dotType) {
			case INVINCIBLE:
				Assets.power_dot_manager
						.setInvincibleDotAmount(Assets.power_dot_manager
								.getInvincibleDots() + amountToBuy);
				break;
			case MAGNET:
				Assets.power_dot_manager
						.setMagnetDotAmount(Assets.power_dot_manager
								.getMagnetDots() + amountToBuy);
				break;
			case RAINBOW:
				Assets.power_dot_manager
						.setRainbowDotAmount(Assets.power_dot_manager
								.getRainbowDots() + amountToBuy);
				break;
			}

			shop.updatePoints();
		}
	}

	protected void unlockDot() {
		int cost = dotUnlock.price();

		if (shop.getPoints() < cost) {
			shop.notEnoughPoints();

		} else {

			Assets.points_manager.addPoints(-(cost));
			unlocked = true;

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

			shop.updatePoints();
			shop.updateShopTable();
		}
	}

	protected void initilize() {

		// Locked Labels
		unlockPriceLabel = new Label(
				String.valueOf(dotUnlock.price()),
				Assets.prefs.getString("bg_color").equals("Black") ? Assets.style_font_32_white
						: Assets.style_font_32_black);
		unlockBuyLabel = new Label("Unlock", Assets.prefs.getString("bg_color")
				.equals("Black") ? Assets.style_font_32_white
				: Assets.style_font_32_black);

		unlockBuyLabel.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				unlockDot();
			}
		});

		// Unlocked Labels
		priceLabel = new Label(
				String.valueOf(dotType.price()),
				Assets.prefs.getString("bg_color").equals("Black") ? Assets.style_font_32_white
						: Assets.style_font_32_black);

		descriptionImage = new Image(Assets.question_mark);
		descriptionImage.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				descriptionWindow();
			}
		});

		amountTextField = new TextField("", Assets.skin);
		amountTextField.getStyle().background = Assets.gray_box;
		amountTextField.setWidth(Assets.width / 5);

		Image add = new Image(Assets.up_button);
		add.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				addAmountToBuy(1);
			}
		});

		Image minus = new Image(Assets.down_button);
		minus.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				minusAmountToBuy(1);
			}
		});

		Table addTable = new Table();
		addTable.setSkin(Assets.skin);
		addTable.setSize(55, dotImage.getHeight());
		addTable.add(add).height((dotImage.getHeight() / 2) - 10);
		addTable.top();

		Table minusTable = new Table();
		minusTable.setSkin(Assets.skin);
		minusTable.setSize(55, dotImage.getHeight());
		minusTable.add(minus).height((dotImage.getHeight() / 2) - 10);
		minusTable.bottom();

		totalCostLabel = new Label(
				String.valueOf(totalCostToBuy),
				Assets.prefs.getString("bg_color").equals("Black") ? Assets.style_font_32_white
						: Assets.style_font_32_black);

		amountTable.clear();
		amountTable.add(amountTextField).height(dotImage.getHeight())
				.width(Assets.width / 5);
		amountTable.stack(addTable, minusTable).height(dotImage.getHeight())
				.width(55);
	}

	protected void addAmountToBuy(int amount) {
		amountToBuy++;
		setTotalCost();
	}

	protected void minusAmountToBuy(int amount) {
		if (amountToBuy - amount < 0) {
			return;
		}

		amountToBuy -= amount;
		setTotalCost();
	}

	protected void setAmountToBuy(int amount) {
		if (amount < 0) {
			return;
		}

		amountToBuy = amount;
		setTotalCost();
	}

	protected void setAmountOnEditText() {
		amountTextField.setText(String.valueOf(amountToBuy));
	}

	protected void setTotalCost() {
		totalCostToBuy = dotType.price() * amountToBuy;

		totalCostLabel.setText(String.valueOf(totalCostToBuy));
		setAmountOnEditText();
		shop.updateTotalPrice();
	}

	private void descriptionWindow() {
		MessageScreen message = new MessageScreen(shop.getStage());

		message.makeSingleButtonWindow(description, "Ok",
				new ButtonInterface() {

					@Override
					public void buttonPressed() {
					}
				});
	}

	public void clear() {
		totalCostToBuy = 0;
		amountToBuy = 0;

		initilize();
	}

	public int getTotalCost() {
		return totalCostToBuy;
	}
}