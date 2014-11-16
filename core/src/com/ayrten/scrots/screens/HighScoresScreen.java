package com.ayrten.scrots.screens;

import com.ayrten.scrots.manager.Assets;
import com.ayrten.scrots.scoreboard.ChallengeScoreboard;
import com.ayrten.scrots.scoreboard.NormalScoreboard;
import com.ayrten.scrots.scoreboard.Scoreboard;
import com.ayrten.scrots.scoreboard.Scoreboard.Scores;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class HighScoresScreen extends ScrotsScreen
{
	private float pad_left = (float) 5.5;
	private float pad_right = (float) 5.5;
	private int height = 75;

	private Table table;
	private SelectBox<String> mode;
	private Label clear;
	private Window confirm_clear;

	LabelStyle style_big;
	LabelStyle style_small;

//	private boolean should_add_action;
	
	public HighScoresScreen(Screen bscreen) 
	{
		super(bscreen, true);
		
//		should_add_action = true;

		table = new Table();
		table.setFillParent(true);
		table.setSkin(Assets.skin);

		style_big = new LabelStyle();
		style_big.font = Assets.font_64;
		
		style_small = new LabelStyle();
		style_small.font = Assets.font_64;

		switchFontColor();
		
		clear = new Label("Clear", style_small);
		clear.setBounds(clear.getX(), clear.getY(), clear.getWidth(),
				clear.getHeight());
		clear.setPosition(0, Gdx.graphics.getHeight() - clear.getHeight());
		clear.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				if (Assets.prefs.getBoolean("sound_effs"))
					Assets.pop.play();
				confirm_clear.setVisible(true);
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
		proceed.add(new Label("Proceed", new LabelStyle(Assets.font_16,
				Color.WHITE)));
		proceed.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				clearScoreboard();
				confirm_clear.setVisible(false);
			};
		});

		TextButton cancel = new TextButton("", Assets.skin);
		cancel.add(new Label("Cancel", new LabelStyle(Assets.font_16,
				Color.WHITE)));
		cancel.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				confirm_clear.setVisible(false);
			};
		});

		confirm_clear = new Window("Clear scores?", Assets.skin);
		confirm_clear.setTitle("Clear scores?");

		confirm_clear.add(proceed);
		confirm_clear.row();
		confirm_clear.add("").height(Gdx.graphics.getHeight() / 100);
		confirm_clear.row();
		confirm_clear.add(cancel);
		confirm_clear.setPosition(
				stage.getWidth() / 2 - confirm_clear.getWidth() / 2,
				stage.getHeight() / 2 - confirm_clear.getHeight() / 2);
		confirm_clear.pack();
		confirm_clear.setMovable(false);
		confirm_clear.setVisible(false);

		setHighScoreTable(table);
		setupStage();
		stage.addActor(table);
		stage.addActor(confirm_clear);
	}

	private void clearScoreboard() {
		if (mode.getSelected().equals("Normal")) {
			NormalScoreboard scoreboard = new NormalScoreboard();
			scoreboard.clearScoreboard();
		} else {
			ChallengeScoreboard scoreboard = new ChallengeScoreboard();
			scoreboard.clearScoreboard();
		}
		switchHighScoreTable();
	}

	private void switchFontColor() {
		if (Assets.prefs.getString("bg_color").equals("Black")) {
			style_big.fontColor = Color.WHITE;
			style_small.fontColor = Color.WHITE;
		} else {
			style_big.fontColor = Color.BLACK;
			style_small.fontColor = Color.BLACK;
		}
	}

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

		float width = Gdx.graphics.getWidth();
		float font_width = style_big.font.getMultiLineBounds("Highscore").width;
		float center = (width / 2) - (font_width / 2);

		// table.add(back).top().left();
		// table.add(mode).top().right();
		table.row();
		table.add("").height(Gdx.graphics.getHeight() / 20);
		table.row();
		table.add(new Label("Highscore", style_big)).left().padLeft(center)
				.fillX();
		table.row();
		table.add("").height(Gdx.graphics.getHeight() / height);
		table.row();
		table.add(new Label(scores.first_name, style_small)).left()
				.padLeft((float) (Gdx.graphics.getWidth() / pad_left));
		table.add(new Label(String.valueOf(scores.first), style_small)).right()
				.padRight(Gdx.graphics.getWidth() / pad_right)
				.height(style_small.font.getLineHeight());
		table.row();
		table.add("").height(Gdx.graphics.getHeight() / height);
		table.row();
		table.add(new Label(scores.second_name, style_small)).left()
				.padLeft((float) (Gdx.graphics.getWidth() / pad_left));
		table.add(new Label(String.valueOf(scores.second), style_small))
				.right().padRight(Gdx.graphics.getWidth() / pad_right)
				.height(style_small.font.getLineHeight());
		table.row();
		table.add("").height(Gdx.graphics.getHeight() / height);
		table.row();
		table.add(new Label(scores.third_name, style_small)).left()
				.padLeft((float) (Gdx.graphics.getWidth() / pad_left));
		table.add(new Label(String.valueOf(scores.third), style_small)).right()
				.padRight(Gdx.graphics.getWidth() / pad_right)
				.height(style_small.font.getLineHeight());
		table.row();
		table.add("").height(Gdx.graphics.getHeight() / height);
		table.row();
		table.add(new Label(scores.fourth_name, style_small)).left()
				.padLeft((float) (Gdx.graphics.getWidth() / pad_left));
		table.add(new Label(String.valueOf(scores.fourth), style_small))
				.right().padRight(Gdx.graphics.getWidth() / pad_right)
				.height(style_small.font.getLineHeight());
		table.row();
		table.add("").height(Gdx.graphics.getHeight() / height);
		table.row();
		table.add(new Label(scores.fifth_name, style_small)).left()
				.padLeft((float) (Gdx.graphics.getWidth() / pad_left));
		table.add(new Label(String.valueOf(scores.fifth), style_small)).right()
				.padRight(Gdx.graphics.getWidth() / pad_right)
				.height(style_small.font.getLineHeight());
		table.row();
		table.add(clear).left()
				.padLeft((float) (Gdx.graphics.getWidth() / pad_left)).expand();
		table.row();
	}
	
	public void addActors() 
	{
		super.addActors();
		actors.add(clear);
	}
	
	public void otherShowOptions() {
		switchFontColor();
		switchHighScoreTable();
//		should_add_action = true;
	}
}
