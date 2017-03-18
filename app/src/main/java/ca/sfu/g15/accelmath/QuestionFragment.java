package ca.sfu.g15.accelmath;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import io.github.kexanie.library.MathView;

public class QuestionFragment extends Fragment {

    private static final String ARG_UNIT_INDEX = "unit_index";
    private static final String ARG_CHAPTER_INDEX = "chapter_index";
    private static final String ARG_QUESTION_INDICES = "question_indices";
    private static final String ARG_CURRENT_QUESTION_INDEX = "current_question_index";

    private MathView mQuestionMathView;
    private EditText mUserAnswer;
    private Button mSubmitButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question, container, false);

        mQuestionMathView = (MathView) view.findViewById(R.id.question_math_view);



        return view;
    }
}
