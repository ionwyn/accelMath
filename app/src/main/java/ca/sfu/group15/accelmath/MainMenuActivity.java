package ca.sfu.group15.accelmath;

import android.support.v4.app.Fragment;

public class MainMenuActivity extends SingleFragmentActivity{


    @Override
    protected Fragment createFragment() {
        return new MainMenuFragment();
    }
}
