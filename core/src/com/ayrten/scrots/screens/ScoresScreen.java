package com.ayrten.scrots.screens;

import com.ayrten.scrots.manager.Assets;
import com.ayrten.scrots.manager.ButtonInterface;
import com.ayrten.scrots.scoreboard.NormalScoreboard;
import com.ayrten.scrots.scoreboard.Scoreboard;
import com.ayrten.scrots.scoreboard.Scoreboard.Scores;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class ScoresScreen extends ScrotsScreen {
	private float pad_left = (float) 5.5;
	private float pad_right = (float) 5.5;
	private int height = 75;

	//	private SelectBox<String> mode;
	protected Label clear;

	protected LabelStyle style_big;
	protected LabelStyle style_small;


	public ScoresScreen(Screen bscreen) {
		super(bscreen, true);

		setupStage();
		showTableScreen();

		style_big = new LabelStyle();
		style_big.font = Assets.font_64;
		style_big.fontColor = Assets.ORANGE;

		style_small = new LabelStyle();
		style_small.font = Assets.font_64;
		style_small.fontColor = Assets.ORANGE;

		// switchFontColor();

		clear = new Label("Clear", style_small);
		clear.setBounds(clear.getX(), clear.getY(), clear.getWidth(),
				clear.getHeight());
		clear.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				if (Assets.prefs.getBoolean("sound_effs"))
					Assets.button_pop.play();
				MessageScreen message = new MessageScreen(stage);
				message.makeWindow("Clear Highscores?", "Yes", "No",
						new ButtonInterface() {

					@Override
					public void buttonPressed() {
						clearScoreboard();
					}
				}, new ButtonInterface() {
					@Override
					public void buttonPressed() {
					}
				});
			}
		});

		//		mode = new SelectBox<String>(Assets.skin);
		//		mode.setItems("Normal", "Challenge");
		//		if (!Assets.prefs.getString("mode", "").equals(""))
		//			mode.setSelected(Assets.prefs.getString("mode"));
		//		// Set the font size of the current shown item.
		//		mode.getStyle().font = Assets.font_32;
		//		// Set the font size of all the items in the list.
		//		mode.getList().getStyle().font = Assets.font_32;
		//		mode.addListener(new ChangeListener() {
		//			@Override
		//			public void changed(ChangeEvent event, Actor actor) {
		//				switchHighScoreTable();
		//			}
		//		});



		setHighScoreTable(table);

		final int pages = 2;
		Table score_table = new Table(Assets.skin);
		score_table.setWidth(Assets.width * pages);
		score_table.add(table).width(Assets.width);

		final ScrollPane score_scroll = new ScrollPane(score_table);
		score_scroll.setFlickScroll(false);
		score_scroll.setWidth(Assets.width);
		score_scroll.setHeight(navigation_bar.getY());

		Table leaderboard_table = new Table(Assets.skin);
		leaderboard_table.setWidth(Assets.width);
		leaderboard_table.setHeight(Assets.height);

		LabelStyle labelStyle = new LabelStyle();
		labelStyle.font = Assets.font_64;
		labelStyle.fontColor = Assets.ORANGE;

		Label time_mode = new Label("Time Mode", labelStyle);
		time_mode.setBounds(time_mode.getX(), time_mode.getY(), time_mode.getWidth(), time_mode.getHeight());
		time_mode.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(Assets.prefs.getBoolean("sound_effs"))
					Assets.button_pop.play();
				Assets.game.apk_intf.showLeaderboard(Assets.LeaderboardType.TIME);
			}
		});
		leaderboard_table.add(time_mode);
		score_table.add(leaderboard_table).width(Assets.width);

		final Image highscore_tab = new Image(Assets.transparent_background);
		final Image leaderboard_tab = new Image(Assets.transparent_background);
		leaderboard_tab.setVisible(false);

		Label leaderboard = new Label("Leaderboard", style_big);
		leaderboard.setBounds(leaderboard.getX(), leaderboard.getY(), leaderboard.getWidth(), leaderboard.getHeight());
		leaderboard.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(score_scroll.getScrollX() != Assets.width * (pages - 1)) {
					if(Assets.prefs.getBoolean("sound_effs"))
						Assets.button_pop.play();
					score_scroll.scrollTo(score_scroll.getScrollX() + Assets.width, 0, score_scroll.getWidth(), score_scroll.getHeight());
				}
				
				highscore_tab.setVisible(false);
				leaderboard_tab.setVisible(true);
			}
		});

		Label highscore = new Label("Highscore", style_big);
		highscore.setBounds(highscore.getX(), highscore.getY(), highscore.getWidth(), highscore.getHeight());
		highscore.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(score_scroll.getScrollX() != 0) {
					if(Assets.prefs.getBoolean("sound_effs"))
						Assets.button_pop.play();
					score_scroll.scrollTo(score_scroll.getScrollX() - Assets.width, 0, score_scroll.getWidth(), score_scroll.getHeight());
				}
				
				highscore_tab.setVisible(true);
				leaderboard_tab.setVisible(false);
			}
		});
				
		float length = Assets.width / 2 + highscore.getWidth() / 2;
		float tab_width = (length / 2 > leaderboard.getWidth()) ? length / 2 : leaderboard.getWidth();
		
		Table tab_table = new Table(Assets.skin);
		Table center_table = new Table(Assets.skin);
		center_table.add(highscore);
		tab_table.stack(highscore_tab, center_table).width(tab_width);
		center_table = new Table(Assets.skin);
		center_table.add(leaderboard);
		tab_table.stack(leaderboard_tab, center_table).width(tab_width);

		
		navigation_bar.clear();
		navigation_bar.add(back).width(navigation_bar.getWidth() - tab_width * 2 - 50 - Assets.PAD);
		navigation_bar.add(tab_table).width(tab_width * 2);
		
		stage.addActor(score_scroll);
	}
	
	public void clearScoreboard() {
		// if (mode.getSelected().equals("Normal")) {
		NormalScoreboard scoreboard = new NormalScoreboard();
		scoreboard.clearScoreboard();
		// } else {
		// ChallengeScoreboard scoreboard = new ChallengeScoreboard();
		// scoreboard.clearScoreboard();
		// }
		switchHighScoreTable();
	}

	// private void switchFontColor() {
	// if (Assets.prefs.getString("bg_color").equals("Black")) {
	// style_big.fontColor = Color.WHITE;
	// style_small.fontColor = Color.WHITE;
	// } else {
	// style_big.fontColor = Color.BLACK;
	// style_small.fontColor = Color.BLACK;
	// }
	// }

	private void switchHighScoreTable() {
		table.clear();
		setHighScoreTable(table);
	}

	private void setHighScoreTable(Table table) {
		// if (mode.getSelected().equals("Normal")) {
		NormalScoreboard scoreboard = new NormalScoreboard();
		fillInHighScore(scoreboard, table);
		// } else {
		// ChallengeScoreboard scoreboard = new ChallengeScoreboard();
		// fillInHighScore(scoreboard, table);
		// }
	}

	private void fillInHighScore(Scoreboard scoreboard, Table table) {
		Scores scores = scoreboard.getAllScores();

		// table.add(mode).top().right();
		// table.row();
		// table.add("").height(Gdx.graphics.getHeight() / 20);
		// table.row();
		//		table.add(new Label("Highscore", style_big)).center();
		//		table.row();

		Table innerTable = new Table(Assets.skin);
		innerTable.add("").height(Gdx.graphics.getHeight() / height);
		innerTable.row();
		innerTable.add(new Label(scores.first_name, style_small)).left()
		.padRight((float) (Gdx.graphics.getWidth() / pad_left));
		innerTable.add(new Label(String.valueOf(scores.first), style_small))
		.right().padLeft(Gdx.graphics.getWidth() / pad_right)
		.height(style_small.font.getLineHeight());
		innerTable.row();
		innerTable.add(new Label(scores.second_name, style_small)).left()
		.padRight((float) (Gdx.graphics.getWidth() / pad_left));
		innerTable.add(new Label(String.valueOf(scores.second), style_small))
		.right().padLeft(Gdx.graphics.getWidth() / pad_right)
		.height(style_small.font.getLineHeight());
		innerTable.row();
		innerTable.add(new Label(scores.third_name, style_small)).left()
		.padRight((float) (Gdx.graphics.getWidth() / pad_left));
		innerTable.add(new Label(String.valueOf(scores.third), style_small))
		.right().padLeft(Gdx.graphics.getWidth() / pad_right)
		.height(style_small.font.getLineHeight());
		innerTable.row();
		innerTable.add(new Label(scores.fourth_name, style_small)).left()
		.padRight((float) (Gdx.graphics.getWidth() / pad_left));
		innerTable.add(new Label(String.valueOf(scores.fourth), style_small))
		.right().padLeft(Gdx.graphics.getWidth() / pad_right)
		.height(style_small.font.getLineHeight());
		innerTable.row();
		innerTable.add(new Label(scores.fifth_name, style_small)).left()
		.padRight((float) (Gdx.graphics.getWidth() / pad_left));
		innerTable.add(new Label(String.valueOf(scores.fifth), style_small))
		.right().padLeft(Gdx.graphics.getWidth() / pad_right)
		.height(style_small.font.getLineHeight());
		innerTable.row();

		table.add(innerTable);
		table.row();
		table.add(clear).center();
	}

	public void addActors() {
		super.addActors();
		actors.add(clear);
	}

	@Override
	public void show() {
		stage.addAction(Actions.parallel(
				Actions.run(new Runnable() {
					public void run() {
						otherShowOptions();
					}
				}),
				Actions.sequence(Actions.alpha(0), Actions.fadeIn(1f),
						Actions.run(new Runnable() {
							public void run() {
								setActorsTouchable(Touchable.enabled);
								Gdx.input.setInputProcessor(stage);
							}
						}))));
	}

	public void otherShowOptions() {
		// switchFontColor();
		switchHighScoreTable();
	}
}
