package angelcotes.models;

import android.content.Context;
import android.media.SoundPool;

/**
 * Created by Alberto Mario Camargo Castro on 21-Mar-16.
 */
public class EffectSound {

    String name; // identification for the effect
    // Memory address: Value return by R.raw.effect, SoundId: value return by SoundPoll.load, StreamId: use for stop or recover a sound. It is the value return by function soundPoolplay(...)
    int memoryAddress, soundId, streamID = -1;

    public EffectSound(String name, int memoryAddress) {
        this.name = name;
        this.memoryAddress = memoryAddress;
    }

    public void loadEffect(SoundPool soundPool, Context context) {
        // params: Context, memory address of sound, priority
        this.soundId = soundPool.load(context, this.memoryAddress, 1);
    }

    public void play(SoundPool soundPool) {
        // stop in case of the sound is playing
        if (this.streamID != -1)
            soundPool.stop(this.streamID);

        // params: ID return by load() method, volume right, volume left, priority, loop, rate (velocity 1. normal, 0.5 - 2)
        this.streamID = soundPool.play(this.soundId, 1, 1, 1, 0, 0);

    }

    public void mute(SoundPool soundPool) {
        soundPool.setVolume(this.streamID, 0, 0);
    }

    public void unMute(SoundPool soundPool) {
        soundPool.setVolume(this.streamID, 1, 1);
    }

}
