package com.ayrten.scrots.game;

import java.util.ArrayList;

import com.ayrten.scrots.level.Level;
import com.ayrten.scrots.manager.Manager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class GameMode
{
	private SpriteBatch batch = new SpriteBatch();;
	private Stage stage;
	private Manager gm;

	private ArrayList<Level> all_levels = new ArrayList<Level>();
	private Level curr_level;
	private CharSequence str = " points";
	private CharSequence time = "Time left: ";

	private BitmapFont font_points = new BitmapFont();
	private BitmapFont font_time = new BitmapFont();

	private int w, h;

	public GameMode(Stage stage, Manager gm, int width, int height)
	{
		this.stage = stage;
		this.gm = gm;
		this.w = width;
		this.h = height;

		generate();
		gm.startGame();
	}

	public void dispose()
	{
		batch.dispose();
	}

	private void generate()
	{
		// Generate the first 20 levels.
		for (int i = 1; i < 20; i++)
		{
			Level lvl = new Level(i, w, h, gm);
			all_levels.add(lvl);
		}

		setStage();
	}

	public void render()
	{
		if (gm.isGameOver())
		{
			gameOver();
		}
		else
		{
			point();
			time();

			stage.draw();
			if (curr_level.level_clear())
			{
				levelClear();
			}
		}
	}

	public void point()
	{
		batch.begin();
		font_points.draw(batch, String.valueOf(gm.getPoints()) + str, 50, 50);
		batch.end();

	}

	public void time()
	{
		batch.begin();
		font_time.draw(batch, time + gm.getTime(), 50, 65);
		batch.end();

	}

	public void gameOver()
	{
		gm.setHighScore();
		
		batch.begin();
		font_points.draw(batch, "Game Over", 50, 65);
		font_time.draw(batch, "Highscore: " + gm.getHighScore(), 50, 50);
		batch.end();
	}

	public void levelClear()
	{
		// One point for clearing a level
		stage.clear();
		gm.plusOnePoint();

		// Level newLevel = new Level()
		setStage();
	}

	public void setStage()
	{
		curr_level = all_levels.remove(0);
		gm.setLevel(curr_level);
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
		for (int i = 0; i < curr_level.get_baby_blue_dots().size(); i++)
		{
			stage.addActor(curr_level.get_baby_blue_dots().get(i));
		}
	}
}
