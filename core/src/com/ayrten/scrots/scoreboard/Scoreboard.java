package com.ayrten.scrots.scoreboard;

import com.ayrten.scrots.manager.Assets;

public class Scoreboard {
	protected String file = "hs_mde";

	public static class Scores {
		public int first = 0;
		public int second = 0;
		public int third = 0;
		public int fourth = 0;
		public int fifth = 0;
		
		public String first_name = "n/a";
		public String second_name = "n/a";
		public String third_name = "n/a";
		public String fourth_name = "n/a";
		public String fifth_name = "n/a";
	}

	public Scores scores;
	public Scoreboard() 
	{
		scores = getAllScores();
	}

	public int getLowestHighScore() {
		String file = Assets.readFile(this.file);

		if (!file.isEmpty()) {
			scores = Assets.json.fromJson(Scores.class, file);
			return (scores.fifth);
		}

		return 0;
	}

	public Scores getAllScores() {
		String file = Assets.readFile(this.file);

		if (!file.isEmpty()) {
			return (Assets.json.fromJson(Scores.class, file));
		}

		return new Scores();
	}

	public void addHighScore(int highscore, String name) {
		scores = getAllScores();
		
		if(scores.first < highscore)
		{
			scores.fifth = scores.fourth;
			scores.fourth = scores.third;
			scores.third = scores.second;
			scores.second = scores.first;
			scores.first = highscore;
			
			scores.fifth_name = scores.fourth_name;
			scores.fourth_name = scores.third_name;
			scores.third_name = scores.second_name;
			scores.second_name = scores.first_name;
			scores.first_name = name;
		}
		else if(scores.second < highscore)
		{
			scores.fifth = scores.fourth;
			scores.fourth = scores.third;
			scores.third = scores.second;
			scores.second = highscore;
			
			scores.fifth_name = scores.fourth_name;
			scores.fourth_name = scores.third_name;
			scores.third_name = scores.second_name;
			scores.second_name = name;
		}
		else if(scores.third < highscore)
		{
			scores.fifth = scores.fourth;
			scores.fourth = scores.third;
			scores.third = highscore;
			
			scores.fifth_name = scores.fourth_name;
			scores.fourth_name = scores.third_name;
			scores.third_name = name;
		}
		else if(scores.fourth < highscore)
		{
			scores.fifth = scores.fourth;
			scores.fourth = highscore;
			
			scores.fifth_name = scores.fourth_name;
			scores.fourth_name = name;
		}
		else if(scores.fifth < highscore)
		{
			scores.fifth = highscore;
			
			scores.fifth_name = name;
		}
		
		Assets.writeFile(this.file, Assets.json.toJson(scores));
	}
	
	public void clearScoreboard()
	{
		scores = new Scores();
		Assets.writeFile(this.file, Assets.json.toJson(scores));
	}
}
