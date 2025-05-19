package kr.ac.tukorea.ge.and.great1625.herosurvivor.game;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.Log;

import kr.ac.tukorea.ge.and.great1625.herosurvivor.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IBoxCollidable;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.ILayerProvider;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IRecyclable;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.AnimSprite;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.util.Gauge;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.GameView;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.Metrics;

public class Enemy extends AnimSprite implements IRecyclable, IBoxCollidable, ILayerProvider<MainScene.Layer> {
    protected static final String TAG = Enemy.class.getSimpleName();

    private static final float SPEED = 300f;
    private static final float SCALE = 0.5f;
    private static final int[] resIds = {
            R.mipmap.slime01
    };
    //public static final int MAX_LEVEL = resIds.length - 1;
    private int level;
    private int life, maxLife;
    protected RectF collisionRect = new RectF();
    protected static Gauge gauge = new Gauge(0.1f, R.color.enemy_gauge_fg, R.color.enemy_gauge_bg);
    public static Enemy get(int level, float x, float y) {
        return Scene.top().getRecyclable(Enemy.class).init(level, x, y);
    }
    public Enemy() {
        super(0, 0, 0);
    }
    private Enemy init(int level, float x, float y) {
        this.setImageResourceId(resIds[level], 10);
        setPosition(x, y, bitmap.getWidth() * SCALE, bitmap.getHeight() * SCALE);
        Log.d(TAG, "width = " + width + ", height = " + height);
        updateCollisionRect();
        this.level = level;
        this.life = this.maxLife = (level + 1) * 10;
        dy = SPEED;
        return this;
    }

    public int getScore() {
        return (level + 1) * 100;
    }

    public boolean decreaseLife(int power) {
        life -= power;
        return life <= 0;
    }

    @Override
    public void update() {
        //super.update();
        float moveDist = SPEED * GameView.frameTime;

        MainScene scene = (MainScene) Scene.top();
        Player player = scene.player;

        float targetX = player.x;
        float targetY = player.y;

        float vx = targetX - this.x;
        float vy = targetY - this.y;
        float len = (float) Math.sqrt(vx*vx + vy*vy);

        if (len > 0) {
            float dx = (vx / len) * moveDist;
            float dy = (vy / len) * moveDist;

            float nextX = this.x + dx;
            float nextY = this.y + dy;

            float adjX;
            if ((dx < 0 && nextX < targetX) || (dx > 0 && nextX > targetX)) {
                adjX = targetX;
            } else {
                adjX = Math.max(width, Math.min(nextX, Metrics.width  - width));
            }

            float adjY;
            if ((dy < 0 && nextY < targetY) || (dy > 0 && nextY > targetY)) {
                adjY = targetY;
            } else {
                adjY = Math.max(height, Math.min(nextY, Metrics.height - height));
            }

            setPosition(adjX, adjY, width, height);
        }

        updateCollisionRect();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        float gauge_width = width * 0.7f;
        float gauge_x = x - gauge_width / 2;
        float gauge_y = dstRect.bottom;
        gauge.draw(canvas,gauge_x, gauge_y, gauge_width, (float)life / maxLife);
    }

    private void updateCollisionRect() {
        collisionRect.set(dstRect);
        collisionRect.inset(11f, 11f);
    }

    public RectF getCollisionRect() {
        return collisionRect;
    }

    @Override
    public void onRecycle() {
    }

    @Override
    public MainScene.Layer getLayer() {
        return MainScene.Layer.enemy;
    }
}
