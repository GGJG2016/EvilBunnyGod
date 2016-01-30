package at.ggjg.evg;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;

import java.util.ArrayList;
import java.util.List;

import at.ggjg.evg.gestures.Sequence;
import at.ggjg.evg.gestures.SequenceHolder;

public class EvilBunnyGodGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	Texture touch_helper;
	at.ggjg.evg.gestures.SequenceHolder sequenceHolder;
	List<Sequence> sequenceList;
	ShapeRenderer shapeDebugger;

	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		touch_helper = new Texture("touch_helper.png");

		ArrayList<Sequence> sequenceList = new ArrayList<Sequence>();
		//define sequences .. item which first matches wins
		sequenceList.add(new Sequence(Sequence.SequenceName.CIRCLE, 0,3,6,9,10,11,8,5,2,1));
		sequenceList.add(new Sequence(Sequence.SequenceName.LINE, 0,1,2));
		sequenceHolder = new SequenceHolder(24, sequenceList);

		Gdx.input.setInputProcessor(new GestureDetector(new at.ggjg.evg.gestures.SequenceGestureListener(sequenceHolder, Gdx.graphics.getHeight(), Gdx.graphics.getWidth())));

		shapeDebugger = new ShapeRenderer();
	}

	@Override
	public void render () {
		for (Integer i : sequenceHolder.getLastAreas()) {
			//System.out.print(" -- " + i);
		}

		Sequence match = sequenceHolder.getMatch();
		if(match != null){
			System.out.println("sequence was a circle " + match.getSequenceName());
			sequenceHolder.clearLastArea();
		}
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(touch_helper, 0, 0);
		batch.draw(touch_helper, 150, 150);
		batch.draw(touch_helper, 180, 180);

		int height = Gdx.graphics.getHeight() / 4;
		int width = Gdx.graphics.getWidth() / 3;
		for(int i = 0; i<4; i++){
			shapeDebugger.begin(ShapeRenderer.ShapeType.Line);
			shapeDebugger.setColor(1, 1, 1, 1);
			shapeDebugger.line(0, i*height, Gdx.graphics.getWidth(), i*height);
			shapeDebugger.end();
		}
		for (int i = 0; i < 4; i++) {
			shapeDebugger.begin(ShapeRenderer.ShapeType.Line);
			shapeDebugger.setColor(1, 1, 1, 1);
			shapeDebugger.line(i*width, 0, i*width, Gdx.graphics.getHeight());
			shapeDebugger.end();
		}

		batch.end();
	}
}

// Test oh fuck yeah!
