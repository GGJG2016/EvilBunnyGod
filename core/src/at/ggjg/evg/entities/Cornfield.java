package at.ggjg.evg.entities;

import at.ggjg.evg.State;
import at.ggjg.evg.gestures.Sequence;
import at.ggjg.evg.helpers.Assets;
import at.ggjg.evg.helpers.Bounds;
import at.ggjg.evg.mechanic.World;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import java.util.Random;

/**
 * Created by christophergalle on 30/01/16.
 */
public class Cornfield extends GameObject {
    private static final float SCALING_FACTOR = 1.2f;
    public TextureRegion tr;
    public TextureRegion bunniesmakingloooove;
    public Array<Bunny> bunnies = new Array<Bunny>();
    public float cooldown = 0;
    Random r = new Random();

    public Cornfield(Float posX, Float posY) {
        super(posX, posY);

    }

    @Override
    public void update(World world, float deltaTime) {
        if (gesture_done_time > 0) this.gesture_done_time -= deltaTime;
        if (gesture_required_time > 0) this.gesture_required_time -= deltaTime;
        stateTime += deltaTime;
        cooldown -= deltaTime;
        if (bunnies.size >= 2 && stateTime >= r.nextInt(10) + 5) {
            Bunny haeschjen = new Bunny(this.position.x, this.position.y);
            haeschjen.init(world);
            gestureSuccessful = false;
            world.bunnies.add(haeschjen);
            world.entities.add(haeschjen);
            bunnies.add(haeschjen);
            for (Bunny bunny : bunnies) {
                bunny.state = State.IDLE;
                bunny.stateTime = 0;
            }
            cooldown = 25;
            bunnies.removeRange(0, bunnies.size - 1);
        }
    }

    @Override
    public void init(World world) {
        tr = Assets.farm;
        bunniesmakingloooove = Assets.nastyBunnies;
        acceptedGesture = Sequence.SequenceName.SQUARE;
        gestureDoneAsset = Assets.schnackselnGesture;
        gestureRequiredAsset = Assets.schnackselnGesture_required;
        origin.x = dimension.x / 2;
        origin.y = dimension.y / 2;
        scale.set(SCALING_FACTOR, SCALING_FACTOR);
        bounds = new Bounds(position.x, position.y, scale.x, scale.y);
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(tr, position.x, position.y, scale.x, scale.y);
        if (bunnies.size >= 2)
            batch.draw(bunniesmakingloooove, position.x, position.y, scale.x, scale.y);
    }

    @Override
    public void onGestureAction() {

    }

    public void addBunny(Bunny bunny) {
        if (!gestureSuccessful || bunnies.size > 2 || cooldown > 0)
            return;
        bunnies.add(bunny);
        bunny.state = State.SCHNACKSELN;
        bunny.stateTime = 0;
        this.stateTime = 0;
    }
}
