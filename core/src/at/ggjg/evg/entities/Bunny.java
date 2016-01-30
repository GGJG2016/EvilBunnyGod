package at.ggjg.evg.entities;

import at.ggjg.evg.State;
import at.ggjg.evg.helpers.Assets;
import at.ggjg.evg.helpers.Bounds;
import at.ggjg.evg.mechanic.World;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by jarhoax on 1/29/16.
 */
public class Bunny extends GameObject{

    public TextureRegion bunny;
    private boolean inCornfield;
    private Vector2 movement;
    private float speed;


    public boolean isInCornfield() {
        return inCornfield;
    }

    public Bunny(Float posX, Float posY) {
        super(posX, posY);
        inCornfield = false;
        bunny = Assets.bunny;
        movement = new Vector2(0f,0f);
        speed =1f;
    }


    @Override
    public void init(World world) {
        bunny = Assets.bunny;
        dimension.set(1f, 1f);
        origin.x = dimension.x / 2;
        origin.y = dimension.y / 2;
        scale= new Vector2(1.3f, 1.3f);
        this.state = State.IDLE;
        bounds = new Bounds(position.x - dimension.x / 2 , position.y - dimension.y / 2, dimension.x,dimension.y);
    }
    private void setNewPosition()
    {
        Vector2 newPosition = new Vector2(position.x + movement.x * speed, position.y + movement.y * speed);
        // fixxxme auf die states achten
        position = newPosition;
    }

    public Bounds getAndUpdateBounds(){
        bounds = new Bounds(position.x - dimension.x / 2 , position.y - dimension.y / 2, dimension.x,dimension.y);
        return bounds;
    }
    @Override
    public void render(SpriteBatch batch) {
        switch (this.state) {
            case IDLE:
                batch.draw(bunny, position.x, position.y, 1,1);
//
//
//                        position.x - dimension.x / 2, position.y - dimension.y / 2,
//                        origin.x, origin.y,
//                        dimension.x, dimension.y,
//                        scale.x, scale.y,
//                        rotation);

//                if (bunny.heading == Heading.Left) {
//                    frame = anim.getKeyFrame(bunny.stateTime, true);
//                    offset = clipOffset(frame, upper);
//                    frame = clip(frame, upper);
//                    frame.flip(true, false);
//                    batch.draw(frame, bunny.position.x, entity.position.y + offset, 1, 1);
//                } else {
//                    frame = anim.getKeyFrame(bunny.stateTime, true);
//                    offset = clipOffset(frame, upper);
//                    frame = clip(frame, upper);
//                    batch.draw(frame, bunny.position.x, bunny.position.y + offset, 1, 1);
//                }
                break;
            case MOVING:
//                if (bunny.heading == Heading.Left) {
//                    frame = anim.getKeyFrame(entity.stateTime, true);
//                    offset = clipOffset(frame, upper);
//                    frame = clip(frame, upper);
//                    frame.flip(true, false);
//                    batch.draw(frame, bunny.position.x, bunny.position.y + offset, 1, 1);
//                } else {
//                    frame = anim.getKeyFrame(bunny.stateTime, true);
//                    offset = clipOffset(frame, upper);
//                    frame = clip(frame, upper);
//                    batch.draw(frame, bunny.position.x, bunny.position.y + offset, 1, 1);
//                }
                break;
            case ATTACKING:
//                if (bunny.heading == Heading.Left) {
//                    frame = mainAttack.getKeyFrame(bunny.stateTime, false);
//                    offset = clipOffset(frame, upper);
//                    frame = clip(frame, upper);
//                    frame.flip(true, false);
//                    batch.draw(frame, bunny.position.x - 0.5f, bunny.position.y + offset, 2, 1);
//                } else {
//                    frame = mainAttack.getKeyFrame(bunny.stateTime, false);
//                    offset = clipOffset(frame, upper);
//                    frame = clip(frame, upper);
//                    batch.draw(frame, bunny.position.x - 0.5f, bunny.position.y + offset, 2, 1);
//                }
                break;
            case DESTROYED:
            //    batch.draw(mainDead, bunny.position.x - 0.5f, bunny.position.y + offset, 2, 1);
                break;
            default:
        }
    }
    public GameObject collidesWith(World world)
    {
        GameObject collidingObject = null;

        Rectangle newbounds = new Rectangle(bounds.x + movement.x, bounds.y + movement.y, bounds.width, bounds.height);
//        int sx, sy, ex, ey, ux, uy;
//        if(movement.x > 0) {
//            sx = (int)Math.floor(bounds.x);
//            ex = (int)Math.ceil(newbounds.x + bounds.width) + 1;
//        }
//        else {
//            sx = (int)Math.ceil(bounds.x + bounds.width);
//            ex = (int)Math.floor(newbounds.x) - 1;
//        }
//
//        if(movement.y > 0) {
//            sy = (int)Math.floor(bounds.y);
//            ey = (int)Math.ceil(newbounds.y + bounds.height) + 1;
//        }
//        else {
//            sy = (int)Math.ceil(bounds.y + bounds.height);
//            ey = (int)Math.floor(newbounds.y) - 1;
//        }

        Rectangle objRect;
        for (GameObject obj:world.entities) {
            if(obj instanceof Bunny)
                continue;
            if(obj instanceof Cornfield) {
                // TODO: define bunny in confield
                continue;
            }
            objRect = new Rectangle(obj.posX,obj.posY,obj.dimension.x,obj.dimension.y);
            if(newbounds.overlaps(objRect)){
                System.out.println("Collides");

                collidingObject = obj;
            }
        }

        return collidingObject;
    }
}
