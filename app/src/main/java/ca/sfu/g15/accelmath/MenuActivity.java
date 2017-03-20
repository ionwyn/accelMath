package ca.sfu.g15.accelmath;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.os.CountDownTimer;
import android.widget.ImageButton;

public class MenuActivity extends AppCompatActivity {

    private ImageButton mPlayButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.splash);
        //display the logo during 5 seconds,
        new CountDownTimer(5000,1000){
            @Override
            public void onTick(long millisUntilFinished){}

            @Override
            public void onFinish(){
                //set the new Content of your activity
                MenuActivity.this.setContentView(R.layout.activity_menu);
                mPlayButton = (ImageButton) findViewById(R.id.play_button);
                mPlayButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MenuActivity.this, UnitListActivity.class);
                        startActivity(intent);
                    }
                });
            }
        }.start();



        //setContentView(R.layout.activity_menu);


    }
}
