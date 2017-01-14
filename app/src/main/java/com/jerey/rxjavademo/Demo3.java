package com.jerey.rxjavademo;

/**
 * 第三章：Scheduler 线程控制
 * Schedulers.immediate(): 直接在当前线程运行，相当于不指定线程。这是默认的 Scheduler。
 * Schedulers.newThread(): 总是启用新线程，并在新线程执行操作。
 * Schedulers.io(): I/O 操作（读写文件、读写数据库、网络信息交互等）所使用的 Scheduler
 * 行为模式和 newThread() 差不多，区别在于 io() 的内部实现是是用一个无数量上限的线程池，可以重用空闲的线程，因此多数情况下 io() 比 newThread() 更有效率。不要把计算工作放在 io() 中，可以避免创建不必要的线程。
 * Schedulers.computation(): 计算所使用的 Scheduler。这个计算指的是 CPU 密集型计算，即不会被 I/O 等操作限制性能的操作，例如图形的计算。这个 Scheduler 使用的固定的线程池，大小为 CPU 核数。不要把 I/O 操作放在 computation() 中，否则 I/O 操作的等待时间会浪费 CPU。
 * 另外， Android 还有一个专用的 AndroidSchedulers.mainThread()，它指定的操作将在 Android 主线程运行。
 * <p>
 * Created by Xiamin on 2017/1/14.
 */

import android.util.Log;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Log结果：
 * 01-14 21:52:34.823 11566-11616/com.jerey.rxjavademo D/Demo3: OnSubscribe Threadid: 1423
 * 01-14 21:52:34.867 11566-11566/com.jerey.rxjavademo D/Demo3: map Threadid: 1
 * 01-14 21:52:34.867 11566-11566/com.jerey.rxjavademo D/Demo3: onNext Threadid: 1
 * 01-14 21:52:34.867 11566-11566/com.jerey.rxjavademo D/Demo3:  test:1
 */

public class Demo3 {
    public static final String TAG = "Demo3";

    public static void test() {
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                subscriber.onNext(1);
                Log.d(TAG, "OnSubscribe Threadid: " + Thread.currentThread().getId());
            }
        })
                .subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
                .observeOn(AndroidSchedulers.mainThread()) // 指定 Subscriber 的回调发生在主线程
                .map(new Func1<Integer, String>() {
                    @Override
                    public String call(Integer integer) {
                        Log.d(TAG, "map Threadid: " + Thread.currentThread().getId());
                        return " test:" + integer;
                    }
                })
                .lift(new Observable.Operator<String, String>() {
                    @Override
                    public Subscriber<? super String> call(final Subscriber<? super String> subscriber) {
                        return new Subscriber<String>() {
                            @Override
                            public void onCompleted() {
                                subscriber.onCompleted();
                            }

                            @Override
                            public void onError(Throwable e) {
                                subscriber.onError(e);
                            }

                            @Override
                            public void onNext(String s) {
                                subscriber.onNext("lift->" + s);
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
                        Log.d(TAG, "onNext Threadid: " + Thread.currentThread().getId());
                        Log.d(TAG, s);
                    }
                });
    }

}
