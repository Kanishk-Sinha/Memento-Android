package com.kanishk.code.bloop.view.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.Toast;

import com.kanishk.code.bloop.R;
import com.kanishk.code.bloop.databinding.LayoutFragmentAudioBloopBinding;
import com.kanishk.code.bloop.model.notes.AudioNote;

import java.io.IOException;
import java.util.Random;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * Created by kanishk on 21/7/17.
 */

public class AudioBloopFragment extends DialogFragment {

    private LayoutFragmentAudioBloopBinding binding;
    private boolean isRecording;
    private MediaRecorder mediaRecorder;
    private String AudioSavePathInDevice;
    String RandomAudioFileName = "BLOOP";
    private Random random;
    private AudioBloopFragmentListener mListener;
    private String audioBloopTitle, duration;
    private Chronometer chronometer;

    public static AudioBloopFragment newInstance(Context context) {
        return new AudioBloopFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.ThemeOverlay_Material_Light);
        random = new Random();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_fragment_audio_bloop, container, false);
        initViews();
        return binding.getRoot();
    }

    private void initViews() {
        binding.record.setOnClickListener(v -> startRecording());
        binding.wave.stopAnimation();
    }

    private void startRecording() {
        changeViews();
        chronometer = binding.timeCounter;
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
        checkPermission();
        if(checkPermission()) {
            AudioSavePathInDevice = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + CreateRandomAudioFileName(5) + "BloopAudioRecording.3gp";
            MediaRecorderReady();
            try {
                mediaRecorder.prepare();
                mediaRecorder.start();
            } catch (IllegalStateException | IOException e) {
                e.printStackTrace();
            }
        } else {
            requestPermission();
        }
        isRecording = true;
        /*CountDownTimer timer=new CountDownTimer(120000, 1000) {
            public void onTick(long millisUntilFinished) {
                binding.timeCounter.setText("0"+String.format("%d:%d",
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }

            public void onFinish() {
                binding.timeCounter.setText("Time Up");
                //stopRecording();
            }
        };
        timer.start();*/
    }

    private void changeViews() {
        binding.wave.startAnimation();
        binding.wave.setStrokeWidth(6f);
        binding.record.setVisibility(View.INVISIBLE);
        binding.stop.setVisibility(View.VISIBLE);
        setListeners();
    }

    private void setListeners() {
        binding.done.setOnClickListener(v -> {
            AudioNote audioNote = new AudioNote();
            audioNote.setPath(AudioSavePathInDevice);
            audioNote.setDuration(chronometer.getText().toString());
            mListener.onGetAudioBloop(audioNote);
            dismiss();
        });

        binding.stop.setOnClickListener(v -> {
            stopRecording();
            binding.done.setVisibility(View.VISIBLE);
            binding.refresh.setVisibility(View.VISIBLE);
        });

        binding.refresh.setOnClickListener(v -> {
            binding.done.setVisibility(View.GONE);
            binding.refresh.setVisibility(View.GONE);
            binding.stop.setVisibility(View.GONE);
            binding.record.setVisibility(View.VISIBLE);
        });
    }

    public boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getContext(),
                WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getContext(),
                RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED &&
                result1 == PackageManager.PERMISSION_GRANTED;
    }

    public void MediaRecorderReady(){
        mediaRecorder=new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mediaRecorder.setOutputFile(AudioSavePathInDevice);
    }

    public String CreateRandomAudioFileName(int string){
        StringBuilder stringBuilder = new StringBuilder( string );
        int i = 0 ;
        while(i < string ) {
            stringBuilder.append(RandomAudioFileName.charAt(random.nextInt(RandomAudioFileName.length())));
            i++ ;
        }
        return stringBuilder.toString();
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{WRITE_EXTERNAL_STORAGE, RECORD_AUDIO}, 1);
    }

    private void stopRecording() {
        binding.wave.stopAnimation();
        chronometer.stop();
        mediaRecorder.stop();
        isRecording = false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length> 0) {
                    boolean StoragePermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean RecordPermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (StoragePermission && RecordPermission) {
                        Toast.makeText(getContext(), "Permission Granted", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getContext(),"Permission Denied",Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }

    public void setAudioBloopFragmentListener(AudioBloopFragmentListener listener) {
        mListener = listener;
    }

    public interface AudioBloopFragmentListener {
        void onGetAudioBloop(AudioNote audioNote);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }
}
