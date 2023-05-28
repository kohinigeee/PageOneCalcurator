package com.example.pageoneculator;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

public class MyConfigFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle bundle, String s ) {
        addPreferencesFromResource(R.xml.pref);
    }
}
