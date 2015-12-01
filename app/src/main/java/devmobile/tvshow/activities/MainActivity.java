package devmobile.tvshow.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

import devmobile.tvshow.db.SQLiteHelper;
import devmobile.tvshow.db.adapter.ShowDataSource;
import devmobile.tvshow.db.object.Show;
import devmobile.tvshow.adapters.CustomAdapterMain;
import devmobile.tvshow.R;


public class MainActivity extends AppCompatActivity {

    public  final static String SHOW_ID = "devemobile.tvshow.activities.MainActivity.SHOW_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Préférence de langue
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        changeLanguage(sharedPrefs.getString("pref_lang", "en"));

        // Obtention de la liste des séries et affichage
        ShowDataSource sds = new ShowDataSource(this);

        ArrayList<Show> listOfSeries = (ArrayList<Show>) sds.getAllShows();

        ListView list = (ListView) findViewById(R.id.listOfSeries);

        final ListAdapter adapter = new CustomAdapterMain(this, listOfSeries);

        list.setAdapter(adapter);

        // en cas de click sur une série on l'affiche dans une autre activié
        list.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent appInfo = new Intent(MainActivity.this, ByShow.class);

                        Show show = (Show) adapter.getItem(position);

                        appInfo.putExtra("SHOW_ID", String.valueOf(show.getShowId()));

                        startActivity(appInfo);

                    }
                }
        );

        SQLiteHelper sqlHelper = SQLiteHelper.getInstance(this);
        sqlHelper.getWritableDatabase().close();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent = getIntent();

        switch(item.getItemId()){

            case R.id.action_byActor:

                intent = new Intent(MainActivity.this, ByActor.class);
                MainActivity.this.startActivity(intent);
                break;

            case R.id.action_addShow:

                intent = new Intent(MainActivity.this, ByShow_Creation.class);
                MainActivity.this.startActivity(intent);
                break;

            case R.id.action_settings:

                intent = new Intent(MainActivity.this, Settings.class);
                MainActivity.this.startActivity(intent);
                break;
        }
        return true;
    }

    // Localisation
    public void changeLanguage(String lang){
        Locale myLocale = new Locale(lang);
        Locale.setDefault(myLocale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = myLocale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

    }
}
