package at.ggjg.evg.entities;

import at.ggjg.evg.State;
import at.ggjg.evg.helpers.Bounds;
import at.ggjg.evg.mechanic.World;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public abstract class GameObject {
    public Vector2 position;
    public Vector2 dimension;
    public Vector2 origin;
    public Vector2 scale;
    public float rotation;
    public State state;
    public float stateTime;
    public Bounds bounds;
    public boolean isClicked = false;

    public GameObject() {
        position = new Vector2();
        dimension = new Vector2(1, 1);
        origin = new Vector2();
        scale = new Vector2(1, 1);
        rotation = 0;
    }

    public GameObject(Float posX, Float posY) {
        this();
        this.position.x = posX;
        this.position.y = posY;

    }

    public abstract void update(World world, float deltaTime);

    public abstract void init(World world);

    public abstract void render(SpriteBatch batch);

    public boolean wasClicked(float screenX, float screenY) {
        boolean retVal = this.bounds.contains(screenX, screenY);
        if (retVal)
            System.out.println("Clicked on " + this.getClass().getSimpleName());
        return retVal;
    }
}
