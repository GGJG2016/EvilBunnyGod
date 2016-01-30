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

    public Sound Attack;
    public Sound Bugger;
    public Sound Bunnies;
    public Sound Come;
    public Sound DieForMe;
    public Sound EvilBunnieGod;
    public Sound Go;
    public Sound ILoveBunnies;
    public Sound Kill;
    public Sound Move_1;
    public Sound Move_2;
    public Sound Obey;
    public Sound Pray;
    public Sound Rest;
    public Sound Ritual;
    public Sound Sacrifice;


    public AudioManager() {
        backgroundmusic = Gdx.audio.newMusic(Gdx.files.internal("audio/backgroundMusic.mp3"));
        backgroundmusic.setLooping(true);

        loops_game_high_01 = Gdx.audio.newMusic(Gdx.files.internal("audio/Loops/game/high-01.ogg"));
        loops_game_high_02  = Gdx.audio.newMusic(Gdx.files.internal("audio/Loops/game/high-02.ogg"));
        loops_game_high_03  = Gdx.audio.newMusic(Gdx.files.internal("audio/Loops/game/high-03.ogg"));
        loops_game_high_04  = Gdx.audio.newMusic(Gdx.files.internal("audio/Loops/game/high-04.ogg"));
        loops_game_low_01 = Gdx.audio.newMusic(Gdx.files.internal("audio/Loops/game/low-01.ogg"));
        loops_game_low_02  = Gdx.audio.newMusic(Gdx.files.internal("audio/Loops/game/low-02.ogg"));
        loops_game_mid_01 = Gdx.audio.newMusic(Gdx.files.internal("audio/Loops/game/mid-01.ogg"));
        loops_game_mid_02 = Gdx.audio.newMusic(Gdx.files.internal("audio/Loops/game/mid-02.ogg"));

        loops_menu_01  = Gdx.audio.newMusic(Gdx.files.internal("audio/Loops/menu/loop-01.ogg"));
        loops_menu_02  = Gdx.audio.newMusic(Gdx.files.internal("audio/Loops/menu/loop-02.ogg"));
        loops_menu_03  = Gdx.audio.newMusic(Gdx.files.internal("audio/Loops/menu/loop-03.ogg"));
        loops_menu_04 = Gdx.audio.newMusic(Gdx.files.internal("audio/Loops/menu/loop-04.ogg"));
        loops_menu_all = Gdx.audio.newMusic(Gdx.files.internal("audio/Loops/menu/menu_loop_all.ogg"));

        menu_loop_all = Gdx.audio.newMusic(Gdx.files.internal("audio/menu_loop_all.ogg"));
        menu_loop_all.setLooping(true);

        Attack = Gdx.audio.newSound(Gdx.files.internal("audio/Sfx/Attack.ogg"));
        Bugger = Gdx.audio.newSound(Gdx.files.internal("audio/Sfx/Bugger.ogg"));
        Bunnies= Gdx.audio.newSound(Gdx.files.internal("audio/Sfx/Bunnies.ogg"));
        Come= Gdx.audio.newSound(Gdx.files.internal("audio/Sfx/Come.ogg"));
        DieForMe= Gdx.audio.newSound(Gdx.files.internal("audio/Sfx/DieForMe.ogg"));
        EvilBunnieGod = Gdx.audio.newSound(Gdx.files.internal("audio/Sfx/EvilBunnieGod.ogg"));
        Go = Gdx.audio.newSound(Gdx.files.internal("audio/Sfx/Go.ogg"));
        ILoveBunnies = Gdx.audio.newSound(Gdx.files.internal("audio/Sfx/ILoveBunnies.ogg"));
        Kill = Gdx.audio.newSound(Gdx.files.internal("audio/Sfx/Kill.ogg"));
        Move_1 = Gdx.audio.newSound(Gdx.files.internal("audio/Sfx/Move-1.ogg"));
        Move_2 = Gdx.audio.newSound(Gdx.files.internal("audio/Sfx/Move-2.ogg"));
        Obey = Gdx.audio.newSound(Gdx.files.internal("audio/Sfx/Obey.ogg"));
        Pray = Gdx.audio.newSound(Gdx.files.internal("audio/Sfx/Pray.ogg"));
        Rest = Gdx.audio.newSound(Gdx.files.internal("audio/Sfx/Rest.ogg"));
        Ritual = Gdx.audio.newSound(Gdx.files.internal("audio/Sfx/Ritual.ogg"));
        Sacrifice = Gdx.audio.newSound(Gdx.files.internal("audio/Sfx/Sacrifice.ogg"));

        allSounds.add(Attack);
        allSounds.add(Bugger);
        allSounds.add(Bunnies);
        allSounds.add(Come);
        allSounds.add(DieForMe);
        allSounds.add(EvilBunnieGod);
        allSounds.add(Go);
        allSounds.add(ILoveBunnies);
        allSounds.add(Kill);
        allSounds.add(Move_1);
        allSounds.add(Move_2);
        allSounds.add(Obey);
        allSounds.add(Pray);
        allSounds.add(Rest);
        allSounds.add(Ritual);
        allSounds.add(Sacrifice);
        
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
