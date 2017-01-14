package com.jerey.rxjavademo;

import android.util.Log;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by Xiamin on 2017/1/14.
 */

/**
 * 第五章：学会使用Observable.Transformer 改变自身属性
 * Log:
 * 01-14 22:29:30.179 3753-3753/com.jerey.rxjavademo D/Demo5: xiamni10
 */
public class Demo5 {
    public static final String TAG = "Demo5";

    public static void test() {
        Observable observable = Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                subscriber.onNext(10);
            }
        });
        Observable.Transformer<Integer, String> transformer = new Observable.Transformer<Integer, String>() {
            @Override
            public Observable<String> call(Observable<Integer> integerObservable) {
                return integerObservable.map(new Func1<Integer, String>() {
                    @Override
                    public String call(Integer integer) {
                        return "xiamni" + integer;
                    }
                });
            }
        };
        observable.compose(transformer)
                .subscribe(new Subscriber() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Object o) {
                        Log.d(TAG, o.toString());
                    }
                });


    }
}
