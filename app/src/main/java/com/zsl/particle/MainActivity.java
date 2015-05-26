package com.zsl.particle;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;

import com.plattysoft.leonids.ParticleSystem;

import java.util.Random;


public class MainActivity extends ActionBarActivity {


    Button bt_particle;
    ParticleSystem ps;

    Handler handler;

    int height=0;
    int widht=0;

    private int angle=0;

    int r=300;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt_particle = (Button) findViewById(R.id.main_bt_particle);
        bt_particle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int[] location = new int[2];
                bt_particle.getLocationOnScreen(location);
                widht= location[0]+(bt_particle.getRight()-bt_particle.getLeft())/2;
                height = location[1]+(bt_particle.getBottom()-bt_particle.getTop())/2;

                if (ps == null) {
                    ps = new ParticleSystem(MainActivity.this, 100, R.drawable.star_pink, 800);
                    ps.setScaleRange(0.7f, 1.3f);
                    ps.setSpeedRange(0.05f, 0.1f);
                    ps.setRotationSpeedRange(90, 180);
                    ps.setFadeOut(200, new AccelerateInterpolator());
                    ps.emit((widht / 2), (height / 2), 40);
                    handler.sendEmptyMessageDelayed(1, 50);
                } else {
                    ps.cancel();
                    ps = null;
                }
            }
        });



        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                Random random=new Random();
                int centerX=widht;
                int centerY=height;

                double radian=Math.toRadians(angle);
                //  通过圆心坐标、半径和当前角度计算当前圆周的某点横坐标
                int currentX = (int) (centerX + r * Math.cos(radian));
                //  通过圆心坐标、半径和当前角度计算当前圆周的某点纵坐标
                int currentY = (int) (centerY + r * Math.sin(radian));
                Log.e("XXXXYYYYYY",currentX+":"+currentY);
                //  重绘View，并在圆周的某一点扭曲图像
                ps.updateEmitPoint(currentX, currentY);
                angle += 5;
                if (angle > 360) {
                    angle = 5;
                }
                handler.sendEmptyMessageDelayed(1,50);
            }
        };



    }

}
