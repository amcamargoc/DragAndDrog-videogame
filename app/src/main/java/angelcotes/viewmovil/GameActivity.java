package angelcotes.viewmovil;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import angelcotes.models.BackgroundSound;
import angelcotes.models.Bill;
import angelcotes.models.CashRegister;
import angelcotes.models.EffectSoundManager;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class GameActivity extends AppCompatActivity {

    // DON'T TOUCH IT
    private static final boolean AUTO_HIDE = true;
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;

    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    private View mControlsView;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            mControlsView.setVisibility(View.VISIBLE);
        }
    };
    private boolean mVisible;


    // CUSTOM CODE. DO BY ALBERTO CAMARGO AND ANGEL COTES

    // Object dragged
    Bill billDragged;

    // Bills manage
    public static CashRegister cashRegister;

    // Elements that can be dragged
    Button bill_1000, bill_2000, bill_5000, bill_10000, bill_20000, bill_50000, bill_100000;
    ImageButton enableSound;
    public static TextView money, toPay;
    public static boolean sound = true;

    // Value of element dragged
    int value_bill;

    // Background Sound
    private BackgroundSound backgroundSound = null;
    private EffectSoundManager effectSoundManager = null;

    // Target drop element
    public static Button  target;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mVisible = true;
        mContentView = findViewById(R.id.fullscreen_content);

        initializeElements();
        setTags();

        //Show values
        setValues();


        // SOUND EFFECT
        effectSoundManager = new EffectSoundManager(this);
        effectSoundManager.addEffect("cash register", R.raw.cash_register);
        effectSoundManager.addEffect("lost", R.raw.error);
        effectSoundManager.addEffect("ta-da", R.raw.ta_da);

        backgroundSound = new BackgroundSound(this, R.raw.background_sound);
        backgroundSound.play();


        // Button event for drag
        bill_1000.setOnLongClickListener(longListener);
        bill_2000.setOnLongClickListener(longListener);
        bill_5000.setOnLongClickListener(longListener);
        bill_10000.setOnLongClickListener(longListener);
        bill_20000.setOnLongClickListener(longListener);
        bill_50000.setOnLongClickListener(longListener);
        bill_100000.setOnLongClickListener(longListener);


        // Element where you can  action drop
        target.setOnDragListener(dragListener);

    }


    public static void setValues() {
        GameActivity.cashRegister = new CashRegister();
        money.setText("Paid $" + GameActivity.cashRegister.getTotal());
        toPay.setText("Total price $" + MenuActivity.gameManagement.getMoneyToPay());
    }


    private void initializeElements() {
        // Bills
        bill_1000 = (Button) findViewById(R.id.one_thousand);
        bill_2000 = (Button) findViewById(R.id.two_thousand);
        bill_5000 = (Button) findViewById(R.id.five_thousand);
        bill_10000 = (Button) findViewById(R.id.ten_thousand);
        bill_20000 = (Button) findViewById(R.id.twenty_thousand);
        bill_50000 = (Button) findViewById(R.id.fifty_thousand);
        bill_100000 = (Button) findViewById(R.id.one_hundred_thousand);

        money = (TextView) findViewById(R.id.money);
        toPay = (TextView) findViewById(R.id.toPay);
        target = (Button) findViewById(R.id.target);

        enableSound = (ImageButton) findViewById(R.id.enableSound);
    }

    private void setTags() {
        bill_1000.setTag(1000);
        bill_2000.setTag(2000);
        bill_5000.setTag(5000);
        bill_10000.setTag(10000);
        bill_20000.setTag(20000);
        bill_50000.setTag(50000);
        bill_100000.setTag(100000);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        getSupportActionBar().hide();
    }

    /***********************/
    // SOUND LOGIC
    /***********************/
    @Override
    protected void onPause() {
        super.onPause();
        backgroundSound.pause();
        effectSoundManager.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        backgroundSound.resume();
        effectSoundManager.resume();
    }


    /***********************/
    /* Drag and Drog LOGIC */
    /***********************/

    View.OnLongClickListener longListener = new View.OnLongClickListener() {

        @Override
        public boolean onLongClick(View v) {

            ClipData data = ClipData.newPlainText("", "");
            DragShadow dragShadow = new DragShadow(v);
            v.startDrag(data, dragShadow, v, 0);

            return true;
        }
    };

    View.OnDragListener dragListener = new View.OnDragListener()
    {
        @Override
        public boolean onDrag(View v, DragEvent event)
        {
            int dragEvent = event.getAction();
            Button dropText = (Button) v;

            switch(dragEvent)
            {
                case DragEvent.ACTION_DRAG_ENTERED:
                    //dropText.setTextColor(Color.GREEN);
                    Log.d("DEBUG", "Entroo");
                    break;

                case DragEvent.ACTION_DRAG_EXITED:
                    //dropText.setTextColor(Color.BLACK);
                    Log.d("DEBUG", "SAlio");
                    break;

                case DragEvent.ACTION_DROP:
                    //dropText.setTextColor(Color.BLACK);
                    //TextView draggedText = (TextView)event.getLocalState();
                    //dropText.setText(draggedText.getText());

                    // The bill is dropped in accepted area. instance bill and save in cash register
                    billDragged = new Bill(value_bill);
                    cashRegister.addBill(billDragged);

                    // Update total money
                    money.setText("Paid $" + cashRegister.getTotal());

                    // EFFECT SOUND
                    effectSoundManager.playEffect("cash register");

                    // Bill dropped
                    setBackground();



                    // Ask if the player win, lost or continue playing
                    if (MenuActivity.gameManagement.gameStatus(cashRegister) == 1) {
                        // WIN!!!!!!!
                        effectSoundManager.playEffect("ta-da");


                        // Menu for reset game
                        ContinueDialogFragment continueDialogFragment = new ContinueDialogFragment();
                        continueDialogFragment.setvalues("Has Ganado!", "Seguir jugando", GameActivity.this);
                        continueDialogFragment.show(getSupportFragmentManager(), "");

                        // Reset Menu
                        //MenuActivity.gameManagement.newGame();
                        //setValues();


                    } else if (MenuActivity.gameManagement.gameStatus(cashRegister) == -1){
                        // LOST!!
                        effectSoundManager.playEffect("lost");

                        // Menu for reset game
                        ContinueDialogFragment continueDialogFragment = new ContinueDialogFragment();
                        continueDialogFragment.setvalues("Has ingresado una cantidad incorrecta. Intentalo nuevamente", "Volver a intentar", GameActivity.this);
                        continueDialogFragment.show(getSupportFragmentManager(), "");

                        //MenuActivity.gameManagement.newGame();
                        //setValues();

                    }

                    // logs
                    Log.d("MONEY SAVE!", cashRegister.getTotal() + "");
                    Log.d("DEBUG", "Soltooooo");

                    break;
            }

            return true;
        }

    };

    public void backToMenu(View view) {
        this.finish();
    }

    public void setSound(View view) {

        if (sound) {
            backgroundSound.mute();
            effectSoundManager.mute();
            enableSound.setImageResource(R.drawable.sin);
            sound = false;
        } else {
            backgroundSound.unmute();
            effectSoundManager.unmute();
            enableSound.setImageResource(R.drawable.tecn);
            sound = true;
        }
    }


    private class DragShadow extends View.DragShadowBuilder {
        ColorDrawable greyBox;

        public DragShadow(View view) {
            super(view);
            greyBox =  new ColorDrawable(Color.RED);
            Log.d("BILL",  view.getTag() + "");
            value_bill = (Integer) view.getTag();
        }

        @Override
        public void onDrawShadow(Canvas canvas) {
            greyBox.draw(canvas);
            super.onDrawShadow(canvas);
        }

        @Override
        public void onProvideShadowMetrics(Point shadowSize, Point shadowTouchPoint) {
            View v = getView();

            int height = v.getHeight();
            int width = v.getWidth();


            greyBox.setBounds(0, 0, width, height);

            super.onProvideShadowMetrics(shadowSize, shadowTouchPoint);

            shadowSize.set(width, height);
            shadowTouchPoint.set(width/2, height/2);
        }
    }


    public void setBackground() {
        int id = 0;
        switch(value_bill) {
            case 1000:
                id = R.mipmap.mil;
                break;
            case 2000:
                id = R.mipmap.dos;
                break;
            case 5000:
                id = R.mipmap.cinco;
                break;
            case 10000:
                id = R.mipmap.diez;
                break;
            case 20000:
                id = R.mipmap.veinte;
                break;
            case 50000:
                id = R.mipmap.cincuenta;
                break;
            case 100000:
                id = R.mipmap.cien;
                break;
        }
        target.setBackgroundDrawable(getResources().getDrawable(id));

    }


}
