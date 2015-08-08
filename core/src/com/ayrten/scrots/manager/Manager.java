/*
 *  The Manager is in charge of keeping track of the current state of the game.
 *  (ie. current time, current points, etc.) It's also in charge of the current
 *  state of the current level (ie. removing dots, adding/subtracting time, etc.)
 */

package com.ayrten.scrots.manager;

import java.util.ArrayList;

import com.ayrten.scrots.dotAnimation.DotAnimation;
import com.ayrten.scrots.dotAnimation.DotAnimation_TimeMode;
import com.ayrten.scrots.dots.Dot;
import com.ayrten.scrots.dots.DotGenerator;
import com.ayrten.scrots.dots.MovingDot;
import com.ayrten.scrots.dots.penalty.DWD_PenDot_Base;
import com.ayrten.scrots.dots.penalty.PenDot_Base;
import com.ayrten.scrots.dots.power.PowerDot_Magnet;
import com.ayrten.scrots.game.GameMode;
import com.ayrten.scrots.level.Level;
import com.ayrten.scrots.scoreboard.Scoreboard;
import com.ayrten.scrots.screens.GameModeScreen;
import com.ayrten.scrots.screens.MessageScreen;
import com.ayrten.scrots.time.Time;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

public class Manager {
	public Level curr_level;
	private Time time;
	private Scoreboard sb;
	private Stage stage;
	public DotGenerator generator;
	public DotAnimation animation;

	public enum gameState {
		ONGOING, PAUSED, GAME_OVER
	};

	public gameState currGameState;

	// Game mode
	private int mode;

	private boolean isRainbowState;
	private boolean isMagnetState;
	private boolean isInvincible;

	private PowerDot_Magnet magnet;

	// The score, time, etc. of the game
	protected int score;
	protected int combo_chain;
	protected Label comboLabel;
	
	// Area of game play.
	public float min_width, max_width;
	public float min_height, max_height;
	public boolean addedDots;

	public Manager(int score, float min_w, float max_w, float min_h,
			float max_h, Stage stage) {
		this.score = score;

		isRainbowState = false;
		isMagnetState = false;
		isInvincible = false;
		addedDots = false;
		combo_chain = 0;
		min_width = min_w;
		max_width = max_w;
		min_height = min_h;
		max_height = max_h;
		currGameState = gameState.ONGOING;
		
		time = new Time(this);
		sb = new Scoreboard();
		this.stage = stage;
		generator = new DotGenerator(this);
		comboLabel = new Label("Combo: x" + combo_chain, Assets.style_font_32_white);
		
		if (mode == GameMode.NORMAL_MODE
				|| get_game_mode() == GameMode.CHALLENGE_MODE) {
			animation = new DotAnimation_TimeMode();
		} else 
			animation = new DotAnimation();
	}
	
	public void incrementCombo() {
		combo_chain++;
		comboLabel.setText("Combo: x" + combo_chain);
		if(curr_level.getRegDotList().size() > 0)
			curr_level.getRegDotList().get(0).setComboDot();
	}
	
	public void resetCombo() {
		combo_chain = 0;
		comboLabel.setText("Combo: x" + combo_chain);
	}
	
	public Label getComboLabel() {
		return comboLabel;
	}
	
	public int getComboChain() {
		return combo_chain;
	}

	public gameState getGameState() {
		return currGameState;
	}

	public void setGameState(gameState state) {
		currGameState = state;
	}

	public boolean isRainbowState() {
		return isRainbowState;
	}

	public boolean isMagnetState() {
		return isMagnetState;
	}
	
	public boolean isInvincible() {
		return isInvincible;
	}

	public PowerDot_Magnet getMagnet() {
		return magnet;
	}

	public void setRainbowState(boolean state) {
		isRainbowState = state;
	}

	public void setMagnetState(boolean state, PowerDot_Magnet magnet) {
		isMagnetState = state;
		this.magnet = magnet;
	}
	
	public void setInvincible(boolean state) {
		isInvincible = state;
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
		setGameState(gameState.ONGOING);
	}

	public void pauseGame() {
		time.pauseTime();
		changeDotVisibility(false);
		setGameState(gameState.PAUSED);
	}

	public void changeDotVisibility(boolean visible) {
		for (Dot dot : curr_level.getDotList())
			if (!isRainbowState || !isPenDot(dot))
				dot.setVisible(visible);
	}

	public void changeDotSize() {
		for (MovingDot dot : curr_level.getDotList())
			dot.resetRatio();
	}

	public void changePenalityDotVisibility(boolean visible) {
		for (Dot dot : curr_level.getDotList())
			if (isPenDot(dot))
				dot.setVisible(visible);
	}

	public boolean isPenDot(Dot dot) {
		return (PenDot_Base.class.isInstance(dot)
				|| DWD_PenDot_Base.class.isInstance(dot));
	}

	public void minusGreenDot() {
		curr_level.minusGreenDot();
	}

	public void gameOver() {
		currGameState = gameState.GAME_OVER;
	}

	public void plusOnePoint() {
		if (Assets.gplay_manager.isAchievementLevel(score))
			Assets.gplay_manager.unlockLevelAchievement(score);
		score++;
	}

