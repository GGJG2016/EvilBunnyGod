package at.ggjg.evg.entities;

import at.ggjg.evg.State;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import at.ggjg.evg.mechanic.World;

public abstract class GameObject {
    public Vector2 position;
    public Vector2 dimension;
    public Vector2 origin;
    public Vector2 scale;
    public float rotation;
    public State state;
    public int stateTime;
    public float posX;
    public float posY;

    public GameObject() {
        position = new Vector2();
        dimension = new Vector2(1, 1);
        origin = new Vector2();
        scale = new Vector2(1, 1);
        rotation = 0;
    }

    public GameObject(Float posX, Float posY){
        this();
        this.posX = posX;

    }
    public void update(World world, float deltaTime) {
    }

    public abstract void init(com.badlogic.gdx.physics.box2d.World world);

    public void update(float deltaTime) {}

    public abstract void render(SpriteBatch batch);
}
