package com.test.quandoo.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.widget.Button;

import com.test.quandoo.R;
import com.test.quandoo.controller.GOLConfig;
import com.test.quandoo.view.base.BaseActivity;
import com.test.quandoo.view.components.GOLRenderingView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * This activity handels the main game activity.
 */
public class GOLHomeActivity extends BaseActivity {

    @BindView(R.id.buttonStartGame) Button buttonStartGame;
    @BindView(R.id.buttonStopGame) Button buttonStopGame;
    @BindView(R.id.buttonSettingsGame) Button buttonSettingsGame;
    @BindView(R.id.viewGOLRendering) GOLRenderingView golRenderingView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // get device width and height BEFORE initializing anything else
        int[] screenDimens = getWindowDimensions();
        GOLConfig.setViewportWidth(screenDimens[0]);
        GOLConfig.setViewportHeight((int) (screenDimens[1] - getResources().getDimension(R.dimen.control_btns_height)));

        setContentView(R.layout.activity_home_gol);
        ButterKnife.bind(this);

    }

    /**
     * Start the game simulation
     */
    private void startGame() {
        if(golRenderingView != null) {
            golRenderingView.lock();
            golRenderingView.runLoop();
        }
    }
    /**
     * Stop the game simulation
     */
    private void stopGame() {
        if(golRenderingView != null) {
            golRenderingView.unlock();
        }
    }

    /**
     * On Start button clicked
     */
    @OnClick(R.id.buttonStartGame)
    protected void onstartButtonClicked() {
        startGame();
    }

    /**
     * On Stop button clicked
     */
    @OnClick(R.id.buttonStopGame)
    protected void onStopButtonClicked() {
        stopGame();
    }

    /**
     * On Settings button clicked, open rule editor activity
     */
    @OnClick(R.id.buttonSettingsGame)
    protected void onSettingsButtonClicked() {
        golRenderingView.unlock();
        final Intent intent = new Intent(
                getApplicationContext(),
                RuleEditorActivity.class);

        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // in case of any interruption, stop the game
        stopGame();
    }
}
