package com.haanhgs.app.sound.view;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import com.haanhgs.app.sound.R;
import com.haanhgs.app.sound.viewmodel.ViewModel;
import java.util.Locale;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
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
    @BindView(R.id.tvVolume)
    TextView tvVolume;

    private ViewModel viewModel;

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(ViewModel.class);

        viewModel.getSound().observe(this, sound ->
            tvVolume.setText(String.format(Locale.getDefault(), "%.0f", sound.getVolume()*100)));
    }

    private void setupSpinner() {
        spRepeat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int repeat = Integer.valueOf(spRepeat.getSelectedItem().toString()) - 1;
                viewModel.setRepeat(repeat);
            }
            @Override public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void setupSeekBar() {
        if (viewModel.getSound().getValue() != null) {
            sbrVolume.setProgress((int) (viewModel.getSound().getValue().getVolume() * 100));
        }
        sbrVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                viewModel.setVolume(progress / 100f);
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
        initViewModel();
        setupSpinner();
        setupSeekBar();
    }

    private void increaseVolume() {
        int progress = sbrVolume.getProgress();
        if (progress + 20 > sbrVolume.getMax()) {
            sbrVolume.setProgress(sbrVolume.getMax());
        } else {
            sbrVolume.setProgress(progress + 20);
        }
    }

    private void decreaseVolume() {
        int progress = sbrVolume.getProgress();
        if (progress - 20 <= 0) {
            sbrVolume.setProgress(0);
        } else {
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
                if (viewModel.getSound().getValue() != null) {
                    viewModel.play(viewModel.getSound().getValue().getId1());
                }
                break;
            case R.id.bnFx3:
                if (viewModel.getSound().getValue() != null) {
                    viewModel.play(viewModel.getSound().getValue().getId2());
                }
                break;
            case R.id.bnFx2:
                if (viewModel.getSound().getValue() != null) {
                    viewModel.play(viewModel.getSound().getValue().getId3());
                }
                break;
            case R.id.bnStop:
                viewModel.stop();
                break;
        }
    }
}
