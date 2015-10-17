package devmobile.tvshow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.LENGTH_LONG;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                        Serie serie = listOfSeries.get(position);
                        Toast.makeText(MainActivity.this, serie.getTitle(), LENGTH_LONG).show();
                    }
                }
        );


    }
}
