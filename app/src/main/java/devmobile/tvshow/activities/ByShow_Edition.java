package devmobile.tvshow.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.util.Locale;

import devmobile.tvshow.R;
import devmobile.tvshow.db.adapter.ShowDataSource;
import devmobile.tvshow.db.object.Show;

public class ByShow_Edition extends AppCompatActivity {
    private ImageView imgView;
    private EditText etShowName;
    private EditText etShowStart;
    private EditText etShowEnd;
    private CheckBox cbIsFinished;
    private TextView tvShowEnd;
    private Button saveButton;
    private Button cancelButton;
    private Button loadButton;
    private ShowDataSource showds;
    private Show show;
    long Show_Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_by_show_edition);

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        changeLanguage(sharedPrefs.getString("pref_lang", "en"));

        Intent intent = getIntent();
        String dataTransfered = intent.getStringExtra("SHOWID");
        Show_Id = Long.parseLong(dataTransfered);

        cbIsFinished = (CheckBox) findViewById(R.id.cbiSFinished);
        etShowName = (EditText) findViewById(R.id.etShowName);
        etShowStart = (EditText) findViewById(R.id.etShowStart);
        etShowEnd = (EditText) findViewById(R.id.etShowEnd);
        tvShowEnd = (TextView) findViewById(R.id.showEnd);

        // Button and imageView button
        imgView = (ImageView) findViewById(R.id.imgView);
        saveButton = (Button) findViewById(R.id.buttonOk);
        cancelButton = (Button) findViewById(R.id.buttonCancel);

        etShowEnd.setVisibility(View.GONE);
        tvShowEnd.setVisibility(View.GONE);


        showds = new ShowDataSource(this);
        show = showds.getShowById(Show_Id);

        etShowName.setText(show.getShowTitle());
        etShowStart.setText(show.getShowStart());

        if (show.getShowEnd().startsWith("En")){
            etShowEnd.setText("");
        }
        else{
            etShowEnd.setText(show.getShowEnd());
            cbIsFinished.setChecked(true);
            etShowEnd.setVisibility(View.VISIBLE);
            tvShowEnd.setVisibility(View.VISIBLE);
        }

        File imgFile = new File(show.getShowImage());

        if (imgFile.exists()) {
            try {
                File f = new File(imgFile.getPath());
                Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
                Uri uri = Uri.fromFile(imgFile);
                imgView.setImageURI(uri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }


    private void setupActionBar() {

        getActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        menu.findItem(R.id.action_settings).setVisible(false);
        menu.findItem(R.id.action_addShow).setVisible(false);
        menu.findItem(R.id.action_byActor).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                Intent intent = new Intent(ByShow_Edition.this, BySeason.class);
                ByShow_Edition.this.startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    // NEXT METHODS HELP US TO SAVE INTO THE APP THE IMAGE AND INTO THE DB THE SHOW
    private void saveUpdatedShow() {
        // Create show if we have enter all the required information
        if (cbIsFinished.isChecked() && etShowEnd.length() > 3) {
            show = new Show();
            show.setShowTitle(etShowName.getText().toString());
            show.setShowStart(etShowStart.getText().toString());
            show.setShowEnd(etShowEnd.getText().toString());
            show.setShowId((int) Show_Id);
            updateIntoDB(show);
            backToPreviousActivity();
        }

        // Create show if we only set Image, name of the show and starts date
        else {
            show = new Show();
            Show showToUpdate = show;
            showToUpdate.setShowTitle(etShowName.getText().toString());
            showToUpdate.setShowStart(etShowStart.getText().toString());
            showToUpdate.setShowEnd("En production");
            showToUpdate.setShowId((int) Show_Id);
            updateIntoDB(showToUpdate);
            backToPreviousActivity();
        }
    }

    private void updateIntoDB(Show showToUpdate) {
        if (showToUpdate != null) {
            ShowDataSource sds = new ShowDataSource(this);
            sds.updateInfoShow(showToUpdate);
        }
        return;
    }


    // NEXT METHODS ARE THE onClick METHODS
    public void onClickSave(View v) {
        if (etShowName.length() > 0 && etShowStart.length() > 3) {
            saveButton.setEnabled(false);
            saveUpdatedShow();
        }
        else{
            String toast = "\n";
            if(etShowName.length() == 0)
                toast = toast + getString(R.string.title_missing) + "\n";
            if(etShowStart.length() < 4)
                toast = toast + getString(R.string.showStart_missing) + "\n";
            if(etShowEnd.length() < 4 && cbIsFinished.isChecked())
                toast = toast + getString(R.string.showEnd_missing) + "\n";

            Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickCancel(View v) {
        backToPreviousActivity();
    }

    public void onClickCheckbox(View view) {
        if (cbIsFinished.isChecked()) {
            etShowEnd.setVisibility(View.VISIBLE);
            tvShowEnd.setVisibility(View.VISIBLE);
        } else {
            etShowEnd.setVisibility(View.GONE);
            tvShowEnd.setVisibility(View.GONE);
            etShowEnd.setText("");
        }
    }

    private void backToPreviousActivity() {
        Intent intent = new Intent(ByShow_Edition.this, MainActivity.class);
        ByShow_Edition.this.startActivity(intent);
        finish();

    }

    /**
     * Méthode permettant la localisation du texte de l'activité
     */
    public void changeLanguage(String lang) {
        Locale myLocale = new Locale(lang);
        Locale.setDefault(myLocale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = myLocale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        getSupportActionBar().setTitle(R.string.title_activity_by_show_edition);
        /*
        Button buttonLoadPicture = (Button) findViewById(R.id.buttonLoadPicture);
        buttonLoadPicture.setText(R.string.buttonLoadPicture);
        TextView showName = (TextView) findViewById(R.id.showName);
        showName.setText(R.string.ShowName);
        TextView showStart = (TextView) findViewById(R.id.showStart);
        showStart.setText(R.string.ShowStart);
        CheckBox cbIsFinished = (CheckBox) findViewById(R.id.cbiSFinished);
        cbIsFinished.setText(R.string.cbisFinished);
        TextView showEnd = (TextView) findViewById(R.id.showEnd);
        showEnd.setText(R.string.ShowEnd);
        Button buttonOk = (Button) findViewById(R.id.buttonOk);
        buttonOk.setText(R.string.buttonOk);
        Button buttonCancel = (Button) findViewById(R.id.buttonCancel);
        buttonCancel.setText(R.string.buttonCancel);
        */
    }
}