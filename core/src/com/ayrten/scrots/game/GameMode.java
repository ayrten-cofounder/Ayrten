package com.ayrten.scrots.game;

import java.util.ArrayList;

import com.ayrten.scrots.dots.Level;
import com.ayrten.scrots.manager.Manager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class GameMode
{
	private SpriteBatch batch;
	private Stage stage;
	private Manager gm;
	
	private ArrayList<Level> all_levels = new ArrayList<Level>();	
	private Level curr_level;
	// private int curr_lvl = 0;
	private CharSequence str = " points";
	
	private BitmapFont font = new BitmapFont();

	private int w, h;

	public GameMode(SpriteBatch batch, Stage stage, Manager gm, int width, int height)
	{
		this.batch = batch;
		this.stage = stage;
		this.gm = gm;
		this.w = width;
		this.h = height;
		
		generate();
	}

	private void generate()
	{
		// Generate the first 20 levels.
		for (int i = 0; i < 20; i++)
		{
			Level lvl = new Level(i, w, h, gm);
			all_levels.add(lvl);
		}

		curr_level = all_levels.remove(0);
		for (int i = 0; i < curr_level.get_blue_dots().size(); i++)
		{
			stage.addActor(curr_level.get_blue_dots().get(i));
		}
		for (int i = 0; i < curr_level.get_grn_dots().size(); i++)
		{
			stage.addActor(curr_level.get_grn_dots().get(i));
		}
		for (int i = 0; i < curr_level.get_red_dots().size(); i++)
		{
			stage.addActor(curr_level.get_red_dots().get(i));
		}
	}

	public void render()
	{
		if (gm.isGameOver())
		{
			batch.begin();
			font.draw(batch, "Game Over", 50, 50);
			batch.end();
		}
		else
		{

			batch.begin();
			font.draw(batch, String.valueOf(gm.getPoints()) + str, 50, 50);
			batch.end();

			stage.draw();
			if (stage.getActors().size == 0)
			{
				// Level newLevel = new Level()
				curr_level = all_levels.remove(0);
				for (int i = 0; i < curr_level.get_blue_dots().size(); i++)
					stage.addActor(curr_level.get_blue_dots().get(i));
				for (int i = 0; i < curr_level.get_grn_dots().size(); i++)
					stage.addActor(curr_level.get_grn_dots().get(i));
				for (int i = 0; i < curr_level.get_red_dots().size(); i++)
					stage.addActor(curr_level.get_red_dots().get(i));
			}
		}
	}
}
