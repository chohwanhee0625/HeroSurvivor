package kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects;

import android.graphics.Canvas;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.Metrics;

public class Background extends Sprite  {
    private final float height;

    public Background(int mipmapId) {
        super(mipmapId);
        this.height = bitmap.getHeight() * Metrics.width / bitmap.getWidth();
        setPosition(Metrics.width / 2, Metrics.height / 2, Metrics.width, height);
    }

    @Override
    public void draw(Canvas canvas) {
        //super.draw(canvas);
        dstRect.set(0, 0, Metrics.width, Metrics.height);
        canvas.drawBitmap(bitmap, null, dstRect, null);
    }
}
