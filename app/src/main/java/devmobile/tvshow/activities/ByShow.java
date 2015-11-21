package devmobile.tvshow.activities;

import android.app.DialogFragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import devmobile.tvshow.adapters.CustomAdapterNextToWatch;
import devmobile.tvshow.adapters.CustomAdapterShow;
import devmobile.tvshow.db.SQLiteHelper;
import devmobile.tvshow.db.adapter.SeasonDataSource;
import devmobile.tvshow.db.adapter.ShowDataSource;
import devmobile.tvshow.db.object.Episode;
import devmobile.tvshow.R;
import devmobile.tvshow.db.object.Season;
import devmobile.tvshow.alert.CreateSeasonDialogAlert;
import devmobile.tvshow.alert.DeleteShowDialogAlert;
import devmobile.tvshow.db.object.Show;

public class ByShow extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_by_show);

        String pass = getIntent().getStringExtra(MainActivity.SHOW_ID);
        final long num = Long.parseLong(pass);

        ShowDataSource sds = new ShowDataSource(this);

        Show show = sds.getShowById(num);

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
        Toast.makeText(ByShow.this, "test", Toast.LENGTH_SHORT).show();

        final ArrayList<Season> listOfSeasons = (ArrayList<Season>) seasons.getAllSeasons(num);



        //ListAdapter adapter = new CustomAdapterShow(this, listOfSeasons);


        ListView list = (ListView) findViewById(R.id.listOfSeasons);
       // list.setAdapter(adapter);
        //setListViewHeightBasedOnChildren(list);
        // Retire le focus sur la liste afin que l'activité démarre en haut de la page
        list.setFocusable(false);

        list.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent appInfo = new Intent(ByShow.this, BySeason.class);
                        startActivity(appInfo);
                    }
                }
        );



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
                int i = (int) num;
                args.putInt("num", i);
                newFragment.setArguments(args);
                newFragment.show(getFragmentManager(), "create");

            }
        });

        SQLiteHelper sqlHelper = SQLiteHelper.getInstance(getApplicationContext());
        sqlHelper.getWritableDatabase().close();

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
