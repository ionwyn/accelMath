package ca.sfu.group15.accelmath;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import ca.sfu.group15.accelmath.database.Database;
import ca.sfu.group15.accelmath.database.DatabaseHandler;
import ca.sfu.group15.accelmath.database.DatabaseMetaData;
import ca.sfu.group15.accelmath.database.Scores;
import ca.sfu.group15.accelmath.database.ScoresHandler;

public class ProgressFragment extends Fragment {

    private RecyclerView mProgressRecyclerView;

    private ProgressAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_progress, container, false);

        mProgressRecyclerView = (RecyclerView) view.findViewById(R.id.progress_recycler_view);
        mProgressRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    private void updateUI() {

        List<DatabaseMetaData> databases = DatabaseHandler.get(getActivity())
                .getDatabases(getActivity());

        if (mAdapter == null) {
            mAdapter = new ProgressAdapter(databases);
            mProgressRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    private class ProgressHolder extends RecyclerView.ViewHolder {

        private ProgressBar mTopProgressBar;
        private ProgressBar mBottomProgressBar;
        private Button mResetButton;
        private TextView mNameTextView;
        private TextView mProgressTextView;

        private String mDatabaseFileName;
        private String mScoresFileName;

        public ProgressHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_progress, parent, false));

            mTopProgressBar = (ProgressBar) itemView.findViewById(R.id.top_progress_bar);
            mBottomProgressBar = (ProgressBar) itemView.findViewById(R.id.bottom_progress_bar);
            mResetButton = (Button) itemView.findViewById(R.id.reset_button);
            mResetButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ScoresHandler.get(getActivity()).deleteFile(getActivity(), mScoresFileName)) {
                        getActivity().finish();
                        startActivity(getActivity().getIntent());
                    }
                }
            });

            mNameTextView = (TextView) itemView.findViewById(R.id.name_text_view);
            mProgressTextView = (TextView) itemView.findViewById(R.id.progress_text_view);
        }

        public void bind(String databaseFileName, String scoresFileName) {
            mDatabaseFileName = databaseFileName;
            mScoresFileName = scoresFileName;
            update();
        }

        private void update() {
            Database database = DatabaseHandler.get(getActivity())
                    .getDatabaseContents(getActivity(), mDatabaseFileName);
            Scores scores = ScoresHandler.get(getActivity())
                    .getScoresContents(getActivity(), mScoresFileName);

            int userScore = 0;
            int maxScore = 0;
            String name = "undefined";

            if (database != null) {
                name = database.edition;
                for (int i = 0; i < database.units.size(); i++) {
                    for (int j = 0; j < database.units.get(i).chapters.size(); j++) {
                        if (scores != null) {
                            for (Scores.Score score : scores.scores) {
                                if (score.unitIndex == i && score.chapterIndex == j) {
                                    if (score.rating > 0) {
                                        userScore += score.rating;
                                    }
                                    break;
                                }
                            }
                            maxScore += 3;
                        }
                    }
                }
            }

            if (maxScore == 0) {
                maxScore = 1;
            }

            int progress = (int) (((double) userScore / maxScore) * 100);
            Log.d("ACCELMATH", progress+"");

            mTopProgressBar.setProgress(progress);
            mBottomProgressBar.setProgress(progress);

            mNameTextView.setText(name);
            mProgressTextView.setText(userScore + " out of " + maxScore + " stars gained");
        }

    }

    private class ProgressAdapter extends RecyclerView.Adapter<ProgressHolder> {

        private List<DatabaseMetaData> mDatabases;

        public ProgressAdapter(List<DatabaseMetaData> databases) {
            mDatabases = databases;
        }

        @Override
        public ProgressHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new ProgressHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(ProgressHolder holder, int position) {
            DatabaseMetaData data = mDatabases.get(position);
            holder.bind(data.getFileName(), data.getScoresFileName());
        }

        @Override
        public int getItemCount() {
            return mDatabases.size();
        }
    }
}
