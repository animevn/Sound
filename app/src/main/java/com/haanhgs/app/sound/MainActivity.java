package com.haanhgs.app.sound;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import java.io.IOException;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.spRepeat)
    Spinner spRepeat;
    @BindView(R.id.sbrVolume)
    SeekBar sbrVolume;
    @BindView(R.id.bnDecrease)
    ImageButton bnDecrease;
    @BindView(R.id.bnIncrease)
    ImageButton bnIncrease;
    @BindView(R.id.bnFx1)
    Button bnFx1;
    @BindView(R.id.bnFx3)
    Button bnFx3;
    @BindView(R.id.bnFx2)
    Button bnFx2;
    @BindView(R.id.bnStop)
    Button bnStop;

    private static final String ETAG = "E.MainActivity";
    private int id1;
    private int id2;
    private int id3;
    private int nowPlaying = 1;
    private int repeat = 0;
    private float volume = 0.2f;
    private SoundPool soundPool;

    private void setupSoundPool(){
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                .build();
        soundPool = new SoundPool.Builder().setAudioAttributes(attributes).setMaxStreams(5).build();
    }

    private void loadSoundFiles(){
        AssetManager manager = getAssets();
        AssetFileDescriptor descriptor;
        try{
            descriptor = manager.openFd("fx1.ogg");
            id1 = soundPool.load(descriptor, 1);

            descriptor = manager.openFd("fx2.ogg");
            id2 = soundPool.load(descriptor, 1);

            descriptor = manager.openFd("fx3.ogg");
            id3 = soundPool.load(descriptor, 1);
        }catch (IOException e){
            Log.e(ETAG, "load sound fail: " + e.toString());
        }
    }

    private void setupSpinner(){
        spRepeat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                repeat = Integer.valueOf(spRepeat.getSelectedItem().toString()) - 1;
            }

            @Override public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void setupSeekBar(){
        sbrVolume.setProgress((int)(volume * 100));
        sbrVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                volume = progress/100f;
                soundPool.setVolume(nowPlaying, volume, volume);
            }

            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setupSoundPool();
        loadSoundFiles();
        setupSpinner();
        setupSeekBar();
    }

    private void increaseVolume(){
        int progress = sbrVolume.getProgress();
        if (progress + 20 > sbrVolume.getMax()){
            sbrVolume.setProgress(sbrVolume.getMax());
        }else {
            sbrVolume.setProgress(progress + 20);
        }
    }

    private void decreaseVolume(){
        int progress = sbrVolume.getProgress();
        if (progress - 20 <= 0){
            sbrVolume.setProgress(0);
        }else {
            sbrVolume.setProgress(progress - 20);
        }
    }

    @OnClick({R.id.bnDecrease, R.id.bnIncrease, R.id.bnFx1, R.id.bnFx3, R.id.bnFx2, R.id.bnStop})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bnDecrease:
                decreaseVolume();
                break;
            case R.id.bnIncrease:
                increaseVolume();
                break;
            case R.id.bnFx1:
                soundPool.stop(nowPlaying);
                nowPlaying = soundPool.play(id1, volume, volume, 1, repeat, 1);
                break;
            case R.id.bnFx3:
                soundPool.stop(nowPlaying);
                nowPlaying = soundPool.play(id2, volume, volume, 1, repeat, 1);
                break;
            case R.id.bnFx2:
                soundPool.stop(nowPlaying);
                nowPlaying = soundPool.play(id3, volume, volume, 1, repeat, 1);
                break;
            case R.id.bnStop:
                soundPool.stop(nowPlaying);
                break;
        }
    }
}
