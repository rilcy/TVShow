package devmobile.tvshow.alert;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;


import devmobile.tvshow.R;
import devmobile.tvshow.activities.ByShow;
import devmobile.tvshow.activities.MainActivity;
import devmobile.tvshow.db.SQLiteHelper;
import devmobile.tvshow.db.adapter.SeasonDataSource;

public class CreateSeasonDialogAlert extends DialogFragment {

    int mNumSeasons;
    int mNum;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mNum = getArguments().getInt("showId");
        mNumSeasons = getArguments().getInt("numSeasons");


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
                        SeasonDataSource sds = new SeasonDataSource(getActivity());
                        sds.createSeason(mNum, mNumSeasons);

                        Intent intent = new Intent(getActivity(), ByShow.class);
                        intent.putExtra("SHOW_ID", String.valueOf(mNum));
                        startActivity(intent);
                        getActivity().finish();
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