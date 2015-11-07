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
import java.util.ArrayList;

import devmobile.tvshow.db.object.Show;
import devmobile.tvshow.adapters.CustomAdapter;
import devmobile.tvshow.R;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final ArrayList<Show> listOfSeries = new ArrayList<Show>();


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
                        Show serie = listOfSeries.get(position);
                        Toast.makeText(MainActivity.this, serie.getTitle(), LENGTH_LONG).show();
                        */
                    }
                }
        );


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

            //TODO : Ajouter ici l'action pour la page d'ajout de séries lorsque la page sera créée

            case R.id.action_byActor:

                intent = new Intent(MainActivity.this, ByActor.class);
                MainActivity.this.startActivity(intent);
                break;

            case R.id.action_addShow:

                intent = new Intent(MainActivity.this, ByShow_Creation.class);
                MainActivity.this.startActivity(intent);
                break;
        }
        return true;
    }
}
