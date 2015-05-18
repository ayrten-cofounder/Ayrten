package com.ayrten.scrots.manager;

public class PointsManager {
	protected String file = "pm";

	public static class Points {
		public int points = 0;
	}
	
	public Points points;

	public PointsManager() {
		points = getPoints();
	}

	public int getTotalPoints() {
		String file = Assets.readFile(this.file);

		if (!file.isEmpty()) {
			points = Assets.json.fromJson(Points.class, file);
			return (points.points);
		}

		return 0;
	}

	public void addPoints(int amount) {
		points = getPoints();
		points.points += amount;
		Assets.writeFile(this.file, Assets.json.toJson(points));
	}
	
	public void subtractPoints(int amount) {
		points = getPoints();
		points.points -= amount;
		Assets.writeFile(this.file, Assets.json.toJson(points));
	}

	public Points getPoints() {
		String file = Assets.readFile(this.file);

		if (!file.isEmpty()) {
			return (Assets.json.fromJson(Points.class, file));
		} else {
			points = new Points();
			Assets.writeFile(this.file, Assets.json.toJson(points));
		}

		return new Points();
	}

	public void clearPoints() {
		points = new Points();
		Assets.writeFile(this.file, Assets.json.toJson(points));
	}
}
