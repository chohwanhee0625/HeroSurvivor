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

    private static final float SPEED = 250f;
    private static final float WIDTH = 100f;
    private static final float HEIGHT = 100f;
    private static final int[] resIds = {
            R.mipmap.slime01, R.mipmap.slime02, R.mipmap.golem01, R.mipmap.golem02, R.mipmap.float_golem01,
            R.mipmap.float_golem02, R.mipmap.dragon01, R.mipmap.dragon02, R.mipmap.float_dragon01
    };
    private static final int[] resIds_rev = {
            R.mipmap.slime01_rev, R.mipmap.slime02_rev, R.mipmap.golem01_rev, R.mipmap.golem02_rev, R.mipmap.float_golem01_rev,
            R.mipmap.float_golem02_rev, R.mipmap.dragon01_rev, R.mipmap.dragon02_rev, R.mipmap.float_dragon01_rev
    };

    public static final int MAX_TYPE = resIds.length;

    //public static final int MAX_LEVEL = resIds.length - 1;
    private int type;
    private int life, maxLife;
    protected RectF collisionRect = new RectF();
    protected static Gauge gauge = new Gauge(0.1f, R.color.enemy_gauge_fg, R.color.enemy_gauge_bg);
    public static Enemy get(int level, float x, float y) {
        return Scene.top().getRecyclable(Enemy.class).init(level, x, y);
    }
    public Enemy() {
        super( R.mipmap.slime01, 5, 4);
        setPosition(0, 0, WIDTH, HEIGHT);
    }

    public static Enemy get(int type, int x, int y) {
        return Scene.top().getRecyclable(Enemy.class).init(type, x, y);
    }

    private Enemy init(int type, float x, float y) {
        setPosition(x, y);
        setImageResourceId(resIds[type]);
        setFrameInfo(4);
        this.type = type;
        Log.d(TAG, "width = " + width + ", height = " + height);
        updateCollisionRect();
        this.life = this.maxLife = 100;
        return this;
    }

    public int getScore() {
        return (type + 1) * 100;
    }

    public boolean decreaseLife(int power) {
        life -= power;
        return life <= 0;
    }

    public int score() {
        return 50;
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

            if (dx > 0) {
                setImageResourceId(resIds_rev[type]);
            } else if (dx < 0) {
                setImageResourceId(resIds[type]);
            }

            float nextX = this.x + dx;
            float nextY = this.y + dy;

            float adjX;
            if ((dx < 0 && nextX < targetX) || (dx > 0 && nextX > targetX)) {
                adjX = targetX;
            } else {
                adjX = Math.max(0, Math.min(nextX, scene.backgroundWidth - width));
            }

            float adjY;
            if ((dy < 0 && nextY < targetY) || (dy > 0 && nextY > targetY)) {
                adjY = targetY;
            } else {
                adjY = Math.max(0, Math.min(nextY, scene.backgroundHeight - height));
            }

            setPosition(adjX, adjY);
        }

        updateCollisionRect();
    }

    @Override
    public void draw(Canvas canvas) {
        MainScene scene = (MainScene) Scene.top();
        float camX = scene.camX;
        float camY = scene.camY;

        canvas.save();
        canvas.translate(-camX, -camY);
        super.draw(canvas);
        float gauge_width = WIDTH * 0.7f;
        float gauge_x = x - gauge_width / 2;
        float gauge_y = dstRect.bottom;
        gauge.draw(canvas,gauge_x, gauge_y, gauge_width, (float)life / maxLife);
        canvas.restore();
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
