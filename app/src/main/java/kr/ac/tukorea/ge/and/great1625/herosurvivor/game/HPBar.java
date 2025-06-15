package kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

import kr.ac.tukorea.ge.and.great1625.herosurvivor.R;
import kr.ac.tukorea.ge.and.great1625.herosurvivor.game.MainScene;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.interfaces.IGameObject;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.res.BitmapPool;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;

public class HPBar implements IGameObject {

    private static final int[] HPresIds = {
            R.mipmap.heart_row_0, R.mipmap.heart_row_1, R.mipmap.heart_row_2,
            R.mipmap.heart_row_3,R.mipmap.heart_row_4,R.mipmap.heart_row_5
    };

    private Bitmap bitmap;
    private final float left, top, width;
    private float height;
    private int hp = 5;

    public HPBar(float left, float top, float width) {
        this.left = left;
        this.top = top;
        this.width = width;
        setHp(hp);
    }

    public void setHp(int hp) {
        bitmap = BitmapPool.get(HPresIds[this.hp]);

        // 비율 유지한 높이 계산
        float bitmapRatio = (float) bitmap.getHeight() / bitmap.getWidth();
        this.height = width * bitmapRatio;
    }

    @Override
    public void update() {
        MainScene scene = (MainScene) Scene.top();
        int c_hp = scene.player.hp;
        if (hp != c_hp && c_hp >= 0) {
            hp = c_hp;
            setHp(hp);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        if (bitmap == null) return;
        RectF dstRect = new RectF(left, top, left + width, top + height);
        canvas.drawBitmap(bitmap, null, dstRect, null);
    }
}