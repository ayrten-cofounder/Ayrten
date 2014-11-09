package com.ayrten.scrots.screens;

import com.ayrten.scrots.manager.Assets;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class LoadingScreen implements Screen 
{
	private Stage stage;
	private boolean can_transition;
	
	public LoadingScreen()
	{
		stage = new Stage();
		can_transition = false;

		Label.LabelStyle style = new Label.LabelStyle();
		style.font = Assets.font_120;
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

		if(can_transition && Gdx.input.isTouched())
		{
			can_transition = false;
			stage.addAction(Actions.sequence(Actions.alpha(1), Actions.fadeOut(1f), Actions.run(new Runnable() {
				@Override
				public void run() {
					// Should get loading screen.dispose but how...
					stage.dispose();
					Assets.game.setScreen(Assets.game.main_menu);	
				}
			})));
		}
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
				can_transition = true;
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