	public void minusOnePoint() {
		score--;
	}

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
		return (currGameState == gameState.GAME_OVER);
	}

	public boolean isLevelClear() {
		return (curr_level.level_clear());
	}

	public void addDotsToStage() {
		addedDots = true;
		for (int i = 0; i < curr_level.getDotList().size(); i++)
			stage.addActor(curr_level.getDotList().get(i));
		
		curr_level.getRegDotList().get(0).setComboDot();
		
		if (isRainbowState())
			changePenalityDotVisibility(false);

		if (isMagnetState())
			stage.addActor(getMagnet());
	}

	public boolean hasTutorials() {
		return (curr_level.getFirst("firstDWD_regdot1_help")
				|| curr_level.getFirst("firstDWD_regdot2_help")
				|| curr_level.getFirst("firstDWD_pendot1_help") || curr_level
					.getFirst("firstDWD_pendot2_help"));
	}

	public void loadTutorial() {
		Image dot_image;
		String dot_desc;
		String intro = "";
		ArrayList<Image> dot_images = new ArrayList<Image>();
		ArrayList<String> dot_descs = new ArrayList<String>();

		if (curr_level.getFirst("firstDWD_regdot1_help")) {
			curr_level.setFirst("firstDWD_regdot1_help", false);
			dot_image = new Image(Assets.dwdRegDot_1);
			Image explosion = new Image(Assets.explosion_dot);

			dot_desc = "3 to 4 green dots will appear. An explosion dot may appear.";
			String explosion_desc = "Pops all dots around the explosion dot. Radius depends on dot size.";
			intro = "Congratulations on making this far! From this point on, two new dots will appear, "
					+ "which may or may not help you. Is this a gamble you're willing to take?";

			dot_images.add(dot_image);
			dot_images.add(explosion);
			dot_descs.add(dot_desc);
			dot_descs.add(explosion_desc);
		} else if (curr_level.getFirst("firstDWD_regdot2_help")) {
			curr_level.setFirst("firstDWD_regdot2_help", false);
			dot_image = new Image(Assets.dwdRegDot_1);
			dot_desc = "2 blue dots will appear. Increases time limit by 1 second.";
			intro = "Wow! You must have some skills to have made it this far. This new dot we're introducing will surely help you.";

			dot_images.add(dot_image);
			dot_descs.add(dot_desc);
		} else if (curr_level.getFirst("firstDWD_pendot1_help")) {
			curr_level.setFirst("firstDWD_pendot1_help", false);
			dot_image = new Image(Assets.dwdPenDot_1);
			dot_desc = "4 red dots will appear. Decreases time limit by half.";
			intro = "You have some skills to have made it this far... we'll have to make this more challenging for you... Are you prepared to "
					+ "take on this new dot?";

			dot_images.add(dot_image);
			dot_descs.add(dot_desc);
		} else if (curr_level.getFirst("firstDWD_pendot2_help")) {
			curr_level.setFirst("firstDWD_pendot2_help", false);
			dot_image = new Image(Assets.dwdPenDot_2);
			dot_desc = "2 purple dots will appear. Decreases time limit by 3 seconds.";
			intro = "Let's see how you deal with this challenging new dot...";

			dot_images.add(dot_image);
			dot_descs.add(dot_desc);
		}

		playTutorial(intro, dot_images, dot_descs);
	}

	private void playTutorial(String intro_msg, ArrayList<Image> dots,
			ArrayList<String> desc) {
		Table main_table = new Table(Assets.skin);
		main_table.setFillParent(true);
		main_table.setSize(Assets.width, MessageScreen.WINDOW_DISPLAY_HEIGHT);

		Label intro = new Label(intro_msg, Assets.style_font_32_white);
		intro.setWrap(true);
		Table intro_table = new Table(Assets.skin);
		intro_table.add(intro).width(Assets.width - Assets.PAD * 2)
				.padLeft(Assets.PAD).padRight(Assets.PAD);

		main_table.add(intro_table).width(Assets.width - Assets.PAD * 2)
				.padLeft(Assets.PAD).padRight(Assets.PAD);
		main_table.row();
		main_table.add().height(Assets.font_32.getLineHeight());
		main_table.row();

		Table body_table = new Table(Assets.skin);
		assert (dots.size() == desc.size());
		for (int i = 0; i < dots.size(); i++) {
			body_table.add(dots.get(i))
					.width(Assets.font_32.getLineHeight() * 2)
					.height(Assets.font_32.getLineHeight() * 2);
			Label desc_label = new Label(desc.get(i),
					Assets.style_font_32_white);
			desc_label.setWrap(true);
			desc_label.setAlignment(Align.left);
			body_table.add(desc_label).padLeft(Assets.PAD);
			if (i != dots.size() - 1) {
				body_table.row();
				body_table.add().height(Assets.font_32.getLineHeight());
				body_table.row();
			}
		}

		main_table.add(body_table).left().padLeft(Assets.PAD);

		MessageScreen dyn_help = new MessageScreen(main_table, 1) {
			@Override
			public void transition() {
				super.transition();
				Assets.game.setScreen(GameModeScreen.game_screen);
			}
		};
		dyn_help.setBackgroundColor(0, 0, 0);
		Assets.game.setScreen(dyn_help);
	}
}
