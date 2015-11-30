package devmobile.tvshow.alert;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;

import java.util.ArrayList;

import devmobile.tvshow.R;
import devmobile.tvshow.activities.ByEpisode;
import devmobile.tvshow.activities.MainActivity;
import devmobile.tvshow.db.adapter.CastingEpisodeDataSource;
import devmobile.tvshow.db.adapter.EpisodeDataSource;
import devmobile.tvshow.db.adapter.SeasonDataSource;
import devmobile.tvshow.db.adapter.ShowDataSource;
import devmobile.tvshow.db.object.Actor;
import devmobile.tvshow.db.object.CastingEpisode;
import devmobile.tvshow.db.object.Episode;
import devmobile.tvshow.db.object.Season;
import devmobile.tvshow.db.object.Show;

/**
 * Created by rilcy on 31.10.15.
 */
public class DeleteActorEpisodeDialogAlert extends DialogFragment {

    private int episode_id;
    private String actor;
    private int actor_id;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        episode_id = getArguments().getInt("EPISODE_ID");
        actor = getArguments().getString("NAME");
        actor_id = getArguments().getInt("ACTOR_ID");

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
                        CastingEpisodeDataSource ceds = new CastingEpisodeDataSource(getActivity());

                        ceds.deleteCastingForActor(episode_id, actor_id);

                        //End activity and get back to the main activity
                        Intent intent = new Intent(getActivity(), ByEpisode.class);
                        intent.putExtra("EPISODE_ID", String.valueOf(episode_id));

                        startActivity(intent);
                        getActivity().finish();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        DeleteActorEpisodeDialogAlert.this.getDialog().cancel();

                    }
                });

        return builder.create();
    }
}
