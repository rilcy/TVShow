package devmobile.tvshow.activities;

import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.tvshow.backend.actorApi.ActorApi;
import com.google.tvshow.backend.actorApi.model.Actor;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EndpointsAsyncTask extends AsyncTask<Void, Void, List<Actor>> {
    private static ActorApi actorApi = null;
    private static final String TAG = EndpointsAsyncTask.class.getName();
    private Actor actor;

    public EndpointsAsyncTask(){}

    public EndpointsAsyncTask(Actor actor){
        this.actor = actor;
    }

    @Override
    protected List<Actor> doInBackground(Void... params) {
        if(actorApi == null) {  // Only do this once
            /*
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    //.setRootUrl("https://tvshow-1186.appspot.com/")
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
                    */
            // end options for devappserver

            ActorApi.Builder builder = new ActorApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl("https://tvshowz-1186.appspot.com/_ah/api/");
            actorApi = builder.build();
        }

        try{
            // Call here the wished methods on the Endpoints
            // For instance insert
            if(actor != null){
                actorApi.insert(actor).execute();
                Log.i(TAG, "insert actor");
            }
            // and for instance return the list of all actors
            return actorApi.list().execute().getItems();

        } catch (IOException e){
            Log.e(TAG, e.toString());
            return new ArrayList<Actor>();
        }
    }

    @Override
    protected void onPostExecute(List<Actor> result) {
        if(result != null) {
            for (Actor actor : result) {
                Log.i(TAG, "First name: " + actor.getFirstName() + " Last name: "
                        + actor.getLastName());
            }
        }
    }
}