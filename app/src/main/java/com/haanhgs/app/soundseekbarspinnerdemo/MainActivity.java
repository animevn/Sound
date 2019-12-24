package com.haanhgs.app.soundseekbarspinnerdemo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Spinner;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.bnDecrease, R.id.bnIncrease, R.id.bnFx1, R.id.bnFx3, R.id.bnFx2, R.id.bnStop})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bnDecrease:
                break;
            case R.id.bnIncrease:
                break;
            case R.id.bnFx1:
                break;
            case R.id.bnFx3:
                break;
            case R.id.bnFx2:
                break;
            case R.id.bnStop:
                break;
        }
    }
}
