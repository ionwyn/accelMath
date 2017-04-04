package ca.sfu.group15.accelmath;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

public class CompleteActivity extends AppCompatActivity{

    private static final String EXTRA_UNIT_INDEX =
        "ca.sfu.g15.accelmath.unit_index";
    private static final String EXTRA_CHAPTER_INDEX =
            "ca.sfu.g15.accelmath.chapter_index";
    private static final String EXTRA_POINTS_SCORED =
            "ca.sfu.g15.accelmath.points_scored";
    private static final String EXTRA_POINTS_TOTAL =
            "ca.sfu.g15.accelmath.points_total";

    private int mUnitIndex;
    private int mChapterIndex;
    private int mPointsScored;
    private int mPointsTotal;

    public static Intent newIntent(Context packageContext, int unitIndex, int chapterIndex,
                                   int pointsScored, int pointsTotal) {
        Intent intent = new Intent(packageContext, CompleteActivity.class);
        intent.putExtra(EXTRA_UNIT_INDEX, unitIndex);
        intent.putExtra(EXTRA_CHAPTER_INDEX, chapterIndex);
        intent.putExtra(EXTRA_POINTS_SCORED, pointsScored);
        intent.putExtra(EXTRA_POINTS_TOTAL, pointsTotal);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fragment);

        //Get extras
        mUnitIndex = getIntent().getIntExtra(EXTRA_UNIT_INDEX, 0);
        mChapterIndex = getIntent().getIntExtra(EXTRA_CHAPTER_INDEX, 0);
        mPointsScored = getIntent().getIntExtra(EXTRA_POINTS_SCORED, 0);
        mPointsTotal = getIntent().getIntExtra(EXTRA_POINTS_TOTAL, 1);


        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = CompleteFragment.newInstance(mUnitIndex, mChapterIndex, mPointsScored,
                    mPointsTotal);
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }

    }
}
