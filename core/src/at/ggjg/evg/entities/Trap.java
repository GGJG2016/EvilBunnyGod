package at.ggjg.evg.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import at.ggjg.evg.helpers.Assets;
import at.ggjg.evg.helpers.Bounds;
import at.ggjg.evg.mechanic.World;

/**
 * Created by jarhoax on 1/29/16.
 */
public class Trap extends LethalObstacle {

    private static final int SCALING_FACTOR = 1;

    public TextureRegion tr;

    public Trap(Float posX, Float posY) {
        super(posX, posY);
    }

    @Override
    public void init(World world) {
        this.tr = Assets.trap;
        dimension.set(1f, 1f);
        origin.x = dimension.x / 2;
        origin.y = dimension.y / 2;
        scale.set(SCALING_FACTOR, SCALING_FACTOR);
        bounds = new Bounds(position.x +0.2f, position.y+0.2f, 0.6f, 0.6f);
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(tr, position.x, position.y, scale.x, scale.y);
    }

    @Override
    public void onGestureAction() {

    }
}
