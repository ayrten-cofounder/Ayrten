package com.ayrten.scrots.screens;

import com.ayrten.scrots.game.GameMode;
import com.ayrten.scrots.game.MainMenuBackgroundGameMode;
import com.ayrten.scrots.manager.Assets;
import com.ayrten.scrots.manager.ButtonInterface;
import com.ayrten.scrots.scoreboard.ChallengeScoreboard;
import com.ayrten.scrots.scoreboard.NormalScoreboard;
import com.ayrten.scrots.scoreboard.Scoreboard;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

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

		others_screen = new OthersScreen(this);
		high_score_screen = new HighScoresScreen(this);
		// game_screen = new GameScreen((ScrotsGame)

		LabelStyle title_style = new LabelStyle();
		title_style.font = Assets.font_120;
		title_style.fontColor = Color.valueOf("9f38ff");
		Label scrots = new Label("Scrots", title_style);
		scrots.setCenterPosition(Gdx.graphics.getWidth() / 2,
				Gdx.graphics.getHeight() / 3 * 2);

		LabelStyle style = new LabelStyle();
		style.font = Assets.font_64;
		style.fontColor = Assets.ORANGE;
		start = new Label("Start", style);
		start.setBounds(start.getX(), start.getY(), start.getWidth(),
				start.getHeight());
		start.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				if(Assets.prefs.getBoolean("sound_effs", true))
					Assets.button_pop.play();
				if(Assets.prefs.getBoolean("first_time", true)) {
					Assets.game.apk_intf.makeYesNoWindow("This is your first time playing. Do you want to view the tutorial?", new ButtonInterface() {
						@Override
						public void buttonPressed() {
							Assets.game.setScreen(Assets.game.main_menu.others_screen.tutorial_screen);
						}
					}, new ButtonInterface() {
						@Override
						public void buttonPressed() {
							Timer timer = new Timer();
							timer.scheduleTask(new Task() {
								@Override
								public void run() {
									Assets.prefs.putBoolean("first_time", true);
									Assets.prefs.flush();
									game_screen = new GameScreen();
									Assets.playGameBGM();
									Assets.game.setScreen(game_screen);
								}
							}, 0.5f);
						}
					}, Assets.prefs.getString("bg_color").equals("Black") ? 0 : 1);
				} else {
					game_screen = new GameScreen();
					Assets.playGameBGM();
					Assets.game.setScreen(game_screen);
				}
			}			
		});
		start.setCenterPosition(Gdx.graphics.getWidth()/2, scrots.getY() - scrots.getStyle().font.getLineHeight()/2);

		highscore = new Label("High Scores", style);
		highscore.setBounds(highscore.getX(), highscore.getY(), highscore.getWidth(), highscore.getHeight());
		highscore.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				if (Assets.prefs.getBoolean("sound_effs", true))
					Assets.button_pop.play();
				Assets.game.setScreen(high_score_screen);
			}			
		});

		highscore.setCenterPosition(Gdx.graphics.getWidth()/2, start.getY() - highscore.getStyle().font.getLineHeight()/2);

		others = new Label("Others", style);
		others.setBounds(others.getX(), others.getY(), others.getWidth(), others.getHeight());
		others.addListener(new ClickListener(){
			public void clicked(InputEvent event, float x, float y) {
				if (Assets.prefs.getBoolean("sound_effs", true))
					Assets.button_pop.play();
				Assets.game.setScreen(others_screen);
			}			
		});
		others.setCenterPosition(Gdx.graphics.getWidth()/2, highscore.getY() - others.getStyle().font.getLineHeight()/2);

		Manager gm = new Manager(0, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		gm.setMode(GameMode.MAIN_MENU_BACKGROUND_MODE);
		gm.setScoreboard(new Scoreboard());
		new MainMenuBackgroundGameMode(stage, gm, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight()).gen_curr_level();
		gm.changeDotSize();

		setupStage();
		stage.addActor(scrots);
		stage.addActor(start);
		stage.addActor(highscore);
		stage.addActor(others);
	}

	public void addActors()
	{
		actors.add(start);
		actors.add(highscore);
		actors.add(others);
	}
}