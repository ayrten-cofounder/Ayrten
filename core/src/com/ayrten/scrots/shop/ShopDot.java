package com.ayrten.scrots.shop;

import com.ayrten.scrots.manager.Assets;
import com.ayrten.scrots.manager.ButtonInterface;
import com.ayrten.scrots.manager.GPlayManager;
import com.ayrten.scrots.screens.MessageScreen;
import com.ayrten.scrots.screens.ShopScreen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldListener;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

public class ShopDot extends ShopItem {
	private DOT_TYPE dotType;
	private DOT_UNLOCK dotUnlock;

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
		super(shop);
		this.dotType = dotType;
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

		initialize();
	}

	public void buyDots() {
		if (shop.getPoints() < totalCostToBuy) {
			shop.notEnoughPoints();

		} else {

			Assets.points_manager.subtractPoints(totalCostToBuy);

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

			Assets.points_manager.subtractPoints(cost);
			unlocked = true;

			switch (dotUnlock) {
			case INVINCIBLE:
				Assets.power_dot_manager.unlockInvincibleDot();
				Assets.gplay_manager
						.unlockUnlockPowerDot(GPlayManager.ACHIEVEMENT_UNLOCK_DOT_INVINCIBLE);
				break;
			case MAGNET:
				Assets.power_dot_manager.unlockMagnetDot();
				Assets.gplay_manager
						.unlockUnlockPowerDot(GPlayManager.ACHIEVEMENT_UNLOCK_DOT_MAGNET);
				break;
			case RAINBOW:
				Assets.power_dot_manager.unlockRainbowDot();
				Assets.gplay_manager
						.unlockUnlockPowerDot(GPlayManager.ACHIEVEMENT_UNLOCK_DOT_RAINBOW);
				break;
			}

			shop.updatePoints();
			shop.updateShopTable();
		}
	}

	protected void initialize() {
		// Locked Labels
		unlockPriceLabel = new Label(String.valueOf(dotUnlock.price()),
				Assets.style_font_32_white);
		unlockPriceLabel.setAlignment(Align.center);

		unlockBuyLabel = new Label("Unlock", Assets.style_font_32_white);
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
		unlockBuyLabel.setAlignment(Align.center);

		// Unlocked Labels
		priceLabel = new Label(String.valueOf(dotType.price()),
				Assets.style_font_32_white);
		priceLabel.setAlignment(Align.center);

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

		amountTextField = new TextField("0", Assets.skin);
		amountTextField.getStyle().background = Assets.gray_box;
		amountTextField.getStyle().font = Assets.font_32;
		amountTextField.getStyle().fontColor = Color.WHITE;
		amountTextField.setAlignment(Align.center);
		amountTextField.setWidth(Assets.width / 5);
		amountTextField
				.setTextFieldFilter(new TextField.TextFieldFilter.DigitsOnlyFilter());
		amountTextField.setMaxLength(4);
		amountTextField.setTextFieldListener(new TextFieldListener() {
			@Override
			public void keyTyped(TextField textField, char c) {
				if (c == '\n' || c == '\r') {
					if (!textField.getText().equals("")) {
						amountToBuy = Integer.valueOf(textField.getText());
						setTotalCost();
					}
				}
			}
		});

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

		// Same as the in ShopScreen.
		float cell_wh = (float) (Assets.height * 0.14);
		float spacing = (float) (Assets.height * 0.01);

		Table addTable = new Table();
		addTable.setSkin(Assets.skin);
		addTable.setSize(55, dotImage.getHeight());
		addTable.add(add).height(cell_wh / 2 - spacing);
		addTable.top();

		Table minusTable = new Table();
		minusTable.setSkin(Assets.skin);
		minusTable.setSize(55, dotImage.getHeight());
		minusTable.add(minus).height(cell_wh / 2 - spacing);
		minusTable.bottom();

		totalCostLabel = new Label(String.valueOf(totalCostToBuy),
				Assets.style_font_32_white);
		totalCostLabel.setAlignment(Align.center);

		amountTable.clear();
		amountTable.add(amountTextField).height(cell_wh)
				.width(Assets.width / 12);
		amountTable.stack(addTable, minusTable).height(cell_wh)
				.width(Assets.width / 12).padLeft(spacing * 2);
	}

	protected void addAmountToBuy(int amount) {
		if (amountToBuy + amount > 9999)
			amountToBuy = 9999;
		else
			amountToBuy += amount;
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

		initialize();
	}

	public int getTotalCost() {
		return totalCostToBuy;
	}
}