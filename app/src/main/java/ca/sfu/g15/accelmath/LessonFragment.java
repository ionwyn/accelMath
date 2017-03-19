package ca.sfu.g15.accelmath;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ca.sfu.g15.accelmath.database.Database;
import ca.sfu.g15.accelmath.database.DatabaseHandler;
import io.github.kexanie.library.MathView;

public class LessonFragment extends Fragment {

    private static final String ARG_UNIT_INDEX = "unit_index";
    private static final String ARG_CHAPTER_INDEX = "chapter_index";

    private int mUnitIndex;
    private int mChapterIndex;

    private Database.Unit.Chapter mChapter;

    private TextView mLessonNameTextView;
    private MathView mLessonMathView;
    private Button mStartQuizButton;

    public static LessonFragment newInstance(int unitIndex, int chapterIndex) {
        Bundle args = new Bundle();
        args.putInt(ARG_UNIT_INDEX, unitIndex);
        args.putInt(ARG_CHAPTER_INDEX, chapterIndex);

        LessonFragment fragment = new LessonFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mUnitIndex = getArguments().getInt(ARG_UNIT_INDEX);
        mChapterIndex = getArguments().getInt(ARG_CHAPTER_INDEX);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lesson, container, false);

        List<Database.Unit> units = DatabaseHandler.get(getContext()).getUnits();
        mChapter = units.get(mUnitIndex).chapters.get(mChapterIndex);
        String lessonTopic = mChapter.topic;
        String lesson = mChapter.lesson;

        mLessonNameTextView = (TextView) view.findViewById(R.id.lesson_name_text_view);
        mLessonNameTextView.setText(lessonTopic);

        mLessonMathView = (MathView) view.findViewById(R.id.lesson_math_view);
        mLessonMathView.setText(lesson);

        mStartQuizButton = (Button) view.findViewById(R.id.start_quiz_button);
        mStartQuizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
                Intent intent = QuestionActivity.newIntent(getActivity(), mUnitIndex, mChapterIndex,
                        generateQuestionsIndices(), 0, 0);
                startActivity(intent);
            }
        });

        return view;
    }

    private int[] generateQuestionsIndices() {

        //Get the number of questions for current lesson
        int size = mChapter.questions.size();

        //Create a list of all possibl indices and shuffle it
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            indices.add(i);
        }
        Collections.shuffle(indices);

        //Copy list values to an array
        int [] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = indices.get(i);
        }

        return array;
    }
}
