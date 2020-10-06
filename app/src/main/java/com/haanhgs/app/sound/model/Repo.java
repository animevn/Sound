package com.haanhgs.app.sound.model;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.util.Log;
import java.io.IOException;
import androidx.lifecycle.MutableLiveData;

public class Repo {

    private MutableLiveData<Sound> liveData = new MutableLiveData<>();
    private Sound sound;

    private Context context;
    private SoundPool soundPool;

    public Repo(Context context) {
        this.context = context;
        initSoundPool();
        loadSound();
        liveData.setValue(sound);
    }

    private void initSoundPool() {
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                .build();
        soundPool = new SoundPool.Builder().setMaxStreams(5).setAudioAttributes(attributes).build();
    }

    private void loadSound() {
        sound = new Sound();
        AssetManager assetManager = context.getAssets();
        AssetFileDescriptor descriptor;
        try{
            descriptor = assetManager.openFd("fx1.ogg");
            sound.setId1(soundPool.load(descriptor, 1));

            descriptor = assetManager.openFd("fx2.ogg");
            sound.setId2(soundPool.load(descriptor, 1));

            descriptor = assetManager.openFd("fx3.ogg");
            sound.setId3(soundPool.load(descriptor, 1));
        }catch (IOException e){
            Log.e("IOError: ", e.toString());
        }
    }

    public MutableLiveData<Sound> getLiveData(){
        return liveData;
    }

    public void setRepeat(int repeat){
        sound.setRepeat(repeat);
        liveData.setValue(sound);
    }

    public void setVolume(float volume){
        sound.setVolume(volume);
        soundPool.setVolume(sound.getNowPlaying(), volume, volume);
        liveData.setValue(sound);
    }

    public void playId1(){
        soundPool.stop(sound.getNowPlaying());
        int nowPlaying = soundPool.play(
                sound.getId1(), sound.getVolume(), sound.getVolume(), 1, sound.getRepeat(), 1
        );
        sound.setNowPlaying(nowPlaying);
        liveData.setValue(sound);
    }

    public void playId2(){
        soundPool.stop(sound.getNowPlaying());
        int nowPlaying = soundPool.play(
                sound.getId2(), sound.getVolume(), sound.getVolume(), 1, sound.getRepeat(), 1
        );
        sound.setNowPlaying(nowPlaying);
        liveData.setValue(sound);
    }

    public void playId3(){
        soundPool.stop(sound.getNowPlaying());
        int nowPlaying = soundPool.play(
                sound.getId3(), sound.getVolume(), sound.getVolume(), 1, sound.getRepeat(), 1
        );
        sound.setNowPlaying(nowPlaying);
        liveData.setValue(sound);
    }

    public void stop(){
        soundPool.stop(sound.getNowPlaying());
    }


}
