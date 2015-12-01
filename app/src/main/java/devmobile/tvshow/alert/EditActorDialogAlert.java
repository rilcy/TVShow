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
import devmobile.tvshow.db.adapter.CastingDataSource;
import devmobile.tvshow.db.object.Actor;

/**
 * Created by Elsio on 30.11.2015.
 */
public class EditActorDialogAlert extends DialogFragment {

    EditText actorFirstName;
    EditText actorLastName;
    private String castingFirstName;
    private String castingLastName;
    private int actorId ;



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        actorId = getArguments().getInt("passedActorId");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.activity_alert_edition_actor, null))

                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {


                        CastingDataSource castingds = new CastingDataSource(getActivity());
                        actorFirstName = (EditText) getDialog().findViewById(R.id.alert_actorEditTextFirstName);
                        actorLastName = (EditText) getDialog().findViewById(R.id.alert_actorEditTextLastName);
                        String firstname = actorFirstName.getText().toString();
                        String lastname = actorLastName.getText().toString();
                        if (firstname.length() > 0 && lastname.length() > 0) {
                            castingFirstName = firstname;
                            castingLastName = lastname;
                            castingds.updateActor(actorId, castingFirstName, castingLastName);
                        }
                        // si aucune valeur n'est rentrée, on retourne à l'activité et on affiche un toast
                        else {
                            String toast = getString(R.string.name_or_lastname_missing);
                            Toast.makeText(getActivity(), toast, Toast.LENGTH_LONG).show();
                        }

                        // retour à l'activité
                        refreshMyActivity();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        EditActorDialogAlert.this.getDialog().cancel();
                        // retour à l'activité
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
