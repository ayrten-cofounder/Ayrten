package com.ayrten.scrots.shop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

public class PointsManager {
	protected String file = "points_manager2.txt";

	public static class Points {
		public int points = 0;
	}

	public Points points;

	public PointsManager() {
		points = getPoints();
	}

	public int getTotalPoints() {
		String file = readFile(this.file);

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

		writeFile(this.file, json.toJson(points));
	}

	public Points getPoints() {
		String file = readFile(this.file);

		if (!file.isEmpty()) {
			Json json = new Json();
			Points points = json.fromJson(Points.class, file);

			return points;
		} else {
			Json json = new Json();
			Points points = new Points();

			writeFile(this.file, json.toJson(points));
		}

		return new Points();
	}

	public void clearPoints() {
		Points points = new Points();
		Json json = new Json();

		writeFile(this.file, json.toJson(points));
	}

	public static void writeFile(String fileName, String s) {
		FileHandle file = Gdx.files.local(fileName);
		file.writeString(com.badlogic.gdx.utils.Base64Coder.encodeString(s),
				false);
	}

	public static String readFile(String fileName) {
		FileHandle file = Gdx.files.local(fileName);
		if (file != null && file.exists()) {
			String s = file.readString();
			if (!s.isEmpty()) {
				return com.badlogic.gdx.utils.Base64Coder.decodeString(s);
			}
		}
		return "";
	}
}
