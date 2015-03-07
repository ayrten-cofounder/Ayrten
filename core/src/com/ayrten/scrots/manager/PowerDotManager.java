package com.ayrten.scrots.manager;

import com.badlogic.gdx.utils.Json;

public class PowerDotManager {
	protected String file = "power_dots.txt";

	public static class Dots {
		public int magnet_dot = 0;
		public int invincible_dot = 0;
		public int rainbow_dot = 0;
	}

	public Dots dots;

	public PowerDotManager() {
		dots = getPowerDots();
	}

	public int getMagnetDots() {
		String file = Assets.readFile(this.file);

		if (!file.isEmpty()) {
			Json json = new Json();
			Dots dots = json.fromJson(Dots.class, file);

			return (dots.magnet_dot);
		}

		return 0;
	}

	public int getInvincibleDots() {
		String file = Assets.readFile(this.file);

		if (!file.isEmpty()) {
			Json json = new Json();
			Dots dots = json.fromJson(Dots.class, file);

			return (dots.invincible_dot);
		}

		return 0;
	}

	public int getRainbowDots() {
		String file = Assets.readFile(this.file);

		if (!file.isEmpty()) {
			Json json = new Json();
			Dots dots = json.fromJson(Dots.class, file);

			return (dots.rainbow_dot);
		}

		return 0;
	}

	public void setMagnetDotAmount(int amount) {
		Dots dots = getPowerDots();
		Json json = new Json();

		dots.magnet_dot = amount;

		Assets.writeFile(this.file, json.toJson(dots));
	}

	public void setInvincibleDotAmount(int amount) {
		Dots dots = getPowerDots();
		Json json = new Json();

		dots.invincible_dot = amount;

		Assets.writeFile(this.file, json.toJson(dots));
	}

	public void setRainbowDotAmount(int amount) {
		Dots dots = getPowerDots();
		Json json = new Json();

		dots.rainbow_dot = amount;

		Assets.writeFile(this.file, json.toJson(dots));
	}

	public Dots getPowerDots() {
		String file = Assets.readFile(this.file);

		if (!file.isEmpty()) {
			Json json = new Json();
			Dots dots = json.fromJson(Dots.class, file);

			return dots;
		}
		else
		{
			Json json = new Json();
			Dots dots = new Dots();
			
			Assets.writeFile(this.file, json.toJson(dots));
		}

		return new Dots();
	}

	public void clearPowerDots() {
		Dots dots = new Dots();
		Json json = new Json();

		Assets.writeFile(this.file, json.toJson(dots));
	}
}
