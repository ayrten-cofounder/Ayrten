package com.ayrten.scrots.screens;


import com.ayrten.scrots.manager.Assets;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
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
									Assets.game.setScreen(Assets.game.main_menu);
								}
							}
						})));
			}
		}, 2.5f);

		Label.LabelStyle style = new Label.LabelStyle();
		style.font = Assets.font_120;
		style.fontColor = Assets.ORANGE;
		Label scrots = new Label("Ayrten", style);
		scrots.setCenterPosition(Gdx.graphics.getWidth() / 2,
				Gdx.graphics.getHeight() / 2);

		stage.addActor(scrots);
	}

	private void loadUpdateScreen() {
		int pages = 3;
		Table top_table = new Table(Assets.skin);
		top_table.setWidth(Assets.width * pages);
		top_table.add("first").width(Assets.width);
		top_table.add("middle").width(Assets.width);
		top_table.add("last").width(Assets.width);
		
		MessageScreen update_screen = new MessageScreen(top_table, pages){
			@Override
			public void transition() {
				Assets.prefs.putFloat("app_version", Assets.game.apk_intf.getAppVersion());
				Assets.prefs.flush();
				Assets.game.setScreen(Assets.game.main_menu);
			}
		};
		Assets.game.setScreen(update_screen);
	}

	@Override
	public void render(float delta) {
		if (Assets.prefs.getString("bg_color").equals("Black"))
			Gdx.gl.glClearColor(0, 0, 0, 0);
		else
			Gdx.gl.glClearColor(1, 1, 1, 1);
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
