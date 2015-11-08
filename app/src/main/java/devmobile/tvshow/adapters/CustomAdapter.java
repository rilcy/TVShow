package devmobile.tvshow.adapters;

/**
 * Created by rilcy on 17.10.15.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import devmobile.tvshow.R;
import devmobile.tvshow.db.object.Show;

public class CustomAdapter extends ArrayAdapter<Show>{

    private Show show;

    public CustomAdapter(Context context, ArrayList<Show> show) {
        super(context, R.layout.custom_row_main, (ArrayList) show);


    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.custom_row_main, parent, false);

        Show showToShow = getItem(position);
        TextView textView = (TextView) customView.findViewById(R.id.serieTitle);
        ImageView imageView = (ImageView) customView.findViewById(R.id.imageSerie);

        textView.setText(showToShow.getShowTitle());
        imageView.setImageResource(showToShow.getShowImage());

        // SRC POUR AJOUTER L'IMAGE : http://www.androidinterview.com/android-custom-listview-with-image-and-text-using-arrayadapter/
        // https://youtu.be/U_Jvk4G28YE New Boston

        return customView;
    }
}
