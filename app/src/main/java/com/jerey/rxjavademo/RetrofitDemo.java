package com.jerey.rxjavademo;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Xiamin on 2017/1/14.
 */

/**
 * 第六章：学会使用Retrofit
 * 网络请求结果在文件代码最后
 */

public class RetrofitDemo {
    public static final String TAG = "RetrofitDemo";
    public static String baseUrl = "http://jerey.cn/";

    public static void test() {
        OkHttpClient client;
        client = new OkHttpClient.Builder()
                //添加应用拦截器
                .addInterceptor(new HttpInterceptor())
                //添加网络拦截器
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                //将client与retrofit关联
                .client(client)
                //增加返回值为String的支持
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        IRetrofitService retrofitService = retrofit.create(IRetrofitService.class);
        retrofitService
                .getTestHtmlString()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        /**
                         * 01-14 23:12:14.572 32015-32015/com.jerey.rxjavademo
                         * D/RetrofitDemo: java.lang.SecurityException: Permission denied (missing INTERNET permission?)
                         */
                        Log.d(TAG, e.toString());
                    }

                    @Override
                    public void onNext(String s) {
                        Log.d(TAG, s);
                    }
                });

    }


    static class HttpInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            //打印请求链接
            String TAG_REQUEST = "request";
            Log.e(TAG_REQUEST, "request" + request.url().toString());
            Response response = chain.proceed(request);
            //打印返回的message
            Log.e(TAG_REQUEST, "response" + response.toString());

            /**
             * 01-14 23:13:24.852 1484-1650/com.jerey.rxjavademo E/request: requesthttp://jerey.cn/
             * 01-14 23:13:25.685 1484-1650/com.jerey.rxjavademo E/request: responseResponse{protocol=http/1.1, code=200, message=OK, url=http://jerey.cn/}
             */
            return response;
        }
    }
}

/**
 * 01-14 23:16:28.631 4432-4432/com.jerey.rxjavademo D/RetrofitDemo: <!DOCTYPE html>
 * <html lang="en">
 * <p>
 * <head>
 * <meta charset="utf-8">
 * <meta http-equiv="X-UA-Compatible" content="IE=edge">
 * <meta name="google-site-verification" content="xBT4GhYoi5qRD5tr338pgPM5OWHHIDR6mNg1a3euekI" />
 * <meta name="viewport" content="width=device-width, initial-scale=1">
 * <meta name="description" content="这里是夏敏的个人博客，希望能与你一同进步。">
 * <meta name="keywords"  content="夏敏, Android, Anderson, Jerey_Jobs, Anderson大码渣, 博客, 个人网站, 互联网">
 * <meta name="theme-color" content="#000000">
 * <p>
 * <title>夏敏的博客 | Anderson's Blog</title>
 * <p>
 * <!-- Web App Manifest -->
 * <link rel="manifest" href="/pwa/manifest.json">
 * <p>
 * <!-- Favicon -->
 * <link rel="shortcut icon" href="/img/favicon.ico">
 * <p>
 * <!-- Canonical URL -->
 * <link rel="canonical" href="https://jerey-jobs.github.io//">
 * <p>
 * <!-- Bootstrap Core CSS -->
 * <link rel="stylesheet" href="/css/bootstrap.min.css">
 * <p>
 * <!-- Custom CSS -->
 * <link rel="stylesheet" href="/css/hux-blog.min.css">
 * <p>
 * <!-- Pygments Github CSS -->
 * <link rel="stylesheet" href="/css/syntax.css">
 * <p>
 * <!-- Custom Fonts -->
 * <!-- <link href="http://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet" type="text/css"> -->
 * <!-- Hux change font-awesome CDN to qiniu -->
 * <link href="//cdnjs.cloudflare.com/ajax/libs/font-awesome/4.6.3/css/font-awesome.min.css" rel="stylesheet" type="text/css">
 * <p>
 * <p>
 * <!-- Hux Delete, sad but pending in China
 * <link href='http://fonts.googleapis.com/css?family=Lora:400,700,400italic,700italic' rel='stylesheet' type='text/css'>
 * <link href='http://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800' rel='stylesheet' type='text/
 * css'>
 * -->
 * <p>
 * <p>
 * <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
 * <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
 * <!--[if lt IE 9]>
 * <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
 * <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
 * <![endif]-->
 * <p>
 * <!-- ga & ba script hoook -->
 * <script></script>
 * </head>
 * <p>
 * <p>
 * <!-- hack iOS CSS :active style -->
 * <body ontouchstart="">
 * <p>
 * <!-- Navigation -->
 * <nav class="navbar navbar-default navbar-custom navbar-fixed-top">
 * <div class="container-fluid">
 * <!-- Brand and toggle get grouped for better mobile display -->
 * <div class="navbar-header page-scroll">
 * <button type="button" class="navbar-toggle">
 * <span class="sr-only">Toggle navigation</span>
 * <span class="icon-bar"></span>
 * <span class="icon-bar"></span>
 * <span class="icon-bar"></span>
 * </button>
 * <a class="navbar-brand" href="/">夏敏的博客</a>
 * </div>
 * <p>
 * <!-- Collect the nav links, forms, and other content for toggling -->
 * <div id="huxblog_navbar">
 * <div class="navbar-collapse">
 * <ul class="nav navbar-nav navbar-right">
 * <li>
 * <a href="/">Home</a>
 * </li>
 * <p>
 * <li>
 * <a href="/about/">About</a>
 * </li>
 * <p>
 * <li>
 * <a href="/portfolio/">Portfolio</a>
 * </li>
 * <p>
 * <li>
 * <a href="/tags/">Tags</a>
 * </li>
 * <p>
 * </ul>
 * </div>
 * </div>
 * <!-- /.navbar-collapse -->
 * </div>
 * <!-- /.container -->
 * </nav>
 * <script>
 * // Drop Bootstarp low-performance Navbar
 * // Use customize navbar with high-quality material design animation
 * // in high-perf jank-free CSS3 implementation
 * var $body   = document.body;
 * var $toggle = document.querySelector('.
 * 01-14 23:16:28.631 4432-4432/com.jerey.rxjavademo D/RetrofitDemo: onCompleted
 */
