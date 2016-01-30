package at.ggjg.evg;

import at.ggjg.evg.screens.MainMenuScreen;
import at.ggjg.evg.screens.Screen;
import at.ggjg.evg.screens.ScreenManager;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class EvilBunnyGodGame extends ApplicationAdapter {
	ScreenManager manager;
	AudioManager audio;
	
	@Override
	public void create () {
		manager = new ScreenManager();
		Screen screen = new MainMenuScreen(manager);
		manager.setScreen(screen);
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
