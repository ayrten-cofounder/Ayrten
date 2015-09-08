package com.ayrten.scrots.scoreboard;

public class TimeScoreboard extends Scoreboard
{
	public TimeScoreboard()	{}
	
	@Override
	public Scores getAllScores() {
		this.file = "hsnrm_mde";
		return super.getAllScores();
	}
}
