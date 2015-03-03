package com.ayrten.scrots.screens;

import java.util.ArrayList;

import com.ayrten.scrots.dots.Dot;
import com.ayrten.scrots.dots.PowerDot;
import com.ayrten.scrots.dots.PowerDot_Invincible;
import com.ayrten.scrots.dots.PowerDot_Magnet;
import com.ayrten.scrots.dots.PowerDot_Rainbow;
import com.ayrten.scrots.game.GameMode;
import com.ayrten.scrots.game.NormalGameMode;
import com.ayrten.scrots.level.Level;
import com.ayrten.scrots.manager.Assets;
import com.ayrten.scrots.manager.ButtonInterface;
import com.ayrten.scrots.manager.Manager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class GameScreen extends ScrotsScreen {
	// Widgets
	protected Image pause;

	protected Label level_title;
	protected Label level;
	protected Label time_title;
	protected Label time;
	protected Label time_end;

	protected Label points_title;
	protected Label points;

	protected GameMode gamemode;
	protected Manager gm;
	protected Stage stage;

	protected int w;
	protected int h;

	protected Level curr_level;
	protected SpriteBatch batch;
	protected boolean should_clear_stage;
	protected ArrayList<Level> all_levels = new ArrayList<Level>();

//	protected Table slots;
	protected Table top_table;
	protected Table corner_table;
	protected Table side_table;

	// Pause Menu
	ArrayList<PowerDot> powDots;
	ArrayList<Label> powDots_time;
	ArrayList<Label> powDot_num;
	protected ScrollPane pause_scroll;

	protected Pool<MoveToAction> pool;

	public GameScreen() {
		super(null, false);
		createBackLabel();

		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();
		should_clear_stage = true;
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		this.batch = (SpriteBatch) stage.getBatch();
		this.batch.enableBlending();
		this.batch.setBlendFunction(GL20.GL_LINEAR_MIPMAP_NEAREST,
				GL20.GL_NEAREST);

		initializePointsTime();

		Label.LabelStyle overStyle = new Label.LabelStyle();
		overStyle.font = Assets.font_120;

		TextFieldStyle textStyle = new TextFieldStyle();
		textStyle.font = Assets.font_64;

		if (Assets.prefs.getString("bg_color", "").equals("")
				|| Assets.prefs.getString("bg_color", "").equals("White")) {
			overStyle.fontColor = Color.BLACK;
			textStyle.fontColor = Color.BLACK;
		} else {
			overStyle.fontColor = Color.WHITE;
			textStyle.fontColor = Color.WHITE;
		}

		gm = new Manager(0, w, h, stage); // Starts with 0 points
		// if (Assets.prefs.getString("mode").equals("Normal")) {
		gm.setMode(GameMode.NORMAL_MODE);
		gm.setScoreboard(Assets.game.main_menu.nsb);
		gamemode = new NormalGameMode(stage, gm, w, h);
		// } else {
		// gm.setMode(GameMode.CHALLENGE_MODE);
		// gm.setScoreboard(Assets.game.main_menu.csb);
		// gamemode = new ChallengeGameMode(stage, gm, w, h);
		// }

		pool = new Pool<MoveToAction>() {
			@Override
			protected MoveToAction newObject() {
				return new MoveToAction();
			}
		};

		initializePauseMenu();
		
		top_table = new Table(Assets.skin);
		top_table.setPosition(0, Assets.game_height);
		top_table.setWidth(Assets.width);
		top_table.setHeight(Assets.height - Assets.game_height);
		top_table.align(Align.left);
		top_table.add(pause).width(pause.getWidth()/5 * 4).height(pause.getWidth()/5 * 4).padLeft(pause.getWidth()/5);
		
		corner_table = new Table(Assets.skin);
		corner_table.setHeight(top_table.getHeight()/2 * 3);
		corner_table.setWidth(top_table.getHeight()/2 * 3);
		corner_table.setPosition(Assets.width - corner_table.getWidth(), Assets.height - corner_table.getHeight());
		
		Table temp = new Table(Assets.skin);
		temp.add(level);
		
		Table lvl_table = new Table(Assets.skin);
		lvl_table.setHeight(corner_table.getHeight());
		lvl_table.setWidth(corner_table.getWidth());
		Image lvl_bubble = new Image(Assets.lvl_bubble);
		lvl_table.stack(lvl_bubble, temp).height(corner_table.getHeight()/2).width(corner_table.getWidth()/2);
		lvl_table.top().right();
		
		temp = new Table(Assets.skin);
		temp.add(time);
		
		Table time_table = new Table(Assets.skin);
		time_table.setHeight(corner_table.getHeight());
		time_table.setWidth(corner_table.getWidth());
		Image time_bubble = new Image(Assets.time_bubble);
		time_table.stack(time_bubble, temp).height(corner_table.getHeight()/4 * 3).width(corner_table.getWidth()/4 * 3);
		time_table.bottom().left();
		
		corner_table.stack(time_table, lvl_table).height(corner_table.getHeight()).width(corner_table.getWidth());
		
		side_table = new Table(Assets.skin_window);
		side_table.setHeight(Assets.height - corner_table.getHeight());
		side_table.setWidth(pause.getWidth());
		side_table.setPosition(Assets.width - side_table.getWidth(), 0);
		
		boolean use_default_height = false;
		boolean should_check = true;
		for (int i = 0; i < powDots.size(); i++) {
			Dot powDot = powDots.get(i);
			Label powDotNum = powDot_num.get(i);
			
			float width, height;
			if(powDot.getWidth() > side_table.getWidth())
				width = side_table.getWidth()/5 * 4;
			else
				width = powDot.getWidth();
			
			if(should_check)
			{
				if(side_table.getHeight() / powDots.size() > width)
					use_default_height = true;
				should_check = false;
			}
			
			if(use_default_height)
				height = width;
			else
				height = powDot.getHeight();
			
			temp = new Table(Assets.skin);
			temp.add(powDot).width(side_table.getWidth()/5 * 4).height(side_table.getWidth()/5 * 4);
			temp.add(powDotNum).width(side_table.getWidth()/5).top();

			side_table.add(temp).width(side_table.getWidth())
					.height(height).left();
			if (i != powDots.size() - 1)
				side_table.row();
		}
//	}

//		slots = new Table(Assets.skin);
//		slots.left();
//		slots.setY(Assets.game_height);
//		slots.setX(time_end.getX() + time_end.getWidth());
//		slots.setWidth(pause.getX() - slots.getX());
//		slots.setHeight(Assets.height - Assets.game_height);

		// Image slot_switch = new Image(Assets.slot_switch);
		// float dot_width = (slots.getWidth())/3;
//		float dot_width = slots.getHeight();

//		Table scroll_table = new Table(Assets.skin);

		// float spacing = 0;
		// if(dot_width > slots.getHeight())
		// {
		// dot_width = slots.getHeight();
		// spacing = (slots.getWidth() - dot_width * 3)/3;
		// }

//		for (int i = 0; i < powDots.size(); i++) {
//			Table temp = new Table(Assets.skin);
//			temp.add(powDots_time.get(i));
//			scroll_table.stack(powDots.get(i), temp).width(dot_width)
//					.height(dot_width).center();
//			powDots.get(i).setTimeLabel(powDots_time.get(i));
//
//			Table temp2 = new Table(Assets.skin);
//			temp2.add(powDot_num.get(i));
//			scroll_table.add(temp2).width(
//					powDot_num.get(i).getStyle().font.getBounds("99").width);
//			powDots.get(i).setNumLabel(powDot_num.get(i));
//		}

//		final ScrollPane slots_scroll = new ScrollPane(scroll_table);
//		slots_scroll.setScrollingDisabled(true, true);
//		slots_scroll.setFadeScrollBars(false);

//		gm.setSlotWidth(slots.getWidth());
//		slots.add(slots_scroll).width(slots.getWidth());
		// slots.add(slot_switch).width(slot_switch.getWidth());

		addStageActors();
		curr_level = gamemode.gen_curr_level();
		gm.startGame();
	}

	private void initializePointsTime() {
//		level_title = new Label("LEVEL", Assets.style_font_32_red);
//		level_title
//				.setWidth(level_title.getStyle().font.getBounds("LEVEL").width);
//		level_title.setCenterPosition(
//				1 + level_title.getWidth() / 2,
//				Gdx.graphics.getHeight()
//						- Assets.style_font_32_white.font.getLineHeight() / 2);
//
		level = new Label("00", Assets.prefs.getString("bg_color").equals(
				"Black") ? Assets.style_font_32_white
				: Assets.style_font_32_black);
//		level.setPosition(0 + level_title.getWidth(), Gdx.graphics.getHeight()
//				- level.getStyle().font.getLineHeight());
//
//		time_title = new Label("WITH", Assets.style_font_32_red);
//		time_title.setWidth(time_title.getStyle().font.getBounds("WITH").width);
//		time_title.setCenterPosition(
//				1 + level_title.getWidth() + level.getWidth()
//						+ time_title.getWidth() / 2, level.getCenterY());
//
		time = new Label("60.0", Assets.prefs.getString("bg_color").equals(
				"Black") ? Assets.style_font_32_white
				: Assets.style_font_32_black);
//		time.setPosition(1 + level_title.getWidth() + level.getWidth()
//				+ time_title.getWidth(),
//				Gdx.graphics.getHeight() - time.getStyle().font.getLineHeight());
//
//		time_end = new Label("SECONDSLEFT", Assets.style_font_32_red);
//		time_end.setWidth(time_end.getStyle().font.getBounds("SECONDSLEFT").width);
//		time_end.setCenterPosition(
//				1 + level_title.getWidth() + level.getWidth()
//						+ time_title.getWidth() + time.getWidth()
//						+ time_end.getWidth() / 2, time.getCenterY());
//
//		points_title = new Label("Points: ", Assets.style_font_32_orange);
//		points_title.setPosition(0, Assets.game_height);
//		points_title.setWidth(points_title.getStyle().font
//				.getBounds("Points: ").width);
//
//		points = new Label(String.valueOf(Assets.points_manager
//				.getTotalPoints()), Assets.prefs.getString("bg_color").equals(
//				"Black") ? Assets.style_font_32_white
//				: Assets.style_font_32_black);
//		points.setPosition(0 + points_title.getWidth(), Assets.game_height);
	}

	public Manager getManager() {
		return gm;
	}

	private void replay() {
		Timer timer = new Timer();
		timer.scheduleTask(new Task() {
			@Override
			public void run() {
				GameScreen new_game = new GameScreen();
				Assets.game.main_menu.game_screen.dispose();
				Assets.game.main_menu.game_screen = new_game;
				Assets.game.setScreen(Assets.game.main_menu.game_screen);
			}
		}, 0.5f);
	}

	private void main_menu() {
		Assets.game.main_menu.game_screen.dispose();
		Assets.playMenuBGM();
		Assets.game.setScreen(Assets.game.main_menu);
	}

	private void initializePauseMenu() {
		LabelStyle buttonStyle = (Assets.prefs.getString("bg_color")
				.equals("Black")) ? Assets.style_font_64_white
				: Assets.style_font_64_black;

		pause = new Image(Assets.pause_dot);
		pause.setHeight(Assets.height - Assets.game_height);
		pause.setWidth(Assets.height - Assets.game_height);
		pause.setBounds(pause.getX(), pause.getY(), pause.getWidth(),
				pause.getHeight());
		pause.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (Assets.prefs.getBoolean("sound_effs"))
					Assets.button_pop.play();				
				if(gm.getGameState() == Manager.game_state.ONGOING)
				{
					gm.setGameState(Manager.game_state.PAUSED);
					gm.pauseGame();
					pause_scroll.scrollTo(0, Assets.game_height * 2,
					pause_scroll.getWidth(), pause_scroll.getHeight());
				}
				else if (gm.getGameState() == Manager.game_state.PAUSED)
				{
					gm.setGameState(Manager.game_state.ONGOING);
					new Timer().scheduleTask(new Task() {
						@Override
						public void run() {
							gm.startGame();
							pause.setTouchable(Touchable.enabled);
//							slots.setTouchable(Touchable.enabled);
//							for (int i = 0; i < powDots.size(); i++)
//								powDots.get(i).resumeTime();
						}
					}, 1);

					pause_scroll.scrollTo(0, 0, pause_scroll.getWidth(),
							pause_scroll.getHeight());
				}
			}
		});
		pause.setAlign(Align.left);
		
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
				Assets.game.main_menu.others_screen.tutorial_screen
						.setBackScreen(Assets.game.getScreen());
				Assets.game
						.setScreen(Assets.game.main_menu.others_screen.tutorial_screen);
			}
		});

		Label options = new Label("Options", buttonStyle);
		options.setBounds(options.getX(), options.getY(), options.getWidth(),
				options.getHeight());
		options.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Assets.game.main_menu.options_screen.setBackScreen(Assets.game
						.getScreen());
				Assets.game.setScreen(Assets.game.main_menu.options_screen);
			}
		});

		final Table pause_table = new Table(Assets.skin);
		pause_table.setWidth(Assets.width);
		pause_table.setHeight(Assets.game_height * 2);
		pause_table.left();
		Table dotTable = new Table(Assets.skin);
		dotTable.align(Align.right);

		addPowDots();
		addPowDotsNum();
		for(int i = 0; i < powDots.size(); i++)
			powDots.get(i).setNumLabel(powDot_num.get(i));

