package kr.ac.tukorea.ge.and.great1625.herosurvivor.game;

import android.view.MotionEvent;

import kr.ac.tukorea.ge.and.great1625.herosurvivor.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.Background;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.JoyStick;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;

public class MainScene extends Scene {
    private final Player player;
    private final JoyStick joyStick;

    public enum Layer {
        bg1, enemy, item, player, ui, controller;
        public static final int COUNT = values().length;
    }

    public MainScene() {
        initLayers(Layer.COUNT);

        add(Layer.bg1, new Background(R.mipmap.map_plain));

        this.joyStick = new JoyStick(R.mipmap.joystick_bg, R.mipmap.joystick_thumb, 0, 0, 150, 70, 80);
        add(Layer.controller, joyStick);

        this.player = new Player(joyStick);
        add(Layer.player, player);
        //add(new EnemyGenerator());
    }

    // Overridables

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return joyStick.onTouch(event);
    }
}
