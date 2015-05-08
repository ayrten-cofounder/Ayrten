/*
 *  The Manager is in charge of keeping track of the current state of the game.
 *  (ie. current time, current points, etc.) It's also in charge of the current
 *  state of the current level (ie. removing dots, adding/subtracting time, etc.)
 */

package com.ayrten.scrots.manager;

import com.ayrten.scrots.dots.DWD_PenDot1;
import com.ayrten.scrots.dots.DWD_PenDot2;
import com.ayrten.scrots.dots.Dot;
import com.ayrten.scrots.dots.PenDot1;
import com.ayrten.scrots.dots.PenDot2;
import com.ayrten.scrots.dots.PowerDot_Magnet;
import com.ayrten.scrots.level.Level;
import com.ayrten.scrots.scoreboard.Scoreboard;
import com.ayrten.scrots.time.Time;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Manager {
	public Level curr_level;
	private Time time;
	private Scoreboard sb;
	private Stage stage;
	
	public enum game_state { ONGOING, PAUSED, GAME_OVER };
	
	public game_state current_state;

	// Game mode
	private int mode;
	
	private boolean isRainbowState;
	private boolean isMagnetState;
	
	private PowerDot_Magnet magnet;

	// True if player loses
	private boolean game_over = false;

	// The score, time, etc. of the game
	private int score;
	
	// Area of game play.
	public float min_width, max_width;
	public float min_height, max_height;

	public Manager(int score, float min_w, float max_w, float min_h, float max_h, Stage stage) {
		this.score = score;

		time = new Time(this);
		sb = new Scoreboard();
		this.stage = stage;
		isRainbowState = false;
		current_state = game_state.ONGOING;
		
		min_width = min_w;
		max_width = max_w;
		min_height = min_h;
		max_height = max_h;
	}
	
	public game_state getGameState()
	{
		return current_state;
	}
	
	public void setGameState(game_state state)
	{
		current_state = state;
	}
	
	public boolean isRainbowState()
	{
		return isRainbowState;
	}
	
	public boolean isMagnetState()
	{
		return isMagnetState;
	}
	
	public PowerDot_Magnet getMagnet()
	{
		return magnet;
	}
	
	public void setRainbowState(boolean state)
	{
		isRainbowState = state;
	}
	
	public void setMagnetState(boolean state, PowerDot_Magnet magnet)
	{
		isMagnetState = state;
		this.magnet = magnet;
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
		for(Dot dot: curr_level.get_all_dots())
			if(!isRainbowState || !isPenDot(dot))
				dot.setVisible(visible);
	}

	public void changeDotSize() {
		if(curr_level == null)
			System.out.println("CURR LEVEL NULL");
		for(Dot dot: curr_level.get_all_dots())
			dot.resetRatio();
	}
	
	public void changePenalityDotVisibility(boolean visible) {
		for(Dot dot: curr_level.get_all_dots())
			if(isPenDot(dot))
				dot.setVisible(visible);
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
		if(Assets.gplay_manager.isAchievementLevel(score))
			Assets.gplay_manager.unlockLevelAchievement(score);
		score++;
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
	
	public void subtractTime(float x) {
		time.subtractTime(x);
	}

	public boolean isGameOver() {
		return game_over;
	}
	
	public boolean isLevelClear() {
		return (curr_level.level_clear());
	}
}
