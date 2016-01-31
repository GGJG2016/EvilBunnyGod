package at.ggjg.evg.entities;

import at.ggjg.evg.State;
import at.ggjg.evg.gestures.Sequence;
import at.ggjg.evg.helpers.Assets;
import at.ggjg.evg.helpers.Bounds;
import at.ggjg.evg.mechanic.World;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


/**
 * Created by jarhoax on 1/29/16.
 */
public class House extends GameObject {
    private static final int SCALING_FACTOR = 5;

    public TextureRegion house_destroyed;
    public TextureRegion house_idle;
    public TextureRegion frame;
    public Animation house_attacking_anim;
    public int health;
    public int damageDealt;
    private float attackCounter;

    public House(Float posX, Float posY) {
        super(posX, posY);
        attackCounter = 0;

    }

    @Override
    public void update(World world, float deltaTime) {
        this.stateTime += deltaTime;
        if (gesture_visible > 0) this.gesture_visible -= deltaTime;
        if(state == State.ATTACKING)
        {
            attackCounter += deltaTime;
//            if(attackCounter > 2) {
                world.audio.playAttackSounds();
//                attackCounter = 0;
//            }
        }
        if (this.state == State.ATTACKING && this.stateTime >= 3) {
            this.state = State.IDLE;
            this.stateTime = 0;

        }
    }

    @Override
    public void init(World world) {
        this.state = State.IDLE;
        this.acceptedGesture = Sequence.SequenceName.VERTICAL_LINE;
        this.house_destroyed = Assets.house_destroyed;
        this.house_attacking_anim = Assets.houseAnim;
        this.house_idle = Assets.house_idle;
        this.gestureDoneAsset = Assets.attack_gesture;
        origin.x = dimension.x / 2;
        origin.y = dimension.y / 2;
        scale.set(SCALING_FACTOR, SCALING_FACTOR);
        bounds = new Bounds(position.x, position.y, scale.x, scale.y);
        this.health = 30;
        this.damageDealt = 1;

    }

    public float getAttacked(float damageGained) {

        if (this.state == State.DESTROYED)
            return 0;
        this.state = State.ATTACKING;


        this.health -= damageGained;
        if (this.health <= 0) {
            this.state = State.DESTROYED;
        }
        return damageDealt;
    }

    @Override
    public void render(SpriteBatch batch) {
        switch (this.state) {
            case DESTROYED:
                batch.draw(house_destroyed, position.x, position.y, scale.x, scale.y);
                break;
            case IDLE:
                batch.draw(house_idle, position.x, position.y, scale.x, scale.y);
                break;
            case ATTACKING:
                frame = house_attacking_anim.getKeyFrame(stateTime, true);
                batch.draw(frame, position.x, position.y, scale.x, scale.y);
                //batch.draw(frame, position.x, position.y,1,1);
                break;
        }
    }

    @Override
    public void onGestureAction() {

    }

}
