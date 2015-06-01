package com.ayrten.scrots.level;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

import com.ayrten.scrots.dots.MovingDot;
import com.ayrten.scrots.dots.penalty.DWD_PenDot1;
import com.ayrten.scrots.dots.penalty.DWD_PenDot2;
import com.ayrten.scrots.dots.penalty.PenDot1;
import com.ayrten.scrots.dots.penalty.PenDot2;
import com.ayrten.scrots.dots.regular.DWD_RegDot1;
import com.ayrten.scrots.dots.regular.DWD_RegDot2;
import com.ayrten.scrots.dots.regular.OrderDot;
import com.ayrten.scrots.dots.regular.RegDot1;
import com.ayrten.scrots.dots.regular.RegDot2;
import com.ayrten.scrots.manager.Assets;
import com.ayrten.scrots.manager.Manager;

public class Level
{
	protected static final int GREEN_DOT_START = 0;
	protected static final int RED_DOT_START = 1;
	protected static final int BLUE_DOT_START = 2;
	protected static final int BABY_BLUE_DOT_START = 5;
	
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

	protected LinkedList<MovingDot> allDots;
	protected int number_of_green_dots;
	protected boolean firstDWD_regdot1, firstDWD_regdot2, firstDWD_pendot1, firstDWD_pendot2;
	protected Manager gm;
	
	protected ArrayList<RegDot1> reg_dots;
	
	public Level(int level, Manager gm)
	{
		assert level >= 0;
		this.gm = gm;
		allDots = new LinkedList<MovingDot>();
		
		firstDWD_regdot1 = false;
		firstDWD_regdot2 = false;
		firstDWD_pendot1 = false;
		firstDWD_pendot2 = false;
		
		reg_dots = new ArrayList<RegDot1>();
		
		gen_grn_dots(level);
		gen_red_dots(level);
		gen_blue_dots(level);
		gen_baby_blue_dots(level);

		Collections.shuffle(allDots);
		Collections.shuffle(reg_dots);
	}

	public boolean level_clear() {
		return number_of_green_dots <= 0 ? true : false;
	}
	
	public void addGreenDot() {
		number_of_green_dots++;
	}
	
	public void minusGreenDot() {
		number_of_green_dots--;
	}
	
	public void addRegDot(RegDot1 reg_dot) {
		reg_dots.add(reg_dot);
	}

	public LinkedList<MovingDot> getDotList() {
		return allDots;
	}
	
	public ArrayList<RegDot1>getRegDotList() {
		return reg_dots;
	}
	
	protected void gen_grn_dots(int level)
	{
		int num = (int) Math.floor((level - GREEN_DOT_START) * GREEN_DOT_MOD);
		
		if	(num > GREEN_DOT_CAP)
		{
			int yellow_num = (int) Math.floor(level * YELLOW_DOT_MOD);
			num = GREEN_DOT_CAP;
			
			for (int i = 0; i < yellow_num; i++)
			{
				DWD_RegDot1 dot = gm.generator.genDWDRegDot1();
				allDots.add(dot);
			}
			
			firstDWD_regdot1 = Assets.prefs.getBoolean("firstDWD_regdot1_help", true);
		}
		
		for (int i = 0; i < num; i++)
		{
			RegDot1 dot = gm.generator.genRegDot1();
			allDots.add(dot);
			reg_dots.add(dot);
		}
		
		// Tony (6/1/2015) - DO NOT DELETE!! Example for OrderDot...
//		LinkedList<OrderDot> tempList = new LinkedList<OrderDot>();
//		for (int i = 1; i <= 9; i++)
//		{			
//			OrderDot dot = gm.generator.genOrderDot(i);
//			allDots.add(dot);
//			reg_dots.add(dot);
//			tempList.add(dot);
//		}
//		
//		for(int i = 0; i < tempList.size(); i++) {
//			OrderDot dot = tempList.get(i);
//			dot.setFirstDot(tempList.getFirst());
//			dot.setLastDot(tempList.getLast());
//			if(i != tempList.size() - 1) {
//				dot.setNextDot(tempList.get(i + 1));
//			}
//		}
		
		number_of_green_dots = num;
	}

