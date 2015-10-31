package devmobile.tvshow;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ByActor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_by_actor);

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

    }
}
