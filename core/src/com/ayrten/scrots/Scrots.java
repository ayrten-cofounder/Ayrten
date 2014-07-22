package com.ayrten.scrots;

import java.util.ArrayList;

import com.ayrten.scrots.dots.Level;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;


public class Scrots implements ApplicationListener
{
	private SpriteBatch			batch;
	TextureRegion				region;
	
	Stage						stage;
	
	private int					w;
	private int					h;
	int curr_lvl;
	Level curr_level;
	// Tracks all level objects
	ArrayList<Level> all_levels;
	
	@Override
	public void create()
	{
		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();
		System.out.println("Width of screen: " + w);
		System.out.println("Height of scrren: " + h);
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		batch = new SpriteBatch();
		
		curr_lvl = 0;
		all_levels = new ArrayList<Level>();
		// Generate the first five levels.
		for(int i = 0; i < 5; i++)
		{
			Level lvl = new Level(i, w, h);
			all_levels.add(lvl);
		}
		curr_level = all_levels.remove(0);
		for(int i = 0; i < curr_level.get_blue_dots().size(); i++)
			stage.addActor(curr_level.get_blue_dots().get(i));
		for(int i = 0; i < curr_level.get_grn_dots().size(); i++)
			stage.addActor(curr_level.get_grn_dots().get(i));
		for(int i = 0; i < curr_level.get_red_dots().size(); i++)
			stage.addActor(curr_level.get_red_dots().get(i));
	}
	
	@Override
	public void dispose()
	{
		batch.dispose();
	}
	
	@Override
	public void render()
	{ 
		// Draws white background?
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		// stage.act(Gdx.graphics.getDeltaTime());

		stage.draw();
		if(stage.getActors().size == 0)
		{
			//Level newLevel = new Level()
			curr_level = all_levels.remove(0);
			for(int i = 0; i < curr_level.get_blue_dots().size(); i++)
				stage.addActor(curr_level.get_blue_dots().get(i));
			for(int i = 0; i < curr_level.get_grn_dots().size(); i++)
				stage.addActor(curr_level.get_grn_dots().get(i));
			for(int i = 0; i < curr_level.get_red_dots().size(); i++)
				stage.addActor(curr_level.get_red_dots().get(i));
		}
	}
	
	@Override
	public void resize(int width, int height)
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
