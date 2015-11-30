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
import devmobile.tvshow.activities.ByShow;
import devmobile.tvshow.db.adapter.EpisodeDataSource;
import devmobile.tvshow.db.adapter.SeasonDataSource;


public class DeleteSeasonDialogAlert extends DialogFragment {

    private int numSeasonId;
    private SeasonDataSource seasonsds;
    private EpisodeDataSource episodeDataSource;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        numSeasonId = getArguments().getInt("numSeasonId");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.activity_alert_delete_season, null))
                // Add action buttons
                .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        //End activity and get back to the previous activity
                        Intent intent = new Intent(getActivity(), ByShow.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        DeleteSeasonDialogAlert.this.getDialog().cancel();
                    }
                });

        return builder.create();
    }
}
