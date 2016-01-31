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
    private static AudioManager singletonInstance = null;

    public Music menu_loop_all, intro_loop_main, intro_loop_intro;
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

    public void playBugger() {
         Bugger.play();
    }

    public void playBunnies() {
         Bunnies.play(1);
    }

    public void playCome() {
         Come.play(1);
    }

    public void playDieForMe() {
         DieForMe.play(1);
    }

    public void playEvilBunnieGod() {
         EvilBunnieGod.play(1);
    }

    public void playGo() {
         Go.play(1);
    }

    public void playILoveBunnies() {
         ILoveBunnies.play(1);
    }

    public void playKill() {
         Kill.play(1);
    }

    public void playMove_1() {
         Move_1.play(1);
    }

    public void playMove_2() {
         Move_2.play(1);
    }

    public void playObey() {
         Obey.play();
    }

    public void playPray() {
         Pray.play();
    }

    public void playRest() {
         Rest.play();
    }

    public void playRitual() {
         Ritual.play();
    }

    public void playSacrifice() {
         Sacrifice.play();
    }
    private float vol;
    private Sound Bugger;
    private Sound Bunnies;
    private Sound Come;
    private Sound DieForMe;
    private Sound EvilBunnieGod;
    private Sound Go;
    private Sound ILoveBunnies;
    private Sound Kill;
    private Sound Move_1;
    private Sound Move_2;
    private Sound Obey;
    private Sound Pray;
    private Sound Rest;
    private Sound Ritual;
    private Sound Sacrifice;
    private float playingTime;
    private boolean stateChanged;
    private  Music currentMusic;
    private  Music nextMusic;
    private boolean newMusic;

    private AudioManager() {
        random = new Random();

        newMusic = true;
        vol = 0.2f;
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

        intro_loop_main = Gdx.audio.newMusic(Gdx.files.internal("audio/Loops/intro/main.ogg"));
        intro_loop_intro = Gdx.audio.newMusic(Gdx.files.internal("audio/Loops/intro/intro.ogg"));

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
    currentMusic.setVolume(vol);
    }

    public void playRandomGameHighLoop()
    {
        switch(random.nextInt(4)) {
            case 0:
                nextMusic = loops_game_high_01;
                break;
            case 1:
                nextMusic = loops_game_high_02;
                break;
            case 2:
                nextMusic = loops_game_high_03;
                break;
            case 3:
                nextMusic = loops_game_high_04;
            default:
        }
    }
    public void playMenuTheme()
    {
        currentMusic.stop();
        currentMusic = menu_loop_all;
        currentMusic.setVolume(vol);
        currentMusic.play();
        currentMusic.setVolume(vol);
        nextMusic = currentMusic;
    }
    public void playIntroTheme()
    {
        if(currentMusic!=null)
            currentMusic.stop();
        currentMusic = intro_loop_intro;
        intro_loop_intro.setOnCompletionListener(new Music.OnCompletionListener() {
            @Override
            public void onCompletion(Music music) {
                currentMusic = intro_loop_main;
                currentMusic.setVolume(vol);
                currentMusic.play();
                currentMusic.setVolume(vol);
            }
        });
        currentMusic.setVolume(vol);
        currentMusic.play();
        currentMusic.setVolume(vol);
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
//        float delay = 0;
        if(state == s)
            stateChanged = false;
        else
            stateChanged = true;

//        if(state == State.MENU&& stateChanged){
//            System.out.println("menu to game");
////            delay =8.1f;
//        }

        state = s;


        if(stateChanged) {

            switch (s) {
                case MENU:
                    playMenuTheme();
                    break;
                case INTRO:
                    playIntroTheme();
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
                    // playNext();
            }
            stateChanged = false;
            if (playingTime > 1 || this.state == State.ATTACKING) {
                playNext();
                playingTime = 0;
            }
        }



//        if(stateChanged)
//            update(delay);
//        if(state == State.MENU)
//            update(888888f);
    }


    public void update(float deltaTime) {

        playingTime += deltaTime;
  //      Random r = new Random();
        //System.out.println( " playing time = " + playingTime);
  //  if(stateChanged) {

  //      stateChanged = false;
//        switch (this.state) {
//            case MENU:
//               System.out.println("MENU State");
//                playMenuTheme();
//                break;
//            case IDLE:
//                playRandomGameLowLoop();
//                break;
//            case MOVING:
//                playRandomGameMidLoop();
//                break;
//            case ATTACKING:
//                playRandomGameHighLoop();
//                break;
//            case DESTROYED:
//                break;
//            default:
//               // playNext();
//        }
//        if(playingTime > 1 || this.state == State.ATTACKING)
//        {
//            playNext();
//            playingTime = 0;
//        }

    }

    public void playNext(){
        System.out.println("next sound");

        Music old = currentMusic;

        if(nextMusic == null){
            nextMusic = currentMusic;
        }
        if(nextMusic == currentMusic)
            return;

        currentMusic=nextMusic;
        currentMusic.setVolume(vol);
        currentMusic.play();
        currentMusic.setVolume(vol);

        old.stop();
        old = null;

    }

    public void dispose() {
        menu_loop_all.dispose();
        for(Sound sound: allSounds) {
            sound.dispose();
        }
    }

    public void playKillSounds() {
        System.out.println("kill!!!!!");
        Attack.play();
    }

    public static AudioManager getInstance() {
        if (singletonInstance == null) singletonInstance = new AudioManager();
        return singletonInstance;
    }

    public void playAttackSounds() {
        Attack.play();
    }

    public void playMoveSound() {
        System.out.println("MOVE sound!!!!!");
        switch(random.nextInt(4)) {
            case 0:
                playMove_1();
                break;
            case 1:
                playMove_2();
                break;
            case 2:
                playObey();
                break;
            case 4:
                playCome();
                break;
            default:
                break;
        }
    }

    public void playSchnackselnSound() {
        switch(random.nextInt(2)) {
            case 0:
                playILoveBunnies();
                break;
            case 1:
                playBunnies();
                break;

            default:
                break;
        }
    }
}

