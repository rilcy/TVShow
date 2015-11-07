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
        View customView = inflater.inflate(R.layout.custom_row_actor, parent, false);

        Actor actorToShow = getItem(position);
        TextView firstNameTextView = (TextView) customView.findViewById(R.id.actorFirstname);
        TextView lastNameTextView = (TextView) customView.findViewById(R.id.actorLaststname);

        firstNameTextView.setText(actorToShow.getFirstName());
        lastNameTextView.setText(actorToShow.getLastName());

        return customView;
    }
}
