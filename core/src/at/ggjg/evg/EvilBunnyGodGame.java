package at.ggjg.evg;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;

import java.util.ArrayList;

import at.ggjg.evg.gestures.Sequence;
import at.ggjg.evg.gestures.SequenceHolder;
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
//		ArrayList<Sequence> sequenceList = new ArrayList<Sequence>();
//		//define sequences .. item which first matches wins
//		sequenceList.add(new Sequence(Sequence.SequenceName.CIRCLE, 0, 3, 6, 9, 10, 11, 8, 5, 2, 1));
//		sequenceList.add(new Sequence(Sequence.SequenceName.LINE, 0, 1, 2));
//		SequenceHolder sequenceHolder = new SequenceHolder(24, sequenceList);
//		Gdx.input.setInputProcessor(new GestureDetector(new at.ggjg.evg.gestures.SequenceGestureListener(sequenceHolder, Gdx.graphics.getHeight(), Gdx.graphics.getWidth())));
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


//		Sequence match = sequenceHolder.getMatch();
//		if(match != null){
//			System.out.println("sequence was a circle " + match.getSequenceName());
//			sequenceHolder.clearLastArea();
//		}
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
