package at.ggjg.evg.entities;

import at.ggjg.evg.mechanic.World;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by jarhoax on 1/29/16.
 */
public abstract class LethalObstacle extends GameObject {

    public LethalObstacle(Float posX, Float posY) {
        super(posX, posY);
    }

    @Override
    public void update(World world, float deltaTime) {

    }

    @Override
    public void init(World world) {

    }

    @Override
    public void render(SpriteBatch batch) {

    }
}
