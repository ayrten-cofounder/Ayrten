/*
 *  This guy takes cares of everything data related of the game.
 *  Not everything.
 */

package com.ayrten.scrots.manager;

import com.ayrten.scrots.dots.Level;
import com.ayrten.scrots.highscore.HighScore;
import com.ayrten.scrots.time.Time;

public class Manager
{
	private Level curr_level;
	private Time time;
	private HighScore highscore;
	

	// True if player loses
	private boolean game_over = false;

	// The score, time, etc. of the game
	private int score;
	
	public int w, h;

	public Manager(int score, int w, int h)
	{
		this.score = score;
		this.w = w;
		this.h = h;
		
		time = new Time(this);
		highscore = new HighScore();
	}
	
	public String getHighScore()
	{
		return highscore.getHighScore();
	}
	
	public void setHighScore()
	{
		highscore.setHighScore(score);
	}

	public void setLevel(Level level)
	{
		this.curr_level = level;
	}
	
	public void startGame()
	{
		time.startTime();
	}

	public void minusGreenDot()
	{
		if (!curr_level.get_grn_dots().isEmpty())
		{
			curr_level.get_grn_dots().remove(0);
		}
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

	public int getPoints()
	{
		return score;
	}

	public boolean isGameOver()
	{
		return game_over;
	}
}
