package ca.sfu.g15.accelmath;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class SettingsFragment extends Fragment{

    private TextView mCurrentDatabaseTextView;
    private Button mAddButton;
    private RecyclerView mDatabaseRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        mCurrentDatabaseTextView = (TextView) view.findViewById(R.id.current_database);
        mCurrentDatabaseTextView.setText("YOU");

        mAddButton = (Button) view.findViewById(R.id.add_button);
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO onclick
            }
        });

        mDatabaseRecyclerView = (RecyclerView) view.findViewById(R.id.database_recycler_view);
        mDatabaseRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    private class DatabaseHolder extends RecyclerView.ViewHolder {

        private TextView mDatabaseTextView;
        private Button mSelectButton;
        private Button mDeleteButton;

        public DatabaseHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_database, parent, false));

            mDatabaseTextView = (TextView) itemView.findViewById(R.id.database_name);
            mSelectButton = (Button) itemView.findViewById(R.id.select_button);
            mDeleteButton = (Button) itemView.findViewById(R.id.delete_button);
        }
    }
}
