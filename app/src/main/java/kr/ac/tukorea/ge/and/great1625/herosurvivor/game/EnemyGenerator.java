package kr.ac.tukorea.ge.and.great1625.herosurvivor.game;

import android.graphics.Canvas;

import java.util.Random;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.GameView;

public class EnemyGenerator implements IGameObject {
    private final int BG_WIDTH, BG_HEIGHT;
    private final Random random = new Random();
    private float enemyTime = 0f;
    private float totalTime = 0f;
    private float spawnInterval = 1.5f;
    private final float MinSpawnInterval = 0.3f;

    public EnemyGenerator(int bgWidth, int bgHeight) {
        BG_WIDTH = bgWidth; BG_HEIGHT = bgHeight;
    }

    @Override
    public void update() {
        float delta = GameView.frameTime;
        totalTime += delta;
        enemyTime -= delta;

        // 30초 동안 spawnInterval 이 MinSpawnInterval 에 근접
        float t = totalTime / 30.0f;
        spawnInterval = Math.max(MinSpawnInterval, 1.5f - t * 1.2f);

        if (enemyTime <= 0f) {
            int type = random.nextInt(Enemy.MAX_TYPE);
            int x = random.nextInt(BG_WIDTH);
            int y = random.nextInt(BG_HEIGHT);
            Scene.top().add(Enemy.get(type, x, y));

            enemyTime += spawnInterval;
        }
    }

    @Override
    public void draw(Canvas canvas) {}
}
