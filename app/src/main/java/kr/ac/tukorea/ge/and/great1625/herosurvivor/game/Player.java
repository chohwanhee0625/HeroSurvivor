package kr.ac.tukorea.ge.and.great1625.herosurvivor.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.view.MotionEvent;


import kr.ac.tukorea.ge.and.great1625.herosurvivor.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.JoyStick;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.GameView;

public class Player extends Sprite {
    private static final float PLAYER_WIDTH = 80.f;
    private static final float PLAYER_HEIGHT = PLAYER_WIDTH * 348.f / 184.f;
    private static final float SPEED = 800f;
    public float x;
    public float y;
    private float angle;
    private static final float FIRE_INTERVAL = 0.25f;
    private float fireCoolTime = FIRE_INTERVAL;
    private static final float BULLET_OFFSET = 80f;

    private static final float SPARK_OFFSET = 66f;
    private static final float SPARK_DURATION = 0.1f;
    private static final float SPARK_WIDTH = 115f;
    private static final float SPARK_HEIGHT = SPARK_WIDTH * 3 / 5;
    private RectF sparkRect = new RectF();
    private Bitmap sparkBitmap;
    private final JoyStick joyStick;

    public Player(JoyStick joyStick, float startX, float startY) {
        super(R.mipmap.avatar09);
        this.joyStick = joyStick;
        setPosition(startX, startY);
        angle = -90;
    }

    @Override
    public void update() {
        if (joyStick.power <= 0) {
            return;
        }

//        float distance = SPEED * GameView.frameTime;
        float distance = SPEED * joyStick.power * GameView.frameTime;

        final int way = 8;
        final double TWO_PI = Math.PI * 2;
        float eightWayAngle = (float) (Math.round(way * joyStick.angle_radian / TWO_PI) * TWO_PI / way);
        x += (float) (distance * Math.cos(eightWayAngle));
        y += (float) (distance * Math.sin(eightWayAngle));

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
        super.draw(canvas);
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
