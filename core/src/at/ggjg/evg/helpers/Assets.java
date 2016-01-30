package at.ggjg.evg.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

public class Assets implements Disposable {
    //DO THIS WITH ASSETMANAGER LATER TODO
    public static TextureRegion bunny;

    static Array<Animation> allAnimations = new Array<Animation>();
    static Array<TextureRegion> allTextureRegions = new Array<TextureRegion>();

    public static void init() {

        /**
         * Begin Loading Animations
         */

        /**
         * End Loading Animations
         */


        /**
         * Begin Loading TextureRegions
         */
        bunny = new TextureRegion(new Texture("rabbit-animation-1.png"));
        allTextureRegions.add(bunny);

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
