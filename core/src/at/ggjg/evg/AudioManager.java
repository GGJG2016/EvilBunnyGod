package at.ggjg.evg;

/**
 * Created by Veit on 29.01.2016.
 */
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Array;

public class AudioManager {
    private static final float MUSIC_VOLUME = 0.7f;
    public Music backgroundmusic;
    public Music menu_loop_all;

    public Music loops_game_high_01;
    public Music loops_game_high_02;
    public Music loops_game_high_03;
    public Music loops_game_high_04;
    public Music loops_game_low_01;
    public Music loops_game_low_02;
    public Music loops_game_mid_01;
    public Music loops_game_mid_02;

    public Music loops_menu_01;
    public Music loops_menu_02;
    public Music loops_menu_03;
    public Music loops_menu_04;
    public Music loops_menu_all;


    Array<Sound> allSounds = new Array<Sound>();

    public Sound sampleSound;



    public AudioManager() {
        backgroundmusic = Gdx.audio.newMusic(Gdx.files.internal("audio/backgroundMusic.mp3"));
        backgroundmusic.setLooping(true);

        menu_loop_all = Gdx.audio.newMusic(Gdx.files.internal("audio/menu_loop_all.ogg"));
        menu_loop_all.setLooping(true);


//        sampleSound = Gdx.audio.newSound(Gdx.files.internal("audio/effects/sampleSound.mp3"));
//        allSounds.add(sampleSound);
    }

    public void update(float deltaTime) {

    }

    public void dispose() {
        backgroundmusic.dispose();
        menu_loop_all.dispose();
        for(Sound sound: allSounds) {
            sound.dispose();
        }
    }
}
