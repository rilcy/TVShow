package devmobile.tvshow;

import android.app.DialogFragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import devmobile.tvshow.alert.EditShowDialogAlert;

public class ByShow extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_by_show);

        //PARTIE SUPERIEURE "A VOIR PROCHAINEMENT"

        final ArrayList<Episode> ep = new ArrayList<Episode>();


        Episode ncis = new Episode("2", "Un mal nécessaire", "Saison 2 Episode 3", R.drawable.ncis_la);
        ep.add(ncis);

        ListAdapter adapterNextoWatch = new CustomAdapterNextToWatch(this, ep);

        ListView listNextToWatch = (ListView) findViewById(R.id.listeNextToWatch);
        listNextToWatch.setAdapter(adapterNextoWatch);


        // PARTIE INFERIEURE "LISTE DES SAISONS"
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

        LinearLayout llayout_edit = (LinearLayout) findViewById (R.id.linearlayout_editShow);;
        llayout_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new EditShowDialogAlert();
                newFragment.show(getFragmentManager(), "Edit");
            }
        });

        };

    }
