package com.ayrten.scrots.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Select;

public class OptionsScreen implements Screen
{
	private ScrotsGame game;
	private Stage stage;
	
	// Actors
	SelectBox bgm;
	SelectBox<String> mode;
	SelectBox<String> bg_color;
	CheckBox  sound_effs;
	
	public OptionsScreen(ScrotsGame game)
	{
		this.game = game;
		stage = new Stage();
		
		Table table = new Table();
		//table.setHeight(Gdx.graphics.getHeight()/3);
		table.setCenterPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/3);
		
		mode = new SelectBox<String>(game.skin);
		mode.setItems("Normal", "Challenge");
		if(!game.prefs.getString("mode").equals(""))
			mode.setSelected(game.prefs.getString("mode"));
		
		sound_effs = new CheckBox("Sound Effects", game.skin);
		sound_effs.setChecked(true);
		if(game.prefs.getBoolean("sound_effs") == false)
			sound_effs.setChecked(false);
		
		bg_color = new SelectBox<String>(game.skin);
		bg_color.setItems("White" , "Black");
		if(!game.prefs.getString("bg_color").equals(""))
			bg_color.setSelected(game.prefs.getString("bg_color"));
		
//		table.add(bgm);
//		table.row();
//		table.add().expand().fill();
//		table.row();
		table.add(mode);
		table.row();
		table.add().expand().fill();
		table.row();
		table.add(sound_effs);
		table.row();
		table.add().expand().fill();
		table.row();
		table.add(bg_color);
		
		stage.addActor(table);
	}
	
	@Override
	public void render(float delta) 
	{
		if(bg_color.getSelected().equals("White"))
			  Gdx.gl.glClearColor(1, 1, 1, 1);
			else
			  Gdx.gl.glClearColor(0, 0, 0, 0);
		
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();
		
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
