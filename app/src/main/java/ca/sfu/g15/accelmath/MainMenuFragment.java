package ca.sfu.g15.accelmath;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

public class MainMenuFragment extends Fragment{

    private ImageButton mStartButton;
    private ImageButton mProgressButton;
    private ImageButton mSettingsButton;
    private ImageButton mQuotesButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main_menu, container, false);

        mStartButton = (ImageButton) v.findViewById(R.id.start_button);
        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        Intent mainIntent = new Intent(getActivity(),
                                UnitListActivity.class);
                        mainIntent.putExtra("id", "1");
                        startActivity(mainIntent);
//                        getActivity().finish();

                        getActivity().overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
                    }
                }, 100);
            }
        });

        mProgressButton = (ImageButton) v.findViewById(R.id.progress_button);

        mSettingsButton = (ImageButton) v.findViewById(R.id.settings_button);
        mSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        Intent mainIntent = new Intent(getActivity(),
                                SettingsActivity.class);
                        mainIntent.putExtra("id", "1");
                        startActivity(mainIntent);
//                        getActivity().finish();

                        getActivity().overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
                    }
                }, 100);
            }
        });

        mQuotesButton = (ImageButton) v.findViewById(R.id.quotes_button);
        mQuotesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        Intent mainIntent = new Intent(getActivity(),
                                QuoteActivity.class);
                        mainIntent.putExtra("id", "1");
                        startActivity(mainIntent);
//                        getActivity().finish();
                        getActivity().overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
                    }
                }, 100);
            }
        });

        return v;
    }
}
