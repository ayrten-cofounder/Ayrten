package com.ayrten.scrots.screens;

import java.util.ArrayList;

import com.ayrten.scrots.game.ChallengeGameMode;
import com.ayrten.scrots.game.GameMode;
import com.ayrten.scrots.game.NormalGameMode;
import com.ayrten.scrots.level.Level;
import com.ayrten.scrots.manager.Assets;
import com.ayrten.scrots.manager.ButtonInterface;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Timer.Task;
import com.badlogic.gdx.utils.Timer;

public class GameScreen implements Screen {
	// Widgets
	protected TextField user_name;
	protected Label game_over;
	protected Label replay;
	protected Label main_menu;
	protected Label pause;

	protected Label can_you_label;
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
		// points = new Label("00", Assets.style_font_64_blue);
		points.setPosition(0 + points_title.getWidth(), Assets.game_height);

		time_title = new Label("WITH", Assets.style_font_64_red);
		time_title.setPosition(1 + points_title.getWidth() + points.getWidth(),
				Assets.game_height);

		time = new Label("60.0", Assets.prefs.getString("bg_color").equals(
				"Black") ? Assets.style_font_64_white
				: Assets.style_font_64_black);
		// time = new Label("60.0", Assets.style_font_64_blue);
		time.setPosition(1 + points_title.getWidth() + points.getWidth()
				+ time_title.getWidth(), Assets.game_height);

		time_end = new Label("SECONDSLEFT", Assets.style_font_64_red);
		time_end.setPosition(1 + points_title.getWidth() + points.getWidth()
				+ time_title.getWidth() + time.getWidth(), Assets.game_height);

		can_you_label = new Label("POPALLGREENDOTS",
				Assets.style_font_64_orange);
		can_you_label.setPosition(1, Assets.game_height
				+ (points.getStyle().font.getLineHeight() / 1.4f));

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

		game_over = new Label("Game Over!", overStyle);
		game_over.setCenterPosition(Gdx.graphics.getWidth() / 2,
				Gdx.graphics.getHeight() / 3 * 2);
		game_over.setVisible(false);

		replay = new Label("Replay", buttonStyle);
		replay.setBounds(replay.getX(), replay.getY(), replay.getWidth(),
				replay.getHeight());
		replay.setPosition(0, Gdx.graphics.getHeight() - replay.getHeight());
		replay.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				GameScreen new_game = new GameScreen();
				Assets.game.main_menu.game_screen.dispose();
				Assets.game.main_menu.game_screen = new_game;
				Assets.game.apk_intf.shouldShowAd(false);
				Assets.game.setScreen(Assets.game.main_menu.game_screen);
			}
		});
		replay.setCenterPosition(
				Gdx.graphics.getWidth() / 2,
				game_over.getCenterY()
						- game_over.getStyle().font.getLineHeight() / 2
						- game_over.getStyle().font.getLineHeight() / 2);
		replay.setVisible(false);

		main_menu = new Label("Main Menu", buttonStyle);
		main_menu.setBounds(main_menu.getX(), main_menu.getY(),
				main_menu.getWidth(), main_menu.getHeight());
		main_menu.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				stage.addAction(Actions.sequence(Actions.alpha(1),
						Actions.fadeOut(1f), Actions.run(new Runnable() {
							@Override
							public void run() {
								Assets.game.apk_intf.shouldShowAd(false);
								Assets.game.main_menu.game_screen.dispose();
								Assets.playMenuBGM();
								Assets.game.setScreen(Assets.game.main_menu);
							}
						})));
			}
		});
		main_menu.setVisible(false);
		main_menu.setCenterPosition(Gdx.graphics.getWidth() / 2,
				replay.getCenterY() - replay.getStyle().font.getLineHeight()
						/ 2 - game_over.getStyle().font.getLineHeight() / 2);

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
					main_menu.setVisible(true);
					replay.setVisible(true);
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
				showMenu();

				gm.pauseGame();
			}
		});
		pause.setWidth(buttonStyle.font.getBounds("  Menu").width);
		// pause.setHeight(buttonStyle.font.getLineHeight());
		pause.setPosition(w - pause.getWidth(),
				Assets.game_height + pause.getStyle().font.getLineHeight()
						/ 1.4f);

		TextButton pause_quit = new TextButton("", Assets.skin);
		pause_quit.add(new Label("Quit", buttonStyle));
		pause_quit.setBounds(pause_quit.getX(), pause_quit.getY(),
				pause_quit.getWidth(), pause_quit.getHeight());

		pool = new Pool<MoveToAction>() {
			@Override
			protected MoveToAction newObject() {
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
				});
	}

	public void showMenu() {
		Assets.game.apk_intf.makeWindow("Menu", "Quit", "Resume",
				new ButtonInterface() {

					@Override
					public void buttonPressed() {
						showQuitConfirm();
					}
				}, new ButtonInterface() {

					@Override
					public void buttonPressed() {
						gm.startGame();
					}
				});
	}

	public void showQuitConfirm() {
		Assets.game.apk_intf.makeWindow("Quit?", "Yes", "No",
				new ButtonInterface() {

					@Override
					public void buttonPressed() {
						Assets.game.main_menu.game_screen.dispose();
						Assets.game.setScreen(Assets.game.main_menu);
					}
				}, new ButtonInterface() {

					@Override
					public void buttonPressed() {
						showMenu();
					}
				});
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
				Assets.game.main_menu.game_screen.dispose();
				Assets.game.setScreen(Assets.game.main_menu);
			}
		});
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
						Assets.game.main_menu.game_screen.dispose();
						Assets.game.setScreen(Assets.game.main_menu);
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
		stage.addActor(main_menu);
		stage.addActor(replay);
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
