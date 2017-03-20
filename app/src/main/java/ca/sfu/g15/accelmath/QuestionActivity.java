package ca.sfu.g15.accelmath;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.*;
import android.view.Window;

public class QuestionActivity extends AppCompatActivity{

    private static final String EXTRA_UNIT_INDEX =
            "ca.sfu.g15.accelmath.unit_index";

    private static final String EXTRA_CHAPTER_INDEX =
            "ca.sfu.g15.accelmath.chapter_index";

    private static final String EXTRA_QUESTION_INDICES =
            "ca.sfu.g15.accelmath.question_indices";
    private static final String EXTRA_CURRENT_QUESTION_INDEX =
            "ca.sfu.g15.accelmath.current_question_index";
    private static final String EXTRA_POINTS_SCORED =
            "ca.sfu.g15.accelmath.points_scored";

    private int mUnitIndex;
    private int mChapterIndex;
    private int mQuestionIndices[];
    private int mCurrentQuestionIndex;
    private int mPointsScored;

    public static Intent newIntent(Context packageContext, int unitIndex, int chapterIndex,
                                   int questionIndices[], int currentQuestionIndex,
                                   int pointsScored) {
        Intent intent = new Intent(packageContext, QuestionActivity.class);
        intent.putExtra(EXTRA_UNIT_INDEX, unitIndex);
        intent.putExtra(EXTRA_CHAPTER_INDEX, chapterIndex);
        intent.putExtra(EXTRA_QUESTION_INDICES, questionIndices);
        intent.putExtra(EXTRA_CURRENT_QUESTION_INDEX, currentQuestionIndex);
        intent.putExtra(EXTRA_POINTS_SCORED, pointsScored);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_fragment);

        //Get extras
        mUnitIndex = getIntent().getIntExtra(EXTRA_UNIT_INDEX, 0);
        mChapterIndex = getIntent().getIntExtra(EXTRA_CHAPTER_INDEX, 0);
        mQuestionIndices = getIntent().getIntArrayExtra(EXTRA_QUESTION_INDICES);
        mCurrentQuestionIndex = getIntent().getIntExtra(EXTRA_CURRENT_QUESTION_INDEX, 0);
        mPointsScored = getIntent().getIntExtra(EXTRA_POINTS_SCORED, 0);


        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = QuestionFragment.newInstance(mUnitIndex, mChapterIndex, mQuestionIndices,
                    mCurrentQuestionIndex, mPointsScored);
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }

    }
}
