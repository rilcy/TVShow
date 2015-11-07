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

import devmobile.tvshow.R;
import devmobile.tvshow.db.object.Season;

public class CustomAdapterShow extends ArrayAdapter<Season>{

    private Season season;

    public CustomAdapterShow(Context context, ArrayList<Season> season) {
        super(context, R.layout.custom_row_season, (ArrayList) season);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.custom_row_season, parent, false);

        Season seasonToShow = getItem(position);
        TextView textView = (TextView) customView.findViewById(R.id.seasonNumber);

        textView.setText(seasonToShow.getSeasonNumber());

        return customView;
    }
}
