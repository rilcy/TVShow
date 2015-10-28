package devmobile.tvshow;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class BySeason extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_by_season);

        final ArrayList<Episode> listOfEpisodes = new ArrayList<Episode>();

        Episode episode1 = new Episode("1", "Avis de recherche");
        listOfEpisodes.add(episode1);
        Episode episode2 = new Episode("2", "Le Syndrome de Cendrillon");
        listOfEpisodes.add(episode2);
        Episode episode3 = new Episode("3", "Un mal nécessaire");
        listOfEpisodes.add(episode3);
        Episode episode4 = new Episode("4", "Tu ne voleras point");
        listOfEpisodes.add(episode4);

        ListAdapter adapter = new CustomAdapterSeason(this, listOfEpisodes);

        ListView list = (ListView) findViewById(R.id.listOfEpisodes);
        list.setAdapter(adapter);

        list.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Intent appInfo = new Intent(BySeason.this, ByEpisode.class);
                        startActivity(appInfo);
                    }
                }
        );


    }
}
