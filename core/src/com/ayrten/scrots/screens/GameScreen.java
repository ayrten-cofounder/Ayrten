package com.ayrten.scrots.screens;

import java.util.ArrayList;

import com.ayrten.scrots.game.ChallengeGameMode;
import com.ayrten.scrots.game.GameMode;
import com.ayrten.scrots.game.NormalGameMode;
import com.ayrten.scrots.level.Level;
import com.ayrten.scrots.manager.Assets;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Pool;

public class GameScreen implements Screen {
	// Widgets
	protected TextField user_name;
	protected Label game_over;
	protected Label replay;
	protected Label main_menu;
	protected Label pause;

	protected Window pause_menu;
	protected Window confirm_quit;

	protected Label can_you_label;
	protected Label points_title;
	protected Label points;
	protected Label time_title;
	protected Label time;
	protected Label time_end;

	protected GameMode gamemode;
	protected Manager gm;
	protected Stage stage;
	protected Table table;

	protected int w;
	protected int h;

	protected Level curr_level;
	protected SpriteBatch batch;
	protected boolean should_clear_stage;
	protected ArrayList<Level> all_levels = new ArrayList<Level>();

	protected Pool<MoveToAction> pool;

	public boolean go_back = false;

	public GameScreen() {
		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();
		should_clear_stage = true;
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		this.batch = (SpriteBatch) stage.getBatch();
		this.batch.enableBlending();
		this.batch.setBlendFunction(GL20.GL_LINEAR_MIPMAP_NEAREST,
				GL20.GL_NEAREST);

		// this.batch.setBlendFunction(GL20.GL_LINEAR_MIPMAP_LINEAR,
		// GL20.GL_LINEAR_MIPMAP_LINEAR);

		points_title = new Label("YOUAREATLEVEL", Assets.style_font_64_red);
		points_title.setPosition(1, Assets.game_height);

		points = new Label("00", Assets.prefs.getString("bg_color").equals(
				"Black") ? Assets.style_font_64_white
				: Assets.style_font_64_black);
//		points = new Label("00", Assets.style_font_64_blue);
		points.setPosition(0 + points_title.getWidth(), Assets.game_height);

		time_title = new Label("WITH", Assets.style_font_64_red);
		time_title.setPosition(1 + points_title.getWidth() + points.getWidth(),
				Assets.game_height);

		 time = new Label("60.0", Assets.prefs.getString("bg_color").equals(
		 "Black") ? Assets.style_font_64_white
		 : Assets.style_font_64_black);
//		time = new Label("60.0", Assets.style_font_64_blue);
		time.setPosition(1 + points_title.getWidth() + points.getWidth()
				+ time_title.getWidth(), Assets.game_height);

		time_end = new Label("SECONDSLEFT", Assets.style_font_64_red);
		time_end.setPosition(1 + points_title.getWidth() + points.getWidth()
				+ time_title.getWidth() + time.getWidth(), Assets.game_height);
		
		can_you_label = new Label("CANYOUGETTOLEVEL20?YOU CANT.", Assets.style_font_64_orange);
		can_you_label.setPosition(1, Assets.game_height + (points.getStyle().font.getLineHeight() / 1.4f));

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

		gm = new Manager(0, w, h); // Starts with 0 points
		if (Assets.prefs.getString("mode").equals("Normal")) {
			gm.setMode(GameMode.NORMAL_MODE);
			gm.setScoreboard(Assets.game.main_menu.nsb);
			gamemode = new NormalGameMode(stage, gm, w, h);
		} else {
			gm.setMode(GameMode.CHALLENGE_MODE);
			gm.setScoreboard(Assets.game.main_menu.csb);
			gamemode = new ChallengeGameMode(stage, gm, w, h);
		}

		replay = new Label("Replay", buttonStyle);
		replay.setBounds(replay.getX(), replay.getY(), replay.getWidth(),
				replay.getHeight());
		replay.setPosition(0, Gdx.graphics.getHeight() - replay.getHeight());
		replay.addListener(new InputListener() {
			// Need this or else it won't recognize the touch down event.
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				GameScreen new_game = new GameScreen();
				((ScrotsGame) Gdx.app.getApplicationListener()).main_menu.game_screen
						.dispose();
				((ScrotsGame) Gdx.app.getApplicationListener()).main_menu.game_screen = new_game;
				((ScrotsGame) Gdx.app.getApplicationListener()).setScreen(((ScrotsGame) Gdx.app
						.getApplicationListener()).main_menu.game_screen);
			}
		});

