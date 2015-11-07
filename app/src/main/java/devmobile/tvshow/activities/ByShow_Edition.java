package devmobile.tvshow.activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import devmobile.tvshow.R;

public class ByShow_Edition extends AppCompatActivity {

    private ImageView imgView;

    private String current = "";
    private String yyyy = "YYYY";
    private Calendar cal = Calendar.getInstance();

    private static final int RESULT_LOAD_IMG = 1;

    private EditText etShowName;
    private EditText etShowStart;
    private EditText etShowEnd ;
    private CheckBox cbIsFinished ;
    private TextView tvShowEnd;
    private GridLayout glShowEditCreat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_by_show_edition);

        cbIsFinished = (CheckBox) findViewById(R.id.cbiSFinished);
        etShowName = (EditText) findViewById(R.id.etShowName);
        etShowStart = (EditText) findViewById(R.id.etShowStart);
        etShowEnd = (EditText) findViewById(R.id.etShowEnd);
        tvShowEnd = (TextView) findViewById(R.id.showEnd);
        glShowEditCreat = (GridLayout) findViewById(R.id.glShowEditCreat);

        etShowEnd.setVisibility(View.GONE);
        tvShowEnd.setVisibility(View.GONE);

        //etShowEnd.setFocusable(false);

    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pick up a picture from the picture's gallery
        //http://programmerguru.com/android-tutorial/how-to-pick-image-from-gallery/
        try {
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {

                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
                imgView = (ImageView) findViewById(R.id.imgView);

                imgView.setImageBitmap(BitmapFactory
                        .decodeFile(imgDecodableString));

            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }
    }



            public void onClickSelectImg(View view) {
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
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

                Intent intent = new Intent(ByShow_Edition.this, BySeason.class);
                ByShow_Edition.this.startActivity(intent);
                break;

            case R.id.action_byActor:

                intent = new Intent(ByShow_Edition.this, ByActor.class);
                ByShow_Edition.this.startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClickCancel(View view) {
        Intent intent = new Intent(ByShow_Edition.this, ByShow.class);
        ByShow_Edition.this.startActivity(intent);
        finish();
    }

    public void onClickOk(View view) {
        /*
        Enregistrer l'image et les modifications
        TESTE SI DES VALEURS ONT ETE ENTREES
        L'IMAGE N'EST PAS TESTEE
        */

        if(etShowName.length() > 0 && etShowStart.length() > 3  && !cbIsFinished.isChecked())
            backToPreviousActivity();

        if(etShowName.length() > 0 && etShowStart.length() > 3 && cbIsFinished.isChecked() && etShowEnd.length() > 3)
            backToPreviousActivity();
        else{
            sendToast();
        }





    }

    private void sendToast() {
        String toast = "Qqch est manquant !";


        Toast.makeText(ByShow_Edition.this, toast, Toast.LENGTH_SHORT).show();
    }

    private void backToPreviousActivity() {
        Intent intent = new Intent(ByShow_Edition.this, ByShow.class);
        ByShow_Edition.this.startActivity(intent);
        finish();
    }

    public void onClickCheckbox(View view) {

        if (cbIsFinished.isChecked()) {
            //
            etShowEnd.setVisibility(View.VISIBLE);
            tvShowEnd.setVisibility(View.VISIBLE);
        } else {
            //
            etShowEnd.setVisibility(View.GONE);
            tvShowEnd.setVisibility(View.GONE);
            etShowEnd.setText("");
        }

    }


}
