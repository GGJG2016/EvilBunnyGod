package at.ggjg.evg;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import at.ggjg.evg.screens.GameplayScreen;
import at.ggjg.evg.screens.Screen;
import at.ggjg.evg.screens.ScreenManager;

public class EvilBunnyGodGame extends ApplicationAdapter {
	ScreenManager manager;
	AudioManager audio;
	
	@Override
	public void create () {
		manager = new ScreenManager();
		Screen screen = new GameplayScreen(manager,1);
		manager.setScreen(screen);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		manager.render();
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
