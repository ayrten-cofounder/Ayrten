package com.ayrten.scrots.screens;

import java.util.ArrayList;

import com.ayrten.scrots.game.ChallengeGameMode;
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
import com.badlogic.gdx.utils.Timer.Task;
import com.badlogic.gdx.utils.Timer;

public class GameScreen extends ScrotsScreen {
	// Widgets
	protected Label pause;

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
	ArrayList<Image> powDot_images;
	ArrayList<Label> powDot_num;
	protected Table table;

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

		LabelStyle buttonStyle = new LabelStyle();
		buttonStyle.font = Assets.font_64;

		if (Assets.prefs.getString("bg_color", "").equals("")
				|| Assets.prefs.getString("bg_color", "").equals("White")) {
			overStyle.fontColor = Color.BLACK;
			textStyle.fontColor = Color.BLACK;
			buttonStyle.fontColor = Color.BLACK;
		} else {
			overStyle.fontColor = Color.WHITE;
			textStyle.fontColor = Color.WHITE;
			buttonStyle.fontColor = Color.WHITE;
		}

		gm = new Manager(0, w, h, stage); // Starts with 0 points
//		if (Assets.prefs.getString("mode").equals("Normal")) {
			gm.setMode(GameMode.NORMAL_MODE);
			gm.setScoreboard(Assets.game.main_menu.nsb);
			gamemode = new NormalGameMode(stage, gm, w, h);
//		} else {
//			gm.setMode(GameMode.CHALLENGE_MODE);
//			gm.setScoreboard(Assets.game.main_menu.csb);
//			gamemode = new ChallengeGameMode(stage, gm, w, h);
//		}

		pool = new Pool<MoveToAction>() {
			@Override
			protected MoveToAction newObject() {
				return new MoveToAction();
			}
		};
		
		initializePauseMenu(buttonStyle);

		slots = new Table(Assets.skin);
		slots.left();
		slots.setY(Assets.game_height);
		slots.setX(time_end.getX() + time_end.getWidth());
		slots.setWidth(pause.getX() - slots.getX());
		slots.setHeight(Assets.height - Assets.game_height);

		Image slot_switch = new Image(Assets.slot_switch);
	  	float dot_width = (slots.getWidth() - slot_switch.getWidth())/3;
	  	System.out.println("DOT WIDTH: " + dot_width);
	  	System.out.println("SLOTS HEIGHT: " + slots.getHeight());
	  	
		Table scroll_table = new Table(Assets.skin);
		float spacing = 0;
		if(dot_width > slots.getHeight())
		{
			dot_width = slots.getHeight();
			spacing = (slots.getWidth() - slot_switch.getWidth() - dot_width * 3)/3;
		}

	  	for(int i = 0; i < powDot_images.size(); i++) 
	  	{
	  		Table temp = new Table(Assets.skin);
	  		temp.add(powDot_num.get(i));
	  		scroll_table.stack(powDot_images.get(i), temp).width(dot_width).height(dot_width).center();
	  		if(spacing != 0 && i != powDot_images.size() - 1)
	  			scroll_table.add().width(spacing);
	  	}
	  	
	  	for(int i = 0; i < 9 - powDot_images.size(); i ++)
	  	{
	  		scroll_table.add().width(dot_width);
	  		if(spacing != 0 && i != 9 - powDot_images.size() - 1)
	  			scroll_table.add().width(spacing);
	  	}
	  	
	  	final ScrollPane slots_scroll = new ScrollPane(scroll_table);
	  	slots_scroll.setScrollingDisabled(false, true);
	  	slots_scroll.setFadeScrollBars(false);
	  	slots_scroll.setTouchable(Touchable.disabled);
	  	slot_switch.addListener(new ClickListener(){
	  		@Override
	  		public void clicked(InputEvent event, float x, float y) {
	  			gm.nextSlot();
	  			slots_scroll.scrollTo(gm.getCurrentSlot() * gm.getSlotWidth(), 0, gm.getSlotWidth(), slots_scroll.getHeight());
	  		}
	  	});
	  	
