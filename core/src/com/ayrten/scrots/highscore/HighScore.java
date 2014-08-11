package com.ayrten.scrots.highscore;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class HighScore
{
	private FileHandle handle;

	public HighScore()
	{
	}

	public String getHighScore()
	{
		handle = Gdx.files.internal("data/highscore.txt");

		if (handle.exists())
		{
			if (!handle.readString().isEmpty())
			{
				return handle.readString();
			}
		}
		else
		{
			return "0";
		}

		return "0";
	}

	public void setHighScore(int highscore)
	{
		if (Integer.valueOf(getHighScore()) < highscore)
		{
			handle = Gdx.files.local("data/highscore.txt");
			handle.writeString(String.valueOf(highscore), false);
		}

		// if(handle.exists())
		// {
		// handle.writeString(String.valueOf(highscore), false);
		// }
	}
}
