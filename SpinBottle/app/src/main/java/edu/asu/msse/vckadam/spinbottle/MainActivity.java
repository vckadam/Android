package edu.asu.msse.vckadam.spinbottle;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.hardware.SensorEventListener;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements SensorEventListener{
    TextView textview, textview3;
    Button b1;
    float value;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SensorManager sensorM = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor accelerometer = sensorM.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        sensorM.registerListener(this, accelerometer, SensorManager.SENSOR_STATUS_ACCURACY_HIGH);
        textview = (TextView)findViewById(R.id.textView);
        textview3 = (TextView)findViewById(R.id.textView3);
        b1 = (Button)findViewById(R.id.button);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        value = 0;
        if(event.values[0] > 5) {
            //Toast t = Toast.makeText(this,"This is awesome",Toast.LENGTH_SHORT);
            //t.show();
            if(Math.abs(event.values[0]) > value) {
                value = event.values[0];
            }

            b1.performClick();
        }
    }

    public void clockwise(View view){
        ImageView image = (ImageView)findViewById(R.id.imageView);
        Random random = new Random();
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        textview.setText(df.format(value) + " ");
        textview3.setText(Html.fromHtml("m/s<sup>2</sup>"));
        int number = random.nextInt(8)+1;
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotation);;
        switch(number) {
            case 1 :
                animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotation);
                if(image != null) {
                    image.startAnimation(animation);
                }
                break;
            case 2 :
                animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotation2);
                if(image != null) {
                    image.startAnimation(animation);
                }
                break;
            case 3 :
                animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotation3);
                if(image != null) {
                    image.startAnimation(animation);
                }
                break;
            case 4 :
                animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotation4);
                if(image != null) {
                    image.startAnimation(animation);
                }
                break;
            case 5 :
                animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotation5);
                if(image != null) {
                    image.startAnimation(animation);
                }
                break;
            case 6 :
                animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotation6);
                if(image != null) {
                    image.startAnimation(animation);
                }
                break;
            case 7 :
                animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotation7);
                if(image != null) {
                    image.startAnimation(animation);
                }
                break;
            case 8 :
                animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotation8);
                if(image != null) {
                    image.startAnimation(animation);
                }
                break;
        }
        Button b1 = (Button)findViewById(R.id.button);
        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade);
        if(b1 != null) {
            b1.startAnimation(animation1);
        }

        animation1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent i = new Intent(MainActivity.this, Dialogbox.class);
                startActivity(i);
                //Toast t = Toast.makeText(MainActivity.this,"This is awesome",Toast.LENGTH_SHORT);
                //t.show();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


    }
}
