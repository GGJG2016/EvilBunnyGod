package at.ggjg.evg.helpers;

/**
 * Created by jarhoax on 1/30/16.
 */
public class Bounds {

    public float x, y;
    public float width, height;

    public Bounds(float x, float y, float dimX, float dimnY) {
        this.x = x;
        this.y = y;
        this.width = dimX;
        this.height = dimnY;
    }
}
