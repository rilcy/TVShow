package devmobile.tvshow;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

       final ArrayList<Serie> listOfSeries = new ArrayList<Serie>();


        Serie ncis = new Serie("NCIS : Los Angeles", R.drawable.ncis_la);
        listOfSeries.add(ncis);
        Serie got = new Serie("Game of Thrones", R.drawable.got);
        listOfSeries.add(got);
        Serie impastor = new Serie("Impastor", R.drawable.impastor);
        listOfSeries.add(impastor);
        Serie poi = new Serie("Person of Interest", R.drawable.person_of_interest);
        listOfSeries.add(poi);
        Serie dexter = new Serie("Dexter", R.drawable.dexter);
        listOfSeries.add(dexter);
        Serie americans = new Serie("The Americans", R.drawable.the_americans);
        listOfSeries.add(americans);
        Serie daredevil = new Serie("Daredevil", R.drawable.daredevil);
        listOfSeries.add(daredevil);


        ListAdapter adapter = new CustomAdapter(this, listOfSeries);

        ListView list = (ListView) findViewById(R.id.listOfSeries);
        list.setAdapter(adapter);

        list.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Intent appInfo = new Intent(MainActivity.this, ByShow.class);
                        startActivity(appInfo);
                        /*
                        Serie serie = listOfSeries.get(position);
                        Toast.makeText(MainActivity.this, serie.getTitle(), LENGTH_LONG).show();
                        */
                    }
                }
        );


    }
}
