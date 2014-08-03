package com.ayrten.scrots.dots;

import java.util.ArrayList;

import com.ayrten.scrots.manager.Manager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Level 
{
	// Stores all the dots for that level.
	private ArrayList<GreenDot> greenDots;
	private ArrayList<RedDot>	redDots;
	private ArrayList<BlueDot>  blueDots;
	
	static final Texture red_texture = new Texture(Gdx.files.internal("data/red_dot.png"));
	static final Texture grn_texture = new Texture(Gdx.files.internal("data/green_dot.png"));
	static final Texture blu_texture = new Texture(Gdx.files.internal("data/blue_dot.png"));
	
	private int level;
	private DotGenerator generator;
	
	public Level(int level, int width, int height, Manager gm)
	{
		assert level >= 0;
		this.level = level;
		generator = new DotGenerator(width, height, gm);
		
		greenDots = new ArrayList<GreenDot>();
		redDots = new ArrayList<RedDot>();
		blueDots = new ArrayList<BlueDot>();
		
		gen_grn_dots(); 
		gen_red_dots();
		gen_blue_dots();
	}
	
	public boolean level_clear()
	{
		return greenDots.isEmpty();
	}
	
	public ArrayList<GreenDot> get_grn_dots()
	{
		return greenDots;
	}
	
	public ArrayList<RedDot> get_red_dots()
	{
		return redDots;
	}
	
	public ArrayList<BlueDot> get_blue_dots()
	{
		return blueDots;
	}
	
	private void gen_grn_dots()
	{
		greenDots.clear();
		int num = (int) Math.floor(level * 1.5);
		for(int i = 0; i < num; i++)
		{
			GreenDot dot = generator.genGreenDot();
			greenDots.add(dot);
		}
	}
	
	private void gen_red_dots()
	{
		redDots.clear();
		int num = (int) Math.floor((level - 1) * 1.5);
		for(int i = 0; i < num; i++)
		{
			RedDot dot = generator.genRedDot();
			redDots.add(dot);
		}
	}
	
	private void gen_blue_dots()
	{
		blueDots.clear();
		int num = (int) Math.floor((level - 2) * 1.5);
		for(int i = 0; i < num; i++)
		{
			BlueDot dot = generator.genBlueDot();
			blueDots.add(dot);
		}
	}

}
