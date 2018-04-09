package com.canhuah.h5;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }





    public void overrideUrl(View view){
        startActivity(new Intent(this,OverrideUrlActivity.class));
    }

    public void jsCallJava(View view){
        startActivity(new Intent(this,JscallJavaActivity.class));
    }

    public void overrideUrl3(){
        startActivity(new Intent(this,MainActivity.class));
    }



}
