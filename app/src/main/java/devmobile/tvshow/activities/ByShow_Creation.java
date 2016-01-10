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
import android.util.Pair;
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

        // Préférence de langage
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

    // Après sélection d'une image dans la gallery photo
    // affiche cette image
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK && null != data) {
                selectedImage = data.getData();
                imgView.setImageURI(selectedImage);
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                isPicture = true;
            }
            // en cas d'erreur un message est affiché
            else {
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


    // Méthode permettant de récupérer les valeurs entrée et de vérifier leur exactitude
    private void saveNewShow() {
        Show show = null;
        String imagePath;
        // Création d'un Show si les bonnes valeurs sont entrées
        // avec date de fin
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

        // Création d'un Show sans la date de fin
        else {
            imagePath = saveToInternalSorage(bitmap);
            show = new Show();
            show.setShowTitle(etShowName.getText().toString());
            show.setShowStart(etShowStart.getText().toString());
            show.setShowEnd("En production");
            show.setShowCompleted(0);
            show.setShowImage(imagePath);
            saveIntoDB(show);
            backToPreviousActivity();
        }
    }

    // Méthode permettant de sauver dans la DB nos valeurs pour le Show
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


    // En cas de click pour sauver la série
    public void onClickSave(View v) {
        // s'il y a au moins une image, un titre et une date de début on peut créer le show
        if (isPicture && etShowName.length() > 0 && etShowStart.length() > 3) {
            saveButton.setEnabled(false);
            saveNewShow();
        }
        // si une information est manquante on en informe l'utilisateur via un toast
        else{
            String toast = "\n";
            if(!isPicture)
                toast = toast + getString(R.string.image_missing) + "\n";
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

    // Si on souhaite afficher ou non le champs de la date de fin en clickant sur la checkbox
    public void onClickCheckbox(View view) {
        if (cbIsFinished.isChecked()) {
            etShowEnd.setVisibility(View.VISIBLE);
            tvShowEnd.setVisibility(View.VISIBLE);
        } else {
            // si on ne veut pas ajouter de date de fin on supprime la potentielle
            // valeur entrée par l'utilisateur.
            etShowEnd.setVisibility(View.GONE);
            tvShowEnd.setVisibility(View.GONE);
            etShowEnd.setText("");
        }
    }
    // en fin de processus on retourne au main.
    private void backToPreviousActivity() {
        new EndpointsAsyncTask().execute(new Pair<Context, String>(this, "Cyril, how are you ?"));
        Intent intent = new Intent(ByShow_Creation.this, MainActivity.class);
        ByShow_Creation.this.startActivity(intent);
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        menu.findItem(R.id.action_settings).setVisible(false);
        menu.findItem(R.id.action_addShow).setVisible(false);
        menu.findItem(R.id.action_byActor).setVisible(false);
        return true;
    }

    // Configuration du menu de l'action bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                Intent intent = new Intent(ByShow_Creation.this, BySeason.class);
                ByShow_Creation.this.startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

     // Méthode permettant la localisation du texte de l'activité
    public void changeLanguage(String lang){
        Locale myLocale = new Locale(lang);
        Locale.setDefault(myLocale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = myLocale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        getSupportActionBar().setTitle(R.string.title_activity_by_show_creation);

    }
}
