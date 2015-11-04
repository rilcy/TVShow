package devmobile.tvshow.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import devmobile.tvshow.Actor;
import devmobile.tvshow.R;
import devmobile.tvshow.adapters.CustomAdapterActor;
import devmobile.tvshow.adapters.CustomAdapterSeason;

public class ByEpisode extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_by_episode);

        final ArrayList<Actor> listOfActors = new ArrayList<Actor>();

        Actor actor1 = new Actor("Alan", "Ruck");
        listOfActors.add(actor1);
        Actor actor2 = new Actor("Ashanti", "Brown");
        listOfActors.add(actor2);
        Actor actor3 = new Actor("Billy", "Magnussen");
        listOfActors.add(actor3);
        Actor actor4 = new Actor("Vincent", "Laresca");
        listOfActors.add(actor4);
        Actor actor5 = new Actor("Graham", "Shiels");
        listOfActors.add(actor5);
        Actor actor6 = new Actor("Bruno", "Gioiello");
        listOfActors.add(actor6);
        Actor actor7 = new Actor("Walter", "Fauntleroy");
        listOfActors.add(actor7);
        Actor actor8 = new Actor("Zachary", "Stockdale");
        listOfActors.add(actor8);
        Actor actor9 = new Actor("Ashanti", "Brown");
        listOfActors.add(actor9);
        Actor actor10 = new Actor("Dusty", "Storg");
        listOfActors.add(actor10);

        ListAdapter adapter = new CustomAdapterActor(this, listOfActors);

        ListView list = (ListView) findViewById(R.id.listOfActorsForEpisode);
        list.setAdapter(adapter);
        setListViewHeightBasedOnChildren(list);
        // Retire le focus sur la liste afin que l'activité démarre en haut de la page
        list.setFocusable(false);

        LinearLayout llayout_addActor = (LinearLayout) findViewById (R.id.linearLayout_addActor);
        llayout_addActor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent appInfo = new Intent(ByEpisode.this, ByActor.class);
                startActivity(appInfo);
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

                Intent intent = new Intent(ByEpisode.this, BySeason.class);
                ByEpisode.this.startActivity(intent);
                break;

            case R.id.action_byActor:

                intent = new Intent(ByEpisode.this, ByActor.class);
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
}
