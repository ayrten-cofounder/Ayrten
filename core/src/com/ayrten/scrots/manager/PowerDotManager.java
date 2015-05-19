package com.ayrten.scrots.manager;

public class PowerDotManager {
	protected String file = "pd";

	public static class Dots {
		public int magnet_dot = 0;
		public int invincible_dot = 0;
		public int rainbow_dot = 0;

		// 0 == locked :: 1 == unlocked
		public int magnet_dot_unlock = 0;
		public int invincible_dot_unlock = 0;
		public int rainbow_dot_unlock = 0;
	}

	public Dots dots;

	public PowerDotManager() {
		dots = getPowerDots();
	}

	public boolean isMagnetDotUnlocked() {
		String file = Assets.readFile(this.file);

		if (!file.isEmpty()) {
			dots = Assets.json.fromJson(Dots.class, file);
			return dots.magnet_dot_unlock == 1 ? true : false;
		}

		return false;
	}
	
	public boolean isInvincibleDotUnlocked() {
		String file = Assets.readFile(this.file);

		if (!file.isEmpty()) {
			dots = Assets.json.fromJson(Dots.class, file);
			return dots.invincible_dot_unlock == 1 ? true : false;
		}

		return false;
	}
	
	public boolean isRainbowDotUnlocked() {
		String file = Assets.readFile(this.file);

		if (!file.isEmpty()) {
			dots = Assets.json.fromJson(Dots.class, file);
			return dots.rainbow_dot_unlock == 1 ? true : false;
		}

		return false;
	}

	public void unlockMagnetDot() {
		dots = getPowerDots();
		dots.magnet_dot_unlock = 1;
		Assets.writeFile(this.file, Assets.json.toJson(dots));
	}

	public void unlockInvincibleDot() {
		dots = getPowerDots();
		dots.invincible_dot_unlock = 1;
		Assets.writeFile(this.file, Assets.json.toJson(dots));
	}

	public void unlockRainbowDot() {
		dots = getPowerDots();
		dots.rainbow_dot_unlock = 1;

		Assets.writeFile(this.file, Assets.json.toJson(dots));
	}

	public int getMagnetDots() {
		String file = Assets.readFile(this.file);

		if (!file.isEmpty()) {
			dots = Assets.json.fromJson(Dots.class, file);
			return (dots.magnet_dot);
		}

		return 0;
	}

	public int getInvincibleDots() {
		String file = Assets.readFile(this.file);

		if (!file.isEmpty()) {
			dots = Assets.json.fromJson(Dots.class, file);
			return (dots.invincible_dot);
		}

		return 0;
	}

	public int getRainbowDots() {
		String file = Assets.readFile(this.file);

		if (!file.isEmpty()) {
			dots = Assets.json.fromJson(Dots.class, file);
			return (dots.rainbow_dot);
		}

		return 0;
	}

	public void setMagnetDotAmount(int amount) {
		dots = getPowerDots();
		dots.magnet_dot = amount;

		Assets.writeFile(this.file, Assets.json.toJson(dots));
	}

	public void setInvincibleDotAmount(int amount) {
		dots = getPowerDots();
		dots.invincible_dot = amount;

		Assets.writeFile(this.file, Assets.json.toJson(dots));
	}

	public void setRainbowDotAmount(int amount) {
		dots = getPowerDots();
		dots.rainbow_dot = amount;

		Assets.writeFile(this.file, Assets.json.toJson(dots));
	}

	public Dots getPowerDots() {
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
