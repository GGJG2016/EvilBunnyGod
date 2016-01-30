package at.ggjg.evg.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import at.ggjg.evg.helpers.Assets;
import at.ggjg.evg.mechanic.World;

/**
 * Created by jarhoax on 1/29/16.
 */
public class Fence extends LethalObstacle {
    public TextureRegion tr;
    public Fence(Float posX, Float posY) {
        super(posX, posY);

    }

    @Override
    public void init(World world) {
        this.tr = Assets.fence;

    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(tr, position.x, position.y, 1,1);

    }
}
