package devmobile.tvshow.alert;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;

import java.util.List;

import devmobile.tvshow.R;
import devmobile.tvshow.adapters.CustomAdapterShow;
import devmobile.tvshow.db.adapter.SeasonDataSource;
import devmobile.tvshow.db.object.Season;

public class CreateSeasonDialogAlert extends DialogFragment {

    private SeasonDataSource sds = new SeasonDataSource(getActivity());

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        int mNum = getArguments().getInt("num");
        final long SHOW_ID = (long) mNum;

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.activity_alert_creation_season, null))
                // Add action buttons
                .setPositiveButton(R.string.create, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        sds.createSeason(SHOW_ID);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        CreateSeasonDialogAlert.this.getDialog().cancel();
                    }
                });

        return builder.create();
    }
}