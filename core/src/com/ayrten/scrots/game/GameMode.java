package com.ayrten.scrots.game;

import java.util.ArrayList;

import com.ayrten.scrots.level.Level;
import com.ayrten.scrots.manager.Manager;
import com.ayrten.scrots.screens.GameScreen;
import com.ayrten.scrots.screens.ScrotsGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

public class GameMode
{
	public static final int NORMAL_MODE = 1;
	public static final int CHALLENGE_MODE = 2;
	
	protected SpriteBatch batch = new SpriteBatch();
	protected Stage stage;
	protected Manager gm;

	protected ArrayList<Level> all_levels = new ArrayList<Level>();
	protected Level curr_level;
	protected CharSequence str = " points";
	protected CharSequence time = "Time left: ";

	protected BitmapFont font_points;
	protected BitmapFont font_time;
	protected ScrotsGame game;
	
	// Widgets
	protected TextField user_name;
	protected Label game_over;
	protected TextButton replay;
	protected TextButton main_menu;
	
	protected int w, h;
	protected boolean should_clear_stage;

	public GameMode(ScrotsGame game, Stage stage, Manager gm, int width, int height)
	{
		this.game = game;
		this.stage = stage;
		this.gm = gm;
		this.w = width;
		this.h = height;
		this.batch = (SpriteBatch) stage.getBatch();
		should_clear_stage = true;
		
		font_points = game.font_16;
		font_time = game.font_16;
		
		Label.LabelStyle overStyle = new Label.LabelStyle();
		overStyle.font = game.font_64;
		
		TextFieldStyle textStyle = new TextFieldStyle();
		textStyle.font = game.font_32;
		
		LabelStyle buttonStyle = new LabelStyle();
		buttonStyle.font = game.font_32;
		
		if(game.prefs.getString("bg_color", "").equals("") 
				|| game.prefs.getString("bg_color", "").equals("White"))
		{
			overStyle.fontColor   = Color.BLACK;
			textStyle.fontColor   = Color.BLACK;
			buttonStyle.fontColor = Color.BLACK;
			font_points.setColor(Color.BLACK);
			font_time.setColor(Color.BLACK);
		}
		else
		{
			overStyle.fontColor   = Color.WHITE;
			textStyle.fontColor   = Color.WHITE;
			buttonStyle.fontColor = Color.WHITE;
			font_points.setColor(Color.WHITE);
			font_time.setColor(Color.WHITE);
		}
		
		replay = new TextButton("", game.skin);
		replay.add(new Label("Replay", buttonStyle));
		replay.setBounds(replay.getX(), replay.getY(), replay.getWidth(), replay.getHeight());
		replay.addListener(new InputListener()
		{
			// Need this or else it won't recognize the touch down event.
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
			{
				return true;
			}
			
			public void touchUp(InputEvent event, float x, float y,	int pointer, int button)
			{
				GameScreen new_game = new GameScreen((ScrotsGame) Gdx.app.getApplicationListener());
				((ScrotsGame) Gdx.app.getApplicationListener()).main_menu.game_screen.dispose();
				((ScrotsGame) Gdx.app.getApplicationListener()).main_menu.game_screen = new_game;
				((ScrotsGame) Gdx.app.getApplicationListener()).setScreen(((ScrotsGame) Gdx.app.getApplicationListener()).main_menu.game_screen);
			}
		});
		
		main_menu = new TextButton("", game.skin);
		main_menu.add(new Label("Main Menu", buttonStyle));
		main_menu.setBounds(main_menu.getX(), main_menu.getY(), main_menu.getWidth(), main_menu.getHeight());
		main_menu.addListener(new InputListener()
		{
			// Need this or else it won't recognize the touch down event.
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
			{
				return true;
			}
			
			public void touchUp(InputEvent event, float x, float y,	int pointer, int button)
			{
				((GameScreen) ((ScrotsGame) Gdx.app.getApplicationListener()).getScreen()).dispose();
				((ScrotsGame) Gdx.app.getApplicationListener()).setScreen(((ScrotsGame) Gdx.app.getApplicationListener()).main_menu);
			}
		});
		
		game_over = new Label("Game Over!", overStyle);
		game_over.setCenterPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/3 * 2);
		
