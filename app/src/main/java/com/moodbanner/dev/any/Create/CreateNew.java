package com.moodbanner.dev.any.Create;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringSystem;

import com.moodbanner.dev.any.Backgrounds.AdapterBackground;
import com.moodbanner.dev.any.Backgrounds.BackgroundList;
import com.moodbanner.dev.any.Backgrounds.ValueBackground;
import com.moodbanner.dev.any.JSON.JSONHelper;
import com.moodbanner.dev.any.JSON.JSONParserBackgrounds;
import com.moodbanner.dev.any.JSON.JSONParserFonts;
import com.moodbanner.dev.any.R;
import com.moodbanner.dev.any.Text.Colour.AdapterTextColour;
import com.moodbanner.dev.any.Text.Colour.TextColourList;
import com.moodbanner.dev.any.Text.Colour.ValueTextColour;
import com.moodbanner.dev.any.Text.Font.AdapterFont;
import com.moodbanner.dev.any.Text.Font.FontList;
import com.moodbanner.dev.any.Text.Font.ValueFont;
import com.moodbanner.dev.any.Text.Texture.AdapterTexture;
import com.moodbanner.dev.any.Text.Texture.TextureList;
import com.moodbanner.dev.any.Text.Texture.ValueTexture;
import com.moodbanner.dev.any.listener.RecyclerClickListener;
import com.moodbanner.dev.any.listener.RecyclerTouchListener;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class CreateNew extends AppCompatActivity {


    /**  Variables for the Spring Animator **/
    private static double TENSION = 500;
    private static double DAMPER = 10;

    private TextView txtMainText;
    private ImageView createnew_background_image, ivFontThumb, ivColour;
    private EditText txtInput;
    private Button btnMoveTextUp, btnMoveTextDown;
    private ToggleButton tbtnText, tbtnBackground, btnBorder, btnOverlay, btnShare, tbtnShowHideUI,
            tbtnShadow, tbtnTextFont, tbtnTextColour, tbtnTextTexture, tbtnTextEffects, tbtnBackgroundStandard, tbtnBackgroundCamera, tbtnBackgroundOnline;
    private Typeface RalewayMedium, RalewayBold, RalewayLight, font;
    private RelativeLayout mRelativeLayout, main_container, seekbarContainer;
    private LinearLayout menuFonts, menuBackgrounds;

    private AppCompatActivity activity = CreateNew.this;
    private RecyclerView mRecyclerViewBackground, mRecyclerViewFonts, mRecyclerViewTextColours, mRecyclerViewTexture;
    private List<ValueBackground> ListBackgrounds;
    private List<ValueFont> ListFonts;
    private List<ValueTextColour> ListFontColours;
    private List<ValueTexture> ListTextures;
    private Context mContext;

    private ScaleGestureDetector scaleGestureDetector;

    SeekBar seekBar;


    int fontSize, fontPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new);


        scaleGestureDetector = new ScaleGestureDetector(this, new simpleOnScaleGestureListener());


        RalewayLight = Typeface.createFromAsset(getAssets(), "interface-fonts/Raleway-Thin.ttf");
        RalewayMedium = Typeface.createFromAsset(getAssets(), "interface-fonts/Raleway-Medium.ttf");
        RalewayBold = Typeface.createFromAsset(getAssets(), "interface-fonts/Raleway-Bold.ttf");

        font = Typeface.createFromAsset(getAssets(), "interface-fonts/Raleway-Thin.ttf");

        txtMainText = (TextView) findViewById(R.id.txtMainText);
        txtMainText.setLineSpacing((200), 0.4f);

        txtInput = (EditText) findViewById(R.id.etInput);

        tbtnBackgroundStandard = (ToggleButton) findViewById(R.id.tbtnBackgroundStandard);
        tbtnBackgroundCamera = (ToggleButton) findViewById(R.id.tbtnBackgroundCamera);
        tbtnBackgroundOnline = (ToggleButton) findViewById(R.id.tbtnBackgroundOnline);

        tbtnText = (ToggleButton) findViewById(R.id.tbtnText);
        tbtnText.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.slide_up_in));

        tbtnBackground = (ToggleButton) findViewById(R.id.tbtnBackground);
        tbtnBackground.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.slide_up_in));

        btnOverlay = (ToggleButton) findViewById(R.id.btnOverlay);
        btnOverlay.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.slide_up_in));

        btnBorder = (ToggleButton) findViewById(R.id.btnBorder);
        btnBorder.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.slide_up_in));

        btnShare = (ToggleButton) findViewById(R.id.btnShare);
        btnShare.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.slide_up_in));


        menuFonts = (LinearLayout) findViewById(R.id.menuFonts);
        menuBackgrounds = (LinearLayout) findViewById(R.id.menuBackgrounds);


        tbtnShowHideUI = (ToggleButton) findViewById(R.id.tbtnShowHideUI);
        tbtnShowHideUI.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.slide_left_in));

        txtInput.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.slide_down_in));

        btnMoveTextUp = (Button) findViewById(R.id.btnMoveTextUp);
        btnMoveTextDown = (Button) findViewById(R.id.btnMoveTextDown);

        tbtnShadow = (ToggleButton) findViewById(R.id.tbtnShadow);


        mRelativeLayout = (RelativeLayout) findViewById(R.id.create_new_container);


        main_container = (RelativeLayout) findViewById(R.id.main_container);

        seekbarContainer = (RelativeLayout) findViewById(R.id.seekbarContainer);
        seekBar = (SeekBar) findViewById(R.id.seekBar1);


        tbtnTextFont = (ToggleButton) findViewById(R.id.tbtnTextFont);
        tbtnTextColour = (ToggleButton) findViewById(R.id.tbtnTextColour);
        tbtnTextTexture = (ToggleButton) findViewById(R.id.tbtnTextTexture);
        tbtnTextEffects = (ToggleButton) findViewById(R.id.tbtnTextEffects);

        /** Menu Base Fonts  **/
        tbtnText.setTypeface(RalewayMedium);
        tbtnBackground.setTypeface(RalewayMedium);
        btnBorder.setTypeface(RalewayMedium);
        btnOverlay.setTypeface(RalewayMedium);
        btnShare.setTypeface(RalewayMedium);


        /** Menu Text Menu Fonts **/
        tbtnTextFont.setTypeface(RalewayMedium);
        tbtnTextColour.setTypeface(RalewayMedium);
        tbtnTextTexture.setTypeface(RalewayMedium);
        tbtnTextEffects.setTypeface(RalewayMedium);

        /** Menu Background Menu Fonts **/
        tbtnBackgroundStandard.setTypeface(RalewayMedium);
        tbtnBackgroundCamera.setTypeface(RalewayMedium);
        tbtnBackgroundOnline.setTypeface(RalewayMedium);

        tbtnShowHideUI.setTypeface(RalewayMedium);

        txtInput.setTypeface(RalewayLight);