	protected void gen_red_dots(int level)
	{
		int num = (int) Math.floor((level - RED_DOT_START) * RED_DOT_MOD);
		
		if	(num > RED_DOT_CAP)
		{
			int brown_num = (int) Math.floor(level * BROWN_DOT_MOD);
			num = num - (brown_num * DWD_WEIGHT);
			
			for (int i = 0; i < brown_num; i++)
			{
				DWD_PenDot1 dot = gm.generator.genDWDPenDot1();
				allDots.add(dot);
			}
			
			 firstDWD_pendot1 = Assets.prefs.getBoolean("firstDWD_pendot1_help", true);
		}
		
		for (int i = 0; i < num; i++)
		{
			PenDot1 dot = gm.generator.genPenDot1();
			allDots.add(dot);
		}
	}

	protected void gen_blue_dots(int level)
	{
		int num = (int) Math.floor((level - BLUE_DOT_START) * BLUE_DOT_MOD);
		
		if	(num > BLUE_DOT_CAP)
		{
			int pink_num = (int) Math.floor(level * BLUE_DOT_MOD);
			num = num - (pink_num * DWD_WEIGHT);
			
			for (int i = 0; i < pink_num; i++)
			{
				DWD_PenDot2 dot = gm.generator.genDWDPenDot2();
				allDots.add(dot);
			}
			
			 firstDWD_pendot2 = Assets.prefs.getBoolean("firstDWD_pendot2_help", true);
		}
		
		for (int i = 0; i < num; i++)
		{
			PenDot2 dot = gm.generator.genPenDot2();
			allDots.add(dot);
		}
	}

	protected void gen_baby_blue_dots(int level)
	{
		int num = (int) Math.floor((level - BABY_BLUE_DOT_START) * BABY_BLUE_DOT_MOD);
		
		if	(num > BABY_BLUE_DOT_CAP)
		{
			int orange_num = (int) Math.floor(level * ORANGE_DOT_MOD);
			num = num - (orange_num * DWD_WEIGHT);
			
			for (int i = 0; i < orange_num; i++)
			{
				DWD_RegDot2 dot = gm.generator.genDWDRegDot2();
				allDots.add(dot);
			}
			
			 firstDWD_regdot1 = Assets.prefs.getBoolean("firstDWD_regdot2_help", true);
		}
		
		for (int i = 0; i < num; i++)
		{
			RegDot2 dot = gm.generator.genRegDot2();
			allDots.add(dot);
		}
	}
	
	public void setFirst(String key, boolean enable) {
		if(key.equals("firstDWD_regdot1_help"))  {
			Assets.prefs.putBoolean("firstDWD_regdot1_help", enable);
		} else if(key.equals("firstDWD_regdot2_help")) {
			Assets.prefs.putBoolean("firstDWD_regdot2_help", enable);
		} else if(key.equals("firstDWD_pendot1_help")) {
			Assets.prefs.putBoolean("firstDWD_pendot1_help", enable);
		} else if(key.equals("firstDWD_pendot2_help")) {
			Assets.prefs.putBoolean("firstDWD_pendot2_help", enable);
		}
		Assets.prefs.flush();
	}
	
	public boolean getFirst(String key) {
		boolean ret_val = false;
		
		if(key.equals("firstDWD_regdot1_help"))  {
			ret_val = Assets.prefs.getBoolean("firstDWD_regdot1_help", true) && firstDWD_regdot1;
		} else if(key.equals("firstDWD_regdot2_help")) {
			ret_val = Assets.prefs.getBoolean("firstDWD_regdot2_help", true) && firstDWD_regdot2;
		} else if(key.equals("firstDWD_pendot1_help")) {
			ret_val = Assets.prefs.getBoolean("firstDWD_pendot1_help", true) && firstDWD_pendot1;
		} else if(key.equals("firstDWD_pendot2_help")) {
			ret_val = Assets.prefs.getBoolean("firstDWD_pendot2_help", true) && firstDWD_pendot2;
		}
		
		return ret_val;
	}
}
