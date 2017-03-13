package ca.sfu.g15.accelmath;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import ca.sfu.g15.accelmath.database.Database;
import ca.sfu.g15.accelmath.database.DatabaseHandler;

public class ChapterFragment extends Fragment{

    private static final String ARG_UNIT_INDEX = "unit_index";

    private RecyclerView mChapterRecyclerView;
    private int mUnitIndex;
    private ChapterAdapter mAdapter;

    public static ChapterFragment newInstance(int unitIndex) {
        Bundle args = new Bundle();
        args.putInt(ARG_UNIT_INDEX, unitIndex);

        ChapterFragment fragment = new ChapterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUnitIndex = getArguments().getInt(ARG_UNIT_INDEX);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chapter_list, container, false);

        mChapterRecyclerView = (RecyclerView) view.findViewById(R.id.chapter_recycler_view);
        mChapterRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    private void updateUI() {
        DatabaseHandler handler = DatabaseHandler.get(getActivity());
        List<Database.Unit.Chapter> chapters = handler.getUnits().get(mUnitIndex).chapters;

        if (mAdapter == null) {
            mAdapter = new ChapterAdapter(chapters);
            mChapterRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    private class ChapterHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mChapterNameTextView;
        private RatingBar mChapterRatingBar;

        private Database.Unit.Chapter mChapter;
        private int mChapterIndex;

        public ChapterHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_chapter, parent, false));
            itemView.setOnClickListener(this);

            mChapterNameTextView = (TextView) itemView.findViewById(R.id.chapter_name);
            mChapterRatingBar = (RatingBar) itemView.findViewById(R.id.chapter_stars);
        }

        public void bind (Database.Unit.Chapter chapter, int chapterIndex) {
            mChapter = chapter;
            mChapterIndex = chapterIndex;
            mChapterNameTextView.setText(chapter.topic);
            mChapterRatingBar.setNumStars(3);
        }

        @Override
        public void onClick(View v) {
            Intent intent = LessonActivity.newIntent(getActivity(), mUnitIndex, mChapterIndex);
            startActivity(intent);
        }
    }

    private class ChapterAdapter extends RecyclerView.Adapter<ChapterHolder> {

        private List<Database.Unit.Chapter> mChapters;

        public ChapterAdapter(List<Database.Unit.Chapter> chapters) {
            mChapters = chapters;
        }

        @Override
        public ChapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new ChapterHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(ChapterHolder holder, int position) {
            Database.Unit.Chapter chapter = mChapters.get(position);
            holder.bind(chapter, position);
        }

        @Override
        public int getItemCount() {
            return mChapters.size();
        }
    }
}
