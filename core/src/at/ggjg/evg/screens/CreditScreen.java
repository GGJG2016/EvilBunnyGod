package at.ggjg.evg.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

/**
 * Created by lschmoli on 19.04.2015.
 */
public class CreditScreen extends Screen {

    private static final String credits = "Pius Pfister [Artwork & Visual Design]\n" +
            "Lorenz Schmoliner [Programming]\n" +
            "Veit Frick [Programming & Production]\n" +
            "Christopher Gallé [Programming]\n" +
            "Christian Zellot [Programming]\n" +
            "Mario Zechner [Programming]\n" +
            "Mathias Lux [Sound, lil‘ bit of Programming]\n" +
            "Gabe Napetschnig [Intro comic]\n\n" +
            "IDEA and GAMEDESIGN all of them!\n\n" +
            "SPECIAL THANKS to the Graz GGJ 2016 team!\n\n" +
            "EvilBunnyGod was developed during the GlobalGamejam 2016, Graz, AT\n\n" +
            "A big THANK YOU to all who have contributed to the game - in whatever way #ggjg #ggj16\n";
    private Stage stage;

    public CreditScreen(ScreenManager manager) {
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

        TextButton button = new TextButton("Back", skin);
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

        Texture texture = new Texture("backgroundImages/menu-ph.jpg");
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        TextureRegion region = new TextureRegion(texture, 0, 0, texture.getWidth(), texture.getHeight());

        table.setBackground(new TextureRegionDrawable(region));

        table.row();
        table.add(scrollPane).fill().expand().row();
        table.add(button).size(150, 60).pad(10);
        Gdx.input.setInputProcessor(stage);
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
