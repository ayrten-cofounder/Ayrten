package com.ayrten.scrots.time;

import com.ayrten.scrots.manager.Manager;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class Time
{
	private static final float START_TIME = (float) 60.00; // seconds
	private static final float DELAY = (float) 0.01; // seconds
	private static final float ADDER = (float) 0.005; // seconds
	private float second = START_TIME;
	
	private Manager gm;
	private Timer timer;

	public Time(Manager gm)
	{
		this.gm = gm;
		timer = new Timer();
	}

	private boolean timeOver()
	{
		return second <= 0 ? true : false;
	}

	// Will have to parse the second into X:XX format
	public String getTime()
	{
		return String.valueOf(second);
	}
	
	public float getFloatTime()
	{
		return second;
	}

	// The change in second added to second
	public void addTime(float secondDelta)
	{
		second += secondDelta;
	}

	// I was thinking about using a runnable class but lets try their Timer
	// class first.
	public void startTime()
	{
		// Schedules a task to do something in x seconds, where x is DELAY.
		timer.scheduleTask(new Task()
		{
			@Override
			public void run()
			{
				if(timeOver())
				{
					gm.gameOver();
				}
				
				// Subtract for the countdown
				// Subtracting more because it is slow
				float subtract = DELAY + ADDER;
				second = second - subtract;

				// Schedule again for countdown
				startTime();
			}
		}, DELAY);
	}
	
	public void pauseTime()
	{
		timer.clear();
	}
}
