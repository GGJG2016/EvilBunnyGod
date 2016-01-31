package at.ggjg.evg.entities;

import at.ggjg.evg.State;
import at.ggjg.evg.helpers.Assets;
import at.ggjg.evg.helpers.Bounds;
import at.ggjg.evg.mechanic.World;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.Random;

/**
 * Created by jarhoax on 1/29/16.
 */

public class Bunny extends GameObject {
    public TextureRegion frame;
    public Animation bunny_anim;
    public float health = 10;
    public float damage = 1;
    public TextureRegion bunny;
    public TextureRegion bunny_dead;
    public float timeSinceMoved;
    public Cornfield cornfield;
    public boolean firstAtCornfield;
    Random r;
    //    private float speed;
    private Vector2 destination;
    private Vector2 lastPosition;
    private int schnackselcooldown;

    public Bunny(Float posX, Float posY) {
        super(posX, posY);
        destination = new Vector2();
        bounds = new Bounds(position.x, position.y, 1, 1);
        r = new Random();
    }

    @Override
    public void init(World world) {
        bunny = Assets.bunny_1;
        bunny_dead = Assets.bunny_dead;
        dimension.set(1f, 1f);
        origin.x = dimension.x / 2;
        origin.y = dimension.y / 2;
        scale.set(1f, 1f);
        this.state = State.IDLE;
        this.bunny_anim = Assets.bunnyAnim;
        lastPosition = position;
        timeSinceMoved = 0;
        firstAtCornfield = true;
    }

    private void setNewPosition(float deltatime) {
        this.position.x = this.position.x + (destination.x - this.position.x) * deltatime;
        this.position.y = this.position.y + (destination.y - this.position.y) * deltatime;
        if (position.equals(destination)) //TODO APPROX
        {
            state = State.IDLE;
            stateTime = 0;
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        switch (this.state) {
            case IDLE:
                batch.draw(bunny, position.x, position.y, scale.x, scale.y);
                break;
            case MOVING:
                frame = bunny_anim.getKeyFrame(stateTime, true);
                batch.draw(frame, position.x, position.y, scale.x, scale.y);
                break;
            case ATTACKING:
                frame = bunny_anim.getKeyFrame(stateTime, true);
                batch.draw(frame, position.x, position.y, scale.x, scale.y);
                break;
            case DESTROYED:
                batch.draw(bunny_dead, position.x, position.y, scale.x, scale.y);
                break;
            case SCHNACKSELN:
                break;
            default:
        }
    }

    @Override
    public void onGestureAction() {

    }

    public void setNewDestination(Vector3 newDestination) {
        this.destination = new Vector2(newDestination.x, newDestination.y);
        state = State.MOVING;
        stateTime = 0;
    }

    public void update(World world, float deltaTime) {
        stateTime += deltaTime;
        if (this.health <= 0 && this.state != State.DESTROYED) {
            this.state = State.DESTROYED;
            this.stateTime = 0;

//            world.audio.playKillSounds();
        }
        if (this.state == State.DESTROYED) {
            if (stateTime >= 10)
                world.entities.removeValue(this, false);
            world.bunnies.removeValue(this, false);//TODO
            return;
        }

        schnackselcooldown -= deltaTime;
        if (this.state != State.ATTACKING && lastPosition.equals(this.position) && this.state != State.SCHNACKSELN) {
            timeSinceMoved += deltaTime;
            if (timeSinceMoved > 2) {
                this.state = State.IDLE;
                timeSinceMoved = 0;
            }
        }
        lastPosition = this.position;
        world.audio.setNewState(this.state);

        switch (this.state) {
            case IDLE:
                if (stateTime >= 2.5) {
                    if (r.nextBoolean()) {
                        destination.x += r.nextInt(6 - 1 + 1) + 1;
                    } else {
                        destination.x -= r.nextInt(6 - 1 + 1) + 1;
                    }
                    if (r.nextBoolean()) {
                        destination.y += r.nextInt(6 - 1 + 1) + 1;
                    } else {
                        destination.y -= r.nextInt(6 - 1 + 1) + 1;
                    }
                    this.state = State.MOVING;
                }
                break;
            case MOVING:
                setNewPosition(deltaTime);
                break;
            case ATTACKING:
                break;
            case DESTROYED:
                break;
            case SCHNACKSELN:

                if (!firstAtCornfield) {
                    if (stateTime >= r.nextInt(10) + 5) {
                        Bunny haeschjen = new Bunny(this.position.x, this.position.y);
                        haeschjen.init(world);
                        cornfield.slots++;
                        this.state = State.IDLE;
                        this.schnackselcooldown = 22;
                        haeschjen.schnackselcooldown = 25;
                        haeschjen.state = State.IDLE;
                        world.bunnies.add(haeschjen);
                        world.entities.add(haeschjen);

                    }
                }
                break;
            default:
        }
        this.bounds.x = this.position.x;
        this.bounds.y = this.position.y;

        for (int i = 0; i < world.entities.size; i++) {
            GameObject obj = world.entities.get(i);
            if (obj.bounds.overlaps(this.bounds)) {
                if (obj instanceof House && obj.state != State.DESTROYED) {
                    if (this.state == State.ATTACKING) {
                        if (stateTime >= 0.5f) {
                            this.health -= ((House) obj).getAttacked(this.damage);
                            stateTime = 0;
                        }
                    } else if (state != State.DESTROYED && obj.gestureSuccessful) {
                        this.state = State.ATTACKING;
                        obj.gestureSuccessful = false;
                    }
                } else if (obj instanceof Fence) {
                    this.health = -932873;

                } else if (obj instanceof Cornfield) {
                    if (this.state != State.SCHNACKSELN && schnackselcooldown <= 0) {
                        if ((((Cornfield) obj).slots >= 2 && obj.gestureSuccessful) || ((Cornfield) obj).slots < 2) {
                            cornfield = ((Cornfield) obj);
                            this.state = State.SCHNACKSELN;
                            firstAtCornfield = cornfield.slots > 0;
                            cornfield.slots--;
                            this.stateTime = 0;
                        }
                    }
                } else if (obj instanceof Bunny) {

                }
            }
        }
    }


}
