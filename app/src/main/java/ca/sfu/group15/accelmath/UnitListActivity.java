package ca.sfu.group15.accelmath;

import android.support.v4.app.Fragment;

public class UnitListActivity extends SingleFragmentActivity{
    @Override
    protected Fragment createFragment() {
        return new UnitListFragment();
    }
}
