package at.ggjg.evg.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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

import at.ggjg.evg.State;
import at.ggjg.evg.helpers.Assets;

/**
 * Created by lschmoli on 19.04.2015.
 */
public class IntroScreen extends Screen {
    enum IntroState {PreLoading, Loading, Showing, Finished}

    ;

    enum PanelState {p1, p2, p3}

    ;
    IntroState state = IntroState.PreLoading;
    PanelState panelState = PanelState.p1;
    SpriteBatch batch;
    AssetManager assMan = Assets.assMan;
    Texture[] anim = new Texture[10];

    float tb_x, tb_y, tm_x, tm_y; // position of panels front and back

    float panelShow = 0;

    public IntroScreen(ScreenManager manager) {
        super(manager);
        batch = new SpriteBatch();
        // give work to assMan
        assMan.load("load/loading-screen-1.png", Texture.class);
        assMan.load("load/loading-screen-2.png", Texture.class);
        assMan.load("load/loading-screen-3.png", Texture.class);
        assMan.load("load/loading-screen-4.png", Texture.class);
        assMan.load("load/loading-screen-5.png", Texture.class);
        assMan.load("load/loading-screen-6.png", Texture.class);
        assMan.load("load/loading-screen-7.png", Texture.class);
        assMan.load("load/loading-screen-8.png", Texture.class);
        assMan.load("load/loading-screen-9.png", Texture.class);
        assMan.load("load/loading-screen-10.png", Texture.class);

        // assMan.finishLoadingAsset("load/loading-screen-10.png");

        assMan.load("intro/loading.png", Texture.class);
        assMan.load("intro/p1_front.png", Texture.class);
        assMan.load("intro/p1_middle.png", Texture.class);
        assMan.load("intro/p1_back.png", Texture.class);
        assMan.load("intro/p2_front.png", Texture.class);
        assMan.load("intro/p2_middle.png", Texture.class);
        assMan.load("intro/p2_back.png", Texture.class);
        assMan.load("intro/p3_front.png", Texture.class);
        assMan.load("intro/p3_middle.png", Texture.class);
        assMan.load("intro/p3_back.png", Texture.class);

        assMan.load("entityassets/bunny_dead.png", Texture.class);
        assMan.load("game_over.png", Texture.class);

        audioManager.setNewState(State.INTRO);

    }

    @Override
    public void render() {
        // define states:
        if (state == IntroState.PreLoading && assMan.isLoaded("load/loading-screen-1.png")) {
            state = IntroState.Loading;
        }

        if (state == IntroState.Loading) {
            for (int i = 0; i < anim.length; i++) {
                if (assMan.isLoaded("load/loading-screen-" + (i + 1) + ".png"))
                    anim[i] = assMan.get("load/loading-screen-" + (i + 1) + ".png");
            }
        }

        if (assMan.update() && state == IntroState.Loading && panelShow > 3) {
            state = IntroState.Showing;
            panelShow = 0f;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.ANY_KEY) && state == IntroState.Showing) {
            state = IntroState.Finished;
        }

        if (state == IntroState.Finished) {
            manager.setScreen(new MainMenuScreen(manager));
            // manager.setScreen(new GameOverScreen(manager));
        }

        Gdx.gl.glClearColor(0.8313725490196078f, 0.8392156862745098f, 0.8666666666667f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        float deltaTime = Gdx.graphics.getDeltaTime();
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        batch.begin();
        if (state == IntroState.PreLoading) {

        } else {
            if (state == IntroState.Loading) {
                int index = ((int) Math.floor(panelShow * 8)) %10;
                if (anim[index] == null) index = 0;
                batch.draw(anim[index], w / 2 - anim[index].getWidth()/2, h / 2 - anim[index].getHeight()/2);
                panelShow += deltaTime;
            } else if (state == IntroState.Showing) {
                panelShow += deltaTime;
                if (panelShow > 15 && panelState == PanelState.p3) {
                    state = IntroState.Finished;
                } else if (panelShow > 10 && panelState == PanelState.p2) {
                    panelState = PanelState.p3;
                    resetMotion();
                } else if (panelShow > 5 && panelState == PanelState.p1) {
                    panelState = PanelState.p2;
                    resetMotion();
                }

                if (panelState == PanelState.p1) {
                    tm_y += 5 * deltaTime;
                    tb_y += 2 * deltaTime;
                    Texture tb, tm, tf;
                    int tw, th;
                    tb = ((Texture) assMan.get("intro/p1_back.png"));
                    tm = ((Texture) assMan.get("intro/p1_middle.png"));
                    tf = ((Texture) assMan.get("intro/p1_front.png"));
                    tw = tb.getWidth() / 2;
                    th = tb.getHeight() / 2;
                    float x = w / 2 - tw / 2;
                    float y = h / 2 - th / 2;
                    batch.draw(tb, x + tb_x, y + tb_y, tw, th);
                    batch.draw(tm, x - tm_y / 2, y, tw + tm_y, th + tm_y);
                    batch.draw(tf, w / 2 - tf.getWidth() / 4, h / 2 - tf.getHeight() / 4, tf.getWidth() / 2, tf.getHeight() / 2);
                } else if (panelState == PanelState.p2) {
                    tm_y += 2 * deltaTime;
                    tb_y += 2 * deltaTime;
                    Texture tb, tm, tf;
                    int tw, th;
                    tb = ((Texture) assMan.get("intro/p2_back.png"));
                    tm = ((Texture) assMan.get("intro/p2_middle.png"));
                    tf = ((Texture) assMan.get("intro/p2_front.png"));
                    tw = tb.getWidth() / 2;
                    th = tb.getHeight() / 2;
                    float x = w / 2 - tw / 2;
                    float y = h / 2 - th / 2;
                    batch.draw(tb, x - tb_y / 2, y - tb_y / 2, tw + tb_y, th + tb_y);
                    batch.draw(tm, x + tm_y, y, tw, th);
                    batch.draw(tf, w / 2 - tf.getWidth() / 4, h / 2 - tf.getHeight() / 4, tf.getWidth() / 2, tf.getHeight() / 2);
                } else {
                    tm_y += 6 * deltaTime;
                    tb_y += 3 * deltaTime;
                    Texture tb, tm, tf;
                    int tw, th;
                    tb = ((Texture) assMan.get("intro/p3_back.png"));
                    tm = ((Texture) assMan.get("intro/p3_middle.png"));
                    tf = ((Texture) assMan.get("intro/p3_front.png"));
                    tw = tb.getWidth() / 2;
                    th = tb.getHeight() / 2;
                    float x = w / 2 - tw / 2;
                    float y = h / 2 - th / 2;
                    batch.draw(tb, x - tb_y / 2, y - tb_y / 2, tw + tb_y, th + tb_y);
                    batch.draw(tm, x - tm_y / 2, y - tm_y / 2, tw + tm_y, th + tm_y);
                    batch.draw(tf, w / 2 - tf.getWidth() / 4, h / 2 - tf.getHeight() / 4, tf.getWidth() / 2, tf.getHeight() / 2);
                }
            }
        }
        batch.end();
    }

    private void resetMotion() {
        tb_x = 0;
        tb_y = 0;
        tm_x = 0;
        tm_y = 0;
    }


    @Override
    public void dispose() {

    }

    @Override
    public void resize(int width, int height) {

    }
}
