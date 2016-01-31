package at.ggjg.evg.entities;

import at.ggjg.evg.State;
import at.ggjg.evg.helpers.Assets;
import at.ggjg.evg.helpers.Bounds;
import at.ggjg.evg.mechanic.World;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.Random;

/**
 * Created by jarhoax on 1/29/16.
 */

public class Bunny extends GameObject {
	private static float SPEED = 4;
	
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

    private Vector2 destination;
    private Vector2 velocity;
    private int schnackselcooldown;

    public Bunny(Float posX, Float posY) {
        super(posX, posY);
        destination = new Vector2();
        velocity = new Vector2();
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
        this.setState(State.IDLE);
        this.bunny_anim = Assets.bunnyAnim;
        timeSinceMoved = 0;
        firstAtCornfield = true;
    }
    
    public void setNewDestination(Vector3 newDestination) {
        this.destination = new Vector2(newDestination.x, newDestination.y);        
        setState(State.MOVING);
        stateTime = 0;
    }

    private void updatePosition(float deltatime) {
    	this.velocity = velocity.set(destination).sub(position).nor().scl(SPEED);
        this.position.x += velocity.x * deltatime;
        this.position.y += velocity.y * deltatime;
        System.out.println(position.dst(destination));
        if (position.dst(destination) < 0.1f) {
            setState(State.IDLE);
            stateTime = 0;
        }
        this.bounds.x = this.position.x;
        this.bounds.y = this.position.y;
    }

    @Override
    public void render(SpriteBatch batch) {
        switch (this.getState()) {
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

    public void update(World world, float deltaTime) {
        stateTime += deltaTime;
        if (this.health <= 0 && this.getState() != State.DESTROYED) {
            this.setState(State.DESTROYED);
            this.stateTime = 0;
            world.audio.Kill.play();
        }
        if (this.getState() == State.DESTROYED) {
            if (stateTime >= 10) {
                world.entities.removeValue(this, true);
                world.bunnies.removeValue(this, true);
            }
            return;
        }

        schnackselcooldown -= deltaTime;

        switch (this.getState()) {        	
            case IDLE:
                if (stateTime >= 2.5) {
                    float radius = MathUtils.random(1, 4);
                    float angle = MathUtils.random(360);
                    this.destination.add(MathUtils.cosDeg(angle) * radius, MathUtils.sinDeg(angle) * radius);
                    this.setState(State.MOVING);
                }
                break;
            case MOVING:
                updatePosition(deltaTime);
                break;
            case ATTACKING:
                break;            
            case SCHNACKSELN:
                if (!firstAtCornfield) {
                    if (stateTime >= r.nextInt(10) + 5) {
                        Bunny haeschjen = new Bunny(this.position.x, this.position.y);
                        haeschjen.init(world);
                        cornfield.slots++;
                        this.setState(State.IDLE);
                        this.schnackselcooldown = 22;
                        haeschjen.schnackselcooldown = 25;
                        haeschjen.setState(State.IDLE);
                        world.bunnies.add(haeschjen);
                        world.entities.add(haeschjen);

                    }
                }
                break;
            default:
        }       

        for (int i = 0; i < world.entities.size; i++) {
            GameObject obj = world.entities.get(i);
            if (obj.bounds.overlaps(this.bounds)) {
                if (obj instanceof House && obj.getState() != State.DESTROYED) {
                    if (this.getState() == State.ATTACKING) {
                        if (stateTime >= 0.5f) {
                            this.health -= ((House) obj).getAttacked(this.damage);
                            stateTime = 0;
                        }
                    } else if (getState() != State.DESTROYED && obj.gestureSuccessful) {
                        this.setState(State.ATTACKING);
                        obj.gestureSuccessful = false;
                    }
                } else if (obj instanceof Fence) {
                    this.health = -932873;

                } else if (obj instanceof Cornfield) {
                    if (this.getState() != State.SCHNACKSELN && schnackselcooldown <= 0) {
                        if ((((Cornfield) obj).slots >= 2 && obj.gestureSuccessful) || ((Cornfield) obj).slots < 2) {
                            cornfield = ((Cornfield) obj);
                            this.setState(State.SCHNACKSELN);
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
