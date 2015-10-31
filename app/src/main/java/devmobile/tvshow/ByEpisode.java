package devmobile.tvshow;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class ByEpisode extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_by_episode);

        LinearLayout llayout_addActor = (LinearLayout) findViewById (R.id.linearLayout_addActor);
        llayout_addActor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent appInfo = new Intent(ByEpisode.this, ByActor.class);
                startActivity(appInfo);
            }
        });
    }
}