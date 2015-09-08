package com.ayrten.scrots.screens;

import com.ayrten.scrots.manager.Assets;
import com.ayrten.scrots.manager.ButtonInterface;
import com.ayrten.scrots.scoreboard.TimeScoreboard;
import com.ayrten.scrots.scoreboard.Scoreboard;
import com.ayrten.scrots.scoreboard.Scoreboard.Scores;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

public class ScoresScreen extends ScrotsScreen {
	private float pad_left = (float) 5.5;
	private float pad_right = (float) 5.5;
	private int height = 75;

	// private SelectBox<String> mode;
	protected Label clear;

//	protected LabelStyle Assets.style_font_64_white;

	public ScoresScreen(Screen bscreen) {
		super(bscreen, true);

		setupStage();
		// showTableScreen();

		LabelStyle clear_style = new LabelStyle();
		clear_style.font = Assets.font_32;
		clear_style.fontColor = Color.WHITE;

		int left = (int) ((int) Assets.width * 0.02);
		int right = left;
		int top = (int) ((int) Assets.height * 0.02);
		int bottom = top;

		NinePatchDrawable rounded_rectangle_orange = new NinePatchDrawable(
				new NinePatch(new Texture(Gdx.files
						.internal("data/rounded_rectangle_pale_orange.png")),
						left, right, top, bottom));

		clear_style.background = rounded_rectangle_orange;

		// switchFontColor();

		clear = new Label("Clear", clear_style);
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

		// mode = new SelectBox<String>(Assets.skin);
		// mode.setItems("Normal", "Challenge");
		// if (!Assets.prefs.getString("mode", "").equals(""))
		// mode.setSelected(Assets.prefs.getString("mode"));
		// // Set the font size of the current shown item.
		// mode.getStyle().font = Assets.font_32;
		// // Set the font size of all the items in the list.
		// mode.getList().getStyle().font = Assets.font_32;
		// mode.addListener(new ChangeListener() {
		// @Override
		// public void changed(ChangeEvent event, Actor actor) {
		// switchHighScoreTable();
		// }
		// });

//		Table scores_table = new Table(Assets.skin);
//		setHighScoreTable(scores_table);

		final int pages = 2;
		Table score_screen_table = new Table(Assets.skin);
		score_screen_table.setWidth(Assets.width * pages);
		score_screen_table.add(table)
				.width(Assets.width - (Assets.PAD * 2))
				.height(Assets.height - back.getHeight() - (Assets.PAD * 2))
				.padLeft(Assets.PAD);

		final ScrollPane score_scroll = new ScrollPane(score_screen_table);
		score_scroll.setFlickScroll(false);
		score_scroll.setWidth(Assets.width);
		score_scroll.setHeight(navigation_bar.getY());

		Table leaderboard_table = new Table(Assets.skin);
		leaderboard_table.setWidth(Assets.width);
		leaderboard_table.setHeight(Assets.height);

//		LabelStyle labelStyle = new LabelStyle();
//		labelStyle.font = Assets.font_64;
//		labelStyle.fontColor = Color.WHITE;

		Label time_mode = new Label("Time Mode", Assets.style_font_64_white);
		time_mode.setBounds(time_mode.getX(), time_mode.getY(),
				time_mode.getWidth(), time_mode.getHeight());
		time_mode.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (Assets.prefs.getBoolean("sound_effs"))
					Assets.button_pop.play();
				Assets.game.apk_intf
						.showLeaderboard(Assets.LeaderboardType.TIME);
			}
		});
		leaderboard_table.add(time_mode);
		
		Label survival_mode = new Label("Survial Mode - Coming Soon!", Assets.style_font_64_orange);
		survival_mode.setBounds(survival_mode.getX(), survival_mode.getY(), survival_mode.getWidth(), survival_mode.getHeight());
		leaderboard_table.row();
		leaderboard_table.add(survival_mode);
		
		Table second_bkg_table = new Table(Assets.skin);
		second_bkg_table.setBackground(Assets.rounded_rectangle_border);
		score_screen_table.stack(second_bkg_table, leaderboard_table)
				.width(Assets.width - (Assets.PAD * 2))
				.height(Assets.height - back.getHeight() - (Assets.PAD * 2))
				.padLeft(Assets.PAD).padRight(Assets.PAD);

		final Image highscore_tab = new Image(Assets.transparent_background);
		final Image leaderboard_tab = new Image(Assets.transparent_background);
		leaderboard_tab.setVisible(false);

		Label leaderboard = new Label("Leaderboard", Assets.style_font_64_white);
		leaderboard.setBounds(leaderboard.getX(), leaderboard.getY(),
				leaderboard.getWidth(), leaderboard.getHeight());
		leaderboard.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (score_scroll.getScrollX() != Assets.width * (pages - 1)) {
					if (Assets.prefs.getBoolean("sound_effs"))
						Assets.button_pop.play();
					score_scroll.scrollTo(score_scroll.getScrollX()
							+ Assets.width, 0, score_scroll.getWidth(),
							score_scroll.getHeight());
				}

				highscore_tab.setVisible(false);
				leaderboard_tab.setVisible(true);
			}
		});

		Label highscore = new Label("Highscore", Assets.style_font_64_white);
		highscore.setBounds(highscore.getX(), highscore.getY(),
				highscore.getWidth(), highscore.getHeight());
		highscore.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (score_scroll.getScrollX() != 0) {
					if (Assets.prefs.getBoolean("sound_effs"))
						Assets.button_pop.play();
					score_scroll.scrollTo(score_scroll.getScrollX()
							- Assets.width, 0, score_scroll.getWidth(),
							score_scroll.getHeight());
				}

				highscore_tab.setVisible(true);
				leaderboard_tab.setVisible(false);
			}
		});

		float length = Assets.width / 2 + highscore.getWidth() / 2;
		float tab_width = (length / 2 > leaderboard.getWidth()) ? length / 2
				: leaderboard.getWidth();

		Table tab_table = new Table(Assets.skin);
		Table center_table = new Table(Assets.skin);
		center_table.add(highscore);
		tab_table.stack(highscore_tab, center_table).width(tab_width);
		center_table = new Table(Assets.skin);
		center_table.add(leaderboard);
		tab_table.stack(leaderboard_tab, center_table).width(tab_width);

		navigation_bar.clear();
		navigation_bar.add(back).width(
				navigation_bar.getWidth() - tab_width * 2 - 50 - Assets.PAD);
		navigation_bar.add(tab_table).width(tab_width * 2);

		stage.addActor(score_scroll);
	}

	public void clearScoreboard() {
		// if (mode.getSelected().equals("Normal")) {
		TimeScoreboard scoreboard = new TimeScoreboard();
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
		TimeScoreboard scoreboard = new TimeScoreboard();
		fillInHighScore(scoreboard, table);
		// } else {
		// ChallengeScoreboard scoreboard = new ChallengeScoreboard();
		// fillInHighScore(scoreboard, table);
		// }
	}

	private void fillInHighScore(Scoreboard scoreboard, Table local_table) {
		Scores scores = scoreboard.getAllScores();
		table.clear();

		Table innerTable = new Table(Assets.skin);
		innerTable.add("").height(Gdx.graphics.getHeight() / height);
		innerTable.row();
		innerTable.add(new Label(scores.first_name, Assets.style_font_64_white)).left()
				.padRight((float) (Gdx.graphics.getWidth() / pad_left));
		innerTable.add(new Label(String.valueOf(scores.first), Assets.style_font_64_white))
				.right().padLeft(Gdx.graphics.getWidth() / pad_right)
				.height(Assets.style_font_64_white.font.getLineHeight());
		innerTable.row();
		innerTable.add(new Label(scores.second_name, Assets.style_font_64_white)).left()
				.padRight((float) (Gdx.graphics.getWidth() / pad_left));
		innerTable.add(new Label(String.valueOf(scores.second), Assets.style_font_64_white))
				.right().padLeft(Gdx.graphics.getWidth() / pad_right)
				.height(Assets.style_font_64_white.font.getLineHeight());
		innerTable.row();
		innerTable.add(new Label(scores.third_name, Assets.style_font_64_white)).left()
				.padRight((float) (Gdx.graphics.getWidth() / pad_left));
		innerTable.add(new Label(String.valueOf(scores.third), Assets.style_font_64_white))
				.right().padLeft(Gdx.graphics.getWidth() / pad_right)
				.height(Assets.style_font_64_white.font.getLineHeight());
		innerTable.row();
		innerTable.add(new Label(scores.fourth_name, Assets.style_font_64_white)).left()
				.padRight((float) (Gdx.graphics.getWidth() / pad_left));
		innerTable.add(new Label(String.valueOf(scores.fourth), Assets.style_font_64_white))
				.right().padLeft(Gdx.graphics.getWidth() / pad_right)
				.height(Assets.style_font_64_white.font.getLineHeight());
		innerTable.row();
		innerTable.add(new Label(scores.fifth_name, Assets.style_font_64_white)).left()
				.padRight((float) (Gdx.graphics.getWidth() / pad_left));
		innerTable.add(new Label(String.valueOf(scores.fifth), Assets.style_font_64_white))
				.right().padLeft(Gdx.graphics.getWidth() / pad_right)
				.height(Assets.style_font_64_white.font.getLineHeight());
		innerTable.row();

		table.add(innerTable);
		table.row();
		table.add(clear);
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
