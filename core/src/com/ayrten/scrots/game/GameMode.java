package com.ayrten.scrots.game;

import java.util.ArrayList;

import com.ayrten.scrots.level.Level;
import com.ayrten.scrots.manager.Manager;
import com.ayrten.scrots.screens.ScrotsGame;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;

public class GameMode
{
	protected SpriteBatch batch = new SpriteBatch();
	protected Stage stage;
	protected Manager gm;

	protected ArrayList<Level> all_levels = new ArrayList<Level>();
	protected Level curr_level;
	protected CharSequence str = " points";
	protected CharSequence time = "Time left: ";

	protected BitmapFont font_points = new BitmapFont();
	protected BitmapFont font_time = new BitmapFont();
	protected ScrotsGame game;

	protected int w, h;

	public GameMode(ScrotsGame game, Stage stage, Manager gm, int width, int height)
	{
		this.game = game;
		this.stage = stage;
		this.gm = gm;
		this.w = width;
		this.h = height;
		this.batch = (SpriteBatch) stage.getBatch();
		
		font_points = game.font_16;
		font_time = game.font_16;

		generate();
		gm.startGame();
	}

	public void dispose()
	{
		batch.dispose();
	}
	
	public void resize(int width, int height)
	{
//		stage.setViewport(new StretchViewport(width, height));
//		stage.getCamera().position.set(width, height, 0);
	}

	protected void generate()
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
		// Draw box to input to user name
		gm.setHighScore("test");
		
		batch.begin();
		font_points.draw(batch, "Game Over", 50, 65);
		font_time.draw(batch, "Highscore: " + gm.getHighScore(), 50, 50);
		batch.end();
		
		TextFieldStyle style = new TextFieldStyle();
		style.fontColor = Color.RED;
		style.font = game.font_32;
		TextField textfield = new TextField("test", style);
		textfield.setX(300);
		textfield.setY(300);
		stage.addActor(textfield );
		stage.draw();
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

		for (int i = 0; i < curr_level.get_red_dots().size(); i++)
		{
			stage.addActor(curr_level.get_red_dots().get(i));
		}
		
		for (int i = 0; i < curr_level.get_grn_dots().size(); i++)
		{
			stage.addActor(curr_level.get_grn_dots().get(i));
		}
		
		for (int i = 0; i < curr_level.get_baby_blue_dots().size(); i++)
		{
			stage.addActor(curr_level.get_baby_blue_dots().get(i));
		}
		for (int i = 0; i < curr_level.get_grn_dots().size(); i++)
		{
			stage.addActor(curr_level.get_grn_dots().get(i));
		}
	}
}
