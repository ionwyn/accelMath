package ca.sfu.group15.accelmath;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ca.sfu.group15.accelmath.database.Database;
import ca.sfu.group15.accelmath.database.DatabaseHandler;
import io.github.kexanie.library.MathView;

public class QuestionFragment extends Fragment implements View.OnClickListener {

    private static final String ARG_UNIT_INDEX = "unit_index";
    private static final String ARG_CHAPTER_INDEX = "chapter_index";
    private static final String ARG_QUESTION_INDICES = "question_indices";
    private static final String ARG_CURRENT_QUESTION_INDEX = "current_question_index";
    private static final String ARG_POINTS_SCORED = "points_scored";

    private int mUnitIndex;
    private int mChapterIndex;
    private int mQuestionIndices[];
    private int mCurrentQuestionIndex;
    private int mPointsScored;

    private Database.Unit.Chapter.Question mQuestion;

    private TextView mStatusTextView;
    private MathView mQuestionMathView;

    private LinearLayout mButtonContainer;

    /**
     * Creates a new instance of QuestionFragment by encapsulating arguments.
     *
     * @param unitIndex
     * @param chapterIndex
     * @param questionIndices
     * @param currentQuestionIndex
     * @param pointsScored
     * @return
     */
    public static QuestionFragment newInstance(int unitIndex, int chapterIndex,
                                               int[] questionIndices, int currentQuestionIndex,
                                               int pointsScored) {
        Bundle args = new Bundle();
        args.putInt(ARG_UNIT_INDEX, unitIndex);
        args.putInt(ARG_CHAPTER_INDEX, chapterIndex);
        args.putIntArray(ARG_QUESTION_INDICES, questionIndices);
        args.putInt(ARG_CURRENT_QUESTION_INDEX, currentQuestionIndex);
        args.putInt(ARG_POINTS_SCORED, pointsScored);

        QuestionFragment fragment = new QuestionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mUnitIndex = getArguments().getInt(ARG_UNIT_INDEX);
        mChapterIndex = getArguments().getInt(ARG_CHAPTER_INDEX);
        mQuestionIndices = getArguments().getIntArray(ARG_QUESTION_INDICES);
        mCurrentQuestionIndex = getArguments().getInt(ARG_CURRENT_QUESTION_INDEX);
        mPointsScored = getArguments().getInt(ARG_POINTS_SCORED);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question, container, false);

        //Get the current question based on mCurrentQuestionIndex
        mQuestion = DatabaseHandler.get(getActivity()).getUnits().get(mUnitIndex).chapters
                .get(mChapterIndex).questions.get(mQuestionIndices[mCurrentQuestionIndex]);

        int currentQuestion = mCurrentQuestionIndex + 1;
        int numberOfQuestions = mQuestionIndices.length;

        String statusText = "Question " + currentQuestion + " of " + numberOfQuestions;
        mStatusTextView = (TextView) view.findViewById(R.id.question_status);
        mStatusTextView.setText(statusText);

        //Set the MathView text to the current question
        mQuestionMathView = (MathView) view.findViewById(R.id.question_math_view);
        mQuestionMathView.config(
                "MathJax.Hub.Config({\n" +
                        "  CommonHTML: { linebreaks: { automatic: true } },\n" +
                        "  \"HTML-CSS\": { linebreaks: { automatic: true } },\n" +
                        "         SVG: { linebreaks: { automatic: true } }\n" +
                        "});");
        mQuestionMathView.setText(mQuestion.question);

        mButtonContainer = (LinearLayout) view.findViewById(R.id.button_container);

        //Create a button for each multiple choice option
        List<String> options = mQuestion.options;
        for (int i = 0; i < options.size(); i++) {
            Button button = new Button(getActivity());
            button.setText(options.get(i));
            button.setLayoutParams(new LinearLayout.LayoutParams
                    (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            button.setOnClickListener(QuestionFragment.this);
            mButtonContainer.addView(button);
        }


        return view;
    }

    @Override
    public void onClick(View v) {
        Button button = (Button) v;
        String response = button.getText().toString();
        MediaPlayer mp = MediaPlayer.create(getActivity().getApplicationContext(), R.raw.ping);

        //Check if answer is correct
        int message;
        boolean correct = false;
        if (response.equals(mQuestion.answer)) {
            mp.start();
            message = R.string.correct_toast;
            correct = true;
        } else {
            message = R.string.incorrect_toast;
        }

        //Show toast to display if answer is correct
        Toast toast = Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT);
        toast.show();

        //Load the next question and add points to score
        int index = mCurrentQuestionIndex + 1;
        int score = correct ? mPointsScored + 1 : mPointsScored;

        final Intent intent;
        if (index < mQuestionIndices.length) {
            intent = QuestionActivity.newIntent(getActivity(), mUnitIndex, mChapterIndex,
                    mQuestionIndices, index, score);
        } else {
            intent = CompleteActivity.newIntent(getActivity(), mUnitIndex, mChapterIndex,
                    score, mQuestionIndices.length);
        }

        new Handler().postDelayed(new Runnable() {
            public void run() {

                startActivity(intent);
                getActivity().finish();

                getActivity().overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
            }
        }, 100);
    }
}
