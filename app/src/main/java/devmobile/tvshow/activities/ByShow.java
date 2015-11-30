package devmobile.tvshow.activities;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Locale;

import devmobile.tvshow.adapters.CustomAdapterNextToWatch;
import devmobile.tvshow.adapters.CustomAdapterShow;
import devmobile.tvshow.db.adapter.EpisodeDataSource;
import devmobile.tvshow.db.adapter.SeasonDataSource;
import devmobile.tvshow.db.adapter.ShowDataSource;
import devmobile.tvshow.R;
import devmobile.tvshow.db.object.Episode;
import devmobile.tvshow.db.object.Season;
import devmobile.tvshow.alert.CreateSeasonDialogAlert;
import devmobile.tvshow.alert.DeleteShowDialogAlert;
import devmobile.tvshow.db.object.Show;

public class ByShow extends AppCompatActivity {


    private ArrayList<Season> listOfSeasons;
    private long numberSeasons;
    private long show_Id;
    private Season season;
    private ShowDataSource showds;
    private SeasonDataSource seasonds;
    private EpisodeDataSource episodeds;

    private Show show = new Show();

    private TextView beginYearByShow;
    private TextView endYearByShow;
    private CheckBox cbNumberEpisodeToWatch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_by_show);

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        changeLanguage(sharedPrefs.getString("pref_lang", "en"));

        Intent intent = getIntent();
        String dataTransfered = intent.getStringExtra("SHOW_ID");
        show_Id = Long.parseLong(dataTransfered);

        showds = new ShowDataSource(this);
        show = showds.getShowById(show_Id);
        seasonds = new SeasonDataSource(this);
        episodeds = new EpisodeDataSource(this);
        showds = new ShowDataSource(this);

        SeasonDataSource seasons = new SeasonDataSource(this);
        listOfSeasons = (ArrayList<Season>) seasons.getAllSeasons((int)show_Id);


        //PARTIE SUPERIEURE "A VOIR PROCHAINEMENT"
        Episode ep = findNextEpisodeToWatch(listOfSeasons);
        final ArrayList<Episode> eplist = new ArrayList<Episode>();
        eplist.add(ep);
        ListAdapter adapterNextoWatch = new CustomAdapterNextToWatch(this, eplist, show.getShowImage(), show.getShowId());

        ListView listNextToWatch = (ListView) findViewById(R.id.listeNextToWatch);
        listNextToWatch.setAdapter(adapterNextoWatch);

        //PARTIE MEDIANE
        beginYearByShow = (TextView) findViewById(R.id.beginYearByShow);
        endYearByShow = (TextView) findViewById(R.id.endYearByShow);
        cbNumberEpisodeToWatch = (CheckBox) findViewById(R.id.cbNumberEpisodeToWatch);
        beginYearByShow.setText(show.getShowStart());
        endYearByShow.setText(show.getShowEnd());

        int numberleft = countNumberOfEpisodeLeft();
        cbNumberEpisodeToWatch.setText(" " + numberleft);

        checkIfShowIsCompleted();

        if(show.isShowCompleted() == 0)
            cbNumberEpisodeToWatch.setChecked(false);
        else
            cbNumberEpisodeToWatch.setChecked(true);

        cbNumberEpisodeToWatch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    checkAllSeasonsAndEpisode();
                else
                    uncheckLastEpisode();

                refreshMyActivity();
            }
        });


        // PARTIE INFERIEURE "LISTE DES SAISONS"
        numberSeasons = listOfSeasons.size();

        final ListAdapter adapter = new CustomAdapterShow(this, listOfSeasons);


        ListView list = (ListView) findViewById(R.id.listOfSeasons);
        list.setAdapter(adapter);


        setListViewHeightBasedOnChildren(list);
        // Retire le focus sur la liste afin que l'activité démarre en haut de la page
        list.setFocusable(false);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent appInfo = new Intent(ByShow.this, BySeason.class);

                Season goToSeason = (Season) adapter.getItem(position);
                appInfo.putExtra("SEASON_ID", String.valueOf(goToSeason.getSeasonId()));
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

    private void checkIfShowIsCompleted() {
        int cpt = 0;
        boolean isWatched = false;
        while(cpt < listOfSeasons.size() && listOfSeasons.get(cpt).isSeasonCompleted() == 1){
            ++cpt;
            isWatched = true;
        }

        if(listOfSeasons.size() != cpt){
            show.setShowCompleted(0);
            showds.updateShow(show);
        }
        else{
            show.setShowCompleted(1);
            showds.updateShow(show);
        }
    }

    private void checkAllSeasonsAndEpisode() {
        for (int i = 0; i < listOfSeasons.size(); i++) {
            ArrayList<Episode> listOfEpisodes = (ArrayList<Episode>) episodeds.getAllEpisodes(listOfSeasons.get(i).getSeasonId());
            for (int j = 0; j < listOfEpisodes.size(); j++) {
                if (listOfEpisodes.get(j).isEpisodeCompleted() == 0) {
                    listOfEpisodes.get(j).setEpisodeCompleted(1);
                    episodeds.updateEpisodeIfWatched(listOfEpisodes.get(j));
                }
            }
            if (listOfSeasons.get(i).isSeasonCompleted() == 0) {
                listOfSeasons.get(i).setSeasonCompleted(1);
                seasonds.updateSeason(listOfSeasons.get(i));
            }
        }
        show.setShowCompleted(1);
        showds.updateShow(show);
    }

    private void uncheckLastEpisode() {

        // CHANGE STATUT OF THE SHOW AS NOT WATCHED
        show.setShowCompleted(0);
        showds.updateShow(show);

        // CHANGE STATUT OF LAST SEASON AS NOT WATCHED
        int i = listOfSeasons.size()-1;
        listOfSeasons.get(i).setSeasonCompleted(0);
        seasonds.updateSeason(listOfSeasons.get(i));

        // CHANGE LAST EPISODE OF LAST SEASON AS NOT WATCHED
        ArrayList<Episode> listOfEpisodes = (ArrayList<Episode>) episodeds.getAllEpisodes(listOfSeasons.get(i).getSeasonId());
        int j = listOfEpisodes.size()-1;
        listOfEpisodes.get(j).setEpisodeCompleted(0);
        episodeds.updateEpisodeIfWatched(listOfEpisodes.get(j));
    }

    private void refreshMyActivity() {
        finish();
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }

    private int countNumberOfEpisodeLeft() {

        int nbSeasons = listOfSeasons.size();
        int seasonId;
        EpisodeDataSource episodeds = new EpisodeDataSource(this);
        ArrayList<Episode> listOfEpisode = new ArrayList<Episode>();
        int nbEpisode = 0;
        int cpt = 0;

        for(int i = 0; i<nbSeasons; i++){
            seasonId = listOfSeasons.get(i).getSeasonId();
            listOfEpisode = (ArrayList<Episode>) episodeds.getAllEpisodes(seasonId);
            nbEpisode = listOfEpisode.size();

            for(int j = 0; j<nbEpisode; j++){
                if(listOfEpisode.get(j).isEpisodeCompleted() == 0)
                    ++cpt;
            }
        }

        return cpt;
    }

    private Episode findNextEpisodeToWatch(ArrayList<Season> listOfSeasons) {
        EpisodeDataSource episodeds = new EpisodeDataSource(this);
        Episode ep = null;

        int cptSeasons = 0;
        while(cptSeasons < listOfSeasons.size()){
            if(listOfSeasons.get(cptSeasons).isSeasonCompleted() == 0) {
                Season season = seasonds.getSeasonById(listOfSeasons.get(cptSeasons).getSeasonId());
                ArrayList<Episode> listOfEpisode = (ArrayList<Episode>) episodeds.getAllEpisodes(season.getSeasonId());
                int cptEpisode = 0;
                while(cptEpisode < listOfEpisode.size()){
                    if(listOfEpisode.get(cptEpisode).isEpisodeCompleted() == 0){
                        return ep = episodeds.getEpisodeById(listOfEpisode.get(cptEpisode).getEpisodeID());
                    }
                    ++cptEpisode;
                }
            }
            ++cptSeasons;
        }
        return ep;
    }


    private void setupActionBar() {
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        menu.findItem(R.id.action_settings).setVisible(false);
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

    public void changeLanguage(String lang) {
        Locale myLocale = new Locale(lang);
        Locale.setDefault(myLocale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = myLocale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        getSupportActionBar().setTitle(R.string.byShow_pageTitle);
    }
}
