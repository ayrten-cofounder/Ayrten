package com.ayrten.scrots.dots;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class Dot extends Actor
{
	private Texture	dot;
	
	public Dot(Texture dot)
	{
		this.dot = dot;
		setBounds(getX(), getY(), dot.getWidth(), dot.getHeight());
		// An InputListener is a subclass of EventListener
		addListener(new InputListener(){
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
			{
				return true;
			}
			
			public void touchUp(InputEvent event, float x, float y, int pointer, int button)
			{
				// Remove the actor from the stage.
				event.getTarget().remove();
			}
		});
	}
	
	public void setTexture(Texture dot)
	{
		this.dot = dot;
	}
	
	public Texture getTexture()
	{
		return dot;
	}
	
	public void setPosition(float x, float y)
	{
		setX(x);
		setY(y);
	}
	
	// Overridden functions.
	@Override
	public void draw(Batch batch, float alpha)
	{
		batch.draw(dot, getX(), getY());
	}
}
