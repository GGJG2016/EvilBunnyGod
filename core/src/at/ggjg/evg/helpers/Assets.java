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
    public static TextureRegion cornfield;
    public static TextureRegion fence;
    public static TextureRegion trap;
    public static TextureRegion sacrifice;
    public static TextureRegion house_idle;
    public static TextureRegion house_attacking;
    public static TextureRegion house_destroyed;

    static Array<Animation> allAnimations = new Array<Animation>();
    static Array<TextureRegion> allTextureRegions = new Array<TextureRegion>();

    public static void init() {

        /**
         * Begin Loading Animations
         */
        bunnyAnim = new Animation(3, bunny_1, bunny_2, bunny_3);
        allAnimations.add(bunnyAnim);
        houseAnim = new Animation(3, house_idle, house_attacking, house_destroyed);
        /**
         * End Loading Animations
         */


        /**
         * Begin Loading TextureRegions
         */
        bunny_1 = new TextureRegion(new Texture("rabbit-animation-1.png"));
        bunny_2 = new TextureRegion(new Texture("rabbit-animation-2.png"));
        bunny_3 = new TextureRegion(new Texture("rabbit-animation-3.png"));
        allTextureRegions.add(bunny_1);
        allTextureRegions.add(bunny_2);
        allTextureRegions.add(bunny_3);
        cornfield = new TextureRegion(new Texture("trap.png"));
        allTextureRegions.add(cornfield);
        fence = new TextureRegion(new Texture("fence.png"));
        allTextureRegions.add(fence);
        house_idle = new TextureRegion(new Texture("house-1.png"));
        house_attacking = new TextureRegion(new Texture("house-2.png"));
        house_destroyed = new TextureRegion(new Texture("house-3.png"));
        allTextureRegions.add(house_idle);
        allTextureRegions.add(house_attacking);
        allTextureRegions.add(house_destroyed);
        trap = new TextureRegion(new Texture("trap.png"));
        allTextureRegions.add(trap);
        sacrifice = new TextureRegion(new Texture("opfer.png"));
        allTextureRegions.add(sacrifice);
        /**
         * End Loading TextureRegions
         */

    }

    private static Animation loadAnimation(String path, int frames, float frameDuration) {
        TextureRegion[] regions = new TextureRegion[frames];

        for (int i = 0; i < frames; i++) {
            Texture tex = new Texture(Gdx.files.internal(path + i + ".png"));
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
