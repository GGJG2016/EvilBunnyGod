package at.ggjg.evg.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;

import at.ggjg.evg.helpers.Assets;

/**
 * Created by lschmoli on 19.04.2015.
 */
public class GameOverScreen extends Screen {
    private final AssetManager assMan;
    private SpriteBatch spriteBatch;
    private Texture deadBunny, gameOver;
    private float bunnyWidth;
    private float bunnyHeight;
    Interpolation transitionBunny = Interpolation.bounceOut;
    Interpolation transitionGameOver = Interpolation.fade;
    float progress = 0;


    public GameOverScreen(ScreenManager manager) {
        super(manager);
        assMan = Assets.assMan;
        spriteBatch = new SpriteBatch();
        deadBunny = assMan.get("entityassets/bunny_dead.png");
        gameOver = assMan.get("game_over.png");
        bunnyWidth = deadBunny.getWidth() << 2;
        bunnyHeight = deadBunny.getHeight() << 2;

    }

    @Override
    public void render() {
        if (Gdx.input.isButtonPressed(Input.Keys.ANY_KEY)) {
            manager.setScreen(new MainMenuScreen(manager));
        }

        Gdx.gl.glClearColor(0.8313725490196078f, 0.8392156862745098f, 0.8666666666667f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        float deltaTime = Gdx.graphics.getDeltaTime();
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        progress += Gdx.graphics.getDeltaTime() / 5;
        float val = 1f - transitionBunny.apply(progress);
        bunnyWidth = 128f + 128f * val;
        bunnyHeight = 128f + 128f * val;
        // System.out.println(val);
        spriteBatch.begin();
        spriteBatch.draw(deadBunny, w / 2 - bunnyWidth / 2, h / 2 - bunnyHeight / 2, bunnyWidth, bunnyHeight);
        int goWidth = ((int) (gameOver.getWidth() * transitionGameOver.apply(progress)));
        int goHeight = ((int) (gameOver.getHeight() * transitionGameOver.apply(progress)));
        spriteBatch.draw(gameOver, w / 2 - goWidth / 2, h / 2 - goHeight / 2, goWidth, goHeight);
        spriteBatch.end();


    }

    @Override
    public void dispose() {

    }

    @Override
    public void resize(int width, int height) {

    }
}
