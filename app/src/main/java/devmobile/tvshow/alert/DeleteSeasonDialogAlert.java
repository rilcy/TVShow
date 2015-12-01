package devmobile.tvshow.alert;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;

import java.util.ArrayList;

import devmobile.tvshow.R;
import devmobile.tvshow.activities.BySeason;
import devmobile.tvshow.activities.ByShow;
import devmobile.tvshow.db.adapter.CastingEpisodeDataSource;
import devmobile.tvshow.db.adapter.EpisodeDataSource;
import devmobile.tvshow.db.adapter.SeasonDataSource;
import devmobile.tvshow.db.object.CastingEpisode;
import devmobile.tvshow.db.object.Episode;
import devmobile.tvshow.db.object.Season;


public class DeleteSeasonDialogAlert extends DialogFragment {

    private int SeasonId;
    private int ShowId;
    private EpisodeDataSource episodeds;
    private ArrayList<Episode> listOfEpisodes;
    private CastingEpisodeDataSource castingds;
    private SeasonDataSource seasonds;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        SeasonId = getArguments().getInt("SEASONS_ID");
        ShowId = getArguments().getInt("SHOW_ID");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.activity_alert_delete_season, null))

                .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        // En cas de suppression d'une saison
                        // Suppression du casting pour chaque épisode
                        listOfEpisodes = new ArrayList<Episode>();
                        episodeds = new EpisodeDataSource(getActivity());
                        castingds = new CastingEpisodeDataSource(getActivity());
                        listOfEpisodes = (ArrayList) episodeds.getAllEpisodes(SeasonId);
                        if (listOfEpisodes != null && listOfEpisodes.size() > -1) {
                            for (int i = 0; i < listOfEpisodes.size(); i++) {
                                castingds.deleteAllCastingsForAnEpisode(listOfEpisodes.get(i).getEpisodeID());
                            }
                        }
                        // Suppression de chaque Episode pour une saison donnée
                        episodeds.deleteAllEpisodesBySeasonId(SeasonId);

                        // Suppression de la saison
                        seasonds = new SeasonDataSource(getActivity());
                        seasonds.deleteSeason(SeasonId);


                        // retour à l'activité ByShow
                        getActivity().finish();
                        Intent intent = new Intent(getActivity(), ByShow.class);
                        intent.putExtra("SHOW_ID", String.valueOf(ShowId));
                        startActivity(intent);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        DeleteSeasonDialogAlert.this.getDialog().cancel();
                        // retour à l'activité précédente
                    }
                });

        return builder.create();
    }
}
