package devmobile.tvshow.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Locale;

import devmobile.tvshow.*;
import devmobile.tvshow.adapters.CustomAdapterActor;
import devmobile.tvshow.db.adapter.CastingDataSource;
import devmobile.tvshow.db.adapter.CastingEpisodeDataSource;
import devmobile.tvshow.db.object.Actor;
import devmobile.tvshow.db.object.Episode;

public class ActorByEpisode extends AppCompatActivity {

    private long num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actor_by_episode);

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        changeLanguage(sharedPrefs.getString("pref_lang", "en"));

        final String dataTransfered = getIntent().getStringExtra("EPISODE_ID");
        num = Long.parseLong(dataTransfered);

        CastingDataSource cds = new CastingDataSource(this);
        final ArrayList<Actor> listOfActors = (ArrayList<Actor>) cds.getAllActors();

        final CastingEpisodeDataSource castingEpisodeds = new CastingEpisodeDataSource(this);

        ListView list = (ListView) findViewById(R.id.listOfActorsFromEpisode);
        final ListAdapter adapter = new CustomAdapterActor(this, listOfActors);
        list.setAdapter(adapter);

        list.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent appInfo = new Intent(ActorByEpisode.this, ByEpisode.class);
                        //Actor actor = (Actor) adapter.getItem(position);
                        castingEpisodeds.createActorEpisode(id, num);
                        startActivity(appInfo);
                    }
                }
        );
/*
        final ArrayList<Actor> listOfActors = new ArrayList<Actor>();

        Actor actor1 = new Actor("Jean", "Reno");
        listOfActors.add(actor1);
        Actor actor2 = new Actor("Michael", "Douglas");
        listOfActors.add(actor2);
        Actor actor3 = new Actor("Leonardo", "DiCaprio");
        listOfActors.add(actor3);
        Actor actor4 = new Actor("Brad", "Pitt");
        listOfActors.add(actor4);
        Actor actor5 = new Actor("Kevin", "Spacey");
        listOfActors.add(actor5);
        Actor actor6 = new Actor("Jim", "Parsons");
        listOfActors.add(actor6);
        Actor actor7 = new Actor("Kaley", "Kuoco");
        listOfActors.add(actor7);

        ListAdapter adapter = new CustomAdapterActor(this, listOfActors);

        ListView list = (ListView) findViewById(R.id.listOfActors);
        list.setAdapter(adapter);
        */
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

                Intent intent = new Intent(ActorByEpisode.this, MainActivity.class);
                ActorByEpisode.this.startActivity(intent);
                return true;

            case R.id.action_addShow:

                intent = new Intent(ActorByEpisode.this, ByShow_Creation.class);
                ActorByEpisode.this.startActivity(intent);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    public void changeLanguage(String lang){
        Locale myLocale = new Locale(lang);
        Locale.setDefault(myLocale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = myLocale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

    }


}
