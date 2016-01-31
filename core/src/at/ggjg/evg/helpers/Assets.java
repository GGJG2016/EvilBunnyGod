package at.ggjg.evg.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

public class Assets implements Disposable {
    //DO THIS WITH ASSETMANAGER LATER TODO

    public static Animation bunnyAnim;
    public static Animation houseAnim;

    public static TextureRegion bunny_1;
    public static TextureRegion bunny_2;
    public static TextureRegion bunny_3;
    public static TextureRegion bunny_dead;
    public static TextureRegion fence;
    public static TextureRegion trap;
    public static TextureRegion sacrifice;
    public static TextureRegion house_idle;
    public static TextureRegion house_attacking;
    public static TextureRegion house_destroyed;
    public static TextureRegion farm;
    static Array<Animation> allAnimations = new Array<Animation>();
    static Array<TextureRegion> allTextureRegions = new Array<TextureRegion>();
    public static TextureRegion nastyBunnies;

    public static void init() {

        /**
         * Begin Loading Animations
         */
        bunnyAnim = loadAnimation("entityassets/rabbit-animation-", 3, 0.25f);
        allAnimations.add(bunnyAnim);
        houseAnim = loadAnimation("entityassets/house-", 2, 0.2f);
        allAnimations.add(houseAnim);
        /**
         * End Loading Animations
         */


        /**
         * Begin Loading TextureRegions
         */
        nastyBunnies = new TextureRegion(new Texture("entityassets/reproduce.png"));
        bunny_1 = new TextureRegion(new Texture("entityassets/rabbit-animation-1.png"));
        bunny_2 = new TextureRegion(new Texture("entityassets/rabbit-animation-2.png"));
        bunny_3 = new TextureRegion(new Texture("entityassets/rabbit-animation-3.png"));
        bunny_dead = new TextureRegion(new Texture("entityassets/bunny_dead.png"));
        farm = new TextureRegion(new Texture("entityassets/heuballen.png"));
        allTextureRegions.add(bunny_dead);
        allTextureRegions.add(bunny_1);
        allTextureRegions.add(bunny_2);
        allTextureRegions.add(bunny_3);
        allTextureRegions.add(farm);
        fence = new TextureRegion(new Texture("entityassets/fence.png"));
        allTextureRegions.add(fence);
        house_idle = new TextureRegion(new Texture("entityassets/house-1.png"));
        house_attacking = new TextureRegion(new Texture("entityassets/house-2.png"));
        house_destroyed = new TextureRegion(new Texture("entityassets/house-3.png"));
        allTextureRegions.add(house_idle);
        allTextureRegions.add(house_attacking);
        allTextureRegions.add(house_destroyed);
        trap = new TextureRegion(new Texture("entityassets/trap.png"));
        allTextureRegions.add(trap);
        sacrifice = new TextureRegion(new Texture("entityassets/opfer.png"));
        allTextureRegions.add(sacrifice);
        /**
         * End Loading TextureRegions
         */

    }

    private static Animation loadAnimation(String path, int frames, float frameDuration) {
        TextureRegion[] regions = new TextureRegion[frames];

        for (int i = 0; i < frames; i++) {
            Texture tex = new Texture(Gdx.files.internal(path + (i + 1) + ".png"));
            tex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            regions[i] = new TextureRegion(tex);
        }
        return new Animation(frameDuration, regions);
    }

    @Override
    public void dispose() {
        for (TextureRegion tex : allTextureRegions) {
            tex.getTexture().dispose();
        }
        for (Animation anim : allAnimations) {
            for (int i = 0; i < anim.getKeyFrames().length; i++) {
                anim.getKeyFrames()[i].getTexture().dispose();
            }
        }
    }
}
