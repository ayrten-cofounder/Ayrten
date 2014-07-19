package com.ayrten.scrots.time;

public class Time
{
	int	second	= 00;
	
	public Time()
	{
	}
	
	// Will have to parse the second into X:XX format
	public String getTime()
	{
		return null;
	}
	
	// The change in second added to second
	public void changeTime(int secondDelta)
	{
		second += secondDelta;
	}
}
