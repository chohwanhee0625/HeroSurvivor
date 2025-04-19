package kr.ac.tukorea.ge.and.great1625.herosurvivor.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.view.MotionEvent;


import kr.ac.tukorea.ge.and.great1625.herosurvivor.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.JoyStick;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.res.BitmapPool;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.util.RectUtil;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.GameView;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.Metrics;

public class Player extends Sprite {
    private static final float PLANE_WIDTH = 150f;
    private static final float PLANE_HEIGHT = PLANE_WIDTH * 348 / 184;
    private static final float SPEED = 800f;
    private float x, y, angle;
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

    public Player(JoyStick joyStick) {
        super(R.mipmap.avatar09);
        setPosition(Metrics.width / 2, Metrics.height - 200, PLANE_WIDTH, PLANE_HEIGHT);
        this.joyStick = joyStick;

        float x = Metrics.width / 2;
        float y = 2 * Metrics.height / 3;
        setPosition(x, y);
        angle = -90;
    }

    @Override
    public void update() {


        if (joyStick.power <= 0) {
            return;
        }

        float distance = SPEED * GameView.frameTime;

        final int way = 8;
        final double TWO_PI = Math.PI * 2;
        float eightWayAngle = (float) (Math.round(way * joyStick.angle_radian / TWO_PI) * TWO_PI / way);
        x += (float) (distance * Math.cos(eightWayAngle));
        y += (float) (distance * Math.sin(eightWayAngle));

        setPosition(x, y);

        angle = (float) Math.toDegrees(eightWayAngle) + 90;
        super.update();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    public void setPosition(float x, float y) {
        float r = 125f;
        dstRect.set(x-r, y-r, x+r, y+r);
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
