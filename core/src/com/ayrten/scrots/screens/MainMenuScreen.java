package com.ayrten.scrots.screens;

import java.util.ArrayList;

import com.ayrten.scrots.game.GameMode;
import com.ayrten.scrots.manager.Assets;
import com.ayrten.scrots.manager.ButtonInterface;
import com.ayrten.scrots.manager.Manager;
import com.ayrten.scrots.scoreboard.NormalScoreboard;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

public class MainMenuScreen extends ScrotsScreen {
	protected GameScreen game_screen;
	protected OthersScreen others_screen;
	protected ProfileScreen profile_screen;
	protected ScoresScreen high_score_screen;
	protected ShopScreen shop_screen;
	protected OptionsScreen options_screen;
	protected ContactScreen contact_screen;

	protected Label shop;
	protected Label play;
	protected Label scores;
	protected Label options;
	protected Label contacts;
	protected Label others;
	protected Label gplay_log;

	public NormalScoreboard nsb;

	// public ChallengeScoreboard csb;

	public MainMenuScreen() {
		super(null, false);

		// Initialize variables
		nsb = new NormalScoreboard();
		// csb = new ChallengeScoreboard();

		contact_screen = new ContactScreen(this);
		high_score_screen = new ScoresScreen(this);
		options_screen = new OptionsScreen(this);
		others_screen = new OthersScreen(this);
		profile_screen = new ProfileScreen(this);
		shop_screen = new ShopScreen(this);

		LabelStyle title_style = new LabelStyle();
		title_style.font = Assets.font_200;
		title_style.fontColor = Color.valueOf("0099cc");
		title_style.fontColor = Color.GRAY;
		Label scrots = new Label("Scrots", title_style);
		scrots.setPosition(Assets.width / 2, Assets.height / 3 * 2,
				Align.center);
		scrots.setAlignment(Align.center);

		LabelStyle style = new LabelStyle();
		style.font = Assets.font_64;
		style.fontColor = Color.WHITE;

		int left = (int) ((int) Assets.width * 0.02);
		int right = left;
		int top = (int) ((int) Assets.height * 0.02);
		int bottom = top;

		NinePatchDrawable rounded_rectangle_pale_orange = new NinePatchDrawable(
				new NinePatch(new Texture(Gdx.files
						.internal("data/rounded_rectangle_pale_orange.png")),
						left, right, top, bottom));
		style.background = rounded_rectangle_pale_orange;

		shop = new Label("Shop", style);
		shop.setBounds(shop.getX(), shop.getY(), shop.getWidth(),
				shop.getHeight());
		shop.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Assets.game.setScreen(shop_screen);
			}
		});
		shop.setAlignment(Align.center);

		play = new Label("Play", style);
		play.setBounds(play.getX(), play.getY(), play.getWidth(),
				play.getHeight());
		play.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				if (Assets.prefs.getBoolean("sound_effs", true))
					Assets.button_pop.play();
				if (Assets.prefs.getBoolean("first_time", true))
					loadTutorialScreen();
				else {
					game_screen = new GameScreen();
					Assets.playGameBGM();
					Assets.game.setScreen(game_screen);
				}
			}
		});
		play.setAlignment(Align.center);

		scores = new Label("Scores", style);
		scores.setBounds(scores.getX(), scores.getY(), scores.getWidth(),
				scores.getHeight());
		scores.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				if (Assets.prefs.getBoolean("sound_effs", true))
					Assets.button_pop.play();
				Assets.game.setScreen(high_score_screen);
			}
		});
		scores.setAlignment(Align.center);

		options = new Label("Options", style);
		options.setBounds(options.getX(), options.getY(), options.getWidth(),
				options.getHeight());
		options.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				setActorsTouchable(Touchable.disabled);
				if (Assets.prefs.getBoolean("sound_effs", true))
					Assets.button_pop.play();
				options_screen.setBackScreen(Assets.game.getScreen());
				Assets.game.setScreen(options_screen);
			}
		});
		options.setAlignment(Align.center);

		contacts = new Label("Contacts", style);
		contacts.setBounds(contacts.getX(), contacts.getY(),
				contacts.getWidth(), contacts.getHeight());
		contacts.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				setActorsTouchable(Touchable.disabled);
				if (Assets.prefs.getBoolean("sound_effs", true))
					Assets.button_pop.play();
				Assets.game.setScreen(contact_screen);
			}
		});
		contacts.setAlignment(Align.center);

		others = new Label("Others", style);
		others.setBounds(others.getX(), others.getY(), others.getWidth(),
				others.getHeight());
		others.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				if (Assets.prefs.getBoolean("sound_effs", true))
					Assets.button_pop.play();
				Assets.game.setScreen(others_screen);
			}
		});
		others.setAlignment(Align.center);

		LabelStyle gplay_style = new LabelStyle();
		gplay_style.font = Assets.font_64;
		gplay_style.fontColor = Color.WHITE;

		gplay_log = new Label("Sign in", gplay_style);
		gplay_log.setWidth(gplay_log.getStyle().font.getBounds("Logout").width);
		gplay_log.setBounds(gplay_log.getX(), gplay_log.getY(),
				gplay_log.getWidth(), gplay_log.getHeight());
		gplay_log.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				if (Assets.game.apk_intf.is_gplay_signedin())
					Assets.game.apk_intf.gplay_logout();
				else
					Assets.game.apk_intf.gplay_signin();
			}
		});
		gplay_log.setAlignment(Align.left);

		setupStage();

		Table upperTable = new Table(Assets.skin);
		upperTable.add(scrots);

		ArrayList<Actor> lowerTableActors = new ArrayList<Actor>();
		lowerTableActors.add(shop);
		lowerTableActors.add(play);
		lowerTableActors.add(scores);
		float cell_width = Assets.width / lowerTableActors.size();

		Table lowerTable = new Table(Assets.skin);
		for (int i = 0; i < lowerTableActors.size(); i++) {
			// Reason for adding another layer of table is alignment correct and
			// bounds correction of touch listener.
			Table temp = new Table(Assets.skin);
			temp.add(lowerTableActors.get(i)).width(
					cell_width - style.font.getSpaceWidth() * 2);
			lowerTable.add(temp).width(cell_width).height(Assets.height / 5);
		}

		lowerTable.row().padBottom(style.font.getSpaceWidth());
		lowerTableActors.clear();
		lowerTableActors.add(options);
		lowerTableActors.add(contacts);
		lowerTableActors.add(others);
		for (int i = 0; i < lowerTableActors.size(); i++) {
			Table temp = new Table(Assets.skin);
			temp.add(lowerTableActors.get(i)).width(
					cell_width - style.font.getSpaceWidth() * 2);
			lowerTable.add(temp).width(cell_width).height(Assets.height / 5);
		}

		initializeNaviBar();
		addToNavBar(gplay_log);
		stage.addActor(navigation_bar);

		Table main_table = new Table(Assets.skin);
		main_table.setFillParent(true);
		main_table.add(upperTable).height(Assets.height / 5 * 3);
		main_table.row();
		main_table.add(lowerTable).height(Assets.height / 5 * 2)
				.width(Assets.width);

		// For some reason, you can't add the dots first...
		Manager gm = new Manager(0, 0, Assets.width, 0, navigation_bar.getY(),
				stage);
		GameMode mainMenuMode = new GameMode(stage, gm);
		mainMenuMode.gen_start_level(15, Touchable.disabled);
		gm.changeDotSize();
		stage.addActor(main_table);
	}

	@Override
	public void addActors() {
		super.addActors();
		actors.add(shop);
		actors.add(play);
		actors.add(scores);
		actors.add(options);
		actors.add(contacts);
		actors.add(others);
		actors.add(gplay_log);
	}
	
	public void checkRateMe(){
		int rateMeCount = Assets.prefs.getInteger("rateMeCount", 0);
		if (rateMeCount == 5) {
			showRateMe();
		}
		rateMeCount = rateMeCount + 1;
		Assets.prefs.putInteger("rateMeCount", rateMeCount);
		Assets.prefs.flush();
	}

	private void loadTutorialScreen() {
		Image tutorial_1 = new Image(new Texture(
				Gdx.files.internal("data/tutorial_1.png")));
		Image tutorial_2 = new Image(new Texture(
				Gdx.files.internal("data/tutorial_2.png")));

		Table top_table = new Table(Assets.skin);
		top_table.setWidth(Assets.width * 2);

		Table page_one = new Table(Assets.skin);
		page_one.setHeight(MessageScreen.WINDOW_DISPLAY_HEIGHT);
		page_one.setWidth(Assets.width);
		page_one.add(tutorial_1).width(Assets.width)
				.height(MessageScreen.WINDOW_DISPLAY_HEIGHT);

		Table page_two = new Table(Assets.skin);
		page_two.setHeight(MessageScreen.WINDOW_DISPLAY_HEIGHT);
		page_two.setWidth(Assets.width);
		page_two.add(tutorial_2).width(Assets.width)
				.height(MessageScreen.WINDOW_DISPLAY_HEIGHT);

		top_table.add(page_one).width(Assets.width)
				.height(MessageScreen.WINDOW_DISPLAY_HEIGHT);
		top_table.add(page_two).width(Assets.width)
				.height(MessageScreen.WINDOW_DISPLAY_HEIGHT);

		// Use the slideshow type MessageScreen.
		MessageScreen tutorial_screen = new MessageScreen(top_table, 2) {
			@Override
			public void transition() {
				Assets.prefs.putBoolean("first_time", false);
				Assets.prefs.flush();
				game_screen = new GameScreen();
				Assets.playGameBGM();
				Assets.game.setScreen(game_screen);
			}
		};

		Assets.game.setScreen(tutorial_screen);
	}

	public void showRateMe() {
		MessageScreen message = new MessageScreen(stage);
		message.makeWindow(
				"It seems like you've been playing Scrots a bit, will you please rate us? (You will not be asked again)",
				"Yes", "No", new ButtonInterface() {
					@Override
					public void buttonPressed() {
						Assets.game.apk_intf.rateMe();
					}
				}, new ButtonInterface() {
					@Override
					public void buttonPressed() {

					}
				});
	}

	@Override
	public void show() {
		super.show();
		Assets.game.apk_intf.setGPlayButton(gplay_log);
		gplay_log.setText((Assets.game.apk_intf.is_gplay_signedin()) ? "Logout"
				: "Sign in");
	}
}