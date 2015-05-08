package com.ayrten.scrots.manager;

import com.badlogic.gdx.utils.Json;

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
			Json json = new Json();
			Points points = json.fromJson(Points.class, file);
			
			return (points.points);
		}

		return 0;
	}

	public void addPoints(int amount) {
		Points points = getPoints();
		Json json = new Json();

		points.points += amount;

		Assets.writeFile(this.file, json.toJson(points));
	}

	public Points getPoints() {
		String file = Assets.readFile(this.file);

		if (!file.isEmpty()) {
			Json json = new Json();
			Points points = json.fromJson(Points.class, file);
			
			return points;
		} else {
			Json json = new Json();
			Points points = new Points();

			Assets.writeFile(this.file, json.toJson(points));
		}

		return new Points();
	}

	public void clearPoints() {
		Points points = new Points();
		Json json = new Json();

		Assets.writeFile(this.file, json.toJson(points));
	}
}
