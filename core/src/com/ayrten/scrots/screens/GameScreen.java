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
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class GameScreen extends ScrotsScreen {
	// Widgets
	protected Image pause;

	protected Label points_title;
	protected Label points;
	protected Label time_title;
	protected Label time;
	protected Label time_end;

	protected GameMode gamemode;
	protected Manager gm;
	protected Stage stage;

	protected int w;
	protected int h;

	protected Level curr_level;
	protected SpriteBatch batch;
	protected boolean should_clear_stage;
	protected ArrayList<Level> all_levels = new ArrayList<Level>();

	protected Table slots;

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

		slots = new Table(Assets.skin);
		slots.left();
		slots.setY(Assets.game_height);
		slots.setX(time_end.getX() + time_end.getWidth());
		slots.setWidth(pause.getX() - slots.getX());
		slots.setHeight(Assets.height - Assets.game_height);

		// Image slot_switch = new Image(Assets.slot_switch);
		// float dot_width = (slots.getWidth())/3;
		float dot_width = slots.getHeight();

		Table scroll_table = new Table(Assets.skin);

		// float spacing = 0;
		// if(dot_width > slots.getHeight())
		// {
		// dot_width = slots.getHeight();
		// spacing = (slots.getWidth() - dot_width * 3)/3;
		// }
		
		for (int i = 0; i < powDots.size(); i++) {
			Table temp = new Table(Assets.skin);
			temp.add(powDots_time.get(i));
			scroll_table.stack(powDots.get(i), temp).width(dot_width)
					.height(dot_width).center();
			powDots.get(i).setTimeLabel(powDots_time.get(i));
			
			Table temp2 = new Table(Assets.skin);
			temp2.add(powDot_num.get(i));
			scroll_table.add(temp2).width(powDot_num.get(i).getStyle().font.getBounds("99").width);;
			powDots.get(i).setNumLabel(powDot_num.get(i));

			// if(spacing != 0 && i != powDot_images.size() - 1)
			// scroll_table.add().width(spacing);
		}

		// for(int i = 0; i < 9 - powDot_images.size(); i ++)
		// {
		// scroll_table.add().width(dot_width);
		// if(spacing != 0 && i != 9 - powDot_images.size() - 1)
		// scroll_table.add().width(spacing);
		// }

		final ScrollPane slots_scroll = new ScrollPane(scroll_table);
		slots_scroll.setScrollingDisabled(true, true);
		slots_scroll.setFadeScrollBars(false);
		// slots_scroll.setTouchable(Touchable.disabled);
		// slot_switch.addListener(new ClickListener(){
		// @Override
		// public void clicked(InputEvent event, float x, float y) {
		// gm.nextSlot();
		// slots_scroll.scrollTo(gm.getCurrentSlot() * gm.getSlotWidth(), 0,
		// gm.getSlotWidth(), slots_scroll.getHeight());
		// }
		// });

		gm.setSlotWidth(slots.getWidth());
		slots.add(slots_scroll).width(slots.getWidth());
		// slots.add(slot_switch).width(slot_switch.getWidth());

		addStageActors();
		curr_level = gamemode.gen_curr_level();
		gm.startGame();
	}

	private void initializePointsTime() {
		points_title = new Label("LEVEL", Assets.style_font_32_red);
		points_title
				.setWidth(points_title.getStyle().font.getBounds("LEVEL").width);
		points_title.setCenterPosition(
				1 + points_title.getWidth() / 2,
				Gdx.graphics.getHeight()
						- Assets.style_font_32_white.font.getLineHeight() / 2);

		points = new Label("00", Assets.prefs.getString("bg_color").equals(
				"Black") ? Assets.style_font_32_white
				: Assets.style_font_32_black);
		points.setPosition(
				0 + points_title.getWidth(),
				Gdx.graphics.getHeight()
						- points.getStyle().font.getLineHeight());

		time_title = new Label("WITH", Assets.style_font_32_red);
		time_title.setWidth(time_title.getStyle().font.getBounds("WITH").width);
		time_title.setCenterPosition(
				1 + points_title.getWidth() + points.getWidth()
						+ time_title.getWidth() / 2, points.getCenterY());

		time = new Label("60.0", Assets.prefs.getString("bg_color").equals(
				"Black") ? Assets.style_font_32_white
				: Assets.style_font_32_black);
		time.setPosition(1 + points_title.getWidth() + points.getWidth()
				+ time_title.getWidth(),
				Gdx.graphics.getHeight() - time.getStyle().font.getLineHeight());

		time_end = new Label("SECONDSLEFT", Assets.style_font_32_red);
		time_end.setWidth(time_end.getStyle().font.getBounds("SECONDSLEFT").width);
		time_end.setCenterPosition(
				1 + points_title.getWidth() + points.getWidth()
						+ time_title.getWidth() + time.getWidth()
						+ time_end.getWidth() / 2, time.getCenterY());
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
	
	private void initializePauseMenu()
	{
		LabelStyle buttonStyle = (Assets.prefs.getString("bg_color").equals("Black")) ? Assets.style_font_64_white : Assets.style_font_64_black;
		
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
				gm.pauseGame();
				pause_scroll.scrollTo(0, Assets.game_height * 2,
						pause_scroll.getWidth(), pause_scroll.getHeight());
				slots.setTouchable(Touchable.disabled);
				for(int i = 0; i < powDots.size(); i++)
						powDots.get(i).pauseTime();
			}
		});
		pause.setPosition(w - pause.getWidth(), Gdx.graphics.getHeight()
				- pause.getHeight());

		TextButton pause_quit = new TextButton("", Assets.skin);
		pause_quit.add(new Label("Quit", buttonStyle));
		pause_quit.setBounds(pause_quit.getX(), pause_quit.getY(),
				pause_quit.getWidth(), pause_quit.getHeight());

		Label resume = new Label("Resume", buttonStyle);
		resume.setBounds(resume.getX(), resume.getY(), resume.getWidth(),
				resume.getHeight());
		resume.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				gm.startGame();
				pause_scroll.scrollTo(0, 0, pause_scroll.getWidth(),
						pause_scroll.getHeight());
				slots.setTouchable(Touchable.enabled);
				for(int i = 0; i < powDots.size(); i++)
						powDots.get(i).resumeTime();
			}
		});

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

		Table pause_table = new Table(Assets.skin);
		pause_table.setWidth(Assets.width);
		pause_table.setHeight(Assets.game_height);
		pause_table.left();
		Table dotTable = new Table(Assets.skin);
		dotTable.align(Align.right);

		addPowDots();
		addPowDotsNum();

		for (int i = 0; i < powDots.size(); i++) {
			Dot powDot = powDots.get(i);
			Label powDotNum = powDot_num.get(i);

			dotTable.add(powDot).padLeft(back.getWidth() / 5)
					.width(powDot.getWidth() / 3 * 2)
					.height(Assets.game_height / powDots.size());
			dotTable.add(powDotNum).padLeft(back.getWidth() / 5);
			if (i != powDots.size() - 1)
				dotTable.row();
		}

		ArrayList<Actor> opts = new ArrayList<Actor>();
		opts.add(resume);
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
		pause_table.add(dotTable).top();
		pause_table.add(opTable).expandX().right().top();

		pause_scroll = new ScrollPane(pause_table);
		pause_scroll.setPosition(0, 0);
		pause_scroll.setWidth(Assets.width);
		pause_scroll.setHeight(Assets.game_height);
		pause_scroll.scrollTo(0, 0, pause_scroll.getWidth(),
				pause_scroll.getHeight());
		// pause_scroll.addAction(Actions.run(new Runnable() {
		// @Override
		// public void run() {
		// pause_scroll.scrollTo(0, 0, pause_scroll.getWidth(),
		// pause_scroll.getHeight());
		// }
		// }));
		// pause_scroll.setScrollingDisabled(true, true);
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
		powDot_num = new ArrayList<Label>();
		Label powDot_1_num = new Label("0", Assets.prefs.getString("bg_color")
				.equals("Black") ? Assets.style_font_64_white
				: Assets.style_font_64_black);
		// powDot_1_num.setWidth(powDot_1_num.getStyle().font.getBounds("99").width);
		Label powDot_2_num = new Label("0", Assets.prefs.getString("bg_color")
				.equals("Black") ? Assets.style_font_64_white
				: Assets.style_font_64_black);
		powDot_2_num
				.setWidth(powDot_2_num.getStyle().font.getBounds("99").width);
		Label powDot_3_num = new Label("0", Assets.prefs.getString("bg_color")
				.equals("Black") ? Assets.style_font_64_white
				: Assets.style_font_64_black);
		powDot_3_num
				.setWidth(powDot_3_num.getStyle().font.getBounds("99").width);
		powDot_num.add(powDot_1_num);
		powDot_num.add(powDot_2_num);
		powDot_num.add(powDot_3_num);
	}

	public void setHighScoreName(String name) {
		// Gdx.input.setOnscreenKeyboardVisible(false);

		((ScrotsGame) Gdx.app.getApplicationListener()).main_menu.game_screen
				.getManager().addHighScore(name);
	}

	public void showQuitHighScoreMenu() {
		Assets.game.apk_intf.makeWindow(
				"Are you sure you don't want to enter your highscore?", "Yes",
				"Cancel", new ButtonInterface() {

					@Override
					public void buttonPressed() {
						showQuitConfirm();
					}
				}, new ButtonInterface() {

					@Override
					public void buttonPressed() {
					}
				}, Assets.prefs.getString("bg_color").equals("Black") ? 0 : 1);
	}

	public void showQuitConfirm() {
		Assets.game.apk_intf.makeWindow("Quit?", "Yes", "No",
				new ButtonInterface() {

					@Override
					public void buttonPressed() {
						main_menu();
					}
				}, null, Assets.prefs.getString("bg_color").equals("Black") ? 0
						: 1);
	}

	public void showGameOver() {
		Assets.game.apk_intf.makeGameOverDialog(new ButtonInterface() {

			@Override
			public void buttonPressed() {
				replay();
			}
		}, new ButtonInterface() {

			@Override
			public void buttonPressed() {
				main_menu();
			}
		}, Assets.prefs.getString("bg_color").equals("Black") ? 0 : 1);
	}

	public void showGameOverWithHighScore() {
		Assets.game.apk_intf.makeGameOverDialogHighScore(this,
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
				}, Assets.prefs.getString("bg_color").equals("Black") ? 0 : 1);
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
			point();
			time();

			stage.draw();
			if (curr_level.level_clear()) {
				levelClear();
			}
		}
	}

	public void point() {
		String score = String.valueOf(gm.get_player_score());

		if (gm.get_player_score() < 10) {
			score = "0" + score;
		}
		points.setText(score);
	}

	public void time() {
		time.setText(gm.getTime().substring(0, 4));
	}

	public void gameOver() {
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
		stage.addActor(pause);
		stage.addActor(points_title);
		stage.addActor(points);
		stage.addActor(time);
		stage.addActor(time_title);
		stage.addActor(time_end);
		stage.addActor(pause_scroll);
		stage.addActor(slots);
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
