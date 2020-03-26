package com.haanhgs.app.sound.model;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.SoundPool;
import java.io.IOException;
import androidx.lifecycle.MutableLiveData;

public class Repo {

    private final MutableLiveData<Sound> liveData = new MutableLiveData<>();
    private Sound sound;

    private final Context context;
    private SoundPool soundPool;

    private void initSoundPool(){
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                .build();
        soundPool = new SoundPool.Builder().setMaxStreams(5).setAudioAttributes(attributes).build();
    }

    private void loadSound(){
        sound = new Sound();
        AssetManager manager = context.getAssets();
        AssetFileDescriptor descriptor;
        try{
            descriptor = manager.openFd("fx1.ogg");
            sound.setId1(soundPool.load(descriptor, 1));

            descriptor = manager.openFd("fx2.ogg");
            sound.setId2(soundPool.load(descriptor, 1));

            descriptor = manager.openFd("fx3.ogg");
            sound.setId3(soundPool.load(descriptor, 1));

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public Repo(Context context) {
        this.context = context;
        initSoundPool();
        loadSound();
        liveData.setValue(sound);
    }

    public MutableLiveData<Sound> getLiveData() {
        return liveData;
    }

    public void setRepeat(int repeat){
        sound.setRepeat(repeat);
        liveData.setValue(sound);
    }

    public void setVolume(float volume){
        sound.setVolume(volume);
        soundPool.setVolume(sound.getNowPlaying(), sound.getVolume(), sound.getVolume());
        liveData.setValue(sound);
    }

    public void play(int id){
        soundPool.stop(sound.getNowPlaying());
        int nowPlaying = soundPool.play(id, sound.getVolume(), sound.getVolume(), 1,
                sound.getRepeat(), 1);
        sound.setNowPlaying(nowPlaying);
        liveData.setValue(sound);
    }

    public void stop(){
        soundPool.stop(sound.getNowPlaying());
    }

}
