package com.ayrten.scrots.shop;

import com.ayrten.scrots.manager.Assets;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

// Basic premiumables

public class ShopPremiumables {

	private String item;
	private String cost;
	private String description;

	public Label cost_label;
	public Label description_label;
	public Label buy_label;

	private int pointValue = 0; // How many points the premiumable is worth

	public boolean retrievedItemInfo = false;

	public ShopPremiumables(String item) {
		this.item = item;

		setInfo();
	}

	private void setInfo() {
		if (!Assets.game.iap_inft.retrievedItems()) {
			retrievedItemInfo = false;
			return;
		}

		cost = Assets.game.iap_inft.getPrice(item);
		description = Assets.game.iap_inft.getDescription(item) + " points";
		pointValue = Integer.valueOf(Assets.game.iap_inft.getDescription(item));

		cost_label = new Label(cost, Assets.prefs.getString("bg_color").equals(
				"Black") ? Assets.style_font_32_white
				: Assets.style_font_32_black);

		description_label = new Label(description, Assets.prefs.getString(
				"bg_color").equals("Black") ? Assets.style_font_32_white
				: Assets.style_font_32_black);

		buy_label = new Label("Buy", Assets.prefs.getString("bg_color").equals(
				"Black") ? Assets.style_font_32_white
				: Assets.style_font_32_black);

		buy_label.addListener(new InputListener() {

			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				super.touchUp(event, x, y, pointer, button);

				purchase();
			}

		});

		cost_label.setAlignment(Align.center);
		description_label.setAlignment(Align.center);
		buy_label.setAlignment(Align.center);

		retrievedItemInfo = true;
	}

	public void purchase() {
		Assets.game.iap_inft.purchase(item, new IAPInterface() {

			@Override
			public void purchaseSuccess() {
			}

			@Override
			public void purchaseFailed() {
			}

			@Override
			public void consumeSuccess() {
				Assets.points_manager.addPoints(pointValue);
			}

			@Override
			public void consumeFailed() {
			}
		});
	}
}
