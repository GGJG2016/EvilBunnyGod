package at.ggjg.evg;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

import at.ggjg.evg.screens.IntroScreen;
import at.ggjg.evg.screens.MainMenuScreen;
import at.ggjg.evg.screens.ScreenManager;

public class EvilBunnyGodGame extends ApplicationAdapter {
	ScreenManager manager;
//	AudioManager audio;
	
	@Override
	public void create () {
		manager = new ScreenManager();
		manager.setScreen(new IntroScreen(manager));
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		manager.render();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		manager.resize(width, height);
	}

	@Override
	public void pause () {
		manager.pause();
	}

	@Override
	public void resume () {
		manager.resume();
	}

	@Override
	public void dispose () {
		manager.dispose();
	}
}
