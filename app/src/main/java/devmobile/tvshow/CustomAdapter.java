package devmobile.tvshow;

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

public class CustomAdapter extends ArrayAdapter<String>{

    public CustomAdapter(Context context, String[] serie) {
        super(context, R.layout.custom_row, serie);

    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.custom_row, parent, false);

        String serieToShow = getItem(position);
        TextView textView = (TextView) customView.findViewById(R.id.serieTitle);
        ImageView imageView = (ImageView) customView.findViewById(R.id.imageSerie);

        textView.setText(serieToShow);
        imageView.setImageResource(R.drawable.ncis_la);
        return customView;
    }
}
