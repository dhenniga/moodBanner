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
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
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
import com.moodbanner.dev.any.backgrounds.AdapterBackground;
import com.moodbanner.dev.any.backgrounds.BackgroundList;
import com.moodbanner.dev.any.backgrounds.ValueBackground;
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

import com.moodbanner.dev.any.multiTouch.MultiTouchListener;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class CreateNew extends AppCompatActivity {




    /**  Variables for the Spring Animator **/
    private static double TENSION = 500;
    private static double DAMPER = 10;

    private AppCompatActivity activity = CreateNew.this;
    private TextView txtMainText, txtHeight, txtWidth, txtSize, txtAlpha;
    private ImageView createnew_background_image, ivFontThumb, ivColour;
    private EditText txtInput;
    private ToggleButton tbtnText, tbtnBackground, btnBorder, btnOverlay, btnShare, tbtnShowHideUI, tbtnShadow, tbtnTextFont, tbtnTextColour, tbtnTextTexture, tbtnTextEffects, tbtnBackgroundStandard, tbtnBackgroundCamera, tbtnBackgroundOnline;
    private Button btnTextAlignLeft, btnTextAlignCenter, btnTextAlignRight;
    private Typeface RalewayMedium, RalewayBold, RalewayLight, font;
    private RelativeLayout mRelativeLayout, main_container, seekbarHeightContainer, seekbarSizeContainer, seekbarWidthContainer, seekbarAlphaContainer;
    private LinearLayout menuFonts, menuBackgrounds, llTextControls1, llTextControls2, tempButtons;
    private RecyclerView mRecyclerViewBackground, mRecyclerViewFonts, mRecyclerViewTextColours, mRecyclerViewTexture;
    private List<ValueBackground> ListBackgrounds;
    private List<ValueFont> ListFonts;
    private List<ValueTextColour> ListFontColours;
    private List<ValueTexture> ListTextures;
    private Context mContext;
    private SeekBar seekBarHeight, seekBarSize, seekBarWidth, seekBarAlpha;

    int fontSize, fontPosition, windowHeight, windowWidth;
    int mainTextHeightProgress = 0;
    int mainTextWidth = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        removeSoftMenuBar();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new);


        /**  Get the Extra intent from the previous screen as to which social media
         *   layout button was press and adjust the screen height accordingly
         */
        if (savedInstanceState == null) {

            Bundle extras = getIntent().getExtras();

            if (extras == null) {

                WindowManager.LayoutParams params = getWindow().getAttributes();
                windowHeight = params.height;

            } else {

                windowHeight = extras.getInt("screenHeight");
                Log.d("windowHeight", ((String.valueOf(windowHeight))));

                main_container = (RelativeLayout) findViewById(R.id.main_container);
                main_container.getLayoutParams().height = windowHeight;
                main_container.requestLayout();

            }

        } else {

            windowHeight = (Integer) savedInstanceState.getSerializable("screenHeight");

        }


        /**  Set the width of the display to Int windowWidth  **/
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        Log.d("windowWidth", "Display width in px is " + metrics.widthPixels);
        windowWidth = metrics.widthPixels;



        txtMainText = (TextView) findViewById(R.id.txtMainText);

        txtInput = (EditText) findViewById(R.id.etInput);

        tbtnBackgroundStandard = (ToggleButton) findViewById(R.id.tbtnBackgroundStandard);
        tbtnBackgroundCamera = (ToggleButton) findViewById(R.id.tbtnBackgroundCamera);
        tbtnBackgroundOnline = (ToggleButton) findViewById(R.id.tbtnBackgroundOnline);


        /**  Bottom Menu  **/
        tbtnText = (ToggleButton) findViewById(R.id.tbtnText);
        tbtnBackground = (ToggleButton) findViewById(R.id.tbtnBackground);
        btnOverlay = (ToggleButton) findViewById(R.id.btnOverlay);
        btnBorder = (ToggleButton) findViewById(R.id.btnBorder);
        btnShare = (ToggleButton) findViewById(R.id.btnShare);
        TBAnimateIn(tbtnText);
        TBAnimateIn(tbtnBackground);
        BAnimateIn(btnOverlay);
        BAnimateIn(btnBorder);
        BAnimateIn(btnShare);


        menuFonts = (LinearLayout) findViewById(R.id.menuFonts);
        menuBackgrounds = (LinearLayout) findViewById(R.id.menuBackgrounds);
        tempButtons = (LinearLayout) findViewById(R.id.tempButtons);


        tbtnShowHideUI = (ToggleButton) findViewById(R.id.tbtnShowHideUI);
        tbtnShowHideUI.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.slide_left_in));

        txtInput.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.slide_down_in));

        tbtnShadow = (ToggleButton) findViewById(R.id.tbtnShadow);


        btnTextAlignLeft = (Button) findViewById(R.id.btnTextAlignLeft);
        btnTextAlignCenter = (Button) findViewById(R.id.btnTextAlignCenter);
        btnTextAlignRight = (Button) findViewById(R.id.btnTextAlignRight);


        mRelativeLayout = (RelativeLayout) findViewById(R.id.create_new_container);

        llTextControls1 = (LinearLayout) findViewById(R.id.llTextControls1);
        llTextControls2 = (LinearLayout) findViewById(R.id.llTextControls2);

        seekbarHeightContainer = (RelativeLayout) findViewById(R.id.seekbarHeightContainer);
        seekBarHeight = (SeekBar) findViewById(R.id.sbHeight);
        txtHeight = (TextView) findViewById(R.id.txtHeight);

        seekbarSizeContainer = (RelativeLayout) findViewById(R.id.seekbarSizeContainer);
        seekBarSize = (SeekBar) findViewById(R.id.sbSize);
        txtSize = (TextView) findViewById(R.id.txtSize);

        seekbarWidthContainer = (RelativeLayout) findViewById(R.id.seekbarWidthContainer);
        seekBarWidth = (SeekBar) findViewById(R.id.sbWidth);
        txtWidth = (TextView) findViewById(R.id.txtWidth);

        seekbarAlphaContainer = (RelativeLayout) findViewById(R.id.seekbarAlphaContainer);
        seekBarAlpha = (SeekBar) findViewById(R.id.sbAlpha);
        txtAlpha = (TextView) findViewById(R.id.txtAlpha);


        tbtnTextFont = (ToggleButton) findViewById(R.id.tbtnTextFont);
        tbtnTextColour = (ToggleButton) findViewById(R.id.tbtnTextColour);
        tbtnTextTexture = (ToggleButton) findViewById(R.id.tbtnTextTexture);
        tbtnTextEffects = (ToggleButton) findViewById(R.id.tbtnTextEffects);



        //  FONTS  //

        /**  Import three interface fonts **/
        RalewayLight = Typeface.createFromAsset(getAssets(), "interface-fonts/Raleway-Thin.ttf");
        RalewayMedium = Typeface.createFromAsset(getAssets(), "interface-fonts/Raleway-Medium.ttf");
        RalewayBold = Typeface.createFromAsset(getAssets(), "interface-fonts/Raleway-Bold.ttf");
        font = Typeface.createFromAsset(getAssets(), "interface-fonts/Raleway-Thin.ttf");

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


        /** SeekBar Description Text fonts  **/
        txtWidth.setTypeface(RalewayLight);
        txtSize.setTypeface(RalewayLight);
        txtHeight.setTypeface(RalewayLight);
        txtAlpha.setTypeface(RalewayLight);

        tbtnShowHideUI.setTypeface(RalewayMedium);

        txtInput.setTypeface(RalewayLight);
        txtMainText.setTypeface(font);



        /**
         *  Setup the screen buttons.  These will be replaced with a gesture touch system.
         */
        tbtnBackground.setOnClickListener(listener);
        tbtnText.setOnClickListener(listener);
        tbtnShowHideUI.setOnClickListener(listener);
        tbtnShadow.setOnClickListener(listener);

        btnTextAlignLeft.setOnClickListener(listener);
        btnTextAlignCenter.setOnClickListener(listener);
        btnTextAlignRight.setOnClickListener(listener);


        /**
         * Changing the font of the main text to something.
         */
        txtInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                removeSoftMenuBar();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                txtMainText.setText(txtInput.getText());
                removeSoftMenuBar();

            }
        });


        BackgroundGallery();
        FontGallery();
        FontColourGallery();
        TextureGallery();

        /**  Setup the SeekBar Controllers **/
        SeekBarController seekBarHeightController = new SeekBarController();
        seekBarHeightController.seekBarMethod(seekBarHeight, txtMainText);

        SeekBarController seekBarSizeController = new SeekBarController();
        seekBarSizeController.seekBarMethod(seekBarSize, txtMainText);

        SeekBarController seekBarWidthController = new SeekBarController();
        seekBarWidthController.seekBarMethod(seekBarWidth, txtMainText);

        SeekBarController seekBarAlphaController = new SeekBarController();
        seekBarAlphaController.seekBarMethod(seekBarAlpha, txtMainText);