		user_name = new TextField("", textStyle);
		user_name.setMessageText("Enter your name");
		user_name.getStyle().background = new NinePatchDrawable( new NinePatch(new Texture(Gdx.files.internal("data/gray_box.9.png"))));
		user_name.setMaxLength(19);
		user_name.setWidth(textStyle.font.getBounds("01234567890123456789").width);
		user_name.setCenterPosition(Gdx.graphics.getWidth()/2,  Gdx.graphics.getHeight()/3 * 2 - overStyle.font.getLineHeight());
		user_name.setTextFieldListener(new TextFieldListener() 
		{
			public void keyTyped(TextField textField, char key) 
			{
				if(key == '\n'|| Gdx.input.isKeyPressed(Keys.ENTER))
				{
					((ScrotsGame) Gdx.app.getApplicationListener()).main_menu.game_screen.getManager().addHighScore(textField.getText());
					((ScrotsGame) Gdx.app.getApplicationListener()).setScreen(((ScrotsGame) Gdx.app.getApplicationListener()).main_menu);
					// ((GameScreen) ((ScrotsGame) Gdx.app.getApplicationListener()).getScreen()).go_back = true;
				}
			}
		});
		
		Gdx.input.setInputProcessor(this.stage);
		generate();
		gm.startGame();
	}

	public void dispose()
	{
		batch.dispose();
	}
	
	public void resize(int width, int height)
	{

	}

	protected void generate()
	{
		// Generate the first 20 levels.
		for (int i = 1; i < 20; i++)
		{
			Level lvl = new Level(i, w, h, gm);
			all_levels.add(lvl);
		}

		setStage();
	}

	public void render()
	{
		if (gm.isGameOver())
		{
			gameOver();
		}
		else
		{
			point();
			time();

			stage.draw();
			if (curr_level.level_clear())
			{
				levelClear();
			}
		}
	}

	public void point()
	{
		batch.begin();
		font_points.draw(batch, String.valueOf(gm.get_player_score()) + str, Gdx.graphics.getWidth()/20, Gdx.graphics.getHeight() - (Gdx.graphics.getHeight()/20));
		batch.end();

	}

	public void time()
	{
		batch.begin();
		font_time.draw(batch, time + gm.getTime(), Gdx.graphics.getWidth()/20, Gdx.graphics.getHeight() - (Gdx.graphics.getHeight()/20) - font_points.getLineHeight());
		batch.end();
	}

	public void gameOver()
	{
		if(should_clear_stage)
		{
			stage.clear();
			should_clear_stage = false;
			stage.addActor(game_over);
			if(gm.get_player_score() > gm.getScoreBoard().getLowestHighScore())
			{
				stage.addActor(user_name);
			}
			else
			{
				Table table = new Table();
				table.setSkin(game.skin);
				table.setCenterPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
				table.add(replay);
				table.row();
				table.add("").height(Gdx.graphics.getHeight()/50);
				table.row();
				table.add(main_menu);
				stage.addActor(table);
			}
		}
		
		stage.draw();
	}

	public void levelClear()
	{
		// One point for clearing a level
		stage.clear();
		gm.plusOnePoint();

		setStage();
	}

	public void setStage()
	{
		curr_level = all_levels.remove(0);
		gm.setLevel(curr_level);
		for (int i = 0; i < curr_level.get_blue_dots().size(); i++)
		{
			stage.addActor(curr_level.get_blue_dots().get(i));
		}

		for (int i = 0; i < curr_level.get_red_dots().size(); i++)
		{
			stage.addActor(curr_level.get_red_dots().get(i));
		}
		
		for (int i = 0; i < curr_level.get_grn_dots().size(); i++)
		{
			stage.addActor(curr_level.get_grn_dots().get(i));
		}
		
		for (int i = 0; i < curr_level.get_baby_blue_dots().size(); i++)
		{
			stage.addActor(curr_level.get_baby_blue_dots().get(i));
		}
		for (int i = 0; i < curr_level.get_grn_dots().size(); i++)
		{
			stage.addActor(curr_level.get_grn_dots().get(i));
		}
	}
}
