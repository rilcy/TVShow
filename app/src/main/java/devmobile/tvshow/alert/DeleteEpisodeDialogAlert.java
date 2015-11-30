package devmobile.tvshow.alert;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;

import devmobile.tvshow.R;
import devmobile.tvshow.activities.BySeason;
import devmobile.tvshow.activities.ByShow;
import devmobile.tvshow.db.adapter.EpisodeDataSource;
import devmobile.tvshow.db.object.Episode;

public class DeleteEpisodeDialogAlert extends DialogFragment {

    private EpisodeDataSource episodeds;
    private Episode episode;
    private int numEpisodeId;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        numEpisodeId = getArguments().getInt("numEpisodeId");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.activity_alert_delete_episode, null))
                // Add action buttons
                .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        episodeds = new EpisodeDataSource(getActivity());
                        episode = episodeds.getEpisodeById(numEpisodeId);

                        episodeds.deleteEpisode(episode.getEpisodeID());

                        //End activity and get back to the previous activity
                        getActivity().finish();
                        Intent intent = new Intent(getActivity(), BySeason.class);
                        intent.putExtra("SEASON_ID", String.valueOf(episode.getSeasonID()));
                        startActivity(intent);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        DeleteEpisodeDialogAlert.this.getDialog().cancel();
                    }
                });

        return builder.create();
    }
}