package ca.sfu.group15.accelmath;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import ca.sfu.group15.accelmath.database.DatabaseHandler;
import ca.sfu.group15.accelmath.database.DatabaseMetaData;

public class SettingsFragment extends Fragment {

    private TextView mCurrentDatabaseTextView;
    private Button mAddButton;
    private EditText mAddDatabaseEditText;
    private RecyclerView mDatabaseRecyclerView;

    private DatabaseAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        String currentDatabase = DatabaseHandler.get(getActivity()).getDatabase().edition;

        mCurrentDatabaseTextView = (TextView) view.findViewById(R.id.current_database);
        mCurrentDatabaseTextView.setText(currentDatabase);

        mAddDatabaseEditText = (EditText) view.findViewById(R.id.add_database_edit_text);

        mAddButton = (Button) view.findViewById(R.id.add_button);
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = mAddDatabaseEditText.getText().toString();
                DatabaseHandler handler = DatabaseHandler.get(getActivity());
                if (handler.addDatabaseFromURL(getActivity(), url)) {
                    getActivity().finish();
                    startActivity(getActivity().getIntent());
                }
            }
        });

        mDatabaseRecyclerView = (RecyclerView) view.findViewById(R.id.database_recycler_view);
        mDatabaseRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    private void updateUI() {
        DatabaseHandler handler = DatabaseHandler.get(getActivity());
        List<DatabaseMetaData> databases = handler.getDatabases(getActivity());

        if (mAdapter == null) {
            mAdapter = new DatabaseAdapter(databases);
            mDatabaseRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    private class DatabaseHolder extends RecyclerView.ViewHolder {

        private TextView mDatabaseTextView;
        private Button mSelectButton;
        private Button mDeleteButton;

        private String mDatabaseName;
        private String mFileName;

        public DatabaseHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_database, parent, false));

            mDatabaseTextView = (TextView) itemView.findViewById(R.id.database_name);
            mSelectButton = (Button) itemView.findViewById(R.id.select_button);
            mSelectButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseHandler handler = DatabaseHandler.get(getActivity());
                    if (handler.setDatabase(getActivity(), mFileName)) {
                        getActivity().finish();
                        startActivity(getActivity().getIntent());
                    }
                }
            });

            mDeleteButton = (Button) itemView.findViewById(R.id.delete_button);
            mDeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseHandler handler = DatabaseHandler.get(getActivity());
                    if (handler.deleteDatabase(getActivity(), mFileName)) {
                        getActivity().finish();
                        startActivity(getActivity().getIntent());
                    }
                }
            });
        }

        public void bind(DatabaseMetaData data) {
            mDatabaseName = data.getDatabaseName();
            mFileName = data.getFileName();

            mDatabaseTextView.setText(mDatabaseName);
        }
    }

    private class DatabaseAdapter extends RecyclerView.Adapter<DatabaseHolder> {

        private List<DatabaseMetaData> mDatabases;

        public DatabaseAdapter(List<DatabaseMetaData> databases) {
            mDatabases = databases;
        }

        @Override
        public DatabaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());

            return new DatabaseHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(DatabaseHolder holder, int position) {
            DatabaseMetaData data = mDatabases.get(position);
            holder.bind(data);
        }

        @Override
        public int getItemCount() {
            return mDatabases.size();
        }
    }
}
