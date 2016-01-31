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

    public float flip = 1;
    private Vector2 destination;
    private Vector2 velocity;
    private int schnackselcooldown;

    public Bunny(Float posX, Float posY) {
        super(posX, posY);
        destination = new Vector2();
        velocity = new Vector2();
        bounds = new Bounds(position.x+0.1f, position.y+0.1f, 0.8f, 0.8f);
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
        timeSinceMoved = 0;
        firstAtCornfield = true;
    }
    
    public void setNewDestination(Vector3 newDestination) {
    	flip = 1;
    	if(newDestination.x - position.x != 0 && state!=State.DESTROYED) {
    		flip = Math.signum(newDestination.x - position.x);
    	}
        this.destination = new Vector2(newDestination.x, newDestination.y);
        if (this.state != State.SCHNACKSELN) {
            state = State.MOVING;
            stateTime = 0;
        }
    }

    private void updatePosition(World world, float deltatime) {
    	this.velocity = velocity.set(destination).sub(position).nor().scl(SPEED);       
        
        // separation behaviour
        int hitBunnies = 0;
        Vector2 separation = new Vector2();
        for(Bunny bunny: world.bunnies) {
        	if(bunny == this) {
        		continue;
        	}
        	if(bunny.position.dst(this.position) < 0.5f) {
        		hitBunnies++;
        		separation.add(this.position.x - bunny.position.x, this.position.y - bunny.position.y);        		
        	}
        }
        if(hitBunnies > 0) {
        	separation.nor().scl(hitBunnies);
        	velocity.add(separation);
        }
        
        this.position.x += velocity.x * deltatime;
        this.position.y += velocity.y * deltatime;
        if (position.dst(destination) < 0.1f || velocity.len() < SPEED * deltatime / 2) {
            state = State.IDLE;
            stateTime = 0;
        }
        this.bounds = new Bounds(position.x+0.1f, position.y+0.1f, 0.8f, 0.8f);
    }

    @Override
    public void render(SpriteBatch batch) {    	    	
        switch (this.state) {
            case IDLE:
                batch.draw(bunny, position.x + (flip < 0? 1: 0), position.y, scale.x * flip, scale.y);
                break;
            case MOVING:
                frame = bunny_anim.getKeyFrame(stateTime, true);
                batch.draw(frame, position.x + (flip < 0? 1: 0), position.y, scale.x * flip, scale.y);
                break;
            case ATTACKING:
                frame = bunny_anim.getKeyFrame(stateTime, true);
                batch.draw(frame, position.x + (flip < 0? 1: 0), position.y, scale.x * flip, scale.y);
                break;
            case DESTROYED:
                batch.draw(bunny_dead, position.x + (flip < 0? 1: 0), position.y, scale.x * flip, scale.y);
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
        if (gesture_done_time > 0) this.gesture_done_time -= deltaTime;
        if (gesture_required_time > 0) this.gesture_required_time -= deltaTime;

        if (this.health <= 0 && this.state != State.DESTROYED) {
            this.state = State.DESTROYED;
            this.stateTime = 0;

//            world.audio.playKillSounds();
        }
        if (this.state == State.DESTROYED) {
            if (stateTime >= 10) {
                world.entities.removeValue(this, true);
                world.bunnies.removeValue(this, true);
            }
            return;
        }

        schnackselcooldown -= deltaTime;
        if (stateTime == deltaTime)
            world.audio.setNewState(this.state);

        switch (this.state) {        	
            case IDLE:
                if (stateTime >= 2.5) {
                    float radius = MathUtils.random(1, 4);
                    float angle = MathUtils.random(360);
                    this.destination.add(MathUtils.cosDeg(angle) * radius, MathUtils.sinDeg(angle) * radius);
                    setNewDestination(new Vector3(this.destination.x, this.destination.y, 0));
                }
                break;
            case MOVING:
                updatePosition(world, deltaTime);
                break;
            case ATTACKING:
                break;            
            case SCHNACKSELN:
                break;
            default:
        }       

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
                    }
                } else if (obj instanceof LethalObstacle) {
                    this.health = -932873;

                } else if (obj instanceof Cornfield) {
                    if (this.state != State.SCHNACKSELN && schnackselcooldown <= 0) {
                        cornfield = ((Cornfield) obj);
                        if (cornfield.bunnies.size < 2) {
                            cornfield.addBunny(this);
                        }
                    }
                }
            } else if (obj instanceof Bunny) {

            }
        }
    }
}



