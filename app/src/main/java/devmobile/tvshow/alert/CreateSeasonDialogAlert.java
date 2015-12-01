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
        mNum = getArguments().getInt("SHOW_ID");
        mNumSeasons = getArguments().getInt("numSeasons");


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.activity_alert_creation_season, null))

                .setPositiveButton(R.string.create, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        // Création d'une nouvelle saison
                        SeasonDataSource sds = new SeasonDataSource(getActivity());
                        // sauvegarde dans la DB
                        sds.createSeason(mNum, mNumSeasons);

                        // retour à l'activité
                        Intent intent = new Intent(getActivity(), ByShow.class);
                        intent.putExtra("SHOW_ID", String.valueOf(mNum));
                        startActivity(intent);
                        getActivity().finish();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        CreateSeasonDialogAlert.this.getDialog().cancel();
                        // retour à l'activité précédente
                    }
                });


        return builder.create();
    }
}