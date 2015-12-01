package devmobile.tvshow.adapters;

/**
 * Created by Elsio on 28.10.15.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import devmobile.tvshow.db.object.Actor;
import devmobile.tvshow.R;

public class CustomAdapterActor extends ArrayAdapter<Actor>{

    public CustomAdapterActor(Context context, ArrayList<Actor> actors) {
        super(context, R.layout.custom_row_episode, (ArrayList) actors);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.custom_row_by_actor, parent, false);

        // on récupère l'acteur à afficher dans la liste
        Actor actorToShow = getItem(position);

        TextView firstNameTextView = (TextView) customView.findViewById(R.id.actorFirstname);
        TextView lastNameTextView = (TextView) customView.findViewById(R.id.actorLaststname);
        // on affiche son nom et son prénom
        firstNameTextView.setText(actorToShow.getFirstName());
        lastNameTextView.setText(actorToShow.getLastName());

        // colore les rows une ligne sur deux
        if ( position % 2 == 0)
        customView.setBackgroundResource(R.drawable.listview_selector_even);
        else
        customView.setBackgroundResource(R.drawable.listview_selector_odd);


        return customView;
    }
}
