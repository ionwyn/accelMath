package ca.sfu.g15.accelmath;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

public class CompleteFragment extends Fragment {

    private static final String ARG_UNIT_INDEX = "unit_index";
    private static final String ARG_CHAPTER_INDEX = "chapter_index";
    private static final String ARG_POINTS_SCORED = "points_scored";
    private static final String ARG_POINTS_TOTAL = "points_total";

    private static final float RATING_MAX = 3f;

    private int mUnitIndex;
    private int mChapterIndex;
    private int mPointsScored;
    private int mPointsTotal;

    private float mRating;

    private TextView mScoreTextView;
    private RatingBar mScoreRatingBar;
    private Button mFinishButton;

    public static CompleteFragment newInstance(int unitIndex, int chapterIndex,
                                               int pointsScored, int pointsTotal) {
        Bundle args = new Bundle();
        args.putInt(ARG_UNIT_INDEX, unitIndex);
        args.putInt(ARG_CHAPTER_INDEX, chapterIndex);
        args.putInt(ARG_POINTS_SCORED, pointsScored);
        args.putInt(ARG_POINTS_TOTAL, pointsTotal);

        CompleteFragment fragment = new CompleteFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mUnitIndex = getArguments().getInt(ARG_UNIT_INDEX);
        mChapterIndex = getArguments().getInt(ARG_CHAPTER_INDEX);
        mPointsScored = getArguments().getInt(ARG_POINTS_SCORED);
        mPointsTotal = getArguments().getInt(ARG_POINTS_TOTAL);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_complete, container, false);

        String scoreText = mPointsScored + " out of " + mPointsTotal;
        mScoreTextView = (TextView) view.findViewById(R.id.score_text_view);
        mScoreTextView.setText(scoreText);

        mRating = ((float) mPointsScored / mPointsTotal) * RATING_MAX;
        mScoreRatingBar = (RatingBar) view.findViewById(R.id.score_rating_bar);
        mScoreRatingBar.setRating(mRating);

        mFinishButton = (Button) view.findViewById(R.id.finish_button);
        mFinishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        return view;
    }
}
