package at.ggjg.evg.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import at.ggjg.evg.State;
import at.ggjg.evg.helpers.Assets;
import at.ggjg.evg.mechanic.World;

/**
 * Created by jarhoax on 1/29/16.
 */
public class House extends GameObject {
    public TextureRegion house_destroyed;
    public TextureRegion house_idle;
    public TextureRegion house_attacking;
    public House(Float posX, Float posY) {
        super(posX, posY);

    }

    @Override
    public void init(World world) {
        this.state = State.IDLE;
        this.house_destroyed = Assets.house_destroyed;
        this.house_attacking = Assets.house_attacking;
        this.house_idle = Assets.house_idle;

    }

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
                batch.draw(house_attacking, position.x, position.y, 1,1);
                break;
        }
    }
}
