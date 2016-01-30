package at.ggjg.evg.screens;

import com.badlogic.gdx.Gdx;

import at.ggjg.evg.AudioManager;
import at.ggjg.evg.mechanic.World;
import at.ggjg.evg.mechanic.WorldRenderer;

/**
 * Created by Veit on 29.01.2016.
 */
public class GameplayScreen extends Screen {
    World world;
    WorldRenderer renderer;
    AudioManager audio;
    int lvl;

    public GameplayScreen (ScreenManager manager, int lvl) {
        super(manager);
        this.lvl = lvl;
        switch(lvl){
            case 0: world = new World("levels/testMap.tmx");
                break;
            case 1: world = new World("levels/map1_v2.tmx");
                break;
            case 2: world = new World("levels/map2_v1.tmx");
                break;
        }
        renderer = new WorldRenderer(world);
        audio = new AudioManager();
        world.setRenderer(renderer);
        world.setAudio(audio);
    }

    @Override
    public void render () {
       float delta = Gdx.graphics.getDeltaTime();
//
//        if(delta > 1.0f)
//            delta = 0.0f;
//
        renderer.render(delta);
        audio.update(delta);
        world.update(delta);
//
//        if(world.player.isDead()) {
//            manager.setScreen(new GameOverScreen(manager));
//        } else if(world.player.isWin()) {
//            if(lvl==1)
//            {
//                manager.setScreen(new GameplayScreen(manager,2));
//            }
//            else{
//                manager.setScreen(new WinScreen(manager));
//            }
//        }
//
//        if(Gdx.input.isKeyPressed(Keys.ESCAPE)) {
//            Gdx.app.exit();
//        }
    }

    @Override
    public void dispose () {
//        renderer.dispose();
//        audio.dispose();
//        world.dispose();
    }
}
