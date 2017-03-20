package ca.sfu.g15.accelmath;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.os.*;

import java.util.List;

import ca.sfu.g15.accelmath.database.Database;
import ca.sfu.g15.accelmath.database.DatabaseHandler;

public class UnitListFragment extends Fragment{

    private RecyclerView mUnitRecyclerView;
    private UnitAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_unit_list, container, false);

        mUnitRecyclerView = (RecyclerView) view.findViewById(R.id.unit_recycler_view);
        mUnitRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    private void updateUI() {
        DatabaseHandler databaseHandler = DatabaseHandler.get(getActivity());
        List<Database.Unit> units = databaseHandler.getUnits();

        if (mAdapter == null) {
            mAdapter = new UnitAdapter(units);
            mUnitRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    private class UnitHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mUnitNameTextView;
        private TextView mChapterCountTextView;

        private Database.Unit mUnit;
        private int mUnitIndex;

        public UnitHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_unit, parent, false));
            itemView.setOnClickListener(this);
            mUnitNameTextView = (TextView) itemView.findViewById(R.id.unit_name);
            mChapterCountTextView = (TextView) itemView.findViewById(R.id.unit_chapter_count);
        }

        public void bind (Database.Unit unit, int unitIndex) {
            mUnit = unit;
            mUnitIndex = unitIndex;
            mUnitNameTextView.setText(mUnit.unitName);
            mChapterCountTextView.setText(mUnit.chapters.size() + " chapters");
        }

        @Override
        public void onClick(View v) {
            Intent intent = ChapterActivity.newIntent(getActivity(), mUnitIndex);
            startActivity(intent);
        }
    }

    private class UnitAdapter extends RecyclerView.Adapter<UnitHolder> {

        private List<Database.Unit> mUnits;

        public UnitAdapter(List<Database.Unit> units) {
            mUnits = units;
        }

        @Override
        public UnitHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new UnitHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(UnitHolder holder, int position) {
            Database.Unit unit = mUnits.get(position);
            holder.bind(unit, position);
        }

        @Override
        public int getItemCount() {
            return mUnits.size();
        }
    }
}
