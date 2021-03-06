package at.ggjg.evg.screens;

import at.ggjg.evg.AudioManager;
import at.ggjg.evg.State;
import at.ggjg.evg.gestures.Sequence;
import at.ggjg.evg.gestures.SequenceFactory;
import at.ggjg.evg.gestures.SequenceHolder;
import at.ggjg.evg.mechanic.World;
import at.ggjg.evg.mechanic.WorldRenderer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

/**
 * Created by Veit on 29.01.2016.
 */
public class GameplayScreen extends Screen {
    World world;
    WorldRenderer renderer;
    AudioManager audio;
    int lvl;
    SequenceHolder sequenceHolder;

    public GameplayScreen(ScreenManager manager, int lvl) {
        super(manager);
        audio = AudioManager.getInstance();
        audio.setNewState(State.IDLE);
        this.lvl = lvl;
        switch (lvl) {
            case 0:
                world = new World("levels/testMap.tmx");
                audio.playRandomGameLowLoop();
                break;
            case 1:
                world = new World("levels/level_1.tmx");
                audio.playRandomGameLowLoop();
                break;
            case 2:
                world = new World("levels/level_2.tmx");
                audio.playRandomGameLowLoop();
                break;
            case 100:
                world = new World("levels/oneBunnyLevel.tmx");
        }
        world.level = lvl;
        renderer = new WorldRenderer(world);
        audio = AudioManager.getInstance();
        world.setRenderer(renderer);

        world.setAudio(audio);
        world.setManager(manager);
        initGestures();
        Gdx.input.setInputProcessor(new GestureDetector(new at.ggjg.evg.gestures.SequenceGestureListener(world, sequenceHolder, Gdx.graphics.getHeight(), Gdx.graphics.getWidth())));

    }

    private void initGestures() {
        ArrayList<Sequence> sequenceList = new ArrayList<Sequence>();
        //define sequences .. item which first matches wins
        sequenceList.add(SequenceFactory.createSquare(6, 7, 8, 13, 18, 17, 16, 11, 6));
        sequenceList.add(SequenceFactory.createSquare(6, 11, 16, 17, 18, 13, 8, 7, 6));
        sequenceList.add(SequenceFactory.createSquare(8, 7, 6, 11, 16, 17, 18, 13, 8));
        sequenceList.add(SequenceFactory.createSquare(8, 13, 18, 17, 16, 11, 6, 7, 8));
        sequenceList.add(SequenceFactory.createSquare(16, 11, 6, 7, 8, 13, 18, 17, 16));
        sequenceList.add(SequenceFactory.createSquare(16, 17, 18, 13, 8, 7, 6, 11, 16));
        //sequenceList.add(SequenceFactory.createHeart(7, 1, 0, 5, 10, 16, 22, 18, 14, 9, 3, 7));
        //sequenceList.add(SequenceFactory.createHorizontalLine(0, 1, 2, 3, 4));
        sequenceList.add(SequenceFactory.createVerticalLine(2, 7, 12, 17, 22));
        sequenceList.add(SequenceFactory.createVerticalLine(22, 17, 12, 7, 2));
        sequenceList.add(SequenceFactory.createHorizontalLine(10, 11, 12, 13, 14));
        sequenceList.add(SequenceFactory.createHorizontalLine(14, 13, 12, 11, 10));
        sequenceHolder = new SequenceHolder(24, sequenceList);
    }


    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();

        Sequence match = sequenceHolder.getMatch();
        if (match != null) {
            System.out.println("sequence was a " + match.getSequenceName());
            if (world.currentClickedObj != null) {
                world.currentClickedObj.onGestureDrawn(match.getSequenceName());
            }
            sequenceHolder.clearLastArea();
        }
        renderer.render(delta);
        audio.update(delta);
        world.update(delta);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        renderer.resize(width, height);
    }

    @Override
    public void dispose() {
        renderer.dispose();
        audio.dispose();
        world.dispose();
    }
}
