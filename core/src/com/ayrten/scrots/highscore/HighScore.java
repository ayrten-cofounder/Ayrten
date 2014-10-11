package com.ayrten.scrots.highscore;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

public class HighScore {
	private FileHandle handle;

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
		// handle = Gdx.files.internal("data/highscore.txt");
		//
		// if (handle.exists())
		// {
		// if (!handle.readString().isEmpty())
		// {
		// return handle.readString();
		// }
		// }
		// else
		// {
		// return "0";
		// }
		//
		// return "0";
		
		String file = readFile("highscore.txt");
		
		if(!file.isEmpty())
		{
			Json json = new Json();
			Scores scores = json.fromJson(Scores.class, file);
			
			return String.valueOf(scores.first);
		}
		
		return "0";
	}

	public void setHighScore(int highscore) {
		// if (Integer.valueOf(getHighScore()) < highscore)
		// {
		// handle = Gdx.files.local("data/highscore.txt");
		// handle.writeString(String.valueOf(highscore), false);
		// }
		
		if(Integer.valueOf(getHighScore()) < highscore)
		{
			Scores score = new Scores();
			score.first = highscore;

			Json json = new Json();
			writeFile("highscore.txt", json.toJson(score));
		}

		// if(handle.exists())
		// {
		// handle.writeString(String.valueOf(highscore), false);
		// }
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
