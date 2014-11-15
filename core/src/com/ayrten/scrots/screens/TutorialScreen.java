package com.ayrten.scrots.screens;

import com.ayrten.scrots.manager.Assets;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class TutorialScreen implements Screen 
{	
	protected Label back;
	
	protected Screen backScreen;
	protected Stage stage;
	
	public TutorialScreen(Screen bscreen)
	{
		backScreen = bscreen;
		stage = new Stage();
	
		LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = Assets.font_64;
		labelStyle.fontColor = Assets.ORANGE;
		
		back = new Label("Back", labelStyle);
		back.setBounds(back.getX(), back.getY(), back.getWidth(), back.getHeight());
		back.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(Assets.prefs.getBoolean("sound_effs"))
					Assets.pop.play();
				stage.addAction(Actions.sequence(Actions.alpha(1), Actions.fadeOut(0.35f), Actions.run(new Runnable() {
					@Override
					public void run() {
						Assets.game.setScreen(backScreen);
					}
				})));
			}
		});
		back.setPosition(0 + back.getWidth()/5, Gdx.graphics.getHeight() - back.getHeight());
		
		stage.addActor(back);
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
	public void resize(int width, int height) {
		
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
		stage.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(1f)));
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

	@Override
	public void dispose() {
		
	}
}