//        /**
//         *   OnTouch listener for moving the txtMainText around the screen
//         */
//        txtMainText.setOnTouchListener(new TextView.OnTouchListener() {
//
//            public boolean onTouch(View v, MotionEvent event) {
//                drag(event, v);
//                return true;
//            }
//        });


        txtMainText.setOnTouchListener(new MultiTouchListener());


    }  /**  END OF ONCREATE  **/



    /** Method to remove the Soft MenuBar **/
    public void removeSoftMenuBar() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }


//    /**
//     * Drag method for anything to enable anything to be scrolled around the screen.
//     *
//     * @param event
//     * @param v
//     */
//    float dx=0,dy=0,x=0,y=0;
//
//    public boolean drag(MotionEvent event, View v) {
//
//        switch (event.getAction()) {
//
//            case MotionEvent.ACTION_DOWN: {
//
//                x = event.getRawX();
//                y = event.getRawY();
//                dx = x - v.getX();
//                dy = y - v.getY();
//
//            }
//            break;
//
//
//            case MotionEvent.ACTION_MOVE: {
//
//                v.setX(event.getRawX() - dx);
//                v.setY(event.getRawY() - dy);
//
//            }
//            break;
//
//        }
//
//        return true;
//
//    }



    View.OnClickListener listenerText = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.tbtnTextFont:

                    if (tbtnTextFont.isChecked()) {

                        mRecyclerViewFonts = (RecyclerView) findViewById(R.id.recyclerViewFonts);
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
                        recyclerViewDisappear(mRecyclerViewBackground);
                        recyclerViewDisappear(mRecyclerViewTextColours);
                        recyclerViewDisappear(mRecyclerViewTexture);

                    } else {

                        Log.i("Button Press", "Menu Close - Font");

                        //  Remove the recyclerView for the Text | Fonts selection
                        recyclerViewDisappear(mRecyclerViewFonts);

                        //  Set the "Font" toggle button to unchecked
                        setToggleButtonToUnchecked(tbtnTextFont);

                        // check of the any of the other toggle button is "on" and set them to off, removing the relevant recyclerView
                        recyclerViewDisappear(mRecyclerViewBackground);
                        recyclerViewDisappear(mRecyclerViewTextColours);
                        recyclerViewDisappear(mRecyclerViewTexture);

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
                        recyclerViewDisappear(mRecyclerViewBackground);
                        recyclerViewDisappear(mRecyclerViewFonts);
                        recyclerViewDisappear(mRecyclerViewTexture);


                    } else {

                        Log.i("Button Press", "Menu Close - Colour");

                        // Remove the recyclerView for the Text | Colours selection
                        recyclerViewDisappear(mRecyclerViewTextColours);

                        //  Set the "Colour" toggle button to unchecked
                        setToggleButtonToUnchecked(tbtnTextColour);

                        // check of the any of the other toggle button is "on" and set them to off, removing the relevant recyclerView
                        recyclerViewDisappear(mRecyclerViewBackground);
                        recyclerViewDisappear(mRecyclerViewFonts);
                        recyclerViewDisappear(mRecyclerViewTexture);

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
                        recyclerViewDisappear(mRecyclerViewBackground);
                        recyclerViewDisappear(mRecyclerViewFonts);
                        recyclerViewDisappear(mRecyclerViewTextColours);


                    } else {

                        Log.i("Button Press", "Menu Close - Texture");

                        // Remove the recyclerView for the Text | Colours selection
                        recyclerViewDisappear(mRecyclerViewTexture);

                        //  Set the "Colour" toggle button to unchecked
                        setToggleButtonToUnchecked(tbtnTextTexture);

                        // check of the any of the other toggle button is "on" and set them to off, removing the relevant recyclerView
                        recyclerViewDisappear(mRecyclerViewBackground);
                        recyclerViewDisappear(mRecyclerViewFonts);
                        recyclerViewDisappear(mRecyclerViewTextColours);


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


    /**
     *
     * @param recyclerView
     * @param toggleButton
     */
    public void toggleButtonChecked(RecyclerView recyclerView, ToggleButton toggleButton) {

        if (toggleButton.isChecked() || recyclerView.getVisibility() == View.VISIBLE) {

            recyclerView.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.fadeout));
            recyclerView.setVisibility(View.GONE);
            toggleButton.setTypeface(RalewayMedium);
            toggleButton.setTextColor(getResources().getColor(R.color.toggleButtonText_Off));
            toggleButton.setBackgroundColor(getResources().getColor(R.color.toggleButtonBackground_Off));
            toggleButton.setChecked(false);
        }
    }

    /**
     *
     * @param toggleButton
     */
    public void setToggleButtonToChecked(ToggleButton toggleButton) {

            toggleButton.setTypeface(RalewayBold);
            toggleButton.setTextColor(getResources().getColor(R.color.toggleButtonText_On));
            toggleButton.setBackgroundColor(getResources().getColor(R.color.toggleButtonBackground_On));
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
            toggleButton.setTextColor(getResources().getColor(R.color.toggleButtonText_Off));
            toggleButton.setBackgroundColor(getResources().getColor(R.color.toggleButtonBackground_Off));
            toggleButton.setChecked(false);

    }


    /**
     *
     * @param menuTitle
     */
    public void removeMenu (LinearLayout menuTitle) {

        if (menuTitle.getVisibility() == View.VISIBLE) {
            menuTitle.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.fadeout));
            menuTitle.setVisibility(View.GONE);
        }

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


    public void TBAnimateOut(ToggleButton toggleButton) {

        if (toggleButton.getVisibility() == View.VISIBLE) {

            toggleButton.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.slide_down_out));
            toggleButton.setVisibility(View.GONE);
            setToggleButtonToUnchecked(toggleButton);

        }
    }

    public void TBAnimateIn(ToggleButton toggleButton) {

        if (toggleButton.getVisibility() != View.VISIBLE) {


            toggleButton.setVisibility(View.VISIBLE);
            Animation animation = AnimationUtils.loadAnimation(CreateNew.this, R.anim.slide_up_in);
            animation.setDuration(1000);
            animation.setStartOffset(100);

            toggleButton.startAnimation(animation);

//        toggleButton.setVisibility(View.VISIBLE);
//        toggleButton.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.slide_up_in));

        }

    }

    public void BAnimateOut(Button button) {

        if (button.getVisibility() == View.VISIBLE) {

            button.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.slide_down_out));
            button.setVisibility(View.GONE);
        }
    }

    public void BAnimateIn(Button button) {

        if (button.getVisibility() != View.VISIBLE) {

            button.setVisibility(View.VISIBLE);
            button.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.slide_up_in));
        }
    }


    /**
     * Button Actions for the text controls.  Will be changed soon.
     */
    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.tbtnShadow:

                    if (tbtnShadow.isChecked()) {
                        txtMainText.setShadowLayer(25, 1, 1, Color.parseColor("#ffffffff"));
                    } else {
                        txtMainText.setShadowLayer(0, 0, 0, 0);
                    }
                    break;


                case R.id.btnTextAlignLeft:
                    txtMainText.setGravity(View.TEXT_ALIGNMENT_VIEW_END);
                    break;


                case R.id.btnTextAlignCenter:
                    txtMainText.setGravity(Gravity.CENTER_HORIZONTAL);
                    break;


                case R.id.btnTextAlignRight:
                    txtMainText.setGravity(View.TEXT_ALIGNMENT_VIEW_START);
                    break;


                case R.id.tbtnShowHideUI:

                    if (tbtnShowHideUI.isChecked()) {

                        Log.i("Button Press", "UI - Hide");

                        tbtnShowHideUI.setAlpha(0.4f);

                        TBAnimateOut(tbtnText);
                        TBAnimateOut(tbtnBackground);
                        TBAnimateOut(tbtnTextFont);
                        TBAnimateOut(tbtnTextColour);
                        BAnimateOut(btnOverlay);
                        BAnimateOut(btnBorder);
                        BAnimateOut(btnShare);


                        if (llTextControls1.getVisibility() == View.VISIBLE) {

                            llTextControls1.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.fadeout));
                            llTextControls1.setVisibility(View.GONE);

                        }


                        if (llTextControls2.getVisibility() == View.VISIBLE) {

                            llTextControls2.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.fadeout));
                            llTextControls2.setVisibility(View.GONE);

                        }

                        txtInput.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.slide_up_out));
                        txtInput.setVisibility(View.INVISIBLE);

                        tbtnShadow.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.slide_left_out));
                        tbtnShadow.setVisibility(View.GONE);

                        btnTextAlignLeft.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.slide_left_out));
                        btnTextAlignLeft.setVisibility(View.GONE);

                        btnTextAlignCenter.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.slide_left_out));
                        btnTextAlignCenter.setVisibility(View.GONE);

                        btnTextAlignRight.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.slide_left_out));
                        btnTextAlignRight.setVisibility(View.GONE);

                        toggleButtonChecked(mRecyclerViewFonts, tbtnTextFont);
                        toggleButtonChecked(mRecyclerViewTextColours, tbtnTextColour);
                        toggleButtonChecked(mRecyclerViewTexture, tbtnTextTexture);
                        toggleButtonChecked(mRecyclerViewBackground, tbtnBackground);

                        if (menuFonts.getVisibility() == View.VISIBLE) {

                            menuFonts.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.fadeout));
                            menuFonts.setVisibility(View.GONE);
                            tbtnTextFont.setChecked(false);
                            tbtnTextColour.setChecked(false);
                            tbtnTextTexture.setChecked(false);
                            tbtnTextEffects.setChecked(false);

                        }

                        if (menuBackgrounds.getVisibility() == View.VISIBLE) {

                            menuBackgrounds.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.fadeout));
                            menuBackgrounds.setVisibility(View.GONE);
                            tbtnBackgroundStandard.setChecked(false);
                            tbtnBackgroundCamera.setChecked(false);
                            tbtnBackgroundOnline.setChecked(false);
                        }

                } else {

                        Log.i("Button Press", "UI - Show");

                    tbtnShowHideUI.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.ui_fadein));
                    tbtnShowHideUI.setAlpha(1);

                        TBAnimateIn(tbtnText);
                        TBAnimateIn(tbtnBackground);
                        TBAnimateIn(btnOverlay);
                        TBAnimateIn(tbtnText);
                        BAnimateIn(btnBorder);
                        BAnimateIn(btnShare);

                        txtInput.setVisibility(View.VISIBLE);
                        txtInput.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.slide_down_in));

                }
                break;



                case R.id.tbtnBackground:


                    if (tbtnBackground.isChecked()) {


                        Log.i("Menu Open", "Background");

                        /** Set the Toggle button "tbtnText" to checked  **/
                        setToggleButtonToChecked(tbtnBackground);
                        setToggleButtonToUnchecked(tbtnText);
                        removeMenu(menuFonts);

                        /** Set the Background menu ToggleButton's to unchecked  **/
                        setToggleButtonToUnchecked(tbtnBackgroundStandard);
                        setToggleButtonToUnchecked(tbtnBackgroundCamera);
                        setToggleButtonToUnchecked(tbtnBackgroundOnline);

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

                        if (llTextControls1.getVisibility() == View.VISIBLE) {

                            llTextControls1.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.fadeout));
                            llTextControls1.setVisibility(View.GONE);

                        }


                        if (llTextControls2.getVisibility() == View.VISIBLE) {

                            llTextControls2.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.fadeout));
                            llTextControls2.setVisibility(View.GONE);

                        }

                        if (tempButtons.getVisibility() == View.VISIBLE) {

                            tempButtons.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.fadeout));
                            tempButtons.setVisibility(View.GONE);

                        }


                        setToggleButtonToUnchecked(tbtnText);




                    } else {

                        Log.i("Menu Close", "Backgrounds");

                        /** Set the Toggle button "tbtnBackground" to unchecked  **/
                        setToggleButtonToUnchecked(tbtnBackground);

                        /**  Remove the "Background" submenu  **/
                        menuBackgrounds.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.fadeout));
                        menuBackgrounds.setVisibility(View.GONE);

                        /**  Set all the Toggle Buttons to "off" **/
                        setToggleButtonToUnchecked(tbtnBackgroundStandard);
                        setToggleButtonToUnchecked(tbtnBackgroundCamera);
                        setToggleButtonToUnchecked(tbtnBackgroundOnline);

                        /**  Remove the listener for the menu buttons  **/
                        tbtnBackgroundStandard.setOnClickListener(null);
                        tbtnBackgroundCamera.setOnClickListener(null);
                        tbtnBackgroundOnline.setOnClickListener(null);

                        toggleButtonChecked(mRecyclerViewFonts, tbtnText);
                        setToggleButtonToUnchecked(tbtnText);

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

                        if (llTextControls1.getVisibility() != View.VISIBLE) {
                            llTextControls1.setVisibility(View.VISIBLE);
                            llTextControls1.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.fadein));
                        }

                        if (llTextControls2.getVisibility() != View.VISIBLE) {
                            llTextControls2.setVisibility(View.VISIBLE);
                            llTextControls2.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.fadein));
                        }

                        if (tempButtons.getVisibility() != View.VISIBLE) {
                            tempButtons.setVisibility(View.VISIBLE);
                            tempButtons.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.fadein));
                        }

                    } else {

                        tbtnTextFont.setOnClickListener(null);
                        tbtnTextColour.setOnClickListener(null);
                        tbtnTextTexture.setOnClickListener(null);
                        tbtnTextEffects.setOnClickListener(null);

                        Log.i("Menu Close", "Text");

                        /** Set the Toggle button "tbtnText" to unchecked  **/
//                        setToggleButtonToUnchecked(tbtnText);

                        tbtnText.setTypeface(RalewayMedium);
                        tbtnText.setTextColor(getResources().getColor(R.color.white_transparent_80));
                        tbtnText.setBackgroundColor(getResources().getColor(R.color.menuButtonOff));
                        tbtnText.setChecked(false);


                        /**  Remove the "Text" submenu  **/
                        menuFonts.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.fadeout));
                        menuFonts.setVisibility(View.GONE);

                        /** Set the Toggle Buttons to off for the above menu  **/
                        setToggleButtonToUnchecked(tbtnTextFont);
                        setToggleButtonToUnchecked(tbtnTextColour);
                        setToggleButtonToUnchecked(tbtnTextTexture);
                        setToggleButtonToUnchecked(tbtnTextEffects);

                        toggleButtonChecked(mRecyclerViewBackground, tbtnBackground);
                        toggleButtonChecked(mRecyclerViewTextColours, tbtnTextColour);
                        toggleButtonChecked(mRecyclerViewFonts, tbtnTextFont);
                        toggleButtonChecked(mRecyclerViewTexture, tbtnTextTexture);

                        if (llTextControls1.getVisibility() == View.VISIBLE) {
                            llTextControls1.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.fadeout));
                            llTextControls1.setVisibility(View.GONE);
                        }


                        if (llTextControls2.getVisibility() == View.VISIBLE) {
                            llTextControls2.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.fadeout));
                            llTextControls2.setVisibility(View.GONE);
                        }

                        tempButtons.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.fadeout));
                        tempButtons.setVisibility(View.GONE);

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
            removeSoftMenuBar();
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
        removeSoftMenuBar();
    }

    @Override
    protected void onResume() {
        super.onResume();
        removeSoftMenuBar();
    }
}
