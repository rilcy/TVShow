package devmobile.tvshow.adapters;

/**
 * Created by Elsio on 28.10.15.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

import devmobile.tvshow.R;
import devmobile.tvshow.db.SQLiteHelper;
import devmobile.tvshow.db.object.Season;

public class CustomAdapterShow extends ArrayAdapter<Season>{

    private CustomAdapterShow customAdapterShow = null;
    private Context context;
    private TextView textSeason = null;
    private CheckBox cb = null;

    public CustomAdapterShow(Context context, ArrayList<Season> season) {
        super(context, R.layout.custom_row_season, (ArrayList) season);
        this.customAdapterShow = this;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.custom_row_season, parent, false);


        final Season season = (Season) this.getItem(position);
        textSeason = (TextView) customView.findViewById(R.id.episodeTitle);
        cb = (CheckBox) customView.findViewById(R.id.seasonCheckBox);

        textSeason.setText("Season " + season.getSeasonNumber());
        if(season.isSeasonCompleted() == 0)
            cb.setChecked(false);
        else
            cb.setChecked(true);

        SQLiteHelper sqlHelper = SQLiteHelper.getInstance(context.getApplicationContext());
        sqlHelper.getWritableDatabase().close();

        return customView;
    }
}
