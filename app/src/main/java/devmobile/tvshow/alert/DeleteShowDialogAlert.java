package devmobile.tvshow.alert;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;

import java.util.ArrayList;

import devmobile.tvshow.R;
import devmobile.tvshow.activities.MainActivity;
import devmobile.tvshow.db.adapter.CastingEpisodeDataSource;
import devmobile.tvshow.db.adapter.EpisodeDataSource;
import devmobile.tvshow.db.adapter.SeasonDataSource;
import devmobile.tvshow.db.adapter.ShowDataSource;
import devmobile.tvshow.db.object.CastingEpisode;
import devmobile.tvshow.db.object.Episode;
import devmobile.tvshow.db.object.Season;
import devmobile.tvshow.db.object.Show;

/**
 * Created by rilcy on 31.10.15.
 */
public class DeleteShowDialogAlert extends DialogFragment {

    private int show_id;
    private Show show;
    private Season season;
    private ArrayList<Season> listOfSeasons;
    private ArrayList<Episode> listOfEpisodes;
    private ArrayList<CastingEpisode> listOfCastingEpisode;
    private ShowDataSource showds;
    private SeasonDataSource seasonds;
    private EpisodeDataSource episodeds;
    private CastingEpisodeDataSource castringEpds;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        show_id = getArguments().getInt("SHOW_ID");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.activity_alert_delete, null))

                .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        show = new Show();

                        showds = new ShowDataSource(getActivity());
                        listOfSeasons = new ArrayList<Season>();
                        seasonds = new SeasonDataSource(getActivity());
                        listOfEpisodes = new ArrayList<Episode>();
                        episodeds = new EpisodeDataSource(getActivity());
                        listOfCastingEpisode = new ArrayList<CastingEpisode>();
                        castringEpds = new CastingEpisodeDataSource(getActivity());

                        listOfSeasons = (ArrayList) seasonds.getAllSeasons(show_id);

                        for (int i = 0; i < listOfSeasons.size(); i++) {
                            int seasonId = listOfSeasons.get(i).getSeasonId();
                            listOfEpisodes = (ArrayList) episodeds.getAllEpisodes(seasonId);
                            for (int j = 0; j < listOfEpisodes.size(); j++) {
                                int episodeId = listOfEpisodes.get(j).getEpisodeID();
                                // Suppression du casting d'un épsiode d'une saison
                                castringEpds.deleteAllCastingsForAnEpisode(episodeId);
                            }
                            // Suppression des épisodes d'une saison
                            episodeds.deleteAllEpisodesBySeasonId(seasonId);
                        }
                        // Suppression des saisons
                        seasonds.deleteAllSeasonsByShowId(show_id);

                        // Suppression du show
                        showds.deleteShow(show_id);

                        // retour à l'activité MainActivity
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        DeleteShowDialogAlert.this.getDialog().cancel();
                        // retour à l'activité
                    }
                });

        return builder.create();
    }
}
