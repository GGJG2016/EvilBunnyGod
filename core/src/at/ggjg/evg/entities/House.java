package at.ggjg.evg.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import at.ggjg.evg.State;
import at.ggjg.evg.helpers.Assets;
import at.ggjg.evg.helpers.Bounds;
import at.ggjg.evg.mechanic.World;


/**
 * Created by jarhoax on 1/29/16.
 */
public class House extends GameObject {
    public TextureRegion house_destroyed;
    public TextureRegion house_idle;
    public TextureRegion frame;
    public Animation house_attacking_anim;
    public int health;
    public int damageDealt;
    public House(Float posX, Float posY) {
        super(posX, posY);

    }

    @Override
    public void update(World world, float deltaTime) {

    }

    @Override
    public void update(float deltaTime) {

    }

    @Override
    public void init(World world) {
        this.state = State.IDLE;
        this.house_destroyed = Assets.house_destroyed;
        this.house_attacking_anim = Assets.houseAnim;
        this.house_idle = Assets.house_idle;
        bounds = new Bounds(position.x , position.y, 1,1);
        this.health = 30;
        this.damageDealt = 1;
    }

    public float getAttacked(float damageGained){
        if(this.state == State.DESTROYED)
            return 0;
        this.state = State.ATTACKING;
        this.health -= damageDealt;
        if(this.health<=0){
            this.state=State.DESTROYED;
        }
        return  damageDealt;
    };

    @Override
    public void render(SpriteBatch batch) {
        switch (this.state){
            case DESTROYED:
                batch.draw(house_destroyed, position.x, position.y, 1,1);
                break;
            case IDLE:
                batch.draw(house_idle, position.x, position.y, 1,1);
                break;
            case ATTACKING:
                frame = house_attacking_anim.getKeyFrame(stateTime, true);
                batch.draw(frame, position.x, position.y);
                //batch.draw(frame, position.x, position.y,1,1);
                break;
        }
    }

    public void update(World world, Float deltaTime){
        this.stateTime+=deltaTime;
        if(this.state == State.ATTACKING && this.stateTime>=1){
            this.state = State.IDLE;
            this.stateTime = 0;
        }
    }


}
