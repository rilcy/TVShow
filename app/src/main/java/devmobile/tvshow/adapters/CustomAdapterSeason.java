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

import devmobile.tvshow.object.Episode;
import devmobile.tvshow.R;

public class CustomAdapterSeason extends ArrayAdapter<Episode>{

    private Episode episode;

    public CustomAdapterSeason(Context context, ArrayList<Episode> episode) {
        super(context, R.layout.custom_row_episode, (ArrayList) episode);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.custom_row_episode, parent, false);

        Episode episodeToShow = getItem(position);
        TextView textView = (TextView) customView.findViewById(R.id.episodeTitle);

        textView.setText(episodeToShow.getEpisodeTitle());

        return customView;
    }
}
