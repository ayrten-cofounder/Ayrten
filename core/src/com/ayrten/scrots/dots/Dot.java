package com.ayrten.scrots.dots;

import com.ayrten.scrots.manager.Manager;
import com.ayrten.scrots.screens.ScrotsGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class Dot extends Actor
{
	private static final float SIZE_RATIO = 15;
	
	private Texture dot;

	public Manager gm;

	public Sound pop;

	public Dot(Texture dot, Manager gm, Sound pop)
	{
		this.dot = dot;
		this.gm = gm;
		this.pop = pop;
		setBounds(getX(), getY(), dot.getWidth(), dot.getHeight());
		// An InputListener is a subclass of EventListener
		addListener(new InputListener()
		{
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button)
			{
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button)
			{

				// Gotta get touched by an angel ;)
				  touchedByAnAngel();

				// Remove the actor from the stage.
				event.getTarget().remove();
			}
		});
	}

	private float getCircleWidth()
	{
		return (float) (gm.w / SIZE_RATIO);
	}

	private float getCircleHeight()
	{
		return (float) (gm.w / SIZE_RATIO);
	}

	// This class shall be overriddent by the blue, green, red dots
	public void touchedByAnAngel()
	{
		if(((ScrotsGame) Gdx.app.getApplicationListener()).prefs.getBoolean("sound_effs"))
		  pop.play();
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
		batch.draw(dot, getX(), getY(), getCircleWidth(), getCircleHeight());
	}
}
