package at.ggjg.evg.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import at.ggjg.evg.helpers.Assets;
import at.ggjg.evg.helpers.Bounds;
import at.ggjg.evg.mechanic.World;

/**
 * Created by christophergalle on 30/01/16.
 */
public class Cornfield extends GameObject {
    public TextureRegion tr;

    public Cornfield(Float posX, Float posY) {
        super(posX, posY);

    }

    @Override
    public void init(World world) {
        tr = Assets.cornfield;
        bounds = new Bounds(position.x , position.y, 1,1);
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(tr, position.x, position.y, 1,1);

    }
}
