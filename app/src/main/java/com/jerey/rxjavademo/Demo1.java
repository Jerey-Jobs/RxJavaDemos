package com.jerey.rxjavademo;

import android.util.Log;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action1;

/**
 * 第一章：
 * 数据的发射与接收
 * Created by Xiamin on 2017/1/14.
 */

public class Demo1 {
    public static final String TAG = "Demo1";

    public static void test() {
        /**
         * bong，数据发射
         */
        observable.subscribe(observer);

        /**
         * 写法二
         */
        Observable.just("第一章")
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.d(TAG, s);
                    }
                });
    }


    /**
     * Observable：发射源，英文释义“可观察的”，在观察者模式中称为“被观察者”或“可观察对象”；
     */
    static Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {

        @Override
        public void call(Subscriber<? super String> subscriber) {
            subscriber.onNext("xiamin");
        }
    });

    /**
     * Observer：接收源，英文释义“观察者”，没错！就是观察者模式中的“观察者”，可接收Observable、Subject发射的数据；
     */
    static Observer<String> observer = new Observer<String>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(String s) {
            Log.d(TAG, s);
        }
    };
}
