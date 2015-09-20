package com.ayrten.scrots.manager;

import com.ayrten.scrots.common.Assets;

public class PowerDotManager {
	protected String file = "pd";

	public static class Dots {
		public int magnet_dot = 0;
		public int invincible_dot = 0;
		public int rainbow_dot = 0;
		public int decelerate_dot = 0;

		public boolean magnet_dot_unlock = false;
		public boolean invincible_dot_unlock = false;
		public boolean rainbow_dot_unlock = false;
		public boolean decelerate_dot_unlock = false;
	}

	public Dots dots;

	public PowerDotManager() {
		dots = getPowerDots();
	}
	
	public boolean isDecelDotUnlocked() {
		dots = getPowerDots();
		return (dots.decelerate_dot_unlock);
	}

	public boolean isMagnetDotUnlocked() {
		dots = getPowerDots();
		return (dots.magnet_dot_unlock);
	}
	
	public boolean isInvincibleDotUnlocked() {
		dots = getPowerDots();
		return (dots.invincible_dot_unlock);
	}
	
	public boolean isRainbowDotUnlocked() {
		dots = getPowerDots();
		return (dots.rainbow_dot_unlock);
	}
	
	public void unlockDecelDot() {
		dots.decelerate_dot_unlock = true;
		Assets.writeFile(this.file, Assets.json.toJson(dots));
	}

	public void unlockMagnetDot() {
		dots.magnet_dot_unlock = true;
		Assets.writeFile(this.file, Assets.json.toJson(dots));
	}

	public void unlockInvincibleDot() {
		dots.invincible_dot_unlock = true;
		Assets.writeFile(this.file, Assets.json.toJson(dots));
	}

	public void unlockRainbowDot() {
		dots.rainbow_dot_unlock = true;
		Assets.writeFile(this.file, Assets.json.toJson(dots));
	}
	
	public int getDecelDots() {
		dots = getPowerDots();
		return (dots.decelerate_dot);
	}

	public int getMagnetDots() {
		dots = getPowerDots();
		return (dots.magnet_dot);
	}

	public int getInvincibleDots() {		
		dots = getPowerDots();
		return(dots.invincible_dot);
	}

	public int getRainbowDots() {
		dots = getPowerDots();
		return (dots.rainbow_dot);
	}

	public void setMagnetDotAmount(int amount) {
		dots.magnet_dot = amount;
		Assets.writeFile(this.file, Assets.json.toJson(dots));
	}

	public void setInvincibleDotAmount(int amount) {
		dots.invincible_dot = amount;
		Assets.writeFile(this.file, Assets.json.toJson(dots));
	}

	public void setRainbowDotAmount(int amount) {
		dots.rainbow_dot = amount;
		Assets.writeFile(this.file, Assets.json.toJson(dots));
	}

	public Dots getPowerDots() {
		if(dots != null)
			return dots;
		String file = Assets.readFile(this.file);

		if (!file.isEmpty()) {
			dots = Assets.json.fromJson(Dots.class, file);
			return dots;
		} else {
			dots = new Dots();
			Assets.writeFile(this.file, Assets.json.toJson(dots));
		}

		return new Dots();
	}

	public void clearPowerDots() {
		dots = new Dots();
		Assets.writeFile(this.file, Assets.json.toJson(dots));
	}
}
