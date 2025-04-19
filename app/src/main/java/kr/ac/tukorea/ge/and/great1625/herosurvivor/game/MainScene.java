package kr.ac.tukorea.ge.and.great1625.herosurvivor.game;

import android.view.MotionEvent;

import kr.ac.tukorea.ge.and.great1625.herosurvivor.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.JoyStick;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;

public class MainScene extends Scene {
    private final Player player;
    private final JoyStick joyStick;
    public MainScene() {
        this.joyStick = new JoyStick(R.mipmap.joystick_bg, R.mipmap.joystick_thumb, 100, 1500, 100, 30, 80);
        add(joyStick);

        this.player = new Player(joyStick);
        add(player);
        //add(new EnemyGenerator());
    }

    // Overridables

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return joyStick.onTouch(event);
    }
}
