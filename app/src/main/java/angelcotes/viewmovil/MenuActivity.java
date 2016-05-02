package angelcotes.viewmovil;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import angelcotes.models.BackgroundSound;
import angelcotes.models.GameManagement;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class MenuActivity extends AppCompatActivity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;

    private View mContentView;
    private boolean mVisible;
    private Button btn_play;


    // Game Management instance. Control all the game
    public static GameManagement gameManagement = new GameManagement();

    // Background Sound
    private BackgroundSound backgroundSound = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fullscreen);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mVisible = true;
        mContentView = findViewById(R.id.fullscreen_content);
        btn_play = (Button) findViewById(R.id.btn_play);


        // SOUND EFFECT
        backgroundSound = new BackgroundSound(this, R.raw.background_sound);
        backgroundSound.play();

    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        getSupportActionBar().hide();
    }


    public void play(View view) {
        // Establish attributes for the new game
        gameManagement.newGame();

        Intent intent = new Intent(getApplicationContext(), GameActivity.class);
        startActivity(intent);
    }

    /***********************/
    // SOUND LOGIC
    /***********************/
    @Override
    protected void onPause() {
        super.onPause();
        backgroundSound.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        backgroundSound.resume();
    }

    public void exitGame(View view) {
        this.finish();
    }
}
