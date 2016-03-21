package com.moodbanner.dev.any;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
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
import android.view.LayoutInflater;
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

import com.moodbanner.dev.any.JSON.JSONHelper;
import com.moodbanner.dev.any.JSON.JSONParser;
import com.moodbanner.dev.any.backgrounds.MyAdapter;
import com.moodbanner.dev.any.backgrounds.PostValueBackground;
import com.moodbanner.dev.any.listener.RecyclerClickListener;
import com.moodbanner.dev.any.listener.RecyclerTouchListener;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.List;

public class CreateNew extends AppCompatActivity {

    private TextView txtMainText;
    private ImageView createnew_background_image;
    private EditText txtInput;
    Button btnClipArt, btnSave, btnFontBigger, btnFontSmaller, btnMoveTextUp, btnMoveTextDown;
    private ToggleButton tbtnFont, tbtnBackground;
    private Typeface AfterShock, CreatedWorld, AquilineTwo, RalewayMedium, RalewayBold, RalewayLight, PermanentMarker, WCManoNegraBoldBta;
    private RelativeLayout mRelativeLayout;

    private AppCompatActivity activity = CreateNew.this;
    private RecyclerView mRecyclerView;
    private List<PostValueBackground> postList;
    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new);


        AfterShock = Typeface.createFromAsset(getAssets(), "fonts/After_Shok.ttf");
        CreatedWorld = Typeface.createFromAsset(getAssets(), "fonts/ANUDRG__.ttf");
        AquilineTwo = Typeface.createFromAsset(getAssets(), "fonts/AquilineTwo.ttf");
        PermanentMarker = Typeface.createFromAsset(getAssets(), "fonts/PermanentMarker.ttf");
        WCManoNegraBoldBta = Typeface.createFromAsset(getAssets(), "fonts/WCManoNegraBoldBta.otf");

        RalewayLight = Typeface.createFromAsset(getAssets(), "interface-fonts/Raleway-Thin.ttf");
        RalewayMedium = Typeface.createFromAsset(getAssets(), "interface-fonts/Raleway-Medium.ttf");
        RalewayBold = Typeface.createFromAsset(getAssets(), "interface-fonts/Raleway-Bold.ttf");


        txtMainText = (TextView) findViewById(R.id.txtMainText);
        txtMainText.setLineSpacing(1, 0.5f);

        txtInput = (EditText) findViewById(R.id.etInput);
        tbtnFont = (ToggleButton) findViewById(R.id.tbtnFont);
        tbtnFont.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.slide_up));
        tbtnBackground = (ToggleButton) findViewById(R.id.tbtnBackground);
        tbtnBackground.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.slide_up));
        btnClipArt = (Button) findViewById(R.id.btnClipArt);
        btnClipArt.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.slide_up));
        btnSave = (Button) findViewById(R.id.btnSave);
        btnFontBigger = (Button) findViewById(R.id.btnFontBigger);
        btnFontSmaller = (Button) findViewById(R.id.btnFontSmaller);
        btnMoveTextUp = (Button) findViewById(R.id.btnMoveTextUp);
        btnMoveTextDown = (Button) findViewById(R.id.btnMoveTextDown);
        btnSave.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.slide_up));
        mRelativeLayout = (RelativeLayout) findViewById(R.id.create_new_container);

        tbtnFont.setText("FONT");
        tbtnBackground.setText("BACKGROUND");


        btnSave.setTypeface(RalewayMedium);
        tbtnFont.setTypeface(RalewayMedium);
        tbtnBackground.setTypeface(RalewayMedium);
        btnClipArt.setTypeface(RalewayMedium);

        Shader shader = new LinearGradient(0, 0, 0, txtMainText.getTextSize(), Color.RED, Color.BLUE, Shader.TileMode.MIRROR);
        txtMainText.getPaint().setShader(shader);
        txtMainText.setShadowLayer(1.5f, -1, 1, Color.LTGRAY);

        txtMainText.setTypeface(WCManoNegraBoldBta);


        /**
         *  Setup the screen buttons.  These will be replaced with a gesture touch system.
         */
        btnFontBigger.setOnClickListener(listener);
        btnFontSmaller.setOnClickListener(listener);
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

    }


    /**
     *   Controls the display of the background gallery popout.
     */
    public void BackgroundGallery() {

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(activity, mRecyclerView, new RecyclerClickListener() {

            @Override
            public void onClick(View view, int position) {

                if (postList != null) {

                    Log.i("Background: ", postList.get(position).getName());

                    Picasso.with(mContext).load(postList.get(position).getImageURL()).into(createnew_background_image);
                    createnew_background_image.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.zoom_out));

                }
            }
        }));
    }



    /**
     *   Button Actions for the text controls.  Will be changed soon.
     */
    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.btnFontBigger:
                    Log.i("Button Press","INCREASE Font Size");
                    txtMainText.setTextSize(TypedValue.COMPLEX_UNIT_PX, txtMainText.getTextSize() + 10);
                    break;

                case R.id.btnFontSmaller:
                    Log.i("Button Press","DECREASE Font Size");
                    txtMainText.setTextSize(TypedValue.COMPLEX_UNIT_PX, txtMainText.getTextSize() - 10);
                    break;

                case R.id.btnMoveTextUp:
                    Log.i("Button Press","Move text UP");
                    txtMainText.setY(txtMainText.getY() - 40f);
                    break;

                case R.id.btnMoveTextDown:
                    Log.i("Button Press","Move text DOWN");
                    txtMainText.setY(txtMainText.getY() + 40f);
                    break;

                case R.id.tbtnBackground:

                    mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
                    createnew_background_image = (ImageView) findViewById(R.id.createnew_background_image);
                    LinearLayoutManager mLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);

                    if (tbtnBackground.isChecked()) {

                        Log.i("Menu Open: ", "Backgrounds");

                        new JSONAsync().execute();

                        mRecyclerView.setLayoutManager(mLayoutManager);
                        mRecyclerView.setVisibility(View.VISIBLE);
                        mRecyclerView.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.fadein));


                        WindowManager w = getWindowManager();
                        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(w.getDefaultDisplay().getWidth(), 520);
                        params.topMargin = (w.getDefaultDisplay().getHeight() - 695);
                        tbtnBackground.setText("BACKGROUND");
                        tbtnBackground.setTypeface(RalewayBold);
                        tbtnBackground.setTextColor(getResources().getColor(R.color.corporate_blue));
                        tbtnBackground.setBackgroundResource(R.drawable.menubar_button_active);
                    } else {

                        Log.i("Menu Close: ", "Backgrounds");
                        mRecyclerView.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.fadeout));
                        mRecyclerView.setVisibility(View.GONE);
                        tbtnBackground.setText("BACKGROUND");
                        tbtnBackground.setTypeface(RalewayMedium);
                        tbtnBackground.setTextColor(getResources().getColor(R.color.black));
                        tbtnBackground.setBackgroundResource(R.drawable.text_input_background);
                    }

                    break;

                case R.id.tbtnFont:


