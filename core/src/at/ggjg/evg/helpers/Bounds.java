package at.ggjg.evg.helpers;

import com.badlogic.gdx.math.Rectangle;

/**
 * Created by jarhoax on 1/30/16.
 */
public class Bounds extends Rectangle{

    public Bounds(float x, float y, float dimX, float dimnY) {
        this.x = x;
        this.y = y;
        this.width = dimX;
        this.height = dimnY;
    }
}
