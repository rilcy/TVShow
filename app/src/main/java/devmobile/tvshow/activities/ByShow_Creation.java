package devmobile.tvshow.activities;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
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
import java.io.FileOutputStream;
import java.util.Locale;

import devmobile.tvshow.R;
import devmobile.tvshow.db.SQLiteHelper;
import devmobile.tvshow.db.adapter.ShowDataSource;
import devmobile.tvshow.db.object.Show;

public class ByShow_Creation extends AppCompatActivity {
    private ImageView imgView;
    private static final int RESULT_LOAD_IMG = 1;
    private EditText etShowName;
    private EditText etShowStart;
    private EditText etShowEnd;
    private CheckBox cbIsFinished;
    private TextView tvShowEnd;
    private Button saveButton;
    private Button cancelButton;
    private Button loadButton;
    private Uri selectedImage;
    private Bitmap bitmap;
    private boolean isPicture = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_by_show_creation);

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        changeLanguage(sharedPrefs.getString("pref_lang", "en"));

        cbIsFinished = (CheckBox) findViewById(R.id.cbiSFinished);
        etShowName = (EditText) findViewById(R.id.etShowName);
        etShowStart = (EditText) findViewById(R.id.etShowStart);
        etShowEnd = (EditText) findViewById(R.id.etShowEnd);
        tvShowEnd = (TextView) findViewById(R.id.showEnd);

        // Button and imageView button
        imgView = (ImageView) findViewById(R.id.imgView);
        saveButton = (Button) findViewById(R.id.buttonOk);
        cancelButton = (Button) findViewById(R.id.buttonCancel);
        loadButton = (Button) findViewById(R.id.buttonLoadPicture);

        etShowEnd.setVisibility(View.GONE);
        tvShowEnd.setVisibility(View.GONE);

    }

    // NEXT METHODS HELP US TO PICK AN IMAGE FROM THE DB
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK && null != data) {
                selectedImage = data.getData();
                imgView.setImageURI(selectedImage);
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                isPicture = true;
            } else {
                Toast.makeText(this, "You haven't picked Image", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
        }
    }

    public void onClickUpload(View v) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(intent, RESULT_LOAD_IMG);
    }

    private void setupActionBar() {

        getActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                Intent intent = new Intent(ByShow_Creation.this, BySeason.class);
                ByShow_Creation.this.startActivity(intent);
                break;

            case R.id.action_byActor:

                intent = new Intent(ByShow_Creation.this, ActorByEpisode.class);
                ByShow_Creation.this.startActivity(intent);
                break;

            case R.id.action_addShow:

                intent = new Intent(ByShow_Creation.this, ByShow_Creation.class);
                ByShow_Creation.this.startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    // NEXT METHODS HELP US TO SAVE INTO THE APP THE IMAGE AND INTO THE DB THE SHOW
    private void saveNewShow() {
        Show show = null;
        String imagePath;
        // Create show if we have enter all the required information
        if (cbIsFinished.isChecked() && etShowEnd.length() > 3) {
            imagePath = saveToInternalSorage(bitmap);
            show = new Show();
            show.setShowTitle(etShowName.getText().toString());
            show.setShowStart(etShowStart.getText().toString());
            show.setShowEnd(etShowEnd.getText().toString());
            show.setShowCompleted(0);
            show.setShowImage(imagePath);
            saveIntoDB(show);
            backToPreviousActivity();
        }

        // Create show if we only set Image, name of the show and starts date
        else {
            imagePath = saveToInternalSorage(bitmap);
            show = new Show();
            show.setShowTitle(etShowName.getText().toString());
            show.setShowStart(etShowStart.getText().toString());
            // TODO  Plutôt entrer une valeur vide "" et gérer à l'affichage si vide. plus simple pour la traduction, sinon la donnée est entrée en dur
            show.setShowEnd("En production");
            show.setShowCompleted(0);
            show.setShowImage(imagePath);
            saveIntoDB(show);
            backToPreviousActivity();
        }

    }

    private void saveIntoDB(Show show) {
        if (show != null) {
            ShowDataSource sds = new ShowDataSource(this);
            sds.createShow(show);
            SQLiteHelper sqlHelper = SQLiteHelper.getInstance(this);
            sqlHelper.getWritableDatabase().close();
        }
        return;
    }

    private String saveToInternalSorage(Bitmap bitmaImg) {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());

        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);

        // Create a random number to add to image path to make a picture unique
        int random = 10 + (int) (Math.random() * 5000);

        // Create imageDir
        File mypath = new File(directory, "show" + random + ".jpg");

        // Saving image in the application
        FileOutputStream fos = null;
        try {

            fos = new FileOutputStream(mypath);

            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmaImg.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mypath.getPath();
    }


    // NEXT METHODS ARE THE onClick METHODS
    public void onClickSave(View v) {
        if (isPicture && etShowName.length() > 0 && etShowStart.length() > 3) {
            saveButton.setFocusableInTouchMode(false);
            saveNewShow();
        }
    }

    public void onClickCancel(View v) {
        Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
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
        Intent intent = new Intent(ByShow_Creation.this, MainActivity.class);
        ByShow_Creation.this.startActivity(intent);
        finish();
    }

    /**
     * Méthode permettant la localisation du texte de l'activité
     */
    public void changeLanguage(String lang){
        Locale myLocale = new Locale(lang);
        Locale.setDefault(myLocale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = myLocale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        getSupportActionBar().setTitle(R.string.title_activity_by_show_creation);
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
    }
}
