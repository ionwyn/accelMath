package ca.sfu.group15.accelmath;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatDelegate;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import java.util.Calendar;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        simulateDayNight(/* DAY */ 0);
        String description =
                "AccelMath is an education app designed to help you further your Mathematics skills.  AccelMath is based on an academic project for CMPT 276 at Simon Fraser University. \n\n Features include:\n-Advanced Math topics\n-Community-led questions\n-Quotes to inspire you\n-Progress tracking\n-and more...\n\nDeveloped by:\nEric Lin\nIonwyn Sean\nJagrajan Bhullar\nJason Nguyen";
        View aboutPage = new AboutPage(this)
                .setDescription(description)
                .isRTL(false)
                .setImage(R.mipmap.accelmath_logo)
                .addItem(new Element().setTitle("Version 1.2"))
                .addGroup("Connect with us")
                .addEmail("isean@sfu.ca")
                .addWebsite("http://www.ionwyn.com/")
                .addFacebook("ionwyn")
                .addTwitter("ionwyn")
                .addYoutube("VVqKMMVILZ88pz_6iQ3TiQ")
                .addPlayStore("ca.sfu.group15.accelmath")
                .addInstagram("ionwyn")
                .addGitHub("ionwyn")
                .create();

        setContentView(aboutPage);

    }


    void simulateDayNight(int currentSetting) {
        final int DAY = 0;
        final int NIGHT = 1;
        final int FOLLOW_SYSTEM = 3;

        int currentNightMode = getResources().getConfiguration().uiMode
                & Configuration.UI_MODE_NIGHT_MASK;
        if (currentSetting == DAY && currentNightMode != Configuration.UI_MODE_NIGHT_NO) {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_NO);
        } else if (currentSetting == NIGHT && currentNightMode != Configuration.UI_MODE_NIGHT_YES) {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_YES);
        } else if (currentSetting == FOLLOW_SYSTEM) {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        }
    }
}
