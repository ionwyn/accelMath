package ca.sfu.g15.accelmath;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import ca.sfu.g15.accelmath.database.Database;
import ca.sfu.g15.accelmath.database.DatabaseHandler;

public class ChapterActivity extends AppCompatActivity {

    private static final String EXTRA_UNIT_INDEX =
            "ca.sfu.g15.accelmath.unit_index";

    private int mUnitIndex;

    public static Intent newIntent(Context packageContext, int unitIndex) {
        Intent intent = new Intent(packageContext, ChapterActivity.class);
        intent.putExtra(EXTRA_UNIT_INDEX, unitIndex);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fragment);

        //Get the unit index extra
        mUnitIndex = getIntent().getIntExtra(EXTRA_UNIT_INDEX, 0);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = ChapterFragment.newInstance(mUnitIndex);
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }

    }
}
