package devmobile.tvshow.activities;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import devmobile.tvshow.adapters.CustomAdapterShow;
import devmobile.tvshow.db.SQLiteHelper;
import devmobile.tvshow.db.adapter.EpisodeDataSource;
import devmobile.tvshow.db.adapter.SeasonDataSource;
import devmobile.tvshow.db.adapter.ShowDataSource;
import devmobile.tvshow.R;
import devmobile.tvshow.db.object.Season;
import devmobile.tvshow.alert.CreateSeasonDialogAlert;
import devmobile.tvshow.alert.DeleteShowDialogAlert;
import devmobile.tvshow.db.object.Show;

public class ByShow extends AppCompatActivity {

    public  final static String SEASON_ID = "devemobile.tvshow.activities.ByShow.SEASON_ID";

    private ArrayList<Season> listOfSeasons;
    private long numberSeasons;
    private long num;
    private Season season;
    private SeasonDataSource seasonds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_by_show);

        Intent intent = getIntent();
        String dataTransfered = intent.getStringExtra("SHOW_ID");
        num = Long.parseLong(dataTransfered);
        ShowDataSource sds = new ShowDataSource(this);
        final Show show = sds.getShowById(num);
        seasonds = new SeasonDataSource(this);


        /*

        //PARTIE SUPERIEURE "A VOIR PROCHAINEMENT"

        final ArrayList<Episode> ep = new ArrayList<Episode>();


        Episode ncis = new Episode(2, "Un mal nécessaire", "Saison 2 Episode 3", R.drawable.ncis_la);
        ep.add(ncis);

        ListAdapter adapterNextoWatch = new CustomAdapterNextToWatch(this, ep);

        ListView listNextToWatch = (ListView) findViewById(R.id.listeNextToWatch);
        listNextToWatch.setAdapter(adapterNextoWatch);
        */

        // PARTIE INFERIEURE "LISTE DES SAISONS"
        SeasonDataSource seasons = new SeasonDataSource(this);
        listOfSeasons = (ArrayList<Season>) seasons.getAllSeasons((int)num);
        numberSeasons = listOfSeasons.size();

        final ListAdapter adapter = new CustomAdapterShow(this, listOfSeasons);


        ListView list = (ListView) findViewById(R.id.listOfSeasons);
        list.setAdapter(adapter);

        SQLiteHelper sqlHelper = SQLiteHelper.getInstance(this);
        sqlHelper.getWritableDatabase().close();
        setListViewHeightBasedOnChildren(list);
        // Retire le focus sur la liste afin que l'activité démarre en haut de la page
        list.setFocusable(false);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent appInfo = new Intent(ByShow.this, BySeason.class);

                Season goToSeason = (Season) adapter.getItem(position);
                appInfo.putExtra(SEASON_ID, String.valueOf(goToSeason.getSeasonId()));
                startActivity(appInfo);
            }
        });

        LinearLayout llayout_edit = (LinearLayout) findViewById (R.id.linearlayout_editShow);
        llayout_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent appInfo = new Intent(ByShow.this, ByShow_Edition.class);
                startActivity(appInfo);
                finish();
            }
        });

        LinearLayout llayout_delete = (LinearLayout) findViewById (R.id.linearlayout_deleteShow);
        llayout_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DeleteShowDialogAlert();
                newFragment.show(getFragmentManager(), "delete");

            }
        });


        LinearLayout llayout_create = (LinearLayout) findViewById (R.id.linearlayout_createSeason);
        llayout_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogFragment newFragment = new CreateSeasonDialogAlert();
                Bundle args = new Bundle();
                args.putInt("showId", show.getShowId());
                args.putInt("numSeasons", listOfSeasons.size());
                newFragment.setArguments(args);
                newFragment.show(getFragmentManager(), "create");
            }
        });


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

                Intent intent = new Intent(ByShow.this, MainActivity.class);
                ByShow.this.startActivity(intent);
                break;

            case R.id.action_byActor:

                intent = new Intent(ByShow.this, ByActor.class);
                ByShow.this.startActivity(intent);
                break;

            case R.id.action_addShow:

                intent = new Intent(ByShow.this, ByShow_Creation.class);
                ByShow.this.startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    // Source : http://stackoverflow.com/questions/18367522/android-list-view-inside-a-scroll-view
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

}
