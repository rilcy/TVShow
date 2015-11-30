package devmobile.tvshow.alert;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.EditText;

import devmobile.tvshow.R;
import devmobile.tvshow.db.adapter.CastingDataSource;
import devmobile.tvshow.db.object.Actor;

/**
 * Created by Elsio on 30.11.2015.
 */
public class EditActorDialogAlert extends DialogFragment {

    EditText actorFirstName;
    EditText actorLastName;
    Actor actor;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.activity_alert_edition_actor, null))
                // Add action buttons
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        actorFirstName = (EditText) getDialog().findViewById(R.id.alert_actorEditTextFirstName);
                        String stringFirstName = actorFirstName.getText().toString();
                        actorLastName = (EditText) getDialog().findViewById(R.id.alert_actorEditTextLastName);
                        String stringLastName = actorLastName.getText().toString();
                        CastingDataSource castingds = new CastingDataSource(getActivity());
                        castingds.createCasting(stringFirstName, stringLastName);

                        Intent intent = getActivity().getIntent();
                        getActivity().finish();
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        EditActorDialogAlert.this.getDialog().cancel();
                    }
                });

        return builder.create();
    }
}
