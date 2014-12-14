package com.ayrten.scrots.screens;

import com.ayrten.scrots.manager.Assets;
import com.ayrten.scrots.manager.ButtonInterface;
import com.ayrten.scrots.scoreboard.ChallengeScoreboard;
import com.ayrten.scrots.scoreboard.NormalScoreboard;
import com.ayrten.scrots.scoreboard.Scoreboard;
import com.ayrten.scrots.scoreboard.Scoreboard.Scores;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class HighScoresScreen extends ScrotsScreen {
	private float pad_left = (float) 5.5;
	private float pad_right = (float) 5.5;
	private int height = 75;

	private Table table;
	private SelectBox<String> mode;
	private Label clear;
	private Window confirm_clear;

	LabelStyle style_big;
	LabelStyle style_small;

	public HighScoresScreen(Screen bscreen) {
		super(bscreen, true);

		table = new Table();
		table.setFillParent(true);
		table.setSkin(Assets.skin);

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
					Assets.pop.play();
				// confirm_clear.setVisible(true);
				Assets.game.apk_intf.makeYesNoWindow("Clear Highscores?",
						new ButtonInterface() {

							@Override
							public void buttonPressed() {
								clearScoreboard();
							}
						}, null);
			}
		});

		mode = new SelectBox<String>(Assets.skin);
		mode.setItems("Normal", "Challenge");
		if (!Assets.prefs.getString("mode", "").equals(""))
			mode.setSelected(Assets.prefs.getString("mode"));
		// Set the font size of the current shown item.
		mode.getStyle().font = Assets.font_32;
		// Set the font size of all the items in the list.
		mode.getList().getStyle().font = Assets.font_32;
		mode.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				switchHighScoreTable();
			}
		});

		TextButton proceed = new TextButton("", Assets.skin);
		proceed.add(new Label("Proceed", new LabelStyle(Assets.font_32,
				Color.WHITE)));
		proceed.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				clearScoreboard();
				confirm_clear.setVisible(false);
			};
		});

		TextButton cancel = new TextButton("", Assets.skin);
		cancel.add(new Label("Cancel", new LabelStyle(Assets.font_32,
				Color.WHITE)));
		cancel.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				confirm_clear.setVisible(false);
			};
		});

		confirm_clear = new Window("Clear scores?", Assets.skin);
		confirm_clear.getStyle().titleFont = Assets.font_64;

		confirm_clear.add(proceed);
		confirm_clear.row();
		confirm_clear.add("").height(Gdx.graphics.getHeight() / 100);
		confirm_clear.row();
		confirm_clear.add(cancel);
		confirm_clear.setPosition(
				stage.getWidth() / 2 - confirm_clear.getWidth() / 2,
				stage.getHeight() / 2 - confirm_clear.getHeight() / 2);
		// confirm_clear.pack();
		confirm_clear.setMovable(false);
		confirm_clear.setVisible(false);

		setHighScoreTable(table);
		setupStage();
		stage.addActor(table);
		stage.addActor(confirm_clear);
	}

	public void clearScoreboard() {
		if (mode.getSelected().equals("Normal")) {
			NormalScoreboard scoreboard = new NormalScoreboard();
			scoreboard.clearScoreboard();
		} else {
			ChallengeScoreboard scoreboard = new ChallengeScoreboard();
			scoreboard.clearScoreboard();
		}
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
		if (mode.getSelected().equals("Normal")) {
			NormalScoreboard scoreboard = new NormalScoreboard();
			fillInHighScore(scoreboard, table);
		} else {
			ChallengeScoreboard scoreboard = new ChallengeScoreboard();
			fillInHighScore(scoreboard, table);
		}
	}

	private void fillInHighScore(Scoreboard scoreboard, Table table) {
		Scores scores = scoreboard.getAllScores();

		// table.add(mode).top().right();
		// table.row();
		// table.add("").height(Gdx.graphics.getHeight() / 20);
		// table.row();
		table.add(new Label("Highscore", style_big)).center();
		table.row();

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
								setTouchable(Touchable.enabled);
								Gdx.input.setInputProcessor(stage);
							}
						}))));
	}

	public void otherShowOptions() {
		// switchFontColor();
		switchHighScoreTable();
	}
}
