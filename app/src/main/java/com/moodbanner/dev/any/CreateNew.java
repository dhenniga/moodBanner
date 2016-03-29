package com.moodbanner.dev.any;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringSystem;
import com.moodbanner.dev.any.Fonts.AdapterFont;
import com.moodbanner.dev.any.Fonts.AdapterFontColour;
import com.moodbanner.dev.any.Fonts.FontColourList;
import com.moodbanner.dev.any.Fonts.FontList;
import com.moodbanner.dev.any.Fonts.ValueFont;
import com.moodbanner.dev.any.Fonts.ValueFontColour;
import com.moodbanner.dev.any.JSON.JSONHelper;
import com.moodbanner.dev.any.JSON.JSONParserBackgrounds;
import com.moodbanner.dev.any.JSON.JSONParserFonts;
import com.moodbanner.dev.any.backgrounds.AdapterBackground;
import com.moodbanner.dev.any.backgrounds.ValueBackground;
import com.moodbanner.dev.any.listener.RecyclerClickListener;
import com.moodbanner.dev.any.listener.RecyclerTouchListener;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.List;

public class CreateNew extends AppCompatActivity {

    private static double TENSION = 500;
    private static double DAMPER = 10;

    private TextView txtMainText;
    private ImageView createnew_background_image, ivFontThumb, ivColour;
    private EditText txtInput;
    private Button  btnMoveTextUp, btnMoveTextDown;
    private ToggleButton tbtnFont, tbtnBackground, btnBorder, btnOverlay, btnShare, tbtnShowHideUI, tbtnShadow;
    private Typeface RalewayMedium, RalewayBold, RalewayLight;
    private RelativeLayout mRelativeLayout, main_container;

    private AppCompatActivity activity = CreateNew.this;
    private RecyclerView mRecyclerViewBackground, mRecyclerViewFonts, mRecyclerViewFontColours;
    private List<ValueBackground> ListBackgrounds;
    private List<ValueFont> ListFonts;
    private List<ValueFontColour> ListFontColours;
    private Context mContext;

    private ScaleGestureDetector scaleGestureDetector;

    SeekBar seekBar;


    int fontSize, fontPosition;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new);


        scaleGestureDetector = new ScaleGestureDetector(this, new simpleOnScaleGestureListener());


        RalewayLight = Typeface.createFromAsset(getAssets(), "interface-fonts/Raleway-Thin.ttf");
        RalewayMedium = Typeface.createFromAsset(getAssets(), "interface-fonts/Raleway-Medium.ttf");
        RalewayBold = Typeface.createFromAsset(getAssets(), "interface-fonts/Raleway-Bold.ttf");

        txtMainText = (TextView) findViewById(R.id.txtMainText);
        txtMainText.setLineSpacing((200), 0.4f);

        txtInput = (EditText) findViewById(R.id.etInput);


        tbtnFont = (ToggleButton) findViewById(R.id.tbtnFont);
        tbtnFont.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.slide_up_in));

        tbtnBackground = (ToggleButton) findViewById(R.id.tbtnBackground);
        tbtnBackground.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.slide_up_in));

        btnOverlay = (ToggleButton) findViewById(R.id.btnOverlay);
        btnOverlay.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.slide_up_in));

        btnBorder = (ToggleButton) findViewById(R.id.btnBorder);
        btnBorder.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.slide_up_in));

        btnShare = (ToggleButton) findViewById(R.id.btnShare);
        btnShare.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.slide_up_in));

        tbtnShowHideUI = (ToggleButton) findViewById(R.id.tbtnShowHideUI);
        tbtnShowHideUI.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.slide_left_in));

        txtInput.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.slide_down_in));

        btnMoveTextUp = (Button) findViewById(R.id.btnMoveTextUp);
        btnMoveTextDown = (Button) findViewById(R.id.btnMoveTextDown);

        tbtnShadow = (ToggleButton) findViewById(R.id.tbtnShadow);


        mRelativeLayout = (RelativeLayout) findViewById(R.id.create_new_container);
        main_container = (RelativeLayout) findViewById(R.id.main_container);

        seekBar = (SeekBar) findViewById(R.id.seekBar1);


        tbtnFont.setTypeface(RalewayMedium);
        tbtnBackground.setTypeface(RalewayMedium);
        btnBorder.setTypeface(RalewayMedium);
        btnOverlay.setTypeface(RalewayMedium);
        btnShare.setTypeface(RalewayMedium);
        tbtnShowHideUI.setTypeface(RalewayMedium);

        txtInput.setTypeface(RalewayLight);

