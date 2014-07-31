/*
 *  This guy takes cares of everything data related of the game.
 *  Not everything.
 */

package com.ayrten.scrots.manager;

public class Manager
{
	// True if player loses
	private boolean game_over = false;
	
	// The score, time, etc. of the game
	private int score;
	
	public Manager(int score)
	{
		this.score = score;
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
