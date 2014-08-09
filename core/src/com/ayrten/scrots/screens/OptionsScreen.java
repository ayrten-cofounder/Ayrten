package com.ayrten.scrots.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class OptionsScreen implements Screen
{
	private ScrotsGame game;
	private Stage stage;
	
	public OptionsScreen(ScrotsGame game)
	{
		this.game = game;
		stage = new Stage();
	}
	
	public Stage getStage()
	{
		return stage;
	}
	
	@Override
	public void render(float delta) 
	{
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.draw();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		stage.dispose();
	}
	
	@Override
	public void show() 
	{
		Gdx.input.setInputProcessor(stage);
	}
	
	@Override
	public void resize(int width, int height) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}
}
