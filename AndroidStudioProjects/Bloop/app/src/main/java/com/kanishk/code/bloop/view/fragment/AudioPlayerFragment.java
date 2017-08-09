package com.kanishk.code.bloop.view.fragment;

import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;

import com.kanishk.code.bloop.R;
import com.kanishk.code.bloop.databinding.LayoutFragmentAudioPlayerBinding;

import java.io.IOException;

/**
 * Created by kanishk on 22/7/17.
 */

public class AudioPlayerFragment extends DialogFragment {

    private LayoutFragmentAudioPlayerBinding binding;
    private static String audioPath;
    private MediaPlayer mediaPlayer;
    private boolean isMediaPlaying;
    private Chronometer chronometer;

    public static AudioPlayerFragment newInstance(String path) {
        audioPath = path;
        return new AudioPlayerFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_fragment_audio_player, container, false);
        initViews();
        mediaPlayer = new MediaPlayer();
        playTrack(audioPath);
        return binding.getRoot();
    }

    private void initViews() {
        binding.wave.startAnimation();
        chronometer = binding.timer;
        chronometer.setOnChronometerTickListener(cArg -> {
            long time = SystemClock.elapsedRealtime() - cArg.getBase();
            int h   = (int)(time /3600000);
            int m = (int)(time - h*3600000)/60000;
            int s= (int)(time - h*3600000- m*60000)/1000 ;
            String hh = h < 10 ? "0"+h: h+"";
            String mm = m < 10 ? "0"+m: m+"";
            String ss = s < 10 ? "0"+s: s+"";
            cArg.setText(hh+":"+mm+":"+ss);
        });
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();

        binding.pause.setOnClickListener(v -> {
            chronometer.stop();
            mediaPlayer.pause();
            binding.pause.setVisibility(View.INVISIBLE);
            binding.play.setVisibility(View.VISIBLE);
        });

        binding.play.setOnClickListener(v -> {
            binding.pause.setVisibility(View.VISIBLE);
            binding.play.setVisibility(View.INVISIBLE);
            mediaPlayer.start();
            chronometer.start();
        });
    }

    private void playTrack(String audioPath) {
        try {
            mediaPlayer.setDataSource(audioPath);
            mediaPlayer.prepare();
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(mp -> {
                chronometer.stop();
                dismiss();
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        mediaPlayer.stop();
        mediaPlayer.release();
    }
}
