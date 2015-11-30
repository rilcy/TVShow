package devmobile.tvshow.alert;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.Toast;

import devmobile.tvshow.R;
import devmobile.tvshow.activities.ByEpisode;
import devmobile.tvshow.activities.BySeason;
import devmobile.tvshow.db.adapter.EpisodeDataSource;
import devmobile.tvshow.db.adapter.SeasonDataSource;

public class EditEpisodeDialogAlert extends DialogFragment {

    private int mNumEpisodeId;
    private String episodeTitle;
    private EditText etEditEpisodeByEpisode;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mNumEpisodeId = getArguments().getInt("numEpisodeId");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.activity_alert_edition_episode, null))
                // Add action buttons
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        EpisodeDataSource episodeds = new EpisodeDataSource(getActivity());
                        etEditEpisodeByEpisode = (EditText) getDialog().findViewById(R.id.etEditEpisodeByEpisode);
                        if (etEditEpisodeByEpisode.length() > 0){
                            episodeTitle = etEditEpisodeByEpisode.getText().toString();
                            episodeds.updateEpisode(mNumEpisodeId, episodeTitle);

                            Intent intent = new Intent(getActivity(), ByEpisode.class);
                            intent.putExtra("EPISODE_ID", String.valueOf(mNumEpisodeId));
                            startActivity(intent);
                            getActivity().finish();
                        }
                        else{
                            //TODO TRANSLATE
                            String toast = "Title too short !";
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
}
