package devmobile.tvshow.alert;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.Toast;

import devmobile.tvshow.R;
import devmobile.tvshow.db.adapter.EpisodeDataSource;

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
        LayoutInflater inflater = getActivity().getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.activity_alert_creation_episode, null))

                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // Création d'une nouvelle épisode dans BySeason
                        etNewEpisode = (EditText) getDialog().findViewById(R.id.etNewEpisode);
                        String text = etNewEpisode.getText().toString();
                        // Vérification que l'utilisateur est entrée au moins une valeur
                        if (text.length() > 0) {
                            EpisodeDataSource episodeds = new EpisodeDataSource(getActivity());
                            // enregistrement dans la DB
                            episodeds.createEpisode((int) seasonId, (int) numEpisodes, text);

                            // retour à l'activité
                            Intent intent = getActivity().getIntent();
                            getActivity().finish();
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(intent);
                        } else {
                            // si aucune valeur n'est rentrée, on retourne à l'activité et on affiche un toast
                            String toast = getString(R.string.title_too_short);
                            Toast.makeText(getActivity(), toast, Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        CreateEpisodeDialogAlert.this.getDialog().cancel();
                        // retour à l'activité
                    }
                });

        return builder.create();
    }
}
