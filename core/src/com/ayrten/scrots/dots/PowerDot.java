package com.ayrten.scrots.dots;

import com.ayrten.scrots.manager.Manager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class PowerDot extends Dot
{
	private float DELAY = (float) 0.05; // seconds
	private static final float ADDER = (float) 0.005; // seconds
	protected float ACTIVE_TIME = 0; // seconds
	
	private Timer timer;
	public float time;
	
	protected InputListener powerdot_listener; 
	
	public PowerDot(Texture dot, Manager gm, Sound pop)
	{
		super(dot, gm, pop);
		
		timer = new Timer();
		powerdot_listener = new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				return true;
			}

			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				touchedByAnAngel();
			}
		};
		
		removeListener(listener);
		addListener(powerdot_listener);
	}

	@Override
	public void touchedByAnAngel()
	{
		super.touchedByAnAngel();
		
		beforeAction();
		
		time = ACTIVE_TIME;
		startTime();
	}
	
	// Action to do before timer starts.
	public void beforeAction()
	{
		
	}
	
	// Action to do during the timer
	public void duringAction()
	{
		
	}
	
	// Action to do after timer ends
	public void afterAction()
	{
		
	}
	
	public void startTime()
	{
		timer.scheduleTask(new Task()
		{
			@Override
			public void run()
			{
				float subtract = DELAY + ADDER;
				time = time - subtract;
			
				duringAction();
				
				if(time <= 0)
				{
					afterAction();
					return;
				}
				
				startTime();
			}
		}, DELAY);
	}
}
