package com.ayrten.scrots.screens;

import com.ayrten.scrots.manager.Assets;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.ui.*;

public class ProfileScreen extends ScrotsScreen {

	private Table table;

	public ProfileScreen(Screen bscreen) {
		super(bscreen, true);

		table = new Table();
		table.setFillParent(true);
		table.setSkin(Assets.skin);

		setupStage();
		table.row();
		table.add(new Label(
				String.valueOf("Green Dot"),
				Assets.prefs.getString("bg_color").equals("Black") ? Assets.style_font_32_white : Assets.style_font_32_black));
		table.add(new Label(
				String.valueOf(Assets.stats_manager.getPlayerStats().reg_dot_1.popped),
				Assets.prefs.getString("bg_color").equals("Black") ? Assets.style_font_32_white : Assets.style_font_32_black));
		stage.addActor(table);
	}


	@Override
	public void show() {
		super.show();
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		super.render(delta);
	}
	
	
}