//        Shader shader = new LinearGradient(0, 0, 0, txtMainText.getTextSize(), Color.RED, Color.BLUE, Shader.TileMode.MIRROR);
//        txtMainText.getPaint().setShader(shader);
//        txtMainText.setShadowLayer(20, 15, 15, Color.parseColor("#44000000"));

        txtMainText.setTypeface(RalewayLight);


        /**
         *  Setup the screen buttons.  These will be replaced with a gesture touch system.
         */
        btnMoveTextUp.setOnClickListener(listener);
        btnMoveTextDown.setOnClickListener(listener);
        tbtnBackground.setOnClickListener(listener);
        tbtnFont.setOnClickListener(listener);
        tbtnShowHideUI.setOnClickListener(listener);
        tbtnShadow.setOnClickListener(listener);



        /**
         * Changing the font of the main text to something.
         */
        txtInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                txtMainText.setText(txtInput.getText());

            }
        });


        BackgroundGallery();
        FontGallery();
        FontColourGallery();


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            int progress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                progress = progresValue;
                txtMainText.setLineSpacing((progress), 0.4f);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}

        });






}


    /**
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        scaleGestureDetector.onTouchEvent(event);
        return true;
    }


    public class simpleOnScaleGestureListener extends
            ScaleGestureDetector.SimpleOnScaleGestureListener {

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            // TODO Auto-generated method stub
            float size = txtMainText.getTextSize();
            Log.d("TextSizeStart", String.valueOf(size));

            float factor = detector.getScaleFactor();
            Log.d("Factor", String.valueOf(factor));


            float product = size*factor;
            Log.d("TextSize", String.valueOf(product));
            txtMainText.setTextSize(TypedValue.COMPLEX_UNIT_PX, product);

            size = txtMainText.getTextSize();
            Log.d("TextSizeEnd", String.valueOf(size));
            fontSize = (int)txtMainText.getTextSize();
            return true;
        }
    }



    /**
     * Controls the display of the background gallery popout.
     */
    public void BackgroundGallery() {

        mRecyclerViewBackground = (RecyclerView) findViewById(R.id.recyclerViewBackgrounds);
        LinearLayoutManager mLayoutManagerBackground = new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerViewBackground.setLayoutManager(mLayoutManagerBackground);

        mRecyclerViewBackground.addOnItemTouchListener(new RecyclerTouchListener(activity, mRecyclerViewBackground, new RecyclerClickListener() {

            @Override
            public void onClick(View view, int position) {

                if (ListBackgrounds != null) {

                    Log.i("Background: ", ListBackgrounds.get(position).getName());

                    Picasso.with(mContext).load(ListBackgrounds.get(position).getImageURL()).into(createnew_background_image);
                    createnew_background_image.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.zoom_out));

                }
            }
        }));
    }



    /**
     * Controls the display of the background gallery popout.
     */
    public void FontGallery() {

        mRecyclerViewFonts = (RecyclerView) findViewById(R.id.recyclerViewFonts);
        LinearLayoutManager mLayoutManagerFont = new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerViewFonts.setLayoutManager(mLayoutManagerFont);

        mRecyclerViewFonts.addOnItemTouchListener(new RecyclerTouchListener(activity, mRecyclerViewFonts, new RecyclerClickListener() {

            @Override
            public void onClick(View view, int position) {

                Log.i("Font Name", ListFonts.get(position).getName());

                Typeface font = Typeface.createFromAsset(getAssets(), ListFonts.get(position).getFontFile());
                txtMainText.setTypeface(font);

                SpringyTextAnimation(txtMainText);
            }

        }));
    }


    /**
     *  Setup for the Font Colour selector horizontal menu
     */
    public void FontColourGallery() {

        mRecyclerViewFontColours = (RecyclerView) findViewById(R.id.recyclerViewFontColours);
        LinearLayoutManager mLayoutManagerFont = new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerViewFontColours.setLayoutManager(mLayoutManagerFont);

        mRecyclerViewFontColours.addOnItemTouchListener(new RecyclerTouchListener(activity, mRecyclerViewFontColours, new RecyclerClickListener() {

            @Override
            public void onClick(View view, int position) {

                txtMainText.setTextColor(Color.parseColor(ListFontColours.get(position).getColourHexCode()));
                SpringyTextAnimation(txtMainText);

            }

        }));
    }

    /**
     *
     * Spring Animation for TextView objects
     *
     * @param textObject
     */
    public void SpringyTextAnimation(final TextView textView) {

        SpringSystem springSystem = SpringSystem.create();
        Spring spring = springSystem.createSpring();
        SpringConfig config = new SpringConfig(TENSION, DAMPER);
        spring.setSpringConfig(config);

        spring.addListener(new SimpleSpringListener() {

            @Override
            public void onSpringUpdate(Spring spring) {
                float value = (float) spring.getCurrentValue();
                float scale = 0.8f - (value * -0.1f);
                textView.setScaleX(scale);
                textView.setScaleY(scale);
            }
        });

        spring.setEndValue(1);
    }



    /**
     * Button Actions for the text controls.  Will be changed soon.
     */
    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.btnMoveTextUp:
                    Log.i("Button Press", "Move text UP");
                    txtMainText.setY(txtMainText.getY() - 40f);
                    fontPosition = (int) txtMainText.getY();
                    break;


                case R.id.btnMoveTextDown:
                    Log.i("Button Press", "Move text DOWN");
                    txtMainText.setY(txtMainText.getY() + 40f);
                    fontPosition = (int) txtMainText.getY();
                    break;

                case R.id.tbtnShadow:

                    if (tbtnShadow.isChecked()) {
                        txtMainText.setShadowLayer(20, 15, 15, Color.parseColor("#44000000"));
                    } else {
                        txtMainText.setShadowLayer(0, 0, 0, 0);
                    }
                    break;


                case R.id.tbtnShowHideUI:

                if (tbtnShowHideUI.isChecked()) {

                    Log.i("Button Press", "UI - Hide");


                    tbtnShowHideUI.setAlpha(0.2f);

                    tbtnFont.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.slide_down_out));
                    tbtnFont.setVisibility(View.GONE);
                    tbtnBackground.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.slide_down_out));
                    tbtnBackground.setVisibility(View.GONE);
                    btnOverlay.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.slide_down_out));
                    btnOverlay.setVisibility(View.GONE);
                    btnBorder.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.slide_down_out));
                    btnBorder.setVisibility(View.GONE);
                    btnShare.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.slide_down_out));
                    btnShare.setVisibility(View.GONE);

                    txtInput.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.slide_up_out));
                    txtInput.setVisibility(View.INVISIBLE);
                    btnMoveTextUp.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.slide_left_out));
                    btnMoveTextUp.setVisibility(View.GONE);
                    btnMoveTextDown.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.slide_left_out));
                    btnMoveTextDown.setVisibility(View.GONE);
                    tbtnShadow.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.slide_left_out));
                    tbtnShadow.setVisibility(View.GONE);

                    if (mRecyclerViewFonts.getVisibility() == View.VISIBLE) {
                        mRecyclerViewFonts.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.fadeout));
                        mRecyclerViewFonts.setVisibility(View.GONE);
                        tbtnFont.setChecked(false);
                        tbtnFont.setTextColor(getResources().getColor(R.color.white_transparent_80));
                        tbtnFont.setBackgroundResource(R.drawable.text_input_background);
                        tbtnFont.setTypeface(RalewayMedium);
                    }

                    if (mRecyclerViewFontColours.getVisibility() == View.VISIBLE){
                        mRecyclerViewFontColours.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.fadeout));
                        mRecyclerViewFontColours.setVisibility(View.GONE);
                        tbtnFont.setChecked(false);
                        tbtnFont.setTextColor(getResources().getColor(R.color.white_transparent_80));
                        tbtnFont.setBackgroundResource(R.drawable.text_input_background);
                        tbtnFont.setTypeface(RalewayMedium);
                    }

                    if (mRecyclerViewBackground.getVisibility() == View.VISIBLE) {
                        mRecyclerViewBackground.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.fadeout));
                        mRecyclerViewBackground.setVisibility(View.GONE);
                        tbtnBackground.setChecked(false);
                        tbtnBackground.setTextColor(getResources().getColor(R.color.white_transparent_80));
                        tbtnBackground.setBackgroundResource(R.drawable.text_input_background);
                        tbtnBackground.setTypeface(RalewayMedium);

                    }


                } else {

                    Log.i("Button Press", "UI - Show");

                    tbtnShowHideUI.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.ui_fadein));
                    tbtnShowHideUI.setAlpha(1);

                    tbtnFont.setVisibility(View.VISIBLE);
                    tbtnFont.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.slide_up_in));
                    tbtnBackground.setVisibility(View.VISIBLE);
                    tbtnBackground.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.slide_up_in));
                    btnOverlay.setVisibility(View.VISIBLE);
                    btnOverlay.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.slide_up_in));
                    btnBorder.setVisibility(View.VISIBLE);
                    btnBorder.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.slide_up_in));
                    btnShare.setVisibility(View.VISIBLE);
                    btnShare.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.slide_up_in));

                    txtInput.setVisibility(View.VISIBLE);
                    txtInput.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.slide_down_in));
                    btnMoveTextUp.setVisibility(View.VISIBLE);
                    btnMoveTextUp.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.slide_right_in));
                    btnMoveTextDown.setVisibility(View.VISIBLE);
                    btnMoveTextDown.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.slide_right_in));
                    tbtnShadow.setVisibility(View.VISIBLE);
                    tbtnShadow.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.slide_right_in));



                }
                break;



                case R.id.tbtnBackground:

                    mRecyclerViewBackground = (RecyclerView) findViewById(R.id.recyclerViewBackgrounds);
                    createnew_background_image = (ImageView) findViewById(R.id.createnew_background_image);
                    LinearLayoutManager llmBackground = new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);


                    if (tbtnBackground.isChecked()) {

                        Log.i("Menu Open", "Backgrounds");

                        new JSONAsyncBackgrounds().execute();


                        /** If the fonts menu is visible, remove it  **/
                        if (mRecyclerViewFonts.getVisibility() == View.VISIBLE) {
                            mRecyclerViewFonts.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.fadeout));
                            mRecyclerViewFonts.setVisibility(View.GONE);
                            tbtnFont.setChecked(false);
                            tbtnFont.setTypeface(RalewayMedium);
                            tbtnFont.setTextColor(getResources().getColor(R.color.white_transparent_80));
                            tbtnFont.setBackgroundResource(R.drawable.text_input_background);
                        }

                        if (mRecyclerViewFontColours.getVisibility() == View.VISIBLE){
                            mRecyclerViewFontColours.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.fadeout));
                            mRecyclerViewFontColours.setVisibility(View.GONE);
                            tbtnFont.setChecked(false);
                            tbtnFont.setTypeface(RalewayMedium);
                            tbtnFont.setTextColor(getResources().getColor(R.color.white_transparent_80));
                            tbtnFont.setBackgroundResource(R.drawable.text_input_background);
                        }


                        mRecyclerViewBackground.setLayoutManager(llmBackground);
                        mRecyclerViewBackground.setVisibility(View.VISIBLE);
                        mRecyclerViewBackground.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.fadein));


                        WindowManager w = getWindowManager();
                        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(w.getDefaultDisplay().getWidth(), 520);
                        params.topMargin = (w.getDefaultDisplay().getHeight() - 495);
                        tbtnBackground.setTypeface(RalewayBold);
                        tbtnBackground.setTextColor(getResources().getColor(R.color.white));
                        tbtnBackground.setBackgroundColor(getResources().getColor(R.color.black));

                    } else {

                        Log.i("Menu Close", "Backgrounds");
                        mRecyclerViewBackground.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.fadeout));
                        mRecyclerViewBackground.setVisibility(View.GONE);
                        tbtnBackground.setTypeface(RalewayMedium);
                        tbtnBackground.setTextColor(getResources().getColor(R.color.white_transparent_80));
                        tbtnBackground.setBackgroundResource(R.drawable.text_input_background);
                    }

                    break;


                case R.id.tbtnFont:


                    mRecyclerViewFonts = (RecyclerView) findViewById(R.id.recyclerViewFonts);
                    ivFontThumb = (ImageView) findViewById(R.id.ivFontThumb);
                    LinearLayoutManager llmFont = new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);


                    mRecyclerViewFontColours = (RecyclerView) findViewById(R.id.recyclerViewFontColours);
                    ivColour = (ImageView) findViewById(R.id.ivColour);
                    LinearLayoutManager llmFontColour = new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);

                    if (tbtnFont.isChecked()) {

                        Log.i("Menu Open", "Fonts");

                        /**  Populate the contents of the RecyclerView with the Assets Folder fonts and thumbnail images from the adapter
                         */
                        ListFonts = new FontList().listDirectoryFonts(getApplicationContext(), "fonts");
                        AdapterFont adapterFont = new AdapterFont(activity, ListFonts);
                        mRecyclerViewFonts.setAdapter(adapterFont);

                        ListFontColours = new FontColourList().listFontColours();
                        AdapterFontColour adapterFontColour = new AdapterFontColour(activity, ListFontColours);
                        mRecyclerViewFontColours.setAdapter(adapterFontColour);


                        /** If the fonts menu is visible, remove it  **/
                        if (mRecyclerViewBackground.getVisibility() == View.VISIBLE) {

                            mRecyclerViewBackground.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.fadeout));
                            mRecyclerViewBackground.setVisibility(View.GONE);
                            tbtnBackground.setChecked(false);
                            tbtnBackground.setTypeface(RalewayMedium);
                            tbtnBackground.setTextColor(getResources().getColor(R.color.white_transparent_80));
                            tbtnBackground.setBackgroundResource(R.drawable.text_input_background);

                        }

                        mRecyclerViewFonts.setLayoutManager(llmFont);
                        mRecyclerViewFonts.setVisibility(View.VISIBLE);
                        mRecyclerViewFonts.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.fadein));

                        mRecyclerViewFontColours.setLayoutManager(llmFontColour);
                        mRecyclerViewFontColours.setVisibility(View.VISIBLE);
                        mRecyclerViewFontColours.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.fadein));

                        WindowManager w = getWindowManager();
                        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(w.getDefaultDisplay().getWidth(), 520);
                        params.topMargin = (w.getDefaultDisplay().getHeight() - 485);
                        tbtnFont.setTypeface(RalewayBold);
                        tbtnFont.setTextColor(getResources().getColor(R.color.white));
                        tbtnFont.setBackgroundColor(getResources().getColor(R.color.black));

                    } else {

                        Log.i("Menu Close", "Fonts");

                        mRecyclerViewFontColours.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.fadeout));
                        mRecyclerViewFontColours.setVisibility(View.GONE);
                        mRecyclerViewFonts.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.fadeout));
                        mRecyclerViewFonts.setVisibility(View.GONE);
                        tbtnFont.setTypeface(RalewayMedium);
                        tbtnFont.setTextColor(getResources().getColor(R.color.white_transparent_80));
                        tbtnFont.setBackgroundResource(R.drawable.text_input_background);
                    }

                    break;


                default:
                    break;
            }
        }
    };


    /**
     * get the JSON data for the Backgrounds and do stuff with it.
     */
    class JSONAsyncBackgrounds extends AsyncTask<Void, Void, Void> {
//        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
//            pd = ProgressDialog.show(CreateNew.this, null, "Loading Backgrounds ...", true, false);
        }

        @Override
        protected Void doInBackground(Void... params) {
            JSONObject jsonObject = new JSONHelper().getJSONFromUrlBackgrounds();
            ListBackgrounds = new JSONParserBackgrounds().parse(jsonObject);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            AdapterBackground adapterBackground = new AdapterBackground(activity, ListBackgrounds);
            mRecyclerViewBackground.setAdapter(adapterBackground);
//            pd.dismiss();
        }
    }


    /**
     * get the JSON data for the Backgrounds and do stuff with it.  Currently not used but kept for future updates.
     */
    class  JSONAsyncFonts extends AsyncTask<Void, Void, Void> {
//        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
//            pd = ProgressDialog.show(CreateNew.this, null, "Loading Fonts ...", true, false);
        }

        @Override
        protected Void doInBackground(Void... params) {
            JSONObject jsonObject = new JSONHelper().getJSONFromUrlFonts();
            ListFonts = new JSONParserFonts().parse(jsonObject);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            AdapterFont adapterFont = new AdapterFont(activity, ListFonts);
            mRecyclerViewFonts.setAdapter(adapterFont);
//            pd.dismiss();
        }
    }
}
