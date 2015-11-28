package devmobile.tvshow.activities;

import android.content.Intent;
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
import devmobile.tvshow.adapters.CustomAdapter;
import devmobile.tvshow.R;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ShowDataSource sds = new ShowDataSource(this);

        ArrayList<Show> listOfSeries = (ArrayList<Show>) sds.getAllShows();

        ListView list = (ListView) findViewById(R.id.listOfSeries);

        ListAdapter adapter = new CustomAdapter(this, listOfSeries);
/*
        Show show = new Show();
        show.setShowTitle("Daredevil");
        show.setShowStart("2015");
        show.setShowEnd("2015");
        show.setShowCompleted(0);

        int resID = getResources().getIdentifier("@drawable/daredevil", "drawable", getPackageName());

        show.setShowImage(resID);

        sds.createShow(show);
*/
        list.setAdapter(adapter);

        /*
        Show ncis = new Show("NCIS : Los Angeles", R.drawable.ncis_la);
        listOfSeries.add(ncis);
        Show got = new Show("Game of Thrones", R.drawable.got);
        listOfSeries.add(got);
        Show impastor = new Show("Impastor", R.drawable.impastor);
        listOfSeries.add(impastor);
        Show poi = new Show("Person of Interest", R.drawable.person_of_interest);
        listOfSeries.add(poi);
        Show dexter = new Show("Dexter", R.drawable.dexter);
        listOfSeries.add(dexter);
        Show americans = new Show("The Americans", R.drawable.the_americans);
        listOfSeries.add(americans);
        Show daredevil = new Show("Daredevil", R.drawable.daredevil);
        listOfSeries.add(daredevil);
        */

        list.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Intent appInfo = new Intent(MainActivity.this, ByShow.class);
                        startActivity(appInfo);
                        /*
                        Show serie = listOfSeries.get(position);
                        Toast.makeText(MainActivity.this, serie.getTitle(), LENGTH_LONG).show();
                        */
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

    //TODO : définir cette méthode
    /*
    public void changeLanguage(String lang){
        Locale myLocale = new Locale(lang);
        Locale.setDefault(myLocale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = myLocale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        TextView welcome = (TextView) findViewById(R.id.main_txt_welcome);
        welcome.setText(R.string.main_welcome);
    }
    */
}
