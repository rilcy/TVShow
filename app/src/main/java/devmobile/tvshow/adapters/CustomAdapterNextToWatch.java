package devmobile.tvshow.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import devmobile.tvshow.object.Episode;
import devmobile.tvshow.R;

/**
 * Created by rilcy on 29.10.15.
 */
public class CustomAdapterNextToWatch extends ArrayAdapter<Episode>{

    private Episode episode;

    public CustomAdapterNextToWatch(Context context, ArrayList<Episode> episode) {
        super(context, R.layout.custom_row_main, (ArrayList) episode);


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.custom_row_main, parent, false);

        Episode episode = getItem(position);
        TextView textViewTitle = (TextView) customView.findViewById(R.id.serieTitle);
        TextView textViewInfo = (TextView) customView.findViewById(R.id.serieInfo);
        ImageView imageView = (ImageView) customView.findViewById(R.id.imageSerie);

        textViewTitle.setText(episode.getEpisodeTitle());
        textViewInfo.setText(episode.getEpisodeInfo());
        imageView.setImageResource(episode.getImg());

        // SRC POUR AJOUTER L'IMAGE : http://www.androidinterview.com/android-custom-listview-with-image-and-text-using-arrayadapter/
        // https://youtu.be/U_Jvk4G28YE New Boston

        return customView;
    }
}
