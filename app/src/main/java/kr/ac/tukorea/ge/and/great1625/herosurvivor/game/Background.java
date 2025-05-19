package kr.ac.tukorea.ge.and.great1625.herosurvivor.game;

import android.graphics.Canvas;
import android.graphics.Rect;

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
        //super.draw(canvas);
        MainScene scene = (MainScene) Scene.top();
        float px = scene.player.x;
        float py = scene.player.y;

        float camX = px - width / 2f;
        float camY = py - height / 2f;

        camX = Math.max(0, Math.min(camX, width));
        camY = Math.max(0, Math.min(camY, height));

        Rect src = new Rect((int)camX, (int)camY,
                (int)(camX + Metrics.width), (int)(camY + Metrics.height));

        Rect dst = new Rect(0, 0, (int)width, (int)height);

        canvas.drawBitmap(bitmap, src, dst, null);
    }

    public float getWidth() {
        return width;
    }
    public float getHeight() {
        return height;
    }

}
