package kr.ac.tukorea.ge.and.great1625.herosurvivor.game;

import android.graphics.Canvas;

import java.util.Random;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.GameView;

public class EnemyGenerator implements IGameObject {
    private final Random random = new Random();
    private float enemyTime = 0;
    @Override
    public void update() {
        enemyTime -= GameView.frameTime;
        if (enemyTime < 0) {
            int level = random.nextInt(10);
            int index = random.nextInt(5);
//            Scene.top().add(new Enemy(level, index));
            enemyTime = random.nextFloat() + 0.05f;
        }
    }

    @Override
    public void draw(Canvas canvas) {

    }
}
