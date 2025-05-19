package kr.ac.tukorea.ge.spgp2025.a2dg.framework.objects;

import android.graphics.Canvas;

import kr.ac.tukorea.ge.spgp2025.a2dg.framework.view.Metrics;

public class Background extends Sprite  {
    private final float width, height;

    public Background(int mipmapId) {
        super(mipmapId);
        height = bitmap.getHeight();
        width = bitmap.getWidth() ;
        setPosition(Metrics.width / 2, Metrics.height / 2, width, height);
    }

    @Override
    public void draw(Canvas canvas) {
        //super.draw(canvas);
        dstRect.set(-width / 2.f, -height / 2.f, Metrics.width, Metrics.height);
        canvas.drawBitmap(bitmap, null, dstRect, null);
    }
}
