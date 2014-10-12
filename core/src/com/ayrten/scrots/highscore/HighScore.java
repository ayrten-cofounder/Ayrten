package com.ayrten.scrots.highscore;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

public class HighScore {
	protected String file = "highscore.txt";

	public static class Scores {
		public int first = 0;
		public int second = 0;
		public int third = 0;
		public int fourth = 0;
		public int fifth = 0;
	}

	public HighScore() {
	}

	public String getHighScore() {
		String file = readFile(this.file);

		if (!file.isEmpty()) {
			Json json = new Json();
			Scores scores = json.fromJson(Scores.class, file);

			return String.valueOf(scores.first);
		}

		return "0";
	}

	public Scores getAllScores() {
		String file = readFile(this.file);

		if (!file.isEmpty()) {
			Json json = new Json();
			Scores scores = json.fromJson(Scores.class, file);

			return scores;
		}

		return new Scores();
	}

	public void setHighScore(int highscore) {
		Scores scores = getAllScores();
		Json json = new Json();
		
		if(scores.first < highscore)
		{
			scores.fifth = scores.fourth;
			scores.fourth = scores.third;
			scores.third = scores.second;
			scores.second = scores.first;
			scores.first = highscore;
		}
		else if(scores.second < highscore)
		{
			scores.fifth = scores.fourth;
			scores.fourth = scores.third;
			scores.third = scores.second;
			scores.second = highscore;
		}
		else if(scores.third < highscore)
		{
			scores.fifth = scores.fourth;
			scores.fourth = scores.third;
			scores.third = highscore;
		}
		else if(scores.fourth < highscore)
		{
			scores.fifth = scores.fourth;
			scores.fourth = highscore;
		}
		else if(scores.fifth < highscore)
		{
			scores.fifth = highscore;
		}
		
		writeFile(this.file, json.toJson(scores));
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
