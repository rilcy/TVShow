package devmobile.tvshow.activities;

import android.app.DialogFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Locale;

import devmobile.tvshow.adapters.CustomAdapterSeason;
import devmobile.tvshow.db.adapter.EpisodeDataSource;
import devmobile.tvshow.db.adapter.SeasonDataSource;
import devmobile.tvshow.db.adapter.ShowDataSource;
import devmobile.tvshow.db.object.Episode;
import devmobile.tvshow.R;
import devmobile.tvshow.alert.CreateEpisodeDialogAlert;
import devmobile.tvshow.alert.DeleteSeasonDialogAlert;
import devmobile.tvshow.db.object.Season;
import devmobile.tvshow.db.object.Show;

public class BySeason extends AppCompatActivity {

    //public  final static String EPISODE_ID = "devemobile.tvshow.activities.BySeason.EPISODE_ID";

    // TOP OF THE ACTIVITY
    private ImageView imgBySeason;
    private TextView showTitleBySeason;
    private TextView showInfoBySeason;
    private CheckBox cbSeasonBySeason;
    private long Season_ID;
    private ShowDataSource showds;
    private SeasonDataSource seasonds;
    private EpisodeDataSource episodeds;
    private Season season;
    private ArrayList<Episode> listOfEpisodes;
    private ArrayList<Season> listOfSeasons ;
    private ListAdapter adapter;
    private Show show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_by_season);

        // Préférence de langage
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        changeLanguage(sharedPrefs.getString("pref_lang", "en"));

        // Obtenition des données transmis via l'Intent
        String dataTransfered = getIntent().getStringExtra("SEASON_ID");
        Season_ID = Long.parseLong(dataTransfered);

        // Obtention des données relatives à la saison
        seasonds = new SeasonDataSource(this);
        season = seasonds.getSeasonById((int)Season_ID);

        // Obtention des données relatives au show
        showds = new ShowDataSource(this);
        show = showds.getShowById(season.getShowId());

        // Obtention des données relatives aux épisodes de la saison
        listOfEpisodes = new ArrayList<Episode>();
        episodeds = new EpisodeDataSource(this);
        listOfEpisodes = (ArrayList<Episode>) episodeds.getAllEpisodes(season.getSeasonId());


        imgBySeason = (ImageView) findViewById(R.id.imgBySeason);
        showTitleBySeason = (TextView) findViewById(R.id.showTitleBySeason);
        showInfoBySeason = (TextView) findViewById(R.id.showInfoBySeason);
        cbSeasonBySeason = (CheckBox) findViewById(R.id.cbSeasonBySeason);

        // HAUT DE L'ACTIVITE

        // On affiche les données
        showInfoBySeason.setText(getString(R.string.Season) + season.getSeasonNumber());

       // Vérifie si tous les épisodes de la saison ont été vus...
        boolean watched = true;
        // ... si la saison compte au moins un épisode...
        if(listOfEpisodes.size() > 0) {
            //... méthode vérifiant si les épisodes sont tous vu ou non
            watched = checkIfAllEpisodesAreWatched(listOfEpisodes, watched);

            if (watched) {
                // si c'est tout est vu on met à jour le statut de la saison
                cbSeasonBySeason.setChecked(true);
                if(season.isSeasonCompleted() != 1) {
                    season.setSeasonCompleted(1);
                    seasonds.updateSeason(season);
                }
            } else {
                // si rien n'est vu on met à jour le statut de la saison
                cbSeasonBySeason.setChecked(false);
                if(season.isSeasonCompleted() != 0) {
                    season.setSeasonCompleted(0);
                    seasonds.updateSeason(season);
                }
            }
        }

        // Création d'une liste des saisons
        listOfSeasons = (ArrayList<Season>) seasonds.getAllSeasons(show.getShowId());

        // si la liste des épisode n'est pas égale à 0...
        if(listOfEpisodes.size() != 0) {
            final ArrayList<Episode> finalListOfEpisodes1 = listOfEpisodes;
            //.. et que l'on clique sur la checkbox TV de la saison on met à jour la saison et tous
            // ses épisodes enfants
            cbSeasonBySeason.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    // Si la checkbox est checkée...
                    if (cbSeasonBySeason.isChecked()) {
                        //... on met à jour la saison
                        season.setSeasonCompleted(1);
                        seasonds.updateSeason(season);
                        for (Episode e : finalListOfEpisodes1) {
                            // et les épisodes s'ils ne sont pas déjà checkés
                            if(e.isEpisodeCompleted() != 1) {
                                e.setEpisodeCompleted(1);
                                episodeds.updateEpisodeIfWatched(e);
                            }
                        }

                        // on vérifie aussi s'il faut checker la série comme vue ou non vue
                        boolean isWatched = false;
                        int cpt = 0;

                        if(listOfSeasons.size() > 0) {
                            // on tourne dans toutes les saisons et on vérifie que le statut soit vu
                            while (listOfSeasons.get(cpt).isSeasonCompleted() == 1 && listOfSeasons.size() < cpt) {
                                isWatched = true;
                                ++cpt;
                            }
                        }

                        // ... si on sort à la dernière, on vérifie bien que la dernière saison soit vu
                        // si ce n'est pas le cas on  met le boolean à false
                        if(listOfSeasons.get(cpt).isSeasonCompleted() == 0)
                            isWatched = false;

                        if(isWatched){
                            // si le boolean est true, on met à jour le statut de la série
                            show.setShowCompleted(1);
                            showds.updateShow(show);
                        }
                        // et on raffraichit l'activité
                        refreshMyActivity();
                    }
                    // Si on uncheck la saison, on met le dernier épisode comme non-vu.
                    else {
                        season.setSeasonCompleted(0);
                        seasonds.updateSeason(season);
                        int i = finalListOfEpisodes1.size() - 1;
                        if (i != -1) {
                            finalListOfEpisodes1.get(i).setEpisodeCompleted(0);
                            episodeds.updateEpisodeIfWatched(finalListOfEpisodes1.get(i));
                            refreshMyActivity();
                        }
                    }
                }
            });
        }
        // s'il n'y a pas d'épisode on ne peut pas checker la checkbox
        else
            cbSeasonBySeason.setClickable(false);

        // on affiche les infos relative à la série
        showTitleBySeason.setText(show.getShowTitle());

        File imgFile = new  File(show.getShowImage());

        if(imgFile.exists()) {
            try {
                File f = new File(imgFile.getPath());
                Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
                Uri uri = Uri.fromFile(imgFile);
                imgBySeason.setImageURI(uri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        // on met en place la liste d'épisodes via l'adapter
        adapter = new CustomAdapterSeason(this, listOfEpisodes);

        ListView list = (ListView) findViewById(R.id.listOfEpisodes);
        list.setAdapter(adapter);
        setListViewHeightBasedOnChildren(list);
        // Retire le focus sur la liste afin que l'activité démarre en haut de la page
        list.setFocusable(false);

        // en cas de click sur un épisode de la liste on ouvre une activité épisode
        list.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent appInfo = new Intent(BySeason.this, ByEpisode.class);
                        Episode goToEpisode = (Episode) adapter.getItem(position);
                        appInfo.putExtra("EPISODE_ID", String.valueOf(goToEpisode.getEpisodeID()));
                        startActivity(appInfo);
                    }
                }
        );

        // en cas de click sur la bouton delete....
        LinearLayout llayout_delete = (LinearLayout) findViewById (R.id.linearlayout_deleteSeason);
        llayout_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Si la liste de TOUTES les saisons de la série n'est pas nule
                if(listOfSeasons != null) {
                    int lastSeason = listOfSeasons.size() - 1;
                    if(lastSeason == -1)
                        lastSeason = 0;
                    // si la saison en cours est la dernière saison créée pour la série...
                    // on peut la supprimer pour ce faire on ouvre un dialog fragment
                    if (season.getSeasonNumber() == listOfSeasons.get(lastSeason).getSeasonNumber()) {
                        DialogFragment newFragment = new DeleteSeasonDialogAlert();
                        Bundle args = new Bundle();
                        args.putInt("SEASONS_ID", season.getSeasonId());
                        args.putInt("SHOW_ID", season.getShowId());
                        newFragment.setArguments(args);
                        newFragment.show(getFragmentManager(), "delete");
                    } else {
                        // si ce n'est pas la dernière on ne supprime pas la saison et on affiche un toast informatif
                        String text = getString(R.string.only_last_season);
                        Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }

            }
        });


        // en cas de click on peut créer une nouvelle épisode pour cette série
        // ouverture d'un dialog fragment permttant de saisir le nom de l'épisode.
        LinearLayout llayout_create = (LinearLayout) findViewById (R.id.linearlayout_createEpisode);
        final ArrayList<Episode> finalListOfEpisodes = listOfEpisodes;
        llayout_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new CreateEpisodeDialogAlert();
                Bundle args = new Bundle();
                int i = (int) Season_ID;
                args.putInt("numSeasonId", (int) season.getSeasonId());
                args.putInt("numEpisodes", finalListOfEpisodes.size());
                newFragment.setArguments(args);
                newFragment.show(getFragmentManager(), "create");
            }
        });

    }
    

    private void refreshMyActivity() {
        finish();
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }

    private boolean checkIfAllEpisodesAreWatched(ArrayList<Episode> listOfEpisodes, boolean watched) {
        int cpt = 0;
        while(cpt < listOfEpisodes.size() && watched == true){

            if(listOfEpisodes.get(cpt).isEpisodeCompleted() == 0)
                watched = false;
            ++cpt;
        }
        return watched;
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

    // TODO Problème avec l'ActionBar en cliquant sur Actor dans cette activité.

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                Intent intent = new Intent(BySeason.this, ByShow.class);
                BySeason.this.startActivity(intent);
                break;

            case R.id.action_byActor:

                intent = new Intent(BySeason.this, ActorByEpisode.class);
                BySeason.this.startActivity(intent);
                break;

            case R.id.action_addShow:

                intent = new Intent(BySeason.this, ByShow_Creation.class);
                BySeason.this.startActivity(intent);
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

    public void changeLanguage(String lang){
        Locale myLocale = new Locale(lang);
        Locale.setDefault(myLocale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = myLocale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        getSupportActionBar().setTitle(R.string.bySeason_pageTitle);

        TextView deleteSeason = (TextView) findViewById(R.id.deleteSeasonText);
        deleteSeason.setText(R.string.delete_season);
        TextView addEpisode = (TextView) findViewById(R.id.addEpisodeText);
        addEpisode.setText(R.string.add_episode);

    }
}
