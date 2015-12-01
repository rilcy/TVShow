package devmobile.tvshow.activities;

import android.app.DialogFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Locale;

import devmobile.tvshow.R;
import devmobile.tvshow.adapters.CustomAdapterActor;
import devmobile.tvshow.alert.CreateActorDialogAlert;
import devmobile.tvshow.alert.EditActorDialogAlert;
import devmobile.tvshow.db.adapter.CastingDataSource;
import devmobile.tvshow.db.object.Actor;

public class ByActor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_by_actor);

        // Préférence de langage
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        changeLanguage(sharedPrefs.getString("pref_lang", "en"));

        // Obtention de tous les acteurs via la DB
        CastingDataSource cds = new CastingDataSource(this);
        final ArrayList<Actor> listOfActors = (ArrayList<Actor>) cds.getAllActors();

        // Création de la ListView des acteurs et de l'adapter de la ListView
        ListView list = (ListView) findViewById(R.id.listOfActors);
        final ListAdapter adapter = new CustomAdapterActor(this, listOfActors);
        list.setAdapter(adapter);

        // En cas de clic pour créer un acteur
        LinearLayout llayout_create = (LinearLayout) findViewById (R.id.linearLayout_createActor);
        llayout_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Appelle le DialogFragment
                DialogFragment newFragment = new CreateActorDialogAlert();
                newFragment.show(getFragmentManager(), "create");
            }
        });

        // En cas de long click sur la row d'un acteur, on obtient un dialog fragment pour
        // modifier l'acteur (nom, prénom)
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {
                // Appel le DialogFragment
                DialogFragment newFragment = new EditActorDialogAlert();

                // Obtient l'acteur "cliqué"
                Actor actor = (Actor) adapter.getItem(pos);
                Bundle args = new Bundle();
                //int i = (int) num;
                args.putInt("passedActorId", actor.getIdActor());
                newFragment.setArguments(args);
                newFragment.show(getFragmentManager(), "edit");

                return true;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        menu.findItem(R.id.action_settings).setVisible(false);
        menu.findItem(R.id.action_byActor).setVisible(false);
        return true;
    }

    // Configuration du menu de l'action bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:

                Intent intent = new Intent(ByActor.this, MainActivity.class);
                ByActor.this.startActivity(intent);
                return true;

            case R.id.action_addShow:

                intent = new Intent(ByActor.this, ByShow_Creation.class);
                ByActor.this.startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    // Localisation + changement du titre de l'activité selon la langue
    public void changeLanguage(String lang){
        Locale myLocale = new Locale(lang);
        Locale.setDefault(myLocale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = myLocale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        getSupportActionBar().setTitle(R.string.byActor_pageTitle);

    }

}
