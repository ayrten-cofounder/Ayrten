/*
 *  The Manager is in charge of keeping track of the current state of the game.
 *  (ie. current time, current points, etc.) It's also in charge of the current
 *  state of the current level (ie. removing dots, adding/subtracting time, etc.)
 */

package com.ayrten.scrots.screens;

import com.ayrten.scrots.dots.Dot;
import com.ayrten.scrots.level.Level;
import com.ayrten.scrots.scoreboard.Scoreboard;
import com.ayrten.scrots.time.Time;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Manager
{
	public Level curr_level;
	private Time time;
	private Scoreboard sb;
	private Stage stage;
	
	// Game mode
	private int mode;
	
	// Tracks number of power dots.
	private int powDot1_num;
	private int powDot2_num;
	private int powDot3_num;
	
	// True if player loses
	private boolean game_over = false;

	// The score, time, etc. of the game
	private int score;
	
	public Manager(int score, int w, int h, Stage stage)
	{
		this.score = score;
		
		time = new Time(this);
		sb = new Scoreboard();
		this.stage = stage;
	}
	
	public void setMode(int mode)
	{
		this.mode = mode;
	}
	
	public void setScoreboard(Scoreboard sb)
	{
		this.sb = sb;
	}
	
	public Scoreboard getScoreBoard()
	{
		return sb;
	}
	
	public void addHighScore(String name)
	{
		sb.addHighScore(score, name);
	}
	
	public int get_game_mode()
	{
		return mode;
	}
	
	public int get_player_score()
	{
		return score;
	}

	public void setLevel(Level level)
	{
		this.curr_level = level;
	}
	
	public void startGame()
	{
		time.startTime();
		changeDotVisibility(true);
	}
	
	public void pauseGame()
	{
		time.pauseTime();
		changeDotVisibility(false);
	}
	
	public void changeDotVisibility(boolean visible)
	{
		for(Dot dot : curr_level.get_baby_blue_dots())
		{
			dot.setVisible(visible);
		}
		for(Dot dot : curr_level.get_blue_dots())
		{
			dot.setVisible(visible);
		}
		for(Dot dot : curr_level.get_red_dots())
		{
			dot.setVisible(visible);
		}
		for(Dot dot : curr_level.get_grn_dots())
		{
			dot.setVisible(visible);
		}
	}
	
	public void changeDotSize()
	{
		for(Dot dot : curr_level.get_baby_blue_dots())
		{
			dot.resetRatio();
		}
		for(Dot dot : curr_level.get_blue_dots())
		{
			dot.resetRatio();
		}
		for(Dot dot : curr_level.get_red_dots())
		{
			dot.resetRatio();
		}
		for(Dot dot : curr_level.get_grn_dots())
		{
			dot.resetRatio();
		}
	}

	public void minusGreenDot()
	{
		curr_level.minusGreenDot();
	}

	public void gameOver()
	{
		game_over = true;
	}

	public void plusOnePoint()
	{
		score++;
	}

	public void minusOnePoint()
	{
		score--;
	}

	// Put negative number to subtract x points
	public void addXPoint(int x)
	{
		score = score + x;
	}
	
	public String getTime()
	{
		return time.getTime();
	}
	
	public void addTime(float x)
	{
		time.addTime(x);
	}

	public boolean isGameOver()
	{
		return game_over;
	}
}
