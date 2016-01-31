package at.ggjg.evg.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
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

/**
 * Created by lschmoli on 19.04.2015.
 */
public class GameOverScreen extends Screen {

    private static final String credits = "Game Over!";
    private Stage stage;

    public GameOverScreen(ScreenManager manager) {
        super(manager);
        initUI();
    }

    private void initUI() {
        this.stage = new Stage();
        ScreenManager.multiplexer.addProcessor(this.stage);
        final Skin skin = new Skin(Gdx.files.internal("mainmenu/uiskin.json"));

        Table scrollTable = new Table();

        Label text = new Label("powered by libGDX\n", skin);
        scrollTable.add(text);
        scrollTable.row();

        text = new Label(credits, skin);
        text.setAlignment(Align.center);
        text.setWrap(true);
        scrollTable.add(text);
        scrollTable.row();

        TextButton button = new TextButton("Main Menu", skin);
        button.setColor(Color.GREEN);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                manager.setScreen(new MainMenuScreen(manager));
            }
        });
        ScrollPane scrollPane = new ScrollPane(scrollTable);
        Table table = new Table();
        table.setFillParent(true);

        Texture texture = new Texture("entityassets/opfer.png");
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        TextureRegion region = new TextureRegion(texture, 0, 0, texture.getWidth(), texture.getHeight());

        table.setBackground(new TextureRegionDrawable(region));

        table.row();
        table.add(scrollPane).fill().expand().row();
        table.add(button).size(150, 60).pad(10);

        this.stage.addActor(table);
    }

    @Override
    public void render() {
        this.stage.act();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        this.stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }
}
