package devmobile.tvshow.alert;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.Toast;

import devmobile.tvshow.R;
import devmobile.tvshow.activities.ByActor;
import devmobile.tvshow.activities.BySeason;
import devmobile.tvshow.db.adapter.CastingDataSource;
import devmobile.tvshow.db.adapter.EpisodeDataSource;
import devmobile.tvshow.db.object.Actor;

/**
 * Created by Elsio on 28.11.2015.
 */
public class CreateActorDialogAlert extends DialogFragment {

    EditText actorFirstName;
    EditText actorLastName;
    Actor actor;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.activity_alert_creation_actor, null))

                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // Création d'un acteur via ByActor
                        actorFirstName = (EditText) getDialog().findViewById(R.id.alert_actorEditTextFirstName);
                        String stringFirstName = actorFirstName.getText().toString();
                        actorLastName = (EditText) getDialog().findViewById(R.id.alert_actorEditTextLastName);
                        String stringLastName = actorLastName.getText().toString();
                        // vérification que l'utilisateur ait bien entrée au moins une valeur pour
                        // le nom et le prénom
                        if (stringFirstName.length() > 0 && stringLastName.length() > 0) {
                            CastingDataSource castingds = new CastingDataSource(getActivity());
                            // on sauve dans la DB.
                            castingds.createCasting(stringFirstName, stringLastName);

                            // retour à l'activité
                            refreshMyActivity();
                        }
                        // si aucune valeur n'est rentrée, on retourne à l'activité et on affiche un toast
                        else {
                            String toast = getString(R.string.name_or_lastname_missing);
                            Toast.makeText(getActivity(), toast, Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        CreateActorDialogAlert.this.getDialog().cancel();
                        // retour à l'activité précédente
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
