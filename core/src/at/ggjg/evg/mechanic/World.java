package at.ggjg.evg.mechanic;

/**
 * Created by Veit on 29.01.2016.
 */

import at.ggjg.evg.AudioManager;
import at.ggjg.evg.State;
import at.ggjg.evg.entities.*;
import at.ggjg.evg.helpers.OnMapClickedListener;
import at.ggjg.evg.screens.GameOverScreen;
import at.ggjg.evg.screens.GameplayScreen;
import at.ggjg.evg.screens.MainMenuScreen;
import at.ggjg.evg.screens.ScreenManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

public class World implements OnMapClickedListener {

    public static int TILE_SIZE = 128;
    public TiledMap map;
    public Rectangle[][] walls;
    public Array<GameObject> entities = new Array<GameObject>();
    public Array<Bunny> bunnies = new Array<Bunny>();
    public Array<House> houses = new Array<House>();
    //    public Array<Entity> delete = new Array<Entity>();
    public WorldRenderer renderer;
    public AudioManager audio;
    public int mapWidth, tileWidth;
    public int mapHeight, tileHeight;
    public GameObject currentClickedObj;
    public int level;
    private ScreenManager manager;

    public World(String level) {
        loadLevel(level);
    }

    public void setRenderer(WorldRenderer renderer) {
        this.renderer = renderer;
    }

    public void setAudio(AudioManager audio) {
        this.audio = audio;
    }

    private void loadLevel(String level) {
        // load tile map
        TmxMapLoader.Parameters params = new TmxMapLoader.Parameters();
        params.textureMinFilter = TextureFilter.Linear;
        params.textureMagFilter = TextureFilter.Linear;
        map = new TmxMapLoader().load(level);
        mapWidth = map.getProperties().get("width", Integer.class);
        tileWidth = map.getProperties().get("tilewidth", Integer.class);
        mapHeight = map.getProperties().get("height", Integer.class);
        tileHeight = map.getProperties().get("tileheight", Integer.class);
        // load objects from map
        MapObjects objects = map.getLayers().get("objects").getObjects();
        // create objects
        for (int i = 0; i < objects.getCount(); i++) {
            MapProperties object = objects.get(i).getProperties();
            String type = object.get("type", String.class);
            if (type.equals("bunny")) {

                Bunny bunny = new Bunny(object.get("x", Float.class), object.get("y", Float.class));
                bunny.position.scl(1f / TILE_SIZE);
                bunny.bounds.y /= TILE_SIZE;
                bunny.bounds.x /= TILE_SIZE;
                entities.add(bunny);
                bunnies.add(bunny);
            } else if (type.equals("house")) {
                House house = new House(object.get("x", Float.class), object.get("y", Float.class));
                house.position.scl(1f / TILE_SIZE);
                entities.add(house);
                houses.add(house);
            } else if (type.equals("fence")) {
                Fence fence = new Fence(object.get("x", Float.class), object.get("y", Float.class));
                fence.position.scl(1f / TILE_SIZE);
                entities.add(fence);
            } else if (type.equals("trap")) {
                Trap trap = new Trap(object.get("x", Float.class), object.get("y", Float.class));
                trap.position.scl(1f / TILE_SIZE);
                entities.add(trap);
            } else if (type.equals("cornfield")) {
                Cornfield cf = new Cornfield(object.get("x", Float.class), object.get("y", Float.class));
                cf.position.scl(1f / TILE_SIZE);
                entities.add(cf);
            }
        }
    }

    public void update(float deltaTime) {
        boolean housecheck = true;
        boolean bunnycheck = true;
        for (GameObject entity : entities) {
            entity.update(this, deltaTime);
        }
        for (Bunny bunny : bunnies) {
            if (bunny.state != State.DESTROYED) {
                bunnycheck = false;
            }
        }
        if (bunnycheck) {
            //manager.setScreen(new GameOverScreen(manager));
        }


        for (House house : houses) {
            if (house.state != State.DESTROYED) {
                housecheck = false;
            }
        }
        if (housecheck) {

            if (level == 1){
            manager.setScreen(new GameplayScreen(manager, 2));}
            else if (level == 2){
             manager.setScreen(new MainMenuScreen(manager));}
        }


    }

    public int getNotDestroyedHouses() {
        int count = 0;
        for (House house : houses) {
            if (house.state != State.DESTROYED) count += 1;
        }
        return count;
    }

    public void clipCollision(Rectangle bounds, Vector2 movement) {
        Rectangle newbounds = new Rectangle(bounds.x + movement.x, bounds.y + movement.y, bounds.width, bounds.height);

        int sx, sy, ex, ey, ux, uy;
        if (movement.x > 0) {
            sx = (int) Math.floor(bounds.x);
            ex = (int) Math.ceil(newbounds.x + bounds.width) + 1;
        } else {
            sx = (int) Math.ceil(bounds.x + bounds.width);
            ex = (int) Math.floor(newbounds.x) - 1;
        }

        if (movement.y > 0) {
            sy = (int) Math.floor(bounds.y);
            ey = (int) Math.ceil(newbounds.y + bounds.height) + 1;
        } else {
            sy = (int) Math.ceil(bounds.y + bounds.height);
            ey = (int) Math.floor(newbounds.y) - 1;
        }

        Color c = new Color(0, 0, 1, 1);
        boolean displayDebug = false;

        sx = Math.max(Math.min(sx, walls.length - 1), 0);
        sy = Math.max(Math.min(sy, walls[0].length - 1), 0);
        ex = Math.max(Math.min(ex, walls.length), -1);
        ey = Math.max(Math.min(ey, walls[0].length), -1);
        ux = ex - sx > 0 ? 1 : -1;
        uy = ey - sy > 0 ? 1 : -1;

        for (int x = sx; x != ex; x += ux) {
            for (int y = sy; y != ey; y += uy) {
                Rectangle r = walls[x][y];
                if (r != null) {
                    if (r.overlaps(newbounds)) {
                        float x1, x2, y1, y2;

                        if (movement.x > 0) {
                            x1 = bounds.x + bounds.width;
                            x2 = r.x;
                        } else {
                            x1 = bounds.x;
                            x2 = r.x + r.width;
                        }

                        if (movement.y > 0) {
                            y1 = bounds.y + bounds.height;
                            y2 = r.y;
                        } else {
                            y1 = bounds.y;
                            y2 = r.y + r.height;
                        }

                        float d1 = (x2 - x1) / movement.x;
                        float d2 = (y2 - y1) / movement.y;

                        if (d1 >= 0 && d1 <= 1) {
                            // collision in x direction
                            movement.x = 0;
                        }

                        if (d2 >= 0 && d2 <= 1) {
                            // collision in y direction
                            movement.y = 0;
                        }

                        newbounds.x = bounds.x + movement.x;
                        newbounds.y = bounds.y + movement.y;
                    }
                }
            }
        }

    }

    @Override
    public void onMapClicked(float x, float y, boolean isRightClick) {
        if (isRightClick) {
            return;
        }
        Vector3 clicked = renderer.camera.unproject(new Vector3(x, y, 0f));
        for (Bunny bunny : bunnies) {
            bunny.setNewDestination(clicked);
        }
        for (GameObject entity : entities) {
            if (entity.wasClicked(clicked.x, clicked.y)) {
                currentClickedObj = entity;
                return;
            }
        }
        audio.playMoveSound();
    }
    

    public void setManager(ScreenManager manager) {
        this.manager = manager;
    }

    public void dispose() {
    }
}