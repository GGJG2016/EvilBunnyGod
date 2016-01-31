package at.ggjg.evg.entities;

import at.ggjg.evg.gestures.Sequence;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import at.ggjg.evg.helpers.Assets;
import at.ggjg.evg.helpers.Bounds;
import at.ggjg.evg.mechanic.World;

/**
 * Created by christophergalle on 30/01/16.
 */
public class Cornfield extends GameObject {
    private static final float SCALING_FACTOR = 1.2f;
    public TextureRegion tr;
    public TextureRegion bunniesmakingloooove;
    public int slots = 2;

    public Cornfield(Float posX, Float posY) {
        super(posX, posY);

    }

    @Override
    public void update(World world, float deltaTime) {
        if (gesture_visible > 0)this.gesture_visible -= deltaTime;
    }

    @Override
    public void init(World world) {
        tr = Assets.farm;
        bunniesmakingloooove = Assets.nastyBunnies;
        acceptedGesture = Sequence.SequenceName.SQUARE;
        gestureDoneAsset = Assets.schnackselnGesture;
        origin.x = dimension.x / 2;
        origin.y = dimension.y / 2;
        scale.set(SCALING_FACTOR, SCALING_FACTOR);
        bounds = new Bounds(position.x, position.y, scale.x, scale.y);
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(tr, position.x, position.y, scale.x,scale.y);
        if(slots<=0)
            batch.draw(bunniesmakingloooove, position.x, position.y, scale.x,scale.y);
    }

    @Override
    public void onGestureAction() {

    }
}
