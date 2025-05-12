package kr.ac.tukorea.ge.and.great1625.herosurvivor.app;

import android.os.Bundle;

import kr.ac.tukorea.ge.and.great1625.herosurvivor.BuildConfig;
import kr.ac.tukorea.ge.and.great1625.herosurvivor.game.MainScene;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.activity.GameActivity;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.GameView;

public class HeroSurvivorActivity extends GameActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        GameView.drawsDebugStuffs = BuildConfig.DEBUG;
        super.onCreate(savedInstanceState);
        new MainScene().push();
    }
}