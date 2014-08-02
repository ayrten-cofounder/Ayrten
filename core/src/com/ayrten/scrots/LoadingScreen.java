package com.ayrten.scrots;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;

public class LoadingScreen implements Screen 
{
	ScrotsGame game;
	// Here, Music is better than Sound cause it's a longer. Sound should only be for short file like sounds effects.
	// Sound bg;
	Music bg;
	
	public LoadingScreen(ScrotsGame game)
	{
		this.game = game;
		// bg = Gdx.audio.newSound(Gdx.files.internal("sounds/Naoki Sato - Final Wish.mp3"));
		// bg = Gdx.audio.newMusic(Gdx.files.internal("sounds/Naoki Sato - Final Wish.mp3"));
		bg = Gdx.audio.newMusic(Gdx.files.internal("sounds/Shinji Orito - Yume no Ato I.mp3"));
	}

	@Override
	public void render(float delta) 
	{
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		game.batch.begin();
		game.font.draw(game.batch, "SCROTS", Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
		game.font.draw(game.batch, "by Ayrten", Gdx.graphics.getWidth()/2 + 50, Gdx.graphics.getHeight()/2 - 100);
		game.batch.end();

		if(Gdx.input.isTouched())
		{
			game.setScreen(new MainMenuScreen(game));
			bg.stop();
			dispose();
		}
	}
	
	@Override
	public void dispose() {
		bg.dispose();
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void show() {
		bg.play();
		bg.setLooping(true);
	}

	@Override
	public void hide() {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}
}
