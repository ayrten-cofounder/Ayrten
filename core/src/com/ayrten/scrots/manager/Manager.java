/*
 *  This guy takes cares of everything data related of the game.
 *  Not everything.
 */

package com.ayrten.scrots.manager;

import java.util.ArrayList;

import com.ayrten.scrots.dots.Dot;
import com.ayrten.scrots.level.Level;
import com.ayrten.scrots.scoreboard.Scoreboard;
import com.ayrten.scrots.time.Time;

public class Manager
{
	private Level curr_level;
	private Time time;
	private Scoreboard sb;
	
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
		sb = new Scoreboard();
	}
	
	public void setScoreboard(Scoreboard sb)
	{
		this.sb = sb;
	}
	
	public Scoreboard getScoreBoard()
	{
		return sb;
	}
	
	public void setHighScore(String name)
	{
		sb.setHighScore(score, name);
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

	public boolean isGameOver()
	{
		return game_over;
	}
}
