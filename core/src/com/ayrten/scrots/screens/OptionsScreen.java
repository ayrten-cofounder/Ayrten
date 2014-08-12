package com.ayrten.scrots.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class OptionsScreen implements Screen
{
	private ScrotsGame game;
	private Stage stage;
	
	// Actors
	private SelectBox<String> mode;
	private SelectBox<String> bg_color;
	private CheckBox  sound_effs;
	
	private float label_pad_left = (float) 5.5; // Lower # = more left
	
	public OptionsScreen(ScrotsGame game)
	{
		this.game = game;
		stage = new Stage();
		
		Table table = new Table();
		// table.setHeight(Gdx.graphics.getHeight());
		// table.setWidth(Gdx.graphics.getWidth());
		table.setFillParent(true);
		table.setSkin(game.skin);
		
		LabelStyle style = new LabelStyle();
		style.font = game.font_16;
		
		mode = new SelectBox<String>(game.skin);
		mode.setItems("Normal", "Challenge");
		if(!game.prefs.getString("mode", "").equals(""))
			mode.setSelected(game.prefs.getString("mode"));
		
		sound_effs = new CheckBox("", game.skin);
		sound_effs.setChecked(true);
		if(game.prefs.getBoolean("sound_effs", true) == false)
			sound_effs.setChecked(false);
		
		bg_color = new SelectBox<String>(game.skin);
		bg_color.setItems("White" , "Black");
		if(!game.prefs.getString("bg_color", "").equals(""))
			bg_color.setSelected(game.prefs.getString("bg_color"));
		
		TextButton back = new TextButton("", game.skin);
		back.add(new Label("Back", style));
		back.setBounds(back.getX(), back.getY(), back.getWidth(), back.getHeight());
		back.setPosition(0, Gdx.graphics.getHeight() - back.getHeight());
		// back.setPosition(Gdx.graphics.getWidth()/2, 0);
		back.addListener(new InputListener()
		{
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
			{
				return true;
			}
			
			public void touchUp(InputEvent event, float x, float y,	int pointer, int button)
			{
				if(((ScrotsGame) Gdx.app.getApplicationListener()).prefs.getBoolean("sound_effs"))
				  ((ScrotsGame) Gdx.app.getApplicationListener()).pop.play();
				((ScrotsGame) Gdx.app.getApplicationListener()).setScreen(((ScrotsGame) Gdx.app.getApplicationListener()).main_menu);
			}
		});
		
		table.add(back).left().top();
		table.row();
		table.add("").height(Gdx.graphics.getHeight()/5 * 2);
		table.row();
		table.add(new Label("Game Mode: ", style)).left().padLeft((float) (Gdx.graphics.getWidth()/label_pad_left));
		table.add(mode).center().padLeft(Gdx.graphics.getWidth()/6);
		table.row();
		table.add("").height(Gdx.graphics.getHeight()/50);
		table.row();
		table.add(new Label("Background: ", style)).left().padLeft((float) (Gdx.graphics.getWidth()/label_pad_left));
		table.add(bg_color).center().padLeft(Gdx.graphics.getWidth()/6);
		table.row();
		table.add("").height(Gdx.graphics.getHeight()/50);
		table.row();
		table.add(new Label("Sound Effects: ", style)).left().padLeft((float) (Gdx.graphics.getWidth()/label_pad_left));
		table.add(sound_effs).center().padLeft(Gdx.graphics.getWidth()/6);
		
		table.left().top();
		stage.addActor(table);
	}
	
	@Override
	public void render(float delta) 
	{
		if(bg_color.getSelected().equals("White"))
		{
			setOptsColor(stage, Color.BLACK);
			Gdx.gl.glClearColor(1, 1, 1, 1);
		}
		else
		{
			setOptsColor(stage, Color.WHITE);
			Gdx.gl.glClearColor(0, 0, 0, 0);
		}
			
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();
		
		game.prefs.putBoolean("sound_effs", sound_effs.isChecked());
		
		if(Gdx.input.isKeyPressed(Input.Keys.BACKSPACE))
			game.setScreen(game.main_menu);
	}
	
	@Override
	public void show() 
	{
		Gdx.input.setInputProcessor(stage);
	}
	
	@Override
	public void hide() 
	{
		game.prefs.putString("mode", mode.getSelected());
		game.prefs.putString("bg_color", bg_color.getSelected());
		game.prefs.putBoolean("sound_effs", sound_effs.isChecked());
	}
	
	@Override
	public void dispose() 
	{
		game.prefs.putString("mode", mode.getSelected());
		game.prefs.putString("bg_color", bg_color.getSelected());
		game.prefs.putBoolean("sound_effs", sound_effs.isChecked());
		stage.dispose();
	}
	
	public void setOptsColor(Stage stage, Color color)
	{
		for(Actor actor : stage.getActors())
		{
			if(actor.toString().equals("Table"))
			{
				for(Actor table_actor : ((Table) actor).getChildren())
				{
					if(table_actor.toString().equals("Label"))
						table_actor.setColor(color);
				}
			}
		}
	}
	
	@Override
	public void resize(int width, int height) 
	{
		
	}

	@Override
	public void pause() 
	{
		
	}

	@Override
	public void resume() 
	{
		
	}
}
