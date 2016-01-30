package at.ggjg.evg.mechanic;

/**
 * Created by Veit on 29.01.2016.
 */

import at.ggjg.evg.State;
import at.ggjg.evg.entities.*;
import at.ggjg.evg.helpers.Assets;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class WorldRenderer {
    private static final float MODE_TRANSITION_TIME = 1.0f;

    private static final float CAM_DAMP = 4;
    private static final int CULL_RADIUS = 10;
    public OrthographicCamera camera;
    public Animation mainIdle;
    public Animation mainAxeIdle;
    public Animation mainAttack;
    public Animation[] lethalObstacleAnim = new Animation[2];
    public Animation[] obstacleAnim = new Animation[2];
    public Animation poof;
    public Texture houseAttacking;
    public Texture houseIdle;
    public Texture mainDead;
    public Texture fence;
    World world;
    SpriteBatch batch;
    OrthogonalTiledMapRenderer tileMapRenderer;
    ShaderProgram vignetteShader;
    ShapeRenderer sr = new ShapeRenderer();
    private int LAYER_FLOOR = 0;
    private int LAYER_INTERIEUR = 3;
//    public Texture pill;
//    public Texture axe;
//    public Texture blood;
//    public Texture switchOn;
//    public Texture switchOff;
//    public Texture patient1RedEyes;
//    public Texture patient2RedEyes;

    ShapeRenderer shapeDebugger;
    public WorldRenderer(World world) {
          shapeDebugger = new ShapeRenderer();
        this.world = world;
        //loadAssets();
        Assets.init();
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth() / (float) World.TILE_SIZE, Gdx.graphics.getHeight() / (float) World.TILE_SIZE);
        tileMapRenderer = new OrthogonalTiledMapRenderer(world.map, 1f / World.TILE_SIZE, batch);

        // vignette shader
//        ShaderProgram.pedantic = false;
//        vignetteShader = new ShaderProgram(Gdx.files.internal("graphics/vignette.vsh"), Gdx.files.internal("graphics/vignette.fsh"));
//        if (!vignetteShader.isCompiled())
//            System.out.println(vignetteShader.getLog());
//        batch.setShader(vignetteShader);

        // figure out which layer has which id, idiotic
        for (int i = 0; i < world.map.getLayers().getCount(); i++) {
            MapLayer layer = world.map.getLayers().get(i);
            if (layer.getName().equals("floor")) LAYER_FLOOR = i;
            if (layer.getName().equals("interieur")) LAYER_INTERIEUR = i;
        }
    }

    public void dispose() {
        batch.dispose();
        tileMapRenderer.dispose();
        vignetteShader.dispose();
        world.map.dispose();
        disposeAnim(mainIdle);
        disposeAnim(mainAxeIdle);
        disposeAnim(mainAttack);
        mainDead.dispose();
        disposeAnim(lethalObstacleAnim[0]);
        disposeAnim(lethalObstacleAnim[1]);
        disposeAnim(obstacleAnim[0]);
        disposeAnim(obstacleAnim[1]);
//        doorClosed.dispose();
//        doorOpen.dispose();
//        doorVertical.dispose();
//        pill.dispose();
//        axe.dispose();
//        blood.dispose();
//        switchOn.dispose();
//        switchOff.dispose();
//        patient1RedEyes.dispose();
//        patient2RedEyes.dispose();
        sr.dispose();
    }

    private void disposeAnim(Animation mainIdle2) {
        for (TextureRegion region : mainIdle.getKeyFrames()) {
            region.getTexture().dispose();
        }
    }

    private void loadAssets() {
        // main
        mainIdle = loadAnimation("graphics/animations/main-normal-idle", 2, 0.5f);
        mainAxeIdle = loadAnimation("graphics/animations/main-axe-idle", 2, 0.5f);
        mainAttack = loadAnimation("graphics/animations/main-char-axe", 2, 1);
        mainDead = new Texture("graphics/animations/main-dead.png");

        // patient1
        lethalObstacleAnim[0] = loadAnimation("graphics/animations/patient1-real-idle", 2, 0.5f);
        // patient1RedEyes = new Texture(Gdx.files.internal("graphics/animations/patient1-redEyes.png"));

        // patient2
        obstacleAnim[0] = loadAnimation("graphics/animations/patient2-ghost-idle", 2, 0.5f);
        //   obstacleAnim[World.modeToInt(World.Mode.REAL)] = loadAnimation("graphics/animations/patient2-real-idle", 2, 0.5f);
        //  patient2RedEyes = new Texture(Gdx.files.internal("graphics/animations/patient2-redEyes.png"));

        // poof
        poof = loadAnimation("graphics/animations/poof-", 2, 0.3f);

        // statics
//        doorOpen = new Texture(Gdx.files.internal("graphics/door-open.png"));
//        doorClosed = new Texture(Gdx.files.internal("graphics/door-closed.png"));
//        doorVertical = new Texture(Gdx.files.internal("graphics/door-vertical.png"));
//        pill = new Texture(Gdx.files.internal("graphics/tablette.png"));
//        axe = new Texture(Gdx.files.internal("graphics/axe.png"));
//        blood = new Texture(Gdx.files.internal("graphics/blood.png"));
//        switchOn = new Texture(Gdx.files.internal("graphics/switch-on.png"));
//        switchOff = new Texture(Gdx.files.internal("graphics/switch-off.png"));
    }

    private Animation loadAnimation(String path, int frames, float frameDuration) {
        TextureRegion[] regions = new TextureRegion[frames];
        for (int i = 1; i <= frames; i++) {
            Texture tex = new Texture(Gdx.files.internal(path + i + ".png"));
            regions[i - 1] = new TextureRegion(tex);
        }
        return new Animation(frameDuration, regions);
    }

    public void render(float deltaTime) {
        //debuglines
        int height = Gdx.graphics.getHeight() / 4;
        int width = Gdx.graphics.getWidth() / 3;
        for(int i = 0; i<4; i++){
            shapeDebugger.begin(ShapeRenderer.ShapeType.Line);
            shapeDebugger.setColor(1, 1, 1, 1);
            shapeDebugger.line(0, i*height, Gdx.graphics.getWidth(), i*height);
            shapeDebugger.end();
        }
        for (int i = 0; i < 4; i++) {
            shapeDebugger.begin(ShapeRenderer.ShapeType.Line);
            shapeDebugger.setColor(1, 1, 1, 1);
            shapeDebugger.line(i*width, 0, i*width, Gdx.graphics.getHeight());
            shapeDebugger.end();
        }
        //debuglines end
        // set vignette based on
//        vignetteShader.begin();
//        vignetteShader.setUniformf("u_resolution", Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//
//        float transition = 0.0f;
//
//        vignetteShader.setUniformf("tint", 1, 0.7f + transition * 0.3f, 0.7f + transition * 0.3f, 1);
//        vignetteShader.setUniformf("innerRadius", 0.02f);
//        vignetteShader.setUniformf("outerRadius", 0.4f + transition * 0.5f);
//        vignetteShader.setUniformf("intensity", 0.99f);
//        vignetteShader.setUniformf("noise", 1 - transition);
//        vignetteShader.end();
        cameraFollow(deltaTime);
        camera.update();

        // render tiles
        tileMapRenderer.setView(camera);
        tileMapRenderer.render(new int[]{LAYER_FLOOR});

        // render collision layer
        sr.setProjectionMatrix(camera.combined);
        if (false) {
            Color c = new Color(1, 0, 0, 1);
            sr.begin(ShapeRenderer.ShapeType.Filled);
            for (int x = 0; x < world.walls.length; x++) {
                for (int y = 0; y < world.walls[x].length; y++) {
                    Rectangle r = world.walls[x][y];
                    if (r != null) {
                        sr.rect(r.x, r.y, r.width, r.height, c, c, c, c);
                    }
                }
            }
            sr.end();
        }

        // render interieur
//        tileMapRenderer.render(new int[]{LAYER_INTERIEUR});

        // render objects
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        // blood at the bottom...
        for (GameObject entity : world.entities) {
            if (entity.position.dst(camera.position.x, camera.position.y) > CULL_RADIUS) continue;
            //if (!entity.isVisible) continue;
            if (entity instanceof LethalObstacle) {
                renderLethalObstacle((LethalObstacle) entity);
            } else if (entity instanceof NonLethalObstacle) {
                renderNonLethalObstacle((NonLethalObstacle) entity);
            } else if (entity instanceof House) {
                House house = (House) entity;
                if (house.state == State.ATTACKING) {
                    TextureRegion frame = new TextureRegion(houseAttacking);
                    frame = clip(frame, false);
                    batch.draw(frame, entity.position.x, entity.position.y, 1, 1);
                } else {
                    TextureRegion frame = new TextureRegion(houseIdle);
                    frame = clip(frame, false);
                    batch.draw(frame, entity.position.x, entity.position.y, 1, 1);
                }
            }
        }

        for (GameObject entity : world.bunnies) {
            if (entity.position.dst(camera.position.x, camera.position.y) > CULL_RADIUS) continue;
            //   if (!entity.isVisible) continue;
            if (entity instanceof Bunny) {
                renderBunny((Bunny) entity);
            }
        }
        batch.end();
        // draw entity bounds
//        sr.begin(ShapeRenderer.ShapeType.Line);
//        sr.setColor(0, 1, 0, 1);
//        for (GameObject gameObject : world.entities) {
//            sr.rect(gameObject.bounds.x, gameObject.bounds.y, gameObject.bounds.width, gameObject.bounds.height);
//        }
//        sr.end();
    }

    private void renderLethalObstacle(LethalObstacle entity) {
        Animation[] anims = entity instanceof Fence ? obstacleAnim : lethalObstacleAnim;
        TextureRegion frame;
        float offset = 0;

        switch (entity.state) {
            case IDLE:
//                if (entity.heading == Enemy.Heading.Left) {
//                    frame = anims[mode].getKeyFrame(entity.stateTime, true);
//                    offset = clipOffset(frame, upper);
//                    frame = clip(frame, upper);
//                    frame.flip(true, false);
//                    batch.draw(frame, entity.position.x, entity.position.y + offset, 1, 1);
//                } else {
//                    frame = anims[mode].getKeyFrame(entity.stateTime, true);
//                    offset = clipOffset(frame, upper);
//                    frame = clip(frame, upper);
//                    batch.draw(frame, entity.position.x, entity.position.y + offset, 1, 1);
//                }
                break;
            case ATTACKING:
//                if (entity.heading == Enemy.Heading.Left) {
//                    frame = anims[mode].getKeyFrame(entity.stateTime, true);
//                    offset = clipOffset(frame, upper);
//                    frame = clip(frame, upper);
//                    frame.flip(true, false);
//                    batch.draw(frame, entity.position.x, entity.position.y + offset, 1, 1);
//                } else {
//                    frame = anims[mode].getKeyFrame(entity.stateTime, true);
//                    offset = clipOffset(frame, upper);
//                    frame = clip(frame, upper);
//                    batch.draw(frame, entity.position.x, entity.position.y + offset, 1, 1);
//                }
                break;
            case DESTROYED:
//                if (world.mode == World.Mode.REAL) {
//                    batch.draw(blood, entity.position.x, entity.position.y, 1, 1);
//                } else {
//                    if (!poof.isAnimationFinished(entity.stateTime)) {
//                        frame = poof.getKeyFrame(entity.stateTime, false);
//                        batch.draw(frame, entity.position.x, entity.position.y, 1, 2);
//                    }
//                }
            default:
        }
    }

    private void renderNonLethalObstacle(NonLethalObstacle obstacle) {

    }

    private TextureRegion clip(TextureRegion region, boolean upper) {
        if (region.getRegionWidth() == 64 && region.getRegionHeight() == 64) return new TextureRegion(region);
        if (upper) return new TextureRegion(region.getTexture(), 0, 0, region.getRegionWidth(), 64);
        else return new TextureRegion(region.getTexture(), 0, 64, region.getRegionWidth(), 64);
    }

    private float clipOffset(TextureRegion region, boolean upper) {
        if (region.getRegionWidth() == 64 && region.getRegionHeight() == 64) return 0;
        if (upper) return 1;
        else return 0;
    }

    private void renderBunny(Bunny bunny) {
        TextureRegion frame;
        Animation anim = null;
        bunny.render(batch);
        float offset = 0;
    }

    private void cameraFollow(float deltaTime) {
        // TODO: enaable for bunny swarm
        Vector2 dist = new Vector2(world.bunnies.get(0).position).sub(camera.position.x, camera.position.y);
        camera.position.add(dist.x * deltaTime * CAM_DAMP, dist.y * deltaTime * CAM_DAMP, 0);
    }
}