package kr.ac.tukorea.ge.and.great1625.herosurvivor.game;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;


import kr.ac.tukorea.ge.and.great1625.herosurvivor.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.AnimSprite;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.JoyStick;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.util.Gauge;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.GameView;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.Metrics;

public class Player extends AnimSprite {
    public enum State {
        idle, attack
    }
    private static final float PLAYER_WIDTH = 160f;
    private static final float PLAYER_HEIGHT = PLAYER_WIDTH;
    private static final float SPEED = 300f;
    public float x;
    public float y;
    private float angle;
    private final JoyStick joyStick;
    protected static Gauge gauge = new Gauge(0.1f, R.color.player_gauge_fg, R.color.player_gauge_bg);

    public Player(JoyStick joyStick, float startX, float startY) {
        super(R.mipmap.player_idle, 5, 4);
        this.joyStick = joyStick;
        setPosition(startX, startY);
        angle = -90;
    }

    @Override
    public void update() {
        if (joyStick.power <= 0) {
            return;
        }

        float distance = SPEED * joyStick.power * GameView.frameTime;

        final int way = 8;
        final double TWO_PI = Math.PI * 2;
        float eightWayAngle = (float) (Math.round(way * joyStick.angle_radian / TWO_PI) * TWO_PI / way);
        float dx = (float) (distance * Math.cos(eightWayAngle));
        float dy = (float) (distance * Math.sin(eightWayAngle));
        x += dx;
        y += dy;

        if (dx > 0) {
            setImageResourceId(R.mipmap.player_idle_rev);
        } else if (dx < 0) {
            setImageResourceId(R.mipmap.player_idle);
        }

        float w_r = PLAYER_WIDTH / 2f;
        float h_r = PLAYER_HEIGHT / 2f;

        MainScene scene = (MainScene) Scene.top();
        x = Math.max(w_r, Math.min(x, scene.backgroundWidth - w_r));
        y = Math.max(h_r, Math.min(y, scene.backgroundHeight - h_r));

        setPosition(x, y);

        angle = (float) Math.toDegrees(eightWayAngle) + 90;
        super.update();
    }

    @Override
    public void draw(Canvas canvas) {
        MainScene scene = (MainScene) Scene.top();
        float camX = scene.camX;
        float camY = scene.camY;

        canvas.save();
        canvas.translate(-camX, -camY);
        super.draw(canvas);
        canvas.restore();
    }

    public void setPosition(float x, float y) {
        float w_r = PLAYER_WIDTH / 2;
        float h_r = PLAYER_HEIGHT / 2;
        dstRect.set(x-w_r, y-h_r, x+w_r, y+h_r);

        this.x = x;
        this.y = y;
        //Log.d(TAG, "x=" + x + " y=" + y + " rect=" + dstRect);
    }

    public boolean onTouch(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
                return true;

        }
        return false;
    }
}
