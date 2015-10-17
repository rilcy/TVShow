package devmobile.tvshow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import static android.widget.Toast.LENGTH_LONG;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] serieName = {"NCIS : Los Angeles", "Game of Thrones", "Impastor", "Les Revenants", "Dora, the explorer",
                                    "The Americans", "Lost : les dispaprus"};
        ListAdapter adapter = new CustomAdapter(this, serieName);
        final ListView listOfSeries = (ListView) findViewById(R.id.listOfSeries);
        listOfSeries.setAdapter(adapter);
 
        listOfSeries.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String serie = String.valueOf(parent.getItemAtPosition(position));
                        Toast.makeText(MainActivity.this, serie, LENGTH_LONG).show();
                    }
                }
        );


    }
}
