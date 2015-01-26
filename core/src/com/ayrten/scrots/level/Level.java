package com.ayrten.scrots.level;

import java.util.ArrayList;

import com.ayrten.scrots.dots.DWD_PenDot1;
import com.ayrten.scrots.dots.DWD_PenDot2;
import com.ayrten.scrots.dots.DWD_RegDot1;
import com.ayrten.scrots.dots.DWD_RegDot2;
import com.ayrten.scrots.dots.DotGenerator;
import com.ayrten.scrots.dots.PenDot1;
import com.ayrten.scrots.dots.PenDot2;
import com.ayrten.scrots.dots.RegDot1;
import com.ayrten.scrots.dots.RegDot2;
import com.ayrten.scrots.screens.Manager;

public class Level
{
	private static final int GREEN_DOT_START = 0;
	private static final int RED_DOT_START = 1;
	private static final int BLUE_DOT_START = 2;
	private static final int BABY_BLUE_DOT_START = 5;
	
	// Modifiers for generating dots
	protected static final float GREEN_DOT_MOD = 1.5f;
	protected static final float RED_DOT_MOD = 1.3f;
	protected static final float BLUE_DOT_MOD = 1.2f;
	protected static final float BABY_BLUE_DOT_MOD = 1.25f;
	
	protected static final float YELLOW_DOT_MOD = 0.3f;
	protected static final float ORANGE_DOT_MOD = 0.3f;
	protected static final float BROWN_DOT_MOD = 0.3f;
	protected static final float PINK_DOT_MOD = 0.3f;
	
	// Cap for dots
	protected static final int GREEN_DOT_CAP = 15;
	protected static final int RED_DOT_CAP = 15;
	protected static final int BLUE_DOT_CAP = 15;
	protected static final int BABY_BLUE_DOT_CAP = 15;
	
	// DWD (Dot Within Dot) Weight
	protected static final int DWD_WEIGHT = 2;

	// Stores all the dots for that level.
	private ArrayList<RegDot1> greenDots;
	private ArrayList<PenDot1> redDots;
	private ArrayList<PenDot2> blueDots;
	private ArrayList<RegDot2> babyBlueDots;
	
	private ArrayList<DWD_RegDot1> dwdGreenDots;
	private ArrayList<DWD_PenDot1> dwdRedDots;
	private ArrayList<DWD_PenDot2> dwdBlueDots;
	private ArrayList<DWD_RegDot2> dwdBabyBlueDots;

	private int level;
	private DotGenerator generator;
	
	private int number_of_green_dots;

	public Level(int level, int width, int height, Manager gm)
	{
		assert level >= 0;
		this.level = level;
		// Maybe should move this up to the GameMode level? - Tony
		generator = new DotGenerator(width, height, gm);

//		generator = new DotGenerator(width, height, gm);
				
		greenDots = new ArrayList<RegDot1>();
		redDots = new ArrayList<PenDot1>();
		blueDots = new ArrayList<PenDot2>();
		babyBlueDots = new ArrayList<RegDot2>();
		
		dwdGreenDots = new ArrayList<DWD_RegDot1>();
		dwdRedDots = new ArrayList<DWD_PenDot1>();
		dwdBlueDots = new ArrayList<DWD_PenDot2>();
		dwdBabyBlueDots = new ArrayList<DWD_RegDot2>();

		gen_grn_dots();
		gen_red_dots();
		gen_blue_dots();
		gen_baby_blue_dots();
		
		number_of_green_dots = greenDots.size();
	}

	public boolean level_clear()
	{
		return number_of_green_dots <= 0 ? true : false;
	}
	
	public void minusGreenDot()
	{
		number_of_green_dots--;
	}

	public ArrayList<RegDot1> get_grn_dots()
	{
		return greenDots;
	}

	public ArrayList<PenDot1> get_red_dots()
	{
		return redDots;
	}

	public ArrayList<PenDot2> get_blue_dots()
	{
		return blueDots;
	}

	public ArrayList<RegDot2> get_baby_blue_dots()
	{
		return babyBlueDots;
	}
	
	public ArrayList<DWD_RegDot1> get_dwd_grn_dots()
	{
		return dwdGreenDots;
	}

	public ArrayList<DWD_PenDot1> get_dwd_red_dots()
	{
		return dwdRedDots;
	}

	public ArrayList<DWD_PenDot2> get_dwd_blue_dots()
	{
		return dwdBlueDots;
	}

	public ArrayList<DWD_RegDot2> get_dwd_baby_blue_dots()
	{
		return dwdBabyBlueDots;
	}

	private void gen_grn_dots()
	{
		greenDots.clear();
		int num = (int) Math.floor((level - GREEN_DOT_START) * GREEN_DOT_MOD);
		
		if	(num > GREEN_DOT_CAP)
		{
			int yellow_num = (int) Math.floor(level * YELLOW_DOT_MOD);
			num = num - (yellow_num * DWD_WEIGHT);
			
			for (int i = 0; i < yellow_num; i++)
			{
				DWD_RegDot1 dot = generator.genDWDRegDot1();
				dwdGreenDots.add(dot);
			}
		}
		
		for (int i = 0; i < num; i++)
		{
			RegDot1 dot = generator.genRegDot1();
			greenDots.add(dot);
		}
	}

	private void gen_red_dots()
	{
		redDots.clear();
		int num = (int) Math.floor((level - RED_DOT_START) * RED_DOT_MOD);
		
		if	(num > RED_DOT_CAP)
		{
			int brown_num = (int) Math.floor(level * BROWN_DOT_MOD);
			num = num - (brown_num * DWD_WEIGHT);
			
			for (int i = 0; i < brown_num; i++)
			{
				DWD_PenDot1 dot = generator.genDWDPenDot1();
				dwdRedDots.add(dot);
			}
		}
		
		for (int i = 0; i < num; i++)
		{
			PenDot1 dot = generator.genPenDot1();
			redDots.add(dot);
		}
	}

	private void gen_blue_dots()
	{
		blueDots.clear();
		int num = (int) Math.floor((level - BLUE_DOT_START) * BLUE_DOT_MOD);
		
		if	(num > BLUE_DOT_CAP)
		{
			int pink_num = (int) Math.floor(level * BLUE_DOT_MOD);
			num = num - (pink_num * DWD_WEIGHT);
			
			for (int i = 0; i < pink_num; i++)
			{
				DWD_PenDot2 dot = generator.genDWDPenDot2();
				dwdBlueDots.add(dot);
			}
		}
		
		for (int i = 0; i < num; i++)
		{
			PenDot2 dot = generator.genPenDot2();
			blueDots.add(dot);
		}
	}

	private void gen_baby_blue_dots()
	{
		babyBlueDots.clear();
		int num = (int) Math.floor((level - BABY_BLUE_DOT_START) * BABY_BLUE_DOT_MOD);
		
		if	(num > BABY_BLUE_DOT_CAP)
		{
			int orange_num = (int) Math.floor(level * ORANGE_DOT_MOD);
			num = num - (orange_num * DWD_WEIGHT);
			
			for (int i = 0; i < orange_num; i++)
			{
				DWD_RegDot2 dot = generator.genDWDRegDot2();
				dwdBabyBlueDots.add(dot);
			}
		}
		
		for (int i = 0; i < num; i++)
		{
			RegDot2 dot = generator.genRegDot2();
			babyBlueDots.add(dot);
		}
	}

}
