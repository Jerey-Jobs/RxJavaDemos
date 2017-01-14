package com.jerey.rxjavademo;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Demo1.test();
        Demo2.test();
        Demo3.test();
        Demo4.test();
        Demo5.test();
        RetrofitDemo.test();
    }
}
