package at.ggjg.evg.gestures;

import at.ggjg.evg.helpers.OnMapClickedListener;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by zelle on 30.01.2016.
 */
public class SequenceGestureListener implements GestureDetector.GestureListener {

    private static final int NR_COL = 5;
    private static final int NR_ROWS = 5;

    private SequenceHolder sequenceHolder;
    private int height;
    private int width;
    private OnMapClickedListener listener;

    public SequenceGestureListener(OnMapClickedListener listener, SequenceHolder sequenceHolder, int height, int width) {
        this.listener = listener;
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
        System.out.println("Tapped");
        if (listener != null) {
            listener.onMapClicked(x, y, button == Input.Buttons.RIGHT);
        }
        return true;
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
