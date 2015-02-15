/*
 *  The Manager is in charge of keeping track of the current state of the game.
 *  (ie. current time, current points, etc.) It's also in charge of the current
 *  state of the current level (ie. removing dots, adding/subtracting time, etc.)
 */

package com.ayrten.scrots.manager;

import java.util.ArrayList;

import com.ayrten.scrots.dots.DWD_PenDot1;
import com.ayrten.scrots.dots.DWD_PenDot2;
import com.ayrten.scrots.dots.Dot;
import com.ayrten.scrots.dots.PenDot1;
import com.ayrten.scrots.dots.PenDot2;
import com.ayrten.scrots.level.Level;
import com.ayrten.scrots.scoreboard.Scoreboard;
import com.ayrten.scrots.time.Time;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Manager {
	public Level curr_level;
	private Time time;
	private Scoreboard sb;
	private Stage stage;

	// Game mode
	private int mode;

	private int slot_num;
	private float slot_width;
	
	private boolean isRainbowState;

	// True if player loses
	private boolean game_over = false;

	// The score, time, etc. of the game
	private int score;

	public Manager(int score, int w, int h, Stage stage) {
		this.score = score;

		time = new Time(this);
		sb = new Scoreboard();
		this.stage = stage;
		slot_num = 0;
		slot_width = 0;
		isRainbowState = false;
	}
	
	public boolean isRainbowState()
	{
		return isRainbowState;
	}
	
	public void setRainbowState(boolean state)
	{
		isRainbowState = state;
	}
	
	public int getCurrentSlot()
	{
		return slot_num;
	}
	
	public void nextSlot()
	{
		slot_num++;
		if(slot_num > 2)
			slot_num = 0;
	}
	
	public void setSlotWidth(float f)
	{
		slot_width = f;
	}
	
	public float getSlotWidth()
	{
		return slot_width;
	}

	public Stage getStage() {
		return stage;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public void setScoreboard(Scoreboard sb) {
		this.sb = sb;
	}

	public Scoreboard getScoreBoard() {
		return sb;
	}

	public void addHighScore(String name) {
		sb.addHighScore(score, name);
	}

	public int get_game_mode() {
		return mode;
	}

	public int get_player_score() {
		return score;
	}

	public void setLevel(Level level) {
		this.curr_level = level;
	}

	public void startGame() {
		time.startTime();
		changeDotVisibility(true);
	}

	public void pauseGame() {
		time.pauseTime();
		changeDotVisibility(false);
	}

	public void changeDotVisibility(boolean visible) {		
		for(ArrayList<Dot> dotList: curr_level.get_all_dots())
		{
			for(Dot dot: dotList)
			{
				if(!isRainbowState || !isPenDot(dot))
					dot.setVisible(visible);
			}
		}
	}

	public void changeDotSize() {		
		for(ArrayList<Dot> dotList: curr_level.get_all_dots())
		{
			for(Dot dot: dotList)
			{
				dot.resetRatio();
			}
		}
	}
	
	public void changePenalityDotVisibility(boolean visible)
	{
		for(ArrayList<Dot> dotList: curr_level.get_all_dots())
		{
			for(Dot dot: dotList)
			{
				if(isPenDot(dot))
				{
					dot.setVisible(visible);
				}
			}
		}
	}
	
	private boolean isPenDot(Dot dot)
	{
		return (dot.getClass() == PenDot1.class
				|| dot.getClass() == PenDot2.class
				|| dot.getClass() == DWD_PenDot1.class
				|| dot.getClass() == DWD_PenDot2.class);
	}

	public void minusGreenDot() {
		curr_level.minusGreenDot();
	}

	public void gameOver() {
		game_over = true;
	}

	public void plusOnePoint() {
		score++;
		if(score == 5)
			Assets.game.apk_intf.unlockAchievement("pass_lvled5");
	}

	public void minusOnePoint() {
		score--;
	}

	// Put negative number to subtract x points
	public void addXPoint(int x) {
		score = score + x;
	}

	public String getTime() {
		return time.getTime();
	}

	public float getFloatTime() {
		return time.getFloatTime();
	}

	public void addTime(float x) {
		time.addTime(x);
	}

	public boolean isGameOver() {
		return game_over;
	}
}
