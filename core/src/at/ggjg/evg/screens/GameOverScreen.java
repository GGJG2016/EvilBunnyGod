package at.ggjg.evg.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

import at.ggjg.evg.helpers.Assets;

/**
 * Created by lschmoli on 19.04.2015.
 */
public class GameOverScreen extends Screen {
    private final AssetManager assMan;
    private SpriteBatch spriteBatch;
    private Texture deadBunny;
    private float bunnyWidth;
    private float bunnyHeight;

    public GameOverScreen(ScreenManager manager) {
        super(manager);
        assMan = Assets.assMan;
        spriteBatch = new SpriteBatch();
        deadBunny = assMan.get("entityassets/bunny_dead.png");
        bunnyWidth = deadBunny.getWidth() << 2;
        bunnyHeight = deadBunny.getHeight() << 2;

    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0.8313725490196078f, 0.8392156862745098f, 0.8666666666667f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        float deltaTime = Gdx.graphics.getDeltaTime();
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        if (bunnyWidth > 128) {
            bunnyWidth -= 160*Gdx.graphics.getDeltaTime();
            bunnyHeight -= 160*Gdx.graphics.getDeltaTime();
        }
        spriteBatch.begin();
        spriteBatch.draw(deadBunny, w/2 - bunnyWidth/2, h/2 - bunnyHeight/2, bunnyWidth, bunnyHeight);
        spriteBatch.end();
    }

    @Override
    public void dispose() {

    }

    @Override
    public void resize(int width, int height) {

    }
}
