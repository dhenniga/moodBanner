package com.moodbanner.dev.any.Create;

import android.graphics.Color;
import android.widget.TextView;

import com.moodbanner.dev.any.R;

/**
 * Created by dhen0003 on 06/04/16.
 */
public class SeekBarController {

    public void seekBarMethod(android.widget.SeekBar seekBar, final TextView txtCounter, final TextView txtMainText) {

        seekBar.setOnSeekBarChangeListener(new android.widget.SeekBar.OnSeekBarChangeListener() {

            int progress = 0;

            @Override
            public void onProgressChanged(android.widget.SeekBar seekBar, int progressValue, boolean fromUser) {

                progress = progressValue;
                txtMainText.setTextSize(progress);
                txtCounter.setText((String.valueOf(progress - 20)));
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