//		for (int i = 0; i < powDots.size(); i++) {
//			Dot powDot = powDots.get(i);
//			Label powDotNum = powDot_num.get(i);
//
//			dotTable.add(powDot).padLeft(back.getWidth() / 5)
//					.width(powDot.getWidth() / 3 * 2)
//					.height(Assets.game_height / powDots.size());
//			dotTable.add(powDotNum).padLeft(back.getWidth() / 5);
//			if (i != powDots.size() - 1)
//				dotTable.row();
//		}

		ArrayList<Actor> opts = new ArrayList<Actor>();
		opts.add(quit);
		opts.add(options);
		opts.add(tutorial);

		Table opTable = new Table(Assets.skin);
		opTable.right();
		for (int i = 0; i < opts.size(); i++) {
			Table temp = new Table(Assets.skin);
			temp.add(opts.get(i));
			opTable.add(temp).height(Assets.game_height / opts.size());
			if (i != opts.size() - 1)
				opTable.row();
		}

		pause_table.add().height(Assets.game_height * 2);
		pause_table.add(opTable).top();
//		pause_table.add(dotTable).expandX().right().top();
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
		powDots_time = new ArrayList<Label>();
		Label powDot_1_num = new Label("0", Assets.style_font_64_white);
		// powDot_1_num.setWidth(powDot_1_num.getStyle().font.getBounds("99").width);
		Label powDot_2_num = new Label("0", Assets.style_font_64_white);
		// powDot_2_num.setWidth(powDot_2_num.getStyle().font.getBounds("99").width);
		Label powDot_3_num = new Label("0", Assets.style_font_64_white);
		// powDot_3_num.setWidth(powDot_3_num.getStyle().font.getBounds("99").width);
		powDots_time.add(powDot_1_num);
		powDots_time.add(powDot_2_num);
		powDots_time.add(powDot_3_num);

		powDots = new ArrayList<PowerDot>();
		PowerDot powDot_1 = new PowerDot_Rainbow(Assets.rainbow_dot, gm,
				Assets.reg_pop_1);
		PowerDot powDot_2 = new PowerDot_Invincible(Assets.invincible_dot, gm,
				Assets.reg_pop_1);
		PowerDot powDot_3 = new PowerDot_Magnet(Assets.magnet_dot, gm,
				Assets.reg_pop_1);
		powDots.add(powDot_1);
		powDots.add(powDot_2);
		powDots.add(powDot_3);

		for (int i = 0; i < powDots.size(); i++) {
			powDots.get(i).setTimeLabel(powDots_time.get(i));
		}
	}

	private void addPowDotsNum() {
		LabelStyle dot_count_style = new LabelStyle();
		dot_count_style.font = Assets.font_16;
		dot_count_style.fontColor = Assets.prefs.getString("bg_color")
				.equals("Black") ? Color.WHITE : Color.BLACK;
		
		powDot_num = new ArrayList<Label>();
		Label powDot_1_num = new Label("x0", dot_count_style);
		// powDot_1_num.setWidth(powDot_1_num.getStyle().font.getBounds("99").width);
		Label powDot_2_num = new Label("x0", dot_count_style);
		powDot_2_num
				.setWidth(powDot_2_num.getStyle().font.getBounds("99").width);
		Label powDot_3_num = new Label("x0", dot_count_style);
		powDot_3_num
				.setWidth(powDot_3_num.getStyle().font.getBounds("99").width);
		powDot_num.add(powDot_1_num);
		powDot_num.add(powDot_2_num);
		powDot_num.add(powDot_3_num);
		
		for (int i = 0; i < powDots.size(); i++) {
			powDots.get(i).setNumLabel(powDot_num.get(i));
		}
	}

	public void setHighScoreName(String name) {
		// Gdx.input.setOnscreenKeyboardVisible(false);

		((ScrotsGame) Gdx.app.getApplicationListener()).main_menu.game_screen
				.getManager().addHighScore(name);
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
		message.makeGameOverWithHighScore(this, "Game Over!", "Replay", "Main Menu",
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

	@Override
	public void dispose() {
		// gamemode.dispose();
	}

	@Override
	public void render(float delta) {		
		if (Assets.prefs.getString("bg_color").equals("Black"))
			Gdx.gl.glClearColor(0, 0, 0, 0);
		else
			Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(Gdx.graphics.getDeltaTime());

		if (gm.isGameOver()) {
			gameOver();
		} else {
			level();
			time();
//			points();

			stage.draw();
			if (curr_level.level_clear()) {
				levelClear();
			}
		}
	}

	public void points() {
		points.setText(String.valueOf(Assets.points_manager.getTotalPoints()));
	}

	public void level() {
		String score = String.valueOf(gm.get_player_score());

		if (gm.get_player_score() < 10) {
			score = "0" + score;
		}
		level.setText(score);
	}

	public void time() {
		time.setText(gm.getTime().substring(0, 4));
	}

	public void gameOver() {
		Assets.stats_manager.writePlayerStatsToFile();

		if (should_clear_stage) {
			stage.clear();
			should_clear_stage = false;
			addStageActors();
			if (gm.get_player_score() > gm.getScoreBoard().getLowestHighScore()) {
				showGameOverWithHighScore();
			} else {
				showGameOver();
			}
		}

		stage.draw();
	}

	private void addStageActors() {
//		stage.addActor(pause);
//		stage.addActor(level_title);
//		stage.addActor(level);
//		stage.addActor(time);
//		stage.addActor(time_title);
//		stage.addActor(points_title);
//		stage.addActor(points);
//		stage.addActor(time_end);
		stage.addActor(pause_scroll);
//		stage.addActor(slots);
		stage.addActor(top_table);
		stage.addActor(corner_table);
		stage.addActor(side_table);
	}

	public void levelClear() {
		// One point for clearing a level
		stage.clear();
		addStageActors();
		gm.plusOnePoint();

		curr_level = gamemode.gen_curr_level();
		Assets.level_clear.play();
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {

	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void hide() {

	}
}
