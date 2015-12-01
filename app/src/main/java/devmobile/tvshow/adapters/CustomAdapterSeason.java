package devmobile.tvshow.adapters;

/**
 * Created by Elsio on 28.10.15.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import devmobile.tvshow.db.adapter.EpisodeDataSource;
import devmobile.tvshow.db.object.Episode;
import devmobile.tvshow.R;

public class CustomAdapterSeason extends ArrayAdapter<Episode>{

    private CustomAdapterSeason customAdapterSeason = null;
    private Context context;
    private TextView text = null;
    private ImageView imageView = null;

    private Episode episodeToShow;
    private CheckBox cbEpisode;
    private TextView textView;
    private EpisodeDataSource episodeds;

    private RecyclerView.ViewHolder viewHolder;

    public CustomAdapterSeason(Context context, ArrayList<Episode> episode) {
        super(context, R.layout.custom_row_episode, (ArrayList) episode);
        this.customAdapterSeason = this;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.custom_row_episode, parent, false);

        // affiche des épisodes d'une saison dans BySeason
        episodeToShow = getItem(position);
        textView = (TextView) customView.findViewById(R.id.episodeTitle);

        // affiche le numéro de l'épisode et le titre et la checkbox d'état
        textView.setText(episodeToShow.getEpisodeNumber() + " " + episodeToShow.getEpisodeTitle());

        cbEpisode = (CheckBox) customView.findViewById(R.id.episodeCheckBox);
        episodeds = new EpisodeDataSource(context);

        // on affiche l'état de l'épisode
        if(episodeToShow.isEpisodeCompleted() == 0)
            cbEpisode.setChecked(false);
        else
            cbEpisode.setChecked(true);

        // colore les rows une ligne sur deux
        if (position % 2 == 0)
            customView.setBackgroundResource(R.drawable.listview_selector_even);
        else
            customView.setBackgroundResource(R.drawable.listview_selector_odd);


        return customView;
    }
}