		main_menu = new Label("Main Menu", buttonStyle);
		main_menu.setBounds(main_menu.getX(), main_menu.getY(),
				main_menu.getWidth(), main_menu.getHeight());
		main_menu.addListener(new InputListener() {
			// Need this or else it won't recognize the touch down event.
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				stage.addAction(Actions.sequence(Actions.alpha(1),
						Actions.fadeOut(1f), Actions.run(new Runnable() {
							@Override
							public void run() {
								Assets.game.main_menu.game_screen.dispose();
								Assets.game.setScreen(Assets.game.main_menu);
							}
						})));
			}
		});

		game_over = new Label("Game Over!", overStyle);
		game_over.setCenterPosition(Gdx.graphics.getWidth() / 2,
				Gdx.graphics.getHeight() / 3 * 2);
		game_over.setVisible(false);

		user_name = new TextField("", textStyle);
		user_name.setMessageText("Enter your name");
		user_name.getStyle().background = Assets.gray_box;
		user_name.getStyle().background
				.setLeftWidth(user_name.getStyle().background.getLeftWidth()
						+ textStyle.font.getBounds("w").width);
		user_name.setMaxLength(10);
		user_name
				.setWidth(textStyle.font.getBounds("01234567890123456789").width);
		user_name.setCenterPosition(
				Gdx.graphics.getWidth() / 2,
				Gdx.graphics.getHeight() / 3 * 2
						- overStyle.font.getLineHeight());
		user_name.setTextFieldListener(new TextFieldListener() {
			public void keyTyped(TextField textField, char key) {
				if (key == '\n' || Gdx.input.isKeyPressed(Keys.ENTER)) {
					((ScrotsGame) Gdx.app.getApplicationListener()).main_menu.game_screen
							.getManager().addHighScore(textField.getText());
					table.setVisible(true);
					user_name.setVisible(false);
				}
			}
		});

		user_name.setVisible(false);

		pause = new Label("Menu", buttonStyle);
		pause.setBounds(pause.getX(), pause.getY(), pause.getWidth(),
				pause.getHeight());
		pause.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				pause_menu.setVisible(true);
				gm.pauseGame();
			}
		});
		pause.setWidth(buttonStyle.font.getBounds("  Menu").width);
		// pause.setHeight(buttonStyle.font.getLineHeight());
		pause.setPosition(w - pause.getWidth(),
				Assets.game_height + pause.getStyle().font.getLineHeight()
						/ 1.4f);

		pause_menu = new Window("Menu", Assets.skin);
		pause_menu.getStyle().titleFont = Assets.font_64;
		pause_menu.setPosition(-pause_menu.getWidth(), stage.getHeight() / 2
				- pause_menu.getHeight() / 2);
		pause_menu.addAction(Actions.moveTo(
				stage.getWidth() / 2 - pause_menu.getWidth() / 2,
				stage.getHeight() / 2 - pause_menu.getHeight() / 2, 1f));
		pause_menu.setMovable(false);
		pause_menu.setVisible(false);

		TextButton pause_quit = new TextButton("", Assets.skin);
		pause_quit.add(new Label("Quit", buttonStyle));
		pause_quit.setBounds(pause_quit.getX(), pause_quit.getY(),
				pause_quit.getWidth(), pause_quit.getHeight());
		pause_quit.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				pause_menu.addAction(Actions.sequence(Actions.alpha(1),
						Actions.fadeOut(0.25f), Actions.run(new Runnable() {
							@Override
							public void run() {
								pause_menu.setVisible(false);
								confirm_quit.addAction(Actions.parallel(
										Actions.run(new Runnable() {
											@Override
											public void run() {
												confirm_quit.setVisible(true);
											}
										}), 
										Actions.sequence(Actions.alpha(0),
												Actions.fadeIn(0.25f))));
							}
						})));
			}
		});
		pause_menu.add(pause_quit);
		pause_menu.row();

		TextButton pause_cancel = new TextButton("", Assets.skin);
		pause_cancel.add(new Label("Cancel", buttonStyle));
		pause_cancel.setBounds(pause_cancel.getX(), pause_cancel.getY(),
				pause_cancel.getWidth(), pause_cancel.getHeight());
		pause_cancel.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				pause_menu.addAction(Actions.sequence(
						Actions.moveTo(
								stage.getWidth() - pause_menu.getWidth(),
								stage.getHeight() / 2 - pause_menu.getHeight()
										/ 2, 1f), Actions.run(new Runnable() {
							@Override
							public void run() {
								for (Actor actor : stage.getActors()) {
									actor.setVisible(true);
								}

								pause_menu.setVisible(false);
								confirm_quit.setVisible(false);
								table.setVisible(false);
								game_over.setVisible(false);
								user_name.setVisible(false);
								gm.startGame();

								pause_menu.setPosition(
										-pause_menu.getWidth(),
										stage.getHeight() / 2
												- pause_menu.getHeight() / 2);
								pause_menu.addAction(Actions.moveTo(
										stage.getWidth() / 2
												- pause_menu.getWidth() / 2,
										stage.getHeight() / 2
												- pause_menu.getHeight() / 2,
										1f));
							}
						})));
			}
		});
		pause_menu.add(pause_cancel);

		TextButton proceed = new TextButton("", Assets.skin);
		proceed.add(new Label("Proceed", buttonStyle));
		proceed.setBounds(proceed.getX(), proceed.getY(), proceed.getWidth(),
				proceed.getHeight());
		proceed.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				stage.addAction(Actions.sequence(Actions.alpha(1),
						Actions.fadeOut(1f), Actions.run(new Runnable() {
							@Override
							public void run() {
								Assets.game.main_menu.game_screen.dispose();
								Assets.game.setScreen(Assets.game.main_menu);
							}
						})));
			}
		});

		TextButton quit_cancel = new TextButton("", Assets.skin);
		quit_cancel.add(new Label("Cancel", buttonStyle));
		quit_cancel.setBounds(quit_cancel.getX(), quit_cancel.getY(),
				quit_cancel.getWidth(), quit_cancel.getHeight());
		quit_cancel.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				confirm_quit.addAction(Actions.sequence(Actions.alpha(1),
						Actions.fadeOut(0.25f), Actions.run(new Runnable() {
							@Override
							public void run() {
								confirm_quit.setVisible(false);
								pause_menu.setVisible(true);
								pause_menu.addAction(Actions.sequence(
										Actions.alpha(0),
										Actions.fadeIn(0.25f),
										Actions.run(new Runnable() {
											@Override
											public void run() {
												pause_menu.setVisible(true);
											}
										})));
							}
						})));
			}
		});

		confirm_quit = new Window("Back to main menu?", Assets.skin);
		confirm_quit.add(proceed);
		confirm_quit.row();
		confirm_quit.add(quit_cancel);
		confirm_quit.setVisible(false);
		confirm_quit.setMovable(false);
		confirm_quit.setPosition(stage.getWidth() / 2 - confirm_quit.getWidth()
				/ 2, stage.getHeight() / 2 - confirm_quit.getHeight() / 2);

		table = new Table(Assets.skin);
		table.setSkin(Assets.skin);
		table.setCenterPosition(Gdx.graphics.getWidth() / 2,
				Gdx.graphics.getHeight() / 2);
		table.add(replay);
		table.row();
		table.add("").height(Gdx.graphics.getHeight() / 50);
		table.row();
		table.add(main_menu);
		table.setVisible(false);

		pool = new Pool<MoveToAction>() {
			@Override
			protected MoveToAction newObject() {
				// TODO Auto-generated method stub
				return new MoveToAction();
			}
		};

		addStageActors();
		curr_level = gamemode.gen_curr_level();
		gm.startGame();
	}

	public Manager getManager() {
		return gm;
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
			game_over.setVisible(true);
			pause.setVisible(false);
			if (gm.get_player_score() > gm.getScoreBoard().getLowestHighScore()) {
				user_name.setVisible(true);
			} else {
				table.setVisible(true);
			}
		}

		stage.draw();
	}

	private void addStageActors() {
		stage.addActor(pause);
		stage.addActor(pause_menu);
		stage.addActor(confirm_quit);
		stage.addActor(table);
		stage.addActor(game_over);
		stage.addActor(user_name);
		stage.addActor(can_you_label);
		stage.addActor(points_title);
		stage.addActor(points);
		stage.addActor(time);
		stage.addActor(time_title);
		stage.addActor(time_end);
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
