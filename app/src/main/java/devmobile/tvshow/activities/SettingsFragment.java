package devmobile.tvshow.activities;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import devmobile.tvshow.R;
import devmobile.tvshow.adapters.CustomAdapter;
import devmobile.tvshow.db.SQLiteHelper;
import devmobile.tvshow.db.adapter.ShowDataSource;
import devmobile.tvshow.db.object.Show;


public class SettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
    }
}
