package kr.ac.tukorea.ge.and.great1625.herosurvivor.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;

import androidx.appcompat.app.AppCompatActivity;

import kr.ac.tukorea.ge.and.great1625.herosurvivor.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            startActivity(new Intent(this, HeroSurvivorActivity.class));
        }
        return super.onTouchEvent(event);
    }
}