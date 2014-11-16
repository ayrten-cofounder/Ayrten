package com.ayrten.scrots.screens;

import com.ayrten.scrots.manager.Assets;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class LoadingScreen implements Screen 
{
	private Stage stage;
	private Timer timer;

	public LoadingScreen()
	{
		stage = new Stage();
		
		timer = new Timer();
		timer.scheduleTask(new Task() {
			public void run() {
				stage.addAction(Actions.sequence(Actions.alpha(1), Actions.fadeOut(1f), Actions.run(new Runnable() {
					@Override
					public void run() {
						stage.dispose();
						Assets.game.setScreen(Assets.game.main_menu);	
					}
				})));
			}
		}, 2.5f);

		Label.LabelStyle style = new Label.LabelStyle();
		style.font = Assets.font_120;
		style.fontColor = Assets.ORANGE;
		Label scrots = new Label("Ayrten", style);
		scrots.setCenterPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);

		stage.addActor(scrots);
	}

	@Override
	public void render(float delta) 
	{
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}

	@Override
	public void dispose() 
	{
		stage.dispose();
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void show() 
	{
		stage.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(1f), Actions.run(new Runnable() {
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
