package edu.asu.msse.vckadam.spinbottle;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class Dialogbox extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_dialogbox);
    }
    public void truthCliced(View view) {
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }
    public void dareClicked(View view) {
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }
}
