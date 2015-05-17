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

								if (Assets.game.apk_intf.getAppVersion() != Assets.prefs.getFloat("app_version", 0))
									loadUpdateScreen();
								else {
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
		int pages = 5;

		Table top_table = new Table(Assets.skin);
		top_table.setWidth(Assets.width * pages);

		Image image = new Image(new Texture(
				Gdx.files.internal("data/update_1.png")));
		Table page_one = new Table(Assets.skin);
		page_one.setHeight(MessageScreen.WINDOW_DISPLAY_HEIGHT);
		page_one.setWidth(Assets.width);
		page_one.add(image).width(Assets.width).height(MessageScreen.WINDOW_DISPLAY_HEIGHT);
		
		Table page_two = new Table(Assets.skin);
		page_two.setHeight(MessageScreen.WINDOW_DISPLAY_HEIGHT);
		page_two.setWidth(Assets.width);
		image = new Image(new Texture(Gdx.files.internal("data/update_2.png")));
		page_two.add(image).width(Assets.width).height(MessageScreen.WINDOW_DISPLAY_HEIGHT);

		Table page_three = new Table(Assets.skin);
		page_three.setHeight(MessageScreen.WINDOW_DISPLAY_HEIGHT);
		page_three.setWidth(Assets.width);
		image = new Image(new Texture(Gdx.files.internal("data/update_3.png")));
		page_three.add(image).width(Assets.width).height(MessageScreen.WINDOW_DISPLAY_HEIGHT);
		
		Table page_four = new Table(Assets.skin);
		page_four.setHeight(MessageScreen.WINDOW_DISPLAY_HEIGHT);
		page_four.setWidth(Assets.width);
		image = new Image(new Texture(Gdx.files.internal("data/update_4.png")));
		page_four.add(image).width(Assets.width).height(MessageScreen.WINDOW_DISPLAY_HEIGHT);

		Table page_five = new Table(Assets.skin);
		page_five.setHeight(MessageScreen.WINDOW_DISPLAY_HEIGHT);
		page_five.setWidth(Assets.width);
		image = new Image(new Texture(Gdx.files.internal("data/update_5.png")));
		page_five.add(image).width(Assets.width).height(MessageScreen.WINDOW_DISPLAY_HEIGHT);
		
		top_table.add(page_one).width(Assets.width).height(MessageScreen.WINDOW_DISPLAY_HEIGHT);
		top_table.add(page_two).width(Assets.width).height(MessageScreen.WINDOW_DISPLAY_HEIGHT);
		top_table.add(page_three).width(Assets.width).height(MessageScreen.WINDOW_DISPLAY_HEIGHT);
		top_table.add(page_four).width(Assets.width).height(MessageScreen.WINDOW_DISPLAY_HEIGHT);
		top_table.add(page_five).width(Assets.width).height(MessageScreen.WINDOW_DISPLAY_HEIGHT);
		top_table.left();
		// Use the slideshow version of MessageScreen.
		MessageScreen update_screen = new MessageScreen(top_table, pages) {
			@Override
			public void transition() {
				super.transition();
				Assets.prefs.putFloat("app_version",
						Assets.game.apk_intf.getAppVersion());
				Assets.prefs.flush();
				Assets.game.setScreen(Assets.game.main_menu);
				if (Assets.prefs.getBoolean("auto_gplay_signin", true))
					Assets.game.apk_intf.gplay_signin();
			}
		};
		update_screen.setBackgroundColor(150, 66, 66);
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
