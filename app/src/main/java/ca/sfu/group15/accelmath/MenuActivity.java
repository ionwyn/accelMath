package ca.sfu.group15.accelmath;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.os.CountDownTimer;
import android.widget.ImageButton;
import android.os.Handler;

public class MenuActivity extends AppCompatActivity {

    private ImageButton mPlayButton;
    private ImageButton mQuoteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.splash);

        MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.jingle);
        mp.start();

        new CountDownTimer(1000,1000){
            @Override
            public void onTick(long millisUntilFinished){}

            @Override
            public void onFinish(){


                MenuActivity.this.setContentView(R.layout.activity_menu);

                mPlayButton = (ImageButton) findViewById(R.id.play_button);
                mPlayButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(MenuActivity.this, MainMenuActivity.class);
                        startActivity(intent);
                    }
                });

            }
        }.start();



        //setContentView(R.layout.activity_menu);


    }
}
