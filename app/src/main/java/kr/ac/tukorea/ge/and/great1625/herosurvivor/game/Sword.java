package kr.ac.tukorea.ge.and.great1625.herosurvivor.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

import java.util.ArrayList;

import kr.ac.tukorea.ge.and.great1625.herosurvivor.R;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IBoxCollidable;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.ILayerProvider;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IRecyclable;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.util.CollisionHelper;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.Metrics;

public class Sword extends Sprite implements IRecyclable, IBoxCollidable, ILayerProvider<MainScene.Layer> {

    private static final float SWORD_HEIGHT = 100f;
    private static final float SWORD_WIDTH = SWORD_HEIGHT / 2;
    private static final float SPEED = 1000f;
    private int power;
    protected float angle = -90;
    private Enemy target;

    public static Sword get(float x, float y, Enemy target) {
        return Scene.top().getRecyclable(Sword.class).init(x, y, target);
    }

    public Sword() {
        super(R.mipmap.sword);
        setPosition(0, 0, SWORD_WIDTH, SWORD_HEIGHT);
    }

    private Sword init(float x, float y, Enemy target) {
        this.x = x;
        this.y = y;
        this.target = target;
        dx = target.getX() - x;
        dy = target.getY() - y;
        setPosition(x, y, SWORD_WIDTH, SWORD_HEIGHT);
        angle = (float) Math.toDegrees(Math.atan2(dy, dx));
        this.power = 50;
        return this;
    }

    @Override
    public void update() {
        super.update();
        MainScene scene = (MainScene) Scene.top();
        float camX = scene.camX;
        float camY = scene.camY;
        if (x + radius < camX || x - radius > camX + Metrics.width ||
                y + radius < camY || y - radius > camY + Metrics.height) {
            scene.remove(MainScene.Layer.sword, this);
            return;
        }

        ArrayList<IGameObject> enemys = scene.objectsAt(MainScene.Layer.enemy);
        for (int index = enemys.size() - 1; index >= 0; index--) {
            Enemy enemy = (Enemy) enemys.get(index);
            if (null == enemy) continue;
            boolean collides = CollisionHelper.collidesRadius(this, enemy);
            if (collides) {
                scene.remove(MainScene.Layer.sword, this);
                hit(enemy, power, scene);
                break;
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        MainScene scene = (MainScene) Scene.top();
        float camX = scene.camX;
        float camY = scene.camY;
        canvas.save();
        canvas.translate(-camX, -camY);
        canvas.rotate(angle, x, y);
        super.draw(canvas);
        canvas.restore();
    }

    private void hit(Enemy enemy, int damage, MainScene scene) {
        boolean dead = enemy.decreaseLife(damage);
        if (dead) {
            scene.remove(MainScene.Layer.enemy, enemy);
            scene.score.add(enemy.score());
        }
    }

    public int getPower() {
        return power;
    }
    public RectF getCollisionRect() {
        return dstRect;
    }

    @Override
    public void onRecycle() {
    }

    @Override
    public MainScene.Layer getLayer() {
        return MainScene.Layer.sword;
    }
}
