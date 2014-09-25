package org.cosgix.ttmobileapp.activity;

import org.cosgix.ttmobileapp.R;

import yuku.ambilwarna.AmbilWarnaDialog;
import yuku.ambilwarna.AmbilWarnaDialog.OnAmbilWarnaListener;
import android.app.ActionBar;
import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

    public class FavoritesActivity extends Activity {

        private Button mShow;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_favorites);

            // Getting reference to the button
            mShow = (Button) findViewById(R.id.btn_show);
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
        
        /**
         * This method is used to display the action bar
         */
        private void showActionBar() {

            ActionBar actionBar = getActionBar();
            actionBar.setTitle(getResources().getString(R.string.addtoFavorites)); 
            actionBar.setIcon(R.drawable.ic_time);
            actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.lightskyblue)));

        }

        public void colorpicker() {
            //     initialColor is the initially-selected color to be shown in the rectangle on the left of the arrow.
            //     for example, 0xff000000 is black, 0xff0000ff is blue. Please be aware of the initial 0xff which is the alpha.

            AmbilWarnaDialog dialog = new AmbilWarnaDialog(this, 0xff0000ff, new OnAmbilWarnaListener() {

                // Executes, when user click Cancel button
                @Override
                public void onCancel(AmbilWarnaDialog dialog){
                }

                // Executes, when user click OK button
                @Override
                public void onOk(AmbilWarnaDialog dialog, int color) {
                    Toast.makeText(getBaseContext(), "Selected Color : " + color, Toast.LENGTH_LONG).show();
                }
            });
            dialog.show();
        }

     
    }



