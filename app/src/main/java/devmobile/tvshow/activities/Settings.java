package devmobile.tvshow.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Elsio on 28.11.2015.
 */
public class Settings extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction()
                .replace(android.R.id.content,new SettingsFragment())
                .commit();
    }
}
