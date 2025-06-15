package kr.ac.tukorea.ge.and.great1625.herosurvivor.game;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;


import java.util.ArrayList;

import kr.ac.tukorea.ge.and.great1625.herosurvivor.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IBoxCollidable;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.ILayerProvider;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.AnimSprite;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.JoyStick;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.res.Sound;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.util.CollisionHelper;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.util.Gauge;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.GameView;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.Metrics;

public class Player extends AnimSprite implements IBoxCollidable, ILayerProvider<MainScene.Layer> {
    private boolean facingRight = false;

    public enum State {
        idle, attack
    }
    private static final float PLAYER_WIDTH = 160f;
    private static final float PLAYER_HEIGHT = PLAYER_WIDTH;
    private static final float SPEED = 300f;
    private static final float FIRE_INTERVAL = 0.5f;
    private float fireCoolTime = FIRE_INTERVAL;
    private static final float HIT_INTERVAL = 2.0f;
    private float hitCoolTime = HIT_INTERVAL;
    private static final float SWORD_RANGE = 500f;
    public float x;
    public float y;
    public int hp = 5;
    public State state = State.idle;
    private final JoyStick joyStick;
    protected static Gauge gauge = new Gauge(0.1f, R.color.player_gauge_fg, R.color.player_gauge_bg);

    public Player(JoyStick joyStick, float startX, float startY) {
        super(R.mipmap.player_idle, 5, 4);
        this.joyStick = joyStick;
        setPosition(startX, startY, PLAYER_WIDTH, PLAYER_HEIGHT);
    }

    @Override
    public void update() {
        //super.update();
        MainScene scene = (MainScene) Scene.top();

        Enemy nearest = findNearestEnemy();
        if (null != nearest) {
            fireCoolTime += GameView.frameTime;
            if (fireCoolTime > FIRE_INTERVAL) {
                Sword sword = Sword.get(x, y, nearest);
                Scene.top().add(MainScene.Layer.sword, sword);
                Sound.playEffect(R.raw.player_attack);
                setState(State.attack);
                fireCoolTime = 0;
            }
        }
        else {
            setState(State.idle);
        }

        hitCoolTime += GameView.frameTime;
        if (hitCoolTime > HIT_INTERVAL) {
            ArrayList<IGameObject> enemys = scene.objectsAt(MainScene.Layer.enemy);
            for (int index = enemys.size() - 1; index >= 0; index--) {
                Enemy enemy = (Enemy) enemys.get(index);
                if (null == enemy) continue;
                boolean collides = CollisionHelper.collidesRadius(this, enemy);
                if (collides) {
                    --hp;
                    Log.d("Player", "Player HP: "+hp);
                    break;
                }
                else {
                    Log.d("Player", "No Collides");
                }
            }
            hitCoolTime = 0;
        }

        if (joyStick.power > 0) {
            float distance = SPEED * joyStick.power * GameView.frameTime;

            final int way = 8;
            final double TWO_PI = Math.PI * 2;
            float eightWayAngle = (float) (Math.round(way * joyStick.angle_radian / TWO_PI) * TWO_PI / way);
            float dx = (float) (distance * Math.cos(eightWayAngle));
            float dy = (float) (distance * Math.sin(eightWayAngle));
            x += dx;
            y += dy;

            float w_r = PLAYER_WIDTH / 2f;
            float h_r = PLAYER_HEIGHT / 2f;

            x = Math.max(w_r, Math.min(x, scene.backgroundWidth - w_r));
            y = Math.max(h_r, Math.min(y, scene.backgroundHeight - h_r));

            setPosition(x, y);

            if (dx != 0) {
                facingRight = dx > 0;
                setState(state);
            }
        }


    }

    private void setState(State state) {
        this.state = state;

        if (facingRight) {
            if (state == State.idle)
                setImageResourceId(R.mipmap.player_idle_rev);
            else
                setImageResourceId(R.mipmap.player_attack_rev);
        } else {
            if (state == State.idle)
                setImageResourceId(R.mipmap.player_idle);
            else
                setImageResourceId(R.mipmap.player_attack);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        MainScene scene = (MainScene) Scene.top();
        float camX = scene.camX;
        float camY = scene.camY;

        canvas.save();
        canvas.translate(-camX, -camY);
        super.draw(canvas);
        float gauge_width = PLAYER_WIDTH * 0.7f;
        float gauge_x = x - gauge_width / 2;
        float gauge_y = dstRect.bottom;
        gauge.draw(canvas, gauge_x, gauge_y, gauge_width, fireCoolTime / FIRE_INTERVAL);
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

    public Enemy findNearestEnemy() {
        float nearest_dist_sq = SWORD_RANGE * SWORD_RANGE;
        Enemy nearest = null;
        MainScene scene = (MainScene) Scene.top();
        ArrayList<IGameObject> enemies = scene.objectsAt(MainScene.Layer.enemy);
        for (IGameObject gameObject: enemies) {
            if (!(gameObject instanceof Enemy)) continue;
            Enemy enemy = (Enemy) gameObject;
            float ex = enemy.getX();
            float ey = enemy.getY();

            float dx = x - ex;
            float dx_sq = dx * dx;
            if (dx_sq > nearest_dist_sq) continue;
            float dy = y - ey;
            float dy_sq = dy * dy;
            if (dy_sq > nearest_dist_sq) continue;
            float dist_sq = dx_sq + dy_sq;
            if (nearest_dist_sq > dist_sq) {
                nearest_dist_sq = dist_sq;
                nearest = enemy;
            }
        }
        return nearest;
    }

    public RectF getCollisionRect() {
        return dstRect;
    }

    @Override
    public MainScene.Layer getLayer() {
        return MainScene.Layer.player;
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
