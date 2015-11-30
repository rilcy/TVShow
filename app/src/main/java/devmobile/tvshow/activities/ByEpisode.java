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

import devmobile.tvshow.db.adapter.CastingDataSource;
import devmobile.tvshow.db.adapter.CastingEpisodeDataSource;
import devmobile.tvshow.db.adapter.EpisodeDataSource;
import devmobile.tvshow.db.adapter.SeasonDataSource;
import devmobile.tvshow.db.adapter.ShowDataSource;
import devmobile.tvshow.db.object.Actor;
import devmobile.tvshow.R;
import devmobile.tvshow.adapters.CustomAdapterActor;
import devmobile.tvshow.alert.DeleteEpisodeDialogAlert;
import devmobile.tvshow.alert.EditEpisodeDialogAlert;
import devmobile.tvshow.db.object.CastingEpisode;
import devmobile.tvshow.db.object.Episode;
import devmobile.tvshow.db.object.Season;
import devmobile.tvshow.db.object.Show;

public class ByEpisode extends AppCompatActivity {

    private long Episode_ID;
    private EpisodeDataSource episodeds;
    private SeasonDataSource seasonds;
    private ShowDataSource showds;
    private CastingEpisodeDataSource castingEpisodeds;
    private Episode episode;
    private CastingDataSource castingds;
    private ArrayList<Episode> listOfEpisode;
    private ArrayList<Actor> listOfActors;
    private ArrayList<CastingEpisode> listOfCastingEpisodes;
    private Season season;
    private Show show;
    private ImageView imgByEpisode;
    private TextView titleByEpisode;
    private TextView infoByEpisode;
    private CheckBox cbByEpisode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_by_episode);

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        changeLanguage(sharedPrefs.getString("pref_lang", "en"));

        String dataTransfered = getIntent().getStringExtra("EPISODE_ID");
        Episode_ID = Long.parseLong(dataTransfered);

        // Get data from the Episode
        episodeds = new EpisodeDataSource(this);
        episode = episodeds.getEpisodeById(Episode_ID);

        listOfEpisode = new ArrayList<Episode>();
        listOfEpisode = (ArrayList<Episode>) episodeds.getAllEpisodes(episode.getSeasonID());

        // Get data from the actors
        listOfActors = new ArrayList<Actor>();

        // Get data from casting+episodes
        castingEpisodeds = new CastingEpisodeDataSource(this);
        castingds = new CastingDataSource(this);
        listOfCastingEpisodes = (ArrayList<CastingEpisode>) castingEpisodeds.getActorsIdByEpisodeId(episode.getEpisodeID());


        for (int i=0; i<listOfCastingEpisodes.size(); i++){

            Actor actor = new Actor();
            actor = castingds.getActorById(listOfCastingEpisodes.get(i).getCastingId());

            listOfActors.add(actor);
        }

        ListAdapter adapter = new CustomAdapterActor(this, listOfActors);
        ListView list = (ListView) findViewById(R.id.listOfActorsForEpisode);
        list.setAdapter(adapter);
        setListViewHeightBasedOnChildren(list);

        // Retire le focus sur la liste afin que l'activité démarre en haut de la page
        list.setFocusable(false);


        // Get data from the Season of the Episode
        seasonds = new SeasonDataSource(this);
        season = seasonds.getSeasonById(episode.getSeasonID());

        // Get data from the Show of the Episode
        showds = new ShowDataSource(this);
        show = showds.getShowById(season.getShowId());


        // TOP OF THE ACTIVITY
        imgByEpisode = (ImageView) findViewById(R.id.imgByEpisode);
        titleByEpisode = (TextView) findViewById(R.id.titleByEpisode);
        infoByEpisode = (TextView) findViewById(R.id.infoByEpisode);
        cbByEpisode = (CheckBox) findViewById(R.id.cbByEpisode);

        titleByEpisode.setText(episode.getEpisodeTitle());

        infoByEpisode.setText(getString(R.string.Season) + season.getSeasonNumber() + " Episode " + episode.getEpisodeNumber());

        File imgFile = new  File(show.getShowImage());

        if(imgFile.exists()) {
            try {
                File f = new File(imgFile.getPath());
                Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
                Uri uri = Uri.fromFile(imgFile);
                imgByEpisode.setImageURI(uri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        if(episode.isEpisodeCompleted() == 0)
            cbByEpisode.setChecked(false);
        else
            cbByEpisode.setChecked(true);


        // IF EPISODE WAS WATCHED
        cbByEpisode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    episode.setEpisodeCompleted(1);
                    episodeds.updateEpisodeIfWatched(episode);
                    checkIfSeasonHasToBeCompleted();
                } else {
                    episode.setEpisodeCompleted(0);
                    episodeds.updateEpisodeIfWatched(episode);
                    checkIfSeasonsHasToBeUnchecked();
                }
            }
        });


        LinearLayout llayout_delete = (LinearLayout) findViewById (R.id.linearlayout_deleteEpisode);
        llayout_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listOfEpisode != null) {
                    int lastEpisode = listOfEpisode.size() - 1;
                    if (episode.getEpisodeNumber() == listOfEpisode.get(lastEpisode).getEpisodeNumber()) {
                        DialogFragment newFragment = new DeleteEpisodeDialogAlert();
                        Bundle args = new Bundle();
                        int i = (int) Episode_ID;
                        args.putInt("numEpisodeId", (int) episode.getEpisodeID());
                        newFragment.setArguments(args);
                        newFragment.show(getFragmentManager(), "delete");
                    } else {
                        // todo traduction
                        String text = "Only the last episode can be deleted";
                        Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
            }
        });

        LinearLayout llayout_edit = (LinearLayout) findViewById (R.id.linearlayout_editEpisode);
        llayout_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new EditEpisodeDialogAlert();
                Bundle args = new Bundle();
                int i = (int) Episode_ID;
                args.putInt("numEpisodeId", episode.getEpisodeID());
                newFragment.setArguments(args);
                newFragment.show(getFragmentManager(), "edit");

            }
        });

        LinearLayout llayout_addActor = (LinearLayout) findViewById (R.id.linearLayout_addActor);
        llayout_addActor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent appInfo = new Intent(ByEpisode.this, ActorByEpisode.class);
                appInfo.putExtra("EPISODE_ID", String.valueOf(episode.getEpisodeID()));
                startActivity(appInfo);
                finish();
            }
        });
    }

    protected void onPause(){
        super.onPause();
    }


    private void checkIfSeasonHasToBeCompleted() {
        int cpt = 1;
        while (cpt < listOfEpisode.size() && listOfEpisode.get(cpt).isEpisodeCompleted() == 1){
            cpt++;
        }

        if (cpt == 0)
            cpt = 1;
        if(listOfEpisode.get(cpt-1).isEpisodeCompleted() == 0){
            season.setSeasonCompleted(1);
            seasonds.updateSeason(season);
        }
    }


    private void checkIfSeasonsHasToBeUnchecked() {
        season.setSeasonCompleted(0);
        seasonds.updateSeason(season);
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

                Intent intent = new Intent(ByEpisode.this, ByShow.class);
                ByEpisode.this.startActivity(intent);
                break;

            case R.id.action_byActor:

                intent = new Intent(ByEpisode.this, ByActor.class);
                ByEpisode.this.startActivity(intent);
                break;

            case R.id.action_addShow:

                intent = new Intent(ByEpisode.this, ByShow_Creation.class);
                ByEpisode.this.startActivity(intent);
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
        getSupportActionBar().setTitle(R.string.byEpisode_pageTitle);

        TextView editEpisode = (TextView) findViewById(R.id.editEpisodeText);
        editEpisode.setText(R.string.edit_episode);
        TextView deleteEpisode = (TextView) findViewById(R.id.deleteEpisodeText);
        deleteEpisode.setText(R.string.delete_episode);
        TextView addActor = (TextView) findViewById(R.id.addActorText);
        addActor.setText(R.string.add_actor);

        /*
        Button buttonLoadPicture = (Button) findViewById(R.id.buttonLoadPicture);
        buttonLoadPicture.setText(R.string.buttonLoadPicture);
        TextView showName = (TextView) findViewById(R.id.showName);
        showName.setText(R.string.ShowName);
        TextView showStart = (TextView) findViewById(R.id.showStart);
        showStart.setText(R.string.ShowStart);
        CheckBox cbIsFinished = (CheckBox) findViewById(R.id.cbiSFinished);
        cbIsFinished.setText(R.string.cbisFinished);
        TextView showEnd = (TextView) findViewById(R.id.showEnd);
        showEnd.setText(R.string.ShowEnd);
        Button buttonOk = (Button) findViewById(R.id.buttonOk);
        buttonOk.setText(R.string.buttonOk);
        Button buttonCancel = (Button) findViewById(R.id.buttonCancel);
        buttonCancel.setText(R.string.buttonCancel);
        */
    }
}
