package com.ayrten.scrots.screens;

import java.util.ArrayList;

import com.ayrten.scrots.dots.power.PowerDot;
import com.ayrten.scrots.dots.power.PowerDot_Decelerate;
import com.ayrten.scrots.dots.power.PowerDot_Magnet;
import com.ayrten.scrots.dots.power.PowerDot_Rainbow;
import com.ayrten.scrots.game.GameMode;
import com.ayrten.scrots.game.TimeMode;
import com.ayrten.scrots.manager.Assets;
import com.ayrten.scrots.manager.ButtonInterface;
import com.ayrten.scrots.manager.Manager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class GameScreen extends ScrotsScreen {
	protected static final float TIME_TO_RED = 10f;

	// Widgets
	protected Image menu_button;

	protected Label level_title;
	protected Label level;
	protected Label time_title;
	protected Label time;
	protected Label time_end;

	protected Label points;
	protected Table points_table;

	protected GameMode gamemode;
	protected Manager gm;

	protected boolean should_clear_stage;

	protected Table top_table;
	protected Table corner_table;
	protected Table side_table;

	protected PowerDot_Magnet magnet;

	protected ArrayList<PowerDot> powDots;
	
	// Used for changing Drawable for menu button.
	protected TextureRegionDrawable[] trd;
	
	protected ScrollPane pause_scroll;

	public GameScreen() {
		super(null, false);
		createBackLabelAndInitNavBar();

		should_clear_stage = true;
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		stage.getBatch().enableBlending();
		stage.getBatch().setBlendFunction(GL20.GL_LINEAR_MIPMAP_NEAREST,
				GL20.GL_NEAREST);

		trd = new TextureRegionDrawable[2];
		trd[0] = new TextureRegionDrawable(
				new TextureRegion(Assets.play_button));
		trd[1] = new TextureRegionDrawable(new TextureRegion(
				Assets.pause_button));


		gm = new Manager(0, Assets.width - Assets.game_width, Assets.width, 0, Assets.game_height, stage); // Starts with 0 points
		// if (Assets.prefs.getString("mode").equals("Normal")) {
		gm.setMode(GameMode.NORMAL_MODE);
		gm.setScoreboard(Assets.game.main_menu.nsb);
		gamemode = new TimeMode(stage, gm);
		// } else {
		// gm.setMode(GameMode.CHALLENGE_MODE);
		// gm.setScoreboard(Assets.game.main_menu.csb);
		// gamemode = new ChallengeGameMode(stage, gm, w, h);
		// }
		
		initializePointsTime();
		initializePauseMenu();
		addPowDots();
		
		corner_table = new Table(Assets.skin);
		corner_table.setHeight((Assets.height - Assets.game_height - 10) / 2 * 3);
		corner_table.setWidth(corner_table.getHeight());
		corner_table.setPosition(10, Assets.height - corner_table.getHeight()
				- 10);

		Table temp = new Table(Assets.skin);
		temp.add(level);

		Table lvl_table = new Table(Assets.skin);
		lvl_table.setHeight(corner_table.getHeight());
		lvl_table.setWidth(corner_table.getWidth());
		Image lvl_bubble = new Image(Assets.lvl_bubble);
		lvl_table.stack(lvl_bubble, temp).height(corner_table.getHeight() / 2)
				.width(corner_table.getWidth() / 2);
		lvl_table.top().left();

		temp = new Table(Assets.skin);
		temp.add(time);

		Table time_table = new Table(Assets.skin);
		time_table.setHeight(corner_table.getHeight());
		time_table.setWidth(corner_table.getWidth());
		Image time_bubble = new Image(Assets.time_bubble);
		time_table.stack(time_bubble, temp)
				.height(corner_table.getHeight() / 4 * 3)
				.width(corner_table.getWidth() / 4 * 3);
		time_table.bottom().right();

		corner_table.stack(time_table, lvl_table)
				.height(corner_table.getHeight())
				.width(corner_table.getWidth());

		int points_top = (int) (Assets.height * 0.02);
		int points_bottom = points_top;
		int points_left = (int) (Assets.width * 0.015);
		int points_right = points_left;

		Label points_title = new Label("Pts", Assets.style_font_32_white);
		points_title.setAlignment(Align.right);
		NinePatchDrawable rounded_rectangle_blue_small = new NinePatchDrawable(
				new NinePatch(new Texture(Gdx.files
						.internal("data/rounded_rectangle_blue_small.png")),
						points_top, points_bottom, points_left, points_right));

		points_table = new Table(Assets.skin);
		points_table.setBackground(rounded_rectangle_blue_small);
		points_table
				.setWidth(points.getStyle().font.getBounds("999999999+").width
						+ points_title.getWidth() + corner_table.getWidth()
						+ 10);
		points_table.setPosition(
				corner_table.getWidth() / 4 + 10,
				Assets.height - corner_table.getHeight() / 8 - 10
						- points.getStyle().font.getLineHeight());
		points_table.setHeight(points.getStyle().font.getLineHeight());
		points_table.add(points);
		points_table.add(points_title);
		points_table.right();

		side_table = new Table(Assets.skin_window);
		side_table.setBackground(Assets.rounded_rectangle_border_blue);
		side_table.setHeight(corner_table.getY() - 20);
		side_table.setWidth(Assets.width - Assets.game_width - 10);
		side_table.setPosition(10, 10);

		for (int i = 0; i < powDots.size(); i++) {
			PowerDot powDot = powDots.get(i);

			temp = new Table(Assets.skin);
			temp.setWidth(side_table.getWidth());
			temp.setHeight(side_table.getWidth());
			temp.add(powDot.getNumLabel());
			temp.top().right();

			Table timer_table = new Table(Assets.skin);
			timer_table.add(powDot.getTimeLabel());

			Table cell_table = new Table(Assets.skin);
			cell_table.setSize(side_table.getWidth() - 10,
					side_table.getHeight() / powDots.size());
			cell_table.stack(powDot, powDot.getGrayImage(), powDot.getRadialTimer(), timer_table, temp)
					.width(side_table.getWidth() - 10)
					.height(side_table.getWidth() - 10);

			side_table.add().width(20);
			side_table.add(cell_table).height(
					side_table.getHeight() / powDots.size());
			side_table.add().width(20);
			if (i != powDots.size() - 1)
				side_table.row();
		}
		
		// TODO: set width to max number of combo label... but what should the max combo displayed be?
		top_table = new Table(Assets.skin);
		top_table.setPosition(points_table.getX() + points_table.getWidth() + 10, Assets.game_height);
		top_table.setWidth(Assets.width - top_table.getX() - 20);
		top_table.setHeight(Assets.height - Assets.game_height - 10);
		
		Table info_table = new Table(Assets.skin);
		info_table.setSize(top_table.getWidth() - top_table.getHeight(), top_table.getHeight());
		info_table.align(Align.top);
		info_table.add(gm.getComboLabel()).width(top_table.getWidth() - top_table.getHeight()).left().top();
		
		top_table.add(info_table).width(top_table.getWidth() - top_table.getHeight()).height(top_table.getHeight());
		top_table.add(menu_button).width(top_table.getHeight())
				.height(top_table.getHeight());

		// Problem: dots are over the timer and lvl and power dots. However, you
		// need to put the
		// pause_scroll at the bottom or else you can't touch the dots.
		stage.addActor(pause_scroll);
		gamemode.gen_start_level(1);
		addStageActors();
		gm.startGame();
	}

	private void initializePointsTime() {
		level = new Label("00", Assets.style_font_32_white);
		time = new Label("60.0", Assets.style_font_32_white);
		points = new Label("0", Assets.style_font_32_white);
	}

	private void replay() {
		Timer timer = new Timer();
		timer.scheduleTask(new Task() {
			@Override
			public void run() {
				GameModeScreen.game_screen.dispose();
				GameModeScreen.game_screen = new GameScreen();
				Assets.game.setScreen(GameModeScreen.game_screen);
			}
		}, 0.5f);
	}

	private void main_menu() {
		Assets.stats_manager.writePlayerStatsToFile();
		GameModeScreen.game_screen.dispose();
		Assets.playMenuBGM();
		Assets.game.setScreen(Assets.game.main_menu);
		Assets.game.main_menu.checkRateMe();
	}

	private void initializePauseMenu() {
		LabelStyle buttonStyle = Assets.style_font_64_orange;

		menu_button = new Image(Assets.pause_button);
		menu_button.setHeight(Assets.height - Assets.game_height);
		menu_button.setWidth(Assets.height - Assets.game_height);
		menu_button.setBounds(menu_button.getX(), menu_button.getY(),
				menu_button.getWidth(), menu_button.getHeight());
		menu_button.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (Assets.prefs.getBoolean("sound_effs"))
					Assets.button_pop.play();
				if (gm.getGameState() == Manager.gameState.ONGOING) {
					gm.setGameState(Manager.gameState.PAUSED);
					gm.pauseGame();
					side_table.setTouchable(Touchable.disabled);
					for (int i = 0; i < powDots.size(); i++)
						powDots.get(i).pauseTime();
					pause_scroll.scrollTo(0, Assets.game_height * 2,
							pause_scroll.getWidth(), pause_scroll.getHeight());
					menu_button.setDrawable(trd[0]);
				} else if (gm.getGameState() == Manager.gameState.PAUSED) {
					gm.setGameState(Manager.gameState.ONGOING);
					new Timer().scheduleTask(new Task() {
						@Override
						public void run() {
							gm.startGame();
							menu_button.setTouchable(Touchable.enabled);
							side_table.setTouchable(Touchable.enabled);
							for (int i = 0; i < powDots.size(); i++)
								powDots.get(i).resumeTime();
							menu_button.setDrawable(trd[1]);
						}
					}, 1);

					pause_scroll.scrollTo(0, 0, pause_scroll.getWidth(),
							pause_scroll.getHeight());
					menu_button.setTouchable(Touchable.disabled);
				}
			}
		});

		TextButton pause_quit = new TextButton("", Assets.skin);
		pause_quit.add(new Label("Quit", buttonStyle));
		pause_quit.setBounds(pause_quit.getX(), pause_quit.getY(),
				pause_quit.getWidth(), pause_quit.getHeight());

		Label quit = new Label("Quit", buttonStyle);
		quit.setBounds(quit.getX(), quit.getY(), quit.getWidth(),
				quit.getHeight());
		quit.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				showQuitConfirm();
			}
		});

		Label tutorial = new Label("Tutorial", buttonStyle);
		tutorial.setBounds(tutorial.getX(), tutorial.getY(),
				tutorial.getWidth(), tutorial.getHeight());
		tutorial.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Assets.game.main_menu.othersScreen.tutorial_screen
						.setBackScreen(Assets.game.getScreen());
				Assets.game
						.setScreen(Assets.game.main_menu.othersScreen.tutorial_screen);
			}
		});

		Label options = new Label("Options", buttonStyle);
		options.setBounds(options.getX(), options.getY(), options.getWidth(),
				options.getHeight());
		options.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Assets.game.main_menu.optionsScreen.setBackScreen(Assets.game
						.getScreen());
				Assets.game.main_menu.optionsScreen
						.enableNonGameOptions(false);
				Assets.game.setScreen(Assets.game.main_menu.optionsScreen);
			}
		});

		final Table pause_table = new Table(Assets.skin);
		pause_table.setWidth(Assets.width);
		pause_table.setHeight(Assets.game_height * 2);
		pause_table.right().padRight(10);
		Table dotTable = new Table(Assets.skin);
		dotTable.align(Align.right);

		ArrayList<Actor> opts = new ArrayList<Actor>();
		opts.add(quit);
		opts.add(options);
		opts.add(tutorial);

		Table opTable = new Table(Assets.skin);
		opTable.right();
		for (int i = 0; i < opts.size(); i++) {
			Table temp = new Table(Assets.skin);
			temp.add(opts.get(i));
			opTable.add(temp).height(Assets.game_height / opts.size()).right();
			if (i != opts.size() - 1)
				opTable.row();
		}

		pause_table.add(opTable);
		pause_table.row();
		pause_table.add().height(Assets.game_height);
		pause_table.setVisible(false);

		pause_scroll = new ScrollPane(pause_table);
		pause_scroll.setWidth(Assets.width);
		pause_scroll.setHeight(Assets.game_height);
		pause_scroll.setPosition(0, 0);
		pause_scroll.layout();
		pause_scroll.setFlickScroll(false);
		pause_scroll.setScrollPercentY(100);
		pause_scroll.setFadeScrollBars(false);

		new Timer().scheduleTask(new Task() {
			@Override
			public void run() {
				pause_table.setVisible(true);
			}
		}, (float) 0.5);
	}

	// Creates the time label for the power dots also
	private void addPowDots() {
		powDots = new ArrayList<PowerDot>();
		PowerDot powDot_1 = new PowerDot_Rainbow(Assets.rainbow_dot, gm,
				Assets.reg_pop_1);
//		PowerDot powDot_2 = new PowerDot_Invincible(Assets.invincible_dot, gm,
//				Assets.reg_pop_1);
		PowerDot powDot_2 = new PowerDot_Decelerate(Assets.decelerate_dot, gm,
				Assets.reg_pop_1);
		magnet = new PowerDot_Magnet(Assets.magnet_dot, gm, Assets.reg_pop_1);
		PowerDot powDot_3 = magnet;
		powDots.add(powDot_1);
		powDots.add(powDot_2);
		powDots.add(powDot_3);
	}

	public void setHighScoreName(String name) {
		gm.addHighScore(name);
	}

	public void showQuitHighScoreMenu() {
		MessageScreen message = new MessageScreen(stage);
		message.makeWindow(
				"Are you sure you don't want to enter your highscore?", "Yes",
				"Cancel", new ButtonInterface() {
					@Override
					public void buttonPressed() {
						main_menu();
					}
				}, new ButtonInterface() {
					@Override
					public void buttonPressed() {
					}
				});
	}

	public void showQuitConfirm() {
		MessageScreen message = new MessageScreen(stage);

		message.makeWindow("Quit?", "Yes", "No", new ButtonInterface() {

			@Override
			public void buttonPressed() {
				main_menu();
			}
		}, new ButtonInterface() {

			@Override
			public void buttonPressed() {
			}
		});
	}

	public void showGameOver() {
		MessageScreen message = new MessageScreen(stage);
		message.makeWindow("Game Over!", "Replay", "Main Menu",
				new ButtonInterface() {
					@Override
					public void buttonPressed() {
						replay();
					}
				}, new ButtonInterface() {
					@Override
					public void buttonPressed() {
						main_menu();
					}
				});
	}

	public void showGameOverWithHighScore() {
		MessageScreen message = new MessageScreen(stage);
		message.makeGameOverWithHighScore(this, "Game Over!", "Replay",
				"Main Menu", new ButtonInterface() {
					@Override
					public void buttonPressed() {
						replay();
					}
				}, new ButtonInterface() {
					@Override
					public void buttonPressed() {
						main_menu();
					}
				});
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(Gdx.graphics.getDeltaTime());
		
		if(gm.hasTutorials()) {
			gm.pauseGame();
			for(int i = 0; i < powDots.size(); i++)
				powDots.get(i).pauseTime();
			gm.loadTutorial();
		} else { 
			if(!gm.addedDots) {
				gm.addDotsToStage();
				if(gm.getGameState() == Manager.gameState.PAUSED) {
					gm.startGame();
					for(int i = 0; i < powDots.size(); i++)
						powDots.get(i).resumeTime();
				}
			}

			if (gm.isGameOver()) {
				gameOver();
			} else {
				level();
				time();
				points();

				if (gm.isLevelClear())
					levelClear();
			}
			stage.draw();
		}
	}

	public void points() {
		if (Assets.points_manager.getTotalPoints() > Assets.MAX_POINTS)
			points.setText("999999999+");
		else
			points.setText(String.valueOf(Assets.points_manager
					.getTotalPoints()));
	}

	public void level() {
		String score = String.valueOf(gm.get_player_score());

		if (gm.get_player_score() < 10) {
			score = "0" + score;
		}
		level.setText(score);
	}

	public void time() {
		if (Float.valueOf(gm.getTime()) < TIME_TO_RED) {
			time.setStyle(Assets.style_font_32_timer_red);
		} else {
			time.setStyle(Assets.style_font_32_white);
		}

		time.setText(gm.getTime());
	}

	public void gameOver() {
		if (should_clear_stage) {
			Assets.stats_manager.writePlayerStatsToFile();
			Assets.game.apk_intf.submitLeaderboardScore(
					Assets.LeaderboardType.TIME, gm.get_player_score());

			stage.clear();
			should_clear_stage = false;
			addStageActors();
			if (gm.get_player_score() > gm.getScoreBoard().getLowestHighScore()) {
				showGameOverWithHighScore();
			} else {
				showGameOver();
			}
		}
	}

	private void addStageActors() {
		stage.addActor(top_table);
		stage.addActor(points_table);
		stage.addActor(corner_table);
		stage.addActor(side_table);
	}

	public void levelClear() {
		// One point for clearing a level
		stage.clear();
		gm.plusOnePoint();
		stage.addActor(pause_scroll);
		addStageActors();

		if (Assets.prefs.getBoolean("sound_effs", true))
			Assets.level_clear.play();
		gamemode.gen_next_level();
		
		if (gm.isMagnetState())
			magnet.magnet();
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
	}
}