//                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.circles);
//                    Shader shader = new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
//                    txtMainText.getPaint().setShader(shader);

                    LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View addView = layoutInflater.inflate(R.layout.activity_font_selection, null);

                    if (tbtnFont.isChecked()) {

                        Log.i("Menu Open", "Fonts");

                        addView.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.fadein));

                        WindowManager w = getWindowManager();
                        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(w.getDefaultDisplay().getWidth(), 210);
                        params.topMargin = (w.getDefaultDisplay().getHeight() - 385);
                        mRelativeLayout.addView(addView, params);
                        tbtnFont.setText("FONT");
                        tbtnFont.setTextColor(getResources().getColor(R.color.corporate_blue));
                        tbtnFont.setBackgroundResource(R.drawable.menubar_button_active);

                    } else {

                        Log.i("Menu Close", "Fonts");

                        addView.startAnimation(AnimationUtils.loadAnimation(CreateNew.this, R.anim.fadeout));

                        tbtnFont.setText("FONT");
                        tbtnFont.setTextColor(getResources().getColor(R.color.black));
                        tbtnFont.setBackgroundResource(R.drawable.text_input_background);
                    }

                    break;

                default:
                    break;
            }
        }
    };



    /**
     *  get the JSON data and do stuff with it.
     */
    class JSONAsync extends AsyncTask<Void, Void, Void> {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            pd = ProgressDialog.show(CreateNew.this, null, "Loading Backgrounds ...", true, false);
        }

        @Override
        protected Void doInBackground(Void... params) {
            JSONObject jsonObject = new JSONHelper().getJSONFromUrl();
            postList = new JSONParser().parse(jsonObject);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            MyAdapter myAdapter = new MyAdapter(activity, postList);
            mRecyclerView.setAdapter(myAdapter);
            pd.dismiss();
        }
    }
}
