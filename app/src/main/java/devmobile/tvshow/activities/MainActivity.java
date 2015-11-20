package devmobile.tvshow.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import java.util.ArrayList;

import devmobile.tvshow.db.SQLiteHelper;
import devmobile.tvshow.db.adapter.ShowDataSource;
import devmobile.tvshow.db.object.Show;
import devmobile.tvshow.adapters.CustomAdapterMain;
import devmobile.tvshow.R;


public class MainActivity extends AppCompatActivity {

    public  final static String SHOW_ID = "devemobile.tvshow.activities.MainActivity.SHOW_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ShowDataSource sds = new ShowDataSource(this);

        ArrayList<Show> listOfSeries = (ArrayList<Show>) sds.getAllShows();

        ListView list = (ListView) findViewById(R.id.listOfSeries);

        final ListAdapter adapter = new CustomAdapterMain(this, listOfSeries);

        list.setAdapter(adapter);

        list.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent appInfo = new Intent(MainActivity.this, ByShow.class);

                        Show show = (Show) adapter.getItem(position);

                        appInfo.putExtra(SHOW_ID, String.valueOf(show.getShowId()));


                        startActivity(appInfo);

                    }
                }
        );

        SQLiteHelper sqlHelper = SQLiteHelper.getInstance(this);
        sqlHelper.getWritableDatabase().close();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent = getIntent();

        switch(item.getItemId()){

            //TODO : Ajouter ici l'action pour la page d'ajout de séries lorsque la page sera créée

            case R.id.action_byActor:

                intent = new Intent(MainActivity.this, ByActor.class);
                MainActivity.this.startActivity(intent);
                break;

            case R.id.action_addShow:

                intent = new Intent(MainActivity.this, ByShow_Creation.class);
                MainActivity.this.startActivity(intent);
                break;
        }
        return true;
    }
}
