package at.ggjg.evg.entities;

import at.ggjg.evg.State;
import at.ggjg.evg.helpers.Assets;
import at.ggjg.evg.helpers.Bounds;
import at.ggjg.evg.mechanic.World;

import com.badlogic.gdx.graphics.Texture;
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

    public TextureRegion bunny;
    private boolean inCornfield;
    private Vector2 movement;
//    private float speed;
    private Vector2 destination;
    private float idle;
    private float deltatime;

    public boolean isInCornfield() {
        return inCornfield;
    }

    public Bunny(Float posX, Float posY) {
        super(posX, posY);
        inCornfield = false;
        bunny = Assets.bunny_1;
        movement = new Vector2(0f,0f);
        destination = new Vector2();

        idle = 0;
        state = State.IDLE;

    }

    public void updateIdleTime(){
        idle += deltatime;
    }
    @Override
    public void init(World world) {
        bunny = Assets.bunny_1;
        dimension.set(1f, 1f);
        origin.x = dimension.x / 2;
        origin.y = dimension.y / 2;
        scale= new Vector2(1.3f, 1.3f);
        this.state = State.IDLE;
        bounds = new Bounds(position.x , position.y , 1,1);
    }
    private void setNewPosition()
    {
        this.position.x = this.position.x + (destination.x- this.position.x) * deltatime;
        this.position.y = this.position.y + (destination.y- this.position.y) * deltatime;
        if(position.equals(destination))
        {
            state = State.IDLE;
        }
    }

    public Bounds getAndUpdateBounds(){
 //       bounds = new Bounds(position.x - dimension.x / 2 , position.y - dimension.y / 2, dimension.x,dimension.y);
        bounds = new Bounds(position.x  , position.y , 1,1);
        return bounds;
    }
    @Override
    public void render(SpriteBatch batch) {
        switch (this.state) {
            case IDLE:
                batch.draw(bunny, position.x, position.y, 1,1);
                break;
            case MOVING:
                batch.draw(bunny, position.x, position.y, 1,1);
                break;
            case ATTACKING:
                break;
            case DESTROYED:
                break;
            default:
        }
    }

    private void randomDestination() {
        Random r = new Random();
        setNewDestination(new Vector3(r.nextFloat(),r.nextFloat(),0));
    }

    public GameObject collidesWith(World world) {
        GameObject collidingObject = null;
        for (int i = 0; i < world.entities.size; i++) {
            //  for (Iterator<GameObject> iterator = world.entities.iterator(); iterator.hasNext(); ) {
            GameObject obj = world.entities.get(i);
//            if (obj instanceof Bunny)
//                continue;
//            if (obj instanceof Cornfield) {
//                // TODO: define bunny in confield
//                continue;
//            }
//            System.out.println(bounds.x + ", " + bounds.y);
//            System.out.println(obj.bounds.x + ", " + obj.bounds.y);
            // objRect = ;
            Rectangle inter = new Rectangle();
            if (Intersector.intersectRectangles(bounds,obj.bounds,inter))
            {
                System.out.println("Collides like a pro");

                return obj;
            }
//            if ((obj.bounds).(bounds)) {
//                System.out.println("Collides");
//                return obj;
//
//            }
        }
        return null;
    }
//                collidingObject = obj;
//            }




    public void setNewDestination(Vector3 newDestination) {
//        System.out.println("SetNewDestination: " +  newDestination.x + " " + newDestination.y);
        this.destination = new Vector2(newDestination.x, newDestination.y);
        state = State.MOVING;
    }

    public void update(World world, float deltaTime) {
        this.deltatime = deltaTime;
        if(!this.destination.equals(this.position)) {
//            System.out.println("mx: " + this.destination.x + " my: " + this.destination.y);
//            System.out.println("mx: " + this.position.x + " my: " + this.position.y);
        }
        switch (this.state) {
            case IDLE:
                //System.out.println(this.idle);
                updateIdleTime();
                if(idle % 5 <= 0)
                {
                    randomDestination();
                    this.state = State.MOVING;
                }
                break;
            case MOVING:
                this.idle = 0f;
//                System.out.println("moving!!!!!!!");
                setNewPosition();
                getAndUpdateBounds();
                if(collidesWith(world)!=null){
                    System.out.println("juhydfjhsfgl");
                }
                // todo check for dest reached
                break;
            case ATTACKING:
                this.idle = 0f;
                break;
            case DESTROYED:
                this.idle = 0f;
                break;
            default:
        }

    }

    @Override
    public void update(float deltaTime) {

    }

}
