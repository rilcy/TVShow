package devmobile.tvshow.activities;


import android.os.Bundle;
import android.preference.PreferenceFragment;

import java.util.ArrayList;

import devmobile.tvshow.R;

// Fragment s'ouvrant depuis l'activité settings afin de permettre le changement de langue
public class SettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Charge les préférences depuis le fichier xml/preferences.xml
        addPreferencesFromResource(R.xml.preferences);
    }
}