	  	gm.setSlotWidth(slots.getWidth() - slot_switch.getWidth());
		slots.add(slots_scroll).width(slots.getWidth() - slot_switch.getWidth());
		slots.add(slot_switch).width(slot_switch.getWidth());

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
						- Assets.style_font_64_white.font.getLineHeight() / 2);

		points = new Label("00", Assets.prefs.getString("bg_color").equals(
				"Black") ? Assets.style_font_64_white
				: Assets.style_font_64_black);
		// points = new Label("00", Assets.style_font_64_blue);
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
				"Black") ? Assets.style_font_64_white
				: Assets.style_font_64_black);
		// time = new Label("60.0", Assets.style_font_64_blue);
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
	
	private void initializePauseMenu(LabelStyle buttonStyle)
	{
		pause = new Label("Menu", buttonStyle);
		pause.setBounds(pause.getX(), pause.getY(), pause.getWidth(),
				pause.getHeight());
		pause.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (Assets.prefs.getBoolean("sound_effs"))
					Assets.button_pop.play();
				gm.pauseGame();
				table.setVisible(true);
			}
		});
		pause.setWidth(buttonStyle.font.getBounds("  Menu").width);
		pause.setPosition(w - pause.getWidth(), Gdx.graphics.getHeight()
				- pause.getStyle().font.getLineHeight());

		TextButton pause_quit = new TextButton("", Assets.skin);		
		pause_quit.add(new Label("Quit", buttonStyle));
		pause_quit.setBounds(pause_quit.getX(), pause_quit.getY(),
				pause_quit.getWidth(), pause_quit.getHeight());
		
		Label resume = new Label("Resume", buttonStyle);
		resume.setBounds(resume.getX(), resume.getY(), resume.getWidth(), resume.getHeight());
		resume.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				gm.startGame();
				table.setVisible(false);
			}
		});
		
		Label quit = new Label("Quit", buttonStyle);
		quit.setBounds(quit.getX(), quit.getY(), quit.getWidth(), quit.getHeight());
		quit.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				showQuitConfirm();
			}
		});
		
		Label tutorial = new Label("Tutorial", buttonStyle);
		tutorial.setBounds(tutorial.getX(), tutorial.getY(), tutorial.getWidth(), tutorial.getHeight());
		tutorial.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Assets.game.main_menu.others_screen.tutorial_screen.setBackScreen(Assets.game.getScreen());
				Assets.game.setScreen(Assets.game.main_menu.others_screen.tutorial_screen);
			}
		});
		
		Label options = new Label("Options", buttonStyle);
		options.setBounds(options.getX(), options.getY(), options.getWidth(), options.getHeight());
		options.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Assets.game.main_menu.others_screen.options_screen.setBackScreen(Assets.game.getScreen());
				Assets.game.setScreen(Assets.game.main_menu.others_screen.options_screen);
			}
		});
		
		table = new Table(Assets.skin);
		table.setWidth(Assets.width);
		table.setHeight(Assets.game_height);
		table.left();
		Table dotTable = new Table(Assets.skin);
		dotTable.align(Align.right);
		
		addPowDots();
		addPowDotsNum();
		
		for(int i = 0; i < powDot_images.size(); i++)
		{
			Image powDot = powDot_images.get(i);
			Label powDotNum = powDot_num.get(i);
			
			
			dotTable.add(powDot).padLeft(back.getWidth()/5).width(powDot.getWidth()/3 * 2).height(powDot.getHeight()/3 * 2);
			dotTable.add(powDotNum).padLeft(back.getWidth()/5);
			if(i != powDot_images.size() - 1)
			{
				dotTable.row();
				dotTable.add().height(back.getStyle().font.getLineHeight()/2);
				dotTable.row();			
			}
		}
		
		Table opTable = new Table(Assets.skin);
		opTable.right();
		opTable.add(resume).expandX();
		opTable.row();
		opTable.add().height(back.getStyle().font.getLineHeight()/2);
		opTable.row();
		opTable.add(quit);
		opTable.row();
		opTable.add().height(back.getStyle().font.getLineHeight()/2);
		opTable.row();
		opTable.add(options);
		opTable.row();
		opTable.add().height(back.getStyle().font.getLineHeight()/2);
		opTable.row();
		opTable.add(tutorial);
		
		table.top();
		table.add(dotTable);
		table.add(opTable).expandX().right();
		table.setVisible(false);
	}
	
	private void addPowDots()
	{
		powDot_images = new ArrayList<Image>();
		Image powDot_1 = Assets.powDot1_image;
		Image powDot_2 = Assets.powDot2_image;
		Image powDot_3 = Assets.powDot3_image;
		powDot_images.add(powDot_1);
		powDot_images.add(powDot_2);
		powDot_images.add(powDot_3);
	}
	
	private void addPowDotsNum()
	{
		powDot_num = new ArrayList<Label>();
		Label powDot_1_num = new Label("0", Assets.prefs.getString("bg_color").equals(	
				"Black") ? Assets.style_font_64_white
				: Assets.style_font_64_black);
		// powDot_1_num.setWidth(powDot_1_num.getStyle().font.getBounds("99").width);
		Label powDot_2_num = new Label("0", Assets.prefs.getString("bg_color").equals(	
				"Black") ? Assets.style_font_64_white
				: Assets.style_font_64_black);
		powDot_2_num.setWidth(powDot_2_num.getStyle().font.getBounds("99").width);
		Label powDot_3_num = new Label("0", Assets.prefs.getString("bg_color").equals(	
				"Black") ? Assets.style_font_64_white
				: Assets.style_font_64_black);
		powDot_3_num.setWidth(powDot_3_num.getStyle().font.getBounds("99").width);
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
				}, null, Assets.prefs.getString("bg_color").equals("Black") ? 0 : 1);
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
		stage.addActor(table);
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
