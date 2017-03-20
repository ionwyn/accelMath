package ca.sfu.g15.accelmath;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

public class LessonActivity extends AppCompatActivity{

    private static final String EXTRA_UNIT_INDEX =
            "ca.sfu.g15.accelmath.unit_index";

    private static final String EXTRA_CHAPTER_INDEX =
            "ca.sfu.g15.accelmath.chapter_index";

    private int mUnitIndex;
    private int mChapterIndex;

    public static Intent newIntent(Context packageContext, int unitIndex, int chapterIndex) {
        Intent intent = new Intent(packageContext, LessonActivity.class);
        intent.putExtra(EXTRA_UNIT_INDEX, unitI ndex);
        intent.putExtra(EXTRA_CHAPTER_INDEX, chapterIndex);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        //Get extras from intent
        mUnitIndex = getIntent().getIntExtra(EXTRA_UNIT_INDEX, 0);
        mChapterIndex = getIntent().getIntExtra(EXTRA_CHAPTER_INDEX, 0);

        //Get the fragment manager
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        //Create and show the fragment
        if (fragment == null) {
            fragment = LessonFragment.newInstance(mUnitIndex, mChapterIndex);
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }
}
