package com.example.dereev_pr25;

import android.annotation.TargetApi;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private SoundPool mSoundPool;
    private AssetManager mAssetManager;
    private int mSuslikSound, mBelkaSound, mBoberSound, mMonkeySound, mGrizliSound, mGepardSound;
    private int mStreamID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            // Для устройств до Android 5
            createOldSoundPool();
        } else {
            // Для новых устройств
            createNewSoundPool();
        }

        mAssetManager = getAssets();

        // получим идентификаторы
        mSuslikSound = loadSound("suslik.ogg");
        mBelkaSound = loadSound("belka.ogg");
        mBoberSound = loadSound("bober.ogg");
        mMonkeySound = loadSound("monkey.ogg");
        mGrizliSound = loadSound("grizli.ogg");
        mGepardSound = loadSound("gepard.ogg");

        ImageButton boberImageButton = findViewById(R.id.image_bober);
        boberImageButton.setOnClickListener(onClickListener);

        ImageButton belkaImageButton = findViewById(R.id.image_belka);
        belkaImageButton.setOnClickListener(onClickListener);

        ImageButton suslikImageButton = findViewById(R.id.image_suslik);
        suslikImageButton.setOnClickListener(onClickListener);

        ImageButton grizliImageButton = findViewById(R.id.image_grizli);
        grizliImageButton.setOnClickListener(onClickListener);

        ImageButton gepardImageButton = findViewById(R.id.image_gepard);
        gepardImageButton.setOnClickListener(onClickListener);

        ImageButton monkeyImageButton = findViewById(R.id.image_monkey);
        monkeyImageButton.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.image_bober) {
                playSound(mBoberSound);
            } else if (v.getId() == R.id.image_belka) {
                playSound(mBelkaSound);
            } else if (v.getId() == R.id.image_suslik) {
                playSound(mSuslikSound);
            } else if (v.getId() == R.id.image_grizli) {
                playSound(mGrizliSound);
            } else if (v.getId() == R.id.image_gepard) {
                playSound(mGepardSound);
            } else if (v.getId() == R.id.image_monkey) {
                playSound(mMonkeySound);
            }
        }
    };

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void createNewSoundPool() {
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        mSoundPool = new SoundPool.Builder()
                .setAudioAttributes(attributes)
                .build();
    }

    @SuppressWarnings("deprecation")
    private void createOldSoundPool() {
        mSoundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
    }

    private int playSound(int sound) {
        if (sound > 0) {
            mStreamID = mSoundPool.play(sound, 1, 1, 1, 0, 1);
        }
        return mStreamID;
    }

    private int loadSound(String fileName) {
        AssetFileDescriptor afd;
        try {
            afd = mAssetManager.openFd(fileName);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Не могу загрузить файл " + fileName,
                    Toast.LENGTH_SHORT).show();
            return -1;
        }
        return mSoundPool.load(afd, 1);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            // Для устройств до Android 5
            createOldSoundPool();
        } else {
            // Для новых устройств
            createNewSoundPool();
        }

        mAssetManager = getAssets();

        // получим идентификаторы
        mSuslikSound = loadSound("suslik.ogg");
        mBelkaSound = loadSound("belka.ogg");
        mBoberSound = loadSound("bober.ogg");
        mMonkeySound = loadSound("monkey.ogg");
        mGrizliSound = loadSound("grizli.ogg");
        mGepardSound = loadSound("gepard.ogg");

    }

    @Override
    protected void onPause() {
        super.onPause();
        mSoundPool.release();
        mSoundPool = null;
    }
}