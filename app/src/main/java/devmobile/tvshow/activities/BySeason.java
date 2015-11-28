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

    public  final static String EPISODE_ID = "devemobile.tvshow.activities.ByShow.EPISODE_ID";

    // TOP OF THE ACTIVITY
    private ImageView imgBySeason;
    private TextView showTitleBySeason;
    private CheckBox cbSeasonBySeason;
    private long num;
    private ShowDataSource showds;
    private SeasonDataSource seasonds;
    private EpisodeDataSource episodeds;
    private Season season;
    private ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_by_season);

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        changeLanguage(sharedPrefs.getString("pref_lang", "en"));

        String dataTransfered = getIntent().getStringExtra(ByShow.SEASON_ID);
        num = Long.parseLong(dataTransfered);

        // Get data from the Season
        seasonds = new SeasonDataSource(this);
        season = seasonds.getSeasonById((int)num);

        // Get data from the Show
        showds = new ShowDataSource(this);
        Show show = showds.getShowById(season.getShowId());

        // Get data from episodes of the Season
        ArrayList<Episode> listOfEpisodes = new ArrayList<Episode>();
        episodeds = new EpisodeDataSource(this);
        listOfEpisodes = (ArrayList<Episode>) episodeds.getAllEpisodes(season.getSeasonId());


        imgBySeason = (ImageView) findViewById(R.id.imgBySeason);
        showTitleBySeason = (TextView) findViewById(R.id.showTitleBySeason);
        cbSeasonBySeason = (CheckBox) findViewById(R.id.cbSeasonBySeason);

        // TOP OF THE ACTIVITY
        // TODO : Traduction du mot Season
        cbSeasonBySeason.setText(" Season " + season.getSeasonNumber());

        if(season.isSeasonCompleted() == 0)
            cbSeasonBySeason.setChecked(false);
        else
            cbSeasonBySeason.setChecked(true);

        cbSeasonBySeason.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (cbSeasonBySeason.isChecked()) {
                    season.setSeasonCompleted(1);
                    seasonds.updateSeason(season);
                } else {
                    season.setSeasonCompleted(0);
                    seasonds.updateSeason(season);
                }
            }
        });

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


        adapter = new CustomAdapterSeason(this, listOfEpisodes);

        ListView list = (ListView) findViewById(R.id.listOfEpisodes);
        list.setAdapter(adapter);
        setListViewHeightBasedOnChildren(list);
        // Retire le focus sur la liste afin que l'activité démarre en haut de la page
        list.setFocusable(false);

        list.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent appInfo = new Intent(BySeason.this, ByEpisode.class);
                        Episode goToEpisode = (Episode) adapter.getItem(position);
                        appInfo.putExtra(EPISODE_ID, String.valueOf(goToEpisode.getEpisodeID()));
                        startActivity(appInfo);
                    }
                }
        );


        LinearLayout llayout_delete = (LinearLayout) findViewById (R.id.linearlayout_deleteSeason);
        llayout_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DeleteSeasonDialogAlert();
                newFragment.show(getFragmentManager(), "delete");

            }
        });

        LinearLayout llayout_create = (LinearLayout) findViewById (R.id.linearlayout_createEpisode);
        final ArrayList<Episode> finalListOfEpisodes = listOfEpisodes;
        llayout_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new CreateEpisodeDialogAlert();
                Bundle args = new Bundle();
                int i = (int) num;
                args.putInt("numSeasonId", (int) season.getSeasonId());
                args.putInt("numEpisodes", finalListOfEpisodes.size());
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

        TextView deleteSeason = (TextView) findViewById(R.id.deleteSeasonText);
        deleteSeason.setText(R.string.delete_season);
        TextView addEpisode = (TextView) findViewById(R.id.addEpisodeText);
        addEpisode.setText(R.string.add_episode);
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
