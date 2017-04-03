package ca.sfu.g15.accelmath;

import android.support.v4.app.Fragment;

public class ProgressActivity extends SingleFragmentActivity{

    @Override
    protected Fragment createFragment() {
        return new ProgressFragment();
    }

}
