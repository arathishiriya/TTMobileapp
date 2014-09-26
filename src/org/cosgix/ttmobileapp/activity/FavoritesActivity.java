package org.cosgix.ttmobileapp.activity;
/**
 * FavoritesActivity is responsible for allowing the user to add convenient name and choose a color from the picker for that particular favorite.
 * 
 * @author Shakunthala.M.K
 * 
 */
import org.cosgix.ttmobileapp.R;

import yuku.ambilwarna.AmbilWarnaDialog;
import yuku.ambilwarna.AmbilWarnaDialog.OnAmbilWarnaListener;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class FavoritesActivity extends Activity {

    private Button mShow, mSave;
    Context mContext;
    private EditText mNickName;
    private TextView mColorCodeValue;
    protected AlertDialog alertDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        // Getting reference to the button
        mShow = (Button) findViewById(R.id.btn_show);
        mSave = (Button) findViewById(R.id.btn_save);
        mNickName = (EditText) findViewById(R.id.editNickName);
        mColorCodeValue = (TextView) findViewById(R.id.colorCodeValue);

        showActionBar();
        // Defining click event listener for the button
        OnClickListener clickListener = new OnClickListener() {

            @Override
            public void onClick(View v) {
                colorpicker();
            }
        };

        // Setting click event listener for the button
        mShow.setOnClickListener(clickListener);

    }

    // Passing Nickname and Color Code values to another Activity.
    //For time being, it is sending to TimeEntryActivity.

    public void saveDetails(View v) {

        String nickName = mNickName.getText().toString();
        String colorCode = mColorCodeValue.getText().toString();
        Bundle sendFavouritesInfo = new Bundle();
        sendFavouritesInfo.putString("nickName", nickName);
        sendFavouritesInfo.putString("colorCode", colorCode);
        Intent sendToTimeEntryActivity = new Intent(FavoritesActivity.this, TimeEntryActivity.class);
        sendToTimeEntryActivity.putExtras(sendFavouritesInfo);
        startActivity(sendToTimeEntryActivity);
    }

    /**
     * This method is used to display the action bar
     */
    private void showActionBar() {

        ActionBar actionBar = getActionBar();
        actionBar.setTitle(getResources().getString(R.string.addtoFavorites));
        actionBar.setIcon(R.drawable.ic_time);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.lightskyblue)));

    }

    private void gotoTimeEntryActivity() {
        Intent startTimeEntryIntent = new Intent(FavoritesActivity.this, TimeEntryActivity.class);
        startActivity(startTimeEntryIntent);
    }

    public void colorpicker() {
        // initialColor is the initially-selected color to be shown in the rectangle on the left of the arrow.
        // for example, 0xff000000 is black, 0xff0000ff is blue. Please be aware of the initial 0xff which is the alpha.

        AmbilWarnaDialog dialog = new AmbilWarnaDialog(this, 0xff0000ff, new OnAmbilWarnaListener() {

            // Executes, when user click Cancel button
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {
            }

            // Executes, when user click OK button
            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                Toast.makeText(getBaseContext(), "Selected Color : " + color, Toast.LENGTH_LONG).show();
                mColorCodeValue.setText(color + "");
            }
        });
        dialog.show();
    }

    private void backBtnClickAlert() {
        // Toast.makeText(mContext,"Do you want quit?", Toast.LENGTH_LONG).show();
        AlertDialog.Builder backBtnClick = new AlertDialog.Builder(FavoritesActivity.this);
        backBtnClick.setTitle("");
        backBtnClick.setMessage("Do you really want to quit?").setCancelable(false)
                .setPositiveButton(R.string.Yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, just close the dialog box and go back to Time Entry Activity
                        dialog.dismiss();
                        gotoTimeEntryActivity();

                    }
                }).setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.dismiss();

                    }
                }).setOnKeyListener(new DialogInterface.OnKeyListener() {

                    @Override
                    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_BACK) {
                            dialog.dismiss();
                            finish();
                            return true;

                        }
                        return false;
                    }
                });

        // creates the alert dialog
        alertDialog = backBtnClick.create();
        alertDialog.show();
    }

    // CODE TO HANDLE CLICK ON BACK BUTTON - NEED TO ADD ALERT DIALOG FUNCTION.
    @Override
    public void onBackPressed() {

        backBtnClickAlert();
    }

}
