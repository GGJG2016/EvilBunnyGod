package at.ggjg.evg.screens;

/**
 * Created by Veit on 29.01.2016.
 */

import at.ggjg.evg.AudioManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;

public abstract class Screen {
    protected final ScreenManager manager;
    public AudioManager audioManager = AudioManager.getInstance();
    public boolean keyPressed = false;
    protected InputAdapter adapter;

    public Screen(ScreenManager manager) {
        this.manager = manager;

        adapter = new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Keys.ESCAPE)
                    Gdx.app.exit();

                keyPressed = true;
                ScreenManager.multiplexer.removeProcessor(adapter);
                return true;
            }
        };

        ScreenManager.multiplexer.addProcessor(adapter);
    }

    public Screen(ScreenManager manager, AudioManager audioManager) {
        this(manager);
        this.audioManager = audioManager;
    }

    public abstract void render();

    public abstract void dispose();

    public void pause() {
    }

    public void resume() {
    }

    protected boolean waitForKeypress() {
        if (Gdx.input.justTouched()) {
            return true;
        }

        return keyPressed;
    }

    public void resize(int width, int height) {

    }
}