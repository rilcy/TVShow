package devmobile.tvshow;

/**
 * Created by rilcy on 17.10.15.
 */

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends ArrayAdapter<Serie>{

    private Serie serie;

    public CustomAdapter(Context context, ArrayList<Serie> serie) {
        super(context, R.layout.custom_row, (ArrayList) serie);


    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.custom_row, parent, false);

        Serie serieToShow = getItem(position);
        TextView textView = (TextView) customView.findViewById(R.id.serieTitle);
        ImageView imageView = (ImageView) customView.findViewById(R.id.imageSerie);

        textView.setText(serieToShow.getTitle());
        imageView.setImageResource(serieToShow.getImg());

        // SRC POUR AJOUTER L'IMAGE : http://www.androidinterview.com/android-custom-listview-with-image-and-text-using-arrayadapter/
        // https://youtu.be/U_Jvk4G28YE New Boston

        return customView;
    }
}
