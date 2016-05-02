package angelcotes.models;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import java.util.Hashtable;
import java.util.Iterator;

/**
 * Created by Alberto Camargo on 21-Mar-16.
 */
public class EffectSoundManager {
    Context context;
    Hashtable<String,EffectSound> effects = new Hashtable<>();
    SoundPool soundpool;
    private boolean mute;

    public EffectSoundManager(Context context) {

        this.mute = false;

        /*  Api Must be greater than 17. min API: 21.
            Params: maximum effects playing ,kind of audio, quality of sound (default 0, actually is deprecated)
        */
        this.soundpool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        this.context = context;

    }

    public void addEffect(String name, int addressMemoryEffect) {
        // {'explosion': Object EffectSound}
        EffectSound effectSound =  new EffectSound(name, addressMemoryEffect);
        effectSound.loadEffect(this.soundpool, this.context);
        effects.put(name, effectSound);
    }

    public  void playEffect(String name) {
        if (!this.mute)
            effects.get(name).play(this.soundpool);
    }

    public  void mute() {
        this.mute = true;
        Iterator<EffectSound> values = this.effects.values().iterator();
        do this.soundpool.stop(values.next().streamID); while (values.hasNext());
    }

    public  void unmute() {
        this.mute = false;
    }

    public void pause() {
        this.soundpool.autoPause();
    }

    public void resume() {
        this.soundpool.autoResume();
    }


}
