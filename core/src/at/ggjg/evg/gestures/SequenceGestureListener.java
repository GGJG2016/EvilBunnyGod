package at.ggjg.evg.gestures;

import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by zelle on 30.01.2016.
 */
public class SequenceGestureListener implements GestureDetector.GestureListener {

    private final int NR_COL = 3;
    private final int NR_ROWS = 4;

    private SequenceHolder sequenceHolder;
    private int height;
    private int width;

    public SequenceGestureListener(SequenceHolder sequenceHolder, int height, int width) {
        this.sequenceHolder = sequenceHolder;
        this.height = height;
        this.width = width;
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        int height = this.height / NR_ROWS;
        int width = this.width / NR_COL;
        int row = (((int) (y / height))) * NR_COL;
        int col = (int) (x / width);
        int area = Math.max(row, 0) + col;
        //System.out.println("col "+((int) (x / width)) + " row "+(row));
        sequenceHolder.addToLastAreas(area);

        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        sequenceHolder.stopPanning();
        sequenceHolder.clearTmpLastArea();
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
