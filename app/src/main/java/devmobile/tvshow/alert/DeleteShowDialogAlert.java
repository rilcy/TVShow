package devmobile.tvshow.alert;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;

import java.util.ArrayList;

import devmobile.tvshow.R;
import devmobile.tvshow.activities.MainActivity;
import devmobile.tvshow.db.adapter.CastingEpisodeDataSource;
import devmobile.tvshow.db.adapter.EpisodeDataSource;
import devmobile.tvshow.db.adapter.SeasonDataSource;
import devmobile.tvshow.db.adapter.ShowDataSource;
import devmobile.tvshow.db.object.CastingEpisode;
import devmobile.tvshow.db.object.Episode;
import devmobile.tvshow.db.object.Season;
import devmobile.tvshow.db.object.Show;

/**
 * Created by rilcy on 31.10.15.
 */
public class DeleteShowDialogAlert extends DialogFragment {

    private int show_id;
    private Show show;
    private ArrayList<Season> listOfSeasons;
    private ArrayList<Episode> listOfEpisodes;
    private ArrayList<CastingEpisode> listOfCastingEpisode;
    private ShowDataSource showsds;
    private SeasonDataSource seasonds;
    private EpisodeDataSource episodeds;
    private CastingEpisodeDataSource castringEpds;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        show_id = getArguments().getInt("SHOW_ID");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.activity_alert_delete, null))
                // Add action buttons
                .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        show = new Show();

                        // DELETE CASTING FROM EPISODES OF SEASONS


                        // DELETE EPISODES OF SEASONS


                        // DELETE SEASONS


                        // DELETE SHOW

                        listOfSeasons = new ArrayList<Season>();
                        listOfEpisodes = new ArrayList<Episode>();
                        listOfCastingEpisode = new ArrayList<CastingEpisode>();
                        showsds = new ShowDataSource(getActivity());
                        seasonds = new SeasonDataSource(getActivity());
                        episodeds = new EpisodeDataSource(getActivity());
                        castringEpds = new CastingEpisodeDataSource(getActivity());



                        show.deleteShow(show_id, getActivity());

                        //End activity and get back to the main activity
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        DeleteShowDialogAlert.this.getDialog().cancel();

                    }
                });

        return builder.create();
    }
}
