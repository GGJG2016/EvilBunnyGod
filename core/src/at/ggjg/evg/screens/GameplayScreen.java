package at.ggjg.evg.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.input.GestureDetector;

import java.util.ArrayList;

import at.ggjg.evg.AudioManager;
import at.ggjg.evg.gestures.Sequence;
import at.ggjg.evg.gestures.SequenceHolder;
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
    SequenceHolder sequenceHolder;

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
        ArrayList<Sequence> sequenceList = new ArrayList<Sequence>();
		//define sequences .. item which first matches wins
		sequenceList.add(new Sequence(Sequence.SequenceName.CIRCLE, 0, 3, 6, 9, 10, 11, 8, 5, 2, 1));
		sequenceList.add(new Sequence(Sequence.SequenceName.LINE, 0, 1, 2));
		sequenceHolder = new SequenceHolder(24, sequenceList);
		Gdx.input.setInputProcessor(new GestureDetector(new at.ggjg.evg.gestures.SequenceGestureListener(sequenceHolder, Gdx.graphics.getHeight(), Gdx.graphics.getWidth())));
    }

    @Override
    public void render () {
       float delta = Gdx.graphics.getDeltaTime();
//
//        if(delta > 1.0f)
//            delta = 0.0f;
//
        Sequence match = sequenceHolder.getMatch();
		if(match != null){
			System.out.println("sequence was a " + match.getSequenceName());
			sequenceHolder.clearLastArea();
		}
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
