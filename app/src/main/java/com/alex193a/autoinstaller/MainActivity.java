package com.alex193a.autoinstaller;

import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new PrefsFragment()).commit();
    }

    public static class PrefsFragment extends PreferenceFragment {
        @SuppressWarnings("deprecation")
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            getPreferenceManager().setSharedPreferencesName("pref");

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                getPreferenceManager().setSharedPreferencesMode(MODE_WORLD_READABLE);
            } else {
                getPreferenceManager().setSharedPreferencesMode(MODE_PRIVATE);
            }
            addPreferencesFromResource(R.xml.pref);

        }
    }

}
