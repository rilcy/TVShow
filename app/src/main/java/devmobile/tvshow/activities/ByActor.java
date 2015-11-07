package devmobile.tvshow.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import devmobile.tvshow.*;
import devmobile.tvshow.adapters.CustomAdapter;
import devmobile.tvshow.adapters.CustomAdapterActor;

public class ByActor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_by_actor);

        final ArrayList<Actor> listOfActors = new ArrayList<Actor>();

        Actor actor1 = new Actor("Jean", "Reno");
        listOfActors.add(actor1);
        Actor actor2 = new Actor("Michael", "Douglas");
        listOfActors.add(actor2);
        Actor actor3 = new Actor("Leonardo", "DiCaprio");
        listOfActors.add(actor3);
        Actor actor4 = new Actor("Brad", "Pitt");
        listOfActors.add(actor4);
        Actor actor5 = new Actor("Kevin", "Spacey");
        listOfActors.add(actor5);
        Actor actor6 = new Actor("Jim", "Parsons");
        listOfActors.add(actor6);
        Actor actor7 = new Actor("Kaley", "Kuoco");
        listOfActors.add(actor7);

        ListAdapter adapter = new CustomAdapterActor(this, listOfActors);

        ListView list = (ListView) findViewById(R.id.listOfActors);
        list.setAdapter(adapter);
    }

    private void setupActionBar() {

        getActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:

                Intent intent = new Intent(ByActor.this, MainActivity.class);
                ByActor.this.startActivity(intent);
                return true;

            case R.id.action_addShow:

                intent = new Intent(ByActor.this, ByShow_Creation.class);
                ByActor.this.startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public static class MainActivity extends AppCompatActivity {

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
                            Serie serie = listOfSeries.get(position);
                            Toast.makeText(MainActivity.this, serie.getTitle(), LENGTH_LONG).show();
                            */
                        }
                    }
            );


        }
    }
}
