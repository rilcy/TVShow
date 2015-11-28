package devmobile.tvshow.alert;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.EditText;

import devmobile.tvshow.R;
import devmobile.tvshow.activities.BySeason;
import devmobile.tvshow.activities.ByShow;
import devmobile.tvshow.db.SQLiteHelper;
import devmobile.tvshow.db.adapter.EpisodeDataSource;
import devmobile.tvshow.db.adapter.SeasonDataSource;

public class CreateEpisodeDialogAlert extends DialogFragment {

    public  final static String SEASON_ID = "devemobile.tvshow.activities.ByShow.SEASON_ID";

    int mNumEpisodes;
    int mSeasonId;
    long numEpisodes;
    long seasonId;
    EditText etNewEpisode;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mSeasonId = getArguments().getInt("numSeasonId");
        mNumEpisodes = getArguments().getInt("numEpisodes");
        seasonId = (long) mSeasonId;
        numEpisodes = (long) mNumEpisodes;

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.activity_alert_creation_episode, null))
                // Add action buttons
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        etNewEpisode = (EditText) getDialog().findViewById(R.id.etNewEpisode);
                        String text = etNewEpisode.getText().toString();
                        EpisodeDataSource episodeds = new EpisodeDataSource(getActivity());
                        episodeds.createEpisode((int)seasonId, (int)numEpisodes, text);


                        Intent intent = new Intent(getActivity(), BySeason.class);
                        intent.putExtra(SEASON_ID, String.valueOf(seasonId));
                        startActivity(intent);
                        getActivity().finish();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        CreateEpisodeDialogAlert.this.getDialog().cancel();
                    }
                });

        return builder.create();
    }
}
