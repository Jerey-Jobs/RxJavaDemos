package com.jerey.rxjavademo;

import android.util.Log;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Xiamin on 2017/1/14.
 */

/**
 * 第四章：学会使用left转变类型
 * <p>
 * <p>
 * Log is:
 * 01-14 22:21:01.866 29758-29758/com.jerey.rxjavademo I/Demo4: in left :1
 * 01-14 22:21:01.867 29758-29758/com.jerey.rxjavademo I/Demo4: arter left: had left: 1
 */
public class Demo4 {
    public static final String TAG = "Demo4";

    public static void test() {
        Observable.just(1)
                /**
                 * Integer转换为String
                 */
                .lift(new Observable.Operator<String, Integer>() {

                    @Override
                    public Subscriber<? super Integer> call(final Subscriber<? super String> subscriber) {
                        return new Subscriber<Integer>() {
                            @Override
                            public void onCompleted() {
                                subscriber.onCompleted();
                            }

                            @Override
                            public void onError(Throwable e) {
                                subscriber.onError(e);
                            }

                            @Override
                            public void onNext(Integer integer) {
                                Log.i(TAG, "in left :" + integer);
                                subscriber.onNext("had left: " + integer);
                            }
                        };
                    }
                })
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        Log.i(TAG, "arter left: " + s);
                    }
                });
    }
}
