package at.ggjg.evg;

/**
 * Created by Veit on 29.01.2016.
 */
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Array;

import java.util.Random;

public class AudioManager {
    private static final float MUSIC_VOLUME = 0.7f;
    public Music backgroundmusic;
    public Music menu_loop_all;
    private Random random;
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

    private State state;

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
    private float playingTime;
    private boolean stateChanged;
    private  Music currentMusic;
    private  Music nextMusic;
    private boolean newMusic;

    public AudioManager() {
        random = new Random();
        newMusic = true;
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
        playingTime = 0;
        currentMusic = menu_loop_all;
        nextMusic = menu_loop_all;

    }

    public void playRandomGameHighLoop()
    {
        switch(random.nextInt(4)) {
            case 1:
                nextMusic = loops_game_high_01;
                break;
            case 2:
                nextMusic = loops_game_high_02;
                break;
            case 3:
                nextMusic = loops_game_high_03;
                break;
            case 4:
                nextMusic = loops_game_high_04;
            default:
        }
    }
    public void playMenuTheme()
    {
        currentMusic = menu_loop_all;
        currentMusic.play();
        nextMusic = currentMusic;
    }
    public void playRandomGameMidLoop()
    {
      if(random.nextBoolean()){
          nextMusic = loops_game_mid_01;
      }
        else{
          nextMusic = loops_game_mid_02;
      }
    }

    public void playRandomGameLowLoop()
    {

        if(random.nextBoolean()){
            nextMusic = loops_game_low_01;
        }
        else{
            nextMusic = loops_game_low_02;
        }
    }


    public void setNewState(State s){

        if(state == s)
            stateChanged = false;
        else
            stateChanged = true;
        state = s;
        if(stateChanged)
            update(0);
//        if(state == State.MENU)
//            update(888888f);
    }


    public void update(float deltaTime) {
        playingTime += deltaTime;
        Random r = new Random();
        //System.out.println( " playing time = " + playingTime);
        if(playingTime > 8 || this.state == State.ATTACKING)
        {
            playNext();
            playingTime = 0;
        }
    if(stateChanged) {

        stateChanged = false;
        switch (this.state) {
            case MENU:
                playMenuTheme();
                break;
            case IDLE:
                playRandomGameLowLoop();
                break;
            case MOVING:
                playRandomGameMidLoop();
                break;
            case ATTACKING:
                playRandomGameHighLoop();
                break;
            case DESTROYED:
                break;
            default:
                playNext();
        }
    }
    }
    public void playNext(){
        Music old = currentMusic;
        if(nextMusic == null){
            nextMusic = currentMusic;
        }
        if(nextMusic == currentMusic)
            return;
        currentMusic=nextMusic;
        currentMusic.play();
        old.stop();
        old = null;

    }

    public void dispose() {
        backgroundmusic.dispose();
        menu_loop_all.dispose();
        for(Sound sound: allSounds) {
            sound.dispose();
        }
    }

    public void playKillSounds() {
        Attack.play();
    }
}
