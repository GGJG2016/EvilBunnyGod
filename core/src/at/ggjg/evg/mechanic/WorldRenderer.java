package at.ggjg.evg.mechanic;

/**
 * Created by Veit on 29.01.2016.
 */

import at.ggjg.evg.entities.Bunny;
import at.ggjg.evg.entities.GameObject;
import at.ggjg.evg.helpers.Assets;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class WorldRenderer {
    private static final float MODE_TRANSITION_TIME = 1.0f;

    private static final float CAM_DAMP = 4;
    private static final int CULL_RADIUS = 10;
    public OrthographicCamera camera;
    public Animation mainIdle;
    World world;
    SpriteBatch batch;
    OrthogonalTiledMapRenderer tileMapRenderer;
    ShapeRenderer sr = new ShapeRenderer();
    ShapeRenderer shapeDebugger;
    private int LAYER_FLOOR = 0;

    public WorldRenderer(World world) {
        shapeDebugger = new ShapeRenderer();
        this.world = world;
        //loadAssets();
        Assets.init();
        initGameObjects();
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth() / (float) World.TILE_SIZE, Gdx.graphics.getHeight() / (float) World.TILE_SIZE);
        camera.zoom += 2;

        tileMapRenderer = new OrthogonalTiledMapRenderer(world.map, 1f / World.TILE_SIZE, batch);

        // figure out which layer has which id, idiotic
        for (int i = 0; i < world.map.getLayers().getCount(); i++) {
            MapLayer layer = world.map.getLayers().get(i);
            if (layer.getName().equals("floor")) LAYER_FLOOR = i;
        }
    }

    private void initGameObjects() {
        for (GameObject entity : world.entities) {
            entity.init(world);
        }
//        for (Bunny bunny : world.bunnies) {
//            bunny.init(world);
//        }
    }

    public void dispose() {
        batch.dispose();
        tileMapRenderer.dispose();
        world.map.dispose();
        sr.dispose();
    }

    private void disposeAnim(Animation mainIdle2) {
        for (TextureRegion region : mainIdle.getKeyFrames()) {
            region.getTexture().dispose();
        }
    }

    public void render(float deltaTime) {
        //debuglines
        int height = Gdx.graphics.getHeight() / 4;
        int width = Gdx.graphics.getWidth() / 3;
        for (int i = 0; i < 4; i++) {
            shapeDebugger.begin(ShapeRenderer.ShapeType.Line);
            shapeDebugger.setColor(1, 1, 1, 1);
            shapeDebugger.line(0, i * height, Gdx.graphics.getWidth(), i * height);
            shapeDebugger.end();
        }
        for (int i = 0; i < 4; i++) {
            shapeDebugger.begin(ShapeRenderer.ShapeType.Line);
            shapeDebugger.setColor(1, 1, 1, 1);
            shapeDebugger.line(i * width, 0, i * width, Gdx.graphics.getHeight());
            shapeDebugger.end();
        }

        //cameraFollow(deltaTime);
        handleInput();
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
            entity.render(batch);
        }

        batch.end();
        // draw entity bounds
        sr.begin(ShapeRenderer.ShapeType.Line);
        sr.setColor(0, 1, 0, 1);
        for (GameObject gameObject : world.entities) {
            sr.rect(gameObject.bounds.x, gameObject.bounds.y, gameObject.bounds.width, gameObject.bounds.height);
        }
        sr.end();
    }

    public void resize(int width, int height) {
        camera.viewportWidth = 30f;
        camera.viewportHeight = 30f * height / width;
        camera.update();
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

    private void cameraFollow(float deltaTime) {
        // TODO: enaable for bunny swarm
        Vector2 dist = new Vector2(world.bunnies.get(0).position).sub(camera.position.x, camera.position.y);
        camera.position.add(dist.x * deltaTime * CAM_DAMP, dist.y * deltaTime * CAM_DAMP, 0);
    }

    /**
     * fetch keyboard input
     */
    private void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            camera.zoom += 0.02;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.E)) {
            camera.zoom -= 0.02;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            camera.translate(-0.3f, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            camera.translate(0.3f, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
            camera.translate(0, -0.3f, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            camera.translate(0, 0.3f, 0);
        }

        int mapWidth = world.mapWidth;
        int mapHeight = world.mapHeight;
        float effectiveViewportWidth = camera.viewportWidth * camera.zoom;
        float effectiveViewportHeight = camera.viewportHeight * camera.zoom;

        float camViewportHalfX = effectiveViewportWidth / 2;
        float camViewportHalfY = effectiveViewportHeight / 2;
        camera.zoom = MathUtils.clamp(camera.zoom, 0.3f, 3); // TODO: make generic

        if (camera.position.x < camViewportHalfX) {
            camera.position.x = camViewportHalfX;
        } else if (camera.position.x > mapWidth - camViewportHalfX) {
            camera.position.x = mapWidth - camViewportHalfX;
        }
        if (camera.position.y < camViewportHalfY) {
            camera.position.y = camViewportHalfY;
        } else if (camera.position.y > mapHeight - camViewportHalfY) {
            camera.position.y = mapHeight - camViewportHalfY;
        }
    }

}