package devmobile.tvshow.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import devmobile.tvshow.db.adapter.SeasonDataSource;
import devmobile.tvshow.db.adapter.ShowDataSource;
import devmobile.tvshow.db.object.Episode;
import devmobile.tvshow.R;
import devmobile.tvshow.db.object.Season;
import devmobile.tvshow.db.object.Show;

/**
 * Created by rilcy on 29.10.15.
 */
public class CustomAdapterNextToWatch extends ArrayAdapter<Episode>{

    private Episode episode;
    private int ShowId;

    public CustomAdapterNextToWatch(Context context, ArrayList<Episode> episode, String imgPath, int ShowId) {
        super(context, R.layout.custom_row_main, (ArrayList) episode);
        this.ShowId = ShowId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.custom_row_main, parent, false);

        Episode episode = getItem(position);
        TextView textViewTitle = (TextView) customView.findViewById(R.id.serieTitle);
        TextView textViewInfo = (TextView) customView.findViewById(R.id.serieInfo);
        ImageView imageView = (ImageView) customView.findViewById(R.id.imageSerie);

        ShowDataSource showds = new ShowDataSource(getContext());
        Show show = showds.getShowById(ShowId);

        if(episode != null) {
            textViewTitle.setText(episode.getEpisodeTitle());
            SeasonDataSource seasonds = new SeasonDataSource(getContext());
            Season season = seasonds.getSeasonById(episode.getSeasonID());
            textViewInfo.setText(getContext().getString(R.string.Season) + season.getSeasonNumber() + " Episode " + episode.getEpisodeNumber());
            //textViewInfo.setText("" + getString(R.string.Season) + season.getSeasonNumber() + " Episode " + episode.getEpisodeNumber());
        }
        else{
            textViewTitle.setText(show.getShowTitle());
            textViewInfo.setText(R.string.no_more_episodes);
        }

        File imgFile = new  File(show.getShowImage());

        if(imgFile.exists()) {
            try {
                File f = new File(imgFile.getPath());
                Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
                Uri uri = Uri.fromFile(imgFile);
                imageView.setImageURI(uri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        // SRC POUR AJOUTER L'IMAGE : http://www.androidinterview.com/android-custom-listview-with-image-and-text-using-arrayadapter/
        // https://youtu.be/U_Jvk4G28YE New Boston

        return customView;
    }
}