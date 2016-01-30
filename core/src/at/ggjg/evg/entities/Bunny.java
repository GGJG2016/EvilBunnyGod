package at.ggjg.evg.entities;

import at.ggjg.evg.State;
import at.ggjg.evg.helpers.Assets;
import at.ggjg.evg.helpers.Bounds;
import at.ggjg.evg.mechanic.World;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import java.util.Iterator;
import java.util.Random;

/**
 * Created by jarhoax on 1/29/16.
 */
public class Bunny extends GameObject{
    Random r;
    public TextureRegion frame;
    public Animation bunny_anim;
    public float health = 10;
    public float damage = 1;
    public TextureRegion bunny;
    public TextureRegion bunny_dead;
    private boolean inCornfield;
//    private float speed;
    private Vector2 destination;
    private float deltatime;
    public float stateTime = 0;
    private Vector2 lastPosition;
    public float timeSinceMoved;

    public boolean isInCornfield() {
        return inCornfield;
    }

    public Bunny(Float posX, Float posY) {
        super(posX, posY);
        inCornfield = false;
        destination = new Vector2();
        bounds = new Bounds(position.x , position.y , 1,1);
        r = new Random();

    }
    @Override
    public void init(World world) {
        bunny = Assets.bunny_1;
        bunny_dead = Assets.bunny_dead;
        dimension.set(1f, 1f);
        origin.x = dimension.x / 2;
        origin.y = dimension.y / 2;
        scale= new Vector2(1.3f, 1.3f);
        this.state = State.IDLE;
        this.bunny_anim = Assets.bunnyAnim;
        lastPosition = position;
        timeSinceMoved = 0;
    }
    private void setNewPosition()
    {
        this.position.x = this.position.x + (destination.x- this.position.x) * deltatime;
        this.position.y = this.position.y + (destination.y- this.position.y) * deltatime;
        if(position.equals(destination)) //TODO APPROX
        {
            state = State.IDLE;
            stateTime = 0;
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        switch (this.state) {
            case IDLE:
                batch.draw(bunny, position.x, position.y, 1,1);
                break;
            case MOVING:
                frame = bunny_anim.getKeyFrame(stateTime, true);
                batch.draw(frame, position.x, position.y);
                break;
            case ATTACKING:
                frame = bunny_anim.getKeyFrame(stateTime, true);
                batch.draw(frame, position.x, position.y);
                break;
            case DESTROYED:
                batch.draw(bunny_dead, position.x, position.y, 1,1);
                break;
            default:
        }
    }


    public void setNewDestination(Vector3 newDestination) {
//        System.out.println("SetNewDestination: " +  newDestination.x + " " + newDestination.y);
        this.destination = new Vector2(newDestination.x, newDestination.y);
        state = State.MOVING;
        stateTime = 0;
    }

    public void update(World world, float deltaTime) {
        stateTime+=deltaTime;

        if(this.health <= 0)
            this.state = state.DESTROYED;
        if(this.state == state.DESTROYED){
            return;
        }
        this.deltatime = deltaTime;
        if(this.state != State.ATTACKING && lastPosition.equals(this.position)){
            timeSinceMoved+= deltaTime;
            if(timeSinceMoved>1) {
                this.state = State.IDLE;
                timeSinceMoved = 0;
            }
        }


            lastPosition = this.position;
        if(!this.destination.equals(this.position)) {
//            System.out.println("mx: " + this.destination.x + " my: " + this.destination.y);
//            System.out.println("mx: " + this.position.x + " my: " + this.position.y);
        }
        switch (this.state) {
            case IDLE:
                System.out.println("IDLE" + stateTime);
                if(stateTime >= 2.5)
                {
                    System.out.println("Drinnen");
                    if(r.nextBoolean()) {
                        destination.x += r.nextInt(6 - 1 + 1) + 1;
                    }
                    else{
                        destination.x += r.nextInt(6 - 1 + 1) + 1;
                    }
                    if(r.nextBoolean()) {
                        destination.y -= r.nextInt(6 - 1 + 1) + 1;
                    }
                    else{
                        destination.y -= r.nextInt(6 - 1 + 1) + 1;
                    }

                    this.state = State.MOVING;
                }
                break;
            case MOVING:
                setNewPosition();
                break;
            case ATTACKING:
                break;
            case DESTROYED:
                break;
            default:
        }
        this.bounds.x = this.position.x;
        this.bounds.y = this.position.y;

        for (int i = 0; i < world.entities.size; i++) {
            GameObject obj = world.entities.get(i);
            if (obj.bounds.overlaps(this.bounds))
            {
                    if(obj instanceof House){
                        if(this.state == state.ATTACKING){
                            if(stateTime >= 2){
                                this.health = ((House)obj).getAttacked(this.damage);
                                stateTime = 0;
                            }
                        }

                        this.state = state.ATTACKING;
                    }
                    else if(obj instanceof Fence){

                    }
                    else if(obj instanceof Cornfield){

                    }
                    else if(obj instanceof Bunny){

                    }
            }
        }
    }


}
