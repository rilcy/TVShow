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

public class EditEpisodeDialogAlert extends DialogFragment {

    private int mNumEpisodeId;
    private String episodeTitle;
    private EditText etEditEpisodeByEpisode;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mNumEpisodeId = getArguments().getInt("numEpisodeId");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.activity_alert_edition_episode, null))

                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // Correction du nom de l'épisode

                        EpisodeDataSource episodeds = new EpisodeDataSource(getActivity());
                        etEditEpisodeByEpisode = (EditText) getDialog().findViewById(R.id.etEditEpisodeByEpisode);
                        // vérifie que l'utilisateur est entré au moins une valeur
                        if (etEditEpisodeByEpisode.length() > 0){
                            episodeTitle = etEditEpisodeByEpisode.getText().toString();
                            episodeds.updateEpisode(mNumEpisodeId, episodeTitle);

                            // retour à l'activité
                            refreshMyActivity();
                        }
                        else{
                            String toast = getString(R.string.title_too_short);
                            Toast.makeText(getActivity(), toast, Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        EditEpisodeDialogAlert.this.getDialog().cancel();
                    }
                });

        return builder.create();
    }

    private void refreshMyActivity() {
        getActivity().finish();
        Intent intent = getActivity().getIntent();
        getActivity().overridePendingTransition(0, 0);
        startActivity(getActivity().getIntent());
        getActivity().overridePendingTransition(0, 0);
    }
}
