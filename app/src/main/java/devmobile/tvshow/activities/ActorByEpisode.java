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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import devmobile.tvshow.*;
import devmobile.tvshow.adapters.CustomAdapterActor;
import devmobile.tvshow.db.adapter.CastingDataSource;
import devmobile.tvshow.db.adapter.CastingEpisodeDataSource;
import devmobile.tvshow.db.object.Actor;
import devmobile.tvshow.db.object.CastingEpisode;


public class ActorByEpisode extends AppCompatActivity {

    private long Episode_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actor_by_episode);

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        changeLanguage(sharedPrefs.getString("pref_lang", "en"));

        // Réception des données insérées dans l'intent
        final String dataTransfered = getIntent().getStringExtra("EPISODE_ID");
        Episode_ID = Long.parseLong(dataTransfered);

        // Création d'une liste d'acteurs via les acteurs de la DB
        CastingDataSource cds = new CastingDataSource(this);
        final ArrayList<Actor> listOfActors = (ArrayList<Actor>) cds.getAllActors();


        // Création d'une liste d'acteur présent pour l'épisode sélectionné via la DB.
        final CastingEpisodeDataSource castingEpisodeds = new CastingEpisodeDataSource(this);
        ArrayList<CastingEpisode> listOfCasting = (ArrayList) castingEpisodeds.getActorsIdByEpisodeId((int) Episode_ID);

        // On boucle dans les 2 listes afin de retirer les acteurs déjà présents dans l'épisode
        // Cela permettra d'afficher uniquement les acteurs qui ne sont pas déjà présents dans l'épisode
        for (int i = 0; i < listOfCasting.size(); i++) {
            int idActor = listOfCasting.get(i).getCastingId();
            boolean is = true;
            int cpt = 0;
            while(is && cpt < listOfActors.size()){
                int id = listOfActors.get(cpt).getIdActor();
                if (id == idActor) {
                    listOfActors.remove(cpt);
                    is = false;
                }
                ++cpt;
            }
        }

        // Affiche la liste des acteurs disponibles
        if (listOfActors.size() > 0) {
            ListView list = (ListView) findViewById(R.id.listOfActorsFromEpisode);
            final ListAdapter adapter = new CustomAdapterActor(this, listOfActors);
            list.setAdapter(adapter);

            list.setOnItemClickListener(
                    new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            // Récupère l'acteur sélectionné
                            Actor actor = (Actor) adapter.getItem(position);

                            // Ajoute dans la Table CastingDataSource l'acteur à l'épisode
                            castingEpisodeds.createActorEpisode(actor.getIdActor(), Episode_ID);

                            // Met fin à l'activité en cours et accède à nouveau à l'épisode
                            backToByEpisode();

                        }
                    }
            );
        }
        else{
            // Si tous les acteurs de la DB jouent dans le film. On affiche un toast et on
            // retourne immédiatemment à l'épisode.
            String text = getString(R.string.no_more_actor);
            Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
            toast.show();

            // Met fin à l'activité en cours et accède à nouveau à l'épisode
            backToByEpisode();
        }
    }

    private void backToByEpisode() {

        // Met fin à l'activité en cours et accède à nouveau à l'épisode
        finish();
        Intent appInfo = new Intent(ActorByEpisode.this, ByEpisode.class);
        appInfo.putExtra("EPISODE_ID", String.valueOf(Episode_ID));
        startActivity(appInfo);
        finish();
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
