package com.ayrten.scrots;

import java.util.ArrayList;

import com.ayrten.scrots.dots.Dot;
import com.ayrten.scrots.dots.GreenDot;
import com.ayrten.scrots.dots.Level;
import com.ayrten.scrots.dots.RandomDotGenerator;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Scrots implements ApplicationListener
{
	private RandomDotGenerator	generator;
	
	private SpriteBatch			batch;
	private Texture				texture;
	TextureRegion				region;
	
	Stage						stage;
	Image						image;
	
	ArrayList<Dot>				dot_array	= new ArrayList<Dot>();
	
	private int					w;
	private int					h;
	
	Level level;
	boolean passed;
	boolean init;
	
	// Image image;
	
	@Override
	public void create()
	{
		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		generator = new RandomDotGenerator(w, h);
		batch = new SpriteBatch();
		
		// position = new Vector2(50, 50);
		
		level = new Level(1, w, h);
		passed = false;
		init = true;
		
		Dot dot = generator.getRandomDot();
		image = new Image(dot.getTexture());
		image.setPosition(dot.getX(), dot.getY());
		
		image.addListener(new ClickListener()
		{
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button)
			{
				dot_array.clear();
				return true;
			}
		});
		
		stage.addActor(image);
	}
	
	@Override
	public void dispose()
	{
		batch.dispose();
		texture.dispose();
	}
	
	@Override
	public void render()
	{
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(Gdx.graphics.getDeltaTime());
		
		if (Gdx.input.isTouched())
		{
			dot_array.add(generator.getRandomDot());
		}
		
		stage.draw();
		
		batch.begin();
		
		if(init)
		{
			ArrayList<GreenDot> greens = level.get_grn_dots();
			for(GreenDot dot: greens)
			{
				batch.draw(dot.getTexture(), dot.getX(),  dot.getY());
			}
		}
		else
		{
		  for (Dot dot : dot_array)
		  {
			
		  }
		}
		
		batch.end();
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
