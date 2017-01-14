package com.jerey.rxjavademo;

import android.util.Log;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * 第二章：通过filter 控制筛选 通过map转换格式
 * Created by Xiamin on 2017/1/14.
 */

public class Demo2 {
    public static final String TAG = "Demo2";

    public static void test() {
        Observable.just(1, 2, 3, 4, 5)
                /**
                 * 筛选出偶数
                 */
                .filter(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer integer) {
                        return integer % 2 == 0;
                    }
                })
                /**
                 * 除10转double
                 */
                .map(new Func1<Integer, Double>() {
                    @Override
                    public Double call(Integer integer) {
                        return integer / 10.0;
                    }
                })
                .subscribe(new Subscriber() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(Object o) {
                        /** 打印结果
                         * 01-14 20:50:47.569 3113-3113/com.jerey.rxjavademo D/Demo2: 0.2
                         01-14 20:50:47.569 3113-3113/com.jerey.rxjavademo D/Demo2: 0.4
                         01-14 20:50:47.569 3113-3113/com.jerey.rxjavademo D/Demo2: onCompleted
                         */
                        Log.d(TAG, o.toString());
                    }
                });

    }
}
