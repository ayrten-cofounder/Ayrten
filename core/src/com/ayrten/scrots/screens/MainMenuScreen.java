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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

public class MainMenuScreen extends ScrotsScreen {
	protected OthersScreen othersScreen;
	protected ProfileScreen profileScreen;
	protected ScoresScreen highScoreScreen;
	protected ShopScreen shopScreen;
	protected OptionsScreen optionsScreen;
	protected ContactScreen contactScreen;
	public GameModeScreen gameModeScreen;

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

		contactScreen = new ContactScreen(this);
		highScoreScreen = new ScoresScreen(this);
		optionsScreen = new OptionsScreen(this);
		othersScreen = new OthersScreen(this);
		profileScreen = new ProfileScreen(this);
		shopScreen = new ShopScreen(this);
		gameModeScreen = new GameModeScreen(this);

		LabelStyle title_style = new LabelStyle();
		title_style.font = Assets.font_200;
		title_style.fontColor = Color.valueOf("0099cc");
//		title_style.fontColor = Color.GRAY;
		
		LabelStyle title_style_border = new LabelStyle();
		title_style_border.font = Assets.font_200_border;
		title_style_border.fontColor = Color.BLACK;
		
		Label t1 = new Label("S", title_style);
		Label t2 = new Label("c", title_style);
		Label t3 = new Label("r", title_style);
		Label t4 = new Label("o", title_style);
		Label t5 = new Label("t", title_style);
		Label t6 = new Label("s", title_style);
		
		Label t1b = new Label("S", title_style_border);
		Label t2b = new Label("c", title_style_border);
		Label t3b = new Label("r", title_style_border);
		Label t4b = new Label("o", title_style_border);
		Label t5b = new Label("t", title_style_border);
		Label t6b = new Label("s", title_style_border);
		
		t1.setAlignment(Align.center);
		t2.setAlignment(Align.center);
		t3.setAlignment(Align.center);
		t4.setAlignment(Align.center);
		t5.setAlignment(Align.center);
		t6.setAlignment(Align.center);
		
		t1b.setAlignment(Align.center);
		t2b.setAlignment(Align.center);
		t3b.setAlignment(Align.center);
		t4b.setAlignment(Align.center);
		t5b.setAlignment(Align.center);
		t6b.setAlignment(Align.center);
		
		Table title = new Table(Assets.skin);
		title.setPosition(Assets.width / 2, Assets.height / 3 * 2,
				Align.center);
		title.stack(t1b, t1);
		title.stack(t2b, t2);
		title.stack(t3b, t3);
		title.stack(t4b, t4);
		title.stack(t5b, t5);
		title.stack(t6b, t6);
		
//		Label scrots = new Label("Scrots", title_style);
//		scrots.setPosition(Assets.width / 2, Assets.height / 3 * 2,
//				Align.center);
//		scrots.setAlignment(Align.center);

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
				Assets.game.setScreen(shopScreen);
			}
		});
		shop.setAlignment(Align.center);

		play = new Label("Play", style);
		play.setBounds(play.getX(), play.getY(), play.getWidth(),
				play.getHeight());
		play.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				Assets.game.setScreen(gameModeScreen);
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
				Assets.game.setScreen(highScoreScreen);
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
				optionsScreen.setBackScreen(Assets.game.getScreen());
				Assets.game.setScreen(optionsScreen);
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
				Assets.game.setScreen(contactScreen);
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
				Assets.game.setScreen(othersScreen);
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
		upperTable.add(title);

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
		gm.addDotsToStage();
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
	public void executeBackAction() {
		MessageScreen message = new MessageScreen(stage);
		message.makeWindow("Do you want to exit the game?", "I want to keep playing!", "I am done for today...", new ButtonInterface() {
			@Override
			public void buttonPressed() {
				backStage = true;
			}
		}, new ButtonInterface() {
			@Override
			public void buttonPressed() {
				Gdx.app.exit();
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