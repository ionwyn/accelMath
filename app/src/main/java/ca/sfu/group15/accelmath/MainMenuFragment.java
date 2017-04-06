package ca.sfu.group15.accelmath;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.os.Handler;

public class MainMenuFragment extends Fragment{

    private Button mStartButton;
    private Button mProgressButton;
    private Button mSettingsButton;
    private Button mQuotesButton;
    private Button mAboutButton;
    private Button mCreateButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main_menu, container, false);

        mStartButton = (Button) v.findViewById(R.id.start_button);
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

        mProgressButton = (Button) v.findViewById(R.id.progress_button);
        mProgressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        Intent mainIntent = new Intent(getActivity(),
                                ProgressActivity.class);
                        mainIntent.putExtra("id", "1");
                        startActivity(mainIntent);
//                        getActivity().finish();

                        getActivity().overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
                    }
                }, 100);
            }
        });

        mSettingsButton = (Button) v.findViewById(R.id.settings_button);
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

        mQuotesButton = (Button) v.findViewById(R.id.quotes_button);
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

        mAboutButton = (Button) v.findViewById(R.id.about_button);
        mAboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        Intent mainIntent = new Intent(getActivity(),
                                AboutActivity.class);
                        mainIntent.putExtra("id", "1");
                        startActivity(mainIntent);
//                        getActivity().finish();

                        getActivity().overridePendingTransition(R.anim.slide_in,R.anim.slide_out);
                    }
                }, 100);
            }
        });

        mCreateButton = (Button) v.findViewById(R.id.create_button);
        mCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://jagrajan.com/accelmath/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        return v;
    }
}
