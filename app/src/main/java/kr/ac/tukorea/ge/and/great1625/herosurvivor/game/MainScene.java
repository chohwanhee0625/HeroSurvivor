package kr.ac.tukorea.ge.and.great1625.herosurvivor.game;

import android.graphics.Canvas;
import android.view.MotionEvent;

import kr.ac.tukorea.ge.and.great1625.herosurvivor.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.HPBar;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.JoyStick;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.Score;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.GameView;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.Metrics;

public class MainScene extends Scene {
    public final Player player;
    private final JoyStick joyStick;
    public final float backgroundWidth;
    public final float backgroundHeight;
    protected final Score score;
    protected final HPBar hpBar;

    public enum Layer {
        bg1, enemy, item, player, sword, score, ui, controller;
        public static final int COUNT = values().length;
    }

    public MainScene() {
        push();

        initLayers(Layer.COUNT);

        Background bg = new Background(R.mipmap.map_plain_5000);
        backgroundWidth = bg.getWidth();
        backgroundHeight = bg.getHeight();
        add(Layer.bg1, bg);

        this.joyStick = new JoyStick(R.mipmap.joystick_bg, R.mipmap.joystick_thumb, 0, 0, 150, 70, 80);
        add(Layer.controller, joyStick);

        float startX = backgroundWidth  * 0.5f;
        float startY = backgroundHeight * 0.5f;
        this.player = new Player(joyStick, startX, startY);
        add(Layer.player, player);

        add(Layer.controller, new EnemyGenerator((int)backgroundWidth, (int)backgroundHeight));

        score = new Score(R.mipmap.number_24x32, Metrics.width - 50, 50, 50);
        score.setScore(0);
        add(Layer.score, score);

        hpBar = new HPBar(20, 20, 400);
        add(Layer.ui, hpBar);
    }

    // Overridables
    public float camX, camY;
    @Override
    public void update() {
        super.update();
        // 플레이어가 화면 중앙에 오도록 카메라 오프셋 계산
        Player p = player;
        float halfW = Metrics.width  * 0.5f;
        float halfH = Metrics.height * 0.5f;
        camX = p.x - halfW;
        camY = p.y - halfH;
        // 맵 밖으로 나가지 않도록 클램핑
        camX = Math.max(0, Math.min(camX, backgroundWidth  - Metrics.width));
        camY = Math.max(0, Math.min(camY, backgroundHeight - Metrics.height));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return joyStick.onTouch(event);
    }

    @Override
    public boolean onBackPressed() {
//        new PauseScene().push();
        return true;
    }


}
