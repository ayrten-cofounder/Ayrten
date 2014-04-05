package com.ayrten.scrots.dots;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Dot
{
	private Texture	dot;
	private Vector2	vector;
	
	private int		x, y = 0;
	
	public Dot()
	{
		vector = new Vector2(250, 20);
	}
	
	public Dot(Texture dot)
	{
		this.dot = dot;
	}
	
	public void setTexture(Texture dot)
	{
		this.dot = dot;
	}
	
	public Texture getTexture()
	{
		return dot;
	}
	
	public void setPosition(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	private void setVector(int x, int y)
	{
		vector.set(x, y);
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
}
