package at.ggjg.evg.entities;

import at.ggjg.evg.helpers.Assets;
import at.ggjg.evg.helpers.Bounds;
import at.ggjg.evg.mechanic.World;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by jarhoax on 1/29/16.
 */
public class Fence extends LethalObstacle {

    private static final int SCALING_FACTOR = 2;

    public TextureRegion tr;

    public Fence(Float posX, Float posY) {
        super(posX, posY);
    }

    @Override
    public void init(World world) {
        this.tr = Assets.fence;
        dimension.set(1f, 1f);
        origin.x = dimension.x / 2;
        origin.y = dimension.y / 2;
        scale.set(SCALING_FACTOR, SCALING_FACTOR);
        bounds = new Bounds(position.x, position.y+0.35f, scale.x, 0.85f);
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(tr, position.x, position.y, scale.x, scale.y);
    }

    @Override
    public void onGestureAction() {

    }
}
