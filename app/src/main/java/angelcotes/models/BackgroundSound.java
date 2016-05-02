package angelcotes.models;

import android.content.Context;
import android.media.MediaPlayer;

/** Created by Alberto Camargo on 15-Mar-16. **/

public class BackgroundSound {

   private MediaPlayer backgroundMusic = null;

   public BackgroundSound(Context ctx, int media) {
       // Create Media Player object
       backgroundMusic = MediaPlayer.create(ctx, media);
       backgroundMusic.setLooping(true);

   }

   public void play() {
       if (!isPlaying()) backgroundMusic.start();
   }

   public void mute() {
       backgroundMusic.setVolume(0, 0);
   }

   public  void unmute() {
       backgroundMusic.setVolume(1, 1);
   }

   public void pause() { backgroundMusic.pause(); }

   public void resume() {
       play();
   }

   public boolean isPlaying() {
       return  backgroundMusic == null ? false : backgroundMusic.isPlaying();
   }


}
