package kr.ac.tukorea.ge.and.great1625.herosurvivor.game;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects.Sprite;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.scene.Scene;
import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.Metrics;

public class Background extends Sprite {

    public Background(int mipmapId) {
        super(mipmapId);
        height = bitmap.getHeight();
        width = bitmap.getWidth();
    }

    @Override
    public void draw(Canvas canvas) {
        MainScene scene = (MainScene) Scene.top();
        float camX = scene.camX;
        float camY = scene.camY;

        canvas.save();
        canvas.translate(-camX, -camY);
        Rect src = new Rect((int)camX, (int)camY,
                (int)(camX + Metrics.width), (int)(camY + Metrics.height));
        RectF dst = new RectF(0, 0, (int)width, (int)height);
        canvas.drawBitmap(bitmap, src, dst, null);
        canvas.restore();
    }

    public float getWidth() {
        return width;
    }
    public float getHeight() {
        return height;
    }

}
