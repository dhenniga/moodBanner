package com.moodbanner.dev.any;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.net.Uri;
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
import android.widget.TextView;
import android.widget.ToggleButton;

import com.moodbanner.dev.any.Fonts.AdapterFont;
import com.moodbanner.dev.any.Fonts.ValueFont;
import com.moodbanner.dev.any.JSON.JSONHelper;
import com.moodbanner.dev.any.JSON.JSONParserBackgrounds;
import com.moodbanner.dev.any.JSON.JSONParserFonts;
import com.moodbanner.dev.any.backgrounds.AdapterBackground;
import com.moodbanner.dev.any.backgrounds.ValueBackground;
import com.moodbanner.dev.any.listener.RecyclerClickListener;
import com.moodbanner.dev.any.listener.RecyclerTouchListener;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class CreateNew extends AppCompatActivity {

    TextView txtMainText;
    ImageView createnew_background_image, ivFontThumb;
    EditText txtInput;
    Button  btnMoveTextUp, btnMoveTextDown;
    ToggleButton tbtnFont, tbtnBackground, btnBorder, btnOverlay, btnShare;
    Typeface AfterShock, CreatedWorld, AquilineTwo, RalewayMedium, RalewayBold, RalewayLight, PermanentMarker, WCManoNegraBoldBta;
    RelativeLayout mRelativeLayout, main_container;

    AppCompatActivity activity = CreateNew.this;
    RecyclerView mRecyclerViewBackground, mRecyclerViewFonts;
    List<ValueBackground> ListBackgrounds;
    List<ValueFont> ListFonts;
    Context mContext;

    ScaleGestureDetector scaleGestureDetector;

    int fontSize, fontPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new);


        scaleGestureDetector = new ScaleGestureDetector(this, new simpleOnScaleGestureListener());




    AfterShock = Typeface.createFromAsset(getAssets(), "fonts/After_Shok.ttf");
        CreatedWorld = Typeface.createFromAsset(getAssets(), "fonts/ANUDRG__.ttf");
        AquilineTwo = Typeface.createFromAsset(getAssets(), "fonts/AquilineTwo.ttf");
        PermanentMarker = Typeface.createFromAsset(getAssets(), "fonts/PermanentMarker.ttf");
        WCManoNegraBoldBta = Typeface.createFromAsset(getAssets(), "fonts/WCManoNegraBoldBta.otf");

        RalewayLight = Typeface.createFromAsset(getAssets(), "interface-fonts/Raleway-Thin.ttf");
        RalewayMedium = Typeface.createFromAsset(getAssets(), "interface-fonts/Raleway-Medium.ttf");
        RalewayBold = Typeface.createFromAsset(getAssets(), "interface-fonts/Raleway-Bold.ttf");

        txtMainText = (TextView) findViewById(R.id.txtMainText);
        txtMainText.setLineSpacing(1, 0.6f);

        txtInput = (EditText) findViewById(R.id.etInput);

        tbtnFont = (ToggleButton) findViewById(R.id.tbtnFont);
        tbtnFont.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.slide_up));

        tbtnBackground = (ToggleButton) findViewById(R.id.tbtnBackground);
        tbtnBackground.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.slide_up));

        btnOverlay = (ToggleButton) findViewById(R.id.btnOverlay);
        btnOverlay.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.slide_up));

        btnBorder = (ToggleButton) findViewById(R.id.btnBorder);
        btnBorder.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.slide_up));

        btnShare = (ToggleButton) findViewById(R.id.btnShare);
        btnShare.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.slide_up));

        btnMoveTextUp = (Button) findViewById(R.id.btnMoveTextUp);
        btnMoveTextDown = (Button) findViewById(R.id.btnMoveTextDown);


        mRelativeLayout = (RelativeLayout) findViewById(R.id.create_new_container);
        main_container = (RelativeLayout) findViewById(R.id.main_container);


        tbtnFont.setTypeface(RalewayMedium);
        tbtnBackground.setTypeface(RalewayMedium);
        btnBorder.setTypeface(RalewayMedium);
        btnOverlay.setTypeface(RalewayMedium);
        btnShare.setTypeface(RalewayMedium);

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
     * Get the font file from online and save it to a temp directory
     *
     * @param context
     * @param url
     * @return
     */
    public File getFontFile(Context context, String url) {

        File file = null;

        try {
            String fileName = Uri.parse(url).getLastPathSegment();
            file = File.createTempFile(fileName, null, context.getCacheDir());
            Log.i("Font URL", url);
        } catch (IOException e) {
            Log.i("Font error", "file not created");
            // error
        }

        return file;
    }

    ;


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

                if (ListFonts != null) {

                    File fontFileTemp = getFontFile(getBaseContext(), ListFonts.get(position).getFontFile());

                    Log.i("Font Name", ListFonts.get(position).getName());
                    Log.i("Temp File", fontFileTemp.toString());

                    Typeface font = Typeface.createFromFile(fontFileTemp);
                    txtMainText.setTypeface(font);
                    txtMainText.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.bounce));

                }
            }
        }));
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
                            tbtnFont.setText("FONT");
                            tbtnFont.setTextColor(getResources().getColor(R.color.white_transparent_80));
                            tbtnFont.setBackgroundResource(R.drawable.text_input_background);

                        }

                        mRecyclerViewBackground.setLayoutManager(llmBackground);
                        mRecyclerViewBackground.setVisibility(View.VISIBLE);
                        mRecyclerViewBackground.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.fadein));


                        WindowManager w = getWindowManager();
                        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(w.getDefaultDisplay().getWidth(), 520);
                        params.topMargin = (w.getDefaultDisplay().getHeight() - 495);
                        tbtnBackground.setText("BACKGROUND");
                        tbtnBackground.setTypeface(RalewayBold);
                        tbtnBackground.setTextColor(getResources().getColor(R.color.white));
                        tbtnBackground.setBackgroundColor(getResources().getColor(R.color.black));

                    } else {

                        Log.i("Menu Close", "Backgrounds");
                        mRecyclerViewBackground.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.fadeout));
                        mRecyclerViewBackground.setVisibility(View.GONE);
                        tbtnBackground.setText("BACKGROUND");
                        tbtnBackground.setTypeface(RalewayMedium);
                        tbtnBackground.setTextColor(getResources().getColor(R.color.white_transparent_80));
                        tbtnBackground.setBackgroundResource(R.drawable.text_input_background);
                    }

                    break;


                case R.id.tbtnFont:


                    mRecyclerViewFonts = (RecyclerView) findViewById(R.id.recyclerViewFonts);
                    ivFontThumb = (ImageView) findViewById(R.id.ivFontThumb);
                    LinearLayoutManager llmFont = new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);


                    if (tbtnFont.isChecked()) {

                        Log.i("Menu Open", "Fonts");

                        new JSONAsyncFonts().execute();

                        /** If the fonts menu is visible, remove it  **/
                        if (mRecyclerViewBackground.getVisibility() == View.VISIBLE) {

                            mRecyclerViewBackground.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.fadeout));
                            mRecyclerViewBackground.setVisibility(View.GONE);
                            tbtnBackground.setChecked(false);
                            tbtnBackground.setText("BACKGROUND");
                            tbtnBackground.setTypeface(RalewayMedium);
                            tbtnBackground.setTextColor(getResources().getColor(R.color.white_transparent_80));
                            tbtnBackground.setBackgroundResource(R.drawable.text_input_background);

                        }

                        mRecyclerViewFonts.setLayoutManager(llmFont);
                        mRecyclerViewFonts.setVisibility(View.VISIBLE);
                        mRecyclerViewFonts.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.fadein));

                        WindowManager w = getWindowManager();
                        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(w.getDefaultDisplay().getWidth(), 520);
                        params.topMargin = (w.getDefaultDisplay().getHeight() - 485);
                        tbtnFont.setText("FONT");
                        tbtnFont.setTypeface(RalewayBold);
                        tbtnFont.setTextColor(getResources().getColor(R.color.white));
                        tbtnFont.setBackgroundColor(getResources().getColor(R.color.black));

                    } else {

                        Log.i("Menu Close", "Fonts");

                        mRecyclerViewFonts.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.fadeout));
                        mRecyclerViewFonts.setVisibility(View.GONE);
                        tbtnFont.setText("FONT");
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
     * get the JSON data for the Backgrounds and do stuff with it.
     */
    class JSONAsyncFonts extends AsyncTask<Void, Void, Void> {
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
