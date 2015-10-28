package devmobile.tvshow;

import android.content.Intent;
import android.database.DataSetObserver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ByShow extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_by_show);

        final ArrayList<Season> listOfSeasons = new ArrayList<Season>();


        Season season1 = new Season("Saison 1");
        listOfSeasons.add(season1);
        Season season2 = new Season("Saison 2");
        listOfSeasons.add(season2);
        Season season3 = new Season("Saison 3");
        listOfSeasons.add(season3);
        Season season4 = new Season("Saison 4");
        listOfSeasons.add(season4);


        ListAdapter adapter = new CustomAdapterShow(this, listOfSeasons);

        ListView list = (ListView) findViewById(R.id.listOfSeasons);
        list.setAdapter(adapter);

        list.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Intent appInfo = new Intent(ByShow.this, BySeason.class);
                        startActivity(appInfo);
                    }
                }
        );

    }
}
