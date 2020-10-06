package com.haanhgs.app.sound.viewmodel;

import android.app.Application;
import com.haanhgs.app.sound.model.Repo;
import com.haanhgs.app.sound.model.Sound;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class ViewModel extends AndroidViewModel {

    private Repo repo;

    public ViewModel(@NonNull Application application) {
        super(application);
        repo = new Repo(application.getApplicationContext());
    }

    public LiveData<Sound> getLiveData(){
        return repo.getLiveData();
    }

    public void playId1(){
        repo.playId1();
    }

    public void playId2(){
        repo.playId2();
    }

    public void playId3(){
        repo.playId3();
    }

    public void stop(){
        repo.stop();
    }

    public void setVolume(float volume){
        repo.setVolume(volume);
    }

    public void setRepeat(int repeat){
        repo.setRepeat(repeat);
    }

}
