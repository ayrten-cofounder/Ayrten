/*
 *  This guy takes cares of everything data related of the game.
 *  Not everything.
 */

package com.ayrten.scrots.manager;

import com.ayrten.scrots.dots.Level;

public class Manager
{
	private Level curr_level;

	// True if player loses
	private boolean game_over = false;

	// The score, time, etc. of the game
	private int score;

	public Manager(int score)
	{
		this.score = score;
	}

	public void setLevel(Level level)
	{
		this.curr_level = level;
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

	public int getPoints()
	{
		return score;
	}

	public boolean isGameOver()
	{
		return game_over;
	}
}
