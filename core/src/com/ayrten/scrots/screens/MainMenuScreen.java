package com.ayrten.scrots.screens;

import com.ayrten.scrots.game.GameMode;
import com.ayrten.scrots.game.MainMenuBackgroundGameMode;
import com.ayrten.scrots.manager.Assets;
import com.ayrten.scrots.scoreboard.ChallengeScoreboard;
import com.ayrten.scrots.scoreboard.NormalScoreboard;
import com.ayrten.scrots.scoreboard.Scoreboard;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class MainMenuScreen extends ScrotsScreen {
	private Label start;
	private Label others;
	private Label highscore;
	
	// For options and high scores screens
	protected OthersScreen others_screen;
	protected HighScoresScreen high_score_screen;
	protected GameScreen game_screen;

	public NormalScoreboard nsb;
	public ChallengeScoreboard csb;

	public MainMenuScreen() {
		super(null, false);
		
		// Initialize variables
		nsb = new NormalScoreboard();
		csb = new ChallengeScoreboard();

		Table table = new Table();
		table.setSkin(Assets.skin);
		table.setCenterPosition(Gdx.graphics.getWidth() / 2,
				Gdx.graphics.getHeight() / 4);

		others_screen = new OthersScreen(this);
		high_score_screen = new HighScoresScreen(this);
		// game_screen = new GameScreen((ScrotsGame)
		
		LabelStyle style = new LabelStyle();
		style.font = Assets.font_64;
		style.fontColor = Assets.ORANGE;
		start = new Label("Start", style);
		start.setBounds(start.getX(), start.getY(), start.getWidth(),
				start.getHeight());
		start.addListener(new InputListener() {
			// Need this or else it won't recognize the touch down event.
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				if(Assets.prefs.getBoolean("sound_effs", true))
					Assets.pop.play();
				game_screen = new GameScreen();
				((ScrotsGame) Gdx.app.getApplicationListener())
						.setScreen(game_screen);
			}
		});

		others = new Label("Others", style);
		others.setBounds(others.getX(), others.getY(), others.getWidth(), others.getHeight());
		others.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				if (Assets.prefs.getBoolean("sound_effs", true))
					Assets.pop.play();
				Assets.game.setScreen(others_screen);

			}
		});

		highscore = new Label("High Scores", style);
		highscore.setBounds(highscore.getX(), highscore.getY(), highscore.getWidth(), highscore.getHeight());
		highscore.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				if (Assets.prefs.getBoolean("sound_effs", true))
					Assets.pop.play();
				((ScrotsGame) Gdx.app.getApplicationListener())
						.setScreen(high_score_screen);
			}
		});

		table.add(start);
		table.row();
		table.add("").height(Gdx.graphics.getHeight() / 50);
		table.row();
		table.add(highscore);
		table.row();
		table.add("").height(Gdx.graphics.getHeight() / 50);
		table.row();
		table.add(others);

		Manager gm = new Manager(0, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		gm.setMode(GameMode.MAIN_MENU_BACKGROUND_MODE);
		gm.setScoreboard(new Scoreboard());
		new MainMenuBackgroundGameMode(stage, gm, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight()).gen_curr_level();
		gm.changeDotSize();

		LabelStyle title_style = new LabelStyle();
		title_style.font = Assets.font_120;
		title_style.fontColor = Color.valueOf("9f38ff");
		Label scrots = new Label("Scrots", title_style);
		scrots.setCenterPosition(Gdx.graphics.getWidth() / 2,
				Gdx.graphics.getHeight() / 3 * 2);

		setupStage();
		stage.addActor(scrots);
		stage.addActor(table);
	}
	
	public void addActors()
	{
		actors.add(start);
		actors.add(highscore);
		actors.add(others);
	}
}