//        Shader shader = new LinearGradient(0, 0, 0, txtMainText.getTextSize(), Color.RED, Color.BLUE, Shader.TileMode.MIRROR);
//        txtMainText.getPaint().setShader(shader);
//        txtMainText.setShadowLayer(25, 1, 1, Color.parseColor("#ffffffff"));

//        GLOW BELOW
        txtMainText.setShadowLayer(25, 1, 1, Color.parseColor("#ffffffff"));

        txtMainText.setTypeface(font);


        /**
         *  Setup the screen buttons.  These will be replaced with a gesture touch system.
         */
        btnMoveTextUp.setOnClickListener(listener);
        btnMoveTextDown.setOnClickListener(listener);
        tbtnBackground.setOnClickListener(listener);
        tbtnText.setOnClickListener(listener);
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
                getWindow().getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

            }
        });


        BackgroundGallery();
        FontGallery();
        FontColourGallery();
        TextureGallery();


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            int progress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                progress = progresValue;
                txtMainText.setLineSpacing((progress), 0.4f);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

        });


    }


    View.OnClickListener listenerText = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.tbtnTextFont:

                    if (tbtnTextFont.isChecked()) {

                        mRecyclerViewFonts = (RecyclerView) findViewById(R.id.recyclerViewFonts);
                        ivFontThumb = (ImageView) findViewById(R.id.ivFontThumb);
                        LinearLayoutManager llmFont = new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);

                        ListFonts = new FontList().listDirectoryFonts(getApplicationContext(), "fonts");
                        AdapterFont adapterFont = new AdapterFont(activity, ListFonts);
                        mRecyclerViewFonts.setAdapter(adapterFont);
                        mRecyclerViewFonts.setLayoutManager(llmFont);

                        Log.i("Button Press", "Menu Open - Font");

                        //  Show the recyclerView for the Text | Fonts selection
                        recyclerViewAppear(mRecyclerViewFonts);

                        // set the "Font" toggle button to "on"
                        setToggleButtonToChecked(tbtnTextFont);

                        // check of the any of the other toggle button is "on" and set them to off, removing the relevant recyclerView
                        toggleButtonChecked(mRecyclerViewBackground, tbtnBackground);
                        toggleButtonChecked(mRecyclerViewTextColours, tbtnTextColour);
                        toggleButtonChecked(mRecyclerViewTexture, tbtnTextTexture);


                    } else {

                        Log.i("Button Press", "Menu Close - Font");

                        //  Remove the recyclerView for the Text | Fonts selection
                        recyclerViewDisappear(mRecyclerViewFonts);

                        //  Set the "Font" toggle button to unchecked
                        setToggleButtonToUnchecked(tbtnTextFont);

                        // check of the any of the other toggle button is "on" and set them to off, removing the relevant recyclerView
                        toggleButtonChecked(mRecyclerViewBackground, tbtnBackground);
                        toggleButtonChecked(mRecyclerViewTextColours, tbtnTextColour);
                        toggleButtonChecked(mRecyclerViewTexture, tbtnTextTexture);

                    }

                    break;


                case R.id.tbtnTextColour:

                    if (tbtnTextColour.isChecked()) {

                        mRecyclerViewTextColours = (RecyclerView) findViewById(R.id.recyclerViewTextColours);
                        ivColour = (ImageView) findViewById(R.id.ivColour);
                        LinearLayoutManager llmFontColour = new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);

                        ListFontColours = new TextColourList().listFontColours();
                        AdapterTextColour adapterTextColour = new AdapterTextColour(activity, ListFontColours);
                        mRecyclerViewTextColours.setAdapter(adapterTextColour);
                        mRecyclerViewTextColours.setLayoutManager(llmFontColour);

                        Log.i("Button Press", "Menu Open - Colour");


                        //  Show the recyclerView for the Text | Colours selection
                        recyclerViewAppear(mRecyclerViewTextColours);

                        // set the "Colour" toggle button to "on"
                        setToggleButtonToChecked(tbtnTextColour);

                        // check of the any of the other toggle button is "on" and set them to off, removing the relevant recyclerView
                        toggleButtonChecked(mRecyclerViewBackground, tbtnBackground);
                        toggleButtonChecked(mRecyclerViewFonts, tbtnTextFont);
                        toggleButtonChecked(mRecyclerViewTexture, tbtnTextTexture);


                    } else {

                        Log.i("Button Press", "Menu Close - Colour");

                        // Remove the recyclerView for the Text | Colours selection
                        recyclerViewDisappear(mRecyclerViewTextColours);

                        //  Set the "Colour" toggle button to unchecked
                        setToggleButtonToUnchecked(tbtnTextColour);

                        // check of the any of the other toggle button is "on" and set them to off, removing the relevant recyclerView
                        toggleButtonChecked(mRecyclerViewBackground, tbtnBackground);
                        toggleButtonChecked(mRecyclerViewFonts, tbtnTextFont);
                        toggleButtonChecked(mRecyclerViewTexture, tbtnTextTexture);

                    }

                    break;


                case R.id.tbtnTextTexture:

                    if (tbtnTextTexture.isChecked()) {

                    mRecyclerViewTexture = (RecyclerView) findViewById(R.id.recyclerViewTextures);
                    LinearLayoutManager llmTexture = new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);

                    ListTextures = new TextureList().listDirectoryTextures(getApplicationContext(), "textures");
                    AdapterTexture adapterTexture = new AdapterTexture(activity, ListTextures);
                    mRecyclerViewTexture.setAdapter(adapterTexture);
                    mRecyclerViewTexture.setLayoutManager(llmTexture);

                        Log.i("Button Press", "Menu Open - Texture");

                        //  Show the recyclerView for the Text | Textures selection
                        recyclerViewAppear(mRecyclerViewTexture);

                        // set the "Texture" toggle button to "on"
                        setToggleButtonToChecked(tbtnTextTexture);

                        // check of the any of the other toggle button is "on" and set them to off, removing the relevant recyclerView
                        toggleButtonChecked(mRecyclerViewBackground, tbtnBackground);
                        toggleButtonChecked(mRecyclerViewTextColours, tbtnTextColour);
                        toggleButtonChecked(mRecyclerViewFonts, tbtnTextFont);


                    } else {

                        Log.i("Button Press", "Menu Close - Texture");

                        // Remove the recyclerView for the Text | Colours selection
                        recyclerViewDisappear(mRecyclerViewTexture);

                        //  Set the "Colour" toggle button to unchecked
                        setToggleButtonToUnchecked(tbtnTextTexture);

                        // check of the any of the other toggle button is "on" and set them to off, removing the relevant recyclerView
                        toggleButtonChecked(mRecyclerViewBackground, tbtnBackground);
                        toggleButtonChecked(mRecyclerViewTextColours, tbtnTextColour);
                        toggleButtonChecked(mRecyclerViewFonts, tbtnTextFont);

                    }

                    break;


                case R.id.tbtnTextEffects:
                    Log.i("Button Press", "Text Menu - Effects");
                    break;


                default:
                    break;
            }
        }
    };


    public void toggleButtonChecked(RecyclerView recyclerView, ToggleButton toggleButton) {

        if (toggleButton.isChecked()) {

            recyclerView.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.fadeout));
            recyclerView.setVisibility(View.GONE);
            toggleButton.setTypeface(RalewayMedium);
            toggleButton.setTextColor(getResources().getColor(R.color.white_transparent_80));
            toggleButton.setBackgroundResource(R.drawable.text_input_background);
            toggleButton.setChecked(false);
        }
    }


    public void setToggleButtonToChecked(ToggleButton toggleButton) {

        toggleButton.setTypeface(RalewayBold);
        toggleButton.setTextColor(getResources().getColor(R.color.white));
        toggleButton.setBackgroundColor(getResources().getColor(R.color.black));
        toggleButton.setChecked(true);

    }

    /**
     *
     * If the toggle button is NOT checked, set it to
     *
     * @param toggleButton
     */
    public void setToggleButtonToUnchecked(ToggleButton toggleButton) {

        toggleButton.setTypeface(RalewayMedium);
        toggleButton.setTextColor(getResources().getColor(R.color.white_transparent_80));
        toggleButton.setBackgroundResource(R.drawable.text_input_background);
        toggleButton.setChecked(false);
    }

    public void removeMenu (LinearLayout menuTitle) {

        if (menuTitle.getVisibility() == View.VISIBLE) {
            menuTitle.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.fadeout));
            menuTitle.setVisibility(View.GONE);
        }

    }


    /**
     *  Should remove this as it's too general.
     */
    public void checkAllToggleStates() {

        toggleButtonChecked(mRecyclerViewBackground, tbtnBackground);
        toggleButtonChecked(mRecyclerViewTextColours, tbtnTextColour);
        toggleButtonChecked(mRecyclerViewFonts, tbtnTextFont);

    }

    /**
     *
     * If the RecyclerView is NOT visible, make it visible.
     *
     * @param recyclerView
     */
    public void recyclerViewAppear (RecyclerView recyclerView) {

        if (recyclerView.getVisibility() != View.VISIBLE) {

            recyclerView.setVisibility(View.VISIBLE);
            recyclerView.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.fadein));
        }

    }


    /**
     *
     * If the RecyclerView IS visible, remove it from the layout.
     *
     * @param recyclerView
     */
    public void recyclerViewDisappear (RecyclerView recyclerView) {

        if (recyclerView.getVisibility() == View.VISIBLE) {

            recyclerView.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.fadeout));
            recyclerView.setVisibility(View.GONE);
        }

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

                font = Typeface.createFromAsset(getAssets(), ListFonts.get(position).getFontFile());
                txtMainText.setTypeface(font);

                SpringyTextAnimation(txtMainText);
            }

        }));
    }


    /**
     *  Setup for the Font Colour selector horizontal menu
     */
    public void FontColourGallery() {

        mRecyclerViewTextColours = (RecyclerView) findViewById(R.id.recyclerViewTextColours);
        LinearLayoutManager mLayoutManagerFont = new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerViewTextColours.setLayoutManager(mLayoutManagerFont);

        mRecyclerViewTextColours.addOnItemTouchListener(new RecyclerTouchListener(activity, mRecyclerViewTextColours, new RecyclerClickListener() {

            @Override
            public void onClick(View view, int position) {

                Shader shader = new LinearGradient(0, 0, 0, txtMainText.getTextSize(),
                        Color.parseColor(ListFontColours.get(position).getColourHexCode()),
                        Color.parseColor(ListFontColours.get(position).getColourHexCode()),
                        Shader.TileMode.CLAMP);

                txtMainText.getPaint().setShader(shader);
                txtMainText.setText(txtInput.getText());
                SpringyTextAnimation(txtMainText);

            }

        }));
    }


    /**
     * Controls the display of the background gallery popout.
     */
    public void TextureGallery() {

        mRecyclerViewTexture = (RecyclerView) findViewById(R.id.recyclerViewTextures);
        LinearLayoutManager mLayoutManagerTexture = new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerViewTexture.setLayoutManager(mLayoutManagerTexture);

        mRecyclerViewTexture.addOnItemTouchListener(new RecyclerTouchListener(activity, mRecyclerViewTexture, new RecyclerClickListener() {

            @Override
            public void onClick(View view, int position) {

                Log.i("Name", ListTextures.get(position).getName());
                Log.i("BitmapTexture", ListTextures.get(position).getBitmapTexture());
                Log.i("TextureFile", ListTextures.get(position).getTextureFile());

                try {
                    InputStream ims = getAssets().open(ListTextures.get(position).getBitmapTexture());
                    Bitmap bitmap = BitmapFactory.decodeStream(ims);
                    Shader shader = new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
                    txtMainText.getPaint().setShader(shader);
                    txtMainText.setText(txtInput.getText());
                    SpringyTextAnimation(txtMainText);

                } catch (IOException ex) {
                    return;
                }

            }

        }));
    }

    /**
     * Spring Animation for TextView objects
     *
     * @param textView
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
                float scale = 0.98f - (value * -0.1f);
                textView.setScaleX(scale);
                textView.setScaleY(scale);
            }
        });

        spring.setEndValue(1);
    }


    /**
     *  Switch Listener for the Background menu buttons
     */
    View.OnClickListener listenerBackground = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.tbtnBackgroundStandard:

                    if (tbtnBackgroundStandard.isChecked()) {

                        mRecyclerViewBackground = (RecyclerView) findViewById(R.id.recyclerViewBackgrounds);
                        createnew_background_image = (ImageView) findViewById(R.id.createnew_background_image);
                        LinearLayoutManager llmBackground = new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);

                        ListBackgrounds = new BackgroundList().listInternalDirectoryBackgrounds(getApplicationContext(), "backgrounds");
                        AdapterBackground adapterBackground = new AdapterBackground(activity, ListBackgrounds);
                        mRecyclerViewBackground.setAdapter(adapterBackground);
                        mRecyclerViewBackground.setLayoutManager(llmBackground);

                        mRecyclerViewBackground.setVisibility(View.VISIBLE);
                        mRecyclerViewBackground.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.fadein));

                        Log.i("Menu Open", "Backgrounds - Standard");

                        /** Set the Toggle button "tbtnText" to unchecked  **/
                        setToggleButtonToChecked(tbtnBackgroundStandard);
                        setToggleButtonToUnchecked(tbtnBackgroundCamera);
                        setToggleButtonToUnchecked(tbtnBackgroundOnline);


                        /** If the Text Fonts, Colours menu are visible, remove it  **/
                        toggleButtonChecked(mRecyclerViewFonts, tbtnTextFont);
                        toggleButtonChecked(mRecyclerViewTextColours, tbtnTextColour);

                        /**  Remove the "Text" submenu  **/
                        setToggleButtonToUnchecked(tbtnText);

                        if (menuFonts.getVisibility() == View.VISIBLE) {
                            menuFonts.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.fadeout));
                            menuFonts.setVisibility(View.GONE);
                        }


                        WindowManager w = getWindowManager();
                        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(w.getDefaultDisplay().getWidth(), 520);
                        params.topMargin = (w.getDefaultDisplay().getHeight() - 495);


                    } else {

                        Log.i("Menu Close", "Backgrounds - Standard");

                        /** Set the Toggle button "tbtnText" to unchecked  **/
                        setToggleButtonToUnchecked(tbtnBackgroundStandard);

                        recyclerViewDisappear(mRecyclerViewBackground);

                    }

                    break;


                case R.id.tbtnBackgroundCamera:

                    if (tbtnBackgroundCamera.isChecked()) {

                        mRecyclerViewBackground = (RecyclerView) findViewById(R.id.recyclerViewBackgrounds);
                        createnew_background_image = (ImageView) findViewById(R.id.createnew_background_image);
                        LinearLayoutManager llmBackground = new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);

                        ListBackgrounds = new BackgroundList().listCameraDirectoryImages();
                        AdapterBackground adapterBackground = new AdapterBackground(activity, ListBackgrounds);
                        mRecyclerViewBackground.setAdapter(adapterBackground);
                        mRecyclerViewBackground.setLayoutManager(llmBackground);

                        mRecyclerViewBackground.setVisibility(View.VISIBLE);
                        mRecyclerViewBackground.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.fadein));

                        Log.i("Menu Open", "Backgrounds - Camera");

                        /** Set the Toggle button "tbtnText" to unchecked  **/

                        setToggleButtonToUnchecked(tbtnBackgroundStandard);
                        setToggleButtonToChecked(tbtnBackgroundCamera);
                        setToggleButtonToUnchecked(tbtnBackgroundOnline);


                        /** If the Text Fonts, Colours menu are visible, remove it  **/
                        toggleButtonChecked(mRecyclerViewFonts, tbtnTextFont);
                        toggleButtonChecked(mRecyclerViewTextColours, tbtnTextColour);

                        /**  Remove the "Text" submenu  **/
                        setToggleButtonToUnchecked(tbtnText);

                        if (menuFonts.getVisibility() == View.VISIBLE) {
                            menuFonts.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.fadeout));
                            menuFonts.setVisibility(View.GONE);
                        }


                        WindowManager w = getWindowManager();
                        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(w.getDefaultDisplay().getWidth(), 520);
                        params.topMargin = (w.getDefaultDisplay().getHeight() - 495);


                    } else {

                        Log.i("Menu Close", "Backgrounds - Camera");

                        /** Set the Toggle button "tbtnText" to unchecked  **/
                        setToggleButtonToUnchecked(tbtnBackgroundCamera);

                        recyclerViewDisappear(mRecyclerViewBackground);

                    }

                    break;


                case R.id.tbtnBackgroundOnline:

                    if (tbtnBackgroundOnline.isChecked()) {

                        mRecyclerViewBackground.setVisibility(View.VISIBLE);
                        mRecyclerViewBackground.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.fadein));

                        Log.i("Menu Open", "Backgrounds - Online");

                        new JSONAsyncBackgrounds().execute();

                        /** Set the Toggle button "tbtnText" to unchecked  **/
                        setToggleButtonToUnchecked(tbtnBackgroundStandard);
                        setToggleButtonToUnchecked(tbtnBackgroundCamera);
                        setToggleButtonToChecked(tbtnBackgroundOnline);

                        /** If the Text Fonts, Colours menu are visible, remove it  **/
                        toggleButtonChecked(mRecyclerViewFonts, tbtnTextFont);
                        toggleButtonChecked(mRecyclerViewTextColours, tbtnTextColour);

                        /**  Remove the "Text" submenu  **/
                        setToggleButtonToUnchecked(tbtnText);

                        if (menuFonts.getVisibility() == View.VISIBLE) {
                            menuFonts.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.fadeout));
                            menuFonts.setVisibility(View.GONE);
                        }


                        WindowManager w = getWindowManager();
                        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(w.getDefaultDisplay().getWidth(), 520);
                        params.topMargin = (w.getDefaultDisplay().getHeight() - 495);


                    } else {

                        Log.i("Menu Close", "Backgrounds - Online");

                        /** Set the Toggle button "tbtnText" to unchecked  **/
                        setToggleButtonToUnchecked(tbtnBackgroundOnline);

                        recyclerViewDisappear(mRecyclerViewBackground);

                    }

                    break;


                default:
                    break;


            }
        }
    };



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

                    tbtnShowHideUI.setAlpha(0.4f);

                    tbtnText.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.slide_down_out));
                    tbtnText.setVisibility(View.GONE);
                    setToggleButtonToUnchecked(tbtnText);

                    tbtnBackground.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.slide_down_out));
                    tbtnBackground.setVisibility(View.GONE);
                    setToggleButtonToUnchecked(tbtnBackground);

                    tbtnTextFont.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.slide_down_out));
                    tbtnTextFont.setVisibility(View.GONE);
                    setToggleButtonToUnchecked(tbtnTextFont);

                    tbtnTextColour.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.slide_down_out));
                    tbtnTextColour.setVisibility(View.GONE);
                    setToggleButtonToUnchecked(tbtnTextColour);

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
                    }

                    if (mRecyclerViewTextColours.getVisibility() == View.VISIBLE) {
                        mRecyclerViewTextColours.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.fadeout));
                        mRecyclerViewTextColours.setVisibility(View.GONE);
                    }

                    if (mRecyclerViewBackground.getVisibility() == View.VISIBLE) {
                        mRecyclerViewBackground.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.fadeout));
                        mRecyclerViewBackground.setVisibility(View.GONE);
                    }

                    if (mRecyclerViewTexture.getVisibility() == View.VISIBLE) {
                        mRecyclerViewTexture.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.fadeout));
                        mRecyclerViewTexture.setVisibility(View.GONE);
                    }

                    if (menuFonts.getVisibility() == View.VISIBLE) {

                        tbtnTextFont.setChecked(false);
                        tbtnTextColour.setChecked(false);
                        tbtnTextTexture.setChecked(false);
                        tbtnTextEffects.setChecked(false);
                        menuFonts.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.fadeout));
                        menuFonts.setVisibility(View.GONE);

                    }


                } else {

                    Log.i("Button Press", "UI - Show");

                    tbtnShowHideUI.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.ui_fadein));
                    tbtnShowHideUI.setAlpha(1);

                    tbtnText.setVisibility(View.VISIBLE);
                    tbtnText.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.slide_up_in));
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


                    if (tbtnBackground.isChecked()) {


                        Log.i("Menu Open", "Background");

                        /** Set the Toggle button "tbtnText" to checked  **/
                        setToggleButtonToChecked(tbtnBackground);
                        toggleButtonChecked(mRecyclerViewFonts, tbtnText);
                        removeMenu(menuFonts);


                        /**  Display the "Text" submenu  **/
                        menuBackgrounds.setVisibility(View.VISIBLE);
                        menuBackgrounds.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.fadein));

                        /**  Enable a listener for the menu buttons  **/
                        tbtnBackgroundStandard.setOnClickListener(listenerBackground);
                        tbtnBackgroundCamera.setOnClickListener(listenerBackground);
                        tbtnBackgroundOnline.setOnClickListener(listenerBackground);

                        /**  Check if any of the other Toggle Button are checked and run an uncheck  **/
                        toggleButtonChecked(mRecyclerViewTextColours, tbtnTextColour);
                        toggleButtonChecked(mRecyclerViewFonts, tbtnTextFont);
                        toggleButtonChecked(mRecyclerViewTexture, tbtnTextTexture);

                    } else {

                        Log.i("Menu Close", "Backgrounds");

                        /** Set the Toggle button "tbtnBackground" to unchecked  **/
                        setToggleButtonToUnchecked(tbtnBackground);

                        /**  Remove the "Background" submenu  **/
                        menuBackgrounds.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.fadeout));
                        menuBackgrounds.setVisibility(View.GONE);

                        /**  Remove the listener for the menu buttons  **/
                        tbtnBackgroundStandard.setOnClickListener(null);
                        tbtnBackgroundCamera.setOnClickListener(null);
                        tbtnBackgroundOnline.setOnClickListener(null);


                        toggleButtonChecked(mRecyclerViewFonts, tbtnText);

                        recyclerViewDisappear(mRecyclerViewBackground);
                    }

                    break;


                case R.id.tbtnText:

                    tbtnTextFont.setVisibility(View.VISIBLE);
                    tbtnTextColour.setVisibility(View.VISIBLE);

                    if (tbtnText.isChecked()) {

                        tbtnTextFont.setOnClickListener(listenerText);
                        tbtnTextColour.setOnClickListener(listenerText);
                        tbtnTextTexture.setOnClickListener(listenerText);
                        tbtnTextEffects.setOnClickListener(listenerText);

                        Log.i("Menu Open", "Text");

                        /** Set the Toggle button "tbtnText" to checked  **/
                        setToggleButtonToChecked(tbtnText);
                        toggleButtonChecked(mRecyclerViewBackground, tbtnBackground);
                        removeMenu(menuBackgrounds);

                        /**  Display the "Text" submenu  **/
                        menuFonts.setVisibility(View.VISIBLE);
                        menuFonts.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.fadein));

                        /**  Check if any of the other Toggle Button are checked and run an uncheck  **/
                        toggleButtonChecked(mRecyclerViewBackground, tbtnBackground);
                        toggleButtonChecked(mRecyclerViewTextColours, tbtnTextColour);
                        toggleButtonChecked(mRecyclerViewFonts, tbtnTextFont);
                        toggleButtonChecked(mRecyclerViewTexture, tbtnTextTexture);

                    } else {

                        tbtnTextFont.setOnClickListener(null);
                        tbtnTextColour.setOnClickListener(null);
                        tbtnTextTexture.setOnClickListener(null);
                        tbtnTextEffects.setOnClickListener(null);

                        Log.i("Menu Close", "Text");

                        /** Set the Toggle button "tbtnText" to unchecked  **/
                        setToggleButtonToUnchecked(tbtnText);

                        /**  Remove the "Text" submenu  **/
                        menuFonts.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.fadeout));
                        menuFonts.setVisibility(View.GONE);

                        toggleButtonChecked(mRecyclerViewBackground, tbtnBackground);
                        toggleButtonChecked(mRecyclerViewTextColours, tbtnTextColour);
                        toggleButtonChecked(mRecyclerViewFonts, tbtnTextFont);
                        toggleButtonChecked(mRecyclerViewTexture, tbtnTextTexture);

                    }

                    break;


                default:
                    break;
            }
        }
    };


    /**
     * get the JSON data for the Backgrounds and do stuff with it.
     *
     */
    class JSONAsyncBackgrounds extends AsyncTask<Void, Void, Void> {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            pd = ProgressDialog.show(CreateNew.this, null, "Loading Online Backgrounds ...", true, false);
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
            pd.dismiss();
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

    @Override
    protected void onPause() {
        super.onPause();
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }
}
