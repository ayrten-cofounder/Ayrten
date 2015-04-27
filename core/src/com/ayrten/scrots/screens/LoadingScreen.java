package com.ayrten.scrots.screens;

import com.ayrten.scrots.manager.Assets;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class LoadingScreen implements Screen {
	private Stage stage;
	private Timer timer;

	public LoadingScreen() {
		stage = new Stage();

		timer = new Timer();
		timer.scheduleTask(new Task() {
			public void run() {
				stage.addAction(Actions.sequence(Actions.alpha(1),
						Actions.fadeOut(1f), Actions.run(new Runnable() {
							@Override
							public void run() {
								stage.dispose();

								if (true) {
//								if (Assets.game.apk_intf.getAppVersion() != Assets.prefs.getFloat("app_version", 0))
									loadUpdateScreen();
								} else {
									Assets.game.main_menu.setActorsTouchable(Touchable.disabled);
									Assets.game.setScreen(Assets.game.main_menu);
									if(Assets.prefs.getBoolean("auto_gplay_signin", true))
										Assets.game.apk_intf.gplay_signin();
								}
							}
						})));
			}
		}, 2.5f);

		Label.LabelStyle style = new Label.LabelStyle();
		style.font = Assets.font_120;
		style.fontColor = Assets.ORANGE;
		Label ayrten = new Label("Ayrten", style);
		ayrten.setPosition(Assets.width / 2, Assets.height / 2, Align.center);
		stage.addActor(ayrten);
	}

	private void loadUpdateScreen() {
		int pages = 3;
		float display_height = Assets.height
				- Assets.style_font_64_orange.font.getLineHeight();

		Table top_table = new Table(Assets.skin);

		// Make the tutorial Drawables.
		Image image = new Image(new Texture(
				Gdx.files.internal("data/update1.png")));
		Table page_one = new Table(Assets.skin);
		page_one.setHeight(display_height);
		page_one.setWidth(Assets.width);
		page_one.add(image).width(Assets.width).height(display_height);

		top_table.setWidth(Assets.width * pages);
		top_table.add(page_one).width(Assets.width).height(display_height);
		top_table.add("second").width(Assets.width).height(display_height);
		top_table.add("last").width(Assets.width);
		top_table.left();
		// Use the slideshow version of MessageScreen.
		MessageScreen update_screen = new MessageScreen(top_table, pages) {
			@Override
			public void transition() {
				Assets.prefs.putFloat("app_version",
						Assets.game.apk_intf.getAppVersion());
				Assets.prefs.flush();
				Assets.game.setScreen(Assets.game.main_menu);
				if (Assets.prefs.getBoolean("auto_gplay_signin", true))
					Assets.game.apk_intf.gplay_signin();
			}
		};
		Assets.game.setScreen(update_screen);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}

	@Override
	public void dispose() {
		stage.dispose();
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void show() {
		Assets.game.apk_intf.setGPlayManager(Assets.gplay_manager);
		if (Assets.prefs.getString("bg_color").equals("Black"))
			Gdx.gl.glClearColor(0, 0, 0, 0);
		else
			Gdx.gl.glClearColor(1, 1, 1, 1);
		stage.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(1f),
				Actions.run(new Runnable() {
					@Override
					public void run() {
						timer.start();
					}
				})));
	}

	@Override
	public void hide() {

	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
