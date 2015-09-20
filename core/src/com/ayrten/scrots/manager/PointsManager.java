package com.ayrten.scrots.manager;

import com.ayrten.scrots.common.Assets;

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
		points = getPoints();
		return (points.points + 1000000);
	}

	public void addPoints(int amount) {
		points.points += amount;
		Assets.writeFile(this.file, Assets.json.toJson(points));
	}
	
	public void subtractPoints(int amount) {
		points.points -= amount;
		Assets.writeFile(this.file, Assets.json.toJson(points));
	}

	public Points getPoints() {
		if(points != null)
			return points;
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
		points = getPoints();
		points.points = 0;
		Assets.writeFile(this.file, Assets.json.toJson(points));
	}
}
