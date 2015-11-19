package devmobile.tvshow.adapters;

/**
 * Created by rilcy on 17.10.15.
 */

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
import java.net.URI;
import java.util.ArrayList;

import devmobile.tvshow.R;
import devmobile.tvshow.db.object.Show;

public class CustomAdapter extends ArrayAdapter<Show>{

    private CustomAdapter customAdapter = null;
    private Context context;
    private TextView textView = null;
    private TextView text = null;
    private ImageView imageView = null;

    public CustomAdapter(Context context, ArrayList<Show> shows) {
        super(context, R.layout.custom_row_main, shows);
        this.customAdapter = this;
        this.context = context;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.custom_row_main, parent, false);

        textView = (TextView) customView.findViewById(R.id.serieTitle);
        imageView = (ImageView) customView.findViewById(R.id.imageSerie);

        final int i = position;
        final Show show = this.getItem(position);
        textView.setText(show.getShowTitle());
        //text.setText(show.getShowImage());

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