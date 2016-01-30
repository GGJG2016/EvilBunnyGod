package at.ggjg.evg.entities;

import at.ggjg.evg.State;
import at.ggjg.evg.helpers.Assets;
import at.ggjg.evg.mechanic.World;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by jarhoax on 1/29/16.
 */
public class Bunny extends GameObject{

    public TextureRegion bunny;

    public Bunny(Float posX, Float posY) {
        super(posX, posY);
        bunny = Assets.bunny;
    }


    @Override
    public void init(World world) {
        bunny = Assets.bunny;
        dimension.set(1f, 1f);
        origin.x = dimension.x / 2;
        origin.y = dimension.y / 2;
        scale= new Vector2(1.3f, 1.3f);
        this.state = State.IDLE;
    }

    @Override
    public void render(SpriteBatch batch) {
        switch (this.state) {
            case IDLE:
                batch.draw(bunny,
                        position.x - dimension.x / 2, position.y - dimension.y / 2,
                        origin.x, origin.y,
                        dimension.x, dimension.y,
                        scale.x, scale.y,
                        rotation);

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
}
