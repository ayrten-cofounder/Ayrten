package com.ayrten.scrots.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class HighScoresScreen implements Screen
{
	private ScrotsGame game;
	private Stage stage;
	
	public HighScoresScreen(ScrotsGame game)
	{
		this.game = game;
		stage = new Stage();
		TextButton back = new TextButton("Back", game.skin);
		back.setBounds(back.getX(), back.getY(), back.getWidth(), back.getHeight());
		
		back.setPosition(0, Gdx.graphics.getHeight() - back.getHeight());
		back.addListener(new InputListener()
		{
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
			{
				return true;
			}
			
			public void touchUp(InputEvent event, float x, float y,	int pointer, int button)
			{
				if(((ScrotsGame) Gdx.app.getApplicationListener()).prefs.getBoolean("sound_effs"))
				  ((ScrotsGame) Gdx.app.getApplicationListener()).pop.play();
				((ScrotsGame) Gdx.app.getApplicationListener()).setScreen(((ScrotsGame) Gdx.app.getApplicationListener()).main_menu);
			}
		});	
		
		stage.addActor(back);
	}

	@Override
	public void render(float delta) 
	{
		if(game.prefs.getString("bg_color").equals("White"))
			Gdx.gl.glClearColor(1, 1, 1, 1);
		else
			Gdx.gl.glClearColor(0, 0, 0, 0);
		
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.draw();
		
		if(Gdx.input.isKeyPressed(Input.Keys.BACKSPACE))
			game.setScreen(game.main_menu);
	}

	@Override
	public void dispose() 
	{
		stage.dispose();
	}
	
	@Override
	public void resize(int width, int height) 
	{
		
	}

	@Override
	public void show() 
	{
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void hide() 
	{
		
	}

	@Override
	public void pause() 
	{
		
	}

	@Override
	public void resume() 
	{
		
	}
}
