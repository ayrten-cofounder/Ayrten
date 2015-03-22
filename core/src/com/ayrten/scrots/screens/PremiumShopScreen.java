package com.ayrten.scrots.screens;

import java.util.ArrayList;

import com.ayrten.scrots.manager.Assets;
import com.ayrten.scrots.shop.IAP;
import com.ayrten.scrots.shop.ShopPremiumables;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class PremiumShopScreen extends ScrotsScreen {

	private ArrayList<ShopPremiumables> premiumables;

	public PremiumShopScreen(Screen bscreen) {
		super(bscreen, true);

		setupStage();
		setPremiumTable();

		showTableScreen();
	}

	public void setPremiumTable() {
		table.clear();

		premiumables = new ArrayList<ShopPremiumables>();
		premiumables.add(new ShopPremiumables(IAP.ITEM_1));
		premiumables.add(new ShopPremiumables(IAP.ITEM_2));
		premiumables.add(new ShopPremiumables(IAP.ITEM_3));
		premiumables.add(new ShopPremiumables(IAP.ITEM_4));
		premiumables.add(new ShopPremiumables(IAP.ITEM_5));

		for (ShopPremiumables s : premiumables) {
			if (!s.retrievedItemInfo) {
				break;
			}

			table.row().pad(20);
			table.add(s.cost_label).width(Assets.width / 3);
			table.add(s.description_label).width(Assets.width / 3);
			table.add(s.buy_label).width(Assets.width / 3);
		}
	}

	@Override
	public void show() {
		super.show();
		setPremiumTable();
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		super.render(delta);
	}

}
