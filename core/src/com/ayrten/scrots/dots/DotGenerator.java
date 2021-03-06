package com.ayrten.scrots.dots;

import java.util.Random;

import com.ayrten.scrots.manager.Assets;
import com.ayrten.scrots.manager.Manager;

public class DotGenerator {
	private Random random = new Random();
	private Manager gm;

	public DotGenerator(Manager gm) {
		this.gm = gm;
	}

	public RegDot1 genRegDot1() {
		RegDot1 regDot = new RegDot1(Assets.regDot_1, gm, Assets.reg_pop_1);
		setRandPositions(regDot);
		return regDot;
	}

	public RegDot2 genRegDot2() {
		RegDot2 regDot = new RegDot2(Assets.regDot_2, gm, Assets.reg_pop_2);
		setRandPositions(regDot);
		return regDot;
	}

	public RegDot3 genRegDot3() {
		RegDot3 regDot = new RegDot3(Assets.explosion_dot, gm, Assets.reg_pop_1);
		return regDot;
	}

	public PenDot1 genPenDot1() {
		PenDot1 penDot = new PenDot1(Assets.penDot_1, gm, Assets.button_pop);
		setRandPositions(penDot);
		return penDot;
	}

	public PenDot2 genPenDot2() {
		PenDot2 penDot = new PenDot2(Assets.penDot_2, gm, Assets.pen_pop_2);
		setRandPositions(penDot);
		return penDot;
	}

	public DWD_RegDot1 genDWDRegDot1() {
		DWD_RegDot1 dwdRegDot = new DWD_RegDot1(Assets.dwdRegDot_1, gm,
				Assets.reg_pop_1);
		setRandPositions(dwdRegDot);
		return dwdRegDot;
	}

	public DWD_RegDot2 genDWDRegDot2() {
		DWD_RegDot2 dwdRegDot = new DWD_RegDot2(Assets.dwdRegDot_2, gm,
				Assets.reg_pop_2);
		setRandPositions(dwdRegDot);
		return dwdRegDot;
	}

	public DWD_PenDot1 genDWDPenDot1() {
		DWD_PenDot1 dwdPenDot = new DWD_PenDot1(Assets.dwdPenDot_1, gm,
				Assets.button_pop);
		setRandPositions(dwdPenDot);
		return dwdPenDot;
	}

	public DWD_PenDot2 genDWDPenDot2() {
		DWD_PenDot2 dwdPenDot = new DWD_PenDot2(Assets.dwdPenDot_2, gm,
				Assets.pen_pop_2);
		setRandPositions(dwdPenDot);
		return dwdPenDot;
	}

	public PowerDot_Rainbow genPowerDotRainbow() {
		PowerDot_Rainbow rainbowDot = new PowerDot_Rainbow(Assets.rainbow_dot,
				gm, Assets.reg_pop_1);
		setRandPositions(rainbowDot);
		return rainbowDot;
	}

	public PowerDot_Invincible genPowerDotInvincible() {
		PowerDot_Invincible invincibleDot = new PowerDot_Invincible(
				Assets.invincible_dot, gm, Assets.reg_pop_1);
		setRandPositions(invincibleDot);
		return invincibleDot;
	}

	public PowerDot_Magnet genPowerDotMagnet() {
		PowerDot_Magnet magnetDot = new PowerDot_Magnet(Assets.magnet_dot, gm,
				Assets.reg_pop_1);
		setRandPositions(magnetDot);
		return magnetDot;
	}

	public void setRandPositions(Dot target) {
		float range = gm.max_width - target.getWidth() - gm.min_width;
		float x = gm.min_width + random.nextFloat() * range;
		
		range = gm.max_height - target.getHeight() - gm.min_height;
		float y = gm.min_height + random.nextFloat() * range;

		target.setPosition(x, y);
	}
}
