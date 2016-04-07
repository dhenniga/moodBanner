package com.moodbanner.dev.any.Create;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.moodbanner.dev.any.R;

/**
 * Created by dhen0003 on 06/04/16.
 */
public class SeekBarController extends AppCompatActivity {

    public void seekBarMethod(android.widget.SeekBar seekBar, final TextView txtMainText) {


        seekBar.setOnSeekBarChangeListener(new android.widget.SeekBar.OnSeekBarChangeListener() {

            int progress = 0;

            @Override
            public void onProgressChanged(android.widget.SeekBar seekBar, int progressValue, boolean fromUser) {


                switch (seekBar.getId()) {

                    case R.id.sbHeight:

                        txtMainText.setLineSpacing((progressValue), 0.4f);
                        break;

                    case R.id.sbWidth:

                        txtMainText.setWidth(progressValue);
                        break;

                    case R.id.sbSize:

                        txtMainText.setTextSize(progressValue);
                        break;

                    case R.id.sbAlpha:

                        txtMainText.setAlpha((float)progressValue / 100);
                        break;

                    default:
                        break;
                }

            }

            @Override
            public void onStartTrackingTouch(android.widget.SeekBar seekBar) {
                txtMainText.setBackgroundColor(Color.parseColor("#22000000"));
            }

            @Override
            public void onStopTrackingTouch(android.widget.SeekBar seekBar) {
                txtMainText.setBackgroundColor(Color.parseColor("#00000000"));
            }

        });

    }

}